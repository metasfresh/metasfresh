package de.metas.handlingunits.trace;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.ad.dao.IQueryBL;
import de.metas.material.event.commons.AttributesKey;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

public class HUTraceEventsServiceTests
{
	private HUTraceEventsService huTraceEventsService;

	private HUAccessService huAccessService;

	private InventoryRepository inventoryRepository;

	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		huAccessService = Mockito.spy(new HUAccessService());
		inventoryRepository = Mockito.spy(new InventoryRepository());
		huTraceEventsService = new HUTraceEventsService(new HUTraceRepository(), huAccessService, inventoryRepository);

		LogManager.setLoggerLevel(HUTraceRepository.class, Level.INFO);

		uom = saveFluent(newInstance(I_C_UOM.class));
	}

	/**
	 * Create two records (AD_Users, but doesn't matter) and different {@link I_M_HU_Assignment} that reference them.
	 */
	@Test
	public void createAndAddEventsWithTwoLUs()
	{
		final org.compiere.model.I_AD_User user1 = newInstance(I_AD_User.class);
		user1.setLogin("user");
		user1.setName("we-just-need-some-record-as-a-reference");
		save(user1);

		final org.compiere.model.I_AD_User user2 = newInstance(I_AD_User.class);
		user2.setLogin("user2");
		user1.setName("we-just-need-some-record-as-a-reference");
		save(user2);

		final I_M_HU luHu11 = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu11 = createVHU(X_M_HU.HUSTATUS_Active);

		final ProductId prod11 = newProduct("prod11");
		final Quantity qty11 = Quantity.of(11, uom);

		final I_M_HU luHu12 = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu12 = createVHU(X_M_HU.HUSTATUS_Active);

		final ProductId prod12 = newProduct("prod12");
		// final Quantity qty12 = Quantity.of(12, uom);

		final I_M_HU luHu21 = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu21 = createVHU(X_M_HU.HUSTATUS_Active);

		final ProductId prod21 = newProduct("prod21");
		// final Quantity qty21 = Quantity.of(21, uom);

		final I_M_HU luHu22 = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu22 = createVHU(X_M_HU.HUSTATUS_Active);
		vhu22.setHUStatus(X_M_HU.HUSTATUS_Active);

		final ProductId prod22 = newProduct("prod22");
		// final Quantity qty22 = Quantity.of(22, uom);

		{
			final TableRecordReference ref1 = TableRecordReference.of(user1);

			final I_M_HU_Assignment huAssignment11 = newInstance(I_M_HU_Assignment.class);
			huAssignment11.setM_HU_ID(luHu11.getM_HU_ID());
			huAssignment11.setVHU(vhu11);
			huAssignment11.setAD_Table_ID(ref1.getAD_Table_ID());
			huAssignment11.setRecord_ID(ref1.getRecord_ID());
			save(huAssignment11);

			final I_M_HU_Assignment huAssignment12 = newInstance(I_M_HU_Assignment.class);
			huAssignment12.setM_HU_ID(luHu12.getM_HU_ID());
			huAssignment12.setVHU_ID(vhu12.getM_HU_ID());
			huAssignment12.setAD_Table_ID(ref1.getAD_Table_ID());
			huAssignment12.setRecord_ID(ref1.getRecord_ID());
			save(huAssignment12);

			final TableRecordReference ref2 = TableRecordReference.of(user2);

			final I_M_HU_Assignment huAssignment21 = newInstance(I_M_HU_Assignment.class);
			huAssignment21.setM_HU_ID(luHu21.getM_HU_ID());
			huAssignment21.setVHU_ID(vhu21.getM_HU_ID());
			huAssignment21.setAD_Table_ID(ref2.getAD_Table_ID());
			huAssignment21.setRecord_ID(ref2.getRecord_ID());
			save(huAssignment21);

			final I_M_HU_Assignment huAssignment22 = newInstance(I_M_HU_Assignment.class);
			huAssignment22.setM_HU_ID(luHu22.getM_HU_ID());
			huAssignment22.setVHU_ID(vhu22.getM_HU_ID());
			huAssignment22.setAD_Table_ID(ref2.getAD_Table_ID());
			huAssignment22.setRecord_ID(ref2.getRecord_ID());
			save(huAssignment22);

			// create a 5th assignment that references user2 but has the same HU-ID *and* updated time! as huAssignment22
			de.metas.common.util.time.SystemTime.setTimeSource(() -> huAssignment22.getUpdated().getTime());
			final I_M_HU_Assignment huAssignment22double = newInstance(I_M_HU_Assignment.class);
			huAssignment22double.setM_HU_ID(luHu22.getM_HU_ID());
			huAssignment22double.setVHU_ID(vhu22.getM_HU_ID());
			huAssignment22double.setAD_Table_ID(ref1.getAD_Table_ID());
			huAssignment22double.setRecord_ID(ref1.getRecord_ID());
			save(huAssignment22double);
			SystemTime.resetTimeSource();

			// set up the mocked huAccessService
			{
				Mockito.doReturn(ImmutableList.of(huAssignment11, huAssignment12)).when(huAccessService).retrieveHuAssignments(user1);
				Mockito.doReturn(ImmutableList.of(huAssignment21, huAssignment22, huAssignment22double)).when(huAccessService).retrieveHuAssignments(user2);

				// the LU HUs are already top level HUs themselves, so the method shall return their IDs
				Mockito.doReturn(luHu11.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(luHu11);
				Mockito.doReturn(luHu12.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(luHu12);
				Mockito.doReturn(luHu21.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(luHu21);
				Mockito.doReturn(luHu22.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(luHu22);

				Mockito.doReturn(Optional.of(ImmutablePair.of(prod11, qty11))).when(huAccessService).retrieveProductAndQty(vhu11);
				Mockito.doReturn(Optional.of(ImmutablePair.of(prod12, qty11))).when(huAccessService).retrieveProductAndQty(vhu12);
				Mockito.doReturn(Optional.of(ImmutablePair.of(prod21, qty11))).when(huAccessService).retrieveProductAndQty(vhu21);
				Mockito.doReturn(Optional.of(ImmutablePair.of(prod22, qty11))).when(huAccessService).retrieveProductAndQty(vhu22);
			}
		}

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(OrgId.ofRepoId(10))
				.inOutId(12).type(HUTraceType.MATERIAL_SHIPMENT); // note: inOutId and type don't really matter for this test

		huTraceEventsService.createAndAddEvents(builder, ImmutableList.of(user1, user2));

		final List<I_M_HU_Trace> allHuTraceRecords = retrieveAllHUTraceRecords();

		assertThat(allHuTraceRecords)
				.as("there shall be no record for the 5th assignment")
				.hasSize(4);
		allHuTraceRecords.sort(Comparator.comparing(I_M_HU_Trace::getM_HU_ID));

		assertThat(allHuTraceRecords.get(0).getM_HU_ID()).isEqualTo(luHu11.getM_HU_ID());
		assertThat(allHuTraceRecords.get(1).getM_HU_ID()).isEqualTo(luHu12.getM_HU_ID());
		assertThat(allHuTraceRecords.get(2).getM_HU_ID()).isEqualTo(luHu21.getM_HU_ID());
		assertThat(allHuTraceRecords.get(3).getM_HU_ID()).isEqualTo(luHu22.getM_HU_ID());
	}

	private I_M_HU createVHU(final String huStatus)
	{
		final I_M_HU vhu = saveFluent(newInstance(I_M_HU.class));
		vhu.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		vhu.setHUStatus(huStatus);
		saveRecord(vhu);
		return vhu;
	}

	private I_M_HU createVHU()
	{
		return createVHU(X_M_HU.HUSTATUS_Active);
	}

	private List<I_M_HU_Trace> retrieveAllHUTraceRecords()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Trace.class)
				.orderBy(I_M_HU_Trace.COLUMN_M_HU_Trace_ID)
				.create()
				.list();
	}

	@Test
	public void testCreateAndAddForWithPlannedHUs()
	{
		final I_M_HU_Trx_Hdr trxHeader = newInstance(I_M_HU_Trx_Hdr.class);
		save(trxHeader);

		final I_M_HU_Trx_Line trxLine = newInstance(I_M_HU_Trx_Line.class);
		final I_M_HU vhu;
		{
			vhu = newInstance(I_M_HU.class);
			vhu.setHUStatus(X_M_HU.HUSTATUS_Planning);
			save(vhu);
			final I_M_HU_Item vhuItem = newInstance(I_M_HU_Item.class);
			vhuItem.setM_HU(vhu);
			save(vhuItem);
			trxLine.setVHU_Item(vhuItem);
			trxLine.setQty(BigDecimal.ONE);
		}
		final I_M_HU_Trx_Line sourceTrxLine = newInstance(I_M_HU_Trx_Line.class);
		final I_M_HU sourceVhu;
		{
			sourceVhu = newInstance(I_M_HU.class);
			sourceVhu.setHUStatus(X_M_HU.HUSTATUS_Planning);
			save(sourceVhu);
			final I_M_HU_Item sourceVhuItem = newInstance(I_M_HU_Item.class);
			sourceVhuItem.setM_HU(sourceVhu);
			save(sourceVhuItem);
			sourceTrxLine.setVHU_Item(sourceVhuItem);
			sourceTrxLine.setQty(BigDecimal.ONE.negate());
		}
		save(sourceTrxLine);
		trxLine.setM_HU_Trx_Hdr(trxHeader);
		trxLine.setParent_HU_Trx_Line(sourceTrxLine);
		save(trxLine);

		sourceTrxLine.setM_HU_Trx_Hdr(trxHeader);
		sourceTrxLine.setParent_HU_Trx_Line(trxLine);
		save(sourceTrxLine);

		// set up the mocked huAccessService to make sure the trxLines are not discarded because they don't have a product
		Mockito.doReturn(Optional.of(ImmutablePair.of(null, null))).when(huAccessService).retrieveProductAndQty(vhu);
		Mockito.doReturn(Optional.of(ImmutablePair.of(null, null))).when(huAccessService).retrieveProductAndQty(sourceVhu);

		final ImmutableList<I_M_HU_Trx_Line> trxLines = ImmutableList.of(trxLine, sourceTrxLine);

		assertThat(huTraceEventsService.filterTrxLinesToUse(trxLines))
				.as("guard: make sure they are not discarded for other reasons")
				.isNotEmpty();

		huTraceEventsService.createAndAddFor(trxHeader, trxLines);

		final List<I_M_HU_Trace> allDBRecords = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.create().list();
		assertThat(allDBRecords)
				.as("There shall be no HU_Trace records for HUs with stautus='planned'")
				.isEmpty();
	}

	@Test
	public void createAndAddForShipmentScheduleQtyPicked()
	{
		final I_M_HU tu1 = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu1 = newInstance(I_M_HU.class);
		vhu1.setHUStatus(X_M_HU.HUSTATUS_Active);
		save(vhu1);
		final ProductId vhu1Product = newProduct("vhu1Product");
		final Quantity vhu1Qty = Quantity.of(11, uom);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		shipmentScheduleQtyPicked.setM_TU_HU(tu1);
		shipmentScheduleQtyPicked.setVHU(vhu1);
		save(shipmentScheduleQtyPicked);

		Mockito.doReturn(tu1.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(tu1);
		Mockito.doReturn(tu1.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(vhu1);
		Mockito.doReturn(Optional.of(ImmutablePair.of(vhu1Product, vhu1Qty))).when(huAccessService).retrieveProductAndQty(vhu1);

		huTraceEventsService.createAndAddFor(shipmentScheduleQtyPicked);

		final List<I_M_HU_Trace> allHuTraceRecords = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class).create().list();
		assertThat(allHuTraceRecords).hasSize(1);

		final I_M_HU_Trace trace = allHuTraceRecords.get(0);
		assertThat(trace.getHUTraceType()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_MATERIAL_PICKING);
		assertThat(trace.getQty()).isEqualByComparingTo("11");
	}

	private ProductId newProduct(final String name)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	// =====================================================================
	// T1: createAndAddForHuParentChanged — VHU moved from standalone into LU
	// =====================================================================

	/**
	 * VHU was standalone (no parent). Now it is moved into an LU.
	 * parentHUItemOld = null (had no parent before).
	 * Expected: 2 TRANSFORM_PARENT events:
	 * 1. topLevelHuId = VHU itself, qty = -qty  (removed from old position)
	 * 2. topLevelHuId = LU,         qty = +qty  (added to new position)
	 */
	@Test
	public void createAndAddForHuParentChanged_newParentIsTopLevel()
	{
		final I_M_HU lu = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu = createVHU();

		final ProductId product = newProduct("vhu_product");
		final Quantity qty = Quantity.of(5, uom);

		Mockito.doReturn(ImmutableList.of(vhu)).when(huAccessService).retrieveVhus(HuId.ofRepoId(vhu.getM_HU_ID()));
		// after the move, the VHU's new top-level is the LU
		Mockito.doReturn(lu.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(vhu);
		Mockito.doReturn(Optional.of(ImmutablePair.of(product, qty))).when(huAccessService).retrieveProductAndQty(vhu);

		// parentHUItemOld = null: VHU had no parent before
		huTraceEventsService.createAndAddForHuParentChanged(vhu, null);

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).as("2 TRANSFORM_PARENT events expected (remove from old + add to new)").hasSize(2);

		traces.sort(Comparator.comparing(I_M_HU_Trace::getQty));

		// First event: removal from old position (qty negative)
		assertThat(traces.get(0).getM_HU_ID()).isEqualTo(vhu.getM_HU_ID()); // old top = VHU itself
		assertThat(traces.get(0).getQty()).isEqualByComparingTo("-5");

		// Second event: addition to new position (qty positive)
		assertThat(traces.get(1).getM_HU_ID()).isEqualTo(lu.getM_HU_ID()); // new top = LU
		assertThat(traces.get(1).getQty()).isEqualByComparingTo("5");

		assertThat(traces.get(0).getHUTraceType()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_TRANSFORM_PARENT);
		assertThat(traces.get(1).getHUTraceType()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_TRANSFORM_PARENT);
	}

	// =====================================================================
	// T2: createAndAddForHuParentChanged — VHU removed from parent (Bug 2 fix)
	// =====================================================================

	/**
	 * Bug 2 scenario: VHU is being removed from its parent LU.
	 * parentHUItemOld points to an old LU.
	 * After removal, retrieveTopLevelHuId(vhu) returns 0 (HU is in transient detached state).
	 * <p>
	 * BEFORE FIX: builder.topLevelHuId(null) throws NPE.
	 * AFTER FIX: falls back to hu.getM_HU_ID() — the VHU itself becomes the new top-level.
	 * <p>
	 * Expected: 2 TRANSFORM_PARENT events:
	 * 1. topLevelHuId = old LU,     qty = -qty  (removed from LU)
	 * 2. topLevelHuId = VHU itself, qty = +qty  (now standalone)
	 */
	@Test
	public void createAndAddForHuParentChanged_removedFromParent()
	{
		final I_M_HU oldLu = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu = createVHU();

		final I_M_HU_Item parentHUItemOld = newInstance(I_M_HU_Item.class);
		parentHUItemOld.setM_HU_ID(oldLu.getM_HU_ID());
		save(parentHUItemOld);

		final ProductId product = newProduct("vhu_product_removed");
		final Quantity qty = Quantity.of(7, uom);

		Mockito.doReturn(ImmutableList.of(vhu)).when(huAccessService).retrieveVhus(HuId.ofRepoId(vhu.getM_HU_ID()));
		// Old LU is its own top-level
		Mockito.doReturn(oldLu.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(oldLu);
		// VHU is detached — retrieveTopLevelHuId returns 0 (triggers the bug / fix)
		Mockito.doReturn(0).when(huAccessService).retrieveTopLevelHuId(vhu);
		Mockito.doReturn(Optional.of(ImmutablePair.of(product, qty))).when(huAccessService).retrieveProductAndQty(vhu);

		huTraceEventsService.createAndAddForHuParentChanged(vhu, parentHUItemOld);

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).as("2 TRANSFORM_PARENT events expected after fix (no NPE)").hasSize(2);

		traces.sort(Comparator.comparing(I_M_HU_Trace::getQty));

		// Removal from old LU
		assertThat(traces.get(0).getM_HU_ID()).as("old top = LU").isEqualTo(oldLu.getM_HU_ID());
		assertThat(traces.get(0).getQty()).isEqualByComparingTo("-7");

		// New position: VHU is now its own top-level (fallback to hu.getM_HU_ID())
		assertThat(traces.get(1).getM_HU_ID()).as("new top = VHU itself (fallback)").isEqualTo(vhu.getM_HU_ID());
		assertThat(traces.get(1).getQty()).isEqualByComparingTo("7");
	}

	// =====================================================================
	// T3: createAndAddEventsForInventoryLines — qty difference (count ≠ book)
	// =====================================================================

	/**
	 * Inventory line with QtyCount > QtyBook: a positive stock adjustment.
	 * isQtyDifference = true → one trace for the HU itself with qty = count - book.
	 */
	@Test
	public void createAndAddEventsForInventoryLines_basic()
	{
		final I_M_HU vhu = createVHU();
		final ProductId product = newProduct("inv_product");
		final Quantity huQty = Quantity.of(10, uom);

		final InventoryLineHU inventoryLineHU = InventoryLineHU.builder()
				.huId(HuId.ofRepoId(vhu.getM_HU_ID()))
				.qtyBook(Quantity.of(BigDecimal.ZERO, uom))
				.qtyCount(Quantity.of(new BigDecimal("3"), uom))
				.build();

		final InventoryLine inventoryLine = buildTestInventoryLine(inventoryLineHU, product);

		final de.metas.handlingunits.model.I_M_InventoryLine inventoryLineRecord = newInstance(de.metas.handlingunits.model.I_M_InventoryLine.class);
		inventoryLineRecord.setQtyBook(BigDecimal.ZERO);
		inventoryLineRecord.setQtyCount(new BigDecimal("3"));
		inventoryLineRecord.setC_UOM_ID(uom.getC_UOM_ID());
		save(inventoryLineRecord);

		final InventoryRepository inventoryRepoMock = Mockito.mock(InventoryRepository.class);
		Mockito.doReturn(inventoryLine).when(inventoryRepoMock).toInventoryLine(inventoryLineRecord);

		final HUTraceEventsService svc = new HUTraceEventsService(new HUTraceRepository(), huAccessService, inventoryRepoMock);

		// VHU has no parent → retrieveTopLevelHuId returns its own ID
		Mockito.doReturn(vhu.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(Mockito.any(I_M_HU.class));
		Mockito.doReturn(Optional.of(ImmutablePair.of(product, huQty))).when(huAccessService).retrieveProductAndQty(Mockito.any(I_M_HU.class));

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(OrgId.ofRepoId(10))
				.type(HUTraceType.MATERIAL_INVENTORY);

		svc.createAndAddEventsForInventoryLines(builder, ImmutableList.of(inventoryLineRecord));

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).as("1 trace for qty difference").hasSize(1);
		assertThat(traces.get(0).getQty()).as("qty = count - book = 3").isEqualByComparingTo("3");
		assertThat(traces.get(0).getHUTraceType()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_MATERIAL_INVENTORY);
	}

	// =====================================================================
	// T4: createAndAddEventsForInventoryLines — internal use (QtyInternalUse > 0)
	// =====================================================================

	/**
	 * Internal-use inventory line (disposal). QtyBook = QtyCount, QtyInternalUse > 0.
	 * isQtyDifference = false → traces created for VHUs under the HU, qty = QtyInternalUse.
	 */
	@Test
	public void createAndAddEventsForInventoryLines_noQtyDifference_internalUse()
	{
		final I_M_HU vhu = createVHU();
		final ProductId product = newProduct("inv_internal_use_product");
		final Quantity huQty = Quantity.of(5, uom);

		final InventoryLineHU inventoryLineHU = InventoryLineHU.builder()
				.huId(HuId.ofRepoId(vhu.getM_HU_ID()))
				.qtyInternalUse(Quantity.of(new BigDecimal("4"), uom))
				.build();

		final InventoryLine inventoryLine = buildTestInventoryLine(inventoryLineHU, product);

		final de.metas.handlingunits.model.I_M_InventoryLine inventoryLineRecord = newInstance(de.metas.handlingunits.model.I_M_InventoryLine.class);
		inventoryLineRecord.setQtyBook(new BigDecimal("5"));
		inventoryLineRecord.setQtyCount(new BigDecimal("5")); // no qty difference
		inventoryLineRecord.setQtyInternalUse(new BigDecimal("4")); // internal use qty
		inventoryLineRecord.setC_UOM_ID(uom.getC_UOM_ID());
		save(inventoryLineRecord);

		final InventoryRepository inventoryRepoMock = Mockito.mock(InventoryRepository.class);
		Mockito.doReturn(inventoryLine).when(inventoryRepoMock).toInventoryLine(inventoryLineRecord);

		final HUTraceEventsService svc = new HUTraceEventsService(new HUTraceRepository(), huAccessService, inventoryRepoMock);

		// VHU has no parent → retrieveTopLevelHuId returns its own ID
		Mockito.doReturn(vhu.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(Mockito.any(I_M_HU.class));
		// VHU retrieves itself
		Mockito.doReturn(ImmutableList.of(vhu)).when(huAccessService).retrieveVhus(HuId.ofRepoId(vhu.getM_HU_ID()));
		Mockito.doReturn(Optional.of(ImmutablePair.of(product, huQty))).when(huAccessService).retrieveProductAndQty(Mockito.any(I_M_HU.class));

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.orgId(OrgId.ofRepoId(10))
				.type(HUTraceType.MATERIAL_INVENTORY);

		svc.createAndAddEventsForInventoryLines(builder, ImmutableList.of(inventoryLineRecord));

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).as("1 trace for internal use VHU").hasSize(1);
		assertThat(traces.get(0).getQty()).as("qty = QtyInternalUse = 4").isEqualByComparingTo("4");
	}

	// =====================================================================
	// T8: createAndAddFor(M_Movement) — basic movement trace
	// =====================================================================

	/**
	 * M_Movement with one movement line, one HU assignment → one MATERIAL_MOVEMENT trace.
	 */
	@Test
	public void createAndAddFor_movement_basic()
	{
		final I_M_HU lu = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu = createVHU();
		final ProductId product = newProduct("movement_product");
		final Quantity qty = Quantity.of(3, uom);

		final I_M_Movement movement = newInstance(I_M_Movement.class);
		movement.setMovementDate(new Timestamp(System.currentTimeMillis()));
		movement.setDocStatus("CO");
		save(movement);

		final I_M_MovementLine movementLine = newInstance(I_M_MovementLine.class);
		save(movementLine);

		final I_M_HU_Assignment assignment = newInstance(I_M_HU_Assignment.class);
		assignment.setM_HU_ID(lu.getM_HU_ID());
		assignment.setVHU_ID(vhu.getM_HU_ID());
		save(assignment);

		Mockito.doReturn(ImmutableList.of(assignment)).when(huAccessService).retrieveHuAssignments(movementLine);
		Mockito.doReturn(lu.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(lu);
		Mockito.doReturn(Optional.of(ImmutablePair.of(product, qty))).when(huAccessService).retrieveProductAndQty(vhu);

		huTraceEventsService.createAndAddFor(movement, ImmutableList.of(movementLine));

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).hasSize(1);
		assertThat(traces.get(0).getHUTraceType()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_MATERIAL_MOVEMENT);
		assertThat(traces.get(0).getM_HU_ID()).isEqualTo(lu.getM_HU_ID());
	}

	// =====================================================================
	// T9: createAndAddFor(HUTraceForReturnedQtyRequest) — single source VHU
	// =====================================================================

	/**
	 * Customer return: one returned VHU linked to one original shipped VHU.
	 * Expected: 1 MATERIAL_RECEIPT trace with vhuSourceId set to the shipped VHU.
	 */
	@Test
	public void createAndAddForReturnedQty_basic()
	{
		final I_M_HU topReturnedHU = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU returnedVhu = createVHU();
		final I_M_HU sourceShippedVhu = createVHU();

		final ProductId product = newProduct("returned_product");
		final Quantity qty = Quantity.of(2, uom);

		final HUTraceForReturnedQtyRequest request = HUTraceForReturnedQtyRequest.builder()
				.returnedVirtualHU(returnedVhu)
				.topLevelReturnedHUId(HuId.ofRepoId(topReturnedHU.getM_HU_ID()))
				.sourceShippedVHUIds(ImmutableSet.of(HuId.ofRepoId(sourceShippedVhu.getM_HU_ID())))
				.docStatus("CO")
				.eventTime(Instant.now())
				.orgId(OrgId.ofRepoId(10))
				.customerReturnId(InOutId.ofRepoId(500))
				.productId(product)
				.qty(qty)
				.build();

		huTraceEventsService.createAndAddFor(request);

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).hasSize(1);
		assertThat(traces.get(0).getHUTraceType()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_MATERIAL_RECEIPT);
		assertThat(traces.get(0).getVHU_ID()).isEqualTo(returnedVhu.getM_HU_ID());
		assertThat(traces.get(0).getVHU_Source_ID()).isEqualTo(sourceShippedVhu.getM_HU_ID());
	}

	// =====================================================================
	// T10: createAndAddFor(HUTraceForReturnedQtyRequest) — multiple source VHUs
	// =====================================================================

	/**
	 * Customer return with multiple source shipped VHUs (e.g., goods were split before shipping).
	 * Expected: one trace per source VHU, all pointing to the same returned VHU.
	 */
	@Test
	public void createAndAddForReturnedQty_multipleSourceVhus()
	{
		final I_M_HU topReturnedHU = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU returnedVhu = createVHU();
		final I_M_HU sourceVhu1 = createVHU();
		final I_M_HU sourceVhu2 = createVHU();

		final ProductId product = newProduct("returned_product_multi");
		final Quantity qty = Quantity.of(4, uom);

		final HUTraceForReturnedQtyRequest request = HUTraceForReturnedQtyRequest.builder()
				.returnedVirtualHU(returnedVhu)
				.topLevelReturnedHUId(HuId.ofRepoId(topReturnedHU.getM_HU_ID()))
				.sourceShippedVHUIds(ImmutableSet.of(
						HuId.ofRepoId(sourceVhu1.getM_HU_ID()),
						HuId.ofRepoId(sourceVhu2.getM_HU_ID())))
				.docStatus("CO")
				.eventTime(Instant.now())
				.orgId(OrgId.ofRepoId(10))
				.customerReturnId(InOutId.ofRepoId(501))
				.productId(product)
				.qty(qty)
				.build();

		huTraceEventsService.createAndAddFor(request);

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).as("one trace per source VHU").hasSize(2);
		assertThat(traces).allMatch(t -> t.getVHU_ID() == returnedVhu.getM_HU_ID());
		assertThat(traces).allMatch(t -> t.getHUTraceType().equals(X_M_HU_Trace.HUTRACETYPE_MATERIAL_RECEIPT));
	}

	// =====================================================================
	// T11: createAndAddFor(M_ShipmentSchedule_QtyPicked) — only LU set
	// =====================================================================

	/**
	 * M_ShipmentSchedule_QtyPicked with only M_LU_HU_ID set (no TU, no VHU).
	 * The service must retrieve VHUs from the LU and create one MATERIAL_PICKING trace per VHU.
	 */
	@Test
	public void createAndAddForShipmentScheduleQtyPicked_luOnly()
	{
		final I_M_HU lu = saveFluent(newInstance(I_M_HU.class));
		final I_M_HU vhu = createVHU();
		final ProductId product = newProduct("lu_only_product");
		final Quantity qty = Quantity.of(6, uom);

		final I_M_ShipmentSchedule_QtyPicked qtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		qtyPicked.setM_LU_HU(lu);
		save(qtyPicked);

		Mockito.doReturn(lu.getM_HU_ID()).when(huAccessService).retrieveTopLevelHuId(lu);
		Mockito.doReturn(ImmutableList.of(vhu)).when(huAccessService).retrieveVhus(HuId.ofRepoId(lu.getM_HU_ID()));
		Mockito.doReturn(Optional.of(ImmutablePair.of(product, qty))).when(huAccessService).retrieveProductAndQty(vhu);

		huTraceEventsService.createAndAddFor(qtyPicked);

		final List<I_M_HU_Trace> traces = retrieveAllHUTraceRecords();
		assertThat(traces).hasSize(1);
		assertThat(traces.get(0).getHUTraceType()).isEqualTo(X_M_HU_Trace.HUTRACETYPE_MATERIAL_PICKING);
		assertThat(traces.get(0).getM_HU_ID()).isEqualTo(lu.getM_HU_ID());
		assertThat(traces.get(0).getVHU_ID()).isEqualTo(vhu.getM_HU_ID());
	}

	// =====================================================================
	// T12: createAndAddFor(M_ShipmentSchedule_QtyPicked) — no HU set → no trace
	// =====================================================================

	/**
	 * M_ShipmentSchedule_QtyPicked with none of its three HU fields set.
	 * The service must return early without creating any trace.
	 */
	@Test
	public void createAndAddForShipmentScheduleQtyPicked_noHuSet()
	{
		final I_M_ShipmentSchedule_QtyPicked qtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		save(qtyPicked); // LU, TU, VHU all 0

		huTraceEventsService.createAndAddFor(qtyPicked);

		assertThat(retrieveAllHUTraceRecords()).as("no HU set → no trace created").isEmpty();
	}

	// =====================================================================
	// Helpers
	// =====================================================================

	/**
	 * Builds a minimal {@link InventoryLine} for unit testing, containing a single {@link InventoryLineHU}.
	 */
	private InventoryLine buildTestInventoryLine(
			final InventoryLineHU inventoryLineHU,
			final ProductId productId)
	{
		return InventoryLine.builder()
				.orgId(OrgId.ofRepoId(10))
				.productId(productId)
				.storageAttributesKey(AttributesKey.NONE)
				.locatorId(LocatorId.ofRepoId(1, 100))
				.asiId(AttributeSetInstanceId.NONE)
				.inventoryLineHU(inventoryLineHU)
				.build();
	}

	private static <T> T saveFluent(final T model)
	{
		saveRecord(model);
		return model;
	}
}
