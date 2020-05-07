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
import java.util.Date;

import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.expectations.AllocationResultExpectation;
import de.metas.handlingunits.expectations.HUExpectation;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.util.TraceUtils;
import de.metas.quantity.Quantity;

/**
 * Tests for {@link FIFOAllocationStrategy} and {@link FIFODeallocationStrategy}.
 *
 *
 * NOTEs for all tests:
 * <ul>
 * <li>Allocation strategies are not changing the HU Storages, they are just creating {@link HUTransactionCandidate}. So we are not checking if HU storages changed after an allocation/deallocation round,
 * because the don't.
 * </ul>
 *
 * @author tsa
 *
 */
public class FIFOAllocationDeallocationStrategyTest
{
	private HUTestHelper huTestHelper;

	//
	// LU/TU Config
	private I_M_HU_PI piLU;
	@SuppressWarnings("unused")
	private I_M_HU_PI_Item piLU_Item;
	//
	private I_M_HU_PI piTU;
	@SuppressWarnings("unused")
	private I_M_HU_PI_Version piTU_Version;
	private I_M_HU_PI_Item piTU_Item;
	@SuppressWarnings("unused")
	private Object piTU_ItemProduct;
	//
	private I_M_Product product;
	private I_C_UOM productUOM;

	//
	private AllocationStrategyFactory strategyFactory;

	@Before
	public void init()
	{
		huTestHelper = new HUTestHelper();

		product = huTestHelper.pTomato;
		productUOM = huTestHelper.uomKg;

		strategyFactory = new AllocationStrategyFactory();
	}

	@Test
	public void test_allocate_NoLU_EmptyTU()
	{
		final FIFOAllocationStrategy strategy = new FIFOAllocationStrategy();
		strategy.setAllocationStrategyFactory(strategyFactory);

		//
		// Create an empty HU
		setupLUTU(
				null, // new BigDecimal("10"), // qtyTUsPerLU,
				new BigDecimal("10") // qtyCUsPerTU
		);

		//
		// Create an empty HU
		//@formatter:off
		final HUExpectation<Object> newHUexpectation = new HUExpectation<>()
				.huStatus(X_M_HU.HUSTATUS_Planning)
				.huPI(piTU)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					.noIncludedHUs()
					.noItemStorages()
					.endExpectation();
		//@formatter:on
		final I_M_HU hu = newHUexpectation.createHU();

		//
		// Create request
		final IAllocationRequest request = createAllocationRequest("10", productUOM);

		//
		// Execute allocation strategy
		final IAllocationResult result1 = strategy.execute(hu, request);

		//
		// Test the result
		final IMutable<I_M_HU> vhu1 = new Mutable<>();
		//@formatter:off
		final HUExpectation<Object> result_huExpectation = new HUExpectation<>()
				.huStatus(X_M_HU.HUSTATUS_Planning)
				.huPI(piTU)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					.newIncludedVirtualHU()
						.capture(vhu1)
						.endExpectation()
					.noItemStorages()
					.endExpectation()
				.assertExpected("hu after result1", hu);
		final AllocationResultExpectation result_Expectation = new AllocationResultExpectation()
				.qtyAllocated(new BigDecimal("10")) // everything allocated
				.qtyToAllocate(BigDecimal.ZERO) // nothing remaining to allocate
				.completed(true)
				.newHUTransactionExpectation()
					.product(product)
					.qty("10")
					.uom(productUOM)
					.hu(hu)
					.virtualHU(vhu1)
					.endExpectation()
					//
				.assertExpected("result1", result1);
		//@formatter:on

		//
		// Execute allocation strategy (2nd time)
		final IAllocationResult result2 = strategy.execute(hu, request);

		//
		// Test the result after second allocation
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		//@formatter:off
		result_huExpectation
				.huItemExpectation(piTU_Item)
					.newIncludedVirtualHU() // VHU created on second allocation
						.capture(vhu2)
						.endExpectation()
					.endExpectation()
				.assertExpected("hu after result2", hu);
		result_Expectation
			.huTransactionExpectation(0)
				.virtualHU(vhu2)
				.endExpectation()
			.assertExpected("result2", result2);
		//@formatter:on
	}

