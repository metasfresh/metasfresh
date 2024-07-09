package de.metas.invoice.interceptor;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.export.async.C_Invoice_CreateExportData;
import de.metas.invoice.location.InvoiceLocationsUpdater;
import de.metas.invoice.sequence.InvoiceCountryIdProvider;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.PaymentRule;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.reservation.PaymentReservationCaptureRequest;
import de.metas.payment.reservation.PaymentReservationService;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice // 03771
{
	public static final String SYSCONFIG_EXPORT_DATA_ENQUEUE = "de.metas.invoice.export.C_Invoice_CreateExportData.Enqueue";
	
	private final PaymentReservationService paymentReservationService;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	private final IDocumentLocationBL documentLocationBL;
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	
	private final IBPartnerStatisticsUpdater bPartnerStatisticsUpdater = Services.get(IBPartnerStatisticsUpdater.class);

	private final IDocumentNoBuilderFactory documentNoBuilderFactory;

	public C_Invoice(
			@NonNull final PaymentReservationService paymentReservationService,
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory)
	{
		this.paymentReservationService = paymentReservationService;
		this.documentLocationBL = documentLocationBL;
		this.documentNoBuilderFactory = documentNoBuilderFactory;
	}

	@Init
	void init()
	{
		documentNoBuilderFactory.registerCountryIdProvider(new InvoiceCountryIdProvider());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Invoice.COLUMNNAME_C_Tax_Departure_Country_ID })
	public void updateDocNo(@NonNull final I_C_Invoice invoice)
	{
		final I_C_DocType docTypeRecord = loadOutOfTrx(invoice.getC_DocTypeTarget_ID(), I_C_DocType.class);

		final IDocumentNoInfo documentNoInfo = documentNoBuilderFactory
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docTypeRecord)
				.setOldDocumentNo(invoice.getDocumentNo())
				.setDocumentModel(invoice)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return;
		}

		if (documentNoInfo.isDocNoControlled())
		{
			invoice.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onAfterComplete(final I_C_Invoice invoice)
	{
		// FIXME: This Kills performance. Please ask for extra budget next time you have to work around this area.
		// We're calling `testAndMarkAsPaid` multiple times, just to set the invoice.IsPaid flag.
		// That kills the performance as each time we have to read multiple allocations from db.
		// - Please see the PR: https://github.com/metasfresh/metasfresh/pull/9876 and its comments and reviews
		//
		// The problem I'm trying to fix here is that during allocation of an Invoice, we have allocated the correct payment with amount, and also created an *extra* *wrong* allocation with amt=0 for a different payment.
		// I could never reproduce this locally :(.
		// Please contact teo on possible solutions to fix this in a performant way (hint testAndMarkAsPaid should be called once at the end)
		testAndMarkAsPaid(invoice);
		allocateInvoiceAgainstCreditMemo(invoice);
		linkInvoiceToPaymentIfNeeded(invoice);
		allocateInvoiceAgainstPaymentIfNeeded(invoice);
		autoAllocateAvailablePayments(invoice);
		captureMoneyIfNeeded(invoice);
		ensureUOMsAreNotNull(invoice);

		final boolean enqueueForPossibleExport = sysConfigBL.getBooleanValue(SYSCONFIG_EXPORT_DATA_ENQUEUE, true, invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
		if(enqueueForPossibleExport)
		{
			C_Invoice_CreateExportData.scheduleOnTrxCommit(invoice);
		}
	}

	private void autoAllocateAvailablePayments(final I_C_Invoice invoice)
	{
		allocationBL.autoAllocateAvailablePayments(invoice);
	}

	private void ensureUOMsAreNotNull(@NonNull final I_C_Invoice invoice)
	{
		final IInvoiceBL invoiceBL = this.invoiceBL;
		invoiceBL.ensureUOMsAreNotNull(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void onAfterReversal(final I_C_Invoice invoice)
	{
		invoiceBL.handleReversalForInvoice(invoice);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave_updateCapturedLocationsAndRenderedAddresses(final I_C_Invoice invoice)
	{
		InvoiceLocationsUpdater.builder()
				.documentLocationBL(documentLocationBL)
				.record(invoice)
				.build()
				.updateAllIfNeeded();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }
			// exclude columns which are not relevant if they change
			, ignoreColumnsChanged = {
			I_C_Invoice.COLUMNNAME_IsPaid
	})
	public void updateIsReadOnly(final I_C_Invoice invoice)
	{
		invoiceBL.updateInvoiceLineIsReadOnlyFlags(invoice);
	}

	/**
	 * 07634: Remove lines of products which are not in the invoice's price list / version.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Invoice.COLUMNNAME_M_PriceList_ID })
	public void removeMaterialLinesNotCorrespondingToPriceList(final I_C_Invoice invoice)
	{
		final DocStatus docStatus = DocStatus.ofNullableCode(invoice.getDocStatus());
		if (docStatus != null && docStatus.isCompletedOrClosedReversedOrVoided())
		{
			return; // some metasfresh instances are customized allow changing bpartner locations on completed orders; this might trigger pricelist-changes - don't ask
		}

		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(invoice.getAD_Org_ID()));
		final ZonedDateTime invoiceDate = CoalesceUtil.coalesceSuppliers(
				() -> TimeUtil.asZonedDateTime(invoice.getDateInvoiced(), timeZone),
				() -> SystemTime.asZonedDateTime(timeZone));

		final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it

		@SuppressWarnings("ConstantConditions") final PriceListVersionId priceListVersionId = priceListDAO
				.retrievePriceListVersionIdOrNull(PriceListId.ofRepoId(invoice.getM_PriceList_ID()), invoiceDate, processedPLVFiltering); // can be null

		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

		final List<I_C_InvoiceLine> invoiceLines = invoiceDAO.retrieveLines(invoice, trxName);
		for (final I_C_InvoiceLine invoiceLine : invoiceLines)
		{
			final ProductId productId = ProductId.ofRepoIdOrNull(invoiceLine.getM_Product_ID());
			if (!ProductPrices.hasMainProductPrice(priceListVersionId, productId))
			{
				InterfaceWrapperHelper.delete(invoiceLine);
			}
		}
	}

	/**
	 * In the workflow [order => invoice] : The new invoice must inherit the payment rule from the related order.
	 * When creating a manual invoice: The new invoice must inherit the payment rule from the BPartner.
	 * When cloning an invoice: all should be set as in the original invoice, so the payment rule should be the same as in the old invoice.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Invoice.COLUMNNAME_C_BPartner_ID })
	public void setPaymentRule(final I_C_Invoice invoice)
	{
		if (!InterfaceWrapperHelper.isUIAction(invoice) || InterfaceWrapperHelper.isCopying(invoice))
		{
			return;
		}

		final I_C_BPartner bpartner = bpartnerDAO.getById(invoice.getC_BPartner_ID());
		final PaymentRule paymentRule;
		if (invoice.isSOTrx() && bpartner != null && bpartner.getPaymentRule() != null)
		{
			paymentRule = PaymentRule.ofCode(bpartner.getPaymentRule());

		}
		else if (!invoice.isSOTrx() && bpartner != null && bpartner.getPaymentRulePO() != null)
		{
			paymentRule = PaymentRule.ofCode(bpartner.getPaymentRulePO());
		}
		else
		{
			paymentRule = invoiceBL.getDefaultPaymentRule();
		}

		invoice.setPaymentRule(paymentRule.getCode());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setIsDiscountPrinted(final I_C_Invoice invoice)
	{
		// do nothing in case of PO invoice
		if (!invoice.isSOTrx())
		{
			return;
		}

		final boolean isDiscountPrinted;

		// in case the invoice is linked to an order, set the IsDiscountPrinted from there
		final I_C_Order order = invoice.getC_Order();

		if (order != null && order.getC_Order_ID() > 0)
		{
			isDiscountPrinted = order.isDiscountPrinted();
		}
		else
		{
			// in case the invoice is not linked to an order, take the value from the partner
			final I_C_BPartner partner = bpartnerDAO.getById(invoice.getC_BPartner_ID());

			isDiscountPrinted = partner.isDiscountPrinted();
		}

		invoice.setIsDiscountPrinted(isDiscountPrinted);
	}

	/**
	 * Mark invoice as paid if the grand total/open amount is 0
	 */
	private void testAndMarkAsPaid(final I_C_Invoice invoice)
	{
		// services
		final IInvoiceBL invoiceBL = this.invoiceBL;

		final boolean ignoreProcessed = true; // need to ignoreProcessed, because right now, Processed not yet set to true by the engine.
		invoiceBL.testAllocated(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()), ignoreProcessed);
	}

	/**
	 * Allocate the credit memo against it's parent invoices.
	 * <p>
	 * Note: ATM, there should only be one parent invoice for a credit memo, but it's possible to have more in the future.
	 */
	private void allocateInvoiceAgainstCreditMemo(@NonNull final I_C_Invoice creditMemo)
	{
		// services
		final IInvoiceBL invoiceBL = this.invoiceBL;

		final boolean isCreditMemo = invoiceBL.isCreditMemo(creditMemo);

		if (!isCreditMemo)
		{
			// nothing to do
			return;
		}

		// The amount from the credit memo to be allocated to parent invoices
		final BigDecimal creditMemoLeft = creditMemo.getGrandTotal();

		// the parent invoice might be null if the credit memo was created manually
		if (creditMemo.getRef_Invoice_ID() > 0)
		{
			final I_C_Invoice parentInvoice = InterfaceWrapperHelper.create(creditMemo.getRef_Invoice(), I_C_Invoice.class);
			final BigDecimal invoiceOpenAmt = allocationDAO.retrieveOpenAmtInInvoiceCurrency(parentInvoice,
					false).toBigDecimal(); // creditMemoAdjusted = false

			final BigDecimal amtToAllocate = invoiceOpenAmt.min(creditMemoLeft);

			// Allocate the minimum between parent invoice open amt and what is left of the creditMemo's grand Total
			invoiceBL.allocateCreditMemo(parentInvoice, creditMemo, amtToAllocate);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onDeleteInvoice_DeleteLines(final I_C_Invoice invoice)
	{
		// services
		final IInvoiceBL invoiceBL = this.invoiceBL;

		// ONLY delete lines for status Draft or In Progress
		final DocStatus docStatus = DocStatus.ofCode(invoice.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			return;
		}

		// task 09026. Do not touch other invoices because it was not yet required.
		// ONLY delete lines for credit memo or adjustment charge
		final boolean isAdjustmentCharge = invoiceBL.isAdjustmentCharge(invoice);
		if (isAdjustmentCharge)
		{
			deleteInvoiceLines(invoice);
			return;
		}

		final boolean isCreditMemo = invoiceBL.isCreditMemo(invoice);
		if (isCreditMemo)
		{
			deleteInvoiceLines(invoice);
		}

	}

	/**
	 * task 09026
	 * We need to delete the Invoice Lines before deleting the Invoice itself.
	 * This is not a common thing to be done, therefore I will leave this method only here, as private (not in the DAO class).
	 * Currently, it shall only happen in case of uncompleted invoices that are adjustment charges or credit memos.
	 */
	private void deleteInvoiceLines(final I_C_Invoice invoice)
	{
		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoice);
		for (final I_C_InvoiceLine line : lines)
		{
			InterfaceWrapperHelper.delete(line);
		}
	}

	private void linkInvoiceToPaymentIfNeeded(@NonNull final I_C_Invoice invoice)
	{
		final I_C_Order order = invoice.getC_Order();
		if (paymentBL.canAllocateOrderPaymentToInvoice(order))
		{
			final I_C_Payment payment = paymentBL.getById(PaymentId.ofRepoId(order.getC_Payment_ID()));
			payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
			paymentDAO.save(payment);

			allocationBL.autoAllocateSpecificPayment(invoice, payment, true);
		}
	}

	private void allocateInvoiceAgainstPaymentIfNeeded(@NonNull final I_C_Invoice invoice)
	{
		final I_C_Order order = invoice.getC_Order();
		if (paymentBL.canAllocateOrderPaymentToInvoice(order))
		{
			final I_C_Payment payment = paymentBL.getById(PaymentId.ofRepoId(order.getC_Payment_ID()));
			allocationBL.autoAllocateSpecificPayment(invoice, payment, true);
		}
	}

	private void captureMoneyIfNeeded(@NonNull final I_C_Invoice salesInvoice)
	{
		//
		// We capture money only for sales invoices
		if (!salesInvoice.isSOTrx())
		{
			return;
		}

		//
		// Avoid reversals
		if (invoiceBL.isReversal(salesInvoice))
		{
			return;
		}

		//
		// We capture money only for regular invoices (not credit memos)
		// TODO: for credit memos we shall refund a part of already reserved money
		if (invoiceBL.isCreditMemo(salesInvoice))
		{
			return;
		}

		//
		//
		// If there is no order, we cannot capture money because we don't know which is the payment reservation
		final OrderId salesOrderId = OrderId.ofRepoIdOrNull(salesInvoice.getC_Order_ID());
		if (salesOrderId == null)
		{
			return;
		}

		//
		// No payment reservation
		if (!paymentReservationService.hasPaymentReservation(salesOrderId))
		{
			return;
		}

		final LocalDate dateTrx = TimeUtil.asLocalDate(salesInvoice.getDateInvoiced());
		final Money grandTotal = extractGrandTotal(salesInvoice);

		paymentReservationService.captureAmount(PaymentReservationCaptureRequest.builder()
				.salesOrderId(salesOrderId)
				.salesInvoiceId(InvoiceId.ofRepoId(salesInvoice.getC_Invoice_ID()))
				.customerId(BPartnerId.ofRepoId(salesInvoice.getC_BPartner_ID()))
				.dateTrx(dateTrx)
				.amount(grandTotal)
				.build());
	}

	private static Money extractGrandTotal(@NonNull final I_C_Invoice salesInvoice)
	{
		return Money.of(salesInvoice.getGrandTotal(), CurrencyId.ofRepoId(salesInvoice.getC_Currency_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_Invoice.COLUMNNAME_M_Warehouse_ID, I_C_Invoice.COLUMNNAME_DateInvoiced })
	public void updateInvoiceLinesTax(@NonNull final I_C_Invoice invoice)
	{
		invoiceBL.setInvoiceLineTaxes(invoice);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_C_Invoice.COLUMNNAME_M_SectionCode_ID,
					I_C_Invoice.COLUMNNAME_UserElementString1,
					I_C_Invoice.COLUMNNAME_UserElementString2,
					I_C_Invoice.COLUMNNAME_UserElementString3,
					I_C_Invoice.COLUMNNAME_UserElementString4,
					I_C_Invoice.COLUMNNAME_UserElementString5,
					I_C_Invoice.COLUMNNAME_UserElementString6,
					I_C_Invoice.COLUMNNAME_UserElementString7,
			})
	public void copyDimensionToLines(@NonNull final I_C_Invoice invoice)
	{
		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoice);
		if (lines.isEmpty())
		{
			return;
		}

		final boolean sectionCodeChanged = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_M_SectionCode_ID);
		final boolean userElementString1Changed = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_UserElementString1);
		final boolean userElementString2Changed = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_UserElementString2);
		final boolean userElementString3Changed = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_UserElementString3);
		final boolean userElementString4Changed = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_UserElementString4);
		final boolean userElementString5Changed = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_UserElementString5);
		final boolean userElementString6Changed = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_UserElementString6);
		final boolean userElementString7Changed = InterfaceWrapperHelper.isValueChanged(invoice, I_C_Invoice.COLUMNNAME_UserElementString7);

		for (final I_C_InvoiceLine line : lines)
		{
			if (sectionCodeChanged)
			{
				line.setM_SectionCode_ID(invoice.getM_SectionCode_ID());
			}
			if (userElementString1Changed)
			{
				line.setUserElementString1(invoice.getUserElementString1());
			}
			if (userElementString2Changed)
			{
				line.setUserElementString2(invoice.getUserElementString2());
			}
			if (userElementString3Changed)
			{
				line.setUserElementString3(invoice.getUserElementString3());
			}
			if (userElementString4Changed)
			{
				line.setUserElementString4(invoice.getUserElementString4());
			}
			if (userElementString5Changed)
			{
				line.setUserElementString5(invoice.getUserElementString5());
			}
			if (userElementString6Changed)
			{
				line.setUserElementString6(invoice.getUserElementString6());
			}
			if (userElementString7Changed)
			{
				line.setUserElementString7(invoice.getUserElementString7());
			}

			invoiceDAO.save(line);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_BEFORE_PREPARE })
	public void updateBPartnerStats(@NonNull final I_C_Invoice invoice)
	{
		bPartnerStatisticsUpdater
				.updateBPartnerStatistics(IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest.builder()
						.bpartnerId(invoice.getC_BPartner_ID())
						.build());

	}
}
