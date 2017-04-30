package org.eevolution.mrp.api.impl;

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


import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.mrp.AbstractMRPTestBase;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.material.planning.IMutableMRPContext;

/**
 * Tests how {@link MRPExecutor} behaves when it needs to balance very small numbers.
 *
 * This test was triggered by http://dewiki908/mediawiki/index.php/07796_Folie_not_in_CMP_%28103789966781%29
 *
 * @author tsa
 *
 */
public class MRPExecutor_SmallNumbers_Test extends AbstractMRPTestBase
{
	private MRPTestDataSimple masterData;

	private I_C_UOM uomMillimeter;
	private I_C_UOM uomRolle;
	private I_M_Product pFolie;

	@Override
	protected void afterInit()
	{
		masterData = new MRPTestDataSimple(helper);

		uomRolle = helper.createUOM("Rolle", 5);
		uomMillimeter = helper.createUOM("Millimeter", 2);
		pFolie = helper.createProduct("Folie", uomRolle);

		//
		// Conversion for Folie
		helper.createUOMConversion(
				pFolie.getM_Product_ID(),
				uomRolle,
				uomMillimeter,
				new BigDecimal(1500000),
				new BigDecimal(0.000000666667));

		// Product Planning:
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.plant(masterData.plant01)
				.product(pFolie)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				.build();
	}

	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test()
	{
		helper.createMRPDemand(
				pFolie, // Product
				new BigDecimal("0.00001"), // Demanded Qty
				helper.getToday(), // Date
				masterData.plant01, // Plant
				masterData.warehouse_plant01 // Warehouse
		);

		//
		// Run MRP
		final IMutableMRPContext mrpContext = helper.createMutableMRPContext();
		mrpContext.setPlant(masterData.plant01);
		mrpContext.setM_Warehouse(masterData.warehouse_plant01);
		runMRP(mrpContext);

		//
		// Check MRP qtys
		helper.newMRPExpectation().warehouse(masterData.warehouse_plant01).product(pFolie)
				.qtyDemand("0.00001")
				.qtySupply("0.00001")
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
		helper.newMRPExpectation().warehouse(masterData.warehouse_rawMaterials01).product(pFolie)
				.qtyDemand("0.00001")
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected();
	}

}
