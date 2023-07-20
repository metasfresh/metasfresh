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

package de.metas.cucumber.stepdefs.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.bankStatement.C_BankStatementLine_StepDefData;
import de.metas.cucumber.stepdefs.bankStatement.C_BankStatement_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.docType.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_SectionCode;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Payment_ID;
import static org.compiere.model.I_C_Payment.COLUMNNAME_C_DocType_ID;
import static org.compiere.model.I_C_Payment.COLUMNNAME_DateAcct;
import static org.compiere.model.I_C_Payment.COLUMNNAME_DateTrx;
import static org.compiere.model.I_C_Payment.COLUMNNAME_DiscountAmt;
import static org.compiere.model.I_C_Payment.COLUMNNAME_IsAllocated;
import static org.compiere.model.I_C_Payment.COLUMNNAME_IsReceipt;
import static org.compiere.model.I_C_Payment.COLUMNNAME_PayAmt;
import static org.compiere.model.I_C_Payment.COLUMNNAME_WriteOffAmt;

public class C_Payment_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Payment_StepDefData paymentTable;
	private final CurrencyRepository currencyRepository;
	private final C_BP_BankAccount_StepDefData bpBankAccountTable;
	private final M_SectionCode_StepDefData sectionCodeTable;
	private final C_BankStatement_StepDefData bankStatementTable;
	private final C_BankStatementLine_StepDefData bankStatementLineTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_DocType_StepDefData docTypeTable;
	private final TestContext testContext;

	public C_Payment_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final C_Payment_StepDefData paymentTable,
			@NonNull final C_BP_BankAccount_StepDefData bpBankAccountTable,
			@NonNull final M_SectionCode_StepDefData sectionCodeTable,
			@NonNull final C_BankStatement_StepDefData bankStatementTable,
			@NonNull final C_BankStatementLine_StepDefData bankStatementLineTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final TestContext testContext)
	{
		this.bpartnerTable = bpartnerTable;
		this.currencyRepository = currencyRepository;
		this.paymentTable = paymentTable;
		this.bpBankAccountTable = bpBankAccountTable;
		this.sectionCodeTable = sectionCodeTable;
		this.bankStatementTable = bankStatementTable;
		this.bankStatementLineTable = bankStatementLineTable;
		this.invoiceTable = invoiceTable;
		this.docTypeTable = docTypeTable;
		this.testContext = testContext;
	}

	@And("metasfresh contains C_Payment")
	public void addC_Payment(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			create_C_Payment(dataTableRow);
		}
	}

	@And("^the payment identified by (.*) is (completed|reversed)$")
	public void payment_action(@NonNull final String paymentIdentifier, @NonNull final String action)
	{
		final I_C_Payment payment = paymentTable.get(paymentIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case reversed:
				payment.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(payment, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			case completed:
				payment.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(payment, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			default:
				throw new AdempiereException("Unhandled C_Payment action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	@And("validate payments")
	public void validate_created_payments(@NonNull final DataTable table)
	{
		final List<Map<String, String>> rows = table.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			final String paymentIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, COLUMNNAME_C_Payment_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Boolean paymentIsAllocated = DataTableUtil.extractBooleanForColumnName(dataTableRow, COLUMNNAME_C_Payment_ID + "." + COLUMNNAME_IsAllocated);
			final BigDecimal expectedAvailableAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT.OpenAmt");
			final I_C_Payment payment = paymentTable.get(paymentIdentifier);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(payment).isNotNull();
			InterfaceWrapperHelper.refresh(payment);

			final BigDecimal paymentAvailableAmt = paymentDAO.getAvailableAmount(PaymentId.ofRepoId(payment.getC_Payment_ID()));

			softly.assertThat(payment.isAllocated()).isEqualTo(paymentIsAllocated);
			if (expectedAvailableAmt != null)
			{
				softly.assertThat(paymentAvailableAmt).isEqualTo(payment.isReceipt() ? expectedAvailableAmt : expectedAvailableAmt.negate());
			}

			final String sectionCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_C_Dunning_Candidate.COLUMNNAME_M_SectionCode_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(sectionCodeIdentifier))
			{
				final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
				softly.assertThat(payment.getM_SectionCode_ID()).isEqualTo(sectionCode.getM_SectionCode_ID());
			}

			final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_C_Payment.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(invoiceIdentifier))
			{
				final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);
				softly.assertThat(invoiceRecord).isNotNull();
				softly.assertThat(payment.getC_Invoice_ID()).isEqualTo(invoiceRecord.getC_Invoice_ID());
			}

			final LocalDate dateTrx = DataTableUtil.extractLocalDateOrNullForColumnName(dataTableRow, "OPT." + I_C_Payment.COLUMNNAME_DateTrx);
			if (dateTrx != null)
			{
				final OrgId orgId = OrgId.ofRepoId(payment.getAD_Org_ID());
				final ZoneId zoneId = orgDAO.getTimeZone(orgId);
				softly.assertThat(TimeUtil.asLocalDate(payment.getDateTrx(), zoneId)).isEqualTo(dateTrx);
			}

			final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_C_Payment.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bPartnerIdentifier))
			{
				final I_C_BPartner bPartnerRecord = bpartnerTable.get(bPartnerIdentifier);
				softly.assertThat(bPartnerRecord).isNotNull();
				softly.assertThat(payment.getC_BPartner_ID()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
			}

			final String bankAccountIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_C_Payment.COLUMNNAME_C_BP_BankAccount_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bankAccountIdentifier))
			{
				final I_C_BP_BankAccount bankAccountRecord = bpBankAccountTable.get(bankAccountIdentifier);
				softly.assertThat(bankAccountRecord).isNotNull();
				softly.assertThat(payment.getC_BP_BankAccount_ID()).isEqualTo(bankAccountRecord.getC_BP_BankAccount_ID());
			}

			final BigDecimal payAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_PayAmt);
			if (payAmt != null)
			{
				softly.assertThat(payment.getPayAmt()).isEqualByComparingTo(payAmt);
			}

			final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_C_DocType_ID);
			if (Check.isNotBlank(docTypeIdentifier))
			{
				final I_C_DocType docTypeRecord = docTypeTable.get(docTypeIdentifier);

				softly.assertThat(payment.getC_DocType_ID()).isEqualTo(docTypeRecord.getC_DocType_ID());
			}

			final BigDecimal discountAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_DiscountAmt);
			if (discountAmt != null)
			{
				softly.assertThat(payment.getDiscountAmt()).isEqualByComparingTo(discountAmt);
			}

			final BigDecimal writeOffAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_WriteOffAmt);
			if (writeOffAmt != null)
			{
				softly.assertThat(payment.getWriteOffAmt()).isEqualByComparingTo(writeOffAmt);
			}

			softly.assertAll();
		}
	}

	@And("^after not more than (.*)s, C_Payment is found")
	public void find_C_Payment(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			findPayment(timeoutSec, dataTableRow);
		}
	}

	@Then("validate payment api response error message")
	public void validate_payment_api_response_error_message(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String message = DataTableUtil.extractStringForColumnName(row, "JsonErrorItem.message");

			final JsonErrorItem jsonErrorItem = mapper.readValue(testContext.getApiResponse().getContent(), JsonErrorItem.class);
			assertThat(jsonErrorItem.getMessage()).contains(message);
		}
	}
	
	private void findPayment(
			final int timeoutSec,
			@NonNull final Map<String, String> row) throws InterruptedException
	{
		final I_C_Payment paymentRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> load_C_Payment(row));

		final String paymentIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Payment.COLUMNNAME_C_Payment_ID + "." + TABLECOLUMN_IDENTIFIER);
		paymentTable.putOrReplace(paymentIdentifier, paymentRecord);
	}

	private ItemProvider.ProviderResult<I_C_Payment> load_C_Payment(@NonNull final Map<String, String> row)
	{
		final IQueryBuilder<I_C_Payment> queryBuilder = queryBL.createQueryBuilder(I_C_Payment.class)
				.addOnlyActiveRecordsFilter();

		final String bankStatementIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Payment.COLUMNNAME_C_BankStatement_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bankStatementIdentifier))
		{
			final I_C_BankStatement bankStatementRecord = bankStatementTable.get(bankStatementIdentifier);
			assertThat(bankStatementRecord).isNotNull();

			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BankStatement_ID, bankStatementRecord.getC_BankStatement_ID());
		}

		final String bankStatementLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Payment.COLUMNNAME_C_BankStatementLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bankStatementLineIdentifier))
		{
			final I_C_BankStatementLine bankStatementLineRecord = bankStatementLineTable.get(bankStatementLineIdentifier);
			assertThat(bankStatementLineRecord).isNotNull();

			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BankStatementLine_ID, bankStatementLineRecord.getC_BankStatementLine_ID());
		}

		final String externalId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Payment.COLUMNNAME_ExternalId);
		if (Check.isNotBlank(externalId))
		{
			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_ExternalId, externalId);
		}

		final I_C_Payment paymentRecord = queryBuilder
				.create()
				.first(I_C_Payment.class);

		if (paymentRecord == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("I_C_Payment for row=" + row);
		}
		return ItemProvider.ProviderResult.resultWasFound(paymentRecord);
	}

	private void create_C_Payment(@NonNull final Map<String, String> row)
	{
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer bPartnerId = bpartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final BigDecimal paymentAmount = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PayAmt);

		final String currencyISO = DataTableUtil.extractStringForColumnName(row, I_C_Currency.Table_Name + "." + I_C_Currency.COLUMNNAME_ISO_Code);
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyISO));

		final String docTypeName = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_DocType_ID + ".Name");
		final DocTypeId docTypeId = queryBL.createQueryBuilder(I_C_DocType.class)
				.addEqualsFilter(I_C_DocType.COLUMNNAME_Name, docTypeName)
				.orderBy(I_C_DocType.COLUMNNAME_Name)
				.create()
				.firstId(DocTypeId::ofRepoIdOrNull);

		final boolean isReceipt = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsReceipt);

		final String bpBankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BP_BankAccount.Table_Name + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BP_BankAccount bpBankAccount = bpBankAccountTable.get(bpBankAccountIdentifier);

		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);

		payment.setC_BPartner_ID(bPartnerId);
		payment.setPayAmt(paymentAmount);
		payment.setC_Currency_ID(currencyId.getRepoId());
		payment.setC_DocType_ID(docTypeId.getRepoId());
		payment.setC_BP_BankAccount_ID(bpBankAccount.getC_BP_BankAccount_ID());
		payment.setIsReceipt(isReceipt);
		payment.setIsAutoAllocateAvailableAmt(false);

		final Timestamp dateTrx = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_DateTrx);
		payment.setDateTrx(CoalesceUtil.coalesceNotNull(dateTrx, TimeUtil.asTimestamp(LocalDate.now())));

		final Timestamp dateAcct = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_DateAcct);
		payment.setDateAcct(CoalesceUtil.coalesceNotNull(dateAcct, TimeUtil.asTimestamp(LocalDate.now())));

		paymentDAO.save(payment);

		final String paymentIdentifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);
		paymentTable.putOrReplace(paymentIdentifier, payment);
	}
}
