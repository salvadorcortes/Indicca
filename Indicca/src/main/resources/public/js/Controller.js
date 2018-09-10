//Attach a submit handler to the form
$( "#consultaUsuarios" ).submit(function( event ) {
	// Stop form from submitting normally
	event.preventDefault();
	// Get some values from elements on the page:
	var queryParam = $('input[type=text]').map(function() {
		if($( this ).val() == '') return '';
	    return this.id+'='+$( this ).val();
	  })
	  .get()
	  .join( "&" );
//	alert(queryParam);
	$.getJSON( "http://localhost:4567/getUsuarios", queryParam, function( json ) {
		  console.log( "JSON Data: " + json);
		  $("#tablefriendsname").empty();
		  drawTable(json);
		 });
});

function drawTable(data) {
    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

function drawRow(rowData) {
    var row = $("<tr />")
    $("#tablefriendsname").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
    row.append($("<td align='right'>" + rowData.nombre + "</td>"));
    row.append($("<td align='right'>" + ( rowData.apellidoPaterno === undefined ? '' : rowData.apellidoPaterno ) + "</td>"));
    row.append($("<td align='right'>" + ( rowData.apellidoMaterno === undefined ? '' : rowData.apellidoMaterno ) + "</td>"));
}

$( "#login" ).submit(function( event ) {
	// Stop form from submitting normally
	event.preventDefault();
	// Get some values from elements on the page:
	var queryParam = 'idIngreso='+$('input[id=idIngreso]').val()+'&contrasenia='+$('input[id=contrasenia]').val();
//	alert(queryParam);
	$.post( "http://localhost:4567/login", queryParam, function( json ) {
		  console.log( "JSON Data: " + json);
		  if(json.startsWith('/login')){
			  alert('Ingrese sus credenciales nuevamente');
		  }
		  window.location.replace('http://localhost:4567'+json)
		 });
});
