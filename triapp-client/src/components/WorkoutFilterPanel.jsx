import React, { useState } from 'react'

/**
 * Props:
 *  - onApply: (queryString) => void
 *  - onClear: () => void
 */
export default function WorkoutFilterPanel({ onApply, onClear }) {
  const empty = {
    id:                '',
    userId:            '',
    workoutType:       '',
    dateFrom:          '',
    dateTo:            '',
    minMovingTimeSec:  '',
    maxMovingTimeSec:  '',
    minElapsedTimeSec: '',
    maxElapsedTimeSec: '',
    minAvgHeartRate:   '',
    maxAvgHeartRate:   '',
    minCalories:       '',
    maxCalories:       '',
  }

  const [filters, setFilters] = useState(empty)

  const handleChange = (e) => {
    const { name, value } = e.target
    setFilters(prev => ({ ...prev, [name]: value }))
  }

  const handleApply = () => {
    const params = new URLSearchParams()
    Object.entries(filters).forEach(([key, val]) => {
      if (val === '') return
      // date pickers give YYYY-MM-DD; backend accepts that format directly
      params.append(key, val)
    })
    onApply(params.toString())
  }

  const handleClear = () => {
    setFilters(empty)
    onClear()
  }

  return (
    <div style={styles.panel}>

      {/* Row 1: ID, User ID, Workout Type */}
      <div style={styles.row}>
        <Field label="ID"      name="id"     value={filters.id}     onChange={handleChange} type="number" />
        <Field label="User ID" name="userId" value={filters.userId} onChange={handleChange} type="number" />
        <div style={styles.field}>
          <label style={styles.label}>Workout Type</label>
          <select name="workoutType" value={filters.workoutType} onChange={handleChange} style={styles.input}>
            <option value="">Any</option>
            <option value="RUN">Run</option>
            <option value="BIKE">Bike</option>
            <option value="SWIM">Swim</option>
          </select>
        </div>
      </div>

      {/* Row 2: Date range */}
      <div style={styles.row}>
        <Field label="Date from" name="dateFrom" value={filters.dateFrom} onChange={handleChange} type="date" />
        <Field label="Date to"   name="dateTo"   value={filters.dateTo}   onChange={handleChange} type="date" />
      </div>

      {/* Row 3: Moving time range */}
      <div style={styles.row}>
        <Field label="Moving time min (sec)" name="minMovingTimeSec" value={filters.minMovingTimeSec} onChange={handleChange} type="number" min="0" />
        <Field label="Moving time max (sec)" name="maxMovingTimeSec" value={filters.maxMovingTimeSec} onChange={handleChange} type="number" min="0" />
        <Field label="Elapsed time min (sec)" name="minElapsedTimeSec" value={filters.minElapsedTimeSec} onChange={handleChange} type="number" min="0" />
        <Field label="Elapsed time max (sec)" name="maxElapsedTimeSec" value={filters.maxElapsedTimeSec} onChange={handleChange} type="number" min="0" />
      </div>

      {/* Row 4: Heart rate and calories ranges */}
      <div style={styles.row}>
        <Field label="Avg heart rate min" name="minAvgHeartRate" value={filters.minAvgHeartRate} onChange={handleChange} type="number" min="0" />
        <Field label="Avg heart rate max" name="maxAvgHeartRate" value={filters.maxAvgHeartRate} onChange={handleChange} type="number" min="0" />
        <Field label="Calories min"       name="minCalories"     value={filters.minCalories}     onChange={handleChange} type="number" min="0" />
        <Field label="Calories max"       name="maxCalories"     value={filters.maxCalories}     onChange={handleChange} type="number" min="0" />
      </div>

      <div style={styles.actions}>
        <button style={styles.clearBtn} onClick={handleClear}>Clear</button>
        <button style={styles.applyBtn} onClick={handleApply}>Apply Filters</button>
      </div>
    </div>
  )
}

function Field({ label, name, type = 'text', value, onChange, min }) {
  return (
    <div style={styles.field}>
      <label style={styles.label}>{label}</label>
      <input
        style={styles.input}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        min={min}
        autoComplete="off"
      />
    </div>
  )
}

const styles = {
  panel:     { background: '#f8fafc', border: '1px solid #e2e8f0', borderRadius: 8,
               padding: '0.85rem 1rem', marginBottom: 10, display: 'flex',
               flexDirection: 'column', gap: '0.65rem' },
  row:       { display: 'flex', gap: '0.65rem', flexWrap: 'wrap' },
  field:     { display: 'flex', flexDirection: 'column', gap: '0.25rem',
               flex: '1 1 140px', minWidth: 120 },
  label:     { fontSize: '0.78rem', fontWeight: 600, color: '#475569' },
  input:     { padding: '0.38rem 0.55rem', border: '1px solid #cbd5e1', borderRadius: 5,
               fontSize: '0.88rem', background: '#fff', boxSizing: 'border-box', width: '100%' },
  actions:   { display: 'flex', gap: '0.5rem', justifyContent: 'flex-end', marginTop: '0.15rem' },
  clearBtn:  { padding: '0.38rem 0.9rem', border: '1px solid #cbd5e1', borderRadius: 5,
               background: '#fff', cursor: 'pointer', fontSize: '0.88rem' },
  applyBtn:  { padding: '0.38rem 1rem', border: 'none', borderRadius: 5, background: '#0f172a',
               color: '#fff', cursor: 'pointer', fontWeight: 600, fontSize: '0.88rem' },
}
