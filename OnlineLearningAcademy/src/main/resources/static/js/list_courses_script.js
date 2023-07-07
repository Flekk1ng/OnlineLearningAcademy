function addCourse(areaId) {
    $('#details-card').remove();
    $('.col-md-8')
        .addClass('mx-auto')
        .addClass('col-md-10')
        .removeClass('col-md-8')
    var detailsCard = $('<div id="details-card" class="card border-dark" style="width: 18rem;">' +
        '<div class="card-body">' +
        '<div class="card-header"> ' +
        '<h5 class="text-left">Add new course</h5>' +
        '</div>' +
        '<form method="post" action="/course/create">' +
        '<input type="hidden" name="areaOfStudyId" value="' + areaId +'">' +
        '<div class="mb-3">' +
        '<label class="form-label">Name:</label>' +
        '<input type="text" class="form-control" name="name">' +
        '</div>' +
        '<div class="mb-3">' +
        '<label class="form-label">Description:</label>' +
        '<textarea class="form-control" name="description" rows="7"></textarea>' +
        '</div>' +
        '<div class="container">' +
        '<div class="row">' +
        '<div class="col">' +
        '<button type="submit" class="btn btn-primary">' +
        '<i class="bi bi-cloud-upload-fill"></i> Save' +
        '</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</form>' +
        '</div>' +
        '</div>');
    $('.col-md-10')
        .removeClass('mx-auto')
        .removeClass('col-md-10')
        .addClass('col-md-8')
        .after($('<div class="col-md-4">').append(detailsCard));
}