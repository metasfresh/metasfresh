package org.compiere.report.core;

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


import java.util.Map;

public interface IRModelMetadata
{

	int getGroupsCount();

	/**
	 * 
	 * @return "column index" to "function name"s map
	 */
	Map<Integer, String> getFunctions();

	/**
	 * Find index for ColumnName
	 * 
	 * @param columnName
	 * @return index or -1 if not found
	 */
	int getRColumnIndex(String columnName);

}
