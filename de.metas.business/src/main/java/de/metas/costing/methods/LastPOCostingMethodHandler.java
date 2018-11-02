package de.metas.costing.methods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingMethodHandlerTemplate;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

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
public class LastPOCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public LastPOCostingMethodHandler(final ICurrentCostsRepository currentCostsRepo, final ICostDetailRepository costDetailsRepo)
	{
		super(currentCostsRepo, costDetailsRepo);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.LastPOPrice;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
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
	public BigDecimal calculateSeedCosts(CostSegment costSegment, OrderLineId orderLineId)
	{
		BigDecimal costs = null;
		if (orderLineId != null)
			costs = getPOPrice(costSegment, orderLineId);
		if (costs == null || costs.signum() == 0)
			costs = getLastPOPrice(costSegment);
		return costs;
	}

	/**
	 * Get PO Price in currency
	 * 
	 * @return last PO price in currency or null
	 */
	public static BigDecimal getPOPrice(@NonNull final CostSegment costSegment, @NonNull final OrderLineId orderLineId)
	{
		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(costSegment.getAcctSchemaId());
		final CurrencyId currencyId = as.getCurrencyId();

		final String sql = "SELECT"
				+ " currencyConvert(ol.PriceCost, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID)"
				+ ", currencyConvert(ol.PriceActual, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID) "
				+ " FROM C_OrderLine ol"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ " WHERE ol.C_OrderLine_ID=?"
				+ " AND o.IsSOTrx=?";
		final Object[] sqlParams = new Object[] {currencyId, currencyId, orderLineId, false};
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				BigDecimal priceCost = rs.getBigDecimal(1);
				if (priceCost != null && priceCost.signum() != 0)
				{
					return priceCost;
				}
				
				final BigDecimal priceActual = rs.getBigDecimal(2);
				return priceActual;
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	// getPOPrice

	/**
	 * Get Last PO Price in currency
	 * 
	 * @param product product
	 * @param M_ASI_ID attribute set instance
	 * @param AD_Org_ID org
	 * @param C_Currency_ID accounting currency
	 * @return last PO price in currency or null
	 */
	public static BigDecimal getLastPOPrice(final CostSegment costSegment)
	{
		final int productId = costSegment.getProductId().getRepoId();
		final int AD_Org_ID = costSegment.getOrgId().getRepoId();
		final int M_ASI_ID = costSegment.getAttributeSetInstanceId().getRepoId();
		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(costSegment.getAcctSchemaId());
		final int currencyId = as.getCurrencyId().getRepoId();

		String sql = "SELECT currencyConvert(ol.PriceCost, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID),"
				+ " currencyConvert(ol.PriceActual, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID) "
				// ,ol.PriceCost,ol.PriceActual, ol.QtyOrdered, o.DateOrdered, ol.Line
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ "WHERE ol.M_Product_ID=?"
				+ " AND o.IsSOTrx='N'";
		if (AD_Org_ID > 0)
			sql += " AND ol.AD_Org_ID=?";
		else if (M_ASI_ID > 0)
			sql += " AND ol.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY o.DateOrdered DESC, ol.Line DESC";
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, currencyId);
			pstmt.setInt(2, currencyId);
			pstmt.setInt(3, productId);
			if (AD_Org_ID != 0)
				pstmt.setInt(4, AD_Org_ID);
			else if (M_ASI_ID != 0)
				pstmt.setInt(4, M_ASI_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				BigDecimal retValue = rs.getBigDecimal(1);
				if (retValue == null || retValue.signum() == 0)
				{
					retValue = rs.getBigDecimal(2);
				}
				return retValue;
			}
			else
			{
				return null;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	// getLastPOPrice

}
