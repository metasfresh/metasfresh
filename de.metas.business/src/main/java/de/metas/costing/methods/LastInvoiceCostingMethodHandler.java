package de.metas.costing.methods;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailEvent;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethodHandlerTemplate;
import de.metas.costing.CurrentCost;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class LastInvoiceCostingMethodHandler extends CostingMethodHandlerTemplate
{
	@Override
	protected I_M_CostDetail createCostForPurchaseInvoice(final CostDetailCreateRequest request)
	{
		return createCostDefaultImpl(request);
	}

	@Override
	protected void processPurchaseInvoice(final CostDetailEvent event, final CurrentCost cost)
	{
		final BigDecimal amt = event.getAmt();
		final BigDecimal qty = event.getQty();
		final boolean isReturnTrx = qty.signum() < 0;
		final BigDecimal price = event.getPrice();

		if (!isReturnTrx)
		{
			if (qty.signum() != 0)
			{
				cost.setCurrentCostPrice(price);
			}
			else
			{
				final BigDecimal cCosts = cost.getCurrentCostPrice().add(amt);
				cost.setCurrentCostPrice(cCosts);
			}
		}

		cost.add(amt, qty);
	}

	@Override
	protected void processOutboundTransactionDefaultImpl(final CostDetailEvent event, final CurrentCost cost)
	{
		final BigDecimal qty = event.getQty();
		cost.adjustCurrentQty(qty);
	}

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		return getLastInvoicePrice(costSegment);
	}

	public static BigDecimal getLastInvoicePrice(final CostSegment costSegment)
	{
		final Properties ctx = Env.getCtx();
		final int productId = costSegment.getProductId();
		final int AD_Org_ID = costSegment.getOrgId();
		final int M_ASI_ID = costSegment.getAttributeSetInstanceId();
		final MAcctSchema as = MAcctSchema.get(ctx, costSegment.getAcctSchemaId());
		final int C_Currency_ID = as.getC_Currency_ID();

		String sql = "SELECT currencyConvert(il.PriceActual, i.C_Currency_ID, ?, i.DateAcct, i.C_ConversionType_ID, il.AD_Client_ID, il.AD_Org_ID) "
				// ,il.PriceActual, il.QtyInvoiced, i.DateInvoiced, il.Line
				+ "FROM C_InvoiceLine il "
				+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
				+ "WHERE il.M_Product_ID=?"
				+ " AND i.IsSOTrx='N'";
		if (AD_Org_ID != 0)
		{
			sql += " AND il.AD_Org_ID=?";
		}
		else if (M_ASI_ID != 0)
		{
			sql += " AND il.M_AttributeSetInstance_ID=?";
		}
		sql += " ORDER BY i.DateInvoiced DESC, il.Line DESC";
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, C_Currency_ID);
			pstmt.setInt(2, productId);
			if (AD_Org_ID != 0)
			{
				pstmt.setInt(3, AD_Org_ID);
			}
			else if (M_ASI_ID != 0)
			{
				pstmt.setInt(3, M_ASI_ID);
			}
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				return rs.getBigDecimal(1);
			}
			else
			{
				return null;
			}
		}
		catch (final Exception e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}	// getLastInvoicePrice

}
