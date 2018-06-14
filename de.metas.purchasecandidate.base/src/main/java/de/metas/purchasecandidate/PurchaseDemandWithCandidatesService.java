package de.metas.purchasecandidate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.service.OrgId;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidatesGroup.PurchaseCandidatesGroupBuilder;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoRequest;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;
import de.metas.quantity.Quantity;
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
	private final VendorProductInfoService vendorProductInfosRepo;
	private final PurchaseProfitInfoService purchaseProfitInfoService;
	//
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	public PurchaseDemandWithCandidatesService(
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final BPPurchaseScheduleService bpPurchaseScheduleService,
			@NonNull final VendorProductInfoService vendorProductInfosRepo,
			final PurchaseProfitInfoService purchaseProfitInfoService)
	{
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.bpPurchaseScheduleService = bpPurchaseScheduleService;
		this.vendorProductInfosRepo = vendorProductInfosRepo;
		this.purchaseProfitInfoService = purchaseProfitInfoService;
	}

	public List<PurchaseDemandWithCandidates> getOrCreatePurchaseCandidatesGroups(@NonNull final List<PurchaseDemand> demands)
	{
		final Set<PurchaseDemand> demandsToLoad = demands
				.stream()
				.collect(ImmutableSet.toImmutableSet());

		//
		// Get pre-existing purchase candidates to the result
		final ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> //
		existingCandidatesGroups = getExistingPurchaseCandidatesGroups(demandsToLoad);

		//
		// create and add new purchase candidates
		final Set<BPartnerId> alreadySeenVendorIds = extractVendorIds(existingCandidatesGroups.values());
		final ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> //
		newCandidatesGroups = createMissingPurchaseCandidatesGroups(demands, alreadySeenVendorIds);

		//
		// Assemble both Multimaps and preserve demands order
		return demands
				.stream()
				.map(demand -> PurchaseDemandWithCandidates.builder()
						.purchaseDemand(demand)
						.purchaseCandidatesGroups(existingCandidatesGroups.get(demand))
						.purchaseCandidatesGroups(newCandidatesGroups.get(demand))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private static ImmutableSet<BPartnerId> extractVendorIds(final Collection<PurchaseCandidatesGroup> candidatesGroups)
	{
		return candidatesGroups.stream().map(PurchaseCandidatesGroup::getVendorId).collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> getExistingPurchaseCandidatesGroups(
			@NonNull final Collection<PurchaseDemand> demands)
	{
		final ImmutableListMultimap.Builder<PurchaseDemand, PurchaseCandidatesGroup> result = ImmutableListMultimap.builder();

		for (final PurchaseDemand purchaseDemand : demands)
		{
			final List<PurchaseCandidate> candidates = purchaseCandidateRepository.getForDemand(purchaseDemand);

			final ListMultimap<PurchaseCandidatesGroupKey, PurchaseCandidate> candidatesByKey = candidates
					.stream()
					.filter(candidate -> !candidate.isProcessedOrLocked())
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
		LocalDateTime purchaseDatePromised = null;

		final Set<PurchaseCandidateId> purchaseCandidateIds = new LinkedHashSet<>();

		final Set<OrderAndLineId> salesOrderAndLineIds = new LinkedHashSet<>();

		final Set<DemandGroupReference> //
		groupReferences = new TreeSet<>(Comparator.comparing(DemandGroupReference::getDemandReference));

		for (final PurchaseCandidate candidate : candidates)
		{
			qtyToPurchase = Quantity.addNullables(qtyToPurchase, candidate.getQtyToPurchase());
			purchasedQty = Quantity.addNullables(purchasedQty, candidate.getPurchasedQty());
			purchaseDatePromised = TimeUtil.min(purchaseDatePromised, candidate.getPurchaseDatePromised());

			if (candidate.getId() != null)
			{
				purchaseCandidateIds.add(candidate.getId());
			}
			if (candidate.getSalesOrderAndLineId() != null)
			{
				salesOrderAndLineIds.add(candidate.getSalesOrderAndLineId());
			}

			groupReferences.add(candidate.getGroupReference());
		}

		final BPartnerId vendorId = groupKey.getVendorId();
		final ProductId productId = groupKey.getProductId();
		final OrgId orgId = groupKey.getOrgId();
		final VendorProductInfo vendorProductInfo = vendorProductInfosRepo.getVendorProductInfo(vendorId, productId, orgId);

		final PurchaseProfitInfo profitInfo = purchaseProfitInfoService.calculateNoFail(PurchaseProfitInfoRequest.builder()
				.salesOrderAndLineIds(salesOrderAndLineIds)
				.qtyToPurchase(qtyToPurchase)
				.vendorProductInfo(vendorProductInfo)
				.build());

		final PurchaseCandidatesGroupBuilder builder = PurchaseCandidatesGroup.builder()
				.purchaseDemandId(demand.getId())
				.candidateGroupReferences(ImmutableList.copyOf(groupReferences))
				//
				.orgId(orgId)
				.warehouseId(groupKey.getWarehouseId())
				//
				.vendorProductInfo(vendorProductInfo)
				//
				.qtyToPurchase(qtyToPurchase)
				.purchasedQty(purchasedQty)
				//
				.purchaseDatePromised(purchaseDatePromised)
				//
				.profitInfo(profitInfo)
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
	}

	private ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> createMissingPurchaseCandidatesGroups(
			@NonNull final List<PurchaseDemand> demands,
			@NonNull final Set<BPartnerId> vendorIdsToExclude)
	{
		final ImmutableListMultimap.Builder<PurchaseDemand, PurchaseCandidatesGroup> candidatesByDemandId = ImmutableListMultimap.builder();

		for (final PurchaseDemand demand : demands)
		{
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
			@NonNull final Set<BPartnerId> vendorIdsToExclude)
	{
		final Collection<VendorProductInfo> vendorProductInfos = vendorProductInfosRepo.getVendorProductInfos(demand.getProductId(), demand.getOrgId());

		final ImmutableList<PurchaseCandidatesGroup> candidatesGroups = vendorProductInfos.stream()

				// only if vendor was not already considered (i.e. there was no purchase candidate for it)
				.filter(vendorProductInfo -> !vendorIdsToExclude.contains(vendorProductInfo.getVendorId()))

				// create and collect them
				.map(vendorProductInfo -> createNewPurchaseCandidatesGroup(demand, vendorProductInfo))
				.collect(ImmutableList.toImmutableList());

		// TODO: don't save them here!
		// purchaseCandidateRepository.saveAll(candidates);

		return candidatesGroups;
	}

	private PurchaseCandidatesGroup createNewPurchaseCandidatesGroup(
			@NonNull final PurchaseDemand demand,
			@NonNull final VendorProductInfo vendorProductInfo)
	{
		final WarehouseId warehouseId = getPurchaseWarehouseId(demand);
		final LocalDateTime salesDatePromised = demand.getSalesDatePromised();

		//
		// PurchaseDatePromised and ReminderTime
		final BPartnerId vendorId = vendorProductInfo.getVendorId();
		final BPPurchaseSchedule bpPurchaseSchedule = bpPurchaseScheduleService.getBPPurchaseSchedule(
				vendorId,
				salesDatePromised.toLocalDate())
				.orElse(null);
		final LocalDateTime purchaseDatePromised = calculatePurchaseDatePromised(salesDatePromised, bpPurchaseSchedule);
		final Duration reminderTime = bpPurchaseSchedule != null ? bpPurchaseSchedule.getReminderTime() : null;

		//
		// QtyToPurchase=0
		final Quantity qtyToPurchase = Quantity.zero(uomsRepo.getById(demand.getUOMId()));

		//
		// PurchaseProfitInfo
		final PurchaseProfitInfo purchaseProfitInfo = purchaseProfitInfoService.calculateNoFail(PurchaseProfitInfoRequest
				.builder()
				.salesOrderAndLineId(demand.getSalesOrderAndLineId())
				.qtyToPurchase(qtyToPurchase)
				.vendorProductInfo(vendorProductInfo)
				.build());

		//
		// Assemble the PurchaseCandidate
		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.salesOrderAndLineId(demand.getSalesOrderAndLineId()) // the builder works fine with .salesOrderAndLineId(null)
				//
				.purchaseDatePromised(purchaseDatePromised)
				.reminderTime(reminderTime)
				//
				.orgId(demand.getOrgId())
				.warehouseId(warehouseId)
				.vendorId(vendorId)
				.vendorProductNo(vendorProductInfo.getVendorProductNo())
				//
				.productId(vendorProductInfo.getProductId())
				//
				.qtyToPurchase(qtyToPurchase)
				//
				.profitInfo(purchaseProfitInfo)
				//
				.aggregatePOs(vendorProductInfo.isAggregatePOs())
				//
				.build();

		//
		return PurchaseCandidatesGroup.of(demand.getId(), purchaseCandidate, vendorProductInfo);
	}

	private LocalDateTime calculatePurchaseDatePromised(final LocalDateTime salesDatePromised, final BPPurchaseSchedule bpPurchaseSchedule)
	{
		if (bpPurchaseSchedule != null)
		{
			final LocalDateTime purchaseDatePromised = bpPurchaseScheduleService.calculatePurchaseDatePromised(salesDatePromised, bpPurchaseSchedule).orElse(null);
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
		final WarehouseId orgWarehousePOId = warehouseDAO.retrieveOrgWarehousePOId(purchaseDemand.getOrgId());
		if (orgWarehousePOId != null)
		{
			return orgWarehousePOId;
		}

		return purchaseDemand.getWarehouseId();
	}
}
