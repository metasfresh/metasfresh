package de.metas.costing.methods;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Optionals;
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
	public LastPOCostingMethodHandler(@NonNull final CostingMethodHandlerUtils utils)
	{
		super(utils);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.LastPOPrice;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		
		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

		final CostAmount amt = request.getAmt();
		final Quantity qty = request.getQty();
		final boolean isReturnTrx = qty.signum() < 0;

		if (!isReturnTrx)
		{
			if (qty.signum() != 0)
			{
				final CostAmount price = amt.divide(qty, currentCosts.getPrecision());
				currentCosts.setCostPrice(CostPrice.ownCostPrice(price));
			}
			else
			{
				final CostAmount priceAdjust = amt;
				currentCosts.addToOwnCostPrice(priceAdjust);
			}
		}

		currentCosts.addToCurrentQtyAndCumulate(qty, amt, utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		
		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

		currentCosts.addToCurrentQtyAndCumulate(request.getQty(), request.getAmt(), utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(final CostSegment costSegment, final OrderLineId orderLineId)
	{
		return Optionals.firstPresentOfSuppliers(
				() -> getPOPrice(costSegment, orderLineId),
				() -> getLastPOPrice(costSegment));
	}

	private static Optional<CostAmount> getPOPrice(@NonNull final CostSegment costSegment, @NonNull final OrderLineId orderLineId)
	{
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(costSegment.getAcctSchemaId());
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();

		final String sql = "SELECT"
				+ " currencyConvert(ol.PriceCost, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID)"
				+ ", currencyConvert(ol.PriceActual, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID) "
				+ " FROM C_OrderLine ol"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ " WHERE ol.C_OrderLine_ID=?"
				+ " AND o.IsSOTrx=?";
		final Object[] sqlParams = new Object[] { acctCurrencyId, acctCurrencyId, orderLineId, false };
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
				final BigDecimal priceCost = rs.getBigDecimal(1);
				if (priceCost != null && priceCost.signum() != 0)
				{
					return Optional.of(CostAmount.of(priceCost, acctCurrencyId));
				}

				final BigDecimal priceActual = rs.getBigDecimal(2);
				if (priceActual != null)
				{
					return Optional.of(CostAmount.of(priceActual, acctCurrencyId));
				}

				return Optional.empty();
			}
			else
			{
				return Optional.empty();
			}
		}
		catch (final Exception e)
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

	private static Optional<CostAmount> getLastPOPrice(final CostSegment costSegment)
	{
		final ProductId productId = costSegment.getProductId();
		final OrgId orgId = costSegment.getOrgId();
		final AttributeSetInstanceId asiId = costSegment.getAttributeSetInstanceId();
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(costSegment.getAcctSchemaId());
		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();

		final List<Object> sqlParams = new ArrayList<>();
		String sql = "SELECT currencyConvert(ol.PriceCost, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID),"
				+ " currencyConvert(ol.PriceActual, o.C_Currency_ID, ?, o.DateAcct, o.C_ConversionType_ID, ol.AD_Client_ID, ol.AD_Org_ID) "
				// ,ol.PriceCost,ol.PriceActual, ol.QtyOrdered, o.DateOrdered, ol.Line
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ "WHERE ol.M_Product_ID=?"
				+ " AND o.IsSOTrx='N'";
		sqlParams.add(acctCurrencyId);
		sqlParams.add(acctCurrencyId);
		sqlParams.add(productId);
		if (orgId.isRegular())
		{
			sql += " AND ol.AD_Org_ID=?";
			sqlParams.add(orgId);
		}
		else if (asiId.isRegular())
		{
			sql += " AND ol.M_AttributeSetInstance_ID=?";
			sqlParams.add(asiId);
		}
		sql += " ORDER BY o.DateOrdered DESC, ol.Line DESC";
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
				final BigDecimal priceCost = rs.getBigDecimal(1);
				if (priceCost != null && priceCost.signum() != 0)
				{
					return Optional.of(CostAmount.of(priceCost, acctCurrencyId));
				}

				final BigDecimal priceActual = rs.getBigDecimal(2);
				if (priceActual != null && priceActual.signum() != 0)
				{
					return Optional.of(CostAmount.of(priceActual, acctCurrencyId));
				}

				return Optional.empty();
			}
			else
			{
				return Optional.empty();
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
	}	// getLastPOPrice

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
