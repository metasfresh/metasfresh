package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

import java.math.BigDecimal;
import java.util.Optional;

import org.compiere.model.I_C_Tax;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Service
public class SalesInvoiceCandidateFactory
{
	private static final Logger logger = LogManager.getLogger(SalesInvoiceCandidateFactory.class);

	private final MoneyService moneyService;
	private final CommissionProductService commissionProductService;

	public SalesInvoiceCandidateFactory(
			@NonNull final MoneyService moneyService,
			@NonNull final CommissionProductService commissionProductService)
	{
		this.moneyService = moneyService;
		this.commissionProductService = commissionProductService;
	}

	public Optional<SalesInvoiceCandidate> forRecord(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (icRecord.getC_BPartner_SalesRep_ID() <= 0)
		{
			logger.debug("C_Invoice_Candidate has C_BPartner_SalesRep_ID={}; -> return empty", icRecord.getC_BPartner_SalesRep_ID());
			return Optional.empty();
		}
		final ProductId productId = ProductId.ofRepoIdOrNull(icRecord.getM_Product_ID());
		if (productId == null)
		{
			// things *might* work with M_Product_ID=0, but I don't see the case and it could introduce all kinds of troubles everywhere
			logger.debug("C_Invoice_Candidate has M_Product_ID={}; -> return empty", icRecord.getM_Product_ID());
			return Optional.empty();
		}
		if (commissionProductService.productPreventsCommissioning(productId))
		{
			logger.debug("C_Invoice_Candidate has M_Product_ID={} that prevents commissioning; -> return empty", icRecord.getM_Product_ID());
			return Optional.empty();
		}

		return Optional.of(SalesInvoiceCandidate
				.builder()
				.orgId(OrgId.ofRepoId(icRecord.getAD_Org_ID()))
				.id(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()))
				.salesRepBPartnerId(BPartnerId.ofRepoIdOrNull(icRecord.getC_BPartner_SalesRep_ID()))
				.customerBPartnerId(BPartnerId.ofRepoIdOrNull(icRecord.getBill_BPartner_ID()))
				.productId(ProductId.ofRepoId(icRecord.getM_Product_ID()))
				.commissionDate(TimeUtil.asLocalDate(icRecord.getDateOrdered()))
				.updated(TimeUtil.asInstant(icRecord.getUpdated()))
				.forecastCommissionPoints(extractForecastCommissionPoints(icRecord))
				.commissionPointsToInvoice(extractCommissionPointsToInvoice(icRecord))
				.invoicedCommissionPoints(extractInvoicedCommissionPoints(icRecord))
				.tradedCommissionPercent(extractTradedCommissionPercent(icRecord))
				.build());
	}

	private CommissionPoints extractForecastCommissionPoints(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints forecastCommissionPoints;

		final ProductPrice priceActual = Services.get(IInvoiceCandBL.class).getPriceActual(icRecord);

		final BigDecimal forecastQtyInPriceUOM = icRecord.getQtyEntered()
				.subtract(icRecord.getQtyToInvoiceInUOM())
				.subtract(icRecord.getQtyInvoicedInUOM());

		if (useActualPriceForComputingCommissionPoints(icRecord))
		{
			final Money forecastNetAmt = moneyService.multiply(
					Quantitys.create(forecastQtyInPriceUOM, UomId.ofRepoId(icRecord.getC_UOM_ID())),
					priceActual);

			forecastCommissionPoints = CommissionPoints.of(forecastNetAmt.toBigDecimal());
		}
		else
		{
			final BigDecimal baseCommissionPointsPerPriceUOM = icRecord.getBase_Commission_Points_Per_Price_UOM();

			final BigDecimal forecastCommissionPointsAmount = baseCommissionPointsPerPriceUOM.multiply(forecastQtyInPriceUOM);

			forecastCommissionPoints = CommissionPoints.of(forecastCommissionPointsAmount);
		}
		return deductTaxAmount(forecastCommissionPoints, icRecord);
	}

	private CommissionPoints extractCommissionPointsToInvoice(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints commissionPointsToInvoice;

		if (useActualPriceForComputingCommissionPoints(icRecord))
		{
			commissionPointsToInvoice = CommissionPoints.of(icRecord.getNetAmtToInvoice());
		}
		else
		{
			final BigDecimal baseCommissionPointsPerPriceUOM = icRecord.getBase_Commission_Points_Per_Price_UOM();
			commissionPointsToInvoice = CommissionPoints.of(baseCommissionPointsPerPriceUOM.multiply(icRecord.getQtyToInvoiceInUOM()));
		}
		return deductTaxAmount(commissionPointsToInvoice, icRecord);
	}

	private CommissionPoints extractInvoicedCommissionPoints(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints commissionPointsToInvoice;

		if (useActualPriceForComputingCommissionPoints(icRecord))
		{
			commissionPointsToInvoice = CommissionPoints.of(icRecord.getNetAmtInvoiced());
		}
		else
		{
			final BigDecimal baseCommissionPointsPerPriceUOM = icRecord.getBase_Commission_Points_Per_Price_UOM();
			commissionPointsToInvoice = CommissionPoints.of(baseCommissionPointsPerPriceUOM.multiply(icRecord.getQtyInvoicedInUOM()));
		}
		return deductTaxAmount(commissionPointsToInvoice, icRecord);
	}

	private CommissionPoints deductTaxAmount(
			@NonNull final CommissionPoints commissionPoints,
			@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (commissionPoints.isZero())
		{
			return commissionPoints; // don't bother going to the DAO layer
		}
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		final ITaxBL taxBL = Services.get(ITaxBL.class);
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final int effectiveTaxRepoId = firstGreaterThanZero(icRecord.getC_Tax_Override_ID(), icRecord.getC_Tax_ID());
		if (effectiveTaxRepoId <= 0)
		{
			logger.debug("Invoice candidate has effective C_Tax_ID={}; -> return undedacted commissionPoints={}", commissionPoints);
			return commissionPoints;
		}

		final I_C_Tax taxRecord = taxDAO.getTaxById(effectiveTaxRepoId);

		final CurrencyPrecision precision = invoiceCandBL.extractPricePrecision(icRecord);

		final BigDecimal taxAdjustedAmount = taxBL.calculateBaseAmt(
				taxRecord,
				commissionPoints.toBigDecimal(),
				icRecord.isTaxIncluded(),
				precision.toInt());
		return CommissionPoints.of(taxAdjustedAmount);
	}

	private Percent extractTradedCommissionPercent(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		return useActualPriceForComputingCommissionPoints(icRecord) ? Percent.ZERO : Percent.of(icRecord.getTraded_Commission_Percent());
	}

	/**
	 * Use actual price when computing the base commission points sum if {@link I_C_Invoice_Candidate#COLUMN_Base_Commission_Points_Per_Price_UOM}
	 * was not calculated or the price was overwritten.
	 *
	 * @param icRecord Invoice Candidate record
	 * @return true, if the actual price should be used for computing commission points, false otherwise
	 */
	private boolean useActualPriceForComputingCommissionPoints(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		return (icRecord.getBase_Commission_Points_Per_Price_UOM().signum() == 0)
				|| (icRecord.getPriceEntered_Override().signum() > 0
						&& !icRecord.getPriceEntered_Override().equals(icRecord.getPriceEntered()));
	}
}
