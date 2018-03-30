package de.metas.material.dispo.commons.candidate;

import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;

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

public class DemandDetailTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void forDocumentDescriptor_OrderLineDescriptor()
	{
		final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.builder()
				.docTypeId(30)
				.orderBPartnerId(40)
				.orderId(50)
				.orderLineId(60)
				.build();
		final DemandDetail demandDetail = DemandDetail.forDocumentDescriptor(20, orderLineDescriptor, TEN);
		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(20);

		assertThat(demandDetail.getForecastId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getForecastLineId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getSubscriptionProgressId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getOrderId()).isEqualTo(50);
		assertThat(demandDetail.getOrderLineId()).isEqualTo(60);

		assertThat(demandDetail.getPlannedQty()).isEqualByComparingTo(TEN);
	}

	@Test
	public void forDocumentDescriptor_SubscriptionLineDescriptor()
	{
		final SubscriptionLineDescriptor subscriptionLineDescriptor = SubscriptionLineDescriptor.builder()
				.flatrateTermId(10)
				.subscriptionProgressId(20)
				.subscriptionBillBPartnerId(30).build();

		final DemandDetail demandDetail = DemandDetail.forDocumentDescriptor(20, subscriptionLineDescriptor, TEN);

		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(20);

		assertThat(demandDetail.getForecastId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getForecastLineId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getSubscriptionProgressId()).isLessThanOrEqualTo(20);

		assertThat(demandDetail.getOrderId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getOrderLineId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getPlannedQty()).isEqualByComparingTo(TEN);
	}

	@Test
	public void forForecastLineId()
	{
		final DemandDetail demandDetail = DemandDetail.forForecastLineId(30, 20, TEN);

		assertThat(demandDetail.getShipmentScheduleId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getForecastId()).isEqualTo(20);
		assertThat(demandDetail.getForecastLineId()).isEqualTo(30);

		assertThat(demandDetail.getSubscriptionProgressId()).isLessThanOrEqualTo(20);

		assertThat(demandDetail.getOrderId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getOrderLineId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getPlannedQty()).isEqualByComparingTo(TEN);
	}

	@Test
	public void forSupplyRequiredDescriptorOrNull()
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = SupplyRequiredDescriptor.builder()
				.demandCandidateId(5)
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.forecastId(10)
				.forecastLineId(20)
				.orderId(30)
				.orderLineId(40)
				.shipmentScheduleId(50)
				.subscriptionProgressId(60)
				.materialDescriptor(createMaterialDescriptor())
				.build();
		final DemandDetail demandDetail = DemandDetail.forSupplyRequiredDescriptorOrNull(supplyRequiredDescriptor);

		assertThat(demandDetail.getDemandCandidateId()).isEqualTo(5);
		assertThat(demandDetail.getForecastId()).isEqualTo(10);
		assertThat(demandDetail.getForecastLineId()).isEqualTo(20);
		assertThat(demandDetail.getOrderId()).isEqualTo(30);
		assertThat(demandDetail.getOrderLineId()).isEqualTo(40);
		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(50);
		assertThat(demandDetail.getSubscriptionProgressId()).isEqualTo(60);
	}

	@Test
	public void forSupplyRequiredDescriptorOrNull_when_null_then_null()
	{
		assertThat(DemandDetail.forSupplyRequiredDescriptorOrNull(null)).isNull();
	}

	@Test
	public void forDemandDetailRecord()
	{
		final I_MD_Candidate demandRecord = newInstance(I_MD_Candidate.class);
		demandRecord.setC_Order_ID(10);
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
		final DemandDetail demandDetail = DemandDetail.forDemandDetailRecord(demandDetailRecord);

		assertThat(demandDetail.getOrderId()).isEqualTo(10);
		assertThat(demandDetail.getForecastId()).isEqualTo(20);
		assertThat(demandDetail.getOrderLineId()).isEqualTo(40);
		assertThat(demandDetail.getForecastLineId()).isEqualTo(50);
		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(60);
		assertThat(demandDetail.getSubscriptionProgressId()).isEqualTo(70);
	}

}
