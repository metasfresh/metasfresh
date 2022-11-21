/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class EDIDesadvId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	@NonNull
	public static EDIDesadvId ofRepoId(final int repoId)
	{
		return new EDIDesadvId(repoId);
	}

	@Nullable
	public static EDIDesadvId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new EDIDesadvId(repoId) : null;
	}

	public static int toRepoId(@Nullable final EDIDesadvId ediDesadvId)
	{
		return ediDesadvId != null ? ediDesadvId.getRepoId() : -1;
	}

	private EDIDesadvId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "EDI_Desadv_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}
}
