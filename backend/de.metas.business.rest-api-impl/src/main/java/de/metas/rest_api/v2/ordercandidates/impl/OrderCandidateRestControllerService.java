/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.common.ordercandidates.v2.request.JsonOLCandClearRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderInfo;
import de.metas.common.ordercandidates.v2.response.JsonOLCandClearingResponse;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.impex.InputDataSourceId;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandValidationResult;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Check;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderCompositeInfo;
import de.metas.vertical.healthcare.alberta.order.service.AlbertaOrderService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Service
public class OrderCandidateRestControllerService
{
	public static final String DATA_SOURCE_INTERNAL_NAME = "SOURCE." + OrderCandidatesRestControllerImpl.class.getName();

	private final JsonConverters jsonConverters;
	private final OLCandRepository olCandRepo;
	private final PerformanceMonitoringService perfMonService;
	private final AlbertaOrderService albertaOrderService;
	private final OLCandValidatorService olCandValidatorService;

	public OrderCandidateRestControllerService(
			@NonNull final JsonConverters jsonConverters,
			@NonNull final OLCandRepository olCandRepo,
			@NonNull final PerformanceMonitoringService perfMonService,
			@NonNull final AlbertaOrderService albertaOrderService,
			@NonNull final OLCandValidatorService olCandValidatorService)
	{
		this.jsonConverters = jsonConverters;
		this.olCandRepo = olCandRepo;
		this.perfMonService = perfMonService;
		this.albertaOrderService = albertaOrderService;
		this.olCandValidatorService = olCandValidatorService;
	}

	public JsonOLCandCreateBulkResponse creatOrderLineCandidatesBulk(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final PerformanceMonitoringService.SpanMetadata spanMetadata = PerformanceMonitoringService.SpanMetadata.builder()
				.name("CreatOrderLineCandidatesBulk")
				.type(PerformanceMonitoringService.Type.REST_API_PROCESSING.getCode())
				.build();

		return perfMonService.monitorSpan(
				() -> creatOrderLineCandidates0(bulkRequest, masterdataProvider),
				spanMetadata);
	}

	private JsonOLCandCreateBulkResponse creatOrderLineCandidates0(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final HashMap<OLCandQuery, List<OLCand>> query2OLCandList = new HashMap<>();

		final List<OLCand> olCandidates = bulkRequest
				.getRequests()
				.stream()
				.filter(request -> !wasOLCandAlreadyCreated(request, query2OLCandList))
				.map(request -> createOrderLineCandidate(request, masterdataProvider))
				.collect(ImmutableList.toImmutableList());

		return jsonConverters.toJson(olCandidates, masterdataProvider);
	}

	private boolean wasOLCandAlreadyCreated(
			@NonNull final JsonOLCandCreateRequest olCandRequest,
			@NonNull final HashMap<OLCandQuery, List<OLCand>> queryToOLCandList)
	{
		if (Check.isBlank(olCandRequest.getExternalHeaderId())
				|| Check.isBlank(olCandRequest.getExternalLineId()))
		{
			return false;
		}

		final IdentifierString inputDataSourceIdentifier = IdentifierString.of(olCandRequest.getDataSource());

		final OLCandQuery olCandQuery = OLCandQuery.builder()
				.externalHeaderId(olCandRequest.getExternalHeaderId())
				.inputDataSourceName(inputDataSourceIdentifier.asInternalName())
				.build();

		return queryToOLCandList.computeIfAbsent(olCandQuery, olCandRepo::getByQuery)
				.stream()
				.anyMatch(existingOLCand -> olCandRequest.getExternalLineId().equals(existingOLCand.getExternalLineId()));
	}

	private OLCandCreateRequest fromJson(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final String dataSourceInternalNameToUse = coalesce(
				request.getDataSource(),
				"int-" + DATA_SOURCE_INTERNAL_NAME);

		final InputDataSourceId dataSourceId = masterdataProvider.getDataSourceId(
				dataSourceInternalNameToUse,
				masterdataProvider.getOrgId(request.getOrgCode()));
		if (dataSourceId == null)
		{
			throw MissingResourceException.builder()
					.resourceName("dataSource")
					.resourceIdentifier(dataSourceInternalNameToUse)
					.parentResource(request).build();
		}

		return jsonConverters
				.fromJson(request, masterdataProvider)
				.dataSourceId(dataSourceId)
				.build();
	}

