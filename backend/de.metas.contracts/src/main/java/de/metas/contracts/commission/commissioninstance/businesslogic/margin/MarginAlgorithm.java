/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.businesslogic.margin;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Payer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.contracts.pricing.trade_margin.ComputeSalesRepPriceRequest;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginService;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.money.CurrencyId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Value
public class MarginAlgorithm implements CommissionAlgorithm
{
	private static final Logger logger = LogManager.getLogger(MarginAlgorithm.class);

	@NonNull
	CustomerTradeMarginService customerTradeMarginService;

	@Override
	@NonNull
	public ImmutableList<CommissionShare> createCommissionShares(@NonNull final CreateCommissionSharesRequest request)
	{
		final List<MarginConfig> configs = request.getConfigs()
				.stream()
				.filter(MarginConfig::isInstance)
				.map(MarginConfig::cast)
				.collect(ImmutableList.toImmutableList());

		if (configs.size() > 1)
		{
			throw new AdempiereException("There should be only one margin commission contract on CreateCommissionSharesRequest")
					.appendParametersToMessage()
					.setParameter("request", request);
		}

		final MarginConfig marginConfig = configs.get(0);

		final CommissionTrigger commissionTrigger = request.getTrigger();
		final Beneficiary beneficiary = Beneficiary.of(commissionTrigger.getSalesRepId());

		if (marginConfig.getContractFor(commissionTrigger.getSalesRepId()) == null)
		{
			throw new AdempiereException("No contract available for the given salesRep!")
					.appendParametersToMessage()
					.setParameter("SalesRep.BPartnerId", commissionTrigger.getSalesRepId())
					.setParameter("CommissionConfig", marginConfig);
		}

		final CommissionShare share = CommissionShare.builder()
				.level(HierarchyLevel.ZERO) //dev-note: there is no hierarchy on margin commission
				.beneficiary(beneficiary)
				.payer(Payer.of(commissionTrigger.getOrgBPartnerId()))
				.soTrx(SOTrx.PURCHASE)
				.config(marginConfig)
				.build();

		createAndAddFacts(share, commissionTrigger.getCommissionTriggerData());

		return ImmutableList.of(share);
	}

	@Override
	public void applyTriggerChangeToShares(final CommissionTriggerChange change)
	{
		final CommissionInstance instanceToChange = change.getInstanceToUpdate();

		final ImmutableList<CommissionShare> sharesToChange = instanceToChange.getShares()
				.stream()
				.filter(share -> MarginConfig.isInstance(share.getConfig()))
				.collect(ImmutableList.toImmutableList());

		Check.assume(sharesToChange.size() == 1, "There should be only one margin commission contract on CreateCommissionSharesRequest");

		createAndAddFacts(sharesToChange.get(0), change.getNewCommissionTriggerData());
	}

