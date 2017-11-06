package de.metas.ui.web.session.json;

import org.adempiere.util.time.SystemTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.Builder;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * User session changed event to be sent on websocket.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Builder
@ToString
public class JSONUserSessionChangesEvent
{
	/** user's full name/display name */
	@JsonProperty("fullname")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String fullname;

	@JsonProperty("email")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String email;

	@JsonProperty("avatarId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	// IMPORTANT: empty avatarId is perfectly valid and means the avatar was removed 
	private final String avatarId;

	@JsonProperty("language")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLookupValue language;

	@JsonProperty("timestamp")
	private final String timestamp = JSONDate.toJson(SystemTime.asTimestamp());

	public boolean isEmpty()
	{
		return fullname == null
				&& email == null
				&& avatarId == null
				&& language == null;
	}
}
