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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryPlanningList;
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.NonNull;
import org.compiere.model.Null;

import javax.annotation.Nullable;

/**
 * The selection-shaped precondition guards the delivery planning actions repeat verbatim - "is anything
 * selected", "is too much selected", "is more than one row selected". Business rules stay on the owning process.
 */
public final class DeliveryPlanningProcessHelper
{
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

	/**
	 * The key fields carried by a hidden process parameter of their own, so a test can pin that set against
	 * {@link AggregationKeyField} rather than leave the two to drift apart silently.
	 */
	public static ImmutableSet<AggregationKeyField> aggregationKeyParameterFields()
	{
		return ImmutableSet.copyOf(AGGREGATION_KEY_PARAMETERS.values());
	}

	/**
	 * Whether the column is one of the hidden aggregation key parameters - the cheap test a caller answers
	 * before loading the selection the default value would be read from.
	 */
	public static boolean isAggregationKeyParameter(@NonNull final String columnName)
	{
		return AGGREGATION_KEY_PARAMETERS.containsKey(columnName);
	}

	/** @return {@code null} when the column is not a key parameter, so the caller falls through. */
	@Nullable
	public static Object getAggregationKeyParameterDefault(
			@NonNull final DeliveryPlanningList selectedDeliveryPlannings,
			@NonNull final String columnName)
	{
		final AggregationKeyField field = AGGREGATION_KEY_PARAMETERS.get(columnName);
		if (field == null)
		{
			return null;
		}

		return selectedDeliveryPlannings.getSingleAggregationKeyValue(field)
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
