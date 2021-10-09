package de.metas.handlingunits;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_UOM;
import org.junit.Assert;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Contains BL for easily sampling / creating or splitting Handling Units
 */
public class AbstractHUTestWithSampling extends AbstractHUTest
{
	protected static final BigDecimal CU_QTY_85 = BigDecimal.valueOf(85);
	protected static final BigDecimal CU_QTY_46 = BigDecimal.valueOf(46);

	protected I_M_HU_PI huDefPalet;

	/**
	 * Included-HU item to link {@link #huDefIFCO_10} with {@link #huDefPalet}. 88 of those IFCOs fit on one palet.
	 */
	protected I_M_HU_PI_Item huItemIFCO_10;
	protected I_M_HU_PI_Item huItemIFCO_5;

	/**
	 * Included-HU item to link {@link #huDefIFCO_2} with {@link #huDefPalet}. 88 of those IFCOs fit on one palet.
	 */
	protected I_M_HU_PI_Item huItemIFCO_2;

	/**
	 * PI for IFCO, one of these IFCOs can hold 10kg tomato
	 */
	protected I_M_HU_PI huDefIFCO_10;
	protected I_M_HU_PI_Item materialItemTomato_10;

	/**
	 * HU PI Item that sais that 10kg of tomato fit onto one {@link #huDefIFCO_10}.
	 */
	protected I_M_HU_PI_Item_Product materialItemProductTomato_10;

	/**
	 * PI for IFCO, one of these IFCOs can hold 5kg tomato
	 */
	protected I_M_HU_PI huDefIFCO_5;
	protected I_M_HU_PI_Item materialItemTomato_5;

	/**
	 * HU PI Item that sais that 5kg of tomato fit onto one {@link #huDefIFCO_5}.
	 */
	protected I_M_HU_PI_Item_Product materialItemProductTomato_5;

	/**
	 * PI for IFCO, one of these IFCOs can hold 2kg tomato
	 */
	protected I_M_HU_PI huDefIFCO_2;
	protected I_M_HU_PI_Item materialItemTomato_2;

	/**
	 * HU PI Item that sais that 2kg of tomato fit onto one {@link #huDefIFCO_2}.
	 */
	protected I_M_HU_PI_Item_Product materialItemProductTomato_2;

	protected I_M_HU_PI huDefPaloxe_430;
	protected I_M_HU_PI_Item materialItemTomato_430;
	protected I_M_HU_PI_Item_Product materialItemProductTomato_430;

	protected I_M_HU_PI_Item packingMaterialItemPalet;
	protected I_M_HU_PI_Item packingMaterialItemIFCO_10;
	protected I_M_HU_PI_Item packingMaterialItemIFCO_5;
	protected I_M_HU_PI_Item packingMaterialItemIFCO_2;
	protected I_M_HU_PI_Item packingMaterialItemPaloxe_430;

	protected IHUContext huContext;
	protected IHUStorageFactory huStorageFactory;
	protected IAttributeStorageFactory attributeStorageFactory;

	@Override
	protected final void initialize()
	{
		//
		// Prepare context
		// final String trxName = helper.trxName; // use the helper's thread-inherited trxName
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		huContext = helper.createMutableHUContextForProcessing(trxName);
		huStorageFactory = huContext.getHUStorageFactory();
		attributeStorageFactory = huContext.getHUAttributeStorageFactory();

		//
		// Handling Units Definition
		huDefIFCO_10 = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			materialItemTomato_10 = helper.createHU_PI_Item_Material(huDefIFCO_10);
			materialItemProductTomato_10 = helper.assignProduct(materialItemTomato_10, getCUProductId(), BigDecimal.TEN, uomKg); // 10 x Tomato Per IFCO

			packingMaterialItemIFCO_10 = helper.createHU_PI_Item_PackingMaterial(huDefIFCO_10, pmIFCO);
		}

		huDefIFCO_5 = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			materialItemTomato_5 = helper.createHU_PI_Item_Material(huDefIFCO_5);
			materialItemProductTomato_5 = helper.assignProduct(materialItemTomato_5, getCUProductId(), BigDecimal.valueOf(5), uomKg); // 5 x Tomato Per IFCO

