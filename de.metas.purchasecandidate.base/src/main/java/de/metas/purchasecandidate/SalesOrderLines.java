package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.purchasecandidate.AvailabilityCheck.AvailabilityResult;
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

@ToString(exclude = { "purchaseCandidateRepository", "salesOrderLine2purchaseCandidates" })
public class SalesOrderLines
{
	private final PurchaseCandidateRepository purchaseCandidateRepository;

	private ExtendedMemorizingSupplier<ImmutableList<SalesOrderLineWithCandidates>> salesOrderLine2purchaseCandidates //
			= ExtendedMemorizingSupplier.of(() -> loadOrCreatePurchaseCandidates0());

	private final ImmutableList<Integer> salesOrderLineIds;

	@Builder
	private SalesOrderLines(
			@NonNull final Collection<Integer> salesOrderLineIds,
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository)
	{
		this.salesOrderLineIds = ImmutableList.copyOf(salesOrderLineIds);
		this.purchaseCandidateRepository = purchaseCandidateRepository;
	}

	private ImmutableList<SalesOrderLineWithCandidates> loadOrCreatePurchaseCandidates0()
	{
		final Map<Integer, I_C_OrderLine> salesOrderLineId2Line = salesOrderLineIds.stream()
				.collect(ImmutableMap.toImmutableMap(
						Function.identity(),
						id -> load(id, I_C_OrderLine.class)));

		final List<Integer> distinctC_Order_IDs = salesOrderLineId2Line.values().stream()
				.map(I_C_OrderLine::getC_Order_ID)
				.distinct().collect(Collectors.toList());
		Check.errorIf(distinctC_Order_IDs.size() > 1,
				"All given salesOrderLineIds' order lines need to belong to the same C_Order; distinctC_Order_IDs={}",
				distinctC_Order_IDs);

		final ImmutableListMultimap<Integer, PurchaseCandidate> existingPurchaseCandidatesBySalesOrderLineId = //
				purchaseCandidateRepository
						.streamAllBySalesOrderLineIds(salesOrderLineIds)
						.collect(GuavaCollectors.toImmutableListMultimap(PurchaseCandidate::getSalesOrderLineId));

		final Set<Integer> vendorProductInfoIdsConsidered = existingPurchaseCandidatesBySalesOrderLineId.values().stream()
				.map(purchaseCandidate -> purchaseCandidate.getVendorProductInfo().getBPartnerProductId())
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMultimap.Builder<I_C_OrderLine, PurchaseCandidate> purchaseCandidatesBySalesOrderLineId = ImmutableMultimap.builder();
		for (int salesOrderLineId : existingPurchaseCandidatesBySalesOrderLineId.keySet())
		{
			purchaseCandidatesBySalesOrderLineId.putAll(
					salesOrderLineId2Line.get(salesOrderLineId),
					existingPurchaseCandidatesBySalesOrderLineId.get(salesOrderLineId));
		}

		for (final I_C_OrderLine salesOrderLine : salesOrderLineId2Line.values())
		{
			final int salesOrderLineId = salesOrderLine.getC_OrderLine_ID();

			final Map<Integer, I_C_BPartner_Product> vendorProductInfosByVendorId = getVendorProductInfosIndexedByVendorId(salesOrderLine);

			final ImmutableList<PurchaseCandidate> newPurchaseCandidateForOrderLine = vendorProductInfosByVendorId.values().stream()
					.filter(vendorProductInfo -> !vendorProductInfoIdsConsidered.contains(vendorProductInfo.getC_BPartner_Product_ID())) // only if vendor was not already considered (i.e. there was no purchase candidate for it)
					.map(vendorProductInfo -> PurchaseCandidate.builder()
							.datePromised(salesOrderLine.getDatePromised())
							.orgId(salesOrderLine.getAD_Org_ID())
							.productId(vendorProductInfo.getM_Product_ID())
							.qtyRequired(salesOrderLine.getQtyEntered())
							.salesOrderId(salesOrderLine.getC_Order_ID())
							.salesOrderLineId(salesOrderLineId)
							.uomId(salesOrderLine.getC_UOM_ID())
							.vendorProductInfo(VendorProductInfo.fromDataRecord(vendorProductInfo))
							.vendorBPartnerId(vendorProductInfo.getC_BPartner_ID())
							.warehouseId(salesOrderLine.getM_Warehouse_ID())
							.build())
					.collect(ImmutableList.toImmutableList());

			purchaseCandidateRepository.saveAll(newPurchaseCandidateForOrderLine);

			purchaseCandidatesBySalesOrderLineId.putAll(salesOrderLineId2Line.get(salesOrderLineId), newPurchaseCandidateForOrderLine);
		}

		final ImmutableList<SalesOrderLineWithCandidates> salesOrderLine2purchaseCandidates = //
				purchaseCandidatesBySalesOrderLineId.build().asMap().entrySet()
						.stream()
						.sorted(Comparator.comparing(entry -> entry.getKey().getLine()))
						.map(entry -> SalesOrderLineWithCandidates.builder()
								.salesOrderLine(entry.getKey())
								.purchaseCandidates(entry.getValue())
								.build())
						.collect(ImmutableList.toImmutableList());

		return salesOrderLine2purchaseCandidates;
	}

	private Map<Integer, I_C_BPartner_Product> getVendorProductInfosIndexedByVendorId(final I_C_OrderLine salesOrderLine)
	{
		final int productId = salesOrderLine.getM_Product_ID();
		final int adOrgId = salesOrderLine.getAD_Org_ID();

		final IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);
		return partnerProductDAO
				.retrieveAllVendors(productId, adOrgId)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(I_C_BPartner_Product::getC_BPartner_ID));
	}

	public List<SalesOrderLineWithCandidates> getOrderedSalesOrderLines()
	{
		return salesOrderLine2purchaseCandidates.get();
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
		final ImmutableList<PurchaseCandidate> allPurchaseCandidates = salesOrderLine2purchaseCandidates.get().stream()
				.flatMap(item -> item.getPurchaseCandidates().stream())
				.collect(ImmutableList.toImmutableList());

		return AvailabilityCheck
				.ofPurchaseCandidates(allPurchaseCandidates);
	}

}
