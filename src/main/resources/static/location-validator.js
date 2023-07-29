let stores;
function loadStores() {
	let selectedCityId = $('#cityDropdown').val();

	if (selectedCityId) {
		$.ajax({
			url: '/brand-location/api/getByCityId',
			type: 'GET',
			data: { cityId: selectedCityId },
			success: function(data) {
				var storeDropdown = $('#storeDropdown');
				storeDropdown.empty();

				// Add stores to the dropdown
				$.each(data, function(index, store) {
					storeDropdown.append($('<option></option>').attr('value', store.locationId).text(store.locationName));
				});

				// Show the store dropdown
				$('#storeDropdownContainer').show();

				stores = data;

				// Display stores on the map
				displayStoresOnMap();
			},
			error: function() {
				alert('Error occurred while loading stores');
			}
		});
	} else {
		$('#storeDropdownContainer').hide();
	}
}

function displayStoresOnMap() {
	// Check if a store is selected
	let selectedStoreId = $('#storeDropdown').val();
	let selectedStore = null;

	selectedStore = stores.find(function(store) {
		return store.locationId === Number(selectedStoreId);
	});

	// Set the initial coordinates based on the selected store
	let initialCoordinates = {
		lat: selectedStore.latitude,
		lng: selectedStore.longitude
	};

	// Create a new Google Map instance
	let map = new google.maps.Map(document.getElementById('map'), {
		zoom: 10, // Set the initial zoom level
		center: initialCoordinates // Set the initial center coordinates
	});

	// Create markers for each store and add them to the map
	$.each(stores, function(index, store) {
		let position = { lat: store.latitude, lng: store.longitude };

		marker = new google.maps.Marker({
			position: position,
			map: map,
			title: store.locationName
		});

		// Add click event listener to the marker
		marker.addListener('click', function() {
			// Show the confirmation dialog using SweetAlert
			Swal.fire({
				title: 'Confirmation',
				text: 'Do you want to mark this location as invalid?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonText: 'Yes',
				cancelButtonText: 'No'
			}).then((result) => {
				if (result.isConfirmed) {
					// Call the API to mark the location as invalid
					markLocationAsInvalid(store.locationId);
				}
			});
		});
	});


	function markLocationAsInvalid(locationId) {
		// Perform an API call to mark the location as invalid based on its ID
		$.ajax({
			url: '/brand-location/api/invalid',
			type: 'GET',
			data: { locationId: locationId },
			success: function(response) {
				// Handle the success response
				Swal.fire('Success', response, 'success');
			},
			error: function() {
				// Handle the error response
				Swal.fire('Error', 'An error occurred while marking the location as invalid', 'error');
			}
		});
	}

}