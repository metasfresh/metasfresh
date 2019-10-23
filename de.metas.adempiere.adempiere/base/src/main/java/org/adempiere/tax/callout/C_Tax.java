package org.adempiere.tax.callout;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_C_Tax;

import de.metas.tax.api.ITaxBL;
import de.metas.util.Services;

@Callout(I_C_Tax.class)
public class C_Tax
{
	@CalloutMethod(columnNames = { I_C_Tax.COLUMNNAME_IsWholeTax })
	public void setupIfIsWholeTax(final I_C_Tax tax, final ICalloutField field)
	{
		Services.get(ITaxBL.class).setupIfIsWholeTax(tax);
	}

}
