package org.adempiere.ad.dao.impl;

import org.compiere.model.IQuery;

import lombok.NonNull;
import lombok.Value;

@Value
public final class SqlQueryUnion<T>
{
	private final boolean distinct;
	private final IQuery<T> query;

	protected SqlQueryUnion(
			@NonNull final IQuery<T> query,
			final boolean distinct)
	{
		this.query = query;
		this.distinct = distinct;
	}
}
