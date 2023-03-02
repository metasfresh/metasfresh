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

package de.metas.bpartner.blockfile;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.impexp.blockstatus.BPartnerBlockStatusImportProcess;
import de.metas.common.util.Check;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static de.metas.util.FileUtil.getFileBaseName;

@Service
public class BPartnerBlockFileService
{
	private final BPartnerBlockFileRepository bPartnerBlockFileRepository;
	private final AttachmentEntryService attachmentEntryService;

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final AdMessageKey MSG_FILE_ALREADY_ATTACHED = AdMessageKey.of("de.metas.bpartner.blockfile.FileAlreadyAttached");
	private static final AdMessageKey MSG_BUSINESS_PARTNER_BLOCK_CHANGE_PROCESSED = AdMessageKey.of("de.metas.bpartner.blockfile.BPartnerBlockChangeProcessed");

	public BPartnerBlockFileService(
			@NonNull final BPartnerBlockFileRepository bPartnerBlockFileRepository,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.bPartnerBlockFileRepository = bPartnerBlockFileRepository;
		this.attachmentEntryService = attachmentEntryService;
	}

	@NonNull
	public BPartnerBlockFile getById(@NonNull final BPartnerBlockFileId id)
	{
		return bPartnerBlockFileRepository.getById(id);
	}

	public void overrideFileName(
			@NonNull final TableRecordReference tableRecordReference,
			@Nullable final String fileNameOverride)
	{
		final List<AttachmentEntry> attachmentEntries = attachmentEntryService.getByReferencedRecord(tableRecordReference);
		if (attachmentEntries.size() >= 1)
		{
			final ITranslatableString translatableMsgText = msgBL.getTranslatableMsgText(MSG_FILE_ALREADY_ATTACHED);

			throw new AdempiereException(translatableMsgText)
					.markAsUserValidationError();
		}

		final BPartnerBlockFile blockFile = bPartnerBlockFileRepository.getById(BPartnerBlockFileId.ofRepoId(tableRecordReference.getRecord_ID()));
		if (blockFile.isProcessed())
		{
			final ITranslatableString translatableMsgText = msgBL.getTranslatableMsgText(MSG_BUSINESS_PARTNER_BLOCK_CHANGE_PROCESSED);

			throw new AdempiereException(translatableMsgText)
					.markAsUserValidationError();
		}

		Optional.ofNullable(getFileBaseName(fileNameOverride))
				.ifPresent(fileName -> bPartnerBlockFileRepository.update(blockFile.toBuilder()
																				  .fileName(fileName)
																				  .build()));
	}

	public void updateImportFlags(@NonNull final BPartnerBlockStatusImportProcess.BPBlockStatusGroupKey bpBlockStatusGroupKey, final boolean processed)
	{
		Check.assumeNotNull(bpBlockStatusGroupKey.getBPartnerBlockFileId(), "BPartnerBlockFileId cannot be missing at this stage!");

		final BPartnerBlockFile bPartnerBlockFile = getById(bpBlockStatusGroupKey.getBPartnerBlockFileId());

		bPartnerBlockFileRepository.update(bPartnerBlockFile.toBuilder()
												   .isProcessed(processed)
												   .isError(!processed)
												   .build());
	}
}
