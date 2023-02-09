package de.metas.banking.payment.paymentallocation.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.banking.base
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

final class AllocationLineCandidateSaver
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

	public ImmutableMap<PaymentAllocationId, AllocationLineCandidate> save(final List<AllocationLineCandidate> candidates)
	{
		return trxManager.callInThreadInheritedTrx(() -> saveInTrx(candidates));
	}

	private ImmutableMap<PaymentAllocationId, AllocationLineCandidate> saveInTrx(final List<AllocationLineCandidate> candidates)
	{
		final ImmutableMap.Builder<PaymentAllocationId, AllocationLineCandidate> candidatesByPaymentId = ImmutableMap.builder();

		for (final AllocationLineCandidate candidate : candidates)
		{
			final PaymentAllocationId paymentAllocationId = saveCandidate(candidate);
			if (paymentAllocationId != null)
			{
				candidatesByPaymentId.put(paymentAllocationId, candidate);
			}
		}

		return candidatesByPaymentId.build();
	}

	private PaymentAllocationId saveCandidate(final AllocationLineCandidate candidate)
	{
		final AllocationLineCandidateType type = candidate.getType();
		if (AllocationLineCandidateType.InvoiceToPayment.equals(type))
		{
			return saveCandidate_InvoiceToPayment(candidate);
		}
		else if (AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice.equals(type))
		{
			return saveCandidate_InvoiceToInvoice(candidate);
		}
		else if (AllocationLineCandidateType.InvoiceToCreditMemo.equals(type))
		{
			return saveCandidate_InvoiceToInvoice(candidate);
		}
		else if (AllocationLineCandidateType.InvoiceDiscountOrWriteOff.equals(type))
		{
			return saveCandidate_InvoiceDiscountOrWriteOff(candidate);
		}
		else if (AllocationLineCandidateType.InvoiceProcessingFee.equals(type))
		{
			throw new AdempiereException("Cannot save InvoiceProcessingFee directly. Convert it to a different AllocationLineCandidateType first.")
					.setParameter("candidate", candidate)
					.appendParametersToMessage();
		}
		else if (AllocationLineCandidateType.InboundPaymentToOutboundPayment.equals(type))
		{
			return saveCandidate_InboundPaymentToOutboundPayment(candidate);
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}
	}

	@Nullable
	private PaymentAllocationId saveCandidate_InvoiceToPayment(@NonNull final AllocationLineCandidate candidate)
	{
		final Money payAmt = candidate.getAmounts().getPayAmt();
		final Money discountAmt = candidate.getAmounts().getDiscountAmt();
		final Money writeOffAmt = candidate.getAmounts().getWriteOffAmt();

		Check.assumeEquals(candidate.getAmounts(), AllocationAmounts.builder()
				.payAmt(payAmt)
				.discountAmt(discountAmt)
				.writeOffAmt(writeOffAmt)
				.build());

		final C_AllocationHdr_Builder allocationBuilder = newC_AllocationHdr_Builder(candidate);

		allocationBuilder.addLine()
				.skipIfAllAmountsAreZero()
				//
				.orgId(candidate.getOrgId())
				.bpartnerId(candidate.getBpartnerId())
				//
				// Amounts
				.amount(payAmt.toBigDecimal())
				.discountAmt(discountAmt.toBigDecimal())
				.writeOffAmt(writeOffAmt.toBigDecimal())
				.overUnderAmt(candidate.getPayableOverUnderAmt().toBigDecimal())
				//
				.invoiceId(extractInvoiceId(candidate.getPayableDocumentRef()))
				.paymentId(extractPaymentId(candidate.getPaymentDocumentRef()));

		return createAndComplete(allocationBuilder);
	}

	@Nullable
	private PaymentAllocationId saveCandidate_InvoiceToInvoice(@NonNull final AllocationLineCandidate candidate)
	{
		final Money payAmt = candidate.getAmounts().getPayAmt();
		final Money discountAmt = candidate.getAmounts().getDiscountAmt();
		final Money writeOffAmt = candidate.getAmounts().getWriteOffAmt();

		Check.assumeEquals(candidate.getAmounts(), AllocationAmounts.builder()
				.payAmt(payAmt)
				.discountAmt(discountAmt)
				.writeOffAmt(writeOffAmt)
				.build());

		final C_AllocationHdr_Builder allocationBuilder = newC_AllocationHdr_Builder(candidate);

		// Sales/Purchase invoice
		allocationBuilder.addLine()
				.skipIfAllAmountsAreZero()
				//
				.orgId(candidate.getOrgId())
				.bpartnerId(candidate.getBpartnerId())
				//
				// Amounts
				.amount(payAmt.toBigDecimal())
				.discountAmt(discountAmt.toBigDecimal())
				.writeOffAmt(writeOffAmt.toBigDecimal())
				.overUnderAmt(candidate.getPayableOverUnderAmt().toBigDecimal())
				//
				.invoiceId(extractInvoiceId(candidate.getPayableDocumentRef()));

		// Credit memo line
		// or Purchase/Sales invoice line
		allocationBuilder.addLine()
				.skipIfAllAmountsAreZero()
				//
				.orgId(candidate.getOrgId())
				.bpartnerId(candidate.getBpartnerId())
				//
				// Amounts
				.amount(payAmt.negate().toBigDecimal())
				.overUnderAmt(candidate.getPaymentOverUnderAmt().toBigDecimal())
				.discountAmt(Optional.ofNullable(candidate.getPayAmtDiscountInInvoiceCurrency())
									 .map(Money::toBigDecimal)
									 .orElse(BigDecimal.ZERO))
				//
				.invoiceId(extractInvoiceId(candidate.getPaymentDocumentRef()));

		return createAndComplete(allocationBuilder);
	}

	private PaymentAllocationId saveCandidate_InvoiceDiscountOrWriteOff(@NonNull final AllocationLineCandidate candidate)
	{
		final Money discountAmt = candidate.getAmounts().getDiscountAmt();
		final Money writeOffAmt = candidate.getAmounts().getWriteOffAmt();

		Check.assumeEquals(candidate.getAmounts(), AllocationAmounts.builder()
				.discountAmt(discountAmt)
				.writeOffAmt(writeOffAmt)
				.build());

		final C_AllocationHdr_Builder allocationBuilder = newC_AllocationHdr_Builder(candidate);

		allocationBuilder.addLine()
				.skipIfAllAmountsAreZero()
				//
				.orgId(candidate.getOrgId())
				.bpartnerId(candidate.getBpartnerId())
				//
				// Amounts
				.amount(BigDecimal.ZERO)
				.discountAmt(discountAmt.toBigDecimal())
				.writeOffAmt(writeOffAmt.toBigDecimal())
				.overUnderAmt(candidate.getPayableOverUnderAmt().toBigDecimal())
				//
				.invoiceId(extractInvoiceId(candidate.getPayableDocumentRef()));

		return createAndComplete(allocationBuilder);
	}

	private PaymentAllocationId saveCandidate_InboundPaymentToOutboundPayment(AllocationLineCandidate candidate)
	{
		final Money payAmt = candidate.getAmounts().getPayAmt();

		Check.assumeEquals(candidate.getAmounts(), AllocationAmounts.builder()
				.payAmt(payAmt)
				.build());

		final C_AllocationHdr_Builder allocationBuilder = newC_AllocationHdr_Builder(candidate)
				//
				// Outgoing payment line
				.addLine()
				.skipIfAllAmountsAreZero()
				.orgId(candidate.getOrgId())
				.bpartnerId(candidate.getBpartnerId())
				.paymentId(extractPaymentId(candidate.getPayableDocumentRef()))
				//
				.amount(payAmt.toBigDecimal())
				.overUnderAmt(candidate.getPayableOverUnderAmt().toBigDecimal())
				.lineDone()
				//
				// Incoming payment line
				.addLine()
				.skipIfAllAmountsAreZero()
				.orgId(candidate.getOrgId())
				.bpartnerId(candidate.getBpartnerId())
				.paymentId(extractPaymentId(candidate.getPaymentDocumentRef()))
				//
				.amount(payAmt.negate().toBigDecimal())
				.overUnderAmt(candidate.getPaymentOverUnderAmt().toBigDecimal())
				.lineDone();

		return createAndComplete(allocationBuilder);
	}

	private static InvoiceId extractInvoiceId(@NonNull final TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
	}

	private static PaymentId extractPaymentId(@NonNull final TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_C_Payment.Table_Name, PaymentId::ofRepoId);
	}

	private C_AllocationHdr_Builder newC_AllocationHdr_Builder(final AllocationLineCandidate candidate)
	{
		return allocationBL.newBuilder()
				.orgId(candidate.getOrgId())
				.currencyId(candidate.getCurrencyId())
				.dateTrx(candidate.getDateTrx())
				.dateAcct(candidate.getDateAcct())
				.manual(true); // flag it as manually created by user
	}

	@Nullable
	private PaymentAllocationId createAndComplete(final C_AllocationHdr_Builder allocationBuilder)
	{
		final I_C_AllocationHdr allocationHdr = allocationBuilder.createAndComplete();
		if (allocationHdr == null)
		{
			return null;
		}

		//
		final ImmutableList<I_C_AllocationLine> lines = allocationBuilder.getC_AllocationLines();
		updateCounter_AllocationLine_ID(lines);

		return PaymentAllocationId.ofRepoId(allocationHdr.getC_AllocationHdr_ID());
	}

	/**
	 * Sets the counter allocation line - that means the mathcing line
	 * The id is set only if we have 2 line: credit memo - invoice; purchase invoice - sales invoice; incoming payment - outgoing payment
	 *
	 * @param lines
	 */
	private void updateCounter_AllocationLine_ID(final ImmutableList<I_C_AllocationLine> lines)
	{
		if (lines.size() != 2)
		{
			return;
		}

		//
		final I_C_AllocationLine al1 = lines.get(0);
		final I_C_AllocationLine al2 = lines.get(1);

		al1.setCounter_AllocationLine_ID(al2.getC_AllocationLine_ID());
		allocationDAO.save(al1);

		//
		al2.setCounter_AllocationLine_ID(al1.getC_AllocationLine_ID());
		allocationDAO.save(al2);
	}
}
