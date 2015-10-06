package de.metas.impex.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.File;
import java.util.Arrays;
import java.util.List;

import de.metas.impex.api.IInboundProcessorBL;
import de.metas.impex.exception.ConfigException;

public final class InboundProcessorBL implements IInboundProcessorBL
{

	public List<File> getFiles(final String localFolderName)
	{

		final File localFolder = new File(localFolderName);

		if (!localFolder.isDirectory() || !localFolder.canRead())
		{
			ConfigException.throwNew(ConfigException.LOCALFOLDER_CANT_READ_1P,
					null, localFolderName);
		}
		return Arrays.asList(localFolder.listFiles());
	}

	public String moveToArchive(final File importedFile,
			final String archiveFolder)
	{

		final File archiveDir = new File(archiveFolder);
		checkArchiveDir(archiveDir);

		final File destFile = new File(archiveDir, importedFile.getName());

		if (!importedFile
				.renameTo(destFile))
		{

			ConfigException.throwNew(ConfigException.ARCHIVE_RENAME_FAILED_2P,
					importedFile.getAbsolutePath(), archiveDir
							.getAbsolutePath());
		}
		return null;

	}

	private void checkArchiveDir(final File archiveDir)
	{
		if (!archiveDir.isDirectory())
		{
			ConfigException.throwNew(ConfigException.ARCHIVE_NOT_A_DIR_1P,
					archiveDir.getAbsolutePath());
		}
		if (!archiveDir.canWrite())
		{
			ConfigException.throwNew(ConfigException.ARCHIVE_CANT_WRITE_1P,
					archiveDir.getAbsolutePath());
		}
	}
}
