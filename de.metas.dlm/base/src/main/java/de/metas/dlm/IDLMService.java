package de.metas.dlm;

import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Column;

import de.metas.dlm.connection.DLMConnectionCustomizer;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.config.PartitionConfig;

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

public interface IDLMService extends ISingletonService {
	/**
	 * Can be used to enable or disable the DLM connection customizer. Evaluated
	 * by {@link #isConnectionCustomizerEnabled()}.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1411
	 *
	 */
	String SYSCONFIG_DLM_CONNECTION_CUSTOMIZER_ENABLED_GENERAL = "de.metas.dlm.ConnectionCustomizer.enabled.general";

	/**
	 * Can be used to enable or disable the DLM connection customizer on a
	 * per-user basis. Evaluated by {@link #isConnectionCustomizerEnabled()}.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1411
	 *
	 */
	String SYSCONFIG_DLM_CONNECTION_CUSTOMIZER_ENABLED_FOR_USER_ = "de.metas.dlm.ConnectionCustomizer.enabled.AD_User_ID_";

	/**
	 * This method is invoked after login, to decided whether or not to activate
	 * "DLM-filtering" for the given {@code AD_User_ID}.
	 *
	 * @param AD_User_ID the user in question
	 *
	 * @return true if {@link DLMConnectionCustomizer} should be brought into
	 *         the game, in order to not "see" archived data records.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1411
	 */
	boolean isConnectionCustomizerEnabled(int AD_User_ID);

	/**
	 * Call the DB function <code>dlm.add_table_to_dlm()</code> with the given
	 * <code>table</code>'s name and create new {@link I_AD_Column}s for the
	 * columns declared by {@link IDLMAware} if necessary.
	 *
	 * <b>WARNING:</b> this will require an <code>ACCESS EXCLUSIVE</code> from
	 * the DB, for the given <code>tableName</code>. This means that even if
	 * there was only as much as a <b>SELECT</b> on the table and the select's
	 * <code>ACCESS SHARE</code> was not yet released, then this method will
	 * hang.
	 *
	 * @param table
	 */
	void addTableToDLM(I_AD_Table table);

	/**
	 * Call the DB function <code>dlm.remove_table_from_dlm()</code> with the
	 * given <code>table</code>'s name and remove the {@link I_AD_Column}s for
	 * the columns declared by {@link IDLMAware}.
	 * <p>
	 * As of now, don't drop the physical columns from the table.
	 *
	 * @param table
	 */
	void removeTableFromDLM(I_AD_Table table);

	/**
	 * Call the DB function <code>update_partition_size()</code> with the given
	 * <code>partitionDB</code>'s <code>DLM_Partition_ID</code>.<br>
	 * Do not reload the partition. That's up to the caller if and when she
	 * wants to.
	 * <p>
	 * Background into we use the size as an information to the user, and so
	 * that we keep the biggest partition when merging two or more partitions
	 * with each other.
	 *
	 * @param partitionDB
	 */
	void updatePartitionSize(I_DLM_Partition partitionDB);

	/**
	 * Updates the given <code>columnName</code> for all records (which can be
	 * in different tables) that reference the given partition-ID.
	 *
	 * @param ctxAware
	 * @param dlmPartitionId
	 *            the <code>DLM_Partition_ID</code> whose records shall be
	 *            updated
	 * @param columnName
	 *            name of the to update. Alle referenced records need to have
	 *            this column, so it generally needs to be one of the columns
	 *            declared in {@link IDLMAware}.
	 * @param targetValue
	 * @return the number of records that were updated.
	 */
	int directUpdateDLMColumn(IContextAware ctxAware, int dlmPartitionId, String columnName, int targetValue);

	

	Stream<IQueryBuilder<IDLMAware>> retrieveDLMTableNames(IContextAware ctxAware, int dlmPartitionId);

	/**
	 * Create a {@link Partition} instance for the given database record and
	 * also load both the {@link PartitionConfig} the that DB record references
	 * (see {@link #loadPartitionConfig(I_DLM_Partition_Config)}) and all
	 * records which reference the partition.
	 *
	 * @param partitionDB
	 * @return
	 */
	Partition loadPartition(I_DLM_Partition partitionDB);

	/**
	 * Create or update a {@link I_DLM_Partition} record for the given
	 * <code>partition</code> and update the
	 * {@link IDLMAware#COLUMNNAME_DLM_Partition_ID} values of the given
	 * <code>partition</code>'s records.
	 *
	 * @param partition
	 * @param runInOwnTrx
	 *            if <code>true</code>, then this method will create a dedicated
	 *            transaction using
	 *            {@link ITrxManager#run(org.compiere.util.TrxRunnable)} to
	 *            perform the actual storing in.
	 * @return a news instance that represents the just-stored partition
	 */
	Partition storePartition(Partition partition, boolean runInOwnTrx);

	/**
	 * Create a new config instance for the given database record.
	 *
	 * @param configDB.
	 *            The DB data to load. If <code>null</code>, then return an
	 *            empty config. Never return <code>null</code>.
	 * @return
	 */
	PartitionConfig loadPartitionConfig(I_DLM_Partition_Config configDB);

	PartitionConfig loadDefaultPartitionConfig();

	/**
	 * Persist the given config in the DB and update the ID properties on the
	 * given <code>config</code> that is stored.
	 * <p>
	 * Does <b>not</b> touch existing DB records which are missing in the given
	 * <code>config</code>.
	 *
	 * @param config
	 * @return
	 */
	PartitionConfig storePartitionConfig(PartitionConfig config);
}
