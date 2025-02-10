/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.ad.table;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class ChangeLogEntryRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ImmutableList<ChangeLogEntry> getLogEntriesFor(@NonNull final ChangeLogEntryQuery changeLogEntryQuery)
	{
		if (changeLogEntryQuery.isEmpty())
		{
			throw new AdempiereException("Retrieving all ChangeLogEntries is not allowed!")
					.appendParametersToMessage()
					.setParameter("changeLogEntryQuery", changeLogEntryQuery);
		}

		final IQueryBuilder<I_AD_ChangeLog> queryBuilder = queryBL.createQueryBuilder(I_AD_ChangeLog.class);

		if (changeLogEntryQuery.getAdTableId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_ChangeLog.COLUMNNAME_AD_Table_ID, changeLogEntryQuery.getAdTableId());
		}

		if (changeLogEntryQuery.getAdColumnId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_ChangeLog.COLUMNNAME_AD_Column_ID, changeLogEntryQuery.getAdColumnId());
		}

		if (changeLogEntryQuery.getCreatedFrom() != null && changeLogEntryQuery.getCreatedTo() != null)
		{
			queryBuilder.addBetweenFilter(I_AD_ChangeLog.COLUMNNAME_Created, changeLogEntryQuery.getCreatedFrom(), changeLogEntryQuery.getCreatedTo());
		}

		if (changeLogEntryQuery.getRecordId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_ChangeLog.COLUMNNAME_Record_ID, changeLogEntryQuery.getRecordId());
		}

		if (!Check.isEmpty(changeLogEntryQuery.getAdColumnIds()))
		{
			queryBuilder.addInArrayFilter(I_AD_ChangeLog.COLUMNNAME_AD_Column_ID, changeLogEntryQuery.getAdColumnIds());
		}

		return queryBuilder
				.create()
				.list()
				.stream()
				.map(this::toRecordChangeLogEntry)
				.collect(ImmutableList.toImmutableList());
	}

	private ChangeLogEntry toRecordChangeLogEntry(@NonNull final I_AD_ChangeLog changeLog)
	{
		final boolean oldValueIsNull = changeLog.getOldValue() == null || changeLog.getOldValue().equals("NULL");
		final boolean newValueIsNull = changeLog.getNewValue() == null || changeLog.getNewValue().equals("NULL");

		return ChangeLogEntry.builder()
				.recordId(changeLog.getRecord_ID())
				.changeLogId(changeLog.getAD_ChangeLog_ID())
				.oldValue(oldValueIsNull ? null : changeLog.getOldValue())
				.newValue(newValueIsNull ? null : changeLog.getNewValue())
				.adColumnId(AdColumnId.ofRepoId(changeLog.getAD_Column_ID()))
				.adTableId(AdTableId.ofRepoId(changeLog.getAD_Table_ID()))
				.changeTimestamp(TimeUtil.asInstant(changeLog.getCreated()))
				.build();
	}

	public int deleteDirectly(@NonNull final ChangeLogConfig changeLogConfig, @NonNull final QueryLimit queryLimit)
	{
		final IQueryBuilder<I_AD_ChangeLog> query = queryBL.createQueryBuilder(I_AD_ChangeLog.class)
				.addEqualsFilter(I_AD_ChangeLog.COLUMNNAME_AD_Table_ID, changeLogConfig.getTableId())
				.addCompareFilter(I_AD_ChangeLog.COLUMNNAME_Created, CompareQueryFilter.Operator.LESS_OR_EQUAL, LocalDate.now().minusDays(changeLogConfig.getKeepChangeLogsDays()))
				.orderBy(I_AD_ChangeLog.COLUMNNAME_Created)
				;

		final OrgId orgId = changeLogConfig.getOrgId();
		if(!OrgId.equals(OrgId.ANY, orgId))
		{
			query.addEqualsFilter(I_AD_ChangeLog.COLUMNNAME_AD_Org_ID, orgId);
		}

		return query.setLimit(queryLimit).create().deleteDirectlyInSelect();
	}
}
