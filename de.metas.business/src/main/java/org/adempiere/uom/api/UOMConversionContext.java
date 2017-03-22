package org.adempiere.uom.api;

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


import org.adempiere.uom.api.IUOMConversionContext;
import org.compiere.model.I_M_Product;

/* package */final class UOMConversionContext implements IUOMConversionContext
{
	private final I_M_Product product;

	public UOMConversionContext(final I_M_Product product)
	{
		super();

		// NULL is accepted
		this.product = product;
	}

	@Override
	public String toString()
	{
		return "UOMConversionContext [product=" + product + "]";
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

}
