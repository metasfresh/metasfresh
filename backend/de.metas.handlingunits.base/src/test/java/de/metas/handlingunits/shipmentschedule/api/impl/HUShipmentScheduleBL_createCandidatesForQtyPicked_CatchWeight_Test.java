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
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the catch weight handling of {@link IHUShipmentScheduleBL#createCandidatesForQtyPicked} when the pick
 * is recorded against a (non-virtual) TU: the weight recorded on the {@link I_M_ShipmentSchedule_QtyPicked}
 * must be carried onto the candidates (Case 1: the single VHU matching the picked qty) respectively be split
 * over the attribute groups so that the parts add up exactly to the recorded weight (Case 2: whole-TU pick
 * covering several attribute groups), and a re-run on the split records must neither lose nor double it.
 * <p>
 * Every test first asserts its precondition (VHU qtys and attribute groups of the TU), so that e.g. a
 * {@code cuToExistingTU} that merges VHUs is detected instead of silently exercising another branch.
 */
class HUShipmentScheduleBL_createCandidatesForQtyPicked_CatchWeight_Test extends AbstractHUTest
{
	// NOTE: services are looked up in initialize(), i.e. after the test framework reset the service registry
	private IQueryBL queryBL;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IShipmentScheduleAllocBL shipmentScheduleAllocBL;
	private IHUShipmentScheduleBL huShipmentScheduleBL;

	private ShipmentScheduleHelper shipmentScheduleHelper;
	private I_M_HU_PI_Item_Product tuPIItemProductCatch;
	private I_M_HU_PI_Item_Product tuPIItemProductNoCatch;
	private UomId kgUomId;

	@Override
	protected void initialize()
	{
		queryBL = Services.get(IQueryBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
		huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		kgUomId = helper.uomKgId;

		// Product with catch UOM Kg (precision 3): Each <-> Kg
		final I_C_UOM_Conversion catchUOMConversion = newInstance(I_C_UOM_Conversion.class);
		catchUOMConversion.setM_Product_ID(pTomatoId.getRepoId());
		catchUOMConversion.setC_UOM_ID(helper.uomEachId.getRepoId());
		catchUOMConversion.setC_UOM_To_ID(kgUomId.getRepoId());
		catchUOMConversion.setMultiplyRate(BigDecimal.TEN);
		catchUOMConversion.setDivideRate(BigDecimal.ONE.divide(BigDecimal.TEN));
		catchUOMConversion.setIsCatchUOMForProduct(true);
		catchUOMConversion.setIsActive(true);
		saveRecord(catchUOMConversion);

		// A UseInASI attribute on the virtual PI (CUs): different values => different attribute groups.
		// No ShipmentScheduleHandler is registered in this test, so every UseInASI attribute splits.
		final I_M_HU_PI_Attribute piAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(helper.attr_CountryMadeIn)
				.setM_HU_PI(helper.huDefVirtual)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation)
				.setSplitterStrategyClass(NullSplitterStrategy.class)
				.setAggregationStrategyClass(NullAggregationStrategy.class)
				.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
		piAttribute.setUseInASI(true);
		save(piAttribute);

		final I_M_HU_PI tuPI = helper.createHUDefinition("catchWeightTU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuItem = helper.createHU_PI_Item_Material(tuPI);
		tuPIItemProductCatch = helper.assignProduct(tuItem, pTomatoId, new BigDecimal("100"), uomEach);
		tuPIItemProductNoCatch = helper.assignProduct(tuItem, helper.pSaladProductId, new BigDecimal("100"), uomEach);
	}

	// ------------------------------------------------------------------------------------------------------------

	@Test
	void caseOne_singleCUExactQty_candidateCarriesRecordWeight()
	{
		final I_M_HU tu = createTU(tuPIItemProductCatch, pTomatoId, new CUSpec("4", HUTestHelper.COUNTRYMADEIN_DE));
		assertPrecondition(tu, new String[] { "4" }, 1);

		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pTomato, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_ShipmentSchedule_QtyPicked record = createPickRecord(schedule, tu, pTomatoId, "4", "3.535");

		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForQtyPicked(record, helper.getHUContext(), M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getCatchQty()).as("catch qty of the single candidate").isPresent();
		assertThat(candidates.get(0).getCatchQty().get().getUomId()).isEqualTo(kgUomId);
		assertThat(candidates.get(0).getCatchQty().get().toBigDecimal()).isEqualByComparingTo("3.535");
	}

	@Test
	void caseTwo_threeAttributeGroups_splitsByQty_partsAddUpExactly()
	{
		final I_M_ShipmentSchedule schedule = createSplitScenarioSchedule(pTomatoId, helper.pTomato);
		final I_M_HU tu = createThreeGroupTU(tuPIItemProductCatch, pTomatoId);
		assertPrecondition(tu, new String[] { "1", "1", "1" }, 3);
		final I_M_ShipmentSchedule_QtyPicked record = createPickRecord(schedule, tu, pTomatoId, "3", "10.000");

		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForQtyPicked(record, helper.getHUContext(), M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		assertThat(candidates).hasSize(3);
		assertCatchQtys(candidates, "3.333", "3.333", "3.334");
		final List<I_M_ShipmentSchedule_QtyPicked> records = retrieveAllRecords(schedule);
		assertThat(records).hasSize(3);
		assertRecordCatch(records, "3.333", "3.333", "3.334");
		assertThat(sumCatch(records)).isEqualByComparingTo("10.000");
	}

	@Test
	void caseTwo_oneAttributeGroupTwoCUs_keepsWholeWeight()
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(helper.pTomato, uomEach, new BigDecimal("4"), BigDecimal.ZERO);
		final I_M_HU tu = createTU(tuPIItemProductCatch, pTomatoId,
				new CUSpec("2", HUTestHelper.COUNTRYMADEIN_DE),
				new CUSpec("2", HUTestHelper.COUNTRYMADEIN_DE));
		assertPrecondition(tu, new String[] { "2", "2" }, 1);
		final I_M_ShipmentSchedule_QtyPicked record = createPickRecord(schedule, tu, pTomatoId, "4", "8.000");

		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForQtyPicked(record, helper.getHUContext(), M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		assertThat(candidates).hasSize(1);
		assertCatchQtys(candidates, "8.000");
		final List<I_M_ShipmentSchedule_QtyPicked> records = retrieveAllRecords(schedule);
		assertThat(records).hasSize(1);
		assertRecordCatch(records, "8.000");
	}

	@Test
	void rerun_doesNotLoseOrDoubleWeight()
	{
		final I_M_ShipmentSchedule schedule = createSplitScenarioSchedule(pTomatoId, helper.pTomato);
		final I_M_HU tu = createThreeGroupTU(tuPIItemProductCatch, pTomatoId);
		assertPrecondition(tu, new String[] { "1", "1", "1" }, 3);
		final I_M_ShipmentSchedule_QtyPicked record = createPickRecord(schedule, tu, pTomatoId, "3", "10.000");
		final IHUContext huContext = helper.getHUContext();

		// first run splits the pick
		huShipmentScheduleBL.createCandidatesForQtyPicked(record, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		// re-run on every record of the schedule (as a second shipment generation would)
		final List<I_M_ShipmentSchedule_QtyPicked> recordsAfterFirstRun = retrieveAllRecords(schedule);
		assertThat(recordsAfterFirstRun).hasSize(3);
		final List<ShipmentScheduleWithHU> rerunCandidates = new ArrayList<>();
		for (final I_M_ShipmentSchedule_QtyPicked r : recordsAfterFirstRun)
		{
			rerunCandidates.addAll(huShipmentScheduleBL.createCandidatesForQtyPicked(r, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER));
		}

		final List<I_M_ShipmentSchedule_QtyPicked> records = retrieveAllRecords(schedule);
		assertThat(records).hasSize(3);
		assertRecordCatch(records, "3.333", "3.333", "3.334");
		assertThat(rerunCandidates).hasSize(3);
		assertCatchQtys(rerunCandidates, "3.333", "3.333", "3.334");
		assertThat(sumCatch(records)).isEqualByComparingTo("10.000");
	}

	@Test
	void nonCatchWeightProduct_noCatchQtyAnywhere()
	{
		final I_M_ShipmentSchedule schedule = createSplitScenarioSchedule(helper.pSaladProductId, helper.pSalad);
		final I_M_HU tu = createThreeGroupTU(tuPIItemProductNoCatch, helper.pSaladProductId);
		assertPrecondition(tu, new String[] { "1", "1", "1" }, 3);
		final I_M_ShipmentSchedule_QtyPicked record = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				schedule,
				StockQtyAndUOMQtys.ofQtyInStockUOM(Quantity.of(new BigDecimal("3"), uomEach), helper.pSaladProductId),
				I_M_ShipmentSchedule_QtyPicked.class);
		record.setM_TU_HU_ID(tu.getM_HU_ID());
		saveRecord(record);

		final List<ShipmentScheduleWithHU> candidates = huShipmentScheduleBL.createCandidatesForQtyPicked(record, helper.getHUContext(), M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		assertThat(candidates).hasSize(3);
		candidates.forEach(c -> assertThat(c.getCatchQty()).as("catch qty of a non catch weight product").isEmpty());
		final List<I_M_ShipmentSchedule_QtyPicked> records = retrieveAllRecords(schedule);
		assertThat(records).hasSize(3);
		records.forEach(r -> assertThat(r.getCatch_UOM_ID()).as("Catch_UOM_ID of a non catch weight product").isLessThanOrEqualTo(0));
	}

	// ------------------------------------------------------------------------------------------------------------
	// fixture helpers

	private I_M_ShipmentSchedule createSplitScenarioSchedule(final ProductId productId, final I_M_Product product)
	{
		return shipmentScheduleHelper.createShipmentSchedule(product, uomEach, new BigDecimal("3"), BigDecimal.ZERO);
	}

	private I_M_HU createThreeGroupTU(final I_M_HU_PI_Item_Product tuPIItemProduct, final ProductId productId)
	{
		return createTU(tuPIItemProduct, productId,
				new CUSpec("1", HUTestHelper.COUNTRYMADEIN_DE),
				new CUSpec("1", HUTestHelper.COUNTRYMADEIN_FR),
				new CUSpec("1", HUTestHelper.COUNTRYMADEIN_RO));
	}

	private static final class CUSpec
	{
		private final BigDecimal qty;
		private final String attributeValue;

		private CUSpec(final String qty, final String attributeValue)
		{
			this.qty = new BigDecimal(qty);
			this.attributeValue = attributeValue;
		}
	}

	/**
	 * Builds a TU from the given CUs: the first one via {@code cuToNewTUs}, the others via {@code cuToExistingTU}.
	 */
	private I_M_HU createTU(final I_M_HU_PI_Item_Product tuPIItemProduct, final ProductId productId, final CUSpec... cuSpecs)
	{
		final IHUContext huContext = helper.getHUContext();
		final HUTransformService transformService = HUTransformService.newInstance(huContext);

		I_M_HU tu = null;
		for (final CUSpec cuSpec : cuSpecs)
		{
			final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI().setLocatorId(defaultLocatorId);
			helper.load(producer, productId, cuSpec.qty, uomEach);
			final I_M_HU cu = producer.getCreatedHUs().get(0);

			final IAttributeStorage attributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(cu);
			attributes.setValue(AttributeCode.ofString(helper.attr_CountryMadeIn.getValue()), cuSpec.attributeValue);
			attributes.saveChangesIfNeeded();

			final Quantity qty = Quantity.of(cuSpec.qty, uomEach);
			if (tu == null)
			{
				tu = transformService.cuToNewTUs(cu, qty, tuPIItemProduct, false).get(0);
			}
			else
			{
				transformService.cuToExistingTU(cu, qty, tu);
			}
		}
		refresh(tu);
		return tu;
	}

	/**
	 * Asserts the VHU qtys (in order) and the number of distinct attribute groups of the given TU.
	 */
	private void assertPrecondition(final I_M_HU tu, final String[] expectedVhuQtys, final int expectedAttributeGroups)
	{
		final IHUContext huContext = helper.getHUContext();
		final List<I_M_HU> vhus = handlingUnitsDAO.retrieveIncludedHUs(tu);
		final List<BigDecimal> qtys = new ArrayList<>();
		final Set<String> groups = new LinkedHashSet<>();
		for (final I_M_HU vhu : vhus)
		{
			final List<IHUProductStorage> productStorages = huContext.getHUStorageFactory().getStorage(vhu).getProductStorages();
			assertThat(productStorages).as("product storages of VHU " + vhu.getM_HU_ID()).hasSize(1);
			qtys.add(productStorages.get(0).getQty().toBigDecimal());
			groups.add(huContext.getHUAttributeStorageFactory().getAttributeStorage(vhu)
					.getValueAsString(AttributeCode.ofString(helper.attr_CountryMadeIn.getValue())));
		}
		assertThat(qtys).as("PRECONDITION: VHU qtys of the TU").usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
				.containsExactly(java.util.Arrays.stream(expectedVhuQtys).map(BigDecimal::new).toArray(BigDecimal[]::new));
		assertThat(groups).as("PRECONDITION: attribute groups of the TU").hasSize(expectedAttributeGroups);
	}

	private I_M_ShipmentSchedule_QtyPicked createPickRecord(
			final I_M_ShipmentSchedule schedule,
			final I_M_HU tu,
			final ProductId productId,
			final String qty,
			final String weightKg)
	{
		final I_M_ShipmentSchedule_QtyPicked record = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				schedule,
				StockQtyAndUOMQtys.create(new BigDecimal(qty), productId, new BigDecimal(weightKg), kgUomId),
				I_M_ShipmentSchedule_QtyPicked.class);
		record.setM_TU_HU_ID(tu.getM_HU_ID());
		saveRecord(record);
		return record;
	}

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveAllRecords(final I_M_ShipmentSchedule schedule)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID)
				.create()
				.list();
	}

	private void assertCatchQtys(final List<ShipmentScheduleWithHU> candidates, final String... expectedKg)
	{
		assertThat(candidates).hasSize(expectedKg.length);
		for (int i = 0; i < expectedKg.length; i++)
		{
			final int idx = i;
			assertThat(candidates.get(idx).getCatchQty()).as("catch qty of candidate #" + idx).isPresent();
			assertThat(candidates.get(idx).getCatchQty().get().getUomId()).as("catch UOM of candidate #" + idx).isEqualTo(kgUomId);
			assertThat(candidates.get(idx).getCatchQty().get().toBigDecimal()).as("catch qty of candidate #" + idx).isEqualByComparingTo(expectedKg[idx]);
		}
	}

	private void assertRecordCatch(final List<I_M_ShipmentSchedule_QtyPicked> records, final String... expectedKg)
	{
		assertThat(records).hasSize(expectedKg.length);
		for (int i = 0; i < expectedKg.length; i++)
		{
			final I_M_ShipmentSchedule_QtyPicked r = records.get(i);
			assertThat(r.getCatch_UOM_ID()).as("Catch_UOM_ID of record #" + i).isEqualTo(kgUomId.getRepoId());
			assertThat(r.getQtyDeliveredCatch()).as("QtyDeliveredCatch of record #" + i).isEqualByComparingTo(expectedKg[i]);
		}
	}

	private static BigDecimal sumCatch(final List<I_M_ShipmentSchedule_QtyPicked> records)
	{
		return records.stream()
				.map(I_M_ShipmentSchedule_QtyPicked::getQtyDeliveredCatch)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
