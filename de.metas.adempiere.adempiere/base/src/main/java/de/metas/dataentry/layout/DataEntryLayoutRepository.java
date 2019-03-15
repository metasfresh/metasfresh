package de.metas.dataentry.layout;

import static de.metas.util.Check.fail;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryGroupId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySectionId;
import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryGroup.DataEntryGroupBuilder;
import de.metas.dataentry.layout.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.layout.DataEntryLine.DataEntryLineBuilder;
import de.metas.dataentry.layout.DataEntrySection.DataEntrySectionBuilder;
import de.metas.dataentry.layout.DataEntrySubGroup.DataEntrySubGroupBuilder;
import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Group;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubGroup;
import de.metas.dataentry.model.X_DataEntry_Field;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

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

	public ImmutableList<DataEntryGroup> getByWindowId(@NonNull final AdWindowId adWindowId)
	{
		final ImmutableList<I_DataEntry_Group> groupRecords = retrieveGroupRecords(adWindowId);
		if (groupRecords.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<DataEntryGroup> result = ImmutableList.builder();

		for (final I_DataEntry_Group groupRecord : groupRecords)
		{
			final Optional<DataEntryGroup> group = ofRecord(groupRecord);
			group.ifPresent(result::add);
		}
		return result.build();
	}

	private ImmutableList<I_DataEntry_Group> retrieveGroupRecords(@NonNull final AdWindowId adWindowId)
	{
		final ImmutableList<I_DataEntry_Group> groupRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Group.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Group.COLUMN_DataEntry_TargetWindow_ID, adWindowId)
				.orderBy(I_DataEntry_Group.COLUMNNAME_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Group.class);
		return groupRecords;
	}

	private static Optional<DataEntryGroup> ofRecord(@NonNull final I_DataEntry_Group groupRecord)
	{
		final I_AD_Tab firstADTab = Services.get(IADWindowDAO.class).retrieveFirstTab(groupRecord.getDataEntry_TargetWindow_ID());
		final I_AD_Table windowMainTable = firstADTab.getAD_Table();
		final String parentLinkColumnName = InterfaceWrapperHelper.getKeyColumnName(windowMainTable.getTableName());

		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(groupRecord);

		final ITranslatableString captionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Group.COLUMNNAME_TabName, groupRecord.getTabName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Group.COLUMNNAME_Description, groupRecord.getDescription());

		final DataEntryGroupBuilder group = DataEntryGroup
				.builder()
				.id(DataEntryGroupId.ofRepoId(groupRecord.getDataEntry_Group_ID()))
				.documentLinkColumnName(DocumentLinkColumnName.of(parentLinkColumnName))
				.caption(captionTrl)
				.description(descriptionTrl)
				.internalName(I_DataEntry_Group.COLUMNNAME_DataEntry_Group_ID + "-" + groupRecord.getDataEntry_Group_ID());

		final ImmutableList<I_DataEntry_SubGroup> subGroupRecords = retrieveSubgroupRecords(groupRecord);
		for (final I_DataEntry_SubGroup subGroupRecord : subGroupRecords)
		{
			final Optional<DataEntrySubGroup> subGroup = ofRecord(subGroupRecord);
			subGroup.ifPresent(group::dataEntrySubGroup);
		}

		final DataEntryGroup result = group.build();
		return result.getDataEntrySubGroups().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_SubGroup> retrieveSubgroupRecords(@NonNull final I_DataEntry_Group groupRecord)
	{
		final ImmutableList<I_DataEntry_SubGroup> subGroupRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_SubGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_SubGroup.COLUMN_DataEntry_Group_ID, groupRecord.getDataEntry_Group_ID())
				.orderBy(I_DataEntry_SubGroup.COLUMNNAME_SeqNo)
				.create()
				.listImmutable(I_DataEntry_SubGroup.class);
		return subGroupRecords;
	}

	/** @param sectionRecords ordered by SeqNo */
	private static Optional<DataEntrySubGroup> ofRecord(
			@NonNull final I_DataEntry_SubGroup subGroupRecord)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(subGroupRecord);

		final ITranslatableString captionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_SubGroup.COLUMNNAME_TabName, subGroupRecord.getTabName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_SubGroup.COLUMNNAME_Description, subGroupRecord.getDescription());

		final DataEntrySubGroupBuilder subgroup = DataEntrySubGroup.builder()
				.id(DataEntrySubGroupId.ofRepoId(subGroupRecord.getDataEntry_SubGroup_ID()))
				.caption(captionTrl)
				.description(descriptionTrl)
				.internalName(I_DataEntry_SubGroup.COLUMNNAME_DataEntry_SubGroup_ID + "-" + subGroupRecord.getDataEntry_SubGroup_ID());

		final ImmutableList<I_DataEntry_Section> sectionRecords = retrieveSectionRecords(subGroupRecord);
		for (final I_DataEntry_Section sectionRecord : sectionRecords)
		{
			final Optional<DataEntrySection> dataEntrySection = ofRecord(sectionRecord);
			dataEntrySection.ifPresent(subgroup::dataEntrySection);
		}

		final DataEntrySubGroup result = subgroup.build();
		return result.getDataEntrySections().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_Section> retrieveSectionRecords(@NonNull final I_DataEntry_SubGroup subGroupRecord)
	{
		final ImmutableList<I_DataEntry_Section> subGroupRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Section.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Section.COLUMN_DataEntry_SubGroup_ID, subGroupRecord.getDataEntry_SubGroup_ID())
				.orderBy(I_DataEntry_Section.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Section.class);

		return subGroupRecords;
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
				.initallyClosed(sectionRecord.isInitiallyClosed())
				.internalName(I_DataEntry_Section.COLUMNNAME_DataEntry_Section_ID + "-" + sectionRecord.getDataEntry_Section_ID());

		final ImmutableList<I_DataEntry_Line> lineRecords = retrieveLineRecords(sectionRecord);
		for (final I_DataEntry_Line lineRecord : lineRecords)
		{
			final Optional<DataEntryLine> line = ofRecord(lineRecord);
			line.ifPresent(section::dataEntryLine);
		}

		final DataEntrySection result = section.build();
		return result.getDataEntryLines().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_Line> retrieveLineRecords(@NonNull final I_DataEntry_Section sectionRecord)
	{
		final ImmutableList<I_DataEntry_Line> lineRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Line.COLUMN_DataEntry_Section_ID, sectionRecord.getDataEntry_Section_ID())
				.orderBy(I_DataEntry_Line.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Line.class);
		return lineRecords;
	}

	private static Optional<DataEntryLine> ofRecord(@NonNull final I_DataEntry_Line lineRecord)
	{
		final ImmutableList<I_DataEntry_Field> fieldRecords = retrieveFieldRecords(lineRecord);

		final DataEntryLineBuilder line = DataEntryLine.builder();

		for (final I_DataEntry_Field fieldRecord : fieldRecords)
		{
			final Optional<DataEntryField> field = ofRecord(fieldRecord);
			field.ifPresent(line::dataEntryField);
		}
		final DataEntryLine result = line.build();
		return result.getDataEntryFields().isEmpty() ? Optional.empty() : Optional.of(result);
	}

	private static ImmutableList<I_DataEntry_Field> retrieveFieldRecords(@NonNull final I_DataEntry_Line lineRecord)
	{
		final ImmutableList<I_DataEntry_Field> fieldRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Field.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Field.COLUMN_DataEntry_Line_ID, lineRecord.getDataEntry_Line_ID())
				.orderBy(I_DataEntry_Field.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_Field.class);
		return fieldRecords;
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
				.listValues(listValues.build())
				.build();
		return Optional.of(field);
	}

	private static ImmutableList<I_DataEntry_ListValue> retrieveListValueRecords(@NonNull final I_DataEntry_Field fieldRecord)
	{
		final ImmutableList<I_DataEntry_ListValue> listValueRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_ListValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_ListValue.COLUMN_DataEntry_Field_ID, fieldRecord.getDataEntry_Field_ID())
				.orderBy(I_DataEntry_ListValue.COLUMN_SeqNo)
				.create()
				.listImmutable(I_DataEntry_ListValue.class);
		return listValueRecords;
	}

	private static DataEntryListValue ofRecord(@NonNull final I_DataEntry_ListValue listValueRecord)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(listValueRecord);

		final DataEntryListValueId id = DataEntryListValueId.ofRepoId(listValueRecord.getDataEntry_ListValue_ID());
		final DataEntryFieldId dataEntryFieldId = DataEntryFieldId.ofRepoId(listValueRecord.getDataEntry_Field_ID());

		final ITranslatableString nameTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_ListValue.COLUMNNAME_Name, listValueRecord.getName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_ListValue.COLUMNNAME_Description, listValueRecord.getDescription());

		return new DataEntryListValue(id, dataEntryFieldId, nameTrl, descriptionTrl);
	}
}
