package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.Properties;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductCategory;
import org.slf4j.Logger;

import de.metas.adempiere.model.IProductAware;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.IInstanceTrigger;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.util.CommissionTools;
import de.metas.logging.LogManager;

public class InstanceTriggerBL implements IInstanceTriggerBL
{
	private static final Logger logger = LogManager.getLogger(InstanceTriggerBL.class);

	@Override
	public String setCommissionPoints(final Properties ctx, final IInstanceTrigger it, final boolean nullIfOk, final String trxName)
	{
		if (isCommissionExempt(it))
		{
			InstanceTriggerBL.logger.info("Setting CommissionPoints=0 for " + it + ", because is commission exempt");
			it.setCommissionPoints(BigDecimal.ZERO);
		}
		else if (it.isManualCommissionPoints())
		{
			InstanceTriggerBL.logger.info("Not setting CommissionPoints for " + it + ", because of IsManualCommissionPoints=" + it.isManualCommissionPoints());
		}
		else
		{
			final BigDecimal commissionPoints = retrieveCommissionPoints(ctx, it, trxName);
			it.setCommissionPoints(commissionPoints);
		}
		return nullIfOk ? null : "";
	}

	@Override
	public String setCommissionPointsSum(final Properties ctx, final IInstanceTrigger it, final boolean nullIfOk, final String trxName)
	{
		if (isCommissionExempt(it))
		{
			InstanceTriggerBL.logger.info("Setting CommissionPointsSum=0 for " + it + ", because is commission exempt");
			it.setCommissionPointsSum(BigDecimal.ZERO);
		}
		else
		{
			// note: setting commissionPointsSum, even if it.isManualCommissionPoints(). We assume that
			// 'commisisonPoints' is set correctly by the user and that commissionPointssum is correctly computed.
			final BigDecimal commissionPointsSum = retrieveCommissionPointsSum(ctx, it, trxName);
			it.setCommissionPointsSum(commissionPointsSum);
		}
		return nullIfOk ? null : "";
	}

	@Override
	public BigDecimal retrieveCommissionPoints(
			final Properties ctx,
			final IInstanceTrigger it,
			final String trxName)
	{
		if (isCommissionExempt(it))
		{
			InstanceTriggerBL.logger.info("Returning 0 forNot overriding CommissionPoints " + it + " because is commission exempt");
			return BigDecimal.ZERO;
		}
		else if (it.isManualCommissionPoints())
		{
			InstanceTriggerBL.logger.info("Returning the current value for " + it + ", because of IsManualCommissionPoints=" + it.isManualCommissionPoints());
			return it.getCommissionPoints();
		}
		final BigDecimal commissionPoints;

		if (it instanceof I_C_OrderLine)
		{
			final I_C_OrderLine ol = (I_C_OrderLine)it;

			int plvId = ol.getM_PriceList_Version_ID();
			
			if(plvId <= 0)
			{
				final I_M_PriceList_Version plv = Services.get(IOrderLineBL.class).getPriceListVersion(ol);
				
				plvId = plv.getM_PriceList_Version_ID();
			}

			if (plvId <= 0 || ol.getQtyOrdered().signum() <= 0 || ol.getM_Product_ID() <= 0)
			{
				commissionPoints = BigDecimal.ZERO;
			}
			else
			{
				final MPriceListVersion plv = new MPriceListVersion(ctx, plvId, trxName);

				final BigDecimal pointsPriceList =
						CommissionTools.retrieveCommissionPoints(
								ol.getM_Product_ID(),
								ol.getQtyEntered(),
								plv.getPriceList(),
								ol.getDateOrdered(),
								trxName);

				commissionPoints = pointsPriceList;
			}
		}
		else if (it instanceof I_C_InvoiceLine)
		{
			commissionPoints = retrievePointsForInvoice((I_C_InvoiceLine)it, trxName);
		}
		else
		{
			throw MiscUtils.illegalArgumentEx(it, "IInstanceTrigger");
		}

		return commissionPoints;
	}

	/**
	 * If we have an OrderLine for This Invoice Line use the Commission Points of this Order Line, otherwise retrieve them from the Price List
	 * 
	 * @param it
	 * @param trxName
	 * @return Commission Points for an InvoiceLine
	 */
	private BigDecimal retrievePointsForInvoice(final I_C_InvoiceLine il, final String trxName)
	{
		final I_C_Invoice invoice = il.getC_Invoice();
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(il.getC_OrderLine(), I_C_OrderLine.class);

		BigDecimal comPoints;

		if (ol != null)
		{
			comPoints = ol.getCommissionPoints();
		}
		else
		{
			if (invoice.getM_PriceList_ID() == MPriceList.M_PriceList_ID_None) // us1184
			{
				InstanceTriggerBL.logger.info("Returning 0 forNot overriding CommissionPoints " + il + " because of M_PriceList_ID=None");
				comPoints = BigDecimal.ZERO;
			}
			else
			{
				final BigDecimal pointsPriceList =
						CommissionTools.retrieveCommissionPoints(
								il.getM_Product_ID(),
								il.getQtyInvoiced(),
								(MPriceList)invoice.getM_PriceList(),
								invoice.getDateInvoiced(), trxName);

				comPoints = pointsPriceList;
			}
		}

		// negate the points for credit memos (only points != 0)
		if (Services.get(IInvoiceBL.class).isARCreditMemo(invoice)
				&& !comPoints.equals(BigDecimal.ZERO))
		{
			InstanceTriggerBL.logger.info(il + " belongs to credit memo. Returning " + comPoints.negate());
			comPoints = comPoints.negate();
		}

		return comPoints;
	}
	
