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

import de.metas.invoice.InvoiceId;
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
	@NonNull final OrderPayScheduleId id;
	@NonNull final OrderId orderId;
	@NonNull final SeqNo seqNo;

	@NonNull final PaymentTermBreakId paymentTermBreakId;
	@NonNull final ReferenceDateType referenceDateType;
	@NonNull final Percent percent;
	final int offsetDays;

	@NonNull OrderPayScheduleStatus status;
	boolean isPaid;
	@Nullable LocalDate referenceDate;
	@NonNull LocalDate dueDate;
	@Nullable final Money baseAmount;
	@NonNull final Money dueAmount;
	@Nullable Money dueAmtActual;

	@Nullable InvoiceId invoiceId;

	public OrderAndPayScheduleId getOrderAndPayScheduleId() {return OrderAndPayScheduleId.of(orderId, id);}

	public boolean isLetterOfCreditDate() {return referenceDateType.isLetterOfCreditDate();}

	public void applyAndProcess(@NonNull final OrderPayScheduleLineContext context)
	{
		final OrderPayScheduleStatus nextStatus = context.getStatus();

		if (nextStatus.equals(this.status))
		{
			return;
		}

		if (!this.status.isAllowTransitionTo(nextStatus))
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
		if (context.isDueAmtActualSet())
		{
			this.dueAmtActual = context.getDueAmtActual();
		}
	}
}
