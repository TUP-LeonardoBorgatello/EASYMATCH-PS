var ctx = document.getElementById('reporte1')
var mychart = new Chart(ctx,
    {
        type: 'line',
        data:
            {
                datasets:
                    [{
                        backgroundColor: ['#FC0101','#FC6401','#FCE901','#27FC01','#01FCFC','#0177FC','#8A01FC','#01FCA1','#FCB401','#01CBFC','#B8FC01'],
                        label: 'Cantiadad de Productos por pedido',
                        borderWidth: 3
                    }]
            },
        options:
            {
                legend: {
                    display: false
                },
                scales: {
                    yAxis: [{
                        gridLines: {
                            display: false
                        },
                        ticks: {
                            display: true
                        }
                    }]
                }
            }
    })


url ='http://localhost:8080/api/easyventas/reporte1';

fetch(url)
    .then(response => response.json())
    .then(datos => mostrar(datos))
    .catch(error => console.log(error))

function mostrar(productos) {
    productos.forEach(element => {
        mychart.data['labels'].push(element.numeroPedido)
        mychart.data['datasets'][0].data.push(element.cantidad)
    });

}