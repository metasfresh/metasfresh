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
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
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
	@NonNull private final CurrentCostsRepository currentCostsRepo;
	@NonNull private final CostingMethodHandlerUtils utils;

	public void distribute(@NonNull final PPOrderId orderId)
	{
		// The order stays Completed after distributing, so the action remains offered; re-running would
		// recompute the identical residual and capitalize the current cost price a second time.
		if (isAlreadyDistributed(orderId))
		{
			throw new AdempiereException("@Processed@")
					.setParameter("PP_Order_ID", orderId)
					.appendParametersToMessage();
		}

		final I_PP_Order order = ppOrdersRepo.getById(orderId);

		final ClientId clientId = ClientId.ofRepoId(order.getAD_Client_ID());
		// The order's org schema — the one its PP_Order_Cost rows were created for; not necessarily the client's primary one.
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final AcctSchemaId acctSchemaId = acctSchemasRepo.getByClientAndOrg(clientId, orgId).getId();

		final ResidualAndManufacturedQty residualAndQty = computeResidualAndManufacturedQtyForOrder(orderId, acctSchemaId);

		if (residualAndQty.getResidual().isZero())
		{
			return;
		}

		final CurrentCost currentCost = currentCostsRepo.getOrCreate(residualAndQty.getMainProductCostSegment());

		final CostAmountDetailed split = distributeOnto(
				residualAndQty.getResidual(),
				residualAndQty.getManufacturedQty(),
				currentCost,
				utils.getQuantityUOMConverter());

		if (!split.getCostAdjustmentAmt().isZero())
		{
			currentCostsRepo.save(currentCost);
		}

		// The collector carries no monetary field; the split is recomputed from PP_Order_Cost when posting.
		costCollectorsService.createCostDifferenceDistribution(
				order,
				ProductId.ofRepoId(order.getM_Product_ID()),
				residualAndQty.getManufacturedQty());
	}

	/**
	 * Recomputes the capitalize/COGS split for posting, without moving the current cost price ({@link #distribute}
	 * already did that), so a repost never moves it again. The caller must pass the schema it is posting to: posting
	 * runs once per configured {@link AcctSchemaId}, not only the primary one.
	 *
	 * @return a zero split when there is nothing to discharge
	 */
	public CostAmountDetailed computeSplitForPosting(@NonNull final PPOrderId orderId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ResidualAndManufacturedQty residualAndQty = computeResidualAndManufacturedQtyForOrder(orderId, acctSchemaId);

		if (residualAndQty.getResidual().isZero())
		{
			return CostAmountDetailed.zero(residualAndQty.getResidual().getCurrencyId());
		}

		final CostSegmentAndElement mainProductCostSegment = residualAndQty.getMainProductCostSegment();
		final CurrentCost currentCost = currentCostsRepo.getOrNull(mainProductCostSegment);
		if (currentCost == null)
		{
			// Never fabricate a zero-qty row here: qtyInStock would read 0 and the whole residual would misroute to COGS.
			throw new AdempiereException("CurrentCost record not found for " + mainProductCostSegment
					+ " — expected to already exist from the 'Distribute' action");
		}

		return computeSplit(residualAndQty.getResidual(), residualAndQty.getManufacturedQty(), currentCost);
	}

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

	/** Splits the residual and capitalizes the adjustment leg onto the given {@code currentCost}, which it mutates. */
	@VisibleForTesting
	static CostAmountDetailed distributeOnto(
			@NonNull final CostAmount residual,
			@NonNull final Quantity manufacturedQty,
			@NonNull final CurrentCost currentCost,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		final CostAmountDetailed split = computeSplit(residual, manufacturedQty, currentCost);

		final CostAmount capitalized = split.getCostAdjustmentAmt();
		if (!capitalized.isZero())
		{
			// Zero qty delta => reprices the existing on-hand qty by the capitalized amount.
			currentCost.addWeightedAverage(capitalized, manufacturedQty.toZero(), uomConverter);
		}

		return split;
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
	@VisibleForTesting
	static ResidualAndManufacturedQty computeResidualAndManufacturedQty(
			@NonNull final PPOrderCosts orderCosts,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId materialCostElementId)
	{
		final PPOrderCost mainProductCost = getMainProductCost(orderCosts, acctSchemaId, materialCostElementId);
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
		return ResidualAndManufacturedQty.of(residual, mainProductCost.getAccumulatedQty(), mainProductCost.getCostSegmentAndElement());
	}

	private static PPOrderCost getMainProductCost(
			@NonNull final PPOrderCosts orderCosts,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId materialCostElementId)
	{
		return orderCosts.toCollection().stream()
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> materialCostElementId.equals(cost.getCostElementId()))
				.filter(PPOrderCost::isMainProduct)
				.collect(GuavaCollectors.singleElementOrThrow(
						() -> new AdempiereException("Expected exactly one main-product PP_Order_Cost row for acctSchema=" + acctSchemaId
								+ ", costElement=" + materialCostElementId + " in " + orderCosts)));
	}

	@Value(staticConstructor = "of")
	static class ResidualAndManufacturedQty
	{
		@NonNull CostAmount residual;
		@NonNull Quantity manufacturedQty;
		@NonNull CostSegmentAndElement mainProductCostSegment;
	}
}
