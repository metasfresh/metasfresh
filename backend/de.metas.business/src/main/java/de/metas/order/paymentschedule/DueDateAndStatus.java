package de.metas.order.paymentschedule;

import de.metas.payment.paymentterm.PaymentTermConstants;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.time.Instant;

@Value(staticConstructor = "of")
public class DueDateAndStatus
{
	private static final DueDateAndStatus PENDING = DueDateAndStatus.of(PaymentTermConstants.INFINITE_FUTURE_DATE, OrderPayScheduleStatus.Pending);

	@NonNull Instant dueDate;
	@NonNull OrderPayScheduleStatus status;

	public static DueDateAndStatus pending()
	{
		return PENDING;
	}

	public static DueDateAndStatus awaitingPayment(@NonNull final Instant dueDate)
	{
		if (!dueDate.isBefore(PaymentTermConstants.INFINITE_FUTURE_DATE))
		{
			throw new AdempiereException("DueDate is mandatory when status is " + OrderPayScheduleStatus.Awaiting_Pay);
		}

		return DueDateAndStatus.of(dueDate, OrderPayScheduleStatus.Awaiting_Pay);
	}
}
