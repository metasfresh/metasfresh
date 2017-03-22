package de.metas.fresh.mrp_productinfo.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.fresh.model.I_X_MRP_ProductInfo_V;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoBL;
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
 * Copyright (C) 2016 metas GmbH
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

public class MRPProductInfoBL implements IMRPProductInfoBL
{
	@Override
	public void updateItems(
			final IContextAware ctxProvider,
			final List<Object> items)
	{
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);

		// we need a fixed ordering to make sure that the items are always updated in the same order, in case we go multi-threaded.
		final SortedSet<IMRPProductInfoSelector> orderedSelectors = new TreeSet<>();

		for (final Object item : items)
		{
			final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createOrNullForModel(item);
			if (selector == null)
			{
				continue;
			}
			orderedSelectors.add(selector);
		}
		updateItems(ctxProvider, orderedSelectors);
	}

	@Override
	public void updateItems(
			final IContextAware ctxProvider,
			final IParams params)
	{
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);
		final List<IMRPProductInfoSelector> selectors = mrpProductInfoSelectorFactory.createForParams(params);

		final SortedSet<IMRPProductInfoSelector> orderedSelectors = new TreeSet<>(selectors);
		updateItems(ctxProvider, orderedSelectors);
	}

	private void updateItems(
			final IContextAware ctxProvider,
			final SortedSet<IMRPProductInfoSelector> orderedSelectors)
	{
		final IADInfoWindowDAO adInfoWindowDAO = Services.get(IADInfoWindowDAO.class);

		final I_AD_InfoWindow infoWindow = adInfoWindowDAO.retrieveInfoWindowByTableName(ctxProvider.getCtx(), I_X_MRP_ProductInfo_V.Table_Name);
		final I_AD_InfoColumn qtyMRPColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyMRP);
		final I_AD_InfoColumn qtyOnHandColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_QtyOnHand);

		for (final IMRPProductInfoSelector selector : orderedSelectors)
		{
			final StringBuilder logMsg = new StringBuilder();
			try
			{
				logMsg.append(selector.toStringForRegularLogging());

				// always update the X_MRP_ProductInfo_Detail_MV rows of the date, product and ASI
				{
					final String functionCall = DB_FUNCTION_X_MRP_PRODUCT_INFO_DETAIL_MV_REFRESH;
					logMsg.append("\nCalling " + functionCall);

					doDBFunctionCall(functionCall, selector, ctxProvider.getTrxName());
				}

				// If the selector originates from a C_OrderLine, then also update the poor man's MRP records of the BOM-products which
				// the given order line's product is made of.
				// This needs to happen after having refreshed X_MRP_ProductInfo_Detail_MV, because it uses QtyOrdered_Sale_OnDate
				if (qtyMRPColumn != null
						&& qtyMRPColumn.isActive()
						&& selector.getParamPrefix().contains(I_C_OrderLine.Table_Name))
				{
					final String functionCall = DB_FUNCTION_X_MRP_PRODUCTINFO_DETAIL_UPDATE_POOR_MANS_MRP;
					logMsg.append("\nCalling " + functionCall);

					doDBFunctionCall(functionCall, selector, ctxProvider.getTrxName());
				}

				if (qtyOnHandColumn != null && qtyOnHandColumn.isActive())
				{
					updateQtyOnHand(ctxProvider.getCtx(), selector);
				}
			}
			finally
			{
				// note: just make sure we log what we got. Assume that any error/exception logging is done by the framework
				Loggables.get().addLog(logMsg.toString());
			}
		}
	}

	/**
	 * Updates all {@link I_X_MRP_ProductInfo_Detail_MV}s whose product and ASI match the given selector and which date is greater than or equal to the <code>selector</code>'s date.
	 *
	 * @param ctx
	 * @param selector
	 */
	private void updateQtyOnHand(final Properties ctx, final IMRPProductInfoSelector selector)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);

		// we do this even if selector's model is a I_X_MRP_ProductInfo_Detail_MV, because we also want to update its future siblings.
		final List<I_X_MRP_ProductInfo_Detail_MV> list = queryBL.createQueryBuilder(
				I_X_MRP_ProductInfo_Detail_MV.class,
				PlainContextAware.newWithThreadInheritedTrx(ctx))

				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_DateGeneral, Operator.GREATER_OR_EQUAL, selector.getDate())
				.addEqualsFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_M_Product_ID, selector.getM_Product_ID())
				.addEqualsFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMN_ASIKey, selector.getASIKey())
				.orderBy()
				.addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMN_DateGeneral)
				.addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMN_M_Product_ID)
				.addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMN_ASIKey)
				.addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMN_IsFallBack) // not-fallback first, if they exist
				.endOrderBy()
				.create()
				.list();

		// check if any item still has a null QtyOnHand value.
		// if yes, then we will use the storage engine to get the overall non-iterative QtyOnHand from the system.
		boolean resetAllQtys = false;
		for (I_X_MRP_ProductInfo_Detail_MV item : list)
		{
			if (selector.getModelOrNull() == null // if the model is null, then we already know that we need to reset all qtys, because there is no model to extract the delta from.
					|| !selector.getParamPrefix().startsWith(I_M_InOutLine.Table_Name)
					|| InterfaceWrapperHelper.isNull(item, I_X_MRP_ProductInfo_Detail_MV.COLUMNNAME_QtyOnHand))
			{
				resetAllQtys = true; // at least one item needs a full computation, so we can do it for all
				break;
			}
		}

		// if there are multiple I_X_MRP_ProductInfo_Detail_MV that share the same selector
		// (i.e. one with IsFallBack='N' and following n with IsFallBack='Y')
		// then use this set to only deal with the first one
		// according to our ordering, the first one is the one IsFallBack='N'
		final Set<IMRPProductInfoSelector> seenSelectors = new HashSet<>();

		BigDecimal qtyOnHand = null;
		for (final I_X_MRP_ProductInfo_Detail_MV item : list)
		{
			if (!seenSelectors.add(mrpProductInfoSelectorFactory.createOrNullForModel(item)))
			{
				continue;
			}

			final I_X_MRP_ProductInfo_Detail_MV itemToUse;

			if (item.isFallBack())
			{
				// there is no not-fallback-item for the current selector. create one, so we can set its QtyOnHand
				itemToUse = InterfaceWrapperHelper.newInstance(I_X_MRP_ProductInfo_Detail_MV.class, item);
				InterfaceWrapperHelper.copy().setFrom(item).setTo(itemToUse).copy();
				itemToUse.setQtyOnHand(null);
				itemToUse.setIsFallBack(false);
			}
			else
			{
				itemToUse = item;
			}

			if (resetAllQtys)
			{
				// do the "full" computation. note that all item need the same qtyOnHand value
				if (qtyOnHand == null)
				{
					qtyOnHand = retrieveQtyOnHand(ctx, selector);
				}
				itemToUse.setQtyOnHand(qtyOnHand);
			}
			else if (selector.getParamPrefix().startsWith(I_M_InOutLine.Table_Name))
			{
				// just increase or decrease by the inout line's qty
				final IInOutBL inOutBL = Services.get(IInOutBL.class);
				final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(selector.getModelOrNull(), I_M_InOutLine.class);
				final BigDecimal delta = inOutBL.getEffectiveStorageChange(inOutLine);
				itemToUse.setQtyOnHand(itemToUse.getQtyOnHand().add(delta));
			}
			InterfaceWrapperHelper.save(itemToUse);
		}
	}

	/**
	 * Uses the storage engine to get the overall on hand quantity for the given <code>selector</code>. Note that this includes already picked quantities.
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
				.setExcludeAfterPickingLocator(false) // for the overall qty on hand, we do want *all* the storages, also those that are already picked.
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
				.retrieveStorageRecords(PlainContextAware.newOutOfTrx(ctx), storageQuery)
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
