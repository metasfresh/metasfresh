package de.metas.invoicecandidate.compensationGroup;

import de.metas.currency.ICurrencyDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Lets the percent discount invoice candidate of a compensation group follow partially invoiced goods.
 * <p>
 * Its price is the discount amount still open: the percentage of the goods invoiced so far plus the goods to invoice now,
 * minus what was already invoiced of the discount (see {@code C_OrderLine_Handler#calculatePriceAndTax}).
 * So each invoice carries one discount unit at the percentage of that invoice's goods, and a discount that was left off an invoice
 * follows with the next one at its full remaining amount:
 * <ul>
 * <li>{@link #updateQtyOrdered(I_C_Invoice_Candidate)}: one unit per invoice so far plus one more while the group still has goods
 * that are not fully invoiced; this keeps the candidate open (not processed) until the goods are fully invoiced.</li>
 * <li>{@link #updateQtyToInvoice(I_C_Invoice_Candidate)}: while a discount amount is open, one unit stays open for it; once the candidate
 * was invoiced, that unit is invoiceable right away. There is nothing to invoice while the open amount is zero, except for a discount that
 * was never invoiced: a 0 % discount goes with the first goods to invoice, any other one with the last goods, so that it ends processed.</li>
 * </ul>
 * Compensation lines with a fixed amount are left as they are.
 * <p>
 * Right after a partial invoice the candidate is processed until its recompute (triggered by its goods, see
 * {@link InvoiceCandidateGroupCompensationChangesHandler}) reopens it. Until then it is still marked "to recompute", and invoicing
 * waits for all selected candidates to be recomputed before it enqueues them ({@code InvoiceCandidateEnqueuer#prepareSelection}),
 * so a stalled recompute delays the next invoice instead of invoicing it without its discount.
 */
@Component
public class PercentCompensationLineInvoicing
{
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final InvoiceCandidateGroupRepository groupsRepo;

	public PercentCompensationLineInvoicing(@NonNull final InvoiceCandidateGroupRepository groupsRepo)
	{
		this.groupsRepo = groupsRepo;
	}

	/**
	 * Cheap check without DB access, so that callers need to look up this bean only for percent compensation lines.
	 */
	public static boolean isPercentCompensationLine(@NonNull final I_C_Invoice_Candidate ic)
	{
		return ic.getC_Invoice_Candidate_ID() > 0 // a new candidate can't be invoiced yet
				&& ic.isGroupCompensationLine()
				&& InvoiceCandidateCompensationGroupUtils.isInGroup(ic)
				&& GroupCompensationAmtType.Percent.getAdRefListValue().equals(ic.getGroupCompensationAmtType());
	}

	/**
	 * Expects {@code QtyOrdered} to be already set from the order line.
	 */
	public void updateQtyOrdered(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (!isPercentCompensationLine(ic) || isPriceOverridden(ic))
		{
			return; // an overridden discount is invoiced once, in full (see #computeOpenAmount)
		}

		final BigDecimal openUnit = groupsRepo.hasNotProcessedRegularInvoiceCandidates(extractGroupId(ic)) ? ONE : ZERO;
		setQtyOrdered(ic, ic.getQtyOrdered().max(ic.getQtyInvoiced().add(openUnit)));
	}

	/**
	 * Expects the calculated price (i.e. the discount amount still open), {@code QtyInvoiced}, {@code NetAmtInvoiced} and the processed flag to be up to date.
	 * <p>
	 * For a reopened unit, a user's {@code QtyToInvoice_Override} still caps what is invoiced; candidates in dispute
	 * are left out by the invoicing itself ({@code InvoiceCandBL#getInvoicingSkipReasonOrNull}), and quality issues only
	 * exist on receipts, i.e. never on a sales discount line.
	 */
	public void updateQtyToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (!isPercentCompensationLine(ic) || invoiceCandBL.extractProcessedOverride(ic).isTrue())
		{
			return; // not ours, or closed by the user
		}

		final boolean openAmount = computeOpenAmount(ic).signum() != 0;
		// QtyOrdered was set before QtyInvoiced was brought up to date; it never counts less than what is invoiced (else the candidate reads over-invoiced),
		// and while a discount amount is open (e.g. the discount was left off its goods' invoice), one more unit stays open for it, even if all goods are invoiced
		final BigDecimal minQtyOrdered = ic.getQtyInvoiced().add(openAmount ? ONE : ZERO);
		if (ic.getQtyOrdered().compareTo(minQtyOrdered) < 0)
		{
			setQtyOrdered(ic, minQtyOrdered);
			// the processed flag was already computed from the smaller QtyOrdered (InvoiceCandBL#set_QtyInvoiced_NetAmtInvoiced_Aggregation0),
			// and the interceptor does not recompute it if QtyOrdered is unchanged against the database
			invoiceCandBL.updateProcessedFlag(ic);
		}

		final boolean neverInvoiced = ic.getQtyInvoiced().signum() == 0;
		final BigDecimal qtyToInvoice;
		if (!openAmount)
		{
			if (neverInvoiced && goesWithTheseGoods(ic))
			{
				return; // e.g. a 0 % discount: the invoice rule decides, so that it is invoiced with goods and ends processed
			}
			qtyToInvoice = ZERO; // no discount amount open right now: a 0.00 discount line is not invoiced
		}
		else if (!neverInvoiced)
		{
			// the candidate's own invoice rule (e.g. its delivery) was already satisfied by an earlier invoice
			qtyToInvoice = capByQtyToInvoiceOverride(ic, ic.getQtyOrdered().subtract(ic.getQtyInvoiced()).max(ZERO));
			alignInvoiceDatesWithGoodsToInvoice(ic);
		}
		else
		{
			return; // first invoice: the invoice rule decides, as for any other candidate
		}

		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());
		final BigDecimal qtyToInvoiceInUOM = uomConversionBL.convertFromProductUOM(productId, UomId.ofRepoId(ic.getC_UOM_ID()), qtyToInvoice);
		ic.setQtyToInvoice(qtyToInvoice);
		ic.setQtyToInvoiceBeforeDiscount(qtyToInvoice);
		ic.setQtyToInvoiceInUOM_Calc(qtyToInvoiceInUOM);
		ic.setQtyToInvoiceInUOM(qtyToInvoiceInUOM);
	}

	/**
	 * The discount amount still open, from the calculated values, never from the effective price: the calculated price is the
	 * percentage of the goods invoiced so far and to invoice now, minus the discount invoiced so far (see {@code C_OrderLine_Handler#calculatePriceAndTax}).
	 * <ul>
	 * <li>A user's override is invoiced once, at its effective price, and so it is open only while nothing of the discount is invoiced;
	 * it does not follow partially invoiced goods:
	 * <ul>
	 * <li>{@code PriceEntered_Override} replaces the calculated total discount. Changing it after the discount was invoiced does not
	 * invoice the difference.</li>
	 * <li>{@code Discount_Override} alone scales the calculated open price, so it is invoiced once at that scaled open price of the
	 * invoice it goes with (e.g. of the first partial invoice's goods); later goods get no discount.</li>
	 * <li>Setting an override after a partial invoice freezes the discount at what was already invoiced; later goods get no discount.</li>
	 * <li>Removing an override brings the discount back to the calculated total: the difference (possibly a positive line, if more
	 * was invoiced than calculated) is invoiced with the next invoice, as far as the candidate may reopen (see the next point).</li>
	 * </ul></li>
	 * <li>A processed discount only reopens once goods of its group were invoiced without it (e.g. it was held back from their invoice),
	 * and never for a difference within the currency precision. So an order whose goods were always invoiced together with the discount
	 * is not reopened by rounding or by a changed percentage. Once goods were invoiced without it, that stays so: a later changed
	 * percentage then reopens the discount and invoices the difference, which is intended.</li>
	 * </ul>
	 */
	private BigDecimal computeOpenAmount(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (isPriceOverridden(ic))
		{
			final boolean nothingInvoiced = ic.getQtyInvoiced().signum() == 0 && ic.getNetAmtInvoiced().signum() == 0;
			return nothingInvoiced ? invoiceCandBL.getPriceActual(ic).toBigDecimal() : ZERO;
		}

		final BigDecimal openAmount = ic.getPriceActual();
		if (openAmount.signum() == 0 || !ic.isProcessed())
		{
			return openAmount;
		}

		final BigDecimal tolerance = ONE.movePointLeft(currencyDAO.getStdPrecision(CurrencyId.ofRepoId(ic.getC_Currency_ID())).toInt());
		if (openAmount.abs().compareTo(tolerance) <= 0
				|| !groupsRepo.hasRegularInvoiceLinesWithout(extractGroupId(ic), InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID())))
		{
			return ZERO;
		}
		return openAmount;
	}

	private static boolean isPriceOverridden(@NonNull final I_C_Invoice_Candidate ic)
	{
		return !InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override)
				|| !InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_Discount_Override);
	}

	/**
	 * A never invoiced discount without open amount goes along with goods to invoice: a 0 % discount with the first ones, so that no
	 * invoice holds only the 0.00 line; any other one (e.g. whose goods are out of its product category) only with the last ones,
	 * so that it does not carry a 0.00 line on every invoice.
	 */
	private boolean goesWithTheseGoods(@NonNull final I_C_Invoice_Candidate ic)
	{
		final GroupId groupId = extractGroupId(ic);
		if (ic.getGroupCompensationPercentage().signum() == 0 && groupsRepo.hasRegularInvoiceCandidatesToInvoice(groupId))
		{
			return true;
		}
		return !groupsRepo.hasRegularInvoiceCandidatesWithGoodsStillToCome(groupId);
	}

	private void setQtyOrdered(@NonNull final I_C_Invoice_Candidate ic, @NonNull final BigDecimal qtyOrdered)
	{
		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());
		ic.setQtyOrdered(qtyOrdered);
		ic.setQtyEntered(uomConversionBL.convertFromProductUOM(productId, UomId.ofRepoId(ic.getC_UOM_ID()), qtyOrdered));
	}

	private static BigDecimal capByQtyToInvoiceOverride(@NonNull final I_C_Invoice_Candidate ic, @NonNull final BigDecimal qtyToInvoice)
	{
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override))
		{
			return qtyToInvoice;
		}

		final BigDecimal remainingOverride = ic.getQtyToInvoice_Override().subtract(ic.getQtyToInvoice_OverrideFulfilled());
		return qtyToInvoice.min(remainingOverride).max(ZERO);
	}

	/**
	 * DateInvoiced/DateAcct are part of the header aggregation key; after its earlier invoice this candidate still carries that invoice's dates.
	 * Without aligning them, the next discount unit would not be invoiced together with its goods, but in an invoice of its own.
	 * Without goods to invoice (the discount alone, e.g. after it was held back), the dates are cleared, so that it gets the dates of any new invoice.
	 */
	private void alignInvoiceDatesWithGoodsToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		final Optional<I_C_Invoice_Candidate> goodsToInvoice = groupsRepo.retrieveFirstRegularInvoiceCandidateToInvoice(extractGroupId(ic));
		final Timestamp dateInvoiced = goodsToInvoice.map(I_C_Invoice_Candidate::getDateInvoiced).orElse(null);
		final Timestamp dateAcct = goodsToInvoice.map(I_C_Invoice_Candidate::getDateAcct).orElse(null);
		if (Objects.equals(dateInvoiced, ic.getDateInvoiced()) && Objects.equals(dateAcct, ic.getDateAcct()))
		{
			return;
		}
		ic.setDateInvoiced(dateInvoiced);
		ic.setDateAcct(dateAcct);
		aggregationBL.getUpdateProcessor().process(ic);
	}

	private GroupId extractGroupId(@NonNull final I_C_Invoice_Candidate ic)
	{
		return groupsRepo.extractGroupId(ic);
	}
}
