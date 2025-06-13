const API_BASE = "http://localhost:8080/api";

// 🔹 Lista todos os objetivos
export async function getObjetivos() {
  const res = await fetch(`${API_BASE}/objetivos`);
  if (!res.ok) throw new Error("Erro ao buscar objetivos");
  return res.json();
}

// 🔹 Busca um objetivo específico por ID
export async function getObjetivoById(id: string) {
  const res = await fetch(`${API_BASE}/objetivos/${id}`);
  if (!res.ok) throw new Error("Erro ao buscar objetivo");
  return res.json();
}

// 🔹 Busca um KR (resultado-chave) por ID
export async function getKRById(objId: string, krId: string) {
  // A API não usa objId na URL, mas mantemos para manter o padrão da rota
  const res = await fetch(`${API_BASE}/resultados-chaves/${krId}`);
  if (!res.ok) throw new Error("Erro ao buscar KR");
  return res.json();
}

// 🔹 Busca uma iniciativa por ID
export async function getIniciativaById(objId: string, krId: string, inicId: string) {
  const res = await fetch(`${API_BASE}/iniciativas/${inicId}`);
  if (!res.ok) throw new Error("Erro ao buscar iniciativa");
  return res.json();
}
