import React, { useEffect, useState } from 'react';
import './BillPayment.css'; // Importa estilos desde un archivo externo

export const BillPayment = () => {
  const [companyId, setCompanyId] = useState(1);
  const [referenceNumber, setReferenceNumber] = useState('');
  const [amount, setAmount] = useState<number | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const status = params.get("status");
    if (status === "success") {
      alert("✅ ¡Pago exitoso!");
    } else if (status === "failure") {
      alert("❌ El pago falló.");
    }
  }, []);

  const handleQuery = async () => {
    if (!referenceNumber.trim()) {
      alert("⚠️ Debes ingresar un número de referencia.");
      return;
    }

    setLoading(true);
    try {
      const res = await fetch('http://localhost:8080/api/bills/query', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ companyId, referenceNumber })
      });

      if (!res.ok) {
        throw new Error("Error al consultar el monto");
      }

      const data = await res.json();
      setAmount(data.amount);
    } catch (err) {
      console.error(err);
      alert("❌ No se pudo consultar el monto. Intenta más tarde.");
    } finally {
      setLoading(false);
    }
  };

  const handlePay = async () => {
    if (!referenceNumber.trim()) {
      alert("⚠️ Debes ingresar un número de referencia.");
      return;
    }

    setLoading(true);
    try {
      const res = await fetch('http://localhost:8080/api/bills/pay', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId: '00000000-0000-0000-0000-000000000001',
          companyId,
          referenceNumber
        }),
      });

      if (res.ok) {
        const paymentUrl = await res.text(); // Recibe la URL como texto plano
        window.location.href = paymentUrl + "&statusRedirect=true"; // Redirige a PSE
      } else {
        throw new Error("Error al iniciar el pago");
      }
    } catch (err) {
      console.error(err);
      alert("❌ No se pudo iniciar el pago. Intenta más tarde.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="payment-container">
      <h1 className="payment-title">Pasarela de Pagos</h1>

      <div className="payment-form">
        <label>Empresa de servicios:</label>
        <select value={companyId} onChange={e => setCompanyId(parseInt(e.target.value))}>
          <option value={1}>Luz</option>
          <option value={2}>Gas</option>
          <option value={3}>Agua</option>
          <option value={4}>Internet</option>
        </select>

        <label>Número de referencia:</label>
        <input
          type="text"
          placeholder="Ej. 123456789"
          value={referenceNumber}
          onChange={e => setReferenceNumber(e.target.value)}
        />

        <button onClick={handleQuery} disabled={loading}>
          {loading ? "Consultando..." : "Consultar Monto"}
        </button>

        {amount !== null && (
          <>
            <p>Monto a pagar: <strong>${amount.toFixed(2)}</strong></p>
            <button className="pay-button" onClick={handlePay} disabled={loading}>
              {loading ? "Procesando..." : "Pago PSE"}
            </button>
          </>
        )}
      </div>
    </div>
  );
};
