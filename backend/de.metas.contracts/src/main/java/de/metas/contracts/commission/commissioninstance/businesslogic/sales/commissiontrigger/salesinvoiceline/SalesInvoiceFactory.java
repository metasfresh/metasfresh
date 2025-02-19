package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceLine.SalesInvoiceLineBuilder;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.impl.InvoiceLineAllocType;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.create;

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
public class SalesInvoiceFactory
{
	private static final Logger logger = LogManager.getLogger(SalesInvoiceFactory.class);

	private final CommissionProductService commissionProductService;

	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	public SalesInvoiceFactory(@NonNull final CommissionProductService commissionProductService)
	{
		this.commissionProductService = commissionProductService;
	}

	public Optional<SalesInvoice> forRecord(@NonNull final I_C_Invoice invoiceRecord)
	{
		if (invoiceCandBL.isCreatedByInvoicingJustNow(invoiceRecord))
		{
			logger.debug("The C_Invoice is now manual but created from an invoice candidate; -> nothing to do");
			return Optional.empty();
		}
		final Optional<BPartnerId> salesRepBPartnerId = getSalesRepId(invoiceRecord);
		if (!salesRepBPartnerId.isPresent())
		{
			logger.debug("C_Invoice has C_BPartner_SalesRep_ID={}; -> return empty", invoiceRecord.getC_BPartner_SalesRep_ID());
			return Optional.empty();
		}
		if (invoiceRecord.getC_DocType_ID() <= 0)
		{
			logger.debug("C_Invoice has C_DocType_ID={}; -> return empty", invoiceRecord.getC_DocType_ID());
			return Optional.empty();
		}
		final BPartnerId customerBPartnerId = BPartnerId.ofRepoIdOrNull(invoiceRecord.getC_BPartner_ID());
		if (customerBPartnerId == null)
		{
			logger.debug("C_Invoice has C_BPartner_ID={}; -> return empty", invoiceRecord.getC_BPartner_ID());
			return Optional.empty();
		}
		final LocalDate dateInvoiced = TimeUtil.asLocalDate(invoiceRecord.getDateInvoiced());
		if (dateInvoiced == null)
		{
			logger.debug("C_Invoice has DateInvoiced=null; -> return empty");
			return Optional.empty();
		}

		final boolean invoiceIsCreditMemo = invoiceBL.isCreditMemo(invoiceRecord);
		if (invoiceIsCreditMemo
				&& create(invoiceRecord, de.metas.adempiere.model.I_C_Invoice.class).isCreditedInvoiceReinvoicable())
		{
			logger.debug("C_Invoice is a credit memo that can be invoiced again via invoice candidates; -> return empty");
			return Optional.empty();
		}

		final List<I_C_InvoiceLine> invoiceLineRecords = Services.get(IInvoiceDAO.class).retrieveLines(invoiceRecord);
		final ArrayList<SalesInvoiceLine> invoiceLines = new ArrayList<>();
		for (final I_C_InvoiceLine invoiceLineRecord : invoiceLineRecords)
		{
			try (final MDCCloseable invoiceLineRecordMDC = TableRecordMDC.putTableRecordReference(invoiceLineRecord))
			{
				final ProductId productId = ProductId.ofRepoIdOrNull(invoiceLineRecord.getM_Product_ID());
				if (productId == null)
				{
					// things *might* work with M_Product_ID=0, but I don't see the case and it could introduce all kinds of troubles everywhere
					logger.debug("C_InvoiceLine has M_Product_ID={}; -> return empty", invoiceLineRecord.getM_Product_ID());
					continue;
				}

				final UomId uomId = UomId.ofRepoIdOrNull(invoiceLineRecord.getC_UOM_ID());
				if (uomId == null)
				{
					logger.debug("C_InvoiceLine has C_UOM_ID={}; -> return empty", invoiceLineRecord.getC_UOM_ID());
					continue;
				}

				if (commissionProductService.productPreventsCommissioning(productId))
				{
					logger.debug("C_InvoiceLine has M_Product_ID={} that prevents commissioning; -> return empty", invoiceLineRecord.getM_Product_ID());
					continue;
				}
				final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveIcForIl(invoiceLineRecord);

				boolean isCreditMemoReinvoiceable = true;
				if (!invoiceCandidates.isEmpty())
				{
					if (invoiceIsCreditMemo)
					{

						for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
						{
							final I_C_Invoice_Line_Alloc alloc = invoiceCandDAO.retrieveIlaForIcAndIl(invoiceCandidate, invoiceLineRecord);

							final InvoiceLineAllocType invoiceLineAllocType = InvoiceLineAllocType.ofCodeNullable(alloc.getC_Invoice_Line_Alloc_Type());

							if (invoiceLineAllocType == null || invoiceLineAllocType.isCreditMemoReinvoiceable())
							{
								logger.debug("C_InvoiceLine_ID={} is a reinvoiceable credit memo line as it has  C_Invoice_Line_Allocation_ID={} with the type {}",
								invoiceLineRecord.getC_InvoiceLine_ID(), alloc.getC_Invoice_Line_Alloc_ID(), invoiceLineAllocType);
								isCreditMemoReinvoiceable = true;
								break;
							}
							isCreditMemoReinvoiceable = false;
						}
					}

					if (isCreditMemoReinvoiceable)
					{
						logger.debug("C_InvoiceLine_ID={} is a reinvoiceable credit memo line; -> skip it", invoiceLineRecord.getC_InvoiceLine_ID());
						continue;
					}
				}

				final CommissionPoints invoicedCommissionPoints = extractInvoicedCommissionPoints(invoiceRecord, invoiceLineRecord)
						.negateIf(invoiceIsCreditMemo);

				final SalesInvoiceLineBuilder invoiceLine = SalesInvoiceLine.builder()
						.id(InvoiceAndLineId.ofRepoId(invoiceLineRecord.getC_Invoice_ID(), invoiceLineRecord.getC_InvoiceLine_ID()))
						.updated(TimeUtil.asInstantNonNull(invoiceLineRecord.getUpdated()))
						.invoicedCommissionPoints(invoicedCommissionPoints)
						.productId(productId)
						.invoicedQty(invoiceLineBL.getQtyInvoicedStockUOM(invoiceLineRecord))
						.currencyId(CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID()));

				invoiceLines.add(invoiceLine.build());
			}
		}
		if (invoiceLines.isEmpty())
		{
			logger.debug("C_Invoice has no commission-relevant lines; -> return empty");
			return Optional.empty();
		}

