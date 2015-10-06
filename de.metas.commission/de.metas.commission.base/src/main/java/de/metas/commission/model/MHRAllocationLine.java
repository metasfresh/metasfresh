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
import org.eevolution.model.MHRMovement;

public class MHRAllocationLine extends X_HR_AllocationLine
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8596891348415485014L;

	public MHRAllocationLine(final Properties ctx, final int HR_AllocationLine_ID, final String trxName)
	{
		super(ctx, HR_AllocationLine_ID, trxName);
	}

	public MHRAllocationLine(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static List<MHRAllocationLine> retrieveForMovement(final MHRMovement movement)
	{
		final String whereClause = I_HR_AllocationLine.COLUMNNAME_HR_Movement_ID + "=?";

		return new Query(movement.getCtx(), I_HR_AllocationLine.Table_Name, whereClause, movement.get_TrxName())
				.setParameters(movement.get_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_HR_AllocationLine.COLUMNNAME_HR_AllocationLine_ID)
				.list();
	}
}
