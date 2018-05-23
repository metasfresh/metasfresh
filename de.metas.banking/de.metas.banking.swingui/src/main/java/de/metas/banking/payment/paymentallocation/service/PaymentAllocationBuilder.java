package de.metas.banking.payment.paymentallocation.service;

/*
 * #%L
 * de.metas.banking.swingui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationBuilder;
import de.metas.allocation.api.IAllocationLineBuilder;
import de.metas.banking.model.I_C_Payment;
import de.metas.banking.payment.paymentallocation.IPaymentAllocationBL;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;

/**
 * Builds one {@link I_C_AllocationHdr} of all given {@link IInvoiceRow}s and {@link IPaymentRow}s.
 *
 * @author tsa
 *
 */
public class PaymentAllocationBuilder
{
	public static final PaymentAllocationBuilder newBuilder()
	{
		return new PaymentAllocationBuilder();
	}

	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	// private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);

	// Parameters
	private Properties _ctx;
	private int _adOrgId;
	private int _currencyId;
	// tasl 09643
	// separate transaction date from the accounting date
	private Date _dateTrx;
	private Date _dateAcct;
	private ImmutableList<IPayableDocument> _payableDocuments = ImmutableList.of();
	private ImmutableList<IPaymentDocument> _paymentDocuments = ImmutableList.of();
	private boolean _allowOnlyOneVendorDoc = true;

	// Status
	private boolean _built = false;
	private List<AllocationLineCandidate> _allocationLineCandidates = ImmutableList.of();

	private PaymentAllocationBuilder()
	{
		super();
	}

	/**
	 * @return result message (to be displayed in UI status bar)
	 * @throws PaymentAllocationException in case of any error
	 */
	public String build()
	{
		markAsBuilt();

		try
		{
			trxManager.run(new TrxRunnableAdapter()
			{
				@Override
				public void run(final String localTrxName)
				{
					buildInTrx();
				}
			});
		}
		catch (Exception e)
		{
			final PaymentAllocationException pae = PaymentAllocationException.wrapToPaymentAllocationExceptionIfNeeded(e);
			throw pae;
		}

		return getSummary();
	}

	private String getSummary()
	{
		// Check.assumeNotNull(_allocationHdr, "_allocationHdr not null");
		// return docActionBL.getSummary(_allocationHdr);
		return ""; // TODO
	}

	private final void buildInTrx()
	{
		//
		// Create allocation candidates
		this._allocationLineCandidates = createAllocationLineCandidates();
		if (_allocationLineCandidates.isEmpty())
		{
			return;
		}

		//
		// Add allocation lines
		for (final AllocationLineCandidate line : _allocationLineCandidates)
		{
			createAndCompleteAllocation(line);
		}
	}

	/** @return created allocation candidates */
	@VisibleForTesting
	List<AllocationLineCandidate> getAllocationLineCandidates()
	{
		return _allocationLineCandidates;
	}

