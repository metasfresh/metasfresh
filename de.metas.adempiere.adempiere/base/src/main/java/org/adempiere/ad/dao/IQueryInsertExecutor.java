package org.adempiere.ad.dao;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Mass INSERT executor
 *
 * @author tsa
 *
 * @param <ToModelType> target model (i.e. in which table you want to insert)
 * @param <FromModelType> source model (i.e. from which table are you selecting)
 */
public interface IQueryInsertExecutor<ToModelType, FromModelType>
{
	/**
	 * Execute mass INSERT
	 *
	 * @return how many rows were inserted
	 */
	QueryInsertExecutorResult execute();

	/**
	 * Map all common columns of "To Model" and "From Model".
	 */
	IQueryInsertExecutor<ToModelType, FromModelType> mapCommonColumns();

	/**
	 * Map a column name in target model to a column name of "From Model".
	 *
	 * @param toColumnName
	 * @param fromColumnName
	 * @return
	 */
	IQueryInsertExecutor<ToModelType, FromModelType> mapColumn(String toColumnName, String fromColumnName);

	/**
	 * Map a column name in target model and set it to a constant.
	 *
	 * @param toColumnName
	 * @param constantValue
	 */
	IQueryInsertExecutor<ToModelType, FromModelType> mapColumnToConstant(String toColumnName, Object constantValue);

	/**
	 * Map a column name in target model to a given SQL (which is based on from model).
	 *
	 * NOTE: calling this method is discouraged. Used mainly to port old code.
	 *
	 * @param toColumnName
	 * @param fromSql
	 */
	IQueryInsertExecutor<ToModelType, FromModelType> mapColumnToSql(String toColumnName, String fromSql);

	/**
	 * Map the primary key column (so the ID will be generated)
	 */
	IQueryInsertExecutor<ToModelType, FromModelType> mapPrimaryKey();

	/**
	 * Advice this executor to also create a selection of the inserted rows.
	 */
	IQueryInsertExecutor<ToModelType, FromModelType> creatingSelectionOfInsertedRows();

	@Immutable
	public static final class QueryInsertExecutorResult
	{
		public static QueryInsertExecutorResult of(final int rowsInserted, final int insertSelectionId)
		{
			return new QueryInsertExecutorResult(rowsInserted, insertSelectionId);
		}

		private final int rowsInserted;
		private final int insertSelectionId;

		private QueryInsertExecutorResult(final int rowsInserted, final int insertSelectionId)
		{
			this.rowsInserted = rowsInserted;
			this.insertSelectionId = insertSelectionId;
		}
		
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("rowsInserted", rowsInserted)
					.add("insertSelectionId", insertSelectionId > 0 ? insertSelectionId : null)
					.toString();
		}
		
		public int getRowsInserted()
		{
			return rowsInserted;
		}
		
		public int getInsertSelectionId()
		{
			if(insertSelectionId <= 0)
			{
				throw new IllegalStateException("No insert selection defined");
			}
			return insertSelectionId;
		}
	}
}
