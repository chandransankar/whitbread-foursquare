package uk.co.whitbread.test.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import uk.co.whitbread.test.FoursquareService;
/**
 * Sling servlet to call a OSGI service to interact with Foursquare system 
 * to get the json for a specified place
 * 
 * @author sankarchandran
 *
 */
@Component(immediate = true, metatype = true)
@Service(value = javax.servlet.Servlet.class)
@Properties({
	@Property(name = "service.name", value = "Foursquare JSON Handler Servlet"),
	@Property(name = "sling.servlet.paths", value = "/bin/sling/foursquare-json")
})
public class FoursquareJsonHandler extends SlingAllMethodsServlet {
	
	private static final long serialVersionUID = 1L;
	
	// logger
	private static final Logger log = LoggerFactory.getLogger( FoursquareJsonHandler.class );
	
	private static final String PLACE_NAME	=	"placeName";
	
	@Reference
	FoursquareService	foursquareService;

	@Override
	protected void doGet( SlingHttpServletRequest request, SlingHttpServletResponse response ) throws ServletException, IOException {
		log.debug("Calling FoursquareJsonHandler ....");
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		log.debug("Calling foursquareService ....");
		
		/** The following try catch block throws a json exception
		 * org.json.JSONException: JSONObject["specials"] is not a JSONArray.
		 * looks like this api has some issue
		 */
		try {
		
			FoursquareApi foursquareApi = new FoursquareApi("DY504NG55YKJMUHDKJNYQQFLZYEX1LFFEWILQU1DHMN3SUJS", "QDCEEBQYVCCELRXSCXUXTHGTLYBZ2G55O2JD5I0JIWJRJUQR", "http://localhost:4502/content/whitbread-test.html");
			foursquareApi.setVersion("20140806");
			foursquareApi.setSkipNonExistingFields(true);
			
			response.sendRedirect(foursquareApi.getAuthenticationUrl());
			
			System.out.println( "Auth URL Code	: " + request.getParameter("code"));
			
		    Result<VenuesSearchResult> result = foursquareApi.venuesSearch( request.getParameter(	PLACE_NAME	), null, 50, null, null, null, null, null);
	
		    if (result.getMeta().getCode() == 200) {
		      for (CompactVenue venue : result.getResult().getVenues()) {
		        System.out.println(venue.getName());
		      }
		    } else {
		      System.out.println("Error occured: ");
		      System.out.println("  code: " + result.getMeta().getCode());
		      System.out.println("  type: " + result.getMeta().getErrorType());
		      System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
		    }
	    
		}catch(FoursquareApiException exc) {
			System.out.println("Error occured: " + exc.getMessage());			
		}
		/*** Please see above block to get the venues but still its not working and throws the above mentioned error **/
		
		response.getWriter().print( foursquareService.getPopularVenues( request.getParameter(	PLACE_NAME	) ) );		
		log.debug("returned json ....");
	}	
	
}