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
import lombok.experimental.UtilityClass;
import org.apache.camel.RuntimeCamelException;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Archives an imported scripted-import payload (the raw SFTP file content, the polled LOCAL_FILE
 * content, or the raw REST POST body) to a LOCAL, transport-agnostic processed/error folder — see
 * {@code ExternalSystem_Endpoint.ProcessedDirectory}/{@code ErrorDirectory}.
 * <p>
 * This class never touches any remote resource. For SFTP, the remote file's own fate (consumed by
 * delete) is handled separately via the SFTP endpoint URI options
 * ({@code ScriptedImportConversionSftpRouteBuilder}); this class only ever writes to a local directory.
 */
@UtilityClass
class ScriptedImportConversionLocalArchiver
{
	/**
	 * Writes the raw bytes of {@code content} to {@code directory}/{@code fileName}, creating
	 * {@code directory} (and any missing parents) if needed. This method itself never text-decodes or
	 * re-encodes {@code content}, so whatever bytes it is handed round-trip byte-identical (whether that
	 * makes the archived file byte-identical to the ORIGINAL source depends on how the caller captured
	 * {@code content} — see {@code AbstractScriptedImportConversionArchivingRouteBuilder} for the
	 * per-transport caveat).
	 * <p>
	 * Never overwrites an existing file: {@code fileName} is operator-controlled (an
	 * {@code ImportFileNamePattern} left blank — the default — resolves to the raw incoming file name), so
	 * a scanner that reuses a name (e.g. every scan landing as {@code scan001.pdf}) must not destroy the
	 * previously archived original. On a name collision, a numeric suffix is inserted before the extension
	 * and the write is retried; the check-then-write race between two near-simultaneous arrivals of the
	 * same name is closed by {@link StandardOpenOption#CREATE_NEW}, which fails atomically instead of
	 * silently truncating an existing file, so the retry loop always converges on a name nobody has taken
	 * yet.
	 *
	 * @throws RuntimeCamelException if {@code fileName} would resolve outside {@code directory} (e.g. via
	 * {@code ..} or a path separator), or if the write ultimately fails for a reason other than a name
	 * collision.
	 */
	void archive(@NonNull final String directory, @NonNull final String fileName, @NonNull final byte[] content)
	{
		try
		{
			final Path dirPath = Path.of(directory).toAbsolutePath().normalize();
			Files.createDirectories(dirPath);
			writeWithoutOverwriting(dirPath, fileName, content);
		}
		catch (final IOException e)
		{
			throw new RuntimeCamelException("Failed to locally archive payload to " + directory + "/" + fileName, e);
		}
	}

	/**
	 * Resolves {@code fileName} under {@code dirPath} and writes {@code content} without ever truncating an
	 * existing file: {@link StandardOpenOption#CREATE_NEW} makes file creation itself the collision check
	 * (no separate "does it exist" step that a second, concurrent archiver could race past), and each
	 * collision is retried under a distinct, incrementing suffix until one succeeds.
	 */
	private static void writeWithoutOverwriting(@NonNull final Path dirPath, @NonNull final String fileName, @NonNull final byte[] content) throws IOException
	{
		final Path resolved = resolveWithinDirectory(dirPath, fileName);
		int collisionCount = 0;
		Path candidate = resolved;
		while (true)
		{
			try
			{
				Files.write(candidate, content, StandardOpenOption.CREATE_NEW);
				return;
			}
			catch (final FileAlreadyExistsException e)
			{
				collisionCount++;
				candidate = resolveWithinDirectory(dirPath, disambiguate(fileName, collisionCount));
			}
		}
	}

	/** Inserts a numeric suffix before the last {@code '.'} extension (or at the end, if there is none). */
	@NonNull
	private static String disambiguate(@NonNull final String fileName, final int collisionCount)
	{
		final int extensionSeparatorIndex = fileName.lastIndexOf('.');
		final boolean hasExtension = extensionSeparatorIndex > 0;
		final String baseName = hasExtension ? fileName.substring(0, extensionSeparatorIndex) : fileName;
		final String extension = hasExtension ? fileName.substring(extensionSeparatorIndex) : "";
		return baseName + "_" + collisionCount + extension;
	}

	/**
	 * Rejects a {@code fileName} that would resolve outside {@code dirPath} (e.g. containing {@code ..} or
	 * a path separator) instead of silently writing there. {@code fileName} may be operator-controlled (a
	 * customer-authored {@code ImportFileNamePattern}), so this is a defensive check, not a case this
	 * codebase's own callers are known to trigger.
	 */
	@NonNull
	private static Path resolveWithinDirectory(@NonNull final Path dirPath, @NonNull final String fileName)
	{
		final Path resolved = dirPath.resolve(fileName).normalize();
		if (!resolved.getParent().equals(dirPath))
		{
			throw new RuntimeCamelException("Refusing to archive outside " + dirPath + ": resolved file name '" + fileName + "' escapes the archive directory");
		}
		return resolved;
	}
}
