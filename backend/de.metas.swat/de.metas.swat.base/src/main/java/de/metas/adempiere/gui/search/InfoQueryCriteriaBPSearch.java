package de.metas.adempiere.gui.search;

/*
 * #%L
 * de.metas.swat.base
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


import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.InfoQueryCriteriaBPSearchAbstract;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.swing.CTextField;

public class InfoQueryCriteriaBPSearch  extends InfoQueryCriteriaBPSearchAbstract
{
	private CTextField fieldSearch;

	@Override
	public void init(IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText)
	{
		fieldSearch = new CTextField(10);
	}

	@Override
	public Object getParameterComponent(int index)
	{
		if (index == 0)
			return fieldSearch;
		return null;
	}

	@Override
	public Object getParameterValue(int index, boolean returnValueTo)
	{
		if (index == 0)
			return fieldSearch.getText();
		return null;
	}

	@Override
	public String getText()
	{
		return fieldSearch.getText();
	}
}
