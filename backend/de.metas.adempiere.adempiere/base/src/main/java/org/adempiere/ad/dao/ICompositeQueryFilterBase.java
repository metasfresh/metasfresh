package org.adempiere.ad.dao;

import lombok.NonNull;

public interface ICompositeQueryFilterBase<T>
{
	String getModelTableName();

	void addFilter0(@NonNull final IQueryFilter<T> filter);

	/**
	 * Unboxes and adds the filters contained in the <code>compositeFilter</code>.
	 * If it could not be unboxed (e.g. because JOIN method does not match) the composite filter is added as is.
	 * Note that by "unboxing" we mean getting the filters included in the given {@code compositeFilter} and adding them to this instance directly, rather than adding the given {@code compositeFilter} itself.
	 */
	void addFiltersUnboxed0(ICompositeQueryFilter<T> compositeFilter);
}
