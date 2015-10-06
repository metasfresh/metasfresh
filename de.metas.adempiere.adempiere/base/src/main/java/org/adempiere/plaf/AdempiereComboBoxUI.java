/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.adempiere.plaf;

/*
 * #%L
 * ADempiere ERP - Base
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

import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxButton;

import com.jgoodies.looks.plastic.PlasticComboBoxUI;

/**
 * Compiere ComboBox UI. The ComboBox is opaque - with opaque arrow button and textfield background
 *
 * @author Jorg Janke
 * @version $Id: CompiereComboBoxUI.java,v 1.10 2005/10/09 19:01:37 jjanke Exp $
 */
public class AdempiereComboBoxUI extends PlasticComboBoxUI
{
	/** the UI Class ID to bind this UI to */
	public static final String uiClassID = AdempierePLAF.getUIClassID(JComboBox.class, "ComboBoxUI");

	/**
	 * Create UI
	 * 
	 * @param c
	 * @return new instance of {@link AdempiereComboBoxUI}
	 */
	public static ComponentUI createUI(final JComponent c)
	{
		return new AdempiereComboBoxUI();
	}

	@Override
	public void installUI(final JComponent c)
	{
		final MouseListener[] mouseListeners = c.getMouseListeners();
		
		super.installUI(c);
		// c.setOpaque(false);
		
		
		// Bug in Metal: arrowButton gets Mouse Events, so add the JComboBox MouseListeners to the arrowButton
		for (final MouseListener ml : mouseListeners)
		{
			arrowButton.addMouseListener(ml);
		}

	}   // installUI

	/**
	 * Create opaque button
	 * 
	 * @return opaque button
	 */
	@Override
	protected JButton createArrowButton()
	{
		final JButton button = super.createArrowButton();
		return button;
	}   // createArrowButton

	public JButton getArrowButton()
	{
		return arrowButton;
	}

	/**
	 * Set Icon of arrow button
	 * 
	 * @param defaultIcon
	 */
	public void setIcon(Icon defaultIcon)
	{
		((MetalComboBoxButton)arrowButton).setComboIcon(defaultIcon);
	}   // setIcon

	/**
	 * Create Popup
	 * 
	 * @return {@link AdempiereComboPopup}
	 */
	@Override
	protected ComboPopup createPopup()
	{
		final AdempiereComboPopup popup = new AdempiereComboPopup(comboBox);
		popup.getAccessibleContext().setAccessibleParent(comboBox);
		return popup;
	}

	public static class AdempiereComboPopup extends BasicComboPopup
	{
		private static final long serialVersionUID = 3226003169560939486L;

		public AdempiereComboPopup(final JComboBox<?> combo)
		{
			super(combo);
		}

		@Override 
		protected int getPopupHeightForRowCount(final int maxRowCount) 
		{ 
			// ensure the combo box sized for the amount of data to be displayed
			final int itemCount = comboBox.getItemCount();
			int rows = itemCount < maxRowCount ? itemCount : maxRowCount;
			if (rows <= 0)
				rows = 1;
			
			return super.getPopupHeightForRowCount(1) * rows;
		}
	}
}
