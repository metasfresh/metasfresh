package de.metas.order.paymentschedule;

import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
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
			.dueAmtActual(null) // set
			.build();

	@NonNull OrderPayScheduleStatus status;

	boolean referenceDateSet;
	@Nullable LocalDate referenceDate;

	@NonNull LocalDate dueDate;

	boolean dueAmtActualSet;
	@Nullable Money dueAmtActual;

	@Builder
	private OrderPayScheduleLineContext(
			@NonNull final OrderPayScheduleStatus status,
			final boolean referenceDateSet,
			@Nullable final LocalDate referenceDate,
			@NonNull final LocalDate dueDate,
			final boolean dueAmtActualSet,
			@Nullable final Money dueAmtActual)
	{
		if (status.equals(OrderPayScheduleStatus.Awaiting_Pay) && !dueDate.isBefore(INFINITE_FUTURE_DATE))
		{
			throw new AdempiereException("DueDate is mandatory when status is " + OrderPayScheduleStatus.Awaiting_Pay);
		}

		this.status = status;
		this.referenceDateSet = referenceDateSet;
		this.referenceDate = referenceDate;
		this.dueDate = dueDate;
		this.dueAmtActualSet = dueAmtActualSet;
		this.dueAmtActual = dueAmtActual;
	}

	public static class OrderPayScheduleLineContextBuilder
	{
		public OrderPayScheduleLineContextBuilder referenceDate(@Nullable final LocalDate referenceDate)
		{
			this.referenceDate = referenceDate;
			this.referenceDateSet = true;
			return this;
		}

		public OrderPayScheduleLineContextBuilder dueAmtActual(@Nullable final Money dueAmtActual)
		{
			this.dueAmtActual = dueAmtActual;
			this.dueAmtActualSet = true;
			return this;
		}
	}

	public static OrderPayScheduleLineContext pending()
	{
		return PENDING;
	}

	public static OrderPayScheduleLineContextBuilder awaitingPayment()
	{
		return OrderPayScheduleLineContext.builder().status(OrderPayScheduleStatus.Awaiting_Pay);
	}

	public static OrderPayScheduleLineContextBuilder paid()
	{
		return OrderPayScheduleLineContext.builder().status(OrderPayScheduleStatus.Paid);
	}

}
