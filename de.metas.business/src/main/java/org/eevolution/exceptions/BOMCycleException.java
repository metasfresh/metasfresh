package org.eevolution.exceptions;

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


import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_PP_Product_BOM;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

public class BOMCycleException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6859323608419524916L;

	public BOMCycleException(final I_PP_Product_BOM bom, final ProductId componentProductId)
	{
		super(buildMsg(bom, componentProductId));
	}

	private static final String buildMsg(I_PP_Product_BOM bom, ProductId componentProductId)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("Cycle BOM & Formula:");
		sb.append(bom.getValue()).append("_").append(bom.getName()).append(" (").append(bom.getPP_Product_BOM_ID()).append(")");

		if (componentProductId != null)
		{
			final String componentName = Services.get(IProductBL.class).getProductValueAndName(componentProductId);
			sb.append(" - Component: ").append(componentName);
		}

		return sb.toString();
	}
}
