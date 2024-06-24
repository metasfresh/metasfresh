package de.metas.costing.methods;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

@Component
public class AveragePOCostingMethodHandler extends CostingMethodHandlerTemplate
{
	private final IMatchInvDAO matchInvoicesRepo = Services.get(IMatchInvDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IInOutDAO inoutsRepo = Services.get(IInOutDAO.class);

	public AveragePOCostingMethodHandler(@NonNull final CostingMethodHandlerUtils utils)
	{
		super(utils);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.AveragePO;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		return createCostDetailAndAdjustCurrentCosts(request);
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final Quantity qty = request.getQty();
		final UomId qtyUOMId = qty.getUomId();

		final int matchInvId = request.getDocumentRef().getRecordId();
		final CostAmount costPrice = getPOCostPriceForMatchInv(matchInvId)
				.map(price -> utils.convertToUOM(price, qtyUOMId))
				.orElseThrow(() -> new AdempiereException("Cannot fetch PO cost price for " + request))
				.transform(CostAmount::ofProductPrice);
		final CostAmount amt = costPrice.multiply(qty);
		final CostAmount amtConv = utils.convertToAcctSchemaCurrency(amt, request);

		final CurrentCost currentCost = utils.getCurrentCost(request);

		return utils.createCostDetailRecordNoCostsChanged(
				request.withAmount(amtConv),
				CostDetailPreviousAmounts.of(currentCost));
	}

	@Override
	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		final Quantity qty = request.getQty();
		final UomId qtyUOMId = qty.getUomId();

		final InOutLineId receiptInOutLineId = InOutLineId.ofRepoId(request.getDocumentRef().getRecordId());
		final CostAmount costPrice = getPOCostPriceForReceiptInOutLine(receiptInOutLineId)
				.map(price -> utils.convertToUOM(price, qtyUOMId))
				.map(CostAmount::ofProductPrice)
				.orElseGet(() -> utils.getCurrentCostPrice(request).toCostAmount());
		final CostAmount amt = costPrice.multiply(qty);
		final CostAmount amtConv = utils.convertToAcctSchemaCurrency(amt, request);

		final CurrentCost currentCost = utils.getCurrentCost(request);

		return utils.createCostDetailRecordNoCostsChanged(
				request.withAmount(amtConv),
				CostDetailPreviousAmounts.of(currentCost));
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		return createCostDetailAndAdjustCurrentCosts(request);
	}

	private CostDetailCreateResult createCostDetailAndAdjustCurrentCosts(final CostDetailCreateRequest request)
	{
		final CostAmount explicitCostPrice = request.getExplicitCostPrice();

		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		final CostPrice currentCostPrice = currentCosts.getCostPrice();

		final Quantity requestQty = request.getQty();
		final Quantity qty = utils.convertToUOM(requestQty, currentCostPrice.getUomId(), request.getProductId());

		final boolean isInboundTrx = requestQty.signum() >= 0;

		final CostDetailCreateRequest requestEffective;

		//
		// Inbound transactions (qty >= 0)
		// or Reversals
		if (isInboundTrx || request.isReversal())
		{
			// Seed/initial costs import
			final CostAmount requestAmt = request.getAmt();

			if (request.getDocumentRef().isInventoryLine())
			{
				final CostAmount effectiveAmt = explicitCostPrice != null
						? explicitCostPrice.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision())
						: currentCosts.getCostPrice().multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());

				requestEffective = request.withAmount(effectiveAmt);

				// NOTE: if explicit cost price is provided then use it
				// we are no longer checking for " && currentCosts.getCurrentQty().isZero()"
				// because we agreed that is the responsibility of whom is setting the explicit cost price
				// to decide if it's suitable
				if (explicitCostPrice != null)
				{
					currentCosts.setOwnCostPrice(explicitCostPrice);
				}
			}

			// In case the amount was not provided but there is a positive qty incoming
			// use the current cost price to calculate the amount.
			// In case of reversals, always consider the Amt.

			else
			{
				if (requestAmt.isZero() && !request.isReversal())
				{
					final CostAmount amt = currentCostPrice.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
					requestEffective = request.withAmountAndQty(amt, qty);
				}
				else
				{
					requestEffective = request.withQty(qty);
				}
			}

