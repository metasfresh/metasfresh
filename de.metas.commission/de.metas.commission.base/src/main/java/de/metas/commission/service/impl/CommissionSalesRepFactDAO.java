package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.Properties;

import org.compiere.model.Query;

import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_Sponsor;

public class CommissionSalesRepFactDAO extends AbstractCommissionSalesRepFactDAO
{
	@Override
	public I_C_AdvComSalesRepFact retrieveLatestAtDate(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName,
			final Timestamp date,
			final String trxName,
			final String... status)
	{
		final StringBuilder wc = new StringBuilder();

		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_DateAcct + "<=?");

		appendStatus(wc, status);

		final Object[] params = { sponsor.getC_Sponsor_ID(), factName, comSystem.getC_AdvComSystem_ID(), date };

		final String orderBy = I_C_AdvComSalesRepFact.COLUMNNAME_DateAcct + " DESC, " + I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID + " DESC";

		return new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.first(I_C_AdvComSalesRepFact.class);
	}

}
