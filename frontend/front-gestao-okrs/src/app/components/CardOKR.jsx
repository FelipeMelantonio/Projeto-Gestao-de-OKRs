import Link from "next/link";
import styles from "../page.module.css";

export default function CardOKR({ objetivo }) {
  return (
    <div className={`${styles.card} ${styles.card1}`}>
      <h3>{objetivo.titulo.toUpperCase()}</h3>
      <p>{objetivo.descricao}</p>
      <p><strong>Conclusão:</strong> {objetivo.porcentagemConclusao}%</p>
      <Link href={`/objetivos/${objetivo.id}`}>
        <button className={styles.botao}>Ver mais</button>
      </Link>
    </div>
  );
}
