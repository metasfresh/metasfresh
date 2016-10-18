package de.metas.dlm.partitioner.config;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

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

	public PartitionerConfigLine getLine(final String tableName)
	{
		return lines.stream()
				.filter(l -> l.getTableName().equals(tableName))
				.findFirst()
				.orElseThrow(Check.supplyEx("Partitionconfig={} does not contain any line for tableName={}", this, tableName));
	}

	public static class Builder
	{
		private final List<PartitionerConfigLine.LineBuilder> lineBuilders = new ArrayList<>();

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
}
