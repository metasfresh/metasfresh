/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.blockstatus.file;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.attachments.listener.AttachmentListenerConstants;
import de.metas.common.util.Check;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Block_File;

/**
 * Important: when renaming this class, please make sure to also update its {@link I_AD_JavaClass} record.
 */
public class BPartnerBlockStatusFileAttachListener implements AttachmentListener
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final BPartnerBlockFileService blockFileService = SpringContextHolder.instance.getBean(BPartnerBlockFileService.class);

	private static final AdMessageKey MSG_FILE_ALREADY_ATTACHED = AdMessageKey.of("de.metas.bpartner.blockfile.FileAlreadyAttached");

	@Override
	public AttachmentListenerConstants.ListenerWorkStatus afterRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		final BPartnerBlockFileId fileId = tableRecordReference.getIdAssumingTableName(I_C_BPartner_Block_File.Table_Name,
																					   BPartnerBlockFileId::ofRepoId);

		final BPartnerBlockStatusFile blockStatusFile = blockFileService.getById(fileId);

		blockFileService.save(blockStatusFile.withFileName(attachmentEntry.getFilename()));

		return AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
	}

	@Override
	public AttachmentListenerConstants.ListenerWorkStatus beforeRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference recordReference)
	{
		Check.assume(recordReference.getTableName().equals(I_C_BPartner_Block_File.Table_Name), "This is only about C_BPartner_Block_File!");

		final boolean attachmentEntryMatch = attachmentEntryService.getByReferencedRecord(recordReference)
				.stream()
				.map(AttachmentEntry::getId)
				.allMatch(attachmentEntryId -> AttachmentEntryId.equals(attachmentEntryId, attachmentEntry.getId()));

		if (!attachmentEntryMatch)
		{
			final ITranslatableString translatableMsgText = msgBL.getTranslatableMsgText(MSG_FILE_ALREADY_ATTACHED);

			throw new AdempiereException(translatableMsgText)
					.markAsUserValidationError();
		}

		return AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
	}
}