			packingMaterialItemIFCO_5 = helper.createHU_PI_Item_PackingMaterial(huDefIFCO_5, pmIFCO);
		}

		huDefIFCO_2 = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			materialItemTomato_2 = helper.createHU_PI_Item_Material(huDefIFCO_2);
			materialItemProductTomato_2 = helper.assignProduct(materialItemTomato_2, getCUProductId(), BigDecimal.valueOf(2), uomKg); // 2 x Tomato Per IFCO

			packingMaterialItemIFCO_2 = helper.createHU_PI_Item_PackingMaterial(huDefIFCO_2, pmIFCO);
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			huItemIFCO_10 = helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO_10, BigDecimal.valueOf(88)); // 88 x IFCO Per Palette
			huItemIFCO_5 = helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO_5, BigDecimal.valueOf(88)); // 88 x IFCO Per Palette
			huItemIFCO_2 = helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO_2, BigDecimal.valueOf(88)); // 88 x IFCO Per Palette
			packingMaterialItemPalet = helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);
		}

		huDefPaloxe_430 = helper.createHUDefinition(HUTestHelper.NAME_Paloxe_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			materialItemTomato_430 = helper.createHU_PI_Item_Material(huDefPaloxe_430);
			materialItemProductTomato_430 = helper.assignProduct(materialItemTomato_430, getCUProductId(), BigDecimal.valueOf(430), uomKg); // 430 x Tomato Per Paloxe

			packingMaterialItemPaloxe_430 = helper.createHU_PI_Item_PackingMaterial(huDefPaloxe_430, pmPaloxe);
		}

		afterInitialize();
	}

	/**
	 * Executed after {@link #initialize()}. To be overridden by implementing classes
	 */
	protected void afterInitialize()
	{
		// nothing at this level
	}

	protected final ProductId getCUProductId()
	{
		return pTomatoId;
	}

	protected I_C_UOM getCUUOM()
	{
		return uomEach;
	}

	/**
	 * Note: Sets GROSS weight on the LoadingUnit to given <code>grossWeight</code>
	 *
	 * @param loadingUnitPIItem the PI item of the LU to allocate on
	 * @param cuQty             the full amount of customer units (i.e Tomatoes) which you'll have on your LU-TU structure
	 * @param grossWeight       weight which was supposedly input by the user
	 * @return LU which was created
	 */
	protected final I_M_HU createIncomingLoadingUnit(
			final I_M_HU_PI_Item loadingUnitPIItem,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final BigDecimal cuQty,
			final BigDecimal grossWeight)
	{
		final BPartnerId bpartnerId = null;
		final int bpartnerLocationId = -1;

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				getCUProductId(),
				UomId.ofRepoId(getCUUOM().getC_UOM_ID()),
				bpartnerId,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU
		lutuConfiguration.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);
		lutuConfigurationFactory.save(lutuConfiguration);

		final ILUTUProducerAllocationDestination luProducerDestination = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		luProducerDestination.setLUPI(loadingUnitPIItem.getM_HU_PI_Version().getM_HU_PI());
		luProducerDestination.setLUItemPI(loadingUnitPIItem);
		luProducerDestination.setMaxLUsInfinite(); // allow creating as much LUs as needed

		//
		// Create and destroy instances only with an I_M_Transaction
		final List<I_M_HU> loadingUnits = new ArrayList<>();

		loadingUnits.addAll(helper.createHUs(huContext, luProducerDestination, cuQty));

		Assert.assertEquals("Invalid amount of initial LoadingUnits", 1, loadingUnits.size());
		final I_M_HU loadingUnit = loadingUnits.get(0);
		// HUXmlConverter.toString(HUXmlConverter.toXml(loadingUnit));
		//
		// Propagate WeightGross (this will also calculate Net); Net = Gross - Tare - TareAdjust
		final IAttributeStorage attributeStorageLoadingUnit = attributeStorageFactory.getAttributeStorage(loadingUnit);
		attributeStorageLoadingUnit.setValue(attr_WeightGross, grossWeight);

		return loadingUnit;
	}

	/**
	 * Sets WeightGross to given HUs.
	 * <p>
	 * Please note that WeightGross will be propagated and WeightNet will be also calculated (Net = Gross - Tare - TareAdjust).
	 */
	protected void setWeightGross(final List<I_M_HU> hus, final BigDecimal grossWeight)
	{
		//
		for (final I_M_HU hu : hus)
		{
			final IAttributeStorage attributeStorageTradingUnit = attributeStorageFactory.getAttributeStorage(hu);
			attributeStorageTradingUnit.setValue(attr_WeightGross, grossWeight);
		}
	}

	protected I_M_HU_LUTU_Configuration createM_HU_LUTU_Configuration_ForTU(final I_M_HU_PI_Item_Product tuPIItemProduct)
	{
		final BPartnerId bpartnerId = null;
		final int bpartnerLocationId = -1;

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				getCUProductId(),
				UomId.ofRepoId(getCUUOM().getC_UOM_ID()),
				bpartnerId,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU
		lutuConfiguration.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);
		lutuConfigurationFactory.save(lutuConfiguration);
		return lutuConfiguration;
	}

	/**
	 * @return first found tradingUnit inside the loadingUnit with given customerUnit qty
	 */
	@Nullable
	protected final I_M_HU findTUInLUWithQty(final I_M_HU loadingUnit, final int customerUnitQty)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Iterate through the loadingUnit's items
		final List<I_M_HU_Item> luItems = handlingUnitsDAO.retrieveItems(loadingUnit).stream()
				.filter(item -> X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(item.getItemType()) || X_M_HU_Item.ITEMTYPE_HUAggregate.equals(item.getItemType()))
				.collect(Collectors.toList());

		for (final I_M_HU_Item luItem : luItems)
		{
			//
			// Iterate through the tradingUnits
			final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(luItem);
			for (final I_M_HU tuHU : tuHUs)
			{
				final IHUStorage tradingUnitStorage = huStorageFactory.getStorage(tuHU);
				final BigDecimal tradingUnitQty = tradingUnitStorage.getQty(getCUProductId(), getCUUOM());
				if (tradingUnitQty.intValueExact() != customerUnitQty)
				{
					continue;
				}

				return tuHU;
			}
		}
		Check.errorIf(true, "Storage not found with desired qty {} in {}", customerUnitQty, loadingUnit);
		return null;
	}

	/**
	 * @param cuQty the qty to split, but note that the qty that is actually split is also limited by {@code cuPerTU}, {@code tuPerLU} and {@code maxLUToAllocate}
	 */
	protected final List<I_M_HU> splitLU(final I_M_HU luToSplit,
			final I_M_HU_PI_Item luPIItem,
			final I_M_HU_PI_Item tuPIItem,
			final BigDecimal cuQty,
			final BigDecimal cuPerTU,
			final BigDecimal tuPerLU,
			final BigDecimal maxLUToAllocate)
	{
		final ProductId cuProductId = getCUProductId();
		final I_C_UOM cuUOM = getCUUOM();

		final List<I_M_HU> loadingUnits = new ArrayList<>();

		loadingUnits.addAll(
				helper.splitHUs(
						huContext,
						luToSplit,
						cuProductId, cuQty, cuUOM,
						cuPerTU, tuPerLU, maxLUToAllocate,
						tuPIItem, luPIItem));

		return loadingUnits;
	}

	/**
	 * Note: Sets GROSS weight on the TradingUnits to given <code>grossWeight</code>
	 *
	 * @param tradingUnitPIItem the PI item of the TU to allocate on
	 * @param cuQty             the full amount of customer units (i.e Tomatoes) which you'll have on your LU-TU structure
	 * @param grossWeight       weight which was supposedly input by the user
	 * @return TUs created
	 */
	protected final List<I_M_HU> createIncomingTradingUnits(
			final I_M_HU_PI_Item tradingUnitPIItem,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final BigDecimal cuQty,
			final BigDecimal grossWeight)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = createM_HU_LUTU_Configuration_ForTU(tuPIItemProduct);

		final BigDecimal tuCapacity = tuPIItemProduct.getQty();

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final ILUTUProducerAllocationDestination luProducerDestination = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		luProducerDestination.setNoLU(); // disallow creation of LUs
		luProducerDestination.setTUPI(tradingUnitPIItem.getM_HU_PI_Version().getM_HU_PI());

		//
		// Create and destroy instances only with an I_M_Transaction
		final List<I_M_HU> tradingUnits = helper.createHUs(huContext, luProducerDestination, cuQty);

		Assert.assertEquals("Invalid amount of initial TradingUnits", cuQty.divide(tuCapacity, 2, RoundingMode.CEILING).intValueExact(), tradingUnits.size());

		//
		// Set and propagate WeightGross (this will also calculate Net)
		setWeightGross(tradingUnits, grossWeight);

		return tradingUnits;
	}

	/**
	 * Note: we also have {@link de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport#mkRealStandAloneCuWithCuQty(String)} which is kind of similar to this. 
	 */
	protected final I_M_HU createIncomingCustomerUnit(
			final BigDecimal cuQty,
			final BigDecimal grossWeight)
	{
		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI()
				.setLocatorId(defaultLocatorId);

		final HUTestHelper.TestHelperLoadRequest loadRequest = HUTestHelper.TestHelperLoadRequest.builder()
				.producer(producer)
				.cuProductId(helper.pTomatoProductId)
				.loadCuQty(cuQty)
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

		if (grossWeight != null)
		{
			final IAttributeStorageFactory attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
			final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(cuToSplit);
			final IWeightable weightable = Weightables.wrap(attributeStorage);
			weightable.setWeightNet(grossWeight);
			attributeStorage.saveChangesIfNeeded();
		}

		return cuToSplit;
	}
}
