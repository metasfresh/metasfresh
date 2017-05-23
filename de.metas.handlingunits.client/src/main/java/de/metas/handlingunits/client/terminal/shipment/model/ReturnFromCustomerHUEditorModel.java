package de.metas.handlingunits.client.terminal.shipment.model;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.model.I_M_InOut;

/*
 * #%L
 * de.metas.handlingunits.client
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

public class ReturnFromCustomerHUEditorModel  extends HUEditorModel
{

	public ReturnFromCustomerHUEditorModel(ITerminalContext terminalContext)
	{
		super(terminalContext);
		// TODO Auto-generated constructor stub
	}

	
	public static final ReturnFromCustomerHUEditorModel cast(final HUEditorModel huEditorModel)
	{
		final ReturnFromCustomerHUEditorModel customerReturnHUEditorModel = (ReturnFromCustomerHUEditorModel)huEditorModel;
		return customerReturnHUEditorModel;
	}


	public void loadFromShipment(I_M_InOut shipment)
	{
		// TODO Auto-generated method stub
		
	}
}
