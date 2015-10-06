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
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;

public class MCAdvCommissionPayrollLine extends X_C_AdvCommissionPayrollLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2982388282933349098L;

	public static List<MCAdvCommissionPayrollLine> retrieveFor(final MCAdvCommissionPayroll payroll)
	{
		final String whereClause = I_C_AdvCommissionPayrollLine.COLUMNNAME_C_AdvCommissionPayroll_ID + "=?";

		final int param = payroll.get_ID();

		final String orderBy = I_C_AdvCommissionPayrollLine.COLUMNNAME_Line;

		return new Query(payroll.getCtx(), I_C_AdvCommissionPayrollLine.Table_Name, whereClause, payroll.get_TrxName())
				.setParameters(param)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list();
	}

	public static List<MCAdvCommissionPayrollLine> retrieveFor(final I_C_AdvCommissionTerm commissionTerm, final int bPartnerId)
	{
		final String whereClause = //
		I_C_AdvCommissionPayrollLine.COLUMNNAME_C_AdvCommissionTerm_ID + "=? AND " //
				+ I_C_AdvCommissionPayrollLine.COLUMNNAME_Processed + "!='Y' AND " //
				+ I_C_AdvCommissionPayrollLine.COLUMNNAME_C_AdvCommissionPayroll_ID + " in (" //
				+ "    select C_AdvCommissionPayroll_ID " //
				+ "    from C_AdvCommissionPayroll c " //
				+ "    where c." //
				+ I_C_AdvCommissionPayroll.COLUMNNAME_C_BPartner_ID + "=?" + " )";

		final Properties ctx = InterfaceWrapperHelper.getCtx(commissionTerm);
		final String trxName = InterfaceWrapperHelper.getTrxName(commissionTerm);
		return new Query(ctx, I_C_AdvCommissionPayrollLine.Table_Name, whereClause, trxName)
				.setParameters(commissionTerm.getC_AdvCommissionTerm_ID(), bPartnerId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvCommissionPayrollLine.COLUMNNAME_C_AdvCommissionPayrollLine_ID)
				.list();
	}

	public MCAdvCommissionPayrollLine(final Properties ctx, final int C_AdvCommissionPayrollLine_ID, final String trxName)
	{
		super(ctx, C_AdvCommissionPayrollLine_ID, trxName);
	}

	public MCAdvCommissionPayrollLine(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Cached
	public MCAdvCommissionPayroll getParent()
	{
		return (MCAdvCommissionPayroll)getC_AdvCommissionPayroll();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MCAdvCommissionPayrollLine[Id=");
		sb.append(get_ID());

		sb.append(", C_Currency_ID=");
		sb.append(getC_Currency_ID());
		sb.append(", C_AdvCommissionPayroll_ID=");
		sb.append(getC_AdvCommissionPayroll_ID());
		sb.append(", Line=");
		sb.append(getLine());
		sb.append("]");

		return sb.toString();
	}
}
