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
import io.swagger.v3.oas.annotations.Operation;
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

import static de.metas.rest_api.v2.project.budget.BudgetProjectRestController.BUDGET_PROJECT_REST_CONTROLLER_PATH_V2;

@RestController
@RequestMapping(value = { BUDGET_PROJECT_REST_CONTROLLER_PATH_V2 })
@Profile(Profiles.PROFILE_App)
public class BudgetProjectRestController
{
	public static final String BUDGET_PROJECT_REST_CONTROLLER_PATH_V2 = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/project/budget";

	final BudgetProjectRestService projectRestService;

	public BudgetProjectRestController(@NonNull final BudgetProjectRestService projectRestService)
	{
		this.projectRestService = projectRestService;
	}

	@Operation(summary = "Create or update budget projects with their associated resources.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created or updated budget project"),
			@ApiResponse(responseCode = "401", description = "You are not authorized to create or update the resource"),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(responseCode = "422", description = "The request entity could not be processed")
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
