package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.adempiere.util.trxConstraints.api.impl.TrxConstraintsBL;
import org.adempiere.util.trxConstraints.api.impl.TrxConstraintsDisabled;
import org.eevolution.mrp.expectations.MRPExpectation;
import org.junit.Assert;

import de.metas.material.planning.IMaterialPlanningContext;

/**
 * Helper class used to run MRP and do checkings.
 * 
 * @author tsa
 *
 */
public class MRPTestRun
{
	// services
	private final transient ITrxConstraintsBL trxConstraintsBL = Services.get(ITrxConstraintsBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final MRPTestHelper helper;
	private IMaterialPlanningContext _mrpContext;

	private boolean assertMRPDemandsNotAvailable = true;
	/** expectations to be checked after MRP run */
	private List<MRPExpectation<Object>> expectations = new ArrayList<>();

	public MRPTestRun(final MRPTestHelper helper)
	{
		super();

		Check.assumeNotNull(helper, "helper not null");
		this.helper = helper;
	}

	public void run()
	{
		final IMaterialPlanningContext mrpContext = getMRPContext();

		//
		// Actually execute MRP
		enableTrxContraints();
		try
		{
			helper.mrpExecutorService.run(mrpContext);
		}
		finally
		{
			disableTrxConstraints();
		}

		//
		// Make sure there were no MRP errors
		helper.mrpExecutor.assertNoErrors();

		//
		// Make sure all MRP demands were considered
		if (assertMRPDemandsNotAvailable)
		{
			helper.newMRPExpectation()
					.plant(mrpContext.getPlant())
					.warehouse(mrpContext.getM_Warehouse())
					.product(mrpContext.getM_Product())
					.assertMRPDemandsNotAvailable();
		}

		//
		// Assert MRP expectations
		assertMRPExpectations();
	}

	private IMaterialPlanningContext getMRPContext()
	{
		if (_mrpContext != null)
		{
			return _mrpContext;
		}

		return helper.createMutableMRPContext();
	}

	public MRPTestRun setMRPContext(final IMaterialPlanningContext mrpContext)
	{
		this._mrpContext = mrpContext;
		return this;
	}

	public MRPTestRun setAssertMRPDemandsNotAvailable(final boolean assertMRPDemandsNotAvailable)
	{
		this.assertMRPDemandsNotAvailable = assertMRPDemandsNotAvailable;
		return this;
	}

	private void enableTrxContraints()
	{
		if (trxConstraintsBL.isDisabled(trxConstraintsBL.getConstraints()))
		{
			sysConfigBL.setValue(TrxConstraintsBL.SYSCONFIG_TRX_CONSTRAINTS_DISABLED, false, 0);
		}

		trxConstraintsBL.saveConstraints();
		final ITrxConstraints trxConstraints = trxConstraintsBL.getConstraints();
		Assert.assertNotSame("TrxConstraints shall be enabled", TrxConstraintsDisabled.get(), trxConstraints);
		trxConstraints
				.setActive(true)
				.setOnlyAllowedTrxNamePrefixes(true)
				.setMaxTrx(1)
				.setMaxSavepoints(1)
				.setAllowTrxAfterThreadEnd(false);

		;
	}

	private void disableTrxConstraints()
	{
		trxConstraintsBL.restoreConstraints();
		sysConfigBL.setValue(TrxConstraintsBL.SYSCONFIG_TRX_CONSTRAINTS_DISABLED, true, 0);
	}

	public MRPExpectation<Object> newMRPExpectation()
	{
		final MRPExpectation<Object> expectation = helper.newMRPExpectation();
		expectations.add(expectation);
		return expectation;
	}

	private final void assertMRPExpectations()
	{
		for (final MRPExpectation<Object> expectation : expectations)
		{
			expectation.assertExpected();
		}
	}
}
