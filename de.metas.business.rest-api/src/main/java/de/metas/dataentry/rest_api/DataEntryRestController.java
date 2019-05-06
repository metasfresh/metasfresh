package de.metas.dataentry.rest_api;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.DataEntryRecordRepository.DataEntryRecordQuery;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.model.I_DataEntry_Field;

import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.dataentry.model.I_DataEntry_Section;

import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
import de.metas.dataentry.rest_api.dto.JsonDataEntry;
import de.metas.dataentry.rest_api.dto.JsonDataEntryField;
import de.metas.dataentry.rest_api.dto.JsonDataEntryTab;
import de.metas.dataentry.rest_api.dto.JsonDataEntryLine;
import de.metas.dataentry.rest_api.dto.JsonDataEntryListValue;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySection;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySubTab;
import de.metas.dataentry.rest_api.dto.JsonFieldType;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
	@SuppressWarnings("WeakerAccess") public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/dataentry";

	protected final transient Logger log = LogManager.getLogger(getClass());

	private static final AdWindowId bpartnerWindowId = AdWindowId.ofRepoId(123); // FIXME: HARDCODED for now

	private final DataEntryLayoutRepository layoutRepo;
	private final DataEntryRecordRepository recordRepo;

	private final CCache<BPartnerId, JsonDataEntry> cache;

	DataEntryRestController(
			@NonNull final DataEntryLayoutRepository layoutRepo,
			@NonNull final DataEntryRecordRepository recordRepo,
			@NonNull @Value("${de.metas.dataentry.rest_api.DataEntryRestController.cacheCapacity:200}") final Integer cacheCapacity)
	{
		this.layoutRepo = layoutRepo;
		this.recordRepo = recordRepo;
		cache = CCache.<BPartnerId, JsonDataEntry>builder()
				.cacheMapType(CCache.CacheMapType.LRU)
				.tableName(I_C_BPartner.Table_Name)    // <- apparently this is needed for cache invalidation to work. why? i have no idea! (and i think it's a bug since i'm not caching a BPartner, but rather an _arbitrary_ value
				//				.tableName(I_DataEntry_Record.Table_Name)    // <- contrary to above, this DOES NOT WORK!!!!!
				.additionalTableNameToResetFor(I_DataEntry_Tab.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_SubTab.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Section.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Line.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Field.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Record.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_ListValue.Table_Name)
				.initialCapacity(cacheCapacity)
				.build();
	}

	@GetMapping("/byBPartnerValue/{bpartnerValue}")
	public JsonDataEntry getByBPartnerValue(@PathVariable("bpartnerValue") final String bpartnerValue)
	{
		final Stopwatch w = Stopwatch.createStarted();
		final JsonDataEntry jsonDataEntry = getJsonDataEntry(bpartnerValue);
		w.stop();
		log.info("getByBPartnerValue for bpartner '{}' duration: {}", bpartnerValue, w.toString());
		return jsonDataEntry;
	}

	private JsonDataEntry getJsonDataEntry(final String bpartnerValue)
	{
		final BPartnerId bpartnerId = Services.get(IBPartnerDAO.class).getBPartnerIdByValue(bpartnerValue);

		return cache.getOrLoad(bpartnerId, () -> getJsonDataEntry(bpartnerId));
	}

	private JsonDataEntry getJsonDataEntry(final BPartnerId bpartnerId)
	{
		final String adLanguage = Env.getAD_Language();
		final ImmutableList<DataEntryTab> layout = layoutRepo.getByWindowId(bpartnerWindowId);
		final ImmutableList<JsonDataEntryTab> tabs = getJsonDataEntryTabs(adLanguage, layout, bpartnerId);

		return JsonDataEntry.builder()
				.tabs(tabs)
				.build();
	}

	@NonNull private ImmutableList<JsonDataEntryTab> getJsonDataEntryTabs(final String adLanguage, final List<DataEntryTab> layout, final BPartnerId bpartnerId)
	{
		final ImmutableList.Builder<JsonDataEntryTab> tabs = ImmutableList.builder();
		for (final DataEntryTab layoutTab : layout)
		{
			final ImmutableList<JsonDataEntrySubTab> subTabs = getJsonDataEntrySubTabs(adLanguage, layoutTab, bpartnerId);

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

	@NonNull private ImmutableList<JsonDataEntrySubTab> getJsonDataEntrySubTabs(final String adLanguage, final DataEntryTab layoutTab, final BPartnerId bpartnerId)
	{
		final ImmutableList.Builder<JsonDataEntrySubTab> subTabs = ImmutableList.builder();
		for (final DataEntrySubTab layoutSubTab : layoutTab.getDataEntrySubTabs())
		{
			final ImmutableList<JsonDataEntrySection> sections = getJsonDataEntrySections(adLanguage, layoutSubTab, bpartnerId);

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

	@NonNull private ImmutableList<JsonDataEntrySection> getJsonDataEntrySections(final String adLanguage, final DataEntrySubTab layoutSubTab, final BPartnerId bpartnerId)
	{
		final TableRecordReference bpartnerRef = TableRecordReference.of(I_C_BPartner.Table_Name, bpartnerId);
		final DataEntryRecord record = getDataEntryRecordForSubTab(bpartnerRef, layoutSubTab);

		final ImmutableList.Builder<JsonDataEntrySection> sections = ImmutableList.builder();
		for (final DataEntrySection layoutSection : layoutSubTab.getDataEntrySections())
		{
			final ImmutableList<JsonDataEntryLine> lines = getJsonDataEntryLines(adLanguage, record, layoutSection);

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

	private ImmutableList<JsonDataEntryLine> getJsonDataEntryLines(final String adLanguage, final DataEntryRecord record, final DataEntrySection layoutSection)
	{
		final ImmutableList.Builder<JsonDataEntryLine> lines = ImmutableList.builder();
		for (final DataEntryLine layoutLine : layoutSection.getDataEntryLines())
		{
			final ImmutableList<JsonDataEntryField> fields = getJsonDataEntryFields(adLanguage, record, layoutLine);

			final JsonDataEntryLine line = JsonDataEntryLine.builder()
					.fields(fields)
					.build();
			lines.add(line);
		}
		return lines.build();
	}

	@NonNull private ImmutableList<JsonDataEntryField> getJsonDataEntryFields(final String adLanguage, final DataEntryRecord record, final DataEntryLine layoutLine)
	{
		final ImmutableList.Builder<JsonDataEntryField> fields = ImmutableList.builder();
		for (final DataEntryField layoutField : layoutLine.getDataEntryFields())
		{
			final JsonDataEntryField field = JsonDataEntryField.builder()
					.id(layoutField.getId())
					.caption(layoutField.getCaption().translate(adLanguage))
					.description(layoutField.getDescription().translate(adLanguage))
					.type(JsonFieldType.getBy(layoutField.getType()))
					.mandatory(layoutField.isMandatory())
					.listValues(getDataEntryFieldListValues(layoutField, adLanguage))
					.value(record.getFieldValue(layoutField.getId()).orElse(null))
					.build();
			fields.add(field);
		}
		return fields.build();
	}

	private DataEntryRecord getDataEntryRecordForSubTab(final TableRecordReference bpartnerRef, final DataEntrySubTab layoutSubTab)
	{
		final DataEntrySubTabId subTabId = layoutSubTab.getId();

		return recordRepo.getBy(new DataEntryRecordQuery(subTabId, bpartnerRef))
				.orElseGet(() -> DataEntryRecord.builder()
						.mainRecord(bpartnerRef)
						.dataEntrySubTabId(subTabId)
						.build());
	}

	private ImmutableList<JsonDataEntryListValue> getDataEntryFieldListValues(final DataEntryField layoutField, final String adLanguage)
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

