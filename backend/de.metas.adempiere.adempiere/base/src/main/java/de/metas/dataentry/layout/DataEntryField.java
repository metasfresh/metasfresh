package de.metas.dataentry.layout;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.FieldType;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
public class DataEntryField
{
	DataEntryFieldId id;

	ITranslatableString caption;
	ITranslatableString description;

	FieldType type;

	boolean mandatory;

	boolean availableInApi;

	/** empty, unless type=list */
	ImmutableList<DataEntryListValue> listValues;

	@Builder
	private DataEntryField(
			@NonNull final DataEntryFieldId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ITranslatableString description,
			@NonNull final FieldType type,
			final boolean mandatory,
			final boolean availableInApi,
			@Singular final List<DataEntryListValue> listValues)
	{
		this.id = id;
		this.caption = caption;
		this.description = description;
		this.type = type;
		this.mandatory = mandatory;
		this.availableInApi = availableInApi;
		this.listValues = ImmutableList.copyOf(listValues);
	}

	public Optional<DataEntryListValue> getFirstListValueMatching(@NonNull final Predicate<DataEntryListValue> predicate)
	{
		return listValues.stream()
				.filter(predicate)
				.findFirst();
	}
}
