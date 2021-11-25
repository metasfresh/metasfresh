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
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.ProductPlanningService;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class PPOrderCandidatePojoSupplier
{
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);

	private final ProductPlanningService productPlanningService;

	public PPOrderCandidatePojoSupplier(@NonNull final ProductPlanningService productPlanningService)
	{
		this.productPlanningService = productPlanningService;
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

		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();
		final I_M_Product product = mrpContext.getM_Product();

		final Instant demandDate = request.getDemandDate();
		final Quantity qtyToSupply = request.getQtyToSupply();

		// BOM
		if (productPlanningData.getPP_Product_BOMVersions_ID() <= 0)
		{
			throw new MrpException("@FillMandatory@ @PP_Product_BOMVersions_ID@ ( @M_Product_ID@=" + product.getValue() + ")");
		}

		final ProductBOMVersionsId bomVersionsId = ProductBOMVersionsId.ofRepoId(productPlanningData.getPP_Product_BOMVersions_ID());

		productBOMDAO.getLatestBOMByVersion(bomVersionsId)
				.orElseThrow(() -> new MrpException("@FillMandatory@ @PP_Product_BOM_ID@ ( @M_Product_ID@=" + product.getValue() + ")"));

		//
		// Routing (Workflow)
		final PPRoutingId routingId = PPRoutingId.ofRepoIdOrNull(productPlanningData.getAD_Workflow_ID());
		if (routingId == null)
		{
			throw new MrpException("@FillMandatory@ @AD_Workflow_ID@ ( @M_Product_ID@=" + product.getValue() + ")");
		}

		//
		// Calculate duration & Planning dates
		final int durationDays = productPlanningService.calculateDurationDays(mrpContext.getProductPlanning(), qtyToSupply.toBigDecimal());

		final Instant dateStartSchedule = demandDate.minus(durationDays, ChronoUnit.DAYS);

		final ProductDescriptor productDescriptor = createPPOrderCandidateProductDescriptor(mrpContext);

		final ProductId productId = mrpContext.getProductId();
		final Quantity ppOrderCandidateQuantity = uomConversionBL.convertToProductUOM(qtyToSupply, productId);

		return PPOrderCandidate.builder()
				.ppOrderData(PPOrderData.builder()
									 .clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.toRepoId(mrpContext.getClientId()), OrgId.toRepoIdOrAny(mrpContext.getOrgId())))
									 .plantId(ResourceId.ofRepoId(mrpContext.getPlant_ID()))
									 .warehouseId(mrpContext.getWarehouseId())
									 .productPlanningId(productPlanningData.getPP_Product_Planning_ID())
									 .productDescriptor(productDescriptor)
									 .datePromised(demandDate)
									 .dateStartSchedule(dateStartSchedule)
									 .qtyRequired(ppOrderCandidateQuantity.toBigDecimal())
									 .orderLineId(request.getMrpDemandOrderLineSOId())
									 .bpartnerId(BPartnerId.ofRepoIdOrNull(request.getMrpDemandBPartnerId()))
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
}