'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import styles from '../../page.module.css';
import BotaoVoltarObjetivos from '../../components/BotaoVoltarObjetivos';

export default function ListaObjetivosPage() {
  const [objetivos, setObjetivos] = useState([]);
  const router = useRouter();

  useEffect(() => {
    async function fetchObjetivos() {
      const res = await fetch('http://localhost:8080/api/objetivos');
      const data = await res.json();
      setObjetivos(data);
    }
    fetchObjetivos();
  }, []);

  async function removerObjetivo(id) {
    const confirmar = confirm('Tem certeza que deseja remover este objetivo?');
    if (!confirmar) return;

    try {
      const res = await fetch(`http://localhost:8080/api/objetivos/${id}`, {
        method: 'DELETE',
      });
      if (res.ok) {
        setObjetivos((prev) => prev.filter((obj) => obj.id !== id));
      } else {
        console.error('Erro ao remover o objetivo');
      }
    } catch (error) {
      console.error('Erro ao remover o objetivo:', error);
    }
  }

  return (
    <main className={styles.mainFiltro}>
      <div className={styles.containerFiltro}>
        <h2 className={styles.tituloFiltro}>📋 Lista de Objetivos</h2>

        {objetivos.map((obj) => (
          <div key={obj.id} className={styles.cardFiltro}>
            <h3 className={styles.cardTitulo}>{obj.titulo}</h3>
            <p>{obj.descricao}</p>

            <h4 className={styles.subtitulo}>✅ KR :</h4>
            {obj.resultadosChave?.map((kr, index) => (
              <div key={index} className={styles.krItem}>
                <p><strong>{kr.descricao}</strong> - Meta: {kr.meta}</p>
                <ul className={styles.listaIniciativas}>
                  {kr.iniciativas?.map((ini, i) => (
                    <li key={i}><strong>{ini.titulo}</strong>: {ini.descricao}</li>
                  ))}
                </ul>
              </div>
            ))}

            <div className={styles.botoesContainer1}>
              <button className={styles.botaoRemover} onClick={() => removerObjetivo(obj.id)}>
                🗑️ Deletar
              </button>
            </div>
          </div>
        ))}

        <BotaoVoltarObjetivos />
      </div>
    </main>
  );
}
