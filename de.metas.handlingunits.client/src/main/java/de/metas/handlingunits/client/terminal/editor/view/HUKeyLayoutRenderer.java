package de.metas.handlingunits.client.terminal.editor.view;

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


import java.awt.Color;

import de.metas.adempiere.form.terminal.DefaultKeyPanelRenderer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.util.Check;

public class HUKeyLayoutRenderer extends DefaultKeyPanelRenderer
{
	private final HUEditorPanel editorPanel;

	public HUKeyLayoutRenderer(final HUEditorPanel editorPanel)
	{
		super();

		Check.assumeNotNull(editorPanel, "editorPanel not null");
		this.editorPanel = editorPanel;
	}

	@Override
	protected Color getBackgroundColor(final ITerminalKey terminalKey, final IKeyLayout keyLayout)
	{
		if (terminalKey instanceof IHUKey)
		{
			final IHUKey huKey = (IHUKey)terminalKey;
			return editorPanel.getHUKeyBackgroundColor(huKey);
		}
		else
		{
			return super.getBackgroundColor(terminalKey, keyLayout);
		}
	}

}
