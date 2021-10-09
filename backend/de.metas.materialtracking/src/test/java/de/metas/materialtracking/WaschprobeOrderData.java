package de.metas.materialtracking;

/*
 * #%L
 * de.metas.materialtracking
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.CostCollectorType;
import org.eevolution.model.I_PP_Cost_Collector;
import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.materialtracking.impl.MaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.IPPOrderQualityFields;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.model.I_PP_Order_BOMLine;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.impl.QualityInspectionOrderFactory;
import de.metas.util.Services;

/**
 * TODO: one instance per PP_Order.
 * <p>
 * Helper class to create Manufacturing orders for Carrot Waschproble use-case.
 * <p>
 * We use this class because we also want to keep the structure all together.
 *
 * @author tsa
 */
public class WaschprobeOrderData
{
	// Data

	public final Date productionDate;

	private WaschprobeStandardMasterData data;
	public I_M_Material_Tracking materialTracking;
	public I_PP_Order ppOrder;

	/**
	 * Unwashed carrots, raw material
	 */
	public I_PP_Order_BOMLine ppOrderBOMLine_Carrot_Unwashed;

	public I_PP_Cost_Collector ppOrderCostOllector_Issue_Carrot_Unwashed;

	/**
	 * Unwashed carrots, raw material variant
	 */
	public I_PP_Order_BOMLine ppOrderBOMLine_Carrot_Unwashed_Alternative01;

	/**
	 * Big Carrots, Co-Product
	 */
	public I_PP_Order_BOMLine ppOrderBOMLine_Carrot_Big;

	/**
	 * Animal food, By-Product
	 */
	public I_PP_Order_BOMLine ppOrderBOMLine_Carrot_AnimalFood;

	/**
	 * UOM used for Order and Order BOM Lines
	 */
	public final I_C_UOM uom;

	public WaschprobeOrderData(
			final WaschprobeStandardMasterData masterData,
			final Date productionDate)
	{
		this.data = masterData;
		this.uom = data.uom;
		this.productionDate = productionDate;

		create();
	}

	private void create()
	{
		this.ppOrder = data.createPP_Order(data.pCarrot_Washed, BigDecimal.ZERO, uom, productionDate);

		// Flag it as Quality Inspection (QI)
		this.ppOrder.setOrderType(MaterialTrackingPPOrderBL.C_DocType_DOCSUBTYPE_QualityInspection);
		InterfaceWrapperHelper.save(this.ppOrder);

		//
		// Main component: raw material
		this.ppOrderBOMLine_Carrot_Unwashed = data.createPP_Order_BOMLine(ppOrder,
				BOMComponentType.Component,
				data.pCarrot_Unwashed,
				BigDecimal.ZERO, // delivered
				uom);
		this.ppOrderBOMLine_Carrot_Unwashed.setVariantGroup("Alternative01");
		InterfaceWrapperHelper.save(this.ppOrderBOMLine_Carrot_Unwashed);

		//
		// Raw material variant
		this.ppOrderBOMLine_Carrot_Unwashed_Alternative01 = data.createPP_Order_BOMLine(ppOrder,
				BOMComponentType.Variant,
				data.pCarrot_Unwashed_Alternative01,
				BigDecimal.ZERO, // delivered
				uom);
		this.ppOrderBOMLine_Carrot_Unwashed_Alternative01.setVariantGroup("Alternative01");
		InterfaceWrapperHelper.save(this.ppOrderBOMLine_Carrot_Unwashed_Alternative01);

		//
		// Co-Products
		this.ppOrderBOMLine_Carrot_Big = data.createPP_Order_BOMLine(ppOrder,
				BOMComponentType.CoProduct,
				data.pCarrot_Big,
				BigDecimal.ZERO, // delivered
				uom);

		//
		// By-Products
		this.ppOrderBOMLine_Carrot_AnimalFood = data.createPP_Order_BOMLine(ppOrder,
				BOMComponentType.ByProduct,
				data.pCarrot_AnimalFood,
				BigDecimal.ZERO, // delivered
				uom);

		// required because currently we get the inOutLines and this the receipt date and country of origin via cc.
		this.ppOrderCostOllector_Issue_Carrot_Unwashed = createPP_CostCollector_Issue(
				data.getContext(),
				ppOrder,
				data.pCarrot_Unwashed,
				BigDecimal.ZERO);
	}

