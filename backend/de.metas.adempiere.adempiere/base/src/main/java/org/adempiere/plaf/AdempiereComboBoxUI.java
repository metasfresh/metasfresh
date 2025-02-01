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

import java.awt.event.MouseListener;

import javax.accessibility.Accessible;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxButton;

import org.compiere.swing.CComboBox;

import com.jgoodies.looks.plastic.PlasticComboBoxUI;

/**
 * Compiere ComboBox UI. The ComboBox is opaque - with opaque arrow button and textfield background
 *
 * @author Jorg Janke
 * @version $Id: CompiereComboBoxUI.java,v 1.10 2005/10/09 19:01:37 jjanke Exp $
 */
public class AdempiereComboBoxUI extends PlasticComboBoxUI
{
	/**
	 * The UI Class ID to bind this UI to
	 * See {@link JComboBox#getUIClassID()}.
	 */
	public static final String uiClassID = "ComboBoxUI";

	private static final String KEY_EnableAutoCompletion = "ComboBox.AutoCompletion";
	
	/**
	 * Create UI
	 *
	 * @return new instance of {@link AdempiereComboBoxUI}
	 */
	public static ComponentUI createUI(final JComponent c)
	{
		return new AdempiereComboBoxUI();
	}
	
	public static Object[] getUIDefaults()
	{
		return new Object[] {
				uiClassID, AdempiereComboBoxUI.class.getName()
				
				//
				// Combobox's "arrow down" button size to be same size as the VEditor's action button.
				// NOTE: no, it's not a mistake that we set "ScrollBar.width", see com.jgoodies.looks.plastic.PlasticComboBoxUI.getEditableButtonWidth().
				// On the other hand, we are not using the original ScrollBar UI anymore, so it's safe to play with this one.
				, "ScrollBar.width", AdempierePLAF.createActiveValueProxy(VEditorUI.KEY_VEditor_Height, VEditorUI.DEFAULT_VEditor_Height)
				
				//
				// The combobox shall look similar with any other VEditor (have no borders, the arrow button shall look like the action button of an VEditor) 
				, "ComboBox.editorBorder", BorderFactory.createEmptyBorder()
				, "ComboBox.arrowButtonBorder", BorderFactory.createEmptyBorder()
				, "ComboBox.border", AdempierePLAF.createActiveValueProxy("TextField.border", BorderFactory.createEmptyBorder())
				
				// Don't paint combobox's focus border because it looks very ugly.
				// For future, if we want to change it and paint it nicely (see com.jgoodies.looks.plastic.PlasticComboBoxButton.paintComponent(Graphics))
				, "ComboBox.borderPaintsFocus", true
				
				, KEY_EnableAutoCompletion, true
		};
	}

	@Override
	public void installUI(final JComponent c)
	{
		final MouseListener[] mouseListeners = c.getMouseListeners();

		super.installUI(c);
		c.setBorder(UIManager.getBorder("ComboBox.border"));

		//
		// Enable CComboBox auto-complete if asked
		// NOTE: we do this only for CComboBox and not for all JComboBox-es 
		// because the auto-complete feature works with those ComboBoxes where the items are string or item's toString() returns the string representation.
		// Most of our CComboBoxes are about Value/KeyNamePairs, so we are fine.
		if (UIManager.getBoolean(KEY_EnableAutoCompletion) && (c instanceof CComboBox))
		{
			final CComboBox<?> comboBox = (CComboBox<?>)c;
			comboBox.enableAutoCompletion();
		}

		//
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
		return super.createArrowButton();
	}

	public JButton getArrowButton()
	{
		return arrowButton;
	}

	/**
	 * Set Icon of arrow button
	 * 
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
	
	public ComboPopup getComboPopup()
	{
		return popup;
	}
	
	public static final ComboPopup getComboPopup(final JComboBox<?> comboBox)
	{
		final ComboBoxUI comboBoxUI = comboBox.getUI();
		if (comboBoxUI instanceof AdempiereComboBoxUI)
		{
			return ((AdempiereComboBoxUI)comboBoxUI).getComboPopup();
		}
		
		//
		// Fallback:
		// Back door our way to finding the inner JList.
		//
		// it is unknown whether this functionality will work outside of Sun's
		// implementation, but the code is safe and will "fail gracefully" on
		// other systems
		//
		// see javax.swing.plaf.basic.BasicComboBoxUI.getAccessibleChild(JComponent, int)
		final Accessible a = comboBoxUI.getAccessibleChild(comboBox, 0);
		if (a instanceof ComboPopup)
		{
			return (ComboPopup)a;
		}
		else
		{
			return null;
		}
	}

	public static class AdempiereComboPopup extends BasicComboPopup
	{
		private static final long serialVersionUID = 3226003169560939486L;

		public AdempiereComboPopup(final JComboBox<Object> combo)
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
		
		@Override
		protected void configureScroller()
		{
			super.configureScroller();
			
			// In case user scrolls inside a combobox popup, we want to prevent closing the popup no matter if it could be scrolled or not.
			scroller.putClientProperty(AdempiereScrollPaneUI.PROPERTY_DisableWheelEventForwardToParent, true);
		}
	}
}
