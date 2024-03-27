package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.pcm.config.PurchaseOrderFileEndpointConfig;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;

public class GetPurchaseOrderFromFileRouteBuilder extends IdAwareRouteBuilder
{
	@NonNull
	private final PurchaseOrderFileEndpointConfig fileEndpointConfig;

	@Getter
	@NonNull
	private final String routeId;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@NonNull
	private final ProcessLogger processLogger;

	@Builder
	private GetPurchaseOrderFromFileRouteBuilder(
			@NonNull final PurchaseOrderFileEndpointConfig fileEndpointConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		super(camelContext);
		this.fileEndpointConfig = fileEndpointConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		//@formatter:off
		from(fileEndpointConfig.getPurchaseOrderFileEndpoint())
				.id(routeId)
				.streamCaching()
				.log("Purchase Order Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.split(body().tokenize("\n"))
					.streaming()
					.process(this::logFileContent)
				.end();
		//@formatter:on
	}

	private void logFileContent(@NonNull final Exchange exchange)
	{
		final PInstanceLogger pInstanceLogger = PInstanceLogger.builder()
				.processLogger(processLogger)
				.pInstanceId(enabledByExternalSystemRequest.getAdPInstanceId())
				.build();

		pInstanceLogger.logMessage(exchange.getIn().getBody(String.class));
	}
}
