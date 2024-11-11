let orderApp = (function () {
  const modal = document.querySelector("#order-detail-modal");

  function initOrderTable() {
    new DataTable("#order-table");

    $("#order-table tbody").on("click", "button.detail", async function (e) {
      console.log(ctx);
      const response = await axios.get(ctx + "orders/details", {
        params: {
          orderId: this.dataset.id,
        },
      });

      if (response.status !== 200) {
        window.alert("Gagal mengambil detail pesanan!");
        return;
      }

      const listItems = document.querySelectorAll(".list-group-item");
      for (let item of listItems) {
        item.remove();
      }

      const orderListWrapper = document.querySelector(".list-group");

      // loop through here
      for (let detail of response.data) {
        const li = document.createElement("li");
        li.className = "list-group-item";
        li.textContent = detail.orderDesc;

        orderListWrapper.append(li);
      }

      var myModal = new bootstrap.Modal(
        document.getElementById("order-detail-modal")
      );

      myModal.show();
    });

    $("#order-table tbody").on("click", "button.bayar", function (e) {
      const isConfirmed = window.confirm("Apakah kamu yakin?");
      if (isConfirmed) {
        window.location.replace(ctx + "orders/pay?orderId=" + this.dataset.id);

        console.log("bayar");
      }
    });

    $("#order-table tbody").on("click", "button.kirim", function (e) {
      const isConfirmed = window.confirm("Apakah kamu yakin?");
      if (isConfirmed) {
        window.location.replace(
          ctx + "orders/deliver?orderId=" + this.dataset.id
        );
      }
    });

    $("#order-table tbody").on("click", "button.hapus", function (e) {
      const isConfirmed = window.confirm("Apakah kamu yakin?");
      if (isConfirmed) {
        window.location.replace(
          ctx + "orders/delete?orderId=" + this.dataset.id
        );
      }
    });
  }

  return {
    init: function () {
      initOrderTable();
    },
  };
})();

jQuery(document).ready(function () {
  orderApp.init();
});
