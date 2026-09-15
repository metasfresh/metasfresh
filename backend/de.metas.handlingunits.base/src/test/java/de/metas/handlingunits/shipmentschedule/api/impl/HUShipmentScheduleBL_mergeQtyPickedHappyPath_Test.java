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
package de.metas.handlingunits.shipmentschedule.api.impl;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.AddQtyPickedRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.handlingunits.HuPackingInstructionsVersionId.VIRTUAL;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Happy-path coverage for {@link HUShipmentScheduleBL#tryMergeQtyPickedIntoExistingForVHU(AddQtyPickedRequest)}
 * — the qty is actually summed into an existing un-shipped row instead of creating a duplicate.
 *
 * <p>This is the behaviour that defends against the aggregate-HU reversal defect: when a void/reversal
 * replays N trx-lines through the SAME aggregate VHU, the shipment-schedule listener fires N times for
 * one (schedule, VHU) pair; without merging it produces N identical {@code M_ShipmentSchedule_QtyPicked}
 * rows that collide on the partial unique index when the next shipment binds {@code M_InOutLine_ID}. Merging keeps a
 * single row.</p>
 *
 * <p>The merge itself is pure persistence logic (find the one existing un-shipped listener row for the
 * (schedule, VHU) pair, sum the qty, save) and is therefore faithfully testable in-memory — unlike the
 * full reversal trigger, which depends on running-stack HU snapshot replay.</p>
 */
class HUShipmentScheduleBL_mergeQtyPickedHappyPath_Test
{
	private HUTestHelper helper;
	private HUShipmentScheduleBL bl;
	private IQueryBL queryBL;

	private I_M_ShipmentSchedule schedule;
	private I_M_HU vhu;
	private I_C_UOM uom;
	private IMutableHUContext huContext;

	@BeforeEach
	void init()
	{
		helper = new HUTestHelper();
		helper.init();
		bl = new HUShipmentScheduleBL();
		queryBL = Services.get(IQueryBL.class);

		huContext = helper.createMutableHUContextOutOfTransaction();

		uom = helper.uomEach;

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("customer");
		saveRecord(bpartner);
		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(bpLocation);

		schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Product_ID(helper.pTomatoProductId.getRepoId());
		schedule.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		schedule.setC_BPartner_Location_ID(bpLocation.getC_BPartner_Location_ID());
		saveRecord(schedule);

		// A virtual VHU (PI version = the VIRTUAL one set up by HUTestHelper.init()).
		vhu = newInstance(I_M_HU.class);
		vhu.setM_HU_PI_Version_ID(VIRTUAL.getRepoId());
		saveRecord(vhu);
	}

	/**
	 * First listener-pick created one row; the second listener-pick for the same (schedule, VHU)
	 * must MERGE into it (qty summed) rather than create a duplicate.
	 */
	@Test
	void secondListenerPickForSameVHU_mergesIntoExistingRow_insteadOfDuplicating()
	{
		// given: one existing un-shipped, listener-shaped QtyPicked row for (schedule, vhu)
		final I_M_ShipmentSchedule_QtyPicked existing = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		existing.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		existing.setVHU_ID(vhu.getM_HU_ID());
		existing.setQtyPicked(new BigDecimal("4"));
		existing.setIsActive(true);
		saveRecord(existing);

		// when: a second positive, listener-shaped pick of qty 4 arrives for the same (schedule, vhu)
		final boolean merged = bl.tryMergeQtyPickedIntoExistingForVHU(positiveListenerRequest(new BigDecimal("4")));

		// then: it merged, and there is still exactly ONE row, with the summed qty
		assertThat(merged).as("second listener pick for the same VHU should merge").isTrue();

		final List<I_M_ShipmentSchedule_QtyPicked> rows = rowsForScheduleAndVhu();
		assertThat(rows).as("must remain a single row (no duplicate)").hasSize(1);
		assertThat(rows.get(0).getQtyPicked()).as("qty summed").isEqualByComparingTo("8");
	}

	/**
	 * With no pre-existing row, the merge must fall through (return false) so the listener creates the
	 * first row via the normal path.
	 */
	@Test
	void firstListenerPick_noExistingRow_fallsThrough()
	{
		final boolean merged = bl.tryMergeQtyPickedIntoExistingForVHU(positiveListenerRequest(new BigDecimal("4")));

		assertThat(merged).as("first pick (no existing row) must fall through to create-new").isFalse();
		assertThat(rowsForScheduleAndVhu()).isEmpty();
	}

	private AddQtyPickedRequest positiveListenerRequest(final BigDecimal qty)
	{
		final StockQtyAndUOMQty qtyPicked = StockQtyAndUOMQty.builder()
				.productId(ProductId.ofRepoId(schedule.getM_Product_ID()))
				.stockQty(Quantity.of(qty, uom))
				.build();

		return AddQtyPickedRequest.builder()
				.scheduleId(de.metas.picking.api.ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(
						ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID())))
				.cachedShipmentSchedule(schedule)
				.qtyPicked(qtyPicked)
				.hu(vhu)
				.huContext(huContext)
				.anonymousHuPickedOnTheFly(false)
				.build();
	}

	private List<I_M_ShipmentSchedule_QtyPicked> rowsForScheduleAndVhu()
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, vhu.getM_HU_ID())
				.create()
				.list();
	}
}
