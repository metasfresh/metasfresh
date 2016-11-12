package de.metas.dlm;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Column;

import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.partitioner.config.TableReferenceDescriptor;

/*
 * #%L
 * metasfresh-dlm
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IDLMService extends ISingletonService
{
	/**
	 * Call the DB function <code>dlm.add_table_to_dlm()</code> with the given <code>table</code>'s name
	 * and create new {@link I_AD_Column}s for the columns declared by {@link IDLMAware} if necessary.
	 *
	 * <b>WARNING:</b> this will require an <code>ACCESS EXCLUSIVE</code> from the DB, for the given <code>tableName</code>.
	 * This means that even if there was only as much as a <b>SELECT</b> on the table and the select's <code>ACCESS SHARE</code> was not yet released, then this method will hang.
	 *
	 * @param table
	 */
	void addTableToDLM(I_AD_Table table);

	/**
	 * Call the DB function <code>dlm.remove_table_from_dlm()</code> with the given <code>table</code>'s name
	 * and remove the {@link I_AD_Column}s for the columns declared by {@link IDLMAware}.
	 * <p>
	 * As of now, don't drop the physical columns from the table.
	 *
	 * @param table
	 */
	void removeTableFromDLM(I_AD_Table table);

	/**
	 * Updates the given <code>columnName</code> for all records (which can be in different tables) that reference the given partition-ID.
	 *
	 * @param ctxAware
	 * @param dlmPartitionId the <code>DLM_Partition_ID</code> whose records shall be updated
	 * @param columnName name of the to update. Alle referenced records need to have this column, so it generally needs to be one of the columns declared in {@link IDLMAware}.
	 * @param targetValue
	 */
	void directUpdateDLMColumn(IContextAware ctxAware, int dlmPartitionId, String columnName, int targetValue);

	/**
	 *
	 * @return a map of AD_Table_ID => List-of-AD_Table_ID where the key-<code>AD_Table_ID</code>'s DB-records are
	 *         referenced by records from the <code>*Table_ID</code> and <code>*Record_ID</code> columns of the tables from value-<code>List<AD_Table_ID></code>.
	 */
	List<TableReferenceDescriptor> retrieveTableRecordReferences();

	Stream<IQueryBuilder<IDLMAware>> retrieveDLMTableNames(IContextAware ctxAware, int dlmPartitionId);
}