	public BigDecimal retrieveCommissionPointsSum(
			final Properties ctx,
			final IInstanceTrigger it,
			final String trxName)
	{
		if (isCommissionExempt(it))
		{
			InstanceTriggerBL.logger.info("Returning 0 for " + it + " because is commission exempt");
			return BigDecimal.ZERO;
		}
		final BigDecimal commissionPointsSum;

		if (it instanceof I_C_OrderLine)
		{
			final I_C_OrderLine ol = (I_C_OrderLine)it;
			commissionPointsSum = ol.getCommissionPoints().multiply(ol.getQtyOrdered());
		}
		else if (it instanceof I_C_InvoiceLine)
		{
			final I_C_InvoiceLine il = (I_C_InvoiceLine)it;

			if (it.isManualCommissionPoints())
			{
				commissionPointsSum = il.getCommissionPoints().multiply(il.getQtyInvoiced());
			}
			else
			{
				final I_C_Invoice invoice = il.getC_Invoice();

				if (invoice.getM_PriceList_ID() == MPriceList.M_PriceList_ID_None) // us1184
				{
					commissionPointsSum = il.getCommissionPoints().multiply(il.getQtyInvoiced());
				}
				else
				{
					final MPriceList pl = (MPriceList)invoice.getM_PriceList();

					final BigDecimal pointsPriceList =
							CommissionTools.retrieveCommissionPoints(
									il.getM_Product_ID(), il.getQtyInvoiced(), pl, invoice.getDateInvoiced(), trxName);

					if (Services.get(IInvoiceBL.class).isARCreditMemo(invoice))
					{
						InstanceTriggerBL.logger.info(il + " belongs to credit memo. Returning " + pointsPriceList.negate());
						commissionPointsSum = pointsPriceList.multiply(il.getQtyInvoiced()).negate();
					}
					else
					{
						commissionPointsSum = pointsPriceList.multiply(il.getQtyInvoiced());
					}
				}
			}
		}
		else
		{
			throw MiscUtils.illegalArgumentEx(it, "IInstanceTrigger");
		}

		return commissionPointsSum;
	}

	@Override
	public void updateCommissionPointsNet(final IInstanceTrigger it)
	{
		if (isCommissionExempt(it))
		{
			InstanceTriggerBL.logger.info("Setting CommissionPointsNet=0 for " + it + " because is commission exempt");
			it.setCommissionPointsNet(BigDecimal.ZERO);
			return;
		}

		final de.metas.adempiere.service.IOrderLineBL swatOrderLineBL = Services.get(de.metas.adempiere.service.IOrderLineBL.class);

		final BigDecimal commissionPointsNetPerUnit =
				swatOrderLineBL.subtractDiscount(it.getCommissionPoints(), it.getDiscount(), 2);

		if (it instanceof I_C_OrderLine)
		{
			final I_C_OrderLine ol = (I_C_OrderLine)it;
			it.setCommissionPointsNet(commissionPointsNetPerUnit.multiply(ol.getQtyOrdered()));
		}
		else
		{
			final I_C_InvoiceLine ol = (I_C_InvoiceLine)it;
			it.setCommissionPointsNet(commissionPointsNetPerUnit.multiply(ol.getQtyInvoiced()));
		}
	}

	@Override
	public boolean isInCorrectProductCategory(final I_C_AdvCommissionTerm term,
			final I_C_Sponsor sponsor, final Object ilOrOl)
	{

		final IContractBL contractBL = Services.get(IContractBL.class);
		final MProductCategory productCategory = contractBL.retrieveProductCategory(term, sponsor);

		if (productCategory == null)
		{
			return true;
		}

		final I_M_Product product = InterfaceWrapperHelper.create(ilOrOl, IProductAware.class).getM_Product();
		if (product == null)
		{
			return false;
		}
		return productCategory.getM_Product_Category_ID() == product.getM_Product_Category_ID();
	}

	@Override
	public boolean isCommissionExempt(final IInstanceTrigger it)
	{
		if (it instanceof I_C_OrderLine)
		{
			final I_C_OrderLine orderLine = (I_C_OrderLine)it;
			if (orderLine.getM_Promotion_ID() > 0)
			{
				InstanceTriggerBL.logger.info("Commission exempt: " + it + ", because of M_Promotion_ID=" + orderLine.getM_Promotion_ID());
				return true;
			}

		}

		return false;
	}
}
