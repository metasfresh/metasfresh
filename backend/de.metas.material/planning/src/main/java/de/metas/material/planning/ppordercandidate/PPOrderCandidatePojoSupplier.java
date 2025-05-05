/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.planning.ppordercandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.IdConstants;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ProductPlanningService;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.service.ClientId;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.ProductBOMVersionsId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.eevolution.model.X_PP_Order_Candidate.ISLOTFORLOT_No;
import static org.eevolution.model.X_PP_Order_Candidate.ISLOTFORLOT_Yes;

@Service
public class PPOrderCandidatePojoSupplier
{
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);

	private final ProductPlanningService productPlanningService;
	private final OrderLineRepository orderLineRepository;

	public PPOrderCandidatePojoSupplier(
			@NonNull final ProductPlanningService productPlanningService,
			@NonNull final OrderLineRepository orderLineRepository)
	{
		this.productPlanningService = productPlanningService;
		this.orderLineRepository = orderLineRepository;
	}

	@NonNull
	public PPOrderCandidate supplyPPOrderCandidatePojoWithoutLines(@NonNull final IMaterialRequest request)
	{
		try
		{
			return supplyPPOrderCandidatePojo(request);
		}
		catch (final RuntimeException e)
		{
			throw new AdempiereException("Caught " + e.getClass().getSimpleName() + " trying to create PPOrderCandidate instance for an IMaterialRequest", e)
					.appendParametersToMessage()
					.setParameter("request", request);
		}
	}

	private PPOrderCandidate supplyPPOrderCandidatePojo(@NonNull final IMaterialRequest request)
	{
		final IMaterialPlanningContext mrpContext = request.getMrpContext();

		mrpContext.assertContextConsistent();

		final ProductPlanning productPlanningData = mrpContext.getProductPlanning();
		final ProductId productId = mrpContext.getProductId();

		final Quantity qtyToSupply = request.getQtyToSupply();

		// BOM
		if (productPlanningData.getBomVersionsId() == null)
		{
			throw new MrpException("@FillMandatory@ @PP_Product_BOMVersions_ID@ ( @M_Product_ID@=" + productId + ")");
		}
		final ProductBOMVersionsId bomVersionsId = productPlanningData.getBomVersionsId();

		productBOMDAO.getLatestBOMIdByVersionAndType(bomVersionsId, PPOrderDocBaseType.MANUFACTURING_ORDER.getBOMTypes())
				.orElseThrow(() -> new MrpException("@FillMandatory@ @PP_Product_BOM_ID@ ( @M_Product_ID@=" + productId + ")"));

		//
		// Routing (Workflow)
		final PPRoutingId routingId = productPlanningData.getWorkflowId();
		if (routingId == null)
		{
			throw new MrpException("@FillMandatory@ @AD_Workflow_ID@ ( @M_Product_ID@=" + productId + ")");
		}

		//
		// Calculate duration & Planning dates
		final int durationDays = productPlanningService.calculateDurationDays(mrpContext.getProductPlanning(), qtyToSupply.toBigDecimal());

		final Instant earliestDateStartSchedule = SystemTime.asInstant();

		final Instant datePromised;
		final Instant dateStartSchedule;

		if (request.getDemandDate().minus(durationDays, ChronoUnit.DAYS).isBefore(earliestDateStartSchedule))
		{
			dateStartSchedule = earliestDateStartSchedule;
			datePromised = dateStartSchedule.plus(durationDays, ChronoUnit.DAYS);
		}
		else
		{
			datePromised = request.getDemandDate();
			dateStartSchedule = datePromised.minus(durationDays, ChronoUnit.DAYS);
		}

		final ProductDescriptor productDescriptor = createPPOrderCandidateProductDescriptor(mrpContext);

		final Quantity ppOrderCandidateQuantity = uomConversionBL.convertToProductUOM(qtyToSupply, productId);

		final int orderLineId = request.getMrpDemandOrderLineSOId();

		final String isLotForLot = productPlanningData.isLotForLot() ? ISLOTFORLOT_Yes : ISLOTFORLOT_No;

		return PPOrderCandidate.builder()
				.simulated(request.isSimulated())
				.ppOrderData(PPOrderData.builder()
									 .clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.toRepoId(mrpContext.getClientId()), OrgId.toRepoIdOrAny(mrpContext.getOrgId())))
									 .plantId(mrpContext.getPlantId())
									 .warehouseId(mrpContext.getWarehouseId())
									 .productPlanningId(ProductPlanningId.toRepoId(productPlanningData.getId()))
									 .productDescriptor(productDescriptor)
									 .datePromised(datePromised)
									 .dateStartSchedule(dateStartSchedule)
									 .qtyRequired(ppOrderCandidateQuantity.toBigDecimal())
									 .orderLineId(orderLineId)
									 .shipmentScheduleId(request.getMrpDemandShipmentScheduleId())
									 .bpartnerId(BPartnerId.ofRepoIdOrNull(request.getMrpDemandBPartnerId()))
									 .packingMaterialId(getPackingMaterialId(orderLineId))
									 .lotForLot(isLotForLot)
									 .build())
				.build();
	}

	/**
	 * Creates the "header" product descriptor.
	 * Does not use the given {@code mrpContext}'s product-planning record,
	 * because it might have less specific (or none!) storageAttributesKey.
	 */
	@NonNull
	private ProductDescriptor createPPOrderCandidateProductDescriptor(final IMaterialPlanningContext mrpContext)
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

	@Nullable
	private HUPIItemProductId getPackingMaterialId(final int demandOrderLineId)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(IdConstants.toRepoId(demandOrderLineId));

		return Optional.ofNullable(orderLineId)
				.map(orderLineRepository::getById)
				.map(OrderLine::getHuPIItemProductId)
				.orElse(null);
	}
}