	/**
	 * Create and complete one {@link I_C_AllocationHdr} for given candidate.
	 *
	 * @param line
	 */
	private final void createAndCompleteAllocation(final AllocationLineCandidate line)
	{
		final int adOrgId = getAD_Org_ID();
		final int currencyId = getC_Currency_ID();
		final Timestamp dateTrx = TimeUtil.asTimestamp(getDateTrx());
		final Timestamp dateAcct = TimeUtil.asTimestamp(getDateAcct());

		final IContextAware context = new PlainContextAware(getCtx(), ITrx.TRXNAME_ThreadInherited);
		final String description = Env.getContext(getCtx(), "#AD_User_Name");
		final IAllocationBuilder allocationBuilder = allocationBL.newBuilder(context)
				.setAD_Org_ID(adOrgId)
				.setC_Currency_ID(currencyId)
				.setDateAcct(dateAcct)
				.setDateTrx(dateTrx)
				.setManual(true) // flag it as manually created by user
				.setDescription(description);

		final IAllocationLineBuilder payableLineBuilder = allocationBuilder.addLine()
				.setAD_Org_ID(adOrgId)
				.setC_BPartner_ID(line.getC_BPartner_ID())
				//
				// Amounts
				.setAmount(line.getAmount())
				.setDiscountAmt(line.getDiscountAmt())
				.setWriteOffAmt(line.getWriteOffAmt())
				.setOverUnderAmt(line.getOverUnderAmt())
				.setSkipIfAllAmountsAreZero();

		final ITableRecordReference payableDocRef = line.getPayableDocument();
		final String payableDocTableName = payableDocRef.getTableName();
		final ITableRecordReference paymentDocRef = line.getPaymentDocument();
		final String paymentDocTableName = paymentDocRef == null ? null : paymentDocRef.getTableName();

		//
		// Invoice - Payment
		if (I_C_Invoice.Table_Name.equals(payableDocTableName) && I_C_Payment.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.setC_Invoice_ID(payableDocRef.getRecord_ID());
			payableLineBuilder.setC_Payment_ID(paymentDocRef.getRecord_ID());
		}
		//
		// Prepaid order - Payment
		else if (I_C_Order.Table_Name.equals(payableDocTableName) && I_C_Payment.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.setPrepayOrder_ID(payableDocRef.getRecord_ID());
			payableLineBuilder.setC_Payment_ID(paymentDocRef.getRecord_ID());
		}
		//
		// Invoice - CreditMemo invoice or Sales invoice - Purchase Invoice
		else if (I_C_Invoice.Table_Name.equals(payableDocTableName) && I_C_Invoice.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.setC_Invoice_ID(payableDocRef.getRecord_ID());
			//

			// Credit memo line
			allocationBuilder.addLine()
					.setAD_Org_ID(adOrgId)
					.setC_BPartner_ID(line.getC_BPartner_ID())
					//
					// Amounts
					.setAmount(line.getAmount().negate())
					.setOverUnderAmt(line.getPaymentOverUnderAmt())
					.setSkipIfAllAmountsAreZero()
					//
					.setC_Invoice_ID(paymentDocRef.getRecord_ID());
		}
		//
		// Invoice - just Discount/WriteOff
		else if (I_C_Invoice.Table_Name.equals(payableDocTableName) && paymentDocTableName == null)
		{
			payableLineBuilder.setC_Invoice_ID(payableDocRef.getRecord_ID());
			// allow only if the line's amount is zero, because else, we need to have a document where to allocate.
			Check.assume(line.getAmount().signum() == 0, "zero amount: {}", line);
		}
		//
		// Outgoing payment - Incoming payment
		else if (I_C_Payment.Table_Name.equals(payableDocTableName) && I_C_Payment.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.setC_Payment_ID(payableDocRef.getRecord_ID());
			// Incoming payment line
			allocationBuilder.addLine()
					.setAD_Org_ID(adOrgId)
					.setC_BPartner_ID(line.getC_BPartner_ID())
					//
					// Amounts
					.setAmount(line.getAmount().negate())
					.setOverUnderAmt(line.getPaymentOverUnderAmt())
					.setSkipIfAllAmountsAreZero()
					//
					.setC_Payment_ID(paymentDocRef.getRecord_ID());
		}
		else
		{
			throw new InvalidDocumentsPaymentAllocationException(payableDocRef, paymentDocRef);
		}

		allocationBuilder.createAndComplete();

		//
		final List<I_C_AllocationLine> lines = allocationBuilder.getC_AllocationLines();
		setCounter_AllocationLine_ID(lines);

	}

	/**
	 * Sets the counter allocation line - that means the mathcing line
	 * The id is set only if we have 2 line: credit memo - invoice; purchase invoice - sales invoice; incoming payment - outgoing payment
	 *
	 * @param lines
	 */
	private void setCounter_AllocationLine_ID(List<I_C_AllocationLine> lines)
	{
		if (lines.size() != 2)
		{
			return;
		}

		//
		final I_C_AllocationLine al1 = lines.get(0);
		final I_C_AllocationLine al2 = lines.get(1);

		al1.setCounter_AllocationLine_ID(al2.getC_AllocationLine_ID());
		InterfaceWrapperHelper.save(al1);

		//
		al2.setCounter_AllocationLine_ID(al1.getC_AllocationLine_ID());
		InterfaceWrapperHelper.save(al2);
	}

