package de.metas.handlingunits.allocation.transfer.impl;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUTestHelper.TestHelperLoadRequest;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.model.validator.M_HU;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.junit.Before;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Contains masterdata and common stuff to be used by different tests.
 * This class is convenient whenever you want to test with (aggregate) HUs that were created by the {@link LUTUProducerDestination}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class LUTUProducerDestinationTestSupport
{
	private static final String ATTR_Truck_Product = "Truck";

	public HUTestHelper helper;

	public LocatorId defaultLocatorId;

	/**
	 * The PI for the IFCO TU. By default one IFCO TU can hold 40kg of {@link HUTestHelper#pTomato} or 7pce of {@link HUTestHelper#pSalad}.
	 */
	public I_M_HU_PI piTU_IFCO;

	/**
	 * The PI-item with itemtype "Material" that links from the IFCO to the actual VHU which contains the material.
	 */
	public I_M_HU_PI_Item piTU_Item_IFCO;

	public final I_M_HU_PI_Item_Product piTU_Item_Product_IFCO_40KgTomatoes;

	/**
	 * The PI for the "Bag" TU. By default one bag TU can hold 8kg of {@link HUTestHelper#pTomato} or 5pce of {@link HUTestHelper#pSalad}.
	 * Also see {@link #piTU_Item_Product_Bag_8KgTomatoes}.
	 */
	public I_M_HU_PI piTU_Bag;

	public I_M_HU_PI_Item piTU_Item_Bag;

	public I_M_HU_PI piLU;

	public I_M_HU_PI_Item piLU_Item_Virtual;

	/**
	 * The PI-Item with itemtype "HandlingUnit" that links from the LU's PI "downwards" to the {@link #piTU_IFCO} sub-PI.<br>
	 * One LU can hold 5 IFCOs
	 */
	public I_M_HU_PI_Item piLU_Item_IFCO;

	/**
	 * The PI-Item with itemtype "HandlingUnit" that links from the LU's PI "downwards" to the {@link #piTU_Bag} sub-PI. One LU can hold two bags.
	 */
	public I_M_HU_PI_Item piLU_Item_Bag;

	public final I_M_HU_PI_Item_Product piTU_Item_Product_Bag_8KgTomatoes;

	public I_M_HU_PI piTruckUnlimitedCapacity;
	public I_M_HU_PI_Item piTruck_UnlimitedCapacity_Item;

	/**
	 * This is an <b>alternative</b> to calling link AdempiereTestHelper#init()}! Don't call both, they will reset each other's stuff.
	 * <p>
	 * Creates a new instance with fresh masterdata. Also causes the {@link HUTestHelper} to be initialized.
	 * This constructor should be called from tests' {@link Before} methods.
	 * <p>
	 * Creates PI as follows
	 * <ul>
	 * <li>LU (Palet)
	 * <li>Can hold IFCOs and Bags
	 * </ul>
	 */
	public LUTUProducerDestinationTestSupport()
	{
		helper = new HUTestHelper()
		{
			@Override
			public void beforeRegisteringServices()
			{
				LUTUProducerDestinationTestSupport.this.beforeRegisteringServices();
			}
		};
		helper.init();

		{
			defaultLocatorId = createLocatorId();
		}

		// TU (ifco) with capacities for tomatoes and salad
		{
			piTU_IFCO = helper.createHUDefinition("TU_IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

			piTU_Item_IFCO = helper.createHU_PI_Item_Material(piTU_IFCO);
			piTU_Item_Product_IFCO_40KgTomatoes = helper.assignProduct(piTU_Item_IFCO, helper.pTomatoProductId, new BigDecimal("40"), helper.uomKg);
			helper.assignProduct(piTU_Item_IFCO, helper.pSaladProductId, new BigDecimal("7"), helper.uomEach);

			helper.createHU_PI_Item_PackingMaterial(piTU_IFCO, helper.pmIFCO);
		}

		// TU (bag) with different capacities for tomatoes and salad
		{
			piTU_Bag = helper.createHUDefinition("TU_Bag", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

			piTU_Item_Bag = helper.createHU_PI_Item_Material(piTU_Bag);
			piTU_Item_Product_Bag_8KgTomatoes = helper.assignProduct(piTU_Item_Bag, helper.pTomatoProductId, new BigDecimal("8"), helper.uomKg);
			helper.assignProduct(piTU_Item_Bag, helper.pSaladProductId, new BigDecimal("5"), helper.uomEach);

			helper.createHU_PI_Item_PackingMaterial(piTU_Bag, helper.pmBag);
		}
		// LU (palet)
		{
			piLU = helper.createHUDefinition("LU_Palet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			piLU_Item_IFCO = helper.createHU_PI_Item_IncludedHU(piLU, piTU_IFCO, new BigDecimal("5"));
			piLU_Item_Bag = helper.createHU_PI_Item_IncludedHU(piLU, piTU_Bag, new BigDecimal("2"));
			piLU_Item_Virtual = helper.createHU_PI_Item_IncludedHU(piLU, helper.huDefVirtual, new BigDecimal("1"));

			helper.createHU_PI_Item_PackingMaterial(piLU, helper.pmPalet);
		}

		{
			piTruckUnlimitedCapacity = helper.createHUDefinition(ATTR_Truck_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

			piTruck_UnlimitedCapacity_Item = helper.createHU_PI_Item_Material(piTruckUnlimitedCapacity);
			final I_M_HU_PI_Item_Product piItemProduct = helper.assignProduct(piTruck_UnlimitedCapacity_Item, helper.pTomatoProductId, new BigDecimal("6"), helper.uomEach);
			piItemProduct.setIsInfiniteCapacity(true);
			save(piItemProduct);

			// helper.createHU_PI_Item_PackingMaterial(huDefTruck, null); // in this case there is no truck M_Product

			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(helper.attr_CountryMadeIn)
					.setM_HU_PI(piTruckUnlimitedCapacity));
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(helper.attr_FragileSticker)
					.setM_HU_PI(piTruckUnlimitedCapacity));
			helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(helper.attr_Volume)
					.setM_HU_PI(piTruckUnlimitedCapacity)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}
	}

	public void beforeRegisteringServices()
	{
	}

	private LocatorId createLocatorId()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);

		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		saveRecord(locator);

		return LocatorId.ofRecord(locator);
	}

	public I_M_HU createLU(final int qtyTUs, final int qtyCUPerTU)
	{
		return createLU(qtyTUs, qtyCUPerTU, lutuProducer -> {
		});
	}

	public I_M_HU createLU(final int qtyTUs, final int qtyCUPerTU, final Consumer<LUTUProducerDestination> producerCustomizer)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(defaultLocatorId);

		// LU
		lutuProducer.setLUPI(piLU);
		lutuProducer.setMaxLUs(1);
		lutuProducer.setMaxTUsPerLU(qtyTUs);

		// TU
		lutuProducer.setLUItemPI(piLU_Item_IFCO);
		lutuProducer.setTUPI(piTU_IFCO);
		lutuProducer.addCUPerTU(helper.pTomatoProductId, BigDecimal.valueOf(qtyCUPerTU), helper.uomKg);

		producerCustomizer.accept(lutuProducer);

		final int qtyCUTotal = qtyTUs * qtyCUPerTU;

		helper.load(lutuProducer, helper.pTomatoProductId, BigDecimal.valueOf(qtyCUTotal), helper.uomKg);

		final List<I_M_HU> hus = lutuProducer.getCreatedHUs();
		return CollectionUtils.singleElement(hus);
	}

	/**
	 * Makes a stand alone CU with the given quantity and status "active".
	 */
	public I_M_HU mkRealStandAloneCuWithCuQty(final String strCuQty)
	{
		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI()
				.setLocatorId(defaultLocatorId);

		final TestHelperLoadRequest loadRequest = HUTestHelper.TestHelperLoadRequest.builder()
				.producer(producer)
				.cuProductId(helper.pTomatoProductId)
				.loadCuQty(new BigDecimal(strCuQty))
				.loadCuUOM(helper.uomKg)
				// .huPackingMaterialsCollector(noopPackingMaterialsCollector)
				.build();

		helper.load(loadRequest);

		final List<I_M_HU> createdCUs = producer.getCreatedHUs();
		assertThat(createdCUs.size(), is(1));

		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		final I_M_HU cuToSplit = createdCUs.get(0);
		huStatusBL.setHUStatus(helper.getHUContext(), cuToSplit, X_M_HU.HUSTATUS_Active);
		save(cuToSplit);

		return cuToSplit;
	}

	public I_M_HU mkRealCUWithTUandQtyCU(final String strCuQty)
	{
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(defaultLocatorId);
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(piTU_IFCO);

		final BigDecimal cuQty = new BigDecimal(strCuQty);
		helper.load(lutuProducer, helper.pTomatoProductId, cuQty, helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();
		assertThat(createdTUs.size(), is(1));

		final I_M_HU createdTU = createdTUs.get(0);
		huStatusBL.setHUStatus(helper.getHUContext(), createdTU, X_M_HU.HUSTATUS_Active);
		M_HU.INSTANCE.updateChildren(createdTU);
		save(createdTU);

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTU);
		assertThat(createdCUs.size(), is(1));

		return createdCUs.get(0);
	}

	/**
	 * Creates an LU with PI {@link LUTUProducerDestinationTestSupport#piLU} and an aggregate TU with PI {@link LUTUProducerDestinationTestSupport#piTU_IFCO}.
	 */
	public I_M_HU mkAggregateHUWithTotalQtyCU(final String totalQtyCUStr)
	{
		final int qtyCUsPerTU = -1; // N/A
		return mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU(totalQtyCUStr, qtyCUsPerTU);
	}

	/**
	 * Creates an LU with one aggregate HU. Both the LU's and aggregate HU's status is "active".
	 */
	public I_M_HU mkAggregateHUWithTotalQtyCUandCustomQtyCUsPerTU(final String totalQtyCUStr, final int customQtyCUsPerTU)
	{
		final ProductId cuProductId = helper.pTomatoProductId;
		final I_C_UOM cuUOM = helper.uomKg;
		final BigDecimal totalQtyCU = new BigDecimal(totalQtyCUStr);

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(defaultLocatorId);
		lutuProducer.setLUItemPI(piLU_Item_IFCO);
		lutuProducer.setLUPI(piLU);
		lutuProducer.setTUPI(piTU_IFCO);
		lutuProducer.setMaxTUsPerLU(Integer.MAX_VALUE); // allow as many TUs on that one pallet as we want

		// Custom TU capacity (if specified)
		if (customQtyCUsPerTU > 0)
		{
			lutuProducer.addCUPerTU(cuProductId, BigDecimal.valueOf(customQtyCUsPerTU), cuUOM);
		}

		final TestHelperLoadRequest loadRequest = HUTestHelper.TestHelperLoadRequest.builder()
				.producer(lutuProducer)
				.cuProductId(cuProductId)
				.loadCuQty(totalQtyCU)
				.loadCuUOM(cuUOM)
				// .huPackingMaterialsCollector(noopPackingMaterialsCollector)
				.build();

		helper.load(loadRequest);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		assertThat(createdLUs.size(), is(1));
		// data.helper.commitAndDumpHU(createdLUs.get(0));

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU createdLU = createdLUs.get(0);
		final IMutableHUContext huContext = helper.createMutableHUContextOutOfTransaction();
		huStatusBL.setHUStatus(huContext, createdLU, X_M_HU.HUSTATUS_Active);
		assertThat(createdLU.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		M_HU.INSTANCE.updateChildren(createdLU);
		save(createdLU);

		final List<I_M_HU> createdAggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(createdLUs.get(0));
		assertThat(createdAggregateHUs.size(), is(1));

		final I_M_HU cuToSplit = createdAggregateHUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(cuToSplit), is(true));
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_PI_Item_ID(), is(piLU_Item_IFCO.getM_HU_PI_Item_ID()));
		assertThat(cuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active));

		return cuToSplit;
	}

	/**
	 * @param disableReason explains why we want to disable the collector.
	 */
	public void disableHUPackingMaterialsCollector(final String disableReason)
	{
		helper.getHUContext().getHUPackingMaterialsCollector().disable();
	}
}
