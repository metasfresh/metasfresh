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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Archives an imported scripted-import payload (the raw SFTP file content, or the raw REST POST body)
 * to a LOCAL, transport-agnostic processed/error folder — see
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
	 * Writes {@code content} to {@code directory}/{@code fileName}, creating {@code directory}
	 * (and any missing parents) if needed.
	 */
	void archive(@NonNull final String directory, @NonNull final String fileName, @NonNull final String content)
	{
		try
		{
			final Path dirPath = Path.of(directory);
			Files.createDirectories(dirPath);
			Files.writeString(dirPath.resolve(fileName), content, StandardCharsets.UTF_8);
		}
		catch (final IOException e)
		{
			throw new RuntimeCamelException("Failed to locally archive payload to " + directory + "/" + fileName, e);
		}
	}
}
