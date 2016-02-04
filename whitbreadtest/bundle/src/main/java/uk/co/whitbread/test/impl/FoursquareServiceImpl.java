package uk.co.whitbread.test.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.whitbread.test.FoursquareService;
/**
 * OSGI Service to interact with Foursquare system to get the json for a specified place
 * @author sankarchandran
 *
 */
@Component(immediate = true, metatype = true, label = "Whitbread Foursquare Service")
@Service
@Properties({
    @Property(name = FoursquareServiceImpl.PROP_FOUR_SQUARE_URL, value = FoursquareServiceImpl.PROP_FOUR_SQUARE_URL_DEFAULT ),
	@Property(name = FoursquareServiceImpl.PROP_CLIENT_ID, value = FoursquareServiceImpl.PROP_CLIENT_ID_DEFAULT ),
	@Property(name = FoursquareServiceImpl.PROP_CLIENT_SECRET, value = FoursquareServiceImpl.PROP_CLIENT_SECRET_DEFAULT),
	@Property(name = FoursquareServiceImpl.PROP_CLIENT_VERSION, value = FoursquareServiceImpl.PROP_CLIENT_VERSION_DEFAULT),	
	@Property(name = FoursquareServiceImpl.PROP_REDIRECT_URI, value = FoursquareServiceImpl.PROP_REDIRECT_URI_DEFAULT),
	@Property(name = FoursquareServiceImpl.PROP_AUTHENTICATE_URL, value =FoursquareServiceImpl.PROP_AUTHENTICATE_URL_DEFAULT),
	@Property(name = FoursquareServiceImpl.PROP_ACCESS_TOKEN_URL, value = FoursquareServiceImpl.PROP_ACCESS_TOKEN_URL_DEFAULT)
})
public class FoursquareServiceImpl implements FoursquareService {
	
	// logger
	private static final Logger log = LoggerFactory.getLogger( FoursquareServiceImpl.class );
	
	static final String PROP_FOUR_SQUARE_URL	=	"fourSquareUrl";
	static final String PROP_FOUR_SQUARE_URL_DEFAULT	= "https://api.foursquare.com/v2/venues/search";
	
	static final String PROP_CLIENT_ID	=	"clientId";
	static final String PROP_CLIENT_ID_DEFAULT	=	"DY504NG55YKJMUHDKJNYQQFLZYEX1LFFEWILQU1DHMN3SUJS";
			
	static final String PROP_CLIENT_SECRET	=	"clientSecret";
	static final String PROP_CLIENT_SECRET_DEFAULT	=	"QDCEEBQYVCCELRXSCXUXTHGTLYBZ2G55O2JD5I0JIWJRJUQR";
	
	static final String PROP_CLIENT_VERSION	=	"clientVersion";
	static final String PROP_CLIENT_VERSION_DEFAULT	=	"20160203";
	
	static final String	PROP_REDIRECT_URI	=	"redirectUrl";
	static final String	PROP_REDIRECT_URI_DEFAULT	=	"http://www.sankar-whitbread-test.com";
	
	static final String	PROP_AUTHENTICATE_URL	=	"authenticationUrl";
	static final String	PROP_AUTHENTICATE_URL_DEFAULT	=	"https://foursquare.com/oauth2/authenticate";
	
	static final String	PROP_ACCESS_TOKEN_URL	=	"accessTokenUrl";
	static final String	PROP_ACCESS_TOKEN_URL_DEFAULT	=	"https://foursquare.com/oauth2/access_token";
	
	private static final String	CODE	=	"code";
	private static final String	AUTH_CODE	=	"authorization_code";
	String access_token	=	null;
	
