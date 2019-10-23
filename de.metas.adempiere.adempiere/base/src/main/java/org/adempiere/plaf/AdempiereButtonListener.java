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

import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.AbstractButton;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentInputMapUIResource;
import javax.swing.plaf.basic.BasicButtonListener;

/**
 * 	Button Listener
 *	
 *  @author Jorg Janke
 *  @version $Id: CompiereButtonListener.java,v 1.1 2005/12/05 02:38:28 jjanke Exp $
 */
public class AdempiereButtonListener extends BasicButtonListener
{
	/**
	 * 	Adempiere Button Listener
	 *	@param b button
	 */
	public AdempiereButtonListener (AbstractButton b)
	{
		super (b);
	}	//	AdempiereButtonListener
	
	/**
	 * 	Install Keyboard Actions
	 *	@param c component
	 */
	public void installKeyboardActions (JComponent c)
	{
		super.installKeyboardActions (c);
		updateMnemonicBindingX ((AbstractButton)c);
	}	//	installKeyboardActions

	/**
	 * 	Property Change
	 *	@param e event
	 */
	public void propertyChange (PropertyChangeEvent e)
	{
		String prop = e.getPropertyName();
		if (prop.equals(AbstractButton.MNEMONIC_CHANGED_PROPERTY))
			updateMnemonicBindingX ((AbstractButton)e.getSource());
		else
			super.propertyChange (e);
	}	//	propertyChange
	
	/**
	 * 	Update Mnemonic Binding
	 *	@param b button
	 */
    void updateMnemonicBindingX (AbstractButton b) 
    {
    	int m = b.getMnemonic();
    	if (m != 0) 
    	{
    	    InputMap map = SwingUtilities.getUIInputMap(b, JComponent.WHEN_IN_FOCUSED_WINDOW);

    	    if (map == null) 
    	    {
    	    	map = new ComponentInputMapUIResource(b);
    	    	SwingUtilities.replaceUIInputMap(b, JComponent.WHEN_IN_FOCUSED_WINDOW, map);
    	    }
    	    map.clear();
    	    String className = b.getClass().getName();
    	    int mask = InputEvent.ALT_MASK;		//	Default Buttons
    	    if (b instanceof JCheckBox 			//	In Tab
    	    	|| className.indexOf("VButton") != -1)
    	    	mask = InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK;
    	    map.put(KeyStroke.getKeyStroke(m, mask, false), "pressed");
    	    map.put(KeyStroke.getKeyStroke(m, mask, true), "released");
    	    map.put(KeyStroke.getKeyStroke(m, 0, true), "released");
    	}
    	else 
    	{
    		InputMap map = SwingUtilities.getUIInputMap(b, JComponent.WHEN_IN_FOCUSED_WINDOW);
    		if (map != null)
    			map.clear();
    	}
    }	//	updateMnemonicBindingX
    
}	//	AdempiereButtonListener
