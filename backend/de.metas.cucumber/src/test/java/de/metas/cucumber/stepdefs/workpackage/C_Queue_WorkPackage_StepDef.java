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

import com.google.common.collect.ImmutableSet;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.*;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.olcand.C_OLCand_StepDefData;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class C_Queue_WorkPackage_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final C_Queue_WorkPackage_StepDefData workPackageTable;
	private final C_Queue_Element_StepDefData queueElementTable;
	private final C_OLCand_StepDefData candidateTable;
	private final C_Queue_Processor_StepDefData processorTable;

	public C_Queue_WorkPackage_StepDef(
			@NonNull final C_Queue_WorkPackage_StepDefData workPackageTable,
			@NonNull final C_Queue_Element_StepDefData queueElementTable,
			@NonNull final C_OLCand_StepDefData candidateTable,
			final @NonNull C_Queue_Processor_StepDefData processorTable)
	{
		this.workPackageTable = workPackageTable;
		this.queueElementTable = queueElementTable;
		this.candidateTable = candidateTable;
		this.processorTable = processorTable;
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

		final IQuery<I_C_Queue_PackageProcessor> queueryWithGivenPackageProcessorQuery = queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID, packageProcessor.getC_Queue_PackageProcessor_ID())
				.create();

		final I_C_Queue_WorkPackage workPackage = queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, reference.getAD_Table_ID())
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, reference.getRecord_ID())
				.andCollect(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID, I_C_Queue_WorkPackage.class)
				.addInSubQueryFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID, queueryWithGivenPackageProcessorQuery)
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

	@And("^after not more than (.*)s, there are no C_Queue_WorkPackage pending or running in queue (.*)$")
	public void there_are_no_C_Queue_WorkPackage_Pending_Running(final int nrOfSeconds, @NonNull final String queueProcessorIdentifier) throws InterruptedException
	{
		final I_C_Queue_Processor processor = processorTable.get(queueProcessorIdentifier);

		final Set<Integer> assignedPackageProcessorsIds = queryBL.createQueryBuilder(I_C_Queue_Processor_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_Processor_ID, processor.getC_Queue_Processor_ID())
				.create()
				.stream()
				.map(I_C_Queue_Processor_Assign::getC_Queue_PackageProcessor_ID)
				.collect(ImmutableSet.toImmutableSet());

		assertThat(assignedPackageProcessorsIds.size()).isGreaterThan(0);

		final Supplier<Boolean> noPendingOrRunningPackage = () -> {

			final IQueryFilter<I_C_Queue_WorkPackage> isNotDoneYet = queryBL.createCompositeQueryFilter(I_C_Queue_WorkPackage.class)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, false)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsError, false)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsReadyForProcessing, true);

			return queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
					.addInArrayFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, assignedPackageProcessorsIds)
					.filter(isNotDoneYet)
					.create()
					.count() == 0;
		};

		StepDefUtil.tryAndWait(nrOfSeconds, 1000, noPendingOrRunningPackage);
	}
}
