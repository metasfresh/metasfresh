package de.metas.banking.payment.paymentallocation.service;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Mutable invoice allocation candidate.
 *
 * Used by {@link PaymentAllocationBuilder} internally.
 *
 * @author tsa
 *
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
	private final BPartnerId bpartnerId;
	private final String documentNo;
	private final boolean isSOTrx;
	@Getter
	private final TableRecordReference reference;
	@Getter
	private final PayableDocumentType type;
	@Getter
	private final boolean creditMemo;
	//
	private final Money openAmtInitial;

	@Getter
	private final AllocationAmounts amountsToAllocateInitial;
	@Getter
	private AllocationAmounts amountsToAllocate;
	private AllocationAmounts amountsAllocated;

	@Builder
	private PayableDocument(
			@Nullable final BPartnerId bpartnerId,
			@Nullable final String documentNo,
			final boolean isSOTrx,
			@NonNull final TableRecordReference reference,
			@NonNull final PayableDocumentType type,
			final boolean creditMemo,
			//
			@NonNull final Money openAmt,
			@NonNull AllocationAmounts amountsToAllocate)
	{
		this.bpartnerId = bpartnerId;
		this.documentNo = documentNo;
		this.isSOTrx = isSOTrx;
		this.type = type;
		this.reference = reference;
		this.creditMemo = creditMemo;

		if (!CurrencyId.equals(openAmt.getCurrencyId(), amountsToAllocate.getCurrencyId()))
		{
			throw new AdempiereException("openAmt and amountsToAllocate shall have the same currency: " + openAmt + ", " + amountsToAllocate);
		}
		this.openAmtInitial = openAmt;
		this.amountsToAllocateInitial = amountsToAllocate;
		this.amountsToAllocate = amountsToAllocate;
		this.amountsAllocated = amountsToAllocate.toZero();
	}

	public String getDocumentNo()
	{
		if (!Check.isEmpty(documentNo, true))
		{
			return documentNo;
		}

		final ITableRecordReference reference = getReference();
		if (reference != null)
		{
			return "<" + reference.getRecord_ID() + ">";
		}

		return "?";
	}

	public CurrencyId getCurrencyId()
	{
		return getAmountsToAllocate().getCurrencyId();
	}

	public boolean isCustomerDocument()
	{
		return isSOTrx;
	}

	public boolean isVendorDocument()
	{
		return !isSOTrx;
	}

	public void addAllocatedAmounts(@NonNull final AllocationAmounts amounts)
	{
		amountsAllocated = amountsAllocated.add(amounts);
		amountsToAllocate = amountsToAllocate.subtract(amounts);
	}

	public Money calculateProjectedOverUnderAmt(@NonNull final AllocationAmounts amountsToAllocate)
	{
		return getOpenAmtRemainingToAllocate().subtract(amountsToAllocate.getTotalAmt());
	}

	private Money getOpenAmtRemainingToAllocate()
	{
		return openAmtInitial.subtract(amountsAllocated.getTotalAmt());
	}

	public boolean isFullyAllocated()
	{
		return amountsToAllocate.isZero();
	}
}
