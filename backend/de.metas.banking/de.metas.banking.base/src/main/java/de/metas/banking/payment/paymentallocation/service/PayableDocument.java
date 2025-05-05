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

package de.metas.banking.payment.paymentallocation.service;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * Mutable invoice allocation candidate.
 * <p>
 * Used by {@link PaymentAllocationBuilder} internally.
 *
 * @author tsa
 */
@EqualsAndHashCode
@ToString
public class PayableDocument
{
	public enum PayableDocumentType
	{
		Invoice, PrepaidOrder,
	}

	@Getter
	private final InvoiceId invoiceId;
	private final OrderId prepayOrderId;

	@Getter
	private final BPartnerId bpartnerId;
	@Getter
	private final String documentNo;
	@Getter
	private final SOTrx soTrx;
	@Getter
	private final TableRecordReference reference;
	@Getter
	private final PayableDocumentType type;

	/**
	 * Will cause this payable to be wrapped as payment, so it can be allocated against another invoice.
	 */
	@Getter
	private final boolean creditMemo;

	/**
	 * We need this when allocating the payment of a remittance advice (REMADV) against a credit-memo.
	 * Because the REMADVS payment is diminished by the credit-memo,
	 * but there might be a payment-discount (skonto) that was held back when the original invoice was paid and which is now part of the current remittance payment.
	 * So we do need to allocate the positive-amount payment with the negative-amount credit-memo.
	 */
	@Getter
	private final boolean allowAllocateAgainstDifferentSignumPayment;
	
	//
	@Getter
	@VisibleForTesting
	private final Money openAmtInitial;

	@Getter
	private final AllocationAmounts amountsToAllocateInitial;
	@Getter
	private AllocationAmounts amountsToAllocate;
	private AllocationAmounts amountsAllocated;

	@Getter
	private final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation;

	@Getter
	private final ClientAndOrgId clientAndOrgId;

	@Getter
	private final LocalDate date;

	@Getter
	private final CurrencyConversionTypeId currencyConversionTypeId;

	@Builder
	private PayableDocument(
			@Nullable final InvoiceId invoiceId,
			@Nullable final OrderId prepayOrderId,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final String documentNo,
			@NonNull final SOTrx soTrx,
			final boolean creditMemo,
			final boolean allowAllocateAgainstDifferentSignumPayment,
			//
			@NonNull final Money openAmt,
			@NonNull final AllocationAmounts amountsToAllocate,
			@Nullable final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final LocalDate date,
			@Nullable final CurrencyConversionTypeId currencyConversionTypeId
	)
	{
		final OrgId orgId = clientAndOrgId.getOrgId();
		if (!orgId.isRegular())
		{
			throw new AdempiereException("Transactional organization expected: " + orgId);
		}

		if (amountsToAllocate.getInvoiceProcessingFee().signum() != 0 && invoiceProcessingFeeCalculation == null)
		{
			throw new AdempiereException("invoiceProcessingFeeCalculation is required when the fee is not zero");
		}

		if (invoiceId != null)
		{
			this.invoiceId = invoiceId;
			this.prepayOrderId = null;
			this.type = PayableDocumentType.Invoice;
			this.reference = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId);
		}
		else if (prepayOrderId != null)
		{
			this.invoiceId = null;
			this.prepayOrderId = prepayOrderId;
			this.type = PayableDocumentType.PrepaidOrder;
			this.reference = TableRecordReference.of(I_C_Order.Table_Name, prepayOrderId);
		}
		else
		{
			throw new AdempiereException("Invoice or Prepay Order shall be set");
		}

		this.bpartnerId = bpartnerId;
		this.documentNo = documentNo;
		this.soTrx = soTrx;
		this.creditMemo = creditMemo;
		this.allowAllocateAgainstDifferentSignumPayment = allowAllocateAgainstDifferentSignumPayment;

		if (!CurrencyId.equals(openAmt.getCurrencyId(), amountsToAllocate.getCurrencyId()))
		{
			throw new AdempiereException("openAmt and amountsToAllocate shall have the same currency: " + openAmt + ", " + amountsToAllocate);
		}
		this.openAmtInitial = openAmt;
		this.amountsToAllocateInitial = amountsToAllocate;
		this.amountsToAllocate = amountsToAllocate;
		this.amountsAllocated = amountsToAllocate.toZero();

		this.invoiceProcessingFeeCalculation = invoiceProcessingFeeCalculation;

		this.clientAndOrgId = clientAndOrgId;
		this.date = date;
		this.currencyConversionTypeId = currencyConversionTypeId;
	}

	public CurrencyId getCurrencyId()
	{
		return getAmountsToAllocate().getCurrencyId();
	}

	public void addAllocatedAmounts(@NonNull final AllocationAmounts amounts)
	{
		amountsAllocated = amountsAllocated.add(amounts);
		amountsToAllocate = amountsToAllocate.subtract(amounts);
	}

	public void moveRemainingOpenAmtToDiscount()
	{
		amountsToAllocate = amountsToAllocate.movePayAmtToDiscount();
	}

	public void moveRemainingOpenAmtToWriteOff()
	{
		amountsToAllocate = amountsToAllocate.movePayAmtToWriteOff();
	}

	public Money computeProjectedOverUnderAmt(@NonNull final AllocationAmounts amountsToAllocate)
	{
		return computeOpenAmtRemainingToAllocate().subtract(amountsToAllocate.getTotalAmt());
	}

	@NonNull
	private Money computeOpenAmtRemainingToAllocate()
	{
		return openAmtInitial.subtract(amountsAllocated.getTotalAmt());
	}

	@NonNull
	public Money getTotalAllocatedAmount()
	{
		return amountsAllocated.getTotalAmt();
	}

	public boolean isFullyAllocated()
	{
		return amountsToAllocate.getTotalAmt().isZero();
	}
}
