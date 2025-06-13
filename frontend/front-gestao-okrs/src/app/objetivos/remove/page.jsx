'use client';

import { useEffect, useState } from 'react';
import { useRouter, useParams } from 'next/navigation';
import styles from '../../page.module.css';
import BotaoVoltarObjetivos from '../../components/BotaoVoltarObjetivos';

export default function RemoverObjetivoPage() {
  const router = useRouter();
  const params = useParams();
  const id = params.id;

  const [objetivo, setObjetivo] = useState(null);

  useEffect(() => {
    async function fetchObjetivo() {
      const res = await fetch(`http://localhost:8080/api/objetivos/${id}`);
      const data = await res.json();
      setObjetivo(data);
    }
    fetchObjetivo();
  }, [id]);

  async function handleDelete() {
    const res = await fetch(`http://localhost:8080/api/objetivos/${id}`, {
      method: 'DELETE',
    });

    if (res.ok) {
      router.push('/objetivos');
    } else {
      alert('Erro ao remover o objetivo.');
    }
  }

  if (!objetivo) return <p>Carregando...</p>;

  return (
    <main className={styles.main}>
      <div className={styles.cardNovoObjetivo}>
        <h2>Remover Objetivo</h2>
        <p>Tem certeza que deseja remover o objetivo "{objetivo.titulo}"?</p>
        <button onClick={handleDelete} className={styles.botao}>Confirmar Remoção</button>
        <button onClick={() => router.push('/objetivos')}>Cancelar</button>
         <BotaoVoltarObjetivos />
      </div>
    </main>
  );
}
