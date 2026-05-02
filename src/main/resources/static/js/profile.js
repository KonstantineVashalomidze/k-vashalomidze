async function loadProfile() {
    const res = await fetch('/api/v1/public/profile');
    const data = await res.json();

    const p = data.profile;

    document.getElementById('profile-header').innerHTML = `
    <img src="${'/uploads/' + p.imageFilePath}" alt="profile" />
    <h1>${p.name}</h1>
    <p>${p.profession}</p>
  `;

    document.getElementById('about').innerHTML = `<h2>About Me</h2><p>${p.aboutMe}</p>`;

    const edu = data.education.map(e => `
    <div class="card">
      <img src="/uploads/${e.logoFilePath}" alt="logo" onerror="this.style.display='none'" />
      <h3><a href="${e.instituteUrl}" target="_blank">${e.title}</a></h3>
      <p>${e.instituteName}</p>
      <p>${e.startDate} – ${e.endDate || 'Present'}</p>
      <p>${e.description}</p>
    </div>
  `).join('');
    document.getElementById('education').innerHTML = `<h2>Education</h2>${edu}`;

    const resumes = data.resumes.map(r => `
    <div><a href="/uploads/${r.filePath}" download>${r.id}</a></div>
  `).join('');
    document.getElementById('resumes').innerHTML = `<h2>Resumes</h2>${resumes}`;
    document.getElementById('charisma-count').textContent = data.profile.charismaCount;
}

document.getElementById('charisma-btn').addEventListener('click', async () => {
    const res = await fetch('/api/v1/public/profile/charisma', { method: 'POST' });
    const data = await res.json();
    document.getElementById('charisma-count').textContent = data.count;
});

document.getElementById('sub-btn').addEventListener('click', async () => {
    const email = document.getElementById('sub-email').value;
    const res = await fetch('/api/v1/public/profile/subscribe', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email })
    });
    document.getElementById('sub-msg').textContent = (res.status === 201) ? 'Successful '
        : 'Unsuccessful' + 'subscription';
});

loadProfile();
