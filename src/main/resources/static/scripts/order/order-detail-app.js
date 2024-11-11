let orderDetailApp = (function () {
  let orders = [];

  function initOrderTable() {
    new DataTable("#order-detail-table", {
      data: [],
      columns: [
        {
          data: null,
          render: function (data, type, row, meta) {
            return meta.row + meta.settings._iDisplayStart + 1;
          },
        },
        { data: "description" },
        {
          data: null,
          render: function (data, type, full, meta) {
            return full.quantity + " x " + formatRupiah(full.price);
          },
        },
        {
          data: "paymentSubtotal",
          render: function (data) {
            return formatRupiah(data);
            // return data
          },
        },
        {
          data: null,
          render: function (data) {
            return "<button type='button' class='hapus btn btn-light btn-sm'><i class='bi bi-trash'></i> Hapus</button>";
          },
        },
      ],
      searching: false,
      info: false,
      paging: false,
      footerCallback: function (tr, data, start, end, display) {
        let api = this.api();
        const total = api
          .column(3)
          .data()
          .reduce((a, b) => a + b, 0);
        console.log(total);

        api.column(3).footer().innerHTML = formatRupiah(total);
      },
    });

    $("#order-detail-table tbody").on("click", "button.hapus", function (e) {
      var d = $("#order-detail-table")
        .DataTable()
        .row(e.target.closest("tr"))
        .data();
      orders = orders.filter((order) => order.variantId !== d.variantId);
      refreshOrderTable(orders);
    });
  }

  function findMenu(variantId) {
    return menus.find((menu) => menu.id === parseInt(variantId));
  }

  function addOrderFormHandler() {
    const addOrderForm = $("#add-order-form")[0];

    $("#qty-input").on("blur", function (e) {
      const chosenMenu = findMenu(addOrderForm[0].value);
      addOrderForm[2].value = formatRupiah(
        chosenMenu.price * parseInt(this.value)
      );
    });

    $("#add-order-form").on("submit", function (e) {
      e.preventDefault();
      let isValid = true;
      if (!this[0].value) {
        const errorElement = createErrorElement("Isian tidak boleh kosong");
        this[0].insertAdjacentElement("afterend", errorElement);
        isValid = false;
      }

      if (!this[1].value) {
        const errorElement = createErrorElement("Isian tidak boleh kosong");
        this[1].insertAdjacentElement("afterend", errorElement);
        isValid = false;
      }

      if (isValid) {
        const chosenMenu = findMenu(this[0].value);

        const order = {
          variantId: chosenMenu.id,
          quantity: parseInt(this[1].value),
          paymentSubtotal: chosenMenu.price * parseInt(this[1].value),
          description: chosenMenu.description,
          price: chosenMenu.price,
        };
        orders.push(order);

        refreshOrderTable(orders);

        this.reset();
      }
    });

    console.dir(addOrderForm);
  }

  function orderFormHandler() {
    $("#order-form").on("submit", async function (e) {
      let isValid = true;
      e.preventDefault();

      clearErrorMessage(this);

      if (!this[0].value) {
        const errorElement = createErrorElement("Isian tidak boleh kosong");
        this[0].insertAdjacentElement("afterend", errorElement);
        isValid = false;
      }

      if (!this[1].value) {
        const errorElement = createErrorElement("Isian tidak boleh kosong");
        this[1].insertAdjacentElement("afterend", errorElement);
        isValid = false;
      }

      if (orders.length < 1) {
        alert("Tambahkan pesanan terlebih dahulu");
        isValid = false;
      }

      if (isValid) {
        const request = {
          customerName: this[0].value,
          date: new Date(this[1].value),
          details: orders.map((order) => ({
            variantId: order.variantId,
            quantity: order.quantity,
            paymentSubtotal: order.paymentSubtotal,
          })),
        };

        const response = await axios.post(ctx + "orders/create", request);
        if (response.status === 200) {
          window.alert("Pesanan berhasil ditambahkan");

          this.reset();
          orders = [];
          refreshOrderTable(orders);
        }
      }
    });
  }

  function createErrorElement(targetElement, message) {
    const p = document.createElement("p");
    p.className = "invalid text-danger";
    p.textContent = "Isian tidak boleh kosong";
    return p;
  }

  function clearErrorMessage(form) {
    const errorElements = form.querySelectorAll(".invalid");
    for (let err of errorElements) {
      err.remove();
    }
  }

  function refreshOrderTable(data) {
    $("#order-detail-table").DataTable().clear().rows.add(data).draw();
  }

  function formatRupiah(amount) {
    return new Intl.NumberFormat("id-ID", {
      style: "currency",
      currency: "IDR",
    }).format(amount);
  }

  return {
    init: function () {
      initOrderTable();
      addOrderFormHandler();
      orderFormHandler();
    },
  };
})();

jQuery(document).ready(function () {
  orderDetailApp.init();
});
