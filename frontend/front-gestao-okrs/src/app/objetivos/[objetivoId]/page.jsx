import BotaoVoltar from "../../components/BotaoVoltar";


import { getObjetivoById } from "../../services/api";

import Link from "next/link";
import styles from "../../page.module.css"; // aproveitando o CSS da home

export default async function ObjetivoDetalhePage({ params }) {
  const { objetivoId } = params;
  const objetivo = await getObjetivoById(objetivoId);

  return (
    <main className={styles.main}>
      <div className={styles.cardContainer}>
         <div className={styles.cardDetalheExpandido}>
        

          <h2>{objetivo.titulo.toUpperCase()}</h2>
          <p>{objetivo.descricao}</p>
          <p><strong>Progresso:</strong> {objetivo.porcentagemConclusao}%</p>
          <div className={styles.progressBarContainer}>
            <div
                className={styles.progressBarFill}
                style={{ width: `${objetivo.porcentagemConclusao}%` }}
            ></div>
        </div>

          <h3 style={{ marginTop: "20px" }}>Resultados-Chave (KRs):</h3>
          {objetivo.resultadosChave && objetivo.resultadosChave.length > 0 ? (
            <ul style={{ listStyle: "none", padding: 0 }}>
              {objetivo.resultadosChave.map((kr) => (
                <li key={kr.id} style={{ marginBottom: "10px" }}>
                  <Link href={`/objetivos/${objetivoId}/krs/${kr.id}`}>
                    <button className={styles.botao}>
                      KR: {kr.descricao ? kr.descricao.slice(0, 30) + "..." : "Sem descrição"}

                    </button>
                  </Link>
                </li>
              ))}
            </ul>
          ) : (
            <p style={{ marginTop: "10px" }}>Nenhum KR associado.</p>
          )}
          <BotaoVoltar />  {/* ✅ Aqui fica o botão */}
        </div>
      </div>
    </main>
  );
}
