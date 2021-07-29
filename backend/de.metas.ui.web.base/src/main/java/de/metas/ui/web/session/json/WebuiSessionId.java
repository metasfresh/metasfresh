/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.session.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode
public class WebuiSessionId
{
	@JsonCreator
	public static WebuiSessionId ofString(@NonNull final String sessionId)
	{
		return new WebuiSessionId(sessionId);
	}

	@Nullable
	public static WebuiSessionId ofNullableString(@Nullable final String sessionId)
	{
		final String sessionIdNorm = StringUtils.trimBlankToNull(sessionId);
		return sessionIdNorm != null ? new WebuiSessionId(sessionIdNorm) : null;
	}

	private final String sessionId;

	private WebuiSessionId(@NonNull final String sessionId)
	{
		this.sessionId = StringUtils.trimBlankToNull(sessionId);
		if (this.sessionId == null)
		{
			throw new AdempiereException("sessionId shall not be empty");
		}
	}

	/**
	 * @deprecated please use {@link #getAsString()}
	 */
	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return sessionId;
	}
}
