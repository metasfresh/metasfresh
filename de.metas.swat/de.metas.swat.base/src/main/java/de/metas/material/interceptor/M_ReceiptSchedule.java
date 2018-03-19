package de.metas.material.interceptor;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.InterceptorUtil;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;

import com.google.common.annotations.VisibleForTesting;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import lombok.NonNull;

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
public class M_ReceiptSchedule
{
	static final M_ReceiptSchedule INSTANCE = new M_ReceiptSchedule();

	private M_ReceiptSchedule()
	{
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
			I_M_ReceiptSchedule.COLUMNNAME_MovementDate, // this is the actual order's datePromised
			I_M_ReceiptSchedule.COLUMNNAME_IsActive /* IsActive=N shall be threaded like a deletion */ })
	public void createAndFireEvent(
			@NonNull final I_M_ReceiptSchedule schedule,
			@NonNull final ModelChangeType timing)
	{
		final AbstractReceiptScheduleEvent event = createReceiptScheduleEvent(schedule, timing);

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		materialEventService.postEventAfterNextCommit(event);
	}

	@VisibleForTesting
	AbstractReceiptScheduleEvent createReceiptScheduleEvent(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final ModelChangeType timing)
	{
		final boolean created = timing.isNew() || InterceptorUtil.isJustActivated(receiptSchedule);
		if (created)
		{
			return createCreatedEvent(receiptSchedule);
		}

		final boolean deleted = timing.isDelete() || InterceptorUtil.isJustDeactivated(receiptSchedule);
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
				createOrdereMaterialDescriptor(receiptSchedule);
		final OrderLineDescriptor orderLineDescriptor = //
				createOrderLineDescriptor(receiptSchedule);

		final ReceiptScheduleCreatedEvent event = ReceiptScheduleCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(receiptSchedule))
				.orderLineDescriptor(orderLineDescriptor)
				.materialDescriptor(orderedMaterial)
				.reservedQuantity(extractQtyReserved(receiptSchedule))
				.receiptScheduleId(receiptSchedule.getM_ReceiptSchedule_ID())
				.build();

		return event;
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
		final MaterialDescriptor orderedMaterial = createOrdereMaterialDescriptor(receiptSchedule);

		final I_M_ReceiptSchedule oldReceiptSchedule = InterfaceWrapperHelper.createOld(
				receiptSchedule,
				I_M_ReceiptSchedule.class);


		final BigDecimal oldOrderedQuantity = extractQtyOrdered(oldReceiptSchedule);

		final BigDecimal qtyReserved = extractQtyReserved(receiptSchedule);

		final ReceiptScheduleUpdatedEvent event = ReceiptScheduleUpdatedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(receiptSchedule))
				.materialDescriptor(orderedMaterial)
				.reservedQuantity(qtyReserved)
				.receiptScheduleId(receiptSchedule.getM_ReceiptSchedule_ID())
				.reservedQuantityDelta(qtyReserved.subtract(extractQtyReserved(oldReceiptSchedule)))
				.orderedQuantityDelta(orderedMaterial.getQuantity().subtract(oldOrderedQuantity))
				.build();
		return event;
	}

	private BigDecimal extractQtyOrdered(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IReceiptScheduleQtysBL receiptScheduleQtysBL = Services.get(IReceiptScheduleQtysBL.class);
		final BigDecimal oldOrderedQuantity = receiptScheduleQtysBL.getQtyOrdered(receiptSchedule);
		return oldOrderedQuantity;
	}

	private BigDecimal extractQtyReserved(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		return receiptSchedule.getQtyToMove();
	}

	private AbstractReceiptScheduleEvent createDeletedEvent(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final MaterialDescriptor orderedMaterial = createOrdereMaterialDescriptor(receiptSchedule);

		final ReceiptScheduleDeletedEvent event = ReceiptScheduleDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.createNew(receiptSchedule))
				.materialDescriptor(orderedMaterial)
				.reservedQuantity(extractQtyReserved(receiptSchedule))
				.receiptScheduleId(receiptSchedule.getM_ReceiptSchedule_ID())
				.build();
		return event;
	}

	private MaterialDescriptor createOrdereMaterialDescriptor(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final IReceiptScheduleQtysBL receiptScheduleQtysBL = Services.get(IReceiptScheduleQtysBL.class);
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		final BigDecimal orderedQuantity = receiptScheduleQtysBL.getQtyOrdered(receiptSchedule);

		final Timestamp preparationDate = receiptSchedule.getMovementDate();

		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(receiptSchedule);

		final MaterialDescriptor orderedMaterial = MaterialDescriptor.builder()
				.date(preparationDate)
				.productDescriptor(productDescriptor)
				.warehouseId(receiptScheduleBL.getM_Warehouse_Effective_ID(receiptSchedule))
				.bPartnerId(receiptScheduleBL.getC_BPartner_Effective_ID(receiptSchedule))
				.quantity(orderedQuantity)
				.build();
		return orderedMaterial;
	}
}
