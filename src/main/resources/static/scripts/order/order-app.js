let orderApp = function () {

    function initFoodTable() {
        new DataTable('#order-table')

        // $('#food-table tbody').on('click', 'button.hapus', function (e) {
        //     const isConfirmed = window.confirm("Apakah kamu yakin?");
        //     if (isConfirmed) {
        //         window.location.replace(ctx + 'foods/delete?foodId=' + this.dataset.id)
        //     }
        // })
    }

    return {
        init: function () {
            initFoodTable();
        }
    }
}();

jQuery(document).ready(function () {
    orderApp.init();
})