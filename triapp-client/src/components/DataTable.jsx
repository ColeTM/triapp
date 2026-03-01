import React, { useEffect, useState } from 'react'

export default function DataTable({ title, endpoint }) {
  const [data, setData] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    setLoading(true)
    setError(null)

    fetch(endpoint)
      .then(async (res) => {
        if (!res.ok) {
          const text = await res.text()
          throw new Error(`${res.status} ${res.statusText}: ${text}`)
        }
        return res.json()
      })
      .then((json) => {
        setData(Array.isArray(json) ? json : [json])
      })
      .catch((e) => setError(e.toString()))
      .finally(() => setLoading(false))
  }, [endpoint])

  // derive headers from first object
  const headers = (data && data.length > 0) ? Object.keys(data[0]) : []

  return (
    <section className="table-section">
      <h2>{title}</h2>

      {loading && <div className="info">Loading...</div>}
      {error && <div className="error">Error: {error}</div>}

      {!loading && !error && (!data || data.length === 0) && (
        <div className="info">No records found</div>
      )}

      {!loading && !error && data && data.length > 0 && (
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                {headers.map((h) => (
                  <th key={h}>{h}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {data.map((row, idx) => (
                <tr key={idx}>
                  {headers.map((h) => {
                    let v = row[h]
                    // pretty-print nested objects/arrays
                    if (typeof v === 'object' && v !== null) {
                      v = JSON.stringify(v)
                    }
                    return <td key={h}>{String(v ?? '')}</td>
                  })}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  )
}