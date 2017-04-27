package de.metas.material.planning.pporder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.logging.LogManager;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.ProductPlanningBL;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.exception.BOMExpiredException;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.PPOrder.PPOrderBuilder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Service
public class PPOrderPojoSupplier
{
	private static final transient Logger logger = LogManager.getLogger(PPOrderPojoSupplier.class);

	private final ProductPlanningBL productPlanningBL;

	public PPOrderPojoSupplier(@NonNull final ProductPlanningBL productPlanningBL)
	{
		this.productPlanningBL = productPlanningBL;
	}

	public PPOrder supplyPPOrderPojo(
			@NonNull final IMaterialRequest request,
			@NonNull final IMRPNotesCollector mrpNotesCollector)
	{
		final IMaterialPlanningContext mrpContext = request.getMRPContext();
		// final IMRPExecutor executor = request.getMRPExecutor();

		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();
		final I_M_Product product = mrpContext.getM_Product();
		final I_C_UOM uom = mrpContext.getC_UOM();
		final Timestamp demandDateStartSchedule = TimeUtil.asTimestamp(request.getDemandDate());
		final BigDecimal qtyToSupply = request.getQtyToSupply();

		//
		// BOM
		final int ppProductBomId = productPlanningData.getPP_Product_BOM_ID();
		if (ppProductBomId <= 0)
		{
			throw new MrpException("@FillMandatory@ @PP_Product_BOM_ID@ ( @M_Product_ID@=" + product.getValue() + ")");
		}

		//
		// Routing (Workflow)
		final int adWorkflowId = productPlanningData.getAD_Workflow_ID();
		if (adWorkflowId <= 0)
		{
			throw new MrpException("@FillMandatory@ @AD_Workflow_ID@ ( @M_Product_ID@=" + product.getValue() + ")");
		}

		//
		// Calculate duration & Planning dates
		final int durationDays = calculateDurationDays(mrpContext, qtyToSupply);
		final Timestamp dateFinishSchedule = demandDateStartSchedule;

		final Timestamp dateStartSchedule = TimeUtil.addDays(dateFinishSchedule, -durationDays);

		final PPOrderBuilder ppOrderPojoBuilder = PPOrder.builder()
				.orgId(mrpContext.getAD_Org_ID())
				//
				// Planning dimension
				.plantId(mrpContext.getPlant_ID())
				.warehouseId(mrpContext.getM_Warehouse_ID())
				.plannerId(productPlanningData.getPlanner_ID())
				//
				// Product, UOM, ASI
				.productId(product.getM_Product_ID())
				.uomId(uom.getC_UOM_ID())
				//
				// BOM & Workflow
				.productBomId(ppProductBomId)
				.workflowId(adWorkflowId)
				//
				// Dates
				.dateOrdered(mrpContext.getDateAsTimestamp())
				.datePromised(dateFinishSchedule)
				.dateStartSchedule(dateStartSchedule)
				//
				// Qtys
				.quantity(qtyToSupply)

				.productBomUomId(productPlanningData.getPP_Product_BOM().getC_UOM_ID()); // we store this within the ppOrderPojo because it's the only item we currently need from PP_Order_BOM
		;

		return ppOrderPojoBuilder.build();
	}

	public PPOrder supplyPPOrderPojoWithLines(
			@NonNull final IMaterialRequest request,
			@NonNull final IMRPNotesCollector mrpNotesCollector)
	{
		final PPOrder ppOrderPojo = supplyPPOrderPojo(request, mrpNotesCollector);
		return ppOrderPojo.withLines(supplyPPOrderLinePojos(ppOrderPojo));
	}

	private int calculateDurationDays(
			@NonNull final IMaterialPlanningContext mrpContext,
			@NonNull final BigDecimal qty)
	{
		final int leadtimeDays = calculateLeadtimeDays(mrpContext, qty);
		final int durationTotalDays = productPlanningBL.calculateDurationDays(leadtimeDays, mrpContext.getProductPlanning());
		return durationTotalDays;
	}

	private int calculateLeadtimeDays(
			@NonNull final IMaterialPlanningContext mrpContext,
			@NonNull final BigDecimal qty)
	{
		final Properties ctx = mrpContext.getCtx();
		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();

		final int leadtimeDays = productPlanningData.getDeliveryTime_Promised().intValueExact();
		if (leadtimeDays > 0)
		{
			// Leadtime was set in Product Planning
			// take the leadtime as it is
			return leadtimeDays;
		}

		final I_AD_Workflow adWorkflow = productPlanningData.getAD_Workflow();
		final I_S_Resource plant = productPlanningData.getS_Resource();
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(ctx);
		final BigDecimal leadtimeCalc = routingService.calculateDuration(adWorkflow, plant, qty);
		return leadtimeCalc.intValueExact();
	}

	public PPOrder of(@NonNull final I_PP_Order ppOrder)
	{
		return PPOrder.builder()
				.dateOrdered(ppOrder.getDateOrdered())
				.datePromised(ppOrder.getDatePromised())
				.dateStartSchedule(ppOrder.getDateStartSchedule())
				.orgId(ppOrder.getAD_Org_ID())
				.plannerId(ppOrder.getPlanner_ID())
				.plantId(ppOrder.getS_Resource_ID())
				.productBomId(ppOrder.getPP_Product_BOM_ID())
				.productId(ppOrder.getM_Product_ID())
				.quantity(ppOrder.getQtyOrdered())
				.productBomUomId(ppOrder.getPP_Product_BOM().getC_UOM_ID())
				.uomId(ppOrder.getC_UOM_ID())
				.warehouseId(ppOrder.getM_Warehouse_ID())
				.workflowId(ppOrder.getAD_Workflow_ID())
				.build();
	}

