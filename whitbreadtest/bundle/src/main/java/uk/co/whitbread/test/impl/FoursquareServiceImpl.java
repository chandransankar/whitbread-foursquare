package uk.co.whitbread.test.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.whitbread.test.FoursquareService;
/**
 * OSGI Service to interact with Foursquare system to get the json for a specified place
 * @author sankarchandran
 *
 */
@Component(immediate = true, label = "Whitbread Foursquare Service")
@Service
public class FoursquareServiceImpl implements FoursquareService {
	
	// logger
	private static final Logger log = LoggerFactory.getLogger( FoursquareServiceImpl.class );
	
	private static final String FOUR_SQUARE_URL	=	"https://api.foursquare.com/v2/venues/search";
	private static final String CLIENT_ID	=	"DY504NG55YKJMUHDKJNYQQFLZYEX1LFFEWILQU1DHMN3SUJS";
	private static final String CLIENT_SECRET	=	"QDCEEBQYVCCELRXSCXUXTHGTLYBZ2G55O2JD5I0JIWJRJUQR";
	private static final String CLIENT_VERSION	=	"20130815";

	/**
	 * 
	 */
	public String getPopularVenues( String placeName ) {
		String json	=	null ;
		HttpMethod method = null;
		try {
			
	    	HttpClient client = new HttpClient();    	
	    	client.getParams().setParameter(HttpMethodParams.USER_AGENT,"");
	    	client.getParams().setParameter("client_id", CLIENT_ID);
	    	client.getParams().setParameter("client_secret", CLIENT_SECRET);
	    	client.getParams().setParameter("v", CLIENT_VERSION);
	    	client.getParams().setParameter("near", placeName);
	    	client.getParams().setContentCharset("UTF-8");
	    	method = new GetMethod( FOUR_SQUARE_URL );	    		    	
	    	method.setRequestHeader("Content-Type", "text/json; charset=UTF-8");	    	
	    	
    		int statusCode = client.executeMethod( method) ;    		
    		
    		log.debug("statusCode .... {}" , statusCode);
    		
    		if ( statusCode != HttpStatus.SC_OK ) {    			
    			log.debug("Method failed: {}" , method.getStatusLine());
    	    }
    		else {
    			json	=	method.getResponseBodyAsString();
    		}
		}
		catch( Exception exc ){
			log.error( "Exception occured in FoursquareServiceImpl --> getPopularVenues {}" , exc.getMessage() );
		}
		finally {
			if( method!=null ) method.releaseConnection();
		}
		return json;
	}	
}