$(document).ready(function() {

    var gridId = '#discountByHourGrid';

    var parentFields = {
        id: {
            key: true,
            create: false,
            edit: false,
            list: false
        },
        saleDate: {
            title: 'Дата чека',
            width: '20%',
            type: 'date',
            displayFormat: 'yy-mm-dd'
        },
        saleHour: {
            title: 'Час'
        },
        saleCount: {
            title: 'Количество чеков'
        },
        saleSumFull: {
            title: 'Общая стоимость чеков'
        },
        saleSumFullAvg: {
            title: 'Средняя стоимость чека'
        },
        saleSumDiscount: {
            title: 'Сумма скидок'
        },
        saleSumFullWithDiscount: {
            title: 'Стоимость чеков с учетом скидки'
        },
        saleSumFullAvgWithDiscount: {
            title: 'Средняя стоимость чеков с учетом скидки'
        }
    };

    var parentActions = {
        listAction: function(postData, jtParams) {
            return $.Deferred(function($dfd) {
                $.ajax({
                    url: window.contextPath + '/discounts/reports/list/discount-by-hour',
                    type: 'GET',
                    dataType: 'json',
                    data: null,
                    success: function(response) {

                        var result = {
                            Result: 'ERROR',
                            Message: 'Ошибка получения данных',
                            Records: []
                        };

                        if (response) {
                            if (response.success === true) {
                                result.Result = 'OK';

                                //переводим копейки в рубли
                                response.data.forEach(function(obj) {
                                    obj.saleSumFull = convertPriceInteger2Float(obj.saleSumFull);
                                    obj.saleSumFullAvg = convertPriceInteger2Float(obj.saleSumFullAvg);
                                    obj.saleSumDiscount = convertPriceInteger2Float(obj.saleSumDiscount);
                                    obj.saleSumFullWithDiscount = convertPriceInteger2Float(obj.saleSumFullWithDiscount);
                                    obj.saleSumFullAvgWithDiscount = convertPriceInteger2Float(obj.saleSumFullAvgWithDiscount);
                                });

                                result.Records = response.data;
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

    createJTableGrid(gridId, 'Отчет почасовой статистики', parentActions, parentFields);
    $(gridId).jtable('load');
});