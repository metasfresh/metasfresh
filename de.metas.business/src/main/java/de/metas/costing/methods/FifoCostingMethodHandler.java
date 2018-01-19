package de.metas.costing.methods;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.costing.CostSegment;
import de.metas.currency.ICurrencyBL;

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

public class FifoCostingMethodHandler extends FifoOrLifoCostingMethodHandler
{
	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final Properties ctx = Env.getCtx();
		final int productId = costSegment.getProductId();
		final int AD_Org_ID = costSegment.getOrgId();
		final int M_AttributeSetInstance_ID = costSegment.getAttributeSetInstanceId();
		final MAcctSchema as = MAcctSchema.get(ctx, costSegment.getAcctSchemaId());

		String sql = "SELECT t.MovementQty, mi.Qty, il.QtyInvoiced, il.PriceActual,"
				+ " i.C_Currency_ID, i.DateAcct, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID, t.M_Transaction_ID "
				+ "FROM M_Transaction t"
				+ " INNER JOIN M_MatchInv mi ON (t.M_InOutLine_ID=mi.M_InOutLine_ID)"
				+ " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
				+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
				+ "WHERE t.M_Product_ID=?";
		if (AD_Org_ID != 0)
		{
			sql += " AND t.AD_Org_ID=?";
		}
		else if (M_AttributeSetInstance_ID != 0)
		{
			sql += " AND t.M_AttributeSetInstance_ID=?";
		}
		sql += " ORDER BY t.M_Transaction_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//
		final int oldTransaction_ID = 0;
		final ArrayList<QtyCost> fifo = new ArrayList<>();
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, productId);
			if (AD_Org_ID != 0)
			{
				pstmt.setInt(2, AD_Org_ID);
			}
			else if (M_AttributeSetInstance_ID != 0)
			{
				pstmt.setInt(2, M_AttributeSetInstance_ID);
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(10);
				if (M_Transaction_ID == oldTransaction_ID)
				{
					continue;	// assuming same price for receipt
				}
				M_Transaction_ID = oldTransaction_ID;
				//
				final BigDecimal matchQty = rs.getBigDecimal(2);
				if (matchQty == null)	// out (negative)
				{
					if (fifo.size() > 0)
					{
						QtyCost pp = fifo.get(0);
						pp.addQty(movementQty);
						BigDecimal remainder = pp.getQty();
						if (remainder.signum() == 0)
						{
							fifo.remove(0);
						}
						else
						{
							while (remainder.signum() != 0)
							{
								if (fifo.size() == 1)	// Last
								{
									pp.setCost(BigDecimal.ZERO);
									remainder = BigDecimal.ZERO;
								}
								else
								{
									fifo.remove(0);
									pp = fifo.get(0);
									pp.addQty(movementQty);
									remainder = pp.getQty();
								}
							}
						}
					}
					else
					{
						final QtyCost pp = new QtyCost(movementQty, BigDecimal.ZERO);
						fifo.add(pp);
					}
					continue;
				}
				// Assumption: everything is matched
				final BigDecimal price = rs.getBigDecimal(4);
				final int C_Currency_ID = rs.getInt(5);
				final Timestamp DateAcct = rs.getTimestamp(6);
				final int C_ConversionType_ID = rs.getInt(7);
				final int Client_ID = rs.getInt(8);
				final int Org_ID = rs.getInt(9);
				final BigDecimal cost = currencyConversionBL.convert(ctx, price,
						C_Currency_ID, as.getC_Currency_ID(),
						DateAcct, C_ConversionType_ID, Client_ID, Org_ID);

				// Add Stock
				boolean used = false;
				if (fifo.size() == 1)
				{
					final QtyCost pp = fifo.get(0);
					if (pp.getQty().signum() < 0)
					{
						pp.addQty(movementQty);
						if (pp.getQty().signum() == 0)
						{
							fifo.remove(0);
						}
						else
						{
							pp.setCost(cost);
						}
						used = true;
					}

				}
				if (!used)
				{
					final QtyCost pp = new QtyCost(movementQty, cost);
					fifo.add(pp);
				}
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (fifo.size() == 0)
		{
			return null;
		}
		final QtyCost pp = fifo.get(0);
		return pp.getCost();
	}
}
