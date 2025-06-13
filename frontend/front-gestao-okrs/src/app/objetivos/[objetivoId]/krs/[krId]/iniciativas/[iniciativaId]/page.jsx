import BotaoVoltar from "../../../../../../components/BotaoVoltar";
import { getIniciativaById } from "../../../../../../services/api";
import styles from "../../../../../../page.module.css";

export default async function IniciativaDetalhePage({ params }) {
  const { objetivoId, krId, iniciativaId } = params;
  const iniciativa = await getIniciativaById(objetivoId, krId, iniciativaId);

  return (
    <main className={styles.main}>
      <div className={styles.cardContainer}>
        <div className={styles.cardDetalheExpandido}>
         
          <h2>Iniciativa</h2>
          <p><strong>Título:</strong> {iniciativa.titulo || "Sem título"}</p>
          <p><strong>Descrição:</strong> {iniciativa.descricao || "Sem descrição."}</p>
          <p><strong>Progresso:</strong> {iniciativa.porcentagemConclusao ?? 0}%</p>
          
          {/* ✅ Barra de progresso igual à dos KRs */}
          <div className={styles.progressBarContainer}>
            <div
              className={styles.progressBarFill}
              style={{ width: `${iniciativa.porcentagemConclusao ?? 0}%` }}
            ></div>
          </div>
          <BotaoVoltar />
        </div>
        
      </div>
    </main>
  );
}
