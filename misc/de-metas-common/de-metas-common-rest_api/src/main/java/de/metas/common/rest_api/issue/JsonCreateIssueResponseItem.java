/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.issue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCreateIssueResponseItem
{

	@JsonProperty("issueId")
	JsonMetasfreshId issueId;

	@Builder
	@JsonCreator
	private JsonCreateIssueResponseItem(@JsonProperty("issueId") @NonNull final JsonMetasfreshId issueId)
	{
		this.issueId = issueId;
	}
}
