package de.metas.handlingunits.allocation.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Node;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.MockedAllocationSourceDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.handlingunits.util.TraceUtils;

/**
 *
 * NOTE: use http://www.xpathtester.com/test to test your XPaths
 *
 * @author tsa
 *
 */
// @SuppressWarnings("PMD.SingularField")
public class HULoaderTest extends AbstractHUTest
{
	private I_M_HU_PI huDefPalet;
	private I_M_HU_PI huDefIFCO;
	private I_M_HU_PI huDefBlister;
	private I_M_HU_PI huDefTruck;

	@Override
	protected void initialize()
	{
		//
		// Handling Units Definition
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomato, BigDecimal.TEN, uomEach);

			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);

			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefIFCO));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefIFCO));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefIFCO)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal("2"));
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);

			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefPalet));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefPalet));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefPalet)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}

		huDefBlister = helper.createHUDefinition(HUTestHelper.NAME_Blister_Product);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefBlister);
			helper.assignProduct(itemMA, pTomato, new BigDecimal("6"), uomEach);

			//helper.createHU_PI_Item_PackingMaterial(huDefBlister, null); // in this case there is no blister M_Product

			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefBlister));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefBlister));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefBlister)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}

		huDefTruck = helper.createHUDefinition(HUTestHelper.NAME_Truck_Product);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefTruck);
			helper.assignProductInfiniteCapacity(itemMA, pTomato, new BigDecimal("6"), uomEach);

			//helper.createHU_PI_Item_PackingMaterial(huDefTruck, null); // in this case there is no truck M_Product

			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefTruck));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefTruck));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefTruck)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}
	}

	@Test
	public void useCase1()
	{
		// assume that incomingTrxDoc is a material receipt of 23 tomatoes
		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts, pTomato, new BigDecimal(23));

		// create and destroy instances only with a I_M_Transaction
		final List<I_M_HU> huPalets = createPlainHU(incomingTrxDoc, huDefPalet);

		//
		// Validate HUs
		{
			final Node huPaletsXML = HUXmlConverter.toXml("Palets", huPalets);
			System.out.println("" + HUXmlConverter.toString(huPaletsXML));

			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet)", Matchers.equalTo("2")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='20']"));

			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet[1]/Item[1]/HU-IFCO)", Matchers.equalTo("2")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Item[1]/HU-IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='10']"));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Item[1]/HU-IFCO[2]/Storage[@M_Product_Value='Tomato' and @Qty='10']"));

			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet[2]/Item[1]/HU-IFCO)", Matchers.equalTo("1")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[2]/Item[1]/HU-IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='3']"));
		}

		HUAssert.assertAllStoragesAreValid();

		// TraceUtils.dump(huPalets);
		TraceUtils.dumpTransactions();
	}

	@Test
	public void useCase2()
	{
		// assume that incomingTrxDoc is a material receipt of 20 tomatoes
		final I_M_Transaction incomingTrx = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts, pTomato, new BigDecimal(23));

		final List<I_M_HU> huPalets = createPlainHU(incomingTrx, huDefPalet);

		final IAttributeStorageFactory attrStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage huPalet1_Attrs = attrStorageFactory.getAttributeStorage(huPalets.get(0));
		huPalet1_Attrs.setValue(attr_CountryMadeIn, HUTestHelper.COUNTRYMADEIN_DE);

		//
		// Validate HUs
		{
			final Node huPaletsXML = HUXmlConverter.toXml("Palets", huPalets);
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet)", Matchers.equalTo("2")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='20']"));

			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet[1]/Item[1]/HU-IFCO)", Matchers.equalTo("2")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Item[1]/HU-IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='10']"));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Item[1]/HU-IFCO[2]/Storage[@M_Product_Value='Tomato' and @Qty='10']"));

			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet[2]/Item[1]/HU-IFCO)", Matchers.equalTo("1")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[2]/Item[1]/HU-IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='3']"));

			HUAssert.assertAllStoragesAreValid();
		}

		//
		// Transfer 17 items from Palets to new Blisters
		final List<I_M_HU> huBlisters = helper.transferMaterialToNewHUs(huPalets, new BigDecimal("17"), pTomato, huDefBlister);

		//
		// Validate Palets after moving to blisters
		{
			final Node huPaletsXML = HUXmlConverter.toXml("Palets", huPalets);
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet)", Matchers.equalTo("2")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='3']"));

			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet[1]/Item[1]/HU-IFCO)", Matchers.equalTo("2")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Item[1]/HU-IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='0']"));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[1]/Item[1]/HU-IFCO[2]/Storage[@M_Product_Value='Tomato' and @Qty='3']"));

			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-Palet[2]/Item[1]/HU-IFCO)", Matchers.equalTo("1")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-Palet[2]/Item[1]/HU-IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='3']"));

			HUAssert.assertAllStoragesAreValid();
		}
		//
		// Validate Blisters after transferring 17 items to it
		{
			final Node huBlistersXML = HUXmlConverter.toXml("Blisters", huBlisters);
			// System.out.println(XmlConverter.toString(huBlistersXML));

			Assert.assertThat(huBlistersXML, Matchers.hasXPath("count(/Blisters/HU-Blister)", Matchers.equalTo("3")));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[1]/Storage[@M_Product_Value='Tomato' and @Qty='6']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[2]/Storage[@M_Product_Value='Tomato' and @Qty='6']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[3]/Storage[@M_Product_Value='Tomato' and @Qty='5']"));
		}

		//
		// Transfer another 1 item from Palets to existing Blisters
		helper.transferMaterialToExistingHUs(huPalets, huBlisters, pTomato, BigDecimal.ONE, uomEach);

		//
		// Validate Blisters after transferring another 1 item to it (so we got 18 in total)
		{
			final Node huBlistersXML = HUXmlConverter.toXml("Blisters", huBlisters);
			// System.out.println(XmlConverter.toString(huBlistersXML));

			Assert.assertThat(huBlistersXML, Matchers.hasXPath("count(/Blisters/HU-Blister)", Matchers.equalTo("3")));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[1]/Storage[@M_Product_Value='Tomato' and @Qty='6']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[2]/Storage[@M_Product_Value='Tomato' and @Qty='6']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[3]/Storage[@M_Product_Value='Tomato' and @Qty='6']"));

			HUAssert.assertAllStoragesAreValid();
		}

		//
		// Shipment: ship 17 items from blisters
		final I_M_Transaction outgoingTrx = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_CustomerShipment,
				pTomato,
				new BigDecimal("-17"));
		helper.transferHUsToOutgoing(outgoingTrx, huBlisters);

		//
		// Validate Blisters after taking out 17 items
		{
			final Node huBlistersXML = HUXmlConverter.toXml("Blisters", huBlisters);
			// System.out.println(XmlConverter.toString(huBlistersXML));

			Assert.assertThat(huBlistersXML, Matchers.hasXPath("count(/Blisters/HU-Blister)", Matchers.equalTo("3")));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[1]/Storage[@M_Product_Value='Tomato' and @Qty='0']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[2]/Storage[@M_Product_Value='Tomato' and @Qty='0']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Blisters/HU-Blister[3]/Storage[@M_Product_Value='Tomato' and @Qty='1']"));

			HUAssert.assertAllStoragesAreValid();
		}

		// TraceUtils.dump(huPalets);
		// TraceUtils.dump(huBlisters);
		// TraceUtils.dumpTransactions();
	}

	@Test
	public void useCase3()
	{
		// IsInfiniteCapacity=true will be tested here

		// assume that incomingTrxDoc is a material receipt of 23 tomatoes
		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts, pTomato, new BigDecimal(23));

		// create and destroy instances only with a I_M_Transaction
		final List<I_M_HU> huTruck =createPlainHU(incomingTrxDoc, huDefTruck);

		//
		// Validate HUs
		{
			final Node huPaletsXML = HUXmlConverter.toXml("Truck", huTruck);
			// System.out.println("" + XmlConverter.toString(huPaletsXML));

			// We're asserting that only ONE truck was created, and that it has all 23 products.
			// The truck's product item has the allowed qty limited to 6, BUT it has IsInfiniteCapacity checked
			// As such, the entire qty will be allocated to this particular item
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("count(/Truck/HU-Truck)", Matchers.equalTo("1")));
			Assert.assertThat(huPaletsXML, Matchers.hasXPath("/Truck/HU-Truck[1]/Storage[@M_Product_Value='Tomato' and @Qty='23']"));

			HUAssert.assertAllStoragesAreValid();
		}

		// TraceUtils.dump(huPalets);
		TraceUtils.dumpTransactions();
	}

	@Test
	public void test_load_to_reversal()
	{
		//
		// Create incoming
		final I_M_Transaction mtrx = helper.createMTransaction(
				X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomato,
				new BigDecimal(23));
		POJOWrapper.setInstanceName(mtrx, "Incoming trx");

		// // create and destroy instances only with a I_M_Transaction
		// // final List<I_M_HU> huPalets =
		// trxBL.transferIncomingToHUs(mtrx, huDefPalet);

		//
		// Create incoming mtrx reversal
		final I_M_Transaction mtrxReversal = helper.createMTransactionReversal(mtrx);
		POJOWrapper.setInstanceName(mtrxReversal, "Incoming trx reversal");
		Assert.assertThat("Reversal qty shall be original qty negated",
				mtrxReversal.getMovementQty(),
				Matchers.comparesEqualTo(mtrx.getMovementQty().negate()));

		//
		// Create incoming "source" and validate
		final MTransactionAllocationSourceDestination mtrxSource = new MTransactionAllocationSourceDestination(mtrx);
		HUAssert.assertStorageLevels(mtrxSource.getStorage(), "23", "23", "0"); // Qty Total/Allocated/Available

		//
		// Create reversal "destination" and validate
		final MTransactionAllocationSourceDestination mtrxReversalDestination = new MTransactionAllocationSourceDestination(mtrxReversal);
		HUAssert.assertStorageLevels(mtrxReversalDestination.getStorage(), "23", "0", "23"); // Qty Total/Allocated/Available

		final IMutableHUContext huContext = helper.getHUContext();

		final HULoader loader = new HULoader(mtrxSource, mtrxReversalDestination);

		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				mtrx.getM_Product(),
				mtrx.getMovementQty(),
				Services.get(IHandlingUnitsBL.class).getC_UOM(mtrx),
				helper.getTodayDate(),
				mtrx);

		// dumpStatusAfterTest = true;
		loader.load(request);

		//
		// Reload mtrx storage and validate it
		{
			final IProductStorage mtrxStorage2 = new MTransactionAllocationSourceDestination(mtrx)
					.getStorage();

			HUAssert.assertStorageLevels(mtrxStorage2, "23", "0", "23"); // Total/Qty/Free
		}

		//
		// Reload mtrx storage reversal and validate it
		{
			final IProductStorage mtrxReversalStorage2 = new MTransactionAllocationSourceDestination(mtrxReversal)
					.getStorage();

			HUAssert.assertStorageLevels(mtrxReversalStorage2, "23", "23", "0"); // Total/Qty/Free
		}

		HUAssert.assertAllStoragesAreValid();
	}

	// pure unit test
	@Test
	public void test_partialUnload()
	{
		//
		// Setup
		final IMutableHUContext huContext = helper.getHUContext();
		final Object referenceModel = helper.createDummyReferenceModel();
		final MockedAllocationSourceDestination source = new MockedAllocationSourceDestination();
		final MockedAllocationSourceDestination destination = new MockedAllocationSourceDestination();
		final HULoader loader = new HULoader(source, destination);

		//
		// Config
		final BigDecimal qtyRequest = new BigDecimal("10");
		source.setQtyToUnload(new BigDecimal("7"));
		destination.setQtyToLoad(MockedAllocationSourceDestination.ANY);
		loader.setAllowPartialUnloads(true);
		loader.setAllowPartialLoads(false); // shall not happen anyway

		//
		// Execute transfer
		final IAllocationRequest unloadRequest = AllocationUtils.createQtyRequest(
				huContext,
				pTomato,
				qtyRequest,
				pTomato.getC_UOM(),
				helper.getTodayDate(),
				referenceModel);
		final IAllocationResult result = loader.load(unloadRequest);

		//
		// Validate
		Assert.assertThat("Invalid QtyToAllocate",
				result.getQtyToAllocate(),
				Matchers.comparesEqualTo(new BigDecimal("0")));
		Assert.assertThat("Invalid QtyAllocated",
				result.getQtyAllocated(),
				Matchers.comparesEqualTo(new BigDecimal("7")));
	}

	// pure unit test
	@Test
	public void test_partialLoad()
	{
		//
		// Setup
		final IMutableHUContext huContext = helper.getHUContext();
		final Object referenceModel = helper.createDummyReferenceModel();
		final MockedAllocationSourceDestination source = new MockedAllocationSourceDestination();
		final MockedAllocationSourceDestination destination = new MockedAllocationSourceDestination();
		final HULoader loader = new HULoader(source, destination);

		//
		// Config
		final BigDecimal qtyRequest = new BigDecimal("10");
		source.setQtyToUnload(MockedAllocationSourceDestination.ANY);
		destination.setQtyToLoad(new BigDecimal("7"));
		loader.setAllowPartialUnloads(false);
		loader.setAllowPartialLoads(true);

		//
		// Execute transfer
		final IAllocationRequest unloadRequest = AllocationUtils.createQtyRequest(
				huContext,
				pTomato,
				qtyRequest,
				pTomato.getC_UOM(),
				helper.getTodayDate(),
				referenceModel);
		final IAllocationResult result = loader.load(unloadRequest);

		//
		// Validate
		Assert.assertThat("Invalid QtyToAllocate",
				result.getQtyToAllocate(),
				Matchers.comparesEqualTo(new BigDecimal(10 - 7)));
		Assert.assertThat("Invalid QtyAllocated",
				result.getQtyAllocated(),
				Matchers.comparesEqualTo(new BigDecimal(7)));
	}
}
