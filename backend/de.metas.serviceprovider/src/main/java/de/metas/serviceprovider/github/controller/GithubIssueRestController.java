/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.github.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.Profiles;
import de.metas.organization.OrgId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static de.metas.common.rest_api.v2.APIConstants.GITHUB_ISSUE_CONTROLLER;
import static de.metas.common.rest_api.v2.APIConstants.GITHUB_ISSUE_CONTROLLER_SYNC_ENDPOINT;

@RequestMapping(value = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + GITHUB_ISSUE_CONTROLLER)
@RestController
@Profile(Profiles.PROFILE_App)
public class GithubIssueRestController
{
	private final GithubIssueRestService githubIssueRestService;

	public GithubIssueRestController(@NonNull final GithubIssueRestService githubIssueRestService)
	{
		this.githubIssueRestService = githubIssueRestService;
	}

	@PostMapping(GITHUB_ISSUE_CONTROLLER_SYNC_ENDPOINT)
	public ResponseEntity<?> syncIssue(@RequestBody @NonNull final String githubPayload) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException
	{
		final OrgId loggedInUserOrgId = Env.getOrgId();
		githubIssueRestService.syncIssue(githubPayload, loggedInUserOrgId);

		return ResponseEntity.accepted().build();
	}
}
