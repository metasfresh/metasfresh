package org.adempiere.model.validator;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.spi.impl.NotifyAsyncBatch;
import de.metas.bpartner.product.callout.C_BPartner_Product;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.CacheMgt;
import de.metas.cache.TableNamesGroup;
import de.metas.cache.model.IModelCacheService;
import de.metas.cache.model.ITableCacheConfig;
import de.metas.cache.model.ITableCacheConfig.TrxLevel;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.event.EventBusAdempiereInterceptor;
import de.metas.event.Topic;
import de.metas.notification.INotificationGroupNameRepository;
import de.metas.notification.NotificationGroupName;
import de.metas.organization.interceptors.C_Fiscal_Representation;
import de.metas.reference.model.interceptor.AD_Ref_Table;
import de.metas.util.Services;
import de.metas.workflow.interceptors.AD_Workflow;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.element.model.interceptor.AD_Element;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.copyRecordSupport.CloneASIListener;
import org.adempiere.pricing.model.I_C_PricingRule;
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
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocTypeCounter;
import org.compiere.model.I_C_DocType_Sequence;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Warehouse;

import java.util.List;

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

/**
 * ADempiere Base Module Activator
 *
 * @author tsa
 */
public final class AdempiereBaseValidator extends AbstractModuleInterceptor
{
	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		final INotificationGroupNameRepository notificationGroupNameRepo = Services.get(INotificationGroupNameRepository.class);
		return notificationGroupNameRepo.getAll()
				.stream()
				.map(NotificationGroupName::toTopic)
				.collect(ImmutableList.toImmutableList());
	}

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
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		// Event bus
		engine.addModelValidator(EventBusAdempiereInterceptor.instance);

		engine.addModelValidator(new org.adempiere.ad.callout.model.validator.AD_ColumnCallout());
		engine.addModelValidator(new org.adempiere.model.validator.AD_InfoColumn());
		engine.addModelValidator(new org.adempiere.server.rpl.model.validator.IMP_Processor());

		engine.addModelValidator(new AD_Workflow());

		engine.addModelValidator(new de.metas.javaclasses.model.interceptor.AD_JavaClass()); // 04599
		engine.addModelValidator(new de.metas.javaclasses.model.interceptor.AD_JavaClass_Type()); // 04599

		engine.addModelValidator(de.metas.process.model.interceptor.AD_Process.instance); // FRESH-727

		//
		// Currency
		{
			engine.addModelValidator(new de.metas.currency.model.interceptor.C_Conversion_Rate());
		}

		//
		// Tax
		{
			engine.addModelValidator(new org.adempiere.tax.model.validator.C_Tax());
		}

		//
		// Storage
		{
			engine.addModelValidator(new org.adempiere.model.validator.M_Transaction());
		}

		//
		// fresh 08585: C_DocLine_Sort
		{
			engine.addModelValidator(new de.metas.adempiere.docline.sort.model.validator.C_DocLine_Sort());
			engine.addModelValidator(new de.metas.adempiere.docline.sort.model.validator.C_BP_DocLine_Sort());
		}

		engine.addModelValidator(de.metas.event.interceptor.Main.INSTANCE);

		engine.addModelValidator(de.metas.invoice.interceptor.InvoiceModuleInterceptor.INSTANCE);

		// gh-issue #288
		engine.addModelValidator(de.metas.logging.model.interceptor.LoggingModuleInterceptor.INSTANCE);

		//
		// Script/Rule engine
		engine.addModelValidator(de.metas.script.model.interceptor.AD_Rule.instance);
		engine.addModelValidator(de.metas.script.model.interceptor.AD_Table_ScriptValidator.instance);

		//
		// Request
		engine.addModelValidator(de.metas.request.model.interceptor.RequestsModuleInterceptor.instance);

		//
		// BPartner
		engine.addModelValidator(new de.metas.bpartner.model.interceptor.C_BPartner());

		// #2895
		engine.addModelValidator(AD_Ref_Table.instance);

		//engine.addModelValidator(org.adempiere.ad.column.model.interceptor.AD_Column.instance); // #2913
		engine.addModelValidator(new org.adempiere.ad.column.model.interceptor.AD_SQLColumn_SourceTableColumn());

		engine.addModelValidator(new AD_Element());

		engine.addModelValidator(new C_Fiscal_Representation());
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.javaclasses.model.interceptor.AD_JavaClass_Type());

		calloutsRegistry.registerAnnotatedCallout(new de.metas.process.callout.AD_Process_Para()); // FRESH-727

		calloutsRegistry.registerAnnotatedCallout(new C_BPartner_Product());
	}

	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		cachingService.addTableCacheConfig(I_AD_Client.class);
		cachingService.addTableCacheConfigIfAbsent(I_AD_Table.class);
		cachingService.addTableCacheConfigIfAbsent(I_AD_Ref_List.class);
		cachingService.addTableCacheConfigIfAbsent(I_M_PriceList.class);

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

		final CacheMgt cacheMgt = CacheMgt.get();
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		cacheMgt.enableRemoteCacheInvalidationForTableNamesGroup(TableNamesGroup.builder()
				.groupId("tablesWithRemoteCacheInvalidationFlagSet")
				.tableNames(adTableDAO.getTableNamesWithRemoteCacheInvalidation())
				.build());

		// task 09304: now that we can, let's also invalidate the cached UOM conversions.
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

		// gh #1184: also propagate pricing rule changes to other hosts
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_PricingRule.Table_Name);

		// task 09508: make sure that masterdata-fixes in warehouse and resource/plant make is to other clients
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_Warehouse.Table_Name);
		//cacheMgt.enableRemoteCacheInvalidationForTableName(I_S_Resource.Table_Name); // not needed anymore because we have a dedicated cache for S_Resource

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_InfoWindow.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_AD_InfoColumn.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_BPartner.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_BP_Relation.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_BPartner_Stats.Table_Name);

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_Attribute.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_AttributeValue.Table_Name);
	}
}
