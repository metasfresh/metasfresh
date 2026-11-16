package de.metas.payment.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentbonus.PaymentBonusCreditMemoService;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionLine;
import de.metas.invoice.paymentbonus.PaymentBonusOnPaymentService;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentReconcileReference;
import de.metas.payment.api.PaymentReconcileRequest;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Payment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

@ExtendWith(AdempiereTestWatcher.class)
public class PaymentBLTest
{
	private PaymentBL paymentBL;

	private PlainCurrencyDAO currencyDAO;
	private CurrencyId currencyEUR;
	private CurrencyId currencyCHF;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		paymentBL = (PaymentBL)Services.get(IPaymentBL.class);

		currencyDAO = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
		currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		currencyCHF = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

		final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();
		createClientInfo(acctSchemaId);
	}

	private void createClientInfo(@NonNull final AcctSchemaId acctSchemaId)
	{
		final I_AD_ClientInfo clientInfo = newInstance(I_AD_ClientInfo.class);
		clientInfo.setC_AcctSchema1_ID(acctSchemaId.getRepoId());
		save(clientInfo);
	}

	@Nested
	public class updateAmounts
	{
		private I_C_Order order;
		private I_C_Invoice invoice;

		@BeforeEach
		public void beforeEach()
		{
			PaymentBonusOnPaymentService.newInstanceForUnitTesting(); // no payment bonus deduction for these payments

			order = newInstance(I_C_Order.class);
			order.setAD_Org_ID(1);
			order.setC_Currency_ID(currencyEUR.getRepoId());
			order.setGrandTotal(new BigDecimal("50.0"));
			order.setIsSOTrx(true);
			order.setProcessed(true);
			saveRecord(order);

			final InvoiceDocBaseType docBaseType = InvoiceDocBaseType.CustomerInvoice;
			final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
			docType.setDocBaseType(docBaseType.getCode());
			docType.setIsSOTrx(docBaseType.isSales());
			saveRecord(docType);

			invoice = newInstance(I_C_Invoice.class);
			invoice.setAD_Org_ID(1);
			invoice.setC_Currency_ID(currencyEUR.getRepoId());
			invoice.setGrandTotal(new BigDecimal("50.0"));
			invoice.setC_DocType_ID(docType.getC_DocType_ID());
			invoice.setIsSOTrx(docType.isSOTrx());
			invoice.setProcessed(true);
			invoice.setIsFinancial(InvoiceDocBaseType.ofCode(docType.getDocBaseType()).isFinancial());
			saveRecord(invoice);
		}

		private List<I_C_Invoice> getAllInvoices()
		{
			final POJOLookupMap db = POJOLookupMap.get();
			return db.getRecords(I_C_Invoice.class);
		}

		@Test
		public void standardCases()
		{
			final PaymentBL paymentBL = new PaymentBL(); // the class under test
			final Timestamp today = SystemTime.asDayTimestamp();
			currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal("2.0"));
			currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal("0.5"));

			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setDateTrx(today); // needed for default C_ConversionType_ID retrieval
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);
			saveRecord(payment);

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_DocType_ID, /* creditMemoAdjusted */false);

			Assertions.assertEquals(BigDecimal.ZERO, payment.getOverUnderAmt(), "Incorrect over/under amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getDiscountAmt(), "Incorrect discount amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getWriteOffAmt(), "Incorrect writteoff amount");

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_DocType_ID, /* creditMemoAdjusted */false);

			Assertions.assertEquals(BigDecimal.ZERO, payment.getOverUnderAmt(), "Incorrect over/under amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getDiscountAmt(), "Incorrect discount amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getWriteOffAmt(), "Incorrect writeoff amount");

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assertions.assertEquals(BigDecimal.ZERO, payment.getOverUnderAmt(), "Incorrect over/under amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getDiscountAmt(), "Incorrect discount amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getWriteOffAmt(), "Incorrect writteoff amount");
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			saveRecord(payment);

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.onCurrencyChange(payment);

			Assertions.assertEquals(BigDecimal.ZERO, payment.getOverUnderAmt(), "Incorrect over/under amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getDiscountAmt(), "Incorrect discount amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getWriteOffAmt(), "Incorrect writteoff amount");

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_ConversionType_ID, /* creditMemoAdjusted */false);

			Assertions.assertEquals(BigDecimal.ZERO, payment.getOverUnderAmt(), "Incorrect over/under amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getDiscountAmt(), "Incorrect discount amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getWriteOffAmt(), "Incorrect writeoff amount");

			Assertions.assertEquals(BigDecimal.ZERO, payment.getOverUnderAmt(), "Incorrect over/under amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getDiscountAmt(), "Incorrect discount amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getWriteOffAmt(), "Incorrect writeoff amount");

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assertions.assertEquals(BigDecimal.ZERO, payment.getOverUnderAmt(), "Incorrect over/under amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getDiscountAmt(), "Incorrect discount amount");
			Assertions.assertEquals(BigDecimal.ZERO, payment.getWriteOffAmt(), "Incorrect writteoff amount");
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setC_Invoice_ID(getAllInvoices().get(0).getC_Invoice_ID());
			invoice.setC_Currency_ID(currencyCHF.getRepoId());

			saveRecord(invoice);
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assertions.assertEquals(0, payment.getOverUnderAmt().signum(), "Incorrect over/under amount");
			Assertions.assertEquals(0, payment.getDiscountAmt().signum(), "Incorrect discount amount");
			Assertions.assertEquals(0, payment.getWriteOffAmt().signum(), "Incorrect writteoff amount");

			payment.setAD_Org_ID(1);
			// NOTE: Trick to reset the cached invoice, else payment.getC_Invoice() will return the cached invoice
			// and not "invoice" which we will modify below
			payment.setC_Invoice_ID(-1);
			payment.setC_Invoice_ID(getAllInvoices().get(0).getC_Invoice_ID());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setDiscountAmt(BigDecimal.ZERO);
			payment.setWriteOffAmt(BigDecimal.ZERO);
			payment.setOverUnderAmt(BigDecimal.ZERO);
			payment.setDocStatus(DocStatus.Drafted.getCode());
			payment.setDocAction(X_C_Payment.DOCACTION_Complete);
			saveRecord(payment);

			// Test writeoff completion
			payment.setPayAmt(new BigDecimal("40.0"));
			saveRecord(payment);
			invoice.setC_Currency_ID(currencyEUR.getRepoId());
			invoice.setGrandTotal(new BigDecimal("50.0"));
			saveRecord(invoice);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, /* creditMemoAdjusted */false);

			Assertions.assertEquals(new BigDecimal("10.0"), payment.getWriteOffAmt(), "Incorrect writeoff amount");

			// Test over/under completion
			payment.setPayAmt(new BigDecimal("60.0"));
			payment.setIsOverUnderPayment(true);
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, /* creditMemoAdjusted */false);

			Assertions.assertEquals(BigDecimal.valueOf(-10.0), payment.getOverUnderAmt(), "Incorrect over/under amount");

			Assertions.assertEquals(new BigDecimal("60.0"), payment.getPayAmt(), "Incorrect payment amount in EUR");

			// Test currency change
			payment.setC_Currency_ID(currencyCHF.getRepoId());
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assertions.assertEquals(0, new BigDecimal("120.0").compareTo(payment.getPayAmt()), "Incorrect pay amount");

			paymentBL.updateAmounts(payment, null, /* creditMemoAdjusted */false); // B==D

			Assertions.assertEquals(0, new BigDecimal("120.0").compareTo(payment.getPayAmt()), "Incorrect pay amount");

			currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal("2.0"));
			currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal("0.5"));

			// Called manually because we can't test properly with "creditMemoAdjusted" true
			paymentBL.onPayAmtChange(payment, /* creditMemoAdjusted */false);

			Assertions.assertEquals(0, new BigDecimal("120.0").compareTo(payment.getPayAmt()), "Incorrect payment amount in CHF");

			payment.setC_Invoice_ID(0);
			payment.setC_Order_ID(order.getC_Order_ID());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setPayAmt(new BigDecimal("30.0"));
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, /* creditMemoAdjusted */false);

			payment.setDocStatus(DocStatus.Completed.getCode());
			payment.setDocAction(X_C_Payment.DOCACTION_Close);
			saveRecord(payment);

			paymentBL.onPayAmtChange(payment, /* creditMemoAdjusted */false);
		}

		@Test
		public void noCurrencyConversionDefined()
		{
			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setDateTrx(SystemTime.asDayTimestamp());
			payment.setAD_Org_ID(1);
			payment.setC_Invoice_ID(getAllInvoices().get(0).getC_Invoice_ID());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setDiscountAmt(BigDecimal.ZERO);
			payment.setWriteOffAmt(BigDecimal.ZERO);
			payment.setOverUnderAmt(BigDecimal.ZERO);
			payment.setDocStatus(DocStatus.Drafted.getCode());
			payment.setDocAction(X_C_Payment.DOCACTION_Complete);
			invoice.setC_Currency_ID(currencyCHF.getRepoId());

			saveRecord(invoice);
			saveRecord(payment);

			//currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal(0.0));
			//currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal(0.0));

			assertThatThrownBy(() -> paymentBL.updateAmounts(payment, null, false))
					.isInstanceOf(NoCurrencyRateFoundException.class);
		}
	}

	/**
	 * The payment bonus that the customer deducts („Abzug bei Zahlung“) is dropped when the payment can no longer deduct it.
	 */
	@Nested
	public class paymentBonus
	{
		private I_C_Invoice invoice;

		@BeforeEach
		public void beforeEach()
		{
			final I_C_DocType docType = newInstance(I_C_DocType.class);
			docType.setDocBaseType(InvoiceDocBaseType.CustomerInvoice.getCode());
			docType.setIsSOTrx(true);
			saveRecord(docType);

			invoice = newInstance(I_C_Invoice.class);
			invoice.setAD_Org_ID(1);
			invoice.setC_BPartner_ID(20);
			invoice.setC_Currency_ID(currencyEUR.getRepoId());
			invoice.setGrandTotal(new BigDecimal("166.50"));
			invoice.setC_DocType_ID(docType.getC_DocType_ID());
			invoice.setIsSOTrx(true);
			invoice.setProcessed(true);
			invoice.setIsFinancial(InvoiceDocBaseType.CustomerInvoice.isFinancial());
			saveRecord(invoice);

			// the customer may deduct 2.60 net + 7 % VAT = 2.78, in the invoice's currency
			final Tax tax7 = Tax.builder()
					.taxId(TaxId.ofRepoId(1))
					.name("7 %")
					.orgId(OrgId.ANY)
					.validFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")))
					.taxCategoryId(TaxCategoryId.ofRepoId(1))
					.rate(new BigDecimal("7"))
					.isTaxExempt(false)
					.requiresTaxCertificate(false)
					.seqNo(10)
					.build();
			final PaymentBonusDeduction deduction = PaymentBonusDeduction.builder()
					.orgId(OrgId.ofRepoId(1))
					.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
					.customerId(BPartnerId.ofRepoId(20))
					.currencyId(currencyEUR)
					.precision(CurrencyPrecision.TWO)
					.line(PaymentBonusDeductionLine.builder()
							.bonusProductId(ProductId.ofRepoId(1))
							.tax(tax7)
							.netAmt(Money.of(new BigDecimal("2.60"), currencyEUR))
							.build())
					.build();
			assertThat(deduction.getGrossAmount().toBigDecimal()).isEqualByComparingTo("2.78");

			SpringContextHolder.registerJUnitBean(new PaymentBonusOnPaymentService(Optional.of(invoiceId -> Optional.of(deduction)), new PaymentBonusCreditMemoService()));
		}

		/**
		 * A payment in another currency than the invoice cannot deduct the bonus (the credit memo is in the invoice's currency): the bonus is set to 0.
		 * PayAmt stays what the customer paid (converted), so the shortfall becomes a write-off.
		 */
		@Test
		public void onCurrencyChange_otherCurrency_resetsPaymentBonus()
		{
			currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal("2.0"));

			// pre-filled in EUR: 166.50 - 2.78 = 163.72
			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setAD_Org_ID(1);
			payment.setDateTrx(SystemTime.asDayTimestamp());
			payment.setIsReceipt(true);
			payment.setDocStatus(DocStatus.Drafted.getCode());
			payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setPayAmt(new BigDecimal("163.72"));
			payment.setPaymentBonusAmt(new BigDecimal("2.78"));
			saveRecord(payment);

			payment.setC_Currency_ID(currencyCHF.getRepoId());
			paymentBL.onCurrencyChange(payment);

			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
			assertThat(payment.getPayAmt()).as("PayAmt = 2 x 163.72").isEqualByComparingTo("327.44");
			assertThat(payment.getWriteOffAmt()).as("WriteOffAmt = 2 x 166.50 open - 327.44").isEqualByComparingTo("5.56");
		}

		/** Like Skonto, a change of the conversion type in the same currency keeps the amounts the user entered: a full payment stays without bonus. */
		@Test
		public void onCurrencyChange_sameCurrency_keepsTypedAmounts()
		{
			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setAD_Org_ID(1);
			payment.setDateTrx(SystemTime.asDayTimestamp());
			payment.setIsReceipt(true);
			payment.setDocStatus(DocStatus.Drafted.getCode());
			payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setPayAmt(new BigDecimal("166.50"));
			payment.setPaymentBonusAmt(BigDecimal.ZERO);
			saveRecord(payment);

			// e.g. the user changed C_ConversionType_ID
			paymentBL.onCurrencyChange(payment);

			assertThat(payment.getPayAmt()).as("PayAmt").isEqualByComparingTo("166.50");
			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
			assertThat(payment.getWriteOffAmt()).as("WriteOffAmt").isEqualByComparingTo("0");
		}

		/**
		 * The pre-fill on a payment whose bonus is no longer deductible (e.g. the contract ended) drops the bonus left over from before.
		 * The amounts were just set from the invoice (PayAmt = open - Skonto, see {@code PaymentDAO.updateDiscountAndPayment}), so PayAmt stays open - Skonto.
		 */
		@Test
		public void updatePaymentBonusFromInvoice_noDeduction_dropsLeftoverBonus()
		{
			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setAD_Org_ID(1);
			payment.setDateTrx(SystemTime.asDayTimestamp());
			payment.setIsReceipt(true);
			payment.setDocStatus(DocStatus.Drafted.getCode());
			payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setPayAmt(new BigDecimal("166.50"));
			payment.setPaymentBonusAmt(new BigDecimal("2.78"));
			saveRecord(payment);

			new PaymentBonusOnPaymentService(Optional.empty(), new PaymentBonusCreditMemoService()).updatePaymentBonusFromInvoice(payment);

			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
			assertThat(payment.getPayAmt()).as("PayAmt").isEqualByComparingTo("166.50");
		}

		/** A payment whose invoice is removed has no bonus to deduct. */
		@Test
		public void updateAmounts_noInvoice_zeroesPaymentBonus()
		{
			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setAD_Org_ID(1);
			payment.setC_BPartner_ID(20);
			payment.setDateTrx(SystemTime.asDayTimestamp());
			payment.setIsReceipt(true);
			payment.setDocStatus(DocStatus.Drafted.getCode());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setPayAmt(new BigDecimal("163.72"));
			payment.setPaymentBonusAmt(new BigDecimal("2.78"));
			saveRecord(payment);

			// the user cleared the invoice
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Invoice_ID, /* creditMemoAdjusted */true);

			assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
		}
	}

	@Nested
	public class markReconciledAndSave
	{
		private I_C_Payment payment;

		@BeforeEach
		public void beforeEach()
		{
			payment = newInstance(I_C_Payment.class);
			payment.setC_Currency_ID(currencyEUR.getRepoId());
		}

		@Test
		public void reversal_failIf_DocStatusIsNotReversed()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.reversal(PaymentId.ofRepoId(123));
			// payment.setDocStatus(DocStatus.Reversed.getCode());
			// payment.setReversal_ID(123);
			assertThatThrownBy(() -> paymentBL.markReconciledAndSave(payment, reconcileRef))
					.hasMessageStartingWith("Payment shall be reversed but it's DocStatus is ");
		}

		@Test
		public void reversal_failIf_ReversalIdDoesNotMatch()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.reversal(PaymentId.ofRepoId(123));
			payment.setDocStatus(DocStatus.Reversed.getCode());
			payment.setReversal_ID(999);
			assertThatThrownBy(() -> paymentBL.markReconciledAndSave(payment, reconcileRef))
					.hasMessageStartingWith("Payment shall be reversed by `PaymentId(repoId=123)` but it was reversed by `PaymentId(repoId=999)");
		}

		@Test
		public void reversal()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.reversal(PaymentId.ofRepoId(123));

			payment.setDocStatus(DocStatus.Reversed.getCode());
			payment.setReversal_ID(123);
			paymentBL.markReconciledAndSave(payment, reconcileRef);
			assertThat(payment.isReconciled()).isTrue();
			assertThat(payment.getC_BankStatement_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getC_BankStatementLine_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getC_BankStatementLine_Ref_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getReversal_ID()).isEqualTo(123);
			assertThat(payment.getReconciledBy_SAP_GLJournal_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getReconciledBy_SAP_GLJournalLine_ID()).isLessThanOrEqualTo(0);

			final PaymentReconcileReference extractedReconcileRef = PaymentBL.extractPaymentReconcileReference(payment);
			assertThat(extractedReconcileRef).isEqualTo(reconcileRef);
		}

		@Test
		public void bankStatementLine()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.bankStatementLine(
					BankStatementId.ofRepoId(1),
					BankStatementLineId.ofRepoId(2));

			paymentBL.markReconciledAndSave(payment, reconcileRef);
			assertThat(payment.isReconciled()).isTrue();
			assertThat(payment.getC_BankStatement_ID()).isEqualTo(1);
			assertThat(payment.getC_BankStatementLine_ID()).isEqualTo(2);
			assertThat(payment.getC_BankStatementLine_Ref_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getReconciledBy_SAP_GLJournal_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getReconciledBy_SAP_GLJournalLine_ID()).isLessThanOrEqualTo(0);

			final PaymentReconcileReference extractedReconcileRef = PaymentBL.extractPaymentReconcileReference(payment);
			assertThat(extractedReconcileRef).isEqualTo(reconcileRef);
		}

		@Test
		public void bankStatementLineRef()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.bankStatementLineRef(
					BankStatementId.ofRepoId(1),
					BankStatementLineId.ofRepoId(2),
					BankStatementLineRefId.ofRepoId(3));

			paymentBL.markReconciledAndSave(payment, reconcileRef);
			assertThat(payment.isReconciled()).isTrue();
			assertThat(payment.getC_BankStatement_ID()).isEqualTo(1);
			assertThat(payment.getC_BankStatementLine_ID()).isEqualTo(2);
			assertThat(payment.getC_BankStatementLine_Ref_ID()).isEqualTo(3);
			assertThat(payment.getReconciledBy_SAP_GLJournal_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getReconciledBy_SAP_GLJournalLine_ID()).isLessThanOrEqualTo(0);

			final PaymentReconcileReference extractedReconcileRef = PaymentBL.extractPaymentReconcileReference(payment);
			assertThat(extractedReconcileRef).isEqualTo(reconcileRef);
		}

		@Test
		public void alreadyReconciled()
		{
			final PaymentReconcileReference bankStatementLine1 = PaymentReconcileReference.bankStatementLine(BankStatementId.ofRepoId(1), BankStatementLineId.ofRepoId(2));
			final PaymentReconcileReference bankStatementLine2 = PaymentReconcileReference.bankStatementLine(BankStatementId.ofRepoId(1), BankStatementLineId.ofRepoId(3));

			paymentBL.markReconciledAndSave(payment, bankStatementLine1);

			assertThatThrownBy(() -> paymentBL.markReconciledAndSave(payment, bankStatementLine2))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Payment with DocumentNo=");
		}

		@Test
		public void glJournalLine()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.glJournalLine(SAPGLJournalLineId.ofRepoId(1, 2));

			paymentBL.markReconciledAndSave(payment, reconcileRef);
			assertThat(payment.isReconciled()).isTrue();
			assertThat(payment.getC_BankStatement_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getC_BankStatementLine_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getC_BankStatementLine_Ref_ID()).isLessThanOrEqualTo(0);
			assertThat(payment.getReconciledBy_SAP_GLJournal_ID()).isEqualTo(1);
			assertThat(payment.getReconciledBy_SAP_GLJournalLine_ID()).isEqualTo(2);

			final PaymentReconcileReference extractedReconcileRef = PaymentBL.extractPaymentReconcileReference(payment);
			assertThat(extractedReconcileRef).isEqualTo(reconcileRef);
		}

	}

	@Nested
	public class markReconciled_with_preloadedPayments
	{
		@Nested
		public class singlePayment
		{
			private final PaymentReconcileReference bankStatementLine1 = PaymentReconcileReference.bankStatementLine(BankStatementId.ofRepoId(1), BankStatementLineId.ofRepoId(2));

			@Test
			public void preloaded()
			{
				final I_C_Payment payment = newInstance(I_C_Payment.class);
				payment.setC_Currency_ID(currencyEUR.getRepoId());
				saveRecord(payment);
				assertThat(payment.isReconciled()).isFalse();
				final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());

				final PaymentReconcileRequest request = PaymentReconcileRequest.of(paymentId, bankStatementLine1);
				paymentBL.markReconciled(
						ImmutableList.of(request),
						ImmutableList.of(payment));

				assertThat(payment.isReconciled()).isTrue();
				assertThat(PaymentBL.extractPaymentReconcileReference(payment)).isEqualTo(bankStatementLine1);
			}

			@Test
			public void notPreloaded()
			{
				final PaymentId paymentId;
				{
					final I_C_Payment payment = newInstance(I_C_Payment.class);
					payment.setC_Currency_ID(currencyEUR.getRepoId());
					saveRecord(payment);
					assertThat(payment.isReconciled()).isFalse();
					paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
				}

				final PaymentReconcileRequest request = PaymentReconcileRequest.of(paymentId, bankStatementLine1);
				paymentBL.markReconciled(
						ImmutableList.of(request),
						ImmutableList.of(/* nothing preloaded */));

				final I_C_Payment payment = paymentBL.getById(paymentId);
				assertThat(payment.isReconciled()).isTrue();
				assertThat(PaymentBL.extractPaymentReconcileReference(payment)).isEqualTo(bankStatementLine1);
			}
		}

		@Nested
		public class multiplePayments
		{
			private final PaymentReconcileReference bankStatementLine1 = PaymentReconcileReference.bankStatementLine(BankStatementId.ofRepoId(1), BankStatementLineId.ofRepoId(2));
			private final PaymentReconcileReference bankStatementLine2 = PaymentReconcileReference.bankStatementLine(BankStatementId.ofRepoId(1), BankStatementLineId.ofRepoId(3));

			@Test
			public void partialPreloaded()
			{
				final I_C_Payment payment1;
				final PaymentId paymentId1;
				{
					payment1 = newInstance(I_C_Payment.class);
					payment1.setC_Currency_ID(currencyEUR.getRepoId());
					saveRecord(payment1);
					paymentId1 = PaymentId.ofRepoId(payment1.getC_Payment_ID());
				}

				final PaymentId paymentId2;
				{
					final I_C_Payment payment2 = newInstance(I_C_Payment.class);
					payment2.setC_Currency_ID(currencyEUR.getRepoId());
					saveRecord(payment2);
					paymentId2 = PaymentId.ofRepoId(payment2.getC_Payment_ID());
				}

				paymentBL.markReconciled(
						ImmutableList.of(
								PaymentReconcileRequest.of(paymentId1, bankStatementLine1),
								PaymentReconcileRequest.of(paymentId2, bankStatementLine2)),
						ImmutableList.of(payment1));

				assertThat(payment1.isReconciled()).isTrue();
				assertThat(PaymentBL.extractPaymentReconcileReference(payment1)).isEqualTo(bankStatementLine1);

				final I_C_Payment payment2 = paymentBL.getById(paymentId2);
				assertThat(payment2.isReconciled()).isTrue();
				assertThat(PaymentBL.extractPaymentReconcileReference(payment2)).isEqualTo(bankStatementLine2);
			}
		}
	}

	@Nested
	public class canAllocateOrderPaymentToInvoice
	{
		private I_C_DocType prepayDocType;
		private I_C_DocType salesOrderDocType;

		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();

			prepayDocType = createDocType(DocBaseType.SalesOrder, X_C_DocType.DOCSUBTYPE_PrepayOrder);
			salesOrderDocType = createDocType(DocBaseType.SalesOrder, null);
		}

		@NonNull
		protected I_C_DocType createDocType(
				@NonNull final DocBaseType baseType,
				@Nullable final String subType)
		{
			final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
			docType.setDocBaseType(baseType.getCode());
			docType.setDocSubType(subType);
			saveRecord(docType);
			return docType;
		}

		@Test
		void canAllocate_OrderDoctypePrepay()
		{
			final I_C_Payment payment = createPayment(null);
			final de.metas.adempiere.model.I_C_Order prepayOrder = createSalesOrder(null, prepayDocType, payment);

			Assertions.assertTrue(paymentBL.canAllocateOrderPaymentToInvoice(prepayOrder));
		}

		@Test
		void canAllocate_OrderDoctypeSalesOrder_SameExternalId()
		{
			final ExternalId externalId = ExternalId.of("extId1432");
			final I_C_Payment payment = createPayment(externalId);
			final de.metas.adempiere.model.I_C_Order salesOrder = createSalesOrder(externalId, salesOrderDocType, payment);

			Assertions.assertTrue(paymentBL.canAllocateOrderPaymentToInvoice(salesOrder));
		}

		@Test
		void canAllocate_OrderDoctypeSalesOrder_DifferentExternalID()
		{
			final ExternalId externalIdSO = ExternalId.of("extId1432SO");
			final ExternalId externalIdP = ExternalId.of("extId1432P");
			final I_C_Payment payment = createPayment(externalIdP);
			final de.metas.adempiere.model.I_C_Order salesOrder = createSalesOrder(externalIdSO, salesOrderDocType, payment);

			Assertions.assertTrue(paymentBL.canAllocateOrderPaymentToInvoice(salesOrder));
		}

		@Test
		void canAllocate_OrderDoctypeSalesOrder_NoExternalID()
		{
			final I_C_Payment payment = createPayment(null);
			final de.metas.adempiere.model.I_C_Order salesOrder = createSalesOrder(null, salesOrderDocType, payment);

			Assertions.assertTrue(paymentBL.canAllocateOrderPaymentToInvoice(salesOrder));
		}

		@Test
		void canNotAllocate_OrderDoctypeSalesOrder_DifferentPaymentOrderLinked()
		{
			final de.metas.adempiere.model.I_C_Order salesOrder = createSalesOrder(null, salesOrderDocType, null);

			final I_C_Payment payment = createPayment(null);
			salesOrder.setC_Payment_ID(payment.getC_Payment_ID());

			Assertions.assertFalse(paymentBL.canAllocateOrderPaymentToInvoice(salesOrder));
		}

		@Test
		void canNotAllocate_OrderDoctypeSalesOrder_NoPayment()
		{
			final de.metas.adempiere.model.I_C_Order salesOrder = createSalesOrder(null, salesOrderDocType, null);

			Assertions.assertFalse(paymentBL.canAllocateOrderPaymentToInvoice(salesOrder));
		}

		@NonNull
		private I_C_Payment createPayment(@Nullable final ExternalId externalOrderId)
		{
			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setExternalOrderId(ExternalId.toValue(externalOrderId));
			payment.getExternalOrderId();
			save(payment);
			return payment;
		}

		@NonNull
		private de.metas.adempiere.model.I_C_Order createSalesOrder(
				@Nullable final ExternalId externalOrderId,
				@NonNull final I_C_DocType prepayDocType,
				@Nullable final I_C_Payment payment)
		{
			final de.metas.adempiere.model.I_C_Order order = newInstance(de.metas.adempiere.model.I_C_Order.class);
			order.setExternalId(ExternalId.toValue(externalOrderId));
			order.setC_DocType_ID(prepayDocType.getC_DocType_ID());
			order.setIsSOTrx(true);
			saveRecord(order);

			if (payment != null)
			{
				order.setC_Payment_ID(payment.getC_Payment_ID());
				payment.setC_Order_ID(order.getC_Order_ID());

				saveRecord(payment);
				saveRecord(order);
			}

			return order;
		}
	}
}
