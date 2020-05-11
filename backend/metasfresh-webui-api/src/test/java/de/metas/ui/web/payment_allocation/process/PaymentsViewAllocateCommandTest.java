package de.metas.ui.web.payment_allocation.process;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfigRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class PaymentsViewAllocateCommandTest
{

	private static final OrgId orgId = OrgId.ofRepoId(1);
	private static final LocalDate dateInvoiced = LocalDate.of(2020, Month.APRIL, 1);
	private static final LocalDate paymentDateTrx = LocalDate.of(2020, Month.APRIL, 25);

	private MoneyService moneyService;
	private InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;

	private CurrencyId euroCurrencyId;
	private BPartnerId bpartnerId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		moneyService = new MoneyService(new CurrencyRepository());
		invoiceProcessingServiceCompanyService = new InvoiceProcessingServiceCompanyService(
				new InvoiceProcessingServiceCompanyConfigRepository(),
				moneyService);

		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		bpartnerId = createBPartnerId();
	}

	private BPartnerId createBPartnerId()
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);
		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	private Amount euro(final int amount)
	{
		return Amount.of(amount, CurrencyCode.EUR);
	}

	private TableRecordReference toRecordRef(final PaymentRow paymentRow)
	{
		return TableRecordReference.of(I_C_Payment.Table_Name, paymentRow.getPaymentId());
	}

	private TableRecordReference toRecordRef(final InvoiceRow invoiceRow)
	{
		return TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRow.getInvoiceId());
	}

	@Builder(builderMethodName = "paymentRow", builderClassName = "PaymentRowBuilder")
	private PaymentRow createPaymentRow(
			@NonNull final PaymentDirection direction,
			@NonNull final Amount payAmt)
	{
		final I_C_Payment paymentRecord = newInstance(I_C_Payment.class);
		saveRecord(paymentRecord);
		final PaymentId paymentId = PaymentId.ofRepoId(paymentRecord.getC_Payment_ID());

		return PaymentRow.builder()
				.paymentId(paymentId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId))
				.documentNo("paymentNo_" + paymentId.getRepoId())
				.dateTrx(paymentDateTrx)
				.bpartner(IntegerLookupValue.of(bpartnerId.getRepoId(), "BPartner"))
				.payAmt(payAmt)
				.openAmt(payAmt)
				.paymentDirection(direction)
				.build();
	}

	@Builder(builderMethodName = "invoiceRow", builderClassName = "InvoiceRowBuilder")
	private InvoiceRow createInvoiceRow(
			@NonNull final Amount openAmt,
			@NonNull final InvoiceDocBaseType docBaseType)
	{
		final I_C_Invoice invoiceRecord = newInstance(I_C_Invoice.class);
		saveRecord(invoiceRecord);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());

		return InvoiceRow.builder()
				.invoiceId(invoiceId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId))
				.docTypeName(TranslatableStrings.anyLanguage("invoice doc type"))
				.documentNo("invoiceNo_" + invoiceId.getRepoId())
				.dateInvoiced(dateInvoiced)
				.bpartner(IntegerLookupValue.of(bpartnerId.getRepoId(), "BPartner"))
				.docBaseType(docBaseType)
				.grandTotal(openAmt)
				.openAmt(openAmt)
				.discountAmt(Amount.zero(openAmt.getCurrencyCode()))
				.build();
	}

	@Test
	public void singleInvoice_to_singlePayment()
	{
		final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(100)).build();
		final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();

		final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
				.moneyService(moneyService)
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.paymentRow(paymentRow)
				.invoiceRow(invoiceRow)
				.allowPurchaseSalesInvoiceCompensation(false)
				.dateTrx(LocalDate.parse("2020-04-30"))
				.build()
				.run();

		assertThat(result.isOK()).isTrue();
		assertThat(result.getCandidates()).hasSize(1);
		assertThat(result.getCandidates().get(0))
				.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InvoiceToPayment)
						.orgId(orgId)
						.bpartnerId(bpartnerId)
						.paymentDocumentRef(toRecordRef(paymentRow))
						.payableDocumentRef(toRecordRef(invoiceRow))
						.dateTrx(LocalDate.parse("2020-04-30"))
						.dateAcct(LocalDate.parse("2020-04-30"))
						.amounts(AllocationAmounts.builder()
								.payAmt(Money.of("100", euroCurrencyId))
								.build())
						.build());
	}

	@Test
	public void invoice_to_creditMemo()
	{
		final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();
		final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).build();

		final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
				.moneyService(moneyService)
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.invoiceRow(invoiceRow)
				.invoiceRow(creditMemoRow)
				.allowPurchaseSalesInvoiceCompensation(false)
				.dateTrx(LocalDate.parse("2020-04-30"))
				.build()
				.run();

		System.out.println(result);

		assertThat(result.isOK()).isTrue();
		assertThat(result.getCandidates()).hasSize(1);
		assertThat(result.getCandidates().get(0))
				.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InvoiceToCreditMemo)
						.orgId(orgId)
						.bpartnerId(bpartnerId)
						.paymentDocumentRef(toRecordRef(creditMemoRow))
						.payableDocumentRef(toRecordRef(invoiceRow))
						.dateTrx(LocalDate.parse("2020-04-30"))
						.dateAcct(LocalDate.parse("2020-04-30"))
						.amounts(AllocationAmounts.builder()
								.payAmt(Money.of("20", euroCurrencyId))
								.build())
						.payableOverUnderAmt(Money.of("80", euroCurrencyId))
						.build());
	}

	@Test
	public void invoice_creditMemo_and_payment()
	{
		final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(80)).build();
		final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();
		final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).build();

		final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
				.moneyService(moneyService)
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.paymentRow(paymentRow)
				.invoiceRow(invoiceRow)
				.invoiceRow(creditMemoRow)
				.allowPurchaseSalesInvoiceCompensation(false)
				.dateTrx(LocalDate.parse("2020-04-30"))
				.build()
				.run();

		System.out.println(result);

		assertThat(result.isOK()).isTrue();
		assertThat(result.getCandidates()).hasSize(2);
		assertThat(result.getCandidates().get(0))
				.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InvoiceToCreditMemo)
						.orgId(orgId)
						.bpartnerId(bpartnerId)
						.paymentDocumentRef(toRecordRef(creditMemoRow))
						.payableDocumentRef(toRecordRef(invoiceRow))
						.dateTrx(LocalDate.parse("2020-04-30"))
						.dateAcct(LocalDate.parse("2020-04-30"))
						.amounts(AllocationAmounts.builder()
								.payAmt(Money.of("20", euroCurrencyId))
								.build())
						.payableOverUnderAmt(Money.of("80", euroCurrencyId))
						.build());
		assertThat(result.getCandidates().get(1))
				.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InvoiceToPayment)
						.orgId(orgId)
						.bpartnerId(bpartnerId)
						.paymentDocumentRef(toRecordRef(paymentRow))
						.payableDocumentRef(toRecordRef(invoiceRow))
						.dateTrx(LocalDate.parse("2020-04-30"))
						.dateAcct(LocalDate.parse("2020-04-30"))
						.amounts(AllocationAmounts.builder()
								.payAmt(Money.of("80", euroCurrencyId))
								.build())
						.build());
	}

	@Test
	public void invoice_creditMemo_and_payment_partial()
	{
		final PaymentRow paymentRow = paymentRow().direction(PaymentDirection.INBOUND).payAmt(euro(200)).build();
		final InvoiceRow invoiceRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerInvoice).openAmt(euro(100)).build();
		final InvoiceRow creditMemoRow = invoiceRow().docBaseType(InvoiceDocBaseType.CustomerCreditMemo).openAmt(euro(-20)).build();

		final PaymentAllocationResult result = PaymentsViewAllocateCommand.builder()
				.moneyService(moneyService)
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.paymentRow(paymentRow)
				.invoiceRow(invoiceRow)
				.invoiceRow(creditMemoRow)
				.allowPurchaseSalesInvoiceCompensation(false)
				.dateTrx(LocalDate.parse("2020-04-30"))
				.build()
				.run();

		System.out.println(result);

		assertThat(result.isOK()).isTrue();
		assertThat(result.getCandidates()).hasSize(2);
		assertThat(result.getCandidates().get(0))
				.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InvoiceToCreditMemo)
						.orgId(orgId)
						.bpartnerId(bpartnerId)
						.paymentDocumentRef(toRecordRef(creditMemoRow))
						.payableDocumentRef(toRecordRef(invoiceRow))
						.dateTrx(LocalDate.parse("2020-04-30"))
						.dateAcct(LocalDate.parse("2020-04-30"))
						.amounts(AllocationAmounts.builder()
								.payAmt(Money.of("20", euroCurrencyId))
								.build())
						.payableOverUnderAmt(Money.of("80", euroCurrencyId))
						.build());
		assertThat(result.getCandidates().get(1))
				.isEqualToComparingFieldByField(AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InvoiceToPayment)
						.orgId(orgId)
						.bpartnerId(bpartnerId)
						.paymentDocumentRef(toRecordRef(paymentRow))
						.payableDocumentRef(toRecordRef(invoiceRow))
						.dateTrx(LocalDate.parse("2020-04-30"))
						.dateAcct(LocalDate.parse("2020-04-30"))
						.amounts(AllocationAmounts.builder()
								.payAmt(Money.of("80", euroCurrencyId))
								.build())
						.paymentOverUnderAmt(Money.of("120", euroCurrencyId))
						.build());
	}
}
