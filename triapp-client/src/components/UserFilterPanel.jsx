import React, { useState } from 'react'

/**
 * Props:
 *  - onApply: (queryString) => void  — called with a built query string e.g. "firstName=John&minHeightInches=60"
 *  - onClear:  () => void            — called when the user resets all filters
 */
export default function UserFilterPanel({ onApply, onClear }) {
  const empty = {
    id:              '',
    firstName:       '',
    lastName:        '',
    email:           '',
    heightInches:    '',
    minHeightInches: '',
    maxHeightInches: '',
    weightPounds:    '',
    minWeightPounds: '',
    maxWeightPounds: '',
  }

  const [filters, setFilters] = useState(empty)

  const handleChange = (e) => {
    const { name, value } = e.target
    setFilters(prev => ({ ...prev, [name]: value }))
  }

  const handleApply = () => {
    const params = new URLSearchParams()

    // For exact height/weight, send as both min and max since the backend
    // only implements range filtering (not exact matching) for those fields.
    // If both exact and a range bound are filled, the range bound takes precedence.
    const resolved = { ...filters }
    if (resolved.heightInches !== '') {
      if (resolved.minHeightInches === '') resolved.minHeightInches = resolved.heightInches
      if (resolved.maxHeightInches === '') resolved.maxHeightInches = resolved.heightInches
    }
    if (resolved.weightPounds !== '') {
      if (resolved.minWeightPounds === '') resolved.minWeightPounds = resolved.weightPounds
      if (resolved.maxWeightPounds === '') resolved.maxWeightPounds = resolved.weightPounds
    }

    // Never send the exact fields themselves — backend ignores them
    const skip = new Set(['heightInches', 'weightPounds'])
    Object.entries(resolved).forEach(([key, val]) => {
      if (!skip.has(key) && val !== '') params.append(key, val)
    })

    onApply(params.toString())
  }

  const handleClear = () => {
    setFilters(empty)
    onClear()
  }

  return (
    <div style={styles.panel}>

      {/* Row 1: ID, First Name, Last Name, Email */}
      <div style={styles.row}>
        <Field label="ID"         name="id"        value={filters.id}        onChange={handleChange} type="number" />
        <Field label="First Name" name="firstName" value={filters.firstName} onChange={handleChange} />
        <Field label="Last Name"  name="lastName"  value={filters.lastName}  onChange={handleChange} />
        <Field label="Email"      name="email"     value={filters.email}     onChange={handleChange} type="email" />
      </div>

      {/* Row 2: Height exact / range */}
      <div style={styles.row}>
        <Field label="Height (in) — exact"  name="heightInches"    value={filters.heightInches}    onChange={handleChange} type="number" min="0" />
        <Field label="Height min (in)"      name="minHeightInches" value={filters.minHeightInches} onChange={handleChange} type="number" min="0" />
        <Field label="Height max (in)"      name="maxHeightInches" value={filters.maxHeightInches} onChange={handleChange} type="number" min="0" />
      </div>

      {/* Row 3: Weight exact / range */}
      <div style={styles.row}>
        <Field label="Weight (lb) — exact"  name="weightPounds"    value={filters.weightPounds}    onChange={handleChange} type="number" min="0" step="0.1" />
        <Field label="Weight min (lb)"      name="minWeightPounds" value={filters.minWeightPounds} onChange={handleChange} type="number" min="0" step="0.1" />
        <Field label="Weight max (lb)"      name="maxWeightPounds" value={filters.maxWeightPounds} onChange={handleChange} type="number" min="0" step="0.1" />
      </div>

      {/* Actions */}
      <div style={styles.actions}>
        <button style={styles.clearBtn}  onClick={handleClear}>Clear</button>
        <button style={styles.applyBtn}  onClick={handleApply}>Apply Filters</button>
      </div>
    </div>
  )
}

function Field({ label, name, value, onChange, type = 'text', min, step }) {
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
        step={step}
        autoComplete="off"
      />
    </div>
  )
}

const styles = {
  panel: {
    background: '#f8fafc',
    border: '1px solid #e2e8f0',
    borderRadius: 8,
    padding: '0.85rem 1rem',
    marginBottom: 10,
    display: 'flex',
    flexDirection: 'column',
    gap: '0.65rem',
  },
  row: {
    display: 'flex',
    gap: '0.65rem',
    flexWrap: 'wrap',
  },
  field: {
    display: 'flex',
    flexDirection: 'column',
    gap: '0.25rem',
    flex: '1 1 140px',
    minWidth: 120,
  },
  label: {
    fontSize: '0.78rem',
    fontWeight: 600,
    color: '#475569',
  },
  input: {
    padding: '0.38rem 0.55rem',
    border: '1px solid #cbd5e1',
    borderRadius: 5,
    fontSize: '0.88rem',
    background: '#fff',
    boxSizing: 'border-box',
    width: '100%',
  },
  actions: {
    display: 'flex',
    gap: '0.5rem',
    justifyContent: 'flex-end',
    marginTop: '0.15rem',
  },
  clearBtn: {
    padding: '0.38rem 0.9rem',
    border: '1px solid #cbd5e1',
    borderRadius: 5,
    background: '#fff',
    cursor: 'pointer',
    fontSize: '0.88rem',
  },
  applyBtn: {
    padding: '0.38rem 1rem',
    border: 'none',
    borderRadius: 5,
    background: '#0f172a',
    color: '#fff',
    cursor: 'pointer',
    fontWeight: 600,
    fontSize: '0.88rem',
  },
}
