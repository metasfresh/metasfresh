package de.metas.handlingunits.client.terminal.shipment.view;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.TerminalDialogCancelClosingException;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.shipment.model.ReturnFromCustomerHUEditorModel;
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

public class ReturnFromCustomerHUEditorPanel extends HUEditorPanel
{
	
	protected ITerminalButton bReturnFromCustomer;
	private static final String ACTION_ReturnFromCustomer = "ReturnFromCustomer"; 
	
	public ReturnFromCustomerHUEditorPanel(final ReturnFromCustomerHUEditorModel model)
	{
		super(model);

		setAskUserWhenCancelingChanges(false);
	}

	@Override
	protected ReturnFromCustomerHUEditorModel getHUEditorModel()
	{
		return ReturnFromCustomerHUEditorModel.cast(super.getHUEditorModel());
	}

	@Override
	protected void onDialogOkAfterSave(final ITerminalDialog dialog)
	{
		final ReturnFromCustomerHUEditorModel model = getHUEditorModel();
		final I_M_InOut shipmentToReturn = model.getShipment();
		if (shipmentToReturn == null)
		{
			throw new TerminalException("@NoSelection@");
		}

		//
		// Ask user if he/she really wants to reverse those receipts
		final String receiptsToReverseInfo = model.buildShipmentToReturnInfo(shipmentToReturn);
		final boolean returnShipment = getTerminalFactory()
				.ask(this)
				.setAD_Message("ReturnShipment?") // TODO: better message
				.setAdditionalMessage(receiptsToReverseInfo)
				.setDefaultAnswer(false)
				.getAnswer();
		if (!returnShipment)
		{
			// user canceled!
			throw new TerminalDialogCancelClosingException();
		}

		model.returnShipmentFromCustomer(shipmentToReturn);
	}

	@Override
	protected void createAndAddActionButtons(IContainer buttonsPanel)
	{
		buttonsPanel.add(bReturnFromCustomer, "");
	}
	

}
