package de.metas.handlingunits.client.terminal.shipment.view;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.shipment.model.ReturnFromCustomerHUEditorModel;

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
//		final CustomerReturnHUEditorPanel model = getHUEditorModel();
//		final List<I_M_InOut> receiptsToReverse = model.getReceiptsToReverse();
//		if (receiptsToReverse.isEmpty())
//		{
//			throw new TerminalException("@NoSelection@");
//		}
//
//		//
//		// Ask user if he/she really wants to reverse those receipts
//		final String receiptsToReverseInfo = model.buildReceiptsToReverseInfo(receiptsToReverse);
//		final boolean reverse = getTerminalFactory()
//				.ask(this)
//				.setAD_Message("DeleteRecord?") // TODO: better message
//				.setAdditionalMessage(receiptsToReverseInfo)
//				.setDefaultAnswer(false)
//				.getAnswer();
//		if (!reverse)
//		{
//			// user canceled!
//			throw new TerminalDialogCancelClosingException();
//		}
//
//		model.reverseReceipts(receiptsToReverse);
	}

	@Override
	protected void createAndAddActionButtons(IContainer buttonsPanel)
	{
		buttonsPanel.add(bMoveToQualityWarehouse, "newline");
		buttonsPanel.add(bCreateVendorReturn, "");
		buttonsPanel.add(bMoveToGarbage, "");
	}
}
