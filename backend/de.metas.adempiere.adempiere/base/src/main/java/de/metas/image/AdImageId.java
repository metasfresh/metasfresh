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

package de.metas.image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@ToString
@EqualsAndHashCode
public final class AdImageId implements RepoIdAware
{
	@JsonCreator
	@Nullable
	public static AdImageId ofNullableObject(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		try
		{
			final int id = NumberUtils.asInt(obj, -1);
			return ofRepoIdOrNull(id);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Cannot convert `" + obj + "` from " + obj.getClass() + " to " + AdImageId.class, ex);
		}
	}

	public static AdImageId ofRepoId(final int repoId)
	{
		return new AdImageId(repoId);
	}

	@Nullable
	public static AdImageId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private final int repoId;

	public AdImageId(final int repoId)
	{
		Check.assumeGreaterThanZero(repoId, "AD_Image_ID");
		this.repoId = repoId;
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}
}
