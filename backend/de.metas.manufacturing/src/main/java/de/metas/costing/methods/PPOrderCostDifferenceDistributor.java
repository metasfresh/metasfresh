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
 * Discharges the WIP cost residual of a completed-but-not-closed manufacturing order: the in-stock
 * portion is capitalized onto the finished good's current cost price, the already-shipped remainder
 * spills to COGS.
 * <p>
 * The residual is always recomputed from the order's {@code PP_Order_Cost} rows, as the main-product line's
 * post-calculation amount (the order's total input cost, i.e. what was <i>issued</i>) minus what that line
 * accumulated (what was <i>received</i>). It is therefore the <b>opposite sign</b> of the
 * {@code PP_Order.CostDifference} display column ({@code received - issued}), which is never read here.
 * <p>
 * Once posted, the residual is accumulated onto that same main-product line, so it reads zero afterwards -
 * that self-zeroing is what keeps a re-run (e.g. after a {@code PP_Order_UnClose}) from discharging it twice.
 */
@Component
@RequiredArgsConstructor
public class PPOrderCostDifferenceDistributor
{
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IPPOrderCostBL ppOrderCostsService = Services.get(IPPOrderCostBL.class);
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);
	private final IPPOrderBL ppOrdersService = Services.get(IPPOrderBL.class);

	@NonNull private final ICostElementRepository costElementsRepo;
	@NonNull private final CostingMethodHandlerUtils utils;

	public void distribute(@NonNull final PPOrderId orderId)
	{
		final I_PP_Order order = ppOrdersRepo.getById(orderId);

		// Only decides whether there is anything to discharge at all; the amount that gets posted is recomputed
		// per accounting schema while the collector is posted. A residual that was already discharged reads zero
		// here, so re-running the action after a PP_Order_UnClose is a no-op unless there was further activity.
		final ClientId clientId = ClientId.ofRepoId(order.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final AcctSchemaId acctSchemaId = acctSchemasRepo.getByClientAndOrg(clientId, orgId).getId();

		final CostAmount residual = getResidualCostForOrderOrNull(orderId, acctSchemaId);
		if (residual == null || residual.isZero())
		{
			return;
		}

		costCollectorsService.createCostDifferenceDistribution(order, SystemTime.asZonedDateTime());

		// Closing is part of discharging: it stops any further issue or receipt from re-opening a residual that
		// has just been posted away, and it withdraws the action from the order (the precondition requires
		// completed-and-not-closed).
		ppOrdersService.closeOrder(orderId);
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

		final CostAmount residual = mainProductCost.getResidualCost();
		if (residual.isZero())
		{
			return CostDetailCreateResultsList.EMPTY;
		}

		final CurrentCost currentCost = utils.getCurrentCost(request);
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

		// Discharge the residual on the main-product line as well: it now carries the full cost the order was
		// posted at, so getResidualCost() reads zero and neither a re-post nor a re-run of the action can
		// discharge the same amount twice.
		// Done here rather than in distribute() because the amounts posted above are recomputed from these very
		// rows at posting time - zeroing them earlier would leave nothing to post.
		accumulateOntoMainProduct(orderCosts, mainProductCost, residual);
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
		final CurrentCost currentCost = utils.getCurrentCost(request);

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
	 * Adds {@code amt} to the accumulated amount of the order's main-product cost row, leaving its accumulated
	 * qty alone - the distribution moves value only.
	 */
	private void accumulateOntoMainProduct(
			@NonNull final PPOrderId orderId,
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CostAmount amt)
	{
		final PPOrderCosts orderCosts = ppOrderCostsService.getByOrderId(orderId);
		final PPOrderCost mainProductCost = orderCosts.getMainProductCostOrNull(request.getAcctSchemaId(), request.getCostElementId());
		if (mainProductCost == null)
		{
			return;
		}

		accumulateOntoMainProduct(orderCosts, mainProductCost, amt);
		ppOrderCostsService.save(orderCosts);
	}

	private void accumulateOntoMainProduct(
			@NonNull final PPOrderCosts orderCosts,
			@NonNull final PPOrderCost mainProductCost,
			@NonNull final CostAmount amt)
	{
		orderCosts.accumulateOutboundCostAmount(
				mainProductCost.getCostSegmentAndElement(),
				// accumulateOutbound negates again, so the accumulated amount moves by +amt;
				// the zero qty leaves the accumulated qty untouched.
				amt.negate(),
				mainProductCost.getAccumulatedQty().toZero(),
				utils.getQuantityUOMConverter());
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

}
