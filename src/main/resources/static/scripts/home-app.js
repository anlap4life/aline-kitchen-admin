const homeApp = (function () {
  function initChart() {
    const ctx = document.getElementById("myChart");

    new Chart(ctx, {
      type: "bar",
      data: {
        labels: [
          "Senin",
          "Selasa",
          "Rabu",
          "Kamis",
          "Jumat",
          "Sabtu",
          "Minggu",
        ],
        datasets: [
          {
            label: "Belum Bayar",
            data: [12, 19, 3, 5, 2, 3, 2, 3],
            borderWidth: 1,
            backgroundColor: "rgba(220,53,69,0.2)",
          },
          {
            label: "Sudah Bayar",
            data: [12, 19, 3, 5, 2, 3, 2, 3],
            borderWidth: 1,
            backgroundColor: "rgba(220,53,69,0.5)",
          },

          {
            label: "Sudah Diantar",
            data: [12, 19, 3, 5, 2, 3, 2, 3],
            borderWidth: 1,
            backgroundColor: "rgba(220,53,69,255)",
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          x: {
            stacked: true,
            grid: {
              display: false,
            },
          },
          y: {
            stacked: true,
            grid: {
              display: false,
            },
          },
        },
      },
    });
  }

  return {
    init: function () {
      console.log("hello");
      initChart();
    },
  };
})();

jQuery(document).ready(function () {
  homeApp.init();
});
