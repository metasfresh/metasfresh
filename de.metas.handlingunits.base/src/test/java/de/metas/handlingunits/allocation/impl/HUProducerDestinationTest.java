package de.metas.handlingunits.allocation.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUProducerDestinationTest
{
	private HUTestHelper helper;
	private IHandlingUnitsBL handlingUnitsBL;

	@BeforeEach
	public void init()
	{
		helper = HUTestHelper.newInstanceOutOfTrx(); 

		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	private void forceLoadToDestination(
			@NonNull final IAllocationDestination destination,
			@NonNull final ProductId productId,
			final int qty,
			@NonNull final I_C_UOM uom)
	{
		boolean forceQtyAllocation = true;
		loadToDestination(destination, productId, qty, uom, forceQtyAllocation);
	}

	private void loadToDestination(
			@NonNull final IAllocationDestination destination,
			@NonNull final ProductId productId,
			final int qty,
			@NonNull final I_C_UOM uom)
	{
		boolean forceQtyAllocation = false;
		loadToDestination(destination, productId, qty, uom, forceQtyAllocation);
	}

	private void loadToDestination(
			@NonNull final IAllocationDestination destination,
			@NonNull final ProductId productId,
			final int qty,
			@NonNull final I_C_UOM uom,
			final boolean forceQtyAllocation)
	{
		final AbstractAllocationSourceDestination source = helper.createDummySourceDestination(
				productId,
				new BigDecimal("99999"),
				uom,
				true/* fullyLoaded */
		);

		HULoader.of(source, destination)
				.load(AllocationUtils.createQtyRequest(
						helper.createMutableHUContextOutOfTransaction(),
						productId,
						Quantity.of(qty, uom),
						helper.getTodayZonedDateTime(),
						helper.createDummyReferenceModel(),
						forceQtyAllocation));
	}

	private IHUStorage getSingleCreatedHUStorage(final IHUProducerAllocationDestination destination)
	{
		final List<I_M_HU> hus = destination.getCreatedHUs();
		assertThat(hus).hasSize(1);

		return getHUStorage(hus.get(0));
	}

	private IHUStorage getHUStorage(final I_M_HU hu)
	{
		return handlingUnitsBL.getStorageFactory()
				.getStorage(hu);
	}

	private IHUProductStorage getHUProductStorage(final I_M_HU hu, final ProductId productId)
	{
		return getHUStorage(hu)
				.getProductStorage(productId);
	}

	@Test
	public void load_1product_to_one_TU_several_transactions()
	{
		final I_M_HU_PI huPI;
		{
			huPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item huPIItem = helper.createHU_PI_Item_Material(huPI);
			helper.assignProduct(huPIItem, helper.pTomatoProductId, new BigDecimal("1"), helper.uomKg);
		}

		final IHUProducerAllocationDestination destination = HUProducerDestination.of(huPI)
				.setMaxHUsToCreate(1)
				.setHUStatus(X_M_HU.HUSTATUS_Active);

		forceLoadToDestination(destination, helper.pTomatoProductId, 10, helper.uomKg);
		forceLoadToDestination(destination, helper.pTomatoProductId, 2, helper.uomKg);

		final IHUStorage huStorage = getSingleCreatedHUStorage(destination);

		final IHUProductStorage tomatoesStorage = huStorage.getProductStorage(helper.pTomatoProductId);
		assertThat(tomatoesStorage.getQty().toBigDecimal()).isEqualByComparingTo("12");
	}

	@Test
	public void load_1product_to_several_TUs_several_transactions()
	{
		final I_M_HU_PI huPI;
		{
			huPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item huPIItem = helper.createHU_PI_Item_Material(huPI);
			helper.assignProduct(huPIItem, helper.pTomatoProductId, new BigDecimal("10"), helper.uomKg);
		}

		final IHUProducerAllocationDestination destination = HUProducerDestination.of(huPI)
				// .setMaxHUsToCreate(...)
				.setHUStatus(X_M_HU.HUSTATUS_Active);

		loadToDestination(destination, helper.pTomatoProductId, 9, helper.uomKg);
		loadToDestination(destination, helper.pTomatoProductId, 8, helper.uomKg);
		loadToDestination(destination, helper.pTomatoProductId, 7, helper.uomKg);

		final List<I_M_HU> hus = destination.getCreatedHUs();
		assertThat(hus).hasSize(3);

		final IHUProductStorage huStorage1 = getHUProductStorage(hus.get(0), helper.pTomatoProductId);
		assertThat(huStorage1.getQty().toBigDecimal()).isEqualByComparingTo("10");

		final IHUProductStorage huStorage2 = getHUProductStorage(hus.get(1), helper.pTomatoProductId);
		assertThat(huStorage2.getQty().toBigDecimal()).isEqualByComparingTo("10");

		final IHUProductStorage huStorage3 = getHUProductStorage(hus.get(2), helper.pTomatoProductId);
		assertThat(huStorage3.getQty().toBigDecimal()).isEqualByComparingTo("4");
	}

	@Test
	public void load_2products_to_one_TU()
	{
		final I_M_HU_PI huPI;
		{
			huPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item huPIItem = helper.createHU_PI_Item_Material(huPI);
			helper.assignProduct(huPIItem, helper.pTomatoProductId, new BigDecimal("1"), helper.uomKg);
			helper.assignProduct(huPIItem, helper.pSaladProductId, new BigDecimal("1"), helper.uomKg);
		}

		final IHUProducerAllocationDestination destination = HUProducerDestination.of(huPI)
				.setMaxHUsToCreate(1)
				.setHUStatus(X_M_HU.HUSTATUS_Active);

		forceLoadToDestination(destination, helper.pTomatoProductId, 10, helper.uomKg);
		forceLoadToDestination(destination, helper.pSaladProductId, 13, helper.uomKg);

		final IHUStorage huStorage = getSingleCreatedHUStorage(destination);

		final IHUProductStorage tomatoesStorage = huStorage.getProductStorage(helper.pTomatoProductId);
		assertThat(tomatoesStorage.getQty().toBigDecimal()).isEqualByComparingTo("10");
	}
}
