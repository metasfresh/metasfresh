package de.metas.adempiere.modelvalidator;

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


import java.math.BigDecimal;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MClient;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.exception.OrderInvoicePricesNotMatchException;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.service.IInvoiceLineBL;
import de.metas.adempiere.service.impl.InvoiceLineBL;
import de.metas.logging.LogManager;

public class InvoiceLine implements ModelValidator
{

	private static final Logger logger = LogManager.getLogger(InvoiceLine.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{

		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_C_InvoiceLine.Table_Name, this);

		// register this service for callouts and model validators
		Services.registerService(IInvoiceLineBL.class, new InvoiceLineBL());
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{

		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, int type)
	{

		assert po instanceof MInvoiceLine : po;

		if (type == TYPE_BEFORE_CHANGE || type == TYPE_BEFORE_NEW)
		{

			final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(po, I_C_InvoiceLine.class);

			if (!il.isProcessed())
			{

				logger.debug("Reevaluating tax for " + il);
				invoiceLineBL.setTax(po.getCtx(), il, po.get_TrxName());

				logger.debug("Setting TaxAmtInfo for " + il);
				invoiceLineBL.setTaxAmtInfo(po.getCtx(), il, po.get_TrxName());				
			}

			// Introduced by US1184, because having the same price on Order and Invoice is enforced by German Law
			if (invoiceLineBL.isPriceLocked(il))
			{
				assertOrderInvoicePricesMatch(il);
			}
		}
		else if (type == TYPE_BEFORE_DELETE)
		{
			final IInvoiceDAO invoicePA = Services.get(IInvoiceDAO.class);

			for (final MInvoiceLine il : invoicePA.retrieveReferringLines(po.getCtx(), po.get_ID(), po.get_TrxName()))
			{
				il.setRef_InvoiceLine_ID(0);
				il.saveEx();
			}
		}
		// 02362: begin
		else if (type == TYPE_AFTER_CHANGE)
		{
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(po, I_C_InvoiceLine.class);
			if (!il.isProcessed())
			{
				final BigDecimal PriceList = il.getPriceList();
				final BigDecimal PriceActual = il.getPriceActual();
				BigDecimal Discount;
				if (PriceList.intValue() == 0)
				{
					Discount = Env.ZERO;
				}
				else
				{
					Discount = new BigDecimal(
							(PriceList.doubleValue() - PriceActual.doubleValue())
									/ PriceList.doubleValue() * 100.0);
					if (Discount.scale() > 2)
					{
						Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
					}
				}
				if (Discount.scale() > 2)
				{
					Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				il.setDiscount(Discount);
			}
		}
		// 02362: end
		return null;
	}

	public static void assertOrderInvoicePricesMatch(final I_C_InvoiceLine invoiceLine)
	{
		final I_C_OrderLine oline = invoiceLine.getC_OrderLine();
		if (invoiceLine.getPriceActual().compareTo(oline.getPriceActual()) != 0)
		{
			throw new OrderInvoicePricesNotMatchException(I_C_InvoiceLine.COLUMNNAME_PriceActual, oline.getPriceActual(), invoiceLine.getPriceActual());
		}
		if (invoiceLine.getPriceList().compareTo(oline.getPriceList()) != 0)
		{
			throw new OrderInvoicePricesNotMatchException(I_C_InvoiceLine.COLUMNNAME_PriceList, oline.getPriceList(), invoiceLine.getPriceList());
		}
	}
}
