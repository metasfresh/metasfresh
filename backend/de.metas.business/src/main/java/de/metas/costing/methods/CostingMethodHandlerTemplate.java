package de.metas.costing.methods;

import com.google.common.collect.ImmutableSet;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CurrentCost;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.AdMessageKey;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.business
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

public abstract class CostingMethodHandlerTemplate implements CostingMethodHandler
{
	private static final AdMessageKey MSG_RevaluatingAnotherRevaluationIsNotSupported = AdMessageKey.of("CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported");
	protected final CostingMethodHandlerUtils utils;

	private static final ImmutableSet<String> HANDLED_TABLE_NAMES = ImmutableSet.<String>builder()
			.add(CostingDocumentRef.TABLE_NAME_M_MatchInv)
			.add(CostingDocumentRef.TABLE_NAME_M_MatchPO)
			.add(CostingDocumentRef.TABLE_NAME_M_Shipping_NotificationLine)
			.add(CostingDocumentRef.TABLE_NAME_M_InOutLine)
			.add(CostingDocumentRef.TABLE_NAME_M_InventoryLine)
			.add(CostingDocumentRef.TABLE_NAME_M_MovementLine)
			.add(CostingDocumentRef.TABLE_NAME_C_ProjectIssue)
			.add(CostingDocumentRef.TABLE_NAME_M_CostRevaluationLine)
			.build();

	protected CostingMethodHandlerTemplate(@NonNull final CostingMethodHandlerUtils utils)
	{
		this.utils = utils;
	}

	@Override
	public final Set<String> getHandledTableNames()
	{
		return HANDLED_TABLE_NAMES;
	}

	@Override
	public final Optional<CostDetailCreateResult> createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final List<CostDetail> existingCostDetails = utils.getExistingCostDetails(request);
		if (!existingCostDetails.isEmpty())
		{
			CostDetail mainCostDetail = null;
			CostDetail costAdjustmentDetail = null;
			CostDetail alreadyShippedDetail = null;
			for (final CostDetail existingCostDetail : existingCostDetails)
			{
				@NonNull final CostAmountType amtType = existingCostDetail.getAmtType();
				switch (amtType)
				{
					case MAIN:
						if (mainCostDetail != null)
						{
							throw new AdempiereException("More than one main cost is not allowed: " + existingCostDetails);
						}
						mainCostDetail = existingCostDetail;
						break;
					case ADJUSTMENT:
						if (costAdjustmentDetail != null)
						{
							throw new AdempiereException("More than one adjustment cost is not allowed: " + existingCostDetails);
						}
						costAdjustmentDetail = existingCostDetail;
						break;
					case ALREADY_SHIPPED:
						if (alreadyShippedDetail != null)
						{
							throw new AdempiereException("More than one already shipped cost is not allowed: " + existingCostDetails);
						}
						alreadyShippedDetail = existingCostDetail;
						break;
					default:
						throw new AdempiereException("Unknown type: " + amtType);
				}
			}

			if (mainCostDetail == null)
			{
				throw new AdempiereException("No main cost detail found in " + existingCostDetails);
			}

			// make sure DateAcct is up-to-date
			utils.updateDateAcct(mainCostDetail, request.getDate());
			if (costAdjustmentDetail != null)
			{
				utils.updateDateAcct(costAdjustmentDetail, request.getDate());
			}
			if (alreadyShippedDetail != null)
			{
				utils.updateDateAcct(alreadyShippedDetail, request.getDate());
			}

			return Optional.of(
					utils.toCostDetailCreateResult(mainCostDetail)
							.withAmtAndQty(CostAmountAndQtyDetailed.builder()
									.main(mainCostDetail.getAmtAndQty())
									.costAdjustment(costAdjustmentDetail != null ? costAdjustmentDetail.getAmtAndQty() : null)
									.alreadyShipped(alreadyShippedDetail != null ? alreadyShippedDetail.getAmtAndQty() : null)
									.build())
			);
		}

