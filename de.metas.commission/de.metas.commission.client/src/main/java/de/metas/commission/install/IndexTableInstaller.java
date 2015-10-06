package de.metas.commission.install;

/*
 * #%L
 * de.metas.commission.client
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


import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.process.AD_Index_Create;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MIndexColumn;
import org.compiere.model.MIndexTable;
import org.compiere.model.MTable;
import org.compiere.util.Trx;

public final class IndexTableInstaller extends Installer {

	private MIndexTable indexTable;

	private IndexTableInstaller(Properties ctx, String trxName) {
		super(ctx, trxName);
	}

	public static IndexTableInstaller mkInst(final Properties ctx,
			final String trxName) {

		return new IndexTableInstaller(ctx, trxName);
	}

	public IndexTableInstaller createOrUpdate(final String entityType,
			final String tableName, final String indexName) {

		final MIndexTable[] allForTable = MIndexTable
				.getByTable(ctx, tableName);

		MIndexTable index = null;

		for (final MIndexTable currentIndex : allForTable) {

			if (indexName.equals(currentIndex.getName())) {

				index = currentIndex;
				break;
			}
		}

		if (index == null) {

			index = new MIndexTable(ctx, 0, trxName);
			index.setName(indexName);
		} else {
			index.set_TrxName(trxName);
		}

		index.setAD_Table_ID(MTable.getTable_ID(tableName));
		index.setEntityType(entityType);

		index
				.setDescription("Record has been auto created or updated by "
						+ IndexTableInstaller.class + " at "
						+ SystemTime.asTimestamp());

		this.indexTable = index;

		return this;
	}

	public IndexTableInstaller setUnique(final boolean unique) {

		indexTable.setIsUnique(unique);
		setSaved(false);

		return this;
	}

	public IndexTableInstaller setWhereClause(final String whereClause) {

		indexTable.setWhereClause(whereClause);
		setSaved(false);
		return this;
	}

	public IndexTableInstaller addIndexColumns(final String... colNames) {

		checkSaved();

		int i = 10;

		for (final String colName : colNames) {

			MIndexColumn.retrieveOrCreate(indexTable, colName, i);
			i += 10;
		}
		return this;
	}

	public IndexTableInstaller save() {

		save(indexTable);
		return this;
	}

	public void syncIndexTable() {

		checkSaved();

		final AD_Index_Create indexCreate = new AD_Index_Create();

		if (trxName == null) {
			indexTable.set_TrxName("tmp");
		}
		try {
			indexCreate.createIndex(indexTable);
			indexCreate.createBeforeChangeFunction(indexTable);
			Trx.get(indexTable.get_TrxName(), false).commit(true);
		} catch (SQLException e) {
			throw new DBException(e);
		}
		if (trxName == null) {
			indexTable.set_TrxName(trxName);
		}
	}

}
