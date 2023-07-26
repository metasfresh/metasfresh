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

package de.metas.camel.externalsystems.alberta.institutions.processor;

import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.alberta.common.DataMapper;
import de.metas.camel.externalsystems.alberta.institutions.GetInstitutionsRouteConstants;
import de.metas.camel.externalsystems.alberta.institutions.GetInstitutionsRouteContext;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;

public class PrepareInstitutionToBPartnerProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetInstitutionsRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetInstitutionsRouteConstants.ROUTE_PROPERTY_GET_INSTITUTIONS_CONTEXT, GetInstitutionsRouteContext.class);
		final AlbertaConnectionDetails connectionDetails = routeContext.getAlbertaConnectionDetails();

		final String apiKey = connectionDetails.getApiKey();
		final String tenant = connectionDetails.getTenant();
		final String orgCode = routeContext.getOrgCode();
		
		final JsonBPartnerRole role = routeContext.getRole();
		final String albertaResourceId = routeContext.getAlbertaResourceId();

		final JsonRequestBPartnerUpsertItem upsertItem;

		switch (role)
		{
			case PhysicianDoctor -> {
				final Doctor doctor = routeContext.getDoctorApi().getDoctor(apiKey, tenant, albertaResourceId);
				validateResourceExists(doctor, albertaResourceId, Doctor.class);
				upsertItem = DataMapper.mapDoctorToUpsertRequest(doctor, orgCode);
			}
			case Hospital -> {
				final Hospital hospital = routeContext.getHospitalApi().getHospital(apiKey, tenant, albertaResourceId);
				validateResourceExists(hospital, albertaResourceId, Hospital.class);
				upsertItem = DataMapper.mapHospitalToUpsertRequest(hospital, orgCode);
			}
			case NursingHome -> {
				final NursingHome nursingHome = routeContext.getNursingHomeApi().geNursingHome(apiKey, tenant, albertaResourceId);
				validateResourceExists(nursingHome, albertaResourceId, NursingHome.class);
				upsertItem = DataMapper.mapNursingHomeToUpsertRequest(nursingHome, orgCode);
			}
			case NursingService -> {
				final NursingService nursingService = routeContext.getNursingServiceApi().getNursingService(apiKey, tenant, albertaResourceId);
				validateResourceExists(nursingService, albertaResourceId, NursingService.class);
				upsertItem = DataMapper.mapNursingServiceToUpsertRequest(nursingService, orgCode);
			}
			case Payer -> {
				final Payer payer = routeContext.getPayerApi().getPayer(apiKey, tenant, albertaResourceId);
				validateResourceExists(payer, albertaResourceId, Payer.class);
				upsertItem = DataMapper.mapPayerToUpsertRequest(payer, orgCode);
			}
			case Pharmacy -> {
				final Pharmacy pharmacy = routeContext.getPharmacyApi().getPharmacy(apiKey, tenant, albertaResourceId);
				validateResourceExists(pharmacy, albertaResourceId, Pharmacy.class);
				upsertItem = DataMapper.mapPharmacyToUpsertRequest(pharmacy, orgCode);
			}
			default -> throw new RuntimeCamelException("No bpartner role matched for " + role);
		}

		final JsonRequestBPartnerUpsert bPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItem(upsertItem)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(bPartnerUpsert)
				.orgCode(routeContext.getOrgCode())
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}

	private void validateResourceExists(@Nullable final Object resource, @NonNull final String resourceIdentifier, @NonNull final Class<?> resourceType)
	{
		if (EmptyUtil.isEmpty(resource))
		{
			throw new RuntimeCamelException("The " + resourceType.getSimpleName() + " with identifier=" + resourceIdentifier + " was *not* found on alberta.");
		}
	}
}
