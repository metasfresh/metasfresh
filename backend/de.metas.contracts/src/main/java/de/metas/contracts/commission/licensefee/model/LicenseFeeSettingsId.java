/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.licensefee.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class LicenseFeeSettingsId implements RepoIdAware
{
	@NonNull
	@JsonCreator
	public static LicenseFeeSettingsId ofRepoId(final int repoId)
	{
		return new LicenseFeeSettingsId(repoId);
	}

	@Nullable
	public static LicenseFeeSettingsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new LicenseFeeSettingsId(repoId) : null;
	}

	public static int toRepoId(@Nullable final LicenseFeeSettingsId repoId)
	{
		return repoId != null ? repoId.getRepoId() : -1;
	}

	int repoId;

	private LicenseFeeSettingsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_LicenseFeeSettings.COLUMNNAME_C_LicenseFeeSettings_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
