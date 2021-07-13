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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DocumentPrintOptionsIncludingDescriptors
{
	DocumentPrintOptionDescriptorsList descriptors;
	DocumentPrintOptions values;

	@Builder
	private DocumentPrintOptionsIncludingDescriptors(
			@NonNull final DocumentPrintOptionDescriptorsList descriptors,
			@Nullable final DocumentPrintOptions values)
	{
		this.descriptors = descriptors;
		this.values = values != null ? values : DocumentPrintOptions.NONE;
	}

	public ImmutableList<DocumentPrintOptionDescriptor> getOptionDescriptors()
	{
		return descriptors.getOptions();
	}

	public DocumentPrintOptionValue getOptionValue(@NonNull final String name)
	{
		return values.getOption(name);
	}

}
