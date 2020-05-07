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


import java.util.Collection;

import org.adempiere.util.Services;
import org.compiere.model.I_C_Recurring;

import de.metas.document.IRecurringBL;
import de.metas.document.IRecurringPA;
import de.metas.process.JavaProcess;

public class RecurringAuto extends JavaProcess {

	@Override
	protected String doIt() throws Exception {

		final String result = recurringRun(getAD_Client_ID(), get_TrxName());
		return result;
	}

	@Override
	protected void prepare() {
		// Nothing to to
	}

	String recurringRun(final int adClientId, final String trxName) {

		int count = 0;
		int thisTime = 0;

		while ((thisTime = run(adClientId, trxName)) != 0) {

			count += thisTime;
		}
		return "Performed " + count + " recurring runs";
	}

	private int run(final int adClientId, final String trxName) {

		final IRecurringPA recurringPA = Services.get(IRecurringPA.class);
		final IRecurringBL recurringBL = Services.get(IRecurringBL.class);

		// get recurring docs that need to run today.
		final Collection<I_C_Recurring> recurringDocs = recurringPA
				.retrieveForToday(adClientId, trxName);

		for (final I_C_Recurring recurring : recurringDocs) {

			recurringBL.recurringRun(recurring);
		}
		return recurringDocs.size();
	}
}
