package de.metas.dpd.model;

/*
 * #%L
 * de.metas.swat.base
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
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.Query;

public class MDPDStatusData extends X_DPD_StatusData
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6946174054673072043L;

	public MDPDStatusData(Properties ctx, int DPD_StatusData_ID, String trxName)
	{
		super(ctx, DPD_StatusData_ID, trxName);
	}

	public MDPDStatusData(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static MDPDStatusData retrieveOrCreate(final Properties ctx,
			String parcelNo, final int scanCodeId,
			final Timestamp eventDateTime, final String trxName)
	{

		final Object[] parameters = { parcelNo, scanCodeId, eventDateTime };

		final String whereClause = COLUMNNAME_ParcellNo + "=? AND "
				+ COLUMNNAME_DPD_ScanCode_ID + "=? AND "
				+ COLUMNNAME_EventDateTime + "=?";

		MDPDStatusData instance = new Query(ctx, Table_Name, whereClause,
				trxName).setParameters(parameters).setOnlyActiveRecords(true)
				.setClient_ID().first();

		if (instance == null)
		{

			instance = new MDPDStatusData(ctx, 0, trxName);
		}
		return instance;
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("MDPDStatusData[Id=");
		sb.append(get_ID());
		sb.append(", ParcellNo=");
		sb.append(getParcellNo());
		sb.append("]");

		return sb.toString();
	}
}
