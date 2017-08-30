package de.metas.attachments;

import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_AttachmentEntry;

public interface IAttachmentDAO extends ISingletonService
{
	I_AD_Attachment retrieveAttachment(Properties ctx, int adTableId, int recordId, String trxName);

	List<AttachmentEntry> retrieveAttachmentEntries(I_AD_Attachment attachment);

	AttachmentEntry retrieveAttachmentEntryById(int attachmentId, int attachmentEntryId);

	/**
	 * 
	 * @param attachmentId
	 * @param filename
	 * @return the retrieved entry of {@code null}, if there is none.
	 */
	AttachmentEntry retrieveAttachmentEntryByFilename(int attachmentId, String filename);

	byte[] retrieveFirstAttachmentEntryAsBytes(int attachmentId);

	AttachmentEntry retrieveFirstAttachmentEntry(int attachmentId);

	AttachmentEntry toAttachmentEntry(I_AD_AttachmentEntry entryRecord);

	void saveAttachmentEntry(I_AD_Attachment attachment, AttachmentEntry entry);

	boolean deleteAttachmentEntryById(int attachmentId, int attachmentEntryId);

	boolean hasAttachmentEntries(int attachmentId);

}
