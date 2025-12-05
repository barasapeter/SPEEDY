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
