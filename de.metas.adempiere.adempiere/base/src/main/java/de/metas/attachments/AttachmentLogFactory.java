package de.metas.attachments;

import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_AD_Attachment_Log;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;

import de.metas.util.Check;
import lombok.NonNull;

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
@Service
public class AttachmentLogFactory
{
	public void syncToRecord(AttachmentLog attachmentLog, I_AD_Attachment_Log attachmentLogRecord)
	{
		attachmentLogRecord.setContentType(attachmentLog.getContentType());
		attachmentLogRecord.setDescription(attachmentLog.getDescription());
		attachmentLogRecord.setFileName(attachmentLog.getFilename());
		attachmentLogRecord.setType(attachmentLog.getType().name());
		attachmentLogRecord.setURL(attachmentLog.getUrl().toASCIIString());
		final ITableRecordReference recordRef = attachmentLog.getRecordRef();
		attachmentLogRecord.setRecord_ID(recordRef.getRecord_ID());
		attachmentLogRecord.setAD_Table_ID(recordRef.getAD_Table_ID());
		syncTagsToRecord(
				attachmentLog.getTags(),
				attachmentLogRecord);
	}

	private void syncTagsToRecord(
			@NonNull final Map<String, String> tags,
			@NonNull final I_AD_Attachment_Log attachmentLogRecord)
	{
		validateTags(tags);

		final String tagsAsString = Joiner
				.on(AttachmentConstants.TAGS_SEPARATOR)
				.withKeyValueSeparator(AttachmentConstants.TAGS_KEY_VALUE_SEPARATOR)
				.join(tags);
		attachmentLogRecord.setTags(tagsAsString);
	}

	private Map<String, String> validateTags(@NonNull final Map<String, String> tags)
	{
		for (final Entry<String, String> tag : tags.entrySet())
		{
			Check.errorIf(tag.getKey().contains(AttachmentConstants.TAGS_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					AttachmentConstants.TAGS_SEPARATOR, tag.getKey(), tag.getValue());
			Check.errorIf(tag.getKey().contains(AttachmentConstants.TAGS_KEY_VALUE_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					AttachmentConstants.TAGS_KEY_VALUE_SEPARATOR, tag.getKey(), tag.getValue());

			Check.errorIf(tag.getValue().contains(AttachmentConstants.TAGS_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					AttachmentConstants.TAGS_SEPARATOR, tag.getKey(), tag.getValue());
			Check.errorIf(tag.getValue().contains(AttachmentConstants.TAGS_KEY_VALUE_SEPARATOR),
					"Tags may not contain {}; illegal entry: name={}; value={}",
					AttachmentConstants.TAGS_KEY_VALUE_SEPARATOR, tag.getKey(), tag.getValue());
		}
		return tags;
	}
}
