package org.adempiere.ad.persistence;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pure (no-DB) guard for {@link TableModelLoader}'s large-id-load chunking that fixes the PostgreSQL/JDBC
 * 2-byte bind-parameter overflow. Covers what does NOT need a DB: the production fast-path-vs-chunked
 * decision ({@link TableModelLoader#isSingleQueryLoad(int)}), the chunk-size cap invariant, and the
 * partition semantics used by the chunked path. The actual DB-executed load (the {@code for} loop calling
 * {@code loadPOsFromDB} per chunk) needs a real DB and is exercised by the {@code @Disabled
 * TableModelLoader_DBTest} (this base module has no automated real-DB harness).
 */
class TableModelLoaderChunkingTest
{
	/** The whole point of the fix: the chunk size must stay under the JDBC 2-byte bind-parameter cap. */
	@Test
	void chunkSize_staysUnderJdbc2ByteParamLimit()
	{
		assertTrue(TableModelLoader.MAX_IDS_PER_QUERY > 0,
				"MAX_IDS_PER_QUERY must be positive");
		assertTrue(TableModelLoader.MAX_IDS_PER_QUERY <= 32767,
				"MAX_IDS_PER_QUERY (" + TableModelLoader.MAX_IDS_PER_QUERY
						+ ") must stay <= 32767, else the 2-byte bind-param overflow this fix guards against returns");
	}

	/** The production dispatch decision: <= MAX ids -> single query (fast path); > MAX -> chunked. */
	@Test
	void isSingleQueryLoad_boundary()
	{
		final int max = TableModelLoader.MAX_IDS_PER_QUERY;
		assertTrue(TableModelLoader.isSingleQueryLoad(0), "empty -> single query");
		assertTrue(TableModelLoader.isSingleQueryLoad(1), "1 id -> single query");
		assertTrue(TableModelLoader.isSingleQueryLoad(max), "exactly MAX -> single query (fast path)");
		assertFalse(TableModelLoader.isSingleQueryLoad(max + 1), "MAX+1 -> chunked");
		assertFalse(TableModelLoader.isSingleQueryLoad(2 * max), "2xMAX -> chunked");
	}

	/** The partitioning used by the chunked path (mirrors {@code getPOs}'s {@code Iterables.partition} call). */
	@Nested
	class Partition
	{
		/** A large set (well past the old failing threshold) partitions with every id preserved and no chunk over the cap. */
		@Test
		void preservesAllIds_andNeverExceedsChunkSize()
		{
			final int max = TableModelLoader.MAX_IDS_PER_QUERY;
			final Set<Integer> ids = new HashSet<>();
			for (int i = 1; i <= 40000; i++)
			{
				ids.add(i);
			}

			final Set<Integer> seen = new HashSet<>();
			int chunkCount = 0;
			for (final List<Integer> chunk : Iterables.partition(ids, max))
			{
				assertTrue(chunk.size() <= max, "no chunk may exceed MAX_IDS_PER_QUERY");
				seen.addAll(chunk);
				chunkCount++;
			}

			assertEquals(ids, seen, "every id must appear exactly once across chunks (no loss, no duplication)");
			assertEquals((40000 + max - 1) / max, chunkCount, "chunk count must be ceil(n / max)");
		}

		/** Boundary: exactly MAX ids -> one chunk; MAX+1 -> two chunks (mirrors the fast-path vs chunked split). */
		@Test
		void boundaryAtMax()
		{
			final int max = TableModelLoader.MAX_IDS_PER_QUERY;
			assertEquals(1, countChunks(max), "exactly MAX ids -> one chunk");
			assertEquals(2, countChunks(max + 1), "MAX+1 ids -> two chunks");
		}

		private int countChunks(final int n)
		{
			final List<Integer> ids = new ArrayList<>(n);
			for (int i = 0; i < n; i++)
			{
				ids.add(i);
			}
			return Iterables.size(Iterables.partition(ids, TableModelLoader.MAX_IDS_PER_QUERY));
		}
	}
}
