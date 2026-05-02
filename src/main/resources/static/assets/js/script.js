document.querySelector('form').addEventListener('submit', async function (e) {
    e.preventDefault();
    const email = this.querySelector('input[type="email"]').value;
    const btn = this.querySelector('button[type="submit"]');

    btn.disabled = true;
    btn.textContent = 'Subscribing...';

    try {
      const res = await fetch('/api/newsletter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email })
      });

      if (res.ok) {
        btn.textContent = 'Subscribed!';
        this.querySelector('input[type="email"]').value = '';
      } else {
        btn.textContent = 'Failed, try again';
        btn.disabled = false;
      }
    } catch {
      btn.textContent = 'Failed, try again';
      btn.disabled = false;
    }
  });