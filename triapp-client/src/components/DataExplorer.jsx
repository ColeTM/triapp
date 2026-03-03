import React, { useState } from 'react'
import DataTable from './DataTable'

/**
 * Props:
 *  - title: name for the table
 *  - endpoint: base endpoint (e.g. "/api/workouts")
 *  - columns: passed to DataTable
 */
export default function DataExplorer({ title, endpoint, columns }) {
  const [id, setId] = useState('')
  const [query, setQuery] = useState('')
  const [fetchUrl, setFetchUrl] = useState(null)

  const handleFetchAll = () => {
    setFetchUrl(endpoint)
  }

  const handleFetchById = () => {
    if (!id) {
      alert('Please enter an id to fetch')
      return
    }
    const u = endpoint.endsWith('/') ? `${endpoint}${id}` : `${endpoint}/${id}`
    setFetchUrl(u)
  }

  const handleFetchQuery = () => {
    // query should be something like: userId=1&workoutType=RUN
    const q = (query || '').trim()
    if (!q) {
      alert('Please enter a query string (e.g. userId=1&workoutType=RUN)')
      return
    }
    const u = endpoint.includes('?') ? `${endpoint}&${q}` : `${endpoint}?${q}`
    setFetchUrl(u)
  }

  return (
    <div style={{marginBottom: '1.5rem'}}>
      <div style={{display:'flex', gap:8, alignItems:'center', marginBottom:8}}>
        <strong>{title}</strong>
        <button onClick={handleFetchAll}>Fetch All</button>

        <div style={{display:'flex', gap:6, alignItems:'center'}}>
          <input
            placeholder="id"
            value={id}
            onChange={e => setId(e.target.value)}
            style={{width:80}}
          />
          <button onClick={handleFetchById}>Fetch By ID</button>
        </div>

        <div style={{display:'flex', gap:6, alignItems:'center'}}>
          <input
            placeholder="query (e.g. userId=1&workoutType=RUN)"
            value={query}
            onChange={e => setQuery(e.target.value)}
            style={{width:320}}
          />
          <button onClick={handleFetchQuery}>Fetch With Query</button>
        </div>
      </div>

      <div style={{marginBottom:8}}>
        <small>Current fetch URL: <code>{fetchUrl || endpoint}</code></small>
      </div>

      <DataTable title={title} endpoint={endpoint} fetchUrl={fetchUrl || endpoint} columns={columns} />
    </div>
  )
}