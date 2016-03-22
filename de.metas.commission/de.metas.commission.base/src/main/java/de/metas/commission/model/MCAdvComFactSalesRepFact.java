package de.metas.commission.model;

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


import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

public class MCAdvComFactSalesRepFact extends X_C_AdvComFact_SalesRepFact
{
	private static final Logger logger = LogManager.getLogger(MCAdvComFactSalesRepFact.class);


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MCAdvComFactSalesRepFact(final Properties ctx,
			final int C_AdvComFact_SalesRepFact_ID, final String trxName)
	{
		super(ctx, C_AdvComFact_SalesRepFact_ID, trxName);
	}

	public MCAdvComFactSalesRepFact(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static MCAdvComFactSalesRepFact createIfNotExists(
			final I_C_AdvCommissionFact comFact,
			final I_C_AdvComSalesRepFact salesrepFact)
	{
		final MCAdvComFactSalesRepFact existingFact = MCAdvComFactSalesRepFact.retrieve(comFact, salesrepFact);

		if (existingFact != null)
		{
			return existingFact;
		}

		return create(comFact, salesrepFact);
	}

	public static MCAdvComFactSalesRepFact create(
			final I_C_AdvCommissionFact comFact,
			final I_C_AdvComSalesRepFact salesrepFact)
	{
		assert comFact.getC_AdvCommissionFact_ID() > 0 && salesrepFact.getC_AdvComSalesRepFact_ID() > 0;

		final Properties ctx = InterfaceWrapperHelper.getCtx(comFact, true); // useClientOrgFromModel = true
		final String trxName = InterfaceWrapperHelper.getTrxName(comFact);

		final MCAdvComFactSalesRepFact comFactSalesRepFact = new MCAdvComFactSalesRepFact(ctx, 0, trxName);
		// comFactSalesRepFact.setClientOrg(salesrepFact); // client/org is containted in ctx
		comFactSalesRepFact.setC_AdvCommissionFact_ID(comFact.getC_AdvCommissionFact_ID());
		comFactSalesRepFact.setC_AdvComSalesRepFact_ID(salesrepFact.getC_AdvComSalesRepFact_ID());
		comFactSalesRepFact.saveEx();

		return comFactSalesRepFact;
	}

	public static MCAdvComFactSalesRepFact retrieve(
			final I_C_AdvCommissionFact comFact,
			final I_C_AdvComSalesRepFact salesrepFact)
	{
		assert comFact.getC_AdvCommissionFact_ID() > 0 && salesrepFact.getC_AdvComSalesRepFact_ID() > 0;

		final String whereClause = I_C_AdvComFact_SalesRepFact.COLUMNNAME_C_AdvCommissionFact_ID + "=? AND " + I_C_AdvComFact_SalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID + "=?";

		final Object[] params = { comFact.getC_AdvCommissionFact_ID(), salesrepFact.getC_AdvComSalesRepFact_ID() };

		final Properties ctx = InterfaceWrapperHelper.getCtx(comFact);
		final String trxName = InterfaceWrapperHelper.getTrxName(comFact);

		return new Query(ctx, I_C_AdvComFact_SalesRepFact.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstOnly();
	}

	public static void deleteFor(final I_C_AdvComSalesRepFact salesRepFact)
	{
		assert salesRepFact.getC_AdvComSalesRepFact_ID() > 0 : salesRepFact;

		final String sql = "DELETE FROM " + I_C_AdvComFact_SalesRepFact.Table_Name + " WHERE " + I_C_AdvComFact_SalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID + "=?";

		final Object[] param = { salesRepFact.getC_AdvComSalesRepFact_ID() };

		final String trxName = InterfaceWrapperHelper.getTrxName(salesRepFact);
		final int no = DB.executeUpdateEx(sql, param, trxName);

		logger.info("Deleted " + no + " records for " + salesRepFact);
	}

}
