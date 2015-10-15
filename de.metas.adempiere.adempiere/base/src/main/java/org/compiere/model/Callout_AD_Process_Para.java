/**
 * 
 */
package org.compiere.model;

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


import java.util.Properties;

import org.adempiere.model.GridTabWrapper;
import org.compiere.util.Util;

/**
 * @author tsa
 * 
 */
public class Callout_AD_Process_Para extends CalloutEngine
{
	public String onAD_Element_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		final I_AD_Process_Para pp = GridTabWrapper.create(mTab, I_AD_Process_Para.class);
		if (pp.getAD_Element_ID() <= 0)
		{
			return "";
		}

		I_AD_Element element = pp.getAD_Element();
		pp.setColumnName(element.getColumnName());
		if (pp.isCentrallyMaintained() || Util.isEmpty(pp.getName()))
			pp.setName(element.getName());
		if (pp.isCentrallyMaintained() || Util.isEmpty(pp.getDescription()))
			pp.setDescription(element.getDescription());
		if (pp.isCentrallyMaintained() || Util.isEmpty(pp.getHelp()))
			pp.setHelp(element.getHelp());
		
		String entityType = element.getEntityType();
		if ("D".equals(entityType))
		{
			I_AD_Process process = pp.getAD_Process();
			entityType = process.getEntityType();
		}
		if (!"D".equals(entityType))
		{
			pp.setEntityType(entityType);
		}
		
		return "";
	}

}
