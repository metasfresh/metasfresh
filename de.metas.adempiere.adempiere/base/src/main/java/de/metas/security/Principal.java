package de.metas.security;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Principal
{
	public static final Principal userId(@NonNull final UserId userId)
	{
		return builder().userId(userId).build();
	}

	public static final Principal userGroupId(@NonNull final UserGroupId userGroupId)
	{
		return builder().userGroupId(userGroupId).build();
	}

	@JsonProperty("userId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	UserId userId;

	@JsonProperty("userGroupId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	UserGroupId userGroupId;

	@Builder(toBuilder = true)
	@JsonCreator
	private Principal(
			@JsonProperty("userId") final UserId userId,
			@JsonProperty("userGroupId") final UserGroupId userGroupId)
	{
		if (userId != null)
		{
			Check.assume(userGroupId == null, "Setting both {} and {} not allowed", userId, userGroupId);
			this.userId = userId;
			this.userGroupId = null;
		}
		else if (userGroupId != null)
		{
			Check.assume(userId == null, "Setting both {} and {} not allowed", userId, userGroupId);
			this.userId = null;
			this.userGroupId = userGroupId;
		}
		else
		{
			throw new AdempiereException("UserId or UserGroupId shall be set");
		}
	}
}
