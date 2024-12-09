package org.compiere.acct;

<<<<<<< HEAD
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_MovementLine;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
<<<<<<< HEAD
=======
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_MovementLine;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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

<<<<<<< HEAD
	public final int getM_AttributeSetInstanceTo_ID()
	{
		final I_M_MovementLine movementLine = getModel(I_M_MovementLine.class);
		return movementLine.getM_AttributeSetInstanceTo_ID();
	}

	private final OrgId getFromOrgId()
=======
	private OrgId getFromOrgId()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return Services.get(IWarehouseDAO.class).retrieveOrgIdByLocatorId(getM_Locator_ID());
	}

<<<<<<< HEAD
	private final OrgId getToOrgId()
=======
	private OrgId getToOrgId()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return Services.get(IWarehouseDAO.class).retrieveOrgIdByLocatorId(getM_LocatorTo_ID());
	}

	public final int getM_LocatorTo_ID()
	{
		final I_M_MovementLine movementLine = getModel(I_M_MovementLine.class);
		return movementLine.getM_LocatorTo_ID();
	}

	@Value
	@Builder
	private static class MovementLineCostAmounts
	{
<<<<<<< HEAD
		final AggregatedCostAmount outboundCosts;
		final AggregatedCostAmount inboundCosts;
=======
		AggregatedCostAmount outboundCosts;
		AggregatedCostAmount inboundCosts;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public final MoveCostsResult getCreateCosts(@NonNull final AcctSchema as)
	{
		if (isReversalLine())
		{
<<<<<<< HEAD
			final AggregatedCostAmount outboundCosts = services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(getReversalLine_ID()))
					.date(getDateAcct())
					.build());

			final AggregatedCostAmount inboundCosts = services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofInboundMovementLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofInboundMovementLineId(getReversalLine_ID()))
					.date(getDateAcct())
					.build());
=======
			final AggregatedCostAmount outboundCosts = services.createReversalCostDetails(
							CostDetailReverseRequest.builder()
									.acctSchemaId(as.getId())
									.reversalDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(get_ID()))
									.initialDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(getReversalLine_ID()))
									.date(getDateAcctAsInstant())
									.build())
					.toAggregatedCostAmount();

			final AggregatedCostAmount inboundCosts = services.createReversalCostDetails(
							CostDetailReverseRequest.builder()
									.acctSchemaId(as.getId())
									.reversalDocumentRef(CostingDocumentRef.ofInboundMovementLineId(get_ID()))
									.initialDocumentRef(CostingDocumentRef.ofInboundMovementLineId(getReversalLine_ID()))
									.date(getDateAcctAsInstant())
									.build())
					.toAggregatedCostAmount();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

			return MoveCostsResult.builder()
					.outboundCosts(outboundCosts)
					.inboundCosts(inboundCosts)
					.build();
		}
		else
		{
			return services.moveCosts(MoveCostsRequest.builder()
					.acctSchemaId(as.getId())
					.clientId(getClientId())
<<<<<<< HEAD
					.date(getDateAcct())
=======
					.date(getDateAcctAsInstant())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					// .costElement(null) // all cost elements
					.productId(getProductId())
					.attributeSetInstanceId(getAttributeSetInstanceId())
					.qtyToMove(getQty())
					//
					.outboundOrgId(getFromOrgId())
					.outboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(get_ID()))
					//
					.inboundOrgId(getToOrgId())
					.inboundDocumentRef(CostingDocumentRef.ofInboundMovementLineId(get_ID()))
					//
					.build());
		}
	}
}
