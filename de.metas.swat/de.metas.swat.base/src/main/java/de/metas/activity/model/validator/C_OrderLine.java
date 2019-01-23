package de.metas.activity.model.validator;

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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;

@Validator(I_C_OrderLine.class)
public class C_OrderLine
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void onProductChanged(final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId == null)
		{
			orderLine.setC_Activity_ID(-1);
			return;
		}

		// IsDiverse flag
		final IProductBL productBL = Services.get(IProductBL.class);
		orderLine.setIsDiverse(productBL.isDiverse(productId));

		// Activity
		final ActivityId productActivityId = Services.get(IProductAcctDAO.class).getProductActivityId(productId);
		orderLine.setC_Activity_ID(ActivityId.toRepoId(productActivityId));
	}
}
