/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.service;

import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;

import javax.annotation.Nullable;
import java.util.Optional;

public class CreateOrderCandidateCommand
{
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IProductBOMDAO bomRepo = Services.get(IProductBOMDAO.class);

	private final PPOrderCandidateDAO ppOrderCandidateDAO = SpringContextHolder.instance.getBean(PPOrderCandidateDAO.class);

	private final PPOrderCandidateCreateRequest request;

	@Builder
	public CreateOrderCandidateCommand(@NonNull final PPOrderCandidateCreateRequest request)
	{
		this.request = request;
	}

	public I_PP_Order_Candidate execute()
	{
		// Create PP Order Candidate
		final I_PP_Order_Candidate ppOrderCandidateRecord = InterfaceWrapperHelper.newInstance(I_PP_Order_Candidate.class);

		PPOrderCandidatePojoConverter.setMaterialDispoGroupId(ppOrderCandidateRecord, request.getMaterialDispoGroupId());

		ppOrderCandidateRecord.setPP_Product_Planning_ID(ProductPlanningId.toRepoId(request.getProductPlanningId()));
		ppOrderCandidateRecord.setAD_Org_ID(request.getClientAndOrgId().getOrgId().getRepoId());
		ppOrderCandidateRecord.setS_Resource_ID(request.getPlantId().getRepoId());
		ppOrderCandidateRecord.setM_Warehouse_ID(request.getWarehouseId().getRepoId());

		ppOrderCandidateRecord.setM_Product_ID(request.getProductId().getRepoId());
		ppOrderCandidateRecord.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId().getRepoId());

		final ProductBOMId bomId = getBOMId(request.getProductPlanningId());
		ppOrderCandidateRecord.setPP_Product_BOM_ID(bomId.getRepoId());

		ppOrderCandidateRecord.setDatePromised(TimeUtil.asTimestamp(request.getDatePromised()));
		ppOrderCandidateRecord.setDateStartSchedule(TimeUtil.asTimestamp(request.getDateStartSchedule()));

		final Quantity qtyRounded = request.getQtyRequired().roundToUOMPrecision();

		ppOrderCandidateRecord.setQtyEntered(qtyRounded.toBigDecimal());
		ppOrderCandidateRecord.setC_UOM_ID(qtyRounded.getUomId().getRepoId());

		ppOrderCandidateRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getSalesOrderLineId()));

		ppOrderCandidateDAO.save(ppOrderCandidateRecord);

		Loggables.addLog(
				"Created ppOrderCandidate; PP_Order_Candidate_ID={}",
				ppOrderCandidateRecord.getPP_Order_Candidate_ID());

		return ppOrderCandidateRecord;
	}

	@NonNull
	private ProductBOMId getBOMId(@Nullable final ProductPlanningId productPlanningId)
	{
		if (productPlanningId != null)
		{
			final I_PP_Product_Planning productPlanning = productPlanningsRepo.getById(productPlanningId);

			final Optional<ProductBOMId> productBOMIdFromPlanning = Optional.ofNullable(productPlanning)
					.filter(presentProductPlanning -> presentProductPlanning.getPP_Product_BOMVersions_ID() > 0)
					.map(presentProductPlanning -> ProductBOMVersionsId.ofRepoId(presentProductPlanning.getPP_Product_BOMVersions_ID()))
					.flatMap(bomRepo::getLatestBOMByVersion);

			if (productBOMIdFromPlanning.isPresent())
			{
				return productBOMIdFromPlanning.get();
			}
		}

		final ProductId productId = request.getProductId();

		return bomRepo.getDefaultBOMIdByProductId(productId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @PP_Product_BOM_ID@")
						.appendParametersToMessage()
						.setParameter("request", request)
						.setParameter("productPlanningId", productPlanningId));
	}
}
