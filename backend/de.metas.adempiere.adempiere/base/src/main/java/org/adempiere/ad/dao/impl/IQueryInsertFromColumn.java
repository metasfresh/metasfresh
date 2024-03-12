package org.adempiere.ad.dao.impl;

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

import java.util.List;

import org.adempiere.ad.dao.IQueryInsertExecutor;

import javax.annotation.Nullable;

/**
 * {@link IQueryInsertExecutor}'s source column.
 * 
 * @author tsa
 *
 */
interface IQueryInsertFromColumn
{
	/**
	 * @param sqlParams output SQL parameters list or NULL if the parameters shall be embedded in the generated SQL code
	 * @return SQL code to be used when the INSERT sql is built
	 */
	String getSql(@Nullable List<Object> sqlParams);

	/**
	 * Database decoupled/nonSQL implementation
	 * 
	 * @return true if <code>toModel</code> was updated
	 */
	boolean update(Object toModel, String toColumnName, Object fromModel);

}
