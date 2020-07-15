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

public abstract class MaterialTrackingListenerAdapter implements IMaterialTrackingListener
{
	@Override
	public void beforeModelLinked(final MTLinkRequest request, final I_M_Material_Tracking_Ref materialTrackingRef)
	{
		// nothing
	}

	@Override
	public void afterModelLinked(final MTLinkRequest request)
	{
		// nothing
	}

	@Override
	public void afterModelUnlinked(final Object model, final I_M_Material_Tracking materialTrackingOld)
	{
		// nothing
	}


	@Override
	public void afterQtyIssuedChanged(I_M_Material_Tracking_Ref materialTrackingRef, BigDecimal oldValue)
	{
		// nothing
	}
}
