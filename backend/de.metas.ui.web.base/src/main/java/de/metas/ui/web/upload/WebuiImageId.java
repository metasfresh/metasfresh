package de.metas.ui.web.upload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.image.AdImageId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE) // cannot use it because of "otherProperties"
@ToString
@EqualsAndHashCode
public final class WebuiImageId implements RepoIdAware
{
	@JsonCreator
	public static final WebuiImageId ofNullableObject(final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		try
		{
			final int id;
			if (obj instanceof Number)
			{
				id = ((Number)obj).intValue();
			}
			else
			{
				final String idStr = obj.toString().trim();
				id = !idStr.isEmpty() ? Integer.parseInt(idStr) : -1;
			}

			return ofRepoIdOrNull(id);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Cannot convert `" + obj + "` from " + obj.getClass() + " to " + WebuiImageId.class, ex);
		}
	}

	public static WebuiImageId ofRepoId(final int repoId)
	{
		return new WebuiImageId(repoId);
	}

	public static WebuiImageId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private final int repoId;

	public WebuiImageId(final int repoId)
	{
		Check.assumeGreaterThanZero(repoId, "repoId");
		this.repoId = repoId;
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public AdImageId toAdImageId() {return AdImageId.ofRepoId(repoId);}
}
