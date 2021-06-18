package de.metas.camel.externalsystems.alberta.ordercandidate.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.ordercandidate.GetOrdersRouteConstants;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonRuntimeParameterUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER;

public class RuntimeParametersProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final String updatedAfter =
				ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_UPDATED_AFTER, String.class);
		final String command =
				ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_COMMAND, String.class);
		final String externalSystemConfigId =
				ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_EXTERNAL_SYSTEM_CONFIG_ID, String.class);

		final JsonRuntimeParameterUpsertItem runtimeParameterUpsertItem = JsonRuntimeParameterUpsertItem.builder()
				.externalSystemParentConfigId(JsonMetasfreshId.of(Integer.valueOf(externalSystemConfigId)))
				.request(command)
				.name(PARAM_UPDATED_AFTER)
				.value(updatedAfter)
				.build();

		final JsonESRuntimeParameterUpsertRequest request = JsonESRuntimeParameterUpsertRequest.builder()
				.runtimeParameterUpsertItems(ImmutableList.of(runtimeParameterUpsertItem))
				.build();

		exchange.getIn().setBody(request);
	}
}
