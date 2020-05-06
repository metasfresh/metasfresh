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
 * Wraps a given purchase invoice as an inbound payment document which we will try to allocate to some other sales invoice that we issued.
 * 
 * In other words, if we issued a sales invoice to one of our customers,
 * but the "customer" issued an purchase invoice to us, that purchase invoice can be used to compensate our customer invoice.
 */
@EqualsAndHashCode
final class PurchaseInvoiceAsInboundPaymentDocumentWrapper implements IPaymentDocument
{
	public static PurchaseInvoiceAsInboundPaymentDocumentWrapper wrap(final PayableDocument creditMemoPayableDoc)
	{
		return new PurchaseInvoiceAsInboundPaymentDocumentWrapper(creditMemoPayableDoc);
	}

	private final PayableDocument purchaseInvoicePayableDoc;

	private PurchaseInvoiceAsInboundPaymentDocumentWrapper(@NonNull final PayableDocument purchasePayableDoc)
	{
		Check.assume(purchasePayableDoc.getSoTrx().isPurchase(), "is purchase document: {}", purchasePayableDoc);
		Check.assume(!purchasePayableDoc.isCreditMemo(), "is not credit memo: {}", purchasePayableDoc);

		this.purchaseInvoicePayableDoc = purchasePayableDoc;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + purchaseInvoicePayableDoc.toString() + "]";
	}

	@Override
	public PaymentDocumentType getType()
	{
		return PaymentDocumentType.PurchaseInvoice;
	}

	@Override
	public PaymentDirection getPaymentDirection()
	{
		return PaymentDirection.INBOUND;
	}

	@Override
	public OrgId getOrgId()
	{
		return purchaseInvoicePayableDoc.getOrgId();
	}

	@Override
	public BPartnerId getBpartnerId()
	{
		return purchaseInvoicePayableDoc.getBpartnerId();
	}

	@Override
	public String getDocumentNo()
	{
		return purchaseInvoicePayableDoc.getDocumentNo();
	}

	@Override
	public TableRecordReference getReference()
	{
		return purchaseInvoicePayableDoc.getReference();
	}

	@Override
	public Money getAmountToAllocateInitial()
	{
		return purchaseInvoicePayableDoc.getAmountsToAllocateInitial().getPayAmt().negate();
	}

	@Override
	public Money getAmountToAllocate()
	{
		return purchaseInvoicePayableDoc.getAmountsToAllocate().getPayAmt().negate();
	}

	@Override
	public void addAllocatedAmt(Money allocatedAmtToAdd)
	{
		purchaseInvoicePayableDoc.addAllocatedAmounts(AllocationAmounts.ofPayAmt(allocatedAmtToAdd.negate()));
	}

	@Override
	public boolean isFullyAllocated()
	{
		return purchaseInvoicePayableDoc.isFullyAllocated();
	}

	@Override
	public Money calculateProjectedOverUnderAmt(Money amountToAllocate)
	{
		return purchaseInvoicePayableDoc.computeProjectedOverUnderAmt(AllocationAmounts.ofPayAmt(amountToAllocate.negate()));
	}

	@Override
	public boolean canPay(@NonNull final PayableDocument payable)
	{
		// A purchase invoice can compensate only on a sales invoice
		if (payable.getType() != PayableDocumentType.Invoice)
		{
			return false;
		}
		if (!payable.getSoTrx().isSales())
		{
			return false;
		}

		// if currency differs, do not allow payment
		if (!CurrencyId.equals(payable.getCurrencyId(), purchaseInvoicePayableDoc.getCurrencyId()))
		{
			return false;
		}

		return true;
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return purchaseInvoicePayableDoc.getCurrencyId();
	}
}
