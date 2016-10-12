package de.metas.dlm.partitioner.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartionConfigReference;
import de.metas.dlm.partitioner.config.PartitionerConfig;
import de.metas.dlm.partitioner.config.PartitionerConfigLine;

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

public class PartitionerService implements IPartitionerService
{

	@Override
	public void createPartition(final PartitionerConfig config)
	{
		final Set<IDLMAware> records = new HashSet<>();

		// we need to get to the "first" line(s),
		// i.e. those DLM_PartionLine_Configs that are not referenced via DLM_PartionReference_Config.DLM_Referencing_PartionLine_Config_ID
		// i think we can also live with circles, i.e. if there is no "first", but for those "firsts" that there ware, we need to start with them
		final List<PartitionerConfigLine> lines = config.getLines(); // DLM_PartionLine_Config

		for (final PartitionerConfigLine line : lines)
		{
			// AD_Table_ID
			// get the "record" from DLM_PartionLine_Config.AD_Table_ID as the database record with the smallest ID
			// that does not yet have a DLM_Partition_ID
			IDLMAware record = null;

			records.addAll(recurse(line, record));
		}

		// now, update the DLM_Partion_ID of the records we found.
		//
		// then figure out if records are missing:
		// update each records' DLM_Level to 1 (1="test").
		// if there is a DLMException, then depending on our config,
		// throw an exception,
		// skip the record
		// or add another PartitionerConfigLine, get the additional line's records and retry.
		// when adding another PartitionerConfigLine but the table is not DLM'ed yet, then again depending on out config,
		// throw an exception or DLM it on the fly.
		//
		// if the update worked, then set the DLM level back to the old value
	}

	private Set<IDLMAware> recurse(final PartitionerConfigLine line, IDLMAware record)
	{
		final Set<IDLMAware> records = new HashSet<>();
		if (!records.add(record))
		{
			return Collections.emptySet(); // avoid circles
		}

		for (final PartionConfigReference ref : line.getReferences()) // DLM_PartionReference_Config
		{
			// for each ref,

			// get the foreign key ID of
			// table DLM_PartionLine_Config.AD_Table_ID,
			// column DLM_PartionReference_Config.DLM_Referencing_Column_ID

			// load the foreign record from the table DLM_PartionReference_Config.DLM_Referenced_Table_ID
			IDLMAware foreignRecord = null;

			// get the foreign record's PartitionerConfigLine (DLM_PartionReference_Config.DLM_Referencing_PartionLine_Config_ID)
			PartitionerConfigLine foreignline = null;

			// recurse
			records.addAll(recurse(foreignline, foreignRecord));
		}

		return records;
	}

}
