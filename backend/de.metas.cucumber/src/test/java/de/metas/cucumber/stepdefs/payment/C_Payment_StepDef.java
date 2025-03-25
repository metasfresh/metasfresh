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
import de.metas.banking.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
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
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Payment_ID;
import static org.compiere.model.I_C_Payment.COLUMNNAME_C_DocType_ID;
import static org.compiere.model.I_C_Payment.COLUMNNAME_DiscountAmt;
import static org.compiere.model.I_C_Payment.COLUMNNAME_IsAllocated;
import static org.compiere.model.I_C_Payment.COLUMNNAME_IsReceipt;
import static org.compiere.model.I_C_Payment.COLUMNNAME_PayAmt;
import static org.compiere.model.I_C_Payment.COLUMNNAME_WriteOffAmt;

@RequiredArgsConstructor
public class C_Payment_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
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

	@And("metasfresh contains C_Payment")
	public void createPayments(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.forEach(this::createPayment);
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
	public void validateCreatedPayments(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::validateCreatedPayment);
	}

	private void validateCreatedPayment(final DataTableRow row)
	{
		@NonNull final I_C_Payment payment = row.getAsIdentifier(COLUMNNAME_C_Payment_ID).lookupNotNullIn(paymentTable);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(payment).isNotNull();
		InterfaceWrapperHelper.refresh(payment);

		row.getAsOptionalBoolean(COLUMNNAME_C_Payment_ID + "." + COLUMNNAME_IsAllocated)
				.ifUnknown(() -> row.getAsOptionalBoolean(COLUMNNAME_IsAllocated))
				.ifPresent(paymentIsAllocated -> softly.assertThat(payment.isAllocated()).isEqualTo(paymentIsAllocated));

		row.getAsOptionalBigDecimal(COLUMNNAME_PayAmt)
				.ifPresent(payAmt -> softly.assertThat(payment.getPayAmt()).isEqualByComparingTo(payAmt));
		row.getAsOptionalBigDecimal("OpenAmt")
				.ifPresent(expectedAvailableAmt -> {
					final BigDecimal paymentAvailableAmt = paymentDAO.getAvailableAmount(PaymentId.ofRepoId(payment.getC_Payment_ID()));
					softly.assertThat(paymentAvailableAmt).isEqualTo(payment.isReceipt() ? expectedAvailableAmt : expectedAvailableAmt.negate());
				});
		row.getAsOptionalBigDecimal(COLUMNNAME_DiscountAmt)
				.ifPresent(discountAmt -> softly.assertThat(payment.getDiscountAmt()).isEqualByComparingTo(discountAmt));
		row.getAsOptionalBigDecimal(COLUMNNAME_WriteOffAmt)
				.ifPresent(writeOffAmt -> softly.assertThat(payment.getWriteOffAmt()).isEqualByComparingTo(writeOffAmt));

		row.getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_Invoice_ID)
				.map(invoiceTable::getId)
				.ifPresent(expectedInvoiceId -> softly.assertThat(payment.getC_Invoice_ID()).isEqualTo(expectedInvoiceId.getRepoId()));

		row.getAsOptionalLocalDate(I_C_Payment.COLUMNNAME_DateTrx)
				.ifPresent(dateTrx -> {
					final OrgId orgId = OrgId.ofRepoId(payment.getAD_Org_ID());
					final ZoneId zoneId = orgDAO.getTimeZone(orgId);
					softly.assertThat(TimeUtil.asLocalDate(payment.getDateTrx(), zoneId)).isEqualTo(dateTrx);
				});

		row.getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(expectedBPartnerId -> softly.assertThat(payment.getC_BPartner_ID()).isEqualTo(expectedBPartnerId.getRepoId()));

		row.getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_BP_BankAccount_ID)
				.map(bpBankAccountTable::getOrgBankAccountId)
				.ifPresent(expectedBankAccountId -> softly.assertThat(payment.getC_BP_BankAccount_ID()).isEqualTo(expectedBankAccountId.getRepoId()));

		row.getAsOptionalIdentifier(COLUMNNAME_C_DocType_ID)
				.map(docTypeTable::getId)
				.ifPresent(expectedDocTypeId -> softly.assertThat(payment.getC_DocType_ID()).isEqualTo(expectedDocTypeId.getRepoId()));

		row.getAsOptionalIdentifier(I_C_Dunning_Candidate.COLUMNNAME_M_SectionCode_ID)
				.map(sectionCodeTable::getId)
				.ifPresent(expectedSectionCodeId -> softly.assertThat(payment.getM_SectionCode_ID()).isEqualTo(expectedSectionCodeId.getRepoId()));

		softly.assertAll();
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

	private void createPayment(@NonNull final DataTableRow row)
	{
		final OrgId orgId = Env.getOrgId();
		final BPartnerId bpartnerId = row.getAsIdentifier(COLUMNNAME_C_BPartner_ID).lookupNotNullIdIn(bpartnerTable);

		final Money payAmt = row.getAsMoney(COLUMNNAME_PayAmt, currencyRepository::getCurrencyIdByCurrencyCode);
		final Money discountAmt = row.getAsOptionalMoney(COLUMNNAME_DiscountAmt, currencyRepository::getCurrencyIdByCurrencyCode).orElse(null);
		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(payAmt, discountAmt);
		final boolean isReceipt = row.getAsBoolean(COLUMNNAME_IsReceipt);
		final LocalDate dateTrx = row.getAsOptionalLocalDate(I_C_Payment.COLUMNNAME_DateTrx).orElseGet(SystemTime::asLocalDate);
		final LocalDate dateAcct = row.getAsOptionalLocalDate(I_C_Payment.COLUMNNAME_DateAcct).orElse(dateTrx);

		final BankAccountId orgBankAccountId = row.getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_BP_BankAccount_ID)
				.map(bpBankAccountTable::getOrgBankAccountId)
				.orElseGet(() -> retrieveDefaultOrgBankAccountId(orgId, payAmt.getCurrencyId()));

		final InvoiceId invoiceId = row.getAsOptionalIdentifier(I_C_Payment.COLUMNNAME_C_Invoice_ID)
				.map(invoiceTable::getId)
				.orElse(null);

		final I_C_Payment payment = (isReceipt ? paymentBL.newInboundReceiptBuilder() : paymentBL.newOutboundPaymentBuilder())
				.adOrgId(orgId)
				.bpartnerId(bpartnerId)
				.orgBankAccountId(orgBankAccountId)
				.currencyId(currencyId)
				.payAmt(payAmt.toBigDecimal())
				.discountAmt(discountAmt != null ? discountAmt.toBigDecimal() : null)
				.dateTrx(dateTrx)
				.dateAcct(dateAcct)
				.invoiceId(invoiceId)
				.isAutoAllocateAvailableAmt(false)
				.createDraft();

		row.getAsOptionalIdentifier().ifPresent(identifier -> paymentTable.put(identifier, payment));
	}

	@NonNull
	public BankAccountId retrieveDefaultOrgBankAccountId(
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyId)
	{
		final BPartnerId orgBPartnerId = bpartnerOrgBL.retrieveLinkedBPartnerId(orgId)
				.orElseThrow(() -> new AdempiereException("No linked BPartner found for " + orgId));

		return bankAccountDAO.retrieveBankAccountsForPartnerAndCurrency(orgBPartnerId, currencyId)
				.stream()
				.min(Comparator.comparing(BPartnerBankAccount::isDefault).reversed()
						.thenComparing(BPartnerBankAccount::getIdNotNull))
				.map(bankAccount -> BankAccountId.ofRepoId(bankAccount.getIdNotNull().getRepoId()))
				.orElseThrow(() -> new AdempiereException("No C_BP_BankAccount found for " + orgBPartnerId + " and " + currencyId));
	}

}
