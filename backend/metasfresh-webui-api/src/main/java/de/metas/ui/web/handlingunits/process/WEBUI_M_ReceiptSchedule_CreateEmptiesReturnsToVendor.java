package de.metas.ui.web.handlingunits.process;

import org.compiere.model.X_M_InOut;

import de.metas.ui.web.quickinput.inout.EmptiesQuickInputDescriptorFactory;

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

public class WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsToVendor extends WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base
{
	public WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsToVendor()
	{
		super(X_M_InOut.MOVEMENTTYPE_VendorReturns, EmptiesQuickInputDescriptorFactory.VendorReturns_Window_ID);
	}
}
