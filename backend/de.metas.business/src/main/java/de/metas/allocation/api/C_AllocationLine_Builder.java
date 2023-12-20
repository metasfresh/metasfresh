package de.metas.allocation.api;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * {@link I_C_AllocationLine} builder.
 */
public class C_AllocationLine_Builder
{
	private final IAllocationDAO allocationDAO;
	private final C_AllocationHdr_Builder parent;

	private final I_C_AllocationLine allocLine;
	private boolean skipIfAllAmountsAreZero = false;

	public C_AllocationLine_Builder(final C_AllocationHdr_Builder parent)
	{
		this.parent = parent;
		this.allocationDAO = parent.getAllocationDAO();

		this.allocLine = InterfaceWrapperHelper.newInstance(I_C_AllocationLine.class);
	}

	public final C_AllocationLine_Builder orgId(@NonNull final OrgId orgId)
	{
		return orgId(orgId.getRepoId());
	}

	public final C_AllocationLine_Builder orgId(final int orgId)
	{
		allocLine.setAD_Org_ID(orgId);
		return this;
	}

	public final C_AllocationLine_Builder bpartnerId(@Nullable final BPartnerId bpartnerId)
	{
		return bpartnerId(BPartnerId.toRepoId(bpartnerId));
	}

	public final C_AllocationLine_Builder bpartnerId(final int bpartnerId)
	{
		allocLine.setC_BPartner_ID(bpartnerId);
		return this;
	}

	public final C_AllocationLine_Builder invoiceId(@Nullable final InvoiceId invoiceId)
	{
		return invoiceId(InvoiceId.toRepoId(invoiceId));
	}

	public final C_AllocationLine_Builder invoiceId(int invoiceId)
	{
		allocLine.setC_Invoice_ID(invoiceId);
		return this;
	}

	public final C_AllocationLine_Builder paymentId(@Nullable final PaymentId paymentId)
	{
		return paymentId(PaymentId.toRepoId(paymentId));
	}

	public final C_AllocationLine_Builder paymentId(int paymentId)
	{
		allocLine.setC_Payment_ID(paymentId);
		return this;
	}

	public final C_AllocationLine_Builder amount(final BigDecimal amt)
	{
		allocLine.setAmount(amt);
		return this;
	}

	public final C_AllocationLine_Builder discountAmt(final BigDecimal discountAmt)
	{
		allocLine.setDiscountAmt(discountAmt);
		return this;
	}

	public final C_AllocationLine_Builder writeOffAmt(final BigDecimal writeOffAmt)
	{
		allocLine.setWriteOffAmt(writeOffAmt);
		return this;
	}

	public final C_AllocationLine_Builder overUnderAmt(BigDecimal overUnderAmt)
	{
		allocLine.setOverUnderAmt(overUnderAmt);
		return this;
	}

	public C_AllocationLine_Builder paymentWriteOffAmt(BigDecimal paymentWriteOffAmt)
	{
		allocLine.setPaymentWriteOffAmt(paymentWriteOffAmt);
		return this;
	}

	public final C_AllocationLine_Builder skipIfAllAmountsAreZero()
	{
		this.skipIfAllAmountsAreZero = true;
		return this;
	}

	private boolean isSkipBecauseAllAmountsAreZero()
	{
		if (!skipIfAllAmountsAreZero)
		{
			return false;
		}

		// NOTE: don't check the OverUnderAmt because that amount is not affecting allocation,
		// so an allocation is Zero with our without the over/under amount.
		return allocLine.getAmount().signum() == 0
				&& allocLine.getDiscountAmt().signum() == 0
				&& allocLine.getWriteOffAmt().signum() == 0
				//
				&& allocLine.getPaymentWriteOffAmt().signum() == 0;
	}

	public final C_AllocationHdr_Builder lineDone()
	{
		return parent;
	}

	/**
	 * @param allocHdrSupplier allocation header supplier which will provide the allocation header created & saved, just in time, so call it ONLY if you are really gonna create an allocation line.
	 * @return created {@link I_C_AllocationLine} or <code>null</code> if it was not needed.
	 */
	final I_C_AllocationLine create(final Supplier<I_C_AllocationHdr> allocHdrSupplier)
	{
		Check.assumeNotNull(allocHdrSupplier, "allocHdrSupplier not null");

		if (isSkipBecauseAllAmountsAreZero())
		{
			return null;
		}

		//
		// Get the allocation header, created & saved.
		final I_C_AllocationHdr allocHdr = allocHdrSupplier.get();
		Check.assumeNotNull(allocHdr, "Param 'allocHdr' not null");
		Check.assume(allocHdr.getC_AllocationHdr_ID() > 0, "Param 'allocHdr' has C_AllocationHdr_ID>0");

		allocLine.setC_AllocationHdr(allocHdr);
		allocLine.setAD_Org_ID(allocHdr.getAD_Org_ID());
		allocationDAO.save(allocLine);
		return allocLine;
	}

	public final C_AllocationHdr_Builder getParent()
	{
		return parent;
	}
}
