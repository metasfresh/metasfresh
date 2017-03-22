package de.metas.commission.util;

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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.ProductPriceQuery;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.I_M_ProductPrice;
import de.metas.commission.interfaces.I_M_ProductScalePrice;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.I_M_DiscountSchema;
import de.metas.logging.LogManager;
import de.metas.product.IProductPA;

public final class CommissionTools
{
	private static final Logger logger = LogManager.getLogger(CommissionTools.class);

	private CommissionTools()
	{
	}

	/**
	 * <b>IMPORTANT: The commission points from the price list are not multiplied with qty</b>
	 * 
	 * @param productId
	 * @param qty
	 * @param pl
	 * @param time
	 * @param trxName
	 * @return
	 */
	@Cached
	public static BigDecimal retrieveCommissionPoints(
			final int productId,
			final BigDecimal qty,
			final MPriceList pl,
			final Timestamp time,
			final String trxName)
	{
		CommissionTools.logger.info("productId=" + productId + ", qty=" + qty + ", pl=" + pl + ", time=" + time + ",trxName=" + trxName);

		final BigDecimal commissionPoints;

		final I_M_ProductScalePrice psp = retrieveProductScalePrice(pl, productId, qty, trxName);

		if (psp != null)
		{
			commissionPoints = psp.getCommissionPoints();
			CommissionTools.logger.debug("Got " + commissionPoints + " from product scale price");
		}
		else
		{
			final MPriceListVersion plv = pl.getPriceListVersion(time);
			if (plv == null)
			{
				// TODO translate
				throw new CommissionException(
						"Fehlende Preislistenversion fuer Preisliste {} zum Zeitpunkt {}",
						new Object[] { pl.getName(), time });
			}
			final MProductPrice[] pps =
					plv.getProductPrice(" AND " + org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_Product_ID + "=" + productId);

			if (pps.length > 0)
			{
				commissionPoints = InterfaceWrapperHelper.create(pps[0], I_M_ProductPrice.class).getCommissionPoints();
				CommissionTools.logger.debug("Got " + commissionPoints + " from product price");
			}
			else
			{
				commissionPoints = BigDecimal.ZERO;
				CommissionTools.logger.debug("No pricing info for product. Assuming zero");
			}
		}
		return commissionPoints;
	};

	public static String mkSponsorPathMsg(
			final List<? extends I_C_Sponsor_SalesRep> ssrs,
			final Properties ctx)
	{
		final StringBuilder sb = new StringBuilder();

		for (final I_C_Sponsor_SalesRep ssr : ssrs)
		{
			sb.append(mkRelationString(ctx, ssr));
			sb.append("\n");
		}
		return sb.toString();
	}

	private static String mkRelationString(final Properties ctx,
			final I_C_Sponsor_SalesRep ssr)
	{
		final StringBuilder sb = new StringBuilder();

		final I_C_Sponsor sponsorParent = ssr.getC_Sponsor_Parent();
		final I_C_Sponsor sponsor = ssr.getC_Sponsor();

		final DateFormat fmt =
				DateFormat.getDateInstance(DateFormat.SHORT, Env.getLanguage(ctx).getLocale());

		sb.append(sponsor.getSponsorNo());
		sb.append(" (");
		sb.append(fmt.format(ssr.getValidFrom()));
		sb.append(" - ");
		sb.append(fmt.format(ssr.getValidTo()));
		sb.append(")");

		if (ssr.getC_Sponsor_Parent_ID() > 0)
		{
			sb.append(" -> ");
			sb.append(sponsorParent.getSponsorNo());
		}
		return sb.toString();
	}

	public static String mkParentChildMessage(

			final List<List<I_C_Sponsor_SalesRep>> pathsToSSR,
			final I_C_Sponsor_SalesRep ssr,
			final Properties ctx,
			final String trxName)
	{

		final StringBuilder sb = new StringBuilder();
		sb.append(mkRelationString(ctx, ssr));
		sb.append(": ");

		for (int i = 0; i < pathsToSSR.size(); i++)
		{
			if (pathsToSSR.size() > 1)
			{
				sb.append(i + 1);
				sb.append(".\n");
			}
			else
			{
				sb.append("\n");
			}
			sb.append(mkSponsorPathMsg(pathsToSSR.get(i), ctx));

		}
		final Object[] msgParams = { ssr.getC_Sponsor_Parent().getSponsorNo(),
				ssr.getC_Sponsor().getSponsorNo(), sb.toString() };

		return Services.get(IMsgBL.class).getMsg(ctx, Messages.SPONSOR_IS_CHILD_3P, msgParams);
	}

	public static I_M_DiscountSchema retrieveDiscountSchemaForValue(
			final Properties ctx, final String discountSchemaValue,
			final String trxName)
	{

		final String whereClause = I_M_DiscountSchema.Value + "=?";
		final Object[] parameters = { discountSchemaValue };

		return new Query(ctx, org.compiere.model.I_M_DiscountSchema.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true).setClient_ID()
				.setParameters(parameters)
				.setOrderBy(org.compiere.model.I_M_DiscountSchema.COLUMNNAME_M_DiscountSchema_ID)
				.first(I_M_DiscountSchema.class);
	}

	public static boolean isEmployeeInvoice(final I_C_Invoice invoice)
	{

		final boolean completedPayroll = DocAction.STATUS_Completed
				.equals(invoice.getDocStatus())
				&& Constants.DOCBASETYPE_AEInvoice.equals(invoice
						.getC_DocType().getDocBaseType());

		return completedPayroll;
	}
	
	public static I_M_ProductScalePrice retrieveProductScalePrice(final I_M_PriceList priceList, final int productId, final BigDecimal qty, final String trxName)
	{
		final I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).retrievePriceListVersionOrNull(priceList
				, SystemTime.asDate() //date
				, null // processed
				);
		if(plv == null)
		{
			return null;
		}
		
		final org.compiere.model.I_M_ProductPrice productPrice = ProductPriceQuery.retrieveMainProductPriceIfExists(plv, productId)
				.orElse(null);
		
		if(productPrice == null)
		{
			return null;
		}
		if(!productPrice.isUseScalePrice())
		{
			return null;
		}
		
		final boolean createNew = false;
		final org.adempiere.model.I_M_ProductScalePrice productScalePrice = Services.get(IProductPA.class).retrieveOrCreateScalePrices(productPrice.getM_ProductPrice_ID(), qty, createNew, trxName);
		return InterfaceWrapperHelper.create(productScalePrice, I_M_ProductScalePrice.class);
	}
}
