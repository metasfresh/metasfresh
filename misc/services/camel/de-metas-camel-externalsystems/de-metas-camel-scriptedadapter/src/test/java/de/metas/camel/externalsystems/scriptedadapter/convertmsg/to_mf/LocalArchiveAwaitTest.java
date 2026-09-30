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
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The archiver creates the file and writes its bytes as two observable steps
 * ({@code Files.write(.., CREATE_NEW)}), so a directory listing sees the entry before the content is
 * there. A wait that settles on "an entry exists" therefore hands back an EMPTY file, and the caller —
 * every one of which immediately reads the bytes — asserts against nothing.
 * <p>
 * Seen on CI as {@code ScriptedImportConversionLocalFileDynamicRouteTest} failing with
 * "expected: [37, 80, 68, 70, 3, 4] but was: []" (that is {@code %PDF} plus two bytes).
 */
class LocalArchiveAwaitTest
{
	private static final byte[] CONTENT = { (byte)0x25, (byte)0x50, (byte)0x44, (byte)0x46, 0x03, 0x04 };

	/**
	 * The regression itself: while the only entry is still 0 bytes the wait must NOT hand it back.
	 * Deterministic — the writer only fills the file once the assertion thread has had a chance to observe
	 * the empty one, so a wait that settles on mere existence returns the empty file every run.
	 */
	@Test
	void doesNotSettleWhileTheOnlyFileIsStillEmpty() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		final Path file = Files.createFile(dir.resolve("scan.pdf"));
		final CountDownLatch observed = new CountDownLatch(1);

		final Thread writer = new Thread(() -> {
			try
			{
				// give the waiter several poll cycles against the empty file before any content lands
				observed.await(5, TimeUnit.SECONDS);
				Thread.sleep(300);
				Files.write(file, CONTENT);
			}
			catch (final IOException | InterruptedException e)
			{
				throw new IllegalStateException(e);
			}
		});
		writer.start();
		observed.countDown();

		final Path settled = LocalArchiveAwait.awaitSingleCompleteFile(dir);
		// read AT the moment the wait handed the file back -- joining the writer first would let the
		// content land and hide the very gap under test
		final byte[] contentWhenSettled = Files.readAllBytes(settled);

		writer.join();
		assertThat(contentWhenSettled).isEqualTo(CONTENT);
	}

	/** An already-complete file must still return on the first poll — the fix must not cost the happy path. */
	@Test
	void returnsImmediatelyWhenTheFileIsAlreadyComplete() throws Exception
	{
		final Path dir = Files.createTempDirectory("archive-await");
		Files.write(dir.resolve("scan.pdf"), CONTENT);

		final long startedAt = System.currentTimeMillis();
		final Path settled = LocalArchiveAwait.awaitSingleCompleteFile(dir);

		assertThat(Files.readAllBytes(settled)).isEqualTo(CONTENT);
		assertThat(System.currentTimeMillis() - startedAt).isLessThan(1_000);
	}
}
