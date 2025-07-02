package de.metas.impexp.processing;

import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.process.PInstanceId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;

import javax.annotation.Nullable;

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

@Builder
@ToString
public final class ImportRecordsSelection
{
	@NonNull @Getter private final String importTableName;
	@NonNull private final String importKeyColumnName;

	@NonNull private final ClientId clientId;
	@Nullable @Getter @With private final PInstanceId selectionId;
	@Getter @With private final boolean empty;

	/**
	 * @return `AND ...` where clause
	 */
	public String toSqlWhereClause() {return toSqlWhereClause0(importTableName, true);}

	/**
	 * @return `AND ...` where clause
	 */
	public String toSqlWhereClause(@Nullable final String importTableAlias)
	{
		return toSqlWhereClause0(importTableAlias, true);
	}

	private String toSqlWhereClause0(@Nullable final String importTableAlias, final boolean prefixWithAND)
	{
		final String importTableAliasWithDot = StringUtils.trimBlankToOptional(importTableAlias).map(alias -> alias + ".").orElse("");

		final StringBuilder whereClause = new StringBuilder();
		if (prefixWithAND)
		{
			whereClause.append(" AND ");
		}
		whereClause.append("(");

		if (empty)
		{
			return "1=2";
		}
		else
		{
			// AD_Client
			whereClause.append(importTableAliasWithDot).append(ImportTableDescriptor.COLUMNNAME_AD_Client_ID).append("=").append(clientId.getRepoId());

			// Selection_ID
			if (selectionId != null)
			{
				final String importKeyColumnNameFQ = importTableAliasWithDot + importKeyColumnName;
				whereClause.append(" AND ").append(DB.createT_Selection_SqlWhereClause(selectionId, importKeyColumnNameFQ));
			}
		}

		whereClause.append(")");

		return whereClause.toString();
	}

	public IQueryFilter<Object> toQueryFilter(@Nullable final String importTableAlias)
	{
		return empty
				? ConstantQueryFilter.of(false)
				: TypedSqlQueryFilter.of(toSqlWhereClause0(importTableAlias, false));
	}
}
