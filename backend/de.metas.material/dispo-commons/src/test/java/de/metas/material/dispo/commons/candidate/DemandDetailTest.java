package de.metas.material.dispo.commons.candidate;

import static de.metas.common.util.IdConstants.UNSPECIFIED_REPO_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
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
	@BeforeEach
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
		final DemandDetail demandDetail = DemandDetail.forDocumentLine(20, orderLineDescriptor, TEN);
		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(20);

		assertThat(demandDetail.getForecastId()).isEqualTo(UNSPECIFIED_REPO_ID);
		assertThat(demandDetail.getForecastLineId()).isEqualTo(UNSPECIFIED_REPO_ID);

		assertThat(demandDetail.getSubscriptionProgressId()).isEqualTo(UNSPECIFIED_REPO_ID);

		assertThat(demandDetail.getOrderId()).isEqualTo(50);
		assertThat(demandDetail.getOrderLineId()).isEqualTo(60);

		assertThat(demandDetail.getQty()).isEqualByComparingTo(TEN);
	}

	@Test
	public void forDocumentDescriptor_SubscriptionLineDescriptor()
	{
		final SubscriptionLineDescriptor subscriptionLineDescriptor = SubscriptionLineDescriptor.builder()
				.flatrateTermId(10)
				.subscriptionProgressId(20)
				.subscriptionBillBPartnerId(30).build();

		final DemandDetail demandDetail = DemandDetail.forDocumentLine(20, subscriptionLineDescriptor, TEN);

		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(20);

		assertThat(demandDetail.getForecastId()).isEqualTo(UNSPECIFIED_REPO_ID);
		assertThat(demandDetail.getForecastLineId()).isEqualTo(UNSPECIFIED_REPO_ID);

		assertThat(demandDetail.getSubscriptionProgressId()).isEqualTo(20);

		assertThat(demandDetail.getOrderId()).isEqualTo(UNSPECIFIED_REPO_ID);
		assertThat(demandDetail.getOrderLineId()).isEqualTo(UNSPECIFIED_REPO_ID);

		assertThat(demandDetail.getQty()).isEqualByComparingTo(TEN);
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

		assertThat(demandDetail.getQty()).isEqualByComparingTo(TEN);
	}

	@Test
	public void forSupplyRequiredDescriptorOrNull()
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = SupplyRequiredDescriptor.builder()
				.demandCandidateId(5)
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
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
}
