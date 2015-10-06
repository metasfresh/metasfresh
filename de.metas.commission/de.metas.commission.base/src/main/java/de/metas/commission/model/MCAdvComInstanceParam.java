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

import org.compiere.model.Query;

public class MCAdvComInstanceParam extends X_C_AdvComInstanceParam
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -126881146646890382L;

	public MCAdvComInstanceParam(final Properties ctx, final int C_AdvComInstanceParam_ID,
			final String trxName)
	{
		super(ctx, C_AdvComInstanceParam_ID, trxName);
	}

	public MCAdvComInstanceParam(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static List<MCAdvComInstanceParam> retrieve(final MCAdvComSystemType type)
	{
		final String wc = I_C_AdvComInstanceParam.COLUMNNAME_C_AdvComSystem_Type_ID + "=?";

		final int param = type.get_ID();

		final String orderBy = I_C_AdvComInstanceParam.COLUMNNAME_SeqNo;

		return new Query(type.getCtx(), I_C_AdvComInstanceParam.Table_Name, wc, type.get_TrxName())
				.setParameters(param)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list();
	}
}
