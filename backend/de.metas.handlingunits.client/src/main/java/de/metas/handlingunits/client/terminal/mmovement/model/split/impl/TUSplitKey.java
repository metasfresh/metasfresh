package de.metas.handlingunits.client.terminal.mmovement.model.split.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.client.terminal.lutuconfig.model.TUKey;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.util.Check;

/**
 * Transport Unit Key
 *
 * @author tsa
 * @author al
 *
 */
public class TUSplitKey extends TUKey
{
	private final I_M_HU_PI_Item_Product huPIItemProduct;

	private final String unitType;

	public TUSplitKey(final ITerminalContext terminalContext,
			final I_M_HU_PI_Item_Product huPIItemProduct,
			final I_M_HU_PI tuPI,
			final ProductId cuProductId,
			final BigDecimal capacity,
			final String unitType)
	{
		super(terminalContext,
				huPIItemProduct,
				tuPI,
				cuProductId,
				IHUPIItemProductBL.extractUOMOrNull(huPIItemProduct),
				huPIItemProduct.isInfiniteCapacity(),
				capacity);

		Check.assumeNotNull(huPIItemProduct, "huPIItemProduct not null");
		this.huPIItemProduct = huPIItemProduct;

		Check.assumeNotNull(unitType, "unitType not null");
		this.unitType = unitType;
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return huPIItemProduct;
	}

	public String getUnitType()
	{
		return unitType;
	}
}
