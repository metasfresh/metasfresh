package de.metas.attachments.storeattachment;

import java.net.URI;

import de.metas.common.util.time.SystemTime;
import org.springframework.stereotype.Component;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DefaultAttachmentStoredListener implements AttachmentStoredListener
{
	private final AttachmentEntryService attachmentEntryService;

	public DefaultAttachmentStoredListener(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	@Override
	public void attachmentWasStored(@NonNull final AttachmentEntry attachmentEntry, @NonNull final URI storageIdentifier)
	{
		final AttachmentTags attachmentTags = attachmentEntry.getTags()
				.withTag(AttachmentTags.TAGNAME_STORED_PREFIX + SystemTime.millis(), storageIdentifier.toString());
		final AttachmentEntry attachmentEntryWithStoreInfo = attachmentEntry
				.toBuilder()
				.tags(attachmentTags)
				.build();

		attachmentEntryService.save(attachmentEntryWithStoreInfo);
	}

}
