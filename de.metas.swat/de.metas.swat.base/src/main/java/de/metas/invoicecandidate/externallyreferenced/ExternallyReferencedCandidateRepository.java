package de.metas.invoicecandidate.externallyreferenced;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery.InvoiceCandidateMultiQueryBuilder;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
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

public class ExternallyReferencedCandidateRepository
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	public InvoiceCandidateId save(@NonNull final ExternallyReferencedCandidate invoiceCandidate)
	{
		final InvoiceCandidateId invoiceCandidateId = invoiceCandidate.getLookupKey().getInvoiceCandidateId();

		final I_C_Invoice_Candidate icRecord;
		if (invoiceCandidateId == null)
		{
			icRecord = newInstance(I_C_Invoice_Candidate.class);
			icRecord.setIsManual(true);
		}
		else
		{
			icRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);
		}

		saveRecord(icRecord);
		return InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());
	}

	public ImmutableMap<InvoiceCandidateLookupKey, Optional<ExternallyReferencedCandidate>> getAllBy(
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

		final HashSet<InvoiceCandidateLookupKey> keysWithoutCandidate = new HashSet<>(lookupKeys);
		final ImmutableMap.Builder<InvoiceCandidateLookupKey, Optional<ExternallyReferencedCandidate>> result = ImmutableMap.builder();

		final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO.convertToIQuery(multiQuery.build()).list();
		for (final I_C_Invoice_Candidate invoiceCandidateRecord : invoiceCandidateRecords)
		{
			final ExternallyReferencedCandidate candidate = forRecord(invoiceCandidateRecord);
			final InvoiceCandidateLookupKey lookupKey = candidate.getLookupKey();

			result.put(lookupKey, Optional.of(candidate));
			keysWithoutCandidate.remove(lookupKey);
		}

		for (final InvoiceCandidateLookupKey keyWithoutCandidate : keysWithoutCandidate)
		{
			result.put(keyWithoutCandidate, Optional.empty());
		}

		return result.build();
	}

	private ExternallyReferencedCandidate forRecord(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final ExternallyReferencedCandidate candidate = new ExternallyReferencedCandidate();

		candidate.setOrgId(OrgId.ofRepoId(icRecord.getAD_Org_ID()));

		final InvoiceCandidateLookupKey lookupKey = InvoiceCandidateLookupKey.builder()
				.invoiceCandidateId(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()))
				.externalHeaderId(ExternalId.ofOrNull(icRecord.getExternalHeaderId()))
				.externalLineId(ExternalId.ofOrNull(icRecord.getExternalLineId()))
				.build();
		candidate.setLookupKey(lookupKey);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID());
		final BPartnerInfo bpartnerInfo = BPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, icRecord.getBill_Location_ID()))
				.contactId(BPartnerContactId.ofRepoId(bpartnerId, icRecord.getBill_User_ID()))
				.build();
		candidate.setBillPartnerInfo(bpartnerInfo);

		candidate.setDiscountOverride(Percent.ofNullable(icRecord.getDiscount_Override()));
		candidate.setInvoiceRuleOverride(InvoiceRule.ofNullableCode(icRecord.getInvoiceRule_Override()));

		final ProductId productId = ProductId.ofRepoId(icRecord.getM_Product_ID()); // the column is not mandatory in the DB, but still
		candidate.setProductId(productId);

		final ProductPrice priceEnteredOverride = invoiceCandBL.getPriceEnteredOverride(icRecord).orElse(null);
		candidate.setPriceEnteredOverride(priceEnteredOverride);

		final StockQtyAndUOMQty qtyDelivered = StockQtyAndUOMQtys.create(
				icRecord.getQtyDelivered(),
				productId,
				icRecord.getQtyDeliveredInUOM(),
				UomId.ofRepoId(icRecord.getC_UOM_ID()));
		candidate.setQtyDelivered(qtyDelivered);

		final StockQtyAndUOMQty qtyOrdered = StockQtyAndUOMQtys.create(
				icRecord.getQtyOrdered(),
				productId,
				icRecord.getQtyEntered(),
				UomId.ofRepoId(icRecord.getC_UOM_ID()));
		candidate.setQtyOrdered(qtyOrdered);

		candidate.setSoTrx(SOTrx.ofBoolean(icRecord.isSOTrx()));

		candidate.setInvoiceDocTypeId(DocTypeId.ofRepoIdOrNull(icRecord.getC_DocTypeInvoice_ID()));

		return candidate;
	}
}
