package de.metas.handlingunits.shipmentschedule.integrationtest;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.junit.Assert;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.impl.CachedHUAndItemsDAO;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inout.model.I_M_InOut;
import de.metas.logging.LogManager;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.model.I_M_ShipperTransportation;

/**
 * HU Shipment Process:
 * <ul>
 * <li>picking: TUs are created
 * <li>aggregation: TUs are moved to LUs. But this is not necesary.
 * <li>shipper transportation: add aggregated HUs shipper transportation document
 * <li>generate shipment
 * <li>shipper transportation: make sure M_Packages are updated correctly after shipment
 * <li>
 * </ul>
 *
 * @author tsa
 *
 */
public abstract class AbstractHUShipmentProcessIntegrationTest extends AbstractHUTest
{
	// Services
	// private IHUShipmentScheduleBL huShipmentScheduleBL;
	// private IHUShipmentScheduleDAO huShipmentScheduleDAO;
	protected IHUShipperTransportationBL huShipperTransportationBL;
	protected IHUPackageDAO huPackageDAO;

	// Config: Product
	protected I_M_Product product;
	protected I_C_UOM productUOM;

	// Config: BPartner
	protected I_C_BPartner bpartner;
	protected I_C_BPartner_Location bpartnerLocation;

	// Config: Warehouse
	protected I_M_Warehouse warehouse;

	// Config: LU/TU config
	protected I_M_HU_PI piLU;
	protected I_M_HU_PI_Item piTU_Item;
	protected I_M_HU_PI piTU;
	protected I_M_HU_PI_Item piLU_Item;

	// Config: Shipper Transportation
	protected I_M_Shipper shipper;

	// Masterdata: Shipper Transportation
	protected I_M_ShipperTransportation shipperTransportation;

	//
	//
	// Test methods
	//
	//

