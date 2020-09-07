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
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * Mutable payment allocation candidate.
 * <p>
 * Used by {@link PaymentAllocationBuilder} internally.
 *
 * @author tsa
 */
@EqualsAndHashCode
@ToString
public class PaymentDocument implements IPaymentDocument
{
	@Getter
	private final PaymentId paymentId;
	@Getter
	private final BPartnerId bpartnerId;
	private final String documentNo;
	@Getter
	private final TableRecordReference reference;
	@Getter
	private final PaymentDirection paymentDirection;
	//
	private final Money openAmtInitial;
	@Getter
	private final Money amountToAllocateInitial;

	@Getter
	private Money amountToAllocate;
	private Money allocatedAmt;

	@Getter
	private final LocalDate dateTrx;

	@Getter
	private final ClientAndOrgId clientAndOrgId;

	@Getter
	private final CurrencyConversionTypeId currencyConversionTypeId;

	@Builder
	private PaymentDocument(
			@NonNull final PaymentId paymentId,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final String documentNo,
			@NonNull final PaymentDirection paymentDirection,
			//
			@NonNull final Money openAmt,
			@NonNull final Money amountToAllocate,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final LocalDate dateTrx,
			@Nullable final CurrencyConversionTypeId currencyConversionTypeId
	)
	{
		final OrgId orgId = clientAndOrgId.getOrgId();
		if (!orgId.isRegular())
		{
			throw new AdempiereException("Transactional organization expected: " + orgId);
		}

		this.paymentId = paymentId;
		this.bpartnerId = bpartnerId;
		this.documentNo = documentNo;
		this.reference = TableRecordReference.of(I_C_Payment.Table_Name, paymentId);
		this.paymentDirection = paymentDirection;
		//
		Money.getCommonCurrencyIdOfAll(openAmt, amountToAllocate);
		this.openAmtInitial = openAmt;
		this.amountToAllocateInitial = amountToAllocate;
		this.amountToAllocate = amountToAllocate;
		this.allocatedAmt = amountToAllocate.toZero();
		this.dateTrx = dateTrx;
		this.clientAndOrgId = clientAndOrgId;
		this.currencyConversionTypeId = currencyConversionTypeId;
	}

	@Override
	public PaymentDocumentType getType()
	{
		return PaymentDocumentType.RegularPayment;
	}

	@Override
	public final String getDocumentNo()
	{
		if (!Check.isEmpty(documentNo, true))
		{
			return documentNo;
		}

		final TableRecordReference reference = getReference();
		if (reference != null)
		{
			return "<" + reference.getRecord_ID() + ">";
		}
		return "?";
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return amountToAllocateInitial.getCurrencyId();
	}

	@Override
	public void addAllocatedAmt(@NonNull final Money allocatedAmtToAdd)
	{
		allocatedAmt = allocatedAmt.add(allocatedAmtToAdd);
		amountToAllocate = amountToAllocate.subtract(allocatedAmtToAdd);
	}

	@Override
	public LocalDate getDate()
	{
		return dateTrx;
	}

	@Override
	public boolean isFullyAllocated()
	{
		return getAmountToAllocate().signum() == 0;
	}

	private Money getOpenAmtRemaining()
	{
		final Money totalAllocated = allocatedAmt;
		return openAmtInitial.subtract(totalAllocated);
	}

	@Override
	public Money calculateProjectedOverUnderAmt(@NonNull final Money amountToAllocate)
	{
		return getOpenAmtRemaining().subtract(amountToAllocate);
	}

	@Override
	public boolean canPay(@NonNull final PayableDocument payable)
	{
		return true;
	}
}
