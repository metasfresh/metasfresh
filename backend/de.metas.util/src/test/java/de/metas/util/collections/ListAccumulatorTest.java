package de.metas.util.collections;

import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ListAccumulatorTest
{
	@Test
	void add_flush()
	{
		final ListAccumulator<String> accum = new ListAccumulator<>();
		accum.add("1");
		accum.add("2");

		{
			final ArrayList<String> flushedItems = new ArrayList<>();
			accum.flush(flushedItems::addAll);
			Assertions.assertThat(flushedItems).containsExactly("1", "2");
		}

		{
			final ArrayList<String> flushedItems = new ArrayList<>();
			accum.flush(flushedItems::addAll);
			Assertions.assertThat(flushedItems).isEmpty();
		}
	}

	@Test
	void addAll_flush()
	{
		final ListAccumulator<String> accum = new ListAccumulator<>();
		accum.addAll(ImmutableList.of("1", "2", "3"));

		{
			final ArrayList<String> flushedItems = new ArrayList<>();
			accum.flush(flushedItems::addAll);
			Assertions.assertThat(flushedItems).containsExactly("1", "2", "3");
		}

		{
			final ArrayList<String> flushedItems = new ArrayList<>();
			accum.flush(flushedItems::addAll);
			Assertions.assertThat(flushedItems).isEmpty();
		}
	}
}