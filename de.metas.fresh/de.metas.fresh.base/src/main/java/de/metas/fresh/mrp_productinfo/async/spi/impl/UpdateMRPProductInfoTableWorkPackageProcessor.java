package de.metas.fresh.mrp_productinfo.async.spi.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.fresh.model.I_X_MRP_ProductInfo_V;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelector;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelectorFactory;
import de.metas.handlingunits.model.I_M_Attribute;
import de.metas.inout.IInOutBL;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;

/*
 * #%L
 * de.metas.fresh.base
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

public class UpdateMRPProductInfoTableWorkPackageProcessor extends WorkpackageProcessorAdapter
{

	private static final WorkpackagesOnCommitSchedulerTemplate<IMRPProductInfoSelector> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<IMRPProductInfoSelector>(
			UpdateMRPProductInfoTableWorkPackageProcessor.class)
	{
		@Override
		protected Properties extractCtxFromItem(final IMRPProductInfoSelector item)
		{
			return InterfaceWrapperHelper.getCtx(item.getModel());
		}

		@Override
		protected String extractTrxNameFromItem(final IMRPProductInfoSelector item)
		{
			return InterfaceWrapperHelper.getTrxName(item.getModel());
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final IMRPProductInfoSelector item)
		{
			return item.getModel();
		}

		@Override
		protected Map<String, Object> extractParametersFromItem(final IMRPProductInfoSelector item)
		{
			return item.asMap();
		}
	};

	public static void schedule(final Object item)
	{
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);

		final IMRPProductInfoSelector itemToEnqueue = mrpProductInfoSelectorFactory.createOrNull(item);
		if (itemToEnqueue == null)
		{
			return; // nothing to do
		}
		SCHEDULER.schedule(itemToEnqueue);
	}

	@Override
	public Result processWorkPackage(
			final I_C_Queue_WorkPackage workpackage,
			final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);
		final IADInfoWindowDAO adInfoWindowDAO = Services.get(IADInfoWindowDAO.class);

		final IParams params = workpackageParamDAO.retrieveWorkpackageParams(workpackage);

		final List<Object> items = queueDAO.retrieveItemsSkipMissing(workpackage, Object.class, localTrxName);

		// we need a fixed ordering to make sure that the items are always updated in the same order, in case we go multi-threaded.
		final SortedSet<IMRPProductInfoSelector> orderedSelectors = new TreeSet<IMRPProductInfoSelector>();

		for (final Object item : items)
		{
			final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createOrNull(item, params);
			if (selector == null)
			{
				continue;
			}
			orderedSelectors.add(selector);
		}

		for (final IMRPProductInfoSelector selector : orderedSelectors)
		{
			final StringBuilder logMsg = new StringBuilder();
			try
			{
				logMsg.append(selector.toStringForRegularLogging());

				// always update the X_MRP_ProductInfo_Detail_MV rows of the date, product and ASI
				{
					final String functionCall = "\"de.metas.fresh\".X_MRP_ProductInfo_Detail_MV_Refresh";
					logMsg.append("\nCalling " + functionCall);

					doDBFunctionCall(functionCall, selector, localTrxName);
				}

				final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);

				final I_AD_InfoWindow infoWindow = adInfoWindowDAO.retrieveInfoWindowByTableName(ctx, I_X_MRP_ProductInfo_V.Table_Name);
				final I_AD_InfoColumn qtyMRPColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyMRP);
				final I_AD_InfoColumn qtyOnHandColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_QtyOnHand);

				// If the selector originates from a C_OrderLine, the also update the poor man's MRP records of the BOM-products which
				// the given order line's product is made of.
				// This needs to happen after having refreshed X_MRP_ProductInfo_Detail_MV, because it uses QtyOrdered_Sale_OnDate
				if (qtyMRPColumn != null && qtyMRPColumn.isActive() && InterfaceWrapperHelper.isInstanceOf(selector.getModel(), I_C_OrderLine.class))
				{
					final String functionCall = "\"de.metas.fresh\".x_mrp_productinfo_detail_update_poor_mans_mrp";
					logMsg.append("\nCalling " + functionCall);

					doDBFunctionCall(functionCall, selector, localTrxName);
				}

				if (qtyOnHandColumn != null && qtyOnHandColumn.isActive() && InterfaceWrapperHelper.isInstanceOf(selector.getModel(), I_M_InOutLine.class))
				{
					updateQtyOnHandForInOutLine(ctx, selector);
				}
			}
			finally
			{
				// note: just make sure we log what we got. Assume that any error/exception logging is done by the framework
				ILoggable.THREADLOCAL.getLoggable().addLog(logMsg.toString());
			}
		}

		return Result.SUCCESS;
	}

	private void updateQtyOnHandForInOutLine(final Properties ctx, final IMRPProductInfoSelector selector)
	{
		Check.errorUnless(InterfaceWrapperHelper.isInstanceOf(selector.getModel(), I_M_InOutLine.class),
				"The model of param 'selector' needs to be an M_InOutLine; selector={}; model={}", selector, selector.getModel());

		final String asiKey = DB.getSQLValueString(ITrx.TRXNAME_None, "SELECT GenerateHUStorageASIKey(?, '')", selector.getM_AttributeSetInstance_ID());

		final List<I_X_MRP_ProductInfo_Detail_MV> list = Services.get(IQueryBL.class).createQueryBuilder(I_X_MRP_ProductInfo_Detail_MV.class, selector.getModel())
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_DateGeneral, Operator.GREATER_OR_EQUAL, selector.getDate())
				.addEqualsFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_M_Product_ID, selector.getM_Product_ID())
				.addEqualsFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_ASIKey, asiKey)
				.create()
				.list();

		// check if any item still has a null QtyOnHand value.
		// if yes, then we will use the storage engine to get the overall non-iterative QtyOnHand from the system.
		boolean resetAllQtys = false;
		for (I_X_MRP_ProductInfo_Detail_MV item : list)
		{
			if (InterfaceWrapperHelper.isNull(item, I_X_MRP_ProductInfo_Detail_MV.COLUMNNAME_QtyOnHand))
			{
				resetAllQtys = true; // at least one item needs a full computation, so we can do it for all
				break;
			}
		}

		BigDecimal qtyOnHand = null;
		for (I_X_MRP_ProductInfo_Detail_MV item : list)
		{
			if (resetAllQtys)
			{
				// do the "full" computation
				if (qtyOnHand == null)
				{
					qtyOnHand = retrieveQtyOnHand(ctx, selector);
				}
				item.setQtyOnHand(qtyOnHand);
			}
			else
			{
				// just increase or decrease by the inout line's qty
				final IInOutBL inOutBL = Services.get(IInOutBL.class);
				final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(selector.getModel(), I_M_InOutLine.class);
				final BigDecimal delta = inOutBL.getEffectiveStorageChange(inOutLine);
				item.setQtyOnHand(item.getQtyOnHand().add(delta));
			}
			InterfaceWrapperHelper.save(item);
		}
	}

	/**
	 * Uses the storage engine to get the overall on hand quantity for the given <code>selector</code>
	 *
	 * @param ctx
	 * @param selector
	 * @return
	 * @task https://github.com/metasfresh/metasfresh/issues/213
	 */
	private BigDecimal retrieveQtyOnHand(final Properties ctx, final IMRPProductInfoSelector selector)
	{
		BigDecimal qtyOnHand;
		final IStorageEngineService storageEngineProvider = Services.get(IStorageEngineService.class);
		final IStorageEngine storageEngine = storageEngineProvider.getStorageEngine();

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, selector.getM_Product_ID(), I_M_Product.class, ITrx.TRXNAME_None);
		final IStorageQuery storageQuery = storageEngine
				.newStorageQuery()
				.addProduct(product);

		if (selector.getM_AttributeSetInstance_ID() > 0)
		{
			final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.create(ctx, selector.getM_AttributeSetInstance_ID(), I_M_AttributeSetInstance.class, ITrx.TRXNAME_None);
			final IAttributeSet attributeSet = storageEngine.getAttributeSet(asi);
			storageEngine.getAttributeSet(asi)
					.getAttributes()
					.stream()
					.map(a -> InterfaceWrapperHelper.create(a, I_M_Attribute.class))
					.filter(a -> a.isActive() && a.isMatchHUStorage()) // important: this is also a filter in the GenerateHUStorageASIKey DB function
					.forEach(a -> storageQuery.addAttribute(a, attributeSet.getAttributeValueType(a), attributeSet.getValue(a)));
		}

		qtyOnHand = storageEngine
				.retrieveStorageRecords(new PlainContextAware(ctx), storageQuery)
				.stream()
				.map(storageRecord -> storageRecord.getQtyOnHand())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return qtyOnHand;
	}

	private void doDBFunctionCall(
			final String functionCall,
			final IMRPProductInfoSelector selector,
			final String trxName)
	{
		DB.executeFunctionCallEx(trxName, "select " + functionCall + "(?,?,?)",
				new Object[] {
						selector.getDate(),
						selector.getM_Product_ID(),
						selector.getM_AttributeSetInstance_ID() });
	}
}
