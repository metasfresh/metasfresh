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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.NonNull;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.Null;

import javax.annotation.Nullable;

import java.util.function.Supplier;

/**
 * What the delivery planning actions repeat verbatim: the selection-shaped precondition guards - "is anything
 * selected", "is too much selected", "is more than one row selected" - and the parameter defaults their dialogs
 * read off that selection. Business rules stay on the owning process.
 */
public final class DeliveryPlanningProcessHelper
{
	/**
	 * The direction the target instruction has to match; not shown to the planner, read only by the target
	 * parameter's value rule to narrow the offered instructions to the selection's own direction.
	 */
	private static final String PARAM_TransportDirection = I_M_Delivery_Planning.COLUMNNAME_TransportDirection;

	private static final ImmutableMap<String, AggregationKeyField> AGGREGATION_KEY_PARAMETERS = ImmutableMap
			.<String, AggregationKeyField>builder()
			.put(I_M_ShipperTransportation.COLUMNNAME_AD_Org_ID, AggregationKeyField.Organisation)
			.put(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, AggregationKeyField.Forwarder)
			.put(I_M_ShipperTransportation.COLUMNNAME_C_Incoterms_ID, AggregationKeyField.Incoterms)
			.put(I_M_ShipperTransportation.COLUMNNAME_IncotermLocation, AggregationKeyField.IncotermLocation)
			.put(I_M_ShipperTransportation.COLUMNNAME_M_MeansOfTransportation_ID, AggregationKeyField.MeansOfTransportation)
			.put(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID, AggregationKeyField.LoadingAddress)
			.put(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID, AggregationKeyField.DeliveryAddress)
			.build();

	private DeliveryPlanningProcessHelper()
	{
	}

	public static ProcessPreconditionsResolution checkAnySelection(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	public static ProcessPreconditionsResolution checkAtMostSelected(
			@NonNull final IProcessPreconditionsContext context,
			final int maxSelectionSize)
	{
		if (context.isMoreThanAllowedSelected(maxSelectionSize))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(maxSelectionSize);
		}

		return ProcessPreconditionsResolution.accept();
	}

	/**
	 * The key fields carried by a hidden process parameter of their own: every {@link AggregationKeyField} except
	 * {@link AggregationKeyField#Direction}, which is fed by the pre-existing TransportDirection parameter.
	 */
	@VisibleForTesting
	static ImmutableSet<AggregationKeyField> aggregationKeyParameterFields()
	{
		return ImmutableSet.copyOf(AGGREGATION_KEY_PARAMETERS.values());
	}

	/**
	 * The default for one parameter of an Add-to / Move-to dialog, or
	 * {@link IProcessDefaultParametersProvider#DEFAULT_VALUE_NOTAVAILABLE} for a column that is neither the
	 * direction nor a key parameter - decided by map lookup, before the supplier is asked, so a dialog parameter
	 * that is none of ours costs no load.
	 */
	@Nullable
	public static Object getParameterDefaultValue(
			@NonNull final Supplier<DeliveryPlanningList> selectedDeliveryPlannings,
			@NonNull final String columnName)
	{
		if (PARAM_TransportDirection.equals(columnName))
		{
			// single by the precondition, which rejects a selection spanning two directions
			return selectedDeliveryPlannings.get()
					.getSingleTransportDirection()
					.map(TransportDirection::getCode)
					.map(Object.class::cast)
					.orElse(Null.NULL);
		}

		final AggregationKeyField field = AGGREGATION_KEY_PARAMETERS.get(columnName);
		if (field == null)
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}

		return selectedDeliveryPlannings.get()
				.getSingleAggregationKeyValue(field)
				.map(AggregationKeyField::toProcessParameterValue)
				.orElse(Null.NULL);
	}

	public static ProcessPreconditionsResolution checkSingleSelection(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
