package de.metas.rest_api.invoicecandidates.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery.InvoiceCandidateMultiQueryBuilder;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.web.exception.InvalidEntityException;
import de.metas.rest_api.invoicecandidates.request.JsonCloseInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponseItem;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponseItem.JsonCloseInvoiceCandidatesResponseItemBuilder;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;

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
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	public JsonCloseInvoiceCandidatesResponse closeInvoiceCandidates(final JsonCloseInvoiceCandidatesRequest request)
	{

			if (request.getInvoiceCandidates().isEmpty())
			{
				throw new InvalidEntityException(TranslatableStrings.constant("The request's invoiceCandidates array may not be empty"));
			}
			final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds = InvoiceJsonConverters.fromJson(request.getInvoiceCandidates());

			final InvoiceCandidateMultiQueryBuilder multiQuery = InvoiceCandidateMultiQuery.builder();
			for (final ExternalHeaderIdWithExternalLineIds externalId : headerAndLineIds)
			{
				final InvoiceCandidateQuery query = InvoiceCandidateQuery.builder()
						.externalIds(externalId)
						.build();
				multiQuery.query(query);
			}
			final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO
					.getByQuery(multiQuery.build());

			final List<JsonCloseInvoiceCandidatesResponseItem> invoiceCandidates = closeInvoiceCandidateRecords(invoiceCandidateRecords);

			final JsonCloseInvoiceCandidatesResponse result = JsonCloseInvoiceCandidatesResponse.builder()
					.invoiceCandidates(invoiceCandidates)
					.build();

			return result;

	}

	private List<JsonCloseInvoiceCandidatesResponseItem> closeInvoiceCandidateRecords(final List<I_C_Invoice_Candidate> invoiceCandidateRecords)
	{
		final List<JsonCloseInvoiceCandidatesResponseItem> responseItems = new ArrayList<>();

		for(I_C_Invoice_Candidate invoiceCandidateRecord : invoiceCandidateRecords)
		{
			Services.get(ITrxManager.class).runInNewTrx(() -> responseItems.add(closeInvoiceCandidateRecord(invoiceCandidateRecord)));
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
