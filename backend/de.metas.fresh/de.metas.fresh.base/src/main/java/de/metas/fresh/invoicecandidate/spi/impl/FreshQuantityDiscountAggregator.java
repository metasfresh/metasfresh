package de.metas.fresh.invoicecandidate.spi.impl;

/*
 * #%L
 * de.metas.fresh.base
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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;

import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceLineAggregationRequest;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;

/**
 * Quanity/Quality Discount Aggregation. This aggregator's job is to customize the system's behavior for the case there there is a {@link I_C_Invoice_Candidate#COLUMN_QualityDiscountPercent_Effective}
 * that is greater than zero. In this case, the default implementation only invoices the quantity minus the quality discount and that's it. This implementation created <b>two</b> invoice lines. The
 * fist one ignores the discount and invoices whatever is the full quantity. The second line explicitly subtracts the discount quantity (line with a negative quantity).
 *
 * <p>
 * Note about the naming: this class is called Fresh<b>Quantity</b>DiscountAggregator because the discount is not a percentage on the price, but a part of the delivered quantity is not invoiced. It
 * might also be called Fresh"Quality"DiscountAggregator, because that discount is happened because of quality.
 *
 *
 */
public class FreshQuantityDiscountAggregator implements IAggregator
{
	// services
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final transient String MSG_QualityDiscount = "de.metas.fresh.invoicecandidate.QualityDiscount";
	private final Map<Integer, String> bpartnerId2descriptionPrefix = new HashMap<>();

	/**
	 * Stores ics and their icIols whose iols are in dispute. Those icIols are ignored by the default implementation, so we need to store them here, because in this implementation we want to keep
	 * track of them (to have the chance to create MatchInv and stuff).
	 */
	private Map<I_C_Invoice_Candidate, List<I_C_InvoiceCandidate_InOutLine>> ic2IndisputeIcIols = new IdentityHashMap<I_C_Invoice_Candidate, List<I_C_InvoiceCandidate_InOutLine>>();

	/**
	 * We use the default aggregator to do most of the work.
	 */
	private final DefaultAggregator defaultAggregator = new DefaultAggregator();

