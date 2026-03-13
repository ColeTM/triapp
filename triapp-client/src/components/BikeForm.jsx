import React, { useState, useEffect } from 'react'

const BASE_URL = 'http://localhost:8080'

export default function BikeForm({ mode, initialData, onSuccess, onCancel }) {
  const isEdit = mode === 'edit'

  const empty = {
    workoutId:         '',
    distanceMiles:     '',
    avgSpeedMph:       '',
    maxSpeedMph:       '',
    elevationGainFeet: '',
    avgPedalRateRpm:   '',
  }

  const [form, setForm] = useState(empty)
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (isEdit && initialData) {
      setForm({
        workoutId:         initialData.workoutId         != null ? String(initialData.workoutId)         : '',
        distanceMiles:     initialData.distanceMiles     != null ? String(initialData.distanceMiles)     : '',
        avgSpeedMph:       initialData.avgSpeedMph       != null ? String(initialData.avgSpeedMph)       : '',
        maxSpeedMph:       initialData.maxSpeedMph       != null ? String(initialData.maxSpeedMph)       : '',
        elevationGainFeet: initialData.elevationGainFeet != null ? String(initialData.elevationGainFeet) : '',
        avgPedalRateRpm:   initialData.avgPedalRateRpm   != null ? String(initialData.avgPedalRateRpm)   : '',
      })
    }
  }, [isEdit, initialData])

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    setSubmitting(true)

    const payload = { workoutId: parseInt(form.workoutId, 10) }
    if (form.distanceMiles     !== '') payload.distanceMiles     = parseFloat(form.distanceMiles)
    if (form.avgSpeedMph       !== '') payload.avgSpeedMph       = parseFloat(form.avgSpeedMph)
    if (form.maxSpeedMph       !== '') payload.maxSpeedMph       = parseFloat(form.maxSpeedMph)
    if (form.elevationGainFeet !== '') payload.elevationGainFeet = parseInt(form.elevationGainFeet, 10)
    if (form.avgPedalRateRpm   !== '') payload.avgPedalRateRpm   = parseInt(form.avgPedalRateRpm, 10)

    const url    = isEdit ? `${BASE_URL}/api/bikes/${initialData.id}` : `${BASE_URL}/api/bikes`
    const method = isEdit ? 'PUT' : 'POST'

    try {
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      })
      if (!res.ok) {
        const txt = await res.text()
        throw new Error(`${res.status} ${res.statusText}: ${txt}`)
      }
      const saved = await res.json()
      onSuccess(saved)
    } catch (err) {
      setError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} style={styles.form}>
      {error && <div style={styles.errorBox}><strong>Error:</strong> {error}</div>}

      <Field label="Workout ID *" name="workoutId" value={form.workoutId} onChange={handleChange} type="number" min="1" required />

      <div style={styles.row}>
        <Field label="Distance (mi)"    name="distanceMiles" value={form.distanceMiles} onChange={handleChange} type="number" min="0" step="0.01" />
        <Field label="Avg Speed (mph)"  name="avgSpeedMph"   value={form.avgSpeedMph}   onChange={handleChange} type="number" min="0" step="0.1" />
        <Field label="Max Speed (mph)"  name="maxSpeedMph"   value={form.maxSpeedMph}   onChange={handleChange} type="number" min="0" step="0.1" />
      </div>

      <div style={styles.row}>
        <Field label="Elevation Gain (ft)"    name="elevationGainFeet" value={form.elevationGainFeet} onChange={handleChange} type="number" min="0" />
        <Field label="Avg Pedal Rate (rpm)"   name="avgPedalRateRpm"   value={form.avgPedalRateRpm}   onChange={handleChange} type="number" min="0" />
      </div>

      <div style={styles.actions}>
        <button type="button" style={styles.cancelBtn} onClick={onCancel} disabled={submitting}>Cancel</button>
        <button type="submit" style={styles.submitBtn} disabled={submitting}>
          {submitting ? 'Saving…' : isEdit ? 'Save Changes' : 'Create Bike'}
        </button>
      </div>
    </form>
  )
}

function Field({ label, name, type = 'text', value, onChange, required, min, step }) {
  return (
    <div style={styles.field}>
      <label style={styles.label}>{label}</label>
      <input style={styles.input} name={name} type={type} value={value} onChange={onChange}
             required={required} min={min} step={step} autoComplete="off" />
    </div>
  )
}

const styles = {
  form:      { display: 'flex', flexDirection: 'column', gap: '0.85rem' },
  row:       { display: 'flex', gap: '0.75rem' },
  field:     { display: 'flex', flexDirection: 'column', flex: 1, gap: '0.3rem' },
  label:     { fontSize: '0.82rem', fontWeight: 600, color: '#374151' },
  input:     { padding: '0.45rem 0.6rem', border: '1px solid #d1d5db', borderRadius: 6,
               fontSize: '0.92rem', width: '100%', boxSizing: 'border-box' },
  errorBox:  { background: '#fef2f2', border: '1px solid #fca5a5', borderRadius: 6,
               padding: '0.6rem 0.85rem', color: '#b91c1c', fontSize: '0.88rem' },
  actions:   { display: 'flex', justifyContent: 'flex-end', gap: '0.6rem', marginTop: '0.5rem' },
  cancelBtn: { padding: '0.45rem 1.1rem', border: '1px solid #d1d5db', borderRadius: 6,
               background: '#fff', cursor: 'pointer', fontSize: '0.9rem' },
  submitBtn: { padding: '0.45rem 1.25rem', border: 'none', borderRadius: 6,
               background: '#2563eb', color: '#fff', cursor: 'pointer', fontWeight: 600, fontSize: '0.9rem' },
}
