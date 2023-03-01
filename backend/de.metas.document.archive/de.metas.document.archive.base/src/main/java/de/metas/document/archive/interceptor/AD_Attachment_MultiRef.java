/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.archive.interceptor;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.blockfile.BPartnerBlockFile;
import de.metas.bpartner.blockfile.BPartnerBlockFileId;
import de.metas.bpartner.blockfile.BPartnerBlockFileRepository;
import de.metas.document.DocTypeId;
import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_AD_Attachment_MultiRef;
import org.compiere.model.I_C_BPartner_Block_File;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.FileUtil.getFileBaseName;
import static de.metas.util.FileUtil.getFileExtension;

@Interceptor(I_AD_Attachment_MultiRef.class)
@Component
public class AD_Attachment_MultiRef
{
	private final ArchiveFileNameService archiveFileNameService;
	private final AttachmentEntryService attachmentEntryService;
	private final BPartnerBlockFileRepository blockFileRepository;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	public AD_Attachment_MultiRef(
			@NonNull final ArchiveFileNameService archiveFileNameService,
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final BPartnerBlockFileRepository blockFileRepository)
	{
		this.archiveFileNameService = archiveFileNameService;
		this.attachmentEntryService = attachmentEntryService;
		this.blockFileRepository = blockFileRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_AD_Attachment_MultiRef.COLUMNNAME_AD_Table_ID, I_AD_Attachment_MultiRef.COLUMNNAME_Record_ID })
	public void overrideFileName(@NonNull final I_AD_Attachment_MultiRef record)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID());

		final I_AD_AttachmentEntry attachmentEntry = record.getAD_AttachmentEntry();

		final String fileExtension = Optional.ofNullable(getFileExtension(attachmentEntry.getFileName()))
				.map(extension -> "." + extension)
				.orElse(null);

		final String fileBaseName = getFileBaseName(attachmentEntry.getFileName());

		if (tableRecordReference.getTableName().equals(I_C_Order.Table_Name))
		{
			final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(record.getRecord_ID()));

			final ArchiveFileNameService.ComputeFileNameRequest computeFileNameRequest = ArchiveFileNameService.ComputeFileNameRequest.builder()
					.docTypeId(DocTypeId.ofRepoIdOrNull(coalesce(order.getC_DocType_ID(), order.getC_DocTypeTarget_ID())))
					.recordReference(tableRecordReference)
					.documentNo(order.getDocumentNo())
					.fileExtension(fileExtension)
					.suffix(fileBaseName)
					.build();

			record.setFileName_Override(archiveFileNameService.computeFileName(computeFileNameRequest));
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void overrideBPBlockFileName(@NonNull final I_AD_Attachment_MultiRef record)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID());
		final I_AD_AttachmentEntry attachmentEntry = record.getAD_AttachmentEntry();

		if (!tableRecordReference.getTableName().equals(I_C_BPartner_Block_File.Table_Name))
		{
			return;
		}

		final List<AttachmentEntry> attachmentEntries = attachmentEntryService.getByReferencedRecord(tableRecordReference);
		if (attachmentEntries.size() >= 1)
		{
			throw new AdempiereException("Business partner block file already has one file attached!")
					.appendParametersToMessage()
					.setParameter(I_C_BPartner_Block_File.COLUMNNAME_C_BPartner_Block_File_ID, tableRecordReference.getRecord_ID());
		}

		final BPartnerBlockFile blockFile = blockFileRepository.getById(BPartnerBlockFileId.ofRepoId(tableRecordReference.getRecord_ID()));

		final String fileBaseName = getFileBaseName(attachmentEntry.getFileName());

		blockFileRepository.update(blockFile.toBuilder()
										   .fileName(fileBaseName)
										   .build());
	}
}