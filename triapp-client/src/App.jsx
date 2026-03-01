import React from 'react'
import DataTable from './components/DataTable'

export default function App() {
  return (
    <div className="container">
      <header>
        <h1>TriApp — All Records</h1>
        <p>Backend: http://localhost:8080 — endpoints: /api/users, /api/workouts, /api/runs, /api/bikes, /api/swims</p>
      </header>

      <main>
        <DataTable title="Users" endpoint="/api/users" />
        <DataTable title="Workouts" endpoint="/api/workouts" />
        <DataTable title="Runs" endpoint="/api/runs" />
        <DataTable title="Bikes" endpoint="/api/bikes" />
        <DataTable title="Swims" endpoint="/api/swims" />
      </main>

      <footer>
        <small>If you run the backend and visit the dev server (localhost:5173) you should see table data.</small>
      </footer>
    </div>
  )
}