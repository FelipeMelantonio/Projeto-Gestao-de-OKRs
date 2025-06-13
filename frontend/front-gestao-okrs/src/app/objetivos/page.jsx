/*
export default function Objetivos() {
  return <h1>Minha Página de Objetivos</h1>;
}
*/
/*
'use client';
import { useEffect, useState } from 'react';
//import { getObjetivos } from '@/services/api';
import Link from 'next/link';

export default function ObjetivosPage() {
  const [objetivos, setObjetivos] = useState([]);

  useEffect(() => {
    getObjetivos().then(setObjetivos);
  }, []);

  return (
    <div>
      <h1>Objetivos</h1>
      <ul>
        {objetivos.map((obj) => (
          <li key={obj.id}>
            <Link href={`/objetivos/${obj.id}`}>{obj.titulo}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
*/
'use client';
import BotaoVoltarObjetivos from '../components/BotaoVoltarObjetivos';
import { useEffect, useState } from 'react';
import { getObjetivos } from '../services/api';
import CardOKR from '../components/CardOKR';

export default function ObjetivosPage() {
  const [objetivos, setObjetivos] = useState([]);

  useEffect(() => {
    getObjetivos().then(setObjetivos);
  }, []);

  return (
    <div style={{ padding: '30px' }}>
      <h1 style={{ textAlign: 'center' }}>Objetivos</h1>
      <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center', gap: '20px' }}>
        {objetivos.map((obj) => (
          <CardOKR key={obj.id} objetivo={obj} /> 
        ))} 
      </div>
        <BotaoVoltarObjetivos />
    </div>
    
  );
}
