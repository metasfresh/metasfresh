package de.metas.impexp.processing;

import javax.annotation.Nullable;

import org.adempiere.service.ClientId;
import org.compiere.util.DB;

import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public final class ImportRecordsSelection
{
	private final String importTableName;
	private final String importKeyColumnName;
	private final ClientId clientId;
	private final PInstanceId selectionId;

	@Builder
	private ImportRecordsSelection(
			@NonNull final String importTableName,
			@NonNull final String importKeyColumnName,
			@NonNull final ClientId clientId,
			@Nullable final PInstanceId selectionId)
	{
		this.importTableName = importTableName;
		this.importKeyColumnName = importKeyColumnName;
		this.clientId = clientId;
		this.selectionId = selectionId;
	}

	/**
	 * @return `AND ...` where clause
	 */
	public String toSqlWhereClause()
	{
		return toSqlWhereClause(importTableName);
	}

	/**
	 * @return `AND ...` where clause
	 */
	public String toSqlWhereClause(final String importTableAlias)
	{
		final StringBuilder whereClause = new StringBuilder();

		// AD_Client
		whereClause.append(" AND ").append(importTableAlias).append(".AD_Client_ID=").append(clientId.getRepoId());

		// Selection_ID
		if (selectionId != null)
		{
			final String importKeyColumnNameFQ = importTableAlias + "." + importKeyColumnName;
			whereClause.append(" AND ").append(DB.createT_Selection_SqlWhereClause(selectionId, importKeyColumnNameFQ));
		}

		return whereClause.toString();
	}
}
