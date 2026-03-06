package de.metas.order.paymentschedule;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.time.LocalDate;
import java.time.Month;

@Value(staticConstructor = "of")
public class DueDateAndStatus
{
	private static final LocalDate INFINITE_FUTURE_DATE = LocalDate.of(9999, Month.JANUARY, 1);
	private static final DueDateAndStatus PENDING = DueDateAndStatus.of(INFINITE_FUTURE_DATE, OrderPayScheduleStatus.Pending);

	@NonNull LocalDate dueDate;
	@NonNull OrderPayScheduleStatus status;

	public static DueDateAndStatus pending()
	{
		return PENDING;
	}

	public static DueDateAndStatus paid(@NonNull final LocalDate dueDate)
	{
		return DueDateAndStatus.of(dueDate, OrderPayScheduleStatus.Paid);
	}

	public static DueDateAndStatus awaitingPayment(@NonNull final LocalDate dueDate)
	{
		if (!dueDate.isBefore(INFINITE_FUTURE_DATE))
		{
			throw new AdempiereException("DueDate is mandatory when status is " + OrderPayScheduleStatus.Awaiting_Pay);
		}

		return DueDateAndStatus.of(dueDate, OrderPayScheduleStatus.Awaiting_Pay);
	}
}
