package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.materialtracking.WaschprobeStandardMasterData;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterialQuery;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialType;

/**
 * Test the standard use case:
 * <ul>
 * <li>have a Order for Washed carrots which has Unwashed carrots (component) and Big Carrots (co-product).<br>
 * This BOM structure is defined in task concept.
 * <li>test {@link ProductionMaterialQueryExecutor#retriveProductionMaterials()} for {@link ProductionMaterialType#RAW}, {@link ProductionMaterialType#PRODUCED} and {@link ProductionMaterialType#RAW}+
 * {@link ProductionMaterialType#PRODUCED}.
 * </ul>
 *
 * @task http://dewiki908/mediawiki/index.php/07371_Beleg_Waschprobe_%28109323219023%29
 */
public class ProductionMaterialQueryExecutor_StandardCase_Test
{
	private IContextAware context;
	private WaschprobeStandardMasterData data;

	//
	// Master data
	// private I_M_Product pCarrot_Washed;
	// private I_M_Product pCarrot_Unwashed;
	// private I_M_Product pCarrot_Big;
	/** Order under test */
	private I_PP_Order ppOrder;

	//
	// Expectations
	private IProductionMaterial expectedMaterial_UnwashedCarrot;
	private IProductionMaterial expectedMaterial_WashedCarrot;
	private IProductionMaterial expectedMaterial_BigCarrot;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.context = PlainContextAware.newOutOfTrx();

		// NOTE: we are running this test without any model interceptors, listeners etc.
		// For that reason we can safely enabled strict values because we want to make sure only the values we set in Order/Order BOM Line are used.
		POJOWrapper.setDefaultStrictValues(true);

