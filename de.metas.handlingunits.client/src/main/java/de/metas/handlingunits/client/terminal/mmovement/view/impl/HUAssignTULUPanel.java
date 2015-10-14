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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.metas.adempiere.form.terminal.ITerminalCheckboxField;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.handlingunits.client.terminal.mmovement.model.assign.impl.HUAssignTULUModel;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Panel responsible for assigning TUs on LU (LU zuteilen).
 *
 * @author al
 */
public class HUAssignTULUPanel extends AbstractLTCUPanel<HUAssignTULUModel>
{
	/**
	 * If true, then assume that it's the vendor's/customer's packing material; otherwise, assume it's "ours".
	 */
	private static final String CHECKBOX_HUPlanningReceiptOwnerPM = I_M_HU.COLUMNNAME_HUPlanningReceiptOwnerPM;

	public HUAssignTULUPanel(final HUAssignTULUModel model)
	{
		super(model);
	}

	@Override
	protected final void initLayout(final ITerminalFactory terminalFactory)
	{
		// create LU-Lane
		addLULane(false); // useQtyField=false

		// 08382: Add checkbox for LU packing material's owner (vendor/customer or "us")
		{
			mockHorizontalLaneLabelSpace();

			final ITerminalCheckboxField huPlanningReceiptOwnerPMCheckbox = terminalFactory.createTerminalCheckbox(CHECKBOX_HUPlanningReceiptOwnerPM);
			huPlanningReceiptOwnerPMCheckbox.setTextAndTranslate(CHECKBOX_HUPlanningReceiptOwnerPM);
			huPlanningReceiptOwnerPMCheckbox.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					final Boolean isHUPlanningReceiptOwnerPM = huPlanningReceiptOwnerPMCheckbox.getValue();
					getModel().setHUPlanningReceiptOwnerPM(isHUPlanningReceiptOwnerPM);
				}
			});

			addHorizontalLane(huPlanningReceiptOwnerPMCheckbox, ", wrap");
		}
	}

	@Override
	protected final void doOnDialogCanceling(final ITerminalDialog dialog)
	{
		// nothing for now
	}
}
