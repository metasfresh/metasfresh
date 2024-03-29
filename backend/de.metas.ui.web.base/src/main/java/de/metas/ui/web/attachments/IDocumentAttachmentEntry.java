package de.metas.ui.web.attachments;

import de.metas.attachments.AttachmentEntryType;
import de.metas.ui.web.window.datatypes.DocumentId;

import java.net.URI;
import java.time.Instant;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IDocumentAttachmentEntry
{
	DocumentId getId();

	AttachmentEntryType getType();

	String getFilename();

	byte[] getData();

	String getContentType();

	URI getUrl();

	Instant getCreated();

	static IDocumentAttachmentEntry cast(final Object object)
	{
		return (IDocumentAttachmentEntry)object;
	}
}
