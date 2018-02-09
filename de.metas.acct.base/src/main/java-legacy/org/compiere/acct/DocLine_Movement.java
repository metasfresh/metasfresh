package org.compiere.acct;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_MovementLine;
import org.compiere.util.TimeUtil;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostResult;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailService;
import de.metas.quantity.Quantity;

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

	private final int getFromOrgId()
	{
		return Services.get(IWarehouseDAO.class).retrieveOrgIdByLocatorId(getM_Locator_ID());
	}

	private final int getToOrgId()
	{
		return Services.get(IWarehouseDAO.class).retrieveOrgIdByLocatorId(getM_LocatorTo_ID());
	}

	public final int getM_LocatorTo_ID()
	{
		final I_M_MovementLine movementLine = getModel(I_M_MovementLine.class);
		return movementLine.getM_LocatorTo_ID();
	}

	public final CostResult getCreateInboundCosts(final I_C_AcctSchema as)
	{
		final ICostDetailService costDetailService = Adempiere.getBean(ICostDetailService.class);

		if (isReversalLine())
		{
			return costDetailService.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.reversalDocumentRef(CostingDocumentRef.ofInboundMovementLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofInboundMovementLineId(getReversalLine_ID()))
					.date(TimeUtil.asLocalDate(getDateDoc()))
					.build());
		}
		else
		{
			final CostResult outboundCostResult = getCreateOutboundCosts(as);
			final CostResult inboundCostResult = outboundCostResult.getCostElements()
					.stream()
					.map(costElement -> createInboundCostDetailCreateRequest(as, costElement, outboundCostResult.getCostAmountForCostElement(costElement)))
					.map(costDetailService::createCostDetail)
					.reduce(CostResult::merge)
					.orElse(null);

			return inboundCostResult;
		}
	}

	public final CostResult getCreateOutboundCosts(final I_C_AcctSchema as)
	{
		final ICostDetailService costDetailService = Adempiere.getBean(ICostDetailService.class);

		if (isReversalLine())
		{
			return costDetailService.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.reversalDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(getReversalLine_ID()))
					.date(TimeUtil.asLocalDate(getDateDoc()))
					.build());
		}
		else
		{
			return costDetailService.createCostDetail(createOutboundCostDetailCreateRequest(as));
		}
	}

	private CostDetailCreateRequest createOutboundCostDetailCreateRequest(final I_C_AcctSchema as)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(as.getC_AcctSchema_ID())
				.clientId(getAD_Client_ID())
				.orgId(getFromOrgId())
				.productId(getM_Product_ID())
				.attributeSetInstanceId(getM_AttributeSetInstance_ID())
				.documentRef(CostingDocumentRef.ofOutboundMovementLineId(get_ID()))
				.qty(getQty().negate())
				.amt(CostAmount.zero(as.getC_Currency_ID())) // expect to be calculated
				.date(TimeUtil.asLocalDate(getDateAcct()))
				.build();
	}

	private CostDetailCreateRequest createInboundCostDetailCreateRequest(final I_C_AcctSchema as, final CostElement costElement, final CostAmount amt)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(as.getC_AcctSchema_ID())
				.clientId(getAD_Client_ID())
				.orgId(getToOrgId())
				.productId(getM_Product_ID())
				.attributeSetInstanceId(getM_AttributeSetInstanceTo_ID())
				.documentRef(CostingDocumentRef.ofInboundMovementLineId(get_ID()))
				.qty(getQty())
				.amt(amt)
				.costElement(costElement)
				.date(TimeUtil.asLocalDate(getDateAcct()))
				.build();
	}

}
