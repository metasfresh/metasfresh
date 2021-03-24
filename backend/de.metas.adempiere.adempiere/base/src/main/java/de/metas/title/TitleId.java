/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.title;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class TitleId implements RepoIdAware
{
	@JsonCreator
	public static TitleId ofRepoId(final int repoId)
	{
		return new TitleId(repoId);
	}

	public static TitleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new TitleId(repoId) : null;
	}

	public static int toRepoId(@Nullable final TitleId titleId)
	{
		return toRepoIdOr(titleId, -1);
	}

	public static int toRepoIdOr(
			@Nullable final TitleId titleId,
			final int defaultValue)
	{
		return titleId != null ? titleId.getRepoId() : defaultValue;
	}

	int repoId;

	private TitleId(final int titleRepoId)
	{
		this.repoId = Check.assumeGreaterThanZero(titleRepoId, "titleRepoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
