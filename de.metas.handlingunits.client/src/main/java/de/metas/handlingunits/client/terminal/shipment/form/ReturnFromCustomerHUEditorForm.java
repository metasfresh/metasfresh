package de.metas.handlingunits.client.terminal.shipment.form;

import org.compiere.apps.form.FormFrame;

import de.metas.handlingunits.client.terminal.editor.model.impl.AbstractHUEditorFrame;
import de.metas.handlingunits.client.terminal.form.AbstractHUEditorForm;
import de.metas.handlingunits.client.terminal.shipment.model.ReturnFromCustomerHUEditorModel;
import de.metas.handlingunits.client.terminal.shipment.view.ReturnFromCustomerHUEditorFrame;
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

public class ReturnFromCustomerHUEditorForm extends AbstractHUEditorForm<ReturnFromCustomerHUEditorModel>
{

	@Override
	protected AbstractHUEditorFrame<ReturnFromCustomerHUEditorModel> createFramePanel(FormFrame frame, final I_M_InOut shipment)
	{

		final ReturnFromCustomerHUEditorFrame framePanel = new ReturnFromCustomerHUEditorFrame(frame, frame.getWindowNo(), shipment);

		return framePanel;

	}

}
