/**
 *
 */
package de.metas.handlingunits.client.terminal.pporder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_M_Warehouse_Routing;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.pporder.api.IHUIssueFiltering;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.IMaterialTrackingAware;

/**
 * @author cg
 *
 */
public class HUIssueFiltering implements IHUIssueFiltering
{
	private static final String SYSCONFIG_FilterHUsByQualityInspection = "de.metas.handlingunits.client.terminal.pporder.api.impl.HUIssueFiltering.FilterHUsByQualityInspection";
	
	@Override
	public List<I_M_Warehouse> retrieveWarehouse(final Properties ctx)
	{
		return Services.get(IWarehouseDAO.class).retrieveWarehouses(ctx, X_M_Warehouse_Routing.DOCBASETYPE_ManufacturingOrder);
	}

	@Override
	public List<I_PP_Order> getManufacturingOrders(final Properties ctx, final int warehouseId)
	{
		final List<I_PP_Order> ppOrders = Services.get(IPPOrderDAO.class).retrieveReleasedManufacturingOrdersForWarehouse(ctx, warehouseId);
		//
		// 08181: ORDER BY promisedDateTime ASC, preparationDateTime ASC, productName ASC, partner ASC
		Collections.sort(ppOrders, new Comparator<I_PP_Order>()
		{
			@Override
			public int compare(final I_PP_Order o1, final I_PP_Order o2)
			{
				final ArrayKey key1 = buildKey(o1);
				final ArrayKey key2 = buildKey(o2);
				return ArrayKey.compare(key1, key2);
			}

			private ArrayKey buildKey(final I_PP_Order ppOrder)
			{
				if (ppOrder == null)
				{
					return null;
				}

				// task 08181
				final Timestamp datePromised = ppOrder.getDatePromised(); // not null
				final Timestamp sortingDate = ppOrder.getPreparationDate() != null ? ppOrder.getPreparationDate() : datePromised;

				final I_M_Product product = ppOrder.getM_Product();
				final String productName = product == null ? null : product.getName(); // shall not be null...

				final I_C_BPartner bpartner = ppOrder.getC_BPartner();
				final String bpartnerName = bpartner == null ? null : bpartner.getName();

				return Util.mkKey(sortingDate,
						productName,
						bpartnerName);
			}
		});
		return ppOrders;
	}

	@Override
	public IHUQueryBuilder getHUsForIssueQuery(final I_PP_Order ppOrder, final List<I_PP_Order_BOMLine> orderBOMLines, final int warehouseId)
	{
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// retrieve products from BOM line for specified order
		final Set<Integer> productIds = new HashSet<Integer>();
		for (final I_PP_Order_BOMLine orderBOMLine : orderBOMLines)
		{
			if (ppOrderBOMBL.isReceipt(orderBOMLine))
			{
				continue;
			}

			productIds.add(orderBOMLine.getM_Product_ID());
		}

		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO
				.createHUQueryBuilder()
				.setContext(ppOrder)
				.setOnlyTopLevelHUs()
				.addOnlyWithProductIds(productIds)
				.addOnlyInWarehouseId(warehouseId);

		//
		// if the ppOrder is already associated to a material tracking, then don't allow the user to add others
		final IMaterialTrackingAware materialTrackingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(ppOrder, IMaterialTrackingAware.class);
		if (materialTrackingAware != null && materialTrackingAware.getM_Material_Tracking_ID() > 0)
		{
			final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);
			final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrder);

			huQueryBuilder.addOnlyWithAttribute(
					materialTrackingAttributeBL.getMaterialTrackingAttribute(ctx),
					Integer.toString(materialTrackingAware.getM_Material_Tracking_ID())
					);
		}
		
		//
		// Filter HUs by QualityInspection
		if (Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_FilterHUsByQualityInspection, true))
		{
			final boolean qualityInspectionOrder = Services.get(IMaterialTrackingPPOrderBL.class).isQualityInspection(ppOrder);
			if (qualityInspectionOrder)
			{
				huQueryBuilder.addOnlyWithAttributeNotNull(IHUMaterialTrackingBL.ATTRIBUTENAME_QualityInspectionCycle);
			}
			else
			{
				huQueryBuilder.addOnlyWithAttributeMissingOrNull(IHUMaterialTrackingBL.ATTRIBUTENAME_QualityInspectionCycle);
			}
		}

		return huQueryBuilder;
	}

	@Override
	public List<I_PP_Order_BOMLine> getOrderBOMLines(final I_PP_Order order)
	{
		if (order == null)
		{
			return Collections.emptyList();
		}

		return Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(order);
	}
}
