package de.metas.report.jasper;

/*
 * #%L
 * de.metas.report.jasper.server.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PrintFormat;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.logging.LogManager;
import de.metas.report.PrintFormatId;
import lombok.NonNull;

/**
 * Builds and returns the attachment image {@link File}.
 * 
 * @author cg
 *
 */
final class AttachmentImageFileLoader 
{
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	
	private static final transient Logger logger = LogManager.getLogger(AttachmentImageFileLoader.class);
	final static ImageFileLoader imgFileLoader = ImageFileLoader.newInstance();

	public static AttachmentImageFileLoader newInstance()
	{
		return new AttachmentImageFileLoader();
	}

	private AttachmentImageFileLoader()
	{
	}

	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("emptyPNGFile", imgFileLoader.getEmptyPNGFile())
				.toString();
	}
	
	public Optional<File> loadImageForPrintFormat(@NonNull final PrintFormatId printFormatId)
	{
		final File imageFile = retrieveImageFile(printFormatId);
		if (imageFile != null)
		{
			return Optional.of(imageFile);
		}
		else
		{
			logger.warn("Cannot find image for {}, please add a file to the Print format. Returning empty PNG file", this);
			return Optional.of(imgFileLoader.getEmptyPNGFile());
		}
	}

	private File retrieveImageFile(@NonNull final PrintFormatId printFormatId)
	{
		final byte[] data = attachmentEntryService.retrieveData(getAttachmentEntryIdByPrintFormatId(printFormatId));
		return imgFileLoader.createTempPNGFile("logo", data);
	}
	
	
	/**
	 * get one attachment entry; does not matter if are several
	 * 
	 * @param printFormatId
	 * @return
	 */
	private AttachmentEntryId getAttachmentEntryIdByPrintFormatId(@NonNull final PrintFormatId printFormatId)
	{
		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

		final List<AttachmentEntry> entries = attachmentEntryService.getByReferencedRecord(TableRecordReference.of(I_AD_PrintFormat.Table_Name, printFormatId));

		if (entries.size() > 0)
		{
			final AttachmentEntry entry = entries.get(0);
			return entry.getId();
		}
		else
		{
			return null;
		}
	}
}