		//
		// Create Master Data for our standard test case
		setupMasterData();
	}

	private void setupMasterData()
	{
		this.data = new WaschprobeStandardMasterData(context);

		final I_C_UOM uom1 = data.createUOM("UOM1");
		final I_C_UOM uom2 = data.createUOM("UOM2");
		final I_C_UOM uom3 = data.createUOM("UOM3");

		//
		// Create PP_Order with components
		this.ppOrder = data.createPP_Order(
				data.pCarrot_Washed,
				new BigDecimal("100"),
				uom1,
				TimeUtil.getDay(2015,12,19) // production date, doesn't really matter for this test
				);
		this.expectedMaterial_WashedCarrot = new PlainProductionMaterial(ProductionMaterialType.PRODUCED, data.pCarrot_Washed, new BigDecimal("100"), uom1);

		data.createPP_Order_BOMLine(ppOrder, X_PP_Order_BOMLine.COMPONENTTYPE_Component,
				data.pCarrot_Unwashed,
				new BigDecimal("200"),
				uom2);
		this.expectedMaterial_UnwashedCarrot = new PlainProductionMaterial(ProductionMaterialType.RAW, data.pCarrot_Unwashed, new BigDecimal("200"), uom2);

		data.createPP_Order_BOMLine(ppOrder, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product,
				data.pCarrot_Big,
				new BigDecimal("-50"), // co-products have negative issued qty
				uom3);
		// we expect the qty to be negated in case of co/by-products
		this.expectedMaterial_BigCarrot = new PlainProductionMaterial(ProductionMaterialType.PRODUCED, data.pCarrot_Big, new BigDecimal("50"), uom3);
	}

	/**
	 * Check retrieve: RAW materials, all products
	 */
	@Test
	public void testRetrieve_Raw_AnyProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				expectedMaterial_UnwashedCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.RAW));
	}

	/**
	 * Check retrieve: RAW materials for UnWashed Carrot.
	 *
	 * Expectation: same result as {@link #testRetrieve_Produced_AnyProduct()}.
	 */
	@Test
	public void testRetrieve_Raw_UnwashedCarrotProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				expectedMaterial_UnwashedCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.RAW)
				.setM_Product(data.pCarrot_Unwashed));
	}

	/**
	 * Check retrieve: RAW materials for Washed Carrot.
	 *
	 * Expectation: none
	 */
	@Test
	public void testRetrieve_Raw_WashedCarrotProduct()
	{
		final List<IProductionMaterial> expected = Collections.emptyList();
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.RAW)
				.setM_Product(data.pCarrot_Washed));
	}

	/**
	 * Check retrieve: RAW materials for Big Carrot.
	 *
	 * Expectation: none
	 */
	@Test
	public void testRetrieve_Raw_BigCarrotProduct()
	{
		final List<IProductionMaterial> expected = Collections.emptyList();
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.RAW)
				.setM_Product(data.pCarrot_Big));
	}

	/**
	 * Check retrieve: PRODUCED materials, all products
	 * */
	@Test
	public void testRetrieve_Produced_AnyProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				expectedMaterial_WashedCarrot
				, expectedMaterial_BigCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED));
	}

	/**
	 * Check retrieve: PRODUCED materials, Washed Carrot product
	 * */
	@Test
	public void testRetrieve_Produced_WashedCarrotProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				expectedMaterial_WashedCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED)
				.setM_Product(data.pCarrot_Washed));
	}

	/**
	 * Check retrieve: PRODUCED materials, Big Carrot product
	 * */
	@Test
	public void testRetrieve_Produced_BigCarrotProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				expectedMaterial_BigCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED)
				.setM_Product(data.pCarrot_Big));
	}

	/**
	 * Check retrieve: PRODUCED materials, UnWashed Carrot product
	 * */
	@Test
	public void testRetrieve_Produced_UnwashedCarrotProduct()
	{
		final List<IProductionMaterial> expected = Collections.emptyList();
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED)
				.setM_Product(data.pCarrot_Unwashed));
	}

	/**
	 * Check retrieve: RAW+PRODUCED materials, all products.
	 *
	 * NOTE: we expect in the same order we defined it (PP_Order, PP_Order_BOMLines)
	 */
	@Test
	public void testRetrieve_RawAndProduced_AnyProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				expectedMaterial_WashedCarrot
				, expectedMaterial_UnwashedCarrot
				, expectedMaterial_BigCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED, ProductionMaterialType.RAW));
	}

	/**
	 * Check retrieve: RAW+PRODUCED materials, Washed Carrot product.
	 *
	 * NOTE: we expect in the same order we defined it (PP_Order, PP_Order_BOMLines)
	 */
	@Test
	public void testRetrieve_RawAndProduced_WashedCarrotProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				expectedMaterial_WashedCarrot
				// , expectedMaterial_UnwashedCarrot
				// , expectedMaterial_BigCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED, ProductionMaterialType.RAW)
				.setM_Product(data.pCarrot_Washed));
	}

	/**
	 * Check retrieve: RAW+PRODUCED materials, UnWashed Carrot product.
	 *
	 * NOTE: we expect in the same order we defined it (PP_Order, PP_Order_BOMLines)
	 */
	@Test
	public void testRetrieve_RawAndProduced_UnWashedCarrotProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				// expectedMaterial_WashedCarrot
				expectedMaterial_UnwashedCarrot
				// , expectedMaterial_BigCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED, ProductionMaterialType.RAW)
				.setM_Product(data.pCarrot_Unwashed));
	}

	/**
	 * Check retrieve: RAW+PRODUCED materials, Big Carrot product.
	 *
	 * NOTE: we expect in the same order we defined it (PP_Order, PP_Order_BOMLines)
	 */
	@Test
	public void testRetrieve_RawAndProduced_BigCarrotProduct()
	{
		final List<IProductionMaterial> expected = Arrays.<IProductionMaterial> asList(
				// expectedMaterial_WashedCarrot
				// expectedMaterial_UnwashedCarrot
				expectedMaterial_BigCarrot
				);
		testRetrieve(expected, new ProductionMaterialQuery()
				.setPP_Order(ppOrder)
				.setTypes(ProductionMaterialType.PRODUCED, ProductionMaterialType.RAW)
				.setM_Product(data.pCarrot_Big));
	}

	private void testRetrieve(final List<IProductionMaterial> expected, final IProductionMaterialQuery query)
	{
		final List<IProductionMaterial> actual = new ProductionMaterialQueryExecutor(query)
				.retriveProductionMaterials();

		WaschprobeStandardMasterData.assertEquals(expected, actual);
	}
}
