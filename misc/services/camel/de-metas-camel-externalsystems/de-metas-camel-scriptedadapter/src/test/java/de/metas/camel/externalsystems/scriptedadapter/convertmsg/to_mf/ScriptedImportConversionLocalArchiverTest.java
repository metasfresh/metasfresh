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

import org.apache.camel.RuntimeCamelException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScriptedImportConversionLocalArchiverTest
{
	@TempDir
	Path archiveDir;

	@Test
	void archive_writesBinaryContentByteIdentical() throws Exception
	{
		// a PDF-shaped payload: not valid UTF-8, must never be text-decoded/re-encoded
		final byte[] binaryContent = new byte[]{0x00, (byte) 0xFF, 0x50, 0x44, 0x46, 0x00, (byte) 0xFF};

		ScriptedImportConversionLocalArchiver.archive(archiveDir.toAbsolutePath().toString(), "payload.pdf", binaryContent);

		final byte[] writtenBytes = Files.readAllBytes(archiveDir.resolve("payload.pdf"));
		assertThat(writtenBytes).isEqualTo(binaryContent);
	}

	@Test
	void archive_textPayload_isByteIdenticalToUtf8Encoding()
	{
		// regression guard for the two existing SFTP/REST writers: they still hand the archiver
		// UTF-8-encoded text bytes, so the output must match exactly what the old
		// Files.writeString(path, text, UTF_8) implementation produced — including multi-byte
		// characters, where a charset slip (e.g. platform default instead of UTF-8) would diverge.
		final String text = "Bestellung Nr. 4711 – Kunde: Müller, Café, 日本語, price 12,50 €";
		final byte[] expectedUtf8Bytes = text.getBytes(StandardCharsets.UTF_8);

		ScriptedImportConversionLocalArchiver.archive(archiveDir.toAbsolutePath().toString(), "payload.json", expectedUtf8Bytes);

		final Path archivedFile = archiveDir.resolve("payload.json");
		assertThat(readAllBytesUnchecked(archivedFile)).isEqualTo(expectedUtf8Bytes);
		assertThat(readStringUnchecked(archivedFile)).isEqualTo(text);
	}

	@Test
	void archive_createsMissingParentDirectories() throws Exception
	{
		final Path nestedDir = archiveDir.resolve("processed").resolve("2026");

		ScriptedImportConversionLocalArchiver.archive(nestedDir.toAbsolutePath().toString(), "file.bin", new byte[]{0x01, 0x02});

		assertThat(Files.readAllBytes(nestedDir.resolve("file.bin"))).isEqualTo(new byte[]{0x01, 0x02});
	}

	/**
	 * A scanner reusing an incoming file name (e.g. every scan lands as {@code scan001.pdf}) must never
	 * cause the second archive call to destroy the first: with {@code ImportFileNamePattern} left blank
	 * (the default -- an optional field), the raw incoming name is all {@code archiveFileName()} ever
	 * produces, so this is the only thing standing between two scans and a silently overwritten original.
	 * Both payloads must survive, under distinct names, with no byte lost.
	 */
	@Test
	void archive_sameFileNameTwice_bothPayloadsSurviveDistinctly() throws Exception
	{
		final byte[] firstScan = {0x25, 0x50, 0x44, 0x46, 0x00, 0x01};
		final byte[] secondScan = {0x25, 0x50, 0x44, 0x46, 0x02, 0x03};

		ScriptedImportConversionLocalArchiver.archive(archiveDir.toAbsolutePath().toString(), "scan001.pdf", firstScan);
		ScriptedImportConversionLocalArchiver.archive(archiveDir.toAbsolutePath().toString(), "scan001.pdf", secondScan);

		final List<byte[]> archivedContents;
		try (Stream<Path> files = Files.list(archiveDir))
		{
			archivedContents = files
					.map(ScriptedImportConversionLocalArchiverTest::readAllBytesUnchecked)
					.collect(Collectors.toList());
		}

		assertThat(archivedContents).hasSize(2);
		assertThat(archivedContents).contains(firstScan, secondScan);
	}

	/**
	 * {@code fileName} may derive from an operator-configured {@code ImportFileNamePattern}; a pattern
	 * resolving to a name containing {@code ..} must not be allowed to write outside {@code directory}.
	 */
	@Test
	void archive_fileNameEscapingDirectory_isRejected()
	{
		assertThatThrownBy(() ->
				ScriptedImportConversionLocalArchiver.archive(archiveDir.toAbsolutePath().toString(), "../escaped.pdf", new byte[]{0x01}))
				.isInstanceOf(RuntimeCamelException.class);

		assertThat(archiveDir.getParent().resolve("escaped.pdf")).doesNotExist();
	}

	private static byte[] readAllBytesUnchecked(final Path path)
	{
		try
		{
			return Files.readAllBytes(path);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static String readStringUnchecked(final Path path)
	{
		try
		{
			return Files.readString(path, StandardCharsets.UTF_8);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
