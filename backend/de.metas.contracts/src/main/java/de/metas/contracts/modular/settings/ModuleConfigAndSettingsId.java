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
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@RepoIdAwares.SkipTest
public class ModuleConfigAndSettingsId implements RepoIdAware
{
	@NonNull ModularContractModuleId modularContractModuleId;
	@NonNull ModularContractSettingsId modularContractSettingsId;

	@JsonCreator
	public static ModuleConfigAndSettingsId ofRepoId(@NonNull final ModularContractSettingsId modularContractSettingsId, final int repoId)
	{
		return new ModuleConfigAndSettingsId(modularContractSettingsId, ModularContractModuleId.ofRepoId(repoId));
	}

	public static ModuleConfigAndSettingsId ofRepoId(final int modularContractSettingsId, final int repoId)
	{
		return new ModuleConfigAndSettingsId(ModularContractSettingsId.ofRepoId(modularContractSettingsId), ModularContractModuleId.ofRepoId(repoId));
	}

	@Nullable
	public static ModuleConfigAndSettingsId ofRepoIdOrNull(final Integer modularContractSettingsId, final Integer repoId)
	{
		return modularContractSettingsId != null && modularContractSettingsId > 0
				&& repoId != null && repoId > 0
				? new ModuleConfigAndSettingsId(ModularContractSettingsId.ofRepoId(modularContractSettingsId), ModularContractModuleId.ofRepoId(repoId)) : null;
	}

	private ModuleConfigAndSettingsId(@NonNull final ModularContractSettingsId modularContractSettingsId, @NonNull final ModularContractModuleId modularContractModuleId)
	{
		this.modularContractSettingsId = modularContractSettingsId;
		this.modularContractModuleId = modularContractModuleId;
	}

	@JsonValue
	public int getRepoId()
	{
		return modularContractModuleId.getRepoId();
	}
}
