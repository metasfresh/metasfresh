package de.metas.camel.ebay;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.ebay.api.client.auth.oauth2.CredentialUtil;
import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.Environment;
import com.ebay.api.client.auth.oauth2.model.OAuthResponse;

import io.swagger.client.ApiClient;
import io.swagger.client.Configuration;
import io.swagger.client.api.OrderApi;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.OrderSearchPagedCollection;

public class EbayApiTest {
	
	private static final Environment EXECUTION_ENV = Environment.SANDBOX;
	
	
	@Test
    public void testConfigLoadYamlFile() {
//        if (!CredentialLoaderTestUtil.isAppCredentialsLoaded) {
//            System.err.println("\"Please check if ebay-config.yaml is setup correctly for app credentials");
//            return;
//        }

        String credentialHelperStr = CredentialUtil.dump();
//        assertTrue(credentialHelperStr.contains("APP_ID"));
//        assertTrue(credentialHelperStr.contains("DEV_ID"));
//        assertTrue(credentialHelperStr.contains("CERT_ID"));
//        assertTrue(credentialHelperStr.contains("REDIRECT_URI"));
    }
	
	
	@Test
	public void testMock() throws Exception {
		
		
		//https://api.ebay.com/oauth/api_scope/sell.fulfillment
		
		CredentialUtil.load(new FileInputStream("src/test/resources/ebay-test-creds.yaml"));
		
		OAuth2Api oauth2Api = new OAuth2Api();
		
		List<String> scopes = new ArrayList<>();
		scopes.add("https://api.ebay.com/oauth/api_scope/sell.fulfillment");
		
		OAuthResponse oauth2Response = oauth2Api.getApplicationToken(Environment.SANDBOX, scopes);
		
		

		if(oauth2Response.getRefreshToken().isPresent()) {
			ApiClient defaultClient = Configuration.getDefaultApiClient();

	        // Configure OAuth2 access token for authorization: api_auth
	        OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
	        api_auth.setAccessToken(oauth2Response.getRefreshToken().get().getToken());
	       
			
			final OrderApi api = new OrderApi();
			String fieldGroups = null;
		    String filter = null;
		    String limit = "20";
		    String offset = null;
		    String orderIds = null;
		    OrderSearchPagedCollection response = api.getOrders(fieldGroups, filter, limit, offset, orderIds);
		    
		    
		
		    
		    Assert.assertNotNull(response);
		} else {
			
			
			
		}
		
		
		
	

	}

}
