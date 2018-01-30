package de.metas.costing.methods;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_UOM;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailEvent;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingMethodHandlerTemplate;
import de.metas.costing.CurrentCost;
import de.metas.product.IProductBL;
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

// TODO: figure out if we need it... In legacy code it was called "if (!ce.isCostingMethod()) // Cost Adjustments"
public class CostsAdjustmentsCostingMethodHandler extends CostingMethodHandlerTemplate
{
	@Override
	protected void processMatchInvoice(final CostDetailEvent event, final CurrentCost cost)
	{
		final CostSegment costSegment = event.getCostSegment();
		final CostAmount amt = event.getAmt();
		final Quantity qty = event.getQty();
		final int precision = event.getPrecision();

		// AZ Goodwill
		// get costing method for product
		final I_C_AcctSchema as = MAcctSchema.get(Env.getCtx(), costSegment.getAcctSchemaId());
		final int productId = costSegment.getProductId();
		final IProductBL productBL = Services.get(IProductBL.class);
		final CostingMethod productCostingMethod = productBL.getCostingMethod(productId, as);
		final I_C_UOM productUOM = productBL.getStockingUOM(productId);
		
		if (CostingMethod.AveragePO.equals(productCostingMethod) ||
				CostingMethod.AverageInvoice.equals(productCostingMethod))
		{
			/**
			 * Problem with Landed Costs: certain cost element may not occur in every purchases,
			 * causing the average calculation of that cost element wrongly took the current qty.
			 *
			 * Solution:
			 * Make sure the current qty is reflecting the actual qty in storage
			 */
			String sql = "SELECT COALESCE(SUM(QtyOnHand),0) FROM M_Storage"
					+ " WHERE AD_Client_ID=" + costSegment.getClientId()
					+ " AND M_Product_ID=" + productId;
			// Costing Level
			final CostingLevel costingLevel = costSegment.getCostingLevel();
			if (costingLevel == CostingLevel.Organization)
			{
				sql += " AND AD_Org_ID=" + costSegment.getOrgId();
			}
			else if (costingLevel == CostingLevel.BatchLot)
			{
				sql += " AND M_AttributeSetInstance_ID=" + costSegment.getAttributeSetInstanceId();
			}
			//
			final BigDecimal qtyOnHand = DB.getSQLValueBDEx(ITrx.TRXNAME_ThreadInherited, sql);
			if (qtyOnHand.signum() != 0)
			{
				final CostAmount oldSum = cost.getCurrentCostPrice().multiply(cost.getCurrentQty());
				final CostAmount sumAmt = oldSum.add(amt);	// amt is total already
				final CostAmount costs = sumAmt.divide(qtyOnHand, precision, RoundingMode.HALF_UP);
				cost.setCurrentCostPrice(costs);
			}
			cost.setCurrentQty(Quantity.of(qtyOnHand, productUOM));
			
			cost.addCumulatedAmtAndQty(amt, qty);
		}
		else // original logic from Compiere
		{
			final CostAmount costPrice = cost.getCurrentCostPrice().add(amt);
			cost.setCurrentCostPrice(costPrice);
			cost.add(amt, qty);
		}
	}

	@Override
	protected void processOutboundTransactionDefaultImpl(final CostDetailEvent event, final CurrentCost cost)
	{
		// Should not happen
	}
}
