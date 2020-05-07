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
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.MaterialTrackingWithQuantity;
import de.metas.materialtracking.model.I_M_Material_Tracking;

public interface IHUPPOrderMaterialTrackingBL extends ISingletonService
{
	/**
	 * Link the cost collector and its manufacturing order to the {@code materialTracking} via {@code M_Material_Tracking_Ref}.<br>
	 * If the respective {@code PP_Order} is also a quality inspection, then also set {@code PP_Order.M_MaterialTracking_ID}.
	 */
	void linkPPOrderToMaterialTracking(
			I_PP_Cost_Collector costCollectorRecord,
			MaterialTrackingWithQuantity materialTrackingWithQuantity);

	/**
	 * Extracts HU's material tracking if any
	 *
	 * @return material tracking or null
	 */
	I_M_Material_Tracking extractMaterialTrackingIfAny(IHUContext huContext, I_M_HU hu);

}
