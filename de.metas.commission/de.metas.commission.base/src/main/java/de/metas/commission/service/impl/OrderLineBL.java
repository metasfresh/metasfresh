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
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Product;
import org.compiere.model.MDiscountSchema;
import org.slf4j.Logger;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.custom.type.ISalesRefFactCollector;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSystem_Type;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.service.IOrderLineBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;

public class OrderLineBL implements IOrderLineBL
{

	private static final Logger logger = LogManager.getLogger(OrderLineBL.class);

	private final Set<Integer> ignoredOlIds = new HashSet<Integer>();

	@Override
	public void ignore(final int orderLineId)
	{
		ignoredOlIds.add(orderLineId);
	}

	@Override
	public void unignore(final int orderLineId)
	{
		ignoredOlIds.remove(orderLineId);
	}

	/**
	 * @param oldVal may be <code>null</code>. In that case, zero is substituted.
	 */
	@Override
	public String updateDiscounts(
			final Properties ctx,
			final I_C_OrderLine ol,
			final BigDecimal oldVal,
			final boolean ignoreOlPoints,
			final boolean nullIfOk,
			final String trxName)
	{
		if (ignoredOlIds.contains(ol.getC_OrderLine_ID()))
		{
			return nullIfOk ? null : "";
		}

		final BigDecimal oldValToUse = oldVal == null ? BigDecimal.ZERO : oldVal;

		final BigDecimal olCommissionPointsSum;
		if (ignoreOlPoints)
		{
			olCommissionPointsSum = BigDecimal.ZERO;
		}
		else
		{
			olCommissionPointsSum = ol.getCommissionPointsSum();
		}

		if (oldValToUse.compareTo(olCommissionPointsSum) == 0)
		{
			// no change - nothing to do
			return nullIfOk ? null : "";
		}

		final org.compiere.model.I_C_Order order = ol.getC_Order();

		return updateDiscounts(ctx, order, nullIfOk, ol, trxName);
	}

	@Override
	public String updateDiscounts(final Properties ctx, final org.compiere.model.I_C_Order orderPO, final boolean nullIfOk, final String trxName)
	{
		final I_C_OrderLine ol = null;
		return updateDiscounts(ctx, orderPO, nullIfOk, ol, trxName);
	}

	private String updateDiscounts(final Properties ctx, final org.compiere.model.I_C_Order orderPO, final boolean nullIfOk, final I_C_OrderLine ol, final String trxName)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(orderPO, I_C_Order.class);
		if (!order.isSOTrx())
		{
			// nothing to do
			return nullIfOk ? null : "";
		}
		//
		// find out if the current customer is a salesrep
		final I_C_BPartner customer = order.getBill_BPartner();
		if (!customer.isSalesRep())
		{
			OrderLineBL.logger.debug(customer + " is not a sales rep and thus doesn't receive discount");
			return nullIfOk ? null : "";
		}

		I_C_AdvCommissionSalaryGroup rank = null;
		MDiscountSchema newDs = null;

		final List<I_C_Sponsor> sponsorsAtDate = Services.get(ISponsorDAO.class).retrieveForSalesRepAt(customer, order.getDateOrdered());
		if (sponsorsAtDate.isEmpty())
		{
			// TODO -> AD_Message oder [Ticket#2010072610000044] impl.
			return "Kunde " + customer.getValue() + " ist kein VP am Datum " + order.getDateOrdered();
		}

