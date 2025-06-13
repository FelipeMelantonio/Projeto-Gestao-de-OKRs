
'use client';


import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import styles from '../../page.module.css';
import BotaoVoltarObjetivos from '../../components/BotaoVoltarObjetivos';


export default function EditarObjetivoPage() {
  const router = useRouter();


  const [objetivos, setObjetivos] = useState([]);
  const [objetivoSelecionadoId, setObjetivoSelecionadoId] = useState('');
  const [titulo, setTitulo] = useState('');
  const [descricao, setDescricao] = useState('');
  const [resultadosChave, setResultadosChave] = useState([]);


  // Buscar todos os objetivos
  useEffect(() => {
    async function fetchObjetivos() {
      const res = await fetch('http://localhost:8080/api/objetivos');
      const data = await res.json();
      setObjetivos(data);
    }
    fetchObjetivos();
  }, []);


  // Buscar dados do objetivo selecionado
  useEffect(() => {
    async function fetchObjetivo() {
      if (!objetivoSelecionadoId) return;


      const res = await fetch(`http://localhost:8080/api/objetivos/${objetivoSelecionadoId}`);
      const data = await res.json();
      setTitulo(data.titulo || '');
      setDescricao(data.descricao || '');
      setResultadosChave(data.resultadosChave || []);
    }
    fetchObjetivo();
  }, [objetivoSelecionadoId]);


  // Atualizar Objetivo
  const handleSubmitObjetivo = async (e) => {
    e.preventDefault();


    const atualizado = {
      id: parseInt(objetivoSelecionadoId),
      titulo,
      descricao,
    };


    const response = await fetch(`http://localhost:8080/api/objetivos/${objetivoSelecionadoId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(atualizado),
    });


    if (response.ok) {
      alert('Objetivo atualizado com sucesso!');
      router.push('/objetivos/get');
    } else {
      alert('Erro ao atualizar o objetivo');
    }
  };


  // Atualizar Resultado-Chave
  const handleUpdateResultadoChave = async (kr) => {
    const response = await fetch(`http://localhost:8080/api/resultados-chaves/${kr.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(kr),
    });


    if (response.ok) {
      alert('Resultado-chave atualizado!');
    } else {
      alert('Erro ao atualizar resultado-chave');
    }
  };


  // Atualizar Iniciativa
  const handleUpdateIniciativa = async (iniciativa) => {
    const response = await fetch(`http://localhost:8080/api/iniciativas/${iniciativa.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(iniciativa),
    });


    if (response.ok) {
      alert('Iniciativa atualizada!');
    } else {
      alert('Erro ao atualizar iniciativa');
    }
  };


  return (
    <main className={styles.mainPut}>
      <div className={styles.cardListaObjetivosPut}>
        <h2 className={styles.tituloPut}>✏️ Editar Objetivo</h2>


        <label className={styles.labelPut}>
          Selecione um objetivo:
          <select
            className={styles.inputPut}
            value={objetivoSelecionadoId}
            onChange={(e) => setObjetivoSelecionadoId(e.target.value)}
            required
          >
            <option value="">-- Escolha --</option>
            {objetivos.map((obj) => (
              <option key={obj.id} value={obj.id}>
                {obj.titulo} (ID: {obj.id})
              </option>
            ))}
          </select>
        </label>


        {objetivoSelecionadoId && (
          <form onSubmit={handleSubmitObjetivo} className={styles.formularioPut}>
            <label className={styles.labelPut}>
              Título:
              <input
                type="text"
                value={titulo}
                onChange={(e) => setTitulo(e.target.value)}
                className={styles.inputPut}
                required
              />
            </label>


            <label className={styles.labelPut}>
              Descrição:
              <textarea
                value={descricao}
                onChange={(e) => setDescricao(e.target.value)}
                className={styles.inputPut}
                required
              />
            </label>


            <button type="submit" className={styles.botaoEditarPut}>
              Salvar Alterações do Objetivo e Resultados-Chaves
            </button>
          </form>
        )}


        {resultadosChave.length > 0 && (
          <div className={styles.cardResultadoChave}>
            <h2 className={styles.subtituloPut}>✅ KR</h2>


            {resultadosChave.map((kr, index) => (
              <div key={index} className={styles.resultadoChavePut}>
                <h3>🗝️ KR : </h3>
                <label>
                  Descrição:
                  <input
                    type="text"
                    value={kr.descricao}
                    onChange={(e) => {
                      const nova = [...resultadosChave];
                      nova[index].descricao = e.target.value;
                      setResultadosChave(nova);
                    }}
                    className={styles.inputPut}
                  />
                </label>
                <label>
                  Meta:
                  <input
                    type="number"
                    value={kr.meta}
                    onChange={(e) => {
                      const nova = [...resultadosChave];
                      nova[index].meta = parseFloat(e.target.value);
                      setResultadosChave(nova);
                    }}
                    className={styles.inputPut}
                  />
                </label>
                


                {/* Iniciativas */}
                {kr.iniciativas && kr.iniciativas.length > 0 && (
                  <div className={styles.cardResultadoChave}>
                    <h2>🚀 Iniciativas:</h2>
                    {kr.iniciativas.map((ini, i) => (
                      <div key={i} className={styles.resultadoChavePut}>
                        
                        <label>
                          Título:
                          <input
                            type="text"
                            value={ini.titulo}
                            onChange={(e) => {
                              const nova = [...resultadosChave];
                              nova[index].iniciativas[i].titulo = e.target.value;
                              setResultadosChave(nova);
                            }}
                            className={styles.inputPut}
                          />
                        </label><br></br><br></br>
                        <label>
                          Descrição:
                          <textarea
                            value={ini.descricao}
                            onChange={(e) => {
                              const nova = [...resultadosChave];
                              nova[index].iniciativas[i].descricao = e.target.value;
                              setResultadosChave(nova);
                            }}
                            className={styles.inputPut}
                          />
                        </label><br></br><br></br>
                        <button
                          onClick={() => handleUpdateIniciativa(ini)}
                          className={styles.botaoEditarPut}
                        >
                          Salvar Iniciativa
                        </button>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            ))}
          </div>
        )}


        <BotaoVoltarObjetivos />
      </div>
    </main>
  );
}


