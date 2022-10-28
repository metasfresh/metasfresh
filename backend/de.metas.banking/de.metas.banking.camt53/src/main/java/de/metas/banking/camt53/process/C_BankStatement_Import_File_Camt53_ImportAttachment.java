/*
 * #%L
 * camt53
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.banking.camt53.process;

import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.banking.BankStatementId;
import de.metas.banking.camt53.BankStatementCamt53Service;
import de.metas.banking.camt53.ImportBankStatementRequest;
import de.metas.banking.importfile.BankStatementImportFile;
import de.metas.banking.importfile.BankStatementImportFileId;
import de.metas.banking.importfile.BankStatementImportFileService;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_C_BankStatement;

import java.util.Set;

public class C_BankStatement_Import_File_Camt53_ImportAttachment extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_NO_STATEMENT_IMPORTED = AdMessageKey.of("de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoStatementImported");
	private static final AdMessageKey MSG_SELECTED_RECORD_ALREADY_PROCESSED = AdMessageKey.of("de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed");

	private final BankStatementCamt53Service bankStatementCamt53Service = SpringContextHolder.instance.getBean(BankStatementCamt53Service.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final BankStatementImportFileService bankStatementImportFileService = SpringContextHolder.instance.getBean(BankStatementImportFileService.class);

	@Param(parameterName = I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID, mandatory = true)
	private AttachmentEntryId p_AD_AttachmentEntry_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (isSelectedRecordProcessed(context.getSingleSelectedRecordId()))
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_SELECTED_RECORD_ALREADY_PROCESSED));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final AttachmentEntryDataResource data = attachmentEntryService.retrieveDataResource(getAttachmentEntryId());

		final BankStatementImportFileId bankStatementImportFileId = BankStatementImportFileId.ofRepoId(getRecord_ID());
		final BankStatementImportFile selectedRecord = bankStatementImportFileService.getById(bankStatementImportFileId);

		final ImportBankStatementRequest request = ImportBankStatementRequest.builder()
				.bankStatementImportFileId(bankStatementImportFileId)
				.camt53File(data.getInputStream())
				.isMatchAmounts(selectedRecord.isMatchAmounts())
				.build();

		final Set<BankStatementId> importedBankStatementIds = bankStatementCamt53Service.importBankToCustomerStatement(request);

		openImportedRecords(importedBankStatementIds);

		markRecordAsProcessed(selectedRecord, data.getFilename());

		return JavaProcess.MSG_OK;
	}
	
	private void markRecordAsProcessed(
			@NonNull final BankStatementImportFile bankStatementImportFile, 
			@NonNull final String filename)
	{
		bankStatementImportFileService.save(bankStatementImportFile.toBuilder()
													.filename(filename)
													.importedTimestamp(SystemTime.asInstant())
													.processed(true)
													.build());
	}
	
	private void openImportedRecords(@NonNull final Set<BankStatementId> importedBankStatementIds)
	{
		if (importedBankStatementIds.isEmpty())
		{
			throw new AdempiereException(MSG_NO_STATEMENT_IMPORTED)
					.markAsUserValidationError();
		}

		setRecordsToOpen(importedBankStatementIds);
	}

	private void setRecordsToOpen(@NonNull final Set<BankStatementId> importedBankStatementIds)
	{
		getResult().setRecordToOpen(ProcessExecutionResult.RecordsToOpen.builder()
											.records(TableRecordReference.ofRecordIds(I_C_BankStatement.Table_Name, BankStatementId.toIntSet(importedBankStatementIds)))
											.targetTab(ProcessExecutionResult.RecordsToOpen.TargetTab.NEW_TAB)
											.target(importedBankStatementIds.size() == 1
															? ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument
															: ProcessExecutionResult.RecordsToOpen.OpenTarget.GridView)
											.build());
	}

	private boolean isSelectedRecordProcessed(final int bankStatementImportFileRecordId)
	{
		final BankStatementImportFileId bankStatementImportFileId = BankStatementImportFileId.ofRepoId(bankStatementImportFileRecordId);
		return bankStatementImportFileService.getById(bankStatementImportFileId)
				.isProcessed();
	}

	@NonNull
	private AttachmentEntryId getAttachmentEntryId()
	{
		return p_AD_AttachmentEntry_ID;
	}
}
