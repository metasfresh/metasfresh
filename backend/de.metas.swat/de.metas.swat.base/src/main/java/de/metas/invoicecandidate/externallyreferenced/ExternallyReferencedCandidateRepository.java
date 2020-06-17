package de.metas.invoicecandidate.externallyreferenced;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.invoice.detail.InvoiceDetailItem;
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
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	public InvoiceCandidateId save(@NonNull final ExternallyReferencedCandidate ic)
	{
		final InvoiceCandidateId invoiceCandidateId = ic.getId();
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

			icRecord.setM_PricingSystem_ID(ic.getPricingSystemId().getRepoId());
			icRecord.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(ic.getPriceListVersionId()));
			icRecord.setPriceEntered(ic.getPriceEntered().toBigDecimal());
			icRecord.setC_Currency_ID(ic.getPriceEntered().getCurrencyId().getRepoId());

			icRecord.setDiscount(ic.getDiscount().toBigDecimal());

			icRecord.setPriceActual(ic.getPriceActual().toBigDecimal());

			icRecord.setC_Tax_ID(ic.getTaxId().getRepoId());

			icRecord.setInvoiceRule(ic.getInvoiceRule().getCode());
		}
		else
		{
			icRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);
		}

		final ProductPrice priceEnteredOverride = ic.getPriceEnteredOverride();
		if (priceEnteredOverride != null)
		{
			final Quantity oneUnitInPriceUom = Quantitys.create(ONE, priceEnteredOverride.getUomId());

			final UOMConversionContext conversionCtx = UOMConversionContext.of(icRecord.getM_Product_ID());
			final UomId icRecordUomId = UomId.ofRepoId(icRecord.getC_UOM_ID());

			// example: priceEnteredOverride is 5€ per pound
			// invoice candidate-UOM is kilo
			// => priceUnitInCandidateUom := 0.5 => price amount is 2.5
			final Quantity priceUnitInCandidateUom = uomConversionBL.convertQuantityTo(oneUnitInPriceUom, conversionCtx, icRecordUomId);
			icRecord.setPriceEntered_Override(priceUnitInCandidateUom.toBigDecimal().multiply(priceEnteredOverride.toBigDecimal()));
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

		icRecord.setExternalHeaderId(ExternalId.toValue(ic.getExternalHeaderId()));
		icRecord.setExternalLineId(ExternalId.toValue(ic.getExternalLineId()));

		saveRecord(icRecord);
		final InvoiceCandidateId persistedInvoiceCandidateId =  InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());

		storeInvoiceDetailItems(persistedInvoiceCandidateId, ic.getInvoiceDetailItems());

		return persistedInvoiceCandidateId;
	}

	private void syncBillPartnerToRecord(
			@NonNull final ExternallyReferencedCandidate invoiceCandidate,
			@NonNull final I_C_Invoice_Candidate icRecord)
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

		final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO.getByQuery(multiQuery.build());
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

		candidate.orgId(OrgId.ofRepoId(icRecord.getAD_Org_ID()))
				.id(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()))
				.externalHeaderId(ExternalId.ofOrNull(icRecord.getExternalHeaderId()))
				.externalLineId(ExternalId.ofOrNull(icRecord.getExternalLineId()));

		candidate.poReference(icRecord.getPOReference());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID());
		final BPartnerInfo bpartnerInfo = BPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, icRecord.getBill_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, icRecord.getBill_User_ID()))
				.build();
		candidate.billPartnerInfo(bpartnerInfo);

		candidate.dateOrdered(TimeUtil.asLocalDate(icRecord.getDateOrdered()));
		candidate.presetDateInvoiced(TimeUtil.asLocalDate(icRecord.getPresetDateInvoiced()));

		candidate.invoiceRule(InvoiceRule.ofCode(icRecord.getInvoiceRule()))
				.invoiceRuleOverride(InvoiceRule.ofNullableCode(icRecord.getInvoiceRule_Override()));

		final ProductId productId = ProductId.ofRepoId(icRecord.getM_Product_ID()); // the column is not mandatory in the DB, but still
		candidate.productId(productId);

		candidate.pricingSystemId(PricingSystemId.ofRepoId(icRecord.getM_PricingSystem_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(icRecord.getM_PriceList_Version_ID()));

		final ProductPrice priceEntered = invoiceCandBL.getPriceEntered(icRecord);
		candidate.priceEntered(priceEntered);

		final ProductPrice priceEnteredOverride = invoiceCandBL.getPriceEnteredOverride(icRecord).orElse(null);
		candidate.priceEnteredOverride(priceEnteredOverride);

		candidate.discount(Percent.ofNullable(icRecord.getDiscount()))
				.discountOverride(Percent.ofNullable(icRecord.getDiscount_Override()));

		candidate.priceActual(invoiceCandBL.getPriceActual(icRecord));

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

		candidate.taxId(TaxId.ofRepoId(icRecord.getC_Tax_ID()));

		return candidate.build();
	}

	/**
	 * Persists the given invoice detail items that are not considered empty.
	 * @see InvoiceDetailItem#isEmpty()
	 *
	 * @param invoiceCandidateId	Id of the invoice candidate to which the detail items are referring.
	 * @param invoiceDetailItems    TO holding additional details about invoice candidate.
	 */
	private void storeInvoiceDetailItems(final InvoiceCandidateId invoiceCandidateId, final List<InvoiceDetailItem> invoiceDetailItems)
	{
		if (CollectionUtils.isNotEmpty(invoiceDetailItems))
		{
			invoiceDetailItems.stream()
					.filter(invoiceDetailItem -> !invoiceDetailItem.isEmpty())
					.map(invoiceDetail -> createI_C_InvoiceDetail(invoiceCandidateId, invoiceDetail))
					.forEach(InterfaceWrapperHelper::saveRecord);
		}
	}

	private I_C_Invoice_Detail createI_C_InvoiceDetail(final InvoiceCandidateId invoiceCandidateId, final InvoiceDetailItem invoiceDetailItem)
	{
		final I_C_Invoice_Detail invoiceDetailEntity = newInstance(I_C_Invoice_Detail.class);

		invoiceDetailEntity.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		invoiceDetailEntity.setSeqNo(NumberUtils.asInt(invoiceDetailItem.getSeqNo(), 0));
		invoiceDetailEntity.setDescription(invoiceDetailItem.getDescription());
		invoiceDetailEntity.setLabel(invoiceDetailItem.getLabel());

		return invoiceDetailEntity;
	}
}
