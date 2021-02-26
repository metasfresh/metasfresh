package org.eevolution.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;

import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

public class PP_Order_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(ICalloutRecord calloutRecord)
	{
		final I_PP_Order ppOrder = calloutRecord.getModel(I_PP_Order.class);
		Services.get(IPPOrderBL.class).setDefaults(ppOrder);
	}
}
