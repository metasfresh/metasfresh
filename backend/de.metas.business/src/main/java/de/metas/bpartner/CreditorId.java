package de.metas.bpartner;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Typed value object wrapping the integer creditor number from {@code C_BPartner.CreditorId}.
 * This is a plain number holder, not a table-row foreign key.
 */
@Value
public class CreditorId implements RepoIdAware
{
	@JsonCreator
	public static CreditorId ofRepoId(final int repoId)
	{
		return new CreditorId(repoId);
	}

	@Nullable
	public static CreditorId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CreditorId(repoId) : null;
	}

	public static int toRepoId(@Nullable final CreditorId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private CreditorId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "CreditorId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
