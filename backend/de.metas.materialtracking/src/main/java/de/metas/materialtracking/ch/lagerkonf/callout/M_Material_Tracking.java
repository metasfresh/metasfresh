/**
 *
 */
package de.metas.materialtracking.ch.lagerkonf.callout;

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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;

import de.metas.materialtracking.ch.lagerkonf.interfaces.I_M_Material_Tracking;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Version;

/**
 * @author ts
 *
 */
@Callout(I_M_Material_Tracking.class)
public class M_Material_Tracking
{

	@CalloutMethod(columnNames = { I_M_Material_Tracking.COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID })
	public void setDates(final I_M_Material_Tracking materialTracking, final ICalloutField field)
	{
		final I_M_QualityInsp_LagerKonf_Version lagerKonfVersion = materialTracking.getM_QualityInsp_LagerKonf_Version();

		if (lagerKonfVersion != null)
		{
			materialTracking.setValidFrom(lagerKonfVersion.getValidFrom());
			materialTracking.setValidTo(lagerKonfVersion.getValidTo());
		}
	}
}
