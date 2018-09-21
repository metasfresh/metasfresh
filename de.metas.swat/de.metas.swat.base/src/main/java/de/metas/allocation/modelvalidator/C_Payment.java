package de.metas.allocation.modelvalidator;

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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

import de.metas.util.Check;

@Validator(I_C_Payment.class)
public class C_Payment
{

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_C_Payment.COLUMNNAME_IsAutoAllocateAvailableAmt)
	public void onChangePayment(final I_C_Payment payment)
	{
		if (payment.isAutoAllocateAvailableAmt())
		{
			Check.errorIf(!(payment.isReceipt()), " Payment {} with IsAutoAllocateAvailableAmt=Y is not a receipt.", payment);
		}
	}
}
