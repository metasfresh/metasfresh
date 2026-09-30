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

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;

import javax.annotation.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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

	/**
	 * Waits for {@code dir} to hold exactly one file whose bytes equal {@code expectedContent}, and
	 * returns it.
	 * <p>
	 * The settle condition is the CONTENT, not the file's existence and not its size. The archiver's
	 * {@code Files.write(.., CREATE_NEW)} creates the entry and writes the bytes as separate observable
	 * steps, and {@code Files.write} itself writes a large payload in 8 KiB chunks — so a listing can see
	 * the file at zero bytes, and a size check can see it part-written. Comparing against what the caller
	 * already expects settles exactly when the archive is the archive, at any payload size.
	 * <p>
	 * It also stays correct for a legitimately EMPTY archive: the poller accepts a 0-byte input file
	 * ({@code readLockMinLength=0}, so it cannot wedge on one) and only a {@code null} body is rejected
	 * downstream, so {@code byte[0]} can reach the archiver. A caller expecting no bytes passes
	 * {@code new byte[0]} and settles on the first poll instead of waiting out the timeout.
	 */
	@NonNull
	static Path awaitSingleFileWithContent(@NonNull final Path dir, @NonNull final byte[] expectedContent) throws InterruptedException
	{
		return awaitSingleFileWithContent(dir, expectedContent, TIMEOUT_MS);
	}

	/** Timeout-parameterised for the helper's own tests; production callers use the {@link #TIMEOUT_MS} default. */
	@NonNull
	static Path awaitSingleFileWithContent(
			@NonNull final Path dir,
			@NonNull final byte[] expectedContent,
			final long timeoutMs) throws InterruptedException
	{
		// A read that keeps failing is NOT the same as an archive still being written, but both look
		// identical to the poll loop. Keep the last failure so a timeout can say which one it was.
		final AtomicReference<IOException> lastReadFailure = new AtomicReference<>();
		try
		{
			// get(0) is safe by construction: await returns only a listing its predicate accepted, and this
			// predicate requires exactly one entry. Loosening it means revisiting this line.
			return await(dir,
					entries -> entries.size() == 1 && Arrays.equals(contentOf(entries.get(0), lastReadFailure), expectedContent),
					"exactly one file holding the expected " + expectedContent.length + " byte(s)",
					timeoutMs).get(0);
		}
		catch (final AssertionError timedOut)
		{
			final IOException readFailure = lastReadFailure.get();
			if (readFailure != null)
			{
				// without this the report reads as "the content never matched", sending the next reader
				// after a timing theory when the file was in fact never readable
				timedOut.addSuppressed(readFailure);
			}
			throw timedOut;
		}
	}

	/**
	 * Content that cannot be read counts as "no match yet", so the wait keeps polling instead of failing on
	 * the read still in flight during the archiver's create-then-write.
	 * <p>
	 * That is the EXPECTED cause, not the only one: a permissions fault or a handle held by something else
	 * throws the same {@link IOException} and never clears. The failure is therefore recorded in
	 * {@code sink} rather than discarded, so a timeout can report it instead of presenting a permanent
	 * fault as an ordinary content mismatch.
	 */
	@Nullable
	@VisibleForTesting
	static byte[] contentOf(@NonNull final Path file, @NonNull final AtomicReference<IOException> sink)
	{
		try
		{
			final byte[] content = Files.readAllBytes(file);
			// a read that has since succeeded must not keep a stale failure alive: attaching it to a later
			// timeout would blame a fault that stopped happening, the inverse of the masking this sink exists
			// to prevent
			sink.set(null);
			return content;
		}
		catch (final IOException e)
		{
			sink.set(e);
			return null;
		}
	}

	/**
	 * Waits for {@code dir} to become empty — the polled file is deleted by Camel's own file-consumer
	 * commit. See the class javadoc: that commit's ordering was not reproduced as racy here, and this
	 * wait is precautionary.
	 */
	static void awaitEmpty(@NonNull final Path dir) throws InterruptedException
	{
		await(dir, List::isEmpty, "no files", TIMEOUT_MS);
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
			@NonNull final String expectation,
			final long timeoutMs) throws InterruptedException
	{
		final long deadline = System.currentTimeMillis() + timeoutMs;
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

		throw new AssertionError("Expected " + expectation + " in " + dir + " within " + timeoutMs + "ms, but found: " + lastSeen);
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
