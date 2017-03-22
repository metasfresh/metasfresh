package de.metas.dlm.partitioner;

import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.connection.ITemporaryConnectionCustomizer;
import de.metas.dlm.Partition;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest;
import de.metas.dlm.partitioner.config.PartitionConfig;
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

public interface IPartitionerService extends ISingletonService
{
	/**
	 * Use the given config and create and store a list of partitions.
	 * The method iterates the lines for the {@link PartitionConfig} included in the given <code>request</code>. For each line, it loads one record then follows that record's references.
	 * It might turn out that there is one new partition per line, or one new partition for all lines.
	 *
	 * Note that the {@link IDLMAware} records which are identified as parts of the partitions are have their {@link IDLMAware#COLUMNNAME_DLM_Partition_ID} updated by this method.
	 *
	 * @param request
	 * @return
	 */
	List<Partition> createPartition(CreatePartitionRequest request);

	/**
	 * Creates a new config that has both everything from the given <code>config</code> and the reference specified by the given <code>descriptor</code>.
	 *
	 * @param config
	 * @param descriptor
	 * @return
	 */
	PartitionConfig augmentPartitionerConfig(PartitionConfig config, List<TableReferenceDescriptor> descriptor);

	/**
	 * Create a connection customizer that can (and should) be registered via {@link de.metas.connection.IConnectionCustomizerService#registerTemporaryCustomizer(ITemporaryConnectionCustomizer)}
	 * for the time that {@link #createPartition(CreatePartitionRequest)} and {@link #storePartition(Partition)} are invoked.
	 *
	 * @return
	 */
	ITemporaryConnectionCustomizer createConnectionCustomizer();
}
