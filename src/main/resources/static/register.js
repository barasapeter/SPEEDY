document.addEventListener('DOMContentLoaded', () => {
    const registerButton = document.querySelector('button[type="button"]');

    registerButton.addEventListener('click', async () => {
        const name = document.getElementById('name').value.trim();
        const phone = document.getElementById('phone').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (!name || !phone || !email || !password || !confirmPassword) {
            Swal.fire({
                icon: 'warning',
                title: 'Oops!',
                text: 'Please fill out all fields.'
            });
            return;
        }

        if (password !== confirmPassword) {
            Swal.fire({
                icon: 'error',
                title: 'Password mismatch',
                text: 'Passwords do not match.'
            });
            return;
        }

        const result = await Swal.fire({
            title: 'Are you sure?',
            text: "Do you want to create an account?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Yes, create it!',
            cancelButtonText: 'Cancel'
        });

        if (!result.isConfirmed) return;

        Swal.fire({
            html: `                
                <div style="display: flex; flex-direction: column; align-items: center;">
                <img src="hold-on.gif" alt="Loading..." 
                    style="width: 80px; height: 80px;">
                <h1 style="margin: 15px;">Logging in</h1>
                <p>Verifying your sign-in details...</p>
                </div>
            `,
            showConfirmButton: false,
            showCancelButton: true,
            allowOutsideClick: false,
        });

        const payload = { name, phone, email, password };

        try {
            const response = await fetch('/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            const data = await response.json();

            Swal.close();

            if (response.ok) {
                Swal.fire({
                    icon: 'success',
                    title: data.title,
                    text: data.message || 'Account created successfully.',
                    allowOutsideClick: false
                }).then(() => {
                    history.back();
                });

            } else {
                Swal.fire({
                    icon: 'warning',
                    title: data.title,
                    text: data.message || 'Something went wrong.'
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
});
