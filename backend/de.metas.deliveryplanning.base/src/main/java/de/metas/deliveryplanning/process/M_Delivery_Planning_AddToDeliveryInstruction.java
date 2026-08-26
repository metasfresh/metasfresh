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
import de.metas.deliveryplanning.DeliveryPlanningType;
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
import org.compiere.model.Null;

import javax.annotation.Nullable;

import static de.metas.deliveryplanning.process.M_Delivery_Planning_CombineIntoDeliveryInstruction.MAX_SELECTION_SIZE;

/**
 * Puts the selected delivery plannings on an EXISTING draft delivery instruction, taking each off whatever draft
 * instruction it was on before - as opposed to
 * {@link M_Delivery_Planning_CombineIntoDeliveryInstruction}, which creates a new one.
 * <p>
 * A thin adapter: every rule lives in {@link DeliveryPlanningService}, so a cucumber step drives the same code
 * path the WebUI drives instead of re-implementing the flow.
 * <p>
 * The dialog has exactly one visible field, the target instruction. Its list is narrowed by the value rule to the
 * drafted delivery instructions of the selection's own direction, which is carried into the rule by the hidden
 * {@link #PARAM_M_Delivery_Planning_Type} parameter below - so the commonest wrong pick is unofferable rather than
 * rejected afterwards.
 */
public class M_Delivery_Planning_AddToDeliveryInstruction extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	/**
	 * The direction the target instruction has to match. Not shown to the planner - it is not a choice but a
	 * property of the selection, and the value rule of the target parameter is its only reader.
	 */
	private static final String PARAM_M_Delivery_Planning_Type = I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_Type;

	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, mandatory = true)
	private ShipperTransportationId p_M_ShipperTransportation_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (context.isMoreThanAllowedSelected(MAX_SELECTION_SIZE))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(MAX_SELECTION_SIZE);
		}

		final DeliveryPlanningList selectedDeliveryPlannings = deliveryPlanningService.getBySelection(context.getQueryFilter(I_M_Delivery_Planning.class));

		// null target: the parameter dialog has not been shown yet, so only the selection can be judged here
		return deliveryPlanningService.getAddToRejectionReason(selectedDeliveryPlannings, null)
				.map(ProcessPreconditionsResolution::reject)
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (PARAM_M_Delivery_Planning_Type.equals(parameter.getColumnName()))
		{
			// single by the precondition, which rejects a selection spanning two directions
			return deliveryPlanningService.getBySelection(getProcessInfo().getQueryFilterOrElseFalse())
					.getSingleType()
					.map(DeliveryPlanningType::getCode)
					.map(Object.class::cast)
					.orElse(Null.NULL);
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt()
	{
		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		deliveryPlanningService.addTo(selectedDeliveryPlanningsFilter, p_M_ShipperTransportation_ID);

		return MSG_OK;
	}
}
