package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.Account;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingDocumentRef;
import de.metas.currency.CurrencyConversionContext;
import de.metas.inout.InOutLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.inout.InOutCost;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

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
	@Getter
	@NonNull private final ImmutableList<InOutCost> inoutCosts;

	/**
	 * Outside Processing
	 */
	private Integer ppCostCollectorId = null; // lazy

	@Builder
	private DocLine_InOut(
			@NonNull final I_M_InOutLine inoutLine,
			@NonNull final List<InOutCost> inoutCosts,
			@NonNull final Doc_InOut doc)
	{
		super(InterfaceWrapperHelper.getPO(inoutLine), doc);

		this.inoutCosts = ImmutableList.copyOf(inoutCosts);

		final Quantity qty = doc.inOutBL.getMovementQty(inoutLine);
		setQty(qty, doc.isSOTrx());
	}

	public InOutLineId getInOutLineId()
	{
		return InOutLineId.ofRepoId(get_ID());
	}

	private int getPP_Cost_Collector_ID()
	{
		if (ppCostCollectorId == null)
		{
			ppCostCollectorId = retrievePPCostCollectorId();
		}
		return ppCostCollectorId;
	}

	private int retrievePPCostCollectorId()
	{
		final OrderLineId orderLineId = getOrderLineId();
		if (orderLineId != null)
		{
			final String sql = "SELECT " + I_C_OrderLine.COLUMNNAME_PP_Cost_Collector_ID
					+ " FROM C_OrderLine WHERE C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
			return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, orderLineId);
		}

		return 0;
	}

	public final I_C_OrderLine getOrderLineOrNull()
	{
		return getInOutLine().getC_OrderLine();
	}

	private I_M_InOutLine getInOutLine()
	{
		return getModel(I_M_InOutLine.class);
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

	@NonNull
	public Account getProductAssetAccount(final AcctSchema as)
	{
		if (isItem())
		{
			return getAccount(ProductAcctType.P_Asset_Acct, as);
		}
		// if the line is an Outside Processing then DR WIP
		else if (getPP_Cost_Collector_ID() > 0)
		{
			return getAccount(ProductAcctType.P_WIP_Acct, as);
		}
		else
		{
			return getAccount(ProductAcctType.P_Expense_Acct, as);
		}
	}

	public AggregatedCostAmount getCreateReceiptCosts(final AcctSchema as)
	{
		if (isReversalLine())
		{
			return services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofReceiptLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofReceiptLineId(getReversalLine_ID()))
					.date(getDateAcctAsInstant())
					.build());
		}
		else
		{
			final CostDetailCreateRequest.CostDetailCreateRequestBuilder requestBuilder = CostDetailCreateRequest.builder()
					.acctSchemaId(as.getId())
					.clientId(getClientId())
					.orgId(getOrgId())
					.productId(getProductId())
					.attributeSetInstanceId(getAttributeSetInstanceId())
					.documentRef(CostingDocumentRef.ofReceiptLineId(get_ID()))
					.qty(getQty())
					//.amt(null)
					.currencyConversionContext(getCurrencyConversionContext(as))
					.date(getDateAcctAsInstant());

			//
			// Material costs:
			AggregatedCostAmount result = services.createCostDetail(
					requestBuilder
							.amt(CostAmount.zero(as.getCurrencyId())) // N/A
							.build());

			//
			// Additional costs
			for (final InOutCost inoutCost : inoutCosts)
			{
				final AggregatedCostAmount nonMaterialCosts = services.createCostDetail(
						requestBuilder
								.costElement(services.getCostElementById(inoutCost.getCostElementId()))
								.amt(CostAmount.ofMoney(inoutCost.getCostAmount()))
								.build());

				result = result.merge(nonMaterialCosts);
			}

			return result;
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
							.date(getDateAcctAsInstant())
							.build())
					.getTotalAmountToPost(as)
					.getMainAmt()
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
									.currencyConversionContext(getCurrencyConversionContext(as))
									.date(getDateAcctAsInstant())
									.build())
					.getTotalAmountToPost(as)
					.getMainAmt()
					// The shipment is an outgoing document, so the costing amounts will be negative values.
					// In the accounting they must be positive values. This is the reason why the amount
					// coming from the product costs must be negated.
					.negate();
		}
	}

	private CurrencyConversionContext getCurrencyConversionContext(final AcctSchema as)
	{
		return getDoc().getCurrencyConversionContext(as);
	}

	@Override
	protected OrderId getSalesOrderId()
	{
		final I_M_InOutLine inoutLine = getInOutLine();
		return OrderId.ofRepoIdOrNull(inoutLine.getC_OrderSO_ID());
	}

	@Nullable
	public BPartnerId getBPartnerId(@NonNull final CostElementId costElementId)
	{
		final ImmutableSet<BPartnerId> costBPartnerIds = inoutCosts.stream()
				.filter(inoutCost -> CostElementId.equals(inoutCost.getCostElementId(), costElementId))
				.map(InOutCost::getBpartnerId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final BPartnerId costBPartnerId = CollectionUtils.singleElementOrNull(costBPartnerIds);
		if (costBPartnerId != null)
		{
			return costBPartnerId;
		}
		else
		{
			return getBPartnerId();
		}
	}

	@Nullable
	public BPartnerLocationId getBPartnerLocationId(@NonNull final CostElementId costElementId)
	{
		final BPartnerLocationId bpartnerLocationId = getDoc().getBPartnerLocationId();
		if(bpartnerLocationId == null)
		{
			return null;
		}

		final BPartnerId bpartnerId = getBPartnerId(costElementId);
		if(BPartnerId.equals(bpartnerLocationId.getBpartnerId(), bpartnerId))
		{
			return bpartnerLocationId;
		}
		else
		{
			return null;
		}
	}

}
