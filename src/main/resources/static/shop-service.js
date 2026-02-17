const DIALOG_STYLES = `
  @import url('https://fonts.googleapis.com/css2?family=DM+Mono:wght@400;500&family=Syne:wght@600;700;800&family=DM+Sans:wght@400;500&display=swap');

  .sd-overlay {
    position: fixed; inset: 0; z-index: 9999;
    display: flex; align-items: center; justify-content: center;
    padding: 1rem;
    background: rgba(10,10,10,.55);
    backdrop-filter: blur(6px); -webkit-backdrop-filter: blur(6px);
    opacity: 0; transition: opacity 220ms ease;
  }
  .sd-overlay.sd-visible { opacity: 1; }

  .sd-panel {
    position: relative; width: 100%; max-width: 480px;
    background: #FAFAF8;
    border: 1px solid rgba(0,0,0,.08);
    border-radius: 4px;
    box-shadow:
      0 0 0 1px rgba(255,255,255,.6) inset,
      0 32px 80px -12px rgba(0,0,0,.28),
      0 8px 20px -6px rgba(0,0,0,.12);
    transform: translateY(18px) scale(.98);
    transition: transform 260ms cubic-bezier(.22,1,.36,1), opacity 220ms ease;
    opacity: 0; overflow: hidden;
    font-family: 'DM Sans', sans-serif;
  }
  .sd-overlay.sd-visible .sd-panel { transform: translateY(0) scale(1); opacity: 1; }

  .sd-stripe { height: 3px; background: linear-gradient(90deg,#E85D26 0%,#F0882A 55%,#F7C04A 100%); }

  .sd-header { padding: 28px 32px 0; display: flex; align-items: flex-start; justify-content: space-between; gap: 12px; }
  .sd-eyebrow { font-family:'DM Mono',monospace; font-size:10px; font-weight:500; letter-spacing:.14em; text-transform:uppercase; color:#E85D26; margin-bottom:4px; }
  .sd-title { font-family:'Syne',sans-serif; font-size:22px; font-weight:800; color:#111; line-height:1.15; letter-spacing:-.02em; }
  .sd-close { flex-shrink:0; width:32px; height:32px; border-radius:50%; border:1.5px solid #E0DED8; background:transparent; cursor:pointer; display:flex; align-items:center; justify-content:center; color:#888; transition:border-color 150ms,background 150ms,color 150ms; margin-top:2px; }
  .sd-close:hover { border-color:#111; background:#111; color:#fff; }
  .sd-close svg { width:14px; height:14px; }

  .sd-body { padding:28px 32px 24px; display:flex; flex-direction:column; gap:20px; }
  .sd-field { display:flex; flex-direction:column; gap:6px; }
  .sd-label { font-family:'DM Mono',monospace; font-size:10.5px; font-weight:500; letter-spacing:.1em; text-transform:uppercase; color:#666; }
  .sd-input-wrap { position:relative; display:flex; align-items:center; }
  .sd-input-icon { position:absolute; left:14px; color:#AAA; pointer-events:none; display:flex; align-items:center; transition:color 200ms; }
  .sd-input-icon svg { width:16px; height:16px; }
  .sd-input { width:100%; padding:12px 14px 12px 40px; font-family:'DM Sans',sans-serif; font-size:14.5px; color:#111; background:#fff; border:1.5px solid #E0DED8; border-radius:4px; outline:none; transition:border-color 180ms,box-shadow 180ms; box-sizing:border-box; }
  .sd-input::placeholder { color:#BBB; }
  .sd-input:focus { border-color:#E85D26; box-shadow:0 0 0 3px rgba(232,93,38,.12); }
  .sd-input-wrap:focus-within .sd-input-icon { color:#E85D26; }

  .sd-divider { height:1px; background:linear-gradient(90deg,#E8E6E0,transparent); margin:0 32px; }

  .sd-notice { margin:16px 32px 0; padding:12px 16px; border-radius:4px; background:#F0F4FF; border-left:3px solid #3B5BDB; display:flex; gap:10px; align-items:flex-start; }
  .sd-notice-icon { color:#3B5BDB; flex-shrink:0; margin-top:1px; }
  .sd-notice-icon svg { width:14px; height:14px; }
  .sd-notice-text { font-size:12.5px; color:#3B5BDB; line-height:1.5; }

  .sd-error { display:none; align-items:center; gap:6px; font-family:'DM Mono',monospace; font-size:11px; color:#C92A2A; letter-spacing:.04em; padding:12px 32px 0; }
  .sd-error.sd-shown { display:flex; }
  .sd-error svg { width:13px; height:13px; flex-shrink:0; }

  .sd-footer { padding:20px 32px 28px; display:flex; align-items:center; justify-content:flex-end; gap:10px; }

  .sd-btn { display:inline-flex; align-items:center; gap:7px; padding:10px 20px; border-radius:4px; font-family:'DM Sans',sans-serif; font-size:13.5px; font-weight:500; cursor:pointer; transition:all 160ms ease; border:none; outline:none; white-space:nowrap; }
  .sd-btn svg { width:14px; height:14px; }
  .sd-btn-cancel { background:transparent; color:#666; border:1.5px solid #E0DED8; }
  .sd-btn-cancel:hover { border-color:#999; color:#333; background:#F4F2EC; }
  .sd-btn-confirm { background:#111; color:#fff; border:1.5px solid #111; position:relative; overflow:hidden; }
  .sd-btn-confirm::before { content:''; position:absolute; inset:0; background:linear-gradient(135deg,#E85D26,#F0882A); opacity:0; transition:opacity 200ms; }
  .sd-btn-confirm:hover::before { opacity:1; }
  .sd-btn-confirm span, .sd-btn-confirm svg { position:relative; z-index:1; }
  .sd-btn-confirm:active { transform:scale(.97); }

  .sd-status { padding:40px 32px; text-align:center; display:flex; flex-direction:column; align-items:center; gap:12px; }
  .sd-status-icon { width:52px; height:52px; border-radius:50%; display:flex; align-items:center; justify-content:center; margin-bottom:4px; }
  .sd-status-icon.saving { border:2.5px solid #E0DED8; border-top-color:#E85D26; animation:sd-spin 700ms linear infinite; }
  .sd-status-icon.success { background:#ECFDF5; }
  .sd-status-icon.error   { background:#FFF1F0; }
  .sd-status-icon svg { width:24px; height:24px; }
  .sd-status-title { font-family:'Syne',sans-serif; font-size:19px; font-weight:700; color:#111; letter-spacing:-.02em; }
  .sd-status-msg { font-size:13.5px; color:#666; max-width:280px; line-height:1.55; }
  .sd-status-footer { padding:0 32px 28px; display:flex; justify-content:center; gap:10px; }

  @keyframes sd-spin { to { transform:rotate(360deg); } }

  @media (max-width:520px) {
    .sd-header { padding:22px 22px 0; }
    .sd-body   { padding:22px 22px 20px; }
    .sd-divider { margin:0 22px; }
    .sd-notice  { margin:16px 22px 0; }
    .sd-error   { padding:12px 22px 0; }
    .sd-footer  { padding:16px 22px 22px; flex-direction:column-reverse; }
    .sd-btn     { width:100%; justify-content:center; }
    .sd-status  { padding:32px 22px; }
    .sd-status-footer { padding:0 22px 22px; }
    .sd-title   { font-size:19px; }
  }
`;

