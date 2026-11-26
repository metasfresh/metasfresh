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
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQtys;
import org.compiere.model.I_C_UOM_Conversion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Pins {@link QtyPickedCandidatesCreator} for picks recorded against one TU holding CUs of
 * several attribute values: a second generation over the same rows keeps every row on its own attribute value, and a row
 * split off another row keeps the pick's identity columns.
 */
class QtyPickedCandidatesCreatorTest extends TUPicksTestBase
{
	@Test
	void secondGeneration_afterSingleCURowsGotTheirCU_keepsEveryRowOnItsOwnCountry()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_FR, HUTestHelper.COUNTRYMADEIN_RO, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("8"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked rowFR = createTUOnlyRow(schedule, tu, "2");
		final I_M_ShipmentSchedule_QtyPicked rowRO = createTUOnlyRow(schedule, tu, "2");
		final I_M_ShipmentSchedule_QtyPicked rowDE1 = createTUOnlyRow(schedule, tu, "2");
		final I_M_ShipmentSchedule_QtyPicked rowDE2 = createTUOnlyRow(schedule, tu, "2");

		createCandidates(retrieveRows(schedule));
		assertThat(retrieveRows(schedule)).as("PRECONDITION: the first generation gives the single-CU rows their CU")
				.filteredOn(row -> row.getVHU_ID() > 0).hasSize(2);

		// the shipment of the first generation is gone (e.g. a deleted draft): the rows are generated again
		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates).hasSize(4);
		assertCountryOfRow(candidates, rowFR, HUTestHelper.COUNTRYMADEIN_FR);
		assertCountryOfRow(candidates, rowRO, HUTestHelper.COUNTRYMADEIN_RO);
		assertCountryOfRow(candidates, rowDE1, HUTestHelper.COUNTRYMADEIN_DE);
		assertCountryOfRow(candidates, rowDE2, HUTestHelper.COUNTRYMADEIN_DE);
	}

	@Test
	void rowSpanningTwoCountries_splitRowKeepsPickingJobScheduleAndPickedOnTheFlyFlag()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row3 = createTUOnlyRow(schedule, tu, "3");
		row3.setM_Picking_Job_Schedule_ID(4711);
		row3.setIsAnonymousHuPickedOnTheFly(true);
		saveRecord(row3);
		createTUOnlyRow(schedule, tu, "1");

		createCandidates(retrieveRows(schedule));

		final List<I_M_ShipmentSchedule_QtyPicked> rows = retrieveRows(schedule);
		assertThat(rows).as("the row of 3 is split into 2 DE + 1 FR").hasSize(3);
		final I_M_ShipmentSchedule_QtyPicked splitRow = rows.get(2);
		assertThat(splitRow.getQtyPicked()).isEqualByComparingTo("1");
		assertThat(splitRow.getM_Picking_Job_Schedule_ID()).isEqualTo(4711);
		assertThat(splitRow.isAnonymousHuPickedOnTheFly()).isTrue();
		assertThat(splitRow.getM_TU_HU_ID()).isEqualTo(tu.getM_HU_ID());
	}

	@Test
	void wholeTUPickOfTwoCountries_splitRowKeepsPickingJobScheduleAndPickedOnTheFlyFlag()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row = createTUOnlyRow(schedule, tu, "4");
		row.setM_Picking_Job_Schedule_ID(4711);
		row.setIsAnonymousHuPickedOnTheFly(true);
		saveRecord(row);

		createCandidates(ImmutableList.of(row));

		final List<I_M_ShipmentSchedule_QtyPicked> rows = retrieveRows(schedule);
		assertThat(rows).as("the whole-TU row is split per country").hasSize(2);
		assertThat(rows.get(1).getM_Picking_Job_Schedule_ID()).isEqualTo(4711);
		assertThat(rows.get(1).isAnonymousHuPickedOnTheFly()).isTrue();
	}

	@Test
	void wholeTUPick_cusKeptInAnotherUOMThanTheStockUOM_rowKeepsItsQty()
	{
		// stock UOM Each, the CUs' storages are kept in Kg: 1 Each = 2 Kg
		final I_C_UOM_Conversion conversion = newInstance(I_C_UOM_Conversion.class);
		conversion.setM_Product_ID(helper.pSaladProductId.getRepoId());
		conversion.setC_UOM_ID(uomEach.getC_UOM_ID());
		conversion.setC_UOM_To_ID(uomKg.getC_UOM_ID());
		conversion.setMultiplyRate(new BigDecimal("2"));
		conversion.setDivideRate(new BigDecimal("0.5"));
		conversion.setIsActive(true);
		saveRecord(conversion);
		// two CUs of one country, so neither matches the row alone: the whole-TU check compares the row with their storage quantities (4 Kg + 4 Kg)
		final I_M_HU tu = createTU(Quantity.of(new BigDecimal("4"), uomKg), HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("8"), BigDecimal.ZERO);
		createTUOnlyRow(schedule, tu, "8");

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates).singleElement().satisfies(candidate -> assertThat(candidate.getQtyPicked().toBigDecimal()).isEqualByComparingTo("8"));
		assertThat(retrieveRows(schedule)).singleElement()
				.satisfies(row -> assertThat(row.getQtyPicked()).as("the row counts in the same quantity as the check that let it be split").isEqualByComparingTo("8"));
	}

	@Test
	void zeroQtyRow_givesAZeroCandidate()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		createTUOnlyRow(schedule, tu, "2");
		final I_M_ShipmentSchedule_QtyPicked zeroRow = createTUOnlyRow(schedule, tu, "0");

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates).hasSize(2);
		assertThat(candidates)
				.filteredOn(candidate -> candidate.getShipmentScheduleQtyPicked().getM_ShipmentSchedule_QtyPicked_ID() == zeroRow.getM_ShipmentSchedule_QtyPicked_ID())
				.singleElement()
				.satisfies(candidate -> assertThat(candidate.getQtyPicked().toBigDecimal()).isZero());
	}

	@Test
	void rowOutsideATU_afterATUOnlyRow_candidatesKeepTheRowOrder()
	{
		// the method contract: candidates come in the order of the given rows (line merging does not depend on it, the callers sort)
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("3"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked tuOnlyRow = createTUOnlyRow(schedule, tu, "2");
		final I_M_ShipmentSchedule_QtyPicked rowOutsideTUs = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				schedule,
				StockQtyAndUOMQtys.ofQtyInStockUOM(Quantity.of(BigDecimal.ONE, uomEach), helper.pSaladProductId),
				I_M_ShipmentSchedule_QtyPicked.class);
		saveRecord(rowOutsideTUs);

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates)
				.extracting(candidate -> candidate.getShipmentScheduleQtyPicked().getM_ShipmentSchedule_QtyPicked_ID())
				.containsExactly(tuOnlyRow.getM_ShipmentSchedule_QtyPicked_ID(), rowOutsideTUs.getM_ShipmentSchedule_QtyPicked_ID());
	}

	@Test
	void tuWithCUOfAnotherProduct_ofOneCombination_keepsTheRowsAsTheyWere()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE);
		addCUOfTomato(tu);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		createTUOnlyRow(schedule, tu, "2");

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates).hasSize(1);
		// the CU of the other product is no content of this schedule: nothing to split, the row keeps no CU of its own
		assertThat(retrieveRows(schedule)).singleElement().satisfies(row -> assertThat(row.getVHU_ID()).isZero());
	}

	@Test
	void tuOfOneCombination_withCUsThatRowsOfTheirOwnStandFor_keepsTheTURowAsItWas()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(tu);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("6"), BigDecimal.ZERO);
		// two CUs have rows of their own (as the mobile picking creates them for catch weight), the first CU only the TU row
		createTUOnlyRow(schedule, tu, "2");
		for (final I_M_HU cu : cus.subList(1, 3))
		{
			final I_M_ShipmentSchedule_QtyPicked rowOfCU = createTUOnlyRow(schedule, tu, "2");
			rowOfCU.setVHU_ID(cu.getM_HU_ID());
			saveRecord(rowOfCU);
		}

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates).hasSize(3);
		assertThat(retrieveRows(schedule)).filteredOn(row -> row.getVHU_ID() <= 0).as("the TU row keeps no CU of its own").hasSize(1);
	}

	@Test
	void interleavedLots_ofCatchWeightProduct_eachLotsCandidatesCarryTheWeightOfItsOwnCUs()
	{
		enableCatchWeightOfSalad();
		// picked in this order: FR 2.100 kg, RO 2.000 kg, FR 1.900 kg (2 pieces each)
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_FR, HUTestHelper.COUNTRYMADEIN_RO, HUTestHelper.COUNTRYMADEIN_FR);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(tu);
		setWeightNet(cus.get(0), "2.100");
		setWeightNet(cus.get(1), "2.000");
		setWeightNet(cus.get(2), "1.900");
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("6"), BigDecimal.ZERO);
		createTUOnlyRowWithWeight(schedule, tu, "2", "2.100");
		createTUOnlyRowWithWeight(schedule, tu, "2", "2.000");
		createTUOnlyRowWithWeight(schedule, tu, "2", "1.900");

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidatesOfCountry(candidates, HUTestHelper.COUNTRYMADEIN_FR)).as("weight of the FR candidates").isEqualByComparingTo("4.000");
		assertThat(candidatesOfCountry(candidates, HUTestHelper.COUNTRYMADEIN_RO)).as("weight of the RO candidates").isEqualByComparingTo("2.000");
	}

	@Test
	void twoCountries_ofCatchWeightProduct_cusWithoutWeight_keepTheWeightOfTheRows()
	{
		enableCatchWeightOfSalad();
		// the CUs were picked by count: their net weight is 0, the rows carry the weighed values
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_FR, HUTestHelper.COUNTRYMADEIN_RO);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		createTUOnlyRowWithWeight(schedule, tu, "2", "2.100");
		createTUOnlyRowWithWeight(schedule, tu, "2", "2.000");

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidatesOfCountry(candidates, HUTestHelper.COUNTRYMADEIN_FR)).isEqualByComparingTo("2.100");
		assertThat(candidatesOfCountry(candidates, HUTestHelper.COUNTRYMADEIN_RO)).isEqualByComparingTo("2.000");
	}

	@Test
	void zeroQtyRow_amongTwoCountries_givesAZeroCandidateAndTheOtherRowsStillSplitPerCountry()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		createTUOnlyRow(schedule, tu, "4");
		final I_M_ShipmentSchedule_QtyPicked zeroRow = createTUOnlyRow(schedule, tu, "0");

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates).as("2 DE + 2 FR of the row of 4, and the zero row").hasSize(3);
		assertThat(candidates)
				.filteredOn(candidate -> candidate.getQtyPicked().signum() > 0)
				.extracting(candidate -> candidate.getQtyPicked().toBigDecimal())
				.allSatisfy(qty -> assertThat(qty).isEqualByComparingTo("2"));
		assertThat(candidates)
				.filteredOn(candidate -> candidate.getShipmentScheduleQtyPicked().getM_ShipmentSchedule_QtyPicked_ID() == zeroRow.getM_ShipmentSchedule_QtyPicked_ID())
				.singleElement()
				.satisfies(candidate -> assertThat(candidate.getQtyPicked().toBigDecimal()).isZero());
	}

	@Test
	void rowOfAnotherProductThanTheCUsOfTheTU_givesTheDefaultCandidate()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule scheduleOfTomato = shipmentScheduleHelper.createShipmentSchedule(helper.pTomato, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				scheduleOfTomato,
				StockQtyAndUOMQtys.ofQtyInStockUOM(Quantity.of(new BigDecimal("2"), uomEach), helper.pTomatoProductId),
				I_M_ShipmentSchedule_QtyPicked.class);
		row.setM_TU_HU_ID(tu.getM_HU_ID());
		saveRecord(row);

		final List<ShipmentScheduleWithHU> candidates = createCandidates(ImmutableList.of(row));

		assertThat(candidates).as("the TU holds no CU of the tomato: nothing to expand").hasSize(1);
		assertThat(candidates.get(0).getQtyPicked().toBigDecimal()).isEqualByComparingTo("2");
	}

	@Test
	void reversalRowWithNegativeQty_isNotHandedTheContentOfTheTU()
	{
		final I_M_HU tu = createTU(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		createTUOnlyRow(schedule, tu, "4");
		createTUOnlyRow(schedule, tu, "-4");

		final List<ShipmentScheduleWithHU> candidates = createCandidates(retrieveRows(schedule));

		assertThat(candidates).as("each row alone: the whole-TU pick splits per country, the negative row stays one candidate").hasSize(3);
		assertThat(candidates).filteredOn(candidate -> candidate.getQtyPicked().signum() < 0).hasSize(1);
	}
}
