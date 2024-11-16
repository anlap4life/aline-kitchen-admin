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
        datasets: chartDataset,
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
