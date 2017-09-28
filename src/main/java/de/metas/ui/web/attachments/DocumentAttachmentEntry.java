package de.metas.ui.web.attachments;

import java.net.URI;

import org.adempiere.util.Services;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryType;
import de.metas.attachments.IAttachmentDAO;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.ToString;

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

/**
 * Attachment entry
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
class DocumentAttachmentEntry implements IDocumentAttachmentEntry
{
	/* package */static DocumentAttachmentEntry of(final DocumentId id, final AttachmentEntry entry)
	{
		return new DocumentAttachmentEntry(id, entry);
	}

	private final DocumentId id;
	private final AttachmentEntry entry;

	private DocumentAttachmentEntry(final DocumentId id, final AttachmentEntry entry)
	{
		this.id = id;
		this.entry = entry;
	}

	@Override
	public DocumentId getId()
	{
		return id;
	}

	@Override
	public AttachmentEntryType getType()
	{
		return entry.getType();
	}

	@Override
	public String getFilename()
	{
		return entry.getFilename();
	}

	@Override
	public byte[] getData()
	{
		return Services.get(IAttachmentDAO.class).retrieveData(entry);
	}

	@Override
	public String getContentType()
	{
		return entry.getContentType();
	}

	@Override
	public URI getUrl()
	{
		return entry.getUrl();
	}
}
