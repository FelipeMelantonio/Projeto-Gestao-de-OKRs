import Image from "next/image";
import styles from "./page.module.css";
import Link from "next/link";

// Função assíncrona no Server Component
async function getObjetivos() {
  // MUDAR AQUI PARA O LINK DA API REAL !!!!!!!
  const res = await fetch("http://localhost:8080/api/objetivos",{ // Exemplo de API pública: "http://localhost:8080/api/objetivos"
  });
  if (!res.ok) {
    throw new Error("Erro ao buscar dados da API");
  }
  return res.json();
}

export default async function Home() {
  const objetivos = await getObjetivos(); // Dados carregados via fetch

  return (
    <main className={styles.main}>
      <div className={styles.titulo}>
        <h1>BEM-VINDO AO MELHOR SISTEMA DE GESTÃO DE OKRs DO MUNDO</h1>
      </div>
      {/**/}
       <div className={styles.cardContainer}>
        {objetivos.map((obj, index) => (
          <div
            key={obj.id}
            className={`${styles.card} ${styles[`card${(index % 3) + 1}`]}`}
          >
            <h3>{obj.titulo.toUpperCase()}</h3>
            <p>{obj.descricao}</p>
            <Link href={`/objetivos/${obj.id}`}>
              <button className={styles.botao}>
                Ir para {obj.titulo.split(" ")[0]}
              </button>
            </Link>
          </div>
        ))}
      </div>
      {/*aqui esta a logica da criacao do objetivo*/}
      
      <div style={{ textAlign: 'center', marginBottom: '100px' }}>
        <Link href="/objetivos/novo"><br></br><br></br>
          <button className={styles.botao}>➕ Novo Objetivo</button>
        </Link>
      </div>

        <div style={{ textAlign: 'center', marginBottom: '100px' }}>
        <Link href="/objetivos/get">
          <button className={styles.botao}>➕ Filtrar Objetivo/Remover</button>
        </Link>
      </div>

        <div style={{ textAlign: 'center', marginBottom: '100px' }}>
        <Link href="/objetivos/put">
          <button className={styles.botao}>➕ Editar objetivos</button>
        </Link>
      </div>
      
      
    </main>
  );
}
