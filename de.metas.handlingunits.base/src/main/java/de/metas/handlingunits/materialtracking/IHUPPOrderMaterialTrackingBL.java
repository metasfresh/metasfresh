package de.metas.handlingunits.materialtracking;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;

public interface IHUPPOrderMaterialTrackingBL extends ISingletonService
{
	/**
	 * Cross link the manufacturing order and the <code>materialTracking</code>
	 * 
	 * @param ppOrderBOMLine
	 * @param materialTracking
	 */
	void linkPPOrderToMaterialTracking(I_PP_Order_BOMLine ppOrderBOMLine, I_M_Material_Tracking materialTracking);

	/**
	 * Extracts HU's material tracking if any
	 * 
	 * @param huContext
	 * @param hu
	 * @return material tracking or null
	 */
	I_M_Material_Tracking extractMaterialTrackingIfAny(IHUContext huContext, I_M_HU hu);

}
