package de.metas.adempiere.service;

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


import java.util.List;
import java.util.Properties;

import org.compiere.model.PO;

import de.metas.adempiere.engine.MViewMetadata;
import de.metas.adempiere.model.I_AD_Table_MView;
import de.metas.util.ISingletonService;

public interface ITableMViewBL extends ISingletonService
{
	public static enum RefreshMode
	{
		Partial,
		Complete,
	};

	I_AD_Table_MView fetchForTable(Properties ctx, int AD_Table_ID, String trxName);

	I_AD_Table_MView fetchForTableName(Properties ctx, String tableName, String trxName);

	List<I_AD_Table_MView> fetchAll(Properties ctx);

	List<I_AD_Table_MView> fetchForSourceTableName(Properties ctx, String sourceTableName);

	MViewMetadata getMetadata(I_AD_Table_MView mview);

	void setMetadata(MViewMetadata mdata);

	void setStaled(I_AD_Table_MView mview);

	boolean isAllowRefresh(I_AD_Table_MView mview, PO sourcePO, RefreshMode refreshMode);

	void refreshEx(I_AD_Table_MView mview, PO sourcePO, RefreshMode refreshMode, String trxName);
	boolean refresh(I_AD_Table_MView mview, PO sourcePO, RefreshMode refreshMode, String trxName);

	boolean isSourceChanged(MViewMetadata mdata, PO sourcePO, int changeType);
}
