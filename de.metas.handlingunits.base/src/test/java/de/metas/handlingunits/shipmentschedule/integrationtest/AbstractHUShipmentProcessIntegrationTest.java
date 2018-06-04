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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
import static de.metas.business.BusinessTestHelper.createBPartner;
import static de.metas.business.BusinessTestHelper.createBPartnerLocation;
import static de.metas.business.BusinessTestHelper.createWarehouse;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_AD_User;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
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
import de.metas.handlingunits.shipmentschedule.api.HUShippingFacade;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.logging.LogManager;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.NonNull;

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
		return new HUTestHelper()
		{
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

		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(OrderLineShipmentScheduleHandler.class);

		// Prepare context
		final String trxName = helper.trxName; // use the helper's thread-inherited trxName

		if (Services.get(ITrxManager.class).isNull(trxName))
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
		bpartner = createBPartner("BPartner01");
		bpartnerLocation = createBPartnerLocation(bpartner);

		//
		// Warehouse & Locator
		warehouse = createWarehouse("Warehouse01");

		//
		// Handling Units Definition
		piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			// PM
			// don't create the PM item, because if we do, the HUPackingPaterialsCollector will try to do its thing.
			// this won't work, unless we also give each HU a locator and set up a distribution network
			// helper.createHU_PI_Item_PackingMaterial(piTU, pmIFCO);

			// MI
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
			shipper = newInstance(I_M_Shipper.class, helper.getContextProvider());
			save(shipper);
			shipperTransportation = newInstance(I_M_ShipperTransportation.class, helper.getContextProvider());
			shipperTransportation.setM_Shipper(shipper);
			save(shipperTransportation);
		}

		// allow subclasses to set up the attribute config for their test case
		final IShipmentScheduleHandlerBL shipmentScheduleHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);
		assertThat(shipmentScheduleHandlerBL).as("this rest requires a particular instance").isInstanceOf(ShipmentScheduleHandlerBL.class);

		final I_M_IolCandHandler handlerRecord = ((ShipmentScheduleHandlerBL)shipmentScheduleHandlerBL)
				.retrieveHandlerRecordOrNull(OrderLineShipmentScheduleHandler.class.getName());
		assertThat(handlerRecord).isNotNull(); // should have been registered by super.initialize();
		
		//
		// Create doctype
		final I_C_DocType docType = newInstanceOutOfTrx(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialDelivery);
		save(docType);

		initializeAttributeConfig(handlerRecord);
	}

	protected void initializeAttributeConfig(@NonNull final I_M_IolCandHandler handlerRecord)
	{
		// nothing
	}

	@Test
	public void test()
	{
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
		final HUShippingFacade huShippingFacade = HUShippingFacade.builder()
				.hus(afterAggregation_HUs)
				.build();

		//
		// Important!
		//
		// When matching expectations, sort the candidates so that they have the same indexes as the aggregated HUs
		//
		final List<ShipmentScheduleWithHU> candidatesSorted = new ArrayList<>(huShippingFacade.getCandidates());
		Collections.sort(candidatesSorted, new Comparator<ShipmentScheduleWithHU>()
		{
			@Override
			public int compare(final ShipmentScheduleWithHU schedWithHU1, final ShipmentScheduleWithHU schedWithHU2)
			{
				final int index1 = getIndex(schedWithHU1);
				final int index2 = getIndex(schedWithHU2);

				//
				// Keep order of original aggregated HUs
				return index1 - index2;
			}

			private int getIndex(final ShipmentScheduleWithHU schedWithHU)
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
		
		//
		// Make sure the current user is configured to receive notifications
		final I_AD_User user = newInstance(I_AD_User.class);
		user.setAD_User_ID(0);
		user.setNotificationType(X_AD_User.NOTIFICATIONTYPE_Notice);
		save(user);
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, user.getAD_User_ID());


		// Generate shipments
		huShippingFacade.generateShippingDocuments();

		//
		// Retrieve generated shipments
		generatedShipments = huShippingFacade.getGeneratedShipments();

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
	protected final I_M_ShipmentSchedule createShipmentSchedule(
			final boolean newC_Order,
			final I_M_Product product,
			final I_C_UOM productUOM,
			final BigDecimal qtyOrdered)
	{
		final I_C_Order order;
		if (!newC_Order)
		{
			Check.assumeNotNull(lastOrder, "lastOrder not null; first shipment schedule shall reference a new order");
			order = lastOrder;
		}
		else
		{
			order = newInstance(I_C_Order.class, helper.getContextProvider());
			order.setC_BPartner(bpartner);
			order.setC_BPartner_Location(bpartnerLocation);
			order.setM_Warehouse(warehouse);
			save(order);

			lastOrder = order;
		}

		// FIXME: introduce M_ShipmentSchedule.C_UOM_ID
		// See http://dewiki908/mediawiki/index.php/05565_Introduce_M_ShipmentSchedule.C_UOM_ID_%28107483088069%29
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class, helper.getContextProvider());
		orderLine.setM_Product(product);
		orderLine.setC_UOM(productUOM);
		orderLine.setQtyOrdered(qtyOrdered);
		save(orderLine);

		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class, helper.getContextProvider());
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
		shipmentSchedule.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_OrderLine.Table_Name));
		shipmentSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());

		save(shipmentSchedule);
		return shipmentSchedule;
	}
}
