package de.metas.ui.web.dataentry.window.descriptor.factory;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.user.CreatedUpdatedInfo;

import com.google.common.annotations.VisibleForTesting;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class DataEntryWebuiTools
{
	@VisibleForTesting
	static final String MSG_CREATED_UPDATED_INFO = "de.metas.dataentry_Created_Updated_Info";

	public DataEntryFieldId computeDataEntryFieldId(@NonNull final IDocumentFieldView field)
	{
		final String fieldName = field.getFieldName();
		final DataEntryFieldId dataEntryFieldId = DataEntryFieldId.ofRepoId(Integer.parseInt(fieldName));
		return dataEntryFieldId;
	}

	public String computeFieldName(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return Integer.toString(dataEntryFieldId.getRepoId());
	}

	/** Extracts/converts a data entry value for the webui. */
	public Object extractDataEntryValueForField(
			@NonNull final DataEntryRecord dataEntryRecord,
			@NonNull final DocumentFieldDescriptor fieldDescriptor)
	{
		final DataEntryFieldBindingDescriptor dataBinding = fieldDescriptor.getDataBindingNotNull(DataEntryFieldBindingDescriptor.class);

		final DataEntryFieldId dataEntryFieldId = dataBinding.getDataEntryFieldId();
		switch (dataBinding.getFieldType())
		{
			// case CREATED:
			// return dataEntryRecord.getCreatedValue(dataEntryFieldId).orElse(null);
			// case CREATED_BY:
			// return dataEntryRecord.getCreatedByValue(dataEntryFieldId).map(UserId::getRepoId).orElse(0);
			// case UPDATED:
			// return dataEntryRecord.getUpdatedValue(dataEntryFieldId).orElse(null);
			// case UPDATED_BY:
			// return dataEntryRecord.getUpdatedByValue(dataEntryFieldId).map(UserId::getRepoId).orElse(0);
			case CREATED_UPDATED_INFO:
				return extractCreatedUpdatedInfo(dataEntryRecord, dataEntryFieldId);
			case PARENT_LINK_ID:
				return dataEntryRecord.getMainRecord().getRecord_ID();
			case SUB_GROUP_ID:
				return dataEntryRecord.getDataEntrySubGroupId().getRepoId();
			case LIST:
				final DataEntryListValueId dataEntryListValueId = (DataEntryListValueId)dataEntryRecord.getFieldValue(dataEntryFieldId).orElse(null);
				final DataEntryListValueDataSourceFetcher fetcher = (DataEntryListValueDataSourceFetcher)fieldDescriptor.getLookupDescriptor(LookupScope.DocumentField).getLookupDataSourceFetcher();
				return fetcher.getLookupForForListValueId(dataEntryListValueId);
			default:
				return dataEntryRecord.getFieldValue(dataEntryFieldId).orElse(null);
		}
	}

	private ITranslatableString extractCreatedUpdatedInfo(
			@NonNull final DataEntryRecord dataEntryRecord,
			@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		final Optional<CreatedUpdatedInfo> createdUpdatedInfo = dataEntryRecord.getCreatedUpdatedInfo(dataEntryFieldId);

		return createdUpdatedInfo
				.map(DataEntryWebuiTools::extractCreatedUpdatedInfo)
				.orElse(ITranslatableString.empty());
	}

	@VisibleForTesting
	ITranslatableString extractCreatedUpdatedInfo(@NonNull final CreatedUpdatedInfo createdUpdatedInfo)
	{
		final ITranslatableString createdUpdatedInfoString = Services.get(IMsgBL.class).getTranslatableMsgText(
				MSG_CREATED_UPDATED_INFO,
				createdUpdatedInfo.getCreatedBy(),
				createdUpdatedInfo.getCreated(),
				createdUpdatedInfo.getUpdatedBy(),
				createdUpdatedInfo.getCreated());

		return createdUpdatedInfoString;
	}

	public Object extractFieldValueForDataEntry(@NonNull final IDocumentFieldView fieldView)
	{
		final Object value = fieldView.getValue();

		final DocumentFieldDescriptor descriptor = fieldView.getDescriptor();
		final DataEntryFieldBindingDescriptor dataBinding = descriptor.getDataBindingNotNull(DataEntryFieldBindingDescriptor.class);

		final FieldType fieldType = dataBinding.getFieldType();

		if (value == null)
		{
			return null;
		}

		final Object result;
		switch (fieldType)
		{
			case DATE:
				result = fieldType.getClazz().cast(value);
				break;
			case LIST:
				final LookupDescriptor lookupDescriptor = descriptor.getLookupDescriptor(LookupScope.DocumentField);
				final DataEntryListValueDataSourceFetcher fetcher = (DataEntryListValueDataSourceFetcher)lookupDescriptor.getLookupDataSourceFetcher();
				result = fetcher.getListValueIdForLookup((IntegerLookupValue)value);
				break;
			case NUMBER:
				result = fieldType.getClazz().cast(value);
				break;
			case TEXT:
				result = fieldType.getClazz().cast(value);
				break;
			case LONG_TEXT:
				result = fieldType.getClazz().cast(value);
				break;
			case YESNO:
				result = fieldType.getClazz().cast(value);
				break;
			// case CREATED:
			// result = fieldType.getClazz().cast(value);
			// break;
			// case CREATED_BY:
			// result = fieldType.getClazz().cast(value);
			// break;
			// case UPDATED:
			// result = fieldType.getClazz().cast(value);
			// break;
			// case UPDATED_BY:
			// result = fieldType.getClazz().cast(value);
			// break;
			case PARENT_LINK_ID:
				result = fieldType.getClazz().cast(value);
				break;
			case SUB_GROUP_ID:
				result = fieldType.getClazz().cast(value);
				break;
			default:
				// this includes CREATED_UPDATED_INFO, PARENT_LINK_ID and SUB_GROUP_ID; we don't expect the document repo to try an extract these
				throw new AdempiereException("Unexpected fieldType=" + fieldType);
		}

		return result;
	}
}
