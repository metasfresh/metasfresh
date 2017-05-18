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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.quantity.Quantity;

/**
 * Issues given HUs to configured Order BOM Lines.
 *
 * @author tsa
 *
 */
public class HUPPOrderIssueProducer implements IHUPPOrderIssueProducer
{
	// Services
	private static final transient Logger logger = LogManager.getLogger(HUPPOrderIssueProducer.class);
	//
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	//
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IHULockBL huLockBL = Services.get(IHULockBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

	private static final String MSG_IssuingAggregatedTUsNotAllowed = "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingAggregatedTUsNotAllowed";
	private static final String MSG_IssuingVHUsNotAllowed = "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingVHUsNotAllowed";
	private static final String MSG_IssuingHUWithMultipleProductsNotAllowed = "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingHUsWithMultipleProductsNotAllowed";

	public static final LockOwner lockOwner = LockOwner.forOwnerName("PP_Order_PreparedToIssue");

	private Date movementDate;
	private List<I_PP_Order_BOMLine> targetOrderBOMLines;

	HUPPOrderIssueProducer()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("movementDate", movementDate)
				.add("targetOrderBOMLines", targetOrderBOMLines)
				.toString();
	}

	@Override
	public List<I_PP_Order_Qty> createIssues(final Collection<I_M_HU> hus)
	{
		if (hus.isEmpty())
		{
			return ImmutableList.of();
		}

		// NOTE: we would prefer to always run this out of transactions,
		// but in some cases like issuing from Swing POS we cannot enforce it because in that case
		// the candidates are created and processed in one uber-transaction
		// trxManager.assertThreadInheritedTrxNotExists();

		// Lock the HUs first, to make sure nobody else is changing them
		// The lock shall stay until the issue candidate is processed.
		huLockBL.lockAll(hus, lockOwner);

		boolean success = false;
		try
		{
			final List<I_PP_Order_Qty> candidates = huTrxBL.process(huContext -> {
				return hus.stream()
						.map(hu -> createIssue_InTrx(huContext, hu))
						.filter(issueCandidate -> issueCandidate != null)
						.collect(ImmutableList.toImmutableList());
			});

			success = true;

			return candidates;
		}
		finally
		{
			// In case of failure, unlock the HUs.
			if (!success)
			{
				huLockBL.unlockAll(hus, lockOwner);
			}
		}

	}

	private I_PP_Order_Qty createIssue_InTrx(final IHUContext huContext, final I_M_HU hu)
	{
		//
		// Check if HU status is eligible
		if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
		{
			throw new HUException("Only active HUs can be issued but " + hu + " is " + hu.getHUStatus());
		}

		// If not a top level HU, take it out first
		if (!handlingUnitsBL.isTopLevel(hu))
		{
			if(handlingUnitsBL.isAggregateHU(hu))
			{
				throw HUException.ofAD_Message(MSG_IssuingAggregatedTUsNotAllowed);
			}
			if (handlingUnitsBL.isVirtual(hu))
			{
				throw HUException.ofAD_Message(MSG_IssuingVHUsNotAllowed);
			}
			else
			{
				huTrxBL.setParentHU(huContext //
						, null // parentHUItem
						, hu //
						, true // destroyOldParentIfEmptyStorage
				);
			}
		}

		//
		// Get the product storage
		final IHUProductStorage productStorage;
		{
			final List<IHUProductStorage> productStorages = huContext.getHUStorageFactory()
					.getStorage(hu)
					.getProductStorages();

			// Empty HU
			if (productStorages.isEmpty())
			{
				logger.warn("{}: Skip {} from issuing because its storage is empty", this, hu);
				return null; // no candidate
			}

			if (productStorages.size() != 1)
			{
				throw HUException.ofAD_Message(MSG_IssuingHUWithMultipleProductsNotAllowed)
						.setParameter("HU", hu)
						.setParameter("ProductStorages", productStorages);
			}
			productStorage = productStorages.get(0);
		}

		//
		// Actually create and save the candidate
		{
			final Date movementDate = getMovementDate();
			final int productId = productStorage.getM_Product_ID();
			final I_PP_Order_BOMLine targetBOMLine = getTargetOrderBOMLine(productId);
			final int targetPPOrderId = targetBOMLine.getPP_Order_ID();
			final int topLevelHUId = hu.getM_HU_ID();
			final int locatorId = hu.getM_Locator_ID();
			final Quantity qtyToIssue = calculateQtyToIssue(targetBOMLine, productStorage);

			final I_PP_Order_Qty candidate = InterfaceWrapperHelper.newInstance(I_PP_Order_Qty.class);
			candidate.setPP_Order_ID(targetPPOrderId);
			candidate.setPP_Order_BOMLine(targetBOMLine);
			candidate.setM_Locator_ID(locatorId);
			candidate.setM_HU_ID(topLevelHUId);
			candidate.setM_Product_ID(productId);
			candidate.setQty(qtyToIssue.getQty());
			candidate.setC_UOM(qtyToIssue.getUOM());
			candidate.setMovementDate(TimeUtil.asTimestamp(movementDate));
			candidate.setProcessed(false);
			huPPOrderQtyDAO.save(candidate);

			return candidate;
		}
	}

