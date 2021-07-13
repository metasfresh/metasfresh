package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import org.eevolution.api.IDDOrderLineBL;
import org.eevolution.model.I_DD_OrderLine;

public class DDOrderLineBL implements IDDOrderLineBL
{

	@Override
	public void setUOMInDDOrderLine( final I_DD_OrderLine ddOrderLine)
	{
		if(ddOrderLine.getM_Product_ID() <= 0)
		{
			// nothing to do
			return;
		}

		final I_M_Product product = ddOrderLine.getM_Product();

		ddOrderLine.setC_UOM_ID(product.getC_UOM_ID());
	}
}
