package de.metas.handlingunits.client.terminal.empties.view;

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

import org.compiere.model.I_C_BPartner;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalButtonGroup;
import de.metas.adempiere.form.terminal.ITerminalDateField;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalLookupField;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.client.terminal.empties.model.EmptiesShipReceiveModel;
import de.metas.handlingunits.client.terminal.empties.model.EmptiesShipReceiveModel.BPartnerReturnType;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.AbstractLTCUPanel;

/**
 *
 * @author tsa
 *
 * @task http://dewiki908/mediawiki/index.php/07193_R%C3%BCcknahme_Gebinde_%28104585385527%29
 */
public class EmptiesShipReceivePanel extends AbstractLTCUPanel<EmptiesShipReceiveModel>
{
	private ITerminalLookupField fBPartner;
	private ITerminalButtonGroup<BPartnerReturnType> fBPartnerReturnType;
	private ITerminalDateField fDate;

	private String keyLayoutButtonWidth = null;
	private String keyLayoutButtonHeight = null;

	public EmptiesShipReceivePanel(final EmptiesShipReceiveModel model)
	{
		super(model);
	}

	@Override
	protected void initComponents(final ITerminalFactory terminalFactory)
	{
		//
		// Layout settings
		model.getLUKeyLayout().setColumns(7);
		model.getTUKeyLayout().setColumns(7);
		keyLayoutButtonWidth = "160";
		keyLayoutButtonHeight = "70";
		setLaneCellConstraints(PANEL_LU_QTY, LANECELL_ANY, "h 80");
		setLaneCellConstraints(PANEL_TU_QTY, LANECELL_ANY, "h 320"); // height=4x80 ~ 4rows

		final PropertyChangeListener saveToModelPropertyChangeListener = getVMSynchronizer().getSaveToModelPropertyChangeListener();

		//
		// BPartner Lane - init components
		{
			fBPartnerReturnType = terminalFactory.createButtonGroup();
			fBPartnerReturnType.addListener(saveToModelPropertyChangeListener);
			// When user clicks on one of the return types, focus to BPartner field
			fBPartnerReturnType.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					fBPartner.requestFocus();
				}
			});

			// Create BPartnerReturnType buttons
			for (final BPartnerReturnType bpartnerReturnType : BPartnerReturnType.values())
			{
				final ITerminalButton button = fBPartnerReturnType.addButton(bpartnerReturnType, bpartnerReturnType.getDisplayName(getCtx()));
				button.setFocusable(false);
				button.setBackground(bpartnerReturnType.getColor());
			}
			
			fBPartner = terminalFactory.createTerminalLookupField(I_C_BPartner.COLUMNNAME_C_BPartner_ID, model.getBPartnerLookup());
			fBPartner.addListener(saveToModelPropertyChangeListener);
		}

		//
		// Date
		{
			fDate = terminalFactory.createTerminalDateField("Date");
			fDate.addListener(saveToModelPropertyChangeListener);
		}
	}

	@Override
	protected void initLayout(final ITerminalFactory terminalFactory)
	{
		//
		// Lane 1: BPartner, Date
		{
			final IContainer laneBPartner = terminalFactory.createContainer("", "align left");

			// Add BPartnerReturnType buttons to BPartner Lane
			for (final ITerminalButton btn : fBPartnerReturnType.getButtons())
			{
				laneBPartner.add(btn, "alignx, h 100%");
			}

			// Add BPartner label & field
			final ITerminalLabel lblBPartner = terminalFactory.createLabel(I_C_BPartner.COLUMNNAME_C_BPartner_ID, true);
			laneBPartner.add(lblBPartner, "h 100%");
			laneBPartner.add(fBPartner, "w 200px, h 100%");

			// Date
			final ITerminalLabel lblDate = terminalFactory.createLabel("Date", true);
			laneBPartner.add(lblDate, "h 100%");
			laneBPartner.add(fDate, "w 100px, h 100%");

			// Add Lane to main panel
			addHorizontalLane(laneBPartner, "grow 100 0, shrink 0, h ::40px");
		}
		
		//
		// Lane 2: BPartner Locations
		{
			final ITerminalKeyPanel bpLocationKeyPanel = createTerminalKeyPanel(model.getBPartnerLocationKeyLayout());
			addCustomLane(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpLocationKeyPanel);
		}

		//
		// LUs Lane
		addLULane(true); // useQtyField=true

		//
		// TUs Lane
		addTULane(true); // useQtyField=true
	}

	@Override
	protected ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keyLayout)
	{
		// return super.createTerminalKeyPanel(keyLayout);
		final ITerminalFactory factory = getTerminalFactory();
		return factory.createTerminalKeyPanel(keyLayout, keyLayoutButtonHeight, keyLayoutButtonWidth);

	}

	@Override
	protected void doOnDialogCanceling(final ITerminalDialog dialog)
	{
		// nothing to do
	}

	@Override
	protected final void loadFromModel()
	{
		super.loadFromModel();

		final EmptiesShipReceiveModel model = getModel();
		if (model == null)
		{
			return;
		}
		
		final BPartnerReturnType bpartnerReturnType = model.getBPartnerReturnType();

		fBPartner.setValue(model.getBPartner());
		fBPartner.setEditable(bpartnerReturnType != null);
		fBPartnerReturnType.setValue(bpartnerReturnType);
		fDate.setValue(model.getDate());
	}

	@Override
	protected final void saveToModel()
	{
		final EmptiesShipReceiveModel model = getModel();
		if (model == null)
		{
			return;
		}

		if(fBPartner.getValue() != model.getBPartner())
		{
			// #643: In case the partner was changed, the order will no longer be the one from the selected receipt schedule
			model.setOrder(null);
		}
		
		model.setBPartner(fBPartner.getValue());
		model.setBpartnerReturnType(fBPartnerReturnType.getValue());
		model.setDate(fDate.getValue());
	}

}
