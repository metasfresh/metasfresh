/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
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

package org.eevolution.productioncandidate.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.eevolution.productioncandidate.service.PPOrderCandidateCreateUpdateRequest;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;

@Interceptor(I_C_Order.class)
@RequiredArgsConstructor
public class C_Order
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
    @NonNull private final PPOrderCandidateService ppOrderCandidateService;

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	private void afterComplete(@NonNull final I_C_Order order)
	{
		orderBL.getLinesByOrderIds(ImmutableSet.of(OrderId.ofRepoId(order.getC_Order_ID()))).forEach(orderLine -> createPPOrderCandidates(order, orderLine));
	}

	private void createPPOrderCandidates(@NonNull final I_C_Order order, @NonNull final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(orderLine.getAD_Org_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());

		final IProductPlanningDAO.ProductPlanningQuery query = IProductPlanningDAO.ProductPlanningQuery.builder()
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				.build();

		final ProductPlanning productPlanning = productPlanningDAO.find(query).orElse(null);
		if (productPlanning == null)
		{
			return;
		}
		if (productPlanning.isPickingOrder() || !productPlanning.isManufactured() || !productPlanning.isManufacturedLot4Lot())
		{
			return;
		}
		ppOrderCandidateService.createUpdateCandidate(PPOrderCandidateCreateUpdateRequest.builder()
						.plantId(productPlanning.getPlantId())
						.qtyRequired(Quantitys.of(orderLine.getQtyOrdered(), productId))
						.productPlanningId(productPlanning.getId())
						.dateStartSchedule(order.getPreparationDate().toInstant())
						.datePromised(order.getDatePromised().toInstant())
						.productId(productId)
						.attributeSetInstanceId(asiId)
						.salesOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.build());
	}
}
