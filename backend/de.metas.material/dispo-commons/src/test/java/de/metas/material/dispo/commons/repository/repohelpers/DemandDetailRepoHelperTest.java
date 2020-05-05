package de.metas.material.dispo.commons.repository.repohelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;

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

public class DemandDetailRepoHelperTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void forDemandDetailRecord()
	{
		final I_MD_Candidate demandRecord = newInstance(I_MD_Candidate.class);
		demandRecord.setC_OrderSO_ID(10);
		demandRecord.setM_Forecast_ID(20);
		demandRecord.setM_ShipmentSchedule_ID(99); // ignored by the method under test
		save(demandRecord);

		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(demandRecord);
		demandDetailRecord.setC_OrderLine_ID(40);
		demandDetailRecord.setM_ForecastLine_ID(50);
		demandDetailRecord.setM_ShipmentSchedule_ID(60);
		demandDetailRecord.setC_SubscriptionProgress_ID(70);
		save(demandDetailRecord);

		// invoke the method under test
		final DemandDetail demandDetail = DemandDetailRepoHelper.forDemandDetailRecord(demandDetailRecord);

		assertThat(demandDetail.getOrderId()).isEqualTo(10);
		assertThat(demandDetail.getForecastId()).isEqualTo(20);
		assertThat(demandDetail.getOrderLineId()).isEqualTo(40);
		assertThat(demandDetail.getForecastLineId()).isEqualTo(50);
		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(60);
		assertThat(demandDetail.getSubscriptionProgressId()).isEqualTo(70);
	}
}
