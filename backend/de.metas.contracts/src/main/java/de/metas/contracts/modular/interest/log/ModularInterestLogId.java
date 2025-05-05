/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest.log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.contracts.model.I_ModCntr_Interest.COLUMNNAME_ModCntr_Interest_ID;

@Value
public class ModularInterestLogId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ModularInterestLogId ofRepoId(final int repoId)
	{
		return new ModularInterestLogId(repoId);
	}

	@Nullable
	public static ModularInterestLogId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ModularInterestLogId(repoId) : null;
	}

	private ModularInterestLogId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, COLUMNNAME_ModCntr_Interest_ID);
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
