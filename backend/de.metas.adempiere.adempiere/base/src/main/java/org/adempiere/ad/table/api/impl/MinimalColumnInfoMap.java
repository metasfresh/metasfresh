package org.adempiere.ad.table.api.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;

public interface MinimalColumnInfoMap
{
	@NonNull MinimalColumnInfo getById(@NonNull AdColumnId adColumnId);

	@Nullable
	MinimalColumnInfo getByColumnNameOrNull(AdTableId adTableId, String columnName);

	default MinimalColumnInfo getByColumnName(final AdTableId adTableId, final String columnName)
	{
		final MinimalColumnInfo column = getByColumnNameOrNull(adTableId, columnName);
		if (column == null)
		{
			throw new AdempiereException("No column found for " + adTableId + " and columnName=" + columnName);
		}
		return column;
	}

	default boolean hasColumnName(final AdTableId adTableId, final String columnName)
	{
		final MinimalColumnInfo column = getByColumnNameOrNull(adTableId, columnName);
		return column != null && column.isActive();
	}

	default String getColumnNameById(@NonNull final AdColumnId adColumnId)
	{
		return getById(adColumnId).getColumnName();
	}

	ImmutableList<MinimalColumnInfo> getByIds(Collection<AdColumnId> adColumnIds);
}
