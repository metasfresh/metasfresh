package de.metas.freighcost.modelvalidator;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.I_M_FreightCost;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MFreightCost;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IInvoiceLineBL;
import de.metas.document.engine.IDocument;
import de.metas.freighcost.api.IFreightCostBL;
import de.metas.freighcost.api.impl.FreightCostBL;
import de.metas.logging.LogManager;

/**
 * This model validator checks for each new invoice line if there needs to be an additional invoice line for freight
 * cost.
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 * 
 */
public class FreightCostValidator implements ModelValidator
{
	private static final Logger logger = LogManager.getLogger(FreightCostValidator.class);

	private Map<Integer, Set<Integer>> invoiceId2inOutIds = new HashMap<>();

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
		
		Services.registerService(IFreightCostBL.class, new FreightCostBL());
		
		// see #invoiceLineChanged
		engine.addModelChange(I_C_InvoiceLine.Table_Name, this);
		engine.addDocValidate(I_C_Invoice.Table_Name, this);

		engine.addDocValidate(I_C_Order.Table_Name, this);
		engine.addModelChange(I_C_Order.Table_Name, this);
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
		if (po instanceof I_C_Invoice)
		{
			if (TIMING_AFTER_COMPLETE == timing || TIMING_AFTER_PREPARE == timing)
			{
				final I_C_Invoice inv = (I_C_Invoice)po;

				final Set<Integer> inOutIds = invoiceId2inOutIds.remove(inv.getC_Invoice_ID());

				logger.info("Flushed " + (inOutIds == null ? 0 : inOutIds.size()) + " inOutIds for invoice " + inv);
			}
		}
		else if (po instanceof MOrder)
		{
			// add or remove a freight cost order line
			final MOrder order = (MOrder)po;

			if (TIMING_AFTER_REACTIVATE == timing)
			{
				BigDecimal deletedFreightAmt = BigDecimal.ZERO;
				// making sure that (all) freight cost order lines are deleted

				final IFreightCostBL freighCostBL = Services.get(IFreightCostBL.class);
				for (final MOrderLine ol : order.getLines())
				{
					if (freighCostBL.isFreightCostProduct(po.getCtx(), ol.getM_Product_ID(), po.get_TrxName()))
					{
						deletedFreightAmt = deletedFreightAmt.add(ol.getPriceActual());
						ol.deleteEx(false);
					}
				}
				if (X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule()) && deletedFreightAmt.signum() != 0)
				{
					// reinsert the freight amount value in the field
					order.setFreightAmt(deletedFreightAmt);
				}
			}
			else if (TIMING_BEFORE_COMPLETE == timing)
			{
				Services.get(IFreightCostBL.class).evalAddFreightCostLine(order);
			}
		}
		return null;
	}

	private boolean hasFreightCostLine(final MInOut inOut)
	{
		final IFreightCostBL freighCostBL = Services.get(IFreightCostBL.class);
		for (final MInOutLine inOutLine : inOut.getLines())
		{
			if (freighCostBL.isFreightCostProduct(inOut.getCtx(), inOutLine.getM_Product_ID(), inOut.get_TrxName()))
			{
				return true;
			}
		}
		return false;
	}

	private boolean hasFreightCostLine(final MOrder order)
	{
		final IFreightCostBL freighCostBL = Services.get(IFreightCostBL.class);
		for (final MOrderLine inOutLine : order.getLines())
		{
			if (freighCostBL.isFreightCostProduct(order.getCtx(), inOutLine.getM_Product_ID(), order.get_TrxName()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String modelChange(final PO po, int type) throws Exception
	{
		if (po instanceof MInvoiceLine)
		{
			invoiceLineChanged(po.getCtx(), (MInvoiceLine)po, type, po.get_TrxName());
		}
		else if (po instanceof MOrder)
		{
			if (ModelValidator.TYPE_BEFORE_CHANGE == type)
			{
				// find out if we have a prepay order that enters the status "waiting payment"
				final MOrder order = (MOrder)po;

				if (po.is_ValueChanged(I_C_Order.COLUMNNAME_DocStatus) && IDocument.STATUS_WaitingPayment.equals(order.getDocStatus()))
				{
					Services.get(IFreightCostBL.class).evalAddFreightCostLine(order);
				}
			}
		}
		return null;
	}

	/**
	 * Evaluate the changed line and figure out if we need to add another InvoiceLine for freight cost
	 * 
	 * @param line
	 * @param type
	 * @param trxName
	 */
	void invoiceLineChanged(final Properties ctx, final I_C_InvoiceLine line, final int type, final String trxName)
	{
		if (type != ModelValidator.TYPE_AFTER_NEW)
		{
			return;
		}

		final int iolId = line.getM_InOutLine_ID();
		if (iolId == 0)
		{
			logger.debug(line + " has M_InOutLine_ID=0. Returning.");
			return;
		}

		final MInvoice invoice = (MInvoice)line.getC_Invoice();
		if (!invoice.isSOTrx())
		{
			logger.debug(line + " belongs to a purchase invoice. Returning.");
			return;
		}

		if (line.getC_OrderLine_ID() > 0)
		{
			final MOrder order = (MOrder)line.getC_OrderLine().getC_Order();
			if (hasFreightCostLine(order))
			{
				logger.debug(order + ", which we are invoicing here, has an explicit freight cost line. Returning.");
				return;
			}
		}

		final int invoiceId = line.getC_Invoice_ID();

		Set<Integer> inOutIds = invoiceId2inOutIds.get(invoiceId);
		if (inOutIds == null)
		{
			inOutIds = new HashSet<>();
			invoiceId2inOutIds.put(invoiceId, inOutIds);
		}

		final I_M_InOutLine iol = InterfaceWrapperHelper.create(Env.getCtx(), iolId, I_M_InOutLine.class, trxName);
		final int inOutId = iol.getM_InOut_ID();
		if (!inOutIds.add(inOutId))
		{
			logger.debug("There is already a freight cost invoice line for M_InOut_ID=" + inOutId + " and M_Invoice_ID=" + invoiceId + ". Returning.");
			return;
		}

		final MInOut inOut = new MInOut(ctx, inOutId, trxName);
		if (hasFreightCostLine(inOut))
		{
			logger.debug(inOut + ", which we are invoicing here has an explicit freight cost line. Returning.");
			return;
		}

		final IFreightCostBL freightCostBL = Services.get(IFreightCostBL.class);
		final BigDecimal freightCostAmt = freightCostBL.computeFreightCostForInOut(inOutId, trxName);
		if (freightCostAmt.signum() <= 0)
		{
			logger.debug("Freight cost for M_InOut_ID=" + inOutId + " is " + freightCostAmt + ". Returning");
			return;
		}

		final MInvoiceLine freightCostLine = new MInvoiceLine(invoice);
		freightCostLine.setPriceEntered(freightCostAmt);
		freightCostLine.setPriceActual(freightCostAmt);
		freightCostLine.setPriceList(freightCostAmt);

		// set the iol-ID so this new freightCostLine can be grouped with the respective shipment's invoice lines.
		freightCostLine.setM_InOutLine_ID(iolId);

		final I_M_FreightCost freightCost = MFreightCost.retrieveFor(inOut);

		freightCostLine.setM_Product_ID(freightCost.getM_Product_ID());
		freightCostLine.setQtyEntered(BigDecimal.ONE);
		freightCostLine.setQtyInvoiced(BigDecimal.ONE);
		
		
		final int taxCategoryId = Services.get(IInvoiceLineBL.class).getC_TaxCategory_ID(freightCostLine);
		freightCostLine.setC_TaxCategory_ID(taxCategoryId);

		InterfaceWrapperHelper.save(freightCostLine);

		logger.info("Created new freight cost invoice line with price " + freightCostAmt + " for M_InOut_ID " + inOutId + " and invoice " + invoice);
	}
}
