/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning.process;

import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

/**
 * Combines the selected delivery plannings into ONE delivery instruction - as opposed to
 * {@link M_Delivery_Planning_GenerateDeliveryInstruction}, which creates one instruction per planning.
 * <p>
 * A thin adapter: every rule lives in {@link DeliveryPlanningService}, so a cucumber step drives the same code
 * path the WebUI drives instead of re-implementing the flow.
 * <p>
 * Deliberately does NOT navigate to the instruction it created ({@code setRecordToOpen}). The action requires two
 * or more rows, so it is always launched from a grid the planner is working through; jumping away would cost the
 * grid's filter and scroll position on every consolidation. The generated notification already links to the new
 * document for the planner who does want to go there.
 */
public class M_Delivery_Planning_CombineIntoDeliveryInstruction extends JavaProcess implements IProcessPrecondition
{
	/**
	 * Same cap as the order-to-transport-order precedent {@code AddOrderLinesToShipperTransportation}: the
	 * precondition loads the selection, and no real consolidation puts hundreds of plannings on one truck.
	 */
	private static final int MAX_SELECTION_SIZE = 100;

	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@Param(parameterName = "IsComplete")
	private boolean p_IsComplete;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isMoreThanOneSelected())
		{
			// hidden rather than shown-and-disabled: at a single row Combine and Generate produce the same
			// result, so there is nothing for the planner to learn from seeing it
			return ProcessPreconditionsResolution.rejectWithInternalReason("Combining needs at least two delivery plannings");
		}

		if (context.isMoreThanAllowedSelected(MAX_SELECTION_SIZE))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(MAX_SELECTION_SIZE);
		}

		final DeliveryPlanningList selectedDeliveryPlannings = deliveryPlanningService.getBySelection(context.getQueryFilter(I_M_Delivery_Planning.class));

		return deliveryPlanningService.getCombineRejectionReason(selectedDeliveryPlannings)
				.map(ProcessPreconditionsResolution::reject)
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	@Override
	protected String doIt()
	{
		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		deliveryPlanningService.combine(selectedDeliveryPlanningsFilter, p_IsComplete);

		return MSG_OK;
	}
}
