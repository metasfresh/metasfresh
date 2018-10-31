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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;

import com.google.common.base.Supplier;

import de.metas.util.Check;

/**
 * Default allocation line build implementation. Other modules/project can subclass this if they need to build extended allocation lines.
 * <p>
 * Note that this builder is dump by intention. No assumptions, no code to automatically set field B after field A was set.
 *
 * @author ts
 *
 */
public class DefaultAllocationLineBuilder implements IAllocationLineBuilder
{

	private final DefaultAllocationBuilder parent;

	private final I_C_AllocationLine allocLine;
	private boolean skipIfAllAmountsAreZero = false;

	public DefaultAllocationLineBuilder(final DefaultAllocationBuilder parent)
	{
		this.parent = parent;
		this.allocLine = InterfaceWrapperHelper.newInstance(I_C_AllocationLine.class, parent.getContextProvider());
	}

	@Override
	public final IAllocationLineBuilder setAD_Org_ID(int ad_Org_ID)
	{
		allocLine.setAD_Org_ID(ad_Org_ID);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setC_BPartner_ID(int c_BPartner_ID)
	{
		allocLine.setC_BPartner_ID(c_BPartner_ID);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setC_Invoice_ID(int c_Invoice_ID)
	{
		allocLine.setC_Invoice_ID(c_Invoice_ID);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setC_Payment_ID(int C_Payment_ID)
	{
		allocLine.setC_Payment_ID(C_Payment_ID);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setAmount(BigDecimal amt)
	{
		allocLine.setAmount(amt);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setDiscountAmt(final BigDecimal discountAmt)
	{
		allocLine.setDiscountAmt(discountAmt);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setWriteOffAmt(final BigDecimal writeOffAmt)
	{
		allocLine.setWriteOffAmt(writeOffAmt);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setOverUnderAmt(BigDecimal overUnderAmt)
	{
		allocLine.setOverUnderAmt(overUnderAmt);
		return this;
	}

	@Override
	public final IAllocationLineBuilder setSkipIfAllAmountsAreZero()
	{
		this.skipIfAllAmountsAreZero = true;
		return this;
	}

	private final boolean isSkipBecauseAllAmountsAreZero()
	{
		if (!skipIfAllAmountsAreZero)
		{
			return false;
		}

		// NOTE: don't check the OverUnderAmt because that amount is not affecting allocation,
		// so an allocation is Zero with our without the over/under amount.
		final boolean allAmountsAreZero = allocLine.getAmount().signum() == 0
				&& allocLine.getDiscountAmt().signum() == 0
				&& allocLine.getWriteOffAmt().signum() == 0
				//
				&& allocLine.getPaymentWriteOffAmt().signum() == 0;
		return allAmountsAreZero;
	}

	@Override
	public final IAllocationBuilder lineDone()
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
		InterfaceWrapperHelper.save(allocLine);
		return allocLine;
	}

	@Override
	public final DefaultAllocationBuilder getParent()
	{
		return parent;
	}

	/**
	 * @return C_AllocationLine_ID or -1
	 */
	public final int getC_AllocationLine_ID()
	{
		if (allocLine == null)
		{
			return -1;
		}

		final int allocationLineId = allocLine.getC_AllocationLine_ID();
		return allocationLineId > 0 ? allocationLineId : -1;
	}

	/**
	 *
	 * @return C_BPartner_ID or -1
	 */
	public final int getC_BPartner_ID()
	{
		if (allocLine == null)
		{
			return -1;
		}

		final int bpartnerId = allocLine.getC_BPartner_ID();
		return bpartnerId > 0 ? bpartnerId : -1;
	}

	@Override
	public IAllocationLineBuilder setPaymentWriteOffAmt(BigDecimal paymentWriteOffAmt)
	{
		allocLine.setPaymentWriteOffAmt(paymentWriteOffAmt);
		return this;
	}

	@Override
	public IAllocationLineBuilder setCounter_AllocationLine_ID(int Counter_AllocationLine_ID)
	{
		allocLine.setCounter_AllocationLine_ID(Counter_AllocationLine_ID);
		return this;
	}

}
