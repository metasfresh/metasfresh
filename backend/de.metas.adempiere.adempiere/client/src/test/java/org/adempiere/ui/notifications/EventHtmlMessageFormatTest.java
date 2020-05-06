package org.adempiere.ui.notifications;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.event.Event;
import de.metas.inout.model.I_M_InOut;

public class EventHtmlMessageFormatTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Simple AD_Message to HTML with URLs test.
	 */
	@Test
	public void test()
	{
		final ITableRecordReference inoutRecord = TableRecordReference.of(createM_InOut(12345));
		Event event = Event.builder()
				.setDetailADMessage("Lieferung {0} für Partner {1}&{2} wurde erstellt.", inoutRecord, "12345", "PartnerName")
				.build();

		final String summaryFormatted = EventHtmlMessageFormat.newInstance()
				.setArguments(event.getProperties())
				.format(event.getDetailADMessage());

		final int adTableId = inoutRecord.getAD_Table_ID();
		Assert.assertEquals("Lieferung <a href=\"http://adempiere/ShowWindow?AD_Table_ID=" + adTableId + "%26WhereClause=M_InOut_ID%3D12345\">doc12345</a> für Partner 12345&amp;PartnerName wurde erstellt.",
				summaryFormatted);
	}

	private final I_M_InOut createM_InOut(final int inoutId)
	{
		final I_M_InOut inout = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		final String documentNo = "doc" + inoutId;
		inout.setM_InOut_ID(inoutId);
		inout.setDocumentNo(documentNo);
		InterfaceWrapperHelper.save(inout);
		return inout;
	}

}
