var whitbread = whitbread || {};
whitbread.test = whitbread.test || {};

whitbread.test.foursquare = function() {
    var FS_URL			=	"https://api.foursquare.com/v2/venues/search";
    var CLIENT_ID		=	"DY504NG55YKJMUHDKJNYQQFLZYEX1LFFEWILQU1DHMN3SUJS";
    var CLIENT_SECRET	=	"QDCEEBQYVCCELRXSCXUXTHGTLYBZ2G55O2JD5I0JIWJRJUQR";
    var FS_VERSION		= 	"20160203";

	var getPopularVenues = function( ) {
        var placeName	=	$('#placeName').val();
        if( placeName ) {
            $(".popular-venues").html('');

			// javascript based call to Foursquare api
            $.getJSON( FS_URL + '?near=' + placeName + '&client_id=' + CLIENT_ID + '&client_secret=' + CLIENT_SECRET + '&v=' + FS_VERSION,
                function(data) {
                   var cnt = 1;
                   $.each(data.response.venues, function(i,venues) {
                        console.log(venues);

                       var address	= '';
                       if(venues.location.address ) { address += venues.location.address; };
                       if(venues.location.city ) {address += ', ' + venues.location.city; };
                       if(venues.location.postalCode ) {address += ', ' + venues.location.postalCode; };

	                   $(".popular-venues").append( '<p><b>' + cnt + '. ' + venues.name + '  [ ' + address + ' ]' + '</b></p>' );

                        cnt = cnt + 1;
                   });
            });

            /* Call a sling servlet which will make a request to Foursquare api to get the json

            $.getJSON("/bin/sling/foursquare-json", { "placeName" : placeName},  function( data ) {
				var cnt = 1;
                   $.each(data.response.venues, function(i,venues) {
                        console.log(venues);

                       var address	= '';
                       if(venues.location.address ) { address += venues.location.address; };
                       if(venues.location.city ) {address += ', ' + venues.location.city; };
                       if(venues.location.postalCode ) {address += ', ' + venues.location.postalCode; };

	                   $(".popular-venues").append( '<p><b>' + cnt + '. ' + venues.name + '  [ ' + address + ' ]' + '</b></p>' );

                        cnt = cnt + 1;
                   });
		    });
            */
        }
        else {
			alert('Please enter a place name');
        }
    };

    return {
        getPopularVenues: getPopularVenues
    }

}();
