package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.availability.AvailabilityCheck;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoFactory;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoRequest;
import de.metas.purchasing.api.IBPartnerProductDAO;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

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

@ToString(exclude = { "purchaseCandidateRepository", "salesOrderLineWithCandidates" })
public class SalesOrderLines
{
	// services
	private final PurchaseCandidateRepository purchaseCandidateRepository;
	private final BPPurchaseScheduleService bpPurchaseScheduleService;
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final PurchaseProfitInfoFactory purchaseProfitInfoFactory;
	private final SalesOrderLineRepository salesOrderLineRepository;

	private final ExtendedMemorizingSupplier<ImmutableList<PurchaseDemandWithCandidates>> //
	salesOrderLineWithCandidates = ExtendedMemorizingSupplier.of(this::loadOrCreatePurchaseCandidates0);

	private final ImmutableList<PurchaseDemand> purchaseDemands;

	@Builder
	private SalesOrderLines(
			@NonNull final Collection<PurchaseDemand> purchaseDemands,
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final BPPurchaseScheduleService bpPurchaseScheduleService,
			@NonNull final PurchaseProfitInfoFactory purchaseProfitInfoFactory,
			@NonNull final SalesOrderLineRepository salesOrderLineRepository)
	{
		this.purchaseDemands = ImmutableList.copyOf(purchaseDemands);
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.bpPurchaseScheduleService = bpPurchaseScheduleService;
		this.purchaseProfitInfoFactory = purchaseProfitInfoFactory;
		this.salesOrderLineRepository = salesOrderLineRepository;
	}

