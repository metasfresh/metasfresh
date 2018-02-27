package de.metas.material.planning.pporder.impl;

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
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.eevolution.LiberoConfiguration;
import org.eevolution.LiberoTestConfiguration;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.mrp.api.impl.MRPTestDataSimple;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.document.engine.IDocument;
import de.metas.material.planning.MaterialPlanningConfiguration;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;

/**
 * This class tests {@link IPPOrderBOMBL} in convert with {@link IPPOrderBOMDAO}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LiberoConfiguration.class, LiberoTestConfiguration.class, MaterialPlanningConfiguration.class })
@ActiveProfiles("test")
public class PPOrderBOMBLTest
{
	private MRPTestHelper helper;
	private MRPTestDataSimple masterData;

	/** Service under test */
	@Autowired
	private PPOrderBOMBL ppOrderBOMBL;
	// Other services

	private IPPOrderBOMDAO ppOrderBOMDAO;

	@Before
	public void init()
	{
		// NOTE: after this, model validators will be also registered
		helper = new MRPTestHelper();
		masterData = new MRPTestDataSimple(helper);

		this.ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	}

	/**
	 * Tests {@link PPOrderBOMBL#getQtyMultiplier(I_PP_Order_BOMLine, I_PP_Product_BOMLine)}.
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
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
		helper.createUOMConversion(
				pSalad.getM_Product_ID(),
				uomStuck,
				uomKillogram,
				new BigDecimal(0.25),
				new BigDecimal(4));

		// Components
		final I_M_Product pCarrot = helper.createProduct("Carrot", uomKillogram); // Karotten Julienne 3.2 mm Halbfabrikat

		final I_M_Product pFrisee = helper.createProduct("Frisée", uomKillogram); // Frisée Industrie

		final I_M_Product pRadiesli = helper.createProduct("Radiesli", uomKillogram); // P001697_Radiesli Julienne 3.2 mm Halbfabrikat

		// Packing material
		final I_M_Product pFolie = helper.createProduct("Folie", uomRolle); // Folie AB Alicesalat (1000 lm)

		//
		// Conversion for Folie
		helper.createUOMConversion(
				pFolie.getM_Product_ID(),
				uomRolle,
				uomMillimeter,
				new BigDecimal(1500000),
				new BigDecimal(0.000000666667));

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
		final I_PP_Order ppOrder = createPP_Order(saladProductBom, "100", uomStuck);

		//
		// Test: Carrot
		{
			// qty ordered = 100, qty batch = 44 (percentaje) -> 0,44 per one stuck
			// one stuck = 0,25 kg -> the qty shall be 0,44 * 0,25 = 0,11
			BigDecimal expectedQty = new BigDecimal("0.11");
			final I_C_UOM expectedUOM = uomKillogram;

			final I_PP_Order_BOMLine ppOrderBOMLine_Carrot = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pCarrot);
			assertUOM(expectedUOM, ppOrderBOMLine_Carrot);

			BigDecimal multipliedQty = ppOrderBOMBL.getQtyMultiplier(ppOrderBOMBL.fromRecord(ppOrderBOMLine_Carrot)); // lineCarrot
			Assert.assertTrue("Should be" + expectedQty + "but it is " + multipliedQty, expectedQty.compareTo(multipliedQty) == 0);
		}

		//
		// Test: Frisee
		{
			final BigDecimal expectedQty = new BigDecimal("0.09");
			final I_C_UOM expectedUOM = uomKillogram;

			final I_PP_Order_BOMLine ppOrderBOMLine_Frisee = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFrisee);
			assertUOM(expectedUOM, ppOrderBOMLine_Frisee);

			final BigDecimal multipliedQty = ppOrderBOMBL.getQtyMultiplier(ppOrderBOMBL.fromRecord(ppOrderBOMLine_Frisee)); // lineFrisee
			Assert.assertTrue("Should be" + expectedQty + "but it is " + multipliedQty, expectedQty.compareTo(multipliedQty) == 0);
		}

		//
		// Test: Radisli
		{
			final BigDecimal expectedQty = new BigDecimal("0.05");
			final I_C_UOM expectedUOM = uomKillogram;

			final I_PP_Order_BOMLine ppOrderBOMLine_Radiesli = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pRadiesli);
			assertUOM(expectedUOM, ppOrderBOMLine_Radiesli);

			final BigDecimal multipliedQty = ppOrderBOMBL.getQtyMultiplier(ppOrderBOMBL.fromRecord(ppOrderBOMLine_Radiesli)); // lineRadisli
			Assert.assertTrue("Should be" + expectedQty + "but it is " + multipliedQty, expectedQty.compareTo(multipliedQty) == 0);
		}

		//
		// Test: Folie
		{
			final BigDecimal expectedQty = new BigDecimal("260");
			final I_C_UOM expectedUOM = uomMillimeter;

			final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
			assertUOM(expectedUOM, ppOrderBOMLine_Folie);

			final BigDecimal multipliedQty = ppOrderBOMBL.getQtyMultiplier(ppOrderBOMBL.fromRecord(ppOrderBOMLine_Folie)); // lineFolie
			Assert.assertTrue("Should be" + expectedQty + "but it is " + multipliedQty, expectedQty.compareTo(multipliedQty) == 0);
		}
	}

	private I_PP_Order createPP_Order(final I_PP_Product_BOM productBOM, final String qtyOrderedStr, final I_C_UOM uom)
	{
		final I_M_Product product = productBOM.getM_Product();

		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setAD_Org(masterData.adOrg01);

		setCommonProperties(ppOrder);

		ppOrder.setM_Product(product);
		ppOrder.setPP_Product_BOM(productBOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal(qtyOrderedStr));
		ppOrder.setDatePromised(helper.getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setC_UOM(uom);
		InterfaceWrapperHelper.save(ppOrder);
		return ppOrder;
	}

	private void setCommonProperties(final I_PP_Order ppOrder)
	{
		Services.get(IPPOrderBL.class).setDocType(ppOrder, X_C_DocType.DOCBASETYPE_ManufacturingOrder, null);

		// required to avoid an NPE when building the lightweight PPOrder pojo
		final Timestamp t1 = SystemTime.asTimestamp();
		ppOrder.setDateOrdered(t1);
		ppOrder.setDateStartSchedule(t1);
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
		Assert.assertEquals("Invalid Order BOMLine UOM: " + ppOrderBOMLine, expectedUOM, ppOrderBOMLine.getC_UOM());
	}
}
