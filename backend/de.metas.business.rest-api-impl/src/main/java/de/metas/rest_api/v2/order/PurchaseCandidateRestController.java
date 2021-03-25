/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.order;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.rest_api.order.JsonPurchaseCandidate;
import de.metas.rest_api.order.JsonPurchaseCandidateCreateRequest;
import de.metas.rest_api.order.JsonPurchaseCandidateResponse;
import de.metas.rest_api.order.JsonPurchaseCandidatesRequest;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/order/purchase")
@Profile(Profiles.PROFILE_App)
public class PurchaseCandidateRestController
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final CreatePurchaseCandidatesService createPurchaseCandidatesService;
	private final EnqueuePurchaseCandidateService enqueuePurchaseCandidateService;
	private final PurchaseCandidatesStatusService purchaseCandidatesStatusService;

	public PurchaseCandidateRestController(final CreatePurchaseCandidatesService createPurchaseCandidatesService,
			final EnqueuePurchaseCandidateService enqueuePurchaseCandidateService,
			final PurchaseCandidatesStatusService purchaseCandidatesStatusService)
	{
		this.createPurchaseCandidatesService = createPurchaseCandidatesService;
		this.enqueuePurchaseCandidateService = enqueuePurchaseCandidateService;
		this.purchaseCandidatesStatusService = purchaseCandidatesStatusService;
	}

	@ApiOperation("Create new purchase order candidates")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created new purchase order candidate(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to create new purchase order candidates"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PostMapping(path = "/createCandidates", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JsonPurchaseCandidateResponse> createCandidates(@RequestBody final JsonPurchaseCandidateCreateRequest request)
	{
		return ResponseEntity.ok(trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> createCandidates0(request)));
	}

	private JsonPurchaseCandidateResponse createCandidates0(@RequestBody final JsonPurchaseCandidateCreateRequest request)
	{
		final ImmutableList<JsonPurchaseCandidate> candidates = request.getPurchaseCandidates()
				.stream()
				.map(createPurchaseCandidatesService::createCandidate)
				.collect(ImmutableList.toImmutableList());
		return JsonPurchaseCandidateResponse.builder()
				.purchaseCandidates(candidates)
				.build();
	}

	@ApiOperation("Enqueues purchase candidates for creation of purchase orders")
	@PostMapping(path = "/enqueueForOrdering", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> enqueue(@RequestBody @NonNull final JsonPurchaseCandidatesRequest request)
	{
		enqueuePurchaseCandidateService.enqueue(request);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PutMapping(value = "/status", consumes = "application/json", produces = "application/json")
	public JsonPurchaseCandidateResponse status(@RequestBody final JsonPurchaseCandidatesRequest request)
	{
		return purchaseCandidatesStatusService.getStatusForPurchaseCandidates(request);
	}

}
