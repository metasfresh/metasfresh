package de.metas.purchasecandidate;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ListMultimap;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidatesGroup.PurchaseCandidatesGroupBuilder;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoRequest;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class PurchaseDemandWithCandidatesService
{
	// services
	private final PurchaseCandidateRepository purchaseCandidateRepository;
	private final BPPurchaseScheduleService bpPurchaseScheduleService;
	private final VendorProductInfoService vendorProductInfoService;
	private final PurchaseProfitInfoService purchaseProfitInfoService;
	//
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	public PurchaseDemandWithCandidatesService(
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final BPPurchaseScheduleService bpPurchaseScheduleService,
			@NonNull final VendorProductInfoService vendorProductInfoService,
			final PurchaseProfitInfoService purchaseProfitInfoService)
	{
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.bpPurchaseScheduleService = bpPurchaseScheduleService;
		this.vendorProductInfoService = vendorProductInfoService;
		this.purchaseProfitInfoService = purchaseProfitInfoService;
	}

	public List<PurchaseDemandWithCandidates> getOrCreatePurchaseCandidatesGroups(@NonNull final List<PurchaseDemand> demands)
	{
		//
		// Get pre-existing purchase candidates to the result
		final ImmutableMultimap<PurchaseDemand, PurchaseCandidatesGroup> //
		existingCandidatesGroups = getExistingPurchaseCandidatesGroups(demands);

		//
		// create and add new purchase candidates
		final Map<PurchaseDemand, Set<BPartnerId>> demand2AlreadySeenVendorIds = extractVendorIds(existingCandidatesGroups);

		final ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> //
		newCandidatesGroups = createMissingPurchaseCandidatesGroups(demands, demand2AlreadySeenVendorIds);

		//
		// Assemble both multimaps and preserve demands order
		return demands
				.stream()
				.map(demand -> PurchaseDemandWithCandidates.builder()
						.purchaseDemand(demand)
						.purchaseCandidatesGroups(existingCandidatesGroups.get(demand))
						.purchaseCandidatesGroups(newCandidatesGroups.get(demand))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private static Map<PurchaseDemand, Set<BPartnerId>> extractVendorIds(
			@NonNull final ImmutableMultimap<PurchaseDemand, PurchaseCandidatesGroup> candidatesGroups)
	{
		final HashMap<PurchaseDemand, Set<BPartnerId>> result = new HashMap<>();

		final ImmutableCollection<Entry<PurchaseDemand, PurchaseCandidatesGroup>> entries = candidatesGroups.entries();
		for (final Entry<PurchaseDemand, PurchaseCandidatesGroup> entry : entries)
		{
			final PurchaseCandidatesGroup purchaseCandidatesGroup = entry.getValue();
			if (purchaseCandidatesGroup.isReadonly())
			{
				// don't count readOnly, because their respective purchase candidates are processed or locked, so the user won't be able to work with them.
				continue;
			}

			final PurchaseDemand purchaseDemand = entry.getKey();
			final BPartnerId vendorId = purchaseCandidatesGroup.getVendorId();

			Set<BPartnerId> set = result.get(purchaseDemand);
			if (set == null)
			{
				set = new HashSet<>();
				result.put(purchaseDemand, set);
			}
			set.add(vendorId);
		}

		return ImmutableMap.copyOf(result);
	}

	@VisibleForTesting
	ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> getExistingPurchaseCandidatesGroups(
			@NonNull final Collection<PurchaseDemand> demands)
	{
		final ImmutableListMultimap.Builder<PurchaseDemand, PurchaseCandidatesGroup> result = ImmutableListMultimap.builder();

		for (final PurchaseDemand purchaseDemand : demands)
		{
			final List<PurchaseCandidate> candidates = purchaseCandidateRepository.getAllByIds(purchaseDemand.getExistingPurchaseCandidateIds());

			final ListMultimap<PurchaseCandidatesGroupKey, PurchaseCandidate> candidatesByKey = candidates
					.stream()
					// .filter(candidate -> !candidate.isProcessedOrLocked()) we still need to seem them for their purchased-qty infos
					.collect(GuavaCollectors.toImmutableListMultimap(this::extractPurchaseCandidatesGroupKey));

			final List<PurchaseCandidatesGroup> candidatesGroups = candidatesByKey
					.asMap()
					.entrySet()
					.stream()
					.map(entry -> createPurchaseCandidatesGroup(purchaseDemand, entry.getKey(), entry.getValue()))
					.collect(ImmutableList.toImmutableList());

			result.putAll(purchaseDemand, candidatesGroups);
		}
		return result.build();
	}

	private PurchaseCandidatesGroup createPurchaseCandidatesGroup(
			@NonNull final PurchaseDemand demand,
			@NonNull final PurchaseCandidatesGroupKey groupKey,
			@NonNull final Collection<PurchaseCandidate> candidates)
	{
		Quantity qtyToPurchase = null;
		Quantity purchasedQty = null;
		ZonedDateTime purchaseDatePromised = null;
		Duration reminderTime = null;

		final Set<PurchaseCandidateId> purchaseCandidateIds = new LinkedHashSet<>();

		final Set<OrderAndLineId> salesOrderAndLineIds = new LinkedHashSet<>();

		final Set<DemandGroupReference> //
		groupReferences = new TreeSet<>(Comparator.comparing(DemandGroupReference::getDemandReference));

		for (final PurchaseCandidate candidate : candidates)
		{
			qtyToPurchase = Quantity.addNullables(qtyToPurchase, candidate.getQtyToPurchase());
			purchasedQty = Quantity.addNullables(purchasedQty, candidate.getPurchasedQty());
			purchaseDatePromised = TimeUtil.min(purchaseDatePromised, candidate.getPurchaseDatePromised());
			reminderTime = TimeUtil.max(reminderTime, candidate.getReminderTime());

			if (candidate.getId() != null)
			{
				purchaseCandidateIds.add(candidate.getId());
			}
			if (candidate.getSalesOrderAndLineIdOrNull() != null)
			{
				salesOrderAndLineIds.add(candidate.getSalesOrderAndLineIdOrNull());
			}

			groupReferences.add(candidate.getGroupReference());
		}

		final BPartnerId vendorId = groupKey.getVendorId();
		final ProductId productId = groupKey.getProductId();
		final OrgId orgId = groupKey.getOrgId();
		final boolean readOnly = groupKey.isReadOnly();

		final VendorProductInfo vendorProductInfo = vendorProductInfoService
				.getVendorProductInfo(vendorId, productId, orgId)
				.assertThatAttributeSetInstanceIdCompatibleWith(demand.getAttributeSetInstanceId());

		final PurchaseProfitInfoRequest purchaseProfitInfoRequest = PurchaseProfitInfoRequest.builder()
				.salesOrderAndLineIds(salesOrderAndLineIds)
				.qtyToPurchase(qtyToPurchase)
				.vendorProductInfo(vendorProductInfo)
				.build();

		final PurchaseProfitInfo profitInfo = purchaseProfitInfoService.calculateNoFail(purchaseProfitInfoRequest);

		final PurchaseCandidatesGroupBuilder builder = PurchaseCandidatesGroup.builder()
				.purchaseDemandId(demand.getId())
				.demandGroupReferences(ImmutableList.copyOf(groupReferences))
				.readonly(readOnly)
				//
				.orgId(orgId)
				.warehouseId(groupKey.getWarehouseId())
				//
				.vendorProductInfo(vendorProductInfo)
				.attributeSetInstanceId(demand.getAttributeSetInstanceId())
				//
				.qtyToPurchase(qtyToPurchase)
				.purchasedQty(purchasedQty)
				//
				.purchaseDatePromised(purchaseDatePromised)
				.reminderTime(reminderTime)
				//
				.profitInfoOrNull(profitInfo)
				//
				.purchaseCandidateIds(purchaseCandidateIds)
				.salesOrderAndLineIds(salesOrderAndLineIds);

		return builder.build();
	}

	private PurchaseCandidatesGroupKey extractPurchaseCandidatesGroupKey(final PurchaseCandidate candidate)
	{
		return PurchaseCandidatesGroupKey.builder()
				.orgId(candidate.getOrgId())
				.warehouseId(candidate.getWarehouseId())
				.vendorId(candidate.getVendorId())
				.productId(candidate.getProductId())
				.readOnly(candidate.isProcessedOrLocked())
				.build();
	}

	@lombok.Value
	@lombok.Builder
	private static final class PurchaseCandidatesGroupKey
	{
		OrgId orgId;
		WarehouseId warehouseId;
		BPartnerId vendorId;
		ProductId productId;
		boolean readOnly;
	}

	@VisibleForTesting
	ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> createMissingPurchaseCandidatesGroups(
			@NonNull final List<PurchaseDemand> demands,
			@NonNull final Map<PurchaseDemand, Set<BPartnerId>> demand2vendorIdsToExclude)
	{
		final ImmutableListMultimap.Builder<PurchaseDemand, PurchaseCandidatesGroup> candidatesByDemandId = ImmutableListMultimap.builder();

		for (final PurchaseDemand demand : demands)
		{
			final Set<BPartnerId> vendorIdsToExclude = demand2vendorIdsToExclude.get(demand);

			final List<PurchaseCandidatesGroup> demandCandidates = createMissingPurchaseCandidatesGroups(demand, vendorIdsToExclude);
			if (demandCandidates.isEmpty())
			{
				continue;
			}
			candidatesByDemandId.putAll(demand, demandCandidates);
		}

		return candidatesByDemandId.build();
	}

	private ImmutableList<PurchaseCandidatesGroup> createMissingPurchaseCandidatesGroups(
			@NonNull final PurchaseDemand demand,
			@Nullable final Set<BPartnerId> vendorIdsToExclude)
	{
		final Collection<VendorProductInfo> vendorProductInfos = vendorProductInfoService.getVendorProductInfos(demand.getProductId(), demand.getOrgId());

		final ImmutableList<PurchaseCandidatesGroup> candidatesGroups = vendorProductInfos.stream()

				// only if vendor was not already considered (i.e. there was no purchase candidate for it)
				.filter(vendorProductInfo -> vendorIdsToExclude == null || !vendorIdsToExclude.contains(vendorProductInfo.getVendorId()))

				// create and collect them
				.map(vendorProductInfo -> createNewPurchaseCandidatesGroup(demand, vendorProductInfo))
				.collect(ImmutableList.toImmutableList());

		return candidatesGroups;
	}

	private PurchaseCandidatesGroup createNewPurchaseCandidatesGroup(
			@NonNull final PurchaseDemand purchaseDemand,
			@NonNull final VendorProductInfo vendorProductInfo)
	{
		Check.errorUnless(Objects.equals(purchaseDemand.getProductId(), vendorProductInfo.getProductId()),
				"The given purchaseDemand and vendorProductInfo have different productIds; purchaseDemand={}; vendorProductInfo={}",
				purchaseDemand, vendorProductInfo);

		Check.errorUnless(
				Objects.equals(vendorProductInfo.getAttributeSetInstanceId(), AttributeSetInstanceId.NONE) // if vendorProductInfo has no ASI, then it's also fine.
						|| Objects.equals(purchaseDemand.getAttributeSetInstanceId(), vendorProductInfo.getAttributeSetInstanceId()),
				"The given purchaseDemand and vendorProductInfo have different attributeSetInstanceIds; purchaseDemand={}; vendorProductInfo={}",
				purchaseDemand, vendorProductInfo);

		final WarehouseId warehouseId = getPurchaseWarehouseId(purchaseDemand);
		final ZonedDateTime salesPreparationDate = purchaseDemand.getSalesPreparationDate();

		//
		// PurchaseDatePromised and ReminderTime
		final BPartnerId vendorId = vendorProductInfo.getVendorId();
		final BPPurchaseSchedule bpPurchaseSchedule = bpPurchaseScheduleService.getBPPurchaseSchedule(
				vendorId,
				salesPreparationDate.toLocalDate())
				.orElse(null);
		final ZonedDateTime purchaseDatePromised = calculatePurchaseDatePromised(salesPreparationDate, bpPurchaseSchedule);
		final Duration reminderTime = bpPurchaseSchedule != null ? bpPurchaseSchedule.getReminderTime() : null;

		//
		// QtyToPurchase=0
		final Quantity qtyToPurchase = Quantity.zero(uomsRepo.getById(purchaseDemand.getUOMId()));

		//
		// PurchaseProfitInfo
		final PurchaseProfitInfo purchaseProfitInfo = purchaseProfitInfoService.calculateNoFail(PurchaseProfitInfoRequest
				.builder()
				.salesOrderAndLineId(purchaseDemand.getSalesOrderAndLineIdOrNull()) // the builder works with .salesOrderAndLineId(null)
				.qtyToPurchase(qtyToPurchase)
				.vendorProductInfo(vendorProductInfo)
				.build());

		//
		// Assemble the PurchaseCandidate
		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.salesOrderAndLineIdOrNull(purchaseDemand.getSalesOrderAndLineIdOrNull())
				.groupReference(DemandGroupReference.EMPTY)
				//
				.purchaseDatePromised(purchaseDatePromised)
				.reminderTime(reminderTime)
				//
				.orgId(purchaseDemand.getOrgId())
				.warehouseId(warehouseId)
				.vendorId(vendorId)
				.vendorProductNo(vendorProductInfo.getVendorProductNo())
				//
				.productId(purchaseDemand.getProductId())
				.attributeSetInstanceId(purchaseDemand.getAttributeSetInstanceId())
				//
				.qtyToPurchase(qtyToPurchase)
				//
				.profitInfoOrNull(purchaseProfitInfo)
				//
				.aggregatePOs(vendorProductInfo.isAggregatePOs())
				//
				.build();

		//
		return PurchaseCandidatesGroup.of(purchaseDemand.getId(), purchaseCandidate, vendorProductInfo);
	}

	private ZonedDateTime calculatePurchaseDatePromised(
			@NonNull final ZonedDateTime salesDatePromised,
			@Nullable final BPPurchaseSchedule bpPurchaseSchedule)
	{
		if (bpPurchaseSchedule != null)
		{
			final ZonedDateTime purchaseDatePromised = bpPurchaseScheduleService.calculatePurchaseDatePromised(salesDatePromised, bpPurchaseSchedule).orElse(null);
			if (purchaseDatePromised != null)
			{
				return purchaseDatePromised;
			}
		}

		// fallback
		return salesDatePromised;
	}

	private WarehouseId getPurchaseWarehouseId(final PurchaseDemand purchaseDemand)
	{
		final WarehouseId orgWarehousePOId = orgsRepo.getOrgPOWarehouseId(purchaseDemand.getOrgId());
		if (orgWarehousePOId != null)
		{
			return orgWarehousePOId;
		}
		return purchaseDemand.getWarehouseId();
	}
}
