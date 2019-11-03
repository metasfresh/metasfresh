package de.metas.rest_api.invoicecandidates;

import org.springframework.http.ResponseEntity;

import de.metas.rest_api.invoicecandidates.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.request.JsonRequestInvoiceCandidateUpsert;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsert;
import de.metas.util.rest.MetasfreshRestAPIConstants;

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

	ResponseEntity<JsonResponseInvoiceCandidateUpsert> createOrUpdateInvoiceCandidates(JsonRequestInvoiceCandidateUpsert request);

	ResponseEntity<JsonEnqueueForInvoicingResponse> enqueueForInvoicing(JsonEnqueueForInvoicingRequest request);
}
