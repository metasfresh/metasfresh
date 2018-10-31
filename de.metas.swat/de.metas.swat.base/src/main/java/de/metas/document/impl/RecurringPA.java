package de.metas.document.impl;

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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.db.IDatabaseBL;
import org.compiere.model.I_C_Recurring;
import org.compiere.model.MRecurring;

import de.metas.document.IRecurringPA;
import de.metas.util.Services;

public final class RecurringPA implements IRecurringPA {

	private static final String SQL_TODAY = "SELECT r.* " //
			+ "\n FROM C_Recurring r "//
			+ "\n WHERE "//
			+ "\n    r.IsActive='Y' " //
			+ "\n    AND r.AD_Client_ID=? " //
			+ "\n    AND r.DateNextRun::date <= now()::date " //
			+ "\n    AND not exists ( " //
			+ "\n         select * from C_Recurring_Run rr "//
			+ "\n         where "//
			+ "\n            rr.C_Recurring_ID=r.C_Recurring_ID " //
			+ "\n            AND rr.DateDoc=r.DateNextRun::date "//
			+ "\n    )";

	public Collection<I_C_Recurring> retrieveForToday(final int adClientId,
			final String trxName) {

		final IDatabaseBL databaseBL = Services.get(IDatabaseBL.class);

		final List<MRecurring> recurrings = databaseBL.retrieveList(SQL_TODAY,
				new Object[] { adClientId }, MRecurring.class, trxName);

		return new ArrayList<I_C_Recurring>(recurrings);
	}
}
