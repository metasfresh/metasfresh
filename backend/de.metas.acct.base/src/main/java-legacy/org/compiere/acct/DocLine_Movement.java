package org.compiere.acct;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_M_MovementLine;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostingService;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

class DocLine_Movement extends DocLine<Doc_Movement>
{

	public DocLine_Movement(final I_M_MovementLine movementLine, final Doc_Movement doc)
	{
		super(InterfaceWrapperHelper.getPO(movementLine), doc);

		setQty(Quantity.of(movementLine.getMovementQty(), getProductStockingUOM()), false);
		setReversalLine_ID(movementLine.getReversalLine_ID());
	}

	public final int getM_AttributeSetInstanceTo_ID()
	{
		final I_M_MovementLine movementLine = getModel(I_M_MovementLine.class);
		return movementLine.getM_AttributeSetInstanceTo_ID();
	}

	private final OrgId getFromOrgId()
	{
		return Services.get(IWarehouseDAO.class).retrieveOrgIdByLocatorId(getM_Locator_ID());
	}

	private final OrgId getToOrgId()
	{
		return Services.get(IWarehouseDAO.class).retrieveOrgIdByLocatorId(getM_LocatorTo_ID());
	}

	public final int getM_LocatorTo_ID()
	{
		final I_M_MovementLine movementLine = getModel(I_M_MovementLine.class);
		return movementLine.getM_LocatorTo_ID();
	}

	public final AggregatedCostAmount getCreateInboundCosts(final AcctSchema as)
	{
		final ICostingService costDetailService = Adempiere.getBean(ICostingService.class);

		if (isReversalLine())
		{
			return costDetailService.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofInboundMovementLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofInboundMovementLineId(getReversalLine_ID()))
					.date(getDateAcct())
					.build());
		}
		else
		{
			final AggregatedCostAmount outboundCostResult = getCreateOutboundCosts(as);
			final AggregatedCostAmount inboundCostResult = outboundCostResult.getCostElements()
					.stream()
					.map(costElement -> createInboundCostDetailCreateRequest(as.getId(), costElement, outboundCostResult.getCostAmountForCostElement(costElement).negate()))
					.map(costDetailService::createCostDetail)
					.reduce(AggregatedCostAmount::merge)
					.orElse(null);

			return inboundCostResult;
		}
	}

	public final AggregatedCostAmount getCreateOutboundCosts(final AcctSchema as)
	{
		final ICostingService costDetailService = Adempiere.getBean(ICostingService.class);

		if (isReversalLine())
		{
			return costDetailService.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(getReversalLine_ID()))
					.date(getDateAcct())
					.build());
		}
		else
		{
			return costDetailService.createCostDetail(createOutboundCostDetailCreateRequest(as));
		}
	}

	private CostDetailCreateRequest createOutboundCostDetailCreateRequest(final AcctSchema as)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(as.getId())
				.clientId(getClientId())
				.orgId(getFromOrgId())
				.productId(getProductId())
				.attributeSetInstanceId(getAttributeSetInstanceId())
				.documentRef(CostingDocumentRef.ofOutboundMovementLineId(get_ID()))
				.qty(getQty().negate())
				.amt(CostAmount.zero(as.getCurrencyId())) // expect to be calculated
				.date(getDateAcct())
				.build();
	}

	private CostDetailCreateRequest createInboundCostDetailCreateRequest(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElement costElement,
			@NonNull final CostAmount amt)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(getClientId())
				.orgId(getToOrgId())
				.productId(getProductId())
				.attributeSetInstanceId(getAttributeSetInstanceId())
				.documentRef(CostingDocumentRef.ofInboundMovementLineId(get_ID()))
				.qty(getQty())
				.amt(amt)
				.costElement(costElement)
				.date(getDateAcct())
				.build();
	}

}
