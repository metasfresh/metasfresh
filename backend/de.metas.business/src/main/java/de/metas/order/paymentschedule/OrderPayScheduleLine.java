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

import de.metas.i18n.BooleanWithReason;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermConstants;
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
public class OrderPayScheduleLine
{
	final @NonNull OrderPayScheduleId id;
	final @NonNull OrderId orderId;
	final @NonNull SeqNo seqNo;

	final @NonNull PaymentTermBreakId paymentTermBreakId;
	final @NonNull ReferenceDateType referenceDateType;
	final @NonNull Percent percent;
	final int offsetDays;

	@Setter @NonNull OrderPayScheduleStatus orderPayScheduleStatus;
	@Setter @NonNull Instant dueDate;
	final @NonNull Money dueAmount;

	@Builder
	private OrderPayScheduleLine(@NonNull final OrderPayScheduleId id,
								 @NonNull final OrderId orderId,
								 @NonNull final SeqNo seqNo,
								 @NonNull final PaymentTermBreakId paymentTermBreakId,
								 @NonNull final ReferenceDateType referenceDateType,
								 @NonNull final Percent percent,
								 final int offsetDays,
								 @NonNull final OrderPayScheduleStatus orderPayScheduleStatus,
								 @NonNull final Instant dueDate,
								 @NonNull final Money dueAmount
	)
	{
		this.id = id;
		this.orderId = orderId;
		this.seqNo = seqNo;

		this.paymentTermBreakId = paymentTermBreakId;
		this.referenceDateType = referenceDateType;
		this.percent = percent;
		this.offsetDays = offsetDays;

		this.orderPayScheduleStatus = orderPayScheduleStatus;
		this.dueDate = dueDate;
		this.dueAmount = dueAmount;
	}

	public void changeStatusTo(@NonNull final OrderPayScheduleStatus targetStatus)
	{
		if (OrderPayScheduleStatus.equals(this.orderPayScheduleStatus, targetStatus))
		{
			return;
		}

		OrderPayScheduleStatus newStatus;
		switch (targetStatus)
		{
			case Pending:
			{
				changeStatusTo_Pending();
				newStatus = OrderPayScheduleStatus.Pending;
				break;
			}
			case Awaiting_Pay:
			{
				changeStatusTo_AwaitingPay();
				newStatus = OrderPayScheduleStatus.Awaiting_Pay;
				break;
			}
			case Paid:
			{
				changeStatusTo_Paid();
				newStatus = OrderPayScheduleStatus.Paid;
				break;
			}

			default:
			{
				throw new AdempiereException("Unknown next status " + targetStatus);
			}
		}

		this.orderPayScheduleStatus = newStatus;
	}

	public BooleanWithReason checkCanTransitionToPending()
	{
		return orderPayScheduleStatus.checkCanTransitionTo(OrderPayScheduleStatus.Pending);
	}

	private void changeStatusTo_Pending()
	{
		checkCanTransitionToPending().assertTrue();
	}

	public BooleanWithReason checkCanTransitionToAwaitingPay()
	{
		final BooleanWithReason canTransition = orderPayScheduleStatus.checkCanTransitionTo(OrderPayScheduleStatus.Awaiting_Pay);
		if (canTransition.isFalse())
		{
			return canTransition;
		}

		if (!dueDate.isBefore(PaymentTermConstants.INFINITE_FUTURE_DATE))
		{
			return BooleanWithReason.falseBecause("The due date is NOT set");
		}

		return BooleanWithReason.TRUE;
	}

	private void changeStatusTo_AwaitingPay()
	{
		checkCanTransitionToAwaitingPay().assertTrue();
	}

	private void changeStatusTo_Paid()
	{
		checkCanTransitionToPaid().assertTrue();
	}

	public BooleanWithReason checkCanTransitionToPaid()
	{
		final BooleanWithReason canPay = orderPayScheduleStatus.checkCanTransitionTo(OrderPayScheduleStatus.Paid);
		if (canPay.isFalse())
		{
			return canPay;
		}

		return BooleanWithReason.TRUE;
	}
}
