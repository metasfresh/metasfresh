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

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.gui.search.impl.HUOrderFastInputHandler;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.IModelCacheService;
import de.metas.cache.model.ITableCacheConfig.TrxLevel;
import de.metas.cache.model.ITableCacheConfigBuilder;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.hu_spis.DDOrderLineHUDocumentHandler;
import de.metas.distribution.ddorder.hu_spis.ForecastLineHUDocumentHandler;
import de.metas.distribution.ddorder.interceptor.DD_Order;
import de.metas.distribution.ddorder.interceptor.DD_OrderLine;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inout.HuInOutInvoiceCandidateVetoer;
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
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.inoutcandidate.agg.key.impl.HUShipmentScheduleKeyValueHandler;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.inoutcandidate.spi.impl.HUReceiptScheduleProducer;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.facet.IInvoiceCandidateFacetCollectorFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsDAO;
import de.metas.order.invoicecandidate.IC_OrderLine_HandlerDAO;
import de.metas.storage.IStorageEngineService;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.mm.attributes.spi.impl.WeightGenerateHUTrxListener;
import org.adempiere.ui.api.IGridTabSummaryInfoFactory;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.compiere.apps.search.dao.IInvoiceHistoryDAO;
import org.compiere.apps.search.dao.impl.HUInvoiceHistoryDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_I_Inventory;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public final class Main extends AbstractModuleInterceptor
{
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	private final DDOrderService ddOrderService;
	private final PickingBOMService pickingBOMService;

	public Main(
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DDOrderService ddOrderService,
			@NonNull final PickingBOMService pickingBOMService)
	{
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.ddOrderService = ddOrderService;
		this.pickingBOMService = pickingBOMService;
	}

	@Override
	protected void registerInterceptors(@NonNull final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Version());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Item());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.C_OrderLine());
		engine.addModelValidator(new DD_Order(ddOrderMoveScheduleService, ddOrderService));
		engine.addModelValidator(new DD_OrderLine(ddOrderMoveScheduleService));
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_PI_Item_Product());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.C_Order());
		engine.addModelValidator(de.metas.handlingunits.model.validator.M_Movement.instance);
		engine.addModelValidator(de.metas.handlingunits.model.validator.M_HU.INSTANCE);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_Attribute());
		engine.addModelValidator(de.metas.handlingunits.model.validator.M_HU_Storage.INSTANCE);
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_Assignment());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_HU_LUTU_Configuration());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_Product());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_ProductPrice());
		engine.addModelValidator(new de.metas.handlingunits.model.validator.M_ForecastLine());

		engine.addModelValidator(de.metas.handlingunits.hutransaction.interceptor.M_HU.INSTANCE);

		// #484 HU tracing
		engine.addModelValidator(de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor.INSTANCE);

		// M_Package integration
		engine.addModelValidator(new M_ShippingPackage());

		engine.addModelValidator(new M_InOutLine());
		engine.addModelValidator(new C_Invoice_Line_Alloc());
		engine.addModelValidator(new C_Invoice_Candidate());
		engine.addModelValidator(new M_PickingSlot_HU());
		engine.addModelValidator(new C_InvoiceLine());

		// 06833: M_ReceiptSchedule destruction shall automatically destroy HUs
		engine.addModelValidator(new de.metas.handlingunits.receiptschedule.model.validator.M_ReceiptSchedule());

		// 09502: sync material tracking changes with already created planning-HUs
		engine.addModelValidator(new de.metas.handlingunits.materialtracking.model.validator.M_ReceiptSchedule());

		//
		// 08743: M_ShipperTransportation
		engine.addModelValidator(new M_ShipperTransportation());

		engine.addModelValidator(de.metas.handlingunits.sourcehu.interceptor.M_HU.INSTANCE);

		// engine.addModelValidator(de.metas.handlingunits.material.interceptor.M_Transaction.INSTANCE); // converted to spring component

		// Shipment
		// engine.addModelValidator(new M_ShipmentSchedule()); // now created&registered by spring
		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked());

		//
		// Manufacturing
		engine.addModelValidator(new PP_Cost_Collector());

		// https://github.com/metasfresh/metasfresh/issues/2298
		engine.addModelValidator(de.metas.handlingunits.picking.interceptor.M_HU.INSTANCE);
	}

	@Override
	protected void registerCallouts(@NonNull final IProgramaticCalloutProvider programaticCalloutProvider)
	{
		//
		// 08255: M_ShipmentSchedule update qtys
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.inoutcandidate.callout.M_ShipmentSchedule.instance);

		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.inout.callout.M_InOutLine.instance);

		// replace the default implementation with our own HU-aware one
		Services.registerService(IShipmentScheduleInvalidateBL.class, new HUShipmentScheduleInvalidateBL(pickingBOMService));

		// invoice line callout
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.handlingunits.callout.C_InvoiceLine.instance);
	}

	@Override
	protected void onAfterInit()
	{
		registerFactories();
		registerServices();

		registerTabCallouts(Services.get(ITabCalloutFactory.class));

		registerHUSSAggregationKeyDependencies();

		setupPricing();

		//
		// Tour Planning
		setupTourPlanning();

		//
		// Register GridTabSummaryInfo entries (08734) - override de.metas.swat implementation
		final IGridTabSummaryInfoFactory gridTabSummaryInfoFactory = Services.get(IGridTabSummaryInfoFactory.class);
		gridTabSummaryInfoFactory.register(I_C_Invoice_Candidate.Table_Name, new HUC_Invoice_Candidate_GridTabSummaryInfoProvider(), true); // forceOverride

		registerImportProcesses();
	}

	public static void setupPricing()
	{
		HUPricing.install();
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
	 * <p>
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

		//InOutLine
		{
			Services.get(IInvoiceCandBL.class)
					.registerVetoer(new HuInOutInvoiceCandidateVetoer(), I_M_InOutLine.Table_Name);
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

	private void registerImportProcesses()
	{
		final IImportProcessFactory importProcessesFactory = Services.get(IImportProcessFactory.class);
		importProcessesFactory.registerImportProcess(I_I_Inventory.class, de.metas.inventory.impexp.InventoryImportProcess.class);
	}
}
