package de.metas.banking.payment.paymentallocation.service;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.C_AllocationLine_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;

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

	public ImmutableSet<PaymentAllocationId> save(final List<AllocationLineCandidate> candidates)
	{
		return trxManager.callInNewTrx(() -> saveInTrx(candidates));
	}

	private ImmutableSet<PaymentAllocationId> saveInTrx(final List<AllocationLineCandidate> candidates)
	{
		final ImmutableSet.Builder<PaymentAllocationId> paymentAllocationIds = ImmutableSet.builder();

		for (final AllocationLineCandidate candidate : candidates)
		{
			final PaymentAllocationId paymentAllocationId = createAndCompleteAllocation(candidate);
			if (paymentAllocationId != null)
			{
				paymentAllocationIds.add(paymentAllocationId);
			}
		}

		return paymentAllocationIds.build();
	}

	private PaymentAllocationId createAndCompleteAllocation(final AllocationLineCandidate candidate)
	{
		final OrgId adOrgId = candidate.getOrgId();
		final CurrencyId currencyId = candidate.getCurrencyId();
		final Timestamp dateTrx = TimeUtil.asTimestamp(candidate.getDateTrx());
		final Timestamp dateAcct = TimeUtil.asTimestamp(candidate.getDateAcct());

		final C_AllocationHdr_Builder allocationBuilder = allocationBL.newBuilder()
				.orgId(adOrgId.getRepoId())
				.currencyId(currencyId.getRepoId())
				.dateAcct(dateAcct)
				.dateTrx(dateTrx)
				.manual(true); // flag it as manually created by user

		final C_AllocationLine_Builder payableLineBuilder = allocationBuilder.addLine()
				.orgId(adOrgId.getRepoId())
				.bpartnerId(BPartnerId.toRepoId(candidate.getBpartnerId()))
				//
				// Amounts
				.amount(candidate.getAmounts().getPayAmt().toBigDecimal())
				.discountAmt(candidate.getAmounts().getDiscountAmt().toBigDecimal())
				.writeOffAmt(candidate.getAmounts().getWriteOffAmt().toBigDecimal())
				.overUnderAmt(candidate.getPayableOverUnderAmt().toBigDecimal())
				.skipIfAllAmountsAreZero();

		final TableRecordReference payableDocRef = candidate.getPayableDocumentRef();
		final String payableDocTableName = payableDocRef.getTableName();
		final TableRecordReference paymentDocRef = candidate.getPaymentDocumentRef();
		final String paymentDocTableName = paymentDocRef == null ? null : paymentDocRef.getTableName();

		//
		// Invoice - Payment
		if (I_C_Invoice.Table_Name.equals(payableDocTableName)
				&& I_C_Payment.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.invoiceId(payableDocRef.getRecord_ID());
			payableLineBuilder.paymentId(paymentDocRef.getRecord_ID());
		}
		//
		// Invoice - CreditMemo invoice
		// or Sales invoice - Purchase Invoice
		else if (I_C_Invoice.Table_Name.equals(payableDocTableName)
				&& I_C_Invoice.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.invoiceId(payableDocRef.getRecord_ID());
			//

			// Credit memo line
			allocationBuilder.addLine()
					.orgId(adOrgId.getRepoId())
					.bpartnerId(BPartnerId.toRepoId(candidate.getBpartnerId()))
					//
					// Amounts
					.amount(candidate.getAmounts().getPayAmt().negate().toBigDecimal())
					.overUnderAmt(candidate.getPaymentOverUnderAmt().toBigDecimal())
					.skipIfAllAmountsAreZero()
					//
					.invoiceId(paymentDocRef.getRecord_ID());
		}
		//
		// Invoice - just Discount/WriteOff
		else if (I_C_Invoice.Table_Name.equals(payableDocTableName)
				&& paymentDocTableName == null)
		{
			payableLineBuilder.invoiceId(payableDocRef.getRecord_ID());
			// allow only if the line's amount is zero, because else, we need to have a document where to allocate.
			Check.assume(candidate.getAmounts().getPayAmt().signum() == 0, "zero amount: {}", candidate);
		}
		//
		// Outgoing payment - Incoming payment
		else if (I_C_Payment.Table_Name.equals(payableDocTableName)
				&& I_C_Payment.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.paymentId(payableDocRef.getRecord_ID());
			// Incoming payment line
			allocationBuilder.addLine()
					.orgId(adOrgId.getRepoId())
					.bpartnerId(BPartnerId.toRepoId(candidate.getBpartnerId()))
					//
					// Amounts
					.amount(candidate.getAmounts().getPayAmt().negate().toBigDecimal())
					.overUnderAmt(candidate.getPaymentOverUnderAmt().toBigDecimal())
					.skipIfAllAmountsAreZero()
					//
					.paymentId(paymentDocRef.getRecord_ID());
		}
		else
		{
			throw new InvalidDocumentsPaymentAllocationException(payableDocRef, paymentDocRef);
		}

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
	private static void updateCounter_AllocationLine_ID(final ImmutableList<I_C_AllocationLine> lines)
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
}
