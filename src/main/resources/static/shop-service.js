document.addEventListener("DOMContentLoaded", () => {
  const btn = document.getElementById("getStarted");
  if (btn) {
    btn.addEventListener("click", function() {
      const name = this.dataset.shopName || this.getAttribute('data-shop-name') || '';
      const location = this.dataset.shopLocation || this.getAttribute('data-shop-location') || '';
      openShopDialog(name, location);
    });
  }
});

function openShopDialog(name = '', location = '') {
  Swal.fire({
    title: '<div class="flex items-center gap-3"><svg class="w-8 h-8 text-orange-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path></svg><span>Update Shop Details</span></div>',
    html: `
      <div class="space-y-6 text-left mt-6">
        <!-- Shop Name Input -->
        <div class="relative">
          <label for="shop-name" class="block text-sm font-semibold text-gray-700 mb-2">
            Shop Name
          </label>
          <div class="relative">
            <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
              <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
              </svg>
            </div>
            <input 
              id="shop-name" 
              value="${name || ''}"
              type="text"
              class="w-full pl-12 pr-4 py-3 text-gray-900 border-2 border-gray-200 rounded-xl focus:outline-none focus:border-orange-500 focus:ring-4 focus:ring-orange-100 transition-all duration-200 placeholder-gray-400" 
              placeholder="e.g., Joe's Coffee Shop" 
            />
          </div>
        </div>

        <!-- Location Input -->
        <div class="relative">
          <label for="shop-location" class="block text-sm font-semibold text-gray-700 mb-2">
            Location
          </label>
          <div class="relative">
            <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
              <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
              </svg>
            </div>
            <input 
              id="shop-location"
              value="${location || ''}" 
              type="text"
              class="w-full pl-12 pr-4 py-3 text-gray-900 border-2 border-gray-200 rounded-xl focus:outline-none focus:border-orange-500 focus:ring-4 focus:ring-orange-100 transition-all duration-200 placeholder-gray-400" 
              placeholder="e.g., 123 Main Street, Downtown" 
            />
          </div>
        </div>

        <!-- Info Banner -->
        <div class="bg-blue-50 border-l-4 border-blue-400 p-4 rounded-lg">
          <div class="flex items-start">
            <svg class="w-5 h-5 text-blue-400 mt-0.5 mr-3 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"></path>
            </svg>
            <p class="text-sm text-blue-700">
              This information will be displayed on your dashboard and used for your business records.
            </p>
          </div>
        </div>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: '<span class="flex items-center gap-2"><svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg>Save Details</span>',
    cancelButtonText: '<span class="flex items-center gap-2"><svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>Cancel</span>',
    buttonsStyling: false,
    customClass: {
      confirmButton: "bg-gradient-to-r from-orange-500 to-orange-600 text-white font-semibold px-6 py-3 rounded-xl shadow-lg hover:shadow-xl hover:from-orange-600 hover:to-orange-700 transition-all duration-200 transform hover:scale-105",
      cancelButton: "bg-white text-gray-700 font-semibold px-6 py-3 rounded-xl border-2 border-gray-300 shadow hover:bg-gray-50 hover:border-gray-400 transition-all duration-200 mr-3",
      popup: "rounded-3xl shadow-2xl p-8 max-w-2xl bg-gradient-to-br from-white to-gray-50",
      title: "text-2xl font-bold text-gray-800 mb-2",
      htmlContainer: "text-gray-700",
      actions: "gap-3 mt-8"
    },
    preConfirm: () => {
      const name = document.getElementById("shop-name").value.trim();
      const location = document.getElementById("shop-location").value.trim();

      if (!name || !location) {
        Swal.showValidationMessage(
          '<div class="flex items-center gap-2 text-red-600"><svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path></svg><span>Please fill out all fields</span></div>'
        );
        return false;
      }

      return { name, location };
    }
  }).then(async (result) => {
    if (result.isConfirmed) {
      const shopData = result.value;

      Swal.fire({
        title: '<div class="flex items-center gap-3"><div class="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-500"></div><span>Saving...</span></div>',
        html: '<p class="text-gray-600 mt-2">Please wait while we update your shop details</p>',
        showConfirmButton: false,
        allowOutsideClick: false,
        customClass: {
          popup: "rounded-3xl shadow-2xl p-8",
          title: "text-xl font-semibold text-gray-800"
        }
      });

      try {
        const response = await fetch("/shop/update", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(shopData)
        });

        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message || "Failed to save shop details.");
        }

        Swal.fire({
          icon: "success",
          title: '<div class="text-2xl font-bold text-gray-800">Shop Saved</div>',
          html: `<p class="text-gray-600 mt-2">${data.message}</p>`,
          background: "linear-gradient(135deg, #ECFDF5 0%, #D1FAE5 100%)",
          iconColor: "#10B981",
          confirmButtonText: "Got it!",
          buttonsStyling: false,
          customClass: {
            confirmButton: "bg-gradient-to-r from-green-500 to-green-600 text-white font-semibold px-8 py-3 rounded-xl shadow-lg hover:shadow-xl hover:from-green-600 hover:to-green-700 transition-all duration-200 transform hover:scale-105",
            popup: "rounded-3xl shadow-2xl p-8",
            title: "mb-2",
            htmlContainer: "text-gray-700"
          }
        }).then(() => {
          window.location.reload();
        });
      } catch (error) {
        Swal.fire({
          icon: "error",
          title: '<div class="text-2xl font-bold text-gray-800">Oops! Something went wrong</div>',
          html: `<p class="text-gray-600 mt-2">${error.message}</p>`,
          background: "linear-gradient(135deg, #FEF2F2 0%, #FEE2E2 100%)",
          iconColor: "#EF4444",
          confirmButtonText: "Try Again",
          buttonsStyling: false,
          customClass: {
            confirmButton: "bg-gradient-to-r from-red-500 to-red-600 text-white font-semibold px-8 py-3 rounded-xl shadow-lg hover:shadow-xl hover:from-red-600 hover:to-red-700 transition-all duration-200 transform hover:scale-105",
            popup: "rounded-3xl shadow-2xl p-8",
            title: "mb-2",
            htmlContainer: "text-gray-700"
          }
        });
      }
    }
  });
}
