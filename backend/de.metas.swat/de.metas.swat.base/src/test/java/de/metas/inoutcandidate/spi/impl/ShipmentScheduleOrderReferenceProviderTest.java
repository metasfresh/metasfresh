package de.metas.inoutcandidate.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.interfaces.I_C_OrderLine;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ShipmentScheduleOrderReferenceProviderTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void computeOrderLineDeliveryDate_OrderDatePromised()
	{
		final I_C_Order order = createOrder(LocalDate.of(2019, Month.SEPTEMBER, 1));
		final I_C_OrderLine orderLine = prepareOrderLine().build();

		final ZonedDateTime deliveryDate = ShipmentScheduleOrderReferenceProvider.computeOrderLineDeliveryDate(orderLine, order);
		assertThat(deliveryDate.toLocalDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));
	}

	@Test
	public void computeOrderLineDeliveryDate_OrderLineDatePromised()
	{
		final I_C_Order order = createOrder(LocalDate.of(2019, Month.SEPTEMBER, 1));
		final I_C_OrderLine orderLine = prepareOrderLine()
				.datePromised(LocalDate.of(2019, Month.SEPTEMBER, 2))
				.build();

		final ZonedDateTime deliveryDate = ShipmentScheduleOrderReferenceProvider.computeOrderLineDeliveryDate(orderLine, order);
		assertThat(deliveryDate.toLocalDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 2));
	}

	@Test
	public void computeOrderLineDeliveryDate_OrderLinePresetDateShipped()
	{
		final I_C_Order order = createOrder(LocalDate.of(2019, Month.SEPTEMBER, 1));
		final I_C_OrderLine orderLine = prepareOrderLine()
				.datePromised(LocalDate.of(2019, Month.SEPTEMBER, 2))
				.presetDateShipped(LocalDate.of(2019, Month.SEPTEMBER, 3))
				.build();

		final ZonedDateTime deliveryDate = ShipmentScheduleOrderReferenceProvider.computeOrderLineDeliveryDate(orderLine, order);
		assertThat(deliveryDate.toLocalDate()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 3));
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
