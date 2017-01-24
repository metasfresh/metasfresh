package de.metas.handlingunits.model.validator;

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

import java.util.Arrays;
import java.util.Properties;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.mm.attributes.spi.impl.WeightGenerateHUTrxListener;
import org.adempiere.ui.api.IGridTabSummaryInfoFactory;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.compiere.apps.search.dao.IInvoiceHistoryDAO;
import org.compiere.apps.search.dao.impl.HUInvoiceHistoryDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.gui.search.impl.HUOrderFastInputHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.ddorder.spi.impl.DDOrderLineHUDocumentHandler;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.invoicecandidate.facet.C_Invoice_Candidate_HUPackingMaterials_FacetCollector;
import de.metas.handlingunits.invoicecandidate.ui.spi.impl.HUC_Invoice_Candidate_GridTabSummaryInfoProvider;
import de.metas.handlingunits.materialtracking.impl.QualityInspectionWarehouseDestProvider;
import de.metas.handlingunits.materialtracking.spi.impl.HUDocumentLineLineMaterialTrackingListener;
import de.metas.handlingunits.materialtracking.spi.impl.HUHandlingUnitsInfoFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.ordercandidate.spi.impl.OLCandPIIPListener;
import de.metas.handlingunits.ordercandidate.spi.impl.OLCandPIIPValidator;
import de.metas.handlingunits.pporder.api.impl.PPOrderBOMLineHUTrxListener;
import de.metas.handlingunits.pricing.spi.impl.OrderLinePricingHUDocumentHandler;
import de.metas.handlingunits.pricing.spi.impl.OrderPricingHUDocumentHandler;
import de.metas.handlingunits.receiptschedule.impl.HUReceiptScheduleListener;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUDocumentFactory;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUTrxListener;
import de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleInvalidateBL;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleHUTrxListener;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentSchedulePackingMaterialLineListener;
import de.metas.handlingunits.tourplanning.spi.impl.HUShipmentScheduleDeliveryDayHandler;
import de.metas.inoutcandidate.agg.key.impl.HUShipmentScheduleKeyValueHandler;
import de.metas.inoutcandidate.api.IInOutCandHandlerBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.spi.impl.HUReceiptScheduleProducer;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.facet.IInvoiceCandidateFacetCollectorFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IC_OrderLine_HandlerDAO;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;
import de.metas.order.process.IC_Order_CreatePOFromSOsBL;
import de.metas.order.process.IC_Order_CreatePOFromSOsDAO;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandValdiatorBL;
import de.metas.storage.IStorageEngineService;
import de.metas.tourplanning.api.IDeliveryDayBL;

