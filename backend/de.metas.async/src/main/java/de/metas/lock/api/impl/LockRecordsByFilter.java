package de.metas.lock.api.impl;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;

@Value
public class LockRecordsByFilter
{
	@NonNull AdTableId tableId;
	@NonNull IQueryFilter<?> filters;

	public static <T> LockRecordsByFilter of(final @NonNull Class<T> modelClass, final @NonNull IQueryFilter<T> filters)
	{
		final AdTableId tableId = InterfaceWrapperHelper.getAdTableId(modelClass);
		return new LockRecordsByFilter(tableId, filters);
	}
}
