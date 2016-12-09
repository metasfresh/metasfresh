package org.adempiere.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.ITableCacheConfig;
import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.mm.attributes.copyRecordSupport.CloneASIListener;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocTypeCounter;
import org.compiere.model.I_C_DocType_Sequence;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.CCache.CacheMapType;
import org.compiere.util.CacheMgt;

import de.metas.adempiere.model.I_M_DiscountSchemaBreak;
import de.metas.adempiere.model.I_M_Product;
import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.spi.impl.NotifyAsyncBatch;
import de.metas.event.EventBusAdempiereInterceptor;

/**
 * ADempiere Base Module Activator
 *
 * @author tsa
 *
 */
public final class AdempiereBaseValidator extends AbstractModuleInterceptor
{

	@Override
	protected void onAfterInit()
	{
		CopyRecordFactory.addOnRecordCopiedListener(new CloneASIListener());
		
		// 
		registerFactories();
	}

	public void registerFactories()
	{
		//
		// Register notifier
		Services.get(IAsyncBatchListeners.class).registerAsyncBatchNotifier(new NotifyAsyncBatch());
	}
	
	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		// Security and User/Roles
		{
			engine.addModelValidator(org.adempiere.ad.security.model.validator.SecurityMainInterceptor.instance, client);
		}

		// Event bus
		engine.addModelValidator(EventBusAdempiereInterceptor.instance, client);

		engine.addModelValidator(new org.adempiere.ad.callout.model.validator.AD_ColumnCallout(), client);
		engine.addModelValidator(new org.adempiere.model.validator.AD_InfoColumn(), client);
		engine.addModelValidator(new org.adempiere.server.rpl.model.validator.IMP_Processor(), client);

		engine.addModelValidator(new org.compiere.wf.model.validator.AD_Workflow(), client);

		engine.addModelValidator(new de.metas.javaclasses.model.interceptor.AD_JavaClass(), client); // 04599
		engine.addModelValidator(new de.metas.javaclasses.model.interceptor.AD_JavaClass_Type(), client); // 04599
		
		engine.addModelValidator(de.metas.process.model.interceptor.AD_Process.instance, client); // FRESH-727

		//
		// Currency
		{
			engine.addModelValidator(new de.metas.currency.model.interceptor.C_Conversion_Rate(), client);
		}

		//
		// Tax
		{
			engine.addModelValidator(new org.adempiere.tax.model.validator.C_Tax(), client);
		}

		//
		// Storage
		{
			engine.addModelValidator(new org.adempiere.model.validator.M_Transaction(), client);
		}

		//
		// fresh 08585: C_DocLine_Sort
		{
			engine.addModelValidator(new de.metas.adempiere.docline.sort.model.validator.C_DocLine_Sort(), client);
			engine.addModelValidator(new de.metas.adempiere.docline.sort.model.validator.C_BP_DocLine_Sort(), client);
		}

		//
		// Task 09548
		engine.addModelValidator(de.metas.inout.model.validator.M_InOutLine.INSTANCE, client);

		engine.addModelValidator(de.metas.order.model.validator.OrderModuleInterceptor.INSTANCE, client);

		// gh-issue #288
		engine.addModelValidator(de.metas.logging.model.interceptor.LoggingModuleInterceptor.INSTANCE, client);
		
