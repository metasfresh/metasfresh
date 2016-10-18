package de.metas.dlm.partitioner.config;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

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

	private final String tablename;
	private final PartitionerConfig parent;
	private final List<PartionConfigReference> references = new ArrayList<>();

	private PartitionerConfigLine(final PartitionerConfig parent, final String tableName)
	{
		Check.assumeNotNull(parent, "Paramter 'parent is not null");
		Check.assumeNotEmpty(tableName, "Parameter 'tableName' is not empty");

		this.parent = parent;
		tablename = tableName;
	}

	/**
	 * The name of the DLM'ed table. Records of this table can be loaded as {@link IDLMAware} instances.
	 *
	 * @return never <code>null</code>, but always a real table name
	 */
	public String getTableName()
	{
		return tablename;
	}

	/**
	 *
	 * @return never <code>null</code>
	 */
	public List<PartionConfigReference> getReferences()
	{
		return references;
	}

	public PartitionerConfig getParent()
	{
		return parent;
	}

	@Override
	public String toString()
	{
		return "PartitionerConfigLine [parent=" + parent + ", tablename=" + tablename + ", references=" + references + "]";
	}

	public static class LineBuilder
	{
		private final PartitionerConfig.Builder parentBuilder;

		private String tableName;

		private final List<PartionConfigReference.RefBuilder> refBuilders = new ArrayList<>();

		private PartitionerConfigLine buildLine;

		LineBuilder(final PartitionerConfig.Builder parentBuilder)
		{
			this.parentBuilder = parentBuilder;
		}

		public LineBuilder setTableName(final String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		/**
		 * Convenience method to end the current line builder and start a new one.
		 *
		 * @return the new line builder, <b>not</b> this instance.
		 */
		public LineBuilder newLine()
		{
			return endLine().newLine();
		}

		public PartitionerConfig.Builder endLine()
		{
			return parentBuilder;
		}

		public PartitionerConfigLine buildLine(final PartitionerConfig parent)
		{
			buildLine = new PartitionerConfigLine(parent, tableName);
			return buildLine;
		}

		public PartionConfigReference.RefBuilder newRef()
		{
			final PartionConfigReference.RefBuilder refBuilder = new PartionConfigReference.RefBuilder(this);
			refBuilders.add(refBuilder);

			return refBuilder;
		}

		public void buildRefs()
		{
			for (final PartionConfigReference.RefBuilder refBuilder : refBuilders)
			{
				final PartionConfigReference ref = refBuilder.build(buildLine);
				buildLine.references.add(ref);
			}

		}
	}

}