	protected IHUContext huContext;
	protected IHUStorageFactory huStorageFactory;
	protected IAttributeStorageFactory attributeStorageFactory;

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper() {
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None;
			}

		};
	}

	@Override
	protected void initialize()
	{
		LogManager.setLevel(Level.WARN); // reset the log level. other tests might have set it to trace, which might bring a giant performance penalty.

		//
		// Prepare context
		final String trxName = helper.trxName; // use the helper's thread-inherited trxName

		if(Services.get(ITrxManager.class).isNull(trxName))
		{
			huContext = helper.createMutableHUContextOutOfTransaction();
		}
		else
		{
			huContext = helper.createMutableHUContextForProcessing(trxName);
		}
		huStorageFactory = huContext.getHUStorageFactory();
		attributeStorageFactory = huContext.getHUAttributeStorageFactory();

		//
		// TODO HU Join does not work for some reason if it's true; see why...
		CachedHUAndItemsDAO.DEBUG = false;

		//
		// Product/UOM
		product = pTomato;
		productUOM = uomKg;

		//
		// BPartner
		bpartner = helper.createBPartner("BPartner01");
		bpartnerLocation = helper.createBPartnerLocation(bpartner);

		//
		// Warehouse & Locator
		warehouse = helper.createWarehouse("Warehouse01");

		//
		// Handling Units Definition
		piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			piTU_Item = helper.createHU_PI_Item_Material(piTU);
			helper.assignProduct(piTU_Item, pTomato, BigDecimal.TEN, productUOM);
			helper.assignProduct(piTU_Item, pSalad, BigDecimal.TEN, productUOM);
		}

		piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			piLU_Item = helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("2"));
		}

		//
		// Services
		// this.huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
		// this.huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
		huPackageDAO = Services.get(IHUPackageDAO.class);

		// Masterdata: Shipper Transportation
		{
			shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class, helper.getContextProvider());
			InterfaceWrapperHelper.save(shipper);
			shipperTransportation = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class, helper.getContextProvider());
			shipperTransportation.setM_Shipper(shipper);
			InterfaceWrapperHelper.save(shipperTransportation);
		}
	}

	@Test
	public void test()
	{
		// Assume.assumeFalse("Skipping this test because fresh_QuickShipment flag is active.", HUConstants.isfresh_QuickShipment());

		//
		// Create shipment schedules
		shipmentSchedules = null;
		step10_createShipmentSchedules();
		Check.assumeNotNull(shipmentSchedules, "shipmentSchedules not null");

		//
		// Pick TUs
		step20_pickTUs();
		Check.assumeNotNull(afterPick_HUExpectations, "afterPick_HUExpectations not null");

		//
		// Aggregate TUs to LUs
		step30_aggregateHUs();
		Check.assumeNotNull(afterAggregation_HUExpectations, "afterAggregation_HUExpectations not null");
		Check.assumeNotNull(afterAggregation_ShipmentScheduleQtyPickedExpectations, "afterAggregation_ShipmentScheduleQtyPickedExpectations not null");

		//
		// Add Aggregated HUs to shipper transportation
		step40_addAggregatedHUsToShipperTransportation();
		Check.assumeNotNull(mpackagesForAggregatedHUs, "mpackagesForHUs not null");

		//
		// Generate shipment
		step50_GenerateShipment();
		Check.assumeNotNull(generatedShipments, "generatedShipments not null");
	}

	//
	// Step 10
	protected List<I_M_ShipmentSchedule> shipmentSchedules;

	/**
	 * Creates initial {@link #shipmentSchedules}).
	 */
	protected abstract void step10_createShipmentSchedules();

	//
	// Step 20
	protected HUsExpectation afterPick_HUExpectations;

	/**
	 * Picks some TUs ({@link #afterPick_HUExpectations}) from {@link #shipmentSchedules}).
	 */
	protected abstract void step20_pickTUs();

	//
	// Step 30
	protected HUsExpectation afterAggregation_HUExpectations;
	protected ShipmentScheduleQtyPickedExpectations afterAggregation_ShipmentScheduleQtyPickedExpectations;

	/**
	 * Aggregates Picked TUs ({@link #afterAggregation_HUExpectations}) and creates Aggregated HUs ({@link #afterAggregation_HUExpectations}).
	 *
	 * NOTE: in most of the cases they are LUs but not necesary.
	 *
	 * Also allocates the aggregated HUs to original shipment schedules ({@link #afterAggregation_ShipmentScheduleQtyPickedExpectations}).
	 */
	protected abstract void step30_aggregateHUs();

	//
	// Step 40
	protected List<I_M_Package> mpackagesForAggregatedHUs = null;

	/**
	 * Adds Aggregated HUs ({@link #afterAggregation_HUExpectations}) to {@link #shipperTransportation}.
	 *
	 * Resulting packages will be added to {@link #mpackagesForAggregatedHUs}.
	 */
	protected void step40_addAggregatedHUsToShipperTransportation()
	{
		//
		// Get Aggregated HUs
		final List<I_M_HU> afterAggregation_HUs = afterAggregation_HUExpectations.getCapturedHUs();

		//
		// Iterate HUs and valid them
		mpackagesForAggregatedHUs = new ArrayList<>();
		for (final I_M_HU afterAggregation_HU : afterAggregation_HUs)
		{
			//
			// Add our aggregated HU to Shipper Transportation
			Assert.assertTrue("HU is not eligible for shipper transportation: " + afterAggregation_HU,
					huShipperTransportationBL.isEligibleForAddingToShipperTransportation(afterAggregation_HU));
			huShipperTransportationBL
					.addHUsToShipperTransportation(
							helper.getContextProvider(),
							shipperTransportation.getM_ShipperTransportation_ID(),
							Collections.singletonList(afterAggregation_HU));

			//
			// Make sure M_Package was created and added to shipper transportation
			final I_M_Package mpackage_AggregatedHU = huPackageDAO.retrievePackage(afterAggregation_HU);
			Assert.assertNotNull("M_Package not created for Aggregated HU: " + afterAggregation_HU, mpackage_AggregatedHU);

			mpackagesForAggregatedHUs.add(mpackage_AggregatedHU);
		}
	}

	//
	// Step 50
	protected List<I_M_InOut> generatedShipments = null;

	/**
	 * Generates {@link #generatedShipments} from Aggregated HUs ({@link #afterAggregation_HUExpectations}).
	 *
	 * Validates if {@link #mpackagesForAggregatedHUs} were correctly updated.
	 */
	protected void step50_GenerateShipment()
	{
		// Generate shipments
		step50_GenerateShipment_generateShipmentsFromAggregatedHUs();

		// Validate generated shipments
		step50_GenerateShipment_validateGeneratedShipments();

		// Make sure shipper transportation was updated after shipment
		step50_GenerateShipment_validateShipperTransportationAfterShipment();
	}

	/**
	 * Generates {@link #generatedShipments} from aggregated HUs ({@link #afterAggregation_HUExpectations})
	 */
	protected void step50_GenerateShipment_generateShipmentsFromAggregatedHUs()
	{
		//
		// Get Aggregated LU
		final List<I_M_HU> afterAggregation_HUs = afterAggregation_HUExpectations.getCapturedHUs();

		//
		// Test Generate Shipment from HUs
		final I_C_Queue_WorkPackage workpackage = GenerateInOutFromHU.enqueueWorkpackage(helper.getCtx(), afterAggregation_HUs);

		// Make sure we are working with valid candidates
		final GenerateInOutFromHU processor = new GenerateInOutFromHU();
		final Iterator<IShipmentScheduleWithHU> candidates = processor.retrieveCandidates(huContext, workpackage, ITrx.TRXNAME_None);

		//
		// Important!
		//
		// When matching expectations, sort the candidates so that they have the same indexes as the aggregated HUs
		//
		final List<IShipmentScheduleWithHU> candidatesSorted = new ArrayList<IShipmentScheduleWithHU>();
		while (candidates.hasNext())
		{
			final IShipmentScheduleWithHU candidate = candidates.next();
			candidatesSorted.add(candidate);
		}
		Collections.sort(candidatesSorted, new Comparator<IShipmentScheduleWithHU>()
		{
			@Override
			public int compare(final IShipmentScheduleWithHU schedWithHU1, final IShipmentScheduleWithHU schedWithHU2)
			{
				final int index1 = getIndex(schedWithHU1);
				final int index2 = getIndex(schedWithHU2);

				//
				// Keep order of original aggregated HUs
				return index1 - index2;
			}

			private int getIndex(final IShipmentScheduleWithHU schedWithHU)
			{
				int index = afterAggregation_HUs.indexOf(schedWithHU.getM_LU_HU());
				if (index < 0)
				{
					index = afterAggregation_HUs.indexOf(schedWithHU.getM_TU_HU());
				}
				if (index < 0)
				{
					index = afterAggregation_HUs.indexOf(schedWithHU.getVHU());
				}
				return index;
			}
		});

		afterAggregation_ShipmentScheduleQtyPickedExpectations
				.assertExpected_ShipmentScheduleWithHUs("after split IShipmentScheduleWithHU candidates", candidatesSorted);

		final InOutGeneratedNotificationChecker notificationsChecker = InOutGeneratedNotificationChecker.createAnSubscribe();

		// Process the workpackage
		// => shipment shall be generated
		processor.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance); // fail on error
		processor.processWorkPackage(workpackage, ITrx.TRXNAME_None);

		//
		// Retrieve generated shipment
		generatedShipments = processor.getInOutGenerateResult().getInOuts();

		// Assert all generated shipments were also notified on generated inouts event bus
		notificationsChecker.assertAllNotified(generatedShipments);
	}

	protected abstract void step50_GenerateShipment_validateGeneratedShipments();

	protected abstract void step50_GenerateShipment_validateShipperTransportationAfterShipment();

	//
	//
	// Helper methods:
	//
	//

	/**
	 * Creates a new shipment schedule.
	 *
	 * @return shipment schedule
	 */
	protected final I_M_ShipmentSchedule createShipmentSchedule()
	{
		final BigDecimal qtyOrdered = new BigDecimal("100");
		return createShipmentSchedule(true, product, productUOM, qtyOrdered);
	}

	private I_C_Order lastOrder = null;

	/**
	 * Creates a new shipment schedule.
	 *
	 * @return shipment schedule
	 */
	protected final I_M_ShipmentSchedule createShipmentSchedule(final boolean newC_Order, final I_M_Product product, final I_C_UOM productUOM, final BigDecimal qtyOrdered)
	{
		final I_C_Order order;
		if (!newC_Order)
		{
			Check.assumeNotNull(lastOrder, "lastOrder not null; first shipment schedule shall reference a new order");
			order = lastOrder;
		}
		else
		{
			order = InterfaceWrapperHelper.newInstance(I_C_Order.class, helper.getContextProvider());
			order.setC_BPartner(bpartner);
			order.setC_BPartner_Location(bpartnerLocation);
			order.setM_Warehouse(warehouse);
			InterfaceWrapperHelper.save(order);

			lastOrder = order;
		}

		// FIXME: introduce M_ShipmentSchedule.C_UOM_ID
		// See http://dewiki908/mediawiki/index.php/05565_Introduce_M_ShipmentSchedule.C_UOM_ID_%28107483088069%29
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, helper.getContextProvider());
		orderLine.setM_Product(product);
		orderLine.setC_UOM(productUOM);
		orderLine.setQtyOrdered(qtyOrdered);
		InterfaceWrapperHelper.save(orderLine);

		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, helper.getContextProvider());
		// BPartner
		shipmentSchedule.setC_BPartner(bpartner);
		shipmentSchedule.setC_BPartner_Location(bpartnerLocation);
		// Product/UOM and Qty
		shipmentSchedule.setM_Product(product);
		shipmentSchedule.setC_UOM(productUOM);
		shipmentSchedule.setQtyOrdered_Calculated(qtyOrdered);
		// Warehouse
		shipmentSchedule.setM_Warehouse(warehouse);
		// Order line link
		shipmentSchedule.setC_OrderLine(orderLine);

		InterfaceWrapperHelper.save(shipmentSchedule);
		return shipmentSchedule;
	}
}
