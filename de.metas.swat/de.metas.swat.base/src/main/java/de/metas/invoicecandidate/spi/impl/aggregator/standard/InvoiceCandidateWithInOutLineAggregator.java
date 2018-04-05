package de.metas.invoicecandidate.spi.impl.aggregator.standard;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InventoryLine;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.InvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.exceptions.InvalidQtyForPartialAmtToInvoiceException;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Aggregates {@link InvoiceCandidateWithInOutLine}s and creates one {@link IInvoiceCandAggregate}.
 *
 * @author tsa
 *
 */
/* package */class InvoiceCandidateWithInOutLineAggregator
{
	// Services
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IAggregationBL aggregationBL = Services.get(IAggregationBL.class);

	// Parameters
	@ToStringBuilder(skip = true)
	private IdentityHashMap<I_C_Invoice_Candidate, BigDecimal> _ic2QtyInvoiceable;

	// State variables
	private boolean _initialized = false;
	private I_C_Invoice_Candidate _firstCand;
	private int _productId = -1;
	private int _chargeId = -1;
	private BigDecimal _priceActual = null;
	private BigDecimal _priceEntered = null;
	private BigDecimal _discount = null;
	private BigDecimal _qtyToInvoice = BigDecimal.ZERO;
	private BigDecimal _netLineAmt = BigDecimal.ZERO;
	private int _orderLineId = -1; // -1 means "didn't yet try to set", -2 means "cannot set"
	private boolean _printed = false;
	private int _invoiceLineNo = 0;
	private final IInvoiceLineAttributeAggregator invoiceLineAttributesAggregator = new CommonInvoiceLineAttributeAggregator();

	//
	private boolean _hasAtLeastOneValidICS = false;
	private final Collection<Integer> _iciolIds = new HashSet<>();
	private final List<InvoiceCandidateWithInOutLine> _invoiceCandidateWithInOutLines = new ArrayList<>();
	private final List<IInvoiceCandidateInOutLineToUpdate> _iciolsToUpdate = new ArrayList<>();

	public InvoiceCandidateWithInOutLineAggregator()
	{
		super();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void setInvoiceableQtys(final IdentityHashMap<I_C_Invoice_Candidate, BigDecimal> ic2QtyInvoiceable)
	{
		Check.assumeNotNull(ic2QtyInvoiceable, "ic2QtyInvoiceable not null");
		this._ic2QtyInvoiceable = ic2QtyInvoiceable;
	}

	/**
	 * Aggregates collected {@link InvoiceCandidateWithInOutLine}s.
	 *
	 * @return invoice candidate aggregate; never return <code>null</code>
	 */
	public IInvoiceCandAggregate aggregate()
	{
		// Create the invoice line POJO
		final IInvoiceLineRW invoiceLine = createInvoiceLineRW();

		//
		// Create an invoice candidate aggregate which links each invoice candidate that we got to our newly created invoice line POJO
		final IInvoiceCandAggregate invoiceCandAggregate = aggregationBL.mkInvoiceCandAggregate();
		for (final InvoiceCandidateWithInOutLine iciol : _invoiceCandidateWithInOutLines)
		{
			final I_C_Invoice_Candidate ic = iciol.getC_Invoice_Candidate();
			invoiceCandAggregate.addAssociationIfNotExists(ic, invoiceLine);
		}

		return invoiceCandAggregate;
	}

	private final IInvoiceLineRW createInvoiceLineRW()
	{
		//
		// We will skip any IInvoiceLineRW creation if there is no valid ICS found on this key
		Check.assume(hasAtLeastOneValidICS(), "Aggregation shall contain at least one valid IC-IOL: {}", this);

		final IInvoiceLineRW invoiceLine = aggregationBL.mkInvoiceLine();
		final int invoiceLineNo = getInvoiceLineNo();
		if (invoiceLineNo > 0)
		{
			invoiceLine.setLineNo(invoiceLineNo);
		}
		invoiceLine.setPrinted(isPrinted());
		invoiceLine.setC_Charge_ID(getC_Charge_ID());
		invoiceLine.setM_Product_ID(getM_Product_ID());
		invoiceLine.setInvoiceLineAttributes(getInvoiceLineAttributes());
		invoiceLine.setPriceActual(getPriceActual());
		invoiceLine.setPriceEntered(getPriceEntered());
		invoiceLine.setDiscount(getDiscount());
		invoiceLine.setQtyToInvoice(getQtyToInvoice());
		invoiceLine.setNetLineAmt(getLineNetAmt());
		invoiceLine.setDescription(getDescription());

		// Note: when creating the actual invoiceLine, only positive IDs will be considered
		invoiceLine.setC_OrderLine_ID(getC_OrderLine_ID());

		invoiceLine.getC_InvoiceCandidate_InOutLine_IDs().addAll(_iciolIds);
		invoiceLine.getInvoiceCandidateInOutLinesToUpdate().addAll(getInvoiceCandidateInOutLinesToUpdate());

		// 07442: also add tax and activity
		invoiceLine.setC_Activity_ID(getC_Activity_ID());
		invoiceLine.setC_Tax(getC_Tax());

		invoiceLine.setC_PaymentTerm_ID(getC_PaymentTerm_ID());
		return invoiceLine;
	}

	public void addInvoiceCandidateWithInOutLines(final List<InvoiceCandidateWithInOutLine> icsCollection)
	{
		icsCollection.forEach(ics -> addInvoiceCandidateWithInOutLine(ics));
	}

	private void addInvoiceCandidateWithInOutLine(final InvoiceCandidateWithInOutLine ics)
	{
		Check.assumeNotNull(ics, "ics not null");
		initializeIfNeeded(ics);

		final boolean shipped = ics.isShipped();
		final BigDecimal qtyAlreadyShippedPerCurrentICS = ics.getQtyAlreadyShipped();
		final BigDecimal qtyAlreadyInvoicedPerCurrentICS = ics.getQtyAlreadyInvoiced();

		// task 08606: if we need to allocate the full remaining qty we can't take care to stay within the limits of the current iol's shipped quantity
		final boolean stayWithinShippedQty = !ics.isAllocateRemainingQty();

		//
		// we introduce a multiplier that can be 1 or -1. We will apply the factor in comparisons. If we deal with negative shipped quantities (RMA), then this way we still use the same
		// comparisons
		// we do for positive quantities
		final BigDecimal factor;
		final boolean positiveQty = qtyAlreadyShippedPerCurrentICS.signum() >= 0; // NOTE: we also consider ZERO as positive
		if (positiveQty)
		{
			factor = BigDecimal.ONE;
		}
		else
		{
			factor = BigDecimal.ONE.negate();
		}

		final I_C_Invoice_Candidate cand = ics.getC_Invoice_Candidate();

		//
		// old comment from task 06630: We do not wish to create a line for candidates which don't have the QtyToInvoice set; skip them silently (they should be handled elsewhere)
		// task 07372: yes we do; if there is 0 Qty of scrap, we want to document that on the invoice.
		// also note that lines with QtyOrdered == QtyInvoices == 0 are not set to processed anymore, unless they have an invoice
		//@formatter:off
		// keeping this commented-out code for now because there might be a forgotten but valid reason behind it
		//				if (candQtyToInvoiceInitial.signum() == 0)
		//				{
		//					continue;
		//				}
		//@formatter:on

		//
		// Get quantity left to be invoiced
		final BigDecimal qtyLeftToInvoice = getQtyInvoiceable(cand);

		// #1604
		// if we deal with a material disposal, this qtyLeftToInvoice is acceptable
		final boolean isMaterialDisposalIC = ics.getC_Invoice_Candidate().getAD_Table_ID() == InterfaceWrapperHelper.getTableId(I_M_InventoryLine.class);

		if (qtyLeftToInvoice.multiply(factor).signum() <= 0 && !isMaterialDisposalIC)
		{
			// if we already invoiced the whole qty for this IC, then we also skip
			return;
		}

		//
		// Calculate how much we can MAXIMUM invoice for current invoice candidate line
		final BigDecimal maxQtyToInvoicePerLine;
		// If qty was shipped (i.e. EXISTS IC-IOL)
		if (shipped && stayWithinShippedQty)
		{
			final boolean alreadyInvoicedFullShippedQty = qtyAlreadyShippedPerCurrentICS.multiply(factor).compareTo(qtyAlreadyInvoicedPerCurrentICS.multiply(factor)) <= 0;
			if (alreadyInvoicedFullShippedQty)
			{
				// If we already invoiced the full shipped qty, then skip this ICS
				return;
			}
			else
			{
				// For partially invoiced shipment/receipt line
				final BigDecimal qtyShippedButNotInvoiced = qtyAlreadyShippedPerCurrentICS.subtract(qtyAlreadyInvoicedPerCurrentICS);
				if (positiveQty)
				{
					// e.g. qtyShippedButNotInvoiced = 50 and qtyLeft = 40 => maxQtyToInvoicePerLine = 40
					maxQtyToInvoicePerLine = qtyShippedButNotInvoiced.min(qtyLeftToInvoice);
				}
				else
				{
					// e.g. qtyShippedButNotInvoiced = -50 and qtyLeft = -40 => maxQtyToInvoicePerLine = -40
					maxQtyToInvoicePerLine = qtyShippedButNotInvoiced.max(qtyLeftToInvoice);
				}
			}
		}
		else
		{
			// We can invoice everything if we don't have an IC-IOL association
			maxQtyToInvoicePerLine = qtyLeftToInvoice;
		}

		//
		// Calculate how much we can invoice for current invoice candidate line,
		// without checking the upper limit (see maxQtyToInvoicePerLine).
		final BigDecimal candQtyToInvoiceUnchecked;
		if (shipped && stayWithinShippedQty)
		{
			//
			// We don't want to invoice more than shipped
			if (positiveQty)
			{
				candQtyToInvoiceUnchecked = qtyLeftToInvoice.min(qtyAlreadyShippedPerCurrentICS);
			}
			else
			{
				candQtyToInvoiceUnchecked = qtyLeftToInvoice.max(qtyAlreadyShippedPerCurrentICS);
			}
		}
		else
		{
			//
			// No shipment; just use the candidate qty & net amount
			candQtyToInvoiceUnchecked = qtyLeftToInvoice;
		}

		//
		// Calculate how much we can invoice for this invoice candidate (final result)
		// i.e. if the "candQtyToInvoiceUnchecked" does not fit within the calculated limits, use max value
		final BigDecimal candQtyToInvoiceFinal;
		final boolean doesNofitWithinLimits = maxQtyToInvoicePerLine.compareTo(candQtyToInvoiceUnchecked) < 0;
		if (doesNofitWithinLimits && stayWithinShippedQty)
		{
			candQtyToInvoiceFinal = maxQtyToInvoicePerLine;
		}
		else
		{
			candQtyToInvoiceFinal = candQtyToInvoiceUnchecked;
		}

		final boolean forcedAdditionalQty = false;
		setAdditionalStuff(ics, candQtyToInvoiceFinal, forcedAdditionalQty);

		//
		// Collect our invoice candidate with inout line
		_invoiceCandidateWithInOutLines.add(ics);
		// Collect C_InvoiceCandidate_InOutLine_ID
		final I_C_InvoiceCandidate_InOutLine iciol = ics.getC_InvoiceCandidate_InOutLine();
		if (iciol != null)
		{
			_iciolIds.add(iciol.getC_InvoiceCandidate_InOutLine_ID());
		}
		//
		_hasAtLeastOneValidICS = true;
	}

	private void setAdditionalStuff(final InvoiceCandidateWithInOutLine ics, final BigDecimal candQtyToInvoiceFinal, final boolean forcedAdditionalQty)
	{
		final I_C_Invoice_Candidate cand = ics.getC_Invoice_Candidate();

		//
		// Calculate PriceActual and NetAmount depending on whether is Amount based invoicing or Qty based invoicing
		final BigDecimal candPriceActual;
		final BigDecimal candNetAmtToInvoice;
		if (isAmountBasedInvoicing(cand))
		{
			final BigDecimal candNetAmtToInvoiceOrig = cand.getNetAmtToInvoice();
			final BigDecimal candNetAmtToInvoiceCalc = invoiceCandBL.calculateNetAmt(cand);

			candNetAmtToInvoice = candNetAmtToInvoiceOrig;

			if (candNetAmtToInvoiceOrig.compareTo(candNetAmtToInvoiceCalc) != 0)
			{
				if (BigDecimal.ONE.compareTo(candQtyToInvoiceFinal) != 0)
				{
					throw new InvalidQtyForPartialAmtToInvoiceException(candQtyToInvoiceFinal, cand, candNetAmtToInvoiceOrig, candNetAmtToInvoiceCalc);
				}
				candPriceActual = candNetAmtToInvoiceOrig;
			}
			else
			{
				candPriceActual = invoiceCandBL.getPriceActual(cand);
			}
		}
		else
		{
			final int currencyPrecision = invoiceCandBL.getPrecisionFromCurrency(cand);
			// 07202: Make sure we adapt the quantity we use to the product and price UOM of the candidate.
			final BigDecimal candQtyToInvoiceFinalInPriceUOM = invoiceCandBL.convertToPriceUOM(candQtyToInvoiceFinal, cand);
			candPriceActual = invoiceCandBL.getPriceActual(cand);
			candNetAmtToInvoice = invoiceCandBL.calculateNetAmt(candQtyToInvoiceFinalInPriceUOM, candPriceActual, currencyPrecision);
		}
		setPriceActual(candPriceActual);

		//
		// Set/validate overall "PriceEntered"
		final BigDecimal candPriceEntered = invoiceCandBL.getPriceEntered(cand);
		setPriceEntered(candPriceEntered);

		//
		// Set/validate overall Discount
		final BigDecimal candDiscount = invoiceCandBL.getDiscount(cand);
		setDiscount(candDiscount);

		//
		// Misc
		setC_OrderLine_ID(cand.getC_OrderLine_ID()); // overall C_OrderLine_ID
		setPrinted(cand.isPrinted());
		setInvoiceLineNo(cand.getLine());

		//
		// Collect invoice line product attributes
		addInvoiceLineAttributes(ics.getInvoiceLineAttributes());

		//
		// Add QtyToInvoice and LineNetAmount from this candidate
		addQtyToInvoice(candQtyToInvoiceFinal, ics, forcedAdditionalQty);
		addLineNetAmount(candNetAmtToInvoice);
	}

	private final void initializeIfNeeded(final InvoiceCandidateWithInOutLine ics)
	{
		if (_initialized)
		{
			return;
		}

		// Make sure this aggregator/builder was correctly configured
		Check.assumeNotNull(_ic2QtyInvoiceable, "_ic2QtyInvoiceable not null");

		_firstCand = ics.getC_Invoice_Candidate();

		//
		// Get the M_Product_ID/C_Charge_ID to use
		if (_firstCand.getC_Invoice_Candidate_Agg_ID() > 0 && _firstCand.getC_Invoice_Candidate_Agg().getM_ProductGroup_ID() > 0)
		{
			//
			// We use the proxy product that is defined in the C_Invoice_Candidate_Agg
			_productId = _firstCand.getC_Invoice_Candidate_Agg().getM_ProductGroup().getM_Product_Proxy_ID();
			_chargeId = 0;
		}
		else
		{
			_productId = _firstCand.getM_Product_ID();
			_chargeId = _firstCand.getC_Charge_ID();
		}

		//
		// Misc invoice candidate settings
		_printed = _firstCand.isPrinted();
		_invoiceLineNo = _firstCand.getLine();

		// Flag it as initialized
		_initialized = true;
	}

	private final I_C_Invoice_Candidate getFirstInvoiceCandidate()
	{
		Check.assumeNotNull(_firstCand, "_firstCand not null");
		return _firstCand;
	}

	private final void setPriceActual(final BigDecimal candPriceActual)
	{
		Check.assumeNotNull(candPriceActual, "candPriceActual not null");
		if (_priceActual == null)
		{
			_priceActual = candPriceActual;
		}
		else
		{
			Check.assume(_priceActual.compareTo(candPriceActual) == 0,
					"All invoice candidates from this aggregation shall have the same PriceActual={} but got PriceActual={}",
					_priceActual, candPriceActual);
		}
	}

	private final BigDecimal getPriceActual()
	{
		Check.assumeNotNull(_priceActual, "_priceActual not null");
		return _priceActual;
	}

	private final void setPriceEntered(final BigDecimal candPriceEntered)
	{
		Check.assumeNotNull(candPriceEntered, "candPriceEntered not null");
		if (_priceEntered == null)
		{
			_priceEntered = candPriceEntered;
		}
		else
		{
			Check.assume(_priceEntered.compareTo(candPriceEntered) == 0,
					"All invoice candidates from this aggregation shall have the same PriceEntered={}",
					_priceEntered);
		}
	}

	private final BigDecimal getPriceEntered()
	{
		Check.assumeNotNull(_priceEntered, "_priceEntered not null");
		return _priceEntered;
	}

	private final void setDiscount(final BigDecimal candDiscount)
	{
		Check.assumeNotNull(candDiscount, "candDiscount not null");
		if (_discount == null)
		{
			_discount = candDiscount;
		}
		else
		{
			Check.assume(_discount.compareTo(candDiscount) == 0,
					"All invoice candidates from this aggregation shall have the same Discount={}",
					_discount);
		}
	}

	private final BigDecimal getDiscount()
	{
		Check.assumeNotNull(_discount, "_discount not null");
		return _discount;
	}

	private final void setC_OrderLine_ID(final int candOrderLineId)
	{
		//
		// We will retain the 1:n relation between C_OrderLine and C_InvoiceLine,
		// *unless* invoice candidates with different C_OrderLine_IDs are aggregated into the same C_InvoiceLine
		if (_orderLineId == -1)
		{
			_orderLineId = candOrderLineId;
		}
		else if (_orderLineId > 0 && _orderLineId != candOrderLineId)
		{
			_orderLineId = -2; // -2 means "cannot set"
		}
	}

	private int getC_OrderLine_ID()
	{
		return _orderLineId <= 0 ? -1 : _orderLineId;
	}

	private void setPrinted(final boolean candPrinted)
	{
		// Validate Printed flag
		Check.assume(_printed == candPrinted, "All invoice candidates shall have the same Printed={}", _printed);
	}

	private boolean isPrinted()
	{
		return _printed;
	}

	private void setInvoiceLineNo(final int candInvoiceLineNo)
	{
		// Validate LineNo
		Check.assume(_invoiceLineNo == candInvoiceLineNo, "All invoice candidates shall have the same InvoiceLineNo={}", _invoiceLineNo);
	}

	private final int getInvoiceLineNo()
	{
		return _invoiceLineNo;
	}

	private final int getM_Product_ID()
	{
		return _productId;
	}

	private final int getC_Charge_ID()
	{
		return _chargeId;
	}

	private Set<IInvoiceLineAttribute> getInvoiceLineAttributes()
	{
		return invoiceLineAttributesAggregator.aggregate();
	}

	private void addInvoiceLineAttributes(final Set<IInvoiceLineAttribute> invoiceLineAttributes)
	{
		invoiceLineAttributesAggregator.addAll(invoiceLineAttributes);
	}

	private final BigDecimal getQtyToInvoice()
	{
		return _qtyToInvoice;
	}

	/** @return line net amount to invoice */
	private final BigDecimal getLineNetAmt()
	{
		return _netLineAmt;
	}

	private final String getDescription()
	{
		return getFirstInvoiceCandidate().getDescription();
	}

	private final int getC_Activity_ID()
	{
		return getFirstInvoiceCandidate().getC_Activity_ID();
	}

	private int getC_PaymentTerm_ID()
	{
		return InterfaceWrapperHelper.getValueOverrideOrValue(
				getFirstInvoiceCandidate(),
				I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_ID);
	}

	/** @return effective tax to use in invoice line */
	private final I_C_Tax getC_Tax()
	{
		final I_C_Invoice_Candidate firstCand = getFirstInvoiceCandidate();
		return invoiceCandBL.getTaxEffective(firstCand);
	}

	public List<IInvoiceCandidateInOutLineToUpdate> getInvoiceCandidateInOutLinesToUpdate()
	{
		return _iciolsToUpdate;
	}

	private final void addQtyToInvoice(final BigDecimal candQtyToInvoice, final InvoiceCandidateWithInOutLine fromICS, final boolean forcedAdditionalQty)
	{
		// Increase the IC-IOL's QtyInvoiced
		final I_C_InvoiceCandidate_InOutLine iciol = fromICS.getC_InvoiceCandidate_InOutLine();
		if (iciol != null && !forcedAdditionalQty)
		{
			final BigDecimal qtyAlreadyInvoiced = fromICS.getQtyAlreadyInvoiced();
			final BigDecimal qtyAlreadyInvoicedNew = qtyAlreadyInvoiced.add(candQtyToInvoice);
			// task 07988: *don't* store/persist anything in here..just add, so it will be persisted later when the actual invoice was created
			final IInvoiceCandidateInOutLineToUpdate invoiceCandidateInOutLineToUpdate = new InvoiceCandidateInOutLineToUpdate(iciol, qtyAlreadyInvoicedNew);
			_iciolsToUpdate.add(invoiceCandidateInOutLineToUpdate);
		}

		//
		// Increase QtyToInvoice
		_qtyToInvoice = _qtyToInvoice.add(candQtyToInvoice);

		//
		// Update IC-QtyInvoiceable map (i.e. decrease invoiceable quantity)
		{
			final I_C_Invoice_Candidate cand = fromICS.getC_Invoice_Candidate();
			subtractQtyInvoiceable(cand, candQtyToInvoice);
		}
	}

	private final void subtractQtyInvoiceable(final I_C_Invoice_Candidate ic, final BigDecimal qtyInvoiced)
	{
		final BigDecimal qtyInvoiceable = _ic2QtyInvoiceable.get(ic);
		final BigDecimal qtyInvoiceableNew = qtyInvoiceable.subtract(qtyInvoiced);
		_ic2QtyInvoiceable.put(ic, qtyInvoiceableNew);
	}

	private BigDecimal getQtyInvoiceable(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal qtyInvoiceable = _ic2QtyInvoiceable.get(ic);
		Check.assumeNotNull(qtyInvoiceable, "qtyInvoiceable not null for {}", ic);
		return qtyInvoiceable;
	}

	private void addLineNetAmount(final BigDecimal candLineNetAmt)
	{
		_netLineAmt = _netLineAmt.add(candLineNetAmt);
	}

	public boolean hasAtLeastOneValidICS()
	{
		return _hasAtLeastOneValidICS;
	}

	/**
	 * Checks if given invoice candidate is amount based invoicing (i.e. NOT quantity based invoicing).
	 *
	 * TODO: find a better way to track this. Consider having a field in C_Invoice_Candidate.
	 *
	 * To track where it's used, search also for {@link InvalidQtyForPartialAmtToInvoiceException}.
	 *
	 * @param cand
	 * @return
	 */
	private boolean isAmountBasedInvoicing(final I_C_Invoice_Candidate cand)
	{
		// We can consider manual invoices as amount based invoices
		if (cand.isManual())
		{
			return true;
		}

		// We can consider those candidates which have a SplitAmt set to be amount based invoices
		final BigDecimal splitAmt = cand.getSplitAmt();
		if (splitAmt.signum() != 0)
		{
			return true;
		}

		// More ideas to check:
		// * UOM shall be stuck?!

		return false;
	}
}
