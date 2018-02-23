package de.metas.material.dispo.commons.candidate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;

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
	@Test
	public void forDocumentDescriptor_OrderLineDescriptor()
	{
		final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.builder()
				.docTypeId(30)
				.orderBPartnerId(40)
				.orderId(50)
				.orderLineId(60)
				.build();
		final DemandDetail demandDetail = DemandDetail.forDocumentDescriptor(20, orderLineDescriptor);
		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(20);

		assertThat(demandDetail.getForecastId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getForecastLineId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getSubscriptionProgressId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getOrderId()).isEqualTo(50);
		assertThat(demandDetail.getOrderLineId()).isEqualTo(60);
	}

	@Test
	public void forDocumentDescriptor_SubscriptionLineDescriptor()
	{
		final SubscriptionLineDescriptor subscriptionLineDescriptor = SubscriptionLineDescriptor.builder()
				.flatrateTermId(10)
				.subscriptionProgressId(20)
				.subscriptionBillBPartnerId(30).build();

		final DemandDetail demandDetail = DemandDetail.forDocumentDescriptor(20, subscriptionLineDescriptor);

		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(20);

		assertThat(demandDetail.getForecastId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getForecastLineId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getSubscriptionProgressId()).isLessThanOrEqualTo(20);

		assertThat(demandDetail.getOrderId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getOrderLineId()).isLessThanOrEqualTo(0);
	}

	@Test
	public void forDocumentDescriptor_SubscriptionLineDescripto()
	{
		final DemandDetail demandDetail = DemandDetail.forForecastLineId(30, 20);

		assertThat(demandDetail.getShipmentScheduleId()).isLessThanOrEqualTo(0);

		assertThat(demandDetail.getForecastId()).isEqualTo(20);
		assertThat(demandDetail.getForecastLineId()).isEqualTo(30);

		assertThat(demandDetail.getSubscriptionProgressId()).isLessThanOrEqualTo(20);

		assertThat(demandDetail.getOrderId()).isLessThanOrEqualTo(0);
		assertThat(demandDetail.getOrderLineId()).isLessThanOrEqualTo(0);
	}
}
