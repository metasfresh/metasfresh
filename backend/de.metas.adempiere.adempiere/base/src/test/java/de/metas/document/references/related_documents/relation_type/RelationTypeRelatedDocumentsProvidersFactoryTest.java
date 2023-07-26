/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.related_documents.relation_type;

import com.google.common.collect.ImmutableMap;
import de.metas.document.references.zoom_into.NullCustomizedWindowInfoMapRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.TableRefInfo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.I_AD_Table;
import org.compiere.model.X_AD_Reference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Map.Entry;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RelationTypeRelatedDocumentsProvidersFactoryTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void findRelatedDocumentsProvider_IsTableRecordIdTarget_NoSource()
	{
		final String refTargetName = "RefTargetName1";
		final String validationType = X_AD_Reference.VALIDATIONTYPE_TableValidation;
		final I_AD_Reference referenceTarget = createReferenceSourceOrTarget(refTargetName, validationType);

		final String tableName = "TableName";
		final I_AD_Table table = createTable(tableName);

		final String keyColumnName = "TableName_ID";
		createColumn(table, keyColumnName);

		final String recordColumnname = "Record_ID";
		createColumn(table, recordColumnname);
		createRefTable(referenceTarget, table);

		final boolean isTableRecordIdTarget = true;
		final I_AD_RelationType relationType = createRelationType(isTableRecordIdTarget, null, referenceTarget);

		final TableRefInfo targetTableRefInfo = TableRefInfo.builder()
				.identifier(refTargetName)
				.tableName(tableName)
				.keyColumn(keyColumnName)
				.displayColumn(keyColumnName)
				.valueDisplayed(true)
				.displayColumnSQL("")
				.translated(true)
				.whereClause("")
				.orderByClause("")
				.zoomSO_Window_ID(null)
				.zoomPO_Window_ID(null)
				.zoomAD_Window_ID_Override(null)
				.autoComplete(false)
				.build();

		setupLookupDAOMock(ImmutableMap.of(
				referenceTarget.getAD_Reference_ID(), targetTableRefInfo));

		final RelationTypeRelatedDocumentsProvidersFactory relationTypeRelatedDocumentsProvidersFactory = new RelationTypeRelatedDocumentsProvidersFactory(NullCustomizedWindowInfoMapRepository.instance);
		final SpecificRelationTypeRelatedDocumentsProvider provider = relationTypeRelatedDocumentsProvidersFactory.findRelatedDocumentsProvider(relationType);

		assertThat(provider.isTableRecordIdTarget()).isTrue();
	}

	@Test
	public void findRelatedDocumentsProvider_Is_Not_TableRecordIdTarget_WithSource()
	{
		final String refTargetName = "RefTargetName1";
		final String validationType = X_AD_Reference.VALIDATIONTYPE_TableValidation;
		final I_AD_Reference referenceTarget = createReferenceSourceOrTarget(refTargetName, validationType);

		final String refSourceName = "RefSourceName1";
		final I_AD_Reference referenceSource = createReferenceSourceOrTarget(refSourceName, validationType);

		final String tableName = "TableName";
		final I_AD_Table table = createTable(tableName);

		final String keyColumnName = "TableName_ID";
		createColumn(table, keyColumnName);

		final String recordColumnname = "Record_ID";
		createColumn(table, recordColumnname);
		createRefTable(referenceTarget, table);

		final boolean isTableRecordIdTarget = false;

		final I_AD_RelationType relationType = createRelationType(isTableRecordIdTarget, referenceSource, referenceTarget);
		final TableRefInfo targetTableRefInfo = TableRefInfo.builder()
				.identifier(refTargetName)
				.tableName(tableName)
				.keyColumn(keyColumnName)
				.displayColumn(keyColumnName)
				.valueDisplayed(true)
				.displayColumnSQL("")
				.translated(true)
				.whereClause("")
				.orderByClause("")
				.zoomSO_Window_ID(null)
				.zoomPO_Window_ID(null)
				.zoomAD_Window_ID_Override(null)
				.autoComplete(false)
				.build();

		final TableRefInfo sourceTableRefInfo = TableRefInfo.builder()
				.identifier(refTargetName)
				.tableName(tableName)
				.keyColumn(keyColumnName)
				.displayColumn(keyColumnName)
				.valueDisplayed(true)
				.displayColumnSQL("")
				.translated(true)
				.whereClause("")
				.orderByClause("")
				.zoomSO_Window_ID(null)
				.zoomPO_Window_ID(null)
				.zoomAD_Window_ID_Override(null)
				.autoComplete(false)
				.build();

		setupLookupDAOMock(ImmutableMap.of(
				referenceTarget.getAD_Reference_ID(), targetTableRefInfo,
				referenceSource.getAD_Reference_ID(), sourceTableRefInfo));

		final RelationTypeRelatedDocumentsProvidersFactory relationTypeRelatedDocumentsProvidersFactory = new RelationTypeRelatedDocumentsProvidersFactory(NullCustomizedWindowInfoMapRepository.instance);
		final SpecificRelationTypeRelatedDocumentsProvider provider = relationTypeRelatedDocumentsProvidersFactory.findRelatedDocumentsProvider(relationType);

		assertThat(provider.isTableRecordIdTarget()).isFalse();
	}

	private void setupLookupDAOMock(final Map<Integer, TableRefInfo> idToRefInfo)
	{
		final ILookupDAO lookupDao = Mockito.mock(ILookupDAO.class);
		Services.registerService(ILookupDAO.class, lookupDao);

		for (final Entry<Integer, TableRefInfo> entry : idToRefInfo.entrySet())
		{
			Mockito.doReturn(entry.getValue()).when(lookupDao).retrieveTableRefInfo(entry.getKey());
		}
	}

	@Test
	public void findRelatedDocumentsProvider_DefaultRelType_NoSource()
	{
		final String refTargetName = "RefTargetName1";
		final String validationType = X_AD_Reference.VALIDATIONTYPE_TableValidation;
		final I_AD_Reference referenceTarget = createReferenceSourceOrTarget(refTargetName, validationType);

		final String tableName = "TableName";
		final I_AD_Table table = createTable(tableName);

		final String keyColumnName = "TableName_ID";
		createColumn(table, keyColumnName);

		createRefTable(referenceTarget, table);

		final boolean isTableRecordIdTarget = false;
		final I_AD_RelationType relationType = createRelationType(isTableRecordIdTarget, null, referenceTarget);

		final RelationTypeRelatedDocumentsProvidersFactory relationTypeRelatedDocumentsProvidersFactory = new RelationTypeRelatedDocumentsProvidersFactory(NullCustomizedWindowInfoMapRepository.instance);
		assertThatThrownBy(() -> relationTypeRelatedDocumentsProvidersFactory.findRelatedDocumentsProvider(relationType))
				.isInstanceOf(AdempiereException.class)
				.hasMessage("Assumption failure: sourceReferenceId > 0");
	}

	private void createColumn(final I_AD_Table table, final String columnname)
	{
		final I_AD_Column column = newInstance(I_AD_Column.class);
		column.setAD_Table_ID(table.getAD_Table_ID());
		column.setColumnName(columnname);
		save(column);
	}

	@SuppressWarnings("SameParameterValue")
	private I_AD_Table createTable(final String tableName)
	{
		final I_AD_Table table = newInstance(I_AD_Table.class);
		table.setName(tableName);
		table.setTableName(tableName);
		save(table);
		return table;
	}

	private void createRefTable(final I_AD_Reference reference, @NonNull final I_AD_Table table)
	{
		final I_AD_Ref_Table refTable = newInstance(I_AD_Ref_Table.class);
		refTable.setAD_Reference(reference);
		refTable.setAD_Table_ID(table.getAD_Table_ID());

		save(refTable);
	}

	private I_AD_RelationType createRelationType(
			final boolean IsTableRecordIdTarget,
			@Nullable final I_AD_Reference referenceSource,
			@Nullable final I_AD_Reference referenceTarget)
	{
		final I_AD_RelationType relationType = newInstance(I_AD_RelationType.class);
		relationType.setIsTableRecordIdTarget(IsTableRecordIdTarget);
		relationType.setAD_Reference_Source_ID(referenceSource != null ? referenceSource.getAD_Reference_ID() : -1);
		relationType.setAD_Reference_Target_ID(referenceTarget != null ? referenceTarget.getAD_Reference_ID() : -1);

		save(relationType);
		return relationType;
	}

	@SuppressWarnings("SameParameterValue")
	private I_AD_Reference createReferenceSourceOrTarget(final String name, final String validationType)
	{
		final I_AD_Reference reference = newInstance(I_AD_Reference.class);
		reference.setName(name);
		reference.setValidationType(validationType);

		save(reference);
		return reference;
	}
}
