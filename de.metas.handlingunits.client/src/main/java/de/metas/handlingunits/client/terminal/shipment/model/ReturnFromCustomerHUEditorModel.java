package de.metas.handlingunits.client.terminal.shipment.model;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ReturnFromCustomerHUEditorModel extends HUEditorModel
{

	private I_M_InOut shipment;

	public ReturnFromCustomerHUEditorModel(ITerminalContext terminalContext, final I_M_InOut shipment)
	{
		super(terminalContext);
		this.shipment = shipment;

		setAllowSelectingReadonlyKeys(true);
		setDisplayBarcode(true); // yes, because user will scan/search for the HU which he/she fucked up
	}

	public static final ReturnFromCustomerHUEditorModel cast(final HUEditorModel huEditorModel)
	{
		final ReturnFromCustomerHUEditorModel customerReturnHUEditorModel = (ReturnFromCustomerHUEditorModel)huEditorModel;
		return customerReturnHUEditorModel;
	}

	public void loadFromShipment()
	{
		Check.assumeNotNull(shipment, "shipment not null");

		final List<I_M_HU> shipmentHandlingUnits = Services.get(IHUInOutDAO.class).retrieveHandlingUnits(shipment);

		//
		// Retrieve and create HU Keys
		final ITerminalContext terminalContext = getTerminalContext();
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
		final List<IHUKey> huKeys = shipmentHandlingUnits
				.stream()
				.map(hu -> keyFactory.createKey(hu, null))
				.collect(GuavaCollectors.toImmutableList());

		//
		// Create and set Root HU Key
		final IHUKeyFactory huKeyFactory = getHUKeyFactory();
		final IHUKey rootKey = huKeyFactory.createRootKey();
		rootKey.setReadonly(true); // make sure besides selecting HUs, the user is not allowed to change them; not here!
		rootKey.addChildren(huKeys);
		setRootHUKey(rootKey);
	}

	public I_M_InOut getShipment()
	{
		return shipment;
	}

	public void setShipment(final I_M_InOut shipment)
	{
		this.shipment = shipment;
	}

	public void createCustomerReturn()
	{
		Services.get(IHUInOutBL.class).createCustomerReturnInOutForHUs(getSelectedHUs());

	}

}