	public FreshQuantityDiscountAggregator()
	{
		super();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public void setContext(final Properties ctx, final String trxName)
	{
		defaultAggregator.setContext(ctx, trxName);
	}

	/**
	 * Invokes the {@link #defaultAggregator}'s <code>registerInvoiceCandidateInAggregationCtx</code> method.<br>
	 * Additionally, if the given <code>candidate</code> has a quality discount, then it is recorded in an internal list of this aggregator.
	 *
	 * @see IInvoiceCandBL#getQualityDiscountPercentEffective(I_C_Invoice_Candidate)
	 */
	@Override
	public void addInvoiceCandidate(final IInvoiceLineAggregationRequest request)
	{
		// Delegate all work to default aggregator.
		// Later, on "aggregate" we will create quality discount invoice lines, if needed.
		defaultAggregator.addInvoiceCandidate(request);

		// adding the list anyways, even if we won't ever add an icIol to it. That way we won't have to check for containsKey further down.
		List<I_C_InvoiceCandidate_InOutLine> list = ic2IndisputeIcIols.get(request.getC_Invoice_Candidate());
		if (list == null)
		{
			list = new ArrayList<I_C_InvoiceCandidate_InOutLine>();
			ic2IndisputeIcIols.put(request.getC_Invoice_Candidate(), list);
		}

		if (aggregationBL.isIolInDispute(request.getC_InvoiceCandidate_InOutLine()))
		{
			// here we actually add it to our own data structure
			list.add(request.getC_InvoiceCandidate_InOutLine());
		}
	}

	@Override
	public List<IInvoiceCandAggregate> aggregate()
	{
		// Final invoice candidates aggregates. It will contain:
		// * original invoice candidate aggregates (i.e. regular invoice lines)
		// * quality discount invoice candidate aggregates
		final List<IInvoiceCandAggregate> invoiceCandAggregates = new ArrayList<>();

		//
		// Iterate original IInvoiceCandAggregates -> I_C_Invoice_Candidates
		// and create quality discount invoice lines (if needed)
		final List<IInvoiceCandAggregate> originalInvoiceCandAggregates = defaultAggregator.aggregate();
		for (final IInvoiceCandAggregate invoiceCandAggregate : originalInvoiceCandAggregates)
		{
			//
			// Add original aggregate to our final result
			invoiceCandAggregates.add(invoiceCandAggregate);

			//
			// Create and add quality discount aggregates (if any)
			final List<IInvoiceCandAggregate> discountAggregates = createQualityDiscountAggregates(invoiceCandAggregate);
			invoiceCandAggregates.addAll(discountAggregates);

		} // for each invoice candidate aggregate

		//
		// Return the final result
		return invoiceCandAggregates;
	}

	private Set<Integer> candsSeen = new HashSet<Integer>();

	/**
	 * Create quality discount invoice line aggregates (one for each invoice candidate), if needed.
	 *
	 * NOTE: this method will also adjust the qty to invoice of the original invoice line.
	 *
	 * @param invoiceCandAggregate
	 * @return
	 */
	private List<IInvoiceCandAggregate> createQualityDiscountAggregates(final IInvoiceCandAggregate invoiceCandAggregate)
	{
		final List<IInvoiceCandAggregate> discountAggregates = new ArrayList<>();

		//
		// Check if we have to create quality discount aggregates.
		// Create those aggregates if needed and add them to our final result
		for (final I_C_Invoice_Candidate candidate : invoiceCandAggregate.getAllCands())
		{
			if (!candsSeen.add(candidate.getC_Invoice_Candidate_ID()))
			{
				continue;
			}

			//
			// Calculate quality discount quantity
			final BigDecimal qtyBeforeDiscount = candidate.getQtyToInvoiceBeforeDiscount();
			final BigDecimal qtyInvoiced = candidate.getQtyToInvoice();
			final BigDecimal qtyQualityDiscount = qtyBeforeDiscount.subtract(qtyInvoiced);

			// If there is no quantity with issues, there will be no quality discount line
			// FIXME: how shall we handle the case when qtyQualityDiscount is negative?
			if (qtyQualityDiscount.signum() == 0)
			{
				continue;
			}

			//
			// Get the original invoice line
			// Basically there shall be only one, but there is no harm if we just pick the first one if there are more.
			final List<IInvoiceLineRW> originalInvoiceLineRWs = invoiceCandAggregate.getLinesFor(candidate);
			final IInvoiceLineRW originalInvoiceLineRW = originalInvoiceLineRWs.get(0);

			//
			// Adjust the original invoice line add let it include our qty with issues.
			originalInvoiceLineRW.addQtyToInvoice(qtyQualityDiscount);
			// We also need to update the invoice line's net amount
			setNetLineAmt(originalInvoiceLineRW, invoiceCandBL.getCurrencyPrecision(candidate));
			// Update aggregate's qtyAllocated
			invoiceCandAggregate.addAllocatedQty(candidate, originalInvoiceLineRW, qtyQualityDiscount);

			//
			// Create quality discount invoice line with "minus qtyWithIssues".
			// The quality discount invoice line shall have the same attributes as the original invoice line (08642)
			final IInvoiceCandAggregate discountInvoiceCandidateAggregate = createQualityDiscountInvoiceLine(candidate,
					qtyQualityDiscount,
					originalInvoiceLineRW);
			for (final I_C_InvoiceCandidate_InOutLine icIol : ic2IndisputeIcIols.get(candidate))
			{
				originalInvoiceLineRW.getC_InvoiceCandidate_InOutLine_IDs().add(icIol.getC_InvoiceCandidate_InOutLine_ID());
			}

			discountAggregates.add(discountInvoiceCandidateAggregate);
		} // for each invoice candidate

		return discountAggregates;
	}

	/**
	 * Creates an aggregate with one {@link IInvoiceLineRW} having "minus" <code>qtyDiscount</code>.
	 *
	 * @param candidate
	 * @param qtyDiscount
	 * @param invoiceLineAttributes attributes to be used on new invoice line
	 * @return resulting aggregate; never return <code>null</code>.
	 */
	private IInvoiceCandAggregate createQualityDiscountInvoiceLine(final I_C_Invoice_Candidate candidate,
			final BigDecimal qtyDiscount,
			final IInvoiceLineRW originalInvoiceLineRW)
	{
		final BigDecimal qtyToInvoice = qtyDiscount.negate();
		final BigDecimal priceActual = invoiceCandBL.getPriceActual(candidate);
		final BigDecimal qualityDiscountPercent = getQualityDiscountPercent(candidate);

		final String descriptionPrefix = getDescriptionPrefix(candidate);
		final String description = descriptionPrefix + " " + qualityDiscountPercent + "%";

		// everything not explicitly set below will be taken from originalInvoiceLineRW, e.g. C_Tax
		final IInvoiceLineRW invoiceLine = aggregationBL.mkInvoiceLine(originalInvoiceLineRW);

		invoiceLine.setC_Charge_ID(candidate.getC_Charge_ID());
		invoiceLine.setM_Product_ID(candidate.getM_Product_ID());

		invoiceLine.setPriceActual(priceActual);
		invoiceLine.setPriceEntered(priceActual);
		invoiceLine.setDiscount(invoiceCandBL.getDiscount(candidate));
		invoiceLine.setQtyToInvoice(qtyToInvoice);

		final BigDecimal lineNetAmt = invoiceCandBL.calculateNetAmt(qtyToInvoice, priceActual, invoiceCandBL.getCurrencyPrecision(candidate));
		invoiceLine.setNetLineAmt(lineNetAmt);

		invoiceLine.setDescription(description);
		invoiceLine.setC_OrderLine_ID(candidate.getC_OrderLine_ID());

		for (final I_C_InvoiceCandidate_InOutLine icIol : ic2IndisputeIcIols.get(candidate))
		{
			invoiceLine.getC_InvoiceCandidate_InOutLine_IDs().add(icIol.getC_InvoiceCandidate_InOutLine_ID());
		}

		//
		// Create a new aggregate with our discount invoice line and return it
		final IInvoiceCandAggregate invoiceCandAggregate = aggregationBL.mkInvoiceCandAggregate();
		invoiceCandAggregate.addAssociation(candidate, invoiceLine);
		return invoiceCandAggregate;
	}

	private final BigDecimal getQualityDiscountPercent(final I_C_Invoice_Candidate candidate)
	{
		final BigDecimal qualityDiscoutPercent = invoiceCandBL.getQualityDiscountPercentEffective(candidate);

		return qualityDiscoutPercent.setScale(2, RoundingMode.HALF_UP); // make sure the number looks nice
	}

	/**
	 * Gets description prefix to be used when creating an invoice line for given invoice candidate.
	 *
	 * @param candidate
	 * @return
	 */
	private final String getDescriptionPrefix(final I_C_Invoice_Candidate candidate)
	{
		final int bpartnerId = candidate.getBill_BPartner_ID();

		String descriptionPrefix = bpartnerId2descriptionPrefix.get(bpartnerId);

		// Build descriptionPrefix if not already built
		if (descriptionPrefix == null)
		{
			final String adLanguage = candidate.getBill_BPartner().getAD_Language();
			descriptionPrefix = msgBL.getMsg(adLanguage, MSG_QualityDiscount, new Object[] {});

			bpartnerId2descriptionPrefix.put(bpartnerId, descriptionPrefix);
		}

		return descriptionPrefix;
	}

	private final void setNetLineAmt(final IInvoiceLineRW invoiceLine, final int currencyPrecision)
	{
		BigDecimal qtyToInvoice = invoiceLine.getQtyToInvoice();
		BigDecimal priceActual = invoiceLine.getPriceActual();
		final BigDecimal lineNetAmt = invoiceCandBL.calculateNetAmt(qtyToInvoice, priceActual, currencyPrecision);
		invoiceLine.setNetLineAmt(lineNetAmt);
	}
}
