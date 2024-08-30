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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMVersions;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductBOMBuilder
{
	final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);


	private IContextAware _context;
	// Product BOM fields
	private I_M_Product _product;
	private I_C_UOM _uom;
	private I_PP_Product_BOM _productBOM;
	private I_PP_Product_BOMVersions _bomVersions;

	final ZonedDateTime date_2019_01_01 = LocalDate.of(2019, Month.JANUARY, 1).atStartOfDay(de.metas.common.util.time.SystemTime.zoneId());

	// BOM Line Builders
	private final List<ProductBOMLineBuilder> lineBuilders = new ArrayList<>();

	public ProductBOMBuilder setContext(final IContextAware context)
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
		productBOM.setBOMType(BOMType.CurrentActive.getCode());
		productBOM.setBOMUse(BOMUse.Manufacturing.getCode());
		productBOM.setDocStatus(DocStatus.Completed.getCode());
		productBOM.setValidFrom(TimeUtil.asTimestamp(date_2019_01_01));

		if (_bomVersions != null)
		{
			productBOM.setPP_Product_BOMVersions_ID(_bomVersions.getPP_Product_BOMVersions_ID());
		}

		InterfaceWrapperHelper.save(productBOM);
		this._productBOM = productBOM;

		//
		// Update product
		product.setIsBOM(true);

		InterfaceWrapperHelper.save(product);
		productBOMBL.verifyDefaultBOMProduct(ProductId.ofRepoId(product.getM_Product_ID()));

		return productBOM;
	}

	public ProductBOMBuilder product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	public ProductBOMBuilder bomVersions(final I_PP_Product_BOMVersions bomVersions)
	{
		this._bomVersions = bomVersions;
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

		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		if (_uom != null)
		{
			return _uom;
		}

		final I_M_Product product = getM_Product();
		final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());

		Check.assumeNotNull(uom, "uom not null");
		return uom;
	}
}
