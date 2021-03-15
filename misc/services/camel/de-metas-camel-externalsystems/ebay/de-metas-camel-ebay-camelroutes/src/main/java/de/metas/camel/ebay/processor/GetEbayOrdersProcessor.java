package de.metas.camel.ebay.processor;

import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_EBAY_CLIENT;
import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;

import java.time.Instant;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.api.OrderApi;
import io.swagger.client.model.Order;
import io.swagger.client.model.OrderSearchPagedCollection;

public class GetEbayOrdersProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());

			ProcessorHelper.logProcessMessage(exchange, "Shopware6:GetOrders process started!" +  Instant.now(), request.getAdPInstanceId().getValue() );
		}

		final String clientId = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_ID);
		final String clientSecret = request.getParameters().get(ExternalSystemConstants.PARAM_CLIENT_SECRET);

		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final String updatedAfter = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER);

		
		final OrderApi api = new OrderApi();
		String fieldGroups = null;
	    String filter = null;
	    String limit = "20";
	    String offset = null;
	    String orderIds = null;
	    OrderSearchPagedCollection response = api.getOrders(fieldGroups, filter, limit, offset, orderIds);
		
	    List<Order> orders = response.getOrders();

		exchange.getIn().setBody(orders);
		exchange.setProperty(ROUTE_PROPERTY_ORG_CODE, request.getOrgCode());
		exchange.setProperty(ROUTE_PROPERTY_EBAY_CLIENT, api);
	}

}
