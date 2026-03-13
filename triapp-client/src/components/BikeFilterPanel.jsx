import React, { useState } from 'react'

export default function BikeFilterPanel({ onApply, onClear }) {
  const empty = {
    id:                  '',
    workoutId:           '',
    minDistanceMiles:    '',
    maxDistanceMiles:    '',
    minAvgSpeedMph:      '',
    maxAvgSpeedMph:      '',
    minMaxSpeedMph:      '',
    maxMaxSpeedMph:      '',
    minElevationGainFeet:'',
    maxElevationGainFeet:'',
    minAvgPedalRateRpm:  '',
    maxAvgPedalRateRpm:  '',
  }

  const [filters, setFilters] = useState(empty)

  const handleChange = (e) => {
    const { name, value } = e.target
    setFilters(prev => ({ ...prev, [name]: value }))
  }

  const handleApply = () => {
    const params = new URLSearchParams()
    Object.entries(filters).forEach(([key, val]) => {
      if (val !== '') params.append(key, val)
    })
    onApply(params.toString())
  }

  const handleClear = () => {
    setFilters(empty)
    onClear()
  }

  return (
    <div style={styles.panel}>
      <div style={styles.row}>
        <Field label="ID"         name="id"        value={filters.id}        onChange={handleChange} type="number" />
        <Field label="Workout ID" name="workoutId" value={filters.workoutId} onChange={handleChange} type="number" />
      </div>

      <div style={styles.row}>
        <Field label="Distance min (mi)"   name="minDistanceMiles" value={filters.minDistanceMiles} onChange={handleChange} type="number" min="0" step="0.01" />
        <Field label="Distance max (mi)"   name="maxDistanceMiles" value={filters.maxDistanceMiles} onChange={handleChange} type="number" min="0" step="0.01" />
      </div>

      <div style={styles.row}>
        <Field label="Avg speed min (mph)" name="minAvgSpeedMph" value={filters.minAvgSpeedMph} onChange={handleChange} type="number" min="0" step="0.1" />
        <Field label="Avg speed max (mph)" name="maxAvgSpeedMph" value={filters.maxAvgSpeedMph} onChange={handleChange} type="number" min="0" step="0.1" />
        <Field label="Max speed min (mph)" name="minMaxSpeedMph" value={filters.minMaxSpeedMph} onChange={handleChange} type="number" min="0" step="0.1" />
        <Field label="Max speed max (mph)" name="maxMaxSpeedMph" value={filters.maxMaxSpeedMph} onChange={handleChange} type="number" min="0" step="0.1" />
      </div>

      <div style={styles.row}>
        <Field label="Elevation min (ft)"      name="minElevationGainFeet" value={filters.minElevationGainFeet} onChange={handleChange} type="number" min="0" />
        <Field label="Elevation max (ft)"      name="maxElevationGainFeet" value={filters.maxElevationGainFeet} onChange={handleChange} type="number" min="0" />
        <Field label="Avg pedal rate min (rpm)" name="minAvgPedalRateRpm" value={filters.minAvgPedalRateRpm}  onChange={handleChange} type="number" min="0" />
        <Field label="Avg pedal rate max (rpm)" name="maxAvgPedalRateRpm" value={filters.maxAvgPedalRateRpm}  onChange={handleChange} type="number" min="0" />
      </div>

      <div style={styles.actions}>
        <button style={styles.clearBtn} onClick={handleClear}>Clear</button>
        <button style={styles.applyBtn} onClick={handleApply}>Apply Filters</button>
      </div>
    </div>
  )
}

function Field({ label, name, type = 'text', value, onChange, min, step }) {
  return (
    <div style={styles.field}>
      <label style={styles.label}>{label}</label>
      <input style={styles.input} name={name} type={type} value={value} onChange={onChange}
             min={min} step={step} autoComplete="off" />
    </div>
  )
}

const styles = {
  panel:    { background: '#f8fafc', border: '1px solid #e2e8f0', borderRadius: 8,
              padding: '0.85rem 1rem', marginBottom: 10, display: 'flex',
              flexDirection: 'column', gap: '0.65rem' },
  row:      { display: 'flex', gap: '0.65rem', flexWrap: 'wrap' },
  field:    { display: 'flex', flexDirection: 'column', gap: '0.25rem', flex: '1 1 140px', minWidth: 120 },
  label:    { fontSize: '0.78rem', fontWeight: 600, color: '#475569' },
  input:    { padding: '0.38rem 0.55rem', border: '1px solid #cbd5e1', borderRadius: 5,
              fontSize: '0.88rem', background: '#fff', boxSizing: 'border-box', width: '100%' },
  actions:  { display: 'flex', gap: '0.5rem', justifyContent: 'flex-end', marginTop: '0.15rem' },
  clearBtn: { padding: '0.38rem 0.9rem', border: '1px solid #cbd5e1', borderRadius: 5,
              background: '#fff', cursor: 'pointer', fontSize: '0.88rem' },
  applyBtn: { padding: '0.38rem 1rem', border: 'none', borderRadius: 5, background: '#0f172a',
              color: '#fff', cursor: 'pointer', fontWeight: 600, fontSize: '0.88rem' },
}
