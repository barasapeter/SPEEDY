document.addEventListener('DOMContentLoaded', () => {
    const registerButton = document.querySelector('button[type="button"]');

    registerButton.addEventListener('click', async () => {
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;

        if (!email || !password) {
            Swal.fire({
                icon: 'warning',
                title: 'Oops!',
                text: 'Please enter username and password.'
            });
            return;
        }

        // Swal.fire({
        //     html: `                
        //         <div style="display: flex; flex-direction: column; align-items: center;">
        //         <img src="hold-on.gif" alt="Loading..." 
        //             style="width: 80px; height: 80px;">
        //         <h1 style="margin: 15px;">Authenticating</h1>
        //         <p>Hold on, we are verifying your sign-in credentials...</p>
        //         </div>
        //     `,
        //     showConfirmButton: false,
        //     showCancelButton: true,
        //     allowOutsideClick: false,
        // });

        registerButton.innerHTML = "Logging in...";

        const payload = { email, password };

        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            const data = await response.json();

            // Swal.close();
            registerButton.innerHTML = "Log In";

            if (response.ok) {
                window.location.href = "/dashboard";

            } else {
                Swal.fire({
                    icon: 'warning',
                    title: data.title,
                    text: data.message
                });
            }
        } catch (error) {
            Swal.close();
            Swal.fire({
                icon: 'error',
                title: 'System Error',
                text: 'Network error. Please try again.'
            });
        }
    });

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });
});