const SVG = {
  store: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"/><polyline stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" points="9 22 9 12 15 12 15 22"/></svg>`,
  pin:   `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7z"/><circle cx="12" cy="9" r="2.5" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8"/></svg>`,
  x:     `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 6L6 18M6 6l12 12"/></svg>`,
  check: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2.2" d="M5 13l4 4L19 7"/></svg>`,
  info:  `<svg fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/></svg>`,
  alert: `<svg fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/></svg>`,
  ok:    `<svg fill="none" stroke="#10B981" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2.2" d="M5 13l4 4L19 7"/></svg>`,
  err:   `<svg fill="none" stroke="#EF4444" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 6L6 18M6 6l12 12"/></svg>`,
};

function ensureStyles() {
  if (document.getElementById('sd-styles')) return;
  const el = document.createElement('style');
  el.id = 'sd-styles';
  el.textContent = DIALOG_STYLES;
  document.head.appendChild(el);
}

function mountOverlay(html) {
  const overlay = document.createElement('div');
  overlay.className = 'sd-overlay';
  overlay.innerHTML = html;
  document.body.appendChild(overlay);
  document.body.style.overflow = 'hidden';
  requestAnimationFrame(() => requestAnimationFrame(() => overlay.classList.add('sd-visible')));
  return overlay;
}

function dismissOverlay(overlay, cb) {
  overlay.classList.remove('sd-visible');
  overlay.addEventListener('transitionend', () => {
    overlay.remove();
    document.body.style.overflow = '';
    cb && cb();
  }, { once: true });
}

function replaceContent(overlay, html) {
  overlay.querySelector('.sd-panel').innerHTML = `<div class="sd-stripe"></div>${html}`;
}

