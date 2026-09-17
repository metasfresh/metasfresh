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
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.common.util.time.SystemTime;
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
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import java.util.List;

/**
 * Discharges the WIP cost residual of a completed-but-not-closed manufacturing order: the in-stock portion is
 * capitalized onto the finished good's current cost price, the already-shipped remainder spills to COGS.
 * <p>
 * The residual is recomputed from the order's {@code PP_Order_Cost} rows as {@code issued - received}, i.e. the
 * <b>opposite sign</b> of the {@code PP_Order.CostDifference} display column, which is never read here.
 * <p>
 * Posting accumulates the residual back onto the main-product line, so it reads zero afterwards - that
 * self-zeroing is what keeps a re-run (e.g. after a {@code PP_Order_UnClose}) from discharging it twice.
 */
@Component
@RequiredArgsConstructor
public class PPOrderCostDifferenceDistributor
{
	/** Only these accumulate into {@code PP_Order_Cost}; without that there is no residual to discharge. */
	private static final ImmutableSet<CostingMethod> COSTING_METHODS_WITH_ORDER_COSTS = ImmutableSet.of(
			CostingMethod.AveragePO,
			CostingMethod.LastPOPrice,
			CostingMethod.MovingAverageInvoice);

	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IPPOrderCostBL ppOrderCostsService = Services.get(IPPOrderCostBL.class);
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);
	private final IPPOrderBL ppOrdersService = Services.get(IPPOrderBL.class);

	@NonNull private final ICostElementRepository costElementsRepo;
	@NonNull private final CostingMethodHandlerUtils utils;

	/**
	 * Whether this order's accounting schema accumulates into {@code PP_Order_Cost} at all. Standard costing
	 * values every issue and receipt at standard and accumulates nothing, so the residual is always zero there
	 * and {@link #distribute(PPOrderId)} would silently do nothing.
	 * <p>
	 * Resolved from the accounting schema, not from the product: only a cost element whose costing method matches
	 * the schema's is accountable, and {@link #distribute(PPOrderId)} resolves the residual the same way. Reading
	 * a per-product-category override here would disagree with what actually posts.
	 */
	public boolean hasOrderCosts(@NonNull final I_PP_Order order)
	{
		return COSTING_METHODS_WITH_ORDER_COSTS.contains(getAcctSchema(order).getCosting().getCostingMethod());
	}

	/**
	 * Resolves schema and cost element exactly as {@link #distribute(PPOrderId)} does, so the guard and the
	 * amount that would post cannot disagree.
	 */
	public boolean hasInboundCosts(@NonNull final I_PP_Order order)
	{
		final AcctSchema acctSchema = getAcctSchema(order);
		final CostElementId materialCostElementId = getMaterialCostElementId(acctSchema.getCosting().getCostingMethod());

		return ppOrderCostsService.getByOrderId(PPOrderId.ofRepoId(order.getPP_Order_ID()))
				.hasInboundCosts(acctSchema.getId(), materialCostElementId);
	}

	private AcctSchema getAcctSchema(@NonNull final I_PP_Order order)
	{
		return acctSchemasRepo.getByClientAndOrg(
				ClientId.ofRepoId(order.getAD_Client_ID()),
				OrgId.ofRepoId(order.getAD_Org_ID()));
	}

	public void distribute(@NonNull final PPOrderId orderId)
	{
		final I_PP_Order order = ppOrdersRepo.getById(orderId);

		// Only decides whether there is anything to discharge at all; the amount that gets posted is recomputed
		// per accounting schema while the collector is posted.
		final AcctSchemaId acctSchemaId = getAcctSchema(order).getId();

		final CostAmount residual = getResidualCostForOrderOrNull(orderId, acctSchemaId);
		if (residual == null || residual.isZero())
		{
			return;
		}

		// Repeated from the process precondition so an API caller cannot bypass it.
		if (!hasInboundCosts(order))
		{
			return;
		}

		costCollectorsService.createCostDifferenceDistribution(order, SystemTime.asZonedDateTime());

		// Closing is part of discharging: it stops a further issue or receipt from re-opening the residual that
		// was just posted away, and withdraws the action from the order.
		ppOrdersService.closeOrder(orderId);
	}

	/**
	 * Creates the cost details of a {@code CostDifferenceDistribution} collector and capitalizes the adjustment leg
	 * onto the finished good's {@link CurrentCost}. Driven from each manufacturing costing-method handler, so schema,
	 * cost element and — on a reversal — the already-negated amounts come from the framework.
	 */
	public CostDetailCreateResultsList createCostDetails(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final PPOrderId orderId)
	{
		return request.isReversal()
				? createReversalCostDetails(request, orderId)
				: createDistributionCostDetails(request, orderId);
	}

	private CostDetailCreateResultsList createDistributionCostDetails(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final PPOrderId orderId)
	{
		final PPOrderCosts orderCosts = ppOrderCostsService.getByOrderId(orderId);
		final PPOrderCost mainProductCost = orderCosts.getMainProductCostOrNull(request.getAcctSchemaId(), request.getCostElementId());
		if (mainProductCost == null)
		{
			// The costing engine explodes the client's material cost elements against the schema being posted, so a
			// handler can be asked for a costing method this order carries no PP_Order_Cost rows for.
			return CostDetailCreateResultsList.EMPTY;
		}

		// Each co-product discharges its own residual independently of the main product's - a co-product's booked
		// (accumulated) value can diverge from its carve while the main product's own residual is already zero.
		final boolean anyCoProductDischarged = distributeCoProductResiduals(orderCosts, request);

		final CostAmount residual = mainProductCost.getResidualCost();
		if (residual.isZero())
		{
			// The main product itself has nothing to discharge, but a co-product might still have been - persist
			// that, since the early-return below would otherwise silently drop it.
			if (anyCoProductDischarged)
			{
				ppOrderCostsService.save(orderCosts);
			}
			return CostDetailCreateResultsList.EMPTY;
		}

		final CurrentCost currentCost = utils.getCurrentCostForUpdate(request);
		final CostAmountDetailed split = computeSplit(residual, mainProductCost, currentCost);

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

		// Discharge the residual on the main-product line too, so getResidualCost() reads zero and it cannot be
		// discharged twice. Not done in distribute(): the amounts posted above are recomputed from these rows.
		orderCosts.dischargeOntoMainProduct(mainProductCost, residual, utils.getQuantityUOMConverter());
		ppOrderCostsService.save(orderCosts);

		return CostDetailCreateResultsList.ofNullable(mainResult.withAmtAndQty(amtAndQty));
	}

	/**
	 * Replays one already-negated leg of the original distribution. Only the adjustment leg moved the cost price,
	 * so only it moves it back; only the main leg discharged the residual, so only it re-opens it.
	 */
	private CostDetailCreateResultsList createReversalCostDetails(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final PPOrderId orderId)
	{
		final CurrentCost currentCost = utils.getCurrentCostForUpdate(request);

		if (CostAmountType.ADJUSTMENT.equals(request.getAmtType()))
		{
			final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(
					request,
					CostDetailPreviousAmounts.of(currentCost));

			moveCostPriceBy(currentCost, request.getAmt(), request);

			return CostDetailCreateResultsList.ofNullable(result);
		}

		if (CostAmountType.MAIN.equals(request.getAmtType()))
		{
			// The main leg carries the negated residual, so accumulating it undoes the discharge: the order
			// reports its imbalance again and can be distributed a second time.
			accumulateOntoMainProduct(orderId, request, request.getAmt());
		}

		return CostDetailCreateResultsList.ofNullable(
				utils.createCostDetailRecordNoCostsChanged(request, CostDetailPreviousAmounts.of(currentCost)));
	}

	/**
	 * Adds {@code amt} to the row's accumulated amount, leaving the qty alone - value moves only. The row is
	 * resolved by {@code request}'s own product, which is the main product for a reversed main-product leg but a
	 * co-product for a reversed co-product leg: {@link #distributeCoProductResiduals} persists each co-product's
	 * own MAIN-type {@code CostDetail} row too, and {@code CostingService.createReversalCostDetailsOrEmpty}
	 * replays every {@code CostDetail} of the original document keyed by its own product, not the main product
	 * alone.
	 */
	private void accumulateOntoMainProduct(
			@NonNull final PPOrderId orderId,
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CostAmount amt)
	{
		final PPOrderCosts orderCosts = ppOrderCostsService.getByOrderId(orderId);
		final PPOrderCost targetCost = orderCosts.getMainOrCoProductCostOrNull(request.getAcctSchemaId(), request.getCostElementId(), request.getProductId());
		if (targetCost == null)
		{
			return;
		}

		orderCosts.dischargeOntoMainProduct(targetCost, amt, utils.getQuantityUOMConverter());
		ppOrderCostsService.save(orderCosts);
	}

	/**
	 * Discharges each co-product's leftover WIP the same way the main product's is discharged: split the residual
	 * by how much of the made qty is still in stock vs already shipped, capitalize the in-stock share onto the
	 * product's cost price, expense the shipped share to COGS, and relieve the whole residual from WIP.
	 * <p>
	 * Example: residual 100, made 10 (6 in stock, 4 shipped) => 60 capitalized onto the cost price, 40 to COGS,
	 * 100 relieved from WIP.
	 * <p>
	 * Each co-product's rows are keyed on its own product, so a reversal finds and reverses them (see
	 * {@link #accumulateOntoMainProduct}). They are not folded into the returned list, which must stay one product
	 * ({@code CostDetailCreateResultsList.toAggregatedCostAmount} throws on a mixed segment).
	 *
	 * @return whether any co-product residual was discharged, so the caller persists {@code orderCosts} even when
	 * the main product's own residual is zero.
	 */
	private boolean distributeCoProductResiduals(
			@NonNull final PPOrderCosts orderCosts,
			@NonNull final CostDetailCreateRequest request)
	{
		boolean anyDischarged = false;

		for (final PPOrderCost coProductCost : orderCosts.getCoProductCosts(request.getAcctSchemaId(), request.getCostElementId()))
		{
			final CostAmount residual = coProductCost.getResidualCost();
			if (residual.isZero())
			{
				continue;
			}

			final CurrentCost currentCost = utils.getCurrentCostForUpdate(coProductCost.getCostSegmentAndElement());
			final CostAmountDetailed split = computeSplit(residual, coProductCost, currentCost);

			final CostDetailCreateRequest coProductRequest = request.withProductIdAndQty(coProductCost.getProductId(), request.getQty().toZero());

			utils.createCostDetailRecordNoCostsChanged(
					coProductRequest.withAmountAndType(split.getMainAmt(), CostAmountType.MAIN),
					CostDetailPreviousAmounts.of(currentCost));

			if (!split.getCostAdjustmentAmt().isZero())
			{
				utils.createCostDetailRecordWithChangedCosts(
						coProductRequest.withAmountAndType(split.getCostAdjustmentAmt(), CostAmountType.ADJUSTMENT).withQtyZero(),
						CostDetailPreviousAmounts.of(currentCost));

				moveCostPriceBy(currentCost, split.getCostAdjustmentAmt(), coProductRequest);
			}

			if (!split.getAlreadyShippedAmt().isZero())
			{
				utils.createCostDetailRecordNoCostsChanged(
						coProductRequest.withAmountAndType(split.getAlreadyShippedAmt(), CostAmountType.ALREADY_SHIPPED).withQtyZero(),
						CostDetailPreviousAmounts.of(currentCost));
			}

			orderCosts.dischargeOntoMainProduct(coProductCost, residual, utils.getQuantityUOMConverter());
			anyDischarged = true;
		}

		return anyDischarged;
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
	private CostAmount getResidualCostForOrderOrNull(
			@NonNull final PPOrderId orderId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		final AcctSchema acctSchema = acctSchemasRepo.getById(acctSchemaId);
		final CostingMethod costingMethod = acctSchema.getCosting().getCostingMethod();
		final CostElementId materialCostElementId = getMaterialCostElementId(costingMethod);

		return ppOrderCostsService.getByOrderId(orderId).getResidualCost(acctSchemaId, materialCostElementId);
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
			@NonNull final PPOrderCost mainProductCost,
			@NonNull final CurrentCost currentCost)
	{
		final CurrencyId currencyId = currentCost.getCurrencyId();
		final Quantity manufacturedQty = mainProductCost.getAccumulatedQty();
		// Negative on-hand cannot capitalize into stock, so the whole residual is period cost (COGS).
		final Quantity qtyInStock = currentCost.getCurrentQty().toZeroIfNegative().min(manufacturedQty);

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

}
