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

import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.material.planning.pporder.PPOrderId;

/**
 * Issues given HUs to configured Order BOM Lines.
 *
 * @author tsa
 *
 */
public interface IHUPPOrderIssueProducer
{
	/**
	 * Issue given <code>HUs</code> to configured {@link I_PP_Order_BOMLine}s.
	 * 
	 * If order's planning status is {@link PPOrderPlanningStatus#COMPLETE}
	 * then the candidates will be processed (so cost collectors will be generated).
	 *
	 * @param hus
	 * @return generated candidates
	 */
	List<I_PP_Order_Qty> createIssues(Collection<I_M_HU> hus);

	/**
	 * Convenient way of calling {@link #createIssues(Collection)}.
	 */
	default List<I_PP_Order_Qty> createIssue(final I_M_HU hu)
	{
		return createIssues(ImmutableSet.of(hu));
	}

	IHUPPOrderIssueProducer setOrderId(PPOrderId ppOrderId);

	
	/**
	 * Sets movement date to be used in generated underlying {@link I_PP_Cost_Collector}s.
	 *
	 * @param movementDate may be {@code null} in which case, the current time is used.
	 */
	IHUPPOrderIssueProducer setMovementDate(final ZonedDateTime movementDate);

	/**
	 * Sets manufacturing order BOM Lines which needs to be considered when issuing.
	 *
	 * @param targetOrderBOMLines may not be {@code null} and need to contain lines that match the products of the HUs we give as parameters to {@link #createIssues(Collection)}.
	 */
	IHUPPOrderIssueProducer setTargetOrderBOMLines(final List<I_PP_Order_BOMLine> targetOrderBOMLines);

	/**
	 * Convenient way of calling {@link #setTargetOrderBOMLines(List)} with one bom line.
	 *
	 * @param targetOrderBOMLine
	 */
	IHUPPOrderIssueProducer setTargetOrderBOMLine(I_PP_Order_BOMLine targetOrderBOMLine);
	
	IHUPPOrderIssueProducer considerIssueMethodForQtyToIssueCalculation(boolean considerIssueMethodForQtyToIssueCalculation);
}
