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

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.ad.dao.cache.ITableCacheConfigBuilder;
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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.CacheMgt;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.gui.search.impl.HUOrderFastInputHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.ddorder.spi.impl.DDOrderLineHUDocumentHandler;
import de.metas.handlingunits.ddorder.spi.impl.ForecastLineHUDocumentHandler;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.invoicecandidate.facet.C_Invoice_Candidate_HUPackingMaterials_FacetCollector;
import de.metas.handlingunits.invoicecandidate.ui.spi.impl.HUC_Invoice_Candidate_GridTabSummaryInfoProvider;
import de.metas.handlingunits.materialtracking.impl.QualityInspectionWarehouseDestProvider;
import de.metas.handlingunits.materialtracking.spi.impl.HUDocumentLineLineMaterialTrackingListener;
import de.metas.handlingunits.materialtracking.spi.impl.HUHandlingUnitsInfoFactory;
import de.metas.handlingunits.model.I_M_ForecastLine;
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
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.picking.interceptor.M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.pricing.spi.impl.HUPricing;
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
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.spi.impl.HUReceiptScheduleProducer;
import de.metas.invoicecandidate.facet.IInvoiceCandidateFacetCollectorFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;
import de.metas.order.invoicecandidate.IC_OrderLine_HandlerDAO;
import de.metas.order.process.IC_Order_CreatePOFromSOsBL;
import de.metas.order.process.IC_Order_CreatePOFromSOsDAO;
import de.metas.pricing.ProductPrices;
import de.metas.pricing.attributebased.impl.AttributePricing;
import de.metas.storage.IStorageEngineService;
import de.metas.tourplanning.api.IDeliveryDayBL;
import lombok.NonNull;

public final class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);
		
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);

		registerFactories();
		registerServices();

		registerTabCallouts(Services.get(ITabCalloutFactory.class));

		registerHUSSAggregationKeyDependencies();

		setupPricing();

		//
		// Register model validators
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Version(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Item(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.C_OrderLine(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.DD_Order(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.DD_OrderLine(), client);
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
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_ProductPrice(), client);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_ForecastLine(), client);

		engine.addModelValidator(de.metas.handlingunits.hutransaction.interceptor.M_HU.INSTANCE, client);

		// #484 HU tracing
		engine.addModelValidator(de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor.INSTANCE, client);

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

		engine.addModelValidator(de.metas.handlingunits.sourcehu.interceptor.M_HU.INSTANCE, client);

		engine.addModelValidator(de.metas.handlingunits.material.interceptor.M_Transaction.INSTANCE, client);

		//
		// 08255: M_ShipmentSchedule update qtys
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.inoutcandidate.callout.M_ShipmentSchedule.instance);

		// Shipment
		engine.addModelValidator(new M_ShipmentSchedule(), client);
		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked(), client);

		// Inventory
		engine.addModelValidator(new de.metas.handlingunits.inventory.interceptor.M_Inventory(), client);

		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.inout.callout.M_InOutLine.instance);
		// replace the default implementation with our own HU-aware one
		Services.registerService(IShipmentScheduleInvalidateBL.class, new HUShipmentScheduleInvalidateBL());

		// invoice line callout
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.callout.C_InvoiceLine.instance);

		//
		// Manufacturing
		engine.addModelValidator(new PP_Cost_Collector(), client);

		// https://github.com/metasfresh/metasfresh/issues/2298
		engine.addModelValidator(de.metas.handlingunits.picking.interceptor.M_HU.INSTANCE, client);

		//
		// Tour Planning
		setupTourPlanning();

		//
		// Register GridTabSummaryInfo entries (08734) - override de.metas.swat implementation
		final IGridTabSummaryInfoFactory gridTabSummaryInfoFactory = Services.get(IGridTabSummaryInfoFactory.class);
		gridTabSummaryInfoFactory.register(I_C_Invoice_Candidate.Table_Name, new HUC_Invoice_Candidate_GridTabSummaryInfoProvider(), true); // forceOverride
	}

	public static void setupPricing()
	{
		ProductPrices.registerMainProductPriceMatcher(HUPricing.HUPIItemProductMatcher_None);

		// Registers a default matcher to make sure that the AttributePricing ignores all product prices that have an M_HU_PI_Item_Product_ID set.
		//
		// From skype chat:
		// <pre>
		// [Dienstag, 4. Februar 2014 15:33] Cis:
		//
		// if the HU pricing rule (that runs first) doesn't find a match, the attribute pricing rule runs next and can find a wrong match, because it can't "see" the M_HU_PI_Item_Product
		// more concretely: we have two rules:
		// IFCO A, with Red
		// IFCO B with Blue
		//
		// And we put a product in IFCO A with Blue
		//
		// HU pricing rule won't find a match,
		// Attribute pricing rule will match it with "Blue", which is wrong, since it should fall back to the "base" productPrice
		//
		// <pre>
		// ..and that's why we register the filter here.
		//
		AttributePricing.registerDefaultMatcher(HUPricing.HUPIItemProductMatcher_None);
	}

	public void setupTourPlanning()
	{
		// 07341: Register delivery day updater (for HU related fields)
		Services.get(IDeliveryDayBL.class)
				.registerDeliveryDayHandler(HUShipmentScheduleDeliveryDayHandler.instance);
	}

	@Override
	protected void setupCaching(@NonNull final IModelCacheService cachingService)
	{
		// master data
		setupMasterDataCaching(cachingService);

		setupRemoteCaching();

		setupInTrxOnlyCaching(cachingService);
	}

	private void setupMasterDataCaching(final IModelCacheService cachingService)
	{
		cachingService.addTableCacheConfig(I_M_HU_PI.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Version.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Item.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Item_Product.class);
		cachingService.addTableCacheConfig(I_M_HU_PI_Attribute.class);
		cachingService.addTableCacheConfig(I_M_HU_PackingMaterial.class);
	}

	private void setupRemoteCaching()
	{
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_HU_PI.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_HU_PI_Version.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_HU_PI_Item.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_HU_PI_Item_Product.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_HU_PI_Attribute.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_HU_PackingMaterial.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_Source_HU.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_PP_Order_Qty.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_PickingSlot_HU.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_Picking_Candidate.Table_Name);
	}

	/**
	 * Setup tables for InTransaction only caching. see {@link ITableCacheConfigBuilder#setTrxLevel(TrxLevel)}.
	 *
	 * @param cachingService
	 */
	private void setupInTrxOnlyCaching(final IModelCacheService cachingService)
	{
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
			huTrxBL.addListener(de.metas.handlingunits.allocation.spi.impl.AggregateHUTrxListener.INSTANCE);
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
			Services.get(IShipmentScheduleHandlerBL.class)
					.registerVetoer(new ShipmentSchedulePackingMaterialLineListener(), I_C_OrderLine.Table_Name);
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

		// Add handler for Forecast
		{
			Services.get(IHUDocumentHandlerFactory.class).registerHandler(I_M_ForecastLine.Table_Name, ForecastLineHUDocumentHandler.class);
		}

		final EqualsQueryFilter<I_C_OrderLine> excludePackageOrderLinesFilter = new EqualsQueryFilter<>(de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_IsPackagingMaterial, false);

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
}
