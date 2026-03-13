import React from 'react'
import DataExplorer from './components/DataExplorer'
import UserForm from './components/UserForm'
import UserFilterPanel from './components/UserFilterPanel'
import WorkoutForm from './components/WorkoutForm'
import WorkoutFilterPanel from './components/WorkoutFilterPanel'
import RunForm from './components/RunForm'
import RunFilterPanel from './components/RunFilterPanel'
import BikeForm from './components/BikeForm'
import BikeFilterPanel from './components/BikeFilterPanel'
import SwimForm from './components/SwimForm'
import SwimFilterPanel from './components/SwimFilterPanel'
import './index.css'

export default function App() {
  return (
    <div className="container">
      <header>
        <h1>TriApp Records Explorer</h1>
        <p>Backend: http://localhost:8080 — use Fetch All / By ID / Filters to get subsets</p>
      </header>

      <main>
        <DataExplorer
          title="Users"
          endpoint="/api/users"
          FormComponent={UserForm}
          FilterComponent={UserFilterPanel}
          columns={[
            { key: "id",           label: "ID" },
            { key: "firstName",    label: "First Name" },
            { key: "lastName",     label: "Last Name" },
            { key: "email",        label: "Email" },
            { key: "heightInches", label: "Height (in)" },
            { key: "weightPounds", label: "Weight (lb)" }
          ]}
        />

        <DataExplorer
          title="Workouts"
          endpoint="/api/workouts"
          FormComponent={WorkoutForm}
          FilterComponent={WorkoutFilterPanel}
          columns={[
            { key: "id",             label: "ID" },
            { key: "workoutType",    label: "Type" },
            { key: "date",           label: "Date" },
            { key: "movingTimeSec",  label: "Moving Time" },
            { key: "elapsedTimeSec", label: "Elapsed Time" },
            { key: "avgHeartRate",   label: "Avg Heart Rate" },
            { key: "calories",       label: "Calories" },
            { key: "userId",         label: "User ID" }
          ]}
        />

        <DataExplorer
          title="Runs"
          endpoint="/api/runs"
          FormComponent={RunForm}
          FilterComponent={RunFilterPanel}
          columns={[
            { key: "id",                label: "ID" },
            { key: "distanceMiles",     label: "Distance (mi)" },
            { key: "avgPaceSecPerMile", label: "Avg Pace (min/mi)" },
            { key: "elevationGainFeet", label: "Elevation Gain (ft)" },
            { key: "steps",             label: "Steps" },
            { key: "avgCadenceSpm",     label: "Avg Cadence (spm)" },
            { key: "workoutId",         label: "Workout ID" }
          ]}
        />

        <DataExplorer
          title="Bikes"
          endpoint="/api/bikes"
          FormComponent={BikeForm}
          FilterComponent={BikeFilterPanel}
          columns={[
            { key: "id",                label: "ID" },
            { key: "distanceMiles",     label: "Distance (mi)" },
            { key: "avgSpeedMph",       label: "Avg Speed (mph)" },
            { key: "maxSpeedMph",       label: "Max Speed (mph)" },
            { key: "elevationGainFeet", label: "Elevation Gain (ft)" },
            { key: "avgPedalRateRpm",   label: "Avg Pedal Rate (rpm)" },
            { key: "workoutId",         label: "Workout ID" }
          ]}
        />

        <DataExplorer
          title="Swims"
          endpoint="/api/swims"
          FormComponent={SwimForm}
          FilterComponent={SwimFilterPanel}
          columns={[
            { key: "id",                 label: "ID" },
            { key: "distanceYards",      label: "Distance (yd)" },
            { key: "avgPaceSecPer100Y",  label: "Avg Pace (per 100 yd)" },
            { key: "avgStrokeRateSpm",   label: "Avg Stroke Rate (spm)" },
            { key: "workoutId",          label: "Workout ID" }
          ]}
        />
      </main>
    </div>
  )
}