	public PPOrderLine of(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		return PPOrderLine.builder()
				.assay(ppOrderBOMLine.getAssay())
				.attributeSetInstanceId(ppOrderBOMLine.getM_AttributeSetInstance_ID())
				.backflushGroup(ppOrderBOMLine.getBackflushGroup())
				.changeNoticeId(ppOrderBOMLine.getM_ChangeNotice_ID())
				.componentType(ppOrderBOMLine.getComponentType())
				.critical(ppOrderBOMLine.isCritical())
				.description(ppOrderBOMLine.getDescription())
				.forecast(ppOrderBOMLine.getForecast())
				.help(ppOrderBOMLine.getHelp())
				.issueMethod(ppOrderBOMLine.getIssueMethod())
				.leadTimeOffset(ppOrderBOMLine.getLeadTimeOffset())
				.productId(ppOrderBOMLine.getM_Product_ID())
				.qtyBatch(ppOrderBOMLine.getQtyBatch())
				.qtyBOM(ppOrderBOMLine.getQtyBOM())
				.qtyPercentage(ppOrderBOMLine.isQtyPercentage())
				.qtyRequired(ppOrderBOMLine.getQtyRequiered())
				.scrap(ppOrderBOMLine.getScrap())
				.uomId(ppOrderBOMLine.getC_UOM_ID())
				.validFrom(ppOrderBOMLine.getValidFrom())
				.validTo(ppOrderBOMLine.getValidTo())
				.variantGroup(ppOrderBOMLine.getVariantGroup())
				.build();
	}

	public List<PPOrderLine> supplyPPOrderLinePojos(@NonNull final PPOrder ppOrder)
	{
		final I_PP_Product_BOM productBOM = retriveAndVerifyBOM(ppOrder);

		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);

		final List<PPOrderLine> result = new ArrayList<>();

		final List<I_PP_Product_BOMLine> productBOMLines = Services.get(IProductBOMDAO.class).retrieveLines(productBOM);
		for (final I_PP_Product_BOMLine bomLine : productBOMLines)
		{
			if (!productBOMBL.isValidFromTo(bomLine, ppOrder.getDateStartSchedule()))
			{
				logger.debug("BOM Line skiped - " + bomLine);
				continue;
			}

			final PPOrderLine ppOrderLine = PPOrderLine.builder()
					// this logic was taken from /de.metas.adempiere.libero.libero/src/main/java/org/eevolution/api/impl/PPOrderBOMBL.java method updateOrderBOMLine()
					.changeNoticeId(bomLine.getM_ChangeNotice_ID())
					.description(bomLine.getDescription())
					.help(bomLine.getHelp())
					.assay(bomLine.getAssay())
					.qtyBatch(bomLine.getQtyBatch())
					.qtyBOM(bomLine.getQtyBOM())
					.qtyPercentage(bomLine.isQtyPercentage())
					.componentType(bomLine.getComponentType())
					.uomId(bomLine.getC_UOM_ID())
					.forecast(bomLine.getForecast())
					.critical(bomLine.isCritical())
					.issueMethod(bomLine.getIssueMethod())
					.leadTimeOffset(bomLine.getLeadTimeOffset())
					.productId(bomLine.getM_Product_ID())
					.scrap(bomLine.getScrap())
					.validFrom(bomLine.getValidFrom())
					.validTo(bomLine.getValidTo())
					.backflushGroup(bomLine.getBackflushGroup())
					.variantGroup(bomLine.getVariantGroup()) // 06005
					.attributeSetInstanceId(bomLine.getM_AttributeSetInstance_ID())
					.qtyRequired(BigDecimal.ZERO) // is computed in the next step
					.build();

			final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
			final BigDecimal qtyRequired = ppOrderBOMBL.calculateQtyRequired(ppOrderLine, ppOrder, ppOrder.getQuantity());

			result.add(ppOrderLine.withQtyRequired(qtyRequired));
		}

		return result;
	}

	public I_PP_Product_BOM retriveAndVerifyBOM(@NonNull final PPOrder ppOrder)
	{
		final I_PP_Product_BOM productBOM = InterfaceWrapperHelper.create(Env.getCtx(), ppOrder.getProductBomId(), I_PP_Product_BOM.class, ITrx.TRXNAME_None);

		// Product from Order should be same as product from BOM - teo_sarca [ 2817870 ]
		if (ppOrder.getProductId() != productBOM.getM_Product_ID())
		{
			throw new MrpException("@NotMatch@ @PP_Product_BOM_ID@ , @M_Product_ID@");
		}

		// Product BOM Configuration should be verified - teo_sarca [ 2817870 ]
		final I_M_Product product = productBOM.getM_Product();
		if (!product.isVerified())
		{
			throw new MrpException("Product BOM Configuration not verified. Please verify the product first - " + product.getValue()); // TODO: translate
		}

		//
		// Create BOM Head
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		final Date dateStartSchedule = ppOrder.getDateStartSchedule();
		if (!productBOMBL.isValidFromTo(productBOM, dateStartSchedule))
		{
			throw new BOMExpiredException(productBOM, dateStartSchedule);
		}

		return productBOM;
	}
}
