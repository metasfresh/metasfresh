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

package de.metas.serviceprovider.github.link;

import de.metas.serviceprovider.github.GithubIdSearchKey;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.serviceprovider.github.link.GithubIssueLinkMatcher.ISSUE_NO_GROUP;
import static de.metas.serviceprovider.github.link.GithubIssueLinkMatcher.OWNER_GROUP;
import static de.metas.serviceprovider.github.link.GithubIssueLinkMatcher.PROJECT_GROUP;

@UtilityClass
public class GithubIssueLinkResolver
{
	static Pattern ISSUE_LINK_PATTERN = Pattern.compile("https://github.com/(?<owner>.+)/(?<project>.+)/issues/(?<issueNo>[0-9]+)");

	@NonNull
	public Optional<GithubIssueLink> resolve(@NonNull final String githubLinkCandidate)
	{
		final Matcher matcher = ISSUE_LINK_PATTERN.matcher(githubLinkCandidate);

		if (matcher.find())
		{
			return Optional.of(GithubIssueLink.builder()
									   .githubIdSearchKey(
											   GithubIdSearchKey
													   .builder()
													   .repositoryOwner(matcher.group(OWNER_GROUP))
													   .repository(matcher.group(PROJECT_GROUP))
													   .issueNo(matcher.group(ISSUE_NO_GROUP))
													   .build())
									   .url(matcher.group().trim())
									   .build());
		}

		return Optional.empty();
	}
}
