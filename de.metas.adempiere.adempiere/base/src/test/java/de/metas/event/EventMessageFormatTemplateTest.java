package de.metas.event;

import java.util.Map;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class EventMessageFormatTemplateTest
{
	private ObjectMapper jsonMapper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		jsonMapper = new ObjectMapper();
	}

	/** @return record reference as map, e.g. <code>{ "tableName":"C_Invoice", "recordId":123 }</code> */
	private Map<String, Object> createTableRecordReferenceAsMap(final String tableName, final int recordId)
	{
		try
		{
			final TableRecordReference ref = TableRecordReference.of(tableName, recordId);
			final String refAsJson = jsonMapper.writeValueAsString(ref);
			@SuppressWarnings("unchecked")
			final Map<String, Object> refAsMap = jsonMapper.readValue(refAsJson, Map.class);
			return refAsMap;
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	/**
	 * Make sure {@link EventMessageFormatTemplate} supports {@link TableRecordReference} which is {@link Map} of tableName and recordId.
	 * Usually that's the case when the parameters were deserialized and the the TableRecordRefererence was deserialized as Map.
	 */
	@Test
	public void test_TableRecordReferecenceAsMap()
	{
		final Map<String, Object> args = ImmutableMap.<String, Object> builder()
				.put("v1", createTableRecordReferenceAsMap("C_Invoice", 1234))
				.build();

		final String resultActual = new MockedEventMessageFormat()
				.setTableRecordReferenceFormatter(recordRef -> recordRef.getTableName() + "-" + recordRef.getRecord_ID())
				.setLeftBrace("{")
				.setRightBrace("}")
				.setArguments(args)
				.format("test {v1}");

		Assert.assertEquals("test C_Invoice-1234", resultActual);
	}

	@SuppressWarnings("serial")
	private static class MockedEventMessageFormat extends EventMessageFormatTemplate
	{
		private Function<ITableRecordReference, String> tableRecordReferenceFormatter = recordRef -> recordRef.getTableName() + "_" + recordRef.getRecord_ID();

		public MockedEventMessageFormat setTableRecordReferenceFormatter(@NonNull final Function<ITableRecordReference, String> tableRecordReferenceFormatter)
		{
			this.tableRecordReferenceFormatter = tableRecordReferenceFormatter;
			return this;
		}

		@Override
		protected final String formatTableRecordReference(final ITableRecordReference recordRef)
		{
			return tableRecordReferenceFormatter.apply(recordRef);
		}

	}
}
