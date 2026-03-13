import React, { useState, useEffect } from 'react'

const BASE_URL = 'http://localhost:8080'

export default function WorkoutForm({ mode, initialData, onSuccess, onCancel }) {
  const isEdit = mode === 'edit'

  const emptyBase = {
    userId:         '',
    workoutType:    'RUN',
    date:           '',
    movingTimeSec:  '',
    elapsedTimeSec: '',
    avgHeartRate:   '',
    calories:       '',
  }

  const emptyRun = {
    distanceMiles:     '',
    avgPaceSecPerMile: '',
    elevationGainFeet: '',
    steps:             '',
    avgCadenceSpm:     '',
  }

  const emptyBike = {
    distanceMiles:     '',
    avgSpeedMph:       '',
    maxSpeedMph:       '',
    elevationGainFeet: '',
    avgPedalRateRpm:   '',
  }

  const emptySwim = {
    distanceYards:     '',
    avgPaceSecPer100Y: '',
    avgStrokeRateSpm:  '',
  }

  const [form, setForm]     = useState(emptyBase)
  const [runForm, setRun]   = useState(emptyRun)
  const [bikeForm, setBike] = useState(emptyBike)
  const [swimForm, setSwim] = useState(emptySwim)
  const [submitting, setSubmitting] = useState(false)
  const [error, setError]   = useState(null)

  useEffect(() => {
    if (isEdit && initialData) {
      setForm({
        userId:         initialData.userId         != null ? String(initialData.userId)         : '',
        workoutType:    initialData.workoutType     ?? 'RUN',
        date:           toDateString(initialData.date),
        movingTimeSec:  initialData.movingTimeSec  != null ? String(initialData.movingTimeSec)  : '',
        elapsedTimeSec: initialData.elapsedTimeSec != null ? String(initialData.elapsedTimeSec) : '',
        avgHeartRate:   initialData.avgHeartRate   != null ? String(initialData.avgHeartRate)   : '',
        calories:       initialData.calories       != null ? String(initialData.calories)       : '',
      })
      // Subtype fields are not pre-filled on edit — the subtype record
      // is edited separately via its own table's Edit button
    }
  }, [isEdit, initialData])

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleRunChange  = (e) => { const { name, value } = e.target; setRun(prev  => ({ ...prev, [name]: value })) }
  const handleBikeChange = (e) => { const { name, value } = e.target; setBike(prev => ({ ...prev, [name]: value })) }
  const handleSwimChange = (e) => { const { name, value } = e.target; setSwim(prev => ({ ...prev, [name]: value })) }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    setSubmitting(true)

    const payload = {
      userId:      parseInt(form.userId, 10),
      workoutType: form.workoutType,
      date:        form.date ? `${form.date}T00:00:00` : undefined,
    }
    if (form.movingTimeSec  !== '') payload.movingTimeSec  = parseInt(form.movingTimeSec,  10)
    if (form.elapsedTimeSec !== '') payload.elapsedTimeSec = parseInt(form.elapsedTimeSec, 10)
    if (form.avgHeartRate   !== '') payload.avgHeartRate   = parseInt(form.avgHeartRate,   10)
    if (form.calories       !== '') payload.calories       = parseInt(form.calories,       10)

    // Attach subtype data only on create
    if (!isEdit) {
      if (form.workoutType === 'RUN') {
        const runData = {}
        if (runForm.distanceMiles     !== '') runData.distanceMiles     = parseFloat(runForm.distanceMiles)
        if (runForm.avgPaceSecPerMile !== '') runData.avgPaceSecPerMile = parseInt(runForm.avgPaceSecPerMile, 10)
        if (runForm.elevationGainFeet !== '') runData.elevationGainFeet = parseInt(runForm.elevationGainFeet, 10)
        if (runForm.steps             !== '') runData.steps             = parseInt(runForm.steps, 10)
        if (runForm.avgCadenceSpm     !== '') runData.avgCadenceSpm     = parseInt(runForm.avgCadenceSpm, 10)
        if (Object.keys(runData).length > 0) payload.runData = runData
      } else if (form.workoutType === 'BIKE') {
        const bikeData = {}
        if (bikeForm.distanceMiles     !== '') bikeData.distanceMiles     = parseFloat(bikeForm.distanceMiles)
        if (bikeForm.avgSpeedMph       !== '') bikeData.avgSpeedMph       = parseFloat(bikeForm.avgSpeedMph)
        if (bikeForm.maxSpeedMph       !== '') bikeData.maxSpeedMph       = parseFloat(bikeForm.maxSpeedMph)
        if (bikeForm.elevationGainFeet !== '') bikeData.elevationGainFeet = parseInt(bikeForm.elevationGainFeet, 10)
        if (bikeForm.avgPedalRateRpm   !== '') bikeData.avgPedalRateRpm   = parseInt(bikeForm.avgPedalRateRpm, 10)
        if (Object.keys(bikeData).length > 0) payload.bikeData = bikeData
      } else if (form.workoutType === 'SWIM') {
        const swimData = {}
        if (swimForm.distanceYards     !== '') swimData.distanceYards     = parseInt(swimForm.distanceYards, 10)
        if (swimForm.avgPaceSecPer100Y !== '') swimData.avgPaceSecPer100Y = parseInt(swimForm.avgPaceSecPer100Y, 10)
        if (swimForm.avgStrokeRateSpm  !== '') swimData.avgStrokeRateSpm  = parseInt(swimForm.avgStrokeRateSpm, 10)
        if (Object.keys(swimData).length > 0) payload.swimData = swimData
      }
    }

    const url    = isEdit ? `${BASE_URL}/api/workouts/${initialData.id}` : `${BASE_URL}/api/workouts`
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

      {/* Base workout fields */}
      <div style={styles.row}>
        <Field label="User ID *"     name="userId"      value={form.userId}      onChange={handleChange} type="number" min="1" required />
        <div style={styles.field}>
          <label style={styles.label}>Workout Type *</label>
          <select name="workoutType" value={form.workoutType} onChange={handleChange} style={styles.input} disabled={isEdit}>
            <option value="RUN">Run</option>
            <option value="BIKE">Bike</option>
            <option value="SWIM">Swim</option>
          </select>
        </div>
        <Field label="Date *" name="date" value={form.date} onChange={handleChange} type="date" required />
      </div>

      <div style={styles.row}>
        <Field label="Moving Time (sec)"  name="movingTimeSec"  value={form.movingTimeSec}  onChange={handleChange} type="number" min="0" />
        <Field label="Elapsed Time (sec)" name="elapsedTimeSec" value={form.elapsedTimeSec} onChange={handleChange} type="number" min="0" />
      </div>

      <div style={styles.row}>
        <Field label="Avg Heart Rate (bpm)" name="avgHeartRate" value={form.avgHeartRate} onChange={handleChange} type="number" min="0" />
        <Field label="Calories (kcal)"      name="calories"     value={form.calories}     onChange={handleChange} type="number" min="0" />
      </div>

      {/* Subtype fields — only shown on create */}
      {!isEdit && (
        <>
          <div style={styles.divider} />
          <div style={styles.subtypeLabel}>
            {form.workoutType === 'RUN'  && 'Run Details'}
            {form.workoutType === 'BIKE' && 'Bike Details'}
            {form.workoutType === 'SWIM' && 'Swim Details'}
            <span style={styles.subtypeHint}> (optional)</span>
          </div>

          {form.workoutType === 'RUN' && (
            <>
              <div style={styles.row}>
                <Field label="Distance (mi)"       name="distanceMiles"     value={runForm.distanceMiles}     onChange={handleRunChange} type="number" min="0" step="0.01" />
                <Field label="Avg Pace (sec/mile)" name="avgPaceSecPerMile" value={runForm.avgPaceSecPerMile} onChange={handleRunChange} type="number" min="0" />
              </div>
              <div style={styles.row}>
                <Field label="Elevation Gain (ft)" name="elevationGainFeet" value={runForm.elevationGainFeet} onChange={handleRunChange} type="number" min="0" />
                <Field label="Steps"               name="steps"             value={runForm.steps}             onChange={handleRunChange} type="number" min="0" />
                <Field label="Avg Cadence (spm)"   name="avgCadenceSpm"     value={runForm.avgCadenceSpm}     onChange={handleRunChange} type="number" min="0" />
              </div>
            </>
          )}

          {form.workoutType === 'BIKE' && (
            <>
              <div style={styles.row}>
                <Field label="Distance (mi)"    name="distanceMiles" value={bikeForm.distanceMiles} onChange={handleBikeChange} type="number" min="0" step="0.01" />
                <Field label="Avg Speed (mph)"  name="avgSpeedMph"   value={bikeForm.avgSpeedMph}   onChange={handleBikeChange} type="number" min="0" step="0.1" />
                <Field label="Max Speed (mph)"  name="maxSpeedMph"   value={bikeForm.maxSpeedMph}   onChange={handleBikeChange} type="number" min="0" step="0.1" />
              </div>
              <div style={styles.row}>
                <Field label="Elevation Gain (ft)"  name="elevationGainFeet" value={bikeForm.elevationGainFeet} onChange={handleBikeChange} type="number" min="0" />
                <Field label="Avg Pedal Rate (rpm)" name="avgPedalRateRpm"   value={bikeForm.avgPedalRateRpm}   onChange={handleBikeChange} type="number" min="0" />
              </div>
            </>
          )}

          {form.workoutType === 'SWIM' && (
            <div style={styles.row}>
              <Field label="Distance (yd)"         name="distanceYards"     value={swimForm.distanceYards}     onChange={handleSwimChange} type="number" min="0" />
              <Field label="Avg Pace (sec/100 yd)" name="avgPaceSecPer100Y" value={swimForm.avgPaceSecPer100Y} onChange={handleSwimChange} type="number" min="0" />
              <Field label="Avg Stroke Rate (spm)" name="avgStrokeRateSpm"  value={swimForm.avgStrokeRateSpm}  onChange={handleSwimChange} type="number" min="0" />
            </div>
          )}
        </>
      )}

      {isEdit && (
        <p style={{ fontSize: '0.82rem', color: '#6b7280', margin: '0.25rem 0 0' }}>
          To edit Run/Bike/Swim details, use the Edit button in the corresponding table below.
        </p>
      )}

      <div style={styles.actions}>
        <button type="button" style={styles.cancelBtn} onClick={onCancel} disabled={submitting}>Cancel</button>
        <button type="submit" style={styles.submitBtn} disabled={submitting}>
          {submitting ? 'Saving…' : isEdit ? 'Save Changes' : 'Create Workout'}
        </button>
      </div>
    </form>
  )
}

