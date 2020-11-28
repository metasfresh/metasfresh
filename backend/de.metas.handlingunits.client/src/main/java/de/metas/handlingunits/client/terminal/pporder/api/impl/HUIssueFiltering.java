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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.pporder.api.IHUIssueFiltering;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.IMaterialTrackingAware;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author cg
 *
 */
public class HUIssueFiltering implements IHUIssueFiltering
{
	private static final String SYSCONFIG_FilterHUsByQualityInspection = "de.metas.handlingunits.client.terminal.pporder.api.impl.HUIssueFiltering.FilterHUsByQualityInspection";

	@Override
	public List<I_M_Warehouse> retrieveWarehouse()
	{
		return Services.get(IWarehouseDAO.class).getWarehousesAllowedForDocBaseType(X_C_DocType.DOCBASETYPE_ManufacturingOrder);
	}

	@Override
	public List<I_PP_Order> getManufacturingOrders(final WarehouseId warehouseId)
	{
		final List<I_PP_Order> ppOrders = Services.get(IPPOrderDAO.class).retrieveReleasedManufacturingOrdersForWarehouse(warehouseId);
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

				ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
				final String productName = Services.get(IProductBL.class).getProductName(productId);

				final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(ppOrder.getC_BPartner_ID());
				final String bpartnerName = Services.get(IBPartnerBL.class).getBPartnerName(bpartnerId);

				return Util.mkKey(sortingDate,
						productName,
						bpartnerName);
			}
		});
		return ppOrders;
	}

	@Override
	public IHUQueryBuilder getHUsForIssueQuery(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final List<I_PP_Order_BOMLine> orderBOMLines,
			final WarehouseId warehouseId)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// retrieve products from BOM line for specified order
		final Set<Integer> productIds = new HashSet<>();
		for (final I_PP_Order_BOMLine orderBOMLine : orderBOMLines)
		{
			final BOMComponentType componentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
			if (componentType.isReceipt())
			{
				continue;
			}

			productIds.add(orderBOMLine.getM_Product_ID());
		}

		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO
				.createHUQueryBuilder()
				// make sure not to issue HUs in the swing client that were already issued in the webUI and therefore have HUStatus=Issued
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.setContext(ppOrder)
				.setOnlyTopLevelHUs()
				.addOnlyWithProductIds(productIds)
				.addOnlyInWarehouseId(warehouseId)
				.onlyNotLocked() // skip those locked because usually those were planned for something...
		;

		//
		// if the ppOrder is already associated to a material tracking, then don't allow the user to add others
		final IMaterialTrackingAware materialTrackingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(ppOrder, IMaterialTrackingAware.class);
		if (materialTrackingAware != null && materialTrackingAware.getM_Material_Tracking_ID() > 0)
		{
			final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);

			huQueryBuilder.addOnlyWithAttribute(
					materialTrackingAttributeBL.getMaterialTrackingAttribute(),
					Integer.toString(materialTrackingAware.getM_Material_Tracking_ID()));
		}

		//
		// Filter HUs by QualityInspection
		if (Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_FilterHUsByQualityInspection, true))
		{
			final boolean qualityInspectionOrder = Services.get(IMaterialTrackingPPOrderBL.class).isQualityInspection(ppOrder);
			if (qualityInspectionOrder)
			{
				// #579 Disable this condition.
				// From now on, the qualityInspection production orders will also allow HUs with no QualityInspection attributes set
				// huQueryBuilder.addOnlyWithAttributeNotNull(IHUMaterialTrackingBL.ATTRIBUTENAME_QualityInspectionCycle);
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
