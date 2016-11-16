package org.adempiere.ad.table.process;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;

import de.metas.process.RunOutOfTrx;
import de.metas.process.SvrProcess;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Generates primary key columns for all tables.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_Table_CreatePK_AllMissing extends SvrProcess
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final TablePrimaryKeyGenerator generator = new TablePrimaryKeyGenerator(getCtx());
		generator.generateForTablesIfPossible(retrieveTablesWithMissingPK());
		return generator.getSummary();
	}

	private List<I_AD_Table> retrieveTablesWithMissingPK()
	{
		final IQuery<I_AD_Column> primaryKeyColumnQuery = queryBL
				.createQueryBuilder(I_AD_Column.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_AD_Column.COLUMN_IsKey, true)
				.create();

		return queryBL.createQueryBuilder(I_AD_Table.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Table.COLUMN_IsView, false) // exclude views
				.addNotInSubQueryFilter(I_AD_Table.COLUMN_AD_Table_ID, I_AD_Column.COLUMN_AD_Table_ID, primaryKeyColumnQuery) // no primary key
				.create()
				.list(I_AD_Table.class);
	}

}
