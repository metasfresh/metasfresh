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
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HuId;
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
import de.metas.handlingunits.inout.QtyPickedRecords;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.model.PickedHUsBySchedule;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Harness for tests of picks into a TU that holds CUs of several attribute values.
 */
@SuppressWarnings("SameParameterValue")
abstract class TUPicksTestBase extends AbstractHUTest
{
	protected static final M_ShipmentSchedule_QuantityTypeToUse QTY_TYPE = M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

	protected IQueryBL queryBL;
	protected IHandlingUnitsDAO handlingUnitsDAO;
	protected IShipmentScheduleAllocBL shipmentScheduleAllocBL;
	protected IHUShipmentScheduleBL huShipmentScheduleBL;
	protected ShipmentScheduleHelper shipmentScheduleHelper;
	protected I_M_HU_PI_Item_Product tuPIItemProduct;
	protected AttributeCode countryAttributeCode;

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

		final I_M_HU_PI tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuItem = helper.createHU_PI_Item_Material(tuPI);
		tuPIItemProduct = helper.assignProduct(tuItem, helper.pSaladProductId, new BigDecimal("100"), uomEach);
	}

	protected BigDecimal candidatesOfCountry(final List<ShipmentScheduleWithHU> candidates, final String country)
	{
		return candidates.stream()
				.filter(candidate -> country.equals(getCountry(candidate.getVHU())))
				.map(candidate -> candidate.getShipmentScheduleQtyPicked().getQtyDeliveredCatch())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	protected I_M_ShipmentSchedule_QtyPicked createTUOnlyRowWithWeight(final I_M_ShipmentSchedule schedule, final I_M_HU tu, final String qty, final String weightKg)
	{
		final I_M_ShipmentSchedule_QtyPicked row = createTUOnlyRow(schedule, tu, qty);
		row.setCatch_UOM_ID(uomKg.getC_UOM_ID());
		row.setQtyDeliveredCatch(new BigDecimal(weightKg));
		saveRecord(row);
		return row;
	}

	// ------------------------------------------------------------------------------------------------------------

	/**
	 * A TU with one CU of 2 per given country, created in the given order.
	 */
	protected I_M_HU createTU(final String... countries)
	{
		return createTU(Quantity.of(new BigDecimal("2"), uomEach), countries);
	}

	/**
	 * A TU with one CU of the given qty per given country, created in the given order; each CU's storage is kept in the UOM of that qty.
	 */
	@SuppressWarnings("DataFlowIssue")
	protected I_M_HU createTU(final Quantity cuQty, final String... countries)
	{
		final IHUContext huContext = helper.getHUContext();
		final HUTransformService transformService = HUTransformService.newInstance(huContext);

		I_M_HU tu = null;
		for (final String country : countries)
		{
			final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI().setLocatorId(defaultLocatorId);
			helper.load(producer, helper.pSaladProductId, cuQty.toBigDecimal(), cuQty.getUOM());
			final I_M_HU cu = producer.getCreatedHUs().get(0);

			final IAttributeStorage attributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(cu);
			attributes.setValue(countryAttributeCode, country);
			attributes.saveChangesIfNeeded();

			if (tu == null)
			{
				tu = transformService.cuToNewTUs(cu, cuQty, tuPIItemProduct, false).get(0);
			}
			else
			{
				transformService.cuToExistingTU(cu, cuQty, tu);
			}
		}
		refresh(tu);
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(tu)).as("PRECONDITION: one CU per country").hasSameSizeAs(countries);
		return tu;
	}

	protected void addCUOfTomato(final I_M_HU tu)
	{
		final HUTransformService transformService = HUTransformService.newInstance(helper.getHUContext());
		final Quantity cuQty = Quantity.of(new BigDecimal("2"), uomEach);
		final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI().setLocatorId(defaultLocatorId);
		helper.load(producer, helper.pTomatoProductId, cuQty.toBigDecimal(), uomEach);
		transformService.cuToExistingTU(producer.getCreatedHUs().get(0), cuQty, tu);
		refresh(tu);
	}

	protected I_M_ShipmentSchedule_QtyPicked createTUOnlyRow(final I_M_ShipmentSchedule schedule, final I_M_HU tu, final String qty)
	{
		final I_M_ShipmentSchedule_QtyPicked row = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				schedule,
				StockQtyAndUOMQtys.ofQtyInStockUOM(Quantity.of(new BigDecimal(qty), uomEach), helper.pSaladProductId),
				I_M_ShipmentSchedule_QtyPicked.class);
		row.setM_TU_HU_ID(tu.getM_HU_ID());
		saveRecord(row);
		return row;
	}

	protected List<I_M_ShipmentSchedule_QtyPicked> retrieveRows(final I_M_ShipmentSchedule schedule)
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
	protected void enableCatchWeightOfSalad()
	{
		final I_C_UOM_Conversion catchUOMConversion = newInstance(I_C_UOM_Conversion.class);
		catchUOMConversion.setM_Product_ID(helper.pSaladProductId.getRepoId());
		catchUOMConversion.setC_UOM_ID(uomEach.getC_UOM_ID());
		catchUOMConversion.setC_UOM_To_ID(uomKg.getC_UOM_ID());
		catchUOMConversion.setMultiplyRate(BigDecimal.TEN);
		//noinspection BigDecimalMethodWithoutRoundingCalled
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

	protected void setWeightNet(final I_M_HU cu, final String weightNet)
	{
		final IAttributeStorage attributes = helper.getHUContext().getHUAttributeStorageFactory().getAttributeStorage(cu);
		Weightables.wrap(attributes).setWeightNet(new BigDecimal(weightNet));
		attributes.saveChangesIfNeeded();
	}

	protected BigDecimal getWeightNet(final int huId)
	{
		final IAttributeStorage attributes = helper.getHUContext().getHUAttributeStorageFactory().getAttributeStorage(load(huId, I_M_HU.class));
		return Weightables.wrap(attributes).getWeightNet();
	}

	protected String getCountry(final I_M_HU cu)
	{
		return helper.getHUContext().getHUAttributeStorageFactory().getAttributeStorage(cu).getValueAsString(countryAttributeCode);
	}

	protected List<I_M_ShipmentSchedule_QtyPicked> retrieveOpenRows(final I_M_ShipmentSchedule schedule)
	{
		return retrieveRows(schedule).stream()
				.filter(row -> row.getM_InOutLine_ID() <= 0)
				.collect(ImmutableList.toImmutableList());
	}

	protected void assertCountryOfRow(final List<ShipmentScheduleWithHU> candidates, final I_M_ShipmentSchedule_QtyPicked row, final String expectedCountry)
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

	protected List<ShipmentScheduleWithHU> createCandidates(final List<I_M_ShipmentSchedule_QtyPicked> rows)
	{
		return new QtyPickedCandidatesCreator(helper.getHUContext(), QTY_TYPE, PickedHUsBySchedule.EMPTY).createCandidates(QtyPickedRecords.of(rows));
	}

	/**
	 * What the reversal of a shipment does with its rows, once a picking job covers every HU of them.
	 */
	protected void restoreAfterReversal(final List<I_M_ShipmentSchedule_QtyPicked> reversedRows)
	{
		final PickedHUsBySchedule pickedHuIdsBySchedule = PickingJobRepository.newInstanceForUnitTesting()
				.getPickedHuIdsByShipmentScheduleIds(QtyPickedRecords.of(reversedRows).extractScheduleIds());
		final Set<HuId> coveredHuIds = new HashSet<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : reversedRows)
		{
			final HuId tuId = HuId.ofRepoIdOrNull(row.getM_TU_HU_ID());
			if (tuId != null)
			{
				coveredHuIds.add(tuId);
			}
		}
		huShipmentScheduleBL.restoreUnshippedQtyPickedIfMissing(
				new ReversedPickedRows(QtyPickedRecords.of(reversedRows), pickedHuIdsBySchedule).toRowsToRestore(coveredHuIds).toList());
	}
}
