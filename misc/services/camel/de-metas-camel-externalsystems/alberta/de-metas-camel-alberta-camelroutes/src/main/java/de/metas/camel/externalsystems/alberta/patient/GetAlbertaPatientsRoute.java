/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.alberta.patient;

import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.patient.processor.CreateBPRelationReqProcessor;
import de.metas.camel.externalsystems.alberta.patient.processor.CreateBPartnerReqProcessor;
import de.metas.camel.externalsystems.alberta.patient.processor.PrepareApiClientsProcessor;
import de.metas.camel.externalsystems.alberta.patient.processor.RetrievePatientsProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.alberta.CamelRouteUtil.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetAlbertaPatientsRoute extends RouteBuilder
{
	public static final String GET_PATIENTS_ROUTE_ID = "Alberta-getPatients";
	public static final String PROCESS_PATIENT_ROUTE_ID = "AlbertaPatients-processPatient";
	public static final String IMPORT_BPARTNER_ROUTE_ID = "AlbertaPatients-importBPartner";

	public static final String PREPARE_PATIENTS_API_PROCESSOR_ID = "AlbertaPatients-PreparePatientsApiProcessorId";
	public static final String RETRIEVE_PATIENTS_PROCESSOR_ID = "AlbertaPatients-GetPatientsFromAlbertaProcessorId";
	public static final String CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID = "AlbertaPatients-CreateBPartnerUpsertReqProcessorId";
	public static final String CREATE_UPSERT_BPARTNER_RELATION_REQUEST_PROCESSOR_ID = "Alberta-CreateBPartnerRelationReqProcessorId";
	public static final String IMPORT_BPARTNER_PROCESSOR_ID = "AlbertaPatients-ImportBPartnerProcessorId";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off

		// this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command
		from(direct(GET_PATIENTS_ROUTE_ID))
				.routeId(GET_PATIENTS_ROUTE_ID)
				.streamCaching()
				.process(new PrepareApiClientsProcessor()).id(PREPARE_PATIENTS_API_PROCESSOR_ID)
				.process(new RetrievePatientsProcessor()).id(RETRIEVE_PATIENTS_PROCESSOR_ID)
				.split(body())
					.to(direct(PROCESS_PATIENT_ROUTE_ID))
				.end();

		from(direct(PROCESS_PATIENT_ROUTE_ID))
				.routeId(PROCESS_PATIENT_ROUTE_ID)
				.process(new CreateBPartnerReqProcessor()).id(CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID)

				.split(body())
					.to(direct(IMPORT_BPARTNER_ROUTE_ID))
				.end()

				.process(new CreateBPRelationReqProcessor()).id(CREATE_UPSERT_BPARTNER_RELATION_REQUEST_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPRelations: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPRELATION_CAMEL_URI + "}}")
				.process(this::cleanupResponseItems);

		from(direct(IMPORT_BPARTNER_ROUTE_ID))
				.routeId(IMPORT_BPARTNER_ROUTE_ID)
				.process(this::importBPartner).id(IMPORT_BPARTNER_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartners: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")

				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
				.process(this::gatherBPartnerResponseItems);
		//@formatter:on
	}

	private void importBPartner(@NonNull final Exchange exchange)
	{
		final JsonRequestBPartnerUpsertItem upsertItem = exchange.getIn().getBody(JsonRequestBPartnerUpsertItem.class);

		if (upsertItem == null)
		{
			throw new RuntimeCamelException("Missing exchange body! No JsonRequestBPartnerUpsertItem found!");
		}

		final String orgCode = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE, String.class);

		final JsonRequestBPartnerUpsert bPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItem(upsertItem)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(bPartnerUpsert)
				.orgCode(orgCode)
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}

	private void gatherBPartnerResponseItems(@NonNull final Exchange exchange)
	{

		final JsonResponseBPartnerCompositeUpsert bPartnerUpsertResult = exchange.getIn().getBody(JsonResponseBPartnerCompositeUpsert.class);

		if (bPartnerUpsertResult == null)
		{
			throw new RuntimeCamelException("Missing exchange body! No JsonResponseBPartnerCompositeUpsert found!");
		}

		final GetPatientsRouteContext routeContext = ProcessorHelper
				.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_GET_PATIENTS_CONTEXT, GetPatientsRouteContext.class);

		routeContext.addResponseItems(bPartnerUpsertResult.getResponseItems());
	}

	private void cleanupResponseItems(@NonNull final Exchange exchange)
	{

		final GetPatientsRouteContext routeContext = ProcessorHelper
				.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_GET_PATIENTS_CONTEXT, GetPatientsRouteContext.class);

		routeContext.removeAllResponseItems();
	}
}
