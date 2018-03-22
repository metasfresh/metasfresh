package de.metas.handlingunits.allocation.transfer.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.collections.ListUtils;
import org.junit.Before;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;

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
 *
 */
public class LUTUProducerDestinationTestSupport
{
	public HUTestHelper helper;

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
	 *
	 * Creates a new instance with fresh masterdata. Also causes the {@link HUTestHelper} to be initialized.
	 * This constructor should be called from tests' {@link Before} methods.
	 *
	 * Creates PI as follows
	 * <ul>
	 * <li>LU (Palet)
	 * <li>Can hold IFCOs and Bags
	 * </ul>
	 */
	public LUTUProducerDestinationTestSupport()
	{
		helper = new HUTestHelper();
		helper.init();

		// TU (ifco) with capacities for tomatoes and salad
		{
			piTU_IFCO = helper.createHUDefinition("TU_IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

			piTU_Item_IFCO = helper.createHU_PI_Item_Material(piTU_IFCO);
			piTU_Item_Product_IFCO_40KgTomatoes = helper.assignProduct(piTU_Item_IFCO, helper.pTomato, new BigDecimal("40"), helper.uomKg);
			helper.assignProduct(piTU_Item_IFCO, helper.pSalad, new BigDecimal("7"), helper.uomEach);

			helper.createHU_PI_Item_PackingMaterial(piTU_IFCO, helper.pmIFCO);
		}

		// TU (bag) with different capacities for tomatoes and salad
		{
			piTU_Bag = helper.createHUDefinition("TU_Bag", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

			piTU_Item_Bag = helper.createHU_PI_Item_Material(piTU_Bag);
			piTU_Item_Product_Bag_8KgTomatoes = helper.assignProduct(piTU_Item_Bag, helper.pTomato, new BigDecimal("8"), helper.uomKg);
			helper.assignProduct(piTU_Item_Bag, helper.pSalad, new BigDecimal("5"), helper.uomEach);

			helper.createHU_PI_Item_PackingMaterial(piTU_Bag, helper.pmBag);
		}
		// LU (palet)
		{
			piLU = helper.createHUDefinition("LU_Palet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			piLU_Item_IFCO = helper.createHU_PI_Item_IncludedHU(piLU, piTU_IFCO, new BigDecimal("5"));
			piLU_Item_Bag = helper.createHU_PI_Item_IncludedHU(piLU, piTU_Bag, new BigDecimal("2"));

			helper.createHU_PI_Item_PackingMaterial(piLU, helper.pmPalet);
		}

		{
			piTruckUnlimitedCapacity = helper.createHUDefinition(HUTestHelper.NAME_Truck_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

			piTruck_UnlimitedCapacity_Item = helper.createHU_PI_Item_Material(piTruckUnlimitedCapacity);
			final I_M_HU_PI_Item_Product piItemProduct = helper.assignProduct(piTruck_UnlimitedCapacity_Item, helper.pTomato, new BigDecimal("6"), helper.uomEach);
			piItemProduct.setIsInfiniteCapacity(true);
			InterfaceWrapperHelper.save(piItemProduct);

			// helper.createHU_PI_Item_PackingMaterial(huDefTruck, null); // in this case there is no truck M_Product

			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(helper.attr_CountryMadeIn)
					.setM_HU_PI(piTruckUnlimitedCapacity));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(helper.attr_FragileSticker)
					.setM_HU_PI(piTruckUnlimitedCapacity));
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(helper.attr_Volume)
					.setM_HU_PI(piTruckUnlimitedCapacity)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}
	}

	public I_M_HU createLU(final int qtyTUs, final int qtyCUPerTU)
	{
		return createLU(qtyTUs, qtyCUPerTU, lutuProducer -> {
		});
	}

	public I_M_HU createLU(final int qtyTUs, final int qtyCUPerTU, Consumer<LUTUProducerDestination> producerCustomizer)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		// lutuProducer.setM_Locator(locator);

		// LU
		lutuProducer.setLUPI(piLU);
		lutuProducer.setMaxLUs(1);
		lutuProducer.setMaxTUsPerLU(qtyTUs);

		// TU
		lutuProducer.setLUItemPI(piLU_Item_IFCO);
		lutuProducer.setTUPI(piTU_IFCO);
		lutuProducer.addCUPerTU(helper.pTomato, BigDecimal.valueOf(qtyCUPerTU), helper.uomKg);

		producerCustomizer.accept(lutuProducer);

		final int qtyCUTotal = qtyTUs * qtyCUPerTU;

		helper.load(lutuProducer, helper.pTomato, BigDecimal.valueOf(qtyCUTotal), helper.uomKg);

		final List<I_M_HU> hus = lutuProducer.getCreatedHUs();
		return ListUtils.singleElement(hus);
	}
}
