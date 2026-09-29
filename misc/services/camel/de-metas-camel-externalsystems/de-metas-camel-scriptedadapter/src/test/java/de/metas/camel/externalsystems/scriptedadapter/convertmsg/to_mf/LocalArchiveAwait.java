/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import lombok.NonNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Filesystem waits for the local-file route tests.
 * <p>
 * {@code NotifyBuilder.whenDone(n)} is not a safe trigger for reading the archive directories: it is
 * satisfied by the first {@code n} exchanges Camel routes — which, for a route that splits and dispatches,
 * includes the inner {@code direct:} sub-exchange — so it can fire while the outer file exchange has not
 * yet reached its terminal archiving step. A test that lists the directory immediately afterwards is then
 * racing the archiver's write.
 * <p>
 * Observed on CI as {@code test (java)} failing at "Expected size: 1 but was: 0", reproduced locally under
 * CPU contention (2 of 4 runs), and measured: the file landed ~68ms after the assertion had already run.
 * <p>
 * These waits change only WHEN the directory is read, never WHAT is required of it.
 */
final class LocalArchiveAwait
{
	private static final long TIMEOUT_MS = 10_000;
	private static final long POLL_INTERVAL_MS = 50;

	private LocalArchiveAwait()
	{
	}

	/** Waits for {@code dir} to hold exactly one file and returns it. */
	@NonNull
	static Path awaitSingleFile(@NonNull final Path dir) throws InterruptedException
	{
		final Path found = await(dir, entries -> entries.size() == 1 ? entries.get(0) : null);
		if (found == null)
		{
			throw new AssertionError("Expected exactly one file in " + dir + " within " + TIMEOUT_MS + "ms, but found: " + list(dir));
		}
		return found;
	}

	/**
	 * Waits for {@code dir} to become empty — the polled file is deleted by Camel's own file-consumer
	 * commit, which is ordered against the exchange's completion the same way the archiver's write is.
	 */
	static void awaitEmpty(@NonNull final Path dir) throws InterruptedException
	{
		if (await(dir, entries -> entries.isEmpty() ? dir : null) == null)
		{
			throw new AssertionError("Expected " + dir + " to be empty within " + TIMEOUT_MS + "ms, but found: " + list(dir));
		}
	}

	@SuppressWarnings("BusyWait")
	private static Path await(@NonNull final Path dir, @NonNull final java.util.function.Function<List<Path>, Path> settled) throws InterruptedException
	{
		final long deadline = System.currentTimeMillis() + TIMEOUT_MS;
		do
		{
			final Path result = settled.apply(list(dir));
			if (result != null)
			{
				return result;
			}
			Thread.sleep(POLL_INTERVAL_MS);
		}
		while (System.currentTimeMillis() < deadline);

		return null;
	}

	@NonNull
	private static List<Path> list(@NonNull final Path dir)
	{
		try (final Stream<Path> entries = Files.list(dir))
		{
			return entries.toList();
		}
		catch (final java.io.IOException e)
		{
			throw new AssertionError("Failed to list " + dir, e);
		}
	}
}
