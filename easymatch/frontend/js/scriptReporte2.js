function reporteFecha() {
    var fechaInicio = document.getElementById('fechaInicio2').value;
    var fechaFin = document.getElementById('fechaFin2').value;

    fetch('http://localhost:8080/api/easymatch/reporte3/' + fechaInicio + '/' + fechaFin + '')
        .then(response => response.json())
        .then(data => printCharts(data))
}

function printCharts(coasters) {

    modelDoughnutChart(coasters, 'reporte3')
}

function modelDoughnutChart(coasters, id) {

    const selectedCoasters = coasters.filter(eachCoaster => eachCoaster.cantReservas)

    const data = {
        labels: selectedCoasters.map(eachCoaster => eachCoaster.fecha),
        datasets: [{
            data: selectedCoasters.map(eachCoaster => eachCoaster.cantReservas),
            backgroundColor: ['#FC0101', '#FC6401', '#FCE901', '#27FC01', '#01FCFC', '#0177FC', '#8A01FC', '#01FCA1', '#FCB401', '#01CBFC', '#B8FC01'],
            borderColor: ['black']
        }]
    }

    const options = {
        legend: {
            position: 'right'
        }
    }

    new Chart(id, { type: 'doughnut', data, options })
}
