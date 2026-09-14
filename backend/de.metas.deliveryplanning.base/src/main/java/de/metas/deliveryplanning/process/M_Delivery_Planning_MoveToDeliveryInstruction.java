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

import com.google.common.base.Suppliers;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

import javax.annotation.Nullable;

import java.util.function.Supplier;

import static de.metas.deliveryplanning.process.M_Delivery_Planning_CombineIntoDeliveryInstruction.MAX_SELECTION_SIZE;

/**
 * Moves the selected delivery plannings from the draft delivery instruction they are on to another draft one:
 * the source allocation and its shipping package are released, the planning's dates return to their order-derived
 * origin, and a new allocation is created on the target.
 * <p>
 * The counterpart of {@link M_Delivery_Planning_AddToDeliveryInstruction}, which puts a planning that is on NO
 * instruction on one. Unavailable as soon as the selection holds an unallocated planning; Add is the action for
 * those.
 */
public class M_Delivery_Planning_MoveToDeliveryInstruction extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@NonNull private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	/**
	 * The selection every parameter default is read from, loaded at most once: the framework asks this instance
	 * for a default once per parameter, and the selection cannot change while the dialog is open.
	 */
	private final Supplier<DeliveryPlanningList> selectedDeliveryPlanningsForDefaults = Suppliers.memoize(
			() -> deliveryPlanningService.getBySelection(getProcessInfo().getQueryFilterOrElseFalse()));

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, mandatory = true)
	private ShipperTransportationId p_M_ShipperTransportation_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution.firstRejectOrElseAccept(
				() -> DeliveryPlanningProcessHelper.checkAnySelection(context),
				() -> DeliveryPlanningProcessHelper.checkAtMostSelected(context, MAX_SELECTION_SIZE),
				() -> checkSelectionCanBeMoved(context));
	}

	/**
	 * Shown-and-disabled with its reason, not hidden: the reason says the selection is on no instruction, which
	 * points the planner at Add.
	 */
	private ProcessPreconditionsResolution checkSelectionCanBeMoved(@NonNull final IProcessPreconditionsContext context)
	{
		final DeliveryPlanningList selectedDeliveryPlannings = deliveryPlanningService.getBySelection(context.getQueryFilter(I_M_Delivery_Planning.class));

		// null target: the parameter dialog has not been shown yet, so only the selection can be judged here
		return deliveryPlanningService.getMoveToRejectionReason(selectedDeliveryPlannings, null)
				.map(ProcessPreconditionsResolution::reject)
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		return DeliveryPlanningProcessHelper.getParameterDefaultValue(
				selectedDeliveryPlanningsForDefaults,
				parameter.getColumnName());
	}

	@Override
	protected String doIt()
	{
		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		deliveryPlanningService.moveTo(selectedDeliveryPlanningsFilter, p_M_ShipperTransportation_ID);

		return MSG_OK;
	}
}
