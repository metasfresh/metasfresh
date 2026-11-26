package de.metas.handlingunits.shipmentschedule.api.impl;

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
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.impl.CopyHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_PickedHU;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.compiere.model.I_C_UOM_Conversion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins {@link IHUShipmentScheduleBL#createCandidatesForBoxPicks} for picks recorded against one box (TU) holding CUs of
 * several attribute values: a second generation over the same rows keeps every row on its own attribute value, and a row
 * split off another row keeps the pick's identity columns.
 */
class HUShipmentScheduleBL_createCandidatesForBoxPicks_Test extends AbstractHUTest
{
	private static final M_ShipmentSchedule_QuantityTypeToUse QTY_TYPE = M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

	private IQueryBL queryBL;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IShipmentScheduleAllocBL shipmentScheduleAllocBL;
	private IHUShipmentScheduleBL huShipmentScheduleBL;
	private ShipmentScheduleHelper shipmentScheduleHelper;
	private I_M_HU_PI_Item_Product boxPIItemProduct;
	private AttributeCode countryAttributeCode;

	@Override
	protected void initialize()
	{
		queryBL = Services.get(IQueryBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
		huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		countryAttributeCode = AttributeCode.ofString(helper.attr_CountryMadeIn.getValue());

		// No ShipmentScheduleHandler is registered in this test, so every UseInASI attribute splits the shipment line.
		final I_M_HU_PI_Attribute piAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(helper.attr_CountryMadeIn)
				.setM_HU_PI(helper.huDefVirtual)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation)
				.setSplitterStrategyClass(NullSplitterStrategy.class)
				.setAggregationStrategyClass(NullAggregationStrategy.class)
				.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
		piAttribute.setUseInASI(true);
		save(piAttribute);

		final I_M_HU_PI boxPI = helper.createHUDefinition("box", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item boxItem = helper.createHU_PI_Item_Material(boxPI);
		boxPIItemProduct = helper.assignProduct(boxItem, helper.pSaladProductId, new BigDecimal("100"), uomEach);
	}

	@Test
	void secondGeneration_afterSingleCURowsGotTheirCU_keepsEveryRowOnItsOwnCountry()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_FR, HUTestHelper.COUNTRYMADEIN_RO, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("8"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked rowFR = createBoxRow(schedule, box, "2");
		final I_M_ShipmentSchedule_QtyPicked rowRO = createBoxRow(schedule, box, "2");
		final I_M_ShipmentSchedule_QtyPicked rowDE1 = createBoxRow(schedule, box, "2");
		final I_M_ShipmentSchedule_QtyPicked rowDE2 = createBoxRow(schedule, box, "2");

		huShipmentScheduleBL.createCandidatesForBoxPicks(retrieveRows(schedule), helper.getHUContext(), QTY_TYPE);
		assertThat(retrieveRows(schedule)).as("PRECONDITION: the first generation gives the single-CU rows their CU")
				.filteredOn(row -> row.getVHU_ID() > 0).hasSize(2);

		// the shipment of the first generation is gone (e.g. a deleted draft): the rows are generated again
		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForBoxPicks(retrieveRows(schedule), helper.getHUContext(), QTY_TYPE);

		assertThat(candidates).hasSize(4);
		assertCountryOfRow(candidates, rowFR, HUTestHelper.COUNTRYMADEIN_FR);
		assertCountryOfRow(candidates, rowRO, HUTestHelper.COUNTRYMADEIN_RO);
		assertCountryOfRow(candidates, rowDE1, HUTestHelper.COUNTRYMADEIN_DE);
		assertCountryOfRow(candidates, rowDE2, HUTestHelper.COUNTRYMADEIN_DE);
	}

	@Test
	void rowSpanningTwoCountries_splitRowKeepsPickingJobScheduleAndPickedOnTheFlyFlag()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row3 = createBoxRow(schedule, box, "3");
		row3.setM_Picking_Job_Schedule_ID(4711);
		row3.setIsAnonymousHuPickedOnTheFly(true);
		saveRecord(row3);
		createBoxRow(schedule, box, "1");

		huShipmentScheduleBL.createCandidatesForBoxPicks(retrieveRows(schedule), helper.getHUContext(), QTY_TYPE);

		final List<I_M_ShipmentSchedule_QtyPicked> rows = retrieveRows(schedule);
		assertThat(rows).as("the row of 3 is split into 2 DE + 1 FR").hasSize(3);
		final I_M_ShipmentSchedule_QtyPicked splitRow = rows.get(2);
		assertThat(splitRow.getQtyPicked()).isEqualByComparingTo("1");
		assertThat(splitRow.getM_Picking_Job_Schedule_ID()).isEqualTo(4711);
		assertThat(splitRow.isAnonymousHuPickedOnTheFly()).isTrue();
		assertThat(splitRow.getM_TU_HU_ID()).isEqualTo(box.getM_HU_ID());
	}

	@Test
	void wholeBoxPickOfTwoCountries_splitRowKeepsPickingJobScheduleAndPickedOnTheFlyFlag()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row = createBoxRow(schedule, box, "4");
		row.setM_Picking_Job_Schedule_ID(4711);
		row.setIsAnonymousHuPickedOnTheFly(true);
		saveRecord(row);

		huShipmentScheduleBL.createCandidatesForQtyPicked(row, helper.getHUContext(), QTY_TYPE);

		final List<I_M_ShipmentSchedule_QtyPicked> rows = retrieveRows(schedule);
		assertThat(rows).as("the whole-box row is split per country").hasSize(2);
		assertThat(rows.get(1).getM_Picking_Job_Schedule_ID()).isEqualTo(4711);
		assertThat(rows.get(1).isAnonymousHuPickedOnTheFly()).isTrue();
	}

	@Test
	void zeroQtyRow_givesAZeroCandidate()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		createBoxRow(schedule, box, "2");
		final I_M_ShipmentSchedule_QtyPicked zeroRow = createBoxRow(schedule, box, "0");

		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForBoxPicks(retrieveRows(schedule), helper.getHUContext(), QTY_TYPE);

		assertThat(candidates).hasSize(2);
		assertThat(candidates)
				.filteredOn(candidate -> candidate.getShipmentScheduleQtyPicked().getM_ShipmentSchedule_QtyPicked_ID() == zeroRow.getM_ShipmentSchedule_QtyPicked_ID())
				.singleElement()
				.satisfies(candidate -> assertThat(candidate.getQtyPicked().toBigDecimal()).isZero());
	}

	@Test
	void boxWithAUnitOfAnotherProduct_ofOneCombination_keepsTheRowsAsTheyWere()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE);
		addUnitOfTomato(box);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		createBoxRow(schedule, box, "2");

		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForBoxPicks(retrieveRows(schedule), helper.getHUContext(), QTY_TYPE);

		assertThat(candidates).hasSize(1);
		// the unit of the other product is no content of this schedule: nothing to split, the row keeps no unit of its own
		assertThat(retrieveRows(schedule)).singleElement().satisfies(row -> assertThat(row.getVHU_ID()).isZero());
	}

	@Test
	void boxOfOneCombination_withUnitsThatRowsOfTheirOwnStandFor_keepsTheBoxRowAsItWas()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(box);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("6"), BigDecimal.ZERO);
		// two units have rows of their own (as the mobile picking creates them for catch weight), the first unit only the box row
		createBoxRow(schedule, box, "2");
		for (final I_M_HU cu : cus.subList(1, 3))
		{
			final I_M_ShipmentSchedule_QtyPicked rowOfUnit = createBoxRow(schedule, box, "2");
			rowOfUnit.setVHU_ID(cu.getM_HU_ID());
			saveRecord(rowOfUnit);
		}

		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForBoxPicks(retrieveRows(schedule), helper.getHUContext(), QTY_TYPE);

		assertThat(candidates).hasSize(3);
		assertThat(retrieveRows(schedule)).filteredOn(row -> row.getVHU_ID() <= 0).as("the box row keeps no unit of its own").hasSize(1);
	}

	@Test
	void restoreAfterReversal_ofBoxRowsWithoutVHU_restoresEveryUnitOfTheBox()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("6"), BigDecimal.ZERO);
		// the rows of a reversed shipment: the units of one country share a row, so the row has no VHU_ID
		final I_M_ShipmentSchedule_QtyPicked rowDE = createBoxRow(schedule, box, "4");
		final I_M_ShipmentSchedule_QtyPicked rowFR = createBoxRow(schedule, box, "2");
		rowDE.setM_InOutLine_ID(1);
		rowFR.setM_InOutLine_ID(1);
		saveRecord(rowDE);
		saveRecord(rowFR);

		huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(ImmutableList.of(rowDE, rowFR));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveRows(schedule).stream()
				.filter(row -> row.getM_InOutLine_ID() <= 0)
				.collect(ImmutableList.toImmutableList());
		assertThat(openRows).hasSize(3);
		assertThat(openRows.stream().map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked).reduce(BigDecimal.ZERO, BigDecimal::add)).isEqualByComparingTo("6");
		assertThat(openRows).allSatisfy(row -> assertThat(row.getVHU_ID()).isPositive());
	}

	@Test
	void restoreAfterReversal_ofOneBoxRowWithoutVHU_restoresNoMoreThanTheRowPicked()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		// the reversed shipment took only one CU of 2 out of the box of three CUs
		final I_M_ShipmentSchedule_QtyPicked row = createBoxRow(schedule, box, "2");
		row.setM_InOutLine_ID(1);
		saveRecord(row);

		huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(ImmutableList.of(row));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveOpenRows(schedule);
		assertThat(openRows).hasSize(1);
		assertThat(openRows.get(0).getQtyPicked()).isEqualByComparingTo("2");
		assertThat(openRows.get(0).getVHU_ID()).isPositive();
	}

	@Test
	void restoreAfterReversal_ofBoxRowsWithoutVHU_ofCatchWeightProduct_restoredUnitsCarryTheirOwnWeight()
	{
		enableCatchWeightOfSalad();
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(box);
		setWeightNet(cus.get(0), "3.500");
		setWeightNet(cus.get(1), "5.250");
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row1 = createBoxRow(schedule, box, "2");
		final I_M_ShipmentSchedule_QtyPicked row2 = createBoxRow(schedule, box, "2");
		row1.setM_InOutLine_ID(1);
		row2.setM_InOutLine_ID(1);
		saveRecord(row1);
		saveRecord(row2);

		huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(ImmutableList.of(row1, row2));

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
	void restoreAfterReversal_ofBoxRowWithoutVHU_restoresOnlyTheUnitsItsPickingJobStepsRecorded()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(box);
		final I_M_HU cuFR = cus.stream().filter(cu -> HUTestHelper.COUNTRYMADEIN_FR.equals(getCountry(cu))).findFirst().get();
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("2"), BigDecimal.ZERO);
		// the box is shared: its first unit (DE) belongs to another schedule; this schedule's step recorded only the FR unit
		final I_M_Picking_Job_Step step = newInstance(I_M_Picking_Job_Step.class);
		step.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		saveRecord(step);
		final I_M_Picking_Job_Step_PickedHU pickedHU = newInstance(I_M_Picking_Job_Step_PickedHU.class);
		pickedHU.setM_Picking_Job_Step_ID(step.getM_Picking_Job_Step_ID());
		pickedHU.setPicked_HU_ID(cuFR.getM_HU_ID());
		saveRecord(pickedHU);
		final I_M_ShipmentSchedule_QtyPicked row = createBoxRow(schedule, box, "2");
		row.setM_InOutLine_ID(1);
		saveRecord(row);

		huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(ImmutableList.of(row));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveOpenRows(schedule);
		assertThat(openRows).hasSize(1);
		assertThat(openRows.get(0).getVHU_ID()).isEqualTo(cuFR.getM_HU_ID());
	}

	@Test
	void restoreAfterReversal_ofBoxRowWithoutVHU_nextToAUnitTheReopenAlreadyRestored_doesNotRestoreThatUnitTwice()
	{
		final I_M_HU box = createBox(HUTestHelper.COUNTRYMADEIN_DE, HUTestHelper.COUNTRYMADEIN_FR);
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(box);
		final I_M_HU cuDE = cus.stream().filter(cu -> HUTestHelper.COUNTRYMADEIN_DE.equals(getCountry(cu))).findFirst().get();
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pSalad, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked row = createBoxRow(schedule, box, "4");
		row.setM_InOutLine_ID(1);
		saveRecord(row);
		// the reopen of the completed picking job already restored the DE unit as an open row
		final I_M_ShipmentSchedule_QtyPicked reopened = createBoxRow(schedule, box, "2");
		reopened.setVHU_ID(cuDE.getM_HU_ID());
		saveRecord(reopened);

		huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(ImmutableList.of(row));

		final List<I_M_ShipmentSchedule_QtyPicked> openRows = retrieveOpenRows(schedule);
		assertThat(openRows).hasSize(2);
		assertThat(openRows).extracting(I_M_ShipmentSchedule_QtyPicked::getVHU_ID).doesNotHaveDuplicates();
		assertThat(openRows.stream().map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked).reduce(BigDecimal.ZERO, BigDecimal::add)).isEqualByComparingTo("4");
	}

	// ------------------------------------------------------------------------------------------------------------

	/**
	 * A box with one CU of 2 per given country, created in the given order.
	 */
	private I_M_HU createBox(final String... countries)
	{
		final IHUContext huContext = helper.getHUContext();
		final HUTransformService transformService = HUTransformService.newInstance(huContext);
		final Quantity cuQty = Quantity.of(new BigDecimal("2"), uomEach);

		I_M_HU box = null;
		for (final String country : countries)
		{
			final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI().setLocatorId(defaultLocatorId);
			helper.load(producer, helper.pSaladProductId, cuQty.toBigDecimal(), uomEach);
			final I_M_HU cu = producer.getCreatedHUs().get(0);

			final IAttributeStorage attributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(cu);
			attributes.setValue(countryAttributeCode, country);
			attributes.saveChangesIfNeeded();

			if (box == null)
			{
				box = transformService.cuToNewTUs(cu, cuQty, boxPIItemProduct, false).get(0);
			}
			else
			{
				transformService.cuToExistingTU(cu, cuQty, box);
			}
		}
		refresh(box);
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(box)).as("PRECONDITION: one CU per country").hasSize(countries.length);
		return box;
	}

	private void addUnitOfTomato(final I_M_HU box)
	{
		final HUTransformService transformService = HUTransformService.newInstance(helper.getHUContext());
		final Quantity cuQty = Quantity.of(new BigDecimal("2"), uomEach);
		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI().setLocatorId(defaultLocatorId);
		helper.load(producer, helper.pTomatoProductId, cuQty.toBigDecimal(), uomEach);
		transformService.cuToExistingTU(producer.getCreatedHUs().get(0), cuQty, box);
		refresh(box);
	}

	private I_M_ShipmentSchedule_QtyPicked createBoxRow(final I_M_ShipmentSchedule schedule, final I_M_HU box, final String qty)
	{
		final I_M_ShipmentSchedule_QtyPicked row = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				schedule,
				StockQtyAndUOMQtys.ofQtyInStockUOM(Quantity.of(new BigDecimal(qty), uomEach), helper.pSaladProductId),
				I_M_ShipmentSchedule_QtyPicked.class);
		row.setM_TU_HU_ID(box.getM_HU_ID());
		saveRecord(row);
		return row;
	}

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveRows(final I_M_ShipmentSchedule schedule)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID)
				.create()
				.list();
	}

	/**
	 * Salad gets the catch UOM Kg (Each <-> Kg) and the virtual PI the Net Weight attribute, so that every CU has a weight of its own.
	 */
	private void enableCatchWeightOfSalad()
	{
		final I_C_UOM_Conversion catchUOMConversion = newInstance(I_C_UOM_Conversion.class);
		catchUOMConversion.setM_Product_ID(helper.pSaladProductId.getRepoId());
		catchUOMConversion.setC_UOM_ID(uomEach.getC_UOM_ID());
		catchUOMConversion.setC_UOM_To_ID(uomKg.getC_UOM_ID());
		catchUOMConversion.setMultiplyRate(BigDecimal.TEN);
		catchUOMConversion.setDivideRate(BigDecimal.ONE.divide(BigDecimal.TEN));
		catchUOMConversion.setIsCatchUOMForProduct(true);
		catchUOMConversion.setIsActive(true);
		saveRecord(catchUOMConversion);

		final I_M_HU_PI_Attribute weightNetPIAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(helper.attr_WeightNet)
				.setM_HU_PI(helper.huDefVirtual)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
		weightNetPIAttribute.setIsReadOnly(false);
		weightNetPIAttribute.setUseInASI(false);
		save(weightNetPIAttribute);
	}

	private void setWeightNet(final I_M_HU cu, final String weightNet)
	{
		final IAttributeStorage attributes = helper.getHUContext().getHUAttributeStorageFactory().getAttributeStorage(cu);
		Weightables.wrap(attributes).setWeightNet(new BigDecimal(weightNet));
		attributes.saveChangesIfNeeded();
	}

	private BigDecimal getWeightNet(final int huId)
	{
		final IAttributeStorage attributes = helper.getHUContext().getHUAttributeStorageFactory().getAttributeStorage(load(huId, I_M_HU.class));
		return Weightables.wrap(attributes).getWeightNet();
	}

	private String getCountry(final I_M_HU cu)
	{
		return helper.getHUContext().getHUAttributeStorageFactory().getAttributeStorage(cu).getValueAsString(countryAttributeCode);
	}

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveOpenRows(final I_M_ShipmentSchedule schedule)
	{
		return retrieveRows(schedule).stream()
				.filter(row -> row.getM_InOutLine_ID() <= 0)
				.collect(ImmutableList.toImmutableList());
	}

	private void assertCountryOfRow(final List<ShipmentScheduleWithHU> candidates, final I_M_ShipmentSchedule_QtyPicked row, final String expectedCountry)
	{
		assertThat(candidates)
				.filteredOn(candidate -> candidate.getShipmentScheduleQtyPicked().getM_ShipmentSchedule_QtyPicked_ID() == row.getM_ShipmentSchedule_QtyPicked_ID())
				.as("candidates of row %s", row.getM_ShipmentSchedule_QtyPicked_ID())
				.isNotEmpty()
				.allSatisfy(candidate -> assertThat(helper.getHUContext().getHUAttributeStorageFactory()
						.getAttributeStorage(candidate.getVHU())
						.getValueAsString(countryAttributeCode))
						.as("country of the candidate of row %s", row.getM_ShipmentSchedule_QtyPicked_ID())
						.isEqualTo(expectedCountry));
	}
}
