package de.metas.handlingunits.weighting;

import de.metas.business.BusinessTestHelper;
import de.metas.document.DocBaseType;
import de.metas.document.IDocTypeDAO;
import de.metas.document.IDocTypeDAO.DocTypeCreateRequest;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.strategy.AllocationStrategyType;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.PlainWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.expectations.HUExpectation;
import de.metas.handlingunits.expectations.HUItemExpectation;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.util.TraceUtils;
import de.metas.inventory.InventoryDocSubType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.uom.UOMPrecision;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.assertj.core.api.ObjectAssert;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-webui-api
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

@ExtendWith(AdempiereTestWatcher.class)
public class WeightHUCommandTest
{
	private HUQtyService huQtyService;
	private HUTestHelper helper;

	@BeforeEach
	public void beforeEach()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();

		final InventoryService inventoryService = InventoryService.newInstanceForUnitTesting();
		this.huQtyService = new HUQtyService(inventoryService);

		POJOLookupMap.get().addModelValidator(new de.metas.handlingunits.inventory.interceptor.M_Inventory(inventoryService));
	}

	@Builder
	private static class Masterdata
	{
		@NonNull ProductId productId;
		@NonNull I_C_UOM uomKg;

		@NonNull LocatorId locatorId;

		I_M_HU_PI_Item piLU_Item;
		I_M_HU_PI piTU;
		I_M_HU_PI_Item_Product piTU_Item_Product;
	}

	@Builder(builderMethodName = "masterData", builderClassName = "$MasterDataBuilder")
	private Masterdata createMasterData(
			@Nullable final QuantityTU qtyTUsPerLU,
			@Nullable final String qtyCUsPerTU,
			@Nullable final UOMPrecision uomPrecision)
	{
		I_C_UOM uomKg = helper.uomKg;
		assertThat(uomKg.getStdPrecision()).isEqualTo(3);

		ProductId productId = BusinessTestHelper.createProductId("product", uomKg);

		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		// orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final I_M_Warehouse warehouse = BusinessTestHelper.createWarehouse("warehouse");
		final I_M_Locator locator = BusinessTestHelper.createLocator("locator", warehouse);
		LocatorId locatorId = LocatorId.ofRecord(locator);

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		docTypeDAO.createDocType(DocTypeCreateRequest.builder()
				.ctx(Env.getCtx())
				.docBaseType(DocBaseType.MaterialPhysicalInventory.getCode())
				.docSubType(InventoryDocSubType.SingleHUInventory.getCode())
				.name("inventory")
				.build());

		//
		final I_M_HU_PI piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final I_M_HU_PI_Item_Product piTU_Item_Product;
		if (qtyCUsPerTU != null)
		{
			final I_M_HU_PI_Item piTU_Item = helper.createHU_PI_Item_Material(piTU);
			piTU_Item_Product = helper.assignProduct(
					piTU_Item,
					productId,
					new BigDecimal(qtyCUsPerTU),
					uomKg);
		}
		else
		{
			piTU_Item_Product = null;
		}

		final I_M_HU_PI_Item piLU_Item;
		if (qtyTUsPerLU != null)
		{
			final I_M_HU_PI piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			{
				final I_C_BPartner bpartner = null; // match any BP
				piLU_Item = helper.createHU_PI_Item_IncludedHU(piLU, piTU, qtyTUsPerLU.toBigDecimal(), bpartner);
			}
		}
		else
		{
			piLU_Item = null;
		}

		return Masterdata.builder()
				.productId(productId)
				.uomKg(uomKg)
				.locatorId(locatorId)
				.piLU_Item(piLU_Item)
				.piTU(piTU)
				.piTU_Item_Product(piTU_Item_Product)
				.build();
	}

	@SuppressWarnings("SameParameterValue")
	private HuId createLU(
			@NonNull final String totalQtyCU,
			@NonNull final Masterdata masterdata)
	{
		BigDecimal totalQtyCU_BD = new BigDecimal(totalQtyCU);
		final HuId luId = helper.newLUs()
				.huStatus(X_M_HU.HUSTATUS_Active)
				.locatorId(masterdata.locatorId)
				.loadingUnitPIItem(masterdata.piLU_Item)
				.tuPIItemProduct(masterdata.piTU_Item_Product)
				.totalQtyCU(totalQtyCU_BD)
				.buildSingleLUId();

		setWeightGross(luId, totalQtyCU);

		return luId;
	}

	private void setWeightGross(final HuId huId, final String weightGross)
	{
		final IWeightable huAttributes = getHUAttributes(huId);
		huAttributes.setWeightGross(new BigDecimal(weightGross));
	}

	private IWeightable getHUAttributes(@NonNull final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IAttributeStorage huAttributes = huContextFactory
				.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		return Weightables.wrap(huAttributes);
	}

	private ObjectAssert<PlainWeightable> assertWeightable(@NonNull final HuId huId)
	{
		final IWeightable huAttributes = getHUAttributes(huId);
		return assertThat(PlainWeightable.copyOf(huAttributes));
	}

	@SuppressWarnings("SameParameterValue")
	private void subtractQty(
			@NonNull final HuId huId,
			@NonNull final String qty,
			@NonNull final AllocationStrategyType allocationStrategyType,
			@NonNull final Masterdata masterdata)
	{
		final GenericAllocationSourceDestination destination = helper.createDummySourceDestination(
				masterdata.productId,
				Quantity.QTY_INFINITE,
				masterdata.uomKg,
				true // fullyLoaded
		);

		final I_M_HU hu = Services.get(IHandlingUnitsDAO.class).getById(huId);
		final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(
				hu,
				allocationStrategyType);

		HULoader.of(source, destination)
				.load(AllocationUtils.builder()
						.setHUContext(helper.createMutableHUContextForProcessingOutOfTrx())
						.setDateAsToday()
						.setProduct(masterdata.productId)
						.setQuantity(Quantity.of(qty, masterdata.uomKg))
						.setFromReferencedModel(destination.getReferenceModel())
						.setForceQtyAllocation(true)
						.create());
	}

	private void weight(final HuId huId, PlainWeightable targetWeight)
	{
		WeightHUCommand.builder()
				.huQtyService(huQtyService)
				.huId(huId)
				.targetWeight(targetWeight)
				.build()
				.execute();

	}

	@SuppressWarnings("unused")
	private void dumpHU(final String title, final HuId huId)
	{
		final I_M_HU hu = Services.get(IHandlingUnitsDAO.class).getById(huId);

		System.out.println("-------[ " + title + " ] -----------------------------------------------------------------------");
		TraceUtils.newInstance()
				.dumpAttributes(true)
				.dumpItemStorage(false)
				.dump(hu);
	}

	@Nested
	@DisplayName("LU of 400kg: [40 x TU x 10kg aggregated]")
	public class aggregatedHU
	{
		private Masterdata masterdata;
		private HuId luId;

		@BeforeEach
		public void beforeEach()
		{
			masterdata = masterData()
					.uomPrecision(UOMPrecision.ofInt(3))
					.qtyTUsPerLU(QuantityTU.ofInt(40))
					.qtyCUsPerTU("10")
					.build();

			luId = createLU("400", masterdata);
			// dumpHU("initial", luId);
			assertWeightable(luId)
					.usingRecursiveComparison()
					.isEqualTo(PlainWeightable.builder()
							.uom(masterdata.uomKg)
							.weightGross(new BigDecimal("400.000"))
							.weightNet(new BigDecimal("400.000"))
							.weightTare(new BigDecimal("0.000"))
							.weightTareAdjust(new BigDecimal("0.000"))
							.build());
		}

		@Test
		public void weightTo_410kg()
		{
			weight(luId, PlainWeightable.builder()
					.uom(masterdata.uomKg)
					.weightGross(new BigDecimal("410"))
					.weightNet(new BigDecimal("410"))
					.build());

			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qtyTUs(40)
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("410")
											.uom(masterdata.uomKg))))
					.assertExpected("after weight", luId);

			assertWeightable(luId)
					.usingRecursiveComparison()
					.isEqualTo(PlainWeightable.builder()
							.uom(masterdata.uomKg)
							.weightGross(new BigDecimal("410.000"))
							.weightNet(new BigDecimal("410.000"))
							.weightTare(new BigDecimal("0.000"))
							.weightTareAdjust(new BigDecimal("0.000"))
							.build());
		}

		@Test
		public void weightTo_390kg()
		{
			weight(luId, PlainWeightable.builder()
					.uom(masterdata.uomKg)
					.weightGross(new BigDecimal("390"))
					.weightNet(new BigDecimal("390"))
					.build());

			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qtyTUs(40)
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("390")
											.uom(masterdata.uomKg))))
					.assertExpected("after weight", luId);

			assertWeightable(luId)
					.usingRecursiveComparison()
					.isEqualTo(PlainWeightable.builder()
							.uom(masterdata.uomKg)
							.weightGross(new BigDecimal("390.000"))
							.weightNet(new BigDecimal("390.000"))
							.weightTare(new BigDecimal("0.000"))
							.weightTareAdjust(new BigDecimal("0.000"))
							.build());
		}
	}

	@Nested
	@DisplayName("LU of 399kg: [39 x TU x 10kg aggregated] + [1 x TU 9Kg not aggregated]")
	public class aggregatedHU_and_regularHU
	{
		private Masterdata masterdata;
		private HuId luId;

		@BeforeEach
		public void beforeEach()
		{
			masterdata = masterData()
					.uomPrecision(UOMPrecision.ofInt(3))
					.qtyTUsPerLU(QuantityTU.ofInt(40))
					.qtyCUsPerTU("10")
					.build();

			luId = createLU("400", masterdata);
			// dumpHU("initial", luId);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qtyTUs(40)
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("400")
											.uom(masterdata.uomKg))))
					.assertExpected("initial LU with 40 TUs", luId);

			//
			subtractQty(luId, "1", AllocationStrategyType.FIFO, masterdata);
			setWeightGross(luId, "399");

			// dumpHU("after -1", luId);
			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HandlingUnit)
							.includedHU(HUExpectation.newExpectation()
									.huPI(masterdata.piTU)
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("9")
											.uom(masterdata.uomKg))))
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qty("39")
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("390")
											.uom(masterdata.uomKg))))
					.assertExpected("after -1", luId);

			assertWeightable(luId)
					.usingRecursiveComparison()
					.isEqualTo(PlainWeightable.builder()
							.uom(masterdata.uomKg)
							.weightGross(new BigDecimal("399.000"))
							.weightNet(new BigDecimal("399.000"))
							.weightTare(new BigDecimal("0.000"))
							.weightTareAdjust(new BigDecimal("0.000"))
							.build());
		}

		@Test
		public void weightTo_390Kg()
		{
			weight(luId, PlainWeightable.builder()
					.uom(masterdata.uomKg)
					.weightGross(new BigDecimal("390"))
					.weightNet(new BigDecimal("390"))
					.build());
			// dumpHU("after weight", luId);

			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HandlingUnit)
							.includedHU(HUExpectation.newExpectation()
									.huPI(masterdata.piTU)
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("8.797")
											.uom(masterdata.uomKg))))
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qty("39")
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("381.203")
											.uom(masterdata.uomKg))))
					.assertExpected("after weight", luId);
		}

		@Test
		public void weightTo_399Kg()
		{
			// dumpHU("before weight", luId);
			weight(luId, PlainWeightable.builder()
					.uom(masterdata.uomKg)
					.weightGross(new BigDecimal("399"))
					.weightNet(new BigDecimal("399"))
					.build());
			// dumpHU("after weight", luId);

			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HandlingUnit)
							.includedHU(HUExpectation.newExpectation()
									.huPI(masterdata.piTU)
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("9")
											.uom(masterdata.uomKg))))
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qty("39")
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("390")
											.uom(masterdata.uomKg))))
					.assertExpected("after weight", luId);
		}

		@Test
		public void weightTo_410Kg()
		{
			// dumpHU("before weight", luId);
			weight(luId, PlainWeightable.builder()
					.uom(masterdata.uomKg)
					.weightGross(new BigDecimal("410"))
					.weightNet(new BigDecimal("410"))
					.build());
			// dumpHU("after weight", luId);

			HUExpectation.newExpectation()
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HandlingUnit)
							.includedHU(HUExpectation.newExpectation()
									.huPI(masterdata.piTU)
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("9.248")
											.uom(masterdata.uomKg))))
					.item(HUItemExpectation.newExpectation()
							.itemType(HUItemType.HUAggregate)
							.qty("39")
							.includedHU(HUExpectation.newVirtualHU()
									.storage(HUStorageExpectation.newExpectation()
											.product(masterdata.productId)
											.qty("400.752")
											.uom(masterdata.uomKg))))
					.assertExpected("after weight", luId);
		}
	}
}
