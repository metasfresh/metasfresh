package de.metas.handlingunits.pporder.api.impl;

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

import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.PPOrderPlanningStatus;
import de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer.CreateDraftIssuesCommand;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Issues given HUs to configured Order BOM Lines.
 *
 * @author tsa
 *
 */
public class HUPPOrderIssueProducer implements IHUPPOrderIssueProducer
{
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);

	private PPOrderId _ppOrderId;
	private ZonedDateTime movementDate;
	private ImmutableList<I_PP_Order_BOMLine> targetOrderBOMLines;
	private boolean considerIssueMethodForQtyToIssueCalculation = true;

	@VisibleForTesting
	HUPPOrderIssueProducer()
	{
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("ppOrderId", _ppOrderId)
				.add("movementDate", movementDate)
				.add("targetOrderBOMLines", targetOrderBOMLines)
				.toString();
	}

	@Override
	public List<I_PP_Order_Qty> createIssues(@NonNull final Collection<I_M_HU> hus)
	{
		PPOrderId ppOrderId = getPpOrderIdOrNull();
		List<I_PP_Order_BOMLine> targetOrderBOMLines = getTargetOrderBOMLinesOrNull();
		if (targetOrderBOMLines == null || targetOrderBOMLines.isEmpty())
		{
			if (ppOrderId != null)
			{
				targetOrderBOMLines = retrieveIssueOrderBOMLines(ppOrderId);
				if (targetOrderBOMLines.isEmpty())
				{
					throw new AdempiereException("No issue BOM lines found for " + ppOrderId);
				}
			}
			else
			{
				throw new AdempiereException("No PP_Order_ID and not BOM lines set");
			}
		}

		final PPOrderId ppOrderIdFromBOMLines = extractSinglePPOrderId(targetOrderBOMLines);
		if (ppOrderId == null)
		{
			ppOrderId = ppOrderIdFromBOMLines;
		}
		else if (!ppOrderIdFromBOMLines.equals(ppOrderId))
		{
			throw new AdempiereException("PPOrderId mismatch. Expected " + ppOrderId + " but BOM lines have " + ppOrderIdFromBOMLines);
		}

		final I_PP_Order ppOrder = ppOrdersRepo.getById(ppOrderId);
		final PPOrderPlanningStatus orderPlanningStatus = PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus());

		final List<I_PP_Order_Qty> candidates = CreateDraftIssuesCommand.builder()
				.targetOrderBOMLines(targetOrderBOMLines)
				.movementDate(movementDate)
				.considerIssueMethodForQtyToIssueCalculation(considerIssueMethodForQtyToIssueCalculation)
				.hus(hus)
				.build()
				.execute();

		if (orderPlanningStatus == PPOrderPlanningStatus.COMPLETE)
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

	@Override
	public IHUPPOrderIssueProducer setMovementDate(final ZonedDateTime movementDate)
	{
		this.movementDate = movementDate;
		return this;
	}

	@Override
	public IHUPPOrderIssueProducer setOrderId(@NonNull final PPOrderId ppOrderId)
	{
		this._ppOrderId = ppOrderId;
		return this;
	}

	private PPOrderId getPpOrderIdOrNull()
	{
		return _ppOrderId;
	}

	private List<I_PP_Order_BOMLine> getTargetOrderBOMLinesOrNull()
	{
		return targetOrderBOMLines;
	}

	private ImmutableList<I_PP_Order_BOMLine> retrieveIssueOrderBOMLines(final PPOrderId orderId)
	{
		return ppOrderBOMsRepo.retrieveOrderBOMLines(orderId)
				.stream()
				.filter(line -> PPOrderUtil.isIssue(BOMComponentType.ofCode(line.getComponentType())))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLines(@NonNull final List<I_PP_Order_BOMLine> targetOrderBOMLines)
	{
		Check.assumeNotEmpty(targetOrderBOMLines, "Parameter targetOrderBOMLines is not empty");

		final List<I_PP_Order_BOMLine> notIssueBOMLines = targetOrderBOMLines.stream()
				.filter(bomLine -> !PPOrderUtil.isIssue(BOMComponentType.ofCode(bomLine.getComponentType())))
				.collect(ImmutableList.toImmutableList());
		if (!notIssueBOMLines.isEmpty())
		{
			throw new AdempiereException("Only issue BOM lines are allowed but got: " + notIssueBOMLines);
		}

		this.targetOrderBOMLines = ImmutableList.copyOf(targetOrderBOMLines);
		return this;
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLine(@NonNull final I_PP_Order_BOMLine targetOrderBOMLine)
	{
		return setTargetOrderBOMLines(ImmutableList.of(targetOrderBOMLine));
	}

	@Override
	public IHUPPOrderIssueProducer considerIssueMethodForQtyToIssueCalculation(boolean considerIssueMethodForQtyToIssueCalculation)
	{
		this.considerIssueMethodForQtyToIssueCalculation = considerIssueMethodForQtyToIssueCalculation;
		return this;
	}
}
