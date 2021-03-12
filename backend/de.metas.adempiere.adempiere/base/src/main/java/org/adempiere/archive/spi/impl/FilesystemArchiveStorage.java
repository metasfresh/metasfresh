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


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Ini;
import org.compiere.util.MimeType;
import org.compiere.util.Util;

/**
 * File system archive storage
 * 
 * @author tsa
 * 
 */
public class FilesystemArchiveStorage extends AbstractArchiveStorage
{
	// the encoding that we use when converting the path info to and from byte[]
	private static final String UTF_8 = "UTF-8";

	private static final Logger logger = LogManager.getLogger(FilesystemArchiveStorage.class);

	/**
	 * string replaces the archive root in stored xml file to allow the changing of the attachment root.
	 */
	private static final String ARCHIVE_FOLDER_PLACEHOLDER = "%ARCHIVE_FOLDER%";

	private String archivePathRoot;

	public FilesystemArchiveStorage()
	{
		super();
	}
	
	@Override
	public void init(@NonNull final ClientId adClientId)
	{
		final IClientDAO clientDAO = Services.get(IClientDAO.class);
		final I_AD_Client client = clientDAO.getById(adClientId);
		this.archivePathRoot = getArchivePath(client);
		logger.info("init: Archive Path: {}, Config={}", archivePathRoot, client);
	}
	
	private final void checkContext()
	{
		Check.assume(!Ini.isSwingClient() || Services.get(IDeveloperModeBL.class).isEnabled(), "Server mode required");
		
		if (Check.isEmpty(archivePathRoot, true))
		{
			throw new IllegalArgumentException("FilesystemArchiveStorage is not configured. No root path defined.");
		}
	}

	private static final String getArchivePath(final I_AD_Client config)
	{
		String archivePathRoot;
		if (File.separatorChar == '\\')
		{
			archivePathRoot = config.getWindowsArchivePath();
		}
		else
		{
			archivePathRoot = config.getUnixArchivePath();
		}

		if (Check.isEmpty(archivePathRoot, true))
		{
			throw new AdempiereException("No archive path defined for " + config);
		}

		// Fix path separator
		if (File.separatorChar == '\\')
		{
			archivePathRoot = archivePathRoot.replace('/', File.separatorChar);
		}
		else
		{
			archivePathRoot = archivePathRoot.replace('\\', File.separatorChar);
		}

		if (!archivePathRoot.endsWith(File.separator))
		{
			// log.warn("archive path doesn't end with " + File.separator);
			archivePathRoot += File.separator;
		}

		return archivePathRoot;
	}

	@Override
	public I_AD_Archive newArchive(final Properties ctx, final String trxName)
	{
		checkContext();
		
		final I_AD_Archive archive = super.newArchive(ctx, trxName);
		archive.setIsFileSystem(true);
		return archive;
	}

	/**
	 * @return attachment data
	 */
	@Override
	public byte[] getBinaryData(final I_AD_Archive archive)
	{
		checkContext();
		
		byte[] data = archive.getBinaryData();
		// m_deflated = null;
		// m_inflated = null;
		if (data == null)
		{
			return null;
		}

		try
		{
			// 04692: metas-ts removed xml processing because totally don't need it and it's prone to "content-is-not-allowed-in-prolog" errors
			String filePath = new String(data, UTF_8);
			if (Check.isEmpty(filePath, true))
			{
				throw new AdempiereException("No File Path was found in attached XML message for " + archive);
			}

			filePath = filePath.replaceFirst(ARCHIVE_FOLDER_PLACEHOLDER, archivePathRoot.replaceAll("\\\\", "\\\\\\\\"));
			// just to be shure...
			String replaceSeparator = File.separator;
			if (!replaceSeparator.equals("/"))
			{
				replaceSeparator = "\\\\";
			}
			filePath = filePath.replaceAll("/", replaceSeparator);
			filePath = filePath.replaceAll("\\\\", replaceSeparator);
			logger.debug("FilePath: " + filePath);

			final File file = new File(filePath);
			if (!file.exists())
			{
				throw new AdempiereException("File not found: " + file.getAbsolutePath());
			}

			final byte[] dataEntry = Util.readBytes(file);
			return dataEntry;
		}
		catch (IOException ioe)
		{
			// I/O error
			// logger.error(ioe.getLocalizedMessage(), ioe);
			throw new AdempiereException(ioe.getLocalizedMessage(), ioe);
		}
		// return null;
	}

	/**
	 * Save to file system. If the MArchive is not saved yet (id==0) it will first save the MArchive object because it uses the id as filename.
	 * 
	 * @param inflatedData
	 */
	@Override
	public void setBinaryData(final I_AD_Archive archive, final byte[] inflatedData)
	{
		checkContext();
		
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
					logger.warn("Unable to create folder: " + destFolder.getPath());
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

			// 04692: metas-ts removed xml processing because totally don't need it and it's prone to "content-is-not-allowed-in-prolog" errors
			final String archiveInfo = ARCHIVE_FOLDER_PLACEHOLDER + getArchivePathSnippet(archive) + filenamePart;
			archive.setBinaryData(archiveInfo.getBytes(UTF_8));
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
				catch (Exception e)
				{
				}
				out = null;
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
