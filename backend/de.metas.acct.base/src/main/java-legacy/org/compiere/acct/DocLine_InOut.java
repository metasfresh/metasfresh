package org.compiere.acct;

<<<<<<< HEAD
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
=======
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
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ShipmentCosts;
import de.metas.currency.CurrencyConversionContext;
import de.metas.inout.InOutLineId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.costs.inout.InOutCost;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.eevolution.api.PPCostCollectorId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

<<<<<<< HEAD
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
=======
@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
class DocLine_InOut extends DocLine<Doc_InOut>
{
	@NonNull private final IOrderBL orderBL;

	@NonNull @Getter private final ImmutableList<InOutCost> inoutCosts;
	@Nullable private final OrderAndLineId orderAndLineId;

	/**
	 * Outside Processing
	 */
	private Optional<PPCostCollectorId> ppCostCollectorId = null; // lazy

	DocLine_InOut(
			@NonNull final Doc_InOut doc,
			@NonNull final I_M_InOutLine inoutLine,
			@NonNull final List<InOutCost> inoutCosts)
	{
		super(InterfaceWrapperHelper.getPO(inoutLine), doc);
		this.orderBL = doc.orderBL;
		this.inoutCosts = ImmutableList.copyOf(inoutCosts);

		final Quantity qty = doc.inOutBL.getMovementQty(inoutLine);
		this.orderAndLineId = getOrderAndLineId(doc.getSalesOrderId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		setQty(qty, doc.isSOTrx());
	}

	public InOutLineId getInOutLineId()
	{
		return InOutLineId.ofRepoId(get_ID());
	}

<<<<<<< HEAD
	private final int getPP_Cost_Collector_ID()
	{
		if (ppCostCollectorId == null)
		{
			ppCostCollectorId = retrievePPCostCollectorId();
=======
	private Optional<PPCostCollectorId> getPP_Cost_Collector_ID()
	{
		Optional<PPCostCollectorId> ppCostCollectorId = this.ppCostCollectorId;
		if (ppCostCollectorId == null)
		{
			ppCostCollectorId = this.ppCostCollectorId = getOrderLineId().flatMap(orderBL::getPPCostCollectorId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		return ppCostCollectorId;
	}

<<<<<<< HEAD
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
=======
	@Nullable
	private OrderAndLineId getOrderAndLineId(@Nullable final OrderId orderId)
	{
		final I_M_InOutLine inOutLine = getInOutLine();
		return OrderAndLineId.ofRepoIdsOrNull(OrderId.toRepoId(orderId), inOutLine.getC_OrderLine_ID());
	}

	private Optional<I_C_OrderLine> getOrderLine()
	{
		return Optional.ofNullable(orderAndLineId)
				.map(orderBL::getLineById);
	}

	private I_M_InOutLine getInOutLine()
	{
		return getModel(I_M_InOutLine.class);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * @return order's org if defined, else doc line's org
	 */
	public final OrgId getOrderOrgId()
	{
<<<<<<< HEAD
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
=======
		return getOrderLine()
				.map(orderLine -> OrgId.ofRepoId(orderLine.getAD_Org_ID()))
				.orElseGet(this::getOrgId);
	}

	@NonNull
	public Account getProductAssetAccount(final AcctSchema as)
	{
		if (isItem())
		{
			return getAccount(ProductAcctType.P_Asset_Acct, as);
		}
		// if the line is an Outside Processing then DR WIP
		else if (getPP_Cost_Collector_ID().isPresent())
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
			return services.createReversalCostDetails(
							CostDetailReverseRequest.builder()
									.acctSchemaId(as.getId())
									.reversalDocumentRef(CostingDocumentRef.ofReceiptLineId(get_ID()))
									.initialDocumentRef(CostingDocumentRef.ofReceiptLineId(getReversalLine_ID()))
									.date(getDateAcctAsInstant())
									.build())
					.toAggregatedCostAmount();
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
									.build())
					.toAggregatedCostAmount();

			//
			// Additional costs
			for (final InOutCost inoutCost : inoutCosts)
			{
				final AggregatedCostAmount nonMaterialCosts = services.createCostDetail(
								requestBuilder
										.costElement(services.getCostElementById(inoutCost.getCostElementId()))
										.amt(CostAmount.ofMoney(inoutCost.getCostAmount()))
										.build())
						.toAggregatedCostAmount();

				result = result.merge(nonMaterialCosts);
			}

			return result;
		}
	}

	ShipmentCosts getCreateShipmentCosts(final AcctSchema as)
	{
		final CostDetailCreateResultsList results;
		if (isReversalLine())
		{
			results = services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofShipmentLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(getReversalLine_ID()))
					.date(getDateAcctAsInstant())
					.build());
		}
		else
		{
			results = services.createCostDetail(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					CostDetailCreateRequest.builder()
							.acctSchemaId(as.getId())
							.clientId(getClientId())
							.orgId(getOrgId())
							.productId(getProductId())
							.attributeSetInstanceId(getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofShipmentLineId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getCurrencyId())) // expect to be calculated
<<<<<<< HEAD
							.date(getDateAcct())
							.build())
					.getTotalAmountToPost(as)
					// The shipment is an outgoing document, so the costing amounts will be negative values.
					// In the accounting they must be positive values. This is the reason why the amount
					// coming from the product costs must be negated.
					.negate();
		}
	}
=======
							.currencyConversionContext(getCurrencyConversionContext(as))
							.date(getDateAcctAsInstant())
							.build());
		}

		return ShipmentCosts.extractAccountableFrom(results, as);
	}

	private CurrencyConversionContext getCurrencyConversionContext(final AcctSchema as)
	{
		return getDoc().getCurrencyConversionContext(as);
	}

	@Override
	protected OrderId getSalesOrderId()
	{
		return orderAndLineId == null ? null : orderAndLineId.getOrderId();
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
		if (bpartnerLocationId == null)
		{
			return null;
		}

		final BPartnerId bpartnerId = getBPartnerId(costElementId);
		if (BPartnerId.equals(bpartnerLocationId.getBpartnerId(), bpartnerId))
		{
			return bpartnerLocationId;
		}
		else
		{
			return null;
		}
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
