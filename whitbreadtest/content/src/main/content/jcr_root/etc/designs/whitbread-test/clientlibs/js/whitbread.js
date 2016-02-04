var whitbread = whitbread || {};
whitbread.test = whitbread.test || {};

whitbread.test.foursquare = function() {

	var getPopularVenues = function( ) {
        var placeName	=	$('#placeName').val();
        if( placeName ) {
            $.getJSON("/bin/sling/foursquare-json", { "placeName" : placeName},  function( result ){
				console.log(result);
                /*$.each(result, function(i, field){
                    $(".popular-venues").append(field + " ");
                });*/

		    });
        }
        else {
			alert('Please enter a place name');
        }
    };

    return {
        getPopularVenues: getPopularVenues
    }

}();
