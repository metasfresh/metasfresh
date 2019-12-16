package org.adempiere.util.lang.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.util.Services;

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

public class TableRecordReferenceTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testJsonSerializeDeserialize() throws Exception
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.save(invoice);

		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);

		final ObjectMapper jsonMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonMapper.writeValueAsString(invoiceRef);

		final TableRecordReference invoiceRefFromJson = jsonMapper.readValue(json, TableRecordReference.class);

		assertThat(invoiceRefFromJson).isEqualTo(invoiceRef);
	}

	@Test
	public void ofTableNameAndRecordId_check_lazyTableIdIsLoaded()
	{
		final int invoiceTableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);

		final TableRecordReference recordRef = TableRecordReference.of(I_C_Invoice.Table_Name, 123);
		assertThat(recordRef.getTableName()).isEqualTo(I_C_Invoice.Table_Name);
		assertThat(recordRef.getAD_Table_ID()).isEqualTo(invoiceTableId);
	}

	@Nested
	public class equals
	{
		@Test
		public void test()
		{
			final int invoiceTableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);

			assertThat(TableRecordReference.of(I_C_Invoice.Table_Name, 123))
					.isEqualTo(TableRecordReference.of(invoiceTableId, 123));
		}
	}
}
