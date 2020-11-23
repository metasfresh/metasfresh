package de.metas.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.process.ProcessExecutionResult.DisplayQRCode;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ProcessExecutionResultTest
{
	private ObjectMapper jsonMapper = null;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		jsonMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	private String toJson(final ProcessExecutionResult result) throws JsonProcessingException
	{
		// System.out.println("json: \n" + json);
		return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
	}

	private ProcessExecutionResult fromJson(final String json) throws IOException
	{
		// System.out.println("result (from json): " + resultFromJson);
		return jsonMapper.readValue(json, ProcessExecutionResult.class);
	}

	private void assertEqualsAsJson(final ProcessExecutionResult expected, final ProcessExecutionResult actual) throws JsonProcessingException
	{
		Assert.assertEquals(toJson(expected), toJson(actual));
	}

	private ProcessExecutionResult createTestResult()
	{
		final ProcessExecutionResult result = ProcessExecutionResult.newInstanceForADPInstanceId(PInstanceId.ofRepoId(12345));
		result.setRecordToSelectAfterExecution(createDummyTableRecordReference());
		result.markAsError("error summary1");
		result.setReportData(new byte[] { 1, 2, 3 }, "report.pdf", "application/pdf");
		//
		result.setRecordsToOpen(createDummyTableRecordReferenceList(3), 1234);
		//
		result.setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId("123-dummyIncludedViewId")
				.profileId("dummyProfile")
				.target(ViewOpenTarget.IncludedView)
				.build());
		//
		result.setDisplayQRCode(DisplayQRCode.builder()
				.code("some dummy code")
				.build());
		return result;
	}

	private static TableRecordReference createDummyTableRecordReference()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(invoice);
		return TableRecordReference.of(invoice);
	}

	private static List<TableRecordReference> createDummyTableRecordReferenceList(final int size)
	{
		final List<TableRecordReference> list = new ArrayList<>();
		for (int i = 0; i < size; i++)
		{
			list.add(createDummyTableRecordReference());
		}
		return list;
	}

	@Test
	public void testJsonSerializeDeserialize() throws Exception
	{
		final ProcessExecutionResult result = createTestResult();

		//
		// System.out.println("result: " + result);

		final String json = toJson(result);

		final ProcessExecutionResult resultFromJson = fromJson(json);

		// Compare
		Assert.assertEquals(result.getPinstanceId(), resultFromJson.getPinstanceId());
		Assert.assertEquals(result.getRecordToSelectAfterExecution(), resultFromJson.getRecordToSelectAfterExecution());
		Assert.assertEquals(result.getSummary(), resultFromJson.getSummary());
		Assert.assertEquals(result.isError(), resultFromJson.isError());
		Assert.assertEquals(result.isErrorWasReportedToUser(), resultFromJson.isErrorWasReportedToUser());
		Assert.assertEquals(result.isShowProcessLogs(), resultFromJson.isShowProcessLogs());
		Assert.assertEquals(result.isRefreshAllAfterExecution(), resultFromJson.isRefreshAllAfterExecution());
		//
		Assert.assertEquals(result.getReportData(), resultFromJson.getReportData());
		Assert.assertEquals(result.getReportFilename(), resultFromJson.getReportFilename());
		Assert.assertEquals(result.getReportContentType(), resultFromJson.getReportContentType());
		//
		Assert.assertEquals(result.getRecordToSelectAfterExecution(), resultFromJson.getRecordToSelectAfterExecution());
		//
		Assert.assertEquals(result.getRecordsToOpen(), resultFromJson.getRecordsToOpen());
		//
		Assert.assertEquals(result.getWebuiViewToOpen(), resultFromJson.getWebuiViewToOpen());
		//
		Assert.assertEquals(result.getDisplayQRCode(), resultFromJson.getDisplayQRCode());
		//
		// Assert.assertEquals(result.get, resultFromJson.get);

		assertEqualsAsJson(result, resultFromJson);
	}

	@Test
	public void test_updateFrom() throws JsonProcessingException
	{
		final ProcessExecutionResult result = createTestResult();

		final ProcessExecutionResult resultCopy = ProcessExecutionResult.newInstanceForADPInstanceId(result.getPinstanceId());
		resultCopy.updateFrom(result);

		assertEqualsAsJson(result, resultCopy);
	}
}
