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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The archiver's {@code Files.write(.., CREATE_NEW)} creates the entry and writes the bytes as separate
 * observable steps, and writes a large payload in 8 KiB chunks — so a directory listing can see the file
 * at zero bytes, and a size check can see it part-written. Every caller reads the bytes straight after the
 * wait returns, so anything short of a content match hands them the wrong thing.
 * <p>
 * Seen on CI as {@code ScriptedImportConversionLocalFileDynamicRouteTest} failing with
 * "expected: [37, 80, 68, 70, 3, 4] but was: []" — that is {@code %PDF} plus two bytes, against nothing.
 * <p>
 * These tests are state-based, not timing-based: each sets the directory to a fixed state and asserts what
 * the wait does with it. Nothing here depends on winning a race against a writer thread.
 */
class LocalArchiveAwaitTest
{
	private static final byte[] CONTENT = { (byte)0x25, (byte)0x50, (byte)0x44, (byte)0x46, 0x03, 0x04 };
	/** Long enough to prove the wait did not settle, short enough not to pad the suite. */
	private static final long SHORT_TIMEOUT_MS = 300;

	/** The regression: a created-but-not-yet-written archive must never be handed back. */
	@Test
	void doesNotSettleOnAnEmptyFileWhenContentIsExpected() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		Files.createFile(dir.resolve("scan.pdf"));

		assertThatThrownBy(() -> LocalArchiveAwait.awaitSingleFileWithContent(dir, CONTENT, SHORT_TIMEOUT_MS))
				.isInstanceOf(AssertionError.class)
				.hasMessageContaining("exactly one file holding the expected 6 byte(s)");
	}

	/**
	 * A part-written archive must not settle either — the case a size-based check would wave through.
	 * Reproduced directly by writing a prefix, which is what {@code Files.write} leaves visible between
	 * its 8 KiB chunks for any realistic PDF.
	 */
	@Test
	void doesNotSettleOnAPartiallyWrittenFile() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		Files.write(dir.resolve("scan.pdf"), new byte[] { CONTENT[0], CONTENT[1] });

		assertThatThrownBy(() -> LocalArchiveAwait.awaitSingleFileWithContent(dir, CONTENT, SHORT_TIMEOUT_MS))
				.isInstanceOf(AssertionError.class);
	}

	/** The complete archive is returned, and without paying a poll cycle for it. */
	@Test
	void returnsTheFileOnceItsContentMatches() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		Files.write(dir.resolve("scan.pdf"), CONTENT);

		final Path settled = LocalArchiveAwait.awaitSingleFileWithContent(dir, CONTENT);

		assertThat(Files.readAllBytes(settled)).isEqualTo(CONTENT);
	}

	/**
	 * A read that never succeeds must not be reported as an ordinary content mismatch: the timeout has to
	 * carry the failure, or whoever debugs it chases a timing theory while the real fault was that the file
	 * could not be read at all. Provoked with a directory entry that cannot be read as a file.
	 */
	@Test
	void surfacesAPersistentReadFailureOnTimeout() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		Files.createDirectory(dir.resolve("scan.pdf")); // listed like a file, never readable as one

		assertThatThrownBy(() -> LocalArchiveAwait.awaitSingleFileWithContent(dir, CONTENT, SHORT_TIMEOUT_MS))
				.isInstanceOf(AssertionError.class)
				.satisfies(thrown -> assertThat(thrown.getSuppressed())
						.as("the read failure must travel with the timeout, not be swallowed")
						.hasAtLeastOneElementOfType(IOException.class));
	}

	/**
	 * A failure that has since stopped happening must not be reported at all. Asserted on the recorder
	 * directly: provoking it through the wait would need the directory mutated mid-poll, which is the
	 * thread-racing these tests deliberately do not do.
	 * <p>
	 * Without this, a timeout caused by a genuine content mismatch would carry a transient failure from an
	 * earlier poll and blame a fault that was no longer occurring — the inverse of the masking the recorder
	 * exists to prevent.
	 */
	@Test
	void forgetsAReadFailureOnceAReadSucceeds() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		final Path unreadable = Files.createDirectory(dir.resolve("unreadable"));
		final Path readable = Files.write(dir.resolve("readable"), CONTENT);
		final AtomicReference<IOException> sink = new AtomicReference<>();

		assertThat(LocalArchiveAwait.contentOf(unreadable, sink)).isNull();
		assertThat(sink.get()).as("the failing read must be recorded").isNotNull();

		assertThat(LocalArchiveAwait.contentOf(readable, sink)).isEqualTo(CONTENT);
		assertThat(sink.get()).as("a later success must clear it").isNull();
	}

	/**
	 * An empty archive is reachable — the poller takes a 0-byte input file by design
	 * ({@code readLockMinLength=0}) and only a null body is rejected downstream — so a caller expecting no
	 * bytes must settle immediately rather than wait out the timeout on a state that is already final.
	 */
	@Test
	void settlesOnAnEmptyFileWhenNoContentIsExpected() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		final Path empty = Files.createFile(dir.resolve("scan.pdf"));

		final Path settled = LocalArchiveAwait.awaitSingleFileWithContent(dir, new byte[0], SHORT_TIMEOUT_MS);

		assertThat(settled).isEqualTo(empty);
	}
}
