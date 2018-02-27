package de.metas.material.planning.pporder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.logging.LogManager;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrder.PPOrderBuilder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.ProductPlanningBL;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.exception.MrpException;
import de.metas.quantity.Quantity;
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

	private final ModelProductDescriptorExtractor productDescriptorFactory;

	public PPOrderPojoSupplier(
			@NonNull final ProductPlanningBL productPlanningBL,
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productPlanningBL = productPlanningBL;
		this.productDescriptorFactory = productDescriptorFactory;
	}

	public PPOrder supplyPPOrderPojo(
			@NonNull final IMaterialRequest request,
			@NonNull final IMRPNotesCollector mrpNotesCollector)
	{
		final IMaterialPlanningContext mrpContext = request.getMrpContext();

		mrpContext.assertContextConsistent();

		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();
		final I_M_Product product = mrpContext.getM_Product();

		final Timestamp demandDateStartSchedule = TimeUtil.asTimestamp(request.getDemandDate());
		final Quantity qtyToSupply = request.getQtyToSupply();

		// BOM
		if (productPlanningData.getPP_Product_BOM_ID() <= 0)
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
		final int durationDays = calculateDurationDays(mrpContext, qtyToSupply.getQty());
		final Timestamp dateFinishSchedule = demandDateStartSchedule;

		final Timestamp dateStartSchedule = TimeUtil.addDays(dateFinishSchedule, -durationDays);

		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(productPlanningData);

		final BigDecimal ppOrderQuantity = Services.get(IUOMConversionBL.class)
				.convertToProductUOM(qtyToSupply, mrpContext.getM_Product());

		final PPOrderBuilder ppOrderPojoBuilder = PPOrder.builder()
				.orgId(mrpContext.getAD_Org_ID())

				// Planning dimension
				.plantId(mrpContext.getPlant_ID())
				.warehouseId(mrpContext.getM_Warehouse_ID())
				.productPlanningId(productPlanningData.getPP_Product_Planning_ID())

				// Product, UOM, ASI
				.productDescriptor(productDescriptor)

				// Dates
				.datePromised(dateFinishSchedule)
				.dateStartSchedule(dateStartSchedule)

				.quantity(ppOrderQuantity)

				.orderLineId(request.getMrpDemandOrderLineSOId())
				.bPartnerId(request.getMrpDemandBPartnerId());

		return ppOrderPojoBuilder.build();
	}

	public PPOrder supplyPPOrderPojoWithLines(
			@NonNull final IMaterialRequest request,
			@NonNull final IMRPNotesCollector mrpNotesCollector)
	{
		final PPOrder ppOrder = supplyPPOrderPojo(request, mrpNotesCollector);

		final PPOrder ppOrderWithLines = ppOrder.toBuilder()
				.lines(supplyPPOrderLinePojos(ppOrder)).build();

		return ppOrderWithLines;
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
			// Leadtime was set in Product Planning/ take the leadtime as it is
			return leadtimeDays;
		}

		final I_AD_Workflow adWorkflow = productPlanningData.getAD_Workflow();
		final I_S_Resource plant = productPlanningData.getS_Resource();
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(ctx);
		final BigDecimal leadtimeCalc = routingService.calculateDurationDays(adWorkflow, plant, qty);
		return leadtimeCalc.intValueExact();
	}

	public List<PPOrderLine> supplyPPOrderLinePojos(@NonNull final PPOrder ppOrder)
	{
		final I_PP_Product_BOM productBOM = retriveAndVerifyBOM(ppOrder);

		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);

		final List<PPOrderLine> result = new ArrayList<>();

		final List<I_PP_Product_BOMLine> productBOMLines = Services.get(IProductBOMDAO.class).retrieveLines(productBOM);
		for (final I_PP_Product_BOMLine productBomLine : productBOMLines)
		{
			if (!productBOMBL.isValidFromTo(productBomLine, ppOrder.getDateStartSchedule()))
			{
				logger.debug("BOM Line skiped - " + productBomLine);
				continue;
			}

			final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(productBomLine);

			final boolean receipt = PPOrderUtil.isReceipt(productBomLine.getComponentType());

			final PPOrderLine intermedidatePPOrderLine = PPOrderLine.builder()
					.productBomLineId(productBomLine.getPP_Product_BOMLine_ID())
					.description(productBomLine.getDescription())
					.productDescriptor(productDescriptor)
					.receipt(receipt)
					.issueOrReceiveDate(receipt ? ppOrder.getDatePromised() : ppOrder.getDateStartSchedule())
					.qtyRequired(BigDecimal.ZERO) // is computed in the next step
					.build();

			final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
			final BigDecimal qtyRequired = ppOrderBOMBL.calculateQtyRequired(intermedidatePPOrderLine, ppOrder, ppOrder.getQuantity());

			final PPOrderLine ppOrderLine = intermedidatePPOrderLine.toBuilder()
					.qtyRequired(qtyRequired).build();

			result.add(ppOrderLine);
		}

		return result;
	}

	private I_PP_Product_BOM retriveAndVerifyBOM(@NonNull final PPOrder ppOrder)
	{
		final Date dateStartSchedule = ppOrder.getDateStartSchedule();
		final Integer ppOrderProductId = ppOrder.getProductDescriptor().getProductId();

		final I_PP_Product_BOM productBOM = InterfaceWrapperHelper
				.create(Env.getCtx(), ppOrder.getProductPlanningId(), I_PP_Product_Planning.class, ITrx.TRXNAME_None)
				.getPP_Product_BOM();

		return PPOrderUtil.verifyProductBOM(ppOrderProductId, dateStartSchedule, productBOM);
	}
}
