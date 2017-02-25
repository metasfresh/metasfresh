package de.metas.handlingunits.pricing.spi.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.impl.ProductPriceBuilder;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUProductPriceBuilder extends ProductPriceBuilder
{
	private I_M_HU_PI_Item_Product piItemProduct;

	public HUProductPriceBuilder(final I_M_PriceList_Version plv, final I_M_Product product)
	{
		super(plv, product);
	}

	@Override
	public I_M_ProductPrice build()
	{
		final I_M_ProductPrice pp = InterfaceWrapperHelper.create(super.build(), I_M_ProductPrice.class);
		
		pp.setM_HU_PI_Item_Product(piItemProduct);
		
		InterfaceWrapperHelper.save(pp);
		return pp;
	}

	public HUProductPriceBuilder setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product piItemProduct)
	{
		this.piItemProduct = piItemProduct;
		return this;
	}

}
