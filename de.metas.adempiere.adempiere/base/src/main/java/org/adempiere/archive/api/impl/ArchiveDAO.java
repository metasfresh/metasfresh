package org.adempiere.archive.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.Query;
import org.compiere.util.Env;

public class ArchiveDAO implements IArchiveDAO
{
	// private static final transient CLogger logger = CLogger.getCLogger(ArchiveDAO.class);

	@Override
	public List<I_AD_Archive> retrieveArchives(final Properties ctx, final String whereClause)
	{
		final StringBuilder wc = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<Object>();

		wc.append(I_AD_Archive.COLUMNNAME_AD_Client_ID).append("=?");
		sqlParams.add(Env.getAD_Client_ID(ctx));

		if (!Check.isEmpty(whereClause, true))
		{
			wc.append(" ").append(whereClause);
		}

		return new Query(ctx, I_AD_Archive.Table_Name, wc.toString(), ITrx.TRXNAME_None)
				.setParameters(sqlParams)
				.setOrderBy(I_AD_Archive.COLUMNNAME_Created)
				.list(I_AD_Archive.class);
	}

	@Override
	public <T> T retrieveReferencedModel(final I_AD_Archive archive, final Class<T> modelClass)
	{
		// TODO: use org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal<ParentModelType>

		if (archive == null)
		{
			return null;
		}

		final int tableId = archive.getAD_Table_ID();
		if (tableId <= 0)
		{
			return null;
		}

		final int recordId = archive.getRecord_ID();
		if (recordId < 0)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final String trxName = InterfaceWrapperHelper.getTrxName(archive);
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);
		final T model = InterfaceWrapperHelper.create(ctx, tableName, recordId, modelClass, trxName);
		return model;
	}

	@Override
	public <T extends I_AD_Archive> T retrievePDFArchiveForModel(final Object model, final Class<T> archiveClass)
	{
		return null; // nothing at this level
	}
}
