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

import de.metas.bpartner.BPGroupId;
import de.metas.util.lang.Percent;
import io.micrometer.core.lang.NonNull;
import io.micrometer.core.lang.Nullable;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
public class LicenseFeeSettingsLine
{
	@Nullable
	LicenseFeeSettingsLineId licenseFeeSettingsLineId;

	@NonNull
	LicenseFeeSettingsId licenseFeeSettingsId;

	int seqNo;

	@NonNull
	Percent percentOfBasedPoints;

	@Nullable
	BPGroupId bpGroupIdMatch;

	boolean active;

	@Builder
	public LicenseFeeSettingsLine(
			@Nullable final LicenseFeeSettingsLineId licenseFeeSettingsLineId,
			@NonNull final LicenseFeeSettingsId licenseFeeSettingsId,
			final int seqNo,
			@NonNull final Percent percentOfBasedPoints,
			@Nullable final BPGroupId bpGroupIdMatch,
			final boolean active)
	{
		this.licenseFeeSettingsLineId = licenseFeeSettingsLineId;
		this.licenseFeeSettingsId = licenseFeeSettingsId;
		this.seqNo = seqNo;
		this.percentOfBasedPoints = percentOfBasedPoints;
		this.bpGroupIdMatch = bpGroupIdMatch;
		this.active = active;
	}

	public boolean appliesToBPGroup(@NonNull final BPGroupId bpGroupId)
	{
		return this.bpGroupIdMatch == null || this.bpGroupIdMatch.equals(bpGroupId);
	}

	@NonNull
	public LicenseFeeSettingsLineId getIdNotNull()
	{
		if (this.licenseFeeSettingsLineId == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted LicenseFeeSettingsLine objects!")
					.appendParametersToMessage()
					.setParameter("LicenseFeeSettingsLine", this);
		}

		return licenseFeeSettingsLineId;
	}
}
