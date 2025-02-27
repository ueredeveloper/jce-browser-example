
function initMap() {
	const myLatLng = { lat: -15.800341849307241, lng: -47.742635303776794 };
	const map = new google.maps.Map(document.getElementById("map"), {
		zoom: 10,
		center: myLatLng,
		mapTypeId: 'hybrid'
	});

	new google.maps.Marker({
		position: myLatLng,
		map,
		title: "Hello World!",
	});

	console.log('on load')

	

}

window.initMap = initMap;
