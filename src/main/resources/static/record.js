function populate(list, element) {
    element.empty();
    console.log(list);
    $.each(list, function () {
        console.log(this);
        element.append($("<option />").val(this[0]).text(this[1].name));
    });
    element.trigger('change')
}

$(document).ready(function () {
    let data;

    const table = $('#table').DataTable({
        columnDefs: [
            { targets: '_all', orderable: false }
        ],
        aaSorting: [],
        columns: [
            { data: 'schedule.rule.device.name' },
            { data: 'schedule.rule.name' },
            { data: 'startTime' },
            { data: 'endTime' },
            { data: 'employee.name' }
        ],
        ajax: {
            url: '/getRecord',
            dataSrc: function (json) {
                data = json.reduce(function (acc, cur, i) {
                    const deviceId = cur.schedule.rule.device.id;
                    if (acc.has(deviceId)) {
                        const value = acc.get(deviceId);
                        const ruleId = cur.schedule.rule.id;
                        if (value.rules.has(ruleId))
                            value.rules.get(ruleId).indices.push(i);
                        else
                            value.rules.set(ruleId, {
                                name: cur.schedule.rule.name,
                                indices: [i]
                            });
                    } else
                        acc.set(deviceId, {
                            name: cur.schedule.rule.device.name,
                            rules: new Map([[cur.schedule.rule.id, {
                                name: cur.schedule.rule.name,
                                indices: [i]
                            }]])
                        });
                    return acc;
                }, new Map());
                console.log(data);
                const $deviceSelect = $("#deviceSelect");
                const $ruleSelect = $("#ruleSelect");
                $.fn.dataTable.ext.search.push(
                    function (settings, _data, dataIndex) {
                        const deviceId = Number($deviceSelect.val());
                        const ruleId = Number($ruleSelect.val());
                        if (deviceId && ruleId)
                            return data.get(deviceId).rules.get(ruleId).indices.includes(dataIndex);
                        else
                            return true
                    });
                $ruleSelect.on('change', function () {
                    table.draw()
                });
                $deviceSelect.on('change', function (e) {
                    populate(Array.from(data.get(Number(e.target.value)).rules), $ruleSelect);
                });
                populate(Array.from(data), $deviceSelect);
                return json
            }
        }
    });
});