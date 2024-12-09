package de.metas.invoice;

<<<<<<< HEAD
import de.metas.util.Check;
import lombok.Value;

=======
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
public class InvoiceScheduleId
{
	int repoId;

=======
public class InvoiceScheduleId implements RepoIdAware
{
	int repoId;

	@NonNull
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public static InvoiceScheduleId ofRepoId(final int repoId)
	{
		return new InvoiceScheduleId(repoId);
	}

<<<<<<< HEAD
=======
	@Nullable
	public static InvoiceScheduleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new InvoiceScheduleId(repoId) : null;
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private InvoiceScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}
<<<<<<< HEAD
=======

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