		final CommissionTriggerType triggerType = invoiceIsCreditMemo ? CommissionTriggerType.SalesCreditmemo : CommissionTriggerType.SalesInvoice;

		return Optional.of(SalesInvoice.builder()
								   .orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()))
								   .id(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()))
								   .customerBPartnerId(customerBPartnerId)
								   .salesRepBPartnerId(salesRepBPartnerId.get())
								   .commissionDate(dateInvoiced)
								   .updated(TimeUtil.asInstantNonNull(invoiceRecord.getUpdated()))
								   .triggerType(triggerType)
								   .currencyId(CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID()))
								   .invoiceLines(ImmutableList.copyOf(invoiceLines))
								   .build());
	}

	@NonNull
	private CommissionPoints extractInvoicedCommissionPoints(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final I_C_InvoiceLine invoiceLineRecord)
	{
		final Money invoicedLineAmount = Money.of(invoiceLineRecord.getLineNetAmt(), CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID()));

		final CommissionPoints forecastCommissionPoints = CommissionPoints.of(invoicedLineAmount.toBigDecimal());

		return deductTaxAmount(forecastCommissionPoints, invoiceLineRecord, invoiceRecord.isTaxIncluded());
	}

	@NonNull
	private CommissionPoints deductTaxAmount(
			@NonNull final CommissionPoints commissionPoints,
			@NonNull final I_C_InvoiceLine invoiceLineRecord,
			final boolean isTaxIncluded)
	{
		if (commissionPoints.isZero())
		{
			return commissionPoints;
		}

		BigDecimal taxAdjustedAmount = invoiceLineRecord.getLineNetAmt();

		if (isTaxIncluded)
		{
			taxAdjustedAmount = taxAdjustedAmount.subtract(invoiceLineRecord.getTaxAmtInfo());
		}

		return CommissionPoints.of(taxAdjustedAmount);
	}

	@NonNull
	private Optional<BPartnerId> getSalesRepId(@NonNull final I_C_Invoice invoiceRecord)
	{
		final BPartnerId invoiceSalesRepId = BPartnerId.ofRepoIdOrNull(invoiceRecord.getC_BPartner_SalesRep_ID());

		if (invoiceSalesRepId != null)
		{
			return Optional.of(invoiceSalesRepId);
		}

		final I_C_BPartner customerBPartner = bPartnerDAO.getById(invoiceRecord.getC_BPartner_ID());

		if (customerBPartner.isSalesRep())
		{
			return Optional.of(BPartnerId.ofRepoId(customerBPartner.getC_BPartner_ID()));
		}

		return Optional.empty();
	}
}
