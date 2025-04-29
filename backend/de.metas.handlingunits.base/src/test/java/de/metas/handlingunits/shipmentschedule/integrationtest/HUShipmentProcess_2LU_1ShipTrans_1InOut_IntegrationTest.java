package de.metas.handlingunits.shipmentschedule.integrationtest;

import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.shipping.impl.HUShipperTransportationBL;
import de.metas.handlingunits.shipping.weighting.ShippingWeightSourceType;
import de.metas.inout.IInOutDAO;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Package;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test case:
 * <ul>
 * <li>split each TU <i>fully</i> to an LU in Verdichtung
 * <li>assume: both TUs end up on a single shipment, on different lines (one per line per shipment schedule)
 * </ul>
 */
public class HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest
		extends AbstractAggregateShipment2SSchedHUShipmentProcessIntegrationTest
{
	private static final Logger logger = LogManager.getLogger(HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest.class);

	@Override
	protected void initialize()
	{
		super.initialize();

		Services.get(ISysConfigBL.class).setValue(HUShipperTransportationBL.SYSCONFIG_WeightSourceTypes, ShippingWeightSourceType.ProductWeight.toString(), ClientId.SYSTEM, OrgId.ANY);

		pTomato.setGrossWeight(BigDecimal.ONE);
		pTomato.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());
		InterfaceWrapperHelper.save(pTomato);

		pSalad.setGrossWeight(BigDecimal.ONE);
		pSalad.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());
		InterfaceWrapperHelper.save(pSalad);

		// only set to trace if there are problems to debug
		// LogManager.setLoggerLevel(LogManager.getLogger("de.metas.handlingunits.shipmentschedule"), Level.TRACE);
	}

	@Override
	protected void step30_aggregateHUs()
	{
		//
		// Get Picked TUs
		final I_M_HU tuHU1 = afterPick_HUExpectations.huExpectation(0).getCapturedHU();
		final I_M_HU tuHU2 = afterPick_HUExpectations.huExpectation(1).getCapturedHU();
		final I_M_HU tuHU3 = afterPick_HUExpectations.huExpectation(2).getCapturedHU();
		final I_M_HU tuHU4 = afterPick_HUExpectations.huExpectation(3).getCapturedHU();
		final I_M_HU tuHU5 = afterPick_HUExpectations.huExpectation(4).getCapturedHU();
		final I_M_HU tuHU6 = afterPick_HUExpectations.huExpectation(5).getCapturedHU();

		//
		// Get shipment schedules
		final I_M_ShipmentSchedule shipmentSchedule1 = shipmentSchedules.get(0);
		final I_M_ShipmentSchedule shipmentSchedule2 = shipmentSchedules.get(1);

		//
		// Assign TUs to palettes
		final IMutable<I_M_HU> afterAggregation_LU1 = new Mutable<>();
		// final IMutable<I_M_HU> tu1 = new Mutable<>(); there won't be any tu1 because the result of tuHU1's split will be a LU with an aggregate VHU
		final IMutable<I_M_HU> vhu1 = new Mutable<>();

		final IMutable<I_M_HU> afterAggregation_LU2 = new Mutable<>();
		// final IMutable<I_M_HU> tu2 = new Mutable<>(); there won't be any tu2 because the result of tuHU2's split will be a LU with an aggregate VHU
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		final IMutable<I_M_HU> tu3 = new Mutable<>();
		final IMutable<I_M_HU> vhu3 = new Mutable<>();
		final IMutable<I_M_HU> tu4 = new Mutable<>();
		final IMutable<I_M_HU> vhu4 = new Mutable<>();
		final IMutable<I_M_HU> tu5 = new Mutable<>();
		final IMutable<I_M_HU> vhu5 = new Mutable<>();
		final IMutable<I_M_HU> tu6 = new Mutable<>();
		final IMutable<I_M_HU> vhu6 = new Mutable<>();

		// split everything from tuHU1 and tuHU2 onto one respective LU each
		final List<I_M_HU> splitSS1LUs = splitOnLU(tuHU1, pTomatoId, new BigDecimal("30"));
		assertThat(splitSS1LUs.size(), is(1));

		final List<I_M_HU> splitSS2LUs = splitOnLU(tuHU2, pSaladId, new BigDecimal("10"));
		assertThat(splitSS2LUs.size(), is(1));

		final I_M_HU splitSS2LU = splitSS2LUs.get(0); // we split full qty on a palette

		// also add tuHU3 to tuHU6 onto the 2nd LU
		helper.joinHUs(huContext, splitSS2LU, tuHU3, tuHU4, tuHU5, tuHU6);

		final List<I_M_HU> allSplitHUs = new ArrayList<>();
		allSplitHUs.addAll(splitSS1LUs);
		allSplitHUs.addAll(splitSS2LUs);

		// System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml("allSplitHUs", allSplitHUs)));

		//
		// Validate split LU/TU/VHUs
		//@formatter:off
		afterAggregation_HUExpectations = new HUsExpectation()
			//
			// first LU
			.newHUExpectation()
				.capture(afterAggregation_LU1)
				.huPI(piLU)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.item()
					//
					// aggregate HU
					.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
					.includedHU()
						.capture(vhu1)
						.huPI(null)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.item()
							.noIncludedHUs()
							.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.storage()
								.product(pTomato).qty("30").uom(productUOM)
							.endExpectation() // itemStorageExcpectation
						.endExpectation() // newHUItemExpectation;

						// note: no packing material item because we didn't create a PM PI item

					.endExpectation() // included aggregate VHU
				.endExpectation() // HUAggreagate item
			.endExpectation() // first LU

			// second LU
			.newHUExpectation()
				.capture(afterAggregation_LU2)
				.huPI(piLU)
				.huStatus(X_M_HU.HUSTATUS_Picked)

				// now we still have have tuHU3, tuHU4, tuHU5 and tuHU6 that were not destroyed but simply joined to the 2nd LU
				.item(piLU_Item)
					.itemType(X_M_HU_Item.ITEMTYPE_HandlingUnit)
					.includedHU()
						.capture(tu3)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.item(piTU_Item)
							//
							// VHUs
							.includedVirtualHU()
								.capture(vhu3)
								.virtualPIItem()
									.storage()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
					.includedHU()
						.capture(tu4)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.item(piTU_Item)
							//
							// VHUs
							.includedVirtualHU()
								.capture(vhu4)
								.virtualPIItem()
									.storage()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
					.includedHU()
						.capture(tu5)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.item(piTU_Item)
							//
							// VHUs
							.includedVirtualHU()
								.capture(vhu5)
								.virtualPIItem()
									.storage()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
					.includedHU()
						.capture(tu6)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.item(piTU_Item)
							//
							// VHUs
							.includedVirtualHU()
								.capture(vhu6)
								.virtualPIItem()
									.storage()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()

					.item()
						//
						// aggregate HU of the 2nd LU; it only contains the qty that were split onto the new LU
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.includedHU() // the aggregate VHU
							.capture(vhu2)
							.huPI(null)
							.huStatus(X_M_HU.HUSTATUS_Picked)

							.item()
								.itemType(X_M_HU_Item.ITEMTYPE_Material)
								.noIncludedHUs()
								.storage()
									.product(pSalad).qty("10").uom(productUOM)
								.endExpectation()
							.endExpectation()

							// note: no packing material item because we didn't create a PM PI item

						.endExpectation() // the aggregate VHU
					.endExpectation() // HUAggregate item

			.endExpectation() // 2nd LU
		.assertExpected("split HUs", allSplitHUs);
		//@formatter:on

		// set instance names to make it easier to understand which HU is "wrong"
		POJOWrapper.setInstanceName(afterAggregation_LU1.getValue(), "afterAggregation_LU1");
		POJOWrapper.setInstanceName(vhu1.getValue(), "vhu1");
		POJOWrapper.setInstanceName(afterAggregation_LU2.getValue(), "afterAggregation_LU2");
		POJOWrapper.setInstanceName(vhu2.getValue(), "vhu2");
		POJOWrapper.setInstanceName(tu3.getValue(), "tu3");
		POJOWrapper.setInstanceName(vhu3.getValue(), "vhu3");
		POJOWrapper.setInstanceName(tu4.getValue(), "tu4");
		POJOWrapper.setInstanceName(vhu4.getValue(), "vhu4");
		POJOWrapper.setInstanceName(tu5.getValue(), "tu5");
		POJOWrapper.setInstanceName(vhu5.getValue(), "vhu5");
		POJOWrapper.setInstanceName(tu6.getValue(), "tu6");
		POJOWrapper.setInstanceName(vhu6.getValue(), "vhu6");

		// System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml("allSplitHUs", allSplitHUs)));

		//
		// Validate Shipment Schedule assignments for splitHU's VHUs
		//@formatter:off
		afterAggregation_ShipmentScheduleQtyPickedExpectations = new ShipmentScheduleQtyPickedExpectations()
			.newShipmentScheduleQtyPickedExpectation() // 0 - Tomato
				.shipmentSchedule(shipmentSchedule1)
				.lu(afterAggregation_LU1).tu(vhu1).vhu(vhu1).qtyPicked("30")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 2 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu3).vhu(vhu3).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 3 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu4).vhu(vhu4).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 4 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu5).vhu(vhu5).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 5 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu6).vhu(vhu6).qtyPicked("10")
				.endExpectation()

			// this is the expectation which covers the aggregate VHU of the 2nd LU.
			.newShipmentScheduleQtyPickedExpectation() // 1 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(vhu2).vhu(vhu2).qtyPicked("10")
				.endExpectation()
			.assertExpected("after loading on palette(s)");
		//@formatter:on
	}

	@Override
	protected void step40_addAggregatedHUsToShipperTransportation()
	{
		super.step40_addAggregatedHUsToShipperTransportation();
	}

	@Override
	protected void step50_GenerateShipment()
	{
		super.step50_GenerateShipment();
	}

	@Override
	protected void step50_GenerateShipment_validateGeneratedShipments()
	{
		//
		// Get generated shipment
		assertThat(generatedShipments).as("Invalid generated shipments count").hasSize(1);
		final I_M_InOut shipment = generatedShipments.get(0);

		//
		// Retrieve generated shipment lines
		final List<I_M_InOutLine> shipmentLines = Services.get(IInOutDAO.class).retrieveLines(shipment);
		assertThat(shipmentLines).as("Invalid generated shipment lines count").hasSize(2);
		final I_M_InOutLine shipmentLine1 = shipmentLines.get(0);
		logger.info("shipmentLine1: {}", shipmentLine1);

		final I_M_InOutLine shipmentLine2 = shipmentLines.get(1);
		logger.info("shipmentLine2: {}", shipmentLine2);

		//
		// Revalidate the ShipmentSchedule_QtyPicked expectations,
		// but this time, also make sure the M_InOutLine_ID is set
		//@formatter:off
		afterAggregation_ShipmentScheduleQtyPickedExpectations
			.shipmentScheduleQtyPickedExpectation(0)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(1)
			.inoutLine(shipmentLine2)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(2)
				.inoutLine(shipmentLine2)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(3)
				.inoutLine(shipmentLine2)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(4)
				.inoutLine(shipmentLine2)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(5)
				.inoutLine(shipmentLine2)
				.endExpectation()
			.assertExpected("after shipment generated");
		//@formatter:on
	}

	@Override
	protected void step50_GenerateShipment_validateShipperTransportationAfterShipment()
	{
		//
		// Get LUs Package
		assertThat(mpackagesForAggregatedHUs.size()).as("Invalid generated Aggregated HU packages count").isEqualTo(2);

		assertThat(mpackagesForAggregatedHUs.get(0).getPackageWeight()).isEqualByComparingTo("30");
		assertThat(mpackagesForAggregatedHUs.get(1).getPackageWeight()).isEqualByComparingTo("50");

		for (int i = 0; i < generatedShipments.size(); i++)
		{
			final I_M_Package mpackage_AggregatedHU = mpackagesForAggregatedHUs.get(i);

			//
			// Get generated shipment
			final I_M_InOut shipment = generatedShipments.get(i);

			//
			// Shipper Transportation: Make sure LU's M_Package is updated
			{
				InterfaceWrapperHelper.refresh(mpackage_AggregatedHU);
				assertThat(mpackage_AggregatedHU.getM_InOut_ID()).as("Aggregated HU's M_Package does not have the right M_InOut_ID").isEqualTo(shipment.getM_InOut_ID());
			}
		}
	}
}
