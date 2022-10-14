/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.impexp.camt53;

import de.metas.banking.BankStatementId;
import de.metas.banking.bankstatement.importer.BankStatementCamt53DataSource;
import de.metas.banking.bankstatement.importer.BankStatementCamt53Service;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BankStatement;

import java.io.InputStream;
import java.util.Set;

public class C_BankStatement_Camt53_Import_LoadFromFile extends JavaProcess
{
	private static final AdMessageKey MSG_NO_STATEMENT_IMPORTED = AdMessageKey.of("de.metas.banking.impexp.camt53.C_BankStatement_Camt53_Import_LoadFromFile.NoStatementImported");

	private final BankStatementCamt53Service bankStatementCamt53Service = SpringContextHolder.instance.getBean(BankStatementCamt53Service.class);

	@Param(mandatory = true, parameterName = "FileName")
	private String p_FileName;
	
	@Override
	protected String doIt() throws Exception
	{
		final InputStream bankStatementToBeImported = BankStatementCamt53DataSource
				.ofFile(p_FileName)
				.getContentAsInputStream();

		final Set<BankStatementId> importedBankStatementIds = bankStatementCamt53Service.importBankToCustomerStatement(bankStatementToBeImported);

		openImportedRecords(importedBankStatementIds);

		return MSG_OK;
	}

	private void openImportedRecords(@NonNull final Set<BankStatementId> importedBankStatementIds)
	{
		if (importedBankStatementIds.size() == 0)
		{
			final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_NO_STATEMENT_IMPORTED);
			throw new AdempiereException(msg)
					.markAsUserValidationError();
		}
		else if (importedBankStatementIds.size() == 1)
		{
			setRecordToOpen(importedBankStatementIds.iterator().next());
		}
		else
		{
			setRecordsToOpen(importedBankStatementIds);
		}
	}

	private void setRecordsToOpen(@NonNull final Set<BankStatementId> importedBankStatementIds)
	{
		getResult().setRecordToOpen(ProcessExecutionResult.RecordsToOpen.builder()
											.records(TableRecordReference.ofRecordIds(I_C_BankStatement.Table_Name, BankStatementId.toIntSet(importedBankStatementIds)))
											.target(ProcessExecutionResult.RecordsToOpen.OpenTarget.GridView)
											.targetTab(ProcessExecutionResult.RecordsToOpen.TargetTab.SAME_TAB_OVERLAY)
											.automaticallySetReferencingDocumentPaths(true)
											.build());
	}

	private void setRecordToOpen(@NonNull final BankStatementId importedBankStatementId)
	{
		getResult().setRecordToOpen(ProcessExecutionResult.RecordsToOpen.builder()
											.record(TableRecordReference.of(I_C_BankStatement.Table_Name, importedBankStatementId))
											.target(ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument)
											.targetTab(ProcessExecutionResult.RecordsToOpen.TargetTab.SAME_TAB)
											.build());
	}
}
