/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.workpackage;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.olcand.C_OLCand_StepDefData;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_Queue_WorkPackage_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final C_Queue_WorkPackage_StepDefData workPackageTable;
	private final C_Queue_Element_StepDefData queueElementTable;
	private final C_OLCand_StepDefData candidateTable;

	public C_Queue_WorkPackage_StepDef(
			@NonNull final C_Queue_WorkPackage_StepDefData workPackageTable,
			@NonNull final C_Queue_Element_StepDefData queueElementTable,
			@NonNull final C_OLCand_StepDefData candidateTable)
	{
		this.workPackageTable = workPackageTable;
		this.queueElementTable = queueElementTable;
		this.candidateTable = candidateTable;
	}

	@And("locate last C_Queue_WorkPackage by enqueued element")
	public void locate_C_Queue_WorkPackage_by_enqueued_element(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String workPackageIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String packageProcessorInternalName = DataTableUtil.extractStringForColumnName(row, I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID + "." + I_C_Queue_PackageProcessor.COLUMNNAME_InternalName);
			final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_AD_Table_ID + "." + I_AD_Table.COLUMNNAME_TableName);
			final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_Element.COLUMNNAME_Record_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final I_AD_Table adTable = tableDAO.retrieveTable(tableName);

			switch (adTable.getTableName())
			{
				case I_C_OLCand.Table_Name:
					final I_C_OLCand candidate = candidateTable.get(recordIdentifier);
					final TableRecordReference candidateReference = TableRecordReference.of(candidate);

					resolveWorkPackageByQueueElementAndPackageProcessor(workPackageIdentifier, candidateReference, packageProcessorInternalName);
					break;

				default:
					throw new AdempiereException("Table not supported! TableName:" + tableName);
			}
		}
	}

	@And("validate enqueued elements for C_Queue_WorkPackage")
	public void validate_enqueued_elements_for_C_Queue_WorkPackage(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String queueElementIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_Element.COLUMNNAME_C_Queue_Element_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String workPackageIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Queue_WorkPackage workPackage = workPackageTable.get(workPackageIdentifier);
			final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID());

			final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_AD_Table_ID + "." + I_AD_Table.COLUMNNAME_TableName);
			final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_Element.COLUMNNAME_Record_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final I_AD_Table adTable = tableDAO.retrieveTable(tableName);

			switch (adTable.getTableName())
			{
				case I_C_OLCand.Table_Name:
					final I_C_OLCand candidate = candidateTable.get(recordIdentifier);
					final TableRecordReference candidateReference = TableRecordReference.of(candidate);

					validateC_Queue_Element(workPackageId, candidateReference, queueElementIdentifier);
					break;

				default:
					throw new AdempiereException("Table not supported! TableName:" + tableName);
			}

		}
	}

	private void resolveWorkPackageByQueueElementAndPackageProcessor(
			@NonNull final String workPackageIdentifier,
			@NonNull final TableRecordReference reference,
			@NonNull final String packageProcessorName)
	{
		final I_C_Queue_PackageProcessor packageProcessor = queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_InternalName, packageProcessorName)
				.create()
				.firstOnlyNotNull(I_C_Queue_PackageProcessor.class);

		final IQuery<I_C_Queue_Block> queueBlockWithGivenPackageProcessorQuery = queryBL.createQueryBuilder(I_C_Queue_Block.class)
				.addEqualsFilter(I_C_Queue_Block.COLUMN_C_Queue_PackageProcessor_ID, packageProcessor.getC_Queue_PackageProcessor_ID())
				.create();

		final I_C_Queue_WorkPackage workPackage = queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, reference.getAD_Table_ID())
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, reference.getRecord_ID())
				.andCollect(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID, I_C_Queue_WorkPackage.class)
				.addInSubQueryFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_Block_ID, I_C_Queue_Block.COLUMNNAME_C_Queue_Block_ID, queueBlockWithGivenPackageProcessorQuery)
				.orderByDescending(I_C_Queue_WorkPackage.COLUMNNAME_Created)
				.create()
				.firstOptional(I_C_Queue_WorkPackage.class)
				.orElseThrow(() -> new AdempiereException("No C_Queue_WorkPackage found for TableRecordReference and PackageProcessorName")
						.appendParametersToMessage()
						.setParameter("TableRecordReference", reference)
						.setParameter("PackageProcessorName", packageProcessorName));

		workPackageTable.putOrReplace(workPackageIdentifier, workPackage);
	}

	private void validateC_Queue_Element(
			@NonNull final QueueWorkPackageId workPackageId,
			@NonNull final TableRecordReference recordReference,
			@NonNull final String queueElementIdentifier)
	{
		final I_C_Queue_Element queueElement = queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_C_Queue_WorkPackage_ID, workPackageId)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.create()
				.firstOptional(I_C_Queue_Element.class)
				.orElseThrow(() -> new AdempiereException("No C_Queue_Element found for QueueWorkPackageId and TableRecordReference")
						.appendParametersToMessage()
						.setParameter("QueueWorkPackageId", workPackageId)
						.setParameter("TableRecordReference", recordReference));

		assertThat(queueElement).isNotNull();
		queueElementTable.putOrReplace(queueElementIdentifier, queueElement);
	}
}
