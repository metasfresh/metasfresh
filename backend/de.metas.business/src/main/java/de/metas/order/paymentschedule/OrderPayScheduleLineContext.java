package de.metas.order.paymentschedule;

import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
public class OrderPayScheduleLineContext
{
	/**
	 * "No real date yet" sentinel.
	 *
	 * <p>Chosen value: {@code 9999-12-01}.
	 *
	 * <p>The last day of year 9999 ({@code 9999-12-31}) would overflow when converted to a
	 * timestamp in UTC+1 or later (9999-12-31T23:00:00Z → year 10000). Using the first day of
	 * December gives 30 days of headroom before that boundary, making the sentinel safe even if
	 * it ever gets promoted from a {@code LocalDate} column to a timestamp column. It is still
	 * well above any real business date.
	 */
	private static final LocalDate INFINITE_FUTURE_DATE = LocalDate.of(9999, 12, 1);
	private static final OrderPayScheduleLineContext PENDING = OrderPayScheduleLineContext.builder()
			.status(OrderPayScheduleStatus.Pending)
			.dueDate(INFINITE_FUTURE_DATE)
			.setDueAmtActual(true).dueAmtActual(null)
			.build();

	@NonNull LocalDate dueDate;
	@NonNull OrderPayScheduleStatus status;
	@Nullable Money dueAmtActual;
	boolean setDueAmtActual;

	public static OrderPayScheduleLineContext pending()
	{
		return PENDING;
	}

	public static OrderPayScheduleLineContext awaitingPayment(@NonNull final LocalDate dueDate)
	{
		return awaitingPayment(dueDate, null);
	}

	public static OrderPayScheduleLineContext awaitingPayment(@NonNull final LocalDate dueDate, @Nullable final Money dueAmtActual)
	{
		if (!dueDate.isBefore(INFINITE_FUTURE_DATE))
		{
			throw new AdempiereException("DueDate is mandatory when status is " + OrderPayScheduleStatus.Awaiting_Pay);
		}

		return OrderPayScheduleLineContext.builder()
				.status(OrderPayScheduleStatus.Awaiting_Pay)
				.dueDate(dueDate)
				.setDueAmtActual(dueAmtActual != null)
				.dueAmtActual(dueAmtActual)
				.build();
	}

	public static OrderPayScheduleLineContext paid(@NonNull final LocalDate dueDate)
	{
		return paid(dueDate, null);
	}

	public static OrderPayScheduleLineContext paid(@NonNull final LocalDate dueDate, @Nullable Money dueAmtActual)
	{
		return OrderPayScheduleLineContext.builder()
				.status(OrderPayScheduleStatus.Paid)
				.dueDate(dueDate)
				.setDueAmtActual(dueAmtActual != null)
				.dueAmtActual(dueAmtActual)
				.build();
	}

}
