package de.metas.document.process;

/*
 * #%L
 * de.metas.swat.base
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


import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.test.UnitTestTools;
import org.compiere.model.I_C_Recurring;
import org.compiere.util.Env;
import org.junit.Test;

import de.metas.document.IRecurringPA;
import de.metas.document.process.RecurringAuto;

public class RecurringAutoTest {

	@Test
	public void recurringRun() {
		final String trxName = "trxName";
		final int clientIdToUse = 10;
		final int clientIdEnv = 20;
		Env.setContext(Env.getCtx(), "#AD_Client_ID", clientIdEnv);

		final List<Object> mocks = new ArrayList<Object>();

		final IRecurringPA recurringPA = UnitTestTools.serviceMock(IRecurringPA.class,
				mocks);
		final List<I_C_Recurring> recurring = new ArrayList<I_C_Recurring>();
		expect(recurringPA.retrieveForToday(clientIdToUse, trxName)).andReturn(
				recurring);

		replay(mocks.toArray());
		final RecurringAuto recurringAuto = new RecurringAuto();
		recurringAuto.recurringRun(clientIdToUse, trxName);
		verify(mocks.toArray());

	}
}
