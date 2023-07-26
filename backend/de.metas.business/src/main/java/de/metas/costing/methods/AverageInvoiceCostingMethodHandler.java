package de.metas.costing.methods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
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
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
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
public class AverageInvoiceCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public AverageInvoiceCostingMethodHandler(@NonNull final CostingMethodHandlerUtils utils)
	{
		super(utils);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.AverageInvoice;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		
		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

		currentCosts.addWeightedAverage(request.getAmt(), request.getQty(), utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final boolean isReturnTrx = request.getQty().signum() > 0;

		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		
		final CostDetailCreateResult result;
		if (isReturnTrx)
		{
			result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

			currentCosts.addWeightedAverage(request.getAmt(), request.getQty(), utils.getQuantityUOMConverter());
		}
		else
		{
			final CostPrice price = currentCosts.getCostPrice();
			final Quantity qty = utils.convertToUOM(request.getQty(), price.getUomId(), request.getProductId());
			final CostAmount amt = price.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
			final CostDetailCreateRequest requestEffective = request.withAmount(amt);
			result = utils.createCostDetailRecordWithChangedCosts(requestEffective, previousCosts);

			currentCosts.addToCurrentQtyAndCumulate(qty, amt);
		}

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(final CostSegment costSegment, final OrderLineId orderLineId_NOTUSED)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final int productId = costSegment.getProductId().getRepoId();
		final int orgId = costSegment.getOrgId().getRepoId();
		final int asiId = costSegment.getAttributeSetInstanceId().getRepoId();
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(costSegment.getAcctSchemaId());
		final CurrencyId acctCurencyId = acctSchema.getCurrencyId();
		final CurrencyPrecision costingPrecision = acctSchema.getCosting().getCostingPrecision();

		String sql = "SELECT t.MovementQty, mi.Qty, il.QtyInvoiced, il.PriceActual,"
				+ " i.C_Currency_ID, i.DateAcct, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID, t.M_Transaction_ID "
				+ "FROM M_Transaction t"
				+ " INNER JOIN M_MatchInv mi ON (t.M_InOutLine_ID=mi.M_InOutLine_ID)"
				+ " INNER JOIN C_InvoiceLine il ON (mi.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
				+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
				+ "WHERE t.M_Product_ID=?"; // #1
		if (orgId > 0)
		{
			sql += " AND t.AD_Org_ID=?";
		}
		else if (asiId > 0)
		{
			sql += " AND t.M_AttributeSetInstance_ID=?";
		}
		sql += " ORDER BY t.M_Transaction_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal newStockQty = BigDecimal.ZERO;
		//
		BigDecimal newAverageAmt = BigDecimal.ZERO;
		final int oldTransaction_ID = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, productId);
			if (orgId > 0)
			{
				pstmt.setInt(2, orgId);
			}
			else if (asiId > 0)
			{
				pstmt.setInt(2, asiId);
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final BigDecimal oldStockQty = newStockQty;
				final BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(10);
				if (M_Transaction_ID != oldTransaction_ID)
				{
					newStockQty = oldStockQty.add(movementQty);
				}
				M_Transaction_ID = oldTransaction_ID;
				//
				final BigDecimal matchQty = rs.getBigDecimal(2);
				if (matchQty == null)
				{
					continue;
				}
				// Assumption: everything is matched
				final BigDecimal price = rs.getBigDecimal(4);
				final CurrencyId currencyId = CurrencyId.ofRepoId(rs.getInt(5));
				final LocalDate DateAcct = TimeUtil.asLocalDate(rs.getTimestamp(6));
				final CurrencyConversionTypeId C_ConversionType_ID = CurrencyConversionTypeId.ofRepoIdOrNull(rs.getInt(7));
				final ClientId Client_ID = ClientId.ofRepoId(rs.getInt(8));
				final OrgId Org_ID = OrgId.ofRepoId(rs.getInt(9));
				final BigDecimal cost = currencyConversionBL.convert(
						price,
						currencyId,
						acctCurencyId,
						DateAcct, 
						C_ConversionType_ID, 
						Client_ID, 
						Org_ID);
				//
				final BigDecimal oldAverageAmt = newAverageAmt;
				final BigDecimal averageCurrent = oldStockQty.multiply(oldAverageAmt);
				final BigDecimal averageIncrease = matchQty.multiply(cost);
				BigDecimal newAmt = averageCurrent.add(averageIncrease);
				newAmt = newAmt.setScale(costingPrecision.toInt(), RoundingMode.HALF_UP);
				newAverageAmt = newAmt.divide(newStockQty, costingPrecision.toInt(), RoundingMode.HALF_UP);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		//
		if (newAverageAmt != null && newAverageAmt.signum() != 0)
		{
			return Optional.of(CostAmount.of(newAverageAmt, acctCurencyId));
		}
		else
		{
			return Optional.empty();
		}
	}

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
