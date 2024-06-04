/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.inventory.internaluse;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUTestHelper.TestHelperLoadRequest;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesRepository;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesService;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.validator.M_HU;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.inventory.InventoryId;
import de.metas.inventory.impl.InventoryBL;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test doesn'T really work as it tests nothing.
 * It was added to identify bugs in a different person's issue, but the problems were solved by manual testing before I could get to finish this.
 * Feel free to fix and extend it.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
@ExtendWith(AdempiereTestWatcher.class)
public class HUInternalUseInventoryProducerTests
{
	private LUTUProducerDestinationTestSupport data;

	private static final IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> noopPackingMaterialsCollector = null;

	private IHandlingUnitsBL handlingUnitsBL;
	private IHUStatusBL huStatusBL;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private InventoryService inventoryService;

	private HUUniqueAttributesService huUniqueAttributesService;

	private M_HU huCallout;
	private final BPartnerId bpartnerId = BPartnerId.ofRepoId(12345);
	private I_M_Locator locator;

	@BeforeEach
	public void init()
	{

		huUniqueAttributesService = new HUUniqueAttributesService(new HUUniqueAttributesRepository());

		huCallout = new M_HU(huUniqueAttributesService);
		data = new LUTUProducerDestinationTestSupport();
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(999));

		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		huStatusBL = Services.get(IHUStatusBL.class);
		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsDAO());
		inventoryService = InventoryService.newInstanceForUnitTesting();

		final I_C_DocType dt = newInstance(I_C_DocType.class);
		dt.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory);
		dt.setDocSubType(X_C_DocType.DOCSUBTYPE_InternalUseInventory);
		saveRecord(dt);

		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		saveRecord(wh);

		locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse(wh);
		saveRecord(locator);

		Services.get(ISysConfigBL.class).setValue(InventoryBL.SYSCONFIG_QuickInput_Charge_ID, 1234, ClientId.SYSTEM, OrgId.ANY);

		de.metas.common.util.time.SystemTime.setFixedTimeSource("2019-06-10T10:00:00+01:00");
	}

	@Test
	public void test_CreateInventories()
	{
		final I_M_HU lu = mkAggregateCUs("50", 10);

		final List<I_M_Inventory> inventories = new HUInternalUseInventoryProducer(
				HUInternalUseInventoryCreateRequest.builder()
						.hus(ImmutableList.of(lu))
						.movementDate(LocalDate.parse("2021-04-08").atStartOfDay(ZoneId.of("Europe/Berlin")))
						.sendNotifications(false)
						.build())
				.execute()
				.getInventories();
		assertThat(inventories).hasSize(1);

		final InventoryId inventoryId = InventoryId.ofRepoId(inventories.get(0).getM_Inventory_ID());
		final Inventory inventory = inventoryService.getById(inventoryId);
		assertThat(inventory.getLines()).hasSize(1);
	}

	@Test
	public void moveToGarbage_NonReceipt_HUs()
	{
		final I_M_HU lu = mkAggregateCUs("50", 10);

		final I_M_HU cuWithTU = mkRealCUWithTUToSplit("10");
		final I_M_HU topLevelParentTU = handlingUnitsBL.getTopLevelParent(cuWithTU);

		final I_M_HU cu = mkRealStandAloneCUToSplit("15");

		final Collection<I_M_HU> husToDestroy = new ArrayList<>();

		husToDestroy.add(cu);
		husToDestroy.add(topLevelParentTU);
		husToDestroy.add(lu);

		final ActivityId activityId = createActivity("Activity1");
		final ZonedDateTime movementDate = de.metas.common.util.time.SystemTime.asZonedDateTime();
		final String description = "Test Description";

		final List<I_M_Inventory> inventories = inventoryService.moveToGarbage(
						HUInternalUseInventoryCreateRequest.builder()
								.hus(husToDestroy)
								.movementDate(movementDate)
								.activityId(activityId)
								.description(description)
								.completeInventory(true)
								.moveEmptiesToEmptiesWarehouse(false)
								.sendNotifications(false)
								.build())
				.getInventories();
		assertThat(inventories.size()).isEqualTo(1);

		final InventoryId inventoryId = InventoryId.ofRepoId(inventories.get(0).getM_Inventory_ID());
		final Inventory inventory = inventoryService.getById(inventoryId);
		assertThat(inventory.getActivityId()).isEqualTo(activityId);
		assertThat(inventory.getDescription()).isEqualTo(description);
		assertThat(inventory.getMovementDate()).isEqualTo(movementDate);
		assertThat(inventory.getDocStatus()).isEqualTo(DocStatus.Completed);
		assertThat(inventory.getLines()).hasSize(3);

		{
			final InventoryLine luInventoryLine = inventory.getLines()
					.stream()
					.filter(inventoryLine -> isMatchingSingleHU(inventoryLine, lu))
					.findFirst()
					.get();

			assertThat(luInventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
			assertThat(luInventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(luInventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("50");
		}

		{
			final InventoryLine cuWithTUInventoryLine = inventory.getLines()
					.stream()
					.filter(inventoryLine -> isMatchingSingleHU(inventoryLine, topLevelParentTU))
					.findFirst()
					.get();

			assertThat(cuWithTUInventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
			assertThat(cuWithTUInventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(cuWithTUInventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("10");
		}

		{
			final InventoryLine cuInventoryLine = inventory.getLines()
					.stream()
					.filter(inventoryLine -> isMatchingSingleHU(inventoryLine, cu))
					.findFirst()
					.get();

			assertThat(cuInventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
			assertThat(cuInventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(cuInventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("15");
		}
	}

	private static boolean isMatchingSingleHU(final InventoryLine inventoryLine, final I_M_HU hu)
	{
		return inventoryLine.getSingleLineHU().getHuId().getRepoId() == hu.getM_HU_ID();
	}

	@Test
	public void moveToGarbage_Receipt_HUs()
	{

		final I_M_InOutLine inoutLine = createInOutLine(data.helper.pTomato, data.helper.uomKg);
		final I_M_HU receiptLu = mkAggregateCUs("50", 10);

		createHUAssignment(inoutLine, receiptLu);

		final Collection<I_M_HU> husToDestroy = new ArrayList<>();
		husToDestroy.add(receiptLu);

		final ActivityId activityId = createActivity("Activity1");
		final ZonedDateTime movementDate = de.metas.common.util.time.SystemTime.asZonedDateTimeAtStartOfDay();
		final String description = "Test Description";

		final List<I_M_Inventory> inventories = inventoryService.moveToGarbage(
						HUInternalUseInventoryCreateRequest.builder()
								.hus(husToDestroy)
								.movementDate(movementDate)
								.activityId(activityId)
								.description(description)
								.completeInventory(true)
								.moveEmptiesToEmptiesWarehouse(false)
								.sendNotifications(false)
								.build())
				.getInventories();
		assertThat(inventories.size()).isEqualTo(1);

		final InventoryId inventoryId = InventoryId.ofRepoId(inventories.get(0).getM_Inventory_ID());
		final Inventory inventory = inventoryService.getById(inventoryId);
		assertThat(inventory.getActivityId()).isEqualTo(activityId);
		assertThat(inventory.getDescription()).isEqualTo(description);
		assertThat(inventory.getMovementDate()).isEqualTo(movementDate);
		assertThat(inventory.getDocStatus()).isEqualTo(DocStatus.Completed);
		assertThat(inventory.getLines()).hasSize(1);

		final InventoryLine inventoryLine = inventory.getLines().get(0);
		assertThat(inventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
		assertThat(inventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
		assertThat(inventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("50");
	}

	@Test
	public void moveToGarbage_Mixt_HUs()
	{
		final I_M_InOutLine inoutLine = createInOutLine(data.helper.pTomato, data.helper.uomKg);
		final I_M_HU receiptLu = mkAggregateCUs("50", 10);

		createHUAssignment(inoutLine, receiptLu);

		final I_M_HU lu = mkAggregateCUs("50", 10);

		final I_M_HU cuWithTU = mkRealCUWithTUToSplit("10");
		final I_M_HU topLevelParentTU = handlingUnitsBL.getTopLevelParent(cuWithTU);

		final I_M_HU cu = mkRealStandAloneCUToSplit("15");

		final Collection<I_M_HU> husToDestroy = new ArrayList<>();
		husToDestroy.add(cu);
		husToDestroy.add(topLevelParentTU);
		husToDestroy.add(lu);
		husToDestroy.add(receiptLu);

		final ActivityId activityId = createActivity("Activity1");
		final ZonedDateTime movementDate = SystemTime.asZonedDateTimeAtStartOfDay();
		final String description = "Test Description";

		final List<I_M_Inventory> inventories = inventoryService.moveToGarbage(
						HUInternalUseInventoryCreateRequest.builder()
								.hus(husToDestroy)
								.movementDate(movementDate)
								.activityId(activityId)
								.description(description)
								.completeInventory(true)
								.moveEmptiesToEmptiesWarehouse(false)
								.sendNotifications(false)
								.build())
				.getInventories();
		assertThat(inventories.size()).isEqualTo(1);

		final InventoryId inventoryId = InventoryId.ofRepoId(inventories.get(0).getM_Inventory_ID());
		final Inventory inventory = inventoryService.getById(inventoryId);
		assertThat(inventory.getActivityId()).isEqualTo(activityId);
		assertThat(inventory.getDescription()).isEqualTo(description);
		assertThat(inventory.getMovementDate()).isEqualTo(movementDate);
		assertThat(inventory.getDocStatus()).isEqualTo(DocStatus.Completed);
		assertThat(inventory.getLines()).hasSize(4);

		{
			final InventoryLine luInventoryLine = inventory.getLines()
					.stream()
					.filter(inventoryLine -> isMatchingSingleHU(inventoryLine, lu))
					.findFirst()
					.get();

			assertThat(luInventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
			assertThat(luInventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(luInventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("50");
		}

		{
			final InventoryLine cuWithTUInventoryLine = inventory.getLines()
					.stream()
					.filter(inventoryLine -> isMatchingSingleHU(inventoryLine, topLevelParentTU))
					.findFirst()
					.get();

			assertThat(cuWithTUInventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
			assertThat(cuWithTUInventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(cuWithTUInventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("10");
		}

		{
			final InventoryLine cuInventoryLine = inventory.getLines()
					.stream()
					.filter(inventoryLine -> isMatchingSingleHU(inventoryLine, cu))
					.findFirst()
					.get();

			assertThat(cuInventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
			assertThat(cuInventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(cuInventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("15");
		}

		{
			final InventoryLine receiptInventoryLine = inventory.getLines()
					.stream()
					.filter(inventoryLine -> isMatchingSingleHU(inventoryLine, receiptLu))
					.findFirst()
					.get();

			assertThat(receiptInventoryLine.getProductId().getRepoId()).isEqualTo(data.helper.pTomato.getM_Product_ID());
			assertThat(receiptInventoryLine.getQtyInternalUse().getUomId().getRepoId()).isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(receiptInventoryLine.getQtyInternalUse().toBigDecimal()).isEqualByComparingTo("50");
		}
	}

	private void createHUAssignment(final I_M_InOutLine inoutLine, final I_M_HU hu)
	{
		final I_M_HU_Assignment assignment = newInstance(I_M_HU_Assignment.class);
		assignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		assignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		assignment.setM_HU(hu);

		saveRecord(assignment);

	}

	private I_M_InOutLine createInOutLine(final I_M_Product product, final I_C_UOM uom)
	{
		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setC_BPartner_ID(bpartnerId.getRepoId());
		inout.setMovementDate(de.metas.common.util.time.SystemTime.asDayTimestamp());
		inout.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		saveRecord(inout);

		final I_M_InOutLine inoutLine = newInstance(I_M_InOutLine.class);
		inoutLine.setM_InOut(inout);
		inoutLine.setM_Product_ID(product.getM_Product_ID());
		inoutLine.setQtyEntered(BigDecimal.TEN);
		inoutLine.setC_UOM_ID(uom.getC_UOM_ID());
		inoutLine.setM_Locator_ID(locator.getM_Locator_ID());

		saveRecord(inoutLine);
		return inoutLine;
	}

	@SuppressWarnings("SameParameterValue")
	private ActivityId createActivity(final String name)
	{
		final I_C_Activity activity = newInstance(I_C_Activity.class);
		activity.setName(name);
		saveRecord(activity);

		return ActivityId.ofRepoId(activity.getC_Activity_ID());
	}

	/**
	 * Creates an LU with one aggregate HU. Both the LU's and aggregate HU's status is "active".
	 */
	public I_M_HU mkAggregateCUs(
			@NonNull final String totalQtyCUStr,
			final int qtyCUsPerTU)
	{
		final ProductId cuProductId = data.helper.pTomatoProductId;
		final I_C_UOM cuUOM = data.helper.uomKg;
		final BigDecimal totalQtyCU = new BigDecimal(totalQtyCUStr);

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setMaxTUsPerLU(Integer.MAX_VALUE); // allow as many TUs on that one pallet as we want

		// Custom TU capacity (if specified)
		if (qtyCUsPerTU > 0)
		{
			lutuProducer.addCUPerTU(cuProductId, BigDecimal.valueOf(qtyCUsPerTU), cuUOM);
		}

		final TestHelperLoadRequest loadRequest = HUTestHelper.TestHelperLoadRequest.builder()
				.producer(lutuProducer)
				.cuProductId(cuProductId)
				.loadCuQty(totalQtyCU)
				.loadCuUOM(cuUOM)
				.huPackingMaterialsCollector(noopPackingMaterialsCollector)
				.build();

		data.helper.load(loadRequest);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		assertThat(createdLUs).hasSize(1);
		// data.helper.commitAndDumpHU(createdLUs.get(0));

		final I_M_HU createdLU = createdLUs.get(0);
		final IMutableHUContext huContext = data.helper.createMutableHUContextOutOfTransaction();
		huStatusBL.setHUStatus(huContext, createdLU, X_M_HU.HUSTATUS_Active);
		assertThat(createdLU.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);
		createdLU.setM_Locator_ID(locator.getM_Locator_ID());

		huCallout.updateChildren(createdLU);
		handlingUnitsDAO.saveHU(createdLU);

		final List<I_M_HU> createdAggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(createdLUs.get(0));
		assertThat(createdAggregateHUs).hasSize(1);

		final I_M_HU cuToSplit = createdAggregateHUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(cuToSplit)).isTrue();
		assertThat(cuToSplit.getM_HU_Item_Parent().getM_HU_PI_Item_ID()).isEqualTo(data.piLU_Item_IFCO.getM_HU_PI_Item_ID());
		assertThat(cuToSplit.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);

		return createdLUs.get(0);
	}

	/**
	 * Makes a stand alone CU with the given quantity and status "active".
	 */
	public I_M_HU mkRealStandAloneCUToSplit(final String strCuQty)
	{
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();

		final TestHelperLoadRequest loadRequest = HUTestHelper.TestHelperLoadRequest.builder()
				.producer(producer)
				.cuProductId(data.helper.pTomatoProductId)
				.loadCuQty(new BigDecimal(strCuQty))
				.loadCuUOM(data.helper.uomKg)
				.huPackingMaterialsCollector(noopPackingMaterialsCollector)
				.build();

		data.helper.load(loadRequest);

		final List<I_M_HU> createdCUs = producer.getCreatedHUs();
		assertThat(createdCUs).hasSize(1);

		final I_M_HU cuToSplit = createdCUs.get(0);

		cuToSplit.setM_Locator_ID(locator.getM_Locator_ID());
		huStatusBL.setHUStatus(data.helper.getHUContext(), cuToSplit, X_M_HU.HUSTATUS_Active);
		handlingUnitsDAO.saveHU(cuToSplit);

		return cuToSplit;
	}

	public I_M_HU mkRealCUWithTUToSplit(final String strCuQty)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);

		final BigDecimal cuQty = new BigDecimal(strCuQty);
		data.helper.load(lutuProducer, data.helper.pTomatoProductId, cuQty, data.helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();
		assertThat(createdTUs).hasSize(1);

		final I_M_HU createdTU = createdTUs.get(0);
		huStatusBL.setHUStatus(data.helper.getHUContext(), createdTU, X_M_HU.HUSTATUS_Active);
		createdTU.setM_Locator_ID(locator.getM_Locator_ID());

		huCallout.updateChildren(createdTU);
		handlingUnitsDAO.saveHU(createdTU);

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTU);
		assertThat(createdCUs).hasSize(1);

		final I_M_HU cuToSplit = createdCUs.get(0);
		cuToSplit.setM_Locator_ID(locator.getM_Locator_ID());

		return cuToSplit;
	}
}
