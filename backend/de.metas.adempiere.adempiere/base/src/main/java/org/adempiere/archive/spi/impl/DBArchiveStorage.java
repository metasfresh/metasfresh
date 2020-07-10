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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * Database archive storage
 * 
 * @author tsa
 * 
 */
public class DBArchiveStorage extends AbstractArchiveStorage
{
	private static final Logger logger = LogManager.getLogger(DBArchiveStorage.class);

	@Override
	public I_AD_Archive newArchive(final Properties ctx, final String trxName)
	{
		final I_AD_Archive archive = super.newArchive(ctx, trxName);
		archive.setIsFileSystem(false);
		return archive;
	}

	/**
	 * Get Binary Data. (inflate)
	 * 
	 * @return inflated data
	 */
	@Override
	public byte[] getBinaryData(final I_AD_Archive archive)
	{
		byte[] deflatedData = archive.getBinaryData();
		// m_deflated = null;
		// m_inflated = null;
		if (deflatedData == null)
			return null;
		//
		logger.debug("ZipSize=" + deflatedData.length);
		// m_deflated = new Integer(deflatedData.length);
		if (deflatedData.length == 0)
			return null;

		byte[] inflatedData = null;
		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(deflatedData);
			ZipInputStream zip = new ZipInputStream(in);
			ZipEntry entry = zip.getNextEntry();
			if (entry != null) // just one entry
			{
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[2048];
				int length = zip.read(buffer);
				while (length != -1)
				{
					out.write(buffer, 0, length);
					length = zip.read(buffer);
				}
				//
				inflatedData = out.toByteArray();
				logger.debug("Size=" + inflatedData.length + " - zip=" + entry.getCompressedSize()
						+ "(" + entry.getSize() + ") "
						+ (entry.getCompressedSize() * 100 / entry.getSize()) + "%");
				// m_inflated = new Integer(inflatedData.length);
			}
		}
		catch (Exception e)
		{
			// logger.error("", e);
			inflatedData = null;
			throw new AdempiereException(e);
		}
		return inflatedData;
	} // getBinaryData

	@Override
	public void setBinaryData(@NonNull final I_AD_Archive archive, @NonNull final byte[] uncompressedData)
	{
		if (uncompressedData.length == 0)
		{
			throw new AdempiereException("uncompressedData may not be empty")
					.appendParametersToMessage()
					.setParameter("AD_Archive", archive);
		}
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ZipOutputStream zip = new ZipOutputStream(out);
		zip.setMethod(ZipOutputStream.DEFLATED);
		zip.setLevel(Deflater.BEST_COMPRESSION);
		zip.setComment("adempiere");
		//
		byte[] compressedData = null;
		try
		{
			final ZipEntry entry = new ZipEntry("AdempiereArchive");
			entry.setTime(System.currentTimeMillis());
			entry.setMethod(ZipEntry.DEFLATED);
			zip.putNextEntry(entry);
			zip.write(uncompressedData, 0, uncompressedData.length);
			zip.closeEntry();
			logger.debug(entry.getCompressedSize() + " (" + entry.getSize() + ") "
					+ (entry.getCompressedSize() * 100 / entry.getSize()) + "%");
			//
			// zip.finish();
			zip.close();
			compressedData = out.toByteArray();
			logger.debug("Length=" + uncompressedData.length);
			// m_deflated = new Integer(compressedData.length);
		}
		catch (Exception e)
		{
			// log.error("saveLOBData", e);
			// compressedData = null;
			// m_deflated = null;
			throw AdempiereException.wrapIfNeeded(e);
		}

		archive.setBinaryData(compressedData);
		archive.setIsFileSystem(false);
	}
}
