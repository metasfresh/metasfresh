package de.metas.material.planning.pporder.impl;

import static org.assertj.core.api.Assertions.assertThat;

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

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.util.Services;

/**
 * This class tests {@link IPPOrderBOMBL} in convert with {@link IPPOrderBOMDAO}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PPOrderBOMBLTest
{
	private MRPTestHelper helper;

	/** Service under test */
	@Autowired
	private PPOrderBOMBL ppOrderBOMBL;
	// Other services

	private IPPOrderBOMDAO ppOrderBOMDAO;

	@BeforeEach
	public void init()
	{
		// NOTE: after this, model validators will be also registered
		helper = new MRPTestHelper();

		ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	}

	/**
	 * Tests {@link PPOrderBOMBL#getQtyMultiplier(I_PP_Order_BOMLine, I_PP_Product_BOMLine)}.
	 */
	@Test
	@Disabled("gh #523: test doesn't work right now, and we might drop it in future")
	public void qualityMultiplierTest()
	{
		// Mocking the AB Alicesalat 250g case from db

		// Defining the needed UOM
		final I_C_UOM uomKillogram = createUOM("Killogram", 2, 0);
		final I_C_UOM uomStuck = createUOM("Stück", 0, 0);
		final I_C_UOM uomMillimeter = createUOM("Millimeter", 2, 0);
		final I_C_UOM uomRolle = createUOM("Rolle", 2, 0);

		// Defining products

		final I_M_Product pSalad = helper.createProduct("Salad", uomStuck); // AB Alicesalat 250g - the big product bom

		// Conversion Salad
		helper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(pSalad.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(uomStuck.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomKillogram.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal(0.25))
				.build());

		// Components
		final I_M_Product pCarrot = helper.createProduct("Carrot", uomKillogram); // Karotten Julienne 3.2 mm Halbfabrikat

		final I_M_Product pFrisee = helper.createProduct("Frisée", uomKillogram); // Frisée Industrie

		final I_M_Product pRadiesli = helper.createProduct("Radiesli", uomKillogram); // P001697_Radiesli Julienne 3.2 mm Halbfabrikat

		// Packing material
		final I_M_Product pFolie = helper.createProduct("Folie", uomRolle); // Folie AB Alicesalat (1000 lm)

		//
		// Conversion for Folie
		helper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(pFolie.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(uomRolle.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomMillimeter.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal(1500000))
				.build());

		//
		// Define BOM
		//@formatter:off
		final I_PP_Product_BOM saladProductBom = helper.newProductBOM()
				.product(pSalad).uom(uomStuck)
				// Carrot
				.newBOMLine()
					.product(pCarrot).uom(uomKillogram)
					.setIsQtyPercentage(true)
					.setQtyBatch(new BigDecimal(44))
					.endLine()
				// Frisee
				.newBOMLine()
					.product(pFrisee).uom(uomKillogram)
					.setIsQtyPercentage(true)
					.setQtyBatch(new BigDecimal(36))
					.endLine()
				// Radisli
				.newBOMLine()
					.product(pRadiesli).uom(uomKillogram)
					.setIsQtyPercentage(true)
					.setQtyBatch(new BigDecimal(20))
					.endLine()
				// Folie
				.newBOMLine()
					.product(pFolie).uom(uomMillimeter)
					.setIsQtyPercentage(false)
					.setQtyBOM(new BigDecimal(260))
					.endLine()
				//
				.build();
		//@formatter:off

		//
		// Create and save PP Order
		final I_PP_Order ppOrder = helper.createPP_Order(saladProductBom, "100", uomStuck);

		//
		// Test: Carrot
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Carrot = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pCarrot);
			assertUOM(uomKillogram, ppOrderBOMLine_Carrot);

			final BigDecimal multipliedQty = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Carrot).getFinishedGoodQtyMultiplier(); // lineCarrot
			
			// qty ordered = 100, qty batch = 44 (percentaje) -> 0,44 per one stuck
			// one stuck = 0,25 kg -> the qty shall be 0,44 * 0,25 = 0,11
			assertThat(multipliedQty).isEqualByComparingTo("0.11");
		}

		//
		// Test: Frisee
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Frisee = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFrisee);
			assertUOM(uomKillogram, ppOrderBOMLine_Frisee);

			final BigDecimal multipliedQty = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Frisee).getFinishedGoodQtyMultiplier(); // lineFrisee
			assertThat(multipliedQty).isEqualByComparingTo("0.09");
		}

		//
		// Test: Radisli
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Radiesli = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pRadiesli);
			assertUOM(uomKillogram, ppOrderBOMLine_Radiesli);

			final BigDecimal multipliedQty = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Radiesli).getFinishedGoodQtyMultiplier(); // lineRadisli
			assertThat(multipliedQty).isEqualByComparingTo("0.05");
		}

		//
		// Test: Folie
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
			assertUOM(uomMillimeter, ppOrderBOMLine_Folie);

			final BigDecimal multipliedQty = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Folie).getFinishedGoodQtyMultiplier(); // lineFolie
			assertThat(multipliedQty).isEqualByComparingTo("260");
		}
	}

	private I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecission)
	{
		final I_C_UOM uom = helper.createUOM(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecission);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}

	private final void assertUOM(final I_C_UOM expectedUOM, final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		assertThat(ppOrderBOMLine.getC_UOM())
			.as("BOM line's UOM: "+ppOrderBOMLine)
			.isEqualTo(expectedUOM);
	}
}