	private ImmutableList<PurchaseDemandWithCandidates> loadOrCreatePurchaseCandidates0()
	{
		final ImmutableListMultimap.Builder<PurchaseDemand, PurchaseCandidate> resultBuilder = ImmutableListMultimap.builder();

		final ImmutableMap<PurchaseDemandId, PurchaseDemand> purchaseDemandsById = Maps.uniqueIndex(getPurchaseDemands(), PurchaseDemand::getId);

		// add pre-existing purchase candidates to the result
		final ImmutableListMultimap<PurchaseDemandId, PurchaseCandidate> preExistingPurchaseCandidatesByDemandId = //
				purchaseCandidateRepository
						.streamAllByPurchaseDemandIds(purchaseDemandsById.keySet())
						.collect(GuavaCollectors.toImmutableListMultimap(PurchaseCandidate::getSalesOrderLineIdAsDemandId));

		for (final PurchaseDemandId demandId : preExistingPurchaseCandidatesByDemandId.keySet())
		{
			resultBuilder.putAll(
					purchaseDemandsById.get(demandId),
					preExistingPurchaseCandidatesByDemandId.get(demandId));
		}

		final Set<Integer> alreadySeenVendorProductInfoIds = preExistingPurchaseCandidatesByDemandId.values().stream()
				.filter(Predicates.not(PurchaseCandidate::isProcessed))
				.map(PurchaseCandidate::getBpartnerProductId)
				.filter(OptionalInt::isPresent)
				.map(OptionalInt::getAsInt)
				.collect(ImmutableSet.toImmutableSet());

		// create and add new purchase candidates
		for (final PurchaseDemand purchaseDemand : purchaseDemandsById.values())
		{
			final ImmutableList<PurchaseCandidate> newPurchaseCandidatesForDemand = createMissingPurchaseCandidates(
					purchaseDemand,
					alreadySeenVendorProductInfoIds);

			purchaseCandidateRepository.saveAll(newPurchaseCandidatesForDemand);

			resultBuilder.putAll(purchaseDemand, newPurchaseCandidatesForDemand);
		}

		//
		return resultBuilder.build()
				.asMap()
				.entrySet()
				.stream()
				// .sorted(Comparator.comparing(entry -> entry.getKey().getLine()))
				.map(entry -> PurchaseDemandWithCandidates.builder()
						.purchaseDemand(entry.getKey())
						.purchaseCandidates(entry.getValue())
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private List<PurchaseDemand> getPurchaseDemands()
	{
		return purchaseDemands;
	}
	// private Map<OrderLineId, SalesOrderLine> deriveOrderLineId2OrderLine()
	// {
	// final ImmutableMap<OrderLineId, SalesOrderLine> salesOrderLineId2Line = salesOrderLineIds.stream()
	// .map(salesOrderLineRepository::getById)
	// .collect(ImmutableMap.toImmutableMap(
	// SalesOrderLine::getId,
	// Function.identity()));
	//
	// assertAllLinesHaveSameOrderId(salesOrderLineId2Line);
	//
	// return salesOrderLineId2Line;
	// }

	private void assertAllLinesHaveSameOrderId(@NonNull final Map<OrderLineId, SalesOrderLine> salesOrderLineId2Line)
	{
		final List<OrderId> distinctOrderIds = salesOrderLineId2Line.values().stream()
				.map(SalesOrderLine::getOrderId)
				.distinct().collect(Collectors.toList());

		Check.errorIf(distinctOrderIds.size() > 1,
				"All given salesOrderLineIds' order lines need to belong to the same order; distinct orderIds={}",
				distinctOrderIds);
	}

	private ImmutableList<PurchaseCandidate> createMissingPurchaseCandidates(
			@NonNull final PurchaseDemand purchaseDemand,
			@NonNull final Set<Integer> vendorProductInfoIdsToExclude)
	{
		final Map<BPartnerId, VendorProductInfo> vendorId2VendorProductInfo = retriveVendorProductInfosIndexedByVendorId(purchaseDemand);

		final ImmutableList<PurchaseCandidate> newPurchaseCandidateForOrderLine = vendorId2VendorProductInfo.values().stream()

				// only if vendor was not already considered (i.e. there was no purchase candidate for it)
				.filter(vendorProductInfo -> !vendorProductInfoIdsToExclude.contains(vendorProductInfo.getBpartnerProductId().getAsInt()))

				// create and collect them
				.flatMap(vendorProductInfo -> createPurchaseCandidate(purchaseDemand, vendorProductInfo).stream())
				.collect(ImmutableList.toImmutableList());

		return newPurchaseCandidateForOrderLine;
	}

	private List<PurchaseCandidate> createPurchaseCandidate(
			@NonNull final PurchaseDemand purchaseDemand,
			@NonNull final VendorProductInfo vendorProductInfo)
	{
		final LocalDateTime salesDatePromised = purchaseDemand.getDatePromised();

		LocalDateTime purchaseDatePromised = salesDatePromised;
		Duration reminderTime = null;

		final BPPurchaseSchedule bpPurchaseSchedule = bpPurchaseScheduleService.getBPPurchaseSchedule(
				vendorProductInfo.getVendorBPartnerId(),
				salesDatePromised.toLocalDate()).orElse(null);
		if (bpPurchaseSchedule != null)
		{
			final LocalDateTime calculatedPurchaseDatePromised = bpPurchaseScheduleService.calculatePurchaseDatePromised(salesDatePromised, bpPurchaseSchedule).orElse(null);
			if (calculatedPurchaseDatePromised != null)
			{
				purchaseDatePromised = calculatedPurchaseDatePromised;
			}

			reminderTime = bpPurchaseSchedule.getReminderTime();
		}

		final List<PurchaseProfitInfo> purchaseProfitInfos = purchaseProfitInfoFactory.createInfos(PurchaseProfitInfoRequest.builder()
				.salesOrderLineId(purchaseDemand.getSalesOrderLineId())
				.datePromised(salesDatePromised)
				.productId(purchaseDemand.getProductId())
				.orderedQty(purchaseDemand.getOrderedQty())
				.vendorId(vendorProductInfo.getVendorBPartnerId())
				.paymentTermId(vendorProductInfo.getPaymentTermId())
				.build());

		final ImmutableList.Builder<PurchaseCandidate> result = ImmutableList.builder();
		for (final PurchaseProfitInfo purchaseProfitInfo : purchaseProfitInfos)
		{
			final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
					.dateRequired(purchaseDatePromised)
					.reminderTime(reminderTime)
					.orgId(purchaseDemand.getOrgId())
					.productId(vendorProductInfo.getProductId())
					.qtyToPurchase(BigDecimal.ZERO)
					.salesOrderId(salesOrderLine.getOrderId().getRepoId())
					.salesOrderLineId(OrderLineId.ofRepoId(salesOrderLine.getId().getRepoId()))
					.uomId(purchaseDemand.getUOMId())
					.vendorProductInfo(vendorProductInfo)
					.warehouseId(getPurchaseWarehouseId(purchaseDemand))
					.profitInfo(purchaseProfitInfo)
					.build();
			result.add(purchaseCandidate);
		}
		return result.build();
	}

	private WarehouseId getPurchaseWarehouseId(final PurchaseDemand purchaseDemand)
	{
		final int orgWarehousePOId = warehouseDAO.retrieveOrgWarehousePOId(purchaseDemand.getOrgId().getRepoId());
		if (orgWarehousePOId > 0)
		{
			return WarehouseId.ofRepoId(orgWarehousePOId);
		}

		return purchaseDemand.getWarehouseId();
	}

	private Map<BPartnerId, VendorProductInfo> retriveVendorProductInfosIndexedByVendorId(@NonNull final PurchaseDemand purchaseDemand)
	{
		final int productId = purchaseDemand.getProductId().getRepoId();
		final int adOrgId = purchaseDemand.getOrgId().getRepoId();

		return partnerProductDAO
				.retrieveAllVendors(productId, adOrgId)
				.stream()
				.map(VendorProductInfo::fromDataRecord)
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(VendorProductInfo::getVendorBPartnerId));
	}

	public List<PurchaseDemandWithCandidates> getSalesOrderLinesWithCandidates()
	{
		return salesOrderLineWithCandidates.get();
	}

	public List<PurchaseCandidate> getAllPurchaseCandidates()
	{
		return getSalesOrderLinesWithCandidates()
				.stream()
				.map(PurchaseDemandWithCandidates::getPurchaseCandidates)
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	public Multimap<PurchaseCandidate, AvailabilityResult> checkAvailability()
	{
		return prepareAvailabilityCheck().checkAvailability();
	}

	public void checkAvailabilityAsync(
			@NonNull final BiConsumer<Multimap<PurchaseCandidate, AvailabilityResult>, Throwable> callback)
	{

		final BiConsumer<Multimap<PurchaseCandidate, AvailabilityResult>, Throwable> callbackWrapper = //
				(availabilityCheckResult, throwable) -> callback.accept(
						availabilityCheckResult,
						throwable);

		prepareAvailabilityCheck().checkAvailabilityAsync(callbackWrapper);
	}

	private AvailabilityCheck prepareAvailabilityCheck()
	{
		final List<PurchaseCandidate> allPurchaseCandidates = getAllPurchaseCandidates();
		return AvailabilityCheck.ofPurchaseCandidates(allPurchaseCandidates);
	}

}
