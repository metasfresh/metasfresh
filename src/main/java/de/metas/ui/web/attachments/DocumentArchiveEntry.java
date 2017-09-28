package de.metas.ui.web.attachments;

import java.net.URI;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.FileUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.MimeType;

import de.metas.attachments.AttachmentEntryType;
import de.metas.ui.web.window.datatypes.DocumentId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

class DocumentArchiveEntry implements IDocumentAttachmentEntry
{
	/* package */static DocumentArchiveEntry of(final DocumentId id, final I_AD_Archive archive)
	{
		return new DocumentArchiveEntry(id, archive);
	}

	private DocumentId id;
	private final I_AD_Archive archive;

	private DocumentArchiveEntry(final DocumentId id, final I_AD_Archive archive)
	{
		this.id = id;
		this.archive = archive;
	}

	@Override
	public DocumentId getId()
	{
		return id;
	}
	
	@Override
	public AttachmentEntryType getType()
	{
		return AttachmentEntryType.Data;
	}

	@Override
	public String getFilename()
	{
		final String contentType = getContentType();
		final String fileExtension = MimeType.getExtensionByType(contentType);
		final String name = archive.getName();
		return FileUtils.changeFileExtension(name, fileExtension);
	}

	@Override
	public byte[] getData()
	{
		final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
		return archiveBL.getBinaryData(archive);
	}

	@Override
	public String getContentType()
	{
		final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
		return archiveBL.getContentType(archive);
	}
	
	@Override
	public URI getUrl()
	{
		return null;
	}

}
