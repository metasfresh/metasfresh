package de.metas.rfq.process;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MOrder;

import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.quantity.Quantity;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.exceptions.NoCompletedRfQResponsesFoundException;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import lombok.NonNull;




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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
public class C_RfQ_CreatePO extends JavaProcess
{
	// services
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);

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
				orderBL.setDocTypeTargetIdAndUpdateDescription(order, p_C_DocType_ID);
			}
			else
			{
				orderBL.setDocTypeTargetId(order);
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
					createAndSaveOrderLine(order, rfqResponseLineQty);
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
					orderBL.setDocTypeTargetId(order);
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
						final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

						final I_C_OrderLine ol = orderLineBL.createOrderLine(order);

						ol.setM_Product_ID(line.getC_RfQLine().getM_Product_ID());
						ol.setC_UOM_ID(rfqLineQty.getC_UOM_ID());
						ol.setDescription(line.getDescription());

						setQtyOfOrderLine(rfqLineQty, ol);
						setPriceOfOrderLine(qty, ol);
						save(ol);
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

	private void createAndSaveOrderLine(
			@NonNull final MOrder order,
			@NonNull final I_C_RfQResponseLineQty rfqResponseLineQty)
	{

		final I_C_RfQLineQty rfqLineQty = rfqResponseLineQty.getC_RfQLineQty();
		final boolean createLine = rfqLineQty.isActive() && rfqLineQty.isPurchaseQty();
		if (!createLine)
		{
			return;
		}

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		final I_C_OrderLine ol = orderLineBL.createOrderLine(order);

		final I_C_RfQResponseLine rfqResponseLine = rfqResponseLineQty.getC_RfQResponseLine();
		ol.setM_Product_ID(rfqResponseLine.getM_Product_ID());
		ol.setC_UOM_ID(rfqLineQty.getC_UOM_ID());
		ol.setDescription(rfqResponseLine.getDescription());

		setQtyOfOrderLine(rfqLineQty, ol);
		setPriceOfOrderLine(rfqResponseLineQty, ol);
		save(ol);
	}

	private void setPriceOfOrderLine(
			@NonNull final I_C_RfQResponseLineQty rfqResponseLineQty,
			@NonNull final I_C_OrderLine ol)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		orderLineBL.updatePrices(ol);
		final BigDecimal price = rfqBL.calculatePriceWithoutDiscount(rfqResponseLineQty);
		ol.setPriceEntered(price);
		ol.setPriceActual(price);
	}

	private void setQtyOfOrderLine(
			@NonNull final I_C_RfQLineQty rfqLineQty,
			@NonNull final I_C_OrderLine ol)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		ol.setQtyEntered(rfqLineQty.getQty());
		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(
				Quantity.of(rfqLineQty.getQty(), rfqLineQty.getC_UOM()),
				ol.getM_Product_ID());
		ol.setQtyOrdered(qtyOrdered.getQty());
	}
}
