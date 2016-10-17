package de.metas.dlm.partitioner.config;

import java.util.List;

import de.metas.dlm.IDLMService;
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

/**
 * Each instance of this class if about one table which was added to DLM (see {@link IDLMService#addTableToDLM(org.compiere.model.I_AD_Table)})
 * and whose records shall be assigned to partitions so they can be moved to other DLM-Levels.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartitionerConfigLine
{

	/**
	 * The name of the DLM'ed table. Records of this table can be loaded as {@link IDLMAware} instances.
	 *
	 * @return
	 */
	public String getTableName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<PartionConfigReference> getReferences()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public PartitionerConfig getParent()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
