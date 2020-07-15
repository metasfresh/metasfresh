package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Services;

@Interceptor(I_M_Product.class)
public class M_Product
{
	/**
	 * Updates *all* {@link I_M_HU_PI_Item_Product}s (whether they are currently active or not) with the UOM from the given product.
	 *
	 * @param product
	 * @task 08393
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_M_Product.COLUMNNAME_C_UOM_ID })
	public void updateAllM_HU_PI_Item_Products(final I_M_Product product)
	{
		final List<I_M_HU_PI_Item_Product> huPIPs = Services.get(IHUPIItemProductDAO.class).retrieveAllForProduct(product);

		final int productStockingUomId = product.getC_UOM_ID();

		for (final I_M_HU_PI_Item_Product huPIP : huPIPs)
		{
			huPIP.setC_UOM_ID(productStockingUomId);
			InterfaceWrapperHelper.save(huPIP);
		}
	}
}
