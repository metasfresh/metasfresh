package de.metas.ui.web.window.datatypes;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class LookupValues
{
	public JSONLookupValue createProductLookupValue(@Nullable final I_M_Product product)
	{
		if (product == null)
		{
			return null;
		}

		final String displayName = product.getValue() + "_" + product.getName();
		return JSONLookupValue.of(product.getM_Product_ID(), displayName);
	}

	public JSONLookupValue createUOMLookupValue(@Nullable final I_C_UOM uom)
	{
		if (uom == null)
		{
			return null;
		}

		return JSONLookupValue.of(uom.getC_UOM_ID(), uom.getUOMSymbol());
	}

	public JSONLookupValue createBPartnerLookupValue(@Nullable final BPartnerId bPartnerId)
	{
		if (bPartnerId == null)
		{
			return null;
		}

		final I_C_BPartner bPartnerRecord = loadOutOfTrx(bPartnerId, I_C_BPartner.class);
		return JSONLookupValue.of(bPartnerId.getRepoId(), bPartnerRecord.getValue() + "_" + bPartnerRecord.getName());
	}

	public JSONLookupValue createLocatorLookupValue(final int locatorId)
	{
		if (locatorId <= 0)
		{
			return null;
		}

		final I_M_Locator locator = loadOutOfTrx(locatorId, I_M_Locator.class);
		if (locator == null)
		{
			return JSONLookupValue.unknown(locatorId);
		}

		final I_M_Warehouse warehouse = loadOutOfTrx(locator.getM_Warehouse_ID(), I_M_Warehouse.class);

		final String caption = Stream.of(warehouse.getName(), locator.getValue(), locator.getX(), locator.getX1(), locator.getY(), locator.getZ())
				.filter(part -> !Check.isEmpty(part, true))
				.map(String::trim)
				.collect(Collectors.joining("_"));

		return JSONLookupValue.of(locatorId, caption);
	}
}
