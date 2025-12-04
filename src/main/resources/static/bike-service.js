// Generate a random 6-digit bike code
function generateBikeCode() {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    let code = '';
    for (let i = 0; i < 6; i++) {
        code += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return code;
}

document.getElementById("manageBikes").addEventListener("click", function () {
    const bikeCode = generateBikeCode();
    
    Swal.fire({
        title: '<div class="flex items-center justify-center gap-2 sm:gap-3"><svg class="w-6 h-6 sm:w-8 sm:h-8 text-orange-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path></svg><span class="text-lg sm:text-xl">Register New Bike</span></div>',
        html: `
            <div class="space-y-4 sm:space-y-5 text-left mt-4 sm:mt-6">
                <!-- Bike Code Field (Disabled) -->
                <div class="relative">
                    <label for="bikeCodeInput" class="block text-xs sm:text-sm font-semibold text-gray-700 mb-2">
                        Bike Code
                    </label>
                    <div class="relative">
                        <div class="absolute inset-y-0 left-0 pl-3 sm:pl-4 flex items-center pointer-events-none">
                            <svg class="w-4 h-4 sm:w-5 sm:h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 20l4-16m2 16l4-16M6 9h14M4 15h14"></path>
                            </svg>
                        </div>
                        <input 
                            id="bikeCodeInput" 
                            type="text" 
                            value="${bikeCode}"
                            disabled
                            class="w-full pl-10 sm:pl-12 pr-3 sm:pr-4 py-2.5 sm:py-3 text-sm sm:text-base text-gray-700 font-mono font-bold tracking-wider bg-gray-100 border-2 border-gray-300 rounded-lg sm:rounded-xl cursor-not-allowed" 
                        />
                    </div>
                    <p class="mt-2 text-xs text-gray-500">Auto-generated unique identifier for this bike</p>
                </div>

                <!-- Rate Per Minute Input -->
                <div class="relative">
                    <label for="rpmInput" class="block text-xs sm:text-sm font-semibold text-gray-700 mb-2">
                        Rate Per Minute (KSH)
                    </label>
                    <div class="relative">
                        <div class="absolute inset-y-0 left-0 pl-3 sm:pl-4 flex items-center pointer-events-none">
                            <svg class="w-4 h-4 sm:w-5 sm:h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                            </svg>
                        </div>
                        <input 
                            id="rpmInput" 
                            type="number" 
                            min="0"
                            step="0.01"
                            class="w-full pl-10 sm:pl-12 pr-3 sm:pr-4 py-2.5 sm:py-3 text-sm sm:text-base text-gray-900 border-2 border-gray-200 rounded-lg sm:rounded-xl focus:outline-none focus:border-orange-500 focus:ring-2 sm:focus:ring-4 focus:ring-orange-100 transition-all duration-200 placeholder-gray-400" 
                            placeholder="e.g., 5.00"
                        />
                    </div>
                    <p class="mt-2 text-xs text-gray-500">Enter the rental rate per minute in Kenyan Shillings</p>
                </div>

                <!-- Info Banner -->
                <div class="bg-orange-50 border-l-4 border-orange-400 p-3 sm:p-4 rounded-lg">
                    <div class="flex items-start">
                        <svg class="w-4 h-4 sm:w-5 sm:h-5 text-orange-400 mt-0.5 mr-2 sm:mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"></path>
                        </svg>
                        <p class="text-xs sm:text-sm text-orange-700">
                            The bike code will be used to identify this bike in the system. Keep it safe for your records.
                        </p>
                    </div>
                </div>
            </div>
        `,
        focusConfirm: false,
        showCancelButton: false,
        confirmButtonText: '<span class="flex items-center justify-center gap-2 text-sm sm:text-base"><svg class="w-4 h-4 sm:w-5 sm:h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg>Register Bike</span>',
        buttonsStyling: false,
        customClass: {
            confirmButton: "w-full bg-gradient-to-r from-orange-500 to-orange-600 text-white font-semibold px-6 sm:px-8 py-3 sm:py-3.5 rounded-lg sm:rounded-xl shadow-lg hover:shadow-xl hover:from-orange-600 hover:to-orange-700 transition-all duration-200 transform hover:scale-105",
            popup: "rounded-2xl sm:rounded-3xl shadow-2xl p-5 sm:p-8 md:p-10 w-[85%] sm:w-[80%] md:w-[450px] lg:w-[550px] max-w-2xl bg-gradient-to-br from-white to-gray-50 mx-auto",
            title: "text-xl sm:text-2xl font-bold text-gray-800 mb-2 text-center",
            htmlContainer: "text-gray-700 text-sm sm:text-base",
            actions: "w-full mt-6 sm:mt-8"
        },
        preConfirm: () => {
            const bikeCode = document.getElementById("bikeCodeInput").value;
            const rpm = document.getElementById("rpmInput").value;

            if (!rpm || rpm <= 0) {
                Swal.showValidationMessage(
                    '<div class="flex items-center gap-2 text-red-600"><svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path></svg><span>Please enter a valid rate per minute</span></div>'
                );
                return false;
            }

            return { bikeCode, rpm };
        }
    }).then(async (result) => {
        if (result.isConfirmed) {
            const { bikeCode, rpm } = result.value;

            // Show loading state
            Swal.fire({
                title: '<div class="flex items-center justify-center gap-3"><div class="rounded-full h-8 w-8 border-b-2 border-orange-500"></div><span>Registering...</span></div>',
                html: '<p class="text-gray-600 mt-2 text-center">Please wait while we register your bike</p>',
                showConfirmButton: false,
                allowOutsideClick: false,
                customClass: {
                    popup: "rounded-2xl sm:rounded-3xl shadow-2xl p-6 sm:p-8 w-[90%] sm:w-auto mx-auto",
                    title: "text-lg sm:text-xl font-semibold text-gray-800"
                }
            });

            try {
                // TODO: Replace with actual API call
                // const response = await fetch("/bike/register", {
                //     method: "POST",
                //     headers: { "Content-Type": "application/json" },
                //     body: JSON.stringify({ bikeCode, rpm })
                // });
                // const data = await response.json();
                
                // Simulate API call
                await new Promise(resolve => setTimeout(resolve, 1000));
                console.log("Bike Code:", bikeCode, "Rate per minute:", rpm);

                // Success message
                Swal.fire({
                    icon: "success",
                    title: '<div class="text-xl sm:text-2xl font-bold text-gray-800 text-center">Bike Registered Successfully!</div>',
                    html: `
                        <div class="mt-4 space-y-3">
                            <div class="p-4 bg-orange-50 rounded-lg border border-orange-200">
                                <div class="flex items-center justify-between mb-2">
                                    <span class="text-sm text-gray-600 font-medium">Bike Code:</span>
                                    <span class="text-lg font-bold font-mono tracking-wider text-orange-600">${bikeCode}</span>
                                </div>
                                <div class="flex items-center justify-between">
                                    <span class="text-sm text-gray-600 font-medium">Rate Per Minute:</span>
                                    <span class="text-lg font-bold text-green-600">KSH ${parseFloat(rpm).toFixed(2)}</span>
                                </div>
                            </div>
                            <p class="text-xs sm:text-sm text-gray-500 text-center">Keep the bike code safe for your records</p>
                        </div>
                    `,
                    background: "linear-gradient(135deg, #ECFDF5 0%, #D1FAE5 100%)",
                    iconColor: "#10B981",
                    confirmButtonText: "Done",
                    buttonsStyling: false,
                    customClass: {
                        confirmButton: "w-full bg-gradient-to-r from-green-500 to-green-600 text-white font-semibold px-8 py-3 sm:py-3.5 rounded-lg sm:rounded-xl shadow-lg hover:shadow-xl hover:from-green-600 hover:to-green-700 transition-all duration-200",
                        popup: "rounded-2xl sm:rounded-3xl shadow-2xl p-6 sm:p-8 w-[95%] sm:w-[90%] md:w-[550px] max-w-xl mx-auto",
                        title: "mb-2",
                        htmlContainer: "text-gray-700",
                        actions: "w-full mt-6"
                    }
                });
            } catch (error) {
                // Error message
                Swal.fire({
                    icon: "error",
                    title: '<div class="text-xl sm:text-2xl font-bold text-gray-800 text-center">Registration Failed</div>',
                    html: `<p class="text-gray-600 mt-2 text-center">${error.message || "Unable to register bike. Please try again."}</p>`,
                    background: "linear-gradient(135deg, #FEF2F2 0%, #FEE2E2 100%)",
                    iconColor: "#EF4444",
                    confirmButtonText: "Try Again",
                    buttonsStyling: false,
                    customClass: {
                        confirmButton: "w-full bg-gradient-to-r from-red-500 to-red-600 text-white font-semibold px-8 py-3 sm:py-3.5 rounded-lg sm:rounded-xl shadow-lg hover:shadow-xl hover:from-red-600 hover:to-red-700 transition-all duration-200",
                        popup: "rounded-2xl sm:rounded-3xl shadow-2xl p-6 sm:p-8 w-[95%] sm:w-[90%] md:w-[550px] max-w-xl mx-auto",
                        title: "mb-2",
                        htmlContainer: "text-gray-700",
                        actions: "w-full mt-6"
                    }
                });
            }
        }
    });
});