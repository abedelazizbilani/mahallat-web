myScripts = {
	initDocumentationCharts : function() {
		if ($('#dailySalesChart').length != 0
				&& $('#websiteViewsChart').length != 0) {
			/*
			 * ----------========== Daily Sales Chart initialization For
			 * Documentation ==========----------
			 */

			dataDailySalesChart = {
				labels : [ 'M', 'T', 'W', 'T', 'F', 'S', 'S' ],
				series : [ [ 12, 17, 7, 17, 23, 18, 38 ] ]
			};

			optionsDailySalesChart = {
				lineSmooth : Chartist.Interpolation.cardinal({
					tension : 0
				}),
				low : 0,
				high : 50, // creative tim: we recommend you to set the high sa
				// the biggest value + something for a better look
				chartPadding : {
					top : 0,
					right : 0,
					bottom : 0,
					left : 0
				},
			}

			var dailySalesChart = new Chartist.Line('#dailySalesChart',
					dataDailySalesChart, optionsDailySalesChart);

			var animationHeaderChart = new Chartist.Line('#websiteViewsChart',
					dataDailySalesChart, optionsDailySalesChart);
		}
	},

	initDashboardPageCharts : function() {

		if ($('#dailySalesChart').length != 0
				|| $('#completedTasksChart').length != 0
				|| $('#websiteViewsChart').length != 0) {
			/*
			 * ----------========== Daily Sales Chart initialization
			 * ==========----------
			 */

			dataDailySalesChart = {
				labels : [ 'M', 'T', 'W', 'T', 'F', 'S', 'S' ],
				series : [ [ 12, 17, 7, 17, 23, 18, 38 ] ]
			};

			optionsDailySalesChart = {
				lineSmooth : Chartist.Interpolation.cardinal({
					tension : 0
				}),
				low : 0,
				high : 50, // creative tim: we recommend you to set the high sa
				// the biggest value + something for a better look
				chartPadding : {
					top : 0,
					right : 0,
					bottom : 0,
					left : 0
				},
			}

			var dailySalesChart = new Chartist.Line('#dailySalesChart',
					dataDailySalesChart, optionsDailySalesChart);

			md.startAnimationForLineChart(dailySalesChart);

			/*
			 * ----------========== Completed Tasks Chart initialization
			 * ==========----------
			 */

			dataCompletedTasksChart = {
				labels : [ '12p', '3p', '6p', '9p', '12p', '3a', '6a', '9a' ],
				series : [ [ 230, 750, 450, 300, 280, 240, 200, 190 ] ]
			};

			optionsCompletedTasksChart = {
				lineSmooth : Chartist.Interpolation.cardinal({
					tension : 0
				}),
				low : 0,
				high : 1000, // creative tim: we recommend you to set the
				// high sa the biggest value + something for a
				// better look
				chartPadding : {
					top : 0,
					right : 0,
					bottom : 0,
					left : 0
				}
			}

			var completedTasksChart = new Chartist.Line('#completedTasksChart',
					dataCompletedTasksChart, optionsCompletedTasksChart);

			// start animation for the Completed Tasks Chart - Line Chart
			md.startAnimationForLineChart(completedTasksChart);

			/*
			 * ----------========== Emails Subscription Chart initialization
			 * ==========----------
			 */

			var dataWebsiteViewsChart = {
				labels : [ 'J', 'F', 'M', 'A', 'M', 'J', 'J', 'A', 'S', 'O',
						'N', 'D' ],
				series : [ [ 542, 443, 320, 780, 553, 453, 326, 434, 568, 610,
						756, 895 ]

				]
			};
			var optionsWebsiteViewsChart = {
				axisX : {
					showGrid : false
				},
				low : 0,
				high : 1000,
				chartPadding : {
					top : 0,
					right : 5,
					bottom : 0,
					left : 0
				}
			};
			var responsiveOptions = [ [ 'screen and (max-width: 640px)', {
				seriesBarDistance : 5,
				axisX : {
					labelInterpolationFnc : function(value) {
						return value[0];
					}
				}
			} ] ];
			var websiteViewsChart = Chartist.Bar('#websiteViewsChart',
					dataWebsiteViewsChart, optionsWebsiteViewsChart,
					responsiveOptions);

			// start animation for the Emails Subscription Chart
			md.startAnimationForBarChart(websiteViewsChart);
		}
	},

	initGoogleMaps : function(storeLocation) {
		var myLatlng = new google.maps.LatLng(33.89, 35.48);
		var mapOptions = {
			zoom : 13,
			center : myLatlng,
			scrollwheel : false,
		};
		var marker;
		var map = new google.maps.Map(document.getElementById("map"),
				mapOptions);

		if ((storeLocation !== "undefined")
				&& (!storeLocation instanceof Array)) {
			marker = new google.maps.Marker({
				position : {
					lat : parseFloat(storeLocation.lng),
					lng : parseFloat(storeLocation.lat)
				},
				map : map
			});

			map.addListener('click', function(e) {
				latLng = e.latLng;
				// if marker exists and has a .setMap method, hide it
				if (marker && marker.setMap) {
					marker.setMap(null);
				}
				marker = new google.maps.Marker({
					position : latLng,
					map : map
				});
				setXAndY(e.latLng.lat(), e.latLng.lng())
			});

		}

		if (storeLocation instanceof Array) {
			var i = 0;
			$(storeLocation).each(function(i, location) {
				console.log(storeLocation)
				// create a new marker
				var marker = new google.maps.Marker({
					position : {
						lat : parseFloat(location.lng),
						lng : parseFloat(location.lat),
						title : location.storeName
					},
					map : map
				});
				// assign an info window for each marker
				marker.info = new google.maps.InfoWindow({
					content : "<a href='dashboard/store/"+location.id+"'>"+location.storeName + "</a>"
				});
				// add event listener for each marker	
				marker.addListener("click", function(evt) {
					marker.info.open(map, marker);
				});

				i++
			})
		}
	},
	showNotification : function(from, align) {
		type = [ '', 'info', 'danger', 'success', 'warning', 'rose', 'primary' ];

		color = Math.floor((Math.random() * 6) + 1);

		$
				.notify(
						{
							icon : "add_alert",
							message : "Welcome to <b>Material Dashboard</b> - a beautiful freebie for every web developer."

						}, {
							type : type[color],
							timer : 3000,
							placement : {
								from : from,
								align : align
							}
						});
	},

}

function setXAndY(x, y) {
	$('#longitude').val(x);
	$('#latitude').val(y);

}
