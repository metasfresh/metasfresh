package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.Profiles;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.security.permissions2.PermissionServiceFactories;
import de.metas.security.permissions2.PermissionServiceFactory;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
@RestController
@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/orders/sales/candidates" })
@Profile(Profiles.PROFILE_App)
public class OrderCandidatesRestControllerImpl
{
	private final String PATH_BULK = "/bulk";

	private static final Logger logger = LogManager.getLogger(OrderCandidatesRestControllerImpl.class);

	private final BpartnerRestController bpartnerRestController;
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final OrderCandidateRestControllerService orderCandidateRestControllerService;

	private PermissionServiceFactory permissionServiceFactory;

	public OrderCandidatesRestControllerImpl(
			@NonNull final BpartnerRestController bpartnerRestController,
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final OrderCandidateRestControllerService orderCandidateRestControllerService)
	{
		this.bpartnerRestController = bpartnerRestController;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.orderCandidateRestControllerService = orderCandidateRestControllerService;
		this.permissionServiceFactory = PermissionServiceFactories.currentContext();
	}

	@VisibleForTesting
	public void setPermissionServiceFactory(@NonNull final PermissionServiceFactory permissionServiceFactory)
	{
		this.permissionServiceFactory = permissionServiceFactory;
	}

	@PostMapping
	public ResponseEntity<JsonOLCandCreateBulkResponse> createOrderLineCandidate(@RequestBody @NonNull final JsonOLCandCreateRequest request)
	{
		return createOrderLineCandidates(JsonOLCandCreateBulkRequest.of(request));
	}

	@PostMapping(PATH_BULK)
	public ResponseEntity<JsonOLCandCreateBulkResponse> createOrderLineCandidates(@RequestBody @NonNull final JsonOLCandCreateBulkRequest bulkRequest)
	{
		try
		{
			bulkRequest.validate();

			final MasterdataProvider masterdataProvider = MasterdataProvider.builder()
					.permissionService(permissionServiceFactory.createPermissionService())
					.bpartnerRestController(bpartnerRestController)
					.externalReferenceRestControllerService(externalReferenceRestControllerService)
					.build();

			final ITrxManager trxManager = Services.get(ITrxManager.class);

			final JsonOLCandCreateBulkResponse response = trxManager
					.callInNewTrx(() -> orderCandidateRestControllerService.creatOrderLineCandidatesBulk(bulkRequest, masterdataProvider));

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		catch (final Exception ex)
		{
			logger.warn("Got exception while processing {}", bulkRequest, ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.badRequest()
					.body(JsonOLCandCreateBulkResponse.error(JsonErrors.ofThrowable(ex, adLanguage)));
		}
	}
}
