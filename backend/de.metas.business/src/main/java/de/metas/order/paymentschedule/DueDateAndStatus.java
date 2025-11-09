package de.metas.order.paymentschedule;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

@Value(staticConstructor = "of")
public class DueDateAndStatus
{
	private static final Instant INFINITE_FUTURE_DATE = LocalDateTime.of(9999, Month.JANUARY, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);
	private static final DueDateAndStatus PENDING = DueDateAndStatus.of(INFINITE_FUTURE_DATE, OrderPayScheduleStatus.Pending);

	@NonNull Instant dueDate;
	@NonNull OrderPayScheduleStatus status;

	public static DueDateAndStatus pending()
	{
		return PENDING;
	}

	public static DueDateAndStatus paid(@NonNull final Instant dueDate)
	{
		return DueDateAndStatus.of(dueDate, OrderPayScheduleStatus.Paid);
	}

	public static DueDateAndStatus awaitingPayment(@NonNull final Instant dueDate)
	{
		if (!dueDate.isBefore(INFINITE_FUTURE_DATE))
		{
			throw new AdempiereException("DueDate is mandatory when status is " + OrderPayScheduleStatus.Awaiting_Pay);
		}

		return DueDateAndStatus.of(dueDate, OrderPayScheduleStatus.Awaiting_Pay);
	}
}
