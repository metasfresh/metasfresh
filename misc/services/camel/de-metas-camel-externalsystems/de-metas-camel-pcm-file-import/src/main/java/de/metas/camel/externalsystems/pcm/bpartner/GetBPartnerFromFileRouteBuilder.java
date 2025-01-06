package de.metas.camel.externalsystems.pcm.bpartner;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.SkipFirstLinePredicate;
import de.metas.camel.externalsystems.pcm.bpartner.model.BPartnerRow;
import de.metas.camel.externalsystems.pcm.config.BPartnerFileEndpointConfig;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetBPartnerFromFileRouteBuilder extends IdAwareRouteBuilder
{
	@VisibleForTesting
	public static final String UPSERT_BPARTNER_ENDPOINT_ID = "GetBPartnerFromFileRouteBuilder.UPSERT_BPARTNER_ENDPOINT_ID";
	@VisibleForTesting
	public static final String UPSERT_BPARTNER_PROCESSOR_ID = "GetBPartnerFromFileRouteBuilder.UPSERT_BPARTNER_PROCESSOR_ID";

	@NonNull
	private final BPartnerFileEndpointConfig fileEndpointConfig;
	@Getter
	@NonNull
	private final String routeId;
	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;
	@NonNull
	private final PInstanceLogger pInstanceLogger;

	@Builder
	private GetBPartnerFromFileRouteBuilder(
			@NonNull final BPartnerFileEndpointConfig fileEndpointConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		super(camelContext);
		this.fileEndpointConfig = fileEndpointConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.pInstanceLogger = PInstanceLogger.builder()
				.pInstanceId(enabledByExternalSystemRequest.getAdPInstanceId())
				.processLogger(processLogger)
				.build();
	}

	@Override
	public void configure()
	{
		//@formatter:off
		from(fileEndpointConfig.getBPartnerFileEndpoint())
			.id(routeId)
			.streamCaching()
			.log("Business Partner Sync Route Started with Id=" + routeId)
			.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
			.split(body().tokenize("\n"))
				.streaming()
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.filter(new SkipFirstLinePredicate())
				.doTry()
					.unmarshal(new BindyCsvDataFormat(BPartnerRow.class))
					.process(getBPartnerUpsertProcessor()).id(UPSERT_BPARTNER_PROCESSOR_ID)
					.choice()
						.when(bodyAs(BPUpsertCamelRequest.class).isNull())
							.log(LoggingLevel.INFO, "Nothing to do! No bpartner to upsert!")
						.otherwise()
							.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Business Partners: ${body}")
							.to("{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}").id(UPSERT_BPARTNER_ENDPOINT_ID)
						.endChoice()
					.end()
				.endDoTry()
				.doCatch(Throwable.class)
					.to(direct(ERROR_WRITE_TO_ADISSUE))
				.end()
			.end();
		//@formatter:on
	}

	@NonNull
	private BPartnerUpsertProcessor getBPartnerUpsertProcessor()
	{
		return BPartnerUpsertProcessor.builder()
				.externalSystemRequest(enabledByExternalSystemRequest)
				.pInstanceLogger(pInstanceLogger)
				.build();
	}
}