	private I_PP_Cost_Collector createPP_CostCollector_Issue(
			final IContextAware context,
			final I_PP_Order ppOrder,
			final I_M_Product issuedProduct,
			final BigDecimal issuedQty)
	{
		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class, context);
		cc.setPP_Order(ppOrder);
		cc.setCostCollectorType(CostCollectorType.ComponentIssue.getCode());
		cc.setMovementQty(issuedQty);

		InterfaceWrapperHelper.save(cc);

		return cc;
	}

	public WaschprobeOrderData assignToMaterialTracking(final I_M_Material_Tracking materialTracking)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		final I_M_Material_Tracking_Ref ref = materialTrackingDAO.createMaterialTrackingRefNoSave(materialTracking, ppOrder);

		// NOTE: because we are running without model interceptors, listeners, we need to set IsQualityInspectionDoc flag manually
		ref.setIsQualityInspectionDoc(true);

		InterfaceWrapperHelper.save(ref);

		ppOrder.setM_Material_Tracking(materialTracking);
		InterfaceWrapperHelper.save(ppOrder);

		this.materialTracking = materialTracking;

		return this;
	}

	public WaschprobeOrderData setCarrot_Washed_QtyDelivered(final BigDecimal qtyDelivered)
	{
		ppOrder.setQtyDelivered(qtyDelivered);
		InterfaceWrapperHelper.save(ppOrder);

		return this;
	}

	public WaschprobeOrderData setCarrot_Unwashed_QtyDelivered(final BigDecimal qtyDelivered)
	{
		return setQtyDelivered(ppOrderBOMLine_Carrot_Unwashed, qtyDelivered);
	}

	public WaschprobeOrderData setCarrot_Big_QtyDelivered(final BigDecimal qtyDelivered)
	{
		return setQtyDelivered(ppOrderBOMLine_Carrot_Big, qtyDelivered);
	}

	public WaschprobeOrderData setCarrot_AnimalFood_QtyDelivered(final BigDecimal qtyDelivered)
	{
		return setQtyDelivered(ppOrderBOMLine_Carrot_AnimalFood, qtyDelivered);
	}

	private WaschprobeOrderData setQtyDelivered(final I_PP_Order_BOMLine ppOrderBOMLine, final BigDecimal qtyDelivered)
	{
		//
		// Validate for common mistakes when defining tests: if we are dealing with a co/by-product line the qty shall be negative
		if (qtyDelivered != null
				&& qtyDelivered.signum() > 0
				&& PPOrderUtil.isCoOrByProduct(ppOrderBOMLine))
		{
			throw new IllegalArgumentException("Possible testing issue found: when setting Qty Delivered on a co/by product line, the qty shall be negative"
					+ "\n QtyDelivered to set: " + qtyDelivered
					+ "\n Line: " + ppOrderBOMLine);
		}

		//
		// Set QtyDeliveredActual
		ppOrderBOMLine.setQtyDeliveredActual(qtyDelivered);
		InterfaceWrapperHelper.save(ppOrderBOMLine);

		return this;

	}

	public IQualityInspectionOrder createQualityInspectionOrder()
	{
		return QualityInspectionOrderFactory.createQualityInspectionOrder(ppOrder, materialTracking);
	}

	public WaschprobeOrderData refresh()
	{
		InterfaceWrapperHelper.refresh(ppOrder);
		InterfaceWrapperHelper.refresh(ppOrderBOMLine_Carrot_Unwashed);
		InterfaceWrapperHelper.refresh(ppOrderBOMLine_Carrot_Big);
		InterfaceWrapperHelper.refresh(ppOrderBOMLine_Carrot_AnimalFood);

		return this;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	public WaschprobeOrderData assert_Carrot_Washed_QM_QtyDeliveredPercOfRaw(final BigDecimal expectedQM_QtyDeliveredPercOfRaw)
	{
		assertQM_QtyDeliveredPercOfRaw("Carrot washed", ppOrder, expectedQM_QtyDeliveredPercOfRaw);

		return this;
	}

	public WaschprobeOrderData assert_Carrot_Unwashed_QM_QtyDeliveredPercOfRaw(final BigDecimal expectedQM_QtyDeliveredPercOfRaw)
	{
		assertQM_QtyDeliveredPercOfRaw("Carrot unwashed", ppOrderBOMLine_Carrot_Unwashed, expectedQM_QtyDeliveredPercOfRaw);

		return this;
	}

	public WaschprobeOrderData assert_Carrot_Big_QM_QtyDeliveredPercOfRaw(final BigDecimal expectedQM_QtyDeliveredPercOfRaw)
	{
		assertQM_QtyDeliveredPercOfRaw("Carrot big", ppOrderBOMLine_Carrot_Big, expectedQM_QtyDeliveredPercOfRaw);

		return this;
	}

	private void assertQM_QtyDeliveredPercOfRaw(final String qiItemName,
			final IPPOrderQualityFields qiItem,
			final BigDecimal expectedQM_QtyDeliveredPercOfRaw)
	{
		Assert.assertThat("Invalid QM_QtyDeliveredPercOfRaw for " + qiItemName,
				qiItem.getQM_QtyDeliveredPercOfRaw(),
				Matchers.comparesEqualTo(expectedQM_QtyDeliveredPercOfRaw));
	}

	// -------------------------------------------------------------------------------------------------------------------------
	public WaschprobeOrderData assert_Carrot_Washed_QM_QtyDeliveredAvg(final BigDecimal expectedQM_QtyDeliveredAvg)
	{
		assertQM_QtyDeliveredAvg("Carrot washed", ppOrder, expectedQM_QtyDeliveredAvg);

		return this;
	}

	public WaschprobeOrderData assert_Carrot_Unwashed_QM_QtyDeliveredAvg(final BigDecimal expectedQM_QtyDeliveredAvg)
	{
		assertQM_QtyDeliveredAvg("Carrot unwashed", ppOrderBOMLine_Carrot_Unwashed, expectedQM_QtyDeliveredAvg);

		return this;
	}

	public WaschprobeOrderData assert_Carrot_Big_QM_QtyDeliveredAvg(final BigDecimal expectedQM_QtyDeliveredAvg)
	{
		assertQM_QtyDeliveredAvg("Carrot big", ppOrderBOMLine_Carrot_Big, expectedQM_QtyDeliveredAvg);

		return this;
	}

	private void assertQM_QtyDeliveredAvg(final String qiItemName,
			final IPPOrderQualityFields qiItem,
			final BigDecimal expectedQM_QtyDeliveredAvg)
	{
		Assert.assertThat("Invalid QM_QtyDeliveredAvg for " + qiItemName,
				qiItem.getQM_QtyDeliveredAvg(),
				Matchers.comparesEqualTo(expectedQM_QtyDeliveredAvg));
	}

	// -------------------------------------------------------------------------------------------------------------------------

	public WaschprobeOrderData setDateOfProduction(final Timestamp date)
	{
		// see de.metas.materialtracking.qualityBasedInvoicing.impl.QualityInspectionOrder.getDateOfProduction()
		ppOrder.setDateDelivered(date);
		return this;
	}

	public WaschprobeOrderData setDocStatus(final String docStatus)
	{
		ppOrder.setDocStatus(docStatus);
		return this;
	}
}
