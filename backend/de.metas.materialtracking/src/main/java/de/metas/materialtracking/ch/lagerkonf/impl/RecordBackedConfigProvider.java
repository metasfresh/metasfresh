package de.metas.materialtracking.ch.lagerkonf.impl;

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


import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfigProvider;
import de.metas.util.Check;

public class RecordBackedConfigProvider implements IQualityBasedConfigProvider
{

	@Override
	public IQualityBasedConfig provideConfigFor(final I_M_Material_Tracking materialTracking)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final de.metas.materialtracking.ch.lagerkonf.interfaces.I_M_Material_Tracking materialTrackingExt = InterfaceWrapperHelper.create(materialTracking,
				de.metas.materialtracking.ch.lagerkonf.interfaces.I_M_Material_Tracking.class);
		
		return new RecordBackedQualityBasedConfig(materialTrackingExt.getM_QualityInsp_LagerKonf_Version());
	}

}