	private void createAndAddFacts(
			@NonNull final CommissionShare share,
			@NonNull final CommissionTriggerData commissionTriggerData)
	{
		Check.assume(MarginConfig.isInstance(share.getConfig()), "The commission share is always carrying a margin commission config at this stage!");
		final MarginConfig marginConfig = MarginConfig.cast(share.getConfig());

		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_C_Customer_Trade_Margin.Table_Name, marginConfig.getId()))
		{
			logger.debug("Create commission shares and facts");

			final CommissionPoints salesRepBasedCPointsPerUnit = computeSalesRepBasedCPointsPerUnit(commissionTriggerData, share);

			final CommissionPoints customerBasedCPointsPerUnit = computeCustomerBasedCPointsPerUnit(
					commissionTriggerData.getCommissionBase(),
					commissionTriggerData.getTotalQtyInvolved(),
					marginConfig.getPointsPrecision());

			final CommissionPoints forecastEarnedCPoints = computeEarnedCommissionPointsForSalesRep(
					commissionTriggerData.getForecastedBasePoints(),
					customerBasedCPointsPerUnit,
					salesRepBasedCPointsPerUnit,
					marginConfig.getPointsPrecision());

			final CommissionPoints toInvoiceEarnedCPoints = computeEarnedCommissionPointsForSalesRep(
					commissionTriggerData.getInvoiceableBasePoints(),
					customerBasedCPointsPerUnit,
					salesRepBasedCPointsPerUnit,
					marginConfig.getPointsPrecision());

			final CommissionPoints invoicedEarnedCPoints = computeEarnedCommissionPointsForSalesRep(
					commissionTriggerData.getInvoicedBasePoints(),
					customerBasedCPointsPerUnit,
					salesRepBasedCPointsPerUnit,
					marginConfig.getPointsPrecision());

			final Instant timestamp = commissionTriggerData.getTimestamp();

			final Optional<CommissionFact> forecastedFact = CommissionFact.createFact(timestamp, CommissionState.FORECASTED, forecastEarnedCPoints, share.getForecastedPointsSum());
			final Optional<CommissionFact> toInvoiceFact = CommissionFact.createFact(timestamp, CommissionState.INVOICEABLE, toInvoiceEarnedCPoints, share.getInvoiceablePointsSum());
			final Optional<CommissionFact> invoicedFact = CommissionFact.createFact(timestamp, CommissionState.INVOICED, invoicedEarnedCPoints, share.getInvoicedPointsSum());

			forecastedFact.ifPresent(share::addFact);
			toInvoiceFact.ifPresent(share::addFact);
			invoicedFact.ifPresent(share::addFact);
		}
	}

	@NonNull
	private CommissionPoints computeEarnedCommissionPointsForSalesRep(
			@NonNull final CommissionPoints customerBasedCommissionPointsSum,
			@NonNull final CommissionPoints customerBasedCPointsPerUnit,
			@NonNull final CommissionPoints salesRepBasedCPointsPerUnit,
			@NonNull final Integer pointsPrecision)
	{
		final BigDecimal salesRepBasedCommissionPointsSum = customerBasedCommissionPointsSum.getPoints()
				.divide(customerBasedCPointsPerUnit.getPoints(), pointsPrecision, RoundingMode.HALF_UP)
				.multiply(salesRepBasedCPointsPerUnit.getPoints());

		final CommissionPoints salesRepEarnedCP = customerBasedCommissionPointsSum.subtract(CommissionPoints.of(salesRepBasedCommissionPointsSum));

		return salesRepEarnedCP.getPoints().signum() <= 0
				? CommissionPoints.ZERO
				: salesRepEarnedCP;
	}

	@NonNull
	private CommissionPoints computeCustomerBasedCPointsPerUnit(
			@NonNull final CommissionPoints commissionPointsBase,
			@NonNull final Quantity totalQtyInvolved,
			@NonNull final Integer pointsPrecision)
	{
		final BigDecimal customerPricePerQty = commissionPointsBase.toBigDecimal()
				.divide(totalQtyInvolved.toBigDecimal(), pointsPrecision, RoundingMode.HALF_UP);

		return CommissionPoints.of(customerPricePerQty);
	}

	@NonNull
	private CommissionPoints computeSalesRepBasedCPointsPerUnit(
			@NonNull final CommissionTriggerData commissionTriggerData,
			@NonNull final CommissionShare share)
	{
		final ComputeSalesRepPriceRequest request = ComputeSalesRepPriceRequest.builder()
				.salesRepId(share.getBeneficiary().getBPartnerId())
				.soTrx(SOTrx.SALES)
				.productId(commissionTriggerData.getProductId())
				.qty(commissionTriggerData.getTotalQtyInvolved())
				.customerCurrencyId(commissionTriggerData.getDocumentCurrencyId())
				.commissionDate(commissionTriggerData.getTriggerDocumentDate())
				.build();

		final ProductPrice salesRepNetUnitPrice = customerTradeMarginService.calculateSalesRepNetUnitPrice(request)
				.orElseThrow(() -> new AdempiereException("No price found for SalesRepPricingResultRequest: " + request));

		Check.assume(salesRepNetUnitPrice.getCurrencyId().equals(commissionTriggerData.getDocumentCurrencyId()), "salesRepNetUnitPrice.currencyId={} is in the commissionTriggerDocument.DocumentCurrencyId; commissionTriggerDocument={}", CurrencyId.toRepoId(salesRepNetUnitPrice.getCurrencyId()), commissionTriggerData);
		Check.assume(salesRepNetUnitPrice.getUomId().equals(commissionTriggerData.getTotalQtyInvolved().getUomId()), "salesRepNetUnitPrice.uomId={} is in the commissionTriggerDocument.totalQtyInvolved.uomId; commissionTriggerDocument={}", UomId.toRepoId(salesRepNetUnitPrice.getUomId()), commissionTriggerData);

		return CommissionPoints.of(salesRepNetUnitPrice.toBigDecimal());
	}
}
