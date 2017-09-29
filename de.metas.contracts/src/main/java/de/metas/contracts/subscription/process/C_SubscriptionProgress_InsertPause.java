package de.metas.contracts.subscription.process;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.subscription.impl.SubscriptionCommand;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;

public class C_SubscriptionProgress_InsertPause
		extends C_SubscriptionProgressBase
		implements IProcessPrecondition
{
	@Param(parameterName = "DateGeneral", mandatory = true)
	private Timestamp dateFrom;

	@Param(parameterName = "DateGeneral", mandatory = true, parameterTo = true)
	private Timestamp dateTo;

	@Override
	protected String doIt()
	{
		final I_C_Flatrate_Term term = getTermFromProcessInfo();
		SubscriptionCommand.get().insertPause(term, dateFrom, dateTo);

		return MSG_OK;
	}
}
