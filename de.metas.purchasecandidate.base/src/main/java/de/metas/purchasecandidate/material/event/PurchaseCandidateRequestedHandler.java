package de.metas.purchasecandidate.material.event;

import java.util.Collection;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
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
import de.metas.purchasecandidate.VendorProductInfo;
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
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with PurchaseCandidateRequestedEvent
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

		final VendorProductInfo vendorProductInfo = quickAndDirtyCreateVendorProductInfo(event);

		final PurchaseCandidate newPurchaseCandidate = PurchaseCandidate.builder()
				.vendorProductInfo(vendorProductInfo)
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

	// TODO remove as vendorProduct is removed from PurchaseCandidate
	private VendorProductInfo quickAndDirtyCreateVendorProductInfo(@NonNull final PurchaseCandidateRequestedEvent event)
	{
		final MaterialDescriptor materialDescriptor = event.getPurchaseMaterialDescriptor();

		final int vendorId;
		final int productId = materialDescriptor.getProductId();

		if (event.getPurchaseMaterialDescriptor().getBPartnerId() > 0)
		{
			vendorId = event.getPurchaseMaterialDescriptor().getBPartnerId();
		}
		else
		{
			final int orgId = event.getEventDescriptor().getOrgId();

			final Optional<I_C_BPartner_Product> defaultVendor = Services.get(IBPartnerProductDAO.class)
					.retrieveDefaultVendor(productId, orgId);
			vendorId = defaultVendor
					.orElseThrow(() -> new AdempiereException("missing default vendor for productId=" + productId + " and orgId=" + orgId + ";"))
					.getC_BPartner_ID();

		}
		final I_C_BPartner_Product bPArtnerProductRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Product.COLUMN_M_Product_ID, productId)
				.addEqualsFilter(I_C_BPartner_Product.COLUMN_C_BPartner_ID, vendorId)
				.orderBy().addColumn(I_C_BPartner_Product.COLUMN_C_BPartner_Product_ID).endOrderBy()
				.create()
				.first(I_C_BPartner_Product.class);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.builderFromDataRecord().bpartnerProductRecord(bPArtnerProductRecord).build();
		return vendorProductInfo;
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
					.eventDescriptor(requestedEvent.getEventDescriptor().copy())
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
