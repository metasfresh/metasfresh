package de.metas.banking.payment.paymentallocation.service;

/*
 * #%L
 * de.metas.banking.swingui
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Check;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Immutable allocation candidate.
 *
 * @author tsa
 *
 */
@Value
final class AllocationLineCandidate
{
	private final BPartnerId bpartnerId;

	private final TableRecordReference payableDocumentRef;
	private final TableRecordReference paymentDocumentRef;

	//
	// Amounts
	private final BigDecimal amount;
	private final BigDecimal discountAmt;
	private final BigDecimal writeOffAmt;
	private final BigDecimal payableOverUnderAmt;
	private final BigDecimal paymentOverUnderAmt;

	@Builder
	private AllocationLineCandidate(
			@Nullable final BPartnerId bpartnerId,
			//
			@NonNull final TableRecordReference payableDocumentRef,
			@Nullable final TableRecordReference paymentDocumentRef,
			//
			// Amounts
			@NonNull final BigDecimal amount,
			@Nullable final BigDecimal discountAmt,
			@Nullable final BigDecimal writeOffAmt,
			@Nullable final BigDecimal payableOverUnderAmt,
			@Nullable final BigDecimal paymentOverUnderAmt)
	{
		this.amount = amount;
		this.discountAmt = CoalesceUtil.coalesce(discountAmt, BigDecimal.ZERO);
		this.writeOffAmt = CoalesceUtil.coalesce(writeOffAmt, BigDecimal.ZERO);
		this.payableOverUnderAmt = CoalesceUtil.coalesce(payableOverUnderAmt, BigDecimal.ZERO);
		this.paymentOverUnderAmt = CoalesceUtil.coalesce(paymentOverUnderAmt, BigDecimal.ZERO);

		this.bpartnerId = bpartnerId;

		this.payableDocumentRef = payableDocumentRef;
		this.paymentDocumentRef = paymentDocumentRef;
		Check.errorIf(Objects.equals(this.payableDocumentRef, this.paymentDocumentRef), "payable and payment shall not be the same but there are: {}", payableDocumentRef);
		if (amount.signum() != 0)
		{
			Check.assumeNotNull(paymentDocumentRef, "paymentDocumentRef not null when amount is not zero");
		}
	}

	public BigDecimal getOverUnderAmt()
	{
		return getPayableOverUnderAmt();
	}

	public boolean isZeroToAllocate()
	{
		return amount.signum() == 0
				&& discountAmt.signum() == 0
				&& writeOffAmt.signum() == 0
		// NOTE: don't check the OverUnderAmt because that amount is not affecting allocation,
		// so an allocation is Zero with our without the over/under amount.
		// && overUnderAmt.signum() == 0
		;
	}
}
