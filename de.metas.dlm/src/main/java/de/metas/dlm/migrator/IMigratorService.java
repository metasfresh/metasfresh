package de.metas.dlm.migrator;

import org.adempiere.util.ISingletonService;

import de.metas.dlm.IDLMService;
import de.metas.dlm.Partition;
import de.metas.dlm.model.IDLMAware;

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

public interface IMigratorService extends ISingletonService
{
	/**
	 * This value is equivalent to <code>DLM_Level IS NULL</code> in the database's DLM'ed records.
	 */
	public static final int DLM_Level_NOT_SET = 0;

	public static final int DLM_Level_LIVE = 1;

	public static final int DLM_Level_TEST = 2;

	public static final int DLM_Level_ARCHIVE = 3;

	/**
	 * Checks if the given partition could be migrated.
	 * This is the case if the partition is "complete", meaning that there would be no records left behind with dangling references, if we migrated the partition.
	 * <p>
	 * Make the test by temporarily setting <code>DLM_Level</code> to {@link #DLM_Level_TEST}. If it works, then undo the change.
	 * Both operations are performed in a local transaction.
	 *
	 * @param partition the partition to test. Please note that the content of {@link Partition#getRecords()} does not play a role.
	 * @throws {@link DLMReferenceException} if the given migration is not complete and therefore cannot be migrated.
	 *
	 * @see IDLMService#directUpdateDLMColumn(org.adempiere.model.IContextAware, int, String, int)
	 */
	void testMigratePartition(Partition partition);

	/**
	 * Changes the {@link IDLMAware#COLUMNNAME_DLM_Level} to {@link Partition#getTargetDLMLevel()} for all records for the given <code>partition</code>.
	 *
	 * @param partition partition the partition to change the leve for. Please note that the content of {@link Partition#getRecords()} does not play a role.
	 * @param targetLevel
	 * @return a partition instance whose {@link Partition#getCurrentDLMLevel()} reflects the migration that was performed.
	 *
	 * @see IDLMService#directUpdateDLMColumn(org.adempiere.model.IContextAware, int, String, int)
	 */
	Partition migratePartition(Partition partition);
}
