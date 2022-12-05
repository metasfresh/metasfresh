/*
 * #%L
 * de.metas.business
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

package de.metas.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class RequestStatusCategoryId implements RepoIdAware
{
	@JsonCreator
	public static RequestStatusCategoryId ofRepoId(final int repoId)
	{
		return new RequestStatusCategoryId(repoId);
	}

	@Nullable
	public static RequestStatusCategoryId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new RequestStatusCategoryId(repoId) : null;
	}

	@Nullable
	public static RequestStatusCategoryId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new RequestStatusCategoryId(repoId) : null;
	}

	public static int toRepoId(@Nullable final RequestStatusCategoryId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private RequestStatusCategoryId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "R_StatusCategory_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

}