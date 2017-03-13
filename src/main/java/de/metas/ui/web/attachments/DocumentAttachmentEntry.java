package de.metas.ui.web.attachments;

import org.compiere.model.MAttachmentEntry;

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

/**
 * Attachment entry
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class DocumentAttachmentEntry implements IDocumentAttachmentEntry
{
	/* package */static DocumentAttachmentEntry of(final DocumentId id, final MAttachmentEntry entry)
	{
		return new DocumentAttachmentEntry(id, entry);
	}

	private DocumentId id;
	private final MAttachmentEntry entry;

	private DocumentAttachmentEntry(final DocumentId id, final MAttachmentEntry entry)
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
	public String getFilename()
	{
		return entry.getFilename();
	}

	@Override
	public byte[] getData()
	{
		return entry.getData();
	}

	@Override
	public String getContentType()
	{
		return entry.getContentType();
	}
}
