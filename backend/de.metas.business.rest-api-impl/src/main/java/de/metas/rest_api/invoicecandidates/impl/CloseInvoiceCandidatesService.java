package de.metas.rest_api.invoicecandidates.impl;

import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.rest_api.invoicecandidates.request.JsonCloseInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponseItem;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponseItem.JsonCloseInvoiceCandidatesResponseItemBuilder;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
public class CloseInvoiceCandidatesService
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	private final InvoiceJsonConverters invoiceJsonConverters;

	public CloseInvoiceCandidatesService(@NonNull final InvoiceJsonConverters invoiceJsonConverters)
	{
		this.invoiceJsonConverters = invoiceJsonConverters;
	}

	public JsonCloseInvoiceCandidatesResponse closeInvoiceCandidates(final JsonCloseInvoiceCandidatesRequest request)
	{
		final InvoiceCandidateMultiQuery multiQuery = invoiceJsonConverters.fromJson(request.getInvoiceCandidates());

		final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO
				.getByQuery(multiQuery);

		final List<JsonCloseInvoiceCandidatesResponseItem> invoiceCandidates = closeInvoiceCandidateRecords(invoiceCandidateRecords);

		return JsonCloseInvoiceCandidatesResponse.builder()
				.invoiceCandidates(invoiceCandidates)
				.build();

	}

	private List<JsonCloseInvoiceCandidatesResponseItem> closeInvoiceCandidateRecords(final List<I_C_Invoice_Candidate> invoiceCandidateRecords)
	{
		final List<JsonCloseInvoiceCandidatesResponseItem> responseItems = new ArrayList<>();

		for (final I_C_Invoice_Candidate invoiceCandidateRecord : invoiceCandidateRecords)
		{
			trxManager.runInNewTrx(() -> responseItems.add(closeInvoiceCandidateRecord(invoiceCandidateRecord)));
		}

		return responseItems;
	}

	private JsonCloseInvoiceCandidatesResponseItem closeInvoiceCandidateRecord(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final JsonCloseInvoiceCandidatesResponseItemBuilder responseItemBuilder = JsonCloseInvoiceCandidatesResponseItem.builder();

		responseItemBuilder.externalHeaderId(JsonExternalId.of(invoiceCandidateRecord.getExternalHeaderId()))
				.externalLineId(JsonExternalId.of(invoiceCandidateRecord.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidateRecord.getC_Invoice_Candidate_ID()));

		try
		{
			invoiceCandBL.closeInvoiceCandidate(invoiceCandidateRecord);
			responseItemBuilder.status(JsonCloseInvoiceCandidatesResponseItem.CloseInvoiceCandidateStatus.Closed);
		}
		catch (final Exception ex)
		{
			responseItemBuilder.status(JsonCloseInvoiceCandidatesResponseItem.CloseInvoiceCandidateStatus.Error)
					.error(JsonErrors.ofThrowable(ex, adLanguage));
		}

		return responseItemBuilder.build();
	}

}
