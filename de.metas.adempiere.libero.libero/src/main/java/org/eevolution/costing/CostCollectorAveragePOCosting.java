package org.eevolution.costing;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostResult;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

public class CostCollectorAveragePOCosting
{
	final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
	final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

	public CostingMethod getCostingMethod()
	{
		return CostingMethod.AveragePO;
	}

	public Set<String> getHandledTableNames()
	{
		return ImmutableSet.of(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector);
	}

	public void createForOrder(final int ppOrderId)
	{
		final List<I_PP_Cost_Collector> costCollectors = ppCostCollectorDAO.retrieveForOrderId(ppOrderId);

		final I_PP_Order ppOrder = load(ppOrderId, I_PP_Order.class);
		final BigDecimal qtyReceived = ppOrder.getQtyBeforeClose();
		final I_C_UOM qtyReceivedUOM = ppOrder.getC_UOM();

		//
		// First process the issues
		final CostResult costs = costCollectors.stream()
				.filter(cc -> ppCostCollectorBL.isMaterialIssue(cc))
				.map(cc -> createMaterialIssueCosts(cc))
				.reduce(CostResult::add)
				.orElse(null);

		// TODO: handle null case

		final CostResult costPrice = costs.divide(qtyReceived, 12, RoundingMode.HALF_UP);

		//
		// Then process the receipts
		costCollectors.stream()
				.filter(cc -> ppCostCollectorBL.isMaterialReceipt(cc))
				.forEach(cc -> createMaterialReceiptCosts(cc, costPrice, qtyReceivedUOM));
	}

	private CostResult createMaterialIssueCosts(final I_PP_Cost_Collector cc)
	{
		// TODO Auto-generated method stub
		// create a standard outbound cost detail (Avg)
		return null;
	}

	private void createMaterialReceiptCosts(final I_PP_Cost_Collector cc, final CostResult costPrice, final I_C_UOM uom)
	{
		// TODO Auto-generated method stub
		// final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		// final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(cc.getM_Product());
		// uomConversionBL.convertQty(uomConversionCtx, cc.getMovementQty(), uomFrom, uomTo)
		throw new UnsupportedOperationException();
	}
}
