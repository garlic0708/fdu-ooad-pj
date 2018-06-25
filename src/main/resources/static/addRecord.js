$(document).ready(function () {
    $("#startTime, #endTime").datetimepicker();

    const $deviceSelect = $("#deviceSelect");
    $deviceSelect.change(function (e) {
        $.get('/getSchedule',
            { device: e.target.value },
            data => {
                const scheduleSelect = $("#scheduleSelect");
                scheduleSelect.empty();
                data.forEach(item => scheduleSelect.append($("<option />")
                    .val(item.id).text(item.rule.name)))
            })
    });
    $deviceSelect.trigger('change')
});