/**
 * 
 */
package org.compiere.apps.search;

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


import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.I_AD_InfoColumn;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import de.metas.adempiere.gui.search.SponsorNoObject;
import de.metas.i18n.Msg;

/**
 * @author cg
 *
 */
public abstract class InfoQueryCriteriaBPSonsorAbstract implements IInfoQueryCriteria
{
	private String bpartnerTableAlias = "C_BPartner";

	private IInfoSimple parent;
	private I_AD_InfoColumn infoColumn;


	@Override
	public void init(IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText)
	{
		this.parent = parent;
		this.infoColumn = infoColumn;
	}

	@Override
	public I_AD_InfoColumn getAD_InfoColumn()
	{
		return infoColumn;
	}

	@Override
	public String getLabel(int index)
	{
		return Msg.translate(Env.getCtx(), "SponsorNo");
	}

	@Override
	public int getParameterCount()
	{
		return 1;
	}


	@Override
	public Object getParameterToComponent(int index)
	{
		return null;
	}

	@Override
	public Object getParameterValue(int index, boolean returnValueTo)
	{
		if (index == 0)
			return getText();
		else
			return null;
	}

	@Override
	public String[] getWhereClauses(List<Object> params)
	{
		final SponsorNoObject sno = getSponsorNoObject();
		final String searchText = getText();

		if (sno == null && !Util.isEmpty(searchText, true))
			return new String[]{"1=2"};
		if (sno == null)
			return null;

		final Timestamp date = TimeUtil.trunc(Env.getContextAsDate(Env.getCtx(), "#Date"), TimeUtil.TRUNC_DAY);
		//
		final String whereClause = "EXISTS (SELECT 1 FROM C_Sponsor_Salesrep ssr"
				+ " JOIN C_Sponsor sp ON (ssr.C_Sponsor_ID = sp.C_Sponsor_ID)"
				+ " WHERE " + bpartnerTableAlias + ".C_BPartner_ID = sp.C_BPartner_ID"
				+ " AND ssr.C_Sponsor_Parent_ID = ?"
				+ ")"
				+ " AND ? BETWEEN ssr.Validfrom AND Validto"
				+ " AND ssr.C_BPartner_ID > 0";
		params.add(sno.getSponsorID());
		params.add(date);

		return new String[]{whereClause};
	}

	public IInfoSimple getParent()
	{
		return parent;
	}
	
	protected abstract SponsorNoObject getSponsorNoObject();
}
