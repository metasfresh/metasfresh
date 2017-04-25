package org.adempiere.bpartner.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_BPartner;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Callout(I_C_BPartner.class)
public class C_BPartner
{
	/**
	 * When user is changing the CompanyName field (and IsCompany=Y) set Name=CompanyName
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1307
	 */
	@CalloutMethod(columnNames = I_C_BPartner.COLUMNNAME_CompanyName)
	public void updateNameFromCompanyName(final I_C_BPartner bpartner)
	{
		if (bpartner.isCompany())
		{
			bpartner.setName(bpartner.getCompanyName());
		}
	}
}
