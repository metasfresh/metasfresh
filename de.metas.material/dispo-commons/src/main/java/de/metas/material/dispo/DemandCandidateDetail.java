package de.metas.material.dispo;

import java.util.Optional;

import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.event.MaterialDemandDescr;
import de.metas.material.event.ShipmentScheduleEvent;
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
public class DemandCandidateDetail
{
	public static DemandCandidateDetail createOrNull(@NonNull final Optional<MaterialDemandDescr> materialDemandDescr)
	{
		if (!materialDemandDescr.isPresent())
		{
			return null;
		}
		return new DemandCandidateDetail(
				materialDemandDescr.get().getForecastLineId(),
				materialDemandDescr.get().getShipmentScheduleId(),
				materialDemandDescr.get().getOrderLineId());
	}

	public static DemandCandidateDetail forDemandDetailRecord(@NonNull final I_MD_Candidate_Demand_Detail demandDetailRecord)
	{
		return new DemandCandidateDetail(
				demandDetailRecord.getM_ForecastLine_ID(), 
				demandDetailRecord.getM_ShipmentSchedule_ID(),
				demandDetailRecord.getC_OrderLine_ID());
	}
	
	public static DemandCandidateDetail forShipmentScheduleEvent(ShipmentScheduleEvent event)
	{
		return new DemandCandidateDetail(-1, event.getShipmentScheduleId(), event.getOrderLineId());
	}

	public static DemandCandidateDetail forOrderLineIdOrNull(int salesOrderLineId)
	{
		if (salesOrderLineId <= 0)
		{
			return null;
		}
		return new DemandCandidateDetail(-1, -1, salesOrderLineId);
	}

	public static DemandCandidateDetail forForecastLineId(final int forecastLineId)
	{
		return new DemandCandidateDetail(forecastLineId, -1, -1);
	}

	int forecastLineId;

	int shipmentScheduleId;

	private final int orderLineId;


}
