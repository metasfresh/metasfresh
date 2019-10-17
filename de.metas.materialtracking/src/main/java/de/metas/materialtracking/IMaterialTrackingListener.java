package de.metas.materialtracking;

import java.math.BigDecimal;

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


import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

/**
 * Material Tracking event listener.
 * <p>
 * Note: when implementing your own one, it's recommended to subclass {@link MaterialTrackingListenerAdapter}.
 *
 * @author tsa
 *
 */
public interface IMaterialTrackingListener
{
	/**
	 * Called by API before model will be linked to material tracking.
	 */
	void beforeModelLinked(MTLinkRequest request, I_M_Material_Tracking_Ref materialTrackingRef);

	/**
	 * Called by API when a model is linked to a material tracking.
	 */
	void afterModelLinked(MTLinkRequest request);

	/**
	 * Called by API when a model is un-linked from a material tracking.
	 */
	void afterModelUnlinked(Object model, I_M_Material_Tracking materialTrackingOld);

	/**
	 * Called by API when only a materialTrackingRefRecord's qtyIssue was changed and it was saved.
	 */
	void afterQtyIssuedChanged(I_M_Material_Tracking_Ref materialTrackingRefRecord, BigDecimal oldValue);
}
