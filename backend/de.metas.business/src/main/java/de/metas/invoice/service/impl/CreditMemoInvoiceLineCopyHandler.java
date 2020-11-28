package de.metas.invoice.service.impl;

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


import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoiceLine;

import de.metas.document.IDocLineCopyHandler;
import de.metas.util.Services;

/**
 * 
 * Note: This class is currently instantiated and called directly from BLs in this package.<br>
 * Please move this class to <code>org.adempiere.invoice.spi.impl</code> as soon as it is registered at and invoked via {@link de.metas.document.ICopyHandlerBL}.
 * 
 */
class CreditMemoInvoiceLineCopyHandler implements IDocLineCopyHandler<I_C_InvoiceLine>
{
	private static final CreditMemoInvoiceLineCopyHandler instance = new CreditMemoInvoiceLineCopyHandler();

	private CreditMemoInvoiceLineCopyHandler()
	{
		super();
	}

	@Override
	public void copyPreliminaryValues(final I_C_InvoiceLine from, final I_C_InvoiceLine to)
	{
		// nothing right now
	}

	@Override
	public void copyValues(final I_C_InvoiceLine from, final I_C_InvoiceLine to)
	{
		// 08864
		// Make sure the Attribute Set Instance is cloned form the initial invoice line to the credit memo one
		Services.get(IAttributeSetInstanceBL.class).cloneASI(to, from);
		
		// 08908
		// Make IsManualPrice false, since the price it taken from the invoiceline
		to.setPriceActual(from.getPriceActual());
		to.setPriceList(from.getPriceList());
		to.setPriceLimit(from.getPriceLimit());
		to.setPriceEntered(from.getPriceEntered());

		final de.metas.adempiere.model.I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(to, de.metas.adempiere.model.I_C_InvoiceLine.class);
		
		// set this flag on true; meaning the pricing engine shall not be called in the future because the price was already set from the "parent" invoiceLine
		invoiceLine.setIsManualPrice(true);
	}

	public static CreditMemoInvoiceLineCopyHandler getInstance()
	{
		return CreditMemoInvoiceLineCopyHandler.instance;
	}
	
	/**
	 * 
	 */
	@Override
	public Class<I_C_InvoiceLine> getSupportedItemsClass()
	{
		return I_C_InvoiceLine.class;
	}
}
