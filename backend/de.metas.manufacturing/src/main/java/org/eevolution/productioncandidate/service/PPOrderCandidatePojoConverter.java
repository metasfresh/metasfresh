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

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_Candidate;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class PPOrderCandidatePojoConverter
{
	private static final ModelDynAttributeAccessor<I_PP_Order_Candidate, MaterialDispoGroupId> //
			ATTR_PPORDER_CANDIDATE_REQUESTED_EVENT_GROUP_ID = new ModelDynAttributeAccessor<>(I_PP_Order_Candidate.class.getName(), "PPOrderCandidateRequestedEvent_GroupId", MaterialDispoGroupId.class);

	@Nullable
	public static MaterialDispoGroupId getMaterialDispoGroupIdOrNull(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		return ATTR_PPORDER_CANDIDATE_REQUESTED_EVENT_GROUP_ID.getValue(ppOrderCandidateRecord);
	}

	public static void setMaterialDispoGroupId(
			@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord,
			@Nullable final MaterialDispoGroupId materialDispoGroupId)
	{
		ATTR_PPORDER_CANDIDATE_REQUESTED_EVENT_GROUP_ID.setValue(ppOrderCandidateRecord, materialDispoGroupId);
	}

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@NonNull
	public PPOrderCandidate toPPOrderCandidate(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final I_C_UOM uom = uomDAO.getById(ppOrderCandidateRecord.getC_UOM_ID());
		final Quantity qtyEntered = Quantity.of(ppOrderCandidateRecord.getQtyEntered(), uom);
		final Quantity qtyProcessed = Quantity.of(ppOrderCandidateRecord.getQtyProcessed(), uom);
		final ProductId productId = ProductId.ofRepoId(ppOrderCandidateRecord.getM_Product_ID());

		final Quantity qtyEnteredInStockUOM = uomConversionBL.convertToProductUOM(qtyEntered, productId);
		final Quantity qtyProcessedInStockUOM = uomConversionBL.convertToProductUOM(qtyProcessed, productId);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNull(ppOrderCandidateRecord.getM_AttributeSetInstance_ID());

		return PPOrderCandidate.builder()
				.ppOrderCandidateId(ppOrderCandidateRecord.getPP_Order_Candidate_ID())
				.ppOrderData(PPOrderData.builder()
									 .clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ppOrderCandidateRecord.getAD_Client_ID(), ppOrderCandidateRecord.getAD_Org_ID()))
									 .warehouseId(WarehouseId.ofRepoId(ppOrderCandidateRecord.getM_Warehouse_ID()))
									 .productPlanningId(ppOrderCandidateRecord.getPP_Product_Planning_ID())
									 .productDescriptor(extractProductDescriptor(productId, asiId))
									 .orderLineId(ppOrderCandidateRecord.getC_OrderLine_ID())
									 .datePromised(TimeUtil.asInstantNonNull(ppOrderCandidateRecord.getDatePromised()))
									 .dateStartSchedule(TimeUtil.asInstantNonNull(ppOrderCandidateRecord.getDateStartSchedule()))
									 .qtyRequired(qtyEnteredInStockUOM.toBigDecimal())
									 .qtyDelivered(qtyProcessedInStockUOM.toBigDecimal())
									 .plantId(ResourceId.ofRepoId(ppOrderCandidateRecord.getS_Resource_ID()))
									 .materialDispoGroupId(getMaterialDispoGroupIdOrNull(ppOrderCandidateRecord))
									 .build())
				.build();
	}

	@NonNull
	private ProductDescriptor extractProductDescriptor(
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId)
	{
		final AttributeSetInstanceId asiIdOrNone = Optional.ofNullable(attributeSetInstanceId)
				.orElse(AttributeSetInstanceId.NONE);

		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asiIdOrNone)
				.orElse(AttributesKey.NONE);

		return ProductDescriptor.forProductAndAttributes(
				ProductId.toRepoId(productId),
				attributesKey,
				asiIdOrNone.getRepoId());
	}
}