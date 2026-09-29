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
 * Lets the percent discount invoice candidate of a contract-created compensation group follow partially invoiced goods.
 * <p>
 * Its price is repriced to the percentage of the goods currently to invoice (see {@code C_OrderLine_Handler#calculatePriceAndTax}),
 * so each invoice carries one discount unit at the percentage of that invoice's goods:
 * <ul>
 * <li>{@link #updateQtyOrdered(I_C_Invoice_Candidate)}: one unit per invoice so far plus one more while the group still has goods
 * that are not fully invoiced; this keeps the candidate open (not processed) until the goods are fully invoiced.</li>
 * <li>{@link #updateQtyToInvoice(I_C_Invoice_Candidate)}: once the candidate was invoiced, the next unit is invoiceable as soon as
 * there is a discount amount again; there is never anything to invoice while the discount amount is zero.</li>
 * </ul>
 * Compensation groups that were not created by a contract keep their behaviour.
 */
@Component
public class ContractCompensationLineInvoicing
{
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
	private final InvoiceCandidateGroupRepository groupsRepo;

	public ContractCompensationLineInvoicing(@NonNull final InvoiceCandidateGroupRepository groupsRepo)
	{
		this.groupsRepo = groupsRepo;
	}

	/**
	 * Cheap pre-check without DB access, so that callers need to look up this bean only for percent compensation lines.
	 */
	public static boolean isPercentCompensationLine(@NonNull final I_C_Invoice_Candidate ic)
	{
		return ic.getC_Invoice_Candidate_ID() > 0 // a new candidate can't be invoiced yet
				&& ic.isGroupCompensationLine()
				&& InvoiceCandidateCompensationGroupUtils.isInGroup(ic)
				&& GroupCompensationAmtType.Percent.getAdRefListValue().equals(ic.getGroupCompensationAmtType());
	}

	public boolean isContractPercentCompensationLine(@NonNull final I_C_Invoice_Candidate ic)
	{
		return isPercentCompensationLine(ic) && groupsRepo.isContractCreatedGroup(extractGroupId(ic));
	}

	/**
	 * Expects {@code QtyOrdered} to be already set from the order line.
	 */
	public void updateQtyOrdered(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (!isContractPercentCompensationLine(ic))
		{
			return;
		}

		final BigDecimal openUnit = groupsRepo.hasNotProcessedRegularInvoiceCandidates(extractGroupId(ic)) ? ONE : ZERO;
		final BigDecimal qtyOrdered = ic.getQtyOrdered().max(ic.getQtyInvoiced().add(openUnit));

		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());
		ic.setQtyOrdered(qtyOrdered);
		ic.setQtyEntered(uomConversionBL.convertFromProductUOM(productId, UomId.ofRepoId(ic.getC_UOM_ID()), qtyOrdered));
	}

	/**
	 * Expects the price, {@code QtyInvoiced} and the processed flag to be up to date.
	 */
	public void updateQtyToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (ic.isProcessed() || !isContractPercentCompensationLine(ic))
		{
			return;
		}

		final BigDecimal qtyToInvoice;
		if (invoiceCandBL.getPriceActual(ic).toBigDecimal().signum() == 0)
		{
			qtyToInvoice = ZERO; // no goods to invoice right now: a 0.00 discount line shall never be invoiced
		}
		else if (ic.getQtyInvoiced().signum() != 0)
		{
			// the candidate's own invoice rule (e.g. its delivery) was already satisfied by an earlier invoice
			qtyToInvoice = ic.getQtyOrdered().subtract(ic.getQtyInvoiced()).max(ZERO);
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
