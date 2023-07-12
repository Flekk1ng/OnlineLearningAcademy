$('tr').click(function() {
    $('tr').removeClass('selected');
    $(this).addClass('selected');
    $('#details-card').remove();

    var userId = $(this).data('id');
    var userFirstName = $(this).data('firstname');
    var userLastName = $(this).data('lastname');
    var userEmail = $(this).find('td').eq(0).text();
    var userRole = $(this).find('td').eq(1).text();

    var detailsCard = $('<div id="details-card" class="card border-dark" style="width: 18rem;">' +
        '<div class="card-body">' +
        '<div class="card-header"> ' +
        '<h5 class="text-left">Edit user details</h5>' +
        '</div>' +
        '<form method="post" action="/user/edit">' +
        '<div class="mb-1">' +
        '<input type="hidden" name="id" value="' + userId + '">' +
        '<label class="form-label">User id:</label>' +
        '<label class="form-label">' + userId + '</label>' +
        '</div>' +
        '<div class="mb-3">' +
        '<label class="form-label">First name:</label>' +
        '<input type="text" class="form-control" name="firstName" value="' + userFirstName + '">' +
        '</div>' +
        '<div class="mb-3">' +
        '<label class="form-label">Last name:</label>' +
        '<input type="text" class="form-control" name="lastName" value="' + userLastName + '">' +
        '</div>' +
        '<div class="mb-3">' +
        '<label class="form-label">Email:</label>' +
        '<input type="email" class="form-control" value="' + userEmail + '" disabled>' +
        '</div>' +
        '<div class="mb-3">' +
        '<label for="role-select" class="form-label">Role</label>' +
        '<select class="form-select" name="role">' +
        '<option value="STUDENT"' + (userRole == 'STUDENT' ? ' selected' : '') + '>Student</option>' +
        '<option value="TEACHER"' + (userRole == 'TEACHER' ? ' selected' : '') + '>Teacher</option>' +
        '<option value="ADMIN"' + (userRole == 'ADMIN' ? ' selected' : '') + '>Admin</option>' +
        '</select>' +
        '</div>' +
        '<div class="container">' +
        '<div class="row gx-2">' +
        '<div class="col-auto">' +
        '<button type="submit" class="btn btn-primary">' +
        '<i class="bi bi-cloud-upload-fill"></i> Save' +
        '</button>' +
        '</div>' +
        '<div class="col-auto">' +
        '<button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmModalCenter">' +
        '<i class="bi bi-trash3"></i> Delete' +
        '</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</form>' +
        '</div>' +
        '</div>' +
        '<div class="modal fade" id="confirmModalCenter" tabindex="-1" role="dialog" aria-labelledby="confirmModalCenterTitle" aria-hidden="true">' +
        '<div class="modal-dialog modal-dialog-centered" role="document">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<h5 class="modal-title">Are you sure you want to delete this user?</h5>' +
        '<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">' +
        '<span aria-hidden="true">&times;</span>' +
        '</button>' +
        '</div>' +
        '<div class="modal-body">' +
        'After deleting his grades and attendance will be deleted' +
        '</div>' +
        '<div class="modal-footer">' +
        '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>' +
        '<form method="post" action="/user/delete/' + userId + '">' +
        '<button type="submit" class="btn btn-danger">' +
        '<i class="bi bi-trash3"></i>Delete</button>' +
        '</form>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>'
    );

    $('.col-md-8').removeClass('mx-auto').after($('<div class="col-md-4">').append(detailsCard));
});


