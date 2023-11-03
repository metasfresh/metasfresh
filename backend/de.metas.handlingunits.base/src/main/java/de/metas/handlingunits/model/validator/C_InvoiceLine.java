package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.handlingunits.model.I_C_InvoiceLine;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import java.math.BigDecimal;

@Validator(I_C_InvoiceLine.class)
public class C_InvoiceLine
{
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID,
			I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID

	})
	public void setM_HU_PI_Item_Product(final I_C_InvoiceLine invoiceLine)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final boolean isFreightCost = productBL
				.getProductType(ProductId.ofRepoId(invoiceLine.getM_Product_ID()))
				.isFreightCost();

		if (isFreightCost)
		{
			// don't set a packing material to a freight cost
			return;
		}
		// do nothing in case the invoiceline already has an M_HU_PI_Item_Product
		if (invoiceLine.getM_HU_PI_Item_Product_ID() <= 0)
		{
			return;
		}

		final I_M_InOutLine iol = InterfaceWrapperHelper.create(invoiceLine.getM_InOutLine(), I_M_InOutLine.class);

		if (iol != null)
		{
			invoiceLine.setM_HU_PI_Item_Product_ID(iol.getM_HU_PI_Item_Product_ID());
		}

		// fallback to orderline

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(invoiceLine.getC_OrderLine(), I_C_OrderLine.class);

		if (orderLine != null)
		{
			invoiceLine.setM_HU_PI_Item_Product_ID(orderLine.getM_HU_PI_Item_Product_ID());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_InvoiceLine.COLUMNNAME_QtyEntered, I_C_InvoiceLine.COLUMNNAME_C_UOM_ID })
	public void setQtyEnteredInBPartnerUOM(final I_C_InvoiceLine invoiceLine)
	{
		if (invoiceLine.getC_OrderLine_ID() <= 0)
		{
			invoiceLine.setC_UOM_BPartner_ID(0);
			invoiceLine.setQtyEnteredInBPartnerUOM(null);
			return;
		}

		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(OrderLineId.ofRepoId(invoiceLine.getC_OrderLine_ID()), I_C_OrderLine.class);

		if (orderLine.getC_UOM_BPartner_ID() <= 0 || orderLine.getQtyEnteredInBPartnerUOM().signum() == 0)
		{
			invoiceLine.setC_UOM_BPartner_ID(0);
			invoiceLine.setQtyEnteredInBPartnerUOM(null);
			return;
		}

		final Quantity qtyInvoicedInStockUOM = invoiceLineBL.getQtyEnteredInStockUOM(invoiceLine);

		//dev-note: calculating invoicedQtyInBPartnerUOM using proportion to avoid missing UOM conversion between
		//BPartner_UOM_ID - which might not be considered at all in metas internal processing - and actual stock UOM
		final Quantity invoicedQtyInBPartnerUOM = qtyInvoicedInStockUOM
				.multiply(orderLine.getQtyEnteredInBPartnerUOM())
				.divide(orderLine.getQtyOrdered());

		if (invoicedQtyInBPartnerUOM.signum() < 0)
		{
			invoiceLine.setQtyEnteredInBPartnerUOM(BigDecimal.ZERO);
		}
		else
		{
			invoiceLine.setQtyEnteredInBPartnerUOM(invoicedQtyInBPartnerUOM.toBigDecimal());
		}

		invoiceLine.setC_UOM_BPartner_ID(orderLine.getC_UOM_BPartner_ID());
	}
}
