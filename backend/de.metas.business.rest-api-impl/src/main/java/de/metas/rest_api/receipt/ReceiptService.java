/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.receipt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.receipt.JsonCreateReceiptInfo;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.ApplyReceiptScheduleChangesRequest;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleExternalInfo;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.rest_api.shipping.AttributeSetHelper;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.create;

@Service
public class ReceiptService
{
	private final AttributeSetHelper attributeSetHelper;

	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public ReceiptService(final AttributeSetHelper attributeSetHelper)
	{
		this.attributeSetHelper = attributeSetHelper;
	}

	public List<InOutId> updateReceiptCandidatesAndGenerateReceipts(@NonNull final JsonCreateReceiptsRequest request)
	{
		final ReceiptScheduleCache cache = new ReceiptScheduleCache(receiptScheduleDAO);

		cache.refreshCacheForIds(extractReceiptScheduleIds(request));

		//1. validate request
		validateRequest(request, cache);

		//2. update I_M_ReceiptSchedules
		updateReceiptSchedules(request, cache);

		//3. generate Receipts
		return generateReceipts(request, cache);
	}

	private List<InOutId> generateReceipts(@NonNull final JsonCreateReceiptsRequest request, @NonNull final ReceiptScheduleCache cache)
	{
		//1. generate missing HUs
		final ImmutableSet<ReceiptScheduleId> receiptScheduleIdsToProcess = extractReceiptScheduleIds(request);

		receiptScheduleIdsToProcess
				.stream()
				.map(cache::getById)
				.map(receiptSchedule -> create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class))
				.forEach(receiptSchedule -> huReceiptScheduleBL.generateHUsIfNeeded(receiptSchedule, Env.getCtx()));

		//2. generate receipts
		final Map<ReceiptScheduleId, ReceiptScheduleExternalInfo> externalInfoByScheduleId = buildExternalInfoByScheduleIdMap(request, cache);
		final List<I_M_ReceiptSchedule> receiptSchedulesToProcess = cache.getByIds(receiptScheduleIdsToProcess);

		final IHUReceiptScheduleBL.CreateReceiptsParameters parameters = IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
				.ctx(Env.getCtx())
				.selectedHuIds(null) // null means it will assign all planned HUs which are assigned to the receipt schedule
				.movementDateRule(ReceiptMovementDateRule.EXTERNAL_DATE_IF_AVAIL) // when creating M_InOuts, use the respective ReceiptScheduleExternalInfo#movementDate if available
				.commitEachReceiptIndividually(false)// if one fails, we want all receipts to fail
				.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
				.receiptSchedules(receiptSchedulesToProcess)
				.printReceiptLabels(false)
				.externalInfoByReceiptScheduleId(externalInfoByScheduleId)
				.build();

		final InOutGenerateResult inOutGenerateResult = huReceiptScheduleBL.processReceiptSchedules(parameters);

