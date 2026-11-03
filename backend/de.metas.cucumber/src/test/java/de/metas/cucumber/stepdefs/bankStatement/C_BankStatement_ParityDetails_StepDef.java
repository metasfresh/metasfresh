/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider.ProviderResult;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Self-contained assertions for the camt.053 import "parity" scenarios: they check an imported (and completed)
 * statement and every one of its lines, including the fields the older line step does not cover
 * (imported partner name, reference, payment) and the matching outcome (invoice, partner, payment, "Abgeglichen").
 * <p>
 * Kept in its own class so it does not depend on which columns the other bank statement steps support on a given branch.
 */
public class C_BankStatement_ParityDetails_StepDef
{
	/**
	 * Table cell that means "this field must be empty / not set"; an empty cell means "not checked".
	 */
	private static final String NONE = "<none>";

	/**
	 * A matched payment can be created slightly after the statement is completed, so a mismatching line is re-read for this long before it is reported.
	 */
	private static final int RETRY_TIMEOUT_SECONDS = 30;
	private static final long RETRY_INTERVAL_MILLIS = 500;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_BankStatement_StepDefData bankStatementTable;

	public C_BankStatement_ParityDetails_StepDef(@NonNull final C_BankStatement_StepDefData bankStatementTable)
	{
		this.bankStatementTable = bankStatementTable;
	}

