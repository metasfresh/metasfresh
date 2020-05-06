package de.metas.banking.payment.paymentallocation.service;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;

public interface IPaymentDocument
{
	enum PaymentDocumentType
	{
		RegularPayment, CreditMemoInvoice, PurchaseInvoice,
	}

	PaymentDocumentType getType();
	
	OrgId getOrgId();

	BPartnerId getBpartnerId();

	String getDocumentNo();

	PaymentDirection getPaymentDirection();

	TableRecordReference getReference();

	Money getAmountToAllocateInitial();

	Money getAmountToAllocate();

	CurrencyId getCurrencyId();

	void addAllocatedAmt(Money allocatedPayAmtToAdd);

	/**
	 * @return true if everything that was requested to be allocated, was allocated
	 */
	boolean isFullyAllocated();

	Money calculateProjectedOverUnderAmt(final Money payAmountToAllocate);

	boolean canPay(PayableDocument payable);
}
