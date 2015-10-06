package de.metas.handlingunits.client.terminal.mmovement.view.impl;

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


import java.util.Properties;

import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.handlingunits.client.terminal.mmovement.model.join.impl.HUMergeModel;

public class HUMergePanel extends AbstractMaterialMovementPanel<HUMergeModel>
{
	private static final String PANEL_Merge = "Merge";
	private final ITerminalKeyPanel keyLayoutPanel;

	public HUMergePanel(final HUMergeModel mergeModel)
	{
		super(mergeModel);

		final Properties layoutConstants = model.getLayoutConstants();
		final String fixedHeight = layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUSplit_KeyFixedHeight);
		final String fixedWidth = layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUSplit_KeyFixedWidth);

		final ITerminalFactory factory = getTerminalFactory();
		keyLayoutPanel = factory.createTerminalKeyPanel(model.getKeyLayout(), fixedHeight, fixedWidth);

		//
		// Add label
		final ITerminalLabel labelComp = factory.createLabel(HUMergePanel.PANEL_Merge);
		panel.add(labelComp, "w 60px");

		//
		// Add KeyPanel
		panel.add(keyLayoutPanel, "grow, push, h 90:90:90, wrap");
	}

	@Override
	protected final void loadFromModel()
	{
		// nothing for now
	}

	@Override
	protected void doOnDialogCanceling(final ITerminalDialog dialog)
	{
		// nothing for now
	}
}
