package de.metas.rest_api.invoicecandidates.impl;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.rest_api.invoicecandidates.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.response.JsonEnqueueForInvoicingResponse;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
public class EnqueueForInvoicingService
{
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	private final InvoiceJsonConverters jsonConverters;

	public EnqueueForInvoicingService(@NonNull final InvoiceJsonConverters jsonConverters)
	{
		this.jsonConverters = jsonConverters;
	}

	public JsonEnqueueForInvoicingResponse enqueueForInvoicing(@NonNull final JsonEnqueueForInvoicingRequest request)
	{
		final InvoiceCandidateMultiQuery multiQuery = jsonConverters.fromJson(request.getInvoiceCandidates());
		final PInstanceId pInstanceId = adPInstanceDAO.createSelectionId();

		invoiceCandBL.createSelectionForInvoiceCandidates(multiQuery, pInstanceId);

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL
				.enqueueForInvoicing()
				.setInvoicingParams(InvoiceJsonConverters.createInvoicingParams(request))
				.setFailIfNothingEnqueued(true)
				.prepareAndEnqueueSelection(pInstanceId);

		return InvoiceJsonConverters.toJson(enqueueResult);
	}
}
