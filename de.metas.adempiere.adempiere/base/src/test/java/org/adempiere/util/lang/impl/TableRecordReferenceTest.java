package org.adempiere.util.lang.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.adempiere.model.I_C_Invoice;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class TableRecordReferenceTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}
	
	@Test
	public void testJsonSerializeDeserialize() throws Exception
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(invoice);
		
		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);
		
		final ObjectMapper jsonMapper = new ObjectMapper();
		final String json = jsonMapper.writeValueAsString(invoiceRef);
		
		final TableRecordReference invoiceRefFromJson = jsonMapper.readValue(json, TableRecordReference.class);
		
		Assert.assertEquals(invoiceRef, invoiceRefFromJson);
	}
}
