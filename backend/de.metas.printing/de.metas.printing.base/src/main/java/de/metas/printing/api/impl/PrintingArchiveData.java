/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.printing.api.impl;

import com.lowagie.text.pdf.PdfReader;
import de.metas.logging.LogManager;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;

import java.io.IOException;

class PrintingArchiveData
{
	// Services
	private final transient Logger logger = LogManager.getLogger(PrintingArchiveData.class);
	private final transient IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	// Parameters
	private final I_AD_Archive archive;

	// Archive's Data
	private boolean dataLoaded;
	private transient byte[] data;
	private Integer numberOfPages = null;

	public PrintingArchiveData(@NonNull final I_AD_Archive archive)
	{
		this.archive = archive;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	private final byte[] getData()
	{
		if (dataLoaded)
		{
			return data;
		}

		data = archiveBL.getBinaryData(archive);
		dataLoaded = true;
		if (data == null || data.length == 0)
		{
			logger.info("Archive {} does not contain any data. Skip", archive);
			data = null;
		}

		return data;
	}

	public boolean hasData()
	{
		return getData() != null;
	}

	public PdfReader createPdfReader() throws IOException
	{
		final PdfReader reader = new PdfReader(getData());
		return reader;
	}

	public int getNumberOfPages()
	{
		if (numberOfPages != null)
		{
			return numberOfPages;
		}

		if (!hasData())
		{
			return 0;
		}

		PdfReader reader = null;
		try
		{
			reader = createPdfReader();
			numberOfPages = reader.getNumberOfPages();
			return numberOfPages;
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Cannot get number of pages for archive " + archive, e);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (final Exception e)
				{
				}
				reader = null;
			}
		}
	}
}
