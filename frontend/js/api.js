// O mensageiro: único lugar que conhece o endereço do prédio e carrega o crachá (JWT).
const API = 'http://localhost:8080';
const $ = i => document.getElementById(i);
const tk = () => localStorage.getItem('token');
const brl = v => Number(v || 0).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
const ST = { 
  RECEBIDO: 'Recebido', 
  CONFIRMADO: 'Confirmado', 
  EM_PREPARO: 'Em preparo', 
  SAIU_PARA_ENTREGA: 'Saiu para entrega', 
  ENTREGUE: 'Entregue', 
  CANCELADO: 'Cancelado' 
};

function sess() {
  try { 
    return JSON.parse(decodeURIComponent(escape(atob(tk().split('.')[1].replace(/-/g, '+').replace(/_/g, '/'))))); 
  } catch (e) { 
    return null; 
  }
}

async function api(path, opt = {}) {
  const h = {};
  if (opt.body) h['Content-Type'] = 'application/json';
  if (tk()) h.Authorization = 'Bearer ' + tk();
  
  let r;
  try { 
    r = await fetch(API + path, { 
      method: opt.method || 'GET', 
      headers: h, 
      body: opt.body ? JSON.stringify(opt.body) : undefined 
    }); 
  } catch (e) { 
    throw new Error('Não consegui falar com o servidor. Ele está ligado em localhost:8080?'); 
  }
  
  if (r.status === 204) return null;
  
  let d = null;
  try { 
    d = await r.json(); 
  } catch (e) {}
  
  if (!r.ok) {
    if (r.status === 401 || r.status === 403) throw new Error('Sua sessão expirou ou esta ação exige login.');
    let t = (d && (d.message || d.error)) || 'Erro ' + r.status;
    const x = d && (d.errors || d.erros || d.details || d.fieldErrors);
    if (x) t += ' ' + JSON.stringify(x);
    throw new Error(t);
  }
  
  return d && d.success !== undefined && d.data !== undefined ? d.data : d;
}

function msg(el, t, ok) { 
  el.className = 'msg ' + (ok ? 'ok' : 'err'); 
  el.textContent = t; 
}

function sair() { 
  localStorage.clear(); 
  location = 'index.html'; 
}

function nav() {
  const s = sess();
  let h = '<a class="logo" href="index.html">Alameda</a><a href="index.html#restaurantes">Encontre restaurantes</a>';
  
  if (!s) {
    h += '<div class="dd"><a href="#" onclick="return menuEntrar()">Entrar</a><div id="dd" class="menu" hidden><a href="conta.html?tipo=cliente">Sou cliente</a><a href="conta.html?tipo=restaurante">Sou restaurante</a></div></div>';
  } else {
    h += s.role == 'CLIENTE' ? '<a href="pedidos.html">Meus pedidos</a>' : s.role == 'RESTAURANTE' ? '<a href="restaurante.html">Meu restaurante</a>' : '';
    h += '<span class="muted">' + (s.nome || s.sub) + '</span><a href="#" onclick="sair();return false">Sair</a>';
  }
  
  $('nav').innerHTML = h;
}

function menuEntrar() { 
  $('dd').hidden = !$('dd').hidden; 
  return false; 
}

document.addEventListener('click', e => { 
  const m = $('dd'); 
  if (m && !e.target.closest('.dd')) m.hidden = true; 
});

function guard(role) {
  const s = sess();
  if (!s || s.role != role) { 
    location = 'conta.html?tipo=' + (role == 'CLIENTE' ? 'cliente' : 'restaurante'); 
    return false; 
  }
  return true;
}

async function cid() {
  let c = localStorage.getItem('cid');
  if (!c) { 
    c = (await api('/clientes/por-email?email=' + encodeURIComponent(sess().sub))).id; 
    localStorage.setItem('cid', c); 
  }
  return c;
}