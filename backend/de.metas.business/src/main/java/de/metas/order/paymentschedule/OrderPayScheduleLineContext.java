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
	 * <p>Chosen value: {@code 9999-12-31}, which matches {@code Env.MAX_DATE} — the codebase-wide
	 * convention for "effectively infinite" dates stored in the DB.
	 *
	 * <p><b>Timezone note:</b> Using the last day of 9999 is safe here because this field is stored as
	 * {@code LocalDate} (date-only, no timezone) and compared against real {@code LocalDate} values
	 * that are always well below the year 9999.  If we ever need to convert this value to an
	 * {@code Instant}/{@code ZonedDateTime} (e.g. for a timestamp column), use {@code 9999-12-01}
	 * instead to avoid the theoretical overflow when adding a UTC offset, but for a pure date field
	 * {@code 9999-12-31} causes no overflow.
	 */
	private static final LocalDate INFINITE_FUTURE_DATE = LocalDate.of(9999, 12, 31);
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