	/**
	 * Allocate {@link #getPayableDocuments()} and {@link #getPaymentDocuments()}.
	 *
	 * @return created allocation candidates
	 */
	private List<AllocationLineCandidate> createAllocationLineCandidates()
	{
		//
		// Make sure we have something to allocate
		final List<IPayableDocument> payableDocuments = getPayableDocuments();
		final List<IPaymentDocument> paymentDocuments = getPaymentDocuments();
		if (payableDocuments.isEmpty() && paymentDocuments.isEmpty())
		{
			throw new NoDocumentsPaymentAllocationException();
		}

		//
		// Make sure that we allow allocation one document per type for vendor documents
		assertOnlyOneVendorDocType(payableDocuments, paymentDocuments);

		final List<AllocationLineCandidate> allocationCandidates = new ArrayList<>();

		//
		// Try to allocate credit memos to regular invoices
		allocationCandidates.addAll(createAllocationLineCandidates_CreditMemosToInvoices(payableDocuments));

		//
		// Try to allocate purchase invoices to sales invoices
		if (Services.get(IPaymentAllocationBL.class).isPurchaseSalesInvoiceCompensationAllowed())
		{
			allocationCandidates.addAll(createAllocationLineCandidates_PurchaseInvoicesToSaleInvoices(payableDocuments));
		}

		//
		// Allocate payments to invoices
		allocationCandidates.addAll(createAllocationLineCandidates(payableDocuments, paymentDocuments));

		//
		// Try allocate payment reversals to payments
		allocationCandidates.addAll(createAllocationLineCandidates_ForPayments(paymentDocuments));

		// Try allocate the payable remaining Discounts and WriteOffs.
		allocationCandidates.addAll(createAllocationLineCandidates_DiscountAndWriteOffs(payableDocuments));

		assertAllFullyAllocated();

		return allocationCandidates;
	}

	/***
	 * Do not allow to allocate more then one document type for vendor documents
	 *
	 * @param payableDocuments
	 * @param paymentDocuments
	 */
	private void assertOnlyOneVendorDocType(final List<IPayableDocument> payableDocuments,
			final List<IPaymentDocument> paymentDocuments)
	{
		if (!isAllowOnlyOneVendorDoc())
		{
			return; // task 09558: nothing to do
		}

		// filter payments
		final List<IPaymentDocument> paymentVendorDocuments = ListUtils.copyAndFilter(paymentDocuments, new Predicate<IPaymentDocument>()
		{
			@Override
			public boolean apply(IPaymentDocument paymentDoc)
			{
				if (!paymentDoc.isVendorDocument())
				{
					return false;
				}

				return true;
			}
		});

		//
		// filter invoices and credit memos
		final List<IPaymentDocument> paymentVendorDocuments_CreditMemos = new ArrayList<>();
		final List<IPayableDocument> payableVendorDocuments_NoCreditMemos = ListUtils.copyAndFilter(payableDocuments, new Predicate<IPayableDocument>()
		{
			@Override
			public boolean apply(IPayableDocument payable)
			{
				if (payable.isCreditMemo() && payable.isVendorDocument())
				{
					paymentVendorDocuments_CreditMemos.add(CreditMemoInvoiceAsPaymentDocument.wrap(payable));
					return false;
				}

				if (!payable.isVendorDocument())
				{
					return false;
				}

				return true;
			}
		});

		if (paymentVendorDocuments.size() > 1 || payableVendorDocuments_NoCreditMemos.size() > 1 || paymentVendorDocuments_CreditMemos.size() > 1)
		{
			final List<IPaymentDocument> paymentVendorDocs = new ArrayList<>();
			paymentVendorDocs.addAll(paymentVendorDocuments);
			paymentVendorDocs.addAll(paymentVendorDocuments_CreditMemos);

			throw new MultipleVendorDocumentsException(paymentVendorDocs, payableVendorDocuments_NoCreditMemos);
		}
	}

