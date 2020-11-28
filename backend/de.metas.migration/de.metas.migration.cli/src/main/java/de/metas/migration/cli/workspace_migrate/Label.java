package de.metas.migration.cli.workspace_migrate;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
final class Label
{
	public static Label ofString(final String name)
	{
		return new Label(name);
	}

	public static ImmutableSet<Label> ofCommaSeparatedString(final String labelsStr)
	{
		if (labelsStr == null || labelsStr.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<String> labels = Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(labelsStr);

		return labels.stream()
				.distinct()
				.map(Label::ofString)
				.collect(ImmutableSet.toImmutableSet());
	}

	private final String name;

	private Label(@NonNull final String name)
	{
		this.name = name.trim();
	}

	@Override
	public String toString()
	{
		return name;
	}
}
