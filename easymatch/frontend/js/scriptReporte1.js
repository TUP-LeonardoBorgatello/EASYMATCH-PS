
var ctx = document.getElementById('reporte4')
var mychart = new Chart(ctx,
    {
        type: 'line',
        data:
            {
                datasets:
                    [{
                        backgroundColor: ['#01fc05','#2ffc01','#01fc16','#27FC01','#01FCFC','#0177FC','#8A01FC','#01FCA1','#FCB401','#01CBFC','#B8FC01'],
                        label: 'Montos por fechas de reservas facturadas',
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


url ='http://localhost:8080/api/easymatch/reporte4';

fetch(url)
    .then(response => response.json())
    .then(datos => mostrar(datos))
    .catch(error => console.log(error))

function mostrar(productos) {
    productos.forEach(element => {
        mychart.data['labels'].push(element.idReservaFecha)
        mychart.data['datasets'][0].data.push(element.monto)
    });

}