		else
		{
			return Optional.ofNullable(createCostOrNull(request));
		}
	}

	private CostDetailCreateResult createCostOrNull(final CostDetailCreateRequest request)
	{
		//
		// Create new cost detail
		final CostingDocumentRef documentRef = request.getDocumentRef();
		if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MatchPO))
		{
			return createCostForMatchPO(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MatchInv))
		{
			final CostElement costElement = request.getCostElement();
			if (costElement == null || costElement.isMaterialElement())
			{
				return createCostForMatchInvoice_MaterialCosts(request);
			}
			else
			{
				return createCostForMatchInvoice_NonMaterialCosts(request);
			}
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_Shipping_NotificationLine))
		{
			return createCostForShippingNotification(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_InOutLine))
		{
			final Boolean outboundTrx = documentRef.getOutboundTrx();
			if (outboundTrx)
			{
				return createCostForMaterialShipment(request);
			}
			else
			{
				final CostElement costElement = request.getCostElement();
				if (costElement == null || costElement.isMaterialElement())
				{
					return createCostForMaterialReceipt(request);
				}
				else
				{
					return createCostForMaterialReceipt_NonMaterialCosts(request);
				}
			}
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MovementLine))
		{
			return createCostForMovementLine(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_InventoryLine))
		{
			return createCostForInventoryLine(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_C_ProjectIssue))
		{
			return createCostForProjectIssue(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_CostRevaluationLine))
		{
			return createCostRevaluationLine(request);
		}
		else
		{
			throw new AdempiereException("Unknown documentRef: " + documentRef);
		}
	}

	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMatchInvoice_MaterialCosts(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMatchInvoice_NonMaterialCosts(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForShippingNotification(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForMaterialShipment(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForMovementLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForInventoryLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForProjectIssue(
			@SuppressWarnings("unused") final CostDetailCreateRequest request)
	{
		throw new UnsupportedOperationException();
	}

	protected CostDetailCreateResult createCostRevaluationLine(
			@NonNull final CostDetailCreateRequest request)
	{
		if (!request.getQty().isZero())
		{
			throw new AdempiereException("Cost revaluation requests shall have Qty=0");
		}

		final CostAmount explicitCostPrice = request.getExplicitCostPrice();
		if (explicitCostPrice == null)
		{
			throw new AdempiereException("Cost revaluation requests shall have explicit cost price set");
		}

		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);

		currentCosts.setOwnCostPrice(explicitCostPrice);
		currentCosts.addCumulatedAmt(request.getAmt());

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(
				request,
				previousCosts);

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	protected CostDetailCreateResult createCostForMaterialReceipt_NonMaterialCosts(CostDetailCreateRequest request)
	{
		throw new AdempiereException("Costing method " + getCostingMethod() + " does not support non material costs receipt")
				.setParameter("request", request)
				.appendParametersToMessage();
	}

	protected abstract CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request);

	@Override
	public CostDetailAdjustment recalculateCostDetailAmountAndUpdateCurrentCost(
			@NonNull final CostDetail costDetail,
			@NonNull final CurrentCost currentCost)
	{
		if (costDetail.getDocumentRef().isCostRevaluationLine())
		{
			throw new AdempiereException(MSG_RevaluatingAnotherRevaluationIsNotSupported)
					.setParameter("costDetail", costDetail);
		}

		final CurrencyPrecision precision = currentCost.getPrecision();

		final Quantity qty = costDetail.getQty();
		final CostAmount oldCostAmount = costDetail.getAmt();
		final CostAmount oldCostPrice = qty.signum() != 0
				? oldCostAmount.divide(qty, precision)
				: oldCostAmount;

		final CostAmount newCostPrice = currentCost.getCostPrice().toCostAmount();
		final CostAmount newCostAmount = qty.signum() != 0
				? newCostPrice.multiply(qty).roundToPrecisionIfNeeded(precision)
				: newCostPrice.roundToPrecisionIfNeeded(precision);

		if (costDetail.isInboundTrx())
		{
			currentCost.addWeightedAverage(newCostAmount, qty, utils.getQuantityUOMConverter());
		}
		else
		{
			currentCost.addToCurrentQtyAndCumulate(qty, newCostAmount, utils.getQuantityUOMConverter());
		}

		//
		return CostDetailAdjustment.builder()
				.costDetailId(costDetail.getId())
				.qty(qty)
				.oldCostPrice(oldCostPrice)
				.oldCostAmount(oldCostAmount)
				.newCostPrice(newCostPrice)
				.newCostAmount(newCostAmount)
				.build();
	}
}
