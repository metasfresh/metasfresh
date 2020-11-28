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


import org.compiere.model.I_AD_InfoColumn;
import org.compiere.util.Env;

import de.metas.i18n.Msg;

/**
 * @author cg
 *
 */
public abstract class InfoQueryCriteriaBPRadiusAbstract implements IInfoQueryCriteria
{
	
	public static final String SYSCONFIG_DefaultRadius = "de.metas.radiussearch.DefaultRadius";

	protected String locationTableAlias = "a";
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
	public int getParameterCount()
	{
		return 2;
	}

	@Override
	public String getLabel(int index)
	{
		if (index == 0)
			return Msg.translate(Env.getCtx(), "NearCity");
		else if (index == 1)
			return Msg.translate(Env.getCtx(), "RadiusKM");
		else
			return null;
	}

	@Override
	public Object getParameterToComponent(int index)
	{
		return null;
	}

	@Override
	public Object getParameterValue(int index, boolean returnValueTo)
	{
		return null;
	}
	
	public IInfoSimple getParent()
	{
		return parent;
	}

}
