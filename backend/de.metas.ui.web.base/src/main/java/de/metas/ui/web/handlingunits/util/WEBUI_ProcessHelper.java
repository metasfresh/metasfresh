/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.handlingunits.util;

import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WEBUI_ProcessHelper
{
	private static final String SYSCONFIG_ALLOW_INFINIT_CAPACITY_TUS = "de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Transform.AllowNewTUsWithInfiniteCapacity";

	/**
	 * This method can be used by process methods that are annotated with {@link de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvide}.
	 * It returns the eligible PIIPs for the given product and partner.
	 *
	 * @param ctx
	 * @param productId
	 * @param bPartner optional, may be {@code null}
	 * @param includeVirtualItem if {@code true}, then the resulting list also contains the "virtual" PiiP.
	 * @return
	 */
	public LookupValuesList retrieveHUPIItemProducts(
			@NonNull final Properties ctx,
			@NonNull final ProductId productId,
			@Nullable final BPartnerId bpartnerId,
			final boolean includeVirtualItem)
	{
		final List<I_M_HU_PI_Item_Product> list = retrieveHUPIItemProductRecords(ctx, productId, bpartnerId, includeVirtualItem);

		return list.stream()
				.sorted(Comparator.comparing(I_M_HU_PI_Item_Product::getName))
				.map(huPIItemProduct -> IntegerLookupValue.of(huPIItemProduct.getM_HU_PI_Item_Product_ID(), huPIItemProduct.getName()))
				.collect(LookupValuesList.collect());
	}

	public List<I_M_HU_PI_Item_Product> retrieveHUPIItemProductRecords(
			@NonNull final Properties ctx,
			@NonNull final ProductId productId,
			@Nullable final BPartnerId bpartnerId,
			final boolean includeVirtualItem)
	{
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final boolean allowInfiniteCapacity = sysConfigBL.getBooleanValue(SYSCONFIG_ALLOW_INFINIT_CAPACITY_TUS, true,
				Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));

		final List<I_M_HU_PI_Item_Product> list = hupiItemProductDAO
				.retrieveTUs(ctx, productId, bpartnerId, allowInfiniteCapacity);

		if (includeVirtualItem)
		{
			list.add(hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx));
		}
		return list;
	}

	/**
	 * Creates a string of the form "PI-Version-Name (Qty x Included-PI)", e.g. "Palette (20 x IFCO)".
	 *
	 * @param huPIItem may not be {@code null}
	 * @return
	 */
	public String buildHUPIItemString(@NonNull final I_M_HU_PI_Item huPIItem)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU_PI includedPI = handlingUnitsDAO.getIncludedPI(huPIItem);

		final String result = StringUtils.formatMessage("{} ({} x {})",
				huPIItem.getM_HU_PI_Version().getName(),
				huPIItem.getQty().setScale(0, RoundingMode.HALF_UP), // it's always integer quantities
				includedPI.getName());

		return result;
	}
}