		//3. return generated receipt ids
		return inOutGenerateResult.getInOuts()
				.stream()
				.map(I_M_InOut::getM_InOut_ID)
				.map(InOutId::ofRepoId)
				.collect(ImmutableList.toImmutableList());
	}

	private Map<ReceiptScheduleId, ReceiptScheduleExternalInfo> buildExternalInfoByScheduleIdMap(@NonNull final JsonCreateReceiptsRequest request, @NonNull final ReceiptScheduleCache cache)
	{
		return request.getJsonCreateReceiptInfoList()
				.stream()
				.filter(createReceiptInfo -> createReceiptInfo.getDateReceived() != null
						|| createReceiptInfo.getMovementDate() != null
						|| Check.isNotBlank(createReceiptInfo.getExternalId()))
				.collect(Collectors.toMap(this::extractReceiptScheduleId, (createReceiptInfo) -> this.extractExternalInfo(createReceiptInfo, cache)));
	}

	private void updateReceiptSchedules(@NonNull final JsonCreateReceiptsRequest request, @NonNull final ReceiptScheduleCache receiptScheduleCache)
	{
		final ImmutableSet<ReceiptScheduleId> updatedScheduleIds = request.getJsonCreateReceiptInfoList()
				.stream()
				.map(this::toApplyReceiptScheduleChangesRequest)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.peek(receiptScheduleBL::applyReceiptScheduleChanges)
				.map(ApplyReceiptScheduleChangesRequest::getReceiptScheduleId)
				.collect(ImmutableSet.toImmutableSet());

		receiptScheduleCache.refreshCacheForIds(updatedScheduleIds);
	}

	private void validateRequest(@NonNull final JsonCreateReceiptsRequest request, @NonNull final ReceiptScheduleCache receiptScheduleCache)
	{
		if (Check.isEmpty(request.getJsonCreateReceiptInfoList()))
		{
			throw new AdempiereException("JsonCreateReceiptsRequest is empty! ")
					.setParameter("JsonCreateReceiptsRequest", request);
		}

		request.getJsonCreateReceiptInfoList()
				.forEach(jsonCreateReceiptInfo -> validateCreateReceiptInfo(jsonCreateReceiptInfo, receiptScheduleCache));
	}

	private void validateCreateReceiptInfo(@NonNull final JsonCreateReceiptInfo jsonCreateReceiptInfo, @NonNull final ReceiptScheduleCache cache)
	{

		final I_M_ReceiptSchedule receiptSchedule = cache.getById(extractReceiptScheduleId(jsonCreateReceiptInfo));

		if (receiptSchedule.isProcessed())
		{
			throw new AdempiereException("I_M_ReceiptSchedule is already processed!");
		}

		if (Check.isNotBlank(jsonCreateReceiptInfo.getProductSearchKey()))
		{
			final ProductId incomingProductId = productDAO.retrieveProductIdByValue(jsonCreateReceiptInfo.getProductSearchKey());

			if (incomingProductId == null || incomingProductId.getRepoId() != receiptSchedule.getM_Product_ID())
			{
				throw new AdempiereException("Invalid productSearchKey!")
						.appendParametersToMessage()
						.setParameter("productSearchKey", jsonCreateReceiptInfo.getProductSearchKey())
						.setParameter("corresponding productId", incomingProductId)
						.setParameter("receiptSchedule.M_Product_ID", receiptSchedule.getM_Product_ID());
			}
		}
	}

	private ImmutableSet<ReceiptScheduleId> extractReceiptScheduleIds(@NonNull final JsonCreateReceiptsRequest request)
	{
		return request.getJsonCreateReceiptInfoList().stream()
				.map(this::extractReceiptScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ReceiptScheduleId extractReceiptScheduleId(@NonNull final JsonCreateReceiptInfo receiptInfo)
	{
		return ReceiptScheduleId.ofRepoId(receiptInfo.getReceiptScheduleId().getValue());
	}

	private ReceiptScheduleExternalInfo extractExternalInfo(@NonNull final JsonCreateReceiptInfo receiptInfo, @NonNull final ReceiptScheduleCache cache)
	{
		final ReceiptScheduleId receiptScheduleId = extractReceiptScheduleId(receiptInfo);

		final OrgId orgId = cache.getOrgId(receiptScheduleId);
		final ZoneId timeZoneId = orgDAO.getTimeZone(orgId);

		return ReceiptScheduleExternalInfo.builder()
				.externalId(receiptInfo.getExternalId())
				.dateReceived(TimeUtil.asZonedDateTime(receiptInfo.getDateReceived(), timeZoneId))
				.movementDate(receiptInfo.getMovementDate())
				.build();
	}

	@NonNull
	private Optional<ApplyReceiptScheduleChangesRequest> toApplyReceiptScheduleChangesRequest(@NonNull final JsonCreateReceiptInfo createReceiptInfo)
	{
		if (createReceiptInfo.getMovementQuantity() == null && Check.isEmpty(createReceiptInfo.getAttributes()))
		{
			//nothing to update
			return Optional.empty();
		}

		final List<CreateAttributeInstanceReq> createAttributeInstanceReqList = createReceiptInfo.getAttributes() != null
				? attributeSetHelper.toCreateAttributeInstanceReqList(createReceiptInfo.getAttributes())
				: null;

		return Optional.of(
				ApplyReceiptScheduleChangesRequest.builder()
				.receiptScheduleId(extractReceiptScheduleId(createReceiptInfo))
				.qtyInStockingUOM(createReceiptInfo.getMovementQuantity())
				.attributes(createAttributeInstanceReqList)
				.build());
	}

	//
	//
	//utility class
	private static class ReceiptScheduleCache
	{
		private final IReceiptScheduleDAO receiptScheduleDAO;

		private final HashMap<ReceiptScheduleId, I_M_ReceiptSchedule> receiptScheduleHashMap = new HashMap<>();

		private ReceiptScheduleCache(@NonNull final IReceiptScheduleDAO receiptScheduleDAO)
		{
			this.receiptScheduleDAO = receiptScheduleDAO;
		}

		void refreshCacheForIds(@NonNull final ImmutableSet<ReceiptScheduleId> receiptScheduleIds)
		{
			this.receiptScheduleHashMap.putAll(receiptScheduleDAO.getByIds(receiptScheduleIds));
		}

		private I_M_ReceiptSchedule getById(@NonNull final ReceiptScheduleId receiptScheduleId)
		{
			return this.receiptScheduleHashMap.computeIfAbsent(receiptScheduleId, receiptScheduleDAO::getById);
		}

		private List<I_M_ReceiptSchedule> getByIds(@NonNull final ImmutableSet<ReceiptScheduleId> receiptScheduleIds)
		{
			return receiptScheduleIds.stream()
					.map(this::getById)
					.collect(ImmutableList.toImmutableList());
		}

		private OrgId getOrgId(@NonNull final ReceiptScheduleId receiptScheduleId)
		{
			final I_M_ReceiptSchedule receiptSchedule = getById(receiptScheduleId);
			return OrgId.ofRepoId(receiptSchedule.getAD_Org_ID());
		}
	}
}
