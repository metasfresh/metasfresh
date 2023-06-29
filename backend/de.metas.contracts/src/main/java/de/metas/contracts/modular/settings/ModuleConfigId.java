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
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@RepoIdAwares.SkipTest
public class ModuleConfigId implements RepoIdAware
{
	int repoId;
	ModularContractSettingsId modularContractSettingsId;

	@JsonCreator
	public static ModuleConfigId ofRepoId(@NonNull final ModularContractSettingsId modularContractSettingsId, final int repoId)
	{
		return new ModuleConfigId(modularContractSettingsId, repoId);
	}

	public static ModuleConfigId ofRepoId(final int modularContractSettingsId, final int repoId)
	{
		return new ModuleConfigId(ModularContractSettingsId.ofRepoId(modularContractSettingsId), repoId);
	}

	@Nullable
	public static ModuleConfigId ofRepoIdOrNull(final Integer modularContractSettingsId, final Integer repoId)
	{
		return modularContractSettingsId != null && modularContractSettingsId > 0
				&& repoId != null && repoId > 0
				? new ModuleConfigId(ModularContractSettingsId.ofRepoId(modularContractSettingsId), repoId) : null;
	}

	private ModuleConfigId(@NonNull final ModularContractSettingsId modularContractSettingsId, final int repoId)
	{
		this.modularContractSettingsId = modularContractSettingsId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "moduleConfigId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
