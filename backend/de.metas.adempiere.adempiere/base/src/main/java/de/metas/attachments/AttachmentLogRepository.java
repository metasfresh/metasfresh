package de.metas.attachments;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Attachment_Log;
import org.springframework.stereotype.Repository;

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
@Repository
public class AttachmentLogRepository
{
	public AttachmentLog save(@NonNull final AttachmentLog attachmentLog)
	{
		final I_AD_Attachment_Log attachmentLogRecord;
		attachmentLogRecord = newInstance(I_AD_Attachment_Log.class);
		syncToRecord(attachmentLog, attachmentLogRecord);
		saveRecord(attachmentLogRecord);
		return attachmentLog;
	}
	
	private void syncToRecord(AttachmentLog attachmentLog, I_AD_Attachment_Log attachmentLogRecord)
	{
		attachmentLogRecord.setContentType(attachmentLog.getContentType());
		attachmentLogRecord.setDescription(attachmentLog.getDescription());
		attachmentLogRecord.setFileName(attachmentLog.getFilename());
		attachmentLogRecord.setType(attachmentLog.getType().name());
		final String urlToBeSynced = attachmentLog.getUrl() != null ? attachmentLog.getUrl().toASCIIString() : null;
		attachmentLogRecord.setURL(urlToBeSynced);
		final ITableRecordReference recordRef = attachmentLog.getRecordRef();
		attachmentLogRecord.setRecord_ID(recordRef.getRecord_ID());
		attachmentLogRecord.setAD_Table_ID(recordRef.getAD_Table_ID());
		AttachmentTags attachmentTags=attachmentLog.getAttachmentTags();
		attachmentLogRecord.setTags(attachmentTags.getTagsAsString());
	}
}
