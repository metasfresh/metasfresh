package de.metas.impexp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.OptionalInt;

import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.api.Params;
import org.compiere.model.I_I_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import com.google.common.collect.ImmutableList;

import de.metas.impexp.parser.ImpDataCell;
import de.metas.impexp.parser.ImpDataLine;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.organization.OrgId;
import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class DataImportServiceTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private ByteArrayResource toResource(final String string)
	{
		return new ByteArrayResource(string.getBytes());
	}

	@Nested
	public class simpleMockedProcessTest
	{
		private DataImportServiceTestContext<I_I_Product, MockedImportProcess<I_I_Product>> context;
		private MockedImportRecordsAsyncExecutor importRecordsAsyncExecutor;

		private void setupContext(final boolean manualImport)
		{
			importRecordsAsyncExecutor = new MockedImportRecordsAsyncExecutor();

			context = DataImportServiceTestContext.<I_I_Product, MockedImportProcess<I_I_Product>> builder()
					.importModelClass(I_I_Product.class)
					.impFormatId(ImportFormatBuilder.newInstance(I_I_Product.Table_Name)
							.name("productImportFormat")
							.manualImport(manualImport)
							.stringColumn("Value")
							.stringColumn("Name")
							.build())
					.importProcessFactory(() -> MockedImportProcess.<I_I_Product> builder()
							.importModelClass(I_I_Product.class)
							.onRun(() -> ImportProcessResult.newCollector("TargetTable")
									.importTableName(I_I_Product.Table_Name)
									.toResult())
							.build())
					.importRecordsAsyncExecutor(importRecordsAsyncExecutor)
					.build();
			context.createWindowForTable("TargetTable");
		}

		@Test
		public void manualImport()
		{
			final boolean manualImport = true;
			setupContext(manualImport);

			context.getDataImportService()
					.importDataFromResource(DataImportRequest.builder()
							.data(toResource("value1,name1"
									+ "\nvalue2,name2"))
							.dataImportConfigId(context.getDataImportConfigId())
							.clientId(ClientId.METASFRESH)
							.orgId(OrgId.ANY)
							.userId(UserId.METASFRESH)
							.completeDocuments(manualImport)
							.processImportRecordsSynchronously(false)
							.build());

			final List<MockedImportProcess<I_I_Product>> processRuns = context.getProcessRuns();
			assertThat(processRuns).hasSize(1);
			final MockedImportProcess<I_I_Product> processRun = processRuns.get(0);
			assertThat(processRun.isDeleteImportRecordsCalled()).isFalse();
			assertThat(processRun.isRunCalled()).isTrue();
			assertThat(processRun.isValidateOnly()).isTrue();
			assertThat(processRun.isCompleteDocuments()).isFalse();

			importRecordsAsyncExecutor.assertNoCalls();
		}

		@Test
		public void runSynchronous()
		{
			final boolean manualImport = false;
			setupContext(manualImport);

			final DataImportResult result = context.getDataImportService()
					.importDataFromResource(DataImportRequest.builder()
							.data(toResource("value1,name1"
									+ "\nvalue2,name2"))
							.dataImportConfigId(context.getDataImportConfigId())
							.clientId(ClientId.METASFRESH)
							.orgId(OrgId.ANY)
							.userId(UserId.METASFRESH)
							.completeDocuments(manualImport)
							.processImportRecordsSynchronously(true)
							.build());

			assertThat(result)
					.usingRecursiveComparison()
					.ignoringFields("duration")
					.isEqualTo(DataImportResult.builder()
							.dataImportConfigId(context.getDataImportConfigId())
							.duration(Duration.ZERO) // ignored anyways
							//
							.insertIntoImportTable(InsertIntoImportTableResult.builder()
									.fromResource(null)
									.toImportTableName(I_I_Product.Table_Name)
									.importFormatName("productImportFormat")
									.dataImportConfigId(context.getDataImportConfigId())
									//
									.duration(Duration.ZERO)
									.dataImportRunId(result.getInsertIntoImportTable().getDataImportRunId())
									.countTotalRows(2)
									.countValidRows(2)
									.build())
							.importRecordsValidation(ValidateImportRecordsResult.builder()
									.importTableName(I_I_Product.Table_Name)
									.countImportRecordsDeleted(0)
									.countImportRecordsWithValidationErrors(OptionalInt.of(0))
									.build())
							.actualImport(ActualImportRecordsResult.builder()
									.importTableName(I_I_Product.Table_Name)
									.targetTableName("TargetTable")
									.countImportRecordsConsidered(OptionalInt.of(0))
									.countInsertsIntoTargetTable(OptionalInt.of(0))
									.countUpdatesIntoTargetTable(OptionalInt.of(0))
									.build())
							//
							.build());

			final MockedInsertIntoImportTableService importTableAppenderService = context.getImportTableAppenderService();
			assertThat(importTableAppenderService.getLastRequestLines())
					.usingRecursiveComparison()
					.isEqualTo(ImmutableList.of(
							ImpDataLine.builder()
									.fileLineNo(1)
									.lineStr("value1,name1")
									.cell(ImpDataCell.value("value1")).cell(ImpDataCell.value("name1"))
									.build(),
							ImpDataLine.builder()
									.fileLineNo(2)
									.lineStr("value2,name2")
									.cell(ImpDataCell.value("value2")).cell(ImpDataCell.value("name2"))
									.build()));

			final List<MockedImportProcess<I_I_Product>> processRuns = context.getProcessRuns();
			assertThat(processRuns).hasSize(1);
			final MockedImportProcess<I_I_Product> processRun = processRuns.get(0);
			assertThat(processRun.isDeleteImportRecordsCalled()).isFalse();
			assertThat(processRun.isRunCalled()).isTrue();
			assertThat(processRun.isValidateOnly()).isFalse();
			assertThat(processRun.isCompleteDocuments()).isFalse();

			importRecordsAsyncExecutor.assertNoCalls();
		}

		@Test
		public void runAsync()
		{
			final boolean manualImport = false;
			setupContext(manualImport);

			context.getDataImportService()
					.importDataFromResource(DataImportRequest.builder()
							.data(toResource("value1,name1"
									+ "\nvalue2,name2"))
							.dataImportConfigId(context.getDataImportConfigId())
							.clientId(ClientId.METASFRESH)
							.orgId(OrgId.ANY)
							.userId(UserId.METASFRESH)
							.completeDocuments(false)
							.processImportRecordsSynchronously(false)
							.build());

			final List<MockedImportProcess<I_I_Product>> processRuns = context.getProcessRuns();
			assertThat(processRuns).hasSize(1);
			final MockedImportProcess<I_I_Product> processRun = processRuns.get(0);
			assertThat(processRun.isDeleteImportRecordsCalled()).isFalse();
			assertThat(processRun.isRunCalled()).isTrue();
			assertThat(processRun.isValidateOnly()).isTrue();
			assertThat(processRun.isCompleteDocuments()).isFalse();

			importRecordsAsyncExecutor.assertOneCall();
			ImportRecordsRequest scheduledRequest = importRecordsAsyncExecutor.getSingleScheduledRequest();
			assertThat(scheduledRequest)
					.usingRecursiveComparison()
					.isEqualTo(ImportRecordsRequest.builder()
							.importTableName(I_I_Product.Table_Name)
							.selectionId(processRun.getSelectionId())
							.notifyUserId(UserId.METASFRESH)
							.completeDocuments(false)
							.additionalParameters(Params.EMPTY)
							.build());
		}
	}
}
