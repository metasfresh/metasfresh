package de.metas.costing.methods;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;
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
import de.metas.currency.ICurrencyBL;
import de.metas.order.IOrderLineBL;
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
public class AveragePOCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public AveragePOCostingMethodHandler(final ICurrentCostsRepository currentCostsRepo, final ICostDetailRepository costDetailsRepo)
	{
		super(currentCostsRepo, costDetailsRepo);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.AveragePO;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		final CostDetailCreateResult result = createCostDefaultImpl(request);

		final CurrentCost currentCosts = getCurrentCost(request);
		currentCosts.addWeightedAverage(request.getAmt(), request.getQty());

		saveCurrentCosts(currentCosts);

		return result;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final I_M_MatchInv matchInv = InterfaceWrapperHelper.load(request.getDocumentRef().getRecordId(), I_M_MatchInv.class);
		final I_C_InvoiceLine purchaseInvoiceLine = matchInv.getC_InvoiceLine();
		final I_C_OrderLine purchaseOrderLine = purchaseInvoiceLine.getC_OrderLine();
		if (purchaseOrderLine == null)
		{
			throw new AdempiereException("Cannot compute Average PO price because invoice line is not linked to an order line: " + request);
		}

		final CostAmount costPrice = Services.get(IOrderLineBL.class).getCostPrice(purchaseOrderLine);
		final CostAmount amt = costPrice.multiply(request.getQty());

		final CostDetailCreateResult result = createCostDefaultImpl(request.toBuilder()
				.amt(amt)
				.build());

		// NOTE: we don't have to update the current costs

		return result;
	}

	@Override
	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		final CostAmount amt;

		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.load(request.getDocumentRef().getRecordId(), I_M_InOutLine.class);
		final I_C_OrderLine purchaseOrderLine = receiptLine.getC_OrderLine();
		if (purchaseOrderLine != null)
		{
			final CostAmount costPrice = Services.get(IOrderLineBL.class).getCostPrice(purchaseOrderLine);
			amt = costPrice.multiply(request.getQty());
		}
		else
		{
			final CurrentCost currentCosts = getCurrentCost(request);
			amt = currentCosts.getCurrentCostPrice().multiply(request.getQty());
		}

		// NOTE: we don't have to update the current costs

		return createCostDefaultImpl(request.toBuilder()
				.amt(amt)
				.build());
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final Quantity qty = request.getQty();
		final boolean isReturnTrx = qty.signum() > 0;

		final CurrentCost currentCosts = getCurrentCost(request);
		final CostDetailCreateResult result;
		if (isReturnTrx)
		{
			result = createCostDefaultImpl(request);

			currentCosts.addWeightedAverage(request.getAmt(), qty);
		}
		else
		{
			final CostAmount price = currentCosts.getCurrentCostPrice();
			result = createCostDefaultImpl(request.toBuilder()
					.amt(price.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision()))
					.build());

			currentCosts.adjustCurrentQty(qty);
		}

		saveCurrentCosts(currentCosts);

		return result;
	}

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final Properties ctx = Env.getCtx();
		final int productId = costSegment.getProductId();
		final int AD_Org_ID = costSegment.getOrgId();
		final int M_AttributeSetInstance_ID = costSegment.getAttributeSetInstanceId();
		final MAcctSchema as = MAcctSchema.get(ctx, costSegment.getAcctSchemaId());

		String sql = "SELECT t.MovementQty, mp.Qty, ol.QtyOrdered, ol.PriceCost, ol.PriceActual,"	// 1..5
				+ " o.C_Currency_ID, o.DateAcct, o.C_ConversionType_ID,"	// 6..8
				+ " o.AD_Client_ID, o.AD_Org_ID, t.M_Transaction_ID "		// 9..11
				+ "FROM M_Transaction t"
				+ " INNER JOIN M_MatchPO mp ON (t.M_InOutLine_ID=mp.M_InOutLine_ID)"
				+ " INNER JOIN C_OrderLine ol ON (mp.C_OrderLine_ID=ol.C_OrderLine_ID)"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
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
		BigDecimal newStockQty = BigDecimal.ZERO;
		//
		BigDecimal newAverageAmt = BigDecimal.ZERO;
		final int oldTransaction_ID = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
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
				final BigDecimal oldStockQty = newStockQty;
				final BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(11);
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
				BigDecimal price = rs.getBigDecimal(4);
				if (price == null || price.signum() == 0)
				{
					price = rs.getBigDecimal(5);			// Actual
				}
				final int C_Currency_ID = rs.getInt(6);
				final Timestamp DateAcct = rs.getTimestamp(7);
				final int C_ConversionType_ID = rs.getInt(8);
				final int Client_ID = rs.getInt(9);
				final int Org_ID = rs.getInt(10);
				final BigDecimal cost = currencyConversionBL.convert(ctx, price,
						C_Currency_ID, as.getC_Currency_ID(),
						DateAcct, C_ConversionType_ID, Client_ID, Org_ID);
				//
				final BigDecimal oldAverageAmt = newAverageAmt;
				final BigDecimal averageCurrent = oldStockQty.multiply(oldAverageAmt);
				final BigDecimal averageIncrease = matchQty.multiply(cost);
				BigDecimal newAmt = averageCurrent.add(averageIncrease);
				newAmt = newAmt.setScale(as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
				newAverageAmt = newAmt.divide(newStockQty, as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
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
		//
		if (newAverageAmt != null && newAverageAmt.signum() != 0)
		{
			return newAverageAmt;
		}
		return null;
	}
}
