package de.metas.handlingunits.client.terminal.form;

import org.adempiere.util.Check;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;

import de.metas.handlingunits.client.terminal.editor.model.impl.AbstractHUEditorFrame;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.process.ProcessInfo;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class AbstractHUEditorForm<MT extends HUEditorModel> implements FormPanel
{
	private FormFrame frame;
	private AbstractHUEditorFrame<MT> framePanel;
	
	@Override
	public void init(final int WindowNo, final FormFrame frame)
	{
		this.frame = frame;
		final ProcessInfo processInfo = frame.getProcessInfo();
		
		final I_M_InOut shipment = processInfo.getRecord(I_M_InOut.class);
		
		framePanel = createFramePanel(frame, shipment);
		Check.assumeNotNull(framePanel, "framePanel not null");
	}

	/**
	 * Creates and adds actual frame panel.
	 *
	 * To be overriden by extension classes.
	 *
	 * @param frame outer frame to decorate
	 * @return inner frame component
	 */
	protected abstract AbstractHUEditorFrame<MT> createFramePanel(final FormFrame frame, final I_M_InOut shipment);

	public final AbstractHUEditorFrame<MT> getFramePanel()
	{
		return framePanel;
	}

	@Override
	public final void dispose()
	{
		if (framePanel != null)
		{
			framePanel.dispose();
			framePanel = null;

		}

		if (frame != null)
		{
			frame.dispose();
			frame = null;
		}

	}

	public final void show()
	{
		if (frame == null)
		{
			// not initialized or already disposed
			return;
		}

		frame.setVisible(true);
	}

}


