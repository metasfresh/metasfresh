package de.metas.purchasecandidate.material.event;

import java.util.Collection;

import org.adempiere.service.OrgId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.order.OrderAndLineId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
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
public class PurchaseCandidateRequestedHandler implements MaterialEventHandler<PurchaseCandidateRequestedEvent>
{
	private final ProductRepository productRepository;
	private final PurchaseCandidateRepository purchaseCandidateRepository;
	private final PostMaterialEventService postMaterialEventService;

	public static final ThreadLocal<Boolean> INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD = ThreadLocal.withInitial(() -> false);

	public PurchaseCandidateRequestedHandler(
			@NonNull final ProductRepository productRepository,
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final PostMaterialEventService postMaterialEventService)
	{
		this.productRepository = productRepository;
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.postMaterialEventService = postMaterialEventService;
	}

	@Override
	public Collection<Class<? extends PurchaseCandidateRequestedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PurchaseCandidateRequestedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PurchaseCandidateRequestedEvent event)
	{
		final MaterialDescriptor materialDescriptor = event.getPurchaseMaterialDescriptor();

		final OrderAndLineId orderandLineIdOrNull = OrderAndLineId.ofRepoIdsOrNull(
				event.getSalesOrderRepoId(),
				event.getSalesOrderLineRepoId());

		final Product product = productRepository.getById(ProductId.ofRepoId(materialDescriptor.getProductId()));

		final PurchaseCandidate newPurchaseCandidate = PurchaseCandidate.builder()
				.dateRequired(TimeUtil.asLocalDateTime(materialDescriptor.getDate()))
				.orgId(OrgId.ofRepoId(event.getEventDescriptor().getOrgId()))
				.processed(false)
				.productId(product.getId())
				// .profitInfo(profitInfo)
				// .purchaseItem(purchaseItem) purchase items are only returned by the vendor gateway
				.qtyToPurchase(materialDescriptor.getQuantity())
				.salesOrderAndLineId(orderandLineIdOrNull)
				// .salesOrderQtyToDeliver() leave it empty..if there is a sales order involved, the value will be set when thge candidate is loaded next time
				.uomId(product.getUomId().getRepoId())
				.warehouseId(WarehouseId.ofRepoId(materialDescriptor.getWarehouseId()))
				.build();

		saveCandidateAndPostCreatedEvent(event, newPurchaseCandidate);
	}

	private void saveCandidateAndPostCreatedEvent(
			@NonNull final PurchaseCandidateRequestedEvent requestedEvent,
			@NonNull final PurchaseCandidate newPurchaseCandidate)
	{
		try
		{
			INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD.set(false);

			final PurchaseCandidateId newPurchaseCandidateId = purchaseCandidateRepository.save(newPurchaseCandidate);

			final PurchaseCandidateCreatedEvent purchaseCandidateCreatedEvent = PurchaseCandidateCreatedEvent.builder()
					.purchaseCandidateRepoId(newPurchaseCandidateId.getRepoId())
					.purchaseMaterialDescriptor(requestedEvent.getPurchaseMaterialDescriptor())
					.supplyCandidateRepoId(requestedEvent.getSupplyCandidateRepoId())
					.build();
			postMaterialEventService.postEventAfterNextCommit(purchaseCandidateCreatedEvent);
		}
		finally
		{
			INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD.set(true);
		}
	}

}
