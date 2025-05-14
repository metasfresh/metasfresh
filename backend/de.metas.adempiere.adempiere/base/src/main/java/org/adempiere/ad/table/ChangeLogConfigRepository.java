package org.adempiere.ad.table;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_ChangeLog_Config;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
@RequiredArgsConstructor
public class ChangeLogConfigRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static ChangeLogConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ChangeLogConfigRepository();
	}

	@NonNull
	public ImmutableList<ChangeLogConfig> retrieveChangeLogConfigList()
	{
		return queryBL.createQueryBuilder(I_AD_ChangeLog_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ChangeLogConfigRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static ChangeLogConfig fromRecord(@NonNull final I_AD_ChangeLog_Config changeLogConfig)
	{
		return ChangeLogConfig.builder()
				.tableId(AdTableId.ofRepoId(changeLogConfig.getAD_Table_ID()))
				.keepChangeLogsDays(changeLogConfig.getKeepChangeLogsDays())
				.orgId(OrgId.ofRepoId(changeLogConfig.getAD_Org_ID()))
				.build();
	}
}
