package de.metas.handlingunits.pporder.api;

import java.time.ZonedDateTime;

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
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer.CreateDraftIssuesCommand;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Issues given HUs to configured Order BOM Lines.
 *
 * @author tsa
 *
 */
public class HUPPOrderIssueProducer
{
	public enum ProcessIssueCandidatesPolicy
	{
		NEVER, ALWAYS, IF_ORDER_PLANNING_STATUS_IS_COMPLETE
	}

	//
	// Services
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);

	//
	// Parameters
	private final PPOrderId ppOrderId;
	private I_PP_Order _ppOrder; // lazy
	private ZonedDateTime movementDate;
	private ImmutableList<I_PP_Order_BOMLine> targetOrderBOMLines;
	private Quantity fixedQtyToIssue;
	private boolean considerIssueMethodForQtyToIssueCalculation = true;
	private ProcessIssueCandidatesPolicy processCandidatesPolicy = ProcessIssueCandidatesPolicy.IF_ORDER_PLANNING_STATUS_IS_COMPLETE;
	private boolean changeHUStatusToIssued = true;
	private PickingCandidateId pickingCandidateId;

	public HUPPOrderIssueProducer(@NonNull final PPOrderId ppOrderId)
	{
		this.ppOrderId = ppOrderId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("ppOrderId", ppOrderId)
				.add("movementDate", movementDate)
				.add("targetOrderBOMLines", targetOrderBOMLines != null && !targetOrderBOMLines.isEmpty() ? targetOrderBOMLines : null)
				.toString();
	}

	/**
	 * Convenient way of calling {@link #createIssues(Collection)}.
	 */
	public Optional<I_PP_Order_Qty> createIssue(@NonNull final I_M_HU hu)
	{
		final List<I_PP_Order_Qty> candidates = createIssues(ImmutableSet.of(hu));
		if (candidates.isEmpty())
		{
			return Optional.empty();
		}
		else if (candidates.size() == 1)
		{
			return Optional.of(candidates.get(0));
		}
		else
		{
			throw new AdempiereException("Expected single candidate but got: " + candidates);
		}
	}

	/**
	 * Issue given <code>HUs</code> to configured {@link I_PP_Order_BOMLine}s.
	 * 
	 * If order's planning status is {@link PPOrderPlanningStatus#COMPLETE}
	 * then the candidates will be processed (so cost collectors will be generated).
	 *
	 * @param hus
	 * @return generated candidates
	 */
	public List<I_PP_Order_Qty> createIssues(@NonNull final Collection<I_M_HU> hus)
	{
		final List<I_PP_Order_BOMLine> targetOrderBOMLines = getTargetOrderBOMLines();

		final List<I_PP_Order_Qty> candidates = CreateDraftIssuesCommand.builder()
				.targetOrderBOMLines(targetOrderBOMLines)
				.movementDate(movementDate)
				.fixedQtyToIssue(fixedQtyToIssue)
				.considerIssueMethodForQtyToIssueCalculation(considerIssueMethodForQtyToIssueCalculation)
				.issueFromHUs(hus)
				.changeHUStatusToIssued(changeHUStatusToIssued)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.execute();

		if (!candidates.isEmpty() && isProcessCandidates())
		{
			HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
					.setCandidatesToProcess(candidates)
					.process();
		}

		return candidates;
	}

	private static PPOrderId extractSinglePPOrderId(@NonNull final List<I_PP_Order_BOMLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines is not empty");
		final ImmutableSet<PPOrderId> ppOrderIds = lines.stream()
				.map(I_PP_Order_BOMLine::getPP_Order_ID)
				.map(PPOrderId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		if (ppOrderIds.size() == 1)
		{
			return ppOrderIds.iterator().next();
		}
		else
		{
			throw new AdempiereException("More than one ppOrderId found: " + ppOrderIds)
					.setParameter("lines", lines)
					.appendParametersToMessage();
		}
	}

	/**
	 * Sets movement date to be used in generated underlying {@link I_PP_Cost_Collector}s.
	 *
	 * @param movementDate may be {@code null} in which case, the current time is used.
	 */
	public HUPPOrderIssueProducer movementDate(final ZonedDateTime movementDate)
	{
		this.movementDate = movementDate;
		return this;
	}

	private I_PP_Order getPpOrder()
	{
		I_PP_Order ppOrder = this._ppOrder;
		if (ppOrder == null)
		{
			ppOrder = this._ppOrder = ppOrdersRepo.getById(ppOrderId);
		}
		return ppOrder;
	}

	private ImmutableList<I_PP_Order_BOMLine> getTargetOrderBOMLines()
	{
		if (targetOrderBOMLines != null && !targetOrderBOMLines.isEmpty())
		{
			return targetOrderBOMLines;
		}

		final ImmutableList<I_PP_Order_BOMLine> allOrderBOMLines = retrieveIssueOrderBOMLines(ppOrderId);
		if (!allOrderBOMLines.isEmpty())
		{
			return allOrderBOMLines;
		}

		throw new AdempiereException("No issue BOM lines found for " + ppOrderId);
	}

	private ImmutableList<I_PP_Order_BOMLine> retrieveIssueOrderBOMLines(final PPOrderId orderId)
	{
		return ppOrderBOMsRepo.retrieveOrderBOMLines(orderId)
				.stream()
				.filter(line -> BOMComponentType.ofCode(line.getComponentType()).isIssue())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Sets manufacturing order BOM Lines which needs to be considered when issuing.
	 *
	 * @param targetOrderBOMLines may not be {@code null} and need to contain lines that match the products of the HUs we give as parameters to {@link #createIssues(Collection)}.
	 */
	public HUPPOrderIssueProducer targetOrderBOMLines(@NonNull final List<I_PP_Order_BOMLine> targetOrderBOMLines)
	{
		Check.assumeNotEmpty(targetOrderBOMLines, "Parameter targetOrderBOMLines is not empty");

		//
		// Make sure we have only issue BOM lines
		final List<I_PP_Order_BOMLine> notIssueBOMLines = targetOrderBOMLines.stream()
				.filter(bomLine -> !BOMComponentType.ofCode(bomLine.getComponentType()).isIssue())
				.collect(ImmutableList.toImmutableList());
		if (!notIssueBOMLines.isEmpty())
		{
			throw new AdempiereException("Only issue BOM lines are allowed but got: " + notIssueBOMLines);
		}

		//
		// Make sure all lines are from our manfacturing order
		final PPOrderId ppOrderIdFromBOMLines = extractSinglePPOrderId(targetOrderBOMLines);
		if (!ppOrderIdFromBOMLines.equals(ppOrderId))
		{
			throw new AdempiereException("PPOrderId mismatch. Expected " + ppOrderId + " but BOM lines have " + ppOrderIdFromBOMLines);
		}

		//
		this.targetOrderBOMLines = ImmutableList.copyOf(targetOrderBOMLines);
		return this;
	}

	/**
	 * Convenient way of calling {@link #targetOrderBOMLines(List)} with one bom line.
	 *
	 * @param targetOrderBOMLine
	 */
	public HUPPOrderIssueProducer targetOrderBOMLine(@NonNull final I_PP_Order_BOMLine targetOrderBOMLine)
	{
		return targetOrderBOMLines(ImmutableList.of(targetOrderBOMLine));
	}

	public HUPPOrderIssueProducer targetOrderBOMLine(@NonNull final PPOrderBOMLineId targetOrderBOMLineId)
	{
		final I_PP_Order_BOMLine targetOrderBOMLine = ppOrderBOMsRepo.getOrderBOMLineById(targetOrderBOMLineId);
		return targetOrderBOMLine(targetOrderBOMLine);
	}

	public HUPPOrderIssueProducer fixedQtyToIssue(@NonNull final Quantity fixedQtyToIssue)
	{
		this.fixedQtyToIssue = fixedQtyToIssue;
		return this;
	}

	public HUPPOrderIssueProducer considerIssueMethodForQtyToIssueCalculation(boolean considerIssueMethodForQtyToIssueCalculation)
	{
		this.considerIssueMethodForQtyToIssueCalculation = considerIssueMethodForQtyToIssueCalculation;
		return this;
	}

	public HUPPOrderIssueProducer processCandidates(@NonNull final ProcessIssueCandidatesPolicy processCandidatesPolicy)
	{
		this.processCandidatesPolicy = processCandidatesPolicy;
		return this;
	}

	private boolean isProcessCandidates()
	{
		final ProcessIssueCandidatesPolicy processCandidatesPolicy = this.processCandidatesPolicy;
		if (ProcessIssueCandidatesPolicy.NEVER.equals(processCandidatesPolicy))
		{
			return false;
		}
		else if (ProcessIssueCandidatesPolicy.ALWAYS.equals(processCandidatesPolicy))
		{
			return true;
		}
		else if (ProcessIssueCandidatesPolicy.IF_ORDER_PLANNING_STATUS_IS_COMPLETE.equals(processCandidatesPolicy))
		{
			final I_PP_Order ppOrder = getPpOrder();
			final PPOrderPlanningStatus orderPlanningStatus = PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus());
			return PPOrderPlanningStatus.COMPLETE.equals(orderPlanningStatus);
		}
		else
		{
			throw new AdempiereException("Unknown processCandidatesPolicy: " + processCandidatesPolicy);
		}
	}

	public HUPPOrderIssueProducer changeHUStatusToIssued(final boolean changeHUStatusToIssued)
	{
		this.changeHUStatusToIssued = changeHUStatusToIssued;
		return this;
	}

	public HUPPOrderIssueProducer pickingCandidateId(final PickingCandidateId pickingCandidateId)
	{
		this.pickingCandidateId = pickingCandidateId;
		return this;
	}
}
