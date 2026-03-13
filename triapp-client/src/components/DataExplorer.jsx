import React, { useState, useCallback } from 'react'
import DataTable from './DataTable'
import FormModal from './FormModal'

/**
 * Props:
 *  - title: string
 *  - endpoint: base endpoint (e.g. "/api/users")
 *  - columns: passed to DataTable
 *  - FormComponent: optional — insert/edit form; receives { mode, initialData, onSuccess, onCancel }
 *  - FilterComponent: optional — filter panel; receives { onApply(queryString), onClear() }
 */
export default function DataExplorer({ title, endpoint, columns, FormComponent, FilterComponent }) {
  const [id, setId] = useState('')
  const [fetchUrl, setFetchUrl] = useState(null)
  const [filterOpen, setFilterOpen] = useState(false)

  // Modal state
  const [modalOpen, setModalOpen]   = useState(false)
  const [modalMode, setModalMode]   = useState('create')  // 'create' | 'edit'
  const [editRow, setEditRow]       = useState(null)
  const [refreshKey, setRefreshKey] = useState(0)

  /* --- fetch controls --- */
  const handleFetchAll = () => {
    setFetchUrl(endpoint)
  }

  const handleFetchById = () => {
    if (!id) { alert('Please enter an id to fetch'); return }
    const u = endpoint.endsWith('/') ? `${endpoint}${id}` : `${endpoint}/${id}`
    setFetchUrl(u)
  }

  /* --- filter panel callbacks --- */
  const handleFilterApply = (queryString) => {
    const u = queryString ? `${endpoint}?${queryString}` : endpoint
    setFetchUrl(u)
    setFilterOpen(false)
  }

  const handleFilterClear = () => {
    setFetchUrl(endpoint)
  }

  /* --- modal controls --- */
  const openInsert = () => {
    setModalMode('create')
    setEditRow(null)
    setModalOpen(true)
  }

  const openEdit = useCallback((row) => {
    setModalMode('edit')
    setEditRow(row)
    setModalOpen(true)
  }, [])

  const closeModal = () => setModalOpen(false)

  const handleDelete = useCallback(async (row) => {
    if (!window.confirm(`Delete ${title.slice(0,-1)} #${row.id}? This cannot be undone.`)) return
    try {
      const url = `${endpoint.endsWith('/') ? endpoint : endpoint + '/'}${row.id}`
      const res = await fetch(url, { method: 'DELETE' })
      if (!res.ok) {
        const txt = await res.text()
        throw new Error(`${res.status} ${res.statusText}: ${txt}`)
      }
      setRefreshKey(k => k + 1)
      setFetchUrl(prev => prev ?? endpoint)
    } catch (err) {
      alert(`Delete failed: ${err.message}`)
    }
  }, [endpoint, title])

  const handleSuccess = () => {
    setModalOpen(false)
    setRefreshKey(k => k + 1)
    setFetchUrl(prev => prev ?? endpoint)
  }

  const activeFetchUrl = fetchUrl || endpoint

  return (
    <div style={{ marginBottom: '1.5rem' }}>

      {/* ---- controls row ---- */}
      <div style={{ display: 'flex', gap: 8, alignItems: 'center', marginBottom: 8, flexWrap: 'wrap' }}>
        <strong>{title}</strong>

        <button onClick={handleFetchAll}>Fetch All</button>

        <div style={{ display: 'flex', gap: 6, alignItems: 'center' }}>
          <input
            placeholder="id"
            value={id}
            onChange={e => setId(e.target.value)}
            style={{ width: 80 }}
          />
          <button onClick={handleFetchById}>Fetch By ID</button>
        </div>

        {/* Filter toggle — only shown when a FilterComponent is provided */}
        {FilterComponent && (
          <button
            onClick={() => setFilterOpen(o => !o)}
            style={{
              padding: '0.35rem 0.9rem',
              border: '1px solid #cbd5e1',
              borderRadius: 6,
              background: filterOpen ? '#0f172a' : '#fff',
              color:      filterOpen ? '#fff'    : '#0f172a',
              cursor: 'pointer',
              fontWeight: 600,
              fontSize: '0.88rem',
              transition: 'background 0.15s, color 0.15s',
            }}
          >
            {filterOpen ? '▲ Filters' : '▼ Filters'}
          </button>
        )}

        {/* Insert button */}
        {FormComponent && (
          <button
            onClick={openInsert}
            style={{
              marginLeft: 'auto',
              background: '#2563eb',
              color: '#fff',
              border: 'none',
              borderRadius: 6,
              padding: '0.35rem 0.9rem',
              cursor: 'pointer',
              fontWeight: 600,
            }}
          >
            + Insert
          </button>
        )}
      </div>

      {/* ---- collapsible filter panel ---- */}
      {FilterComponent && filterOpen && (
        <FilterComponent
          onApply={handleFilterApply}
          onClear={handleFilterClear}
        />
      )}

      <div style={{ marginBottom: 8 }}>
        <small>Current fetch URL: <code>{activeFetchUrl}</code></small>
      </div>

      <DataTable
        key={refreshKey}
        title={title}
        endpoint={endpoint}
        fetchUrl={activeFetchUrl}
        columns={columns}
        onEdit={FormComponent ? openEdit : undefined}
        onDelete={FormComponent ? handleDelete : undefined}
      />

      {/* ---- modal ---- */}
      {FormComponent && (
        <FormModal
          isOpen={modalOpen}
          onClose={closeModal}
          title={modalMode === 'create' ? `Insert ${title.slice(0,-1)}` : `Edit ${title.slice(0,-1)}`}
        >
          <FormComponent
            mode={modalMode}
            initialData={editRow}
            onSuccess={handleSuccess}
            onCancel={closeModal}
          />
        </FormModal>
      )}
    </div>
  )
}
