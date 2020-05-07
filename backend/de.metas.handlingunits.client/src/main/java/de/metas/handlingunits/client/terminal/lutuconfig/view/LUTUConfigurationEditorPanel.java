package de.metas.handlingunits.client.terminal.lutuconfig.view;

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


import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUTUConfigurationEditorModel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.AbstractLTCUPanel;

public class LUTUConfigurationEditorPanel extends AbstractLTCUPanel<LUTUConfigurationEditorModel>
{
	public LUTUConfigurationEditorPanel(final LUTUConfigurationEditorModel model)
	{
		super(model);
	}

	@Override
	protected void initLayout(final ITerminalFactory factory)
	{
		// create CU-Lane
		addCULane(true); // useQtyField=true
		// create TU-Lane
		addTULane(true); // useQtyField=true
		// create LU-Lane
		addLULane(true); // useQtyField=true
	}

	@Override
	protected void doOnDialogCanceling(final ITerminalDialog dialog)
	{
		// nothing
	}
}
