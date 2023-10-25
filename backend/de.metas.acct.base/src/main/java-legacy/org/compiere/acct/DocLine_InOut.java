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
import de.metas.costing.CostAmountAndQty;
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
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationCollection;
import de.metas.shippingnotification.ShippingNotificationLine;
import de.metas.shippingnotification.acct.ShippingNotificationAcctService;
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

@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
class DocLine_InOut extends DocLine<Doc_InOut>
{
	@NonNull private final IOrderBL orderBL;
	@NonNull private final ShippingNotificationAcctService shippingNotificationAcctService;

	@NonNull @Getter private final ImmutableList<InOutCost> inoutCosts;
	private final ShippingNotificationCollection shippingNotifications;

	/**
	 * Outside Processing
	 */
	private Optional<PPCostCollectorId> ppCostCollectorId = null; // lazy

	DocLine_InOut(
			@NonNull final Doc_InOut doc,
			@NonNull final I_M_InOutLine inoutLine,
			@NonNull final List<InOutCost> inoutCosts,
			@NonNull final ShippingNotificationCollection shippingNotifications)
	{
		super(InterfaceWrapperHelper.getPO(inoutLine), doc);
		this.orderBL = doc.orderBL;
		this.shippingNotificationAcctService = doc.shippingNotificationAcctService;
		this.inoutCosts = ImmutableList.copyOf(inoutCosts);
		this.shippingNotifications = shippingNotifications;

		final Quantity qty = doc.inOutBL.getMovementQty(inoutLine);
		setQty(qty, doc.isSOTrx());
	}

	public InOutLineId getInOutLineId()
	{
		return InOutLineId.ofRepoId(get_ID());
	}

	private Optional<PPCostCollectorId> getPP_Cost_Collector_ID()
	{
		Optional<PPCostCollectorId> ppCostCollectorId = this.ppCostCollectorId;
		if (ppCostCollectorId == null)
		{
			ppCostCollectorId = this.ppCostCollectorId = getOrderLineId().flatMap(orderBL::getPPCostCollectorId);
		}
		return ppCostCollectorId;
	}

	private Optional<OrderAndLineId> getOrderAndLineId()
	{
		final I_M_InOutLine inOutLine = getInOutLine();
		return OrderAndLineId.optionalOfRepoIds(inOutLine.getC_Order_ID(), inOutLine.getC_OrderLine_ID());
	}

	private Optional<I_C_OrderLine> getOrderLine()
	{
		return getOrderAndLineId().map(orderBL::getLineById);
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
							.externallyOwned(computeAmtAndQtyExternallyOwned(as))
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

	private CostAmountAndQty computeAmtAndQtyExternallyOwned(final AcctSchema as)
	{
		//noinspection UnnecessaryLocalVariable
		final Quantity ZERO = getQty().toZero();

		Quantity qtyExternallyOwned = ZERO;
		CostAmount costsExternallyOwned = CostAmount.zero(as.getCurrencyId());

		final ProductId productId = getProductId();
		final OrderAndLineId orderAndLineId = getOrderAndLineId().orElse(null);
		if (productId != null && orderAndLineId != null)
		{
			for (final ShippingNotification shippingNotification : shippingNotifications)
			{
				final ShippingNotificationLine shippingNotificationLine = shippingNotification.getLineBySalesOrderLineId(orderAndLineId).orElse(null);
				if (shippingNotificationLine == null)
				{
					continue;
				}

				final CostAmount shippingNotificationLineCosts = shippingNotificationAcctService.getCreateCosts(shippingNotification, shippingNotificationLine, as);
				costsExternallyOwned = costsExternallyOwned.add(shippingNotificationLineCosts);

				final Quantity shippingNotificationLineQty = services.convertQuantityTo(shippingNotificationLine.getQty(), productId, qtyExternallyOwned.getUomId());
				qtyExternallyOwned = qtyExternallyOwned.add(shippingNotificationLineQty);
			}
		}

		return CostAmountAndQty.of(costsExternallyOwned, qtyExternallyOwned);
	}

}
