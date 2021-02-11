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

package de.metas.rest_api.bpartner.relation;

import de.metas.Profiles;
import de.metas.RestUtils;
import de.metas.organization.OrgId;
import de.metas.rest_api.bpartner.request.JsonRequestBPRelationsUpsert;
import de.metas.rest_api.bpartner.response.JsonResponseBPRelationComposite;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.rest_api.bpartner.impl.BpartnerRestController.ORG_CODE_PARAMETER_DOC;

@RequestMapping(BpartnerRelationRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
// the spelling "Bpartner" is to avoid swagger from spelling it "b-partner-rest.."
public class BpartnerRelationRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/bpartner/relation";
	private final BPRelationsService bpRelationsService;

	public BpartnerRelationRestController(@NonNull final BPRelationsService bpRelationsService)
	{
		this.bpRelationsService = bpRelationsService;
	}

	@ApiOperation("Returns all relations for the given bpartner the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved all relations"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("{orgCode}/{bpartnerIdentifier}")
	public ResponseEntity<JsonResponseBPRelationComposite> retrieveBPartner(
			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code
			
			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);
		final JsonResponseBPRelationComposite result = bpRelationsService.getRelationsForPartner(orgId, bpartnerIdentifier);
		return okOrNotFound(result);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created or updated bpartner relation(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PutMapping(value = "{bpartnerIdentifier}", consumes = "application/json")
	public ResponseEntity<String> createOrUpdateBPartnerRelation(

			@ApiParam(required = true, value = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,
			@RequestBody @NonNull final JsonRequestBPRelationsUpsert bpartnerUpsertRequest)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(bpartnerUpsertRequest.getOrgCode());
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		final IdentifierString locationIdentifier = IdentifierString.ofOrNull(bpartnerUpsertRequest.getLocationIdentifier());
		bpRelationsService.createOrUpdateRelations(orgId, bpartnerIdentifier, locationIdentifier, bpartnerUpsertRequest.getRelatesTo());

		return new ResponseEntity<>("Ok", HttpStatus.CREATED);
	}

	private static ResponseEntity<JsonResponseBPRelationComposite> okOrNotFound(final JsonResponseBPRelationComposite optionalResult)
	{
		return optionalResult.getResponseItems().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(optionalResult);
	}
}
