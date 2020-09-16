package de.metas.purchasecandidate.material.interceptor;

import com.google.common.annotations.VisibleForTesting;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateUpdatedEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.material.event.PurchaseCandidateRequestedHandler;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

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

@Interceptor(I_C_PurchaseCandidate.class)
@Component
public class C_PurchaseCandidate_PostMaterialEvent
{
	private final PostMaterialEventService postMaterialEventService;
	private final ModelProductDescriptorExtractor productDescriptorFactory;
	private final ReplenishInfoRepository replenishInfoRepository;

	public C_PurchaseCandidate_PostMaterialEvent(
			@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final ReplenishInfoRepository replenishInfoRepository)
	{
		this.postMaterialEventService = postMaterialEventService;
		this.productDescriptorFactory = productDescriptorFactory;
		this.replenishInfoRepository = replenishInfoRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void postPurchaseCandidateCreatedEvent(
			@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord,
			@NonNull final ModelChangeType type)
	{
		if (!PurchaseCandidateRequestedHandler.INTERCEPTOR_SHALL_POST_EVENT_FOR_PURCHASE_CANDIDATE_RECORD.get())
		{
			return;
		}

		final boolean isNewPurchaseCandidateRecord = type.isNew() || ModelChangeUtil.isJustActivated(purchaseCandidateRecord);
		if (!isNewPurchaseCandidateRecord)
		{
			return;
		}

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(purchaseCandidateRecord);

		final PurchaseCandidateCreatedEvent purchaseCandidateCreatedEvent = PurchaseCandidateCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(purchaseCandidateRecord.getAD_Client_ID(), purchaseCandidateRecord.getAD_Org_ID()))
				.purchaseCandidateRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID())
				.purchaseMaterialDescriptor(materialDescriptor)
				.supplyRequiredDescriptor(createSupplyRequiredDescritproOrNull(purchaseCandidateRecord))
				.build();

		postMaterialEventService.postEventAfterNextCommit(purchaseCandidateCreatedEvent);
	}

	private SupplyRequiredDescriptor createSupplyRequiredDescritproOrNull(@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		if (purchaseCandidateRecord.getC_OrderLineSO_ID() <= 0)
		{
			return null;
		}
		return SupplyRequiredDescriptor.builder()
				.orderId(purchaseCandidateRecord.getC_OrderSO_ID())
				.orderLineId(purchaseCandidateRecord.getC_OrderLineSO_ID())
				.build();
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE })
	public void postPurchaseCandidateUpdatedEvent(
			@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord,
			@NonNull final ModelChangeType type)
	{
		final boolean isNewPurchaseCandidateRecord = type.isNew() || ModelChangeUtil.isJustActivated(purchaseCandidateRecord);
		if (isNewPurchaseCandidateRecord)
		{
			return;
		}

		final PurchaseCandidateUpdatedEvent purchaseCandidateUpdatedEvent = createUpdatedEvent(purchaseCandidateRecord);

		postMaterialEventService.postEventAfterNextCommit(purchaseCandidateUpdatedEvent);
	}

	@VisibleForTesting
	PurchaseCandidateUpdatedEvent createUpdatedEvent(@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(purchaseCandidateRecord);

		final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository.getBy(materialDescriptor).toMinMaxDescriptor();

		final PurchaseCandidateUpdatedEvent purchaseCandidateUpdatedEvent = PurchaseCandidateUpdatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(purchaseCandidateRecord.getAD_Client_ID(), purchaseCandidateRecord.getAD_Org_ID()))
				.purchaseCandidateRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID())
				.vendorId(purchaseCandidateRecord.getVendor_ID())
				.purchaseMaterialDescriptor(materialDescriptor)
				.minMaxDescriptor(minMaxDescriptor)
				.build();
		return purchaseCandidateUpdatedEvent;
	}

	private MaterialDescriptor createMaterialDescriptor(@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(purchaseCandidateRecord);

		final ProductId productId = ProductId.ofRepoId(purchaseCandidateRecord.getM_Product_ID());
		final Quantity purchaseQty = Services.get(IUOMConversionBL.class)
				.convertToProductUOM(
						Quantity.of(purchaseCandidateRecord.getQtyToPurchase(), purchaseCandidateRecord.getC_UOM()),
						productId);

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(TimeUtil.asInstant(purchaseCandidateRecord.getPurchaseDatePromised()))
				.warehouseId(WarehouseId.ofRepoId(purchaseCandidateRecord.getM_WarehousePO_ID()))
				.productDescriptor(productDescriptor)
				// .customerId() we don't have a customer
				.quantity(purchaseQty.toBigDecimal())
				.build();
		return materialDescriptor;
	}
}
