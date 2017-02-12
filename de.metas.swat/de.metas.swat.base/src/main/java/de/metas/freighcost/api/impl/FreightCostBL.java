package de.metas.freighcost.api.impl;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.I_M_FreightCostDetail;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MFreightCost;
import org.adempiere.model.MFreightCostDetail;
import org.adempiere.model.MFreightCostShipper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductPricing;
import org.compiere.model.PO;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_OrderOrInOut;
import de.metas.adempiere.service.IOrderDAO;
import de.metas.freighcost.api.IFreightCostBL;
import de.metas.freighcost.spi.IFreightCostFreeEvaluator;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;

// This class was formerly known as de.metas.adempiere.service.impl.FreightCostSubscriptionBL
public class FreightCostBL implements IFreightCostBL
{
	static final Logger logger = LogManager.getLogger(FreightCostBL.class);

	/**
	 * Checks if the order or shipment is without freight cost for different simple reasons
	 * 
	 * @param orderOrInOut
	 * @return
	 */
	@Override
	public boolean checkIfFree(final I_OrderOrInOut orderOrInOut)
	{
		if (!X_C_Order.DELIVERYVIARULE_Shipper.equals(orderOrInOut.getDeliveryViaRule()))
		{
			logger.debug("No freightcost because of deliveryViaRule={} (not 'shipper'); Order :{}", orderOrInOut.getDeliveryViaRule(), orderOrInOut);
			return true;
		}

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(orderOrInOut.getC_BPartner(), I_C_BPartner.class);
		if (bPartner == null)
		{
			logger.debug("No freigtcost because order has no C_BPartner yet; Order : {}", orderOrInOut);
			return true;
		}

		if (X_C_Order.FREIGHTCOSTRULE_FreightIncluded.equals(orderOrInOut.getFreightCostRule()))
		{
			logger.debug("No freightcost because order has FreightCostRule='{}'; Order: {}", X_C_Order.FREIGHTCOSTRULE_FreightIncluded, orderOrInOut);
			return true;
		}

		final String postageFree = bPartner.getPostageFree();

		if (I_C_BPartner.POSTAGEFREE_Always.equals(postageFree))
		{
			logger.debug("No freigtcost because of receiving c_bpartner has postageFree={}; Order: {}", postageFree, orderOrInOut);
			return true;
		}

		if (orderOrInOut.getM_Shipper_ID() <= 0)
		{
			logger.debug("No freigtcost because M_Shipper_ID={}", orderOrInOut.getM_Shipper_ID());
			return true;
		}
		return false;
	}

	@Override
	public BigDecimal computeFreightCostForOrder(
			final Properties ctx,
			final de.metas.adempiere.model.I_C_Order order,
			final String trxName)
	{
		if (checkIfFree(order))
		{
			return BigDecimal.ZERO;
		}

		if (!(de.metas.adempiere.model.I_C_Order.FREIGHTCOSTRULE_Versandkostenpauschale.equals(order.getFreightCostRule())
				|| X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule())))
		{
			logger.debug(
					"Freigt cost is not computed because of FreightCostRule=" + order.getFreightCostRule()
							+ " (not '" + de.metas.adempiere.model.I_C_Order.FREIGHTCOSTRULE_Versandkostenpauschale
							+ "' or '" + X_C_Order.FREIGHTCOSTRULE_FixPrice
							+ "'); C_Order_ID=" + order.getC_Order_ID());
			return BigDecimal.ZERO;
		}

		if (de.metas.adempiere.model.I_C_Order.FREIGHTCOSTRULE_Versandkostenpauschale.equals(order.getFreightCostRule()))
		{
			// sum up the order's value and return the appropriate freight cost
			BigDecimal orderValue = BigDecimal.ZERO;
			final List<I_C_OrderLine> lines = Services.get(IOrderDAO.class).retrieveOrderLines(order);
			for (final I_C_OrderLine poLine : lines)
			{
				final I_C_OrderLine ol = poLine;

				final BigDecimal qty = poLine.getQtyOrdered();
				orderValue = orderValue.add(ol.getPriceActual().multiply(qty));

			}
			return retrieveFreightCost(ctx, order, orderValue, order.getDateOrdered(), trxName);
		}

