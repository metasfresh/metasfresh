package org.adempiere.archive.spi.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.archive.ArchiveStorageConfig;
import de.metas.archive.ArchiveStorageConfigId;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.MimeType;
import org.compiere.util.Util;
import org.slf4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;

/**
 * File system archive storage
 *
 * @author tsa
 */
public class FilesystemArchiveStorage implements IArchiveStorage
{
	private static final Logger logger = LogManager.getLogger(FilesystemArchiveStorage.class);

	/**
	 * string replaces the archive root in stored xml file to allow the changing of the attachment root.
	 */
	private static final String ARCHIVE_FOLDER_PLACEHOLDER = "%ARCHIVE_FOLDER%";

	@NonNull private final ArchiveStorageConfigId storageConfigId;
	@NonNull private final String archivePathRoot;

	public FilesystemArchiveStorage(final ArchiveStorageConfig config)
	{
		this.storageConfigId = config.getId();
		this.archivePathRoot = normalizePath(config.getFileSystemNotNull().getPath());
	}

	private static String normalizePath(@NonNull final Path path)
	{
		String archivePathRoot = path.toAbsolutePath().toString();
		if (!archivePathRoot.endsWith(File.separator))
		{
			// log.warn("archive path doesn't end with " + File.separator);
			archivePathRoot += File.separator;
		}

		return archivePathRoot;
	}

	@Override
	public I_AD_Archive newArchive(final Properties ctx)
	{
		final I_AD_Archive archive = IArchiveStorage.super.newArchive(ctx);
		archive.setAD_Archive_Storage_ID(storageConfigId.getRepoId());
		archive.setIsFileSystem(true);
		return archive;
	}

	/**
	 * @return attachment data
	 */
	@Override
	public byte[] getBinaryData(final I_AD_Archive archive)
	{
		byte[] data = archive.getBinaryData();
		if (data == null)
		{
			return null;
		}

		// 04692: metas-ts removed xml processing because totally don't need it, and it's prone to "content-is-not-allowed-in-prolog" errors
		String filePath = new String(data, StandardCharsets.UTF_8);
		if (Check.isEmpty(filePath, true))
		{
			throw new AdempiereException("No File Path was found in attached XML message for " + archive);
		}

		filePath = filePath.replaceFirst(ARCHIVE_FOLDER_PLACEHOLDER, archivePathRoot.replaceAll("\\\\", "\\\\\\\\"));
		// just to be sure...
		String replaceSeparator = File.separator;
		if (!replaceSeparator.equals("/"))
		{
			replaceSeparator = "\\\\";
		}
		filePath = filePath.replaceAll("/", replaceSeparator);
		filePath = filePath.replaceAll("\\\\", replaceSeparator);
		logger.debug("FilePath: {}", filePath);

		final File file = new File(filePath);
		if (!file.exists())
		{
			throw new AdempiereException("File not found: " + file.getAbsolutePath());
		}

		return Util.readBytes(file);
	}

	/**
	 * Save to file system. If the MArchive is not saved yet (id==0) it will first save the MArchive object because it uses the id as filename.
	 */
	@Override
	public void setBinaryData(final I_AD_Archive archive, final byte[] inflatedData)
	{
		if (inflatedData == null || inflatedData.length == 0)
		{
			throw new IllegalArgumentException("InflatedData is NULL");
		}

		if (archive.getAD_Archive_ID() <= 0)
		{
			// set binary data otherwise save will fail
			archive.setBinaryData(new byte[] { '0' });
			InterfaceWrapperHelper.save(archive);
		}

		// final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		BufferedOutputStream out = null;
		try
		{
			// create destination folder
			final File destFolder = new File(archivePathRoot + File.separator + getArchivePathSnippet(archive));
			if (!destFolder.exists())
			{
				if (!destFolder.mkdirs())
				{
					logger.warn("Unable to create folder: {}", destFolder.getPath());
				}
			}
			// write to pdf
			final String mimeType = Services.get(IArchiveBL.class).getContentType(archive);
			final String fileExtension = MimeType.getExtensionByType(mimeType);
			final String filenamePart = archive.getAD_Archive_ID() + fileExtension;
			final File destFile = new File(destFolder, filenamePart);

			out = new BufferedOutputStream(new FileOutputStream(destFile));
			out.write(inflatedData);
			out.flush();

			// 04692: metas-ts removed xml processing because totally don't need it, and it's prone to "content-is-not-allowed-in-prolog" errors
			final String archiveInfo = ARCHIVE_FOLDER_PLACEHOLDER + getArchivePathSnippet(archive) + filenamePart;
			archive.setBinaryData(archiveInfo.getBytes(StandardCharsets.UTF_8));
			archive.setIsFileSystem(true);
		}
		catch (Exception e)
		{
			archive.setBinaryData(null);
			// m_deflated = null;
			throw new AdempiereException("Error saving data to filesystem (archive=" + archive + ")", e);
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (Exception ignored)
				{
				}
			}
		}

	}

	/**
	 * Returns the archive path (snippet), containing client, org and archive id. The process, table and record id are only included when they are not null.
	 *
	 * @return String
	 */
	private String getArchivePathSnippet(final I_AD_Archive archive)
	{
		final StringBuilder path = new StringBuilder();
		path.append(archive.getAD_Client_ID()).append(File.separator);
		path.append(archive.getAD_Org_ID()).append(File.separator);

		final int adProcessId = archive.getAD_Process_ID();
		if (adProcessId > 0)
		{
			path.append(adProcessId).append(File.separator);
		}

		final int adTableId = archive.getAD_Table_ID();
		if (adTableId > 0)
		{
			path.append(adTableId).append(File.separator);
		}

		final int recordId = archive.getRecord_ID();
		if (recordId > 0)
		{
			path.append(recordId).append(File.separator);
		}

		return path.toString();
	}

	@Override
	public String toString()
	{
		return "FilesystemArchiveStorage [RootPath=" + archivePathRoot + "]";
	}
}
