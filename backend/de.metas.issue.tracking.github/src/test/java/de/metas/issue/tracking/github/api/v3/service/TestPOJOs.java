/*
 * #%L
 * de.metas.issue.tracking.github
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.issue.tracking.github.api.v3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.issue.tracking.github.api.v3.model.GithubMilestone;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.model.Label;
import de.metas.issue.tracking.github.api.v3.model.PullRequest;
import de.metas.issue.tracking.github.api.v3.model.User;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static de.metas.issue.tracking.github.api.v3.service.TestConstants.MOCK_DATE_ISO_8601;
import static de.metas.issue.tracking.github.api.v3.service.TestConstants.MOCK_DESCRIPTION;
import static de.metas.issue.tracking.github.api.v3.service.TestConstants.MOCK_EXTERNAL_ID;
import static de.metas.issue.tracking.github.api.v3.service.TestConstants.MOCK_EXTERNAL_ISSUE_NO;
import static de.metas.issue.tracking.github.api.v3.service.TestConstants.MOCK_EXTERNAL_URL;
import static de.metas.issue.tracking.github.api.v3.service.TestConstants.MOCK_NAME;
import static de.metas.issue.tracking.github.api.v3.service.TestConstants.MOCK_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void githubMilestone_serialize_deserialize() throws IOException
	{
		final GithubMilestone githubMilestone =  getMockGithubMilestone();

		testSerializeDeserializeObject(githubMilestone);
	}

	@Test
	public void user_serialize_deserialize() throws IOException
	{
		final User user = getMockUser();

		testSerializeDeserializeObject(user);
	}

	@Test
	public void label_serialize_deserialize() throws IOException
	{
		final Label label = getMockLabel();

		testSerializeDeserializeObject(label);
	}

	@Test
	public void issue_serialize_deserialize() throws IOException
	{
		final Issue issue = Issue.builder()
				.githubMilestone(getMockGithubMilestone())
				.labelList(Collections.singletonList(getMockLabel()))
				.title(MOCK_NAME)
				.number(MOCK_EXTERNAL_ISSUE_NO)
				.body(MOCK_DESCRIPTION)
				.id(MOCK_EXTERNAL_ID)
				.htmlUrl(MOCK_EXTERNAL_URL)
				.assignee(getMockUser())
				.state(MOCK_VALUE)
				.pullRequest(PullRequest.builder().url(MOCK_EXTERNAL_URL).build())
				.build();

		testSerializeDeserializeObject(issue);
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

	private GithubMilestone getMockGithubMilestone()
	{
		return GithubMilestone.builder()
				.id(MOCK_EXTERNAL_ID)
				.htmlUrl(MOCK_EXTERNAL_URL)
				.dueDate(MOCK_DATE_ISO_8601)
				.title(MOCK_NAME)
				.state(MOCK_VALUE)
				.description(MOCK_DESCRIPTION)
				.build();
	}

	private Label getMockLabel()
	{
		return Label.builder().name(MOCK_NAME).build();
	}

	private User getMockUser()
	{
		return User.builder().id(MOCK_VALUE).build();
	}
}
