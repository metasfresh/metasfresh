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

package de.metas.rest_api.v2.project.budget;

import de.metas.Profiles;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertResponse;
import de.metas.project.ProjectId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/project/budget" })
@Profile(Profiles.PROFILE_App)
public class BudgetProjectRestController
{
	final BudgetProjectRestService projectRestService;

	public BudgetProjectRestController(@NonNull final BudgetProjectRestService projectRestService)
	{
		this.projectRestService = projectRestService;
	}

	@ApiOperation("Create or update budget projects with their associated resources.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created or updated budget project"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PutMapping
	public ResponseEntity<JsonBudgetProjectUpsertResponse> upsertBudgetProject(
			@RequestBody @NonNull final JsonBudgetProjectUpsertRequest request)
	{
		final JsonBudgetProjectUpsertResponse response = projectRestService.upsertBudgetProject(request);

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<JsonBudgetProjectResponse> retrieveBudgetProjectWithResources(@PathVariable("projectId") @NonNull final Integer projectId)
	{
		final JsonBudgetProjectResponse response = projectRestService.retrieveBudgetProjectWithResourcesById(ProjectId.ofRepoId(projectId));

		return ResponseEntity.ok(response);
	}

}
