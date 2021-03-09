package de.metas.material.planning.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningService;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.exception.MrpException;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.compiere.util.TimeUtil.asDate;

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
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final ProductPlanningService productPlanningService;
	private final ModelProductDescriptorExtractor productDescriptorFactory;

	public PPOrderPojoSupplier(
			@NonNull final ProductPlanningService productPlanningService,
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productPlanningService = productPlanningService;
		this.productDescriptorFactory = productDescriptorFactory;
	}

	public PPOrder supplyPPOrderPojoWithLines(@NonNull final IMaterialRequest request)
	{
		try
		{
			return supplyPPOrderPojoWithLines0(request);
		}
		catch (final RuntimeException e)
		{
			throw new AdempiereException("Caught " + e.getClass().getSimpleName() + " trying to create PPOrder instance for an IMaterialRequest", e)
					.appendParametersToMessage()
					.setParameter("request", request);
		}
	}

	public PPOrder supplyPPOrderPojoWithLines0(@NonNull final IMaterialRequest request)
	{
		final PPOrder ppOrder = supplyPPOrderPojo(request);

		return ppOrder.toBuilder()
				.lines(supplyPPOrderLinePojos(ppOrder))
				.build();
	}

	private PPOrder supplyPPOrderPojo(@NonNull final IMaterialRequest request)
	{
		final IMaterialPlanningContext mrpContext = request.getMrpContext();

		mrpContext.assertContextConsistent();

		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();
		final I_M_Product product = mrpContext.getM_Product();

		final Instant demandDateStartSchedule = request.getDemandDate();
		final Quantity qtyToSupply = request.getQtyToSupply();

		// BOM
		if (productPlanningData.getPP_Product_BOM_ID() <= 0)
		{
			throw new MrpException("@FillMandatory@ @PP_Product_BOM_ID@ ( @M_Product_ID@=" + product.getValue() + ")");
		}

		//
		// Routing (Workflow)
		final PPRoutingId routingId = PPRoutingId.ofRepoIdOrNull(productPlanningData.getAD_Workflow_ID());
		if (routingId == null)
		{
			throw new MrpException("@FillMandatory@ @AD_Workflow_ID@ ( @M_Product_ID@=" + product.getValue() + ")");
		}

		//
		// Calculate duration & Planning dates
		final int durationDays = calculateDurationDays(mrpContext, qtyToSupply.toBigDecimal());
		final Instant dateFinishSchedule = demandDateStartSchedule;

		final Instant dateStartSchedule = dateFinishSchedule.minus(durationDays, ChronoUnit.DAYS);

		final ProductDescriptor productDescriptor = createPPOrderProductDescriptor(mrpContext);

		final ProductId productId = mrpContext.getProductId();
		final Quantity ppOrderQuantity = uomConversionBL.convertToProductUOM(qtyToSupply, productId);

		return PPOrder.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.toRepoId(mrpContext.getClientId()), OrgId.toRepoIdOrAny(mrpContext.getOrgId())))

				// Planning dimension
				.plantId(ResourceId.ofRepoIdOrNull(mrpContext.getPlant_ID()))
				.warehouseId(mrpContext.getWarehouseId())
				.productPlanningId(productPlanningData.getPP_Product_Planning_ID())

				// Product, UOM, ASI
				.productDescriptor(productDescriptor)

				// Dates
				.datePromised(dateFinishSchedule)
				.dateStartSchedule(dateStartSchedule)

				.qtyRequired(ppOrderQuantity.toBigDecimal())

				.orderLineId(request.getMrpDemandOrderLineSOId())
				.bpartnerId(BPartnerId.ofRepoIdOrNull(request.getMrpDemandBPartnerId()))
				.build();
	}

	/**
	 * Creates the "header" product descriptor.
	 * Does not use the given {@code mrpContext}'s product-planning record,
	 * because it might have less specific (or none!) storageAttributesKey.
	 */
	private ProductDescriptor createPPOrderProductDescriptor(final IMaterialPlanningContext mrpContext)
	{
		final AttributeSetInstanceId asiId = mrpContext.getAttributeSetInstanceId();
		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asiId)
				.orElse(AttributesKey.NONE);

		return ProductDescriptor.forProductAndAttributes(
				ProductId.toRepoId(mrpContext.getProductId()),
				attributesKey,
				asiId.getRepoId());
	}

	private int calculateDurationDays(
			@NonNull final IMaterialPlanningContext mrpContext,
			@NonNull final BigDecimal qty)
	{
		final int leadtimeDays = calculateLeadtimeDays(mrpContext, qty);
		return productPlanningService.calculateDurationDays(leadtimeDays, mrpContext.getProductPlanning());
	}

	private int calculateLeadtimeDays(
			@NonNull final IMaterialPlanningContext mrpContext,
			@NonNull final BigDecimal qty)
	{
		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();

		final int leadtimeDays = productPlanningData.getDeliveryTime_Promised().intValueExact();
		if (leadtimeDays > 0)
		{
			// Leadtime was set in Product Planning/ take the leadtime as it is
			return leadtimeDays;
		}

		final PPRoutingId routingId = PPRoutingId.ofRepoId(productPlanningData.getAD_Workflow_ID());
		final ResourceId plantId = ResourceId.ofRepoIdOrNull(productPlanningData.getS_Resource_ID());
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService();
		return routingService.calculateDurationDays(routingId, plantId, qty);
	}

	private List<PPOrderLine> supplyPPOrderLinePojos(@NonNull final PPOrder ppOrder)
	{
		final I_PP_Product_BOM productBOM = retrieveAndVerifyBOM(ppOrder);
		final ImmutableList<I_PP_Product_BOMLine> productBOMLines = productBOMDAO.retrieveLines(productBOM);

		final UomId bomUomId = UomId.ofRepoId(productBOM.getC_UOM_ID());
		final Quantity finishedGoodsQtyRequiredInBOMUOM = extractQtyRequired(ppOrder, bomUomId);

		final ArrayList<PPOrderLine> result = new ArrayList<>();
		for (final I_PP_Product_BOMLine productBomLine : productBOMLines)
		{
			if (!productBOMBL.isValidFromTo(productBomLine, TimeUtil.asDate(ppOrder.getDateStartSchedule())))
			{
				logger.debug("BOM Line skipped because it's not between valid from/to: {}", productBomLine);
				continue;
			}

			final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(productBomLine);

			final boolean receipt = BOMComponentType.ofCode(productBomLine.getComponentType()).isReceipt();

			final PPOrderLine intermediatePPOrderLine = PPOrderLine.builder()
					.productBomLineId(productBomLine.getPP_Product_BOMLine_ID())
					.description(productBomLine.getDescription())
					.productDescriptor(productDescriptor)
					.receipt(receipt)
					.issueOrReceiveDate(receipt ? ppOrder.getDatePromised() : ppOrder.getDateStartSchedule())
					.qtyRequired(BigDecimal.ZERO) // is computed in the next step
					.build();

			final Quantity componentQtyRequired = ppOrderBOMBL.computeQtyRequiredByQtyOfFinishedGoods(intermediatePPOrderLine, finishedGoodsQtyRequiredInBOMUOM);
			final ProductId componentId = extractProductId(intermediatePPOrderLine.getProductDescriptor());
			final Quantity componentQtyRequiredInStockingUOM = uomConversionBL.convertToProductUOM(componentQtyRequired, componentId);

			final PPOrderLine ppOrderLine = intermediatePPOrderLine.withQtyRequired(componentQtyRequiredInStockingUOM.toBigDecimal());

			result.add(ppOrderLine);
		}
		return result;
	}

	@NonNull
	private static ProductId extractProductId(final @NonNull PPOrder ppOrder)
	{
		return extractProductId(ppOrder.getProductDescriptor());
	}

	@NonNull
	private static ProductId extractProductId(@NonNull final ProductDescriptor productDescriptor)
	{
		return ProductId.ofRepoId(productDescriptor.getProductId());
	}

	@NonNull
	private Quantity extractQtyRequired(
			final @NonNull PPOrder ppOrder,
			final @NonNull UomId targetUomId)
	{
		final ProductId finishedGoodsProductId = extractProductId(ppOrder);
		final I_C_UOM finishedGoodsStockingUOM = productBL.getStockUOM(finishedGoodsProductId);
		final Quantity finishedGoodsQtyRequiredInStockingUOM = Quantity.of(ppOrder.getQtyRequired(), finishedGoodsStockingUOM);

		return uomConversionBL.convertQuantityTo(
				finishedGoodsQtyRequiredInStockingUOM,
				finishedGoodsProductId,
				targetUomId);
	}

	private I_PP_Product_BOM retrieveAndVerifyBOM(@NonNull final PPOrder ppOrder)
	{
		final Instant dateStartSchedule = ppOrder.getDateStartSchedule();
		final ProductId ppOrderProductId = extractProductId(ppOrder);

		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoId(ppOrder.getProductPlanningId());
		final I_PP_Product_Planning productPlanning = productPlanningsRepo.getById(productPlanningId);

		final ProductBOMId productBOMId = ProductBOMId.ofRepoId(productPlanning.getPP_Product_BOM_ID());
		final I_PP_Product_BOM productBOM = productBOMDAO.getById(productBOMId);

		return PPOrderUtil.verifyProductBOMAndReturnIt(ppOrderProductId, asDate(dateStartSchedule), productBOM);
	}
}
