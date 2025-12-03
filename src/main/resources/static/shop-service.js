document.addEventListener("DOMContentLoaded", () => {
  const btn = document.getElementById("getStarted");
  if (btn) {
    btn.addEventListener("click", openShopDialog);
  }
});

function openShopDialog() {
  Swal.fire({
    title: "Create Your Shop",
    html: `
      <div class="flex flex-col gap-4 text-left mt-2">
        <label for="shop-name" class="font-semibold text-gray-700">Shop Name</label>
        <input id="shop-name" 
               type="text"
               class="swal2-input rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary placeholder-gray-400" 
               placeholder="Enter shop name" />

        <label for="shop-location" class="font-semibold text-gray-700">Location</label>
        <input id="shop-location" 
               type="text"
               class="swal2-input rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary placeholder-gray-400" 
               placeholder="Enter location" />
      </div>
    `,
    focusConfirm: false,
    showCancelButton: false,
    confirmButtonText: "Save Details",
    cancelButtonText: "Cancel",
    
    buttonsStyling: false,
    customClass: {
      confirmButton: "bg-primary text-white px-5 py-2 rounded-lg hover:bg-blue-700 transition",
      cancelButton: "bg-gray-200 text-gray-700 px-5 py-2 rounded-lg hover:bg-gray-300 transition",
    },
    preConfirm: () => {
      const name = document.getElementById("shop-name").value.trim();
      const location = document.getElementById("shop-location").value.trim();

      if (!name || !location) {
        Swal.showValidationMessage("Please fill out all fields.");
        return false;
      }

      return { name, location };
    }
  }).then(result => {
    if (result.isConfirmed) {
      console.log("Saved shop:", result.value);
      Swal.fire({
        icon: "success",
        title: "Shop Saved",
        text: "Your shop details have been stored successfully.",
        timer: 1600,
        showConfirmButton: false,
        background: "#F0F9FF",
        iconColor: "#1D4ED8",
        customClass: {
          title: "font-semibold text-gray-800",
          popup: "rounded-2xl shadow-lg p-6",
          content: "text-gray-700"
        }
      });
    }
  });
}
