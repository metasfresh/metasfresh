package de.metas.rfq.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.util.Env;

import de.metas.process.Param;
import de.metas.process.SvrProcess;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * A Sales Order is created for the entered Business Partner.
 *
 * A sales order line is created for each RfQ line quantity, where "Offer Quantity" is selected.
 * If on the RfQ Line Quantity, an offer amount is entered (not 0), that price is used.
 * If a margin is entered on RfQ Line Quantity, it overwrites the general margin.
 * The margin is the percentage added to the Best Response Amount.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_RfQ_CreateSO extends SvrProcess
{
	// services
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

	@Param(parameterName = "C_DocType_ID")
	private int p_C_DocType_ID;

	@Override
	protected String doIt()
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);

		if (rfq.getC_BPartner_ID() <= 0 || rfq.getC_BPartner_Location_ID() <= 0)
		{
			throw new AdempiereException("No Business Partner/Location");
		}
		final I_C_BPartner bp = rfq.getC_BPartner();

		final MOrder order = new MOrder(getCtx(), 0, get_TrxName());
		order.setIsSOTrx(true);
		if (p_C_DocType_ID > 0)
		{
			order.setC_DocTypeTarget_ID(p_C_DocType_ID);
		}
		else
		{
			order.setC_DocTypeTarget_ID();
		}
		order.setBPartner(bp);
		order.setC_BPartner_Location_ID(rfq.getC_BPartner_Location_ID());
		order.setSalesRep_ID(rfq.getSalesRep_ID());
		if (rfq.getDateWorkComplete() != null)
		{
			order.setDatePromised(rfq.getDateWorkComplete());
		}
		order.save();

		for (final I_C_RfQLine line : rfqDAO.retrieveLines(rfq))
		{
			for (final I_C_RfQLineQty qty : rfqDAO.retrieveLineQtys(line))
			{
				if (qty.isActive() && qty.isOfferQty())
				{
					final MOrderLine ol = new MOrderLine(order);
					ol.setM_Product_ID(line.getM_Product_ID(), qty.getC_UOM_ID());
					ol.setDescription(line.getDescription());
					ol.setQty(qty.getQty());
					//
					BigDecimal price = qty.getOfferAmt();
					if (price == null || price.signum() == 0)
					{
						price = qty.getBestResponseAmt();
						if (price == null || price.signum() == 0)
						{
							price = Env.ZERO;
							log.warn(" - BestResponse=0 - " + qty);
						}
						else
						{
							BigDecimal margin = qty.getMargin();
							if (margin == null || margin.signum() == 0)
							{
								margin = rfq.getMargin();
							}
							if (margin != null && margin.signum() != 0)
							{
								margin = margin.add(Env.ONEHUNDRED);
								price = price.multiply(margin)
										.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
							}
						}
					}     	// price
					ol.setPrice(price);
					ol.save();
				}     	// Offer Qty
			}     	// All Qtys
		}     	// All Lines

		//
		rfq.setC_Order(order);
		InterfaceWrapperHelper.save(rfq);

		return order.getDocumentNo();
	}
}
