document.addEventListener("DOMContentLoaded", () => {
  const btn = document.getElementById("getStarted");
  if (btn) {
    btn.addEventListener("click", openShopDialog);
  }
});

function openShopDialog() {
  Swal.fire({
    title: "Update Shop Details",
    html: `
      <div class="flex flex-col gap-4 text-left mt-2">
        <label for="shop-name" class="font-semibold text-gray-800">Shop Name</label>
        <input id="shop-name" 
               type="text"
               class="swal2-input rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder-gray-400" 
               placeholder="Enter shop name" />

        <label for="shop-location" class="font-semibold text-gray-800">Location</label>
        <input id="shop-location" 
               type="text"
               class="swal2-input rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder-gray-400" 
               placeholder="Enter location" />
      </div>
    `,
    focusConfirm: false,
    showCancelButton: false,
    confirmButtonText: "Save Details",
    buttonsStyling: false,
    customClass: {
      confirmButton: "bg-blue-600 text-white font-semibold px-6 py-2 rounded-lg shadow hover:bg-blue-700 transition-colors",
      cancelButton: "bg-gray-200 text-gray-700 px-6 py-2 rounded-lg shadow hover:bg-gray-300 transition-colors",
      popup: "rounded-2xl shadow-xl p-6 max-w-md mx-auto",
      title: "text-xl font-semibold text-gray-800",
      content: "text-gray-700 mt-2"
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
        background: "#EFF6FF",
        iconColor: "#1D4ED8",
        customClass: {
          title: "font-semibold text-gray-800",
          content: "text-gray-700",
          popup: "rounded-2xl shadow-lg p-6"
        }
      });
    }
  });
}
