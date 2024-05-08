package de.metas.rest_api.invoicecandidates.impl;

import java.util.ArrayList;
import java.util.List;

import org.compiere.util.Env;

import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.rest_api.invoicecandidates.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidateReference;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
@UtilityClass
final class InvoiceJsonConverters
{
	public static JsonEnqueueForInvoicingResponse toJson(@NonNull final IInvoiceCandidateEnqueueResult enqueueResult)
	{
		final JsonEnqueueForInvoicingResponse invoiceCandidateResult = JsonEnqueueForInvoicingResponse.builder()
				.invoiceCandidateEnqueuedCount(enqueueResult.getInvoiceCandidateEnqueuedCount())
				.summaryTranslated(enqueueResult.getSummaryTranslated(Env.getCtx()))
				.totalNetAmtToInvoiceChecksum(enqueueResult.getTotalNetAmtToInvoiceChecksum())
				.workpackageEnqueuedCount(enqueueResult.getWorkpackageEnqueuedCount())
				.workpackageQueueSizeBeforeEnqueueing(enqueueResult.getWorkpackageQueueSizeBeforeEnqueueing())
				.build();
		return invoiceCandidateResult;
	}

	public static List<ExternalHeaderIdWithExternalLineIds> fromJson(@NonNull final List<JsonInvoiceCandidateReference> invoiceCandidates)
	{
		final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds = new ArrayList<>();
		for (final JsonInvoiceCandidateReference cand : invoiceCandidates)
		{
			final ExternalHeaderIdWithExternalLineIds headerAndLineId = ExternalHeaderIdWithExternalLineIds.builder()
					.externalHeaderId(JsonExternalIds.toExternalId(cand.getExternalHeaderId()))
					.externalLineIds(JsonExternalIds.toExternalIds(cand.getExternalLineIds()))
					.build();
			headerAndLineIds.add(headerAndLineId);
		}
		return headerAndLineIds;
	}

	public static PlainInvoicingParams createInvoicingParams(@NonNull final JsonEnqueueForInvoicingRequest request)
	{
		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setDateAcct(request.getDateAcct());
		invoicingParams.setDateInvoiced(request.getDateInvoiced());
		invoicingParams.setIgnoreInvoiceSchedule(request.getIgnoreInvoiceSchedule());
		invoicingParams.setPOReference(request.getPoReference());
		invoicingParams.setSupplementMissingPaymentTermIds(request.getSupplementMissingPaymentTermIds());
		invoicingParams.setUpdateLocationAndContactForInvoice(request.getUpdateLocationAndContactForInvoice());
		return invoicingParams;
	}

}
