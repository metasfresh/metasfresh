package de.metas.attachments;

import java.net.URI;

import org.adempiere.util.lang.ITableRecordReference;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Value
@ToString
public class AttachmentLog
{
	String filename;
	AttachmentEntryType type;
	URI url;
	AttachmentTags attachmentTags;
	ITableRecordReference recordRef;
	String contentType;
	String description;
	AttachmentEntry attachmentEntry;

	@Builder
	private AttachmentLog(@NonNull final AttachmentEntry attachmentEntry,
			final ITableRecordReference recordRef,
			final String description)
	{
		this.filename = attachmentEntry.getFilename();
		this.type = attachmentEntry.getType();
		this.attachmentTags = attachmentEntry.getTags();
		this.attachmentEntry = attachmentEntry;
		this.contentType = attachmentEntry.getMimeType();
		this.recordRef = recordRef;
		this.description = description;
		this.url = attachmentEntry.getUrl();
	}

}
