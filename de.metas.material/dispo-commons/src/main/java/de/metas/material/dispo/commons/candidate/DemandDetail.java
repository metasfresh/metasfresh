package de.metas.material.dispo.commons.candidate;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Value
public class DemandDetail
{
	public static DemandDetail forDocumentDescriptor(
			final int shipmentScheduleId,
			@NonNull final DocumentLineDescriptor documentDescriptor)
	{
		final int orderLineId;
		final int subscriptionProgressId;
		if (documentDescriptor instanceof OrderLineDescriptor)
		{
			orderLineId = ((OrderLineDescriptor)documentDescriptor).getOrderLineId();
			subscriptionProgressId = -1;
		}
		else if (documentDescriptor instanceof SubscriptionLineDescriptor)
		{
			orderLineId = -1;
			subscriptionProgressId = ((SubscriptionLineDescriptor)documentDescriptor).getSubscriptionProgressId();
		}
		else
		{
			Check.errorIf(true,
					"The given documentDescriptor has an unexpected type; documentDescriptor={}", documentDescriptor);
			return null;
		}

		return new DemandDetail(
				-1,
				shipmentScheduleId,
				orderLineId,
				subscriptionProgressId);
	}

	public static DemandDetail createOrNull(
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		if (supplyRequiredDescriptor == null)
		{
			return null;
		}
		return new DemandDetail(
				supplyRequiredDescriptor.getForecastLineId(),
				supplyRequiredDescriptor.getShipmentScheduleId(),
				supplyRequiredDescriptor.getOrderLineId(),
				supplyRequiredDescriptor.getSubscriptionProgressId());
	}

	public static DemandDetail forDemandDetailRecord(
			@NonNull final I_MD_Candidate_Demand_Detail demandDetailRecord)
	{
		return new DemandDetail(
				demandDetailRecord.getM_ForecastLine_ID(),
				demandDetailRecord.getM_ShipmentSchedule_ID(),
				demandDetailRecord.getC_OrderLine_ID(),
				demandDetailRecord.getC_SubscriptionProgress_ID());
	}

	public static DemandDetail forShipmentScheduleIdAndOrderLineId(
			final int shipmentScheduleId,
			final int orderLineId)
	{
		return new DemandDetail(-1, shipmentScheduleId, orderLineId, -1);
	}

	public static DemandDetail forOrderLineIdOrNull(int salesOrderLineId)
	{
		if (salesOrderLineId <= 0)
		{
			return null;
		}
		return new DemandDetail(-1, -1, salesOrderLineId, -1);
	}

	public static DemandDetail forForecastLineId(final int forecastLineId)
	{
		return new DemandDetail(forecastLineId, -1, -1, -1);
	}

	int forecastLineId;

	int shipmentScheduleId;

	int orderLineId;

	int subscriptionProgressId;
}