		final IOrderPA orderPA = Services.get(IOrderPA.class);
		final List<de.metas.interfaces.I_C_OrderLine> orderLines = orderPA.retrieveOrderLines(orderPO, de.metas.interfaces.I_C_OrderLine.class);

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		for (final I_C_Sponsor sponsor : sponsorsAtDate)
		{
			final I_C_AdvCommissionCondition contract = sponsorBL.retrieveContract(ctx, sponsor, order.getDateOrdered(), trxName);
			Check.assume(contract != null, sponsor + " has a contract (because it belongs to a sales rep)");

			final ISalesRefFactCollector srfCollector = sponsorBL.retrieveSalesRepFactCollector(ctx, contract, order.getDateOrdered(), trxName);

			if (srfCollector == null)
			{
				continue;
			}
			if (sponsor.isManualRank())
			{
				rank = sponsor.getC_AdvCommissionSalaryGroup();
			}
			else
			{
				// explicitly add ol's commission points, because we don't know if ol has already been stored and is
				// thus contained in 'orderLines'
				BigDecimal sumFromOrder;
				sumFromOrder = getPoints(ol, srfCollector);

				for (final de.metas.interfaces.I_C_OrderLine currentOlPO : orderLines)
				{
					if (ol != null && currentOlPO.getC_OrderLine_ID() == ol.getC_OrderLine_ID())
					{
						// if the ol has already been stored, don't add its points again
						continue;
					}
					final I_C_OrderLine currentCommissionOl = InterfaceWrapperHelper.create(currentOlPO, I_C_OrderLine.class);
					sumFromOrder = sumFromOrder.add(getPoints(currentCommissionOl, srfCollector));
				}

				// 04639 - product needed in creation of commission context
				// FIXME Should this always be the product? WHat if ol is null?
				Check.assume(ol != null, "nut null order line");
				final I_M_Product product = ol.getM_Product();

				// FIXME Is it ok for ICommissionType to be null in this point?

				final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, order.getDateOrdered(), null,
						srfCollector.getComSystemType().getC_AdvComSystem(), product);

				final BigDecimal sumFromFacts = retrieveSumFromFacts(commissionCtx, order, srfCollector);

				final BigDecimal sumAll = sumFromFacts.add(sumFromOrder);

				assert contract.getC_AdvComSystem_ID() == srfCollector.getComSystemType().getC_AdvComSystem_ID();
				final I_C_AdvCommissionSalaryGroup currentSponsorRankPoints = srfCollector.retrieveSalaryGroup(
						commissionCtx,
						sumAll,
						X_C_AdvComSalesRepFact.STATUS_Prognose);
				assert currentSponsorRankPoints != null;
				if (rank == null || rank.getSeqNo() < currentSponsorRankPoints.getSeqNo())
				{
					rank = currentSponsorRankPoints;
				}

				final I_C_AdvCommissionSalaryGroup currentSponsorRank =
						sponsorBL.retrieveRank(ctx, sponsor, contract.getC_AdvComSystem(), order.getDateOrdered(), X_C_AdvComSalesRepFact.STATUS_Prognose, trxName);

				if (currentSponsorRank.getSeqNo() > currentSponsorRankPoints.getSeqNo())
				{
					rank = currentSponsorRank;
				}
			}
			newDs = srfCollector.retrieveDiscountSchema(rank, sponsor, order.getDateOrdered());
		}

		final int precision = orderPO.getC_Currency().getStdPrecision();

		if (ol != null)
		{
			// update price for our current ol
			updateDiscount(ol, newDs, precision);
		}
		// update prices for the other ols of this order
		for (final de.metas.interfaces.I_C_OrderLine currentOl : orderLines)
		{
			if (ol != null && currentOl.getC_OrderLine_ID() == ol.getC_OrderLine_ID())
			{
				continue;
			}
			if (updateDiscount(currentOl, newDs, precision))
			{
				final de.metas.adempiere.service.IOrderLineBL swatOrderLineBL =
						Services.get(de.metas.adempiere.service.IOrderLineBL.class);

				ignore(currentOl.getC_OrderLine_ID());
				swatOrderLineBL.ignore(currentOl.getC_OrderLine_ID());

				InterfaceWrapperHelper.save(currentOl);

				swatOrderLineBL.unignore(currentOl.getC_OrderLine_ID());
				unignore(currentOl.getC_OrderLine_ID());
			}
		}
		return nullIfOk ? null : "";
	}

	private BigDecimal getPoints(final I_C_OrderLine ol, final ISalesRefFactCollector srfCollector)
	{
		if (ol == null)
		{
			return BigDecimal.ZERO;
		}

		BigDecimal sumFromOrder;

		final boolean useNetPoints = X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Netto.equals(srfCollector.getComSystemType().getUseGrossOrNetPoints());
		if (useNetPoints)
		{
			sumFromOrder = ol.getCommissionPointsNet();
		}
		else
		{
			sumFromOrder = ol.getCommissionPointsSum();
		}
		return sumFromOrder;
	}

	private BigDecimal retrieveSumFromFacts(
			final ICommissionContext commissionCtx,
			final I_C_Order order,
			final ISalesRefFactCollector srfCollector)
	{
		BigDecimal sumFromFacts = BigDecimal.ZERO;

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);
		final Timestamp date = order.getDateOrdered();

		final Properties ctx = commissionCtx.getCtx();
		final String trxName = commissionCtx.getTrxName();
		final I_C_Sponsor sponsor = commissionCtx.getC_Sponsor();
		final ICommissionType type = commissionCtx.getCommissionType();
		final I_M_Product product = commissionCtx.getM_Product();

		final I_C_AdvCommissionCondition contract = sponsorBL.retrieveContract(ctx, sponsor, date, trxName);
		assert contract != null : sponsor + " expected to have a contract (because it belongs to a sales rep)";

		final I_C_Period period = sponsorBL.retrieveCommissionPeriod(ctx, contract, date, trxName);

		final String[] srfStatus = {
				X_C_AdvComSalesRepFact.STATUS_Prov_Relevant,
				X_C_AdvComSalesRepFact.STATUS_Prognose };

		BigDecimal currentSponsorSum = BigDecimal.ZERO;

		final ICommissionContext commissionCtx2 = Services.get(ICommissionContextFactory.class).create(sponsor, order.getDateOrdered(), type, product);
		for (final String factName : srfCollector.getTurnoverFactNames(commissionCtx2))
		{
			currentSponsorSum = currentSponsorSum.add(srfBL.retrieveSum(sponsor, factName, period, srfStatus));
		}

		if (currentSponsorSum.compareTo(sumFromFacts) > 0)
		{
			sumFromFacts = currentSponsorSum;
		}
		return sumFromFacts;
	}

	/**
	 * Note: doesn't touch update the priceUOM.
	 * 
	 * @param olGen ol whose discount needs updating. Note: The type is org.compiere.model.I_C_OrderLine, so MOrderLines as well as any kind of _C_OrderLine can be added
	 * @param newDs
	 * @param precision
	 * @return
	 */
	private boolean updateDiscount(
			final org.compiere.model.I_C_OrderLine olGen,
			final MDiscountSchema newDs, final int precision)
	{

		final IMDiscountSchemaBL discountSchemaBL = Services.get(IMDiscountSchemaBL.class);
		// Order Line wrapped to commission's I_C_OrderLine
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(olGen, I_C_OrderLine.class);

		if (Services.get(IInstanceTriggerBL.class).isCommissionExempt(ol))
		{
			OrderLineBL.logger.info("Skipping " + olGen + " because is commission exempt");
			return false;
		}

		final Boolean manualDiscount = ol.isManualDiscount();
		if (manualDiscount != null && manualDiscount)
		{
			return false;
		}

		final I_M_DiscountSchema discountSchema = InterfaceWrapperHelper.create(newDs, I_M_DiscountSchema.class);

		final BigDecimal priceEntered = olGen.getPriceEntered();
		final I_M_AttributeSetInstance attributeSetInstance = ol.getM_AttributeSetInstance();
		final List<I_M_AttributeInstance> instances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(attributeSetInstance);

		final BigDecimal newDiscount;
		if (newDs != null)
		{
			newDiscount = discountSchemaBL.calculateDiscount(
					discountSchema,
					olGen.getQtyOrdered(),
					priceEntered,
					olGen.getM_Product_ID(),
					0,
					instances,
					null);
		}
		else
		{
			newDiscount = BigDecimal.ZERO;
		}

		olGen.setDiscount(newDiscount);

		if (newDs != null)
		{
			final BigDecimal discountedPrice = discountSchemaBL.calculatePrice(
					discountSchema,
					olGen.getQtyOrdered(),
					priceEntered,
					olGen.getM_Product_ID(),
					0,
					instances,
					null).
					setScale(precision, RoundingMode.UP);

			olGen.setPriceActual(discountedPrice);
			OrderLineBL.logger.info(olGen + " changing Discount from "
					+ olGen.getDiscount() + "% to " + newDiscount
					+ "%; New PriceActual=" + discountedPrice);

			olGen.setLineNetAmt(discountedPrice.multiply(olGen.getQtyOrdered()));
		}
		else
		{
			olGen.setPriceActual(priceEntered);
		}
		return true;
	}
}
