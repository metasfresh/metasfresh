package de.metas.dataentry;

import static de.metas.util.Check.fail;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryField.Type;
import de.metas.dataentry.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Group;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_SubGroup;
import de.metas.dataentry.model.X_DataEntry_Field;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
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

@Repository
public class DataEntryRepository
{
	public List<DataEntryGroup> getByWindowId(final int adWindowId)
	{
		final List<I_DataEntry_Group> groupRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Group.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Group.COLUMN_DataEntry_TargetWindow_ID, adWindowId)
				.create()
				.list();
		if (groupRecords.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<DataEntryGroup> result = ImmutableList.builder();

		for (final I_DataEntry_Group groupRecord : groupRecords)
		{
			result.add(ofRecord(groupRecord));
		}
		return result.build();
	}

	private DataEntryGroup ofRecord(@NonNull final I_DataEntry_Group groupRecord)
	{
		final I_AD_Tab firstADTab = Services.get(IADWindowDAO.class).retrieveFirstTab(groupRecord.getDataEntry_TargetWindow_ID());
		final I_AD_Table windowMainTable = firstADTab.getAD_Table();
		final String parentLinkColumnName = InterfaceWrapperHelper.getKeyColumnName(windowMainTable.getTableName());

		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(groupRecord);

		final ITranslatableString captionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Group.COLUMNNAME_TabName, groupRecord.getTabName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Group.COLUMNNAME_Description, groupRecord.getDescription());

		final List<I_DataEntry_SubGroup> subGroupRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_SubGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_SubGroup.COLUMN_DataEntry_Group_ID, groupRecord.getDataEntry_Group_ID())
				.create()
				.list();

		final ImmutableList.Builder<DataEntrySubGroup> subGroups = ImmutableList.builder();
		for (final I_DataEntry_SubGroup subGroupRecord : subGroupRecords)
		{
			subGroups.add(ofRecord(subGroupRecord));
		}

		return DataEntryGroup
				.builder()
				.id(DataEntryGroupId.ofRepoId(groupRecord.getDataEntry_Group_ID()))
				.documentLinkColumnName(DocumentLinkColumnName.of(parentLinkColumnName))
				.caption(captionTrl)
				.description(descriptionTrl)
				.internalName(groupRecord.getName())
				.dataEntrySubGroups(subGroups.build())
				.build();
	}

	private DataEntrySubGroup ofRecord(final I_DataEntry_SubGroup subGroupRecord)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(subGroupRecord);

		final ITranslatableString captionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_SubGroup.COLUMNNAME_TabName, subGroupRecord.getTabName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_SubGroup.COLUMNNAME_Description, subGroupRecord.getDescription());

		final List<I_DataEntry_Field> fieldRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Field.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Field.COLUMN_DataEntry_SubGroup_ID, subGroupRecord.getDataEntry_SubGroup_ID())
				.orderBy(I_DataEntry_Field.COLUMN_SeqNo)
				.create()
				.list();

		final ImmutableList.Builder<DataEntryField> fields = ImmutableList.builder();
		for (final I_DataEntry_Field fieldRecord : fieldRecords)
		{
			fields.add(ofRecord(fieldRecord));
		}

		return DataEntrySubGroup.builder()
				.id(DataEntrySubGroupId.ofRepoId(subGroupRecord.getDataEntry_SubGroup_ID()))
				.caption(captionTrl)
				.description(descriptionTrl)
				.internalName(subGroupRecord.getTabName())
				.dataEntryFields(fields.build())
				.build();
	}

	private DataEntryField ofRecord(final I_DataEntry_Field fieldRecord)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(fieldRecord);

		final ITranslatableString nameTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Field.COLUMNNAME_Name, fieldRecord.getName());

		final ITranslatableString descriptionTrl = modelTranslationMap
				.getColumnTrl(I_DataEntry_Field.COLUMNNAME_Description, fieldRecord.getDescription());

		final ImmutableList.Builder<DataEntryListValue> listValues = ImmutableList.builder();
		final DataEntryField.Type type;

		final String recordType = fieldRecord.getDataEntry_RecordType();
		if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_List.equals(recordType))
		{
			type = Type.LIST;

			final List<I_DataEntry_ListValue> listValueRecords = Services.get(IQueryBL.class)
					.createQueryBuilder(I_DataEntry_ListValue.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_DataEntry_ListValue.COLUMN_DataEntry_Field_ID, fieldRecord.getDataEntry_Field_ID())
					.create()
					.list();
			for (final I_DataEntry_ListValue listValueRecord : listValueRecords)
			{
				listValues.add(ofRecord(listValueRecord));
			}

		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_Date.equals(recordType))
		{
			type = Type.DATE;
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_Number.equals(recordType))
		{
			type = Type.NUMBER;
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_Text.equals(recordType))
		{
			type = Type.STRING;
		}
		else if (X_DataEntry_Field.DATAENTRY_RECORDTYPE_YesNo.equals(recordType))
		{
			type = Type.YESNO;
		}
		else
		{
			fail("Unexpected type={}; DataEntry_Field={}", recordType, fieldRecord);
			type = null;
		}
		return DataEntryField
				.builder()
				.id(DataEntryFieldId.ofRepoId(fieldRecord.getDataEntry_Field_ID()))
				.caption(nameTrl)
				.description(descriptionTrl)
				.mandatory(fieldRecord.isMandatory())
				.type(type)
				.listValues(listValues.build())
				.build();
	}

	private DataEntryListValue ofRecord(@NonNull final I_DataEntry_ListValue listValueRecord)
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
