import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { BillPayment } from './components/BillPayment';
import BackendCheck from './components/BackendCheck'; //  importo el nuevo componente de prueba

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/bill-payment" element={<BillPayment />} />
        <Route path="/backend-check" element={<BackendCheck />} /> {/* ruta existente */}
      </Routes>
    </Router>
  );
}

export default App;