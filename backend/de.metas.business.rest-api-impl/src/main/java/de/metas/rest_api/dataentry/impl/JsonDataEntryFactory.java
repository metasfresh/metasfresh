package de.metas.rest_api.dataentry.impl;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLayout;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntry;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntryField;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntryLine;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntryListValue;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntrySection;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntrySubTab;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntryTab;
import de.metas.rest_api.dataentry.impl.dto.JsonFieldType;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api
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

/**
 * Produces {@link JsonDataEntry} by merging {@link DataEntryLayout} with {@link DataEntryRecordsMap}.
 */
final class JsonDataEntryFactory
{
	private final DataEntryLayout layout;
	private final DataEntryRecordsMap records;
	private final String adLanguage;

	@Builder(buildMethodName = "buildFactory")
	private JsonDataEntryFactory(
			@NonNull final DataEntryLayout layout,
			@NonNull final DataEntryRecordsMap records,
			@NonNull final String adLanguage)
	{
		this.layout = layout;
		this.records = records;
		this.adLanguage = adLanguage;
	}

	public static class JsonDataEntryFactoryBuilder
	{
		public JsonDataEntry build()
		{
			return buildFactory().create();
		}
	}

	private JsonDataEntry create()
	{
		return JsonDataEntry.builder()
				.tabs(toJsonDataEntryTabs())
				.build();
	}

	@NonNull
	private ImmutableList<JsonDataEntryTab> toJsonDataEntryTabs()
	{
		return layout.getTabs()
				.stream()
				.filter(DataEntryTab::isAvailableInApi)
				.map(this::toJsonDataEntryTab)
				.filter(tab -> !tab.getSubTabs().isEmpty())
				.collect(ImmutableList.toImmutableList());
	}

	private JsonDataEntryTab toJsonDataEntryTab(final DataEntryTab layoutTab)
	{
		final ImmutableList<JsonDataEntrySubTab> subTabs = toJsonDataEntrySubTabs(layoutTab);

		return JsonDataEntryTab.builder()
				.id(layoutTab.getId())
				.caption(layoutTab.getCaption().translate(adLanguage))
				.description(layoutTab.getDescription().translate(adLanguage))
				.subTabs(subTabs)
				.build();
	}

	@NonNull
	private ImmutableList<JsonDataEntrySubTab> toJsonDataEntrySubTabs(@NonNull final DataEntryTab layoutTab)
	{
		final ImmutableList.Builder<JsonDataEntrySubTab> subTabs = ImmutableList.builder();
		for (final DataEntrySubTab layoutSubTab : layoutTab.getSubTabs())
		{
			final DataEntryRecord dataEntryRecord = records.getBySubTabId(layoutSubTab.getId()).orElse(null);
			if (dataEntryRecord == null)
			{
				continue;
			}

			final JsonDataEntrySubTab subTab = toJsonDataEntrySubTab(layoutSubTab, dataEntryRecord);
			subTabs.add(subTab);
		}

		return subTabs.build();
	}

	private JsonDataEntrySubTab toJsonDataEntrySubTab(
			@NonNull final DataEntrySubTab layoutSubTab,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList<JsonDataEntrySection> sections = toJsonDataEntrySections(layoutSubTab, dataEntryRecord);

		return JsonDataEntrySubTab.builder()
				.id(layoutSubTab.getId())
				.caption(layoutSubTab.getCaption().translate(adLanguage))
				.description(layoutSubTab.getDescription().translate(adLanguage))
				.sections(sections)
				.build();
	}

	@NonNull
	private ImmutableList<JsonDataEntrySection> toJsonDataEntrySections(
			@NonNull final DataEntrySubTab layoutSubTab,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntrySection> sections = ImmutableList.builder();
		for (final DataEntrySection layoutSection : layoutSubTab.getSections())
		{
			if (!layoutSection.isAvailableInApi())
			{
				continue;
			}
			final JsonDataEntrySection section = toJsonDataEntrySection(layoutSection, dataEntryRecord);
			sections.add(section);
		}
		return sections.build();
	}

	@NonNull
	private JsonDataEntrySection toJsonDataEntrySection(
			@NonNull final DataEntrySection layoutSection,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList<JsonDataEntryLine> lines = toJsonDataEntryLines(layoutSection, dataEntryRecord);

		return JsonDataEntrySection.builder()
				.id(layoutSection.getId())
				.caption(layoutSection.getCaption().translate(adLanguage))
				.description(layoutSection.getDescription().translate(adLanguage))
				.initiallyClosed(layoutSection.isInitiallyClosed())
				.lines(lines)
				.build();
	}

	private ImmutableList<JsonDataEntryLine> toJsonDataEntryLines(
			@NonNull final DataEntrySection layoutSection,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntryLine> lines = ImmutableList.builder();
		for (final DataEntryLine layoutLine : layoutSection.getLines())
		{
			final JsonDataEntryLine line = toJsonDataEntryLine(layoutLine, dataEntryRecord);
			lines.add(line);
		}
		return lines.build();
	}

	private JsonDataEntryLine toJsonDataEntryLine(
			@NonNull final DataEntryLine layoutLine,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList<JsonDataEntryField> fields = toJsonDataEntryFields(layoutLine, dataEntryRecord);

		return JsonDataEntryLine.builder()
				.fields(fields)
				.build();
	}

	@NonNull
	private ImmutableList<JsonDataEntryField> toJsonDataEntryFields(
			@NonNull final DataEntryLine layoutLine,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntryField> fields = ImmutableList.builder();
		for (final DataEntryField layoutField : layoutLine.getFields())
		{
			// some fields can be excluded from the response, according to this flag
			if (!layoutField.isAvailableInApi())
			{
				continue;
			}

			final JsonDataEntryField field = toJsonDataEntryField(layoutField, dataEntryRecord);
			fields.add(field);
		}

		return fields.build();
	}

	private JsonDataEntryField toJsonDataEntryField(
			@NonNull final DataEntryField layoutField,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final Object fieldValue = dataEntryRecord.getFieldValue(layoutField.getId()).orElse(null);

		return JsonDataEntryField.builder()
				.id(layoutField.getId())
				.caption(layoutField.getCaption().translate(adLanguage))
				.description(layoutField.getDescription().translate(adLanguage))
				.type(JsonFieldType.getBy(layoutField.getType()))
				.mandatory(layoutField.isMandatory())
				.listValues(toJsonDataEntryListValues(layoutField))
				.value(fieldValue)
				.build();
	}

	@NonNull
	private ImmutableList<JsonDataEntryListValue> toJsonDataEntryListValues(@NonNull final DataEntryField layoutField)
	{
		return layoutField.getListValues()
				.stream()
				.map(this::toJsonDataEntryListValue)
				.collect(ImmutableList.toImmutableList());
	}

	private JsonDataEntryListValue toJsonDataEntryListValue(@NonNull final DataEntryListValue listValue)
	{
		return JsonDataEntryListValue.builder()
				.id(listValue.getId())
				.name(listValue.getName().translate(adLanguage))
				.description(listValue.getDescription().translate(adLanguage))
				.build();
	}

}
