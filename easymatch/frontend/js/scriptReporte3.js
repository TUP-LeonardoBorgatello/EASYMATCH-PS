var ctx2 = document.getElementById('reporte3')
var mychart2 = new Chart(ctx2,
    {
        type: 'bar',
        data:
            {
                datasets:
                    [{
                        backgroundColor: ['#01FCA1', '#FCB401', '#01CBFC', '#B8FC01'],
                        label: 'Cantiadad de Productos por pedido',
                        borderColor: ['black'],
                        borderWidth: 1
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


function mostartxt() {
    let doc = document.getElementById("txt").value;
    let url2 = 'http://localhost:8080/api/easyventas/reporte1/' + doc + '';

    fetch(url2)
        .then(response => response.json())
        .then(datos => mostrar2(datos))
        .catch(error => console.log(error))

}



function mostrar2(productos) {
    productos.forEach(element => {
        mychart2.data['labels'].push(element.numeroPedido)
        mychart2.data['datasets'][0].data.push(element.cantidad)
    });
}
