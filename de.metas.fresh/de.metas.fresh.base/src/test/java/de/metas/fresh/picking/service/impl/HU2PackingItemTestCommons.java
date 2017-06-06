package de.metas.fresh.picking.service.impl;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BPartner;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class HU2PackingItemTestCommons
{
	final int COUNT_IFCOs_Per_Palet = 5;
	final int COUNT_Tomatoes_Per_IFCO = 10;
	
	public HUTestHelper commonCreateHUTestHelper()
	{
		return new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				// no transaction by default
				return ITrx.TRXNAME_None;
			}
		};
	}

	public I_M_HU_PI createHuDefIFCO(@NonNull final HUTestHelper helper)
	{
		final I_M_HU_PI huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, helper.pTomato, BigDecimal.valueOf(COUNT_Tomatoes_Per_IFCO), helper.uomEach);
		}
		return huDefIFCO;
	}

	public I_M_HU_PI createHuDefPalet(@NonNull final HUTestHelper helper, @NonNull final I_M_HU_PI tuHuDef)
	{
		final I_M_HU_PI huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			final I_C_BPartner bpartner = null;
			helper.createHU_PI_Item_IncludedHU(huDefPalet, tuHuDef, BigDecimal.valueOf(COUNT_IFCOs_Per_Palet), bpartner);
		}
		return huDefPalet;
	}
}
