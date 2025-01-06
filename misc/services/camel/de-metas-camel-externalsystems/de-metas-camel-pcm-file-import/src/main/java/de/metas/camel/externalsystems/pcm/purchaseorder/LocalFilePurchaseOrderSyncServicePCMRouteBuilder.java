package de.metas.camel.externalsystems.pcm.purchaseorder;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.pcm.PCMConfigUtil;
import de.metas.camel.externalsystems.pcm.service.OnDemandRoutesPCMController;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.pcm.PCMConstants.PCM_SYSTEM_NAME;
import static de.metas.camel.externalsystems.pcm.service.OnDemandRoutesPCMController.START_HANDLE_ON_DEMAND_ROUTE_ID;
import static de.metas.camel.externalsystems.pcm.service.OnDemandRoutesPCMController.STOP_HANDLE_ON_DEMAND_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
@RequiredArgsConstructor
public class LocalFilePurchaseOrderSyncServicePCMRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	private static final String START_PURCHASE_ORDER_SYNC_LOCAL_FILE_ROUTE = "startPurchaseOrderSyncLocalFile";
	private static final String STOP_PURCHASE_ORDER_SYNC_LOCAL_FILE_ROUTE = "stopPurchaseOrderSyncLocalFile";

	@NonNull
	private final ProcessLogger processLogger;

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(getStartPurchaseOrderRouteId()))
				.routeId(getStartPurchaseOrderRouteId())
				.log("Route invoked")
				.process(this::getStartOnDemandRequest)
				.to(direct(START_HANDLE_ON_DEMAND_ROUTE_ID))
				.end();

		from(direct(getStopPurchaseOrderRouteId()))
				.routeId(getStopPurchaseOrderRouteId())
				.log("Route invoked")
				.process(this::getStopOnDemandRequest)
				.to(direct(STOP_HANDLE_ON_DEMAND_ROUTE_ID))
				.end();
	}

	private void getStartOnDemandRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final OnDemandRoutesPCMController.StartOnDemandRouteRequest startOnDemandRouteRequest = OnDemandRoutesPCMController.StartOnDemandRouteRequest.builder()
				.onDemandRouteBuilder(getPurchaseOrdersFromFileRouteBuilder(request, exchange.getContext()))
				.externalSystemRequest(request)
				.externalSystemService(this)
				.build();

		exchange.getIn().setBody(startOnDemandRouteRequest);
	}

	private void getStopOnDemandRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final OnDemandRoutesPCMController.StopOnDemandRouteRequest stopOnDemandRouteRequest = OnDemandRoutesPCMController.StopOnDemandRouteRequest.builder()
				.routeId(getPurchaseOrdersFromLocalFileRouteId(request))
				.externalSystemRequest(request)
				.externalSystemService(this)
				.build();

		exchange.getIn().setBody(stopOnDemandRouteRequest);
	}

	@NonNull
	private GetPurchaseOrderFromFileRouteBuilder getPurchaseOrdersFromFileRouteBuilder(@NonNull final JsonExternalSystemRequest request, @NonNull final CamelContext camelContext)
	{
		return GetPurchaseOrderFromFileRouteBuilder
				.builder()
				.fileEndpointConfig(PCMConfigUtil.extractLocalFileConfig(request, camelContext))
				.camelContext(camelContext)
				.enabledByExternalSystemRequest(request)
				.processLogger(processLogger)
				.routeId(getPurchaseOrdersFromLocalFileRouteId(request))
				.build();
	}

	@NonNull
	@VisibleForTesting
	public static String getPurchaseOrdersFromLocalFileRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return "GetPurchaseOrderFromLocalFile#" + externalSystemRequest.getExternalSystemChildConfigValue();
	}

	@Override
	public String getServiceValue()
	{
		return "LocalFileSyncPurchaseOrders";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return PCM_SYSTEM_NAME;
	}

	@Override
	public String getEnableCommand()
	{
		return START_PURCHASE_ORDER_SYNC_LOCAL_FILE_ROUTE;
	}

	@Override
	public String getDisableCommand()
	{
		return STOP_PURCHASE_ORDER_SYNC_LOCAL_FILE_ROUTE;
	}

	@NonNull
	public String getStartPurchaseOrderRouteId()
	{
		return getExternalSystemTypeCode() + "-" + getEnableCommand();
	}

	@NonNull
	public String getStopPurchaseOrderRouteId()
	{
		return getExternalSystemTypeCode() + "-" + getDisableCommand();
	}
}