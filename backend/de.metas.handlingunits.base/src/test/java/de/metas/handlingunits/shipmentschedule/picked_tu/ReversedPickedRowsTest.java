package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_PickedHU;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Pins what a reversed shipment gives back for rows that only reference their TU: {@link ReversedPickedRows} says which rows to
 * restore, {@code IHUShipmentScheduleBL#restoreUnshippedQtyPickedIfMissing} restores them.
 */
class ReversedPickedRowsTest extends TUPicksTestBase
{
	@Test
	void ofTUOnlyRows_restoresEveryCUOfTheTU()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("6"), BigDecimal.ZERO);
		// the rows of a reversed shipment: the CUs of one country share a row, so the row has no VHU_ID
		final I_M_ShipmentSchedule_QtyPicked rowDE = createTUOnlyRow(schedule, tu, "4");
		final I_M_ShipmentSchedule_QtyPicked rowFR = createTUOnlyRow(schedule, tu, "2");
		rowDE.setM_InOutLine_ID(1);
		rowFR.setM_InOutLine_ID(1);
		saveRecord(rowDE);
		saveRecord(rowFR);

		restoreAfterReversal(ImmutableList.of(rowDE, rowFR));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveRows(schedule).stream()
				.filter(row -> row.getM_InOutLine_ID() <= 0)
				.collect(ImmutableList.toImmutableList());
		assertThat(openRows).hasSize(3);
		assertThat(openRows.stream().map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked).reduce(BigDecimal.ZERO, BigDecimal::add)).isEqualByComparingTo("6");
		assertThat(openRows).allSatisfy(row -> assertThat(row.getVHU_ID()).isPositive());
	}

	@Test
	void ofOneTUOnlyRow_restoresNoMoreThanTheRowPicked()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		// the reversed shipment took only one CU of 2 out of the TU of three CUs
		final I_M_ShipmentSchedule_QtyPicked row = createTUOnlyRow(schedule, tu, "2");
		row.setM_InOutLine_ID(1);
		saveRecord(row);

		restoreAfterReversal(ImmutableList.of(row));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveOpenRows(schedule);
		assertThat(openRows).hasSize(1);
		assertThat(openRows.get(0).getQtyPicked()).isEqualByComparingTo("2");
		assertThat(openRows.get(0).getVHU_ID()).isPositive();
	}

	@Test
	void ofTUOnlyRows_ofCatchWeightProduct_restoredCUsCarryTheirOwnWeight()
	{
		enableCatchWeightOfSalad();
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(tu);
		setWeightNet(cus.get(0), "3.500");
		setWeightNet(cus.get(1), "5.250");
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row1 = createTUOnlyRow(schedule, tu, "2");
		final I_M_ShipmentSchedule_QtyPicked row2 = createTUOnlyRow(schedule, tu, "2");
		row1.setM_InOutLine_ID(1);
		row2.setM_InOutLine_ID(1);
		saveRecord(row1);
		saveRecord(row2);

		restoreAfterReversal(ImmutableList.of(row1, row2));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveOpenRows(schedule);
		assertThat(openRows).hasSize(2);
		assertThat(openRows).allSatisfy(row -> {
			assertThat(row.getCatch_UOM_ID()).isEqualTo(uomKg.getC_UOM_ID());
			assertThat(row.getQtyDeliveredCatch()).isEqualByComparingTo(getWeightNet(row.getVHU_ID()));
			assertThat(row.getQtyTU()).isZero();
			assertThat(row.getQtyLU()).isZero();
		});
		assertThat(openRows).extracting(row -> row.getQtyDeliveredCatch().stripTrailingZeros())
				.containsExactlyInAnyOrder(new BigDecimal("3.5"), new BigDecimal("5.25"));
	}

	@Test
	void ofTUOnlyRow_restoresOnlyTheCUsItsPickingJobStepsRecorded()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(tu);
		final I_M_HU cuFR = cus.stream().filter(cu -> HUTestHelper.COUNTRYMADEIN_FR.equals(getCountry(cu))).findFirst().get();
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		// the TU is shared: its first CU (DE) belongs to another schedule; this schedule's step recorded only the FR CU
		final I_M_Picking_Job_Step step = newInstance(I_M_Picking_Job_Step.class);
		step.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		saveRecord(step);
		final I_M_Picking_Job_Step_PickedHU pickedHU = newInstance(I_M_Picking_Job_Step_PickedHU.class);
		pickedHU.setM_Picking_Job_Step_ID(step.getM_Picking_Job_Step_ID());
		pickedHU.setPicked_HU_ID(cuFR.getM_HU_ID());
		saveRecord(pickedHU);
		final I_M_ShipmentSchedule_QtyPicked row = createTUOnlyRow(schedule, tu, "2");
		row.setM_InOutLine_ID(1);
		saveRecord(row);

		restoreAfterReversal(ImmutableList.of(row));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveOpenRows(schedule);
		assertThat(openRows).hasSize(1);
		assertThat(openRows.get(0).getVHU_ID()).isEqualTo(cuFR.getM_HU_ID());
	}

	@Test
	void ofTUOnlyRow_nextToACUTheReopenAlreadyRestored_doesNotRestoreThatCUTwice()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(tu);
		final I_M_HU cuDE = cus.stream().filter(cu -> HUTestHelper.COUNTRYMADEIN_DE.equals(getCountry(cu))).findFirst().get();
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row = createTUOnlyRow(schedule, tu, "4");
		row.setM_InOutLine_ID(1);
		saveRecord(row);
		// the reopen of the completed picking job already restored the DE CU as an open row
		final I_M_ShipmentSchedule_QtyPicked reopened = createTUOnlyRow(schedule, tu, "2");
		reopened.setVHU_ID(cuDE.getM_HU_ID());
		saveRecord(reopened);

		restoreAfterReversal(ImmutableList.of(row));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveOpenRows(schedule);
		assertThat(openRows).hasSize(2);
		assertThat(openRows).extracting(I_M_ShipmentSchedule_QtyPicked::getVHU_ID).doesNotHaveDuplicates();
		assertThat(openRows.stream().map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked).reduce(BigDecimal.ZERO, BigDecimal::add)).isEqualByComparingTo("4");
	}

	@Test
	void ofCatchWeightProduct_cuWithoutWeight_restoredCUTakesTheWeightOfTheReversedRow()
	{
		enableCatchWeightOfSalad();
		// the CU was picked by count: its net weight is 0, the reversed row carries the weighed value
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row = createTUOnlyRowWithWeight(schedule, tu, "2", "2.100");
		row.setM_InOutLine_ID(1);
		saveRecord(row);

		restoreAfterReversal(ImmutableList.of(row));

		assertThat(retrieveOpenRows(schedule)).singleElement().satisfies(openRow -> {
			assertThat(openRow.getCatch_UOM_ID()).isEqualTo(uomKg.getC_UOM_ID());
			assertThat(openRow.getQtyDeliveredCatch()).isEqualByComparingTo("2.100");
		});
	}

	@Test
	void rowOfATUWhoseCUIsCoveredByAPickingJob_isRestored_alsoWhenTheTUItselfIsNot()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row = createTUOnlyRow(schedule, tu, "2");
		final HuId cuId = HuId.ofRepoId(handlingUnitsDAO.retrieveIncludedHUs(tu).get(0).getM_HU_ID());
		final ReversedPickedRows reversedRows = new ReversedPickedRows(ImmutableList.of(row), ImmutableSetMultimap.of());

		assertThat(reversedRows.toRowsToRestore(ImmutableSet.of(cuId))).singleElement().satisfies(rowOfCU -> assertThat(rowOfCU.getVHU_ID()).isEqualTo(cuId.getRepoId()));
		assertThat(reversedRows.toRowsToRestore(ImmutableSet.of())).as("no picking job covers the row").isEmpty();
	}
}
