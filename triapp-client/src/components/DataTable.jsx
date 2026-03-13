import React, { useEffect, useState } from 'react'

/* --- helpers --- */
const toCamel = s => s.replace(/_([a-z])/g, (_, c) => c.toUpperCase())
const toSnake = s => s.replace(/[A-Z]/g, m => '_' + m.toLowerCase())

function resolveValue(obj, key) {
  if (!obj || !key) return undefined
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
  if (key === 'heightInches' || key === 'weightPounds' || key === 'steps' || key.endsWith('Feet')) {
    return String(value)
  }
  if (key.endsWith('Sec') || key.endsWith('SecPerMile')) {
    if (key.toLowerCase().includes('elapsed') || key.toLowerCase().includes('moving')) {
      return secToHms(value)
    }
    if (key.toLowerCase().includes('pace') && key.toLowerCase().includes('mile')) {
      return secToMinSec(value)
    }
    return String(value)
  }
  if (key === 'avgPaceSecPer100Y') {
    return secToMinSec(value) + ' /100y'
  }
  if (key.toLowerCase().includes('speed') || key.toLowerCase().includes('mph')) {
    return Number(value).toFixed(1)
  }
  if (key.toLowerCase().includes('pace') && typeof value === 'number') {
    return secToMinSec(value)
  }
  if (typeof value === 'object') return JSON.stringify(value)
  return String(value)
}

/* --- DataTable component --- */
/**
 * Props:
 *  - title: string
 *  - endpoint: base endpoint (e.g. "/api/users")
 *  - fetchUrl: optional full URL to fetch instead (overrides endpoint)
 *  - columns: [{key, label}, ...]
 *  - onEdit: optional (row) => void — if provided, an Edit button column is added
 *  - onDelete: optional (row) => void — if provided, a Delete button column is added
 */
export default function DataTable({ title, endpoint, fetchUrl, columns, onEdit, onDelete }) {
  const [data, setData] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const urlToFetch = fetchUrl || endpoint

  useEffect(() => {
    if (!urlToFetch) return
    setLoading(true)
    setError(null)
    fetch(urlToFetch)
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
  }, [urlToFetch, title])

  if (!urlToFetch) return <div style={{ color: 'orange' }}>No endpoint configured for {title}</div>
  if (loading)     return <div>Loading {title}...</div>
  if (error)       return <div style={{ color: 'red' }}>Error loading {title}: {error}</div>
  if (!data || data.length === 0) return <div>No {title} found</div>

  return (
    <section className="table-section">
      <h2>{title}</h2>
      <div style={{ marginBottom: 8 }}>
        <small>Showing <strong>{data.length}</strong> rows from <code>{urlToFetch}</code></small>
      </div>
      <div className="table-wrap">
        <table>
          <thead>
            <tr>
              {columns.map(c => <th key={c.key}>{c.label}</th>)}
              {(onEdit || onDelete) && <th style={{ width: 60 }}></th>}
            </tr>
          </thead>
          <tbody>
            {data.map((row, i) => (
              <tr key={i}>
                {columns.map(col => {
                  const raw  = resolveValue(row, col.key)
                  const cell = prettyFormat(col.key, raw)
                  return <td key={col.key}>{cell}</td>
                })}
                {(onEdit || onDelete) && (
                  <td style={{ whiteSpace: 'nowrap' }}>
                    {onEdit && (
                      <button
                        onClick={() => onEdit(row)}
                        style={{
                          fontSize: '0.78rem',
                          padding: '0.2rem 0.6rem',
                          border: '1px solid #d1d5db',
                          borderRadius: 4,
                          background: '#f9fafb',
                          cursor: 'pointer',
                          marginRight: onDelete ? 4 : 0,
                        }}
                      >
                        Edit
                      </button>
                    )}
                    {onDelete && (
                      <button
                        onClick={() => onDelete(row)}
                        style={{
                          fontSize: '0.78rem',
                          padding: '0.2rem 0.6rem',
                          border: '1px solid #fca5a5',
                          borderRadius: 4,
                          background: '#fef2f2',
                          color: '#b91c1c',
                          cursor: 'pointer',
                        }}
                      >
                        Delete
                      </button>
                    )}
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  )
}
