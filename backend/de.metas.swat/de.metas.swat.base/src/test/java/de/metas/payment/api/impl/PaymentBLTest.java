package de.metas.payment.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.interceptor.C_Invoice;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentReconcileReference;
import de.metas.payment.api.PaymentReconcileRequest;
import de.metas.payment.processor.PaymentProcessorService;
import de.metas.payment.reservation.PaymentReservationCaptureRepository;
import de.metas.payment.reservation.PaymentReservationRepository;
import de.metas.payment.reservation.PaymentReservationService;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.time.SystemTime;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Payment;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
	}

	@Nested
	public class updateAmounts
	{
		private I_C_Order order;
		private I_C_Invoice invoice;

		@BeforeEach
		public void beforeEach()
		{
			order = newInstance(I_C_Order.class);
			order.setAD_Org_ID(1);
			order.setC_Currency_ID(currencyEUR.getRepoId());
			order.setGrandTotal(new BigDecimal(50.0));
			order.setIsSOTrx(true);
			order.setProcessed(true);
			saveRecord(order);

			invoice = newInstance(I_C_Invoice.class);
			invoice.setAD_Org_ID(1);
			invoice.setC_Currency_ID(currencyEUR.getRepoId());
			invoice.setGrandTotal(new BigDecimal(50.0));
			invoice.setIsSOTrx(true);
			invoice.setProcessed(true);
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
			currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal(2.0));
			currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal(0.5));

			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setDateTrx(today); // needed for default C_ConversionType_ID retrieval
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);
			saveRecord(payment);

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_DocType_ID, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
			Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
			Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_DocType_ID, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
			Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
			Assert.assertEquals("Incorrect writeoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
			Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
			Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			saveRecord(payment);

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.onCurrencyChange(payment);

			Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
			Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
			Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_ConversionType_ID, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
			Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
			Assert.assertEquals("Incorrect writeoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

			Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
			Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
			Assert.assertEquals("Incorrect writeoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());

			payment.setDiscountAmt(BigDecimal.ONE);
			payment.setWriteOffAmt(BigDecimal.ONE);
			payment.setOverUnderAmt(BigDecimal.ONE);
			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect over/under amount", BigDecimal.ZERO, payment.getOverUnderAmt());
			Assert.assertEquals("Incorrect discount amount", BigDecimal.ZERO, payment.getDiscountAmt());
			Assert.assertEquals("Incorrect writteoff amount", BigDecimal.ZERO, payment.getWriteOffAmt());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setC_Invoice_ID(getAllInvoices().get(0).getC_Invoice_ID());
			invoice.setC_Currency_ID(currencyCHF.getRepoId());

			saveRecord(invoice);
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect over/under amount", 0, payment.getOverUnderAmt().signum());
			Assert.assertEquals("Incorrect discount amount", 0, payment.getDiscountAmt().signum());
			Assert.assertEquals("Incorrect writteoff amount", 0, payment.getWriteOffAmt().signum());

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
			payment.setPayAmt(new BigDecimal(40.0));
			saveRecord(payment);
			invoice.setC_Currency_ID(currencyEUR.getRepoId());
			invoice.setGrandTotal(new BigDecimal(50.0));
			saveRecord(invoice);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect writeoff amount", new BigDecimal(10.0), payment.getWriteOffAmt());

			// Test over/under completion
			payment.setPayAmt(new BigDecimal(60.0));
			payment.setIsOverUnderPayment(true);
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect over/under amount", new BigDecimal(-10.0), payment.getOverUnderAmt());

			Assert.assertEquals("Incorrect payment amount in EUR", new BigDecimal(60.0), payment.getPayAmt());

			// Test currency change
			payment.setC_Currency_ID(currencyCHF.getRepoId());
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_C_Currency_ID, /* creditMemoAdjusted */false);

			Assert.assertEquals("Incorrect pay amount", 0, new BigDecimal(120.0).compareTo(payment.getPayAmt()));

			paymentBL.updateAmounts(payment, null, /* creditMemoAdjusted */false); // B==D

			Assert.assertEquals("Incorrect pay amount", 0, new BigDecimal(120.0).compareTo(payment.getPayAmt()));

			currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal(2.0));
			currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal(0.5));

			// Called manually because we can't test properly with "creditMemoAdjusted" true
			paymentBL.onPayAmtChange(payment, /* creditMemoAdjusted */false);

			Assert.assertTrue("Incorrect payment amount in CHF", new BigDecimal(120.0).compareTo(payment.getPayAmt()) == 0);

			payment.setC_Invoice_ID(0);
			payment.setC_Order_ID(order.getC_Order_ID());
			payment.setC_Currency_ID(currencyEUR.getRepoId());
			payment.setPayAmt(new BigDecimal(30.0));
			saveRecord(payment);

			paymentBL.updateAmounts(payment, I_C_Payment.COLUMNNAME_IsOverUnderPayment, /* creditMemoAdjusted */false);

			payment.setDocStatus(DocStatus.Completed.getCode());
			payment.setDocAction(X_C_Payment.DOCACTION_Close);
			saveRecord(payment);

			paymentBL.onPayAmtChange(payment, /* creditMemoAdjusted */false);
		}

		@Test
		public void exceptionTest()
		{
			final I_C_Payment payment = newInstance(I_C_Payment.class);
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

			currencyDAO.setRate(currencyEUR, currencyCHF, new BigDecimal(0.0));
			currencyDAO.setRate(currencyCHF, currencyEUR, new BigDecimal(0.0));

			assertThatThrownBy(() -> paymentBL.updateAmounts(payment, null, false))
					.isInstanceOf(AdempiereException.class)
					.hasMessage("NoCurrencyConversion");
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
		}

		@Test
		public void reveral_failIf_DocStatusIsNotReversed()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.reversal(PaymentId.ofRepoId(123));
			// payment.setDocStatus(DocStatus.Reversed.getCode());
			// payment.setReversal_ID(123);
			assertThatThrownBy(() -> paymentBL.markReconciledAndSave(payment, reconcileRef))
					.hasMessageStartingWith("Payment shall be reversed but it's DocStatus is ");
		}

		@Test
		public void reveral_failIf_ReversalIdDoesNotMatch()
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.reversal(PaymentId.ofRepoId(123));
			payment.setDocStatus(DocStatus.Reversed.getCode());
			payment.setReversal_ID(999);
			assertThatThrownBy(() -> paymentBL.markReconciledAndSave(payment, reconcileRef))
					.hasMessageStartingWith("Payment shall be reversed by `PaymentId(repoId=123)` but it was reversed by `PaymentId(repoId=999)");
		}

		@Test
		public void reveral()
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
					.hasMessageStartingWith("Payment was already reconciled");
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
					saveRecord(payment1);
					paymentId1 = PaymentId.ofRepoId(payment1.getC_Payment_ID());
				}

				final PaymentId paymentId2;
				{
					final I_C_Payment payment2 = newInstance(I_C_Payment.class);
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
		private C_Invoice c_invoiceInterceptor;

		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();

			prepayDocType = createDocType(X_C_DocType.DOCBASETYPE_SalesOrder, X_C_DocType.DOCSUBTYPE_PrepayOrder);
			salesOrderDocType = createDocType(X_C_DocType.DOCBASETYPE_SalesOrder, null);

			final @NonNull PaymentReservationRepository reservationsRepo = new PaymentReservationRepository();
			final @NonNull PaymentReservationCaptureRepository capturesRepo = new PaymentReservationCaptureRepository();
			final @NonNull PaymentProcessorService paymentProcessors = new PaymentProcessorService(Optional.empty());
			c_invoiceInterceptor = new C_Invoice(new PaymentReservationService(reservationsRepo, capturesRepo, paymentProcessors));
		}

		@SuppressWarnings("ConstantConditions")
		@NonNull
		protected I_C_DocType createDocType(@NonNull final String baseType, @Nullable final String subType)
		{
			final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
			docType.setDocBaseType(baseType);
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

		@Test
		void canNotAllocate_NullOrder()
		{
				Assertions.assertFalse(paymentBL.canAllocateOrderPaymentToInvoice(null));
		}

		@SuppressWarnings("ConstantConditions")
		@NonNull
		private I_C_Payment createPayment(@Nullable final ExternalId externalOrderId)
		{
			final I_C_Payment payment = newInstance(I_C_Payment.class);
			payment.setExternalOrderId(ExternalId.toValue(externalOrderId));
			payment.getExternalOrderId();
			save(payment);
			return payment;
		}

		@SuppressWarnings("ConstantConditions")
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
