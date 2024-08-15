package org.adempiere.ad.table.api.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.ad.table.api.impl.ImmutableMinimalColumnInfoMap.AdTableIdAndColumnName;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MockedMinimalColumnInfoMap implements MinimalColumnInfoMap
{
	private final HashMap<AdColumnId, MinimalColumnInfo> byId = new HashMap<>();
	private final HashMap<AdTableIdAndColumnName, MinimalColumnInfo> byColumnName = new HashMap<>();

	private final AtomicInteger nextAD_Column_ID = new AtomicInteger(801);

	@Override
	public @NonNull MinimalColumnInfo getById(@NonNull final AdColumnId adColumnId)
	{
		Adempiere.assertUnitTestMode();

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
		Adempiere.assertUnitTestMode();

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
		Adempiere.assertUnitTestMode();

		final AdTableIdAndColumnName key = new AdTableIdAndColumnName(adTableId, columnName);
		MinimalColumnInfo column = byColumnName.get(key);
		if (column == null)
		{
			column = MinimalColumnInfo.builder()
					.columnName(columnName)
					.adColumnId(AdColumnId.ofRepoId(nextAD_Column_ID.getAndIncrement()))
					.adTableId(adTableId)
					.isActive(true)
					.isParent(false)
					.isGenericZoomOrigin(false)
					.displayType(0) // unknown
					.adReferenceValueId(null)
					.adValRuleId(null)
					.entityType("U")
					.build();

			byColumnName.put(key, column);
			byId.put(column.getAdColumnId(), column);
		}
		return column;
	}
}
