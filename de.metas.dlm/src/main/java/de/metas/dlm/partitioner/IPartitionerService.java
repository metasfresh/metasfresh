package de.metas.dlm.partitioner;

import org.adempiere.util.ISingletonService;

import de.metas.dlm.Partition;
import de.metas.dlm.partitioner.config.PartitionerConfig;

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

public interface IPartitionerService extends ISingletonService
{
	/**
	 * Use the given config and create a partition. Note that the records which are part of the partition are not modified by this method.
	 *
	 * @param config
	 * @return
	 */
	Partition createPartition(PartitionerConfig config);

	/**
	 * Update the DLM_Partion_ID of the records we found.
	 *
	 * @param partition
	 */
	void storePartition(Partition partition);
}
