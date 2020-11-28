package org.compiere.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import lombok.NonNull;

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

final class POAccountingInfoRepository
{
	public static final transient POAccountingInfoRepository instance = new POAccountingInfoRepository();

	private static final CCache<String, Optional<POAccountingInfo>> accountInfosByAcctTableName = CCache.<String, Optional<POAccountingInfo>> builder()
			.tableName(I_AD_Column.Table_Name)
			.build();

	public final Optional<POAccountingInfo> getPOAccountingInfo(@NonNull final String acctTable)
	{
		return accountInfosByAcctTableName.getOrLoad(acctTable, this::retrievePOAccountingInfo);
	}

	public final Optional<POAccountingInfo> retrievePOAccountingInfo(@NonNull final String acctTableName)
	{
		final String sql = "SELECT c.ColumnName "
				+ " FROM AD_Column c INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
				+ " WHERE t.TableName=? AND c.IsActive='Y' AND c.AD_Reference_ID=?"
				+ " ORDER BY c.ColumnName";
		final List<Object> sqlParams = Arrays.asList(acctTableName, DisplayType.Account);

		final List<String> acctColumnNames = DB.retrieveRowsOutOfTrx(sql, sqlParams, rs -> rs.getString(1));
		if (acctColumnNames.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(POAccountingInfo.builder()
				.acctTableName(acctTableName)
				.acctColumnNames(ImmutableSet.copyOf(acctColumnNames))
				.build());
	}

}
