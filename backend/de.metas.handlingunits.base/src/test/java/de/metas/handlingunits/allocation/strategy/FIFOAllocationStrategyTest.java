package de.metas.handlingunits.allocation.strategy;

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

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationDirection;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.expectations.AllocationResultExpectation;
import de.metas.handlingunits.expectations.HUExpectation;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.util.TraceUtils;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import lombok.Builder;

/**
 * Tests for {@link FIFOAllocationStrategy}.
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
public class FIFOAllocationStrategyTest
{
	private HUTestHelper huTestHelper;

	//
	private ProductId productId;
	private I_C_UOM productUOM;

	@Builder
	private static class LUTUConfig
	{
		// I_M_HU_PI piLU;
		I_M_HU_PI piTU;
		I_M_HU_PI_Item piTU_Item;
	}

	//
	private AllocationStrategyFactory strategyFactory;

	@BeforeEach
	public void init()
	{
		huTestHelper = new HUTestHelper();

		productId = huTestHelper.pTomatoProductId;
		productUOM = huTestHelper.uomKg;

		strategyFactory = new AllocationStrategyFactory(new AllocationStrategySupportingServicesFacade());
	}

	private LUTUConfig setupLUTU(
			@Nullable final QuantityTU qtyTUsPerLU,
			@Nullable final BigDecimal qtyCUsPerTU)
	{
		final I_M_HU_PI piTU = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final I_M_HU_PI_Item piTU_Item;
		if (qtyCUsPerTU != null)
		{
			piTU_Item = huTestHelper.createHU_PI_Item_Material(piTU);
			huTestHelper.assignProduct(piTU_Item, productId, qtyCUsPerTU, productUOM);
		}
		else
		{
			piTU_Item = null;
		}

		final I_M_HU_PI piLU;
		if (qtyTUsPerLU != null)
		{
			piLU = huTestHelper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			{
				final I_C_BPartner bpartner = null; // match any BP
				huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, qtyTUsPerLU.toBigDecimal(), bpartner);
			}
		}
		else
		{
			piLU = null;
		}

		return LUTUConfig.builder()
				// .piLU(piLU)
				.piTU(piTU)
				.piTU_Item(piTU_Item)
				.build();
	}

	private IAllocationRequestBuilder prepareAllocationRequest(final String qtyStr, final I_C_UOM uom)
	{
		final IHUContext huContext = huTestHelper.createMutableHUContext();
		return AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(Quantity.of(qtyStr, uom))
				.setDate(ZonedDateTime.now()) // not important
				.setFromReferencedModel(huTestHelper.createDummyReferenceModel()) // not important
				.setForceQtyAllocation(false) // not important
		;
	}

	private IAllocationRequest allocationRequest(final String qtyStr, final I_C_UOM uom)
	{
		return prepareAllocationRequest(qtyStr, uom)
				.create();
	}

	@Test
	public void test_allocate_NoLU_EmptyTU()
	{
		final FIFOAllocationStrategy strategy = (FIFOAllocationStrategy)strategyFactory.createAllocationStrategy(AllocationDirection.INBOUND_ALLOCATION);

		//
		// Create an empty HU
		final LUTUConfig lutuConfig = setupLUTU(
				(QuantityTU)null,
				new BigDecimal("10") // qtyCUsPerTU
		);

		//
		// Create an empty HU
		//@formatter:off
		final HUExpectation<Object> newHUexpectation = HUExpectation.newExpectation()
				.huStatus(X_M_HU.HUSTATUS_Planning)
				.huPI(lutuConfig.piTU)
				.item()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(lutuConfig.piTU_Item)
					.noIncludedHUs()
					.noItemStorages()
					.endExpectation();
		//@formatter:on
		final I_M_HU hu = newHUexpectation.createHU();

		//
		// Create request
		final IAllocationRequest request = allocationRequest("10", productUOM);

		//
		// Execute allocation strategy
		final IAllocationResult result1 = strategy.execute(hu, request);

		//
		// Test the result
		final IMutable<I_M_HU> vhu1 = new Mutable<>();
		//@formatter:off
		final HUExpectation<Object> result_huExpectation = HUExpectation.newExpectation()
				.huStatus(X_M_HU.HUSTATUS_Planning)
				.huPI(lutuConfig.piTU)
				.item()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(lutuConfig.piTU_Item)
					.includedVirtualHU()
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
					.product(productId)
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
		final Mutable<I_M_HU> vhu2 = new Mutable<>();
		//@formatter:off
		result_huExpectation
				.existingItem(lutuConfig.piTU_Item)
					.includedVirtualHU() // VHU created on second allocation
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
		final FIFOAllocationStrategy strategy = (FIFOAllocationStrategy)strategyFactory.createAllocationStrategy(AllocationDirection.OUTBOUND_DEALLOCATION);

		//
		// Create an empty HU
		final LUTUConfig lutuConfig = setupLUTU(
				(QuantityTU)null,
				new BigDecimal("10") // qtyCUsPerTU
		);

		//
		// Create a TU with 3xVHUs, each with 10items
		final IMutable<I_M_HU> vhu1 = new Mutable<>();
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		final IMutable<I_M_HU> vhu3 = new Mutable<>();
		//@formatter:off
		final HUExpectation<Object> huExpectation = HUExpectation.newExpectation()
				.huStatus(X_M_HU.HUSTATUS_Planning)
				.huPI(lutuConfig.piTU)
				.item()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(lutuConfig.piTU_Item)
					.includedVirtualHU()
						.capture(vhu1)
						.virtualPIItem()
							.storage()
								.product(productId).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.includedVirtualHU()
						.capture(vhu2)
						.virtualPIItem()
							.storage()
								.product(productId).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.includedVirtualHU()
						.capture(vhu3)
						.virtualPIItem()
							.storage()
								.product(productId).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation();
		//@formatter:on
		final I_M_HU hu = huExpectation.createHU();
		TraceUtils.dump(hu);

		//
		// Execute deallocation strategy for 23items (less than we have).
		final IAllocationRequest request1 = allocationRequest("23", productUOM);

		final IAllocationResult result1 = strategy.execute(hu, request1);

		//
		// Test the result
		//@formatter:off
		final AllocationResultExpectation result_Expectation = new AllocationResultExpectation()
				.qtyAllocated("23") // everything allocated
				.qtyToAllocate("0") // nothing remaining to allocate
				.completed(true)
				.newHUTransactionExpectation()
					.product(productId).qty("-10").uom(productUOM).hu(hu).virtualHU(vhu1)
					.endExpectation()
				.newHUTransactionExpectation()
					.product(productId).qty("-10").uom(productUOM).hu(hu).virtualHU(vhu2)
					.endExpectation()
				.newHUTransactionExpectation()
					.product(productId).qty("-3").uom(productUOM).hu(hu).virtualHU(vhu3)
					.endExpectation()
				//
				.assertExpected("result1", result1);
		//@formatter:on

		//
		// Try to deallocate again, but now we are trying with more then it's available
		final IAllocationRequest request2 = prepareAllocationRequest("40", productUOM)
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
}
