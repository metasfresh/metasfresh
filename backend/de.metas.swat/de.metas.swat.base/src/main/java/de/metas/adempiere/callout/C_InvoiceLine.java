package de.metas.adempiere.callout;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.service.IInvoiceLineBL;

@Callout(I_C_InvoiceLine.class)
public class C_InvoiceLine
{
	@CalloutMethod(columnNames = { I_C_InvoiceLine.COLUMNNAME_QtyEntered,
			I_C_InvoiceLine.COLUMNNAME_M_Product_ID,
			I_C_InvoiceLine.COLUMNNAME_PriceEntered,
			I_C_InvoiceLine.COLUMNNAME_PriceActual,
			I_C_InvoiceLine.COLUMNNAME_QtyInvoicedInPriceUOM })
	public void setQtyInvoicedInPriceUOM_AndLineNetAMT(final I_C_InvoiceLine invoiceLine, final ICalloutField field)
	{
		if (invoiceLine == null)
		{
			// nothing to do
			return;
		}

		final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		invoiceLineBL.setQtyInvoicedInPriceUOM(invoiceLine);

		invoiceBL.setLineNetAmt(invoiceLine);
	}

	/**
	 * update prices on ASI or discount change
	 * 
	 * @param invoiceLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_C_InvoiceLine.COLUMNNAME_M_AttributeSetInstance_ID, I_C_InvoiceLine.COLUMNNAME_Discount })
	public void onASIorDiscountChange(final I_C_InvoiceLine invoiceLine, final ICalloutField field)
	{
		Services.get(IInvoiceLineBL.class).updatePrices(invoiceLine);

	}

	/**
	 * Set the product as soon as the order line is set
	 * 
	 * @param invoiceLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID })
	public void setProduct(final I_C_InvoiceLine invoiceLine, final ICalloutField field)
	{
		if (InterfaceWrapperHelper.isNull(invoiceLine, I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID))
		{
			// set the product to null if the orderline was set to null
			invoiceLine.setM_Product(null);
			
			return;
		}

		final I_C_OrderLine ol = invoiceLine.getC_OrderLine();
	
		final I_M_Product product = ol.getM_Product();

		invoiceLine.setM_Product(product);
	}
	
	
	@CalloutMethod(columnNames = { I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID })
	public void setIsPackagingMaterial(final I_C_InvoiceLine invoiceLine, final ICalloutField field)
	{
		if (InterfaceWrapperHelper.isNull(invoiceLine, I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID))
		{
			//in case the c_orderline_id is removed, make sure the flag is on false. The user can set it on true, manually
			invoiceLine.setIsPackagingMaterial(false);
			return;
		}

		final de.metas.interfaces.I_C_OrderLine ol =  InterfaceWrapperHelper.create(invoiceLine.getC_OrderLine(), de.metas.interfaces.I_C_OrderLine.class);
	
		invoiceLine.setIsPackagingMaterial(ol.isPackagingMaterial());
	}
	
	
	
	
	
	

}
