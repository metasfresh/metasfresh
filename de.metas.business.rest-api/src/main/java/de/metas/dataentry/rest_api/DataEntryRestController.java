package de.metas.dataentry.rest_api;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.cache.CCache;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
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

import java.util.List;
import java.util.Optional;

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
	private final DataEntryRecordRepository recordRepo;

	private final CCache<RecordIdLanguagePair, JsonDataEntry> cache;

	DataEntryRestController(
			@NonNull final DataEntryLayoutRepository layoutRepo,
			@NonNull final DataEntryRecordRepository recordRepo,
			@NonNull @Value("${de.metas.dataentry.rest_api.DataEntryRestController.cacheCapacity:200}") final Integer cacheCapacity)
	{
		this.layoutRepo = layoutRepo;
		this.recordRepo = recordRepo;
		cache = CCache.<RecordIdLanguagePair, JsonDataEntry>builder()
				.initialCapacity(cacheCapacity)
				.additionalTableNameToResetFor(I_DataEntry_Tab.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_SubTab.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Section.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Line.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Field.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Record.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_ListValue.Table_Name)
				.build();
	}

	@GetMapping("/byID/{windowId}/{recordId}")
	public ResponseEntity<JsonDataEntryResponse> getByRecordId(
			@PathVariable("windowId") final int windowId,
			@PathVariable("recordId") final int recordId)
	{
		final Stopwatch w = Stopwatch.createStarted();
		final ResponseEntity<JsonDataEntryResponse> jsonDataEntry = getJsonDataEntry(recordId, windowId);
		w.stop();
		log.info("getJsonDataEntry by {windowId '{}' and recordId '{}'} duration: {}", windowId, recordId, w.toString());
		return jsonDataEntry;
	}

	private ResponseEntity<JsonDataEntryResponse> getJsonDataEntry(final int recordId, final int windowId)
	{
		final String adLanguage = Env.getAD_Language();

		final RecordIdLanguagePair key = RecordIdLanguagePair.of(recordId, adLanguage);
		final JsonDataEntry jsonDataEntry = cache.getOrLoad(key, () -> getJsonDataEntry(adLanguage, AdWindowId.ofRepoId(windowId), recordId));

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
	private JsonDataEntry getJsonDataEntry(final String adLanguage, final AdWindowId windowId, final int recordId)
	{
		final ImmutableList<DataEntryTab> layout = layoutRepo.getByWindowId(windowId);
		final ImmutableList<JsonDataEntryTab> tabs = getJsonDataEntryTabs(adLanguage, layout, recordId);

		return JsonDataEntry.builder()
				.tabs(tabs)
				.build();
	}

	@NonNull
	private ImmutableList<JsonDataEntryTab> getJsonDataEntryTabs(final String adLanguage, @NonNull final List<DataEntryTab> layout, final int recordId)
	{
		final ImmutableList.Builder<JsonDataEntryTab> tabs = ImmutableList.builder();
		for (final DataEntryTab layoutTab : layout)
		{
			final ImmutableList<JsonDataEntrySubTab> subTabs = getJsonDataEntrySubTabs(adLanguage, layoutTab, recordId);

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
	private ImmutableList<JsonDataEntrySubTab> getJsonDataEntrySubTabs(final String adLanguage, @NonNull final DataEntryTab layoutTab, final int recordId)
	{
		final ImmutableList.Builder<JsonDataEntrySubTab> subTabs = ImmutableList.builder();
		for (final DataEntrySubTab layoutSubTab : layoutTab.getDataEntrySubTabs())
		{
			final Optional<DataEntryRecord> dataEntryRecord = getDataEntryRecordForSubtabAndRecord(layoutSubTab.getId(), recordId);
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
	private ImmutableList<JsonDataEntrySection> getJsonDataEntrySections(final String adLanguage, @NonNull final DataEntrySubTab layoutSubTab, final Optional<DataEntryRecord> dataEntryRecord)
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
	private ImmutableList<JsonDataEntryLine> getJsonDataEntryLines(final String adLanguage, @NonNull final DataEntrySection layoutSection, final Optional<DataEntryRecord> dataEntryRecord)
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
	private ImmutableList<JsonDataEntryField> getJsonDataEntryFields(final String adLanguage, @NonNull final DataEntryLine layoutLine, final Optional<DataEntryRecord> dataEntryRecord)
	{
		final ImmutableList.Builder<JsonDataEntryField> fields = ImmutableList.builder();
		for (final DataEntryField layoutField : layoutLine.getDataEntryFields())
		{
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
	private Optional<DataEntryRecord> getDataEntryRecordForSubtabAndRecord(@NonNull final DataEntrySubTabId subTabId, final int recordId)
	{
		return recordRepo.getBy(DataEntryRecordRepository.DataEntryRecordQuery.of(subTabId, recordId));
	}

	@NonNull
	private ImmutableList<JsonDataEntryListValue> getDataEntryFieldListValues(@NonNull final DataEntryField layoutField, final String adLanguage)
	{
		return layoutField.getListValues().stream()
				.map(it -> JsonDataEntryListValue.builder()
						.id(it.getId())
						.name(it.getName().translate(adLanguage))
						.description(it.getDescription().translate(adLanguage))
						.build())
				.collect(GuavaCollectors.toImmutableList());
	}

	@lombok.Value(staticConstructor = "of")
	private static class RecordIdLanguagePair
	{
		int recordId;
		@NonNull String adLanguage;
	}
}

