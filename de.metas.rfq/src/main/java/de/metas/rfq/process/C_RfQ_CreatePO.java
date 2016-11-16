package de.metas.rfq.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;

import de.metas.process.Param;
import de.metas.process.SvrProcess;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.exceptions.NoCompletedRfQResponsesFoundException;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

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
 * Create purchase order(s) for the {@link I_C_RfQResponse}s and lines marked as Selected Winner using the selected Purchase Quantity (in RfQ Line Quantity).
 *
 * If a Response is marked as Selected Winner, all lines are created (and Selected Winner of other responses ignored).
 * If there is no response marked as Selected Winner, the lines are used.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_RfQ_CreatePO extends SvrProcess
{
	// services
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

	@Param(parameterName = "C_DocType_ID")
	private int p_C_DocType_ID;

	@Override
	protected String doIt() throws Exception
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);

		// Complete
		final List<I_C_RfQResponse> responses = rfqDAO.retrieveCompletedResponses(rfq);
		if (responses.isEmpty())
		{
			throw new NoCompletedRfQResponsesFoundException(rfq);
		}

		//
		// Winner for entire RfQ
		for (final I_C_RfQResponse rfqResponse : responses)
		{
			if (!rfqResponse.isSelectedWinner())
			{
				continue;
			}

			//
			final I_C_BPartner bp = rfqResponse.getC_BPartner();
			log.info("Winner={}", bp);

			final MOrder order = new MOrder(getCtx(), 0, get_TrxName());
			order.setIsSOTrx(false);
			if (p_C_DocType_ID > 0)
			{
				order.setC_DocTypeTarget_ID(p_C_DocType_ID);
			}
			else
			{
				order.setC_DocTypeTarget_ID();
			}
			order.setBPartner(bp);
			order.setC_BPartner_Location_ID(rfqResponse.getC_BPartner_Location_ID());
			order.setSalesRep_ID(rfq.getSalesRep_ID());

			if (rfqResponse.getDateWorkComplete() != null)
			{
				order.setDatePromised(rfqResponse.getDateWorkComplete());
			}
			else if (rfq.getDateWorkComplete() != null)
			{
				order.setDatePromised(rfq.getDateWorkComplete());
			}

			order.saveEx();
			//
			for (final I_C_RfQResponseLine rfqResponseLine : rfqDAO.retrieveResponseLines(rfqResponse))
			{
				if (!rfqResponseLine.isActive())
				{
					continue;
				}

				// Response Line Qty
				for (final I_C_RfQResponseLineQty rfqResponseLineQty : rfqDAO.retrieveResponseQtys(rfqResponseLine))
				{
					// Create PO Lline for all Purchase Line Qtys
					final I_C_RfQLineQty rfqLineQty = rfqResponseLineQty.getC_RfQLineQty();
					if (rfqLineQty.isActive() && rfqLineQty.isPurchaseQty())
					{
						final MOrderLine ol = new MOrderLine(order);
						ol.setM_Product_ID(rfqResponseLine.getM_Product_ID(), rfqLineQty.getC_UOM_ID());
						ol.setDescription(rfqResponseLine.getDescription());

						ol.setQty(rfqLineQty.getQty());

						final BigDecimal price = rfqBL.calculatePriceWithoutDiscount(rfqResponseLineQty);
						ol.setPrice();
						ol.setPrice(price);

						ol.saveEx();
					}
				}
			}

			rfqResponse.setC_Order(order);
			InterfaceWrapperHelper.save(rfqResponse);

			return order.getDocumentNo();
		}

		//
		// Selected Winner on Line Level
		int noOrders = 0;
		for (final I_C_RfQResponse response : responses)
		{
			I_C_BPartner bp = null;
			MOrder order = null;
			// For all Response Lines
			for (final I_C_RfQResponseLine line : rfqDAO.retrieveResponseLines(response))
			{
				if (!line.isActive() || !line.isSelectedWinner())
				{
					continue;
				}

				// New/different BP
				if (bp == null || bp.getC_BPartner_ID() != response.getC_BPartner_ID())
				{
					bp = response.getC_BPartner();
					order = null;
				}
				log.info("Line={}, Winner={}", line, bp);

				// New Order
				if (order == null)
				{
					order = new MOrder(getCtx(), 0, get_TrxName());
					order.setIsSOTrx(false);
					order.setC_DocTypeTarget_ID();
					order.setBPartner(bp);
					order.setC_BPartner_Location_ID(response.getC_BPartner_Location_ID());
					order.setSalesRep_ID(rfq.getSalesRep_ID());
					order.saveEx();
					noOrders++;
					addLog(order.getDocumentNo());
				}

				// For all Qtys
				for (final I_C_RfQResponseLineQty qty : rfqDAO.retrieveResponseQtys(line))
				{
					final I_C_RfQLineQty rfqLineQty = qty.getC_RfQLineQty();
					if (rfqLineQty.isActive() && rfqLineQty.isPurchaseQty())
					{
						final MOrderLine ol = new MOrderLine(order);
						ol.setM_Product_ID(line.getC_RfQLine().getM_Product_ID(), rfqLineQty.getC_UOM_ID());
						ol.setDescription(line.getDescription());

						ol.setQty(rfqLineQty.getQty());

						final BigDecimal price = rfqBL.calculatePriceWithoutDiscount(qty);
						ol.setPrice();
						ol.setPrice(price);

						ol.saveEx();
					}
				}       	// for all Qtys
			}       	// for all Response Lines

			if (order != null)
			{
				response.setC_Order(order);
				InterfaceWrapperHelper.save(response);
			}

		}   // for all Responses

		return "#" + noOrders;
	}
}
