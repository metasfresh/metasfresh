package de.metas.material.dispo.commons.repository.repohelpers;

import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class DemandDetailRepoHelper
{
	public DemandDetail forDemandDetailRecord(
			@NonNull final I_MD_Candidate_Demand_Detail demandDetailRecord)
	{
		final I_MD_Candidate demandRecord = demandDetailRecord.getMD_Candidate();

		return DemandDetail.builder()
				.shipmentScheduleId(demandDetailRecord.getM_ShipmentSchedule_ID())
				.forecastId(demandRecord.getM_Forecast_ID())
				.forecastLineId(demandDetailRecord.getM_ForecastLine_ID())
				.orderId(demandRecord.getC_OrderSO_ID())
				.orderLineId(demandDetailRecord.getC_OrderLine_ID())
				.subscriptionProgressId(demandDetailRecord.getC_SubscriptionProgress_ID())
				.inOutLineId(demandDetailRecord.getM_InOutLine_ID())
				.qty(demandDetailRecord.getPlannedQty())
				.build();
	}
}
