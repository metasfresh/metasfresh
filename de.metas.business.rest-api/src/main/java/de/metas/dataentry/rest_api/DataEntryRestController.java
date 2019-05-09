package de.metas.dataentry.rest_api;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.rest_api.dto.JsonDataEntry;
import de.metas.dataentry.rest_api.dto.JsonDataEntryField;
import de.metas.dataentry.rest_api.dto.JsonDataEntryLine;
import de.metas.dataentry.rest_api.dto.JsonDataEntryListValue;
import de.metas.dataentry.rest_api.dto.JsonDataEntryResponse;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySection;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySubTab;
import de.metas.dataentry.rest_api.dto.JsonDataEntryTab;
import de.metas.dataentry.rest_api.dto.JsonFieldType;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Profile(Profiles.PROFILE_App)
@RestController
@RequestMapping(DataEntryRestController.ENDPOINT)
public class DataEntryRestController
{
	@SuppressWarnings("WeakerAccess")
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/dataentry";

	protected final transient Logger log = LogManager.getLogger(getClass());

	private final DataEntryLayoutRepository layoutRepo;
	private final DataEntryRecordCache dataRecords;

	DataEntryRestController(
			@NonNull final DataEntryLayoutRepository layoutRepo,
			@NonNull final DataEntryRecordRepository recordsRepo,
			@Value("${de.metas.dataentry.rest_api.DataEntryRestController.cacheCapacity:200}") final int cacheCapacity)
	{
		this.layoutRepo = layoutRepo;
		this.dataRecords = new DataEntryRecordCache(recordsRepo);
	}

	@GetMapping("/byID/{windowId}/{recordId}")
	public ResponseEntity<JsonDataEntryResponse> getByRecordId(
			@PathVariable("windowId") final int windowId,
			@PathVariable("recordId") final int recordId)
	{
		final Stopwatch w = Stopwatch.createStarted();
		final ResponseEntity<JsonDataEntryResponse> jsonDataEntry = getJsonDataEntry(AdWindowId.ofRepoId(windowId), recordId);
		w.stop();
		log.debug("getJsonDataEntry by {windowId '{}' and recordId '{}'} duration: {}", windowId, recordId, w);
		return jsonDataEntry;
	}

	private ResponseEntity<JsonDataEntryResponse> getJsonDataEntry(final AdWindowId windowId, final int recordId)
	{
		final String adLanguage = Env.getAD_Language();
		final JsonDataEntry jsonDataEntry = getJsonDataEntry(windowId, recordId, adLanguage);

		if (jsonDataEntry.isEmpty())
		{
			final JsonDataEntryResponse dataEntryResponse = JsonDataEntryResponse.builder()
					.error("No dataentry for windowId: " + windowId)
					.status(HttpStatus.NOT_FOUND.value())
					.build();
			return new ResponseEntity<>(dataEntryResponse, HttpStatus.NOT_FOUND);
		}

		final JsonDataEntryResponse dataEntryResponse = JsonDataEntryResponse.builder()
				.result(jsonDataEntry)
				.status(HttpStatus.OK.value())
				.build();
		return new ResponseEntity<>(dataEntryResponse, HttpStatus.OK);
	}

	@NonNull
	private JsonDataEntry getJsonDataEntry(
			@NonNull final AdWindowId windowId,
			final int recordId,
			@NonNull final String adLanguage)
	{
		final ImmutableList<DataEntryTab> layout = layoutRepo.getByWindowId(windowId);
		final DataEntryRecordsMap records = dataRecords.get(recordId, DataEntryTab.getSubTabIds(layout));

		final ImmutableList<JsonDataEntryTab> tabs = getJsonDataEntryTabs(adLanguage, layout, records);

		return JsonDataEntry.builder()
				.tabs(tabs)
				.build();
	}

