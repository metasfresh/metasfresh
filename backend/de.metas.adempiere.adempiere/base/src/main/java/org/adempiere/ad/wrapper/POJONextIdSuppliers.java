package org.adempiere.ad.wrapper;

import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class POJONextIdSuppliers
{
	public static POJONextIdSupplier newSingleSharedSequence()
	{
		return new SingleSharedSequence();
	}

	public static POJONextIdSupplier newPerTableSequence()
	{
		return new PerTableSequence();
	}

	@ToString
	private static class SingleSharedSequence implements POJONextIdSupplier
	{
		// NOTE: because in some tests we are using hardcoded IDs which are like ~50000, we decided to start the IDs sequence from 100k.
		private static final int DEFAULT_FirstId = 100000;
		private int nextId = DEFAULT_FirstId;

		@Override
		public int nextId(String tableName)
		{
			nextId++;
			return nextId;
		}

		public void reset()
		{
			nextId = DEFAULT_FirstId;
		}
	}

	@ToString
	private static class PerTableSequence implements POJONextIdSupplier
	{
		private final int firstId = SingleSharedSequence.DEFAULT_FirstId * 10; // multiply by 10 to prevent overlapping in case we switch from Single Sequence
		private final HashMap<String, AtomicInteger> nextIds = new HashMap<>();

		@Override
		public int nextId(@NonNull final String tableName)
		{
			final String tableNameNorm = tableName.trim().toLowerCase();
			return nextIds.computeIfAbsent(tableNameNorm, k -> new AtomicInteger(firstId))
					.getAndIncrement();
		}

		public void reset()
		{
			nextIds.clear();
		}
	}

}
