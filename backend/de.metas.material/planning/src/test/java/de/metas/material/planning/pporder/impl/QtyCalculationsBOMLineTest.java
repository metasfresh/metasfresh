package de.metas.material.planning.pporder.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.eevolution.api.BOMComponentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class QtyCalculationsBOMLineTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private I_C_UOM uom(final String name, final int precision)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setStdPrecision(precision);
		saveRecord(uom);
		return uom;
	}

	private ProductId product(final String name, final I_C_UOM stockingUOM)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(stockingUOM.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Builder(builderMethodName = "uomConversion", builderClassName = "_UOMConversionBuilder", buildMethodName = "create")
	private void createUOMConversion(final I_C_UOM from, String multipliedBy, I_C_UOM to)
	{
		final BigDecimal multiplyRate = new BigDecimal(multipliedBy);

		final IUOMConversionDAO uomConversionsRepo = Services.get(IUOMConversionDAO.class);
		uomConversionsRepo.createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(from.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(to.getC_UOM_ID()))
				.fromToMultiplier(multiplyRate)
				.build());
	}

	@Nested
	public class getFinishedGoodQtyMultiplier
	{
		@Test
		public void notPercentage()
		{
			QtyCalculationsBOMLine bomLine = QtyCalculationsBOMLine.builder()
					.bomProductId(ProductId.ofRepoId(1))
					.bomProductUOM(uom("Each", 0))
					//
					.componentType(BOMComponentType.Component)
					//
					.productId(ProductId.ofRepoId(2))
					.uom(uom("Kg", 2))
					.qtyPercentage(false)
					.qtyForOneFinishedGood(new BigDecimal("2"))
					//
					.build();

			assertThat(bomLine.getFinishedGoodQtyMultiplier())
					.isEqualTo("2");
		}

		@Test
		public void percentage_same_uom()
		{
			final I_C_UOM uomKg = uom("Kg", 2);

			QtyCalculationsBOMLine bomLine = QtyCalculationsBOMLine.builder()
					.bomProductId(ProductId.ofRepoId(1))
					.bomProductUOM(uomKg)
					//
					.componentType(BOMComponentType.Component)
					//
					.productId(ProductId.ofRepoId(2))
					.uom(uomKg)
					.qtyPercentage(true)
					.percentOfFinishedGood(Percent.of(33))
					//
					.build();

			assertThat(bomLine.getFinishedGoodQtyMultiplier())
					.isEqualByComparingTo("0.33");
		}

		@Test
		public void percentage_different_uoms()
		{
			final I_C_UOM uomLiters = uom("L", 2);
			final I_C_UOM uomMillis = uom("ml", 2);
			uomConversion().from(uomMillis).multipliedBy("1000").to(uomLiters).create();

			final ProductId juiceProductId = product("juice", uomLiters);
			final ProductId concentrateProductId = ProductId.ofRepoId(2);

			QtyCalculationsBOMLine bomLine = QtyCalculationsBOMLine.builder()
					.bomProductId(juiceProductId)
					.bomProductUOM(uomLiters)
					//
					.componentType(BOMComponentType.Component)
					//
					// consider that 12.5% of a concentrate is needed to produce 1L of juice:
					.productId(concentrateProductId)
					.uom(uomMillis)
					.qtyPercentage(true)
					.percentOfFinishedGood(Percent.of(new BigDecimal("12.5")))
					//
					.build();

			assertThat(bomLine.getFinishedGoodQtyMultiplier())
					.isEqualByComparingTo("0.000125");

			assertThat(bomLine.computeQtyRequired(BigDecimal.ONE))
					.as("How much concentrate is needed to produce 1ml of juice")
					.isEqualByComparingTo(Quantity.of("0.000125", uomMillis));
		}
	}

	@Nested
	public class computeQtyOfFinishedGoodsForComponentQty
	{
		@Test
		public void uomEach_precision_0()
		{
			final I_C_UOM uomEach = uom("Each", 0);

			final QtyCalculationsBOMLine bomLine = QtyCalculationsBOMLine.builder()
					.bomProductId(ProductId.ofRepoId(1))
					.bomProductUOM(uomEach)
					//
					.componentType(BOMComponentType.Component)
					//
					.productId(ProductId.ofRepoId(2))
					.uom(uomEach)
					.qtyPercentage(false)
					.qtyForOneFinishedGood(new BigDecimal("3"))
					//
					.build();

			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("2", uomEach)))
					.isEqualTo(Quantity.of("0", uomEach));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("3", uomEach)))
					.isEqualTo(Quantity.of("1", uomEach));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("5", uomEach)))
					.isEqualTo(Quantity.of("1", uomEach));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("6", uomEach)))
					.isEqualTo(Quantity.of("2", uomEach));
		}

		@Test
		public void bomInLiters_componentInMillis_calculateForMillis()
		{
			final I_C_UOM uomLiters = uom("L", 2);
			final I_C_UOM uomMillis = uom("ml", 2);
			uomConversion().from(uomMillis).multipliedBy("1000").to(uomLiters).create();

			final ProductId juiceProductId = product("juice", uomLiters);
			final ProductId concentrateProductId = ProductId.ofRepoId(2);

			final QtyCalculationsBOMLine bomLine = QtyCalculationsBOMLine.builder()
					.bomProductId(juiceProductId)
					.bomProductUOM(uomLiters)
					//
					.componentType(BOMComponentType.Component)
					.productId(concentrateProductId)
					.qtyPercentage(false)
					.uom(uomMillis)
					.qtyForOneFinishedGood(new BigDecimal("12.5"))
					//
					.build();

			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("10", uomMillis)))
					.isEqualTo(Quantity.of("0.80", uomLiters));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("12.5", uomMillis)))
					.isEqualTo(Quantity.of("1", uomLiters));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("25", uomMillis)))
					.isEqualTo(Quantity.of("2", uomLiters));
		}

		@Test
		public void bomInPieces_componentGrams_calculateForKg()
		{
			final I_C_UOM uomEach = uom("Each", 0);
			final I_C_UOM uomKg = uom("Kg", 2);
			final I_C_UOM uomGram = uom("g", 2);
			uomConversion().from(uomKg).multipliedBy("1000").to(uomGram).create();

			final ProductId breadProductId = product("bread", uomEach);
			final ProductId flourProductId = product("flour", uomGram);

			POJOLookupMap.get().dumpStatus("", I_C_UOM_Conversion.Table_Name);
			final Quantity qty2 = Services.get(IUOMConversionBL.class)
					.convertQuantityTo(
							Quantity.of("0.3", uomKg),
							UOMConversionContext.of(flourProductId),
							uomGram);
			System.out.println(qty2);

			final QtyCalculationsBOMLine bomLine = QtyCalculationsBOMLine.builder()
					.bomProductId(breadProductId)
					.bomProductUOM(uomEach)
					//
					.componentType(BOMComponentType.Component)
					.productId(flourProductId)
					.qtyPercentage(false)
					.uom(uomGram)
					.qtyForOneFinishedGood(new BigDecimal("300"))
					//
					.build();

			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("0.1", uomKg)))
					.isEqualTo(Quantity.of("0", uomEach));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("0.3", uomKg)))
					.isEqualTo(Quantity.of("1", uomEach));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("0.5", uomKg)))
					.isEqualTo(Quantity.of("1", uomEach));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("0.6", uomKg)))
					.isEqualTo(Quantity.of("2", uomEach));
			assertThat(bomLine.computeQtyOfFinishedGoodsForComponentQty(Quantity.of("1", uomKg)))
					.isEqualTo(Quantity.of("3", uomEach));
		}
	}
}
