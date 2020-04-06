package de.metas.impex.spi.impl;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import de.metas.adempiere.util.Parameter;
import de.metas.impex.api.IInboundProcessorBL;
import de.metas.impex.exception.ConfigException;
import de.metas.impex.spi.IImportConnector;

public class FileImporter extends BaseConnector implements IImportConnector
{

	private Iterator<File> currentFiles;

	private File currentFile;

	@Override
	protected void openSpecific()
	{

		final String folder = (String)getCurrentParams().get(
				IInboundProcessorBL.LOCAL_FOLDER).getValue();

		final List<File> files = inboundProcessorBL.getFiles(folder);

		currentFiles = files.iterator();
	}

	@Override
	protected void closeSpecific()
	{
		currentFile = null;
		currentFiles = null;
	}

	private FileInputStream currentStream;

	@Override
	public InputStream connectNext(final boolean lastWasSuccess)
	{

		if (currentStream != null)
		{
			try
			{
				// note: close alone didn't suffice (at least in WinXP when
				// running in eclipse debug session)
				currentStream.getFD().sync();
				currentStream.close();
			}
			catch (IOException e)
			{
				throw new AdempiereException(e);
			}
		}

		if (lastWasSuccess && currentFile != null)
		{

			final String archiveFolder = (String)getCurrentParams().get(
					IInboundProcessorBL.ARCHIVE_FOLDER).getValue();

			inboundProcessorBL.moveToArchive(currentFile, archiveFolder);
		}

		if (currentFiles.hasNext())
		{
			currentFile = currentFiles.next();
			try
			{

				currentStream = new FileInputStream(currentFile);
				return currentStream;
			}
			catch (FileNotFoundException e)
			{
				ConfigException.throwNew(ConfigException.FILE_ACCESS_FAILED_2P,
						currentFile.getAbsolutePath(), e.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Parameter> getParameters()
	{

		final List<Parameter> result = new ArrayList<Parameter>();

		result.add(new Parameter(IInboundProcessorBL.LOCAL_FOLDER,
				"Import-Ordner", "Name of the folder to import from",
				DisplayType.FilePath, 1));

		result.add(new Parameter(IInboundProcessorBL.ARCHIVE_FOLDER,
				"Archiv-Ordner",
				"Name of the folder to move files to after import",
				DisplayType.FilePath, 2));

		return result;
	}

}
