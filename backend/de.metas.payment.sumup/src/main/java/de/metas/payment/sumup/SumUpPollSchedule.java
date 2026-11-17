package de.metas.payment.sumup;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

/**
 * How often the SumUp poller asks SumUp for a PENDING transaction, by the transaction's age (since metasfresh created it)
 * and the time of its last lookup ({@code SUMUP_LastSync_Timestamp}, any outcome):
 * <ul>
 * <li>younger than {@link #FAST_PHASE} (the reader's checkout window): in every poller cycle, so the till sees an approval
 * within seconds;</li>
 * <li>younger than {@link #MEDIUM_PHASE}: when its last lookup is at least {@link #MEDIUM_INTERVAL} ago;</li>
 * <li>older: when its last lookup is at least {@link #SLOW_INTERVAL} ago.</li>
 * </ul>
 * A transaction never looked up is always due. An explicit lookup (the till's check, back office's update processes) is not
 * subject to this schedule.
 */
public final class SumUpPollSchedule
{
	public static final Duration FAST_PHASE = Duration.ofMinutes(5);
	public static final Duration MEDIUM_PHASE = Duration.ofHours(1);
	public static final Duration MEDIUM_INTERVAL = Duration.ofMinutes(1);
	public static final Duration SLOW_INTERVAL = Duration.ofMinutes(15);

	private SumUpPollSchedule() {}

	/**
	 * @param createdAt    when metasfresh created the transaction
	 * @param lastLookupAt when it was last looked up, null if never
	 * @param now          the poller cycle's time
	 */
	public static boolean isDue(@NonNull final Instant createdAt, @Nullable final Instant lastLookupAt, @NonNull final Instant now)
	{
		if (lastLookupAt == null)
		{
			return true;
		}

		final Duration age = Duration.between(createdAt, now);
		if (age.compareTo(FAST_PHASE) < 0)
		{
			return true;
		}

		final Duration interval = age.compareTo(MEDIUM_PHASE) < 0 ? MEDIUM_INTERVAL : SLOW_INTERVAL;
		return Duration.between(lastLookupAt, now).compareTo(interval) >= 0;
	}
}
