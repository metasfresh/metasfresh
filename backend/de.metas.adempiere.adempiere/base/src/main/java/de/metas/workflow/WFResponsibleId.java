/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.workflow;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class WFResponsibleId implements RepoIdAware
{
	public static final WFResponsibleId Invoker = new WFResponsibleId(101);

	@JsonCreator
	public static WFResponsibleId ofRepoId(final int repoId)
	{
		if (repoId == Invoker.repoId)
		{
			return Invoker;
		}
		return new WFResponsibleId(repoId);
	}

	public static WFResponsibleId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static Optional<WFResponsibleId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	int repoId;

	private WFResponsibleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_WF_Responsible_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final WFResponsibleId id) {return id != null ? id.getRepoId() : -1;}

	public static boolean equals(@Nullable final WFResponsibleId id1, @Nullable final WFResponsibleId id2) {return Objects.equals(id1, id2);}
}
