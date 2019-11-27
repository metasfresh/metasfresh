package de.metas.banking.payment.paymentallocation.service;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;

public interface IPaymentDocument
{
	BPartnerId getBpartnerId();

	String getDocumentNo();

	boolean isCustomerDocument();

	boolean isVendorDocument();

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
