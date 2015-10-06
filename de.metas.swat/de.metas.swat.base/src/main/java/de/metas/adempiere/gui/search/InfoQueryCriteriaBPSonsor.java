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


import java.awt.Dimension;

import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.InfoQueryCriteriaBPSonsorAbstract;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.swing.CTextField;

public class InfoQueryCriteriaBPSonsor extends InfoQueryCriteriaBPSonsorAbstract
{
	private CTextField fieldSponsorNo;
	private SponsorNoAutoCompleter fieldSponsorNoAutocompleter;

	@Override
	public void init(IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText)
	{
		fieldSponsorNo = new CTextField();
		fieldSponsorNo.setPreferredSize(new Dimension(200, (int)fieldSponsorNo.getPreferredSize().getHeight()));

		fieldSponsorNoAutocompleter = new SponsorNoAutoCompleter(fieldSponsorNo);
		fieldSponsorNoAutocompleter.setUserObject(null);
	}

	@Override
	public Object getParameterComponent(int index)
	{
		if (index == 0)
			return fieldSponsorNo;
		else
			return null;
	}

	protected SponsorNoObject getSponsorNoObject()
	{
		return (SponsorNoObject)fieldSponsorNoAutocompleter.getUserOject();
	}

	@Override
	public String getText()
	{
		return fieldSponsorNo.getText();
	}
}
