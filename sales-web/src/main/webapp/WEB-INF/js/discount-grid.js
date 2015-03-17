$(document).ready(function() {

    var gridId = '#discountGrid';

    var parentFields = {
        id: {
            key: true,
            create: false,
            edit: false,
            list: false
        },
        date: {
            title: 'Дата',
            width: '20%',
            type: 'date',
            displayFormat: 'yy-mm-dd'
        },
        name: {
            title: 'Наименование',
            width: '70%'
        },
        discount: {
            title: 'Скидка, %'
        },
        hour: {
            title: 'Час'
        }
    };

    var parentActions = {
        listAction: function(postData, jtParams) {
            return $.Deferred(function($dfd) {
                $.ajax({
                    url: window.contextPath + '/discounts/list?' + 'page=' + (Math.floor(jtParams.jtStartIndex / jtParams.jtPageSize) + 1) + '&size=' + jtParams.jtPageSize,
                    type: 'GET',
                    dataType: 'json',
                    data: null,
                    success: function(response) {

                        var result = {
                            Result: 'ERROR',
                            Message: 'Ошибка получения данных',
                            Records: [],
                            TotalRecordCount: 0
                        };

                        if (response) {
                            if (response.success === true) {
                                result.Result = 'OK';

                                //переводим
                                response.data.forEach(function(obj) {
                                    result.Records.push({
                                        id: obj.id,
                                        date: obj.date,
                                        name: obj.product.name,
                                        discount: obj.percent,
                                        hour: obj.hour
                                    });
                                });

                                result.TotalRecordCount = response.total;
                            }
                            else
                            {
                                result.Result = 'ERROR';
                                console.log('jtable error:' + response.message);
                            }
                        }

                        $dfd.resolve(result);
                    },
                    error: function() {
                        console.log("jtable: error listAction...");
                        $dfd.reject();
                    }
                });
            });
        }
    };

    createJTableGridWithPages(gridId, 'История скидок', parentActions, parentFields);
    $(gridId).jtable('load');
});