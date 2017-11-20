package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

class PurchaseRowsSaver
{
	private final PurchaseCandidateRepository purchaseCandidatesRepo;

	private final List<PurchaseRow> grouppingRows;

	@Builder
	private PurchaseRowsSaver(
			@NonNull final PurchaseCandidateRepository purchaseCandidatesRepo,
			@NonNull final List<PurchaseRow> grouppingRows)
	{
		this.purchaseCandidatesRepo = purchaseCandidatesRepo;

		this.grouppingRows = grouppingRows;
	}

	public List<PurchaseCandidate> save()
	{
		final Set<Integer> salesOrderLineIds = grouppingRows.stream()
				.map(PurchaseRow::getSalesOrderLineId)
				.filter(id -> id > 0)
				.collect(ImmutableSet.toImmutableSet());

		final Map<Integer, PurchaseCandidate> existingPurchaseCandidatesById = purchaseCandidatesRepo.streamAllBySalesOrderLineIds(salesOrderLineIds)
				.collect(ImmutableMap.toImmutableMap(PurchaseCandidate::getRepoId, Function.identity()));

		final List<PurchaseCandidate> purchaseCandidatesToSave = grouppingRows.stream()
				.flatMap(grouppingRow -> grouppingRow.getIncludedRows().stream()) // purchase candidate lines
				.map(row -> createOrUpdatePurchaseCandidate(row, existingPurchaseCandidatesById))
				.collect(ImmutableList.toImmutableList());

		purchaseCandidatesRepo.saveAll(purchaseCandidatesToSave);

		//
		// Delete remaining candidates:
		final Set<Integer> purchaseCandidateIdsSaved = purchaseCandidatesToSave.stream()
				.map(PurchaseCandidate::getRepoId)
				.collect(ImmutableSet.toImmutableSet());
		final Set<Integer> purchaseCandidateIdsToDelete = existingPurchaseCandidatesById.keySet().stream()
				.filter(id -> !purchaseCandidateIdsSaved.contains(id))
				.collect(ImmutableSet.toImmutableSet());
		purchaseCandidatesRepo.deleteByIds(purchaseCandidateIdsToDelete);
		
		return purchaseCandidatesToSave;
	}

	private PurchaseCandidate createOrUpdatePurchaseCandidate(final PurchaseRow row, final Map<Integer, PurchaseCandidate> existingPurchaseCandidatesById)
	{
		PurchaseCandidate purchaseCandidate = existingPurchaseCandidatesById.get(row.getPurcaseCandidateRepoId());
		if (purchaseCandidate == null)
		{
			purchaseCandidate = PurchaseCandidate.builder()
					.salesOrderId(row.getSalesOrderId())
					.salesOrderLineId(row.getSalesOrderLineId())
					.orgId(row.getOrgId())
					.warehouseId(row.getWarehouseId())
					.productId(row.getProductId())
					.uomId(row.getUOMId())
					.vendorBPartnerId(row.getVendorBPartnerId())
					.qtyRequired(row.getQtyToPurchase())
					.datePromised(row.getDatePromised())
					.build();
		}
		else
		{
			if (purchaseCandidate.isProcessed())
			{
				throw new AdempiereException("Purchase candidate already processed: " + purchaseCandidate);
			}

			purchaseCandidate.setQtyRequired(row.getQtyToPurchase());
			purchaseCandidate.setDatePromised(row.getDatePromised());
		}

		return purchaseCandidate;
	}
}
