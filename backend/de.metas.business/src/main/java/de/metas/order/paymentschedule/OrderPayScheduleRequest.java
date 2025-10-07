/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.paymentschedule;

import de.metas.currency.Amount;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * Request object containing all information needed to create an OrderPaySchedule.
 * This class represents the input data required before persisting a payment schedule record.
 */
@Value
@Builder
public class OrderPayScheduleRequest
{
	@NonNull OrderId orderId;
	@NonNull PaymentTermBreakId paymentTermBreakId;
	@NonNull ReferenceDateType referenceDateType;
	@NonNull Amount dueAmount;
	@NonNull Timestamp dueDate;
	@NonNull Percent percent;
	@Nullable OrderPayScheduleStatus orderPayScheduleStatus;

	int seqNo;

	public OrderPaySchedule toOrderPaySchedule()
	{
		return OrderPaySchedule.builder()
				.id(null) // New record, no ID yet
				.orderId(orderId)
				.paymentTermBreakId(paymentTermBreakId)
				.referenceDateType(referenceDateType)
				.dueAmount(dueAmount)
				.dueDate(dueDate)
				.percent(percent)
				.orderPayScheduleStatus(orderPayScheduleStatus)
				.build();
	}
}
