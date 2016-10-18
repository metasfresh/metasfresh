package de.metas.dlm.partitioner.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.util.Check;

import de.metas.dlm.partitioner.config.PartionerConfigReference.RefBuilder;
import de.metas.dlm.partitioner.config.PartitionerConfigLine.LineBuilder;

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

public class PartitionerConfig
{
	private final List<PartitionerConfigLine> lines;

	private PartitionerConfig()
	{
		lines = new ArrayList<>();
	}

	/**
	 *
	 * @return never <code>null</code>.
	 */
	public List<PartitionerConfigLine> getLines()
	{
		return lines;
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
	public static Builder builder(final PartitionerConfig config)
	{
		return new Builder(config);
	}

	public PartitionerConfigLine getLine(final String tableName)
	{
		return lines.stream()
				.filter(l -> l.getTableName().equalsIgnoreCase(tableName))
				.findFirst()
				.orElseThrow(Check.supplyEx("Partitionconfig={} does not contain any line for tableName={}", this, tableName));
	}

	public static class Builder
	{
		private final List<PartitionerConfigLine.LineBuilder> lineBuilders = new ArrayList<>();

		public Builder()
		{
			this(null);
		}

		public Builder(final PartitionerConfig config)
		{
			initializeFromconfig(config);
		}

		private void initializeFromconfig(final PartitionerConfig config)
		{
			if (config == null)
			{
				return;
			}
			for (final PartitionerConfigLine line : config.getLines())
			{
				final LineBuilder lineBuilder = newLine().setTableName(line.getTableName());
				for (final PartionerConfigReference ref : line.getReferences())
				{
					final RefBuilder refBuilder = lineBuilder.newRef().setReferencedTableName(ref.getReferencedTableName()).setReferencingColumnName(ref.getReferencingColumnName());
					if (ref.getReferencedConfigLine() != null)
					{
						refBuilder.setReferencedConfigLine(ref.getReferencedConfigLine().getTableName());
					}
					refBuilder.endRef();
				}
				lineBuilder.endLine();
			}
		}

		public PartitionerConfigLine.LineBuilder newLine()
		{
			final PartitionerConfigLine.LineBuilder lineBuilder = new PartitionerConfigLine.LineBuilder(this);
			lineBuilders.add(lineBuilder);
			return lineBuilder;
		}

		public PartitionerConfig build()
		{
			final PartitionerConfig partitionerConfig = new PartitionerConfig();

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

	/**
	 *
	 * @param tableName
	 * @return a list of {@link PartionerConfigReference}s which reference the given table name (ignoring upper/lower case).
	 */
	public List<PartionerConfigReference> getReferences(final String tableName)
	{
		final List<PartionerConfigReference> references = getLines().stream()
				.flatMap(line -> line.getReferences().stream()) // get a stream of all references
				.filter(ref -> tableName.equalsIgnoreCase(ref.getReferencedTableName())) // filter those who refer to 'tablename'
				.collect(Collectors.toList());
		return references;
	}
}
