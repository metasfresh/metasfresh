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

import de.metas.builder.ILineBuilder;

/**
 * Implementors of this interface are used to create {@link org.compiere.model.I_C_AllocationLine}s.
 * 
 * Note that to reduce complexity, this builder has no "cleverness" of its own. E.g. if you set a C_Invoice_ID, it does not do automatically deduct the C_BPartner_ID. This is intentional.
 * 
 * @see IAllocationBuilder
 */
public interface IAllocationLineBuilder extends ILineBuilder
{
	IAllocationLineBuilder setAD_Org_ID(int AD_Org_ID);

	IAllocationLineBuilder setC_BPartner_ID(int C_BPartner_ID);

	IAllocationLineBuilder setC_Invoice_ID(int C_Invoice_ID);
	
	IAllocationLineBuilder setCounter_AllocationLine_ID(int Counter_AllocationLine_ID);

	IAllocationLineBuilder setPrepayOrder_ID(int C_Order_ID);

	IAllocationLineBuilder setC_Payment_ID(int C_Payment_ID);

	IAllocationLineBuilder setAmount(BigDecimal amount);

	IAllocationLineBuilder setDiscountAmt(BigDecimal discountAmt);

	IAllocationLineBuilder setWriteOffAmt(BigDecimal writeOffAmt);
	
	IAllocationLineBuilder setPaymentWriteOffAmt(BigDecimal writeOffAmt);

	IAllocationLineBuilder setOverUnderAmt(BigDecimal overUnderAmt);

	/**
	 * Advice the builder to skip this line if ALL amounts are zero (i.e. Amount=0, DiscountAmt=0, WriteOffAmt=0).
	 * 
	 * The OverUnderAmt is not checked because that amount is NOT affecting the allocation at all.
	 */
	IAllocationLineBuilder setSkipIfAllAmountsAreZero();

	/**
	 * Finishes working with this line builder and returns the line builder's parent.
	 * 
	 * @return
	 */
	IAllocationBuilder lineDone();

	/**
	 * Returns the instance from whose <code>addLine()</code> invocation this instance was created.
	 * 
	 * @return
	 */
	IAllocationBuilder getParent();
}
