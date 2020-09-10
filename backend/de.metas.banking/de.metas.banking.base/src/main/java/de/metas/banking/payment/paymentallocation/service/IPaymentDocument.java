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

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentDirection;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.LocalDate;

public interface IPaymentDocument
{

	enum PaymentDocumentType
	{
		RegularPayment, CreditMemoInvoice, PurchaseInvoice
	}

	PaymentDocumentType getType();

	BPartnerId getBpartnerId();

	String getDocumentNo();

	PaymentDirection getPaymentDirection();

	TableRecordReference getReference();

	Money getAmountToAllocateInitial();

	Money getAmountToAllocate();

	CurrencyId getCurrencyId();

	void addAllocatedAmt(Money allocatedPayAmtToAdd);

	LocalDate getDate();

	ClientAndOrgId getClientAndOrgId();

	@Nullable
	CurrencyConversionTypeId getCurrencyConversionTypeId();

	/**
	 * @return true if everything that was requested to be allocated, was allocated
	 */
	boolean isFullyAllocated();

	Money calculateProjectedOverUnderAmt(final Money payAmountToAllocate);

	boolean canPay(PayableDocument payable);
}
