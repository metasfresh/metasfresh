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

import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.time.Instant;

@EqualsAndHashCode
@ToString
@Getter
@Builder
public class OrderPayScheduleLine
{
	final @NonNull OrderPayScheduleId id;
	final @NonNull OrderId orderId;
	final @NonNull SeqNo seqNo;

	final @NonNull PaymentTermBreakId paymentTermBreakId;
	final @NonNull ReferenceDateType referenceDateType;
	final @NonNull Percent percent;
	final int offsetDays;

	@Setter @NonNull OrderPayScheduleStatus status;
	@Setter @NonNull Instant dueDate;
	final @NonNull Money dueAmount;

	public void applyAndProcess(@NonNull final DueDateAndStatus dueDateAndStatus)
	{
		final OrderPayScheduleStatus nextStatus = dueDateAndStatus.getStatus();
		if (!this.status.isAllowTransitionTo(nextStatus))
		{
			throw new AdempiereException("Cannot change status from " + this.status + " to " + nextStatus);
		}

		this.status = nextStatus;
		this.dueDate = dueDateAndStatus.getDueDate();
	}
}
