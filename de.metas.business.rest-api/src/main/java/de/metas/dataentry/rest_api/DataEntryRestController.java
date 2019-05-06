package de.metas.dataentry.rest_api;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.DataEntryRecordRepository.DataEntryRecordQuery;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryGroup;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubGroup;
import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Group;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubGroup;
import de.metas.dataentry.rest_api.dto.JsonDataEntry;
import de.metas.dataentry.rest_api.dto.JsonDataEntryField;
import de.metas.dataentry.rest_api.dto.JsonDataEntryGroup;
import de.metas.dataentry.rest_api.dto.JsonDataEntryLine;
import de.metas.dataentry.rest_api.dto.JsonDataEntryListValue;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySection;
import de.metas.dataentry.rest_api.dto.JsonDataEntrySubGroup;
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
				.additionalTableNameToResetFor(I_DataEntry_Group.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_SubGroup.Table_Name)
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
		final ImmutableList<DataEntryGroup> layout = layoutRepo.getByWindowId(bpartnerWindowId);
		final ImmutableList<JsonDataEntryGroup> groups = getJsonDataEntryGroups(adLanguage, layout, bpartnerId);

		return JsonDataEntry.builder()
				.groups(groups)
				.build();
	}

	@NonNull private ImmutableList<JsonDataEntryGroup> getJsonDataEntryGroups(final String adLanguage, final List<DataEntryGroup> layout, final BPartnerId bpartnerId)
	{
		final ImmutableList.Builder<JsonDataEntryGroup> groups = ImmutableList.builder();
		for (final DataEntryGroup layoutGroup : layout)
		{
			final ImmutableList<JsonDataEntrySubGroup> subGroups = getJsonDataEntrySubGroups(adLanguage, layoutGroup, bpartnerId);

			final JsonDataEntryGroup group = JsonDataEntryGroup.builder()
					.id(layoutGroup.getId())
					.caption(layoutGroup.getCaption().translate(adLanguage))
					.description(layoutGroup.getDescription().translate(adLanguage))
					.subGroups(subGroups)
					.build();
			groups.add(group);
		}
		return groups.build();
	}

	@NonNull private ImmutableList<JsonDataEntrySubGroup> getJsonDataEntrySubGroups(final String adLanguage, final DataEntryGroup layoutGroup, final BPartnerId bpartnerId)
	{
		final ImmutableList.Builder<JsonDataEntrySubGroup> subGroups = ImmutableList.builder();
		for (final DataEntrySubGroup layoutSubGroup : layoutGroup.getDataEntrySubGroups())
		{
			final ImmutableList<JsonDataEntrySection> sections = getJsonDataEntrySections(adLanguage, layoutSubGroup, bpartnerId);

			final JsonDataEntrySubGroup subGroup = JsonDataEntrySubGroup.builder()
					.id(layoutSubGroup.getId())
					.caption(layoutSubGroup.getCaption().translate(adLanguage))
					.description(layoutSubGroup.getDescription().translate(adLanguage))
					.sections(sections)
					.build();
			subGroups.add(subGroup);
		}
		return subGroups.build();
	}

	@NonNull private ImmutableList<JsonDataEntrySection> getJsonDataEntrySections(final String adLanguage, final DataEntrySubGroup layoutSubGroup, final BPartnerId bpartnerId)
	{
		final TableRecordReference bpartnerRef = TableRecordReference.of(I_C_BPartner.Table_Name, bpartnerId);
		final DataEntryRecord record = getDataEntryRecordForSubGroup(bpartnerRef, layoutSubGroup);

		final ImmutableList.Builder<JsonDataEntrySection> sections = ImmutableList.builder();
		for (final DataEntrySection layoutSection : layoutSubGroup.getDataEntrySections())
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

	private DataEntryRecord getDataEntryRecordForSubGroup(final TableRecordReference bpartnerRef, final DataEntrySubGroup layoutSubGroup)
	{
		final DataEntrySubGroupId subGroupId = layoutSubGroup.getId();

		return recordRepo.getBy(new DataEntryRecordQuery(subGroupId, bpartnerRef))
				.orElseGet(() -> DataEntryRecord.builder()
						.mainRecord(bpartnerRef)
						.dataEntrySubGroupId(subGroupId)
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