		//
		// Script/Rule engine
		engine.addModelValidator(de.metas.script.model.interceptor.AD_Rule.instance, client);
		engine.addModelValidator(de.metas.script.model.interceptor.AD_Table_ScriptValidator.instance, client);
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.javaclasses.model.interceptor.AD_JavaClass_Type());
		
		calloutsRegistry.registerAnnotatedCallout(new de.metas.process.callout.AD_Process_Para()); // FRESH-727
	}

	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		cachingService.addTableCacheConfig(I_AD_Client.class);
		cachingService.addTableCacheConfigIfAbsent(I_AD_Table.class);
		cachingService.addTableCacheConfigIfAbsent(I_AD_Ref_List.class);

		// M_Product (for now, using the same setting that were in MProduct.s_cache
		cachingService.createTableCacheConfigBuilder(I_M_Product.class)
				.setEnabled(true)
				.setInitialCapacity(40)
				.setExpireMinutes(5)
				.setCacheMapType(CacheMapType.HashMap)
				.setTrxLevel(TrxLevel.OutOfTransactionOnly)
				.register();
		// M_Product_Category (for now, using the same setting that were in MProductCategory.s_cache
		cachingService.createTableCacheConfigBuilder(I_M_Product_Category.class)
				.setEnabled(true)
				.setInitialCapacity(20)
				.setExpireMinutes(120)
				.setCacheMapType(CacheMapType.HashMap)
				.setTrxLevel(TrxLevel.OutOfTransactionOnly)
				.register();
		// M_AttributeSet (for now, using the same settings that were in MAttributeSet.s_cache)
		cachingService.createTableCacheConfigBuilder(I_M_AttributeSet.class)
				.setEnabled(true)
				.setInitialCapacity(30)
				.setExpireMinutes(120)
				.setCacheMapType(CacheMapType.HashMap)
				.setTrxLevel(TrxLevel.OutOfTransactionOnly)
				.register();

		// AD_Process
		// (copied settings from org.compiere.model.MProcess.s_cache: new CCache<Integer,MProcess>(Table_Name, 20))
		cachingService.createTableCacheConfigBuilder(I_AD_Process.class)
				.setEnabled(true)
				.setInitialCapacity(20)
				.setExpireMinutes(120)
				.setCacheMapType(CacheMapType.HashMap)
				.setTrxLevel(TrxLevel.OutOfTransactionOnly)
				.register();

		// C_Location
		// NOTE: we use the setting that we had in MLocation.s_cache
		cachingService.createTableCacheConfigBuilder(I_C_Location.Table_Name)
				.setEnabled(true)
				.setTrxLevel(TrxLevel.All)
				.setCacheMapType(CacheMapType.LRU)
				.setExpireMinutes(30)
				.setInitialCapacity(100)
				.setMaxCapacity(100)
				.register();

		// AD_Column
		// task 08880: need to cache because (even if for no other reason) there is method in FindPanel called by repaint() and it needs to get a column by its ID
		cachingService.createTableCacheConfigBuilder(I_AD_Column.class)
				.setEnabled(true)
				.setInitialCapacity(1000)
				.setMaxCapacity(1000)
				.setExpireMinutes(ITableCacheConfig.EXPIREMINUTES_Never)
				.setCacheMapType(CacheMapType.LRU)
				.setTrxLevel(TrxLevel.OutOfTransactionOnly)
				.register();

		// C_DocType
		// (#136 FRESH-472)
		cachingService.createTableCacheConfigBuilder(I_C_DocType.class)
				.setEnabled(true)
				.setInitialCapacity(100)
				.setMaxCapacity(100)
				.setExpireMinutes(ITableCacheConfig.EXPIREMINUTES_Never)
				.setCacheMapType(CacheMapType.LRU)
				.setTrxLevel(TrxLevel.All)
				.register();

		// task 09304: now that we can, let's also invalidate the cached UOM conversions.
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_UOM.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_UOM_Conversion.Table_Name);

		// (#136 FRESH-472)
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_DocType.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_DocType_Sequence.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_DocTypeCounter.Table_Name);

		// Broadcast cache invalidation of AD_Client and AD_Org tables.
		// This is needed in case there are some configuration changes and we want them to be applied ASAP, without restarting the server.
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Client.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_ClientInfo.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Org.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_OrgInfo.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Image.Table_Name); // mainly for logos

		// not 100% sure they are cached, but if we fix something in AD_Process, it shall be propagated
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Process.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_Process_Para.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_SysConfig.Table_Name); // also broadcast sysconfig changes

		// task 09509: changes in the pricing data shall also be propagated to other hosts
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_DiscountSchema.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_DiscountSchemaBreak.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_DiscountSchemaLine.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_PricingSystem.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_PriceList.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_PriceList_Version.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_ProductPrice.Table_Name);

		// task 09508: make sure that masterdata-fixes in warehouse and resource/plant make is to other clients
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_Warehouse.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_S_Resource.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_InfoWindow.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_InfoColumn.Table_Name);
	}
}