function toDateString(val) {
  if (!val) return ''
  if (Array.isArray(val)) {
    const [y, m, d] = val
    return `${y}-${String(m).padStart(2,'0')}-${String(d).padStart(2,'0')}`
  }
  return String(val).slice(0, 10)
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
  form:         { display: 'flex', flexDirection: 'column', gap: '0.85rem' },
  row:          { display: 'flex', gap: '0.75rem' },
  field:        { display: 'flex', flexDirection: 'column', flex: 1, gap: '0.3rem' },
  label:        { fontSize: '0.82rem', fontWeight: 600, color: '#374151' },
  input:        { padding: '0.45rem 0.6rem', border: '1px solid #d1d5db', borderRadius: 6,
                  fontSize: '0.92rem', width: '100%', boxSizing: 'border-box' },
  errorBox:     { background: '#fef2f2', border: '1px solid #fca5a5', borderRadius: 6,
                  padding: '0.6rem 0.85rem', color: '#b91c1c', fontSize: '0.88rem' },
  divider:      { borderTop: '1px solid #e5e7eb', margin: '0.25rem 0' },
  subtypeLabel: { fontSize: '0.88rem', fontWeight: 700, color: '#1e293b' },
  subtypeHint:  { fontWeight: 400, color: '#6b7280' },
  actions:      { display: 'flex', justifyContent: 'flex-end', gap: '0.6rem', marginTop: '0.5rem' },
  cancelBtn:    { padding: '0.45rem 1.1rem', border: '1px solid #d1d5db', borderRadius: 6,
                  background: '#fff', cursor: 'pointer', fontSize: '0.9rem' },
  submitBtn:    { padding: '0.45rem 1.25rem', border: 'none', borderRadius: 6,
                  background: '#2563eb', color: '#fff', cursor: 'pointer', fontWeight: 600, fontSize: '0.9rem' },
}
