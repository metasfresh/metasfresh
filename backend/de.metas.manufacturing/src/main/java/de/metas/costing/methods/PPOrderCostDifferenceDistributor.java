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

package de.metas.costing.methods;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import java.util.List;

/**
 * Discharges the WIP cost residual of a completed-but-not-closed manufacturing order: the in-stock
 * portion is capitalized onto the finished good's current cost price, the already-shipped remainder
 * spills to COGS.
 * <p>
 * {@code residual = issued - received} is always recomputed from the order's {@code PP_Order_Cost} rows.
 * It is the <b>opposite sign</b> of the {@code PP_Order.CostDifference} display column
 * ({@code received - issued}), which is never read here.
 */
@Component
@RequiredArgsConstructor
public class PPOrderCostDifferenceDistributor
{
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IPPOrderCostBL ppOrderCostsService = Services.get(IPPOrderCostBL.class);
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);

	@NonNull private final ICostElementRepository costElementsRepo;
	@NonNull private final CostingMethodHandlerUtils utils;

	public void distribute(@NonNull final PPOrderId orderId)
	{
		// The order stays Completed after distributing, so the action remains offered; re-running would
		// discharge the same residual twice.
		if (isAlreadyDistributed(orderId))
		{
			throw new AdempiereException("@Processed@")
					.setParameter("PP_Order_ID", orderId)
					.appendParametersToMessage();
		}

		final I_PP_Order order = ppOrdersRepo.getById(orderId);

		// Only decides whether there is anything to discharge at all; the amount that gets posted is recomputed
		// per accounting schema while the collector is posted.
		final ClientId clientId = ClientId.ofRepoId(order.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final AcctSchemaId acctSchemaId = acctSchemasRepo.getByClientAndOrg(clientId, orgId).getId();

		final ResidualAndManufacturedQty residualAndQty = computeResidualAndManufacturedQtyForOrder(orderId, acctSchemaId);
		if (residualAndQty == null || residualAndQty.getResidual().isZero())
		{
			return;
		}

		costCollectorsService.createCostDifferenceDistribution(
				order,
				ProductId.ofRepoId(order.getM_Product_ID()),
				residualAndQty.getManufacturedQty());
	}

	/**
	 * Creates the cost details of a {@code CostDifferenceDistribution} collector and capitalizes the adjustment leg
	 * onto the finished good's {@link CurrentCost}. Driven from each manufacturing costing-method handler, so the
	 * accounting schema, the cost element and — on a reversal — the already-negated amounts come from the framework.
	 */
	public CostDetailCreateResultsList createCostDetails(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final PPOrderId orderId)
	{
		return request.isReversal()
				? createReversalCostDetails(request)
				: createDistributionCostDetails(request, orderId);
	}

	private CostDetailCreateResultsList createDistributionCostDetails(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final PPOrderId orderId)
	{
		final PPOrderCosts orderCosts = ppOrderCostsService.getByOrderId(orderId);
		final ResidualAndManufacturedQty residualAndQty = computeResidualAndManufacturedQty(
				orderCosts,
				request.getAcctSchemaId(),
				request.getCostElementId());
		if (residualAndQty == null || residualAndQty.getResidual().isZero())
		{
			return CostDetailCreateResultsList.EMPTY;
		}

		final CurrentCost currentCost = utils.getCurrentCost(request);
		final CostAmountDetailed split = computeSplit(
				residualAndQty.getResidual(),
				residualAndQty.getManufacturedQty(),
				currentCost);

		final CostDetailCreateResult mainResult = utils.createCostDetailRecordNoCostsChanged(
				request.withAmountAndType(split.getMainAmt(), CostAmountType.MAIN),
				CostDetailPreviousAmounts.of(currentCost));
		CostAmountAndQtyDetailed amtAndQty = mainResult.getAmtAndQty();

		if (!split.getCostAdjustmentAmt().isZero())
		{
			final CostDetailCreateResult adjustmentResult = utils.createCostDetailRecordWithChangedCosts(
					request.withAmountAndType(split.getCostAdjustmentAmt(), CostAmountType.ADJUSTMENT).withQtyZero(),
					CostDetailPreviousAmounts.of(currentCost));

			moveCostPriceBy(currentCost, split.getCostAdjustmentAmt(), request);

			amtAndQty = amtAndQty.add(adjustmentResult.getAmtAndQty());
		}

		if (!split.getAlreadyShippedAmt().isZero())
		{
			final CostDetailCreateResult alreadyShippedResult = utils.createCostDetailRecordNoCostsChanged(
					request.withAmountAndType(split.getAlreadyShippedAmt(), CostAmountType.ALREADY_SHIPPED).withQtyZero(),
					CostDetailPreviousAmounts.of(currentCost));
			amtAndQty = amtAndQty.add(alreadyShippedResult.getAmtAndQty());
		}

		return CostDetailCreateResultsList.ofNullable(mainResult.withAmtAndQty(amtAndQty));
	}

	/**
	 * Replays one already-negated leg of the original distribution. Only the adjustment leg moved the cost price,
	 * so only it moves it back.
	 */
	private CostDetailCreateResultsList createReversalCostDetails(@NonNull final CostDetailCreateRequest request)
	{
		final CurrentCost currentCost = utils.getCurrentCost(request);

		if (CostAmountType.ADJUSTMENT.equals(request.getAmtType()))
		{
			final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(
					request,
					CostDetailPreviousAmounts.of(currentCost));

			moveCostPriceBy(currentCost, request.getAmt(), request);

			return CostDetailCreateResultsList.ofNullable(result);
		}

		return CostDetailCreateResultsList.ofNullable(
				utils.createCostDetailRecordNoCostsChanged(request, CostDetailPreviousAmounts.of(currentCost)));
	}

	/** Zero qty delta =&gt; reprices the existing on-hand qty by {@code amt}. */
	private void moveCostPriceBy(
			@NonNull final CurrentCost currentCost,
			@NonNull final CostAmount amt,
			@NonNull final CostDetailCreateRequest request)
	{
		currentCost.addWeightedAverage(amt, request.getQty().toZero(), utils.getQuantityUOMConverter());
		utils.saveCurrentCost(currentCost);
	}

	@Nullable
	private ResidualAndManufacturedQty computeResidualAndManufacturedQtyForOrder(
			@NonNull final PPOrderId orderId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		final AcctSchema acctSchema = acctSchemasRepo.getById(acctSchemaId);
		final CostingMethod costingMethod = acctSchema.getCosting().getCostingMethod();
		final CostElementId materialCostElementId = getMaterialCostElementId(costingMethod);

		final PPOrderCosts orderCosts = ppOrderCostsService.getByOrderId(orderId);
		return computeResidualAndManufacturedQty(orderCosts, acctSchemaId, materialCostElementId);
	}

	private boolean isAlreadyDistributed(@NonNull final PPOrderId orderId)
	{
		return costCollectorsService.getByOrderId(orderId).stream()
				.filter(cc -> !DocStatus.ofNullableCodeOrUnknown(cc.getDocStatus()).isReversedOrVoided())
				.map(I_PP_Cost_Collector::getCostCollectorType)
				.map(CostCollectorType::ofNullableCode)
				.anyMatch(type -> type != null && type.isCostDifferenceDistribution());
	}

	private CostElementId getMaterialCostElementId(@NonNull final CostingMethod costingMethod)
	{
		final List<CostElement> materialElements = costElementsRepo.getMaterialCostingElementsForCostingMethod(costingMethod);
		return materialElements.stream()
				.map(CostElement::getId)
				.collect(GuavaCollectors.singleElementOrThrow(
						() -> new AdempiereException("Expected exactly one material cost element for costing method " + costingMethod + " but got " + materialElements)));
	}

	/** Splits the residual pro rata: the still-in-stock share is capitalized, the shipped remainder goes to COGS. */
	@VisibleForTesting
	static CostAmountDetailed computeSplit(
			@NonNull final CostAmount residual,
			@NonNull final Quantity manufacturedQty,
			@NonNull final CurrentCost currentCost)
	{
		final CurrencyId currencyId = currentCost.getCurrencyId();
		final Quantity qtyInStock = currentCost.getCurrentQty().min(manufacturedQty);

		final CostAmount capitalized;
		final CostAmount cogs;
		if (residual.isZero())
		{
			capitalized = CostAmount.zero(currencyId);
			cogs = CostAmount.zero(currencyId);
		}
		else if (manufacturedQty.isZero())
		{
			capitalized = CostAmount.zero(currencyId);
			cogs = residual;
		}
		else if (manufacturedQty.equalsIgnoreSource(qtyInStock))
		{
			capitalized = residual;
			cogs = CostAmount.zero(currencyId);
		}
		else
		{
			final CostAmount perUnit = residual.divide(manufacturedQty, currentCost.getPrecision());
			capitalized = perUnit.multiply(qtyInStock);
			cogs = residual.subtract(capitalized);
		}

		return CostAmountDetailed.builder()
				.mainAmt(residual)
				.costAdjustmentAmt(capitalized)
				.alreadyShippedAmt(cogs)
				.build();
	}

	/**
	 * Recomputes {@code residual = issued - received} over the order's {@code PP_Order_Cost} rows of the given
	 * schema and material cost element: {@code issued} sums {@code -accumulatedQty x price} over the MaterialIssue
	 * rows, {@code received} sums the post-calculation amount over the MainProduct / CoProduct / ByProduct rows.
	 */
	@Nullable
	@VisibleForTesting
	static ResidualAndManufacturedQty computeResidualAndManufacturedQty(
			@NonNull final PPOrderCosts orderCosts,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId materialCostElementId)
	{
		final PPOrderCost mainProductCost = getMainProductCostOrNull(orderCosts, acctSchemaId, materialCostElementId);
		if (mainProductCost == null)
		{
			// The costing engine explodes the client's material cost elements against the schema being posted, so a
			// handler can be asked for a costing method this order carries no PP_Order_Cost rows for.
			return null;
		}

		final CurrencyId currencyId = mainProductCost.getPrice().getCurrencyId();

		CostAmount issued = CostAmount.zero(currencyId);
		CostAmount received = CostAmount.zero(currencyId);
		for (final PPOrderCost cost : orderCosts.toCollection())
		{
			if (!acctSchemaId.equals(cost.getAcctSchemaId())
					|| !materialCostElementId.equals(cost.getCostElementId()))
			{
				continue;
			}

			final PPOrderCostTrxType trxType = cost.getTrxType();
			if (trxType == PPOrderCostTrxType.MaterialIssue)
			{
				// MaterialIssue rows accumulate a negative qty, hence the negate().
				issued = issued.add(cost.getPrice().multiply(cost.getAccumulatedQty()).negate());
			}
			else if (trxType == PPOrderCostTrxType.MainProduct
					|| trxType == PPOrderCostTrxType.CoProduct
					|| trxType == PPOrderCostTrxType.ByProduct)
			{
				received = received.add(cost.getPostCalculationAmount());
			}
		}

		final CostAmount residual = issued.subtract(received);
		return ResidualAndManufacturedQty.of(residual, mainProductCost.getAccumulatedQty());
	}

	@Nullable
	private static PPOrderCost getMainProductCostOrNull(
			@NonNull final PPOrderCosts orderCosts,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId materialCostElementId)
	{
		final List<PPOrderCost> mainProductCosts = orderCosts.toCollection().stream()
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> materialCostElementId.equals(cost.getCostElementId()))
				.filter(PPOrderCost::isMainProduct)
				.collect(ImmutableList.toImmutableList());

		if (mainProductCosts.size() > 1)
		{
			throw new AdempiereException("Expected at most one main-product PP_Order_Cost row for acctSchema=" + acctSchemaId
					+ ", costElement=" + materialCostElementId + " in " + orderCosts);
		}

		return mainProductCosts.isEmpty() ? null : mainProductCosts.get(0);
	}

	@Value(staticConstructor = "of")
	static class ResidualAndManufacturedQty
	{
		@NonNull CostAmount residual;
		@NonNull Quantity manufacturedQty;
	}
}
