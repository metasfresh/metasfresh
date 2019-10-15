package de.metas.rest_api.invoicecandidates.impl;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.logging.LogManager;
import de.metas.rest_api.invoicecandidates.InvoiceCandidatesRestEndpoint;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandCreateResponse;
import de.metas.rest_api.utils.PermissionServiceFactories;
import de.metas.rest_api.utils.PermissionServiceFactory;
import de.metas.util.Services;
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
 * Used for managing invoices and invoice candidates(create, query)
 */
@RestController
@RequestMapping(InvoiceCandidatesRestEndpoint.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class InvoiceCandidatesRestControllerImpl implements InvoiceCandidatesRestEndpoint
{

	private static final Logger logger = LogManager.getLogger(InvoiceCandidatesRestControllerImpl.class);

	private IInvoicingParams invoicingParams;

//	private final InvoiceJsonConverters invoiceJsonConverters;
//	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
//	private PermissionServiceFactory permissionServiceFactory;

//	public InvoiceCandidatesRestControllerImpl(
//			@NonNull final InvoiceJsonConverters jsonConverters,
//			@NonNull final IInvoiceCandBL invoiceCandBL)
//	{
////		this.invoiceJsonConverters = invoiceJsonConverters;
//		this.invoiceCandBL = invoiceCandBL;
//		this.permissionServiceFactory = PermissionServiceFactories.currentContext();
//	}

	@PostMapping
	@Override
	public ResponseEntity<JsonInvoiceCandCreateResponse> createInvoices(@RequestBody @NonNull final JsonInvoiceCandCreateRequest request)
	{
//		C_Invoice_Candidate_EnqueueSelectionForInvoicing c=new C_Invoice_Candidate_EnqueueSelectionForInvoicing();
////		
//		
//		final PInstanceId pinstanceId = getPinstanceId();
//		final IParams params = getParameterAsIParams();
//		this.invoicingParams = new InvoicingParams(params);
//
//		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
//				.setContext(getCtx())
//				.setInvoicingParams(invoicingParams)
//				.setFailIfNothingEnqueued(true)
//				.setTotalNetAmtToInvoiceChecksum(request.getCheck_NetAmtToInvoice())
//
//				.enqueueSelection(pinstanceId);
////
////		return enqueueResult.getSummaryTranslated(getCtx());
//		try
//		{
//
//			final ITrxManager trxManager = Services.get(ITrxManager.class);
//
//			final JsonInvoiceCandCreateResponse response = trxManager.callInNewTrx(() -> createInvoiceCandidates());
//
//			//
//			return new ResponseEntity<>(response, HttpStatus.CREATED);
//		}
//		catch (final Exception ex)
//		{
//
//			final String adLanguage = Env.getADLanguageOrBaseLanguage();
//			return ResponseEntity.badRequest()
//					.body(JsonInvoiceCandCreateResponse.error(JsonErrors.ofThrowable(ex, adLanguage)));
//		}
		return null;
	}
}
