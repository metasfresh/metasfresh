/*
 * #%L
 * de.metas.issue.tracking.everhour
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

package de.metas.issue.tracking.everhour.api;

import com.google.common.collect.ImmutableList;
import de.metas.issue.tracking.everhour.api.model.GetRequest;
import de.metas.issue.tracking.everhour.api.model.GetTeamTimeRecordsRequest;
import de.metas.issue.tracking.everhour.api.model.TimeRecord;
import de.metas.issue.tracking.everhour.api.rest.RestService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static de.metas.issue.tracking.everhour.api.EverhourConstants.BASE_URL;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.DATE_FORMAT;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.Endpoints.TEAM;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.Endpoints.TIME;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.QueryParams.DATE;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.QueryParams.FROM;
import static de.metas.issue.tracking.everhour.api.EverhourConstants.QueryParams.TO;

@Service
public class EverhourClient
{
	private final RestService restService;

	public EverhourClient(final RestService restService)
	{
		this.restService = restService;
	}

	@NonNull
	public ImmutableList<TimeRecord> getTeamTimeRecords(@NonNull final GetTeamTimeRecordsRequest getTeamTimeRecordsRequest)
	{
		final List<String> pathVariables = Arrays.asList(
				TEAM.getValue(),
				TIME.getValue());

		final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

		if (getTeamTimeRecordsRequest.isSingleDate())
		{
			queryParams.add(DATE.getValue(), extractStringDate(getTeamTimeRecordsRequest.getFrom()));
		}
		else
		{
			queryParams.add(FROM.getValue(), extractStringDate(getTeamTimeRecordsRequest.getFrom()));
			queryParams.add(TO.getValue(), extractStringDate(getTeamTimeRecordsRequest.getTo()));
		}

		final GetRequest getRequest = GetRequest.builder()
				.baseURL(BASE_URL)
				.pathVariables(pathVariables)
				.queryParameters(queryParams)
				.apiKey(getTeamTimeRecordsRequest.getApiKey())
				.build();

		return ImmutableList.copyOf( restService.performGet(getRequest, TimeRecord[].class).getBody() );
	}

	@NonNull
	private String extractStringDate(@NonNull final LocalDate localDate)
	{
		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return localDate.format(dateTimeFormatter);
	}
}
