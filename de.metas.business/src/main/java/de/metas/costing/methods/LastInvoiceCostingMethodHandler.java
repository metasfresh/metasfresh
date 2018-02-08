package de.metas.costing.methods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingMethodHandlerTemplate;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.quantity.Quantity;

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

@Component
public class LastInvoiceCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public LastInvoiceCostingMethodHandler(final ICurrentCostsRepository currentCostsRepo, final ICostDetailRepository costDetailsRepo)
	{
		super(currentCostsRepo, costDetailsRepo);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.LastInvoice;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = getCurrentCost(request);
		final CostDetailCreateResult result = createCostDetailRecordWithChangedCosts(request, currentCosts);

		final CostAmount amt = request.getAmt();
		final Quantity qty = request.getQty();
		final boolean isReturnTrx = qty.signum() < 0;

		if (!isReturnTrx)
		{
			if (qty.signum() != 0)
			{
				final CostAmount price = amt.divide(qty, currentCosts.getPrecision(), RoundingMode.HALF_UP);
				currentCosts.setCurrentCostPrice(price);
			}
			else
			{
				final CostAmount priceAdjust = amt;
				final CostAmount price = currentCosts.getCurrentCostPrice().add(priceAdjust);
				currentCosts.setCurrentCostPrice(price);
			}
		}
		currentCosts.adjustCurrentQty(qty);
		currentCosts.addCumulatedAmtAndQty(amt, qty);

		saveCurrentCosts(currentCosts);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = getCurrentCost(request);
		final CostDetailCreateResult result = createCostDetailRecordWithChangedCosts(request, currentCosts);

		currentCosts.adjustCurrentQty(request.getQty());

		saveCurrentCosts(currentCosts);

		return result;
	}

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		return getLastInvoicePrice(costSegment);
	}

	public static BigDecimal getLastInvoicePrice(final CostSegment costSegment)
	{
		final int productId = costSegment.getProductId();
		final int AD_Org_ID = costSegment.getOrgId();
		final int M_ASI_ID = costSegment.getAttributeSetInstanceId();
		final MAcctSchema as = MAcctSchema.get(costSegment.getAcctSchemaId());
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
