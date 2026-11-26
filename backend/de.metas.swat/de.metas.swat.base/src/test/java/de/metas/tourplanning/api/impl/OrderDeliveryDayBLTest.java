/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.tourplanning.api.impl;

import de.metas.interfaces.I_C_OrderLine;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class OrderDeliveryDayBLTest
{
	@Nested
	class computePreparationTime
	{
		@Test
		void daylightSaving_beginningOfSummerTime_minus_24h()
		{
			Assertions.assertThat(OrderDeliveryDayBL.computePreparationTime(ZonedDateTime.parse("2023-03-26T23:59:59+03:00[Europe/Bucharest]"), -24))
					.isEqualTo(ZonedDateTime.parse("2023-03-25T23:59:59+02:00[Europe/Bucharest]"));

			Assertions.assertThat(OrderDeliveryDayBL.computePreparationTime(ZonedDateTime.parse("2023-03-26T23:59:59+02:00[Europe/Berlin]"), -24))
					.isEqualTo(ZonedDateTime.parse("2023-03-25T23:59:59+01:00[Europe/Berlin]"));
		}
	}

	@Nested
	class computeDeliveryDateOrNull
	{
		private final ZoneId timeZone = ZoneId.systemDefault();

		@BeforeEach
		void init()
		{
			AdempiereTestHelper.get().init();
		}

		@Test
		void fallsBackToOrderDatePromised()
		{
			final I_C_Order order = createOrder(LocalDate.of(2019, Month.SEPTEMBER, 1));
			final I_C_OrderLine orderLine = prepareOrderLine().build();

			final ZonedDateTime deliveryDate = OrderDeliveryDayBL.computeDeliveryDateOrNull(order, orderLine, timeZone);
			assertThat(deliveryDate).isNotNull();
			assertThat(deliveryDate.toLocalDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));
		}

		@Test
		void prefersOrderLineDatePromisedOverHeader()
		{
			final I_C_Order order = createOrder(LocalDate.of(2019, Month.SEPTEMBER, 1));
			final I_C_OrderLine orderLine = prepareOrderLine()
					.datePromised(LocalDate.of(2019, Month.SEPTEMBER, 2))
					.build();

			final ZonedDateTime deliveryDate = OrderDeliveryDayBL.computeDeliveryDateOrNull(order, orderLine, timeZone);
			assertThat(deliveryDate).isNotNull();
			assertThat(deliveryDate.toLocalDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 2));
		}

		@Test
		void prefersPresetDateShippedOverDatePromised()
		{
			final I_C_Order order = createOrder(LocalDate.of(2019, Month.SEPTEMBER, 1));
			final I_C_OrderLine orderLine = prepareOrderLine()
					.datePromised(LocalDate.of(2019, Month.SEPTEMBER, 2))
					.presetDateShipped(LocalDate.of(2019, Month.SEPTEMBER, 3))
					.build();

			final ZonedDateTime deliveryDate = OrderDeliveryDayBL.computeDeliveryDateOrNull(order, orderLine, timeZone);
			assertThat(deliveryDate).isNotNull();
			assertThat(deliveryDate.toLocalDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 3));
		}

		@Test
		void returnsNullWhenNoDateAtAll()
		{
			final I_C_Order order = newInstance(I_C_Order.class);
			saveRecord(order);
			final I_C_OrderLine orderLine = prepareOrderLine().build();

			assertThat(OrderDeliveryDayBL.computeDeliveryDateOrNull(order, orderLine, timeZone)).isNull();
		}

		private I_C_Order createOrder(@NonNull final LocalDate datePromised)
		{
			final I_C_Order order = newInstance(I_C_Order.class);
			order.setDatePromised(TimeUtil.asTimestamp(datePromised));
			saveRecord(order);
			return order;
		}

		@Builder(builderMethodName = "prepareOrderLine", builderClassName = "_OrderLineBuilder")
		private I_C_OrderLine createOrderLine(final LocalDate datePromised, final LocalDate presetDateShipped)
		{
			final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
			orderLine.setDatePromised(TimeUtil.asTimestamp(datePromised));
			orderLine.setPresetDateShipped(TimeUtil.asTimestamp(presetDateShipped));
			saveRecord(orderLine);
			return orderLine;
		}
	}
}