	private OLCand createOrderLineCandidate(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{

		assertCanCreate(request, masterdataProvider);

		final OLCandCreateRequest candCreateRequest = fromJson(request, masterdataProvider);
		final OLCand olCand = olCandRepo.create(candCreateRequest);

		if (request.getAlbertaOrderInfo() == null)
		{
			return olCand;
		}

		createAlbertaOrderRecords(request.getOrgCode(), olCand, request.getAlbertaOrderInfo(), masterdataProvider);

		return olCand;
	}

	public void createAlbertaOrderRecords(
			@Nullable final String orgCode,
			@NonNull final OLCand olCand,
			@NonNull final JsonAlbertaOrderInfo jsonAlbertaOrderInfo,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final AlbertaOrderCompositeInfo albertaOrderCompositeInfo = AlbertaOrderCompositeInfo.builder()
				.orgId(OrgId.ofRepoId(olCand.getAD_Org_ID()))
				.olCandId(OLCandId.ofRepoId(olCand.getId()))
				.rootId(jsonAlbertaOrderInfo.getRootId())
				.creationDate(jsonAlbertaOrderInfo.getCreationDate())
				.startDate(jsonAlbertaOrderInfo.getStartDate())
				.endDate(jsonAlbertaOrderInfo.getEndDate())
				.dayOfDelivery(jsonAlbertaOrderInfo.getDayOfDelivery())
				.nextDelivery(jsonAlbertaOrderInfo.getNextDelivery())
				.doctorBPartnerId(resolveExternalBPartnerIdentifier(orgCode, jsonAlbertaOrderInfo.getDoctorBPartnerIdentifier(), masterdataProvider))
				.pharmacyBPartnerId(resolveExternalBPartnerIdentifier(orgCode, jsonAlbertaOrderInfo.getPharmacyBPartnerIdentifier(), masterdataProvider))
				.isInitialCare(jsonAlbertaOrderInfo.getIsInitialCare())
				.isSeriesOrder(jsonAlbertaOrderInfo.getIsSeriesOrder())
				.isArchived(jsonAlbertaOrderInfo.getIsArchived())
				.annotation(jsonAlbertaOrderInfo.getAnnotation())
				.updated(jsonAlbertaOrderInfo.getUpdated())
				.salesLineId(jsonAlbertaOrderInfo.getSalesLineId())
				.unit(jsonAlbertaOrderInfo.getUnit())
				.isPrivateSale(jsonAlbertaOrderInfo.getIsPrivateSale())
				.isRentalEquipment(jsonAlbertaOrderInfo.getIsRentalEquipment())
				.durationAmount(jsonAlbertaOrderInfo.getDurationAmount())
				.timePeriod(jsonAlbertaOrderInfo.getTimePeriod())
				.therapy(jsonAlbertaOrderInfo.getTherapy())
				.therapyTypes(jsonAlbertaOrderInfo.getTherapyTypes())
				.build();

		albertaOrderService.saveAlbertaOrderCompositeInfo(albertaOrderCompositeInfo);
	}

	@NonNull
	public JsonOLCandClearingResponse clearOLCandidates(@NonNull final JsonOLCandClearRequest clearRequest)
	{
		final IdentifierString inputDataSourceIdentifier = IdentifierString.of(clearRequest.getInputDataSourceName());

		final OLCandQuery olCandQuery = OLCandQuery.builder()
				.externalHeaderId(clearRequest.getExternalHeaderId())
				.inputDataSourceName(inputDataSourceIdentifier.asInternalName())
				.build();

		final List<I_C_OLCand> olCands = olCandRepo.getByQuery(olCandQuery)
				.stream()
				.map(OLCand::unbox)
				.collect(ImmutableList.toImmutableList());

		final List<OLCandValidationResult> olCandValidationResults = olCandValidatorService.clearOLCandidates(olCands);

		final boolean finishedWithErrors = olCandValidationResults.stream().anyMatch(result -> !result.isOk());

		final Map<Integer, Boolean> olCandId2ValidationStatus = olCandValidationResults.stream()
				.collect(ImmutableMap.toImmutableMap(result -> result.getOlCandId().getRepoId(), OLCandValidationResult::isOk));

		return JsonOLCandClearingResponse.builder()
				.olCandIdToValidationStatus(olCandId2ValidationStatus)
				.successfullyCleared(!finishedWithErrors)
				.build();
	}

	@Nullable
	private BPartnerId resolveExternalBPartnerIdentifier(
			@Nullable final String orgCode,
			@Nullable final String externalBPartnerIdentifier,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		if (Check.isBlank(externalBPartnerIdentifier))
		{
			return null;
		}

		return BPartnerId.ofRepoId(masterdataProvider
										   .getBPartnerInfoByExternalIdentifier(externalBPartnerIdentifier, orgCode)
										   .getMetasfreshId()
										   .getValue());
	}

	private void assertCanCreate(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getOrgId(request.getOrgCode());
		masterdataProvider.assertCanCreateNewOLCand(orgId);
	}
}
