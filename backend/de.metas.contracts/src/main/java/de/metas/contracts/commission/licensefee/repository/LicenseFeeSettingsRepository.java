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

package de.metas.contracts.commission.licensefee.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettings;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsLine;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsLineId;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettingsLine;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

@Repository
public class LicenseFeeSettingsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public LicenseFeeSettings getById(@NonNull final LicenseFeeSettingsId licenseFeeSettingsId)
	{
		final I_C_LicenseFeeSettings licenseFeeSettings = InterfaceWrapperHelper.load(licenseFeeSettingsId, I_C_LicenseFeeSettings.class);
		return toLicenseFeeSettings(licenseFeeSettings);
	}

	@NonNull
	private LicenseFeeSettings toLicenseFeeSettings(@NonNull final I_C_LicenseFeeSettings record)
	{
		final LicenseFeeSettingsId licenseFeeSettingsId = LicenseFeeSettingsId.ofRepoId(record.getC_LicenseFeeSettings_ID());

		final ImmutableList<LicenseFeeSettingsLine> licenseFeeSettingsLines = queryBL.createQueryBuilder(I_C_LicenseFeeSettingsLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_LicenseFeeSettingsLine.COLUMNNAME_C_LicenseFeeSettings_ID, licenseFeeSettingsId)
				.create()
				.stream()
				.map(this::toLicenseFeeSettingsLine)
				.collect(ImmutableList.toImmutableList());

		return LicenseFeeSettings.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.licenseFeeSettingsId(licenseFeeSettingsId)
				.commissionProductId(ProductId.ofRepoId(record.getCommission_Product_ID()))
				.pointsPrecision(record.getPointsPrecision())
				.lines(licenseFeeSettingsLines)
				.build();
	}

	@NonNull
	private LicenseFeeSettingsLine toLicenseFeeSettingsLine(@NonNull final I_C_LicenseFeeSettingsLine record)
	{
		final LicenseFeeSettingsId licenseFeeSettingsId = LicenseFeeSettingsId.ofRepoId(record.getC_LicenseFeeSettings_ID());

		return LicenseFeeSettingsLine.builder()
				.licenseFeeSettingsLineId(LicenseFeeSettingsLineId.ofRepoId(licenseFeeSettingsId, record.getC_LicenseFeeSettingsLine_ID()))
				.licenseFeeSettingsId(licenseFeeSettingsId)
				.seqNo(record.getSeqNo())
				.percentOfBasedPoints(Percent.of(record.getPercentOfBasePoints()))
				.bpGroupIdMatch(BPGroupId.ofRepoIdOrNull(record.getC_BP_Group_Match_ID()))
				.active(record.isActive())
				.build();
	}
}
