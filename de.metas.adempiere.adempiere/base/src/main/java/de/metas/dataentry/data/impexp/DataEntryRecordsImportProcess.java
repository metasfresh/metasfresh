package de.metas.dataentry.data.impexp;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.X_I_Replenish;
import org.compiere.util.Env;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordField;
import de.metas.dataentry.data.DataEntryRecordId;
import de.metas.dataentry.data.DataEntryRecordQuery;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLayout;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.dataentry.model.I_I_DataEntry_Record;
import de.metas.dataentry.model.X_I_DataEntry_Record;
import de.metas.i18n.TranslatableStrings;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.impexp.processing.SimpleImportProcessTemplate.ImportRecordResult;
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

public class DataEntryRecordsImportProcess extends SimpleImportProcessTemplate<I_I_DataEntry_Record>
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
		final DataEntryRecordId dataEntryRecordId = dataEntryRecordRepo.save(state.getDataEntryRecord());
		importRecord.setDataEntry_Record_ID(dataEntryRecordId.getRepoId());

		return newDataEntryRecord ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private void resolveIds(@NonNull final I_I_DataEntry_Record importRecord)
	{
		//
		// Window
		final AdWindowId adWindowId;
		{
			String windowInternalName = importRecord.getWindowInternalName();
			if (Check.isEmpty(windowInternalName, true))
			{
				throw new FillMandatoryException(I_I_DataEntry_Record.COLUMNNAME_WindowInternalName);
			}

			adWindowId = Services.get(IADWindowDAO.class).getWindowIdByInternalName(windowInternalName);
			importRecord.setAD_Window_ID(adWindowId.getRepoId());
		}

		//
		// DataEntry Layout
		final DataEntryLayout layout = dataEntryLayoutRepo.getByWindowId(adWindowId);

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
		final DataEntryTab tab;
		{
			final NamePattern tabNamePattern = NamePattern.ofStringOrAny(importRecord.getDataEntry_Tab_Name());
			if (tabNamePattern.isAny())
			{
				throw new FillMandatoryException("DataEntry_Tab_Name");
			}
			tab = layout.getFirstTabMatching(tabNamePattern::isMatching)
					.orElseThrow(() -> new AdempiereException("@NotFound@ @DataEntry_Tab_ID@"));
			importRecord.setDataEntry_Tab_ID(tab.getId().getRepoId());
		}

		//
		// DataEntrySubTab:
		final DataEntrySubTab subTab;
		{
			final NamePattern subTabNamePattern = NamePattern.ofStringOrAny(importRecord.getDataEntry_SubTab_Name());
			if (subTabNamePattern.isAny())
			{
				throw new FillMandatoryException("DataEntry_SubTab_Name");
			}

			subTab = tab.getFirstSubTabMatching(subTabNamePattern::isMatching)
					.orElseThrow(() -> new AdempiereException("@NotFound@ @DataEntry_SubTab_ID@"));
			importRecord.setDataEntry_SubTab_ID(subTab.getId().getRepoId());
		}

		//
		// Section, Line, Field
		final NamePattern sectionPattern = NamePattern.ofStringOrAny(importRecord.getDataEntry_Section_Name());
		final NamePattern linePattern = NamePattern.ofStringOrAny(importRecord.getDataEntry_Line_Name());
		final NamePattern fieldNamePattern = NamePattern.ofStringOrAny(importRecord.getFieldName());
		if (fieldNamePattern.isAny())
		{
			throw new FillMandatoryException("FieldName");
		}
		//
		DataEntrySection section = null;
		DataEntryLine line = null;
		DataEntryField field = null;
		for (final DataEntrySection currentSection : subTab.getSections())
		{
			if (!sectionPattern.isMatching(currentSection))
			{
				continue;
			}

			importRecord.setDataEntry_Section_ID(currentSection.getId().getRepoId());

			for (final DataEntryLine currentLine : currentSection.getLines())
			{
				if (!linePattern.isMatching(currentLine))
				{
					continue;
				}

				// importRecord.setDataEntry_Line_ID(currentLine.getId().getRepoId()); // N/A

				for (final DataEntryField currentField : currentLine.getFields())
				{
					if (!fieldNamePattern.isMatching(currentField))
					{
						continue;
					}

					if (field != null)
					{
						throw new AdempiereException(TranslatableStrings.builder()
								.append("More than one matching field found: ")
								.append(section.getCaption()).append(" - ").append(line.getSeqNo()).append(" - ").append(field.getCaption())
								.append(", ")
								.append(currentSection.getCaption()).append(" - ").append(currentLine.getSeqNo()).append(" - ").append(currentField.getCaption())
								.build());
					}

					importRecord.setDataEntry_Field_ID(currentField.getId().getRepoId());

					section = currentSection;
					line = currentLine;
					field = currentField;
				}
			}
		}
		//
		if (field == null)
		{
			throw new AdempiereException("@NotFound@ @DataEntry_Field_ID@");
		}
		else
		{
			importRecord.setDataEntry_Field_ID(field.getId().getRepoId());
		}
	}

	private ImportState newImportState(
			@NonNull final TableRecordReference recordRef,
			@NonNull final AdWindowId adWindowId,
			@NonNull final DataEntrySubTabId subTabId)
	{
		final DataEntryLayout layout = dataEntryLayoutRepo.getByWindowId(adWindowId);
		final DataEntrySubTab subTab = layout.getSubTabById(subTabId);

		final DataEntryRecord dataEntryRecord = dataEntryRecordRepo.getBy(DataEntryRecordQuery.builder()
				.dataEntrySubTabId(subTab.getId())
				.recordId(recordRef.getRecord_ID())
				.build())
				.orElseGet(() -> DataEntryRecord.builder()
						.mainRecord(recordRef)
						.dataEntrySubTabId(subTabId)
						.build());

		return ImportState.builder()
				.subTab(subTab)
				.dataEntryRecord(dataEntryRecord)
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
			try
			{
				return DataEntryRecordField.convertValueToFieldType(value, field);
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
