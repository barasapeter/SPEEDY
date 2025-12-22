let sessionFormCache = {
    sessionUuid: crypto.randomUUID(),
    bikeCode: '',
    customerName: '',
    phone: '',
    registration: '',
    collateral: ''
}

document.getElementById('startSession').addEventListener('click', async () => {
    const dialogWidth = window.innerWidth < 480 ? '90%' : '32rem'

    const swalForm = await Swal.fire({
        title: 'Start New Session',
        width: dialogWidth,
        html: `
            <input id="swal-sessionuuid" type="hidden" value="${sessionFormCache.sessionUuid}">
            <div style="display:flex; flex-direction:column; gap:12px; width:100%;">
                <input id="swal-bikecode" class="swal2-input" placeholder="Bike Code" value="${sessionFormCache.bikeCode}" style="width:100%; margin:0;">
                <input id="swal-customer" class="swal2-input" placeholder="Customer Name" value="${sessionFormCache.customerName}" style="width:100%; margin:0;">
                <input id="swal-phone" class="swal2-input" placeholder="Phone Number" value="${sessionFormCache.phone}" style="width:100%; margin:0;">
                <input id="swal-reg" class="swal2-input" placeholder="Registration Number" value="${sessionFormCache.registration}" style="width:100%; margin:0;">
                <input id="swal-col" class="swal2-input" placeholder="Collateral" value="${sessionFormCache.collateral}" style="width:100%; margin:0;">
            </div>
        `,
        focusConfirm: false,
        showCancelButton: true,
        confirmButtonText: 'Start',
        cancelButtonText: 'Cancel',
        confirmButtonColor: '#22c55e',
        cancelButtonColor: '#ef4444',
        background: '#f8fafc',
        color: '#0f172a',
        backdrop: 'rgba(0,0,0,0.4)',
        preConfirm: () => {
            const uuid = document.getElementById('swal-sessionuuid').value
            const bikeCode = document.getElementById('swal-bikecode').value.trim()
            const customerName = document.getElementById('swal-customer').value.trim()
            const phone = document.getElementById('swal-phone').value.trim()
            const registration = document.getElementById('swal-reg').value.trim()
            const collateral = document.getElementById('swal-col').value.trim()

            sessionFormCache = { sessionUuid: uuid, bikeCode, customerName, phone, registration, collateral }

            if (!bikeCode || !customerName || !phone || !registration || !collateral) {
                Swal.showValidationMessage('All fields are required')
                return false
            }

            if (!/^\d{7,15}$/.test(phone)) {
                Swal.showValidationMessage('Phone number must be digits only, 7–15 characters')
                return false
            }

            return { sessionUuid: uuid, bikeCode, customerName, phone, registration, collateral }
        }
    })

    if (!swalForm.value) {
        sessionFormCache = {
            sessionUuid: sessionFormCache.sessionUuid,
            bikeCode: document.getElementById('swal-bikecode')?.value || sessionFormCache.bikeCode,
            customerName: document.getElementById('swal-customer')?.value || sessionFormCache.customerName,
            phone: document.getElementById('swal-phone')?.value || sessionFormCache.phone,
            registration: document.getElementById('swal-reg')?.value || sessionFormCache.registration,
            collateral: document.getElementById('swal-col')?.value || sessionFormCache.collateral
        }
        return
    }

    try {
        const response = await fetch('/session/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(swalForm.value)
        })

        const data = await response.json()

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Session Created',
                text: data.message || 'Session started.',
                confirmButtonColor: '#22c55e'
            }).then(() => {
                window.location.reload(true);
            })
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Failed',
                text: data.message || 'Unable to create session.',
                confirmButtonColor: '#ef4444'
            })
        }
    } catch {
        Swal.fire({
            icon: 'error',
            title: 'Network Error',
            text: 'Unable to reach the server.',
            confirmButtonColor: '#ef4444'
        })
    }
})

