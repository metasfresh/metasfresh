package de.metas.rest_api.invoicecandidates;

import org.springframework.http.ResponseEntity;

import de.metas.rest_api.invoicecandidates.request.JsonCheckInvoiceCandidatesStatusRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCloseInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCloseInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.util.web.MetasfreshRestAPIConstants;

/*
 * #%L
 * de.metas.business.rest-api
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

public interface IInvoicesRestEndpoint
{
	String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/invoices/";

	ResponseEntity<JsonCreateInvoiceCandidatesResponse> createInvoiceCandidates(JsonCreateInvoiceCandidatesRequest request);

	ResponseEntity<JsonCloseInvoiceCandidatesResponse> closeInvoiceCandidates(JsonCloseInvoiceCandidatesRequest request);

	ResponseEntity<JsonEnqueueForInvoicingResponse> enqueueForInvoicing(JsonEnqueueForInvoicingRequest request);

	ResponseEntity<JsonCheckInvoiceCandidatesStatusResponse> checkInvoiceCandidatesStatus(JsonCheckInvoiceCandidatesStatusRequest request);

	ResponseEntity<byte[]> getInvoicePDF(int invoiceRecordId);

	ResponseEntity<?> getInvoiceInfo(int invoiceRecordId);
}
