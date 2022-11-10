package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

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

	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

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
		final Optional<BPartnerId> salesRepId = getSalesRepId(icRecord);
		if (!salesRepId.isPresent())
		{
			logger.debug("No C_BPartner_SalesRep_ID={} found for C_Invoice_Candidate_ID={}; -> return empty", icRecord.getC_BPartner_SalesRep_ID(), icRecord.getC_Invoice_Candidate_ID());
			return Optional.empty();
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(icRecord.getM_Product_ID());
		if (productId == null)
		{
			// things *might* work with M_Product_ID=0, but I don't see the case and it could introduce all kinds of troubles everywhere
			logger.debug("C_Invoice_Candidate {} has M_Product_ID={}; -> return empty", icRecord.getC_Invoice_Candidate_ID(), icRecord.getM_Product_ID());
			return Optional.empty();
		}

		final UomId uomId = UomId.ofRepoIdOrNull(icRecord.getC_UOM_ID());
		if (uomId == null)
		{
			logger.debug("C_Invoice_Candidate {} has C_UOM_ID={}; -> return empty", icRecord.getC_Invoice_Candidate_ID(), icRecord.getC_UOM_ID());
			return Optional.empty();
		}

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(icRecord.getC_Currency_ID());
		if (currencyId == null)
		{
			logger.debug("C_Invoice_Candidate {} has C_Currency_ID={}; -> return empty", icRecord.getC_Invoice_Candidate_ID(), icRecord.getC_Currency_ID());
			return Optional.empty();
		}

		if (commissionProductService.productPreventsCommissioning(productId))
		{
			logger.debug("C_Invoice_Candidate {} has M_Product_ID={} that prevents commissioning; -> return empty", icRecord.getC_Invoice_Candidate_ID(), icRecord.getM_Product_ID());
			return Optional.empty();
		}

		final DocTypeId invoiceDocTypeId = DocTypeId.ofRepoIdOrNull(icRecord.getC_DocTypeInvoice_ID());
		if (invoiceDocTypeId != null)
		{
			final I_C_DocType invoiceDocType = docTypeBL.getById(invoiceDocTypeId);
			if (invoiceDocType.isExcludeFromCommision())
			{
				logger.debug("C_Invoice_Candidate {} has C_DocTypeInvoice_ID={} which has isExcludeFromCommission=true; -> return empty", icRecord.getC_Invoice_Candidate_ID(), invoiceDocType);
				return Optional.empty();
			}
		}

		final Optional<CommissionPoints> forecastCommissionPoints = extractForecastCommissionPoints(icRecord);
		if (!forecastCommissionPoints.isPresent())
		{
			logger.debug("C_Invoice_Candidate {} has no forecast commission points; -> return empty", icRecord.getC_Invoice_Candidate_ID());
			return Optional.empty();
		}

		return Optional.of(SalesInvoiceCandidate
								   .builder()
								   .orgId(OrgId.ofRepoId(icRecord.getAD_Org_ID()))
								   .id(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()))
								   .salesRepBPartnerId(salesRepId.get())
								   .customerBPartnerId(BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID()))
								   .productId(ProductId.ofRepoId(icRecord.getM_Product_ID()))
								   .commissionDate(TimeUtil.asLocalDate(icRecord.getDateOrdered()))
								   .updated(Objects.requireNonNull(TimeUtil.asInstant(icRecord.getUpdated())))
								   .forecastCommissionPoints(forecastCommissionPoints.get())
								   .commissionPointsToInvoice(extractCommissionPointsToInvoice(icRecord))
								   .invoicedCommissionPoints(extractInvoicedCommissionPoints(icRecord))
								   .totalQtyInvolved(invoiceCandBL.getQtyOrderedStockUOM(icRecord))
								   .currencyId(currencyId)
								   .build());
	}

	@NonNull
	private Optional<CommissionPoints> extractForecastCommissionPoints(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final Quantity forecastQtyStockUOM = invoiceCandBL.getQtyOrderedStockUOM(icRecord)
				.subtract(invoiceCandBL.getQtyToInvoiceStockUOM(icRecord))
				.subtract(invoiceCandBL.getQtyInvoicedStockUOM(icRecord));

		final ProductPrice priceActual = invoiceCandBL.getPriceActual(icRecord);

		final Optional<Quantity> forecastQtyInPriceUOM = uomConversionBL
				.convertQtyTo(forecastQtyStockUOM, priceActual.getUomId());

		if (!forecastQtyInPriceUOM.isPresent())
		{
			logger.debug("C_Invoice_Candidate {}: couldn't convert forecastQty to priceUom; PriceActual: {}, forecastQtyStockUOM: {} -> return empty", icRecord.getC_Invoice_Candidate_ID(), priceActual, forecastQtyStockUOM);
			return Optional.empty();
		}

		final Money forecastAmount = moneyService.multiply(forecastQtyInPriceUOM.get(), priceActual);

		return Optional.of(deductTaxAmount(CommissionPoints.of(forecastAmount.toBigDecimal()), icRecord));
	}

	@NonNull
	private CommissionPoints extractCommissionPointsToInvoice(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints commissionPointsToInvoice = CommissionPoints.of(icRecord.getNetAmtToInvoice());

		return deductTaxAmount(commissionPointsToInvoice, icRecord);
	}

	@NonNull
	private CommissionPoints extractInvoicedCommissionPoints(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints commissionPointsToInvoice = CommissionPoints.of(icRecord.getNetAmtInvoiced());

		return deductTaxAmount(commissionPointsToInvoice, icRecord);
	}

	@NonNull
	private CommissionPoints deductTaxAmount(
			@NonNull final CommissionPoints commissionPoints,
			@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (commissionPoints.isZero())
		{
			return commissionPoints; // don't bother going to the DAO layer
		}

		final int effectiveTaxRepoId = firstGreaterThanZero(icRecord.getC_Tax_Override_ID(), icRecord.getC_Tax_ID());
		if (effectiveTaxRepoId <= 0)
		{
			logger.debug("Invoice candidate has effective C_Tax_ID={}; -> return undedacted commissionPoints={}", effectiveTaxRepoId, commissionPoints);
			return commissionPoints;
		}

		final Tax taxRecord = taxDAO.getTaxById(effectiveTaxRepoId);

		final CurrencyPrecision precision = invoiceCandBL.extractPricePrecision(icRecord);

		final BigDecimal taxAdjustedAmount = taxRecord.calculateBaseAmt(
				commissionPoints.toBigDecimal(),
				icRecord.isTaxIncluded(),
				precision.toInt());
		return CommissionPoints.of(taxAdjustedAmount);
	}

	@NonNull
	private Optional<BPartnerId> getSalesRepId(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final BPartnerId invoiceCandidateSalesRepId = BPartnerId.ofRepoIdOrNull(icRecord.getC_BPartner_SalesRep_ID());

		if (invoiceCandidateSalesRepId != null)
		{
			return Optional.of(invoiceCandidateSalesRepId);
		}

		final I_C_BPartner customerBPartner = bPartnerDAO.getById(icRecord.getBill_BPartner_ID());

		if (customerBPartner.isSalesRep())
		{
			return Optional.of(BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()));
		}

		return Optional.empty();
	}
}
