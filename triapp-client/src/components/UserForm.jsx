import React, { useState, useEffect } from 'react'

const BASE_URL = 'http://localhost:8080'

/**
 * Props:
 *  - mode: 'create' | 'edit'
 *  - initialData: User object (only used in edit mode; pre-populates fields)
 *  - onSuccess: (savedUser) => void  — called after a successful save
 *  - onCancel: () => void
 */
export default function UserForm({ mode, initialData, onSuccess, onCancel }) {
  const isEdit = mode === 'edit'

  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    heightInches: '',
    weightPounds: '',
  })

  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)

  // Pre-populate fields when editing
  useEffect(() => {
    if (isEdit && initialData) {
      setForm({
        firstName:    initialData.firstName    ?? '',
        lastName:     initialData.lastName     ?? '',
        email:        initialData.email        ?? '',
        password:     '',                             // never pre-fill password
        heightInches: initialData.heightInches != null ? String(initialData.heightInches) : '',
        weightPounds: initialData.weightPounds != null ? String(initialData.weightPounds) : '',
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

    // Build payload — omit empty optional fields, convert numerics
    const payload = {
      firstName: form.firstName,
      lastName:  form.lastName,
      email:     form.email,
    }
    if (form.password)     payload.password     = form.password
    if (form.heightInches) payload.heightInches = parseInt(form.heightInches, 10)
    if (form.weightPounds) payload.weightPounds = parseFloat(form.weightPounds)

    const url    = isEdit
      ? `${BASE_URL}/api/users/${initialData.id}`
      : `${BASE_URL}/api/users`
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

      {error && (
        <div style={styles.errorBox}>
          <strong>Error:</strong> {error}
        </div>
      )}

      <div style={styles.row}>
        <Field label="First Name *" name="firstName" value={form.firstName} onChange={handleChange} required />
        <Field label="Last Name *"  name="lastName"  value={form.lastName}  onChange={handleChange} required />
      </div>

      <Field label="Email *" name="email" type="email" value={form.email} onChange={handleChange} required />

      <Field
        label={isEdit ? 'Password (leave blank to keep unchanged)' : 'Password *'}
        name="password"
        type="password"
        value={form.password}
        onChange={handleChange}
        required={!isEdit}
      />

      <div style={styles.row}>
        <Field label="Height (inches)" name="heightInches" type="number" min="0" step="1"   value={form.heightInches} onChange={handleChange} />
        <Field label="Weight (lbs)"    name="weightPounds" type="number" min="0" step="0.1" value={form.weightPounds} onChange={handleChange} />
      </div>

      <div style={styles.actions}>
        <button type="button" style={styles.cancelBtn} onClick={onCancel} disabled={submitting}>
          Cancel
        </button>
        <button type="submit" style={styles.submitBtn} disabled={submitting}>
          {submitting ? 'Saving…' : isEdit ? 'Save Changes' : 'Create User'}
        </button>
      </div>
    </form>
  )
}

/* Small helper to keep field markup DRY */
function Field({ label, name, type = 'text', value, onChange, required, min, step }) {
  return (
    <div style={styles.field}>
      <label style={styles.label}>{label}</label>
      <input
        style={styles.input}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        required={required}
        min={min}
        step={step}
        autoComplete="off"
      />
    </div>
  )
}

const styles = {
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '0.85rem',
  },
  row: {
    display: 'flex',
    gap: '0.75rem',
  },
  field: {
    display: 'flex',
    flexDirection: 'column',
    flex: 1,
    gap: '0.3rem',
  },
  label: {
    fontSize: '0.82rem',
    fontWeight: 600,
    color: '#374151',
  },
  input: {
    padding: '0.45rem 0.6rem',
    border: '1px solid #d1d5db',
    borderRadius: 6,
    fontSize: '0.92rem',
    outline: 'none',
    width: '100%',
    boxSizing: 'border-box',
  },
  errorBox: {
    background: '#fef2f2',
    border: '1px solid #fca5a5',
    borderRadius: 6,
    padding: '0.6rem 0.85rem',
    color: '#b91c1c',
    fontSize: '0.88rem',
  },
  actions: {
    display: 'flex',
    justifyContent: 'flex-end',
    gap: '0.6rem',
    marginTop: '0.5rem',
  },
  cancelBtn: {
    padding: '0.45rem 1.1rem',
    border: '1px solid #d1d5db',
    borderRadius: 6,
    background: '#fff',
    cursor: 'pointer',
    fontSize: '0.9rem',
  },
  submitBtn: {
    padding: '0.45rem 1.25rem',
    border: 'none',
    borderRadius: 6,
    background: '#2563eb',
    color: '#fff',
    cursor: 'pointer',
    fontWeight: 600,
    fontSize: '0.9rem',
  },
}
