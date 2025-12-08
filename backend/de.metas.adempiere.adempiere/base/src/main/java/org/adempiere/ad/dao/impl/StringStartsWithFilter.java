package org.adempiere.ad.dao.impl;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class StringStartsWithFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	@NonNull private final ModelColumnNameValue<T> operand;
	@NonNull private final String prefix;

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	public StringStartsWithFilter(
			@NonNull final String columnName,
			@NonNull final String prefix)
	{
		if (prefix.isEmpty())
		{
			throw new AdempiereException("Prefix must be not empty");
		}

		this.operand = ModelColumnNameValue.forColumnName(columnName);
		this.prefix = prefix;
	}

	@Override
	public boolean accept(final T model)
	{
		final Object value = operand.getValue(model);
		return value != null && value.toString().startsWith(prefix);
	}

	@Override
	public final String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public final List<Object> getSqlParams(final Properties ctx_NOTUSED)
	{
		return getSqlParams();
	}

	public final List<Object> getSqlParams()
	{
		buildSql();
		return sqlParams;
	}

	private void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final String sqlLikeValue = prefix.replace("%", "\\%") + "%";

		sqlWhereClause = operand.getColumnName() + " LIKE ? ESCAPE '\\'";
		sqlParams = Collections.singletonList(sqlLikeValue);
		sqlBuilt = true;
	}
}
