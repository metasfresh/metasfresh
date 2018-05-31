package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.service.OrgId;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoFactory;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoRequest;
import de.metas.purchasing.api.IBPartnerProductDAO;
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
	private final PurchaseProfitInfoFactory purchaseProfitInfoFactory;
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);

	public PurchaseDemandWithCandidatesService(
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final BPPurchaseScheduleService bpPurchaseScheduleService,
			@NonNull final PurchaseProfitInfoFactory purchaseProfitInfoFactory)
	{
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.bpPurchaseScheduleService = bpPurchaseScheduleService;
		this.purchaseProfitInfoFactory = purchaseProfitInfoFactory;
	}

	public List<PurchaseDemandWithCandidates> getOrCreatePurchaseCandidates(final List<PurchaseDemand> purchaseDemands)
	{
		final Set<PurchaseDemandId> demandIdsToLoad = purchaseDemands.stream()
				.map(PurchaseDemand::getId)
				.filter(PurchaseDemandId::isTable)
				.collect(ImmutableSet.toImmutableSet());

		//
		// Get pre-existing purchase candidates to the result
		final ImmutableListMultimap<PurchaseDemandId, PurchaseCandidate> //
		preExistingCandidatesByDemandId = purchaseCandidateRepository.getAllByDemandIds(demandIdsToLoad);

		//
		// create and add new purchase candidates
		final ImmutableSet<VendorProductInfoId> alreadySeenVendorProductInfoIds = extractVendorProductInfoIdsOfNotProcessedCandidates(preExistingCandidatesByDemandId.values());
		final ImmutableListMultimap<PurchaseDemandId, PurchaseCandidate> //
		newCandidatesByDemandId = createMissingPurchaseCandidates(purchaseDemands, alreadySeenVendorProductInfoIds);

		//
		// Assemble both Multimaps and preserve demands order
		return purchaseDemands
				.stream()
				.map(demand -> PurchaseDemandWithCandidates.builder()
						.purchaseDemand(demand)
						.purchaseCandidates(preExistingCandidatesByDemandId.get(demand.getId()))
						.purchaseCandidates(newCandidatesByDemandId.get(demand.getId()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private static ImmutableSet<VendorProductInfoId> extractVendorProductInfoIdsOfNotProcessedCandidates(final Collection<PurchaseCandidate> candidates)
	{
		return candidates.stream()
				.filter(Predicates.not(PurchaseCandidate::isProcessed))
				.map(PurchaseCandidate::getVendorProductInfoId)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableListMultimap<PurchaseDemandId, PurchaseCandidate> createMissingPurchaseCandidates(
			@NonNull final List<PurchaseDemand> demands,
			@NonNull final ImmutableSet<VendorProductInfoId> vendorProductInfoIdsToExclude)
	{
		final ImmutableListMultimap.Builder<PurchaseDemandId, PurchaseCandidate> //
		candidatesByDemandId = ImmutableListMultimap.builder();

		for (final PurchaseDemand demand : demands)
		{
			final ImmutableList<PurchaseCandidate> demandCandidates = createMissingPurchaseCandidates(demand, vendorProductInfoIdsToExclude);
			if (demandCandidates.isEmpty())
			{
				continue;
			}
			candidatesByDemandId.putAll(demand.getId(), demandCandidates);
		}

		return candidatesByDemandId.build();
	}

	private ImmutableList<PurchaseCandidate> createMissingPurchaseCandidates(
			@NonNull final PurchaseDemand demand,
			@NonNull final ImmutableSet<VendorProductInfoId> vendorProductInfoIdsToExclude)
	{
		final Collection<VendorProductInfo> vendorProductInfos = retrieveVendorProductInfos(demand.getProductId(), demand.getOrgId());

		final ImmutableList<PurchaseCandidate> candidates = vendorProductInfos.stream()

				// only if vendor was not already considered (i.e. there was no purchase candidate for it)
				.filter(vendorProductInfo -> !vendorProductInfoIdsToExclude.contains(vendorProductInfo.getId().get()))

				// create and collect them
				.flatMap(vendorProductInfo -> createPurchaseCandidates(demand, vendorProductInfo).stream())
				.collect(ImmutableList.toImmutableList());

		// TODO: don't save them here!
		purchaseCandidateRepository.saveAll(candidates);

		return candidates;
	}

	private List<PurchaseCandidate> createPurchaseCandidates(
			@NonNull final PurchaseDemand purchaseDemand,
			@NonNull final VendorProductInfo vendorProductInfo)
	{
		final LocalDateTime salesDatePromised = purchaseDemand.getDatePromised();

		LocalDateTime purchaseDatePromised = salesDatePromised;
		Duration reminderTime = null;

		final BPPurchaseSchedule bpPurchaseSchedule = bpPurchaseScheduleService.getBPPurchaseSchedule(
				vendorProductInfo.getVendorId(),
				salesDatePromised.toLocalDate())
				.orElse(null);
		if (bpPurchaseSchedule != null)
		{
			final LocalDateTime calculatedPurchaseDatePromised = bpPurchaseScheduleService.calculatePurchaseDatePromised(salesDatePromised, bpPurchaseSchedule).orElse(null);
			if (calculatedPurchaseDatePromised != null)
			{
				purchaseDatePromised = calculatedPurchaseDatePromised;
			}

			reminderTime = bpPurchaseSchedule.getReminderTime();
		}

		final List<PurchaseProfitInfo> purchaseProfitInfos = getPurchaseProfitInfos(purchaseDemand, vendorProductInfo, salesDatePromised);

		final ImmutableList.Builder<PurchaseCandidate> result = ImmutableList.builder();
		for (final PurchaseProfitInfo purchaseProfitInfo : purchaseProfitInfos)
		{
			final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
					.salesOrderAndLineId(purchaseDemand.getSalesOrderAndLineId())
					//
					.dateRequired(purchaseDatePromised)
					.reminderTime(reminderTime)
					//
					.orgId(purchaseDemand.getOrgId())
					.warehouseId(getPurchaseWarehouseId(purchaseDemand))
					//
					.productId(vendorProductInfo.getProductId())
					.uomId(purchaseDemand.getUOMId())
					.vendorProductInfo(vendorProductInfo)
					//
					.qtyToPurchase(BigDecimal.ZERO)
					//
					.profitInfo(purchaseProfitInfo)
					//
					.build();
			result.add(purchaseCandidate);
		}
		return result.build();
	}

	private List<PurchaseProfitInfo> getPurchaseProfitInfos(final PurchaseDemand purchaseDemand, final VendorProductInfo vendorProductInfo, final LocalDateTime salesDatePromised)
	{
		final List<PurchaseProfitInfo> purchaseProfitInfos = purchaseProfitInfoFactory.createInfos(PurchaseProfitInfoRequest.builder()
				.salesOrderLineIds(purchaseDemand.getSalesOrderLineIds())
				.datePromised(salesDatePromised)
				.productId(purchaseDemand.getProductId())
				.orderedQty(purchaseDemand.getQtyToDeliverTotal())
				.vendorId(vendorProductInfo.getVendorId())
				.paymentTermId(vendorProductInfo.getPaymentTermId())
				.build());
		return purchaseProfitInfos;
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

	private Collection<VendorProductInfo> retrieveVendorProductInfos(@NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		return partnerProductDAO
				.retrieveAllVendors(productId, orgId)
				.stream()
				.map(VendorProductInfo::fromDataRecord)
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(VendorProductInfo::getVendorId))
				.values();
	}
}
