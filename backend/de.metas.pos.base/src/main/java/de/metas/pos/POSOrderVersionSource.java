package de.metas.pos;

import de.metas.common.util.time.SystemTime;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Hands out the {@link POSOrder#getVersion()} values. Clients compare them per order to drop snapshots which arrive out of order.
 * <p>
 * Versions are kept in memory only. They start from the current time in millis, so they still increase after a server restart,
 * as long as the previous run averaged less than one save per millisecond and the wall clock did not step back across the restart
 * by more than the restart took. This relies on POS orders being saved and sent to clients by one app server.
 */
@Component
public class POSOrderVersionSource
{
	private final AtomicLong lastVersion = new AtomicLong(0);

	/**
	 * @return the version for a committed save: greater than any version returned so far
	 */
	public long next()
	{
		return lastVersion.updateAndGet(last -> Math.max(last + 1, SystemTime.millis()));
	}

	/**
	 * @return the version for an order loaded from DB, to be taken BEFORE reading it: not lower than the version of any save
	 * stamped so far (so a delayed event of an earlier save is not considered newer), and lower than the version of any
	 * later save (so the event of a save which commits while reading is not considered older)
	 */
	public long current()
	{
		return lastVersion.updateAndGet(last -> Math.max(last, SystemTime.millis()));
	}
}
