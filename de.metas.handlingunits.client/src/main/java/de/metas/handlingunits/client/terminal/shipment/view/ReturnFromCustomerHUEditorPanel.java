package de.metas.handlingunits.client.terminal.shipment.view;

import java.beans.PropertyChangeEvent;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ReturnFromCustomerHUEditorPanel extends HUEditorPanel
{
	/**
	 * Button to create Returns From Vendor
	 */
	protected ITerminalButton bReturnFromCustomer;
	private static final String ACTION_ReturnFromCustomer = "ReturnFromCustomer";

	public ReturnFromCustomerHUEditorPanel(final ReturnFromCustomerHUEditorModel model)
	{
		super(model);

		setAskUserWhenCancelingChanges(false);

	}

	/**
	 * 
	 */
	protected void doReturnFromCustomer()
	{
		getHUEditorModel().createCustomerReturn();
	}

	@Override
	protected ReturnFromCustomerHUEditorModel getHUEditorModel()
	{
		return ReturnFromCustomerHUEditorModel.cast(super.getHUEditorModel());
	}

	public void setShipmentToReturn(final I_M_InOut shipment)
	{
		getHUEditorModel().setShipment(shipment);
	}

	@Override
	protected void createAndAddActionButtons(final IContainer buttonsPanel)
	{

		// task #1062
		// CreateVendorReturn button
		{

			final ITerminalFactory factory = getTerminalContext().getTerminalFactory();

			this.bReturnFromCustomer = factory.createButton(ACTION_ReturnFromCustomer);
			this.bReturnFromCustomer.setTextAndTranslate(ACTION_ReturnFromCustomer);
			bReturnFromCustomer.setEnabled(true);
			bReturnFromCustomer.setVisible(true);
			bReturnFromCustomer.addListener(new UIPropertyChangeListener(factory, bReturnFromCustomer)
			{

				@Override
				protected void propertyChangeEx(PropertyChangeEvent evt)
				{
					doReturnFromCustomer();

				}
			});
		}

		buttonsPanel.add(bReturnFromCustomer, "");
	}
}
