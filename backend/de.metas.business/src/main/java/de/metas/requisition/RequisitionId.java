/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.requisition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static org.compiere.model.I_M_Requisition.COLUMNNAME_M_Requisition_ID;

public class RequisitionId implements RepoIdAware
{
	int repoId;
	@JsonCreator
	public static RequisitionId ofRepoId(final int repoId)
	{
		return new RequisitionId(repoId);
	}

	@Nullable
	public static RequisitionId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new RequisitionId(repoId) : null;
	}

	public static Optional<RequisitionId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final RequisitionId RequisitionId)
	{
		return RequisitionId != null ? RequisitionId.getRepoId() : -1;
	}

	private RequisitionId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, COLUMNNAME_M_Requisition_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final RequisitionId id1, @Nullable final RequisitionId id2)
	{
		return Objects.equals(id1, id2);
	}
}