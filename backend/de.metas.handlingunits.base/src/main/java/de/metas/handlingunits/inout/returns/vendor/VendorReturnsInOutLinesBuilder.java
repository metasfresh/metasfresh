/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.returns.vendor;

import de.metas.handlingunits.inout.returns.AbstractQualityReturnsInOutLinesBuilder;
import org.adempiere.util.lang.IReference;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Services;

/**
 * Builder for vendor return inout lines that are for non-packing material products
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class VendorReturnsInOutLinesBuilder extends AbstractQualityReturnsInOutLinesBuilder
{
	// services
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	public VendorReturnsInOutLinesBuilder(IReference<I_M_InOut> inoutRef)
	{
		super(inoutRef);
	}

	public static VendorReturnsInOutLinesBuilder newBuilder(IReference<I_M_InOut> inoutRef)
	{

		return new VendorReturnsInOutLinesBuilder(inoutRef);
	}

	@Override
	protected void setHUStatus(final IHUContext huContext, final I_M_HU hu)
	{
		huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Shipped);
	}
}
