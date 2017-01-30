package org.adempiere.bpartner.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.util.Services;

import de.metas.interfaces.I_C_BPartner;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_C_BPartner.class)
public class C_BPartner
{
	@Init
	public void init()
	{
		Services.get(ITabCalloutFactory.class)
				.registerTabCalloutForTable(I_C_BPartner.Table_Name, org.adempiere.bpartner.callout.C_BPartner_TabCallout.class);
	}
}
