package de.metas.invoicecandidate.compensationGroup;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
		if (!isPercentCompensationLine(ic))
		{
			return;
		}

		final BigDecimal openUnit = groupsRepo.hasNotProcessedRegularInvoiceCandidates(extractGroupId(ic)) ? ONE : ZERO;
		setQtyOrdered(ic, ic.getQtyOrdered().max(ic.getQtyInvoiced().add(openUnit)));
	}

	/**
	 * Expects the price (i.e. the discount amount still open) and {@code QtyInvoiced} to be up to date.
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

		final boolean openAmount = invoiceCandBL.getPriceActual(ic).toBigDecimal().signum() != 0;
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
	 */
	private void alignInvoiceDatesWithGoodsToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		groupsRepo.retrieveFirstRegularInvoiceCandidateToInvoice(extractGroupId(ic))
				.ifPresent(goodsToInvoice -> {
					ic.setDateInvoiced(goodsToInvoice.getDateInvoiced());
					ic.setDateAcct(goodsToInvoice.getDateAcct());
					aggregationBL.getUpdateProcessor().process(ic);
				});
	}

	private GroupId extractGroupId(@NonNull final I_C_Invoice_Candidate ic)
	{
		return groupsRepo.extractGroupId(ic);
	}
}
