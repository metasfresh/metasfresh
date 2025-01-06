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

package de.metas.handlingunits.ordercandidate.spi.impl;

import de.metas.adempiere.gui.search.impl.OLCandHUPackingAware;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class OLCandPIIPUtil
{

	private static final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);

	@Nullable
	I_M_HU_PI_Item_Product extractHUPIItemProductOrNull(@NonNull final I_C_OLCand olCandRecord)
	{
		final OLCandHUPackingAware huPackingAware = new OLCandHUPackingAware(olCandRecord);

		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNull(huPackingAware.getM_HU_PI_Item_Product_ID());
		return piItemProductId != null
				? hupiItemProductBL.getRecordById(piItemProductId)
				: null;
	}
}
