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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

@Value
public class LicenseFeeSettings
{
	@NonNull
	OrgId orgId;

	@Nullable
	LicenseFeeSettingsId licenseFeeSettingsId;

	@NonNull
	ProductId commissionProductId;

	@NonNull
	Integer pointsPrecision;

	@NonNull
	ImmutableList<LicenseFeeSettingsLine> lines;

	@Builder
	public LicenseFeeSettings(
			@Nullable final LicenseFeeSettingsId licenseFeeSettingsId,
			@NonNull final ProductId commissionProductId,
			@NonNull final Integer pointsPrecision,
			@NonNull final OrgId orgId,
			@NonNull final ImmutableList<LicenseFeeSettingsLine> lines)
	{
		this.licenseFeeSettingsId = licenseFeeSettingsId;
		this.commissionProductId = commissionProductId;
		this.pointsPrecision = pointsPrecision;
		this.orgId = orgId;
		this.lines = lines;
	}

	@NonNull
	public LicenseFeeSettingsId getIdNotNull()
	{
		if (this.licenseFeeSettingsId == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted LicenseFeeSettings objects!")
					.appendParametersToMessage()
					.setParameter("LicenseFeeSettings", this);
		}

		return licenseFeeSettingsId;
	}

	@NonNull
	public Optional<LicenseFeeSettingsLine> getLineForBPGroupId(@NonNull final BPGroupId bpGroupId)
	{
		return lines.stream()
				.sorted(Comparator.comparingInt(LicenseFeeSettingsLine::getSeqNo))
				.filter(line -> line.appliesToBPGroup(bpGroupId))
				.findFirst();
	}
}
