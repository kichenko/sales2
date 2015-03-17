
function getChildSaleGridActions(saleId) {
    return {
        listAction: function(postData, jtParams) {
            return $.Deferred(function($dfd) {
                $.ajax({
                    url: window.contextPath + '/items/list/' + saleId,
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
                                    obj.price = convertPriceInteger2Float(obj.price);
                                    obj.discount = convertPriceInteger2Float(obj.discount);
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
        },
        createAction: function(postData) {
            var dto = $.parseParams(postData);

            //установим saleId для item
            dto.saleId = saleId;

            return $.Deferred(function($dfd) {
                $.ajax({
                    url: window.contextPath + '/items/insert',
                    type: 'POST',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    data: JSON.stringify(dto),
                    success: function(response) {

                        var result = {
                            Result: 'ERROR',
                            Message: 'Ошибка получения данных',
                            Record: null
                        };

                        if (response) {
                            if (response.success === true) {
                                result.Result = 'OK';
                                result.Record = response.data;
                            }
                            else
                            {
                                result.Result = 'ERROR';
                                console.log('jtable errore:' + response.message);
                            }
                        }

                        $dfd.resolve(result);
                    },
                    error: function() {
                        console.log("jtable: error createAction...");
                        $dfd.reject();
                    }
                });
            });
        }
    };
}

function getChildSaleGridFields(saleId) {
    return {
        saleId: {
            key: true,
            create: false,
            edit: false,
            type: 'hidden',
            defaultValue: saleId
        },
        productId: {
            title: 'Товар',
            create: true,
            edit: false,
            list: false,
            visibility: 'hidden',
            inputClass: 'validate[required]',
            options: function() {
                var products = [];

                $.ajax({
                    url: window.contextPath + '/products/list/all',
                    type: 'GET',
                    dataType: 'json',
                    data: null,
                    async: false,
                    success: function(response) {
                        if (response.success !== true) {
                            return [];
                        }

                        response.data.forEach(function(e) {
                            products.push({
                                DisplayText: e.name,
                                Value: e.id
                            });
                        });
                    }
                });

                return products;
            }
        },
        name: {
            title: 'Наименование',
            create: false,
            edit: false,
            width: '20%'
        },
        price: {
            title: 'Цена, руб.',
            create: false,
            edit: false,
            width: '30%'
        },
        quantity: {
            title: 'Количество',
            width: '30%'
        },
        discount: {
            title: 'Скидка, руб.',
            create: false,
            edit: false,
            width: '30%'
        }
    };
}

function getChildSaleGridTitle(saleId) {
    return 'Позиции продажи #' + saleId;
}

$(document).ready(function() {

    var gridId = '#saleGrid';

    var parentFields = {
        id: {
            key: true,
            create: false,
            edit: false,
            list: false
        },
        items: {
            title: '',
            width: '1%',
            sorting: false,
            edit: false,
            create: false,
            display: function(saleData) {
                var $img = $('<img src="' + window.contextPath + '/images/list.png" title="Позиции" style = "cursor: pointer;"/>');
                $img.click(function() {
                    $(gridId).jtable('openChildTable',
                            $img.closest('tr'),
                            {
                                title: getChildSaleGridTitle(saleData.record.id),
                                actions: getChildSaleGridActions(saleData.record.id),
                                fields: getChildSaleGridFields(saleData.record.id),
                                loadingAnimationDelay: 0
                            },
                    function(data) {
                        data.childTable.jtable('load');
                    });
                });

                return $img;
            }
        },
        date: {
            title: 'Дата',
            width: '20%',
            type: 'date',
            displayFormat: 'yy-mm-dd',
            inputClass: 'validate[required,custom[date]]'
        }
    };

    var parentActions = {
        listAction: function(postData, jtParams) {
            return $.Deferred(function($dfd) {
                $.ajax({
                    url: window.contextPath + '/sales/list?' + 'page=' + (Math.floor(jtParams.jtStartIndex / jtParams.jtPageSize) + 1) + '&size=' + jtParams.jtPageSize,
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

                                //переводим копейки в рубли
                                response.data.forEach(function(obj) {
                                    obj.cost = convertPriceInteger2Float(obj.cost);
                                });

                                result.Records = response.data;
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
        },
        createAction: function(postData) {
            var dto = $.parseParams(postData);
            var d = new Date();
            dto.date = dto.date + "T" + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
            console.log(dto.date);
            return $.Deferred(function($dfd) {
                $.ajax({
                    url: window.contextPath + '/sales/insert',
                    type: 'POST',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    data: JSON.stringify(dto),
                    success: function(response) {

                        var result = {
                            Result: 'ERROR',
                            Message: 'Ошибка получения данных',
                            Record: null
                        };

                        if (response) {
                            if (response.success === true) {
                                result.Result = 'OK';
                                result.Record = response.data;
                            }
                            else
                            {
                                result.Result = 'ERROR';
                                console.log('jtable errore:' + response.message);
                            }
                        }

                        $dfd.resolve(result);
                    },
                    error: function() {
                        console.log("jtable: error createAction...");
                        $dfd.reject();
                    }
                });
            });
        }
    };

    createJTableGridWithPages(gridId, 'Продажи', parentActions, parentFields);
    $(gridId).jtable('load');
});