			currentCosts.addWeightedAverage(requestEffective.getAmt(), requestEffective.getQty(), utils.getQuantityUOMConverter());
		}

		//
		// Outbound transactions (qty < 0)
		else
		{
			final CostPrice price = currentCosts.getCostPrice();
			final CostAmount amt = price.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
			requestEffective = request.withAmountAndQty(amt, qty);

			currentCosts.addToCurrentQtyAndCumulate(qty, amt);
		}

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(
				requestEffective,
				previousCosts);

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		final CostElement costElement = request.getCostElement();
		if (costElement == null)
		{
			throw new AdempiereException("No cost element: " + request);
		}

		final CostSegmentAndElement outboundSegmentAndElement = utils.extractOutboundCostSegmentAndElement(request);
		final CostSegmentAndElement inboundSegmentAndElement = utils.extractInboundCostSegmentAndElement(request);

		final CurrentCost outboundCurrentCosts = utils.getCurrentCost(outboundSegmentAndElement);
		final CostDetailPreviousAmounts outboundPreviousCosts = CostDetailPreviousAmounts.of(outboundCurrentCosts);
		final CostPrice currentCostPrice = outboundCurrentCosts.getCostPrice();
		final Quantity outboundQty = utils.convertToUOM(
				request.getQtyToMove().negate(),
				currentCostPrice.getUomId(),
				request.getProductId());
		final CostAmount outboundAmt = currentCostPrice.multiply(outboundQty).roundToPrecisionIfNeeded(outboundCurrentCosts.getPrecision());

		final CostDetailCreateRequest outboundCostDetailRequest = CostDetailCreateRequest.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.clientId(request.getClientId())
				.orgId(request.getOutboundOrgId())
				.productId(request.getProductId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.documentRef(request.getOutboundDocumentRef())
				.costElement(costElement)
				.amt(outboundAmt)
				.qty(outboundQty)
				.date(request.getDate())
				.build();
		final CostDetailCreateRequest inboundCostDetailRequest = CostDetailCreateRequest.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.clientId(request.getClientId())
				.orgId(request.getInboundOrgId())
				.productId(request.getProductId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.documentRef(request.getInboundDocumentRef())
				.costElement(costElement)
				.amt(outboundAmt.negate())
				.qty(outboundQty.negate())
				.date(request.getDate())
				.build();

		//
		// Moving costs inside costing segment
		// => no changes, just record the cost details
		final CostDetailCreateResult outboundResult;
		final CostDetailCreateResult inboundResult;
		if (Objects.equals(outboundSegmentAndElement, inboundSegmentAndElement))
		{
			final CostDetailPreviousAmounts inboundPreviousCosts = outboundPreviousCosts;

			outboundResult = utils.createCostDetailRecordNoCostsChanged(outboundCostDetailRequest, outboundPreviousCosts);
			inboundResult = utils.createCostDetailRecordNoCostsChanged(inboundCostDetailRequest, inboundPreviousCosts);
		}
		//
		// Moving costs between costing segments
		// => change current costs, record the cost details
		else
		{
			outboundResult = utils.createCostDetailRecordWithChangedCosts(
					outboundCostDetailRequest,
					outboundPreviousCosts);

			outboundCurrentCosts.addToCurrentQtyAndCumulate(outboundQty, outboundAmt, utils.getQuantityUOMConverter());
			utils.saveCurrentCost(outboundCurrentCosts);

			// Inbound cost
			final CurrentCost inboundCurrentCosts = utils.getCurrentCost(inboundSegmentAndElement);
			final CostDetailPreviousAmounts inboundPreviousCosts = CostDetailPreviousAmounts.of(inboundCurrentCosts);
			inboundResult = utils.createCostDetailRecordWithChangedCosts(
					inboundCostDetailRequest,
					inboundPreviousCosts);

			inboundCurrentCosts.addWeightedAverage(
					inboundCostDetailRequest.getAmt(),
					inboundCostDetailRequest.getQty(),
					utils.getQuantityUOMConverter());
			utils.saveCurrentCost(inboundCurrentCosts);
		}

		return MoveCostsResult.builder()
				.outboundCosts(AggregatedCostAmount.builder()
									   .costSegment(outboundSegmentAndElement.toCostSegment())
									   .amount(costElement, outboundResult.getAmt())
									   .build())
				.inboundCosts(AggregatedCostAmount.builder()
									  .costSegment(inboundSegmentAndElement.toCostSegment())
									  .amount(costElement, inboundResult.getAmt())
									  .build())
				.build();
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		final Quantity qty = request.getQty();
		final boolean isInboundTrx = qty.signum() > 0;
		final CurrentCost currentCosts = utils.getCurrentCost(request.getCostSegmentAndElement());
		if (isInboundTrx)
		{
			currentCosts.addWeightedAverage(request.getAmt().negate(), qty.negate(), utils.getQuantityUOMConverter());
		}
		else
		{
			currentCosts.addToCurrentQtyAndCumulate(qty.negate(), request.getAmt().negate(), utils.getQuantityUOMConverter());
		}

		utils.saveCurrentCost(currentCosts);
	}

	private Optional<ProductPrice> getPOCostPriceForMatchInv(final int matchInvId)
	{
		final I_M_MatchInv matchInv = matchInvoicesRepo.getById(matchInvId);
		return Optional.of(matchInv)
				.map(I_M_MatchInv::getC_InvoiceLine)
				.map(I_C_InvoiceLine::getC_OrderLine)
				.map(orderLineBL::getCostPrice);
	}

	private Optional<ProductPrice> getPOCostPriceForReceiptInOutLine(final InOutLineId receiptInOutLineId)
	{
		final I_M_InOutLine receiptLine = inoutsRepo.getLineById(receiptInOutLineId);
		return Optional.of(receiptLine)
				.map(I_M_InOutLine::getC_OrderLine)
				.map(orderLineBL::getCostPrice);
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(
			final CostSegment costSegment,
			final OrderLineId orderLineId_NOTUSED)
	{
		final int productId = costSegment.getProductId().getRepoId();
		final int AD_Org_ID = costSegment.getOrgId().getRepoId();
		final int M_AttributeSetInstance_ID = costSegment.getAttributeSetInstanceId().getRepoId();
		final AcctSchema acctSchema = utils.getAcctSchemaById(costSegment.getAcctSchemaId());
		final CurrencyId acctCurencyId = acctSchema.getCurrencyId();
		final CurrencyPrecision costingPrecision = acctSchema.getCosting().getCostingPrecision();

		String sql = "SELECT t.MovementQty, mp.Qty, ol.QtyOrdered, ol.PriceCost, ol.PriceActual,"    // 1..5
				+ " o.C_Currency_ID, o.DateAcct, o.C_ConversionType_ID,"    // 6..8
				+ " o.AD_Client_ID, o.AD_Org_ID, t.M_Transaction_ID "        // 9..11
				+ "FROM M_Transaction t"
				+ " INNER JOIN M_MatchPO mp ON (t.M_InOutLine_ID=mp.M_InOutLine_ID)"
				+ " INNER JOIN C_OrderLine ol ON (mp.C_OrderLine_ID=ol.C_OrderLine_ID)"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ "WHERE t.M_Product_ID=?";
		if (AD_Org_ID != 0)
		{
			sql += " AND t.AD_Org_ID=?";
		}
		else if (M_AttributeSetInstance_ID != 0)
		{
			sql += " AND t.M_AttributeSetInstance_ID=?";
		}
		sql += " ORDER BY t.M_Transaction_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BigDecimal newStockQty = BigDecimal.ZERO;
		//
		BigDecimal newAverageAmt = BigDecimal.ZERO;
		final int oldTransaction_ID = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, productId);
			if (AD_Org_ID != 0)
			{
				pstmt.setInt(2, AD_Org_ID);
			}
			else if (M_AttributeSetInstance_ID != 0)
			{
				pstmt.setInt(2, M_AttributeSetInstance_ID);
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final BigDecimal oldStockQty = newStockQty;
				final BigDecimal movementQty = rs.getBigDecimal(1);
				int M_Transaction_ID = rs.getInt(11);
				if (M_Transaction_ID != oldTransaction_ID)
				{
					newStockQty = oldStockQty.add(movementQty);
				}
				M_Transaction_ID = oldTransaction_ID;
				//
				final BigDecimal matchQty = rs.getBigDecimal(2);
				if (matchQty == null)
				{
					continue;
				}
				// Assumption: everything is matched
				BigDecimal priceBD = rs.getBigDecimal(4);
				if (priceBD == null || priceBD.signum() == 0)
				{
					priceBD = rs.getBigDecimal(5);            // Actual
				}
				final Money price = Money.of(priceBD, CurrencyId.ofRepoId(rs.getInt(6)));
				final LocalDate dateAcct = TimeUtil.asLocalDate(rs.getTimestamp(7));
				final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(rs.getInt(8));
				final ClientId clientId = ClientId.ofRepoId(rs.getInt(9));
				final OrgId orgId = OrgId.ofRepoId(rs.getInt(10));

				final CurrencyConversionContext conversionCtx = utils.createCurrencyConversionContext(
						dateAcct,
						conversionTypeId,
						clientId,
						orgId);

				final BigDecimal cost = utils.convert(conversionCtx, price, acctCurencyId)
						.getAmount();

				final BigDecimal oldAverageAmt = newAverageAmt;
				final BigDecimal averageCurrent = oldStockQty.multiply(oldAverageAmt);
				final BigDecimal averageIncrease = matchQty.multiply(cost);
				BigDecimal newAmt = averageCurrent.add(averageIncrease);
				newAmt = newAmt.setScale(costingPrecision.toInt(), RoundingMode.HALF_UP);
				newAverageAmt = newAmt.divide(newStockQty, costingPrecision.toInt(), RoundingMode.HALF_UP);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		//
		if (newAverageAmt != null && newAverageAmt.signum() != 0)
		{
			return Optional.of(CostAmount.of(newAverageAmt, acctCurencyId));
		}
		else
		{
			return Optional.empty();
		}
	}

	public void adjustInboundCostDetailAmount(
			@NonNull final CostDetail costDetail,
			@NonNull final CostAmount amount)
	{
		if (costDetail.getAmt().equals(amount))
		{
			return;
		}
		if (!costDetail.isInboundTrx())
		{
			throw new AdempiereException("Only inbound cost details can be adjusted: " + costDetail);
		}

		final CurrentCost currentCost = utils.getCurrentCost(costDetail);
		currentCost.setFrom(costDetail.getPreviousAmounts());

		currentCost.addWeightedAverage(amount, costDetail.getQty(), utils.getQuantityUOMConverter());

		final List<CostDetailAdjustment> nextCostDetailAdjustments = utils.streamAllCostDetailsAfter(costDetail)
				.map(nextCostDetail -> recalculateCostDetailAmount(nextCostDetail, currentCost))
				.collect(ImmutableList.toImmutableList());

		//
		// TODO: Create the final cost detail which is about posting the adjustment
		currentCost.getCostPrice();
		currentCost.getCurrentQty();
		currentCost.getCumulatedAmt();
		currentCost.getCumulatedQty();
	}

	private CostDetailAdjustment recalculateCostDetailAmount(
			final CostDetail costDetail,
			final CurrentCost currentCost)
	{
		final CostDetailPreviousAmounts previousAmounts = CostDetailPreviousAmounts.of(currentCost);
		final Quantity qty = costDetail.getQty();
		final CostAmount amt;

		//
		// Inbound
		if (costDetail.isInboundTrx())
		{
			amt = costDetail.getAmt();
			currentCost.addWeightedAverage(amt, qty, utils.getQuantityUOMConverter());
		}
		//
		// Outbound
		else
		{
			amt = currentCost.getCostPrice()
					.multiply(qty)
					.roundToPrecisionIfNeeded(currentCost.getPrecision());

			currentCost.addToCurrentQtyAndCumulate(qty, amt, utils.getQuantityUOMConverter());
		}

		//
		return CostDetailAdjustment.builder()
				.costDetailId(costDetail.getId())
				.amt(amt)
				.qty(qty)
				.previousAmounts(previousAmounts)
				.build();
	}
}
