package org.adempiere.ad.dao.impl;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.IQuery;

import lombok.NonNull;

public class SqlQueryUnion<T>
{
	private final IQuery<T> query;
	private final boolean distinct;

	protected SqlQueryUnion(@NonNull final IQuery<T> query, final boolean distinct)
	{
		this.query = query;
		this.distinct = distinct;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public IQuery<T> getQuery()
	{
		return query;
	}

	public boolean isDistinct()
	{
		return distinct;
	}
}
