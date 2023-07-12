function addLesson(courseId) {
    $('#details-card').remove();
    var detailsCard = $('<div id="details-card" class="card border-dark" style="width: 18rem;">' +
        '<div class="card-body">' +
        '<div class="card-header"> ' +
        '<h5 class="text-left">Add new lesson</h5>' +
        '</div>' +
        '<form method="post" action="/lesson/create">' +
        '<input type="hidden" name="courseId" value="' + courseId +'">' +
        '<div class="mb-3">' +
        '<label class="form-label">Topic:</label>' +
        '<input type="text" class="form-control" name="topic">' +
        '</div>' +
        '<div class="mb-3">' +
        '<label class="form-label">Content:</label>' +
        '<textarea class="form-control" name="content" rows="4"></textarea>' +
        '</div>' +
        '<div class="mb-3">' +
        '<label class="form-label">Lesson start date:</label>' +
        '<input type="text" value="" class="default2" name="date"/>' +
        '</div>' +
        '<div class="mb-3">' +
        '<label class="form-label">Status:</label>' +
        '<select class="form-select" name="status">' +
        '<option value="IN PROGRESS">IN PROGRESS</option>' +
        '<option value="HAS ENDED">HAS ENDED</option>' +
        '<option value="IS EXPECTED">IS EXPECTED</option>' +
        '</select>' +
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
    $('.col-md-8')
        .removeClass('mx-auto')
        .removeClass('offset-md-3')
        .after($('<div class="col-md-4">').append(detailsCard));

    $(function(){
        $(".default2").datetimepicker();
    });
}

var modal = document.getElementById('confirmLessonModalCenter');
modal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var lessonId = button.getAttribute('data-bs-id');
    var form = modal.querySelector('form');
    form.action = '/lesson/delete/' + lessonId;
});