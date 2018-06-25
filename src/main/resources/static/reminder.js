$(document).ready( function () {
    $('#table').DataTable({
        columnDefs: [
            { targets: '_all', orderable: false }
        ],
        aaSorting: []
    });
} );