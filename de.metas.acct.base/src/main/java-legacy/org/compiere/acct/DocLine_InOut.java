package org.compiere.acct;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MAccount;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostAmount;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.order.IOrderLineBL;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.acct.base
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

class DocLine_InOut extends DocLine<Doc_InOut>
{
	/** Outside Processing */
	private Integer ppCostCollectorId = null;

	public DocLine_InOut(final I_M_InOutLine inoutLine, final Doc_InOut doc)
	{
		super(InterfaceWrapperHelper.getPO(inoutLine), doc);

		final Quantity qty = Quantity.of(inoutLine.getMovementQty(), getProductStockingUOM());
		setQty(qty, doc.isSOTrx());
	}

	private final int getPP_Cost_Collector_ID()
	{
		if (ppCostCollectorId == null)
		{
			ppCostCollectorId = retrievePPCostCollectorId();
		}
		return ppCostCollectorId;
	}

	private final int retrievePPCostCollectorId()
	{
		final int orderLineId = getC_OrderLine_ID();
		if (orderLineId > 0)
		{
			final String sql = "SELECT PP_Cost_Collector_ID  FROM C_OrderLine WHERE C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
			return DB.getSQLValueEx(getTrxName(), sql, new Object[] { orderLineId });
		}

		return 0;
	}

	public final I_C_OrderLine getOrderLineOrNull()
	{
		return getModel(I_M_InOutLine.class)
				.getC_OrderLine();
	}

	/**
	 * @return order's org if defined, else doc line's org
	 */
	public final int getOrder_Org_ID()
	{
		final I_C_OrderLine orderLine = getOrderLineOrNull();
		return orderLine != null ? orderLine.getAD_Org_ID() : getAD_Org_ID();
	}

	public MAccount getProductAssetAccount(final I_C_AcctSchema as)
	{
		if (isItem())
		{
			return getAccount(ProductAcctType.Asset, as);
		}
		// if the line is a Outside Processing then DR WIP
		else if (getPP_Cost_Collector_ID() > 0)
		{
			return getAccount(ProductAcctType.WorkInProcess, as);
		}
		else
		{
			return getAccount(ProductAcctType.Expense, as);
		}
	}

	public CostAmount getPurchaseCosts(final I_C_AcctSchema as)
	{
		final CostingMethod costingMethod = getProductCostingMethod(as);
		if (CostingMethod.AveragePO == costingMethod || CostingMethod.LastPOPrice == costingMethod)
		{
			final I_C_OrderLine orderLine = getOrderLineOrNull();
			if (orderLine != null)
			{
				final CostAmount costPrice = Services.get(IOrderLineBL.class).getCostPrice(orderLine);
				final CostAmount costs = costPrice.multiply(getQty());
				return costs;
			}
		}

		final BigDecimal costs = getProductCosts(as, getAD_Org_ID(), false); // current costs
		if (ProductCost.isNoCosts(costs))
		{
			// 08447: we shall allow zero costs in case CostingMethod=Standard costing
			if (CostingMethod.StandardCosting != costingMethod)
			{
				throw newPostingException().setDetailMessage("Resubmit - No Costs for product=" + getProduct()
						+ ", product is stocked and costing method=" + costingMethod + " is != " + CostingMethod.StandardCosting);
			}
		}

		return CostAmount.of(costs != null ? costs : BigDecimal.ZERO, as.getC_Currency_ID());
	}

	public CostAmount getShipmentCosts(final I_C_AcctSchema as)
	{
		// metas-ts: US330: call with zeroCostsOK=false, because we want the system to return the result of MCost.getSeedCosts(), if there are no current costs yet
		final BigDecimal costs = getProductCosts(as, getAD_Org_ID(), false, CostingDocumentRef.ofShipmentLineId(get_ID()));
		if (ProductCost.isNoCosts(costs) || costs.signum() == 0)
		{
			final CostingMethod costingMethod = getProductCostingMethod(as);
			if (CostingMethod.StandardCosting != costingMethod)
			{
				// 08447: we shall allow zero costs in case CostingMethod=Standard costing
				throw newPostingException()
						.setDetailMessage("No Costs for product=" + getProduct()
								+ ", product is stocked and costing method=" + costingMethod + " is != " + CostingMethod.StandardCosting);
			}
		}

		return CostAmount.of(costs != null ? costs : BigDecimal.ZERO, as.getC_Currency_ID());
	}
}
