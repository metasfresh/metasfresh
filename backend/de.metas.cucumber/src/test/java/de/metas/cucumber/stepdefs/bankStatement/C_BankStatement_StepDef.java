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

import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BankStatement;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_BankStatement_StepDef
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_BankStatement_StepDefData bankStatementTable;
	private final C_BP_BankAccount_StepDefData bankAccountTable;

	public C_BankStatement_StepDef(
			@NonNull final C_BankStatement_StepDefData bankStatementTable,
			@NonNull final C_BP_BankAccount_StepDefData bankAccountTable)
	{
		this.bankStatementTable = bankStatementTable;
		this.bankAccountTable = bankAccountTable;
	}

	@And("validate C_BankStatement")
	public void validate_C_BankStatement(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			validateBankStatement(row);
		}
	}

	@Given("^the C_BankStatement identified by (.*) is completed$")
	public void complete_C_BankStatement(@NonNull final String bankStatementIdentifier)
	{
		final I_C_BankStatement bankStatementRecord = bankStatementTable.get(bankStatementIdentifier);
		bankStatementRecord.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(bankStatementRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private void validateBankStatement(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_BankStatement.COLUMNNAME_C_BankStatement_ID + "." + TABLECOLUMN_IDENTIFIER);

		final SoftAssertions softly = new SoftAssertions();

		final I_C_BankStatement bankStatementRecord = bankStatementTable.get(identifier);
		softly.assertThat(bankStatementRecord).as("C_BankStatement.Identifier=%s - C_BankStatement record", identifier).isNotNull();
		InterfaceWrapperHelper.refresh(bankStatementRecord);

		final BigDecimal beginningBalance = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_BankStatement.COLUMNNAME_BeginningBalance);
		if (beginningBalance != null)
		{
			softly.assertThat(bankStatementRecord.getBeginningBalance()).as("C_BankStatement.Identifier=%s - BeginningBalance", identifier).isEqualByComparingTo(beginningBalance);
		}

		final BigDecimal endingBalance = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_BankStatement.COLUMNNAME_EndingBalance);
		if (endingBalance != null)
		{
			softly.assertThat(bankStatementRecord.getEndingBalance()).as("C_BankStatement.Identifier=%s - EndingBalance", identifier).isEqualByComparingTo(endingBalance);
		}

		final BigDecimal statementDifference = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_BankStatement.COLUMNNAME_StatementDifference);
		if (statementDifference != null)
		{
			softly.assertThat(bankStatementRecord.getStatementDifference()).as("C_BankStatement.Identifier=%s - StatementDifference", identifier).isEqualByComparingTo(statementDifference);
		}

		final boolean isProcessed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_BankStatement.COLUMNNAME_Processed, false);
		softly.assertThat(bankStatementRecord.isProcessed()).as("C_BankStatement.Identifier=%s - StatementDate", identifier).isEqualTo(isProcessed);

		final String bankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, "OPT." + I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bankAccountIdentifier))
		{
			final int bankAccountId = bankAccountTable.get(bankAccountIdentifier).getC_BP_BankAccount_ID();
			softly.assertThat(bankStatementRecord.getC_BP_BankAccount_ID()).as("C_BankStatement.Identifier=%s - C_BP_BankAccount_ID", identifier).isEqualTo(bankAccountId);
		}

		final LocalDate statementDate = DataTableUtil.extractLocalDateOrNullForColumnName(row, "OPT." + I_C_BankStatement.COLUMNNAME_StatementDate);
		if (statementDate != null)
		{
			final OrgId orgId = OrgId.ofRepoId(bankStatementRecord.getAD_Org_ID());
			final ZoneId zoneId = orgDAO.getTimeZone(orgId);
			softly.assertThat(TimeUtil.asLocalDate(bankStatementRecord.getStatementDate(), zoneId)).as("C_BankStatement.Identifier=%s - StatementDate", identifier).isEqualTo(statementDate);
		}

		final boolean isReconciled = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_BankStatement.COLUMNNAME_IsReconciled, false);
		softly.assertThat(bankStatementRecord.isReconciled()).as("C_BankStatement.Identifier=%s - IsReconciled", identifier).isEqualTo(isReconciled);

		softly.assertAll();
	}
}
