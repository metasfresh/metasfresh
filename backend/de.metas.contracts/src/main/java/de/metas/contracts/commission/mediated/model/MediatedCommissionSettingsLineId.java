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

package de.metas.contracts.commission.mediated.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class MediatedCommissionSettingsLineId implements RepoIdAware
{
	int repoId;
	MediatedCommissionSettingsId mediatedCommissionSettingsId;

	private MediatedCommissionSettingsLineId(final int repoId, @NonNull final MediatedCommissionSettingsId mediatedCommissionSettingsId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_MediatedCommissionSettingsLine.COLUMNNAME_C_MediatedCommissionSettingsLine_ID);
		this.mediatedCommissionSettingsId = mediatedCommissionSettingsId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	@NonNull
	@JsonCreator
	public static MediatedCommissionSettingsLineId ofRepoId(final int repoId, @NonNull final MediatedCommissionSettingsId mediatedCommissionSettingsId)
	{
		return new MediatedCommissionSettingsLineId(repoId, mediatedCommissionSettingsId);
	}

	@Nullable
	public static MediatedCommissionSettingsLineId ofRepoIdOrNull(final int repoId, @Nullable final MediatedCommissionSettingsId mediatedCommissionSettingsId)
	{
		return repoId > 0 && mediatedCommissionSettingsId != null ? ofRepoId(repoId, mediatedCommissionSettingsId) : null;
	}
}
