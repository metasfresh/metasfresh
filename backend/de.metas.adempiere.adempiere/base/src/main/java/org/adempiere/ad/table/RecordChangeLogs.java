package org.adempiere.ad.table;

import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class RecordChangeLogs
{
	public static <T> RecordChangeLog of(
			@NonNull final T model,
			@NonNull final Class<T> modelClass,
			@NonNull final List<RecordChangeLogEntry> entries)
	{
		final RecordChangeLog.RecordChangeLogBuilder builder = RecordChangeLog.builder().entries(entries);

		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_Created);
		builder.createdByUserId(UserId.ofRepoId(createdBy));

		final Timestamp created = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_Created);
		builder.createdTimestamp(TimeUtil.asInstant(created));

		final Integer updatedBy = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_UpdatedBy);
		builder.lastChangedByUserId(UserId.ofRepoId(updatedBy));

		final Timestamp updated = InterfaceWrapperHelper.getValueOrNull(model, InterfaceWrapperHelper.COLUMNNAME_Updated);
		builder.createdTimestamp(TimeUtil.asInstant(updated));

		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		builder.tableName(tableName);

		final int id = InterfaceWrapperHelper.getId(model);
		final ComposedRecordId recordId = ComposedRecordId.singleKey(InterfaceWrapperHelper.getKeyColumnName(modelClass), id);
		builder.recordId(recordId);

		return builder.build();
	}
}
