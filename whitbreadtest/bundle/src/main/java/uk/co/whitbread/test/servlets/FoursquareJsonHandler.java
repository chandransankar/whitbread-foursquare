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
		
		response.getWriter().print( foursquareService.getPopularVenues( request.getParameter(	PLACE_NAME	) ) );
		
		log.debug("returned json ....");
	}	
	
}