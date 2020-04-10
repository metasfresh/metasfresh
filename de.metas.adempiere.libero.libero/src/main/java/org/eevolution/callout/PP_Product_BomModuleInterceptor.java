/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2019 metas GmbH
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

package org.eevolution.callout;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.eevolution.model.I_PP_Product_BOM;

public class PP_Product_BomModuleInterceptor extends AbstractModuleInterceptor
{
	public static final transient PP_Product_BomModuleInterceptor instance = new PP_Product_BomModuleInterceptor();

	private PP_Product_BomModuleInterceptor()
	{
		super();
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new PP_Product_BOM());
	}

	@Override
	protected void registerTabCallouts(final ITabCalloutFactory tabCalloutsRegistry)
	{
		tabCalloutsRegistry.registerTabCalloutForTable(I_PP_Product_BOM.Table_Name, PP_Product_BOM_TabCallout.class);
	}

}
