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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.ad_reference.ADRefList;
import de.metas.ad_reference.ADRefListId;
import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADRefTable;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.AdRefListRepositoryMocked;
import de.metas.ad_reference.AdRefTableRepositoryMocked;
import de.metas.ad_reference.ReferenceId;
import de.metas.document.references.zoom_into.NullCustomizedWindowInfoMapRepository;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.I_AD_Table;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_AD_RelationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

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

		final ADRefTable targetTableRefInfo = ADRefTable.builder()
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

		final ADReferenceService adReferenceService = newADReferenceService(ImmutableMap.of(ReferenceId.ofRepoId(referenceTarget.getAD_Reference_ID()), targetTableRefInfo));

		final RelationTypeRelatedDocumentsProvidersFactory relationTypeRelatedDocumentsProvidersFactory = new RelationTypeRelatedDocumentsProvidersFactory(
				adReferenceService,
				NullCustomizedWindowInfoMapRepository.instance);
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
		final ADRefTable targetTableRefInfo = ADRefTable.builder()
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

		final ADRefTable sourceTableRefInfo = ADRefTable.builder()
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

		final ADReferenceService adReferenceService = newADReferenceService(ImmutableMap.of(
				ReferenceId.ofRepoId(referenceTarget.getAD_Reference_ID()), targetTableRefInfo,
				ReferenceId.ofRepoId(referenceSource.getAD_Reference_ID()), sourceTableRefInfo));

		final RelationTypeRelatedDocumentsProvidersFactory relationTypeRelatedDocumentsProvidersFactory = new RelationTypeRelatedDocumentsProvidersFactory(
				adReferenceService,
				NullCustomizedWindowInfoMapRepository.instance);
		final SpecificRelationTypeRelatedDocumentsProvider provider = relationTypeRelatedDocumentsProvidersFactory.findRelatedDocumentsProvider(relationType);

		assertThat(provider.isTableRecordIdTarget()).isFalse();
	}

	private ADRefList adRefList(int referenceRepoId, String name, String... values)
	{
		final ReferenceId referenceId = ReferenceId.ofRepoId(referenceRepoId);

		final AtomicInteger nextADRefListRepoId = new AtomicInteger(1);

		return ADRefList.builder()
				.referenceId(referenceId)
				.name(name)
				.items(Stream.of(values)
						.map(value -> ADRefListItem.builder()
								.referenceId(referenceId)
								.refListId(ADRefListId.ofRepoId(nextADRefListRepoId.getAndIncrement()))
								.value(value)
								.valueName(value)
								.name(TranslatableStrings.anyLanguage(value))
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private ADReferenceService newADReferenceService(final Map<ReferenceId, ADRefTable> idToRefInfo)
	{
		final AdRefTableRepositoryMocked adRefTableRepository = new AdRefTableRepositoryMocked();
		idToRefInfo.forEach(adRefTableRepository::put);

		final AdRefListRepositoryMocked adRefListRepository = new AdRefListRepositoryMocked();
		adRefListRepository.put(adRefList(X_AD_RelationType.ROLE_SOURCE_AD_Reference_ID, "ROLE_SOURCE", X_AD_RelationType.ROLE_SOURCE_Abo));

		return new ADReferenceService(adRefListRepository, adRefTableRepository);
	}

	@Test
	public void findRelatedDocumentsProvider_DefaultRelType_NoSource()
	{
		final I_AD_Reference referenceTarget = createReferenceSourceOrTarget("RefTargetName1", X_AD_Reference.VALIDATIONTYPE_TableValidation);
		final boolean isTableRecordIdTarget = false;
		final I_AD_RelationType relationType = createRelationType(isTableRecordIdTarget, null, referenceTarget);

		final RelationTypeRelatedDocumentsProvidersFactory relationTypeRelatedDocumentsProvidersFactory = new RelationTypeRelatedDocumentsProvidersFactory(
				newADReferenceService(ImmutableMap.of()),
				NullCustomizedWindowInfoMapRepository.instance);
		assertThatThrownBy(() -> relationTypeRelatedDocumentsProvidersFactory.findRelatedDocumentsProvider(relationType))
				.isInstanceOf(AdempiereException.class)
				.hasMessage("Assumption failure: sourceReferenceId is set");
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
