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
import org.compiere.model.Query;

public class MCAdvComSponsorParam extends X_C_AdvComSponsorParam
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -200469511210292083L;

	public MCAdvComSponsorParam(final Properties ctx, final int C_AdvComSponsorParam_ID, final String trxName)
	{
		super(ctx, C_AdvComSponsorParam_ID, trxName);
	}

	public MCAdvComSponsorParam(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static List<MCAdvComInstanceParam> retrieve(final I_C_AdvCommissionTerm term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final String wc = I_C_AdvComSponsorParam.COLUMNNAME_C_AdvCommissionTerm_ID + "=?";

		final int param = term.getC_AdvCommissionTerm_ID();

		final String orderBy = I_C_AdvComSponsorParam.COLUMNNAME_SeqNo;

		return new Query(ctx, I_C_AdvComSponsorParam.Table_Name, wc, trxName)
				.setParameters(param)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list();
	}
}
