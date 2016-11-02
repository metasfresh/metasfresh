package de.metas.dlm.partitioner.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.util.Check;

import de.metas.dlm.IDLMService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.partitioner.config.PartitionerConfigReference.RefBuilder;

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
 * <p>
 * Almost immutable; only the {@link #getDLM_Partition_Config_Line_ID()} property is mutable.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartitionerConfigLine
{

	private final String tablename;
	private final PartitionerConfig parent;
	private final List<PartitionerConfigReference> references = new ArrayList<>();
	private int DLM_Partition_Config_Line_ID = 0;

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
	public List<PartitionerConfigReference> getReferences()
	{
		return references;
	}

	public PartitionerConfig getParent()
	{
		return parent;
	}

	public int getDLM_Partition_Config_Line_ID()
	{
		return DLM_Partition_Config_Line_ID;
	}

	public void setDLM_Partition_Config_Line_ID(final int DLM_Partition_Config_Line_ID)
	{
		this.DLM_Partition_Config_Line_ID = DLM_Partition_Config_Line_ID;
	}

	@Override
	public String toString()
	{
		return "PartitionerConfigLine [tablename=" + tablename + ", references=" + references + "]";
	}

	public static class LineBuilder
	{
		private final PartitionerConfig.Builder parentBuilder;

		private String tableName;

		private final List<PartitionerConfigReference.RefBuilder> refBuilders = new ArrayList<>();

		private PartitionerConfigLine buildLine;

		private int dlm_Partition_Config_Line_ID;

		LineBuilder(final PartitionerConfig.Builder parentBuilder)
		{
			this.parentBuilder = parentBuilder;
		}

		public LineBuilder setTableName(final String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		public String getTableName()
		{
			return tableName;
		}

		public LineBuilder setDLM_Partition_Config_Line(final int dlm_Partition_Config_Line_ID)
		{
			this.dlm_Partition_Config_Line_ID = dlm_Partition_Config_Line_ID;
			return this;
		}

		/**
		 * Convenience method to end the current line builder and start a new one.
		 *
		 * @return the new line builder, <b>not</b> this instance.
		 */
		public LineBuilder line(final String tableName)
		{
			return endLine().line(tableName);
		}

		public PartitionerConfig.Builder endLine()
		{
			return parentBuilder;
		}

		public PartitionerConfigLine buildLine(final PartitionerConfig parent)
		{
			buildLine = new PartitionerConfigLine(parent, tableName);
			buildLine.setDLM_Partition_Config_Line_ID(dlm_Partition_Config_Line_ID);
			return buildLine;
		}

		public PartitionerConfigReference.RefBuilder ref()
		{
			final PartitionerConfigReference.RefBuilder refBuilder = new PartitionerConfigReference.RefBuilder(this);
			refBuilders.add(refBuilder);

			return refBuilder;
		}

		public LineBuilder endRef()
		{
			final List<RefBuilder> distinctRefBuilders = refBuilders.stream().distinct().collect(Collectors.toList());
			refBuilders.clear();
			refBuilders.addAll(distinctRefBuilders);
			return this;
		}

		public void buildRefs()
		{
			for (final PartitionerConfigReference.RefBuilder refBuilder : refBuilders)
			{
				final PartitionerConfigReference ref = refBuilder.build(buildLine);
				buildLine.references.add(ref);
			}

		}

		@Override
		public String toString()
		{
			return "LineBuilder [parentBuilder=" + parentBuilder + ", DLM_Partition_Config_Line_ID=" + dlm_Partition_Config_Line_ID + ", tableName=" + tableName + ", refBuilders=" + refBuilders + ", buildLine=" + buildLine + "]";
		}
	}
}
