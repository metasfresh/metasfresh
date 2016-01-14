package de.metas.materialtracking;

import org.adempiere.util.ISingletonService;

import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line_Alloc;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IMaterialTrackingReportBL extends ISingletonService
{
	/**
	 * Create a new report line based on a given material tracking ref
	 * 
	 * @param report
	 * @param ref
	 * @return the created line
	 */
	I_M_Material_Tracking_Report_Line createMaterialTrackingReportLine(I_M_Material_Tracking_Report report, I_M_Material_Tracking_Ref ref);

	/**
	 * Create {@link I_M_Material_Tracking_Report_Line_Alloc} entries between a material tracking line and a material tracking ref.
	 * 
	 * @param line
	 * @param ref
	 * @return
	 */
	I_M_Material_Tracking_Report_Line_Alloc createMaterialTrackingReportLineAllocation(I_M_Material_Tracking_Report_Line line, I_M_Material_Tracking_Ref ref);

}
