package org.adempiere.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.ModelValidator;

import de.metas.product.IStorageBL;

@Interceptor(I_M_Transaction.class)
public class M_Transaction
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void updateStorageOnDelete(final I_M_Transaction mtrx)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(mtrx);
		final String trxName = InterfaceWrapperHelper.getTrxName(mtrx);
		final I_M_Locator locator = mtrx.getM_Locator();
		final BigDecimal diffQtyOnHand = mtrx.getMovementQty().negate();
		final BigDecimal diffQtyReserved = BigDecimal.ZERO;
		final BigDecimal diffQtyOrdered = BigDecimal.ZERO;

		// FIXME: consider to do it async, after commit to make sure we are consistent!
		final boolean updated = Services.get(IStorageBL.class).add(ctx,
				locator.getM_Warehouse_ID(),
				locator.getM_Locator_ID(),
				mtrx.getM_Product_ID(),
				mtrx.getM_AttributeSetInstance_ID(), // M_AttributeSetInstance_ID
				0, // reservationAttributeSetInstance_ID
				diffQtyOnHand,
				diffQtyReserved,
				diffQtyOrdered,
				trxName);
		if (!updated)
		{
			throw new AdempiereException("Cannot update M_Storage for " + mtrx);
		}
	}
}
