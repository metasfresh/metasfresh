package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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


import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.service.ICommissionTermBL;

public class CommissionTermBL implements ICommissionTermBL
{

	@Override
	public void setProductCategory(final I_C_AdvCommissionTerm term)
	{
		if (term.getM_Product_ID() > 0)
		{
			final I_M_Product product = term.getM_Product();
			final I_M_Product_Category category = product.getM_Product_Category();
			term.setM_Product_Category(category);
		}
		else
		{
			term.setM_Product_Category(null);
		}
	}
}
