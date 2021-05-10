package de.metas.camel.ebay.processor;

import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_EBAY_CLIENT;
import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.Environment;
import com.ebay.api.client.auth.oauth2.model.OAuthResponse;

import de.metas.camel.ebay.EbayConstants;
import de.metas.camel.externalsystems.ebay.api.OrderApi;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.OAuth;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.camel.externalsystems.ebay.api.model.OrderSearchPagedCollection;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;

public class GetEbayOrdersProcessor implements Processor {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		log.debug("Execute ebay order request");
		
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null) {
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			ProcessorHelper.logProcessMessage(exchange, "Ebay:GetOrders process started!" + Instant.now(), request.getAdPInstanceId().getValue());
		}

		final String updatedAfter = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER);

		// prepare api call
		final String authCode = request.getParameters().get(EbayConstants.PARAM_API_AUTH_CODE);
		final String apiMode = request.getParameters().get(EbayConstants.PARAM_API_MODE);

		Environment EXECUTION_ENV;
		if (Environment.PRODUCTION.toString().equalsIgnoreCase(apiMode)) {
			EXECUTION_ENV = Environment.PRODUCTION;
		} else {
			EXECUTION_ENV = Environment.SANDBOX;
		}

		OAuth2Api auth2Api = new OAuth2Api();
		OAuthResponse oauth2Response = auth2Api.exchangeCodeForAccessToken(EXECUTION_ENV, authCode);

		if (oauth2Response.getAccessToken().isPresent()) {

			ApiClient defaultClient = Configuration.getDefaultApiClient();

			defaultClient.setBasePath("https://api.sandbox.ebay.com/sell/fulfillment/v1");

			// Configure OAuth2 access token for authorization: api_auth
			OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
			api_auth.setAccessToken(oauth2Response.getAccessToken().get().getToken());

			final OrderApi api = new OrderApi(defaultClient);

			String fieldGroups = null;
			String filter = "lastmodifieddate:".concat(updatedAfter);
			String limit = "50";
			String offset = null;
			String orderIds = null;
			OrderSearchPagedCollection response = api.getOrders(fieldGroups, filter, limit, offset, orderIds);

			List<Order> orders = response.getOrders();

			exchange.getIn().setBody(orders);
			exchange.setProperty(ROUTE_PROPERTY_ORG_CODE, request.getOrgCode());
			exchange.setProperty(ROUTE_PROPERTY_EBAY_CLIENT, api);
		} else {

			ProcessorHelper.logProcessMessage(exchange, "Ebay:Failed to aquire access token!" + Instant.now(), request.getAdPInstanceId().getValue());
			exchange.getIn().setBody(new ArrayList<Order>());
		}

	}

}
