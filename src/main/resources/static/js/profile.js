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
      <p>${new Date(e.startDate).toLocaleString(undefined, {
        year: 'numeric',
        month: 'short',
    })} – ${e.endDate ? new Date(e.endDate).toLocaleString(undefined, {
        year: 'numeric',
        month: 'short',
    }) : 'Present'}</p>
      <p>${e.description}</p>
    </div>
  `).join('');
    document.getElementById('education').innerHTML = `<h2>Education</h2>${edu}`;

    const resumes = data.resumes.map(r => `
    <div>${r.versionLabel} <a href="/uploads/${r.filePath}" download>${r.originalFilename}</a>  ${new Date(r.uploadedAt).toLocaleString()}</div>
  `).join('');
    document.getElementById('resumes').innerHTML = `<h2>Resumes</h2>${resumes}`;
    document.getElementById('charisma-count').textContent = data.profile.charismaCount;

    const c = data.contact;
    document.getElementById('contact').innerHTML = `
    <h2>Contact</h2>
    <div >
          <address>
        <ol>
          <li>
            Email:
            <a href="mailto:${c.email}">${c.email}</a>
          </li>
          <li>
            Phone:
            <a href="tel:${c.phone}">${c.phone}</a>
          </li>
          <li>
            GitHub:
            <a href="${c.github}" target="_blank">${c.github}</a>
          </li>
          <li>
            LinkedIn:
            <a href="${c.linkedin}" target="_blank">${c.linkedin}</a>
          </li>
          <li>
            Codeforces:
            <a href="${c.codeforces}" target="_blank">${c.codeforces}</a>
          </li>
          <li>
            LeetCode:
            <a href="${c.leetcode}" target="_blank">${c.leetcode}</a>
          </li>
          <li>
            HackerRank:
            <a href="${c.hackerrank}" target="_blank">${c.hackerrank}</a>
          </li>
          <li>
            Facebook:
            <a href="${c.facebook}" target="_blank">${c.facebook}</a>
          </li>
          <li>
            TypeRacer:
            <a href="${c.typeRacer}" target="_blank">${c.typeRacer}</a>
          </li>
        </ol>
      </address> 
    </div>
    `;


}

document.getElementById('charisma-btn').addEventListener('click', async () => {
    const res = await fetch('/api/v1/public/profile/charisma', { method: 'POST' });
    const data = await res.json();
    document.getElementById('charisma-count').textContent = data.count;
});

document.getElementById('sub-btn').addEventListener('click', async () => {
    const yes = confirm("Are you sure?")
    if (yes) {
        const email = document.getElementById('sub-email').value;
        const res = await fetch('/api/v1/public/profile/subscribe', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email })
        });
        if (res.status === 201) {
            alert("Successfully Subscribed!");
        } else {
            alert("Failed to Subscribe!");
        }
    }
});

loadProfile();
