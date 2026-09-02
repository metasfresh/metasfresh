package de.metas.util.async;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Backpressure guard: the {@link Debouncer} buffer must never grow without bound when the consumer can't keep up.
 * Once {@code bufferHardLimit} is reached, the OLDEST items are dropped (and counted) instead of accumulating to
 * millions of entries (which can exhaust the heap).
 *
 * <p>A long {@code delayInMillis} keeps the scheduled drain from firing during the test, so we observe the raw
 * buffering behaviour; {@code processAndClearBufferSync()} then drains synchronously to assert WHICH items survived.
 */
class DebouncerBackpressureTest
{
	private static Debouncer<Integer> debouncer(final int bufferMaxSize, final int bufferHardLimit, final List<Integer> consumed)
	{
		return Debouncer.<Integer>builder()
				.name("test-debouncer")
				.delayInMillis(60_000) // long enough that the async drain never fires during the test
				.bufferMaxSize(bufferMaxSize)
				.bufferHardLimit(bufferHardLimit)
				.distinct(false) // ArrayList -> insertion order preserved, so we can assert drop-oldest
				.consumer(consumed::addAll)
				.build();
	}

	@Test
	void hardLimit_dropsOldest_andCounts()
	{
		final List<Integer> consumed = new ArrayList<>();
		final Debouncer<Integer> d = debouncer(/*bufferMaxSize*/ 0, /*bufferHardLimit*/ 10, consumed);

		for (int i = 0; i < 100; i++)
		{
			d.add(i);
		}

		assertThat(d.getCurrentBufferSize()).isEqualTo(10);
		assertThat(d.getDroppedItemsCount()).isEqualTo(90);

		// drain synchronously and verify the SURVIVORS are the newest 10 (90..99) -> oldest were dropped
		d.processAndClearBufferSync();
		assertThat(consumed).containsExactly(90, 91, 92, 93, 94, 95, 96, 97, 98, 99);

		d.shutdown();
	}

	@Test
	void noLimits_remainsUnbounded_backwardCompatible()
	{
		final List<Integer> consumed = new ArrayList<>();
		// neither bufferMaxSize nor bufferHardLimit set -> no hard cap (preserves pre-existing behaviour)
		final Debouncer<Integer> d = debouncer(/*bufferMaxSize*/ 0, /*bufferHardLimit*/ 0, consumed);

		for (int i = 0; i < 1000; i++)
		{
			d.add(i);
		}

		assertThat(d.getCurrentBufferSize()).isEqualTo(1000);
		assertThat(d.getDroppedItemsCount()).isEqualTo(0);

		d.shutdown();
	}
}
