import React, { useState, useEffect } from 'react'

const BASE_URL = 'http://localhost:8080'

export default function SwimForm({ mode, initialData, onSuccess, onCancel }) {
  const isEdit = mode === 'edit'

  const empty = {
    workoutId:         '',
    distanceYards:     '',
    avgPaceSecPer100Y: '',
    avgStrokeRateSpm:  '',
  }

  const [form, setForm] = useState(empty)
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (isEdit && initialData) {
      setForm({
        workoutId:         initialData.workoutId         != null ? String(initialData.workoutId)         : '',
        distanceYards:     initialData.distanceYards     != null ? String(initialData.distanceYards)     : '',
        avgPaceSecPer100Y: initialData.avgPaceSecPer100Y != null ? String(initialData.avgPaceSecPer100Y) : '',
        avgStrokeRateSpm:  initialData.avgStrokeRateSpm  != null ? String(initialData.avgStrokeRateSpm)  : '',
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
    if (form.distanceYards     !== '') payload.distanceYards     = parseInt(form.distanceYards, 10)
    if (form.avgPaceSecPer100Y !== '') payload.avgPaceSecPer100Y = parseInt(form.avgPaceSecPer100Y, 10)
    if (form.avgStrokeRateSpm  !== '') payload.avgStrokeRateSpm  = parseInt(form.avgStrokeRateSpm, 10)

    const url    = isEdit ? `${BASE_URL}/api/swims/${initialData.id}` : `${BASE_URL}/api/swims`
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
        <Field label="Distance (yd)"            name="distanceYards"     value={form.distanceYards}     onChange={handleChange} type="number" min="0" />
        <Field label="Avg Pace (sec/100 yd)"    name="avgPaceSecPer100Y" value={form.avgPaceSecPer100Y} onChange={handleChange} type="number" min="0" />
        <Field label="Avg Stroke Rate (spm)"    name="avgStrokeRateSpm"  value={form.avgStrokeRateSpm}  onChange={handleChange} type="number" min="0" />
      </div>

      <div style={styles.actions}>
        <button type="button" style={styles.cancelBtn} onClick={onCancel} disabled={submitting}>Cancel</button>
        <button type="submit" style={styles.submitBtn} disabled={submitting}>
          {submitting ? 'Saving…' : isEdit ? 'Save Changes' : 'Create Swim'}
        </button>
      </div>
    </form>
  )
}

function Field({ label, name, type = 'text', value, onChange, required, min }) {
  return (
    <div style={styles.field}>
      <label style={styles.label}>{label}</label>
      <input style={styles.input} name={name} type={type} value={value} onChange={onChange}
             required={required} min={min} autoComplete="off" />
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
