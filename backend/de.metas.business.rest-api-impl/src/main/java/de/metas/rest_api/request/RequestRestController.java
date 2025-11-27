/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.rest_api.request;

import de.metas.Profiles;
import de.metas.bpartner.effective.BPartnerEffectiveBL;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.ordercandidates.impl.MasterdataProvider;
import de.metas.security.permissions2.PermissionServiceFactories;
import de.metas.security.permissions2.PermissionServiceFactory;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/request",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/request",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/request" })
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class RequestRestController
{
	private static final Logger logger = LogManager.getLogger(RequestRestController.class);

	@NonNull private final RequestService requestService;
	@NonNull private final BpartnerRestController bpartnerRestController;
	@NonNull private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	@NonNull private final JsonRetrieverService jsonRetrieverService;
	@NonNull private final ExternalSystemRepository externalSystemRepository;
	@NonNull private final PermissionServiceFactory permissionServiceFactory = PermissionServiceFactories.currentContext();
	@NonNull private final BPartnerEffectiveBL bPartnerEffectiveBL;

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated request"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PostMapping
	public ResponseEntity createRequest(@NonNull final JsonRRequest request)
	{
		try
		{
			final MasterdataProvider masterdataProvider = MasterdataProvider.builder()
					.permissionService(permissionServiceFactory.createPermissionService())
					.bpartnerRestController(bpartnerRestController)
					.externalReferenceRestControllerService(externalReferenceRestControllerService)
					.jsonRetrieverService(jsonRetrieverService)
					.externalSystemRepository(externalSystemRepository)
					.bPartnerEffectiveBL(bPartnerEffectiveBL)
					.build();

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(requestService.upsert(request, masterdataProvider));
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.unprocessableEntity()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}
}
