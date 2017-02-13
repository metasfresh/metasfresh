package de.metas.ui.web.handlingunits;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface I_WEBUI_HU_View
{
	String COLUMNNAME_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	String COLUMNNAME_Value = I_M_HU.COLUMNNAME_Value;
	String COLUMNNAME_HU_UnitType = I_M_HU_PI_Version.COLUMNNAME_HU_UnitType;
	
	String COLUMNNAME_HUStatus = I_M_HU.COLUMNNAME_HUStatus;
	int HUSTATUS_AD_Reference_ID = X_M_HU.HUSTATUS_AD_Reference_ID;

	String COLUMNNAME_PackingInfo = "PackingInfo";

	String COLUMNNAME_M_Product_ID = "M_Product_ID";
	String COLUMNNAME_QtyCU = "QtyCU";
	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
	
}
