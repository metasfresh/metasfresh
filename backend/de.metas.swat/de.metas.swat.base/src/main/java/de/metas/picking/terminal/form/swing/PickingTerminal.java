/**
 *
 */
package de.metas.picking.terminal.form.swing;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Dimension;
import java.awt.Frame;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.reflect.ClassReference;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Picking Terminal (first window)
 */
public class PickingTerminal implements FormPanel
{
	private static final transient Logger logger = LogManager.getLogger(PickingTerminal.class);

	private IPickingTerminalPanel panel = null;

	private static ClassReference<? extends IPickingTerminalPanel> pickingTerminalPanelClass = null;

	public static void setPickingTerminalPanelClass(@NonNull final Class<? extends IPickingTerminalPanel> pickingTerminalPanelClass)
	{
		PickingTerminal.pickingTerminalPanelClass = ClassReference.of(pickingTerminalPanelClass);
		logger.info("Set pickingTerminalPanelClass={}", pickingTerminalPanelClass);
	}

	@Override
	public void init(final int windowNo, final FormFrame frame)
	{
		if (pickingTerminalPanelClass == null)
		{
			throw new AdempiereException("No picking terminal panel class was configured");
		}

		Env.setContext(Env.getCtx(), windowNo, "AD_Form_ID", frame.getAD_Form_ID());

		this.panel = newPickingTerminalPanel();

		// cg: maximum size should be 1024x768 : see task 03520
		final Dimension frameSize = new Dimension(1024, 740);

		frame.setResizable(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setMinimumSize(frameSize);
		frame.setMaximumSize(frameSize);
		// NOTE: if you are not setting the preferredSize also, the maximum size won't be enforced
		// see http://stackoverflow.com/questions/10157954/java-swing-setmaximumsize-not-working
		frame.setPreferredSize(frameSize);

		panel.init(windowNo, frame);
	}

	private IPickingTerminalPanel newPickingTerminalPanel()
	{
		final IPickingTerminalPanel panel;
		try
		{
			panel = pickingTerminalPanelClass.getReferencedClass().newInstance();
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		return panel;
	}

	@Override
	public void dispose()
	{
		if (panel != null)
		{
			panel.dispose();
			panel = null;
		}
	}
}
