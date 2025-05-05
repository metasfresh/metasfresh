/*
 * #%L
 * de.metas.issue.tracking.github
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

package de.metas.issue.tracking.github.api.v3.model;

import com.google.common.collect.ImmutableList;
import de.metas.issue.tracking.github.api.v3.GitHubApiConstants;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.function.Function;

@Value
@Builder
@ToString(exclude = { "oAuthToken", "webhookSecret" })
public class CreatWebhookRequest
{
	@NonNull
	String repositoryName;

	@NonNull
	String repositoryOwner;

	@NonNull
	String webhookURL;

	@NonNull
	String webhookSecret;

	@NonNull
	String oAuthToken;

	@NonNull
	List<GitHubApiConstants.Events> events;

	@NonNull
	public <T> List<T> mapEvents(@NonNull final Function<GitHubApiConstants.Events, T> mappingFunction)
	{
		return events.stream()
				.map(mappingFunction)
				.collect(ImmutableList.toImmutableList());
	}
}
