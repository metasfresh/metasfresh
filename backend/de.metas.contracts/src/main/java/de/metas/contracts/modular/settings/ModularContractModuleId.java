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

package de.metas.contracts.modular.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Objects;

@Value
public class ModularContractModuleId implements RepoIdAware
{

	int repoId;

	@JsonCreator
	public static ModularContractModuleId ofRepoId(final int repoId)
	{
		return new ModularContractModuleId(repoId);
	}

	@Nullable
	public static ModularContractModuleId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ModularContractModuleId(repoId) : null;
	}

	@NonNull
	public static Optional<ModularContractModuleId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	private ModularContractModuleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_ModCntr_Module.COLUMNNAME_ModCntr_Module_ID);
	}



	public static int toRepoId(@Nullable final ModularContractModuleId modularContractModuleId)
	{
		return modularContractModuleId != null ? modularContractModuleId.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final ModularContractModuleId o1, @Nullable final ModularContractModuleId o2)
	{
		return Objects.equals(o1, o2);
	}
}
