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

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.github.service.GithubIssueService;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class GithubIssueRestService
{
	private final static Logger logger = LogManager.getLogger(GithubIssueRestService.class);

	private final GithubIssueService githubIssueService;

	public GithubIssueRestService(@NonNull final GithubIssueService githubIssueService)
	{
		this.githubIssueService = githubIssueService;
	}

	public void syncIssue(@NonNull final String githubPayload, @NonNull final OrgId orgId) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException
	{
		final SyncGithubIssueRequest syncGithubIssueRequest = getRequest(githubPayload);

		if (!syncGithubIssueRequest.isSyncRequired())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Returning! Github action={}. Nothing to be done.", syncGithubIssueRequest.getAction());
			return;
		}

		githubIssueService.importByURL(orgId, syncGithubIssueRequest.getUrl());
	}

	@NonNull
	private static SyncGithubIssueRequest getRequest(@NonNull final String githubPayload) throws JsonProcessingException
	{
		final JsonNode githubPayloadNode = JsonObjectMapperHolder
				.sharedJsonObjectMapper()
				.readTree(githubPayload);

		return SyncGithubIssueRequest.from(githubPayloadNode);
	}
}
