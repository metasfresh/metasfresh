package de.metas.handlingunits.allocation.strategy;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.expectations.HUExpectation;
import de.metas.handlingunits.expectations.HUItemExpectation;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.util.TraceUtils;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.uom.UOMPrecision;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class UniformAllocationStrategyTest
{
	private HUTestHelper helper;

	@Builder
	@SuppressWarnings("unused")
	private static class LUTUConfig
	{
		@NonNull
		ProductId productId;
		@NonNull
		I_C_UOM productUOM;

		I_M_HU_PI piLU;
		I_M_HU_PI_Item piLU_Item;

		I_M_HU_PI piTU;
		I_M_HU_PI_Item piTU_Item;
		I_M_HU_PI_Item_Product piTU_Item_Product;
	}

	@BeforeEach
	public void init()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();
	}

	@Builder(builderMethodName = "lutuConfig", builderClassName = "$LUTUConfigBuilder")
	private LUTUConfig createLUTUConfig(
			@Nullable final QuantityTU qtyTUsPerLU,
			@Nullable final String qtyCUsPerTU,
			@Nullable final UOMPrecision uomPrecision)
	{
		final I_C_UOM productUOM = BusinessTestHelper.createUOM("Kg", X_C_UOM.UOMTYPE_Weigth, uomPrecision.toInt());
		final ProductId productId = BusinessTestHelper.createProductId("product", productUOM);

		final I_M_HU_PI piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final I_M_HU_PI_Item piTU_Item;
		final I_M_HU_PI_Item_Product piTU_Item_Product;
		if (qtyCUsPerTU != null)
		{
			piTU_Item = helper.createHU_PI_Item_Material(piTU);
			piTU_Item_Product = helper.assignProduct(
					piTU_Item,
					productId,
					new BigDecimal(qtyCUsPerTU),
					productUOM);
		}
		else
		{
			piTU_Item = null;
			piTU_Item_Product = null;
		}

		final I_M_HU_PI piLU;
		final I_M_HU_PI_Item piLU_Item;
		if (qtyTUsPerLU != null)
		{
			piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			{
				final I_C_BPartner bpartner = null; // match any BP
				piLU_Item = helper.createHU_PI_Item_IncludedHU(piLU, piTU, qtyTUsPerLU.toBigDecimal(), bpartner);
			}
		}
		else
		{
			piLU = null;
			piLU_Item = null;
		}

		return LUTUConfig.builder()
				.productId(productId)
				.productUOM(productUOM)
				//
				.piLU(piLU)
				.piLU_Item(piLU_Item)
				//
				.piTU(piTU)
				.piTU_Item(piTU_Item)
				.piTU_Item_Product(piTU_Item_Product)
				//
				.build();
	}

	private I_M_HU createLU(
			@NonNull final String totalQtyCU,
			@NonNull final LUTUConfig lutuConfig)
	{
		return helper.newLUs()
				.loadingUnitPIItem(lutuConfig.piLU_Item)
				.tuPIItemProduct(lutuConfig.piTU_Item_Product)
				.totalQtyCU(new BigDecimal(totalQtyCU))
				.buildSingleLU();
	}

	@Nested
	@DisplayName("1 LU: [40 x TU x 10kg aggregated]")
	public class aggregatedHU
	{
		private LUTUConfig lutuConfig;
		private I_M_HU lu;

		@BeforeEach
		public void beforeEach()
		{
			lutuConfig = lutuConfig()
					.uomPrecision(UOMPrecision.ofInt(3))
					.qtyTUsPerLU(QuantityTU.ofInt(40))
					.qtyCUsPerTU("10")
					.build();

			lu = createLU("400", lutuConfig);
			// dumpHU("initial", lu);
		}

		@Test
		public void add10()
		{
			addQty(lu, "10", AllocationStrategyType.UNIFORM, lutuConfig);
			// dumpHU("after +10", lu);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qtyTUs(40)
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("410")
											.uom(lutuConfig.productUOM))))
					.assertExpected("after + 10", lu);
		}

		@Test
		public void subtract10()
		{

			subtractQty(lu, "10", AllocationStrategyType.UNIFORM, lutuConfig);
			// dumpHU("after -10", lu);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qtyTUs(40)
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("390")
											.uom(lutuConfig.productUOM))))
					.assertExpected("after -10", lu);
		}
	}

	@Nested
	@DisplayName("1 LU: [39 x TU x 10kg aggregated] + [1 x TU 9Kg not aggregated]")
	public class aggregatedHU_and_regularHU
	{
		private LUTUConfig lutuConfig;
		private I_M_HU lu;

		@BeforeEach
		public void beforeEach()
		{
			lutuConfig = lutuConfig()
					.uomPrecision(UOMPrecision.ofInt(3))
					.qtyTUsPerLU(QuantityTU.ofInt(40))
					.qtyCUsPerTU("10")
					.build();

			lu = createLU("400", lutuConfig);
			// dumpHU("initial", lu);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qtyTUs(40)
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("400")
											.uom(lutuConfig.productUOM))))
					.assertExpected("initial LU with 40 TUs", lu);

			//
			subtractQty(lu, "1", AllocationStrategyType.FIFO, lutuConfig);
			// dumpHU("after -1", lu);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HandlingUnit)
							.includedHU(HUExpectation.newExpectation()
									.huPI(lutuConfig.piTU)
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("9")
											.uom(lutuConfig.productUOM))))
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qty("39")
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("390")
											.uom(lutuConfig.productUOM))))
					.assertExpected("after -1", lu);
		}

		@Test
		public void add10()
		{
			addQty(lu, "10", AllocationStrategyType.UNIFORM, lutuConfig);
			// dumpHU("after +10", lu);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HandlingUnit)
							.includedHU(HUExpectation.newExpectation()
									.huPI(lutuConfig.piTU)
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("9.226")
											.uom(lutuConfig.productUOM))))
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qty("39")
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("399.774")
											.uom(lutuConfig.productUOM))))
					.assertExpected("after +10", lu);
		}

		@Test
		public void subtract10()
		{
			subtractQty(lu, "10", AllocationStrategyType.UNIFORM, lutuConfig);
			// dumpHU("after -10", lu);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HandlingUnit)
							.includedHU(HUExpectation.newExpectation()
									.huPI(lutuConfig.piTU)
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("8.774")
											.uom(lutuConfig.productUOM))))
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qty("39")
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(lutuConfig.productId)
											.qty("380.226")
											.uom(lutuConfig.productUOM))))
					.assertExpected("after -10", lu);
		}
	}

	@SuppressWarnings("unused")
	private void dumpHU(final String title, final I_M_HU hu)
	{
		System.out.println("-------[ " + title + " ] -----------------------------------------------------------------------");
		TraceUtils.newInstance()
				.dumpAttributes(false)
				.dumpItemStorage(false)
				.dump(hu);
	}

	private void addQty(
			@NonNull final I_M_HU hu,
			@NonNull final String qty,
			@NonNull final AllocationStrategyType allocationStrategyType,
			@NonNull final LUTUConfig lutuConfig)
	{
		final GenericAllocationSourceDestination source = helper.createDummySourceDestination(
				lutuConfig.productId,
				Quantity.QTY_INFINITE,
				lutuConfig.productUOM,
				true // fullyLoaded
		);

		final HUListAllocationSourceDestination destination = HUListAllocationSourceDestination.of(
				hu,
				allocationStrategyType);

		HULoader.of(source, destination)
				.load(AllocationUtils.createAllocationRequestBuilder()
						.setHUContext(helper.createMutableHUContextForProcessingOutOfTrx())
						.setDateAsToday()
						.setProduct(lutuConfig.productId)
						.setQuantity(Quantity.of(qty, lutuConfig.productUOM))
						.setFromReferencedModel(source.getReferenceModel())
						.setForceQtyAllocation(true)
						.create());
	}

	private void subtractQty(
			@NonNull final I_M_HU hu,
			@NonNull final String qty,
			@NonNull final AllocationStrategyType allocationStrategyType,
			@NonNull final LUTUConfig lutuConfig)
	{
		final GenericAllocationSourceDestination destination = helper.createDummySourceDestination(
				lutuConfig.productId,
				Quantity.QTY_INFINITE,
				lutuConfig.productUOM,
				true // fullyLoaded
		);

		final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(
				hu,
				allocationStrategyType);

		HULoader.of(source, destination)
				.load(AllocationUtils.createAllocationRequestBuilder()
						.setHUContext(helper.createMutableHUContextForProcessingOutOfTrx())
						.setDateAsToday()
						.setProduct(lutuConfig.productId)
						.setQuantity(Quantity.of(qty, lutuConfig.productUOM))
						.setFromReferencedModel(destination.getReferenceModel())
						.setForceQtyAllocation(true)
						.create());
	}
}
