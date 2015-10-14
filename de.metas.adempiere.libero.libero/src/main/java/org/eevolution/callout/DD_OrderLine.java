package org.eevolution.callout;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import org.adempiere.util.Services;
import org.eevolution.api.IDDOrderLineBL;
import org.eevolution.model.I_DD_OrderLine;

@Callout(I_DD_OrderLine.class)
public class DD_OrderLine
{

	public static final DD_OrderLine instance = new DD_OrderLine();
	
	/**
	 * Calls {@link IDDOrderLineBL#setUOMInDDOrderLine(I_DD_OrderLine)}.
	 * 
	 * @param ddOrderLine
	 * @param field
	 * 
	 * @task http://dewiki908/mediawiki/index.php/fresh_08583_Erfassung_Packvorschrift_in_DD_Order_ist_crap_%28108882381939%29
	 *       ("UOM In manual DD_OrderLine shall always be the uom of the product ( as talked with Mark) ")
	 */
	@CalloutMethod(columnNames = { I_DD_OrderLine.COLUMNNAME_M_Product_ID})
	public void setUOMInDDOrderLine (final I_DD_OrderLine ddOrderLine, final ICalloutField field)
	{
		Services.get(IDDOrderLineBL.class).setUOMInDDOrderLine(ddOrderLine);
	}
}
