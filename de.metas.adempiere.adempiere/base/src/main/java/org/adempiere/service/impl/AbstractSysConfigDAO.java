package org.adempiere.service.impl;

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


import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.service.ISysConfigDAO;
import org.compiere.model.I_AD_SysConfig;

import de.metas.util.Services;

public abstract class AbstractSysConfigDAO implements ISysConfigDAO
{
	/**
	 * ORDER BY used when searching for a particular AD_SysConfig record. i.e.
	 * <ul>
	 * <li>AD_Client_ID DESC</li>
	 * <li>AD_Org_ID DESC</li>
	 * </ul>
	 */
	protected final IQueryOrderBy sysConfigFindOrderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_AD_SysConfig.class)
			.addColumn(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, false)
			.addColumn(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, false)
			.createQueryOrderBy();
}
