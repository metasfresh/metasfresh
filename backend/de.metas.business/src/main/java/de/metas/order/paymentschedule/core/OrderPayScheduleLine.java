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

package de.metas.order.paymentschedule.core;

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@Getter
@Builder
public class OrderPayScheduleLine
{
	@Nullable private OrderPayScheduleId id;
	@NonNull private final OrderId orderId;

	@NonNull private final PaymentTermBreakId paymentTermBreakId;
	@NonNull private final ReferenceDateType referenceDateType;
	@NonNull private final Percent percent;
	private final int offsetDays;

	@NonNull private OrderPayScheduleStatus status;
	private boolean isPaid;
	@Nullable private LocalDate referenceDate;
	@NonNull private LocalDate dueDate;
	@Nullable private Money baseAmount;
	@NonNull private Money dueAmount;
	@Nullable private Money dueAmountActual;

	@Nullable private InvoiceId invoiceId;
	@Nullable private InOutId inoutId;

	public static OrderPayScheduleLine from(@NonNull final OrderSchedulingContext context, @NonNull final PaymentTermBreak termBreak)
	{
		final OrderPayScheduleLineContext lineContext = context.computeLineContext(termBreak);

		return builder()
				.orderId(context.getOrderId())
				.paymentTermBreak(termBreak)
				//
				.status(lineContext.getStatus())
				.referenceDate(lineContext.getReferenceDate())
				.dueDate(lineContext.getDueDate())
				.baseAmount(context.getGrandTotal()) // for LC/OD rows: BaseAmt = order GrandTotal
				.dueAmount(context.getGrandTotalByBreakId(termBreak.getId()))
				//
				.build();
	}

	@SuppressWarnings("unused")
	public static class OrderPayScheduleLineBuilder
	{
		public OrderPayScheduleLineBuilder paymentTermBreak(@NonNull final PaymentTermBreak termBreak)
		{
			paymentTermBreakId(termBreak.getId());
			referenceDateType(termBreak.getReferenceDateType());
			percent(termBreak.getPercent());
			offsetDays(termBreak.getOffsetDays());
			return this;
		}
	}

	@NonNull
	public OrderPayScheduleId getIdNotNull()
	{
		return Check.assumeNotNull(id, "line is saved: {}", this);
	}

	public boolean isSaved() {return id != null;}

	public void markSaved(@NonNull final OrderPayScheduleId id)
	{
		if (this.id == null)
		{
			this.id = id;
		}
		else if (!OrderPayScheduleId.equals(this.id, id))
		{
			throw new AdempiereException("Cannot mark saved a different order pay schedule line");
		}

	}

	public OrderAndPayScheduleId getOrderAndPayScheduleId() {return OrderAndPayScheduleId.of(orderId, getIdNotNull());}

	public void setBaseAndDueAmount(@Nullable final Money baseAmount, @NonNull final Money dueAmount)
	{
		this.baseAmount = baseAmount;
		this.dueAmount = dueAmount;
	}

	public boolean isLetterOfCreditDate() {return referenceDateType.isLetterOfCreditDate();}

	public void applyAndProcess(@NonNull final OrderPayScheduleLineContext context)
	{
		final OrderPayScheduleStatus nextStatus = context.getStatus();

		// Validate transition only when status actually changes; same-status updates apply field changes without re-validating.
		if (!nextStatus.equals(this.status) && !this.status.isAllowTransitionTo(nextStatus))
		{
			throw new AdempiereException("Cannot change status from " + this.status + " to " + nextStatus);
		}

		this.status = nextStatus;
		this.isPaid = nextStatus.isPaid();
		this.dueDate = context.getDueDate();

		if (context.isReferenceDateSet())
		{
			this.referenceDate = context.getReferenceDate();
		}
		else if (nextStatus == OrderPayScheduleStatus.Pending)
		{
			this.referenceDate = null;
		}
		else
		{
			this.referenceDate = this.dueDate.minusDays(offsetDays);
		}

		if (context.isDueAmountActualSet())
		{
			this.dueAmountActual = context.getDueAmountActual();
		}

		if (context.isInvoiceIdSet())
		{
			this.invoiceId = context.getInvoiceId();
		}

		if (context.isInoutIdSet())
		{
			this.inoutId = context.getInoutId();
		}
	}
}
