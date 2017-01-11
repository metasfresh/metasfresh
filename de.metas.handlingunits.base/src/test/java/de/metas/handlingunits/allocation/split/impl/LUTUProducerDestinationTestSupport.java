package de.metas.handlingunits.allocation.split.impl;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Before;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
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
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LUTUProducerDestinationTestSupport
{
	public HUTestHelper helper;

	public I_M_HU_PI piTU_IFCO;
	public I_M_HU_PI_Item piTU_Item_IFCO;

	public I_M_HU_PI piTU_Bag;
	public I_M_HU_PI_Item piTU_Item_Bag;

	public I_M_HU_PI piLU;
	public I_M_HU_PI_Item piLU_Item_IFCO;
	public I_M_HU_PI_Item piLU_Item_Bag;

	public I_M_HU_PI piTruckUnlimitedCapacity;
	public I_M_HU_PI_Item piTruck_UnlimitedCapacity_Item;
	
	/**
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
			helper.assignProduct(piTU_Item_IFCO, helper.pTomato, new BigDecimal("10"), helper.uomKg);
			helper.assignProduct(piTU_Item_IFCO, helper.pSalad, new BigDecimal("7"), helper.uomEach);

			helper.createHU_PI_Item_PackingMaterial(piTU_IFCO, helper.pmIFCO);
		}

		// TU (bag) with different capacities for tomatoes and salad
		{
			piTU_Bag = helper.createHUDefinition("TU_Bag", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

			piTU_Item_Bag = helper.createHU_PI_Item_Material(piTU_Bag);
			helper.assignProduct(piTU_Item_Bag, helper.pTomato, new BigDecimal("8"), helper.uomKg);
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
			piTruckUnlimitedCapacity = helper.createHUDefinition(HUTestHelper.NAME_Truck_Product);
			
			piTruck_UnlimitedCapacity_Item = helper.createHU_PI_Item_Material(piTruckUnlimitedCapacity);
			final I_M_HU_PI_Item_Product piItemProduct = helper.assignProduct(piTruck_UnlimitedCapacity_Item, helper.pTomato, new BigDecimal("6"), helper.uomEach);
			piItemProduct.setIsInfiniteCapacity(true);
			InterfaceWrapperHelper.save(piItemProduct);

			//helper.createHU_PI_Item_PackingMaterial(huDefTruck, null); // in this case there is no truck M_Product

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

}
