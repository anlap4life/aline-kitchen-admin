let procurementApp = function () {

    function initProcurementTable() {
        new DataTable('#procurement-table')

        $('#procurement-table tbody').on('click', 'button.hapus', function (e) {
            const isConfirmed = window.confirm("Apakah kamu yakin?");
            if (isConfirmed) {
                window.location.replace(ctx + 'procurements/delete?procurementId=' + this.dataset.id)
            }
        })
    }

    return {
        init: function () {
            initProcurementTable();
        }
    }
}();

jQuery(document).ready(function () {
    procurementApp.init();
})