public final class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);

		registerFactories();
		registerServices();

		registerTabCallouts(Services.get(ITabCalloutFactory.class));

		registerHUSSAggregationKeyDependencies();

		//
		// Setup caching
		setupTableCacheConfig();

		//
		// Register model validators
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Version(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Item(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.C_OrderLine(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.DD_Order(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.DD_OrderLine(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_InOut(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Item_Product(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.C_Order(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.C_Order_Line_Alloc(), client);
		engine.addModelValidator(de.metas.handlingunits.model.validator.M_Movement.instance, client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_Attribute(), client);
		engine.addModelValidator(de.metas.handlingunits.model.validator.M_HU_Storage.INSTANCE, client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_Assignment(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_LUTU_Configuration(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_Product(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_ProductPrice_Attribute(), client);

		//
		// M_Package integration
		engine.addModelValidator(new M_ShippingPackage(), client);

		engine.addModelValidator(new M_InOutLine(), client);
		engine.addModelValidator(new C_Invoice_Line_Alloc(), client);
		engine.addModelValidator(new C_Invoice_Candidate(), client);
		engine.addModelValidator(new M_PickingSlot_HU(), client);
		engine.addModelValidator(new C_InvoiceLine(), client);

		// 06833: M_ReceiptSchedule destruction shall automatically destroy HUs
		engine.addModelValidator(new de.metas.handlingunits.receiptschedule.model.validator.M_ReceiptSchedule(), client);

		// 09502: sync material tracking changes with already created planning-HUs
		engine.addModelValidator(new de.metas.handlingunits.materialtracking.model.validator.M_ReceiptSchedule(), client);

		//
		// 08743: M_ShipperTransportation
		engine.addModelValidator(new M_ShipperTransportation(), client);

		//
		// 08255: M_ShipmentSchedule update qtys
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.inoutcandidate.callout.M_ShipmentSchedule.instance);

		// Shipment
		engine.addModelValidator(new M_ShipmentSchedule(), client);
		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked(), client);
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.inout.callout.M_InOutLine.instance);
		// replace the default implementation with our own HU-aware one
		Services.registerService(IShipmentScheduleInvalidateBL.class, new HUShipmentScheduleInvalidateBL());

		// invoice line callout
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.callout.C_InvoiceLine.instance);

		//
		// Manufacturing
		engine.addModelValidator(new PP_Cost_Collector(), client);

		//
		// Tour Planning
		setupTourPlanning();

		//
		// Register GridTabSummaryInfo entries (08734) - override de.metas.swat implementation
		final IGridTabSummaryInfoFactory gridTabSummaryInfoFactory = Services.get(IGridTabSummaryInfoFactory.class);
		gridTabSummaryInfoFactory.register(I_C_Invoice_Candidate.Table_Name, new HUC_Invoice_Candidate_GridTabSummaryInfoProvider(), true); // forceOverride
	}

	public void setupTourPlanning()
	{
		// 07341: Register delivery day updater (for HU related fields)
		Services.get(IDeliveryDayBL.class)
				.registerDeliveryDayHandler(HUShipmentScheduleDeliveryDayHandler.instance);
	}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		//
		// Warm-up our cache
		// NOTE: We are calling this on user login and not "onInit" because after logout, cache is reseted.
		// On server side, it's not so important to warm-up cache, because it will be warmed up much more quickly
		cacheWarmUp();
	}

	private void setupTableCacheConfig()
	{
		final IModelCacheService cachingService = Services.get(IModelCacheService.class);

		// master data
		cachingService.addTableCacheConfig(I_M_HU_PI.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Version.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Item.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Item_Product.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Attribute.class);
		cachingService.addTableCacheConfig(I_M_HU_PackingMaterial.class);

		//
		// Setup tables for InTransaction only caching
		for (final String tableName : Arrays.asList(
				I_M_HU.Table_Name, I_M_HU_Storage.Table_Name, I_M_HU_Item.Table_Name, I_M_HU_Item_Storage.Table_Name, I_M_HU_Attribute.Table_Name))
		{
			cachingService.createTableCacheConfigBuilder(tableName)
					.setEnabled(true)
					.setTrxLevel(TrxLevel.InTransactionOnly)
					.registerIfAbsent();
		}
	}

	/**
	 * Register handling unit specific factories, builders etc
	 *
	 * NOTE: we are doing it in a separate method because we are calling this method in JUnit tests too
	 */
	public void registerFactories()
	{
		//
		// de.metas.storage: Register HU storage engine (07991)
		Services.get(IStorageEngineService.class)
				.registerStorageEngine(de.metas.storage.spi.hu.impl.HUStorageEngine.instance);

		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		//
		// aggregate material items
		{
			huTrxBL.addListener(de.metas.handlingunits.allocation.spi.impl.PackingMaterialItemTrxListener.INSTANCE);
		}
		
		//
		// Weights Attributes
		{
			huTrxBL.addListener(WeightGenerateHUTrxListener.instance);
		}

		//
		// Receipt schedule
		{
			Services.get(IReceiptScheduleProducerFactory.class)
					.registerProducer(I_C_OrderLine.Table_Name, HUReceiptScheduleProducer.class)
					.registerWarehouseDestProvider(QualityInspectionWarehouseDestProvider.instance);
			Services.get(IReceiptScheduleBL.class)
					.addReceiptScheduleListener(HUReceiptScheduleListener.instance);
			Services.get(IHUDocumentFactoryService.class)
					.registerHUDocumentFactory(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.Table_Name, new ReceiptScheduleHUDocumentFactory());

			huTrxBL.addListener(ReceiptScheduleHUTrxListener.instance);
		}

		//
		// Shipment Schedule
		{
			huTrxBL.addListener(ShipmentScheduleHUTrxListener.instance);

			// 07042: we don't want shipment schedules for mere packaging order lines
			Services.get(IInOutCandHandlerBL.class)
					.registerListener(new ShipmentSchedulePackingMaterialLineListener(), I_C_OrderLine.Table_Name);
		}

		//
		// Manufacturing
		{
			huTrxBL.addListener(PPOrderBOMLineHUTrxListener.instance);
		}

		// Order - Fast Input
		{
			OrderFastInput.addOrderFastInputHandler(new HUOrderFastInputHandler());
		}

		// 06040 - Add handler for default pricing
		{
			Services.get(IHUDocumentHandlerFactory.class).registerHandler(I_C_Order.Table_Name, OrderPricingHUDocumentHandler.class);
			Services.get(IHUDocumentHandlerFactory.class).registerHandler(I_C_OrderLine.Table_Name, OrderLinePricingHUDocumentHandler.class);
		}

		// 07255 - Add handler for Distribution Orders
		{
			Services.get(IHUDocumentHandlerFactory.class).registerHandler(I_DD_OrderLine.Table_Name, DDOrderLineHUDocumentHandler.class);
		}

		final EqualsQueryFilter<I_C_OrderLine> excludePackageOrderLinesFilter = new EqualsQueryFilter<I_C_OrderLine>(de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_IsPackagingMaterial, false);

		// task 07242: don't create invoice candidates for packaging order lines
		{
			Services.get(IC_OrderLine_HandlerDAO.class).addAdditionalFilter(excludePackageOrderLinesFilter);
		}

		// task 09557: don't attempt to create purchase order lines from packaging sale order lines
		// note: the registerListener() invocation is also there to cover task 07221
		{
			Services.get(IC_Order_CreatePOFromSOsDAO.class).addAdditionalOrderLinesFilter(excludePackageOrderLinesFilter);
			Services.get(IC_Order_CreatePOFromSOsBL.class).registerListener(new de.metas.handlingunits.order.process.spi.impl.HUC_Order_CreatePOFromSOsListener());
		}

		// task 08147: validate if the C_OLCand's PIIP is OK
		Services.get(IOLCandValdiatorBL.class).registerValidator(new OLCandPIIPValidator());

		// task 08839:
		Services.get(IOLCandBL.class).registerOLCandListener(new OLCandPIIPListener());

		//
		// Invoice candidates facets collector
		Services.get(IInvoiceCandidateFacetCollectorFactory.class)
				.registerFacetCollectorClass(C_Invoice_Candidate_HUPackingMaterials_FacetCollector.class);

		//
		// Material tracking
		Services.get(IMaterialTrackingBL.class).addModelTrackingListener(I_C_OrderLine.Table_Name, HUDocumentLineLineMaterialTrackingListener.INSTANCE); // task 09106
		Services.get(IMaterialTrackingBL.class).addModelTrackingListener(I_M_InOutLine.Table_Name, HUDocumentLineLineMaterialTrackingListener.INSTANCE); // task 09106
	}

	private void registerServices()
	{
		//
		// 07085 - Set storage for invoice history and apply filters
		{
			Services.registerService(IInvoiceHistoryDAO.class, new HUInvoiceHistoryDAO());
		}

		// Material tracking (07371)
		Services.registerService(IHandlingUnitsInfoFactory.class, new HUHandlingUnitsInfoFactory());

		// task 09533
		Services.registerService(IPPOrderMInOutLineRetrievalService.class, new de.metas.handlingunits.materialtracking.spi.impl.PPOrderMInOutLineRetrievalService());
	}

	/**
	 * Public for testing purposes only!
	 */
	public static void registerHUSSAggregationKeyDependencies()
	{
		final IAggregationKeyRegistry keyRegistry = Services.get(IAggregationKeyRegistry.class);

		final String registrationKey = HUShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY;

		//
		// Register Handlers
		keyRegistry.registerAggregationKeyValueHandler(registrationKey, new HUShipmentScheduleKeyValueHandler());
	}

	/**
	 * Warm up our cache.
	 *
	 * This method will be executed in a separate thread to not affect system start-up.
	 *
	 * @see #cacheWarmUpNow();
	 */
	private void cacheWarmUp()
	{
		final Runnable runnable = new Runnable()
		{

			@Override
			public void run()
			{
				cacheWarmUpNow();
			}
		};

		final Thread thread = new Thread(runnable, getClass().getName() + "-CacheWarmUp");
		thread.start();
	}

	/**
	 * Do some DAO retrievals in order to warm up our cache.
	 */
	private void cacheWarmUpNow()
	{
		final Properties ctx = Env.getCtx();
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUPIAttributesDAO huAttributesDAO = Services.get(IHUPIAttributesDAO.class);

		//
		// Retrieve all HU PI's and:
		// * fetch their PI Items
		// * fetch their PI Attributes
		// NOTE: we assume there are not so many HU PIs
		for (final I_M_HU_PI huPI : handlingUnitsDAO.retrieveAvailablePIs(ctx))
		{
			{
				final I_M_HU_PI_Version piVersionCurrent = handlingUnitsDAO.retrievePICurrentVersionOrNull(huPI);
				if (piVersionCurrent != null)
				{
					piVersionCurrent.getM_HU_PI(); // shall do nothing because this reference was already set... but just to be sure
				}
			}

			final I_C_BPartner bpartner = null; // all BPs
			for (final I_M_HU_PI_Item piItem : handlingUnitsDAO.retrievePIItems(huPI, bpartner))
			{
				piItem.getM_HU_PI_Version(); // shall do nothing because this reference was already set... but just to be sure
				piItem.getC_BPartner();
				piItem.getIncluded_HU_PI();
				piItem.getM_HU_PackingMaterial();
			}

			for (final I_M_HU_PI_Attribute piAttribute : huAttributesDAO.retrievePIAttributes(huPI))
			{
				piAttribute.getM_HU_PI_Version(); // shall do nothing because this reference was already set... but just to be sure
				piAttribute.getAggregationStrategy_JavaClass();
				piAttribute.getHU_TansferStrategy_JavaClass();
				piAttribute.getSplitterStrategy_JavaClass();
				piAttribute.getM_Attribute();
			}
		}
	}
}