	/**
	 * Allocate given payments to given payable documents.
	 *
	 * @param payableDocuments
	 * @param paymentDocuments
	 * @return created allocation candidates
	 */
	private final List<AllocationLineCandidate> createAllocationLineCandidates(final List<IPayableDocument> payableDocuments, final List<IPaymentDocument> paymentDocuments)
	{
		if (payableDocuments.isEmpty() || paymentDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();

		//
		// First, loop through all Invoices, then, in every single Invoice loop, loop through all Payments.
		// Allocate the value of every Payment with the Invoice, until the Invoice value is reached.
		// In most cases this should only be one Payment.
		for (final IPayableDocument payable : payableDocuments)
		{
			for (final IPaymentDocument payment : paymentDocuments)
			{
				// If the invoice was fully allocated, stop here and go to next invoice.
				if (payable.isFullyAllocated())
				{
					break;
				}

				// Skip fully allocated payments
				if (payment.isFullyAllocated())
				{
					continue;
				}

				// Skip if the invoice and payment are not compatible
				if (!isCompatible(payable, payment))
				{
					continue;
				}

				//
				// Calculate the amounts to allocate:
				final BigDecimal amountToAllocate = calculateAmountToAllocate(payable, payment);
				final BigDecimal payableDiscountAmtToAllocate = payable.getDiscountAmtToAllocate();
				final BigDecimal payableWriteOffAmtToAllocate = payable.getWriteOffAmtToAllocate();
				final BigDecimal payableOverUnderAmt = payable.calculateProjectedOverUnderAmt(amountToAllocate, payableDiscountAmtToAllocate, payableWriteOffAmtToAllocate);
				final BigDecimal paymentOverUnderAmt = payment.calculateProjectedOverUnderAmt(amountToAllocate);

				// Create new Allocation Line
				final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
						.setC_BPartner_ID(payable.getC_BPartner_ID())
						//
						.setPayableDocument(payable.getReference())
						.setPaymentDocument(payment.getReference())
						// Amounts:
						.setAmount(amountToAllocate)
						.setDiscountAmt(payableDiscountAmtToAllocate)
						.setWriteOffAmt(payableWriteOffAmtToAllocate)
						.setOverUnderAmt(payableOverUnderAmt)
						.setPaymentOverUnderAmt(paymentOverUnderAmt)
						//
						.build();
				allocationLineCandidates.add(allocationLine);

				// Update how much was allocated on current invoice and payment.
				payable.addAllocatedAmounts(amountToAllocate, payableDiscountAmtToAllocate, payableWriteOffAmtToAllocate);
				payment.addAllocatedAmt(amountToAllocate);
			}	// loop through payments for invoice
		}   // invoice loop

		return allocationLineCandidates;
	}

	private final List<AllocationLineCandidate> createAllocationLineCandidates_CreditMemosToInvoices(final List<IPayableDocument> payableDocuments)
	{
		final List<IPaymentDocument> paymentDocuments_CreditMemos = new ArrayList<>();
		final List<IPayableDocument> payableDocuments_NoCreditMemos = ListUtils.copyAndFilter(payableDocuments, new Predicate<IPayableDocument>()
		{
			@Override
			public boolean apply(IPayableDocument payable)
			{
				if (payable.isCreditMemo())
				{
					paymentDocuments_CreditMemos.add(CreditMemoInvoiceAsPaymentDocument.wrap(payable));
					return false;
				}
				return true;
			}
		});

		return createAllocationLineCandidates(payableDocuments_NoCreditMemos, paymentDocuments_CreditMemos);
	}

	private final List<AllocationLineCandidate> createAllocationLineCandidates_PurchaseInvoicesToSaleInvoices(final List<IPayableDocument> payableDocuments)
	{
		final List<IPaymentDocument> paymentDocuments_PurchaseInvoices = new ArrayList<>();
		final List<IPayableDocument> payableDocuments_SaleInvoices = ListUtils.copyAndFilter(payableDocuments, new Predicate<IPayableDocument>()
		{
			@Override
			public boolean apply(IPayableDocument payable)
			{
				// do not support credit memo
				if (payable.isCreditMemo())
				{
					return false;
				}

				if (!payable.isCustomerDocument())
				{
					paymentDocuments_PurchaseInvoices.add(PurchaseInvoiceAsPaymentDocument.wrap(payable));
					return false;
				}
				return true;
			}
		});

		return createAllocationLineCandidates(payableDocuments_SaleInvoices, paymentDocuments_PurchaseInvoices);
	}

	/**
	 * Iterate all payment documents and try to allocate incoming payments to outgoing payments
	 *
	 * @param paymentDocuments
	 */
	private final List<AllocationLineCandidate> createAllocationLineCandidates_ForPayments(final List<IPaymentDocument> paymentDocuments)
	{
		if (paymentDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Build the incoming payments and outgoing payments lists.
		final List<IPaymentDocument> paymentDocumentsIn = new ArrayList<>();
		final List<IPaymentDocument> paymentDocumentsOut = new ArrayList<>();
		for (final IPaymentDocument payment : paymentDocuments)
		{
			if (payment.isFullyAllocated())
			{
				continue;
			}
			if (payment.getAmountToAllocate().signum() > 0)
			{
				paymentDocumentsIn.add(payment);
			}
			else
			{
				paymentDocumentsOut.add(payment);
			}
		}
		// No documents => nothing to do
		if (paymentDocumentsIn.isEmpty() || paymentDocumentsOut.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Iterate incoming payments and try allocating them to outgoing payments.
		final List<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();
		for (final IPaymentDocument paymentIn : paymentDocumentsIn)
		{
			for (final IPaymentDocument paymentOut : paymentDocumentsOut)
			{
				if (paymentOut.isFullyAllocated())
				{
					continue;
				}

				final BigDecimal paymentOut_amtToAllocate = paymentOut.getAmountToAllocate(); // => negative
				final BigDecimal paymentIn_amtToAllocate = paymentIn.getAmountToAllocate().negate(); // => negative
				final BigDecimal amtToAllocate = paymentIn_amtToAllocate.max(paymentOut_amtToAllocate);

				final BigDecimal payableOverUnderAmt = paymentOut.calculateProjectedOverUnderAmt(amtToAllocate);
				final BigDecimal paymentOverUnderAmt = paymentIn.calculateProjectedOverUnderAmt(amtToAllocate.negate()).negate();

				// Create new Allocation Line
				final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
						.setC_BPartner_ID(paymentOut.getC_BPartner_ID())
						//
						.setPayableDocument(paymentOut.getReference())
						.setPaymentDocument(paymentIn.getReference())
						// Amounts:
						.setAmount(amtToAllocate)
						.setOverUnderAmt(payableOverUnderAmt)
						.setPaymentOverUnderAmt(paymentOverUnderAmt)
						//
						.build();
				allocationLineCandidates.add(allocationLine);

				// Update how much was allocated on current invoice and payment.
				paymentOut.addAllocatedAmt(amtToAllocate);
				paymentIn.addAllocatedAmt(amtToAllocate.negate());
			}
		}

		return allocationLineCandidates;
	}

	/**
	 * Iterate all given payable documents and create an allocation only for Discount and WriteOff amounts.
	 *
	 * @param payableDocuments
	 * @return created allocation candidates.
	 */
	private List<AllocationLineCandidate> createAllocationLineCandidates_DiscountAndWriteOffs(List<IPayableDocument> payableDocuments)
	{
		if (payableDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();
		for (final IPayableDocument payable : payableDocuments)
		{
			final BigDecimal amtToAllocated = BigDecimal.ZERO;
			final BigDecimal discountAmt = payable.getDiscountAmtToAllocate();
			final BigDecimal writeOffAmt = payable.getWriteOffAmtToAllocate();
			if (discountAmt.signum() == 0 && writeOffAmt.signum() == 0)
			{
				continue;
			}

			BigDecimal payableOverUnderAmt = payable.calculateProjectedOverUnderAmt(amtToAllocated, discountAmt, writeOffAmt);
			final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
					.setC_BPartner_ID(payable.getC_BPartner_ID())
					//
					.setPayableDocument(payable.getReference())
					.setPaymentDocument(null) // nop
					// Amounts:
					.setAmount(amtToAllocated)
					.setDiscountAmt(discountAmt)
					.setWriteOffAmt(writeOffAmt)
					.setOverUnderAmt(payableOverUnderAmt)
					.setPaymentOverUnderAmt(BigDecimal.ZERO)
					//
					.build();
			allocationLineCandidates.add(allocationLine);

			payable.addAllocatedAmounts(amtToAllocated, discountAmt, writeOffAmt);
		}

		return allocationLineCandidates;
	}

	/**
	 * Check all payable and payment documents and make sure all of them were allocated.
	 */
	private final void assertAllFullyAllocated()
	{
		//
		// Check payables (invoices, prepaid orders etc)
		{
			final List<IPayableDocument> payableDocumentsNotFullyAllocated = new ArrayList<>();
			for (final IPayableDocument payable : getPayableDocuments())
			{
				// Skip fully allocated invoices
				if (payable.isFullyAllocated())
				{
					continue;
				}
				payableDocumentsNotFullyAllocated.add(payable);
			}
			if (!payableDocumentsNotFullyAllocated.isEmpty())
			{
				throw new PayableDocumentNotAllocatedException(payableDocumentsNotFullyAllocated);
			}
		}

		//
		// Check payments
		{
			final List<IPaymentDocument> paymentDocumentsNotFullyAllocated = new ArrayList<>();
			for (final IPaymentDocument payment : getPaymentDocuments())
			{
				// Skip fully allocated invoices
				if (payment.isFullyAllocated())
				{
					continue;
				}

				paymentDocumentsNotFullyAllocated.add(payment);
			}
			if (!paymentDocumentsNotFullyAllocated.isEmpty())
			{
				throw new PaymentDocumentNotAllocatedException(paymentDocumentsNotFullyAllocated);
			}
		}

	}

	/**
	 * Check if given payment document can be allocated to payable document.
	 *
	 * @param payable
	 * @param payment
	 * @return true if the invoice and payment are compatible and we could try to do an allocation
	 */
	private final boolean isCompatible(final IPayableDocument payable, final IPaymentDocument payment)
	{
		// Given payment does not support payable's type
		if (!payment.canPay(payable))
		{
			return false;
		}

		//
		// Check invoice-payment compatibility: same sign
		final boolean positiveInvoiceAmtToAllocate = payable.getAmountToAllocateInitial().signum() >= 0;
		final boolean positivePaymentAmtToAllocate = payment.getAmountToAllocateInitial().signum() >= 0;
		if (positiveInvoiceAmtToAllocate != positivePaymentAmtToAllocate)
		{
			return false;
		}

		//
		// Check invoice-payment compatibility: same BPartner
		// NOTE: we don't check this because we are allowed to allocate invoice-payments of different BPartners
		// Think about BP relations.

		return true;
	}

	/**
	 *
	 * @param invoice
	 * @param payment
	 * @return how much we maximum allocate between given invoice and given payment.
	 */
	private final BigDecimal calculateAmountToAllocate(final IPayableDocument invoice, final IPaymentDocument payment)
	{
		final BigDecimal invoiceAmountToAllocate = invoice.getAmountToAllocate();
		final BigDecimal paymentAmountToAllocate = payment.getAmountToAllocate();

		if (invoiceAmountToAllocate.signum() >= 0)
		{
			// Invoice(+), Payment(+)
			if (paymentAmountToAllocate.signum() >= 0)
			{
				return invoiceAmountToAllocate.min(paymentAmountToAllocate);
			}
			// Invoice(+), Payment(-)
			else
			{
				return BigDecimal.ZERO;
			}
		}
		else
		{
			// Invoice(-), Payment(+)
			if (paymentAmountToAllocate.signum() >= 0)
			{
				return BigDecimal.ZERO;
			}
			// Invoice(-), Payment(-)
			else
			{
				return invoiceAmountToAllocate.max(paymentAmountToAllocate);
			}
		}
	}

	private final void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "Not already built");
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "ctx not null");
		return _ctx;
	}

	public PaymentAllocationBuilder setCtx(final Properties ctx)
	{
		assertNotBuilt();
		_ctx = ctx;
		return this;
	}

	public PaymentAllocationBuilder setAD_Org_ID(final int adOrgId)
	{
		assertNotBuilt();
		_adOrgId = adOrgId;
		return this;
	}

	private final int getAD_Org_ID()
	{
		if (_adOrgId <= 0)
		{
			throw new PaymentAllocationException("@Org0NotAllowed@");
		}
		return _adOrgId;
	}

	private final int getC_Currency_ID()
	{
		Check.assume(_currencyId > 0, "currencyId > 0");
		return _currencyId;
	}

	/** Sets the currency of all invoices and payments set. */
	public PaymentAllocationBuilder setC_Currency_ID(final int currencyId)
	{
		assertNotBuilt();
		_currencyId = currencyId;
		return this;
	}

	private final Date getDateTrx()
	{
		Check.assumeNotNull(_dateTrx, "date not null");
		return _dateTrx;
	}

	public PaymentAllocationBuilder setDateTrx(final Date dateTrx)
	{
		assertNotBuilt();
		_dateTrx = dateTrx;
		return this;
	}

	private final Date getDateAcct()
	{
		Check.assumeNotNull(_dateAcct, "date not null");
		return _dateAcct;
	}

	public PaymentAllocationBuilder setDateAcct(final Date dateAcct)
	{
		assertNotBuilt();
		_dateAcct = dateAcct;
		return this;
	}

	public boolean isAllowOnlyOneVendorDoc()
	{
		return _allowOnlyOneVendorDoc;
	}

	public PaymentAllocationBuilder setAllowOnlyOneVendorDoc(boolean AllowOnlyOneVendorDoc)
	{
		assertNotBuilt();
		this._allowOnlyOneVendorDoc = AllowOnlyOneVendorDoc;
		return this;
	}

	public PaymentAllocationBuilder setPayableDocuments(final Collection<IPayableDocument> payableDocuments)
	{
		this._payableDocuments = ImmutableList.copyOf(payableDocuments);
		return this;
	}

	public PaymentAllocationBuilder setPaymentDocuments(final Collection<IPaymentDocument> paymentDocuments)
	{
		this._paymentDocuments = ImmutableList.copyOf(paymentDocuments);
		return this;
	}

	// /** Sets invoices to allocate */
	// public PaymentAllocationBuilder setInvoicesAndPayments(final Collection<IInvoiceRow> invoiceRows, final Collection<IPaymentRow> paymentRows)
	// {
	// assertNotBuilt();
	//
	// final List<IPayableDocument> payableDocuments = new ArrayList<>();
	// final List<IPaymentDocument> paymentDocuments = new ArrayList<>();
	//
	// for (final IInvoiceRow invoiceRow : invoiceRows)
	// {
	// payableDocuments.add(invoiceRow.copyAsPayableDocument());
	// }
	//
	// for (final IPaymentRow paymentRow : paymentRows)
	// {
	// paymentDocuments.add(paymentRow.copyAsPaymentDocument());
	// }
	//
	// this._payableDocuments = ImmutableList.copyOf(payableDocuments);
	// this._paymentDocuments = ImmutableList.copyOf(paymentDocuments);
	//
	// return this;
	// }

	private final List<IPayableDocument> getPayableDocuments()
	{
		return _payableDocuments;
	}

	private final List<IPaymentDocument> getPaymentDocuments()
	{
		return _paymentDocuments;
	}
}
