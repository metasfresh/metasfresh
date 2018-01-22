package de.metas.costing.methods;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAcctSchema;
import org.compiere.model.X_C_AcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.costing.CostDetailEvent;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethodHandlerTemplate;
import de.metas.product.IProductBL;

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
	protected void processPurchaseInvoice(final CostDetailEvent event, final CurrentCost cost)
	{
		final CostSegment costSegment = event.getCostSegment();
		final BigDecimal amt = event.getAmt();
		final BigDecimal qty = event.getQty();
		final int precision = event.getPrecision();

		// AZ Goodwill
		// get costing method for product
		final I_C_AcctSchema as = MAcctSchema.get(Env.getCtx(), costSegment.getAcctSchemaId());
		final I_M_Product product = InterfaceWrapperHelper.loadOutOfTrx(costSegment.getProductId(), I_M_Product.class);
		final String productCostingMethod = Services.get(IProductBL.class).getCostingMethod(product, as);
		if (X_C_AcctSchema.COSTINGMETHOD_AveragePO.equals(productCostingMethod) ||
				X_C_AcctSchema.COSTINGMETHOD_AverageInvoice.equals(productCostingMethod))
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
					+ " AND M_Product_ID=" + costSegment.getProductId();
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
			final BigDecimal qtyOnhand = DB.getSQLValueBDEx(ITrx.TRXNAME_ThreadInherited, sql);
			if (qtyOnhand.signum() != 0)
			{
				final BigDecimal oldSum = cost.getCurrentCostPrice().multiply(cost.getCurrentQty());
				final BigDecimal sumAmt = oldSum.add(amt);	// amt is total already
				final BigDecimal costs = sumAmt.divide(qtyOnhand, precision, BigDecimal.ROUND_HALF_UP);
				cost.setCurrentCostPrice(costs);
			}
			cost.setCurrentQty(qtyOnhand);
			
			cost.addCumulatedAmtAndQty(amt, qty);
		}
		else // original logic from Compiere
		{
			final BigDecimal cCosts = cost.getCurrentCostPrice().add(amt);
			cost.setCurrentCostPrice(cCosts);
			cost.add(amt, qty);
		}
	}

	@Override
	protected void processOutboundTransactionDefaultImpl(final CostDetailEvent event, final CurrentCost cost)
	{
		// Should not happen
	}
}
