/**
 * 
 */
package org.eevolution.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.uom.api.impl.UOMTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.product.ProductId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ProductBOMBLTest
{

	final private String bomProductName = "BOMProductName";
	final private String bomProductValue = "BOMProductValue";

	final private String bomCompProductName = "ComponentName";
	final private String bomCompProductValue = "ComponentValue";

	private UOMTestHelper uomConversionHelper;
	
	private I_M_Product bomProduct;
	private I_M_Product compProduct;
	private I_C_UOM millimeter;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uomConversionHelper = new UOMTestHelper(Env.getCtx());
		
		bomProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		bomProduct.setValue(bomProductValue);
		bomProduct.setName(bomProductName);
		InterfaceWrapperHelper.save(bomProduct);

		compProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		compProduct.setValue(bomCompProductValue);
		compProduct.setName(bomCompProductName);
		InterfaceWrapperHelper.save(compProduct);
		
		millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, "mm");

		POJOWrapper.setDefaultStrictValues(false);
	}

	@Test
	public void test_getBOMDescriptionForProductId_NotQty()
	{
		final I_M_Product bomProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		bomProduct.setValue(bomProductValue);
		bomProduct.setName(bomProductName);
		InterfaceWrapperHelper.save(bomProduct);

		final I_M_Product compProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		compProduct.setValue(bomCompProductValue);
		compProduct.setName(bomCompProductName);
		InterfaceWrapperHelper.save(compProduct);

		final I_PP_Product_BOM bom = InterfaceWrapperHelper.newInstance(I_PP_Product_BOM.class);
		bom.setM_Product_ID(bomProduct.getM_Product_ID());
		bom.setValue(bomProductValue);
		InterfaceWrapperHelper.save(bom);

		

		final I_PP_Product_BOMLine bomLine = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class);
		bomLine.setPP_Product_BOM(bom);
		bomLine.setIsQtyPercentage(false);
		bomLine.setM_Product(compProduct);
		bomLine.setQtyBOM(BigDecimal.ONE);
		bomLine.setC_UOM(millimeter);
		InterfaceWrapperHelper.save(bomLine);

		final String productDescription = Services.get(IProductBOMBL.class).getBOMDescriptionForProductId(ProductId.ofRepoId(bomProduct.getM_Product_ID()));
		assertThat(productDescription).doesNotContain(bomCompProductValue);
		assertThat(productDescription).doesNotContain(BigDecimal.ONE.toString());
	}
	
	@Test
	public void test_getBOMDescriptionForProductId_WithQty()
	{

		final I_PP_Product_BOM bom = InterfaceWrapperHelper.newInstance(I_PP_Product_BOM.class);
		bom.setM_Product_ID(bomProduct.getM_Product_ID());
		bom.setValue(bomProductValue);
		InterfaceWrapperHelper.save(bom);

		final I_C_UOM millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, "mm");

		final I_PP_Product_BOMLine bomLine = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class);
		bomLine.setPP_Product_BOM(bom);
		bomLine.setIsQtyPercentage(false);
		bomLine.setM_Product(compProduct);
		bomLine.setQtyBOM(BigDecimal.TEN);
		bomLine.setC_UOM(millimeter);
		InterfaceWrapperHelper.save(bomLine);

		final String productDescription = Services.get(IProductBOMBL.class).getBOMDescriptionForProductId(ProductId.ofRepoId(bomProduct.getM_Product_ID()));
		assertThat(productDescription).doesNotContain(bomCompProductValue);
		assertThat(productDescription).isEqualTo(bomCompProductName + " " + BigDecimal.TEN + " " + millimeter.getUOMSymbol());
	}
}
