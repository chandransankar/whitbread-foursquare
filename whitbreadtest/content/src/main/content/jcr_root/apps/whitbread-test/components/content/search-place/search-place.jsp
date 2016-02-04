<%@ page contentType="text/html"
         pageEncoding="utf-8"
         import="javax.jcr.Session" %>

<%@include file="/libs/foundation/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
    <head>
        <title>Whitbread Product Developer Test</title>
        <meta http-equiv="Content-Type" content="text/html; utf-8" />   
        <cq:includeClientLib categories="apps.whitbread-design-libs"/>
    </head>    

    <body>
        <table><tr style="vertical-align:top;"><td width="50%">
        <h1>Whitbread Product Developer Test - Foursquare API Integration</h1>
        <form>
            <table class="form">
                <tr>
                    <td>
                        <label>Place Name</label>
                    </td>
                    <td>
                        <input tupe="text" id="placeName" name="placeName" value="" size="50"/>
                    </td>
                    <td>
                        <input type="button" value="Find" onclick="whitbread.test.foursquare.getPopularVenues();">
                    </td>
                </tr>
            </table>        
        </form>  
        <div class="popular-venues"></div>
    </body>    
</html>