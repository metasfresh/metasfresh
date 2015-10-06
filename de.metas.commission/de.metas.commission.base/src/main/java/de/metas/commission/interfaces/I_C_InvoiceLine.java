package de.metas.commission.interfaces;

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


import de.metas.commission.model.IInstanceTrigger;
import de.metas.commission.model.I_HR_Movement;

public interface I_C_InvoiceLine extends de.metas.adempiere.model.I_C_InvoiceLine,
		IInstanceTrigger
{
	public static String COLUMNNAME_HR_Movement_ID = "HR_Movement_ID";

	public int getHR_Movement_ID();

	public I_HR_Movement getHR_Movement();

	public void setHR_Movement_ID(int movementId);

	public static final String COLUMNNAME_IsManualCommissionPoints = "IsManualCommissionPoints";

	@Override
	public void setIsManualCommissionPoints(boolean IsManualCommissionPoints);

	@Override
	public boolean isManualCommissionPoints();
}
