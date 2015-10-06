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
import javax.swing.JEditorPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicEditorPaneUI;

/**
 * UI for {@link JEditorPane}.
 *
 * @author tsa
 *
 */
public class AdempiereEditorPaneUI extends BasicEditorPaneUI
{
	/** the UI Class ID to bind this UI to */
	public static final String uiClassID = AdempierePLAF.getUIClassID(JEditorPane.class, "EditorPaneUI");
	
	// NOTE: factory method used by UIDefaults. Without this method, this class won't be used!
	public static ComponentUI createUI(final JComponent c)
	{
		return new AdempiereEditorPaneUI();
	}

	@Override
	public void installUI(final JComponent c)
	{
		super.installUI(c);

		// Let the JEditorPane components (HTML editing) use the same fonts that we are using in our theme (see task 09141 - Font Harmonization).
		c.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
	}
}
