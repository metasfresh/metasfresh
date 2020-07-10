package org.compiere.acct;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MAccount;
import org.compiere.util.DB;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.inout.InOutLineId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import lombok.Builder;
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

class DocLine_InOut extends DocLine<Doc_InOut>
{
	/** Outside Processing */
	private Integer ppCostCollectorId = null;

	@Builder
	private DocLine_InOut(
			@NonNull final I_M_InOutLine inoutLine,
			@NonNull final Doc_InOut doc)
	{
		super(InterfaceWrapperHelper.getPO(inoutLine), doc);

		final Quantity qty = Quantity.of(inoutLine.getMovementQty(), getProductStockingUOM());
		setQty(qty, doc.isSOTrx());
	}

	public InOutLineId getInOutLineId()
	{
		return InOutLineId.ofRepoId(get_ID());
	}

	private final int getPP_Cost_Collector_ID()
	{
		if (ppCostCollectorId == null)
		{
			ppCostCollectorId = retrievePPCostCollectorId();
		}
		return ppCostCollectorId;
	}

	private final int retrievePPCostCollectorId()
	{
		final OrderLineId orderLineId = getOrderLineId();
		if (orderLineId != null)
		{
			final String sql = "SELECT " + I_C_OrderLine.COLUMNNAME_PP_Cost_Collector_ID
					+ " FROM C_OrderLine WHERE C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
			return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, new Object[] { orderLineId });
		}

		return 0;
	}

	public final I_C_OrderLine getOrderLineOrNull()
	{
		return getModel(I_M_InOutLine.class)
				.getC_OrderLine();
	}

	/**
	 * @return order's org if defined, else doc line's org
	 */
	public final OrgId getOrderOrgId()
	{
		final I_C_OrderLine orderLine = getOrderLineOrNull();
		return orderLine != null
				? OrgId.ofRepoId(orderLine.getAD_Org_ID())
				: getOrgId();
	}

	public MAccount getProductAssetAccount(final AcctSchema as)
	{
		if (isItem())
		{
			return getAccount(ProductAcctType.Asset, as);
		}
		// if the line is a Outside Processing then DR WIP
		else if (getPP_Cost_Collector_ID() > 0)
		{
			return getAccount(ProductAcctType.WorkInProcess, as);
		}
		else
		{
			return getAccount(ProductAcctType.Expense, as);
		}
	}

	public CostAmount getCreateReceiptCosts(final AcctSchema as)
	{
		if (isReversalLine())
		{
			return services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofReceiptLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofReceiptLineId(getReversalLine_ID()))
					.date(getDateAcct())
					.build())
					.getTotalAmountToPost(as);
		}
		else
		{
			return services.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(as.getId())
							.clientId(getClientId())
							.orgId(getOrgId())
							.productId(getProductId())
							.attributeSetInstanceId(getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofReceiptLineId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getCurrencyId())) // N/A
							.date(getDateAcct())
							.build())
					.getTotalAmountToPost(as);
		}
	}

	public CostAmount getCreateShipmentCosts(final AcctSchema as)
	{
		if (isReversalLine())
		{
			return services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofShipmentLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(getReversalLine_ID()))
					.date(getDateAcct())
					.build())
					.getTotalAmountToPost(as)
					// Negate the amount coming from the costs because it must be negative in the accounting.
					.negate();
		}
		else
		{
			return services.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(as.getId())
							.clientId(getClientId())
							.orgId(getOrgId())
							.productId(getProductId())
							.attributeSetInstanceId(getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofShipmentLineId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getCurrencyId())) // expect to be calculated
							.date(getDateAcct())
							.build())
					.getTotalAmountToPost(as)
					// The shipment is an outgoing document, so the costing amounts will be negative values.
					// In the accounting they must be positive values. This is the reason why the amount
					// coming from the product costs must be negated.
					.negate();
		}
	}
}
