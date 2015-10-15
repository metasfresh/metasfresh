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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Dimension;
import java.awt.Frame;
import java.util.logging.Level;

import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * @author cg
 * 
 */
public class PickingTerminal implements FormPanel
{
	private final transient CLogger logger = CLogger.getCLogger(getClass());
	
	private SwingPickingTerminalPanel panel = null;
	
	static String className = null;
	
	static public String getClassName()
	{
		return className;
	}

	static public void setClassName(String name)
	{
		className = name;
	}

	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		Env.setContext(Env.getCtx(), WindowNo, "AD_Form_ID", frame.getAD_Form_ID());

		SwingPickingTerminalPanel panel = null;
		try
		{
			panel = Util.getInstance(SwingPickingTerminalPanel.class, className);
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			panel = null;
		}

		// Fallback
		if (panel == null)
		{
			panel = new SwingPickingTerminalPanel();
		}

		// cg:  maximum size should be 1024x768 : see task 03520
		final Dimension frameSize = new Dimension(1024, 740);
		
		frame.setResizable(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setMinimumSize(frameSize);
		frame.setMaximumSize(frameSize);
		// NOTE: if you are not setting the preferredSize also, the maximum size won't be enforced
		// see http://stackoverflow.com/questions/10157954/java-swing-setmaximumsize-not-working
		frame.setPreferredSize(frameSize);
		
		this.panel = panel;
		panel.init(WindowNo, frame);
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