	/**
	 * <pre>{@code
	 * And validate imported C_BankStatement
	 *   | C_BankStatement_ID.Identifier | LineCount | BeginningBalance | EndingBalance | StatementDifference | DocStatus | IsReconciled | StatementDate |
	 *   | bs1                           | 5         | 1000.00          | 85810.75      | 84810.75            | CO        | false        | 2026-10-16    |
	 * }</pre>
	 * Every column except the identifier is optional.
	 */
	@And("validate imported C_BankStatement")
	public void validate_imported_C_BankStatement(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			validateStatement(row);
		}
	}

	/**
	 * <pre>{@code
	 * And validate imported C_BankStatementLine
	 *   | C_BankStatement_ID.Identifier | Line | TrxAmt | StmtAmt | StatementLineDate | ImportedBillPartnerName | ReferenceNo | C_Invoice.DocumentNo | C_BPartner.Name | C_Payment.PayAmt | IsReconciled |
	 *   | bs1                           | 40   | 300    | 300     | 2026-10-16        | Muster Hans             | TEST-4      | G170311              | B21_Customer    | 300              | true         |
	 *   | bs1                           | 10   | 3243   | 3243    | 2026-10-16        | Muster Debitor 1 AG     | TEST-1      | <none>               | <none>          | <none>           | false        |
	 * }</pre>
	 * Every column except the identifier and the line number is optional; {@code <none>} asserts that the field is empty.
	 */
	@And("validate imported C_BankStatementLine")
	public void validate_imported_C_BankStatementLine(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			validateLineWithRetry(row);
		}
	}

	private void validateStatement(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_BankStatement.COLUMNNAME_C_BankStatement_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BankStatement statement = bankStatementTable.get(identifier);
		assertThat(statement).as("C_BankStatement for identifier=%s", identifier).isNotNull();
		InterfaceWrapperHelper.refresh(statement);

		final SoftAssertions softly = new SoftAssertions();

		final Integer lineCount = DataTableUtil.extractIntegerOrNullForColumnName(row, "LineCount");
		if (lineCount != null)
		{
			final int actualLineCount = queryBL.createQueryBuilder(I_C_BankStatementLine.class)
					.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID, statement.getC_BankStatement_ID())
					.create()
					.count();
			softly.assertThat(actualLineCount).as("%s - number of lines", identifier).isEqualTo(lineCount);
		}

		assertDecimal(softly, identifier, "BeginningBalance", statement.getBeginningBalance(), row.get("BeginningBalance"));
		assertDecimal(softly, identifier, "EndingBalance", statement.getEndingBalance(), row.get("EndingBalance"));
		assertDecimal(softly, identifier, "StatementDifference", statement.getStatementDifference(), row.get("StatementDifference"));

		final String docStatus = row.get("DocStatus");
		if (Check.isNotBlank(docStatus))
		{
			softly.assertThat(statement.getDocStatus()).as("%s - DocStatus", identifier).isEqualTo(docStatus);
		}

		final String isReconciled = row.get("IsReconciled");
		if (Check.isNotBlank(isReconciled))
		{
			softly.assertThat(statement.isReconciled()).as("%s - IsReconciled", identifier).isEqualTo(Boolean.parseBoolean(isReconciled));
		}

		final LocalDate statementDate = DataTableUtil.extractLocalDateOrNullForColumnName(row, "StatementDate");
		if (statementDate != null)
		{
			final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(statement.getAD_Org_ID()));
			softly.assertThat(TimeUtil.asLocalDate(statement.getStatementDate(), zoneId)).as("%s - StatementDate", identifier).isEqualTo(statementDate);
		}

		softly.assertAll();
	}

	private void validateLineWithRetry(@NonNull final Map<String, String> row) throws InterruptedException
	{
		StepDefUtil.<Boolean>tryAndWaitForItem()
				.maxWaitSeconds(RETRY_TIMEOUT_SECONDS)
				.checkingIntervalMs(RETRY_INTERVAL_MILLIS)
				.worker(() -> {
					try
					{
						final SoftAssertions softly = new SoftAssertions();
						validateLine(softly, row);
						softly.assertAll();
						return ProviderResult.resultWasFound(true);
					}
					catch (final AssertionError e)
					{
						return ProviderResult.resultWasNotFound(e);
					}
				})
				.execute();
	}

	private void validateLine(@NonNull final SoftAssertions softly, @NonNull final Map<String, String> row)
	{
		final String statementIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int lineNo = DataTableUtil.extractIntForColumnName(row, I_C_BankStatementLine.COLUMNNAME_Line);
		final String what = statementIdentifier + " line " + lineNo;

		final I_C_BankStatement statement = bankStatementTable.get(statementIdentifier);
		assertThat(statement).as("C_BankStatement for identifier=%s", statementIdentifier).isNotNull();

		final I_C_BankStatementLine line = queryBL.createQueryBuilder(I_C_BankStatementLine.class)
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID, statement.getC_BankStatement_ID())
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_Line, lineNo)
				.create()
				.firstOnly(I_C_BankStatementLine.class);
		assertThat(line).as("%s exists", what).isNotNull();
		InterfaceWrapperHelper.refresh(line);

		assertDecimal(softly, what, "TrxAmt", line.getTrxAmt(), row.get("TrxAmt"));
		assertDecimal(softly, what, "StmtAmt", line.getStmtAmt(), row.get("StmtAmt"));

		final LocalDate statementLineDate = DataTableUtil.extractLocalDateOrNullForColumnName(row, "StatementLineDate");
		if (statementLineDate != null)
		{
			final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(line.getAD_Org_ID()));
			softly.assertThat(TimeUtil.asLocalDate(line.getStatementLineDate(), zoneId)).as("%s - StatementLineDate", what).isEqualTo(statementLineDate);
		}

		assertText(softly, what, "ImportedBillPartnerName", line.getImportedBillPartnerName(), row.get("ImportedBillPartnerName"));
		assertText(softly, what, "ReferenceNo", line.getReferenceNo(), row.get("ReferenceNo"));

		final I_C_Invoice invoice = line.getC_Invoice_ID() > 0 ? InterfaceWrapperHelper.load(line.getC_Invoice_ID(), I_C_Invoice.class) : null;
		assertText(softly, what, "C_Invoice.DocumentNo", invoice != null ? invoice.getDocumentNo() : null, row.get("C_Invoice.DocumentNo"));

		final I_C_BPartner partner = line.getC_BPartner_ID() > 0 ? InterfaceWrapperHelper.load(line.getC_BPartner_ID(), I_C_BPartner.class) : null;
		assertText(softly, what, "C_BPartner.Name", partner != null ? partner.getName() : null, row.get("C_BPartner.Name"));

		final I_C_Payment payment = line.getC_Payment_ID() > 0 ? InterfaceWrapperHelper.load(line.getC_Payment_ID(), I_C_Payment.class) : null;
		assertDecimal(softly, what, "C_Payment.PayAmt", payment != null ? payment.getPayAmt() : null, row.get("C_Payment.PayAmt"));

		final String isReconciled = row.get("IsReconciled");
		if (Check.isNotBlank(isReconciled))
		{
			softly.assertThat(line.isReconciled()).as("%s - IsReconciled", what).isEqualTo(Boolean.parseBoolean(isReconciled));
		}
	}

	private static void assertText(
			@NonNull final SoftAssertions softly,
			@NonNull final String what,
			@NonNull final String column,
			@Nullable final String actual,
			@Nullable final String expectedCell)
	{
		if (Check.isBlank(expectedCell))
		{
			return; // not checked
		}
		if (NONE.equals(expectedCell))
		{
			softly.assertThat(Check.isBlank(actual) ? null : actual).as("%s - %s", what, column).isNull();
		}
		else
		{
			softly.assertThat(actual).as("%s - %s", what, column).isEqualTo(expectedCell);
		}
	}

	private static void assertDecimal(
			@NonNull final SoftAssertions softly,
			@NonNull final String what,
			@NonNull final String column,
			@Nullable final BigDecimal actual,
			@Nullable final String expectedCell)
	{
		if (Check.isBlank(expectedCell))
		{
			return; // not checked
		}
		if (NONE.equals(expectedCell))
		{
			softly.assertThat(actual).as("%s - %s", what, column).isNull();
		}
		else
		{
			softly.assertThat(actual).as("%s - %s", what, column).isNotNull();
			if (actual != null)
			{
				softly.assertThat(actual).as("%s - %s", what, column).isEqualByComparingTo(new BigDecimal(expectedCell));
			}
		}
	}
}
