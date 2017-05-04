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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.materialtracking.IHUPPOrderMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.pporder.api.IHUPPCostCollectorBL;

/**
 * Aggregates {@link IHUTransaction}s and creates {@link I_PP_Cost_Collector}s for issuing materials to manufacturing order
 *
 * @author tsa
 *
 */
/* package */class HUIssueCostCollectorBuilder
{
	// Services
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	private final transient IHUPPOrderMaterialTrackingBL huPPOrderMaterialTrackingBL = Services.get(IHUPPOrderMaterialTrackingBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);

	// Parameters
	private final IHUContext huContext;

	// Status
	private final Map<Integer, HUIssueCostCollectorCandidate> ppOrderBOMLineId2candidate = new HashMap<>();

	public HUIssueCostCollectorBuilder(final IHUContext huContext)
	{
		super();

		Check.assumeNotNull(huContext, "huContext not null");
		this.huContext = huContext;
	}

	public void addHUTransaction(final IHUTransaction huTransaction)
	{
		final I_PP_Order_BOMLine ppOrderBOMLine = getOrderBOMLineToIssueOrNull(huTransaction);
		if (ppOrderBOMLine == null)
		{
			// does not apply
			return;
		}

		//
		// Get/Create Issue Candidate
		final int ppOrderBOMLineId = ppOrderBOMLine.getPP_Order_BOMLine_ID();
		HUIssueCostCollectorCandidate issueCandidate = ppOrderBOMLineId2candidate.get(ppOrderBOMLineId);
		if (issueCandidate == null)
		{
			issueCandidate = new HUIssueCostCollectorCandidate(ppOrderBOMLine);
			ppOrderBOMLineId2candidate.put(ppOrderBOMLineId, issueCandidate);
		}

		//
		// Add Qty To Issue
		final I_M_Product product = huTransaction.getProduct();
		final Quantity qtyToIssue = huTransaction.getQuantity();

		// Get HU from counterpart transaction
		// (because in this transaction we have the Order BOM line)
		final IHUTransaction huTransactionCounterpart = huTransaction.getCounterpart();
		final I_M_HU hu = huTransactionCounterpart.getM_HU();

		//
		// Get the Top Level HU of this transaction.
		// That's the HU that we will need to assign to generated cost collector.
		// NOTE: even if those HUs were already destroyed, we have to assign them, for tracking
		final I_M_HU huTopLevel = handlingUnitsBL.getTopLevelParent(hu);

		//
		// Add Qty To Issue
		issueCandidate.addQtyToIssue(product, qtyToIssue, huTopLevel);

		//
		// Get Material Tracking from HU and link this manufacturing order to that material tracking
		{
			huPPOrderMaterialTrackingBL.linkPPOrderToMaterialTracking(huContext, ppOrderBOMLine, hu);
		}
	}

	private I_PP_Order_BOMLine getOrderBOMLineToIssueOrNull(final IHUTransaction huTransaction)
	{
		final Object referencedModel = huTransaction.getReferencedModel();
		if (!InterfaceWrapperHelper.isInstanceOf(referencedModel, I_PP_Order_BOMLine.class))
		{
			return null;
		}

		final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(referencedModel, I_PP_Order_BOMLine.class);

		//
		// Don't create Issue Cost Collectors for Receipt BOM Lines (i.e. those of type By/Co-Product)
		// .. but only for issues
		if (ppOrderBOMBL.isReceipt(ppOrderBOMLine))
		{
			return null;
		}

		return ppOrderBOMLine;
	}

	/**
	 * Create Issue Cost Collectors from collected candidates
	 *
	 * @return created cost collectors
	 */
	public List<I_PP_Cost_Collector> createCostCollectors()
	{
		final List<I_PP_Cost_Collector> result = new ArrayList<>();

		for (final HUIssueCostCollectorCandidate candidate : ppOrderBOMLineId2candidate.values())
		{
			final I_PP_Cost_Collector cc = createCostCollector(candidate);
			if (cc == null)
			{
				continue;
			}

			result.add(cc);
		}

		return result;
	}

	/**
	 *
	 * @param candidate
	 * @return created issue cost collector or null
	 */
	private I_PP_Cost_Collector createCostCollector(final HUIssueCostCollectorCandidate candidate)
	{
		final BigDecimal qtyToIssue = candidate.getQtyToIssue();
		if (qtyToIssue.signum() == 0)
		{
			return null;
		}

		final I_C_UOM uom = candidate.getC_UOM();
		final Date movementDate = huContext.getDate();
		final I_PP_Order_BOMLine ppOrderBOMLine = candidate.getPP_Order_BOMLine();
		final int locatorId = ppOrderBOMLine.getM_Locator_ID();

		//
		// Create the cost collector & process it.
		final I_PP_Cost_Collector cc = ppCostCollectorBL.createIssue(
				huContext, // context
				ppOrderBOMLine,
				locatorId, // locator
				0, // attributeSetInstanceId: N/A
				movementDate,
				qtyToIssue,
				BigDecimal.ZERO, // qtyScrap,
				BigDecimal.ZERO, // qtyReject
				uom // UOM
				);
		
		//
		// Assign the HUs to cost collector.
		// NOTE: even if those HUs were already destroyed, we have to assign them, for tracking
		huPPCostCollectorBL.assignHUs(cc, candidate.getHUsToAssign());

		//
		// Return it
		return cc;
	}
}