		if (X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule()))
		{
			// get the 'freightcost' product and return its price
			final MFreightCost freightCost = MFreightCost.retrieveFor(ctx, order, order.getDateOrdered(), trxName);

			final MProductPricing pp = new MProductPricing(freightCost.getM_Product_ID(), order.getC_BPartner_ID(), BigDecimal.ONE, order.isSOTrx());
			pp.setM_PriceList_ID(order.getM_PriceList_ID());

			return pp.getPriceList();
		}

		throw new AssertionError("The code above contains a logical error; order=" + order);
	}

	@Override
	public BigDecimal computeFreightCostForOrder(final MOrder order)
	{
		return computeFreightCostForOrder(
				order.getCtx(),
				InterfaceWrapperHelper.create(order, de.metas.adempiere.model.I_C_Order.class),
				order.get_TrxName());
	}

	@Override
	public BigDecimal computeFreightCostForInOut(final int inOutId, final String trxName)
	{
		final MInOut inOut = new MInOut(Env.getCtx(), inOutId, trxName);
		final I_OrderOrInOut orderOrInOut = InterfaceWrapperHelper.create(inOut, I_OrderOrInOut.class);

		if (checkIfFree(orderOrInOut))
		{
			return BigDecimal.ZERO;
		}

		final IOrderPA orderPA = Services.get(IOrderPA.class);

		boolean atLeastOneIsFree = false;
		BigDecimal shipmentValue = BigDecimal.ZERO;

		for (final PO poLine : retrieveLines(inOut))
		{
			Check.assume(poLine instanceof MOrderLine || poLine instanceof MInOutLine,
					poLine + " is either MOrderLine or MInOutLine");

			final I_C_OrderLine ol = getOrderLine(trxName, orderPA, poLine);

			// metas: Wenn keine Order, dann keine Frachtkostenberechnung (B067 im Projekt).
			if (ol.getC_Order_ID() == 0)
			{
				return BigDecimal.ZERO;
			}

			final I_C_Order order = orderPA.retrieveOrder(ol.getC_Order_ID(), trxName);
			final String freightCostRule = order.getFreightCostRule();

			if (X_C_Order.FREIGHTCOSTRULE_FreightIncluded.equals(freightCostRule))
			{
				logger.debug(poLine + " is free because it belongs to " + order
						+ " which has freight cost rule 'frieght included' ("
						+ X_C_Order.FREIGHTCOSTRULE_FreightIncluded + ")");

				atLeastOneIsFree = true;
				break;
			}
			else if (de.metas.adempiere.model.I_C_Order.FREIGHTCOSTRULE_Versandkostenpauschale.equals(freightCostRule))
			{
				// check if this is a delivery related to a subscription
				if (poLine instanceof MInOutLine)
				{
					final MInOutLine iol = (MInOutLine)poLine;

					for(IFreightCostFreeEvaluator freightCostFreeEvaluator: freighCostFreeEvaluators)
					{
						if(freightCostFreeEvaluator.isFreighCostFree(iol))
						{
							logger.debug(poLine + " is free because " + freightCostFreeEvaluator + " sais so");
							atLeastOneIsFree = true;
							break;
						}
					}

					if (atLeastOneIsFree)
					{
						break;
					}
				}

				final BigDecimal qty = getQty(poLine);
				shipmentValue = shipmentValue.add(ol.getPriceActual().multiply(qty));
			}
		}
		if (atLeastOneIsFree)
		{
			logger.debug("At least one shipment line is free for M_InOut_ID {}", inOutId);
			return BigDecimal.ZERO;
		}
		return retrieveFreightCost(inOut.getCtx(), orderOrInOut, shipmentValue, inOut.getMovementDate(), inOut.get_TrxName());
	}

	private I_C_OrderLine getOrderLine(final String trxName, final IOrderPA orderPA, final PO olOrIol)
	{
		final I_C_OrderLine ol;

		if (olOrIol instanceof MOrderLine)
		{
			ol = InterfaceWrapperHelper.create(olOrIol, I_C_OrderLine.class);
		}
		else
		{
			ol = InterfaceWrapperHelper.create(((MInOutLine)olOrIol).getC_OrderLine(), I_C_OrderLine.class);
		}
		return ol;
	}

	private BigDecimal getQty(final PO olOrIol)
	{
		final BigDecimal qty;

		if (olOrIol instanceof MOrderLine)
		{
			qty = ((MOrderLine)olOrIol).getQtyOrdered();
		}
		else
		{
			qty = ((MInOutLine)olOrIol).getMovementQty();
		}
		return qty;
	}

	private BigDecimal retrieveFreightCost(final Properties ctx, final I_OrderOrInOut orderOrInOut, final BigDecimal freightValue, final Timestamp date, final String trxName)
	{
		final MFreightCost freightCost = MFreightCost.retrieveFor(ctx, orderOrInOut, date, trxName);

		final MFreightCostShipper fcs = MFreightCostShipper.retrieveForShipper(freightCost, orderOrInOut.getM_Shipper_ID());

		final List<MFreightCostDetail> costDetails = MFreightCostDetail.retrieveFor(fcs);

		BigDecimal freightCostAmt = BigDecimal.ZERO;

		final int ioCountryId = orderOrInOut.getC_BPartner_Location().getC_Location().getC_Country_ID();

		// assumes that the costDetails are ordered by getShipmentValueAmt ascending
		for (final I_M_FreightCostDetail detail : costDetails)
		{
			if (detail.getShipmentValueAmt().compareTo(freightValue) >= 0)
			{
				if (ioCountryId == detail.getC_Country_ID())
				{
					freightCostAmt = detail.getFreightAmt();
					break;
				}
			}
		}
		return freightCostAmt;
	}

	private PO[] retrieveLines(final PO po)
	{
		assert po instanceof MInOut || po instanceof MOrder : po;

		if (po instanceof MInOut)
		{
			return ((MInOut)po).getLines();
		}

		return ((MOrder)po).getLines();
	}

	@Override
	public boolean isFreightCostProduct(final Properties ctx, final int productId, final String trxName)
	{
		return !MFreightCost.retriveFor(ctx, productId, trxName).isEmpty();
	}
	
	
	/**
	 * If there is a freight amt!=0 and if this is a prepay order, then we create an order line for the freight costs.
	 * We can assume at this point that:
	 * <ul>
	 * <li>there is an MFreightCost instance for this order (needed to the ol's product)</li>
	 * <li>the freight amount is correct</li>
	 * </ul>
	 * 
	 * @param order
	 */
	@Override
	public void evalAddFreightCostLine(final MOrder order)
	{
		final boolean isCustomFreightCost =
				X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule()) || X_C_Order.FREIGHTCOSTRULE_FreightIncluded.equals(order.getFreightCostRule());

		final BigDecimal freightAmt = order.getFreightAmt();
		if (freightAmt.signum() != 0)
		{
			if (isPrepayOrder(order) || isCustomFreightCost)
			{
				if (!hasFreightCostLine(order))
				{
					final MFreightCost freightCost = MFreightCost.retrieveFor(order);
					final MOrderLine newOl = new MOrderLine(order);
					newOl.setM_Product_ID(freightCost.getM_Product_ID());

					final MProductPricing pp = new MProductPricing(freightCost.getM_Product_ID(),order.getBill_Location_ID(), BigDecimal.ONE, order.isSOTrx());
					pp.setM_PriceList_ID(order.getM_PriceList_ID());
					final BigDecimal priceList = pp.getPriceList();
					newOl.setPriceList(priceList);
					InterfaceWrapperHelper.create(newOl, I_C_OrderLine.class).setPriceStd(pp.getPriceStd());

					newOl.setQty(BigDecimal.ONE);
					newOl.setPrice(freightAmt);
					newOl.setProcessed(true);
					newOl.saveEx();

					// reload order lines. otherwise the cached lines (without the new one) might be used
					order.getLines(true, null);
					order.reserveStock(MDocType.get(order.getCtx(), order.getC_DocType_ID()), new MOrderLine[] { newOl });
					order.calculateTaxTotal();
				}
			}
		}
	}

	private boolean isPrepayOrder(final MOrder order)
	{
		final String docSubType = order.getC_DocType().getDocSubType();
		final boolean prepayOrder = X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(docSubType)
				|| de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas.equals(docSubType);
		return prepayOrder;
	}
	
	private boolean hasFreightCostLine(final MOrder order)
	{
		final IFreightCostBL freighCostBL = Services.get(IFreightCostBL.class);
		for (final MOrderLine ol : order.getLines())
		{
			if (freighCostBL.isFreightCostProduct(order.getCtx(), ol.getM_Product_ID(), order.get_TrxName()))
			{
				return true;
			}
		}
		return false;
	}

	private final List<IFreightCostFreeEvaluator> freighCostFreeEvaluators = new ArrayList<IFreightCostFreeEvaluator>();
	
	@Override
	public void registerFreightCostFreeEvaluator(IFreightCostFreeEvaluator evaluator)
	{
		Check.assume(!freighCostFreeEvaluators.contains(evaluator), "Every evaluator is added only once");
		freighCostFreeEvaluators.add(evaluator);
	}
}
