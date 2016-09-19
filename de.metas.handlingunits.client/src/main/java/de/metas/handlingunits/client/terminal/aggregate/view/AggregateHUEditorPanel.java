package de.metas.handlingunits.client.terminal.aggregate.view;

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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalComboboxField;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.client.terminal.aggregate.model.AggregateHUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.shipping.model.I_M_ShipperTransportation;

/**
 * Verdichtung (POS) HU Editor View (second window)
 *
 * @author tsa
 *
 */
public final class AggregateHUEditorPanel extends HUEditorPanel
{
	// Services
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);

	private ITerminalComboboxField fShipperTransportation;

	private PropertyChangeListener onHUKeySelectionChangedListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			updateShipperTransportationUI();
		}
	};

	public AggregateHUEditorPanel(final AggregateHUEditorModel model)
	{
		super(model);

		model.addPropertyChangeListener(HUEditorModel.PROPERTY_HUKeySelectionChanged, onHUKeySelectionChangedListener);

		updateShipperTransportationUI();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		onHUKeySelectionChangedListener = null;
	}

	@Override
	protected AggregateHUEditorModel getHUEditorModel()
	{
		return (AggregateHUEditorModel)super.getHUEditorModel();
	}

	@Override
	protected void createAndAddActionButtons(final IContainer buttonsPanel)
	{
		final ITerminalFactory terminalFactory = getTerminalFactory();
		fShipperTransportation = terminalFactory.createTerminalCombobox(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID);
		fShipperTransportation.addListener(new UIPropertyChangeListener(fShipperTransportation)
		{
			@Override
			protected void propertyChangeEx(final PropertyChangeEvent evt)
			{
				if (ITerminalField.ACTION_ValueChanged.equals(evt.getPropertyName()))
				{
					// Get just selected Shipper Transportation
					final KeyNamePair value = (KeyNamePair)fShipperTransportation.getSelectedValue();

					// Notify the model about it (the model will add selected HUs to that shipper transportation)
					final AggregateHUEditorModel huEditorModel = getHUEditorModel();
					huEditorModel.onShipperTransportationChanged(value);

					// Reset the Shipper Transportation combobox back to null
					fShipperTransportation.setValue(null);
				}
			}
		});

		//
		// Initialize shipper transportation combobox and add it to buttons panel
		final List<KeyNamePair> shipperTransportations = getHUEditorModel().retrieveOpenShipperTransportations();
		fShipperTransportation.setValues(shipperTransportations);

		//
		// Layout
		buttonsPanel.add(fShipperTransportation, "newline"); // adding the Shipper Transportation dropdown on next line because this one could be huge
	}

	@Override
	protected final void onDialogOkAfterSave(final ITerminalDialog dialog)
	{
		// nothing
	}

	/**
	 * Checks if given {@link HUKey}s selection contains at least a key which is eligible for adding to shipper transporatation and if yes, enables the combobox.
	 */
	private final void updateShipperTransportationUI()
	{
		boolean shipperTransportationEnabled = false;

		final Set<HUKey> huKeys = getHUEditorModel().getSelectedHUKeys();
		for (final HUKey huKey : huKeys)
		{
			final I_M_HU hu = huKey.getM_HU();

			if (huShipperTransportationBL.isEligibleForAddingToShipperTransportation(hu))
			{
				shipperTransportationEnabled = true;
				break;
			}
		}

		fShipperTransportation.setEditable(shipperTransportationEnabled);
		fShipperTransportation.setVisible(shipperTransportationEnabled);
	}

}
