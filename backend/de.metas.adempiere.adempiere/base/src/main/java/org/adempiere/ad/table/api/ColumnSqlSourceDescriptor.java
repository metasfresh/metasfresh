package org.adempiere.ad.table.api;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
public class ColumnSqlSourceDescriptor
{
	@NonNull String targetTableName;
	@NonNull String sourceTableName;

	public enum FetchTargetRecordsMethod
	{LINK_COLUMN, SQL}

	@NonNull FetchTargetRecordsMethod fetchTargetRecordsMethod;

	@Nullable String sqlToGetTargetRecordIdBySourceRecordId;
	@Nullable String sourceLinkColumnName;

	@Builder
	private ColumnSqlSourceDescriptor(
			@NonNull final String targetTableName,
			@NonNull final String sourceTableName,
			@NonNull final FetchTargetRecordsMethod fetchTargetRecordsMethod,
			@Nullable final String sqlToGetTargetRecordIdBySourceRecordId,
			@Nullable final String sourceLinkColumnName)
	{
		this.targetTableName = targetTableName;
		this.sourceTableName = sourceTableName;
		this.fetchTargetRecordsMethod = fetchTargetRecordsMethod;
		if (fetchTargetRecordsMethod == FetchTargetRecordsMethod.LINK_COLUMN)
		{
			this.sourceLinkColumnName = Check.assumeNotEmpty(sourceLinkColumnName, "sourceLinkColumnName is not empty");
			this.sqlToGetTargetRecordIdBySourceRecordId = null;
		}
		else if (fetchTargetRecordsMethod == FetchTargetRecordsMethod.SQL)
		{
			this.sourceLinkColumnName = null;
			this.sqlToGetTargetRecordIdBySourceRecordId = Check.assumeNotEmpty(sqlToGetTargetRecordIdBySourceRecordId, "sqlToGetTargetRecordIdBySourceRecordId is not empty");
		}
		else
		{
			throw new AdempiereException("Unknown fetch method: " + fetchTargetRecordsMethod);
		}
	}

	@NonNull
	public String getSourceLinkColumnNameNotNull() {return Check.assumeNotNull(sourceLinkColumnName, "sourceLinkColumnName is not empty");}

}