async function stopSession(button) {
    const sessionDiv = button.closest('.bike-item');
    const uuid = sessionDiv.getAttribute('data-uuid');
    const statusContainer = sessionDiv.querySelector('.session-status');
    const statusBadge = statusContainer.querySelector('.status-badge');
    const actionButtons = sessionDiv.querySelector('.action-buttons');

    Swal.fire({
        title: 'Stop Session?',
        text: "The timer will be paused and you can resume later",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#EF4444',
        cancelButtonColor: '#6B7280',
        confirmButtonText: 'Yes, Stop It',
        cancelButtonText: 'Cancel'
    }).then(async (result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: 'Stopping Session...',
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });

            try {
                const response = await fetch('/session/stop', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        sessionUuid: uuid
                    })
                });

                const data = await response.json();

                if (response.ok) {
                    statusBadge.className = 'status-badge bg-red-100 px-3 py-1 rounded-full';
                    statusBadge.innerHTML = `
                        <span class="flex items-center gap-1.5 text-xs font-semibold text-red-700">
                            <span class="w-2 h-2 bg-red-500 rounded-full"></span>
                            <span>STOPPED</span>
                        </span>
                    `;

                    actionButtons.innerHTML = `
                        <button onclick="resumeSession(this)" style="display: none;" 
                                class="w-full bg-green-500 hover:bg-green-600 text-white px-4 py-2.5 rounded-lg font-semibold shadow-md flex items-center justify-center gap-2">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.868v4.264a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"/>
                            </svg>
                            Resume
                        </button>
                        <button onclick="billSession('${uuid}')" 
                                class="w-full gradient-bg text-white px-4 py-2.5 rounded-lg font-semibold shadow-md flex items-center justify-center gap-2">
                            Bill Now
                        </button>
                        <button onclick="terminateSession(this)" style="display: none;" 
                                class="w-full bg-gray-200 hover:bg-gray-300 text-gray-700 px-4 py-2.5 rounded-lg font-medium shadow-md flex items-center justify-center gap-2">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                            </svg>
                            Delete
                        </button>
                    `;

                    Swal.fire({
                        title: 'Session Stopped',
                        text: data.message || 'You can now bill or resume this session',
                        icon: 'success',
                        timer: 2000,
                    });
                } else {
                    if (response.status === 401) {
                        Swal.fire({
                            title: 'Unauthorized',
                            text: data.message || 'You need to log in first.',
                            icon: 'error',
                            confirmButtonColor: '#667eea'
                        });
                    } else if (response.status === 404) {
                        Swal.fire({
                            title: 'Not Found',
                            text: data.message || 'Session not found.',
                            icon: 'error',
                            confirmButtonColor: '#667eea'
                        });
                    } else {
                        Swal.fire({
                            title: 'Error',
                            text: data.message || 'Failed to stop session.',
                            icon: 'error',
                            confirmButtonColor: '#667eea'
                        });
                    }
                }
            } catch (error) {
                console.error('Error stopping session:', error);
                Swal.fire({
                    title: 'Network Error',
                    text: 'Could not connect to the server. Please try again.',
                    icon: 'error',
                    confirmButtonColor: '#667eea'
                });
            }
        }
    });
}

function resumeSession(button) {
    const sessionDiv = button.closest('.bike-item');
    const statusContainer = sessionDiv.querySelector('.session-status');
    const statusBadge = statusContainer.querySelector('.status-badge');
    const actionButtons = sessionDiv.querySelector('.action-buttons');

    statusBadge.className = 'status-badge bg-green-100 px-3 py-1 rounded-full';
    statusBadge.innerHTML = `
        <span class="flex items-center gap-1.5 text-xs font-semibold text-green-700">
            <span class="w-2 h-2 bg-green-500 rounded-full"></span>
            <span>ONGOING</span>
        </span>
    `;

    actionButtons.innerHTML = `
        <button onclick="stopSession(this)" 
                class="stop-btn w-full bg-red-500 hover:bg-red-600 text-white px-4 py-2.5 rounded-lg font-semibold shadow-md flex items-center justify-center gap-2"
                title="Click to stop this session">
            <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 24 24">
                <rect x="6" y="6" width="12" height="12" rx="1"/>
            </svg>
            Stop Session
        </button>
    `;

    Swal.fire({
        title: 'Session Resumed',
        text: 'Timer is now running again',
        icon: 'success',
        timer: 2000,
    });
}

function billSession(uuid) {
    Swal.fire({
        title: 'Proceed to Billing?',
        text: "You'll be redirected to the billing page",
        icon: 'info',
        showCancelButton: true,
        confirmButtonColor: '#667eea',
        cancelButtonColor: '#6B7280',
        confirmButtonText: 'Yes, Bill Now',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            const billingUrl = `/billing?uuid=${encodeURIComponent(uuid)}`;
            window.location.href = billingUrl;
        }
    });
}

function terminateSession(button) {
    const sessionDiv = button.closest('.bike-item');

    Swal.fire({
        title: 'Delete Session?',
        text: "This action cannot be undone!",
        icon: 'error',
        showCancelButton: true,
        confirmButtonColor: '#EF4444',
        cancelButtonColor: '#6B7280',
        confirmButtonText: 'Yes, Delete It',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            sessionDiv.remove();
            Swal.fire({
                title: 'Deleted!',
                text: 'Session has been permanently removed',
                icon: 'success',
                timer: 2000,
            });
        }
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const emptyButton = document.getElementById('startSessionEmpty');
    if (emptyButton) {
        emptyButton.addEventListener('click', function() {
            document.getElementById('startSession')?.click();
        });
    }
});
