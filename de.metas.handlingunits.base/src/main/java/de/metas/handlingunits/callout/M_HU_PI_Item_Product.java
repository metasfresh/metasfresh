package de.metas.handlingunits.callout;

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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * {@link I_M_HU_PI_Item_Product} Callout
 *
 * @author tsa
 *
 */
@Callout(I_M_HU_PI_Item_Product.class)
public class M_HU_PI_Item_Product
{
	@CalloutMethod(columnNames = { I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID,
			I_M_HU_PI_Item_Product.COLUMNNAME_Qty,
			I_M_HU_PI_Item_Product.COLUMNNAME_IsInfiniteCapacity })
	public void onM_HU_PI_Item(final I_M_HU_PI_Item_Product itemProduct, final ICalloutField field)
	{
		Services.get(IHUPIItemProductBL.class).setNameAndDescription(itemProduct);
	}
}
