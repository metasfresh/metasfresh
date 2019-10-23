package org.adempiere.ad.persistence.modelgen;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

import de.metas.security.TableAccessLevel;
import de.metas.util.Check;

/**
 * AD_Table/AD_Column related meta data.
 * 
 * @author tsa
 */
public class TableInfo
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int adTableId;
	private final String tableName;
	private final TableAccessLevel accessLevel;
	private final List<ColumnInfo> columnInfos;

	private TableInfo(final Builder builder)
	{
		super();

		this.adTableId = builder.adTableId;
		Check.assume(this.adTableId > 0, "AD_Table_ID > 0");

		this.tableName = builder.tableName;
		Check.assumeNotEmpty(tableName, "tableName not empty");

		Check.assumeNotNull(builder.accessLevel, "accessLevel not empty");
		this.accessLevel = builder.accessLevel;

		this.columnInfos = ImmutableList.copyOf(builder.columnInfos);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public int getAD_Table_ID()
	{
		return adTableId;
	}

	public String getTableName()
	{
		return tableName;
	}

	public TableAccessLevel getAccessLevel()
	{
		return accessLevel;
	}

	public List<ColumnInfo> getColumnInfos()
	{
		return columnInfos;
	}

	public static class Builder
	{
		private int adTableId = -1;
		private String tableName;
		private TableAccessLevel accessLevel;
		private Collection<ColumnInfo> columnInfos;

		private Builder()
		{
			super();
		}

		public TableInfo build()
		{
			return new TableInfo(this);
		}

		public Builder setAD_Table_ID(int adTableId)
		{
			this.adTableId = adTableId;
			return this;
		}

		public Builder setTableName(String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		public Builder setAccessLevel(TableAccessLevel accessLevel)
		{
			this.accessLevel = accessLevel;
			return this;
		}

		public Builder setColumnInfos(Collection<ColumnInfo> columnInfos)
		{
			this.columnInfos = columnInfos;
			return this;
		}
	}
}
