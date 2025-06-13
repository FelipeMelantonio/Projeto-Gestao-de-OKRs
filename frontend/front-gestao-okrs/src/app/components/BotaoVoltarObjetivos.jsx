//'use client';

//import { useRouter } from 'next/navigation';

//export default function BotaoVoltarObjetivos() {
//  const router = useRouter();

//  return (
//    <button
//      onClick={() => router.push('/objetivos')}
//      style={{
//        marginBottom: '20px',
//        marginTop: '10px',
//        padding: '10px 18px',
//        backgroundColor: '#555',
//        color: '#fff',
//        border: 'none',
//        borderRadius: '6px',
//        fontSize: '14px',
//        cursor: 'pointer',
//        transition: 'background 0.3s ease'
//      }}
//    >
//      ← Voltar para Objetivos
//    </button>
//  );
//}
'use client';

import { useRouter } from 'next/navigation';
import styles from '../page.module.css';

export default function BotaoVoltarObjetivos() {
  const router = useRouter();

  return (
    <button onClick={() => router.push('/')} className={`${styles.botao} ${styles.botaoVoltar}`}>
      ← Voltar para Página Principal
    </button>
  );
}
