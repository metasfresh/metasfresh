package de.metas.handlingunits.materialtracking.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.materialtracking.IHUPPOrderMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.MaterialTrackingWithQuantity;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MTLinkRequest.IfModelAlreadyLinked;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Links a PP_Order to the <code>M_Material_Tracking</code> of the <code>PP_Order_BOMLine</code>'s HU.<br>
 * Intended to be called when a HU is issued to a PP_Order.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUPPOrderMaterialTrackingBL implements IHUPPOrderMaterialTrackingBL
{
	@Override
	public void linkPPOrderToMaterialTracking(
			@NonNull final I_PP_Cost_Collector costCollectorRecord,
			@NonNull final MaterialTrackingWithQuantity materialTrackingWithQuantity)
	{
		final I_M_Material_Tracking materialTrackingRecord = materialTrackingWithQuantity.getMaterialTrackingRecord();

		// Make sure the material tracking is compatible with BOM line
		if (costCollectorRecord.getM_Product_ID() != materialTrackingRecord.getM_Product_ID())
		{
			// the M_Material_Tracking HU-Attribute was inherited from the original raw material, but this PP_Order is about a different product
			return;
		}

		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
		final boolean isQualityInspection = materialTrackingPPOrderBL.isQualityInspection(costCollectorRecord.getPP_Order_ID());

		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(costCollectorRecord.getPP_Order(), I_PP_Order.class);
		if (isQualityInspection)
		{
			// Set PP_Order.M_Material_Tracking_ID
			if (ppOrder.getM_Material_Tracking_ID() <= 0)
			{
				ppOrder.setM_Material_Tracking(materialTrackingRecord);
				Services.get(IPPOrderDAO.class).save(ppOrder);
			}
			else
			{
				// this should be preserved in HUIssueFiltering, so we don't need a nice user-friendly message
				Check.errorIf(ppOrder.getM_Material_Tracking_ID() != materialTrackingRecord.getM_Material_Tracking_ID(),
						"ppOrder {} is already assinged to materialtracking {} and therefore cannot be additionally assigned to materialtracking {}",
						ppOrder, ppOrder.getM_Material_Tracking(), materialTrackingRecord);
			}
		}

		// Assign PP_Order to material tracking
		final IProductBL productBL = Services.get(IProductBL.class);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);

		// in case of non-quality inspections, we can have multiple links; in case of quality inspections there is a dedicated check
		final MTLinkRequest ppOrderLinkRequest = MTLinkRequest.builder()
				.model(ppOrder)
				.materialTrackingRecord(materialTrackingRecord)
				.ifModelAlreadyLinked(IfModelAlreadyLinked.ADD_ADDITIONAL_LINK)
				.build();

		materialTrackingBL.linkModelToMaterialTracking(ppOrderLinkRequest);

		// Assign PP_Cost_Collector and quantity to material tracking
		final ProductId productId = ProductId.ofRepoId(materialTrackingRecord.getM_Product_ID());
		final UomId toUOMId = productBL.getStockUOMId(productId);
		final Quantity sum = uomConversionBL.computeSum(
				UOMConversionContext.of(productId),
				materialTrackingWithQuantity.getQuantities(),
				toUOMId);

		final MTLinkRequest costCollectorLinkRequest = MTLinkRequest.builder()
				.model(costCollectorRecord)
				.materialTrackingRecord(materialTrackingRecord)
				.qtyIssued(sum.toBigDecimal())
				.ifModelAlreadyLinked(IfModelAlreadyLinked.ADD_ADDITIONAL_LINK)
				.build();
		materialTrackingBL.linkModelToMaterialTracking(costCollectorLinkRequest);
	}

	@Override
	public I_M_Material_Tracking extractMaterialTrackingIfAny(final IHUContext huContext, final I_M_HU hu)
	{
		// Do nothing if material tracking module is not activated
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		if (!materialTrackingBL.isEnabled())
		{
			return null;
		}

		// If there is no HU => do nothing
		if (hu == null)
		{
			return null;
		}

		// Services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);

		//
		// Get HU Attributes from top level HU
		final I_M_HU huTopLevel = handlingUnitsBL.getTopLevelParent(hu);
		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(huTopLevel);

		//
		// Get Material Tracking from HU Attributes
		final I_M_Material_Tracking materialTracking = materialTrackingAttributeBL.getMaterialTracking(huContext, huAttributes);
		return materialTracking;
	}

}
