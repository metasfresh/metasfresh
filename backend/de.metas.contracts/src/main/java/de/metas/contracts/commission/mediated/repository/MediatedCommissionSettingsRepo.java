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

package de.metas.contracts.commission.mediated.repository;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettings;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsId;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsLine;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsLineId;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

@Repository
public class MediatedCommissionSettingsRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public MediatedCommissionSettings getById(@NonNull final MediatedCommissionSettingsId commissionSettingsId)
	{
		final I_C_MediatedCommissionSettings mediatedCommissionSettings = InterfaceWrapperHelper.load(commissionSettingsId, I_C_MediatedCommissionSettings.class);
		return toMediatedCommissionSettings(mediatedCommissionSettings);
	}

	@NonNull
	private MediatedCommissionSettings toMediatedCommissionSettings(@NonNull final I_C_MediatedCommissionSettings record)
	{
		final ImmutableList<MediatedCommissionSettingsLine> lines = queryBL.createQueryBuilder(I_C_MediatedCommissionSettingsLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_MediatedCommissionSettings.COLUMNNAME_C_MediatedCommissionSettings_ID, record.getC_MediatedCommissionSettings_ID())
				.create()
				.stream()
				.map(this::toMediatedCommissionLine)
				.collect(ImmutableList.toImmutableList());

		return MediatedCommissionSettings.builder()
				.commissionSettingsId(MediatedCommissionSettingsId.ofRepoId(record.getC_MediatedCommissionSettings_ID()))
				.commissionProductId(ProductId.ofRepoId(record.getCommission_Product_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.pointsPrecision(record.getPointsPrecision())
				.lines(lines)
				.build();
	}

	@NonNull
	private MediatedCommissionSettingsLine toMediatedCommissionLine(@NonNull final I_C_MediatedCommissionSettingsLine record)
	{
		final MediatedCommissionSettingsId settingsId = MediatedCommissionSettingsId.ofRepoId(record.getC_MediatedCommissionSettings_ID());

		return MediatedCommissionSettingsLine.builder()
				.mediatedCommissionSettingsLineId(MediatedCommissionSettingsLineId.ofRepoId(record.getC_MediatedCommissionSettingsLine_ID(), settingsId))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.percentOfBasedPoints(Percent.of(record.getPercentOfBasePoints()))
				.seqNo(record.getSeqNo())
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(record.getM_Product_Category_ID()))
				.build();
	}
}
