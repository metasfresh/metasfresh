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

package de.metas.rest_api.v2.bpartner.relation;

import de.metas.Profiles;
import de.metas.RestUtils;
import de.metas.common.bprelation.request.JsonRequestBPRelationsUpsert;
import de.metas.common.bprelation.response.JsonResponseBPRelationComposite;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.organization.OrgId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.rest_api.v2.bpartner.BpartnerRestController.ORG_CODE_PARAMETER_DOC;

@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/bpartner/relation" })
@RestController
@Profile(Profiles.PROFILE_App)
// the spelling "Bpartner" is to avoid swagger from spelling it "b-partner-rest.."
public class BpartnerRelationRestController
{
	private final BPRelationsService bpRelationsService;

	public BpartnerRelationRestController(@NonNull final BPRelationsService bpRelationsService)
	{
		this.bpRelationsService = bpRelationsService;
	}

	@Operation(summary = "Returns all relations for the given bpartner the current user's organisation.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all relations"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{orgCode}/{bpartnerIdentifier}")
	public ResponseEntity<JsonResponseBPRelationComposite> retrieveBPartner(
			@Parameter(required = true, description = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") //
			@Nullable final String orgCode, // may be null if called from other metasfresh-code

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr)
	{
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);
		final JsonResponseBPRelationComposite result = bpRelationsService.getRelationsForPartner(orgId, bpartnerIdentifier);
		return okOrNotFound(result);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created or updated bpartner relation(s)"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@PutMapping(value = "{bpartnerIdentifier}", consumes = "application/json")
	public ResponseEntity<String> createOrUpdateBPartnerRelation(

			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,
			@RequestBody @NonNull final JsonRequestBPRelationsUpsert bpartnerUpsertRequest)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(bpartnerUpsertRequest.getOrgCode());
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);
		final ExternalIdentifier locationIdentifier = ExternalIdentifier.ofOrNull(bpartnerUpsertRequest.getLocationIdentifier());
		bpRelationsService.createOrUpdateRelations(orgId, bpartnerIdentifier, locationIdentifier, bpartnerUpsertRequest.getRelatesTo());

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	private static ResponseEntity<JsonResponseBPRelationComposite> okOrNotFound(final JsonResponseBPRelationComposite optionalResult)
	{
		return optionalResult.getResponseItems().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(optionalResult);
	}
}
