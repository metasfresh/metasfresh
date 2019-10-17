package org.adempiere.server.rpl.trx.spi.impl;

/*
 * #%L
 * de.metas.fresh.base
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


import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_C_OLCand;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueSolver;
import org.adempiere.server.rpl.trx.spi.NoOpIssueSolver;

/**
 * 
 * <b>CURRENTLY NOT USED</b>, because the user shall first set the <code>C_OLCand's</code> values, then verify them and then mark the issues as solved, using {@link NoOpIssueSolver}.
 *
 */
public class OLCandIssueSolver implements IReplicationIssueSolver<I_C_OLCand>
{
	@Override
	public void solveIssues(final IReplicationIssueAware recordWithIssues, final IReplicationIssueSolverParams params)
	{
		final I_C_OLCand olCand = InterfaceWrapperHelper.create(recordWithIssues, I_C_OLCand.class);

		// Partner
		final int bpartnerId = params.getParameterAsInt(I_C_OLCand.COLUMNNAME_C_BPartner_ID, -1);
		if (bpartnerId > 0)
		{
			olCand.setC_BPartner_ID(bpartnerId);

			// Location
			final int bpartnerLocationId = params.getParameterAsInt(I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID, -1);
			olCand.setC_BPartner_Location_ID(bpartnerLocationId);
		}

		// DatePromised
		final Timestamp datePromised = params.getParameterAsTimestamp(I_C_OLCand.COLUMNNAME_DatePromised);

		if (datePromised != null)
		{
			olCand.setDatePromised(datePromised);
		}

		InterfaceWrapperHelper.save(olCand);
	}
}
