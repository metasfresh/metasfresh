package de.metas.invoicecandidate.externallyreferenced;

import static de.metas.util.lang.CoalesceUtil.coalesce;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery.InvoiceCandidateMultiQueryBuilder;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidate.ExternallyReferencedCandidateBuilder;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.ITaxBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class ExternallyReferencedCandidateRepository
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);

	private final BPartnerCompositeRepository bPartnerCompositeRepository;

	public ExternallyReferencedCandidateRepository(@NonNull final BPartnerCompositeRepository bPartnerCompositeRepository)
	{
		this.bPartnerCompositeRepository = bPartnerCompositeRepository;
	}

	public InvoiceCandidateId save(@NonNull final ExternallyReferencedCandidate ic)
	{
		final InvoiceCandidateId invoiceCandidateId = ic.getLookupKey().getInvoiceCandidateId();
		final ZoneId timeZone = orgDAO.getTimeZone(ic.getOrgId());

		final I_C_Invoice_Candidate icRecord;
		if (invoiceCandidateId == null)
		{
			icRecord = newInstance(I_C_Invoice_Candidate.class);

			final I_C_ILCandHandler handlerRecord = invoiceCandidateHandlerDAO.retrieveForClassOneOnly(Env.getCtx(), ManualCandidateHandler.class);
			icRecord.setC_ILCandHandler_ID(handlerRecord.getC_ILCandHandler_ID());
			icRecord.setIsManual(true);
			icRecord.setAD_Table_ID(0);
			icRecord.setRecord_ID(0);

			icRecord.setAD_Org_ID(ic.getOrgId().getRepoId());
			icRecord.setM_Product_ID(ic.getProductId().getRepoId());

			syncBillPartnerToRecord(ic, icRecord);

			syncQtysToRecord(ic, icRecord);

			icRecord.setIsSOTrx(ic.getSoTrx().toBoolean());

			// TODO fire up the pricing engine and also, get the taxId
			// TODO but it doesn't belong into a repo
			final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

			final BPartnerComposite bpartnerComp = bPartnerCompositeRepository.getById(ic.getBillPartnerInfo().getBpartnerId());
			final BPartnerLocation location = bpartnerComp.extractLocation(ic.getBillPartnerInfo().getBpartnerLocationId()).get();
			final CountryId countryId = countryDAO.getCountryIdByCountryCode(location.getCountryCode());

			final IPricingBL pricingBL = Services.get(IPricingBL.class);
			final IEditablePricingContext pricingContext = pricingBL
					.createInitialContext(
							ic.getProductId(),
							ic.getBillPartnerInfo().getBpartnerId(),
							ic.getQtyOrdered().getStockQty(),
							ic.getSoTrx())
					.setCountryId(countryId)
					.setPriceDate(ic.getDateOrdered());
			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);
			if (!pricingResult.isCalculated())
			{
				throw ProductNotOnPriceListException.builder()
						.pricingCtx(pricingContext)
						.productId(ic.getProductId())
						.build();
			}

			icRecord.setM_PricingSystem_ID(pricingResult.getPricingSystemId().getRepoId());
			icRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(pricingResult.getPriceListVersionId()));
			icRecord.setPriceEntered(pricingResult.getPriceStd());
			icRecord.setC_Currency_ID(pricingResult.getCurrencyId().getRepoId());

			icRecord.setDiscount(pricingResult.getDiscount().toBigDecimal());

			final BigDecimal discountPercentage = pricingResult.getDiscount().computePercentageOf(pricingResult.getPriceStd(), pricingResult.getPrecision().toInt());
			icRecord.setPriceActual(pricingResult.getPriceStd().subtract(discountPercentage));

			final int taxId = Services.get(ITaxBL.class).getTax(
					Env.getCtx(),
					ic,
					pricingResult.getTaxCategoryId(),
					ic.getProductId().getRepoId(),
					TimeUtil.asTimestamp(ic.getDateOrdered(), timeZone), // shipDate
					ic.getOrgId(),
					ic.getSoTrx().isSales() ? orgDAO.getOrgWarehouseId(ic.getOrgId()) : orgDAO.getOrgPOWarehouseId(ic.getOrgId()),
					ic.getBillPartnerInfo().getBpartnerLocationId().getRepoId(), // ship location id
					ic.getSoTrx().toBoolean());
			icRecord.setC_Tax_ID(taxId);

			final String invoiceRule = coalesce(
					bpartnerComp.getBpartner().getInvoiceRule(),
					InvoiceRule.Immediate//
			).getCode();
			icRecord.setInvoiceRule(invoiceRule);
		}
		else
		{
			icRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);
		}

		if (ic.getPriceEnteredOverride() != null)
		{
			// TODO if we have a priceOverride, then verify that its productId, currencyId and priceUomId are the ones of icRecord (no updating)
			icRecord.setPriceEntered_Override(ic.getPriceEnteredOverride().toBigDecimal());
		}
		else
		{
			icRecord.setPriceEntered_Override(null);
		}

		icRecord.setDiscount_Override(Percent.toBigDecimalOrNull(ic.getDiscountOverride()));
		icRecord.setDateOrdered(TimeUtil.asTimestamp(ic.getDateOrdered(), timeZone));
		icRecord.setC_DocTypeInvoice_ID(DocTypeId.toRepoId(ic.getInvoiceDocTypeId()));
		icRecord.setInvoiceRule_Override(InvoiceRule.toCodeOrNull(ic.getInvoiceRuleOverride()));

		icRecord.setDescription(ic.getLineDescription());
		icRecord.setPOReference(ic.getPoReference());
		icRecord.setPresetDateInvoiced(TimeUtil.asTimestamp(ic.getPresetDateInvoiced(), timeZone));

		icRecord.setExternalHeaderId(ic.getLookupKey().getExternalHeaderId().getValue());
		icRecord.setExternalLineId(ic.getLookupKey().getExternalLineId().getValue());

		saveRecord(icRecord);
		return InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());
	}

	private void syncBillPartnerToRecord(final ExternallyReferencedCandidate invoiceCandidate, final I_C_Invoice_Candidate icRecord)
	{
		icRecord.setBill_BPartner_ID(invoiceCandidate.getBillPartnerInfo().getBpartnerId().getRepoId());
		icRecord.setBill_Location_ID(invoiceCandidate.getBillPartnerInfo().getBpartnerLocationId().getRepoId());
		icRecord.setBill_User_ID(BPartnerContactId.toRepoId(invoiceCandidate.getBillPartnerInfo().getContactId()));
	}

	private void syncQtysToRecord(
			@NonNull final ExternallyReferencedCandidate ic,
			@NonNull final I_C_Invoice_Candidate icRecord)
	{
		icRecord.setC_UOM_ID(ic.getInvoicingUomId().getRepoId());
		icRecord.setQtyOrdered(ic.getQtyOrdered().getStockQty().toBigDecimal());
		icRecord.setC_UOM_ID(ic.getQtyOrdered().getUOMQtyNotNull().getUomId().getRepoId());
		icRecord.setQtyEntered(ic.getQtyOrdered().getUOMQtyNotNull().toBigDecimal());

		icRecord.setQtyDelivered(ic.getQtyDelivered().getStockQty().toBigDecimal());
		icRecord.setQtyDeliveredInUOM(ic.getQtyDelivered().getUOMQtyNotNull().toBigDecimal());
	}

	public ImmutableList<ExternallyReferencedCandidate> getAllBy(
			@NonNull final Collection<InvoiceCandidateLookupKey> lookupKeys)
	{

		final InvoiceCandidateMultiQueryBuilder multiQuery = InvoiceCandidateMultiQuery.builder();
		for (final InvoiceCandidateLookupKey invoiceCandidateLookupKey : lookupKeys)
		{
			final InvoiceCandidateQuery query;
			if (invoiceCandidateLookupKey.getInvoiceCandidateId() != null)
			{
				query = InvoiceCandidateQuery.builder()
						.invoiceCandidateId(invoiceCandidateLookupKey.getInvoiceCandidateId())
						.build();
			}
			else
			{
				query = InvoiceCandidateQuery.builder()
						.externalIds(ExternalHeaderIdWithExternalLineIds.builder()
								.externalHeaderId(invoiceCandidateLookupKey.getExternalHeaderId())
								.externalLineId(invoiceCandidateLookupKey.getExternalLineId())
								.build())
						.build();
			}
			multiQuery.query(query);
		}

		final ImmutableList.Builder<ExternallyReferencedCandidate> result = ImmutableList.builder();

		final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO.convertToIQuery(multiQuery.build()).list();
		for (final I_C_Invoice_Candidate invoiceCandidateRecord : invoiceCandidateRecords)
		{
			final ExternallyReferencedCandidate candidate = forRecord(invoiceCandidateRecord);
			result.add(candidate);
		}
		return result.build();
	}

	private ExternallyReferencedCandidate forRecord(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final ExternallyReferencedCandidateBuilder candidate = ExternallyReferencedCandidate.builder();

		candidate.orgId(OrgId.ofRepoId(icRecord.getAD_Org_ID()));
		final InvoiceCandidateLookupKey lookupKey = InvoiceCandidateLookupKey.builder()
				.invoiceCandidateId(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()))
				.externalHeaderId(ExternalId.ofOrNull(icRecord.getExternalHeaderId()))
				.externalLineId(ExternalId.ofOrNull(icRecord.getExternalLineId()))
				.build();
		candidate.lookupKey(lookupKey);

		candidate.poReference(icRecord.getPOReference());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID());
		final BPartnerInfo bpartnerInfo = BPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, icRecord.getBill_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, icRecord.getBill_User_ID()))
				.build();
		candidate.billPartnerInfo(bpartnerInfo);

		candidate.dateOrdered(TimeUtil.asLocalDate(icRecord.getDateOrdered()));

		candidate.discountOverride(Percent.ofNullable(icRecord.getDiscount_Override()));
		candidate.invoiceRuleOverride(InvoiceRule.ofNullableCode(icRecord.getInvoiceRule_Override()));

		final ProductId productId = ProductId.ofRepoId(icRecord.getM_Product_ID()); // the column is not mandatory in the DB, but still
		candidate.productId(productId);

		final ProductPrice priceEnteredOverride = invoiceCandBL.getPriceEnteredOverride(icRecord).orElse(null);
		candidate.priceEnteredOverride(priceEnteredOverride);

		final StockQtyAndUOMQty qtyDelivered = StockQtyAndUOMQtys.create(
				icRecord.getQtyDelivered(),
				productId,
				icRecord.getQtyDeliveredInUOM(),
				UomId.ofRepoId(icRecord.getC_UOM_ID()));
		candidate.qtyDelivered(qtyDelivered);

		final StockQtyAndUOMQty qtyOrdered = StockQtyAndUOMQtys.create(
				icRecord.getQtyOrdered(),
				productId,
				icRecord.getQtyEntered(),
				UomId.ofRepoId(icRecord.getC_UOM_ID()));
		candidate.qtyOrdered(qtyOrdered);

		candidate.soTrx(SOTrx.ofBoolean(icRecord.isSOTrx()));

		candidate.invoiceDocTypeId(DocTypeId.ofRepoIdOrNull(icRecord.getC_DocTypeInvoice_ID()));

		candidate.invoicingUomId(UomId.ofRepoId(icRecord.getC_UOM_ID()));

		candidate.lineDescription(icRecord.getDescription());

		return candidate.build();
	}
}
