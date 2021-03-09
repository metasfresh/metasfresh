package de.metas.rest_api.invoice.impl;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidatesResponseItem;
import de.metas.rest_api.invoicecandidates.response.JsonReverseInvoiceResponse;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@Service
public class InvoiceService
{
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	public Optional<byte[]> getInvoicePDF(@NonNull final InvoiceId invoiceId)
	{
		return getLastArchive(invoiceId).map(archiveBL::getBinaryData);
	}

	public boolean hasArchive(@NonNull final InvoiceId invoiceId)
	{
		return getLastArchive(invoiceId).isPresent();
	}

	private Optional<I_AD_Archive> getLastArchive(@NonNull final InvoiceId invoiceId)
	{
		return archiveBL.getLastArchive(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId));
	}

	@NonNull
	public JSONInvoiceInfoResponse getInvoiceInfo(@NonNull final InvoiceId invoiceId, final String ad_language)
	{
		final JSONInvoiceInfoResponse.JSONInvoiceInfoResponseBuilder result = JSONInvoiceInfoResponse.builder();

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		final CurrencyCode currency = currencyDAO.getCurrencyCodeById(CurrencyId.ofRepoId(invoice.getC_Currency_ID()));

		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoiceId);
		for (final I_C_InvoiceLine line : lines)
		{
			final String productName = productBL.getProductNameTrl(ProductId.ofRepoId(line.getM_Product_ID())).translate(ad_language);
			final Percent taxRate = taxDAO.getRateById(TaxId.ofRepoId(line.getC_Tax_ID()));

			result.lineInfo(JSONInvoiceLineInfo.builder()
									.lineNumber(line.getLine())
									.productName(productName)
									.qtyInvoiced(line.getQtyEntered())
									.price(line.getPriceEntered())
									.taxRate(taxRate)
									.lineNetAmt(line.getLineNetAmt())
									.currency(currency)
									.build());
		}
		return result.build();
	}

	@NonNull
	public Optional<JsonReverseInvoiceResponse> reverseInvoice(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice documentRecord = invoiceDAO.getByIdInTrx(invoiceId);
		if (documentRecord == null)
		{
			return Optional.empty();
		}

		documentBL.processEx(documentRecord, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

		final JsonReverseInvoiceResponse.JsonReverseInvoiceResponseBuilder responseBuilder = JsonReverseInvoiceResponse.builder();

		invoiceCandDAO
				.retrieveInvoiceCandidates(invoiceId)
				.stream()
				.map(this::buildJSONItem)
				.forEach(responseBuilder::affectedInvoiceCandidate);

		return Optional.of(responseBuilder.build());
	}

	private JsonInvoiceCandidatesResponseItem buildJSONItem(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return JsonInvoiceCandidatesResponseItem
				.builder()
				.externalHeaderId(JsonExternalId.ofOrNull(invoiceCandidate.getExternalHeaderId()))
				.externalLineId(JsonExternalId.ofOrNull(invoiceCandidate.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidate.getC_Invoice_Candidate_ID()))
				.build();
	}
}
