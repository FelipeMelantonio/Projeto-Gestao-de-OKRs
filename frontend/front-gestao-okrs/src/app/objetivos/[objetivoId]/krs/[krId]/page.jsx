import BotaoVoltar from "../../../../components/BotaoVoltar";


import { getKRById } from "../../../../services/api";
import Link from "next/link";
import styles from "../../../../page.module.css"; // corrigido aqui ✅

export default async function KRDetalhePage({ params }) {
  const { objetivoId, krId } = params;
  const kr = await getKRById(objetivoId, krId);

  return (
    <main className={styles.main}>
      <div className={styles.cardContainer}>
        <div className={styles.cardDetalheExpandido}>
          <h2>Resultado-Chave</h2>
          
          <p><strong>Descrição:</strong> {kr.descricao || "Não informado"}</p>
          <p><strong>Meta:</strong> {kr.meta ?? "Não definida"}</p>
          <p><strong>Progresso:</strong> {kr.porcentagemConclusao ?? 0}%</p>
          <div className={styles.progressBarContainer}>
            <div
                className={styles.progressBarFill}
                style={{ width: `${kr.porcentagemConclusao}%` }}
            ></div>
          </div>
          
          <h3 style={{ marginTop: "20px" }}>Iniciativas:</h3>
          {kr.iniciativas && kr.iniciativas.length > 0 ? (
            <ul style={{ listStyle: "none", padding: 0 }}>
              {kr.iniciativas.map((iniciativa) => (
                <li key={iniciativa.id} style={{ marginBottom: "10px" }}>
                  <Link
                    href={`/objetivos/${objetivoId}/krs/${krId}/iniciativas/${iniciativa.id}`}
                  >
                    <button className={styles.botao}>
                      {iniciativa.titulo || "Sem título"}
                    </button>
                    
                  </Link>
                </li>
              ))}
            </ul>
          ) : (
            <p style={{ marginTop: "10px" }}>Nenhuma iniciativa cadastrada.</p>
          )}
          <BotaoVoltar />  {/* ✅ Aqui fica o botão */}
        </div>
        
      </div>
    </main>
  );
}
