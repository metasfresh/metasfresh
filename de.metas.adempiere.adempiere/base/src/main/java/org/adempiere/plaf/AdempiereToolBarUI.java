package org.adempiere.plaf;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolBarUI;

public class AdempiereToolBarUI extends MetalToolBarUI
{
	public static final String uiClassID = AdempierePLAF.getUIClassID(JToolBar.class, "ToolBarUI");

	public static ComponentUI createUI(final JComponent b)
	{
		return new AdempiereToolBarUI();
	}

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				uiClassID, AdempiereToolBarUI.class.getName()
		};
	}

	@Override
	public void installUI(final JComponent c)
	{
		super.installUI(c);
		
		final JToolBar toolBar = (JToolBar)c;
		
		toolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
	}

}
