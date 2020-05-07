package de.metas.migration.async;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.async
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

public class MockedExecuteSQLWorkpackageProcessor extends ExecuteSQLWorkpackageProcessor
{
	public static final List<I_C_Queue_WorkPackage> processedWorkpackages = Collections.synchronizedList(new ArrayList<I_C_Queue_WorkPackage>());
	public static final Map<Integer, ExecuteSQLWorkpackageExpectation> expectations = Collections.synchronizedMap(new HashMap<Integer, ExecuteSQLWorkpackageExpectation>());

	private static final int DEFAULT_ExpectationKey = -100;

	public static final void reset()
	{
		processedWorkpackages.clear();
		expectations.clear();
	}

	public static final void addExpectation(final I_C_Queue_WorkPackage workpackage, final ExecuteSQLWorkpackageExpectation expectation)
	{
		expectations.put(workpackage.getC_Queue_WorkPackage_ID(), expectation);
	}

	public static final void setDefaultExpectation(final ExecuteSQLWorkpackageExpectation expectation)
	{
		expectations.put(DEFAULT_ExpectationKey, expectation);
	}

	private static final ExecuteSQLWorkpackageExpectation getExpectation(final I_C_Queue_WorkPackage workpackage)
	{
		ExecuteSQLWorkpackageExpectation expectation = expectations.get(workpackage.getC_Queue_WorkPackage_ID());
		if(expectation != null)
		{
			return expectation;
		}

		expectation = expectations.get(DEFAULT_ExpectationKey);

		Check.assumeNotNull(expectation, "expectation not null");
		return expectation;
	}

	public static interface ExecuteSQLWorkpackageExpectation
	{
		int onExecuteSql(final String sql, final String trxName);
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		processedWorkpackages.add(workPackage);

		return super.processWorkPackage(workPackage, localTrxName);
	}

	@Override
	int executeSql(final String sql)
	{
		final I_C_Queue_WorkPackage workpackage = getC_Queue_WorkPackage();
		final ExecuteSQLWorkpackageExpectation expectation = getExpectation(workpackage);
		return expectation.onExecuteSql(sql, ITrx.TRXNAME_ThreadInherited);
	}
}