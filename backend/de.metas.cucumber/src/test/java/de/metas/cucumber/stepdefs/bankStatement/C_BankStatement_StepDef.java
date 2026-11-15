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
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.banking.BankStatementId;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class C_BankStatement_StepDef
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	private final C_BankStatement_StepDefData bankStatementTable;
	private final C_BP_BankAccount_StepDefData bankAccountTable;
	private final C_Payment_StepDefData paymentTable;
	private final TestContext restTestContext;

	public C_BankStatement_StepDef(
			@NonNull final C_BankStatement_StepDefData bankStatementTable,
			@NonNull final C_BP_BankAccount_StepDefData bankAccountTable,
			@NonNull final C_Payment_StepDefData paymentTable,
			@NonNull final TestContext restTestContext)
	{
		this.bankStatementTable = bankStatementTable;
		this.bankAccountTable = bankAccountTable;
		this.paymentTable = paymentTable;
		this.restTestContext = restTestContext;
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

	/**
	 * Loads the newest {@code C_BankStatement} of the given bank account and name into the step data, to be used after an import
	 * that does not return the statement it created (e.g. a CSV data import). Fails if there is no such statement,
	 * so an import that created none is not mistaken for an earlier run's statement.
	 * <p>
	 * Example:
	 * <pre>
	 * And load C_BankStatement created by the import for bank account "org_EUR_account"
	 *   | C_BankStatement_ID.Identifier | Name            |
	 *   | bs_1                          | @customerName@  |
	 * </pre>
	 *
	 * @cucumber.columns
	 * <b>C_BankStatement_ID.Identifier</b> — the identifier to store the statement under<br>
	 * <b>Name</b> — the name of the statement (the one given in the import); {@code @name@} tokens are replaced by the REST context variable of that name
	 */
	@And("load C_BankStatement created by the import for bank account {string}")
	public void load_C_BankStatement_created_by_the_import(
			@NonNull final String bankAccountIdentifier,
			@NonNull final DataTable dataTable)
	{
		final int bankAccountId = bankAccountTable.get(bankAccountIdentifier).getC_BP_BankAccount_ID();

		DataTableRows.of(dataTable).forEach(row -> {
			final String name = restTestContext.resolveVariables(row.getAsString(I_C_BankStatement.COLUMNNAME_Name));

			final I_C_BankStatement bankStatementRecord = queryBL.createQueryBuilder(I_C_BankStatement.class)
					.addEqualsFilter(I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID, bankAccountId)
					.addEqualsFilter(I_C_BankStatement.COLUMNNAME_Name, name)
					.orderByDescending(I_C_BankStatement.COLUMNNAME_C_BankStatement_ID)
					.create()
					.first(I_C_BankStatement.class);
			assertThat(bankStatementRecord).as("C_BankStatement of C_BP_BankAccount_ID=%s (identifier %s) with Name=%s", bankAccountId, bankAccountIdentifier, name).isNotNull();

			row.getAsIdentifier(I_C_BankStatement.COLUMNNAME_C_BankStatement_ID).putOrReplace(bankStatementTable, bankStatementRecord);
		});
	}

	@Given("^the C_BankStatement identified by (.*) is completed$")
	public void complete_C_BankStatement(@NonNull final String bankStatementIdentifier)
	{
		final I_C_BankStatement bankStatementRecord = bankStatementTable.get(bankStatementIdentifier);
		bankStatementRecord.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(bankStatementRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	/**
	 * Completes the statement and asserts that the completion is refused with the given {@code AD_Message.ErrorCode}
	 * and that the statement is not completed afterwards.
	 *
	 * @cucumber.example
	 * <pre>
	 * Then the C_BankStatement identified by bs_1 is completed expecting error code Payment_BPartnerMismatch_Order
	 * </pre>
	 */
	@And("^the C_BankStatement identified by (.*) is completed expecting error code (\\S+)$")
	public void complete_C_BankStatement_expecting_error_code(@NonNull final String bankStatementIdentifier, @NonNull final String errorCode)
	{
		completeExpectingError(bankStatementIdentifier, errorCode, null);
	}

	/**
	 * Like {@link #complete_C_BankStatement_expecting_error_code(String, String)}, and also asserts that the message of the refusal
	 * names the {@code C_Payment.DocumentNo} of the given payment.
	 *
	 * @cucumber.example
	 * <pre>
	 * Then the C_BankStatement identified by bs_1 is completed expecting error code BANKSTATEMENT_ORDER_NOT_WAITING naming the payment identified by p_1
	 * </pre>
	 * @cucumber.depends {@link C_Payment_StepDefData}
	 */
	@And("^the C_BankStatement identified by (.*) is completed expecting error code (\\S+) naming the payment identified by (.*)$")
	public void complete_C_BankStatement_expecting_error_code_naming_payment(
			@NonNull final String bankStatementIdentifier,
			@NonNull final String errorCode,
			@NonNull final String paymentIdentifier)
	{
		completeExpectingError(bankStatementIdentifier, errorCode, paymentTable.get(paymentIdentifier));
	}

	private void completeExpectingError(
			@NonNull final String bankStatementIdentifier,
			@NonNull final String errorCode,
			@Nullable final I_C_Payment expectedPaymentInMessage)
	{
		final I_C_BankStatement bankStatementRecord = bankStatementTable.get(bankStatementIdentifier);
		bankStatementRecord.setDocAction(IDocument.ACTION_Complete);

		// the completion may wrap the exception of the payment it creates, so the error code is looked up along the cause chain
		final Throwable thrown = catchThrowable(() -> documentBL.processEx(bankStatementRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed));
		assertThat(thrown).as("completion of %s expected to be refused with error code %s", bankStatementIdentifier, errorCode).isNotNull();
		final List<String> errorCodes = new ArrayList<>();
		String messageWithErrorCode = null;
		for (Throwable cause = thrown; cause != null; cause = cause.getCause())
		{
			if (cause instanceof AdempiereException)
			{
				final AdempiereException adempiereException = (AdempiereException)cause;
				errorCodes.add(adempiereException.getErrorCode());
				if (messageWithErrorCode == null && errorCode.equals(adempiereException.getErrorCode()))
				{
					messageWithErrorCode = adempiereException.getMessage();
				}
			}
		}
		assertThat(errorCodes).as("error codes along the cause chain of %s", thrown).contains(errorCode);
		if (expectedPaymentInMessage != null)
		{
			assertThat(messageWithErrorCode)
					.as("message of the refusal with error code %s must name C_Payment.DocumentNo", errorCode)
					.contains(expectedPaymentInMessage.getDocumentNo());
		}

		final I_C_BankStatement reloaded = bankStatementDAO.getById(BankStatementId.ofRepoId(bankStatementRecord.getC_BankStatement_ID()));
		bankStatementTable.putOrReplace(bankStatementIdentifier, reloaded);
		assertThat(reloaded.isProcessed()).as("C_BankStatement.Processed of %s after the refused completion", bankStatementIdentifier).isFalse();
		assertThat(reloaded.getDocStatus()).as("C_BankStatement.DocStatus of %s after the refused completion", bankStatementIdentifier).isNotEqualTo(IDocument.STATUS_Completed);
	}

	/**
	 * Creates a draft bank statement of an org bank account, as a user does in window {@code Bankauszug}; its lines are added with {@code metasfresh contains C_BankStatementLine:}.
	 *
	 * @cucumber.columns
	 * <b>Identifier</b> — (required) alias<br>
	 * <b>C_BP_BankAccount_ID</b> — (required, identifier-ref) org bank account<br>
	 * <b>StatementDate</b> — (required) e.g. {@code 2026-11-03}<br>
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_BankStatement:
	 *   | Identifier | C_BP_BankAccount_ID | StatementDate |
	 *   | bs_1       | org_account         | 2026-11-03    |
	 * </pre>
	 */
	@And("metasfresh contains C_BankStatement:")
	public void create_C_BankStatement(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(BankStatementCreateRequest.builder()
					.orgId(StepDefConstants.ORG_ID)
					.orgBankAccountId(bankAccountTable.getOrgBankAccountId(row.getAsIdentifier(I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID)))
					.statementDate(row.getAsLocalDate(I_C_BankStatement.COLUMNNAME_StatementDate))
					.name(row.getAsOptionalString(I_C_BankStatement.COLUMNNAME_Name).orElseGet(() -> row.suggestValueAndName().getName()))
					.build());
			row.getAsIdentifier().putOrReplace(bankStatementTable, bankStatementDAO.getById(bankStatementId));
		});
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
