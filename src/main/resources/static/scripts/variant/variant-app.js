let variantApp = function () {

    function initVariantApp() {
        new DataTable('#variant-table')

        $('#variant-table tbody').on('click', 'button.hapus', function (e) {
            const isConfirmed = window.confirm("Apakah kamu yakin?");
            if (isConfirmed) {
                window.location.replace(ctx + 'foods/variants/delete?variantId=' + this.dataset.id)
            }
        })
    }

    return {
        init: function () {
            initVariantApp();
        }
    }
}();

jQuery(document).ready(function () {
    variantApp.init();
})