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

import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;

public class MCAdvComRelevantPOType extends X_C_AdvComRelevantPO_Type
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8476589072104044805L;

	public MCAdvComRelevantPOType(final Properties ctx, final int C_AdvComRelevantPO_Type_ID, final String trxName)
	{
		super(ctx, C_AdvComRelevantPO_Type_ID, trxName);
	}

	public MCAdvComRelevantPOType(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Returns the relevantPOTypes which in turn link to the actual commission types. Those MCAdvComRelevantPOTypes with IsProcessImmediately='Y' are returned first to make sure that they are
	 * processed first.
	 * 
	 * @param poDef
	 * @return
	 */
	@Cached
	public static List<MCAdvComRelevantPOType> retrieveFor(final MCAdvCommissionRelevantPO poDef)
	{
		final String whereClause = I_C_AdvComRelevantPO_Type.COLUMNNAME_C_AdvCommissionRelevantPO_ID + "=?";

		final int param = poDef.get_ID();

		// make sure that type with IsProcessImmediately='Y' are returned first
		final String orderBy = I_C_AdvComRelevantPO_Type.COLUMNNAME_IsProcessImmediately + " DESC, " + I_C_AdvComRelevantPO_Type.COLUMNNAME_SeqNo;

		return new Query(poDef.getCtx(), I_C_AdvComRelevantPO_Type.Table_Name, whereClause, poDef.get_TrxName())
				.setParameters(param)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list();
	}

	@Cached
	public static List<MCAdvComRelevantPOType> retrieveFor(final MCAdvCommissionRelevantPO poDef, final I_C_AdvComSystem_Type comSystemType)
	{
		final String whereClause =
				I_C_AdvComRelevantPO_Type.COLUMNNAME_C_AdvCommissionRelevantPO_ID + "=? AND " + I_C_AdvComRelevantPO_Type.COLUMNNAME_C_AdvComSystem_Type_ID + "=?";

		final String orderBy = I_C_AdvComRelevantPO_Type.COLUMNNAME_SeqNo;

		return new Query(poDef.getCtx(), I_C_AdvComRelevantPO_Type.Table_Name, whereClause, poDef.get_TrxName())
				.setParameters(poDef.get_ID(), comSystemType.getC_AdvComSystem_Type_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MCAdvComRelevantPOType[Id=");
		sb.append(get_ID());
		sb.append(", C_AdvCommissionRelevantPO_ID=");
		sb.append(getC_AdvCommissionRelevantPO_ID());
		sb.append(", C_AdvComSystem_ID=");
		sb.append(getC_AdvComSystem_ID());
		sb.append(", C_AdvComSystem_Type_ID=");
		sb.append(getC_AdvComSystem_Type_ID());
		sb.append(", ProcessImmediately=");
		sb.append(isProcessImmediately());
		sb.append("]");

		return sb.toString();
	}
}
