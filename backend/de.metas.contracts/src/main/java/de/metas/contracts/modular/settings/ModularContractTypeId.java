/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ModularContractTypeId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ModularContractTypeId ofRepoId(final int repoId)
	{
		return new ModularContractTypeId(repoId);
	}

	@Nullable
	public static ModularContractTypeId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ModularContractTypeId(repoId) : null;
	}

	private ModularContractTypeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "ModCntr_Type_ID");
	}

	public static int toRepoId(@Nullable final ModularContractTypeId modularContractTypeId)
	{
		return modularContractTypeId != null ? modularContractTypeId.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
