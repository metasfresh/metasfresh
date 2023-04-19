/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.gljournal_sap.process;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalCopyRequest;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import java.time.Instant;

public class SAP_GLJournal_CopyDocument extends JavaProcess implements IProcessPrecondition
{
	private final static AdMessageKey DOCUMENT_MUST_BE_COMPLETED_MSG = AdMessageKey.of("gljournal_sap.Document_has_to_be_Completed");

	private final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);

	@Param(mandatory = true, parameterName = "DateDoc")
	private Instant dateDoc;

	@Param(mandatory = true, parameterName = "ReversePostingSign")
	private boolean reversePostingSign;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(context.getSingleSelectedRecordId());
		final DocStatus docStatus = glJournalService.getDocStatus(glJournalId);
		if (!docStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DOCUMENT_MUST_BE_COMPLETED_MSG));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final SAPGLJournal createdJournal = glJournalService.copy(SAPGLJournalCopyRequest.builder()
																			  .sourceJournalId(SAPGLJournalId.ofRepoId(getRecord_ID()))
																			  .dateDoc(dateDoc)
																			  .reversePostingSign(reversePostingSign)
																			  .build());

		getResult().setRecordToOpen(TableRecordReference.of(I_SAP_GLJournal.Table_Name, createdJournal.getId()),
									getProcessInfo().getAD_Window_ID(),
									ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument);

		return MSG_OK;
	}
}
