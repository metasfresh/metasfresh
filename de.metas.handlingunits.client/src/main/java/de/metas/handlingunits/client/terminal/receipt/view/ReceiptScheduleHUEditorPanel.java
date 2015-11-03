package de.metas.handlingunits.client.terminal.receipt.view;

/*
 * #%L
 * de.metas.handlingunits.client
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

import org.adempiere.exceptions.AdempiereException;

import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.TerminalDialogCancelClosingException;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.receipt.model.ReceiptScheduleHUEditorModel;

/**
 * Wareneingang POS's {@link HUEditorPanel}: panel which displays the HUs to be received.
 *
 * @author tsa
 *
 */
public class ReceiptScheduleHUEditorPanel extends HUEditorPanel
{
	private static final String MSG_ReceiveNotWeightedHUs = "de.metas.handlingunits.client.terminal.receipt.view.ReceiptScheduleHUEditorPanel.ReceiveNotWeightedHUs?";

	public ReceiptScheduleHUEditorPanel(final ReceiptScheduleHUEditorModel model)
	{
		super(model);

		//
		// Configure
		setAskUserWhenCancelingChanges(true); // 07729
		enableMarkAsQualityInspectionButton(); // task 08639
	}

	@Override
	protected ReceiptScheduleHUEditorModel getHUEditorModel()
	{
		return ReceiptScheduleHUEditorModel.cast(super.getHUEditorModel());
	}

	@Override
	protected void onDialogOkAfterSave(final ITerminalDialog dialog)
	{
		final HUEditorModel model = getHUEditorModel();

		//
		// Make sure there is something selected.
		// Else, it's pointless to go forward (i.e. there is nothing to receive)
		if (!model.hasSelectedKeys())
		{
			throw new AdempiereException("@NoSelection@");
		}

		//
		// In case user selected some HUs which are weightable but not weighted,
		// in most of the cases is a user mistake, so we ask him/her if he/she wants to proceed. (07729)
		if (model.hasSelectedHUKeysWeightableButNotWeighted())
		{
			final boolean receiveNotWeightedHUs = getTerminalFactory().ask(this)
					.setParentComponent(this)
					.setAD_Message(MSG_ReceiveNotWeightedHUs)
					.setDefaultAnswer(false)
					.getAnswer();
			if (!receiveNotWeightedHUs)
			{
				throw new TerminalDialogCancelClosingException();
			}
		}
	}
}
