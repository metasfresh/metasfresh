package de.metas.uom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/*
 * #%L
 * de.metas.business
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

@Value
public class UomId implements RepoIdAware
{
	public static final UomId EACH = new UomId(100);

	@JsonCreator
	public static UomId ofRepoId(final int repoId)
	{
		if (repoId == EACH.repoId)
		{
			return EACH;
		}
		else
		{
			return new UomId(repoId);
		}
	}

	@Nullable
	public static UomId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final UomId uomId)
	{
		return uomId != null ? uomId.getRepoId() : -1;
	}

	public static Optional<UomId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private UomId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_UOM_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final UomId id1, @Nullable final UomId id2)
	{
		return Objects.equals(id1, id2);
	}

	@NonNull
	@SafeVarargs
	public static <T> UomId getCommonUomIdOfAll(
			@NonNull final Function<T, UomId> getUomId,
			@NonNull final String name,
			@Nullable final T... objects)
	{
		if (objects == null || objects.length == 0)
		{
			throw new AdempiereException("No " + name + " provided");
		}
		else if (objects.length == 1 && objects[0] != null)
		{
			return getUomId.apply(objects[0]);
		}
		else
		{
			UomId commonUomId = null;
			for (final T object : objects)
			{
				if (object == null)
				{
					continue;
				}

				final UomId uomId = getUomId.apply(object);
				if (commonUomId == null)
				{
					commonUomId = uomId;
				}
				else if (!UomId.equals(commonUomId, uomId))
				{
					throw new AdempiereException("All given " + name + "(s) shall have the same UOM: " + Arrays.asList(objects));
				}
			}

			if (commonUomId == null)
			{
				throw new AdempiereException("At least one non null " + name + " instance was expected: " + Arrays.asList(objects));
			}

			return commonUomId;
		}
	}
}
