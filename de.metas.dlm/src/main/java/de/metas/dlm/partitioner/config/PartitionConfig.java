package de.metas.dlm.partitioner.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;

import com.google.common.collect.ImmutableList;

import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartitionerConfigLine.LineBuilder;
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
 * One instance specifies all tables whose records shall be part of DLM.
 * Almost immutable; only the {@link #getDLM_Partition_Config_ID()} property is mutable.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartitionConfig
{
	/**
	 * If this instance is already persisted on DB, then this is its key.
	 */
	private int DLM_Partition_Config_ID = 0;

	private final List<PartitionerConfigLine> lines;

	private final String name;

	private final boolean changed;

	private PartitionConfig(final String name, final boolean changed)
	{
		lines = new ArrayList<>();
		this.name = name;
		this.changed = changed;
	}

	/**
	 * See {@link PartitionConfig.Builder#setDLM_Partition_Config_ID(int)} and {@link #getDLM_Partition_Config_ID()}.
	 *
	 * @return
	 */
	public int getDLM_Partition_Config_ID()
	{
		return DLM_Partition_Config_ID;
	}

	/**
	 * To be invoked by {@link IPartitionerService#storePartitionConfig(PartitionConfig)} after a {@link I_DLM_Partition_Config}
	 * record was created or updated for this instance.
	 *
	 * @param dlm_Partition_Config_ID
	 */
	public void setDLM_Partition_Config_ID(final int dlm_Partition_Config_ID)
	{
		DLM_Partition_Config_ID = dlm_Partition_Config_ID;
	}

	/**
	 *
	 * @return never <code>null</code>.
	 */
	public List<PartitionerConfigLine> getLines()
	{
		return ImmutableList.copyOf(lines);
	}

	public String getName()
	{
		return name;
	}

	public boolean isChanged()
	{
		return changed;
	}

	/**
	 *
	 * @param tableName
	 * @return a list of {@link PartitionerConfigReference}s which reference the given table name.
	 */
	public List<PartitionerConfigReference> getReferences(final String tableName)
	{
		final List<PartitionerConfigReference> references = getLines().stream()
				.flatMap(line -> line.getReferences().stream()) // get a stream of all references
				.filter(ref -> tableName.equals(ref.getReferencedTableName())) // filter those who refer to 'tablename'
				.collect(Collectors.toList());
		return references;
	}

	/**
	 * Use this method if you know the line exists. It throws an exception with a nice error-message for you if it doesn't exist after all.
	 *
	 * @param tableName
	 * @return
	 */
	public PartitionerConfigLine getLineNotNull(final String tableName)
	{
		final Optional<PartitionerConfigLine> line = getLine(tableName);
		return line.orElseThrow(Check.supplyEx("Missing line for tableName={} in Partitionconfig={}", tableName, this));
	}

	public Optional<PartitionerConfigLine> getLine(final String tableName)
	{
		return lines.stream()
				.filter(l -> l.getTableName().equals(tableName))
				.findFirst();
	}

	public static Builder builder()
	{
		return new Builder();
	}

	/**
	 * Creates a new builder and "prepopulates" it using the lines and references from the given <code>config</code>.
	 *
	 * @param config
	 * @return
	 */
	public static Builder builder(final PartitionConfig config)
	{
		return new Builder(config);
	}

	@Override
	public String toString()
	{
		return "PartitionerConfig [name=" + name + ", DLM_Partition_Config_ID=" + DLM_Partition_Config_ID + ", lines=" + lines + "]";
	}

	@Override
	public boolean equals(final Object other)
	{
		if (!(other instanceof PartitionConfig))
		{
			return false;
		}

		final PartitionConfig otherConfig = (PartitionConfig)other;

		return new EqualsBuilder().append(name, otherConfig.name)
				.append(lines, otherConfig.lines)
				.isEqual();
	}

	public boolean isMissing(final TableReferenceDescriptor descriptor)
	{
		final Optional<PartitionerConfigLine> existingLine = getLine(descriptor.getReferencingTableName());
		if (!existingLine.isPresent())
		{
			return true; // not even the line exists, so it's missing
		}

		final boolean referenceExists = existingLine.get().getReferences()
				.stream()
				.anyMatch(ref -> ref.getReferencedTableName().equals(descriptor.getReferencedTableName()) && ref.getReferencingColumnName().equals(descriptor.getReferencingColumnName()));
		return !referenceExists;
	}

	public static class Builder
	{
		private final List<PartitionerConfigLine.LineBuilder> lineBuilders = new ArrayList<>();

		private String name;

		private boolean changed = true;

		private int DLM_Partition_Config_ID;

		public Builder()
		{
			this(null);
		}

		public Builder(final PartitionConfig config)
		{
			initializeFromconfig(config);
		}

		private void initializeFromconfig(final PartitionConfig config)
		{
			if (config == null)
			{
				return;
			}
			setDLM_Partition_Config_ID(config.getDLM_Partition_Config_ID());
			setName(config.getName());

			for (final PartitionerConfigLine line : config.getLines())
			{
				final LineBuilder lineBuilder = newLine()
						.setTableName(line.getTableName())
						.setDLM_Partition_Config_Line(line.getDLM_Partition_Config_Line_ID());

				for (final PartitionerConfigReference ref : line.getReferences())
				{
					final RefBuilder refBuilder = lineBuilder.ref()
							.setReferencedTableName(ref.getReferencedTableName())
							.setReferencingColumnName(ref.getReferencingColumnName())
							.setDLM_Partition_Config_Reference_ID(ref.getDLM_Partition_Config_Reference_ID());

					refBuilder.endRef();
				}
				lineBuilder.endLine();
			}
		}

		public Builder setName(final String name)
		{
			this.name = name;
			return this;
		}

		/**
		 * Sets the primary key of the {@link I_DLM_Partition_Config} records that already exists for the {@link PartitionConfig} instances that we want to build.
		 * This information is important when a config is persisted because it determines if a {@link I_DLM_Partition_Config} record shall be loaded and updated rather than inserted.
		 *
		 * @param dlm_Partition_Config_ID
		 * @return
		 */
		public Builder setDLM_Partition_Config_ID(final int dlm_Partition_Config_ID)
		{
			DLM_Partition_Config_ID = dlm_Partition_Config_ID;
			return this;
		}

		public Builder setChanged(final boolean changed)
		{
			this.changed = changed;
			return this;
		}

		private PartitionerConfigLine.LineBuilder newLine()
		{
			assertLineTableNamesUnique();

			final PartitionerConfigLine.LineBuilder lineBuilder = new PartitionerConfigLine.LineBuilder(this);
			lineBuilders.add(lineBuilder);
			return lineBuilder;
		}

		/**
		 *
		 * @param tableName
		 * @return the already existing line builder for the given <code>tableName</code>, or a new one, if none existed yet.
		 */
		public LineBuilder line(final String tableName)
		{
			final Optional<LineBuilder> existingLineBuilder = lineBuilders
					.stream()
					.filter(b -> tableName.equals(b.getTableName()))
					.findFirst();
			if (existingLineBuilder.isPresent())
			{
				return existingLineBuilder.get();
			}

			setChanged(true);
			return newLine().setTableName(tableName);
		}

		private void assertLineTableNamesUnique()
		{
			final List<LineBuilder> nonUniqueLines = lineBuilders.stream()
					.collect(Collectors.groupingBy(r -> r.getTableName())) // group them by tableName; this returns a map
					.entrySet().stream() // now stream the map's entries
					.filter(e -> e.getValue().size() > 1) // now remove from the stream those that are OK
					.flatMap(e -> e.getValue().stream()) // now get a stream with those that are not OK
					.collect(Collectors.toList());

			Check.errorUnless(nonUniqueLines.isEmpty(), "Found LineBuilders with duplicate tableNames: {}", nonUniqueLines);

		}

		public PartitionConfig build()
		{
			assertLineTableNamesUnique();

			final PartitionConfig partitionerConfig = new PartitionConfig(name, changed);
			partitionerConfig.setDLM_Partition_Config_ID(DLM_Partition_Config_ID);

			// first build the lines
			for (final PartitionerConfigLine.LineBuilder lineBuilder : lineBuilders)
			{
				final PartitionerConfigLine line = lineBuilder.buildLine(partitionerConfig);
				partitionerConfig.lines.add(line);
			}

			for (final PartitionerConfigLine.LineBuilder lineBuilder : lineBuilders)
			{
				lineBuilder.buildRefs();
			}
			return partitionerConfig;
		}
	}
}
