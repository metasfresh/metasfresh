package de.metas.dlm.migrator;

import org.adempiere.util.ISingletonService;

import de.metas.dlm.Partition;

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
	 * Checks if the given partition could be migrated. This is the case if the partition is "complete", meaning that there would be no records left behind with dangling references, if we migrated the partition.
	 *
	 * @param partition
	 * @throws {@link DLMException} if the given migration is not complete and therefore cannot be migrated.
	 */
	void testMigratePartition(Partition partition);
}
