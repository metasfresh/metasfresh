package de.metas.dataentry.rest_api;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.layout.DataEntryWindow;
import de.metas.dataentry.rest_api.dto.JsonDataEntry;
import de.metas.dataentry.rest_api.dto.JsonDataEntryField;
import de.metas.dataentry.rest_api.dto.JsonDataEntryLine;
import de.metas.dataentry.rest_api.dto.JsonDataEntryListValue;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySection;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySubTab;
import de.metas.dataentry.rest_api.dto.JsonDataEntryTab;
import de.metas.dataentry.rest_api.dto.JsonFieldType;
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

final class JsonDataEntryFactory
{
	private final DataEntryWindow layout;
	private final DataEntryRecordsMap records;
	private final String adLanguage;

	@Builder(buildMethodName = "buildFactory")
	private JsonDataEntryFactory(
			@NonNull final DataEntryWindow layout,
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
		final ImmutableList.Builder<JsonDataEntryTab> tabs = ImmutableList.builder();
		for (final DataEntryTab layoutTab : layout.getTabs())
		{
			final ImmutableList<JsonDataEntrySubTab> subTabs = toJsonDataEntrySubTabs(layoutTab);

			final JsonDataEntryTab tab = JsonDataEntryTab.builder()
					.id(layoutTab.getId())
					.caption(layoutTab.getCaption().translate(adLanguage))
					.description(layoutTab.getDescription().translate(adLanguage))
					.subTabs(subTabs)
					.build();
			tabs.add(tab);
		}

		return tabs.build();
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

			final ImmutableList<JsonDataEntrySection> sections = toJsonDataEntrySections(layoutSubTab, dataEntryRecord);

			final JsonDataEntrySubTab subTab = JsonDataEntrySubTab.builder()
					.id(layoutSubTab.getId())
					.caption(layoutSubTab.getCaption().translate(adLanguage))
					.description(layoutSubTab.getDescription().translate(adLanguage))
					.sections(sections)
					.build();
			subTabs.add(subTab);
		}
		return subTabs.build();
	}

	@NonNull
	private ImmutableList<JsonDataEntrySection> toJsonDataEntrySections(
			@NonNull final DataEntrySubTab layoutSubTab,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntrySection> sections = ImmutableList.builder();
		for (final DataEntrySection layoutSection : layoutSubTab.getSections())
		{
			final ImmutableList<JsonDataEntryLine> lines = toJsonDataEntryLines(layoutSection, dataEntryRecord);

			final JsonDataEntrySection section = JsonDataEntrySection.builder()
					.id(layoutSection.getId())
					.caption(layoutSection.getCaption().translate(adLanguage))
					.description(layoutSection.getDescription().translate(adLanguage))
					.initiallyClosed(layoutSection.isInitiallyClosed())
					.lines(lines)
					.build();
			sections.add(section);
		}
		return sections.build();
	}

	private ImmutableList<JsonDataEntryLine> toJsonDataEntryLines(
			@NonNull final DataEntrySection layoutSection,
			@NonNull final DataEntryRecord dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntryLine> lines = ImmutableList.builder();
		for (final DataEntryLine layoutLine : layoutSection.getLines())
		{
			final ImmutableList<JsonDataEntryField> fields = toJsonDataEntryFields(layoutLine, dataEntryRecord);

			final JsonDataEntryLine line = JsonDataEntryLine.builder()
					.fields(fields)
					.build();
			lines.add(line);
		}
		return lines.build();
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

			final Object fieldValue = dataEntryRecord.getFieldValue(layoutField.getId()).orElse(null);

			final JsonDataEntryField field = JsonDataEntryField.builder()
					.id(layoutField.getId())
					.caption(layoutField.getCaption().translate(adLanguage))
					.description(layoutField.getDescription().translate(adLanguage))
					.type(JsonFieldType.getBy(layoutField.getType()))
					.mandatory(layoutField.isMandatory())
					.listValues(toJsonDataEntryListValues(layoutField))
					.value(fieldValue)
					.build();
			fields.add(field);
		}
		return fields.build();
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
