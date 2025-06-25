import React, { useEffect, useState } from 'react';

const BackendCheck: React.FC = () => {
  const [message, setMessage] = useState<string>('Cargando...');

  useEffect(() => {
    fetch('http://localhost:8080/') // URL  backend responde en endpoint
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error de conexión');
        }
        return response.text(); // Usa .json() si la respuesta es JSON
      })
      .then((data) => setMessage(data))
      .catch(() => setMessage(' No se pudo conectar al backend'));
  }, []);

  return (
    <div>
      <h2>🔌 Prueba de conexión con el backend</h2>
      <p>{message}</p>
    </div>
  );
};

export default BackendCheck;
