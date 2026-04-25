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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
	@Setter @NonNull LocalDate dueDate;
	final @NonNull Money dueAmount;

	/**
	 * Actual amount due — written exclusively by {@code OrderPayScheduleLCService} for the LC step. NULL on all non-LC rows.
	 *
	 * <p><b>TODO (follow-up)</b>: Convert to {@link de.metas.money.Money} so the currency is always co-located
	 * with the amount. Requires adapting {@link de.metas.order.paymentschedule.repository.OrderPayScheduleLoaderAndSaver}
	 * (load: read C_Currency_ID + BigDecimal → Money; save: Money.toBigDecimal()), the LC service setters, and the
	 * step-def assertions.
	 * See PR review comment https://github.com/metasfresh/metasfresh/pull/23682#discussion_r3141822083.
	 */
	@Setter @Nullable BigDecimal dueAmtActual;

	public OrderAndPayScheduleId getOrderAndPayScheduleId() {return OrderAndPayScheduleId.of(orderId, id);}

	public void applyAndProcess(@NonNull final DueDateAndStatus dueDateAndStatus)
	{
		final OrderPayScheduleStatus nextStatus = dueDateAndStatus.getStatus();

		if (nextStatus.equals(this.status))
		{
			return;
		}

		if (!this.status.isAllowTransitionTo(nextStatus))
		{
			throw new AdempiereException("Cannot change status from " + this.status + " to " + nextStatus);
		}

		this.status = nextStatus;
		this.dueDate = dueDateAndStatus.getDueDate();
	}
}
