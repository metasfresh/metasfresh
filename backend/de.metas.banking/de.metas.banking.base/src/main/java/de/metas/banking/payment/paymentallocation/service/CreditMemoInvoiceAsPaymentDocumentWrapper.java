package de.metas.banking.payment.paymentallocation.service;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.banking.payment.paymentallocation.service.PayableDocument.PayableDocumentType;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
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
final class CreditMemoInvoiceAsPaymentDocumentWrapper implements IPaymentDocument
{
	public static CreditMemoInvoiceAsPaymentDocumentWrapper wrap(final PayableDocument creditMemoPayableDoc)
	{
		return new CreditMemoInvoiceAsPaymentDocumentWrapper(creditMemoPayableDoc);
	}

	private final PayableDocument creditMemoPayableDoc;

	private CreditMemoInvoiceAsPaymentDocumentWrapper(@NonNull final PayableDocument creditMemoPayableDoc)
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
	public OrgId getOrgId()
	{
		return creditMemoPayableDoc.getOrgId();
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
	public boolean canPay(@NonNull final PayableDocument payable)
	{
		if (payable.getType() != PayableDocumentType.Invoice)
		{
			return false;
		}
		if (payable.getSoTrx() != creditMemoPayableDoc.getSoTrx())
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
	public PaymentDirection getPaymentDirection()
	{
		return PaymentDirection.ofSOTrx(creditMemoPayableDoc.getSoTrx());
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return creditMemoPayableDoc.getCurrencyId();
	}

}
