/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.bankStatement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.banking.BankStatementId;
import de.metas.banking.camt53.BankStatementCamt53Service;
import de.metas.banking.camt53.ImportBankStatementRequest;
import de.metas.banking.importfile.BankStatementImportFileId;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.banking.model.I_C_BankStatement_Import_File;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

public class C_BankStatement_Camt53_ImportAttachment_StepDef
{
	private final BankStatementCamt53Service bankStatementCamt53Service = SpringContextHolder.instance.getBean(BankStatementCamt53Service.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	private final C_BankStatement_StepDefData bankStatementTable;

	public C_BankStatement_Camt53_ImportAttachment_StepDef(@NonNull final C_BankStatement_StepDefData bankStatementTable)
	{
		this.bankStatementTable = bankStatementTable;
	}

	@And("^bank statement is imported with identifiers (.*), matching invoice amounts$")
	public void import_camt53_document(@NonNull final String identifier, @NonNull final String fileContent)
	{
		final I_C_BankStatement_Import_File importFileRecord = InterfaceWrapperHelper.newInstance(I_C_BankStatement_Import_File.class);
		InterfaceWrapperHelper.saveRecord(importFileRecord);

		final ImmutableList<String> bankStatementIdentifiers = StepDefUtil.extractIdentifiers(identifier);

		final ImportBankStatementRequest importBankStatementRequest = ImportBankStatementRequest
				.builder()
				.camt53File(AttachmentEntryDataResource.builder()
									.source(fileContent.getBytes())
									.filename("does_not_matter.xml")
									.build())
				.isMatchAmounts(true)
				.bankStatementImportFileId(BankStatementImportFileId.ofRepoId(importFileRecord.getC_BankStatement_Import_File_ID()))
				.build();
		
		final ImmutableSet<BankStatementId> bankStatementIds = bankStatementCamt53Service.importBankToCustomerStatement(importBankStatementRequest);
		assertThat(bankStatementIds).isNotEmpty();
		assertThat(bankStatementIdentifiers.size()).isEqualTo(bankStatementIds.size());

		final Iterator<BankStatementId> bankStatementIdIterator = bankStatementIds.iterator();
		for (int bankStatementIndex = 0; bankStatementIndex < bankStatementIds.size(); bankStatementIndex++)
		{
			bankStatementTable.putOrReplace(bankStatementIdentifiers.get(bankStatementIndex), bankStatementDAO.getById(bankStatementIdIterator.next()));
		}
	}
}
