/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.report;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class DocumentPrintOptionDescriptorsList
{
	public static final DocumentPrintOptionDescriptorsList EMPTY = new DocumentPrintOptionDescriptorsList(ImmutableList.of());

	ImmutableList<DocumentPrintOptionDescriptor> options;

	private DocumentPrintOptionDescriptorsList(@NonNull final List<DocumentPrintOptionDescriptor> options)
	{
		this.options = ImmutableList.copyOf(options);
	}

	public static DocumentPrintOptionDescriptorsList of(@NonNull final List<DocumentPrintOptionDescriptor> options)
	{
		return !options.isEmpty()
				? new DocumentPrintOptionDescriptorsList(options)
				: EMPTY;
	}

	public DocumentPrintOptions getDefaults()
	{
		final DocumentPrintOptions.DocumentPrintOptionsBuilder builder = DocumentPrintOptions.builder()
				.sourceName("AD_Process defaults");

		for (final DocumentPrintOptionDescriptor option : options)
		{
			if (option.getDefaultValue() == null)
			{
				continue;
			}

			builder.option(option.getInternalName(), option.getDefaultValue());
		}

		return builder.build();
	}

}
