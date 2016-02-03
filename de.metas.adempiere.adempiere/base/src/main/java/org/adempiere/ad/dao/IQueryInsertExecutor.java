package org.adempiere.ad.dao;

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
	int execute();

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
}
