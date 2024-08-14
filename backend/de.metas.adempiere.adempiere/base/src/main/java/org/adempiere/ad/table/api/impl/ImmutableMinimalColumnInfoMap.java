package org.adempiere.ad.table.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

class ImmutableMinimalColumnInfoMap implements MinimalColumnInfoMap
{
	private final ImmutableMap<AdColumnId, MinimalColumnInfo> byId;
	private final ImmutableMap<AdTableIdAndColumnName, MinimalColumnInfo> byColumnName;

	ImmutableMinimalColumnInfoMap(final ImmutableList<MinimalColumnInfo> list)
	{
		this.byId = Maps.uniqueIndex(list, MinimalColumnInfo::getAdColumnId);
		this.byColumnName = Maps.uniqueIndex(list, column -> new AdTableIdAndColumnName(column.getAdTableId(), column.getColumnName()));
	}


	@Override
	@NonNull
	public MinimalColumnInfo getById(final @NonNull AdColumnId adColumnId)
	{
		final MinimalColumnInfo column = byId.get(adColumnId);
		if (column == null)
		{
			throw new AdempiereException("No column found for " + adColumnId);
		}
		return column;
	}

	@Override
	public ImmutableList<MinimalColumnInfo> getByIds(final Collection<AdColumnId> adColumnIds)
	{
		if (adColumnIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return adColumnIds.stream()
				.distinct()
				.map(byId::get)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}


	@Nullable
	@Override
	public MinimalColumnInfo getByColumnNameOrNull(final AdTableId adTableId, final String columnName)
	{
		final AdTableIdAndColumnName key = new AdTableIdAndColumnName(adTableId, columnName);
		return byColumnName.get(key);
	}

	//
	//
	//

	@Value
	static class AdTableIdAndColumnName
	{
		@NonNull AdTableId adTableId;
		@NonNull String columnNameUC;

		AdTableIdAndColumnName(@NonNull final AdTableId adTableId, @NonNull final String columnName)
		{
			this.adTableId = adTableId;
			this.columnNameUC = columnName.toUpperCase();
		}
	}
}
