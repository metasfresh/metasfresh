package de.metas.attachments;

import java.net.URI;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableMap;

import de.metas.attachments.AttachmentEntry.AttachmentEntryBuilder;
import de.metas.attachments.AttachmentEntry.Type;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocation;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.BPartnerLocation.BPartnerLocationBuilder;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public final class AttachmentLog
{
	private String filename;
	private Type type;
	private URI url;
	private final ImmutableMap<String, String> tags;
	private final ITableRecordReference recordRef;
	private final String contentType;
	private final String description;
	private final AttachmentEntry attachmentEntry;

	@Builder
	private AttachmentLog(@NonNull final AttachmentEntry attachmentEntry,
			final ITableRecordReference recordRef,
			final String description)
	{
		this.filename = attachmentEntry.getFilename();
		this.type = attachmentEntry.getType();
		this.tags = attachmentEntry.getTags();
		this.attachmentEntry=attachmentEntry;
//		this.attachmentId = attachmentEntry.getId();
		this.contentType = attachmentEntry.getMimeType();
		this.recordRef = recordRef;
		this.description = description;
		this.url = attachmentEntry.getUrl();
	}

}
