/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.process;

import com.google.common.collect.ImmutableMap;
import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.IMsgBL;
import de.metas.impexp.DataImportRequest;
import de.metas.impexp.DataImportResult;
import de.metas.impexp.DataImportService;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.api.Params;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_I_BankStatement;
import org.compiere.util.DB;

public class C_BankStatement_ImportAttachment extends JavaProcess implements IProcessPrecondition
{
	// TODO tbp: @teo how to not hardcode this?
	public static final DataImportConfigId HARDCODED_BANK_STATEMENT_DATA_IMPORT_REPO_ID = DataImportConfigId.ofRepoId(540008);
	@Param(parameterName = I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID, mandatory = true)
	private AttachmentEntryId p_AD_AttachmentEntry_ID;

	private static final String BANK_STATEMENT_MUST_BE_IN_PROGRESS_MSG = "Bank Statement must be in progress.";

	private final IMsgBL iMsgBL = Services.get(IMsgBL.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final transient DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);

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

		final I_C_BankStatement selectedBankStatement = context.getSelectedModel(I_C_BankStatement.class);
		final DocStatus docStatus = DocStatus.ofCode(selectedBankStatement.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.reject(iMsgBL.getTranslatableMsgText(BANK_STATEMENT_MUST_BE_IN_PROGRESS_MSG));
		}

		// TODO tbp:
		// check bpbank account,
		//
		// name ,
		//
		// statement date

		return ProcessPreconditionsResolution.accept();
	}

	@RunOutOfTrx
	@Override
	protected String doIt() throws Exception
	{

		DB.executeUpdateEx("Delete from I_BankStatement where true;", ITrx.TRXNAME_ThreadInherited);

		final AttachmentEntryDataResource data = attachmentEntryService.retrieveDataResource(getAttachmentEntryId());

		final DataImportResult result = dataImportService.importData(DataImportRequest.builder()
				.data(data)
				.dataImportConfigId(HARDCODED_BANK_STATEMENT_DATA_IMPORT_REPO_ID)
				.clientId(getClientId())
				.orgId(getOrgId())
				.userId(getUserId())
				.additionalParameters(computeImportProcessParams())
				.build());

		return MSG_OK;
	}

	private Params computeImportProcessParams()
	{
		final I_C_BankStatement bankStatement = Services.get(IBankStatementDAO.class).getById(getRecord_ID());
		final ImmutableMap<String, Object> paramsMap = ImmutableMap.<String, Object>builder()
				.put(I_I_BankStatement.COLUMNNAME_C_BP_BankAccount_ID, bankStatement.getC_BP_BankAccount_ID())
				.put(I_I_BankStatement.COLUMNNAME_StatementDate, bankStatement.getStatementDate())
				.put(I_I_BankStatement.COLUMNNAME_Name, bankStatement.getName())
				.build();

		return Params.ofMap(paramsMap);
	}

	private AttachmentEntryId getAttachmentEntryId()
	{
		return p_AD_AttachmentEntry_ID;
	}
}
