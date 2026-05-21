package de.metas.inoutcandidate.modelvalidator;

import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.ShipmentConstraintId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.shipmentconstraint.ShipmentConstraintService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.model.I_C_Order;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

import java.util.Optional;

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

@Interceptor(I_C_Order.class)
public class C_Order
{
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	// ShipmentConstraintService is a Spring @Service; this validator is instantiated as a plain
	// object by InOutCandidateValidator, so we resolve via the Spring context.
	private final ShipmentConstraintService shipmentConstraintService = SpringContextHolder.instance.getBean(ShipmentConstraintService.class);

	private static final AdMessageKey MSG_CannotCompleteOrder_DeliveryStop = AdMessageKey.of("CannotCompleteOrder_DeliveryStop");
	private static final AdMessageKey MSG_PO_REACTIVATION_VOID_NOT_ALLOWED = AdMessageKey.of("purchaseorder.shipmentschedule.exported");
	private static final AdMessageKey ERR_ORDER_MODIFICATION_NOT_ALLOWED_RECEIPT_EXISTS = AdMessageKey.of("ERR_ORDER_MODIFICATION_NOT_ALLOWED_RECEIPT_EXISTS");

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_PREPARE)
	public void assertNotDeliveryStopped(final I_C_Order order)
	{
		// For sales orders, check the bill partner; for purchase orders, check the vendor
		final int partnerIdToCheckRepo = order.isSOTrx()
				? order.getBill_BPartner_ID()
				: order.getC_BPartner_ID();

		final BPartnerId partnerIdToCheck = BPartnerId.ofRepoIdOrNull(partnerIdToCheckRepo);
		if (partnerIdToCheck == null)
		{
			return;
		}

		final Optional<ShipmentConstraintId> constraintId = shipmentConstraintService.getDeliveryStopConstraintIdFor(partnerIdToCheck);
		if (constraintId.isPresent())
		{
			throw new AdempiereException(MSG_CannotCompleteOrder_DeliveryStop, partnerIdToCheck.getRepoId(), constraintId.get().getRepoId())
					.markAsUserValidationError();
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_CLOSE })
	public void assertReActivationAllowed(final I_C_Order order)
	{
		if (order.isSOTrx())
		{
			shipmentScheduleBL.assertSalesOrderCanBeReactivated(OrderId.ofRepoId(order.getC_Order_ID()));
		}
		else
		{
			if (receiptScheduleDAO.existsExportedReceiptScheduleForOrder(OrderId.ofRepoId(order.getC_Order_ID())))
			{
				throw new AdempiereException(MSG_PO_REACTIVATION_VOID_NOT_ALLOWED)
						.markAsUserValidationError();
			}
		}
	}

	/**
	 * Prevents altering a PO if it has receipts and has been reactivated via the `PO_AllowReactivationIfReceiptsCreated` sysconfig.
	 * If that's the case, prevent any meaningful changes.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ignoreColumnsChanged = {
					I_C_Order.COLUMNNAME_DocStatus,
					I_C_Order.COLUMNNAME_DocAction,
					I_C_Order.COLUMNNAME_Processing,
					I_C_Order.COLUMNNAME_Processed,
					I_C_Order.COLUMNNAME_IsApproved,
					I_C_Order.COLUMNNAME_QtyOrdered,
					I_C_Order.COLUMNNAME_QtyInvoiced,
					I_C_Order.COLUMNNAME_InvoiceDate,
					I_C_Order.COLUMNNAME_QtyMoved,
					I_C_Order.COLUMNNAME_GrandTotal,
					I_C_Order.COLUMNNAME_TotalLines,
					I_C_Order.COLUMNNAME_Weight,
					I_C_Order.COLUMNNAME_Posted,
					I_C_Order.COLUMNNAME_Updated,
					I_C_Order.COLUMNNAME_UpdatedBy })
	public void assertChangeAllowed(@NonNull final I_C_Order order)
	{
		if (!InterfaceWrapperHelper.isUIAction(order))
		{
			// do nothing if the modification was triggered from the application, not by the user
			return;
		}
		if (order.getQtyMoved().signum() == 0 || orderBL.isSalesOrder(order))
		{
			// not a PO or has no receipts
			return;
		}
		final DocStatus docStatus = DocStatus.ofCode(order.getDocStatus());
		if (!docStatus.isInProgress())
		{
			// document has not been reactivated
			return;
		}

		throw new AdempiereException(ERR_ORDER_MODIFICATION_NOT_ALLOWED_RECEIPT_EXISTS)
				.markAsUserValidationError();
	}
}
