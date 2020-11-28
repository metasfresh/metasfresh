package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterialQuery;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialType;

/* package */class ProductionMaterialQuery implements IProductionMaterialQuery
{

	private List<ProductionMaterialType> types = null;
	private I_M_Product product;
	private I_PP_Order ppOrder;

	@Override
	public String toString()
	{
		return "ProductionMaterialQuery [types=" + types + ", product=" + product + ", ppOrder=" + ppOrder + "]";
	}

	@Override
	public IProductionMaterialQuery setTypes(final ProductionMaterialType... types)
	{
		if (types == null || types.length == 0)
		{
			this.types = null;
		}
		else
		{
			this.types = Arrays.asList(types);
		}

		return this;
	}

	@Override
	public List<ProductionMaterialType> getTypes()
	{
		if (types == null)
		{
			return Collections.emptyList();
		}
		return types;
	}

	@Override
	public IProductionMaterialQuery setM_Product(final I_M_Product product)
	{
		this.product = product;

		return this;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public IProductionMaterialQuery setPP_Order(final I_PP_Order ppOrder)
	{
		this.ppOrder = ppOrder;

		return this;
	}

	@Override
	public I_PP_Order getPP_Order()
	{
		return ppOrder;
	}

}
