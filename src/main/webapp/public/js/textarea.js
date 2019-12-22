$(document).ready(function() {
    $("#description_textarea").summernote({
        height: 100,   //set editable area's height
    });

    $("#text_textarea").summernote({
        height: 600,   //set editable area's height
    });

    $("#comment_textarea").summernote({
        height: 150,   //set editable area's height
        toolbar: [
            // [groupName, [list of button]]
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['font', ['strikethrough', 'superscript', 'subscript']],
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert', ['link']],
        ]
    });
});