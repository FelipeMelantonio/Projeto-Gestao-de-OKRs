'use client';

import { useRouter } from 'next/navigation';
import styles from '../page.module.css'; // ajusta se seu CSS estiver em outro lugar

export default function BotaoVoltar() {
  const router = useRouter();

  return (
    <button onClick={() => router.back()} className={styles.botao}>
      ← Voltar
    </button>
  );
}
