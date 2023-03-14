/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.project;

import de.metas.Profiles;
import de.metas.common.rest_api.v2.project.JsonRequestProjectUpsert;
import de.metas.common.rest_api.v2.project.JsonResponseProject;
import de.metas.common.rest_api.v2.project.JsonResponseProjectUpsert;
import de.metas.project.ProjectId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/project" })
@RestController
@Profile(Profiles.PROFILE_App)
public class ProjectRestController
{
	@NonNull
	private final ProjectRestService projectRestService;

	public ProjectRestController(@NonNull final ProjectRestService projectRestService)
	{
		this.projectRestService = projectRestService;
	}

	@Operation(summary = "Create or update projects.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created or updated project(s)"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
	})
	@PutMapping
	public ResponseEntity<JsonResponseProjectUpsert> upsertProjects(
			@RequestBody @NonNull final JsonRequestProjectUpsert request)

	{
		final JsonResponseProjectUpsert responseUpsert = projectRestService.upsertProjects(request);

		return ResponseEntity.ok().body(responseUpsert);
	}

	@Operation(summary = "Retrieve project.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved project"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
	})
	@GetMapping("{projectId}")
	public ResponseEntity<JsonResponseProject> retrieveProject(
			@Parameter(required = true)
			@PathVariable("projectId")
			@NonNull final String projectId)
	{
		final ProjectId id = ProjectId.ofRepoId(Integer.parseInt(projectId));

		final Optional<JsonResponseProject> result = projectRestService.retrieveProject(id);
		return okOrNotFound(result);
	}

	private static <T> ResponseEntity<T> okOrNotFound(@NonNull final Optional<T> optionalResult)
	{
		return optionalResult
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
