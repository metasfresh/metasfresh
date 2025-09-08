package org.eevolution.api.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.product.ProductId;
import de.metas.uom.X12DE355;
import de.metas.uom.impl.UOMTestHelper;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.X_PP_Product_BOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

public class ProductBOMDescriptionBuilderTest
{
	private I_C_UOM millimeter;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		final UOMTestHelper uomConversionHelper = new UOMTestHelper(Env.getCtx());

		millimeter = uomConversionHelper.createUOM("Millimeter", 2, 0, X12DE355.ofCode("mm"));

		SpringContextHolder.registerJUnitBean(new ProductBOMVersionsDAO());
	}

	/**
	 * task https://github.com/metasfresh/metasfresh/issues/4766
	 */
	@ParameterizedTest
	@EnumSource(value = BOMType.class, names = { "CurrentActive", "MakeToOrder" })
	public void withQty_One(@NonNull final BOMType bomType)
	{
		final ProductId bomProductId = new BOMBuilder("BOMProductName")
				.bomType(bomType)
				.addLine("ComponentName", 1, millimeter)
				.getBomProductId();

		final String productDescription = ProductBOMDescriptionBuilder.newInstance()
				.build(bomProductId);

		assertThat(productDescription).isEqualTo("ComponentName");
	}

	@ParameterizedTest
	@EnumSource(value = BOMType.class, names = { "CurrentActive", "MakeToOrder" })
	public void withQty_Five(@NonNull final BOMType bomType)
	{
		final ProductId bomProductId = new BOMBuilder("BOMProductName")
				.bomType(bomType)
				.addLine("ComponentName", 5, millimeter)
				.getBomProductId();

		final String productDescription = ProductBOMDescriptionBuilder.newInstance()
				.build(bomProductId);

		assertThat(productDescription).isEqualTo("ComponentName 5 mm");
	}

	@ParameterizedTest
	@EnumSource(value = BOMType.class, names = { "CurrentActive", "MakeToOrder" })
	public void withQty_Zero(@NonNull final BOMType bomType)
	{
		final ProductId bomProductId = new BOMBuilder("BOMProductName")
				.bomType(bomType)
				.addLine("ComponentName", 0, millimeter)
				.getBomProductId();

		final String productDescription = ProductBOMDescriptionBuilder.newInstance()
				.build(bomProductId);

		assertThat(productDescription).isEqualTo("ComponentName 0 mm");
	}

	@ParameterizedTest
	@EnumSource(value = BOMType.class, names = { "CurrentActive", "MakeToOrder" })
	public void withQty_One_Five_One(@NonNull final BOMType bomType)
	{
		final ProductId bomProductId = new BOMBuilder("BOMProductName")
				.bomType(bomType)
				.addLine("ComponentName1", 1, millimeter)
				.addLine("ComponentName2", 5, millimeter)
				.addLine("ComponentName3", 1, millimeter)
				.getBomProductId();

		final String productDescription = ProductBOMDescriptionBuilder.newInstance()
				.build(bomProductId);

		assertThat(productDescription).isEqualTo(
				"ComponentName1\r\n"
						+ "ComponentName2 5 mm\r\n"
						+ "ComponentName3");
	}

	private static class BOMBuilder
	{
		private final ProductId bomProductId;
		private final I_PP_Product_BOM bom;

		private BOMBuilder(final String bomProductName)
		{
			final I_M_Product bomProduct = createProduct(bomProductName);
			bomProductId = ProductId.ofRepoId(bomProduct.getM_Product_ID());

			final I_PP_Product_BOMVersions versions = createBOMVersions(bomProduct);

			bom = newInstance(I_PP_Product_BOM.class);
			bom.setM_Product_ID(bomProductId.getRepoId());
			bom.setValue(bomProduct.getValue());
			bom.setBOMType(BOMType.CurrentActive.getCode());
			bom.setBOMUse(BOMUse.Manufacturing.getCode());
			bom.setPP_Product_BOMVersions_ID(versions.getPP_Product_BOMVersions_ID());
			bom.setValidFrom(SystemTime.asTimestamp());
			bom.setDocStatus(X_PP_Product_BOM.DOCSTATUS_Completed);
			saveRecord(bom);
		}

		public BOMBuilder bomType(@NonNull final BOMType bomType)
		{
			bom.setBOMType(bomType.getCode());
			saveRecord(bom);
			return this;
		}

		public ProductId getBomProductId()
		{
			return bomProductId;
		}

		public BOMBuilder addLine(final String productName, final int qtyBOM, final I_C_UOM uom)
		{
			final I_M_Product compProductId = createProduct(productName);

			final I_PP_Product_BOMLine bomLine = newInstance(I_PP_Product_BOMLine.class);
			bomLine.setPP_Product_BOM_ID(bom.getPP_Product_BOM_ID());
			bomLine.setIsQtyPercentage(false);
			bomLine.setM_Product_ID(compProductId.getM_Product_ID());
			bomLine.setQtyBOM(BigDecimal.valueOf(qtyBOM));
			bomLine.setC_UOM_ID(uom.getC_UOM_ID());
			InterfaceWrapperHelper.save(bomLine);

			return this;
		}

		private I_M_Product createProduct(final String name)
		{
			final I_M_Product product = newInstance(I_M_Product.class);
			product.setValue(name + "-value");
			product.setName(name);
			saveRecord(product);
			return product;
		}

		private I_PP_Product_BOMVersions createBOMVersions(final I_M_Product product)
		{
			final I_PP_Product_BOMVersions versions = newInstance(I_PP_Product_BOMVersions.class);
			versions.setM_Product_ID(product.getM_Product_ID());
			versions.setName(product.getName());
			saveRecord(versions);
			return versions;
		}
	}
}
