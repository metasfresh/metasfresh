/**
 * 
 */
package org.adempiere.model.tree.spi.impl;

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


import org.compiere.model.I_AD_OrgType;
import org.compiere.model.MTree;

/**
 * @author tsa
 *
 */
public class OrgTreeSupport extends DefaultPOTreeSupport
{
	@Override
	protected String getPrintColorSQL(String tableAlias)
	{
		return "x."+I_AD_OrgType.COLUMNNAME_AD_PrintColor_ID;
	}

	@Override
	protected String getNodeInfoFromSQL(MTree tree, String tableAlias)
	{
		return "AD_Org "+tableAlias+" INNER JOIN AD_OrgInfo i ON ("+tableAlias+".AD_Org_ID=i.AD_Org_ID) "
		+ "LEFT OUTER JOIN AD_OrgType x ON (i.AD_OrgType_ID=x.AD_OrgType_ID)";
	}
}
