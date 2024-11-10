let foodApp = function () {

    function initFoodTable() {
        new DataTable('#food-table')

        $('#food-table tbody').on('click', 'button.hapus', function (e) {
            const isConfirmed = window.confirm("Apakah kamu yakin?");
            if (isConfirmed) {
                window.location.replace(ctx + 'foods/delete?foodId=' + this.dataset.id)
            }
        })
    }

    return {
        init: function () {
            initFoodTable();
            console.log('hwllo')
        }
    }
}();

jQuery(document).ready(function () {
    foodApp.init();
})