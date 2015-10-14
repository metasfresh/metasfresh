package de.metas.handlingunits.pporder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.storage.impl.AbstractProductStorage;

/**
 * Product storage for a manufacturing order BOM Line.
 *
 * Use this storage when you want to manipulate manufacturing order components (i.e. issuing raw materials to a manufacturing order)
 *
 * @author tsa
 *
 */
public class PPOrderBOMLineProductStorage extends AbstractProductStorage
{
	// Services
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

	private final I_PP_Order_BOMLine orderBOMLine;
	private boolean staled = false;

	public PPOrderBOMLineProductStorage(final I_PP_Order_BOMLine orderBOMLine)
	{
		super();
		setConsiderForceQtyAllocationFromRequest(false); // TODO: consider changing it to "true" (default)

		Check.assumeNotNull(orderBOMLine, "orderBOMLine");
		this.orderBOMLine = orderBOMLine;
	}

	@Override
	protected IHUCapacityDefinition retrieveTotalCapacity()
	{
		final I_M_Product product = orderBOMLine.getM_Product();
		final I_C_UOM uom = orderBOMLine.getC_UOM();

		//
		// Case: if this is an Issue BOM Line, IssueMethod is Backflush and we did not over-issue on it yet
		// => enforce the capacity to Projected Qty Required (i.e. standard Qty that needs to be issued on this line).
		// initial concept: http://dewiki908/mediawiki/index.php/fresh_07433_Folie_Zuteilung_Produktion_Fertigstellung_POS_%28102170996938%29
		// additional (use of projected qty required): http://dewiki908/mediawiki/index.php/fresh_07601_Calculation_of_Folie_in_Action_Receipt_%28102017845369%29
		final String issueMethod = orderBOMLine.getIssueMethod();
		if (X_PP_Order_BOMLine.ISSUEMETHOD_IssueOnlyForReceived.equals(issueMethod) && !ppOrderBOMBL.isReceipt(orderBOMLine))
		{
			final BigDecimal qtyToIssueRequired = ppOrderBOMBL.calculateQtyRequiredBasedOnFinishedGoodReceipt(orderBOMLine);
			return capacityBL.createCapacity(qtyToIssueRequired,
					product,
					uom,
					true // allowNegativeCapacity
					);
		}

		//
		// NOTE: we return infinite capacity because we don't want to enforce how many items we can allocate on this storage
		return capacityBL.createInfiniteCapacity(product, uom);
	}

	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		checkStaled();

		final BigDecimal qtyCapacity;
		final BigDecimal qtyToIssueOrReceive;

		if (ppOrderBOMBL.isReceipt(orderBOMLine))
		{
			qtyCapacity = ppOrderBOMBL.getQtyRequiredToReceive(orderBOMLine);
			qtyToIssueOrReceive = ppOrderBOMBL.getQtyToReceive(orderBOMLine);
		}
		else
		{
			qtyCapacity = ppOrderBOMBL.getQtyRequiredToIssue(orderBOMLine);
			qtyToIssueOrReceive = ppOrderBOMBL.getQtyToIssue(orderBOMLine);
		}

		//
		// We consider our initial qty as the quantity that was already issued/received on this order BOM Line
		final BigDecimal qtyIssued = qtyCapacity.subtract(qtyToIssueOrReceive);
		return qtyIssued;
	}

	@Override
	protected void beforeMarkingStalled()
	{
		staled = true;
	}

	private final void checkStaled()
	{
		if (!staled)
		{
			return;
		}

		InterfaceWrapperHelper.refresh(orderBOMLine);
		staled = false;
	}
}
