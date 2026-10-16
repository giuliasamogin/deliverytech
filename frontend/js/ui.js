// Visual: fotos buscadas na internet por palavra-chave, com plano B (foto local, depois degradê com inicial).
const GRAD = ['#35103f,#5a2569', '#4a1c57,#7a3a6b', '#2b1a3a,#4d3a6e', '#3d1f35,#7b4a5a', '#3a2a1c,#8a6a45', '#24203a,#55507e'];
const KW = { italiana: 'pasta', brasileira: 'brazilian,food', japonesa: 'sushi', mexicana: 'tacos', arabe: 'falafel',
  massas: 'pasta', pizzas: 'pizza', pizza: 'pizza', lanches: 'burger', hamburguer: 'burger', sobremesas: 'dessert',
  bebidas: 'juice', saladas: 'salad', carnes: 'steak', sushi: 'sushi', doces: 'cake' };

// COLE AQUI os links das fotos que você escolher (clique com o botão direito na foto > Copiar endereço da imagem).
// Formato:  categoria em minúsculas e sem acento: 'https://link-da-foto.jpg',
const pex = (id, w = 900) => `https://images.pexels.com/photos/${id}/pexels-photo-${id}.jpeg?auto=compress&cs=tinysrgb&w=${w}`;
const FOTOS = {
  pizzas: pex(5908255), pizza: pex(5908255),
  italiana: pex(31142295), massas: pex(31142295),
  japonesa: pex(28992211), sushi: pex(28992211),
  lanches: pex(33213590), hamburguer: pex(33213590)
};

const slug = t => (t || '').normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase().trim();
const foto = (kw, lock, w, h) => `https://loremflickr.com/${w}/${h}/${kw}?lock=${lock}`;

// Se a foto da internet falhar, tenta a foto local de img/; se também falhar, some e fica o degradê.
function imgFb(el) {
  if (!el.dataset.t) { el.dataset.t = 1; el.src = el.dataset.local; } else el.remove();
}

function capa(categoria, id, peq, nome) {
  const g = GRAD[Math.abs(Number(id) || 0) % GRAD.length];
  const ini = (nome || categoria || '?').trim()[0].toUpperCase();
  const kw = KW[slug(categoria)] || 'food';
  return `<div class="capa ${peq ? 'peq' : ''}" style="background:linear-gradient(135deg,${g})">${ini}
    <img src="${FOTOS[slug(categoria)] || foto(kw, id || 1, peq ? 300 : 700, peq ? 300 : 420)}" data-local="img/${slug(categoria)}.jpg" alt="" loading="lazy" onerror="imgFb(this)"></div>`;
}