	/** @return how much quantity to take "from" and issue it to given BOM line */
	private Quantity calculateQtyToIssue(final I_PP_Order_BOMLine targetBOMLine, final IHUProductStorage from)
	{
		//
		// Case: if this is an Issue BOM Line, IssueMethod is Backflush and we did not over-issue on it yet
		// => enforce the capacity to Projected Qty Required (i.e. standard Qty that needs to be issued on this line).
		// initial concept: http://dewiki908/mediawiki/index.php/07433_Folie_Zuteilung_Produktion_Fertigstellung_POS_%28102170996938%29
		// additional (use of projected qty required): http://dewiki908/mediawiki/index.php/07601_Calculation_of_Folie_in_Action_Receipt_%28102017845369%29
		final String issueMethod = targetBOMLine.getIssueMethod();
		if (X_PP_Order_BOMLine.ISSUEMETHOD_IssueOnlyForReceived.equals(issueMethod))
		{
			return ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(targetBOMLine, from.getC_UOM());
		}
		else
		{
			return Quantity.of(from.getQty(), from.getC_UOM());
		}

	}

	@Override
	public IHUPPOrderIssueProducer setMovementDate(final Date movementDate)
	{
		this.movementDate = movementDate;
		return this;
	}

	private Date getMovementDate()
	{
		if (movementDate == null)
		{
			movementDate = SystemTime.asDayTimestamp();
		}
		return movementDate;
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLines(final List<I_PP_Order_BOMLine> targetOrderBOMLines)
	{
		Check.assumeNotEmpty(targetOrderBOMLines, "Parameter targetOrderBOMLines is not empty");
		targetOrderBOMLines.forEach(bomLine -> {
			if (!PPOrderUtil.isIssue(bomLine.getComponentType()))
			{
				throw new IllegalArgumentException("Not an issue BOM line: " + bomLine);
			}
		});

		this.targetOrderBOMLines = targetOrderBOMLines;
		return this;
	}

	private List<I_PP_Order_BOMLine> getTargetOrderBOMLines()
	{
		if (targetOrderBOMLines == null || targetOrderBOMLines.isEmpty())
		{
			throw new HUException("No BOM lines were configured");
		}

		return targetOrderBOMLines;
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLine(final I_PP_Order_BOMLine targetOrderBOMLine)
	{
		Check.assumeNotNull(targetOrderBOMLine, "targetOrderBOMLine not null");
		return setTargetOrderBOMLines(ImmutableList.of(targetOrderBOMLine));
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLinesByPPOrderId(final int ppOrderId)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.load(ppOrderId, I_PP_Order.class);
		Check.assumeNotNull(ppOrder, "ppOrder not null");

		final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);

		final List<I_PP_Order_BOMLine> ppOrderBOMLines = ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder, I_PP_Order_BOMLine.class)
				.stream()
				.filter(line -> PPOrderUtil.isIssue(line.getComponentType()))
				.collect(GuavaCollectors.toImmutableList());

		return setTargetOrderBOMLines(ppOrderBOMLines);
	}

	private I_PP_Order_BOMLine getTargetOrderBOMLine(final int productId)
	{
		final List<I_PP_Order_BOMLine> targetBOMLines = getTargetOrderBOMLines();
		return targetBOMLines
				.stream()
				.filter(bomLine -> bomLine.getM_Product_ID() == productId)
				.findFirst()
				.orElseThrow(() -> new HUException("No BOM line found for productId=" + productId + " in " + targetBOMLines));
	}

	@Override
	public void reverseDraftIssue(final I_PP_Order_Qty candidate)
	{
		if (candidate.isProcessed())
		{
			throw new HUException("Cannot reverse candidate because it's already processed: " + candidate);
		}

		final I_M_HU huToIssue = candidate.getM_HU();
		huLockBL.unlock(huToIssue, HUPPOrderIssueProducer.lockOwner);

		//
		// Delete the candidate
		huPPOrderQtyDAO.delete(candidate);
	}
}
