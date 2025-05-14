package de.metas.pricing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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
public class PricingSystemId implements RepoIdAware
{
	@JsonCreator
	public static PricingSystemId ofRepoId(final int repoId)
	{
		if (repoId == NONE.repoId)
		{
			return NONE;
		}
		return new PricingSystemId(repoId);
	}

	@Nullable
	public static PricingSystemId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static final PricingSystemId NULL = null;
	public static final PricingSystemId NONE = new PricingSystemId(100);

	int repoId;

	private PricingSystemId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	public boolean isNone()
	{
		return repoId == NONE.repoId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final PricingSystemId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(final PricingSystemId o1, final PricingSystemId o2)
	{
		return Objects.equals(o1, o2);
	}
}
