package org.adempiere.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.VisibleForTesting;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * AD_Client_ID
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class ClientId implements RepoIdAware
{
	@JsonCreator
	public static ClientId ofRepoId(final int repoId)
	{
		final ClientId clientId = ofRepoIdOrNull(repoId);
		if (clientId == null)
		{
			throw new AdempiereException("Invalid AD_Client_ID: " + repoId);
		}
		return clientId;
	}

	public static ClientId ofRepoIdOrSystem(final int repoId)
	{
		final ClientId clientId = ofRepoIdOrNull(repoId);
		return clientId != null ? clientId : SYSTEM;
	}

	public static Optional<ClientId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	@Nullable
	public static ClientId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == SYSTEM.repoId)
		{
			return SYSTEM;
		}
		else if (repoId == TRASH.repoId)
		{
			return TRASH;
		}
		else if (repoId == METASFRESH.repoId)
		{
			return METASFRESH;
		}
		else if (repoId <= 0)
		{
			return null;
		}
		else
		{
			return new ClientId(repoId);
		}
	}

	public static final ClientId SYSTEM = new ClientId(0);
	@VisibleForTesting
	static final ClientId TRASH = new ClientId(99);

	public static final ClientId METASFRESH = new ClientId(1000000);

	int repoId;

	private ClientId(final int repoId)
	{
		// NOTE: validation happens in ofRepoIdOrNull method
		this.repoId = repoId;
	}

	public boolean isSystem()
	{
		return repoId == SYSTEM.repoId;
	}

	public boolean isTrash()
	{
		return repoId == TRASH.repoId;
	}

	public boolean isRegular()
	{
		return !isSystem() && !isTrash();
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ClientId clientId)
	{
		return clientId != null ? clientId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final ClientId id1, @Nullable final ClientId id2)
	{
		return Objects.equals(id1, id2);
	}
}
