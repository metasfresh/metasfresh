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

package de.metas.camel.alberta.patient;

import de.metas.camel.alberta.patient.processor.CreateBPRelationReqProcessor;
import de.metas.camel.alberta.patient.processor.CreateBPartnerReqProcessor;
import de.metas.camel.alberta.patient.processor.CreateESRQueryProcessor;
import de.metas.camel.alberta.patient.processor.PrepareApiClientsProcessor;
import de.metas.camel.alberta.patient.processor.RetrievePatientsProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

import static de.metas.camel.alberta.CamelRouteUtil.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;

@Component
public class GetAlbertaPatientsRoute extends RouteBuilder
{
	public static final String GET_PATIENTS_ROUTE_ID = "Alberta-getPatients";
	public static final String PREPARE_PATIENTS_API_PROCESSOR_ID = "AlbertaPatients-PreparePatientsApiProcessorId";
	public static final String RETRIEVE_PATIENTS_PROCESSOR_ID = "AlbertaPatients-GetPatientsFromAlbertaProcessorId";
	public static final String PROCESS_PATIENT_ROUTE_ID = "AlbertaPatients-processPatient";
	public static final String CREATE_ESR_QUERY_REQ_PROCESSOR_ID = "AlbertaPatients-CreateBPartnerESRQueryProcessorId";
	public static final String CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID = "AlbertaPatients-CreateBPartnerUpsertReqProcessorId";
	public static final String CREATE_UPSERT_BPARTNER_RELATION_REQUEST_PROCESSOR_ID = "Alberta-CreateBPartnerRelationReqProcessorId";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		
		//
		// 1st get the patient data from Alberta
		// this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command
		from(StaticEndpointBuilders.direct(GET_PATIENTS_ROUTE_ID))
				.routeId(GET_PATIENTS_ROUTE_ID)
				.streamCaching()
				.process(new PrepareApiClientsProcessor()).id(PREPARE_PATIENTS_API_PROCESSOR_ID)
				.process(new RetrievePatientsProcessor()).id(RETRIEVE_PATIENTS_PROCESSOR_ID)
				.split(body())
					.to(StaticEndpointBuilders.direct(PROCESS_PATIENT_ROUTE_ID))
				.end();

		from(StaticEndpointBuilders.direct(PROCESS_PATIENT_ROUTE_ID))
				.routeId(PROCESS_PATIENT_ROUTE_ID)
				.process(new CreateESRQueryProcessor()).id(CREATE_ESR_QUERY_REQ_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to query ESR records: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI + "}}")

				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupResponse.class))
				.process(new CreateBPartnerReqProcessor()).id(CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartners: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")

				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
				.process(new CreateBPRelationReqProcessor()).id(CREATE_UPSERT_BPARTNER_RELATION_REQUEST_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPRelations: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPRELATION_CAMEL_URI + "}}");
		//@formatter:on
	}
}