	@Test
	public void test_deallocate_NoLU_TU_3VHUs()
	{
		final FIFODeallocationStrategy strategy = new FIFODeallocationStrategy();
		strategy.setAllocationStrategyFactory(strategyFactory);

		//
		// Create an empty HU
		setupLUTU(
				null, // new BigDecimal("10"), // qtyTUsPerLU,
				new BigDecimal("10") // qtyCUsPerTU
		);

		//
		// Create a TU with 3xVHUs, each with 10items
		final IMutable<I_M_HU> vhu1 = new Mutable<>();
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		final IMutable<I_M_HU> vhu3 = new Mutable<>();
		//@formatter:off
		final HUExpectation<Object> huExpectation = new HUExpectation<>()
				.huStatus(X_M_HU.HUSTATUS_Planning)
				.huPI(piTU)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					.newIncludedVirtualHU()
						.capture(vhu1)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(product).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.newIncludedVirtualHU()
						.capture(vhu2)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(product).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.newIncludedVirtualHU()
						.capture(vhu3)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(product).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation();
		//@formatter:on
		final I_M_HU hu = huExpectation.createHU();
		TraceUtils.dump(hu);

		//
		// Execute deallocation strategy for 23items (less than we have).
		final IAllocationRequest request1 = createAllocationRequest("23", productUOM);

		final IAllocationResult result1 = strategy.execute(hu, request1);

		//
		// Test the result
		//@formatter:off
		final AllocationResultExpectation result_Expectation = new AllocationResultExpectation()
				.qtyAllocated("23") // everything allocated
				.qtyToAllocate("0") // nothing remaining to allocate
				.completed(true)
				.newHUTransactionExpectation()
					.product(product).qty("-10").uom(productUOM).hu(hu).virtualHU(vhu1)
					.endExpectation()
				.newHUTransactionExpectation()
					.product(product).qty("-10").uom(productUOM).hu(hu).virtualHU(vhu2)
					.endExpectation()
				.newHUTransactionExpectation()
					.product(product).qty("-3").uom(productUOM).hu(hu).virtualHU(vhu3)
					.endExpectation()
				//
				.assertExpected("result1", result1);
		//@formatter:on

		//
		// Try to deallocate again, but now we are trying with more then it's available
		final IAllocationRequest request2 = createAllocationRequestBuilder("40", productUOM)
				.setForceQtyAllocation(false) // make sure we are not forcing
				.create();
		final IAllocationResult result2 = strategy.execute(hu, request2);

		//
		// Test the result
		//@formatter:off
		result_Expectation
			.qtyAllocated("30")
			.qtyToAllocate("10")
			.completed(false)
			.huTransactionExpectation(0) // same
				.endExpectation()
			.huTransactionExpectation(1) // same
				.endExpectation()
			.huTransactionExpectation(2) // last one, which initially had qty=-3
				.qty("-10")
				.endExpectation()
			.assertExpected("result2", result2);
		//@formatter:on
	}

	protected void setupLUTU(
			final BigDecimal qtyTUsPerLU,
			final BigDecimal qtyCUsPerTU)
	{
		// if (piTUToUse == null)
		{
			piTU = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			piTU_Version = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(piTU);
		}

		if (qtyCUsPerTU != null)
		{
			piTU_Item = huTestHelper.createHU_PI_Item_Material(piTU);
			piTU_ItemProduct = huTestHelper.assignProduct(piTU_Item, product, qtyCUsPerTU, productUOM);
		}

		if (qtyTUsPerLU != null)
		{
			piLU = huTestHelper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			{
				final I_C_BPartner bpartner = null; // match any BP
				piLU_Item = huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, qtyTUsPerLU, bpartner);
			}
		}
		else
		{
			piLU = null;
		}
	}

	protected IAllocationRequestBuilder createAllocationRequestBuilder(final String qtyStr, final I_C_UOM uom)
	{
		final IHUContext huContext = huTestHelper.createMutableHUContext();
		return AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(new Quantity(new BigDecimal(qtyStr), uom))
				.setDate(new Date()) // not important
				.setFromReferencedModel(huTestHelper.createDummyReferenceModel()) // not important
				.setForceQtyAllocation(false) // not important
		;
	}

	protected IAllocationRequest createAllocationRequest(final String qtyStr, final I_C_UOM uom)
	{
		return createAllocationRequestBuilder(qtyStr, uom)
				.create();
	}

}
