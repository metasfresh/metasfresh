package de.metas.purchasecandidate.material.interceptor;

import com.google.common.annotations.VisibleForTesting;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.OldReceiptScheduleData;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/*
 * #%L
 * de.metas.swat.base
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

@Interceptor(I_M_ReceiptSchedule.class)
@Component
public class M_ReceiptSchedule_PostMaterialEvent
{
	private final PostMaterialEventService postMaterialEventService;
	private final ModelProductDescriptorExtractor productDescriptorFactory;
	private final PurchaseCandidateRepository purchaseCandidateRepository;
	private final ReplenishInfoRepository replenishInfoRepository;

	public M_ReceiptSchedule_PostMaterialEvent(
			@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final ReplenishInfoRepository replenishInfoRepository)
	{
		this.postMaterialEventService = postMaterialEventService;
		this.productDescriptorFactory = productDescriptorFactory;
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.replenishInfoRepository = replenishInfoRepository;
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE /* before delete because we still need the M_ReceiptSchedule_ID */
	}, ifColumnsChanged = {
			I_M_ReceiptSchedule.COLUMNNAME_QtyOrdered,
			I_M_ReceiptSchedule.COLUMNNAME_QtyToMove_Override, // this is the actual qty-ordered-override
			I_M_ReceiptSchedule.COLUMNNAME_QtyToMove, // this is the actual qty-reserved
			I_M_ReceiptSchedule.COLUMNNAME_M_Product_ID,
			I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_Override_ID,
			I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID,
			I_M_ReceiptSchedule.COLUMNNAME_AD_Org_ID,
			I_M_ReceiptSchedule.COLUMNNAME_M_AttributeSetInstance_ID,
			I_M_ReceiptSchedule.COLUMNNAME_MovementDate, // this is the actual order's datePromised
			I_M_ReceiptSchedule.COLUMNNAME_IsActive, /* IsActive=N shall be threaded like a deletion */
			I_M_ReceiptSchedule.COLUMNNAME_Processed,
			I_M_ReceiptSchedule.COLUMNNAME_QtyOrderedOverUnder })
	public void createAndFireEvent(
			@NonNull final I_M_ReceiptSchedule schedule,
			@NonNull final ModelChangeType timing)
	{
		final AbstractReceiptScheduleEvent event = createReceiptScheduleEvent(schedule, timing);

		postMaterialEventService.enqueueEventAfterNextCommit(event);
	}

	@VisibleForTesting
	AbstractReceiptScheduleEvent createReceiptScheduleEvent(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final ModelChangeType timing)
	{
		final boolean created = timing.isNew() || ModelChangeUtil.isJustActivated(receiptSchedule);
		if (created)
		{
			return createCreatedEvent(receiptSchedule);
		}

		final boolean deleted = timing.isDelete() || ModelChangeUtil.isJustDeactivated(receiptSchedule);
		if (deleted)
		{
			return createDeletedEvent(receiptSchedule);
		}

		return createUpdatedEvent(receiptSchedule);
	}

	private AbstractReceiptScheduleEvent createCreatedEvent(
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final MaterialDescriptor orderedMaterial = //
				createOrderMaterialDescriptor(receiptSchedule);
		final OrderLineDescriptor orderLineDescriptor = //
				createOrderLineDescriptor(receiptSchedule);

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(receiptSchedule.getC_OrderLine_ID());
		final PurchaseCandidateId purchaseCandidateIdOrNull = purchaseCandidateRepository.getIdByPurchaseOrderLineIdOrNull(orderLineId);

		return ReceiptScheduleCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()))
				.orderLineDescriptor(orderLineDescriptor)
				.materialDescriptor(orderedMaterial)
				.reservedQuantity(extractQtyReserved(receiptSchedule))
				.purchaseCandidateRepoId(PurchaseCandidateId.getRepoIdOr(purchaseCandidateIdOrNull, 0))
				.receiptScheduleId(receiptSchedule.getM_ReceiptSchedule_ID())
				.build();
	}

	private OrderLineDescriptor createOrderLineDescriptor(
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		return OrderLineDescriptor.builder()
				.orderLineId(receiptSchedule.getC_OrderLine_ID())
				.orderId(receiptSchedule.getC_Order_ID())
				.orderBPartnerId(receiptSchedule.getC_Order().getC_BPartner_ID())
				.docTypeId(receiptSchedule.getC_Order().getC_DocType_ID())
				.build();
	}

	private AbstractReceiptScheduleEvent createUpdatedEvent(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final MaterialDescriptor orderedMaterial = createOrderMaterialDescriptor(receiptSchedule);
		final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository.getBy(orderedMaterial).toMinMaxDescriptor();

		final ReceiptScheduleUpdatedEvent.ReceiptScheduleUpdatedEventBuilder receiptScheduleUpdatedEventBuilder = ReceiptScheduleUpdatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()))
				.materialDescriptor(orderedMaterial)
				.receiptScheduleId(receiptSchedule.getM_ReceiptSchedule_ID())
				.minMaxDescriptor(minMaxDescriptor);

		setQuantities(receiptScheduleUpdatedEventBuilder, orderedMaterial, receiptSchedule);

		return receiptScheduleUpdatedEventBuilder.build();
	}

	private void setQuantities(
			@NonNull final ReceiptScheduleUpdatedEvent.ReceiptScheduleUpdatedEventBuilder receiptScheduleUpdatedEventBuilder,
			@NonNull final MaterialDescriptor currentMaterialDescriptor,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final BigDecimal currentQtyReserved = extractQtyReserved(receiptSchedule);

		receiptScheduleUpdatedEventBuilder
				.reservedQuantity(currentQtyReserved);

		final I_M_ReceiptSchedule oldReceiptSchedule = InterfaceWrapperHelper.createOld(receiptSchedule, I_M_ReceiptSchedule.class);

		final MaterialDescriptor oldMaterialDescriptor = createOrderMaterialDescriptor(oldReceiptSchedule);

		if (targetMaterialDescriptorChanged(currentMaterialDescriptor, oldMaterialDescriptor))
		{
			receiptScheduleUpdatedEventBuilder
					.oldReceiptScheduleData(buildOldReceiptScheduleData(oldMaterialDescriptor, oldReceiptSchedule))
					.reservedQuantityDelta(currentQtyReserved)
					.orderedQuantityDelta(currentMaterialDescriptor.getQuantity());
		}
		else
		{
			final BigDecimal oldQtyReserved = extractQtyReserved(oldReceiptSchedule);

			receiptScheduleUpdatedEventBuilder
					.reservedQuantityDelta(currentQtyReserved.subtract(oldQtyReserved));

			//If Order is closed and receiptSchedule row was already closed no update is needed (ignore orderedQty update)
			if (oldReceiptSchedule.isProcessed() && receiptSchedule.isProcessed())
			{
				return;
			}

			//overDelivery
			if (receiptSchedule.getQtyOrderedOverUnder().signum() > 0 && !receiptSchedule.isProcessed() && !oldReceiptSchedule.isProcessed())
			{
				receiptScheduleUpdatedEventBuilder
						.orderedQuantityDelta(receiptSchedule.getQtyOrderedOverUnder().subtract(oldReceiptSchedule.getQtyOrderedOverUnder())
													  .add(currentMaterialDescriptor.getQuantity().subtract(oldMaterialDescriptor.getQuantity())));
			}
			//receiptSchedule closed or receiptSchedule reopen or order closed
			else if (receiptSchedule.getQtyOrderedOverUnder().signum() != 0 || oldReceiptSchedule.getQtyOrderedOverUnder().signum() != 0)
			{
				receiptScheduleUpdatedEventBuilder
						.orderedQuantityDelta(receiptSchedule.getQtyOrderedOverUnder().subtract(oldReceiptSchedule.getQtyOrderedOverUnder()));
			}
			else
			{
				receiptScheduleUpdatedEventBuilder
						.orderedQuantityDelta(currentMaterialDescriptor.getQuantity().subtract(oldMaterialDescriptor.getQuantity()));
			}
		}
	}

	@NonNull
	private OldReceiptScheduleData buildOldReceiptScheduleData(
			@NonNull final MaterialDescriptor oldMaterialDescriptor,
			@NonNull final I_M_ReceiptSchedule oldReceiptSchedule)
	{
		return OldReceiptScheduleData.builder()
				.oldMaterialDescriptor(oldMaterialDescriptor)
				.oldOrderedQuantity(oldMaterialDescriptor.getQuantity())
				.oldReservedQuantity(extractQtyReserved(oldReceiptSchedule))
				.build();
	}

	private boolean targetMaterialDescriptorChanged(
			@NonNull final MaterialDescriptor orderedMaterialDescriptor,
			@NonNull final MaterialDescriptor oldMaterialDescriptor)
	{
		return !orderedMaterialDescriptor.getStorageAttributesKey().equals(oldMaterialDescriptor.getStorageAttributesKey())
				|| !orderedMaterialDescriptor.getDate().equals(oldMaterialDescriptor.getDate())
				|| orderedMaterialDescriptor.getProductId() != oldMaterialDescriptor.getProductId()
				|| !Objects.equals(orderedMaterialDescriptor.getWarehouseId(), oldMaterialDescriptor.getWarehouseId());
	}

	private BigDecimal extractQtyReserved(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		return receiptSchedule.getQtyToMove();
	}

	private AbstractReceiptScheduleEvent createDeletedEvent(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final MaterialDescriptor orderedMaterial = createOrderMaterialDescriptor(receiptSchedule);
		final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository.getBy(orderedMaterial).toMinMaxDescriptor();

		return ReceiptScheduleDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()))
				.materialDescriptor(orderedMaterial)
				.reservedQuantity(extractQtyReserved(receiptSchedule))
				.receiptScheduleId(receiptSchedule.getM_ReceiptSchedule_ID())
				.minMaxDescriptor(minMaxDescriptor)
				.build();
	}

	private MaterialDescriptor createOrderMaterialDescriptor(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final IReceiptScheduleQtysBL receiptScheduleQtysBL = Services.get(IReceiptScheduleQtysBL.class);
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		final BigDecimal orderedQuantity = receiptScheduleQtysBL.getQtyOrdered(receiptSchedule);

		final Timestamp preparationDate = receiptSchedule.getMovementDate();

		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(receiptSchedule);

		return MaterialDescriptor.builder()
				.date(TimeUtil.asInstant(preparationDate))
				.productDescriptor(productDescriptor)
				.warehouseId(receiptScheduleBL.getWarehouseEffectiveId(receiptSchedule))
				// .customerId() we don't have the *customer* ID
				.quantity(orderedQuantity)
				.build();
	}
}
