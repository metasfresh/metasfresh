package de.metas.banking.payment.paymentallocation.service;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.banking.payment.paymentallocation.service.PayableDocument.PayableDocumentType;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Wraps a credit memo {@link PayableDocument} and behaves like a {@link IPaymentDocument}.
 * 
 * @author tsa
 *
 */
@EqualsAndHashCode
final class CreditMemoInvoiceAsPaymentDocument implements IPaymentDocument
{
	public static CreditMemoInvoiceAsPaymentDocument wrap(final PayableDocument creditMemoPayableDoc)
	{
		return new CreditMemoInvoiceAsPaymentDocument(creditMemoPayableDoc);
	}

	private final PayableDocument creditMemoPayableDoc;

	private CreditMemoInvoiceAsPaymentDocument(@NonNull final PayableDocument creditMemoPayableDoc)
	{
		Check.assume(creditMemoPayableDoc.isCreditMemo(), "is credit memo: {}", creditMemoPayableDoc);
		this.creditMemoPayableDoc = creditMemoPayableDoc;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + creditMemoPayableDoc.toString() + "]";
	}

	@Override
	public PaymentDocumentType getType()
	{
		return PaymentDocumentType.CreditMemoInvoice;
	}

	@Override
	public BPartnerId getBpartnerId()
	{
		return creditMemoPayableDoc.getBpartnerId();
	}

	@Override
	public String getDocumentNo()
	{
		return creditMemoPayableDoc.getDocumentNo();
	}

	@Override
	public TableRecordReference getReference()
	{
		return creditMemoPayableDoc.getReference();
	}

	@Override
	public Money getAmountToAllocateInitial()
	{
		return creditMemoPayableDoc.getAmountsToAllocateInitial().getPayAmt().negate();
	}

	@Override
	public Money getAmountToAllocate()
	{
		return creditMemoPayableDoc.getAmountsToAllocate().getPayAmt().negate();
	}

	@Override
	public void addAllocatedAmt(final Money allocatedPayAmtToAdd)
	{
		creditMemoPayableDoc.addAllocatedAmounts(AllocationAmounts.ofPayAmt(allocatedPayAmtToAdd.negate()));
	}

	@Override
	public boolean isFullyAllocated()
	{
		return creditMemoPayableDoc.isFullyAllocated();
	}

	@Override
	public Money calculateProjectedOverUnderAmt(Money payAmountToAllocate)
	{
		return creditMemoPayableDoc.computeProjectedOverUnderAmt(AllocationAmounts.ofPayAmt(payAmountToAllocate.negate()));
	}

	@Override
	public boolean canPay(PayableDocument payable)
	{
		if (payable.getType() != PayableDocumentType.Invoice)
		{
			return false;
		}
		if (payable.isCustomerDocument() != creditMemoPayableDoc.isCustomerDocument())
		{
			return false;
		}

		// A credit memo cannot pay another credit memo
		if (payable.isCreditMemo())
		{
			return false;
		}

		// if currency differs, do not allow payment
		if (!CurrencyId.equals(payable.getCurrencyId(), creditMemoPayableDoc.getCurrencyId()))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isCustomerDocument()
	{
		return creditMemoPayableDoc.isCustomerDocument();
	}

	@Override
	public boolean isVendorDocument()
	{
		return creditMemoPayableDoc.isVendorDocument();
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return creditMemoPayableDoc.getCurrencyId();
	}

}
