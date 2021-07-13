package de.metas.shipping;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;

import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class ShipperId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ShipperId ofRepoId(final int repoId)
	{
		return new ShipperId(repoId);
	}

	@Nullable
	public static ShipperId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ShipperId(repoId) : null;
	}

	public static Optional<ShipperId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final ShipperId shipperId)
	{
		return shipperId != null ? shipperId.getRepoId() : -1;
	}

	private ShipperId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Shipper_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
