package de.metas.invoice;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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
public class InvoiceScheduleId implements RepoIdAware
{
	int repoId;

	@NonNull
	public static InvoiceScheduleId ofRepoId(final int repoId)
	{
		return new InvoiceScheduleId(repoId);
	}

	@Nullable
	public static InvoiceScheduleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InvoiceScheduleId(repoId) : null;
	}

	private InvoiceScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}
}
