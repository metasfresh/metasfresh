package de.metas.dataentry.layout;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySectionId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.DataEntryTabId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntrySection.DataEntrySectionBuilder;
import de.metas.dataentry.layout.DataEntryTab.DocumentLinkColumnName;
import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
import de.metas.dataentry.model.X_DataEntry_Field;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Tab;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.metas.util.Check.fail;

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

/** Note: has no save method because the records are created directly by the user, via UI. */
@Repository
public class DataEntryLayoutRepository
{
	private static final Logger logger = LogManager.getLogger(DataEntryLayoutRepository.class);

	private CCache<AdWindowId, DataEntryLayout> cache = CCache.<AdWindowId, DataEntryLayout> builder()
			.additionalTableNameToResetFor(I_DataEntry_Tab.Table_Name)
			.additionalTableNameToResetFor(I_DataEntry_SubTab.Table_Name)
			.additionalTableNameToResetFor(I_DataEntry_Section.Table_Name)
			.additionalTableNameToResetFor(I_DataEntry_Line.Table_Name)
			.additionalTableNameToResetFor(I_DataEntry_Field.Table_Name)
			.additionalTableNameToResetFor(I_DataEntry_ListValue.Table_Name)
			.build();

	public DataEntryLayout getByWindowId(@NonNull final AdWindowId adWindowId)
	{
		return cache.getOrLoad(adWindowId, this::retrieveByWindowId);
	}

	private DataEntryLayout retrieveByWindowId(@NonNull final AdWindowId adWindowId)
	{
		final ImmutableList<I_DataEntry_Tab> tabRecords = retrieveTabRecords(adWindowId);

		final I_AD_Tab firstADTab = Services.get(IADWindowDAO.class).retrieveFirstTab(adWindowId);
		if (firstADTab == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Tab_ID@: (@AD_Window_ID@: " + adWindowId.getRepoId() + ")");
		}

		final AdTableId mainTableId = AdTableId.ofRepoId(firstADTab.getAD_Table_ID());
		final String mainTableName = Services.get(IADTableDAO.class).retrieveTableName(mainTableId);

		if (tabRecords.isEmpty())
		{
			return DataEntryLayout.empty(adWindowId, mainTableId);
		}

		final DocumentLinkColumnName parentLinkColumnName = DocumentLinkColumnName.of(InterfaceWrapperHelper.getKeyColumnName(mainTableName));
		final List<DataEntryTab> tabs = new ArrayList<>();
		for (final I_DataEntry_Tab tabRecord : tabRecords)
		{
			final Optional<DataEntryTab> tab = ofRecord(tabRecord, parentLinkColumnName);
			tab.ifPresent(tabs::add);
		}

		if (tabs.isEmpty())
		{
			return DataEntryLayout.empty(adWindowId, mainTableId);
		}

		return DataEntryLayout.builder()
				.windowId(adWindowId)
				.mainTableId(mainTableId)
				.tabs(tabs)
				.build();
	}

