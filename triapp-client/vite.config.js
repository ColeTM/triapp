// vite.config.js
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    // dev proxy to your Spring Boot backend
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
})