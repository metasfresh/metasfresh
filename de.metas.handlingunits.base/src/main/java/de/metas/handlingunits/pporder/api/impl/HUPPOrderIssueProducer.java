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
import org.adempiere.util.lang.IContextAware;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer.CreateDraftIssues;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderUtil;
import lombok.NonNull;

/**
 * Issues given HUs to configured Order BOM Lines.
 *
 * @author tsa
 *
 */
public class HUPPOrderIssueProducer implements IHUPPOrderIssueProducer
{
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IPPOrderProductAttributeDAO ppOrderProductAttributeDAO = Services.get(IPPOrderProductAttributeDAO.class);

	private Date movementDate;
	private List<I_PP_Order_BOMLine> targetOrderBOMLines;

	@VisibleForTesting
	HUPPOrderIssueProducer()
	{
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
	public IHUPPOrderIssueProducer setMovementDate(final Date movementDate)
	{
		this.movementDate = movementDate;
		return this;
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLines(@NonNull final List<I_PP_Order_BOMLine> targetOrderBOMLines)
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

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLine(@NonNull final I_PP_Order_BOMLine targetOrderBOMLine)
	{
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


	@Override
	public List<I_PP_Order_Qty> createDraftIssues(@NonNull final Collection<I_M_HU> hus)
	{
		return new CreateDraftIssues(targetOrderBOMLines, movementDate).createDraftIssues(hus);
	}
	
	@Override
	public void reverseDraftIssue(@NonNull final I_PP_Order_Qty candidate)
	{
		if (candidate.isProcessed())
		{
			throw new HUException("Cannot reverse candidate because it's already processed: " + candidate);
		}

		final I_M_HU huToIssue = candidate.getM_HU();

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(candidate);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		handlingUnitsBL.setHUStatus(huContext, huToIssue, X_M_HU.HUSTATUS_Active);
		Services.get(IHandlingUnitsDAO.class).saveHU(huToIssue);

		// Delete PP_Order_ProductAttributes for issue candidate's HU
		ppOrderProductAttributeDAO.deleteForHU(candidate.getPP_Order_ID(), huToIssue.getM_HU_ID());

		//
		// Delete the candidate
		huPPOrderQtyDAO.delete(candidate);
	}
}
