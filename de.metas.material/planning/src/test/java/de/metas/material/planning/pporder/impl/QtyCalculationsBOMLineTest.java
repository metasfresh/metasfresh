package de.metas.material.planning.pporder.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
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
		final BigDecimal divideRate = BigDecimal.ONE.divide(multiplyRate, 12, RoundingMode.HALF_UP);

		final IUOMConversionDAO uomConversionsRepo = Services.get(IUOMConversionDAO.class);
		uomConversionsRepo.createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(from.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(to.getC_UOM_ID()))
				.fromToMultiplier(multiplyRate)
				.toFromMultiplier(divideRate)
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

			QtyCalculationsBOMLine bomLine = QtyCalculationsBOMLine.builder()
					.bomProductId(juiceProductId)
					.bomProductUOM(uomLiters)
					//
					.componentType(BOMComponentType.Component)
					//
					// consider that 12.5% of a concentrate is needed to produce 1L of juice:
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
}