	@NonNull
	private static ImmutableList<JsonDataEntryTab> getJsonDataEntryTabs(
			final String adLanguage,
			@NonNull final List<DataEntryTab> layout,
			@NonNull final DataEntryRecordsMap records)
	{
		final ImmutableList.Builder<JsonDataEntryTab> tabs = ImmutableList.builder();
		for (final DataEntryTab layoutTab : layout)
		{
			final ImmutableList<JsonDataEntrySubTab> subTabs = getJsonDataEntrySubTabs(adLanguage, layoutTab, records);

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
	private static ImmutableList<JsonDataEntrySubTab> getJsonDataEntrySubTabs(
			final String adLanguage,
			@NonNull final DataEntryTab layoutTab,
			@NonNull final DataEntryRecordsMap records)
	{
		final ImmutableList.Builder<JsonDataEntrySubTab> subTabs = ImmutableList.builder();
		for (final DataEntrySubTab layoutSubTab : layoutTab.getDataEntrySubTabs())
		{
			final Optional<DataEntryRecord> dataEntryRecord = records.getBySubTabId(layoutSubTab.getId());
			final ImmutableList<JsonDataEntrySection> sections = getJsonDataEntrySections(adLanguage, layoutSubTab, dataEntryRecord);

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

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@NonNull
	private static ImmutableList<JsonDataEntrySection> getJsonDataEntrySections(final String adLanguage, @NonNull final DataEntrySubTab layoutSubTab, final Optional<DataEntryRecord> dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntrySection> sections = ImmutableList.builder();
		for (final DataEntrySection layoutSection : layoutSubTab.getDataEntrySections())
		{
			final ImmutableList<JsonDataEntryLine> lines = getJsonDataEntryLines(adLanguage, layoutSection, dataEntryRecord);

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

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static ImmutableList<JsonDataEntryLine> getJsonDataEntryLines(final String adLanguage, @NonNull final DataEntrySection layoutSection, final Optional<DataEntryRecord> dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntryLine> lines = ImmutableList.builder();
		for (final DataEntryLine layoutLine : layoutSection.getDataEntryLines())
		{
			final ImmutableList<JsonDataEntryField> fields = getJsonDataEntryFields(adLanguage, layoutLine, dataEntryRecord);

			final JsonDataEntryLine line = JsonDataEntryLine.builder()
					.fields(fields)
					.build();
			lines.add(line);
		}
		return lines.build();
	}

	@NonNull
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static ImmutableList<JsonDataEntryField> getJsonDataEntryFields(final String adLanguage, @NonNull final DataEntryLine layoutLine, final Optional<DataEntryRecord> dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntryField> fields = ImmutableList.builder();
		for (final DataEntryField layoutField : layoutLine.getDataEntryFields())
		{
			// some fields can be excluded from the response, according to this flag
			if (!layoutField.isAvailableInApi())
			{
				continue;
			}

			Object dataEntryRecordValue = null;
			if (dataEntryRecord.isPresent())
			{
				dataEntryRecordValue = dataEntryRecord.get().getFieldValue(layoutField.getId()).orElse(null);
			}

			final JsonDataEntryField field = JsonDataEntryField.builder()
					.id(layoutField.getId())
					.caption(layoutField.getCaption().translate(adLanguage))
					.description(layoutField.getDescription().translate(adLanguage))
					.type(JsonFieldType.getBy(layoutField.getType()))
					.mandatory(layoutField.isMandatory())
					.listValues(getDataEntryFieldListValues(layoutField, adLanguage))
					.value(dataEntryRecordValue)
					.build();
			fields.add(field);
		}
		return fields.build();
	}

	@NonNull
	private static ImmutableList<JsonDataEntryListValue> getDataEntryFieldListValues(@NonNull final DataEntryField layoutField, final String adLanguage)
	{
		return layoutField.getListValues().stream()
				.map(it -> JsonDataEntryListValue.builder()
						.id(it.getId())
						.name(it.getName().translate(adLanguage))
						.description(it.getDescription().translate(adLanguage))
						.build())
				.collect(GuavaCollectors.toImmutableList());
	}
}