	String fourSquareUrl	=	PROP_FOUR_SQUARE_URL_DEFAULT;
	String clientId	=	PROP_CLIENT_ID_DEFAULT;
	String clientSecret	=	PROP_CLIENT_SECRET_DEFAULT;
	String clientVersion	=	PROP_CLIENT_VERSION_DEFAULT;
	String redirectUrl	=	PROP_REDIRECT_URI_DEFAULT;
	String authenticationUrl	=	PROP_AUTHENTICATE_URL_DEFAULT;
	String accessTokenUrl	=	PROP_ACCESS_TOKEN_URL_DEFAULT;
		
		
	public void activate(ComponentContext context) {
			
			if(context.getProperties().get( PROP_FOUR_SQUARE_URL )!=null){
				fourSquareUrl = (String) context.getProperties().get(PROP_FOUR_SQUARE_URL);
			}
			if(context.getProperties().get( PROP_CLIENT_ID )!=null){
				clientId = (String) context.getProperties().get(PROP_CLIENT_ID);
			}
			
			if(context.getProperties().get( PROP_CLIENT_SECRET )!=null){
				clientSecret = (String) context.getProperties().get(PROP_CLIENT_SECRET);
			}
			if(context.getProperties().get( PROP_CLIENT_VERSION )!=null){
				clientVersion = (String) context.getProperties().get(PROP_CLIENT_VERSION);
			}
			if(context.getProperties().get( PROP_REDIRECT_URI )!=null){
				redirectUrl = (String) context.getProperties().get(PROP_REDIRECT_URI);
			}
			if(context.getProperties().get( PROP_AUTHENTICATE_URL )!=null){
				authenticationUrl = (String) context.getProperties().get(PROP_AUTHENTICATE_URL);
			}
			if(context.getProperties().get( PROP_AUTHENTICATE_URL )!=null){
				accessTokenUrl = (String) context.getProperties().get(PROP_ACCESS_TOKEN_URL);
			}
			
			getAccessToken ();
	}
	
	/**
	 * Authenticates the user and Gets the user access token from Foursqure and keeps it in the access_token
	 * @param context
	 */
	private void getAccessToken () {
		try {			
			// authenticate the user and get the code
			log.debug("In getPopularVenues ....");
			
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setParameter("client_id", this.clientId);
			httpClient.getParams().setParameter("response_type", CODE);
			httpClient.getParams().setParameter("redirect_uri", this.redirectUrl);
			
			PostMethod post = new PostMethod( this.authenticationUrl );
			int statusCode = httpClient.executeMethod(post);
			log.debug("authenticate statusCode .... {}" , statusCode);
			String code	=	post.getResponseBodyAsString();
			
			
			log.debug("authenticate code .... {}" , code);
			
			// get the user access token using the authenticated code
			httpClient = new HttpClient();
			httpClient.getParams().setParameter("client_id", this.clientId);
			httpClient.getParams().setParameter("client_secret", this.clientSecret);
			httpClient.getParams().setParameter("grant_type", AUTH_CODE);
			httpClient.getParams().setParameter("redirect_uri", this.redirectUrl);
			httpClient.getParams().setParameter(CODE, code);

			post = new PostMethod( this.accessTokenUrl );
			statusCode = httpClient.executeMethod(post);
			log.debug("statusCode .... {}" , statusCode);
			this.access_token	=	post.getResponseBodyAsString();		
			log.debug("accessToken .... {}" , this.access_token);
			
		} catch(Exception exc ){
			log.error("Exception occured in FoursquareServiceImpl--> activate {}", exc.getMessage());
			
		}
	}

	/**
	 * Gets the popular venues json for the passed place name
	 */
	public String getPopularVenues( String placeName ) {
		String json	=	null ;
		HttpMethod method = null;
		try {
			// invoke the api to get the popular locations using the place name		
	    	HttpClient client = new HttpClient();    	
	    	client.getParams().setParameter("client_secret", this.clientSecret);
	    	client.getParams().setParameter("oauth_token", this.access_token);
	    	client.getParams().setParameter("near", placeName);
	    	method = new GetMethod( this.fourSquareUrl );	    		    	
	    	method.setRequestHeader("Content-Type", "text/json; charset=UTF-8");	    	
	    	log.debug("executing ....");
    		int statusCode = client.executeMethod( method) ;    		
    		
    		log.debug("statusCode .... {}" , statusCode);
    		
    		if ( statusCode != HttpStatus.SC_OK ) {    			
    			log.debug("Method failed: {}" , method.getStatusLine());
    	    }
    		else {
    			json	=	method.getResponseBodyAsString();
    			log.debug(" got json .. ");
    			log.debug( json );
    		}
		}
		catch( Exception exc ){
			log.debug( "Exception occured in FoursquareServiceImpl --> getPopularVenues {}" , exc.getMessage() );
		}
		finally {
			if( method!=null ) method.releaseConnection();
		}
		return json;
	}	
}