function escAttr(s) { return String(s).replace(/"/g, '&quot;'); }
function escHTML(s) {
  return String(s)
    .replace(/&/g, '&amp;').replace(/</g, '&lt;')
    .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function savingHTML() {
  return `
    <div class="sd-status">
      <div class="sd-status-icon saving"></div>
      <div class="sd-status-title">Saving…</div>
      <div class="sd-status-msg">Updating your shop details, hang tight.</div>
    </div>`;
}

function successHTML(msg = 'Your shop details have been updated.') {
  return `
    <div class="sd-status">
      <div class="sd-status-icon success">${SVG.ok}</div>
      <div class="sd-status-title">All done!</div>
      <div class="sd-status-msg">${escHTML(msg)}</div>
    </div>
    <div class="sd-status-footer">
      <button class="sd-btn sd-btn-confirm" id="sd-done">${SVG.check}<span>Got it</span></button>
    </div>`;
}

function errorHTML(msg = 'Something went wrong.') {
  return `
    <div class="sd-status">
      <div class="sd-status-icon error">${SVG.err}</div>
      <div class="sd-status-title">Couldn't save</div>
      <div class="sd-status-msg">${escHTML(msg)}</div>
    </div>
    <div class="sd-status-footer">
      <button class="sd-btn sd-btn-cancel" id="sd-dismiss">Dismiss</button>
      <button class="sd-btn sd-btn-confirm" id="sd-retry">Try Again</button>
    </div>`;
}

function openShopDialog(name = '', location = '') {
  ensureStyles();

  const overlay = mountOverlay(`
    <div class="sd-panel" role="dialog" aria-modal="true" aria-labelledby="sd-title">
      <div class="sd-stripe"></div>
      <div class="sd-header">
        <div>
          <div class="sd-eyebrow">Shop Setup</div>
          <div class="sd-title" id="sd-title">Update Details</div>
        </div>
        <button class="sd-close" id="sd-close" aria-label="Close">${SVG.x}</button>
      </div>
      <div class="sd-body">
        <div class="sd-field">
          <label class="sd-label" for="sd-name">Shop Name</label>
          <div class="sd-input-wrap">
            <span class="sd-input-icon">${SVG.store}</span>
            <input id="sd-name" class="sd-input" type="text"
              value="${escAttr(name)}" placeholder="e.g., Peter's Bike Shop" autocomplete="organization"/>
          </div>
        </div>
        <div class="sd-field">
          <label class="sd-label" for="sd-location">Location</label>
          <div class="sd-input-wrap">
            <span class="sd-input-icon">${SVG.pin}</span>
            <input id="sd-location" class="sd-input" type="text"
              value="${escAttr(location)}" placeholder="e.g., Egerton Main Gate" autocomplete="street-address"/>
          </div>
        </div>
      </div>
      <div class="sd-error" id="sd-error" role="alert">
        ${SVG.alert}<span id="sd-error-text">Please fill in both fields.</span>
      </div>
      <div class="sd-divider"></div>
      <div class="sd-notice">
        <span class="sd-notice-icon">${SVG.info}</span>
        <span class="sd-notice-text">These details appear on your dashboard and business records.</span>
      </div>
      <div class="sd-footer">
        <button class="sd-btn sd-btn-cancel" id="sd-cancel">Cancel</button>
        <button class="sd-btn sd-btn-confirm" id="sd-confirm">${SVG.check}<span>Save Details</span></button>
      </div>
    </div>
  `);

  const close    = () => dismissOverlay(overlay);
  const nameIn   = overlay.querySelector('#sd-name');
  const locIn    = overlay.querySelector('#sd-location');
  const errEl    = overlay.querySelector('#sd-error');
  const errTxt   = overlay.querySelector('#sd-error-text');

  overlay.querySelector('#sd-close').addEventListener('click', close);
  overlay.querySelector('#sd-cancel').addEventListener('click', close);
  overlay.addEventListener('click', e => { if (e.target === overlay) close(); });
  [nameIn, locIn].forEach(el => el.addEventListener('input', () => errEl.classList.remove('sd-shown')));

  overlay.querySelector('#sd-confirm').addEventListener('click', async () => {
    const shopName = nameIn.value.trim();
    const shopLoc  = locIn.value.trim();

    if (!shopName || !shopLoc) {
      errTxt.textContent = !shopName && !shopLoc
        ? 'Both fields are required.'
        : !shopName ? 'Shop name is required.' : 'Location is required.';
      errEl.classList.add('sd-shown');
      (!shopName ? nameIn : locIn).focus();
      return;
    }

    replaceContent(overlay, savingHTML());

    try {
      const response = await fetch('/shop/update', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: shopName, location: shopLoc }),
      });
      const data = await response.json();
      if (!response.ok) throw new Error(data.message || 'Failed to save.');

      replaceContent(overlay, successHTML(data.message));
      overlay.querySelector('#sd-done')?.addEventListener('click', () =>
        dismissOverlay(overlay, () => window.location.reload())
      );
    } catch (err) {
      replaceContent(overlay, errorHTML(err.message));
      overlay.querySelector('#sd-retry')?.addEventListener('click', () =>
        dismissOverlay(overlay, () => openShopDialog(shopName, shopLoc))
      );
      overlay.querySelector('#sd-dismiss')?.addEventListener('click', () =>
        dismissOverlay(overlay)
      );
    }
  });

  setTimeout(() => nameIn.focus(), 280);
}

document.addEventListener('DOMContentLoaded', () => {
  const btn = document.getElementById('getStarted');
  if (!btn) return;
  btn.addEventListener('click', function () {
    openShopDialog(
      this.dataset.shopName     || this.getAttribute('data-shop-name')     || '',
      this.dataset.shopLocation || this.getAttribute('data-shop-location') || ''
    );
  });
});