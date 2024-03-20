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

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_BankStatementLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_BankStatement_StepDefData bankStatementTable;
	private final C_BankStatementLine_StepDefData bankStatementLineTable;
	private final C_BPartner_StepDefData partnerTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_Payment_StepDefData paymentTable;

	public C_BankStatementLine_StepDef(
			@NonNull final C_BankStatement_StepDefData bankStatementTable,
			@NonNull final C_BankStatementLine_StepDefData bankStatementLineTable,
			@NonNull final C_BPartner_StepDefData partnerTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Payment_StepDefData paymentTable)
	{
		this.bankStatementTable = bankStatementTable;
		this.bankStatementLineTable = bankStatementLineTable;
		this.partnerTable = partnerTable;
		this.invoiceTable = invoiceTable;
		this.paymentTable = paymentTable;
	}

	@And("load C_BankStatementLine")
	public void load_C_BankStatementLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			loadBankStatementLine(row);
		}
	}

	@And("validate C_BankStatementLine")
	public void validate_C_BankStatementLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			validateBankStatementLine(row);
		}
	}

	private void loadBankStatementLine(@NonNull final Map<String, String> row)
	{
		final String bankStatementIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_BankStatement bankStatementRecord = bankStatementTable.get(bankStatementIdentifier);
		assertThat(bankStatementRecord).isNotNull();

		final int line = DataTableUtil.extractIntForColumnName(row, I_C_BankStatementLine.COLUMNNAME_Line);

		final I_C_BankStatementLine bankStatementLineRecord = getBankStatementLineRecord(bankStatementRecord, line);
		final String bankStatementLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BankStatementLine.COLUMNNAME_C_BankStatementLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		bankStatementLineTable.putOrReplace(bankStatementLineIdentifier, bankStatementLineRecord);
	}

	private void validateBankStatementLine(@NonNull final Map<String, String> row)
	{
		final String bslIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BankStatementLine.COLUMNNAME_C_BankStatementLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BankStatementLine bankStatementLineRecord = bankStatementLineTable.get(bslIdentifier);
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(bankStatementLineRecord).isNotNull();
		InterfaceWrapperHelper.refresh(bankStatementLineRecord);

		final OrgId orgId = OrgId.ofRepoId(bankStatementLineRecord.getAD_Org_ID());
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);
		final LocalDate valutaDate = DataTableUtil.extractLocalDateOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_ValutaDate);
		if (valutaDate != null)
		{
			softly.assertThat(TimeUtil.asLocalDate(bankStatementLineRecord.getValutaDate(), zoneId)).as("C_BankStatementLine_ID.Identifier=%s - ValutaDate", bslIdentifier).isEqualTo(valutaDate);
		}

		final LocalDate dateAcct = DataTableUtil.extractLocalDateOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_DateAcct);
		if (dateAcct != null)
		{
			softly.assertThat(TimeUtil.asLocalDate(bankStatementLineRecord.getDateAcct(), zoneId)).as("C_BankStatementLine_ID.Identifier=%s - DateAcct", bslIdentifier).isEqualTo(dateAcct);
		}

		final String currencyIsoCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_C_Currency_ID + "." + "ISO_Code");
		if (Check.isNotBlank(currencyIsoCode))
		{
			final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(currencyIsoCode);
			final Currency currency = currencyDAO.getByCurrencyCode(currencyCode);
			softly.assertThat(bankStatementLineRecord.getC_Currency_ID()).as("C_BankStatementLine_ID.Identifier=%s - C_Currency_ID", bslIdentifier).isEqualTo(currency.getId().getRepoId());
		}

		final BigDecimal trxAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_TrxAmt);
		if (trxAmt != null)
		{
			softly.assertThat(bankStatementLineRecord.getTrxAmt()).as("C_BankStatementLine_ID.Identifier=%s - TrxAmt", bslIdentifier).isEqualByComparingTo(trxAmt);
		}

		final BigDecimal stmtAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_StmtAmt);
		if (stmtAmt != null)
		{
			softly.assertThat(bankStatementLineRecord.getStmtAmt()).as("C_BankStatementLine_ID.Identifier=%s - StmtAmt", bslIdentifier).isEqualByComparingTo(stmtAmt);
		}

		final LocalDate statementLineDate = DataTableUtil.extractLocalDateOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_StatementLineDate);
		if (statementLineDate != null)
		{
			softly.assertThat(TimeUtil.asLocalDate(bankStatementLineRecord.getStatementLineDate(), zoneId)).as("C_BankStatementLine_ID.Identifier=%s - StatementLineDate", bslIdentifier).isEqualTo(statementLineDate);
		}

		final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bPartnerIdentifier))
		{
			final I_C_BPartner bPartnerRecord = partnerTable.get(bPartnerIdentifier);
			softly.assertThat(bPartnerRecord).as("C_BankStatementLine_ID.Identifier=%s - C_BPartner record for identifier=%s", bslIdentifier, bPartnerIdentifier).isNotNull();

			softly.assertThat(bankStatementLineRecord.getC_BPartner_ID()).as("C_BankStatementLine_ID.Identifier=%s - C_BPartner_ID for identifier=%s", bslIdentifier, bPartnerIdentifier).isEqualTo(bPartnerRecord.getC_BPartner_ID());
		}

		final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(invoiceIdentifier))
		{
			final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);
			softly.assertThat(invoiceRecord).as("C_BankStatementLine_ID.Identifier=%s - C_Invoice record %s for identifier=%s ", bslIdentifier, invoiceRecord,invoiceIdentifier).isNotNull();

			softly.assertThat(bankStatementLineRecord.getC_Invoice_ID()).as("C_BankStatementLine_ID.Identifier=%s - C_Invoice_ID for identifier=%s", bslIdentifier, invoiceIdentifier).isEqualTo(invoiceRecord.getC_Invoice_ID());
		}

		final boolean isProcessed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_Processed, false);
		softly.assertThat(bankStatementLineRecord.isProcessed()).as("C_BankStatementLine_ID.Identifier=%s - Processed", bslIdentifier).isEqualTo(isProcessed);

		final boolean isReconciled = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_IsReconciled, false);
		softly.assertThat(bankStatementLineRecord.isReconciled()).as("C_BankStatementLine_ID.Identifier=%s - IsReconciled", bslIdentifier).isEqualTo(isReconciled);

		final String paymentIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BankStatementLine.COLUMNNAME_C_Payment_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(paymentIdentifier))
		{
			final I_C_Payment paymentRecord = paymentTable.get(paymentIdentifier);
			softly.assertThat(paymentRecord).as("C_BankStatementLine_ID.Identifier=%s - C_Payment record for identifier=%s", bslIdentifier, paymentIdentifier).isNotNull();

			softly.assertThat(bankStatementLineRecord.getC_Payment_ID()).as("C_BankStatementLine_ID.Identifier=%s - C_Payment_ID for identifier=%s", bslIdentifier, paymentIdentifier).isEqualTo(paymentRecord.getC_Payment_ID());
		}

		softly.assertAll();
	}

	@NonNull
	private I_C_BankStatementLine getBankStatementLineRecord(
			@NonNull final I_C_BankStatement bankStatementRecord,
			final int line)
	{
		return queryBL.createQueryBuilder(I_C_BankStatementLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID, bankStatementRecord.getC_BankStatement_ID())
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_Line, line)
				.create()
				.firstOnlyNotNull(I_C_BankStatementLine.class);
	}
}
