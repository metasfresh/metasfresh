package de.metas.handlingunits.trace;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_AD_User;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
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
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;

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

	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		huAccessService = Mockito.spy(new HUAccessService());
		huTraceEventsService = new HUTraceEventsService(new HUTraceRepository(), huAccessService);

		LogManager.setLoggerLevel(HUTraceRepository.class, Level.INFO);

		uom = saveFluent(newInstance(I_C_UOM.class));
	}

	/**
	 * Create two records (AD_Users, but doesn't matter) and different {@link I_M_HU_Assignment} that reference them.
	 */
	@Test
	public void createAndAddEventsWithTwoLUs()
	{
		final I_AD_User user1 = newInstance(I_AD_User.class);
		user1.setLogin("user");
		user1.setName("we-just-need-some-record-as-a-reference");
		save(user1);

		final I_AD_User user2 = newInstance(I_AD_User.class);
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

	private static <T> T saveFluent(final T model)
	{
		saveRecord(model);
		return model;
	}
}
