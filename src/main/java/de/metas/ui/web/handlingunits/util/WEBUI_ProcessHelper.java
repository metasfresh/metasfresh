package de.metas.ui.web.handlingunits.util;

import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Stream;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class WEBUI_ProcessHelper
{
	private static final String SYSCONFIG_ALLOW_INFINIT_CAPACITY_TUS = "de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Transform.AllowNewTUsWithInfiniteCapacity";

	/**
	 * This method can be used by process methods that are annotated with {@link de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvide}.
	 * It returns the eligible PIIPs for the given product and partner.
	 * 
	 * @param ctx
	 * @param product
	 * @param bPartner optional, may be null
	 * @return
	 */
	public LookupValuesList retrieveHUPIItemProducts(final Properties ctx, 
			@NonNull final I_M_Product product, 
			final I_C_BPartner bPartner)
	{
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final boolean allowInfiniteCapacity = sysConfigBL.getBooleanValue(SYSCONFIG_ALLOW_INFINIT_CAPACITY_TUS, true,
				Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));

		final Stream<I_M_HU_PI_Item_Product> stream = hupiItemProductDAO
				.retrieveTUs(ctx, product, bPartner, allowInfiniteCapacity)
				.stream();

		return stream
				.sorted(Comparator.comparing(I_M_HU_PI_Item_Product::getName))
				.map(huPIItemProduct -> IntegerLookupValue.of(huPIItemProduct.getM_HU_PI_Item_Product_ID(), huPIItemProduct.getName()))
				.collect(LookupValuesList.collect());
	}
}
