import React, { useEffect, useState } from 'react'

/* --- helpers --- */
const toCamel = s => s.replace(/_([a-z])/g, (_, c) => c.toUpperCase())
const toSnake = s => s.replace(/[A-Z]/g, m => '_' + m.toLowerCase())

function resolveValue(obj, key) {
  if (!obj || !key) return undefined

  // nested path support (not needed for your current API but kept)
  if (key.includes('.')) {
    return key.split('.').reduce((cur, p) => (cur ? resolveValue(cur, p) : undefined), obj)
  }

  if (Object.prototype.hasOwnProperty.call(obj, key)) return obj[key]
  const camel = toCamel(key)
  if (Object.prototype.hasOwnProperty.call(obj, camel)) return obj[camel]
  const snake = toSnake(key)
  if (Object.prototype.hasOwnProperty.call(obj, snake)) return obj[snake]
  const lower = key.toLowerCase()
  if (Object.prototype.hasOwnProperty.call(obj, lower)) return obj[lower]

  const found = Object.keys(obj).find(k => k.toLowerCase() === key.toLowerCase())
  if (found) return obj[found]

  return undefined
}

function secToHms(sec) {
  if (sec == null || Number.isNaN(Number(sec))) return ""
  const s = Number(sec)
  const h = Math.floor(s / 3600)
  const m = Math.floor((s % 3600) / 60)
  const ss = Math.floor(s % 60)
  if (h > 0) return `${h}:${String(m).padStart(2,'0')}:${String(ss).padStart(2,'0')}`
  return `${m}:${String(ss).padStart(2,'0')}`
}

function secToMinSec(sec) {
  if (sec == null || Number.isNaN(Number(sec))) return ""
  const s = Math.round(Number(sec))
  const m = Math.floor(s / 60)
  const ss = s % 60
  return `${m}:${String(ss).padStart(2,'0')}`
}

function prettyFormat(key, value) {
  if (value == null) return ""
  // numbers that should be shown as integers
  if (key === 'heightInches' || key === 'weightPounds' || key === 'steps' || key.endsWith('Feet')) {
    return String(value)
  }

  // times in seconds
  if (key.endsWith('Sec') || key.endsWith('SecPerMile')) {
    // elapsedTimeSec, movingTimeSec -> H:MM:SS
    if (key.toLowerCase().includes('elapsed') || key.toLowerCase().includes('moving')) {
      return secToHms(value)
    }
    // avgPaceSecPerMile -> mm:ss per mile
    if (key.toLowerCase().includes('pace') && key.toLowerCase().includes('mile')) {
      return secToMinSec(value)
    }
    return String(value)
  }

  // avgPaceSecPer100Y -> mm:ss /100y
  if (key === 'avgPaceSecPer100Y') {
    return secToMinSec(value) + ' /100y'
  }

  // speeds / rates
  if (key.toLowerCase().includes('speed') || key.toLowerCase().includes('mph')) {
    return Number(value).toFixed(1)
  }

  // pace fields that explicitly contain 'Pace' measured in sec per mile
  if (key.toLowerCase().includes('pace') && typeof value === 'number') {
    return secToMinSec(value)
  }

  // default: stringify
  if (typeof value === 'object') return JSON.stringify(value)
  return String(value)
}

/* --- component --- */
export default function DataTable({ title, endpoint, columns }) {
  const [data, setData] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    setLoading(true)
    setError(null)
    fetch(endpoint)
      .then(async res => {
        if (!res.ok) {
          const txt = await res.text()
          throw new Error(`${res.status} ${res.statusText}: ${txt}`)
        }
        return res.json()
      })
      .then(json => {
        const arr = Array.isArray(json) ? json : [json]
        setData(arr)
        if (arr.length > 0) console.log(`DataTable "${title}" sample:`, arr[0])
      })
      .catch(e => {
        console.error(e)
        setError(e.toString())
      })
      .finally(() => setLoading(false))
  }, [endpoint, title])

  if (loading) return <div>Loading {title}...</div>
  if (error) return <div style={{color:'red'}}>Error loading {title}: {error}</div>
  if (!data || data.length === 0) return <div>No {title} found</div>

  return (
    <section className="table-section">
      <h2>{title}</h2>
      <div className="table-wrap">
        <table>
          <thead>
            <tr>
              {columns.map(c => <th key={c.key}>{c.label}</th>)}
            </tr>
          </thead>
          <tbody>
            {data.map((row, i) => (
              <tr key={i}>
                {columns.map(col => {
                  const raw = resolveValue(row, col.key)
                  const cell = prettyFormat(col.key, raw)
                  return <td key={col.key}>{cell}</td>
                })}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  )
}