package org.compiere.apps.search;

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

import org.compiere.model.I_AD_InfoColumn;

/**
 * @author cg
 * 
 */
public interface IInfoQueryCriteria
{
	/**
	 * Constant used to inform where clause builder to clear the where clauses produced by previous criterias
	 */
	String WHERECLAUSE_CLEAR_PREVIOUS = new String("/* clear previous */");

	/**
	 * Constant used to inform where clause builder to stop asking other criterias for where clauses
	 */
	String WHERECLAUSE_STOP = new String("/* stop */");

	void init(IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText);

	I_AD_InfoColumn getAD_InfoColumn();
	
	int getParameterCount();

	String getLabel(int index);

	Object getParameterComponent(int index);

	Object getParameterToComponent(int index);

	Object getParameterValue(int index, boolean returnValueTo);

	String[] getWhereClauses(List<Object> params);

	String getText();
}
