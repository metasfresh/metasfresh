package de.metas.rest_api.invoicecandidates.impl;

import java.util.ArrayList;
import java.util.List;

import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidate;
import de.metas.rest_api.invoicecandidates.response.InvoiceCandEnqueuerResult;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandCreateResponse;
import de.metas.util.rest.ExternalHeaderAndLineId;
import lombok.NonNull;

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
class InvoiceJsonConverterService
{
	public final JsonInvoiceCandCreateResponse toJson(@NonNull final IInvoiceCandidateEnqueueResult enqueueResult)
	{
		InvoiceCandEnqueuerResult jsonInvoiceCand=InvoiceCandEnqueuerResult.builder().invoiceCandidateEnqueuedCount(enqueueResult.getInvoiceCandidateEnqueuedCount())
				.summaryTranslated(enqueueResult.getSummaryTranslated(Env.getCtx()))
				.totalNetAmtToInvoiceChecksum(enqueueResult.getTotalNetAmtToInvoiceChecksum())
				.workpackageEnqueuedCount(enqueueResult.getWorkpackageEnqueuedCount())
				.workpackageQueueSizeBeforeEnqueueing(enqueueResult.getWorkpackageQueueSizeBeforeEnqueueing()).build();
		return JsonInvoiceCandCreateResponse.ok(jsonInvoiceCand);
	}
	
	public List<ExternalHeaderAndLineId> convertJICToExternalHeaderAndLineIds(
			List<JsonInvoiceCandidate> invoiceCandidates) {
		List<ExternalHeaderAndLineId> headerAndLineIds=new ArrayList<ExternalHeaderAndLineId>();
		for (JsonInvoiceCandidate cand : invoiceCandidates) {
			ExternalHeaderAndLineId headerAndLineId = ExternalHeaderAndLineId.builder()
					.externalHeaderId(cand.getExternalHeaderId()).externalLineIds(cand.getExternalLineIds()).build();
			headerAndLineIds.add(headerAndLineId);
		}
		return headerAndLineIds;
	}

}
