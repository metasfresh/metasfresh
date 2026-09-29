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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Filesystem waits for the local-file route tests.
 * <p>
 * {@code NotifyBuilder.whenDone(n)} firing is NOT evidence that the step a test wants to observe has run.
 * More than one thing can produce that gap, so do not read the first as the general explanation:
 * <ul>
 *     <li>{@code whenDone(n)} counts the first {@code n} exchanges Camel routes, which for a route that
 *     splits and dispatches includes the inner {@code direct:} sub-exchange — so the threshold can be met
 *     before the outer file exchange reaches its terminal archiving step. This applies only where items
 *     are actually dispatched; a run that produces no items creates no such sub-exchange.</li>
 *     <li>The polled file's deletion is performed by Camel's own file-consumer commit — a
 *     {@code Synchronization} ordered against {@code UnitOfWork} completion, a different mechanism
 *     entirely from which exchange finishes first. Unlike the case above, this one was NOT reproduced
 *     here: the input-directory waits are a precaution taken because the ordering is the same class of
 *     assumption that the archiver's write had already disproved.</li>
 * </ul>
 * Camel's async routing engine can also hand continuation to another thread under load with neither of the
 * above in play. The net effect is the same in every case, which is why these waits are applied uniformly
 * rather than per-mechanism: a test that lists the directory immediately after {@code whenDone} is racing
 * a step that has not necessarily happened yet.
 * <p>
 * Observed on CI as {@code test (java)} failing at "Expected size: 1 but was: 0", reproduced locally under
 * CPU contention (2 of 4 runs), and measured: the file landed ~68ms after the assertion had already run.
 * <p>
 * These waits change only WHEN a directory is read, never WHAT is required of it. The deliberate cost is
 * in the FAILING case: a genuine regression (a file never archived, or never consumed) still fails
 * deterministically, but reports after the timeout instead of immediately. The passing case is unaffected —
 * an already-settled directory returns on the first poll.
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
		// get(0) is safe by construction: await returns only a listing its predicate accepted, and this
		// predicate requires exactly one entry. Loosening it means revisiting this line.
		return await(dir, entries -> entries.size() == 1, "exactly one file").get(0);
	}

	/**
	 * Waits for {@code dir} to become empty — the polled file is deleted by Camel's own file-consumer
	 * commit, which is ordered against the exchange's completion the same way the archiver's write is.
	 */
	static void awaitEmpty(@NonNull final Path dir) throws InterruptedException
	{
		await(dir, List::isEmpty, "no files");
	}

	/**
	 * Polls {@code dir} until {@code settled} accepts its contents, and returns those contents. On timeout
	 * it reports the LAST listing the loop actually saw — re-listing the directory for the message could
	 * show a state that never failed the check, which would mislead whoever debugs the next failure.
	 */
	@NonNull
	@SuppressWarnings("BusyWait")
	private static List<Path> await(
			@NonNull final Path dir,
			@NonNull final Predicate<List<Path>> settled,
			@NonNull final String expectation) throws InterruptedException
	{
		final long deadline = System.currentTimeMillis() + TIMEOUT_MS;
		List<Path> lastSeen;
		do
		{
			lastSeen = list(dir);
			if (settled.test(lastSeen))
			{
				return lastSeen;
			}
			Thread.sleep(POLL_INTERVAL_MS);
		}
		while (System.currentTimeMillis() < deadline);

		throw new AssertionError("Expected " + expectation + " in " + dir + " within " + TIMEOUT_MS + "ms, but found: " + lastSeen);
	}

	@NonNull
	private static List<Path> list(@NonNull final Path dir)
	{
		try (final Stream<Path> entries = Files.list(dir))
		{
			return entries.toList();
		}
		catch (final IOException e)
		{
			throw new AssertionError("Failed to list " + dir, e);
		}
	}
}
