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

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.event.ActionEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentInputMapUIResource;
import javax.swing.plaf.metal.MetalLabelUI;

/**
 * 	Adempiere Label UI
 *	
 *  @author Jorg Janke
 *  @version $Id: CompiereLabelUI.java,v 1.2 2005/12/05 02:38:28 jjanke Exp $
 */
public class AdempiereLabelUI extends MetalLabelUI
{
	/** the UI Class ID to bind this UI to */
	public static final String uiClassID = AdempierePLAF.getUIClassID(JLabel.class, "LabelUI");
	
	public static final String KEY_Border = "Label.border";

	/** Singleton				*/
	private static final AdempiereLabelUI adempiereLabelUI = new AdempiereLabelUI();

    /**
     * 	Create UI
     *	@param c component
     *	@return singleton
     */
    public static AdempiereLabelUI createUI(JComponent c) 
    {
    	return adempiereLabelUI;
    }	//	createUI

    @Override
    protected void installDefaults(final JLabel c)
    {
    	super.installDefaults(c);
    	
    	LookAndFeel.installBorder(c, KEY_Border);
    }
	
	/**
	 * 	Install Keyboard Actions
	 *	@param l label
	 */
	@Override
	protected void installKeyboardActions (JLabel l)
	{
	//	super.installKeyboardActions(l);
        int dka = l.getDisplayedMnemonic();
		if (dka != 0)
		{
			Component lf = l.getLabelFor();
			if (lf != null)
			{
				ActionMap actionMap = l.getActionMap();
				actionMap.put(PRESS, ACTION_PRESS);
				InputMap inputMap = SwingUtilities.getUIInputMap (l, JComponent.WHEN_IN_FOCUSED_WINDOW);
				if (inputMap == null)
				{
					inputMap = new ComponentInputMapUIResource (l);
					SwingUtilities.replaceUIInputMap (l, JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
				}
				inputMap.clear ();
				inputMap.put(KeyStroke.getKeyStroke(dka, ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK, false), PRESS);
			}
		}
	}	//	installKeyboardActions
	
	
	/**	Action Name					*/
	private static final String PRESS   = "press";
	/** Press Action				*/
	private static final PressAction ACTION_PRESS = new PressAction();
	
	/**
	 * 	Compiere Label UI Actions
	 */
    private static class PressAction extends UIAction
	{

		PressAction ()
		{
			super (PRESS);
		}

		@Override
		public void actionPerformed (ActionEvent e)
		{
			JLabel label = (JLabel)e.getSource ();
			String key = getName ();
			if (key.equals(PRESS))
			{
				doPress (label);
			}
		}	//	actionPerformed

		/**
		 * 	Do Press - Focus the Field
		 *	@param label label
		 */
		private void doPress (JLabel label)
		{
			Component labelFor = label.getLabelFor ();
			if (labelFor != null && labelFor.isEnabled ())
			{
				Component owner = label.getLabelFor ();
				if (owner instanceof Container
					&& ((Container)owner).isFocusCycleRoot ())
				{
					owner.requestFocus ();
				}
				else
				{
				 	if (owner instanceof Container) 
				 	{
				 	    Container container = (Container)owner;
				 	    if (container.isFocusCycleRoot()) 
				 	    {
				 	    	FocusTraversalPolicy policy = container.getFocusTraversalPolicy();
				 	    	Component comp = policy.getDefaultComponent(container);
				 	    	if (comp != null) 
				 	    	{
				 	    		comp.requestFocus();
				 	    		return;
				 	    	}
				 	    }
				 	    Container rootAncestor = container.getFocusCycleRootAncestor();
				 	    if (rootAncestor != null) 
				 	    {
				 	    	FocusTraversalPolicy policy = rootAncestor.getFocusTraversalPolicy();
				 	    	Component comp = policy.getComponentAfter(rootAncestor, container);
				 	    	if (comp != null && SwingUtilities.isDescendingFrom(comp, container)) 
				 	    	{
				 	    		comp.requestFocus();
				 	    		return;
				 	    	}
				 	    }
				 	}
			        if (owner.isFocusable()) 
			        {
				 	    owner.requestFocus();
				 	    return;
			        }
			        //	No Forcus
				}
			}
		}	//	doPress
	}	//	PressAction

}	//	AdempiereLabelUI
