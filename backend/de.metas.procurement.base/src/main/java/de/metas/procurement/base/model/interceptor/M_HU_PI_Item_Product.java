package de.metas.procurement.base.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Interceptor(I_M_HU_PI_Item_Product.class)
public class M_HU_PI_Item_Product
{
	public static final transient M_HU_PI_Item_Product instance = new M_HU_PI_Item_Product();

	private M_HU_PI_Item_Product()
	{
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
	//
	, ifColumnsChanged = { I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, I_M_HU_PI_Item_Product.COLUMNNAME_Description, I_M_HU_PI_Item_Product.COLUMNNAME_IsActive, I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID }
	//
	)
	public void updatePMM_Product(final I_M_HU_PI_Item_Product piip)
	{
		Services.get(IPMMProductBL.class).updateByHUPIItemProduct(piip);
	}
}
