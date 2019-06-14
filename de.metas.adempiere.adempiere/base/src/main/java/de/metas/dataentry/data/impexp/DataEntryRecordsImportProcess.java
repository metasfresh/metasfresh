package de.metas.dataentry.data.impexp;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.X_I_Replenish;
import org.compiere.util.Env;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.DataEntryTabId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordField;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLayout;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.dataentry.model.I_I_DataEntry_Record;
import de.metas.dataentry.model.X_I_DataEntry_Record;
import de.metas.i18n.ITranslatableString;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class DataEntryRecordsImportProcess extends AbstractImportProcess<I_I_DataEntry_Record>
{
	private final DataEntryLayoutRepository dataEntryLayoutRepo = Adempiere.getBean(DataEntryLayoutRepository.class);
	private final DataEntryRecordRepository dataEntryRecordRepo = Adempiere.getBean(DataEntryRecordRepository.class);

	private final RecordIdLookup externalIdResolver = new RecordIdLookup();

	@Override
	public Class<I_I_DataEntry_Record> getImportModelClass()
	{
		return I_I_DataEntry_Record.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_DataEntry_Record.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_DataEntry_Record.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_DataEntry_Record.COLUMNNAME_AD_Window_ID
				+ ", " + I_I_DataEntry_Record.COLUMNNAME_DataEntry_SubTab_ID
				+ ", " + I_I_DataEntry_Record.COLUMNNAME_AD_Table_ID
				+ ", " + I_I_DataEntry_Record.COLUMNNAME_Record_ID
				+ ", " + I_I_DataEntry_Record.COLUMNNAME_DataEntry_Field_ID;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		// nothing here
	}

	@Override
	protected I_I_DataEntry_Record retrieveImportRecord(final Properties ctx, final ResultSet rs)
	{
		return new X_I_DataEntry_Record(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(
			@NonNull final IMutable<Object> stateHolder,
			@NonNull final I_I_DataEntry_Record importRecord,
			final boolean isInsertOnly_NOTUSED)
	{
		resolveIds(importRecord);

		final TableRecordReference recordRef = TableRecordReference.of(importRecord.getAD_Table_ID(), importRecord.getRecord_ID());
		final DataEntrySubTabId subTabId = DataEntrySubTabId.ofRepoId(importRecord.getDataEntry_SubTab_ID());
		ImportState state = (ImportState)stateHolder.getValue();

		if (state != null && !state.isMatching(recordRef, subTabId))
		{
			state = null;
		}
		if (state == null)
		{
			final AdWindowId adWindowId = AdWindowId.ofRepoId(importRecord.getAD_Window_ID());
			state = newImportState(recordRef, adWindowId, subTabId);
			stateHolder.setValue(state);
		}

		final DataEntryFieldId fieldId = DataEntryFieldId.ofRepoId(importRecord.getDataEntry_Field_ID());
		state.setFieldValue(fieldId, importRecord.getFieldValue());

		final boolean newDataEntryRecord = state.isNewDataEntryRecord();
		dataEntryRecordRepo.save(state.getDataEntryRecord());
		importRecord.setDataEntry_Record_ID(state.getDataEntryRecord().getId().get().getRepoId());

		return newDataEntryRecord ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private void resolveIds(@NonNull final I_I_DataEntry_Record importRecord)
	{
		AdWindowId adWindowId = AdWindowId.ofRepoIdOrNull(importRecord.getAD_Window_ID());
		if (adWindowId == null)
		{
			String windowInternalName = importRecord.getWindowInternalName();
			if (Check.isEmpty(windowInternalName, true))
			{
				throw new FillMandatoryException(I_I_DataEntry_Record.COLUMNNAME_WindowInternalName);
			}

			adWindowId = Services.get(IADWindowDAO.class).getWindowIdByInternalName(windowInternalName);
			importRecord.setAD_Window_ID(adWindowId.getRepoId());
		}

		final DataEntryLayoutRepository dataEntryLayoutRepository = Adempiere.getBean(DataEntryLayoutRepository.class);
		final DataEntryLayout layout = dataEntryLayoutRepository.getByWindowId(adWindowId);

		//
		// AD_Table_ID, Record_ID
		importRecord.setAD_Table_ID(layout.getMainTableId().getRepoId());
		int recordId = importRecord.getRecord_ID();
		if (recordId <= 0)
		{
			final String externalId = importRecord.getExternalId();
			if (!Check.isEmpty(externalId, true))
			{
				recordId = externalIdResolver.getRecordId(layout.getMainTableId(), externalId);
				importRecord.setRecord_ID(recordId);
			}
			else
			{
				throw new AdempiereException("@NonFound@ @Record_ID@");
			}
		}

		//
		// DataEntryTab:
		DataEntryTabId tabId = DataEntryTabId.ofRepoIdOrNull(importRecord.getDataEntry_Tab_ID());
		final DataEntryTab tab;
		if (tabId == null)
		{
			final String tabNamePattern = importRecord.getDataEntry_Tab_Name();
			tab = layout.getFirstTabMatching(currentTab -> isMatching(currentTab, tabNamePattern))
					.orElseThrow(() -> new AdempiereException("@NotFound@ @DataEntry_Tab_ID@"));
			tabId = tab.getId();
			importRecord.setDataEntry_Tab_ID(tabId.getRepoId());
		}
		else
		{
			tab = layout.getTabById(tabId);
		}

		//
		// DataEntrySubTab:
		DataEntrySubTabId subTabId = DataEntrySubTabId.ofRepoIdOrNull(importRecord.getDataEntry_SubTab_ID());
		final DataEntrySubTab subTab;
		if (subTabId == null)
		{
			final String subTabNamePattern = importRecord.getDataEntry_SubTab_Name();
			subTab = tab.getFirstSubTabMatching(currentSubTab -> isMatching(currentSubTab, subTabNamePattern))
					.orElseThrow(() -> new AdempiereException("@NotFound@ @DataEntry_SubTab_ID@"));
			subTabId = subTab.getId();
			importRecord.setDataEntry_SubTab_ID(subTabId.getRepoId());
		}
		else
		{
			subTab = tab.getSubTabById(subTabId);
		}

		//
		// DataEntryField:
		DataEntryFieldId fieldId = DataEntryFieldId.ofRepoIdOrNull(importRecord.getDataEntry_Field_ID());
		final DataEntryField field;
		if (fieldId == null)
		{
			final String fieldNamePattern = importRecord.getFieldName();
			field = subTab.getFirstFieldMatching(currentField -> isMatching(currentField, fieldNamePattern))
					.orElseThrow(() -> new AdempiereException("@NotFound@ @DataEntry_Field_ID@"));
			fieldId = field.getId();
			importRecord.setDataEntry_Field_ID(fieldId.getRepoId());
		}
		else
		{
			field = subTab.getFieldById(fieldId);
		}
	}

	private static boolean isMatching(@NonNull final DataEntryTab tab, @NonNull final String tabNamePattern)
	{
		return tabNamePattern.equalsIgnoreCase(tab.getInternalName())
				|| isMatching(tab.getCaption(), tabNamePattern);
	}

	private static boolean isMatching(@NonNull final DataEntrySubTab subTab, @NonNull final String tabNamePattern)
	{
		return tabNamePattern.equalsIgnoreCase(subTab.getInternalName())
				|| isMatching(subTab.getCaption(), tabNamePattern);
	}

	private static boolean isMatching(@NonNull final DataEntryField field, @NonNull final String fieldNamePattern)
	{
		return isMatching(field.getCaption(), fieldNamePattern);
	}

	private static boolean isMatching(final ITranslatableString trl, final String pattern)
	{
		if (trl.getDefaultValue().equalsIgnoreCase(pattern))
		{
			return true;
		}

		for (final String adLanguage : trl.getAD_Languages())
		{
			if (trl.translate(adLanguage).equalsIgnoreCase(pattern))
			{
				return true;
			}
		}

		return false;
	}

	private ImportState newImportState(
			@NonNull final TableRecordReference recordRef,
			@NonNull final AdWindowId adWindowId,
			@NonNull final DataEntrySubTabId subTabId)
	{
		final DataEntryLayout layout = dataEntryLayoutRepo.getByWindowId(adWindowId);
		final DataEntrySubTab subTab = layout.getSubTabById(subTabId);

		return ImportState.builder()
				.subTab(subTab)
				.dataEntryRecord(DataEntryRecord.builder()
						.mainRecord(recordRef)
						.dataEntrySubTabId(subTabId)
						.build())
				.updatedBy(Env.getLoggedUserId())
				.build();
	}

	@Override
	protected void markImported(@NonNull final I_I_DataEntry_Record importRecord)
	{
		importRecord.setI_IsImported(X_I_Replenish.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}

	@Builder
	@Getter
	@ToString
	private static class ImportState
	{
		@NonNull
		private final DataEntrySubTab subTab;

		@NonNull
		private final DataEntryRecord dataEntryRecord;

		@NonNull
		private final UserId updatedBy;

		public boolean isMatching(
				@NonNull final TableRecordReference recordRef,
				@NonNull final DataEntrySubTabId subTabId)
		{
			return dataEntryRecord.getMainRecord().equals(recordRef)
					&& dataEntryRecord.getDataEntrySubTabId().equals(subTabId);
		}

		public void setFieldValue(final DataEntryFieldId fieldId, final Object value)
		{
			final Object valueConv = convertValueToFieldType(value, fieldId);
			dataEntryRecord.setRecordField(fieldId, updatedBy, valueConv);
		}

		private Object convertValueToFieldType(final Object value, @NonNull final DataEntryFieldId fieldId)
		{
			final DataEntryField field = subTab.getFieldById(fieldId);
			final FieldType type = field.getType();
			try
			{
				return DataEntryRecordField.convertValueToFieldType(value, type);
			}
			catch (Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("field", field)
						.appendParametersToMessage();
			}
		}

		public boolean isNewDataEntryRecord()
		{
			return !dataEntryRecord.getId().isPresent();
		}
	}
}
