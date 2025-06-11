import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { BillPayment } from './components/BillPayment';

function App() {
   return (
       <Router>
         <Routes>
           <Route path="/bill-payment" element={<BillPayment />} />
           {/* Agrega más rutas aquí si necesitas */}
         </Routes>
       </Router>
     );
}

export default App;
