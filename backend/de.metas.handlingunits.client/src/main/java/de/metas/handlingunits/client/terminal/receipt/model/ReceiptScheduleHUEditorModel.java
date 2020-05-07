package de.metas.handlingunits.client.terminal.receipt.model;

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


import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;

/**
 * Wareneingang POS's {@link HUEditorModel}: panel model which displays the HUs to be received.
 *
 * @author tsa
 *
 */
public class ReceiptScheduleHUEditorModel extends HUEditorModel
{
	public static final ReceiptScheduleHUEditorModel cast(final HUEditorModel huEditorModel)
	{
		final ReceiptScheduleHUEditorModel receiptScheduleHUEditorModel = (ReceiptScheduleHUEditorModel)huEditorModel;
		return receiptScheduleHUEditorModel;
	}

	public ReceiptScheduleHUEditorModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);

		// task 06985: do not display barcode in Wareneingang (POS)
		// ts: generally, i guess when creating a HU from other documents, there isn't a lot of sense in showing the barcode field because its purpose is to select existing HUs
		setDisplayBarcode(false);

		// Our HUs are linked to Receipt Schedule HU document, so we want to update the allocations on close
		setUpdateHUAllocationsOnSave(true);
	}
}