	private ImmutableList<I_DataEntry_Tab> retrieveTabRecords(@NonNull final AdWindowId adWindowId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Tab.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Tab.COLUMN_DataEntry_TargetWindow_ID, adWindowId)
				.orderBy(I_DataEntry_Tab.COLUMNNAME_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Tab.class);
	}

	private static Optional<DataEntryTab> ofRecord(
			@NonNull final I_DataEntry_Tab tabRecord,
			@NonNull final DocumentLinkColumnName parentLinkColumnName)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(tabRecord);
		final ITranslatableString captionTrl = modelTranslationMap.getColumnTrl(I_DataEntry_Tab.COLUMNNAME_TabName, tabRecord.getTabName());
		final ITranslatableString descriptionTrl = modelTranslationMap.getColumnTrl(I_DataEntry_Tab.COLUMNNAME_Description, tabRecord.getDescription());

		final DataEntryTab.DataEntryTabBuilder tab = DataEntryTab.builder()
				.id(DataEntryTabId.ofRepoId(tabRecord.getDataEntry_Tab_ID()))
				.documentLinkColumnName(parentLinkColumnName)
				.caption(captionTrl)
				.description(descriptionTrl)
				.availableInApi(tabRecord.isAvailableInAPI())
				.internalName(I_DataEntry_Tab.COLUMNNAME_DataEntry_Tab_ID + "-" + tabRecord.getDataEntry_Tab_ID());

		final ImmutableList<I_DataEntry_SubTab> subTabRecords = retrieveSubTabRecords(tabRecord);
		for (final I_DataEntry_SubTab subTabRecord : subTabRecords)
		{
			final Optional<DataEntrySubTab> subTab = ofRecord(subTabRecord);
			subTab.ifPresent(tab::subTab);
		}

		final DataEntryTab result = tab.build();
		return result.getSubTabs().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_SubTab> retrieveSubTabRecords(@NonNull final I_DataEntry_Tab tabRecord)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_SubTab.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_SubTab.COLUMN_DataEntry_Tab_ID, tabRecord.getDataEntry_Tab_ID())
				.orderBy(I_DataEntry_SubTab.COLUMNNAME_SeqNo)
				.create()
				.listImmutable(I_DataEntry_SubTab.class);
	}

	private static Optional<DataEntrySubTab> ofRecord(
			@NonNull final I_DataEntry_SubTab subTabRecord)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(subTabRecord);
		final ITranslatableString captionTrl = modelTranslationMap.getColumnTrl(I_DataEntry_SubTab.COLUMNNAME_TabName, subTabRecord.getTabName());
		final ITranslatableString descriptionTrl = modelTranslationMap.getColumnTrl(I_DataEntry_SubTab.COLUMNNAME_Description, subTabRecord.getDescription());

		final DataEntrySubTab.DataEntrySubTabBuilder subTab = DataEntrySubTab.builder()
				.id(DataEntrySubTabId.ofRepoId(subTabRecord.getDataEntry_SubTab_ID()))
				.caption(captionTrl)
				.description(descriptionTrl)
				.internalName(I_DataEntry_SubTab.COLUMNNAME_DataEntry_SubTab_ID + "-" + subTabRecord.getDataEntry_SubTab_ID());

		final ImmutableList<I_DataEntry_Section> sectionRecords = retrieveSectionRecords(subTabRecord);
		for (final I_DataEntry_Section sectionRecord : sectionRecords)
		{
			final Optional<DataEntrySection> dataEntrySection = ofRecord(sectionRecord);
			dataEntrySection.ifPresent(subTab::section);
		}

		final DataEntrySubTab result = subTab.build();
		return result.getSections().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_Section> retrieveSectionRecords(@NonNull final I_DataEntry_SubTab subTabRecord)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Section.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Section.COLUMN_DataEntry_SubTab_ID, subTabRecord.getDataEntry_SubTab_ID())
				.orderBy(I_DataEntry_Section.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Section.class);
	}

	private static Optional<DataEntrySection> ofRecord(@NonNull final I_DataEntry_Section sectionRecord)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(sectionRecord);

		final ITranslatableString captionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Section.COLUMNNAME_SectionName, sectionRecord.getSectionName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Section.COLUMNNAME_Description, sectionRecord.getDescription());

		final DataEntrySectionBuilder section = DataEntrySection.builder()
				.id(DataEntrySectionId.ofRepoId(sectionRecord.getDataEntry_Section_ID()))
				.caption(captionTrl)
				.description(descriptionTrl)
				.initiallyClosed(sectionRecord.isInitiallyClosed())
				.availableInApi(sectionRecord.isAvailableInAPI())
				.internalName(I_DataEntry_Section.COLUMNNAME_DataEntry_Section_ID + "-" + sectionRecord.getDataEntry_Section_ID());

		final ImmutableList<I_DataEntry_Line> lineRecords = retrieveLineRecords(sectionRecord);
		for (final I_DataEntry_Line lineRecord : lineRecords)
		{
			final Optional<DataEntryLine> line = ofRecord(lineRecord);
			line.ifPresent(section::line);
		}

		final DataEntrySection result = section.build();
		return result.getLines().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_Line> retrieveLineRecords(@NonNull final I_DataEntry_Section sectionRecord)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Line.COLUMN_DataEntry_Section_ID, sectionRecord.getDataEntry_Section_ID())
				.orderBy(I_DataEntry_Line.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Line.class);
	}

	private static Optional<DataEntryLine> ofRecord(@NonNull final I_DataEntry_Line lineRecord)
	{
		final ImmutableList<I_DataEntry_Field> fieldRecords = retrieveFieldRecords(lineRecord);

		final DataEntryLine.DataEntryLineBuilder line = DataEntryLine.builder()
				.seqNo(lineRecord.getSeqNo());

		for (final I_DataEntry_Field fieldRecord : fieldRecords)
		{
			final Optional<DataEntryField> field = ofRecord(fieldRecord);
			field.ifPresent(line::field);
		}
		final DataEntryLine result = line.build();
		return result.getFields().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_Field> retrieveFieldRecords(@NonNull final I_DataEntry_Line lineRecord)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Field.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Field.COLUMN_DataEntry_Line_ID, lineRecord.getDataEntry_Line_ID())
				.orderBy(I_DataEntry_Field.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Field.class);
	}

	private static Optional<DataEntryField> ofRecord(final I_DataEntry_Field fieldRecord)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(fieldRecord);

		final ITranslatableString nameTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Field.COLUMNNAME_Name, fieldRecord.getName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Field.COLUMNNAME_Description, fieldRecord.getDescription());

		final ImmutableList.Builder<DataEntryListValue> listValues = ImmutableList.builder();
		final FieldType type;

		final String recordType = fieldRecord.getDataEntry_RecordType();
		if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_List.equals(recordType))
		{
			type = FieldType.LIST;

			final ImmutableList<I_DataEntry_ListValue> listValueRecords = retrieveListValueRecords(fieldRecord);
			if (listValueRecords.isEmpty())
			{
				logger.warn("Skipping DataEntry_Field_ID={} which has no DataEntry_ListValue records", fieldRecord.getDataEntry_Field_ID());
				return Optional.empty();
			}

			for (final I_DataEntry_ListValue listValueRecord : listValueRecords)
			{
				listValues.add(ofRecord(listValueRecord));
			}
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_Date.equals(recordType))
		{
			type = FieldType.DATE;
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_Number.equals(recordType))
		{
			type = FieldType.NUMBER;
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_Text.equals(recordType))
		{
			type = FieldType.TEXT;
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_LongText.equals(recordType))
		{
			type = FieldType.LONG_TEXT;
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_YesNo.equals(recordType))
		{
			type = FieldType.YESNO;
		}
		else
		{
			fail("Unexpected type={}; DataEntry_Field={}", recordType, fieldRecord);
			type = null;
		}
		final DataEntryField field = DataEntryField
				.builder()
				.id(DataEntryFieldId.ofRepoId(fieldRecord.getDataEntry_Field_ID()))
				.caption(nameTrl)
				.description(descriptionTrl)
				.mandatory(fieldRecord.isMandatory())
				.type(type)
				.availableInApi(fieldRecord.isAvailableInAPI())
				.listValues(listValues.build())
				.build();
		return Optional.of(field);
	}

	private static ImmutableList<I_DataEntry_ListValue> retrieveListValueRecords(@NonNull final I_DataEntry_Field fieldRecord)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_ListValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_ListValue.COLUMN_DataEntry_Field_ID, fieldRecord.getDataEntry_Field_ID())
				.orderBy(I_DataEntry_ListValue.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_ListValue.class);
	}

	private static DataEntryListValue ofRecord(@NonNull final I_DataEntry_ListValue listValueRecord)
	{
		final DataEntryListValueId id = DataEntryListValueId.ofRepoId(listValueRecord.getDataEntry_ListValue_ID());
		
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(listValueRecord);
		final ITranslatableString nameTrl = trls.getColumnTrl(I_DataEntry_ListValue.COLUMNNAME_Name, listValueRecord.getName());
		final ITranslatableString descriptionTrl = trls.getColumnTrl(I_DataEntry_ListValue.COLUMNNAME_Description, listValueRecord.getDescription());

		return DataEntryListValue.builder()
				.id(id)
				.name(nameTrl)
				.description(descriptionTrl)
				.build();
	}
}
