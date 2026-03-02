import React from 'react'
import DataTable from './components/DataTable'
import './index.css'

export default function App() {
  return (
    <div className="container">
      <header>
        <h1>TriApp Records</h1>
        <p>Backend: http://localhost:8080</p>
      </header>

      <main>
        <DataTable
          title="Users"
          endpoint="/api/users"
          columns={[
            { key: "id", label: "ID" },
            { key: "firstName", label: "First Name" },
            { key: "lastName", label: "Last Name" },
            { key: "email", label: "Email" },
            { key: "heightInches", label: "Height (in)" },
            { key: "weightPounds", label: "Weight (lb)" }
          ]}
        />

        <DataTable
          title="Workouts"
          endpoint="/api/workouts"
          columns={[
            { key: "id", label: "ID" },
            { key: "workoutType", label: "Type" },
            { key: "date", label: "Date" },
            { key: "movingTimeSec", label: "Moving Time" },
            { key: "elapsedTimeSec", label: "Elapsed Time" },
            { key: "avgHeartRate", label: "Avg Heart Rate" },
            { key: "calories", label: "Calories" },
            { key: "userId", label: "User ID" }
          ]}
        />

        <DataTable
          title="Runs"
          endpoint="/api/runs"
          columns={[
            { key: "id", label: "ID" },
            { key: "distanceMiles", label: "Distance (mi)" },
            { key: "avgPaceSecPerMile", label: "Avg Pace (min/mi)" },
            { key: "elevationGainFeet", label: "Elevation Gain (ft)" },
            { key: "steps", label: "Steps" },
            { key: "avgCadenceSpm", label: "Avg Cadence (spm)" },
            { key: "workoutId", label: "Workout ID" }
          ]}
        />

        <DataTable
          title="Bikes"
          endpoint="/api/bikes"
          columns={[
            { key: "id", label: "ID" },
            { key: "distanceMiles", label: "Distance (mi)" },
            { key: "avgSpeedMph", label: "Avg Speed (mph)" },
            { key: "maxSpeedMph", label: "Max Speed (mph)" },
            { key: "elevationGainFeet", label: "Elevation Gain (ft)" },
            { key: "avgPedalRateRpm", label: "Avg Pedal Rate (rpm)" },
            { key: "workoutId", label: "Workout ID" }
          ]}
        />

        <DataTable
          title="Swims"
          endpoint="/api/swims"
          columns={[
            { key: "id", label: "ID" },
            { key: "distanceYards", label: "Distance (yd)" },
            { key: "avgPaceSecPer100Y", label: "Avg Pace (per 100 yd)" },
            { key: "avgStrokeRateSpm", label: "Avg Stroke Rate (spm)" },
            { key: "workoutId", label: "Workout ID" }
          ]}
        />
      </main>
    </div>
  )
}