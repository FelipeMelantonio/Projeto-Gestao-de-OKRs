'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import BotaoVoltarObjetivos from '../../components/BotaoVoltarObjetivos';
import styles from '../../page.module.css';

export default function NovoObjetivoPage() {
  const router = useRouter();

  const [titulo, setTitulo] = useState('');
  const [descricao, setDescricao] = useState('');
  const [krDescricao, setKrDescricao] = useState('');
  const [krMeta, setKrMeta] = useState('');
  const [iniciativaTitulo, setIniciativaTitulo] = useState('');
  const [iniciativaDescricao, setIniciativaDescricao] = useState('');

  async function handleSubmit(e) {
    e.preventDefault();

    const novoObjetivo = {
      titulo,
      descricao,
      porcentagemConclusao: 0,
      resultadosChave: [
        {
          descricao: krDescricao,
          meta: parseFloat(krMeta),
          porcentagemConclusao: 0,
          iniciativas: [
            {
              titulo: iniciativaTitulo,
              descricao: iniciativaDescricao,
              porcentagemConclusao: 0,
            },
          ],
        },
      ],
    };

    try {
      const response = await fetch('http://localhost:8080/api/objetivos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novoObjetivo),
      });

      if (response.ok) {
        router.push('/objetivos');
      } else {
        alert('Erro ao salvar o objetivo.');
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
      alert('Erro ao conectar com o servidor.');
    }
  }

  return (
    <main className={styles.main}>
      <div className={styles.cardNovoObjetivo}>
        <form onSubmit={handleSubmit} className={styles.formulario}>
          <h2>Novo Objetivo</h2>

          <input
            type="text"
            className={styles.campoInput}
            placeholder="Título do Objetivo"
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
            required
          />
          <textarea
            className={styles.campoInput}
            placeholder="Descrição do Objetivo"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
            required
          />

          <h3>Resultado-Chave</h3>
          <input
            type="text"
            className={styles.campoInput}
            placeholder="Descrição do KR"
            value={krDescricao}
            onChange={(e) => setKrDescricao(e.target.value)}
            required
          />
          <input
            type="number"
            className={styles.campoInput}
            placeholder="Meta do KR"
            value={krMeta}
            onChange={(e) => setKrMeta(e.target.value)}
            required
          />

          <h3>Iniciativa</h3>
          <input
            type="text"
            className={styles.campoInput}
            placeholder="Título da Iniciativa"
            value={iniciativaTitulo}
            onChange={(e) => setIniciativaTitulo(e.target.value)}
            required
          />
          <textarea
            className={styles.campoInput}
            placeholder="Descrição da Iniciativa"
            value={iniciativaDescricao}
            onChange={(e) => setIniciativaDescricao(e.target.value)}
            required
          />

          <button type="submit" className={styles.botao}>
            Salvar Objetivo
          </button>
          <BotaoVoltarObjetivos />
        </form>
      </div>
    </main>
  );
}
