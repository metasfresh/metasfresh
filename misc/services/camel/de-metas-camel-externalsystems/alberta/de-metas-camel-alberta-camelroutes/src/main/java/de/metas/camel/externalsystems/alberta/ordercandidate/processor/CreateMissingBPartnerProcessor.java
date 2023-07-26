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

package de.metas.camel.externalsystems.alberta.ordercandidate.processor;

import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.alberta.common.DataMapper;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.client.ApiException;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Pharmacy;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Optional;

public class CreateMissingBPartnerProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonExternalReferenceLookupResponse lookupResponse = exchange.getIn().getBody(JsonExternalReferenceLookupResponse.class);

		if (lookupResponse == null)
		{
			throw new RuntimeException("Missing JsonExternalReferenceLookupResponse from exchange!");
		}

		final AlbertaConnectionDetails albertaConnectionDetails = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_CONN_DETAILS, AlbertaConnectionDetails.class);
		final Map<String, Object> externalBPIdentifier2Api = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_EXTERNAL_BP_IDENTIFIER_TO_API, Map.class);
		final String orgCode = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE, String.class);

		final BPUpsertCamelRequest bpUpsertCamelRequest = buildUpsertBPartnerRequest(lookupResponse, externalBPIdentifier2Api, albertaConnectionDetails, orgCode)
				.map(jsonRequest -> BPUpsertCamelRequest.builder()
						.jsonRequestBPartnerUpsert(jsonRequest)
						.orgCode(orgCode)
						.build())
				.orElse(null);

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}

	@NonNull
	private Optional<JsonRequestBPartnerUpsert> buildUpsertBPartnerRequest(
			@NonNull final JsonExternalReferenceLookupResponse lookupResponse,
			@NonNull final Map<String, Object> externalBPIdentifier2Api,
			@NonNull final AlbertaConnectionDetails connectionDetails,
			@NonNull final String orgCode)
	{
		final JsonRequestBPartnerUpsert.JsonRequestBPartnerUpsertBuilder bPartnerUpsertBuilder = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE);

		lookupResponse
				.getItems()
				.stream()
				.filter(externalReferenceItem -> externalReferenceItem.getMetasfreshId() == null)
				.map(JsonExternalReferenceItem::getLookupItem)
				.map(JsonExternalReferenceLookupItem::getId)
				.map(externalBPId -> buildJsonRequestBPartnerUpsertItem(externalBPId, connectionDetails, externalBPIdentifier2Api, orgCode))
				.forEach(bPartnerUpsertBuilder::requestItem);

		final JsonRequestBPartnerUpsert requestBPartnerUpsert = bPartnerUpsertBuilder.build();

		return CollectionUtils.isEmpty(requestBPartnerUpsert.getRequestItems()) ? Optional.empty() : Optional.of(requestBPartnerUpsert);
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem buildJsonRequestBPartnerUpsertItem(
			@NonNull final String externalBPId,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@NonNull final Map<String, Object> externalBPIdentifier2Api,
			@NonNull final String orgCode)
	{
		final Object api = externalBPIdentifier2Api.get(externalBPId);

		if (api == null)
		{
			throw new RuntimeException("No api found for externalBPId:" + externalBPId + ", externalBPIdentifier2Type=" + externalBPIdentifier2Api);
		}

		if (api instanceof DoctorApi)
		{
			return getJsonRequestBPartnerUpsertItemForDoctor(externalBPId, (DoctorApi)api, albertaConnectionDetails, orgCode);
		}
		else if (api instanceof PharmacyApi)
		{
			return getJsonRequestBPartnerUpsertItemForPharmacy(externalBPId, (PharmacyApi)api, albertaConnectionDetails, orgCode);
		}
		else
		{
			throw new RuntimeException("Unsupported api type: " + api);
		}
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem getJsonRequestBPartnerUpsertItemForDoctor(
			@NonNull final String doctorId,
			@NonNull final DoctorApi doctorApi,
			@NonNull final AlbertaConnectionDetails connectionDetails,
			@NonNull final String orgCode)
	{
		try
		{
			final Doctor doctor = doctorApi.getDoctor(connectionDetails.getApiKey(), connectionDetails.getTenant(), doctorId);

			return DataMapper.mapDoctorToUpsertRequest(doctor, orgCode);
		}
		catch (final ApiException e)
		{
			throw new RuntimeException("Unexpected exception when retrieving doctor information", e);
		}
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem getJsonRequestBPartnerUpsertItemForPharmacy(
			@NonNull final String pharmacyId,
			@NonNull final PharmacyApi pharmacyApi,
			@NonNull final AlbertaConnectionDetails connectionDetails,
			@NonNull final String orgCode)
	{
		try
		{
			final Pharmacy pharmacy = pharmacyApi.getPharmacy(connectionDetails.getApiKey(), connectionDetails.getTenant(), pharmacyId);

			return DataMapper.mapPharmacyToUpsertRequest(pharmacy, orgCode);
		}
		catch (final ApiException e)
		{
			throw new RuntimeException("Unexpected exception when retrieving pharmacy information", e);
		}
	}
}
