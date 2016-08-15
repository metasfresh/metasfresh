package de.metas.fresh.picking.form.swing;

import java.beans.PropertyChangeEvent;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalCheckboxField;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Picking HU editor (panel).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class PickingHUEditorPanel extends HUEditorPanel
{
	private static final String ACTION_ConsiderAttributesToggle = "de.metas.fresh.picking.form.swing.PickingHUEditorPanel.ConsiderAttributesToggle";

	public PickingHUEditorPanel(final PickingHUEditorModel model)
	{
		super(model);
	}

	@Override
	protected void createAndAddActionButtons(final IContainer buttonsPanel)
	{
		final ITerminalFactory factory = getTerminalFactory();

		//
		// Toggle: consider HU attributes when querying for available HUs (FRESH-578 #275)
		if(getHUEditorModel().isShowConsiderAttributesCheckbox())
		{
			final ITerminalCheckboxField considerAttributesToggle = factory.createTerminalCheckbox(ACTION_ConsiderAttributesToggle);
			considerAttributesToggle.setTextAndTranslate(ACTION_ConsiderAttributesToggle);
			considerAttributesToggle.setValue(getHUEditorModel().isConsiderAttributes());
			considerAttributesToggle.addListener(new UIPropertyChangeListener(factory, considerAttributesToggle)
			{
				@Override
				protected void init()
				{
					setShowGlassPane(true);
				}

				@Override
				protected void propertyChangeEx(final PropertyChangeEvent evt)
				{
					final boolean considerAttributes = considerAttributesToggle.getValue();
					getHUEditorModel().setConsiderAttributes(considerAttributes);
				}
			});
			buttonsPanel.add(considerAttributesToggle, "");
		}
	}

	@Override
	protected PickingHUEditorModel getHUEditorModel()
	{
		return (PickingHUEditorModel)super.getHUEditorModel();
	}

}
