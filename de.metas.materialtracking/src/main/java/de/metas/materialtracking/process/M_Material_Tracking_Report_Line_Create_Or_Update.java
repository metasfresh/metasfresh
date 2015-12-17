package de.metas.materialtracking.process;

import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;

/*
 * #%L
 * de.metas.materialtracking
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

public class M_Material_Tracking_Report_Line_Create_Or_Update extends SvrProcess
{

	private final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_Material_Tracking_Report report = getRecord(I_M_Material_Tracking_Report.class);

		materialTrackingBL.createOrUpdateMaterialTrackingReportLines(report);
		return MSG_OK;
	}

}
