package org.eevolution.util;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_BOM;

public class ProductBOMBuilder
{
	private IContextAware _context;
	// Product BOM fields
	private I_M_Product _product;
	private I_C_UOM _uom;
	private I_PP_Product_BOM _productBOM;

	// BOM Line Builders
	private final List<ProductBOMLineBuilder> lineBuilders = new ArrayList<>();

	public ProductBOMBuilder setContext(IContextAware context)
	{
		this._context = context;
		return this;
	}

	protected final IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	public ProductBOMLineBuilder newBOMLine()
	{
		final ProductBOMLineBuilder bomLineBuilder = new ProductBOMLineBuilder(this);
		lineBuilders.add(bomLineBuilder);
		return bomLineBuilder;
	}

	public I_PP_Product_BOM build()
	{
		// Make sure Product BOM (header is created)
		final I_PP_Product_BOM productBOM = getCreateProductBOM();

		// Build lines
		for (final ProductBOMLineBuilder lineBuilder : lineBuilders)
		{
			lineBuilder.build();
		}

		return productBOM;
	}

	protected final I_PP_Product_BOM getCreateProductBOM()
	{
		if (_productBOM != null)
		{
			return _productBOM;
		}

		final I_M_Product product = getM_Product();

		final I_PP_Product_BOM productBOM = InterfaceWrapperHelper.newInstance(I_PP_Product_BOM.class, getContext());
		productBOM.setM_Product_ID(product.getM_Product_ID());
		productBOM.setC_UOM_ID(getC_UOM().getC_UOM_ID());
		productBOM.setValue(product.getValue());
		productBOM.setName(product.getName());
		productBOM.setBOMType(X_PP_Product_BOM.BOMTYPE_CurrentActive);
		productBOM.setBOMUse(X_PP_Product_BOM.BOMUSE_Manufacturing);
		InterfaceWrapperHelper.save(productBOM);
		this._productBOM = productBOM;

		//
		// Update product
		product.setIsBOM(true);
		// TODO: workaround until we refactor the product BOM verification
		// see org.eevolution.process.PP_Product_BOM_Check.doIt()
		product.setIsVerified(true);
		InterfaceWrapperHelper.save(product);
		
		return productBOM;
	}

	public ProductBOMBuilder product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	private I_M_Product getM_Product()
	{
		Check.assumeNotNull(_product, "_product not null");
		return this._product;
	}

	public ProductBOMBuilder uom(final I_C_UOM uom)
	{
		this._uom = uom;
		return this;
	}
	
	private I_C_UOM getC_UOM()
	{
		if (_uom != null)
		{
			return _uom;
		}
		
		final I_M_Product product = getM_Product();
		final I_C_UOM uom = product.getC_UOM();
		Check.assumeNotNull(uom, "uom not null");
		return uom;
	}
}
