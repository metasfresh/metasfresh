/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.adempiere.images.Images;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBoxMenuItem;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CToggleButton;
import org.compiere.util.Env;

/**
 *  Application Action.
 *		Creates Action with MenuItem and Button, delegate execution of action to an attached ActionListener instance
 *		The ActionCommand is translated for display
 *		If translated text contains &, the next character is the Mnemonic
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: AppsAction.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class AppsAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8522301377339185496L;

	/**
	 *  Application Action
	 *
	 *  @param   action base action command - used as AD_Message for Text and Icon name
	 *  @param   accelerator optional keystroke for accelerator
	 *  @param   toggle is toggle action (maintains state)
	 */
	public AppsAction (String action, KeyStroke accelerator, boolean toggle)
	{
		this(action
				, accelerator
				, (String)null // toolTipText
				, toggle // toggle
				, false // smallSize
		);
	}
	
	/**
	 *  Application Action
	 *
	 *  @param   action base action command - used as AD_Message for Text and Icon name
	 *  @param   accelerator optional keystroke for accelerator
	 *  @param   toolTipText text, if null deferred from action
	 */
	public AppsAction (String action, KeyStroke accelerator, String toolTipText)
	{
		this(action
				, accelerator
				, toolTipText
				, false // toggle
				, false // smallSize
		);
	}	//	AppsAction
	
	/**
	 *  Application Action
	 *
	 *  @param   action base action command - used as AD_Message for Text and Icon name
	 *  @param   accelerator optional keystroke for accelerator
	 *  @param   toolTipText text, if null deferred from action
	 *  @param   toggle is toggle action (maintains state)
	 */
	public AppsAction (String action, KeyStroke accelerator, String toolTipText, boolean toggle)
	{
		this(action
				, accelerator
				, toolTipText
				, toggle
				, false // smallSize
		);
	}
	
	public AppsAction(final String action, 
			final KeyStroke accelerator, 
			String toolTipText, 
			final boolean toggle, 
			final boolean smallSize)
	{
		super();
		this._action = action;
		this._acceleratorDefault = accelerator;
		this.toggleButton = toggle;

		//
		// Tooltip and mnemonic
		{
			if (toolTipText == null)
				toolTipText = Services.get(IMsgBL.class).getMsg(Env.getCtx(), action);
			final int pos = toolTipText.indexOf('&');
			if (pos != -1  && toolTipText.length() > pos)	//	We have a mnemonic - creates ALT-_
			{
				final Character ch = toolTipText.toUpperCase().charAt(pos+1);
				if (ch != ' ')
				{
					toolTipText = toolTipText.substring(0, pos) + toolTipText.substring(pos+1);
					putValue(Action.MNEMONIC_KEY, new Integer(ch.hashCode()));
				}
			}
		}
		
		//
		// Load icons
		final Icon iconSmall = getIcon(action, true);
		final Icon iconLarge = getIcon(action, false);
		Icon iconSmallPressed = null;
		Icon iconLargePressed = null;
		if (toggleButton)
		{
			final String iconNamePressed = action + "X"; // NOTE: ToggleIcons have the pressed name with X
			iconSmallPressed = getIcon(iconNamePressed, true);
			if (iconSmallPressed == null)
				iconSmallPressed = iconSmall;
			
			iconLargePressed = getIcon(iconNamePressed, false);				
			if (iconLargePressed == null)
				iconLargePressed = iconLarge;
		}
		// Set icons
		this.iconSmallPressed = iconSmallPressed;
		if (smallSize)
		{
			this.icon = iconSmall;
			this.iconPressed = iconSmallPressed;
		}
		else
		{
			this.icon = iconLarge;
			this.iconPressed = iconLargePressed;
		}
		

		//	Attributes
		putValue(Action.NAME, toolTipText);					//	Display
		putValue(Action.SMALL_ICON, iconSmall);                 //  Icon
		putValue(Action.SHORT_DESCRIPTION, toolTipText);	//	Tooltip
	//	putValue(Action.DEFAULT, text);						//	Not Used
		putValue(Action.ACTION_COMMAND_KEY, action);      //  ActionCommand
		
		//
		// Accelerator
		setAccelerator(accelerator);
	}	//	Action

	/** Button Size     			*/
	public static final Dimension	BUTTON_SIZE = new Dimension(28,28);
	/** Button Insets   			*/
	public static final Insets		BUTTON_INSETS = new Insets(0, 0, 0, 0);
	/** CButton or CToggelButton	*/
	private AbstractButton 	_button;
	/**	Menu						*/
	private JMenuItem		_menuItem;

	private final String	_action;
	//
	private final KeyStroke	_acceleratorDefault;
	private KeyStroke		_accelerator = null;
	//
	private final Icon icon;
	private final Icon iconPressed;
	private final Icon iconSmallPressed;
	//
	private final boolean toggleButton;
	private boolean pressed = false;
	//
	private ActionListener m_delegate = null;

	/**
	 *	Get Icon with name action
	 *  @param name name
	 *  @param small small
	 *  @return Icon
	 */
	private static ImageIcon getIcon(final String name, final boolean small)
	{
		String fullName = name + (small ? "16" : "24");
		return Images.getImageIcon2(fullName);
	}	//	getIcon

	/**
	 *	Get Name/ActionCommand
	 *  @return ActionName
	 */
	public String getName()
	{
		return getAction();
	}	//	getName

	/**
	 * Return Button
	 * 
	 * @return Button
	 */
	public final AbstractButton getButton()
	{
		if(_button == null)
		{
			this._button = createButton();
		}
		return _button;
	}
	
	private AbstractButton createButton()
	{
		final String action = getAction();
		
		final AbstractButton button;
		if (toggleButton)
		{
			button = new CToggleButton(this);
			button.setSelectedIcon(iconPressed);
		}
		else
		{
			button = new CButton(this);
		}
		button.setName(action);
		button.setIcon(icon);
		button.setText(null); // no text
		
		button.setActionCommand(action);
		button.setMargin(BUTTON_INSETS);
		button.setSize(BUTTON_SIZE);
		
		//
		// Action
		button.getActionMap().put(action, this);

		//
		// Update button state from this action 
		updateButtonPressedState(button);
		replaceAcceleratorKey(button, getAccelerator(), null);

		return button;
	}
	
	/** @return current created button or null */
	private AbstractButton getButtonIfExists()
	{
		return _button;
	}
	
	private final void replaceAcceleratorKey(final JComponent comp, final KeyStroke acceleratorNew, final KeyStroke acceleratorOld)
	{
		if(comp == null)
		{
			return;
		}
		
		if (acceleratorOld != null)
		{
			comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(acceleratorOld);
		}

		if (acceleratorNew != null)
		{
			comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(acceleratorNew, getAction());
		}
	}
	
	private void updateButtonPressedState(final AbstractButton button)
	{
		if (!toggleButton)
		{
			return;
		}
		
		if(button == null)
		{
			return;
		}
		
		button.setSelected(pressed);
	}


	/**
	 *	Return MenuItem
	 *  @return MenuItem
	 */
	public JMenuItem getMenuItem()
	{
		if (_menuItem == null)
		{
			_menuItem = createMenuItem();
		}
		return _menuItem;
	}
	
	private JMenuItem createMenuItem()
	{
		final JMenuItem menuItem;
		if (toggleButton)
		{
			menuItem = new CCheckBoxMenuItem(this);
			menuItem.setSelectedIcon(iconSmallPressed);
		}
		else
		{
			menuItem = new CMenuItem(this);
		}
		menuItem.setActionCommand(getAction());
		
		//
		// Update menu item's state from this action 
		updateButtonPressedState(menuItem);
		replaceAcceleratorKey(menuItem, getAccelerator(), null);
		
		return menuItem;
	}
	
	private final JMenuItem getMenuItemIfExists()
	{
		return _menuItem;
	}

	/**
	 *	Set Delegate to receive the actionPerformed calls
	 *  @param al listener
	 */
	public void setDelegate(final ActionListener al)
	{
		m_delegate = al;
	}	//	setDelegate

	/**
	 *	Toggle
	 *  @param pressed pressed
	 */
	public void setPressed (final boolean pressed)
	{
		if (!toggleButton)
		{
			return;
		}
		
		this.pressed = pressed;

		updateButtonPressedState(getButtonIfExists());
		updateButtonPressedState(getMenuItemIfExists());
	}	//	setPressed

	/**
	 *	IsPressed
	 *  @return true if pressed
	 */
	public boolean isPressed()
	{
		return pressed;
	}	//	isPressed

	/**
	 * 	Get Mnemonic character
	 *	@return character
	 */
	public Character getMnemonic()
	{
		Object oo = getValue(Action.MNEMONIC_KEY);
		if (oo instanceof Integer)
			return (char)((Integer)oo).intValue();
		return null;
	}	//	getMnemonic
	
	@Override
	public final void actionPerformed(final ActionEvent e)
	{
		// Toggle Items
		if (toggleButton)
		{
			setPressed(!pressed);
		}
		
		// Fire delegated action listener
		if (m_delegate != null)
		{
			m_delegate.actionPerformed(e);
		}
	}

	/**
	 *	Dispose
	 */
	public void dispose()
	{
		m_delegate = null;
		
		// dispose generated components
		_button = null;
		_menuItem = null;
	}	//	dispose

	/**
	 *  String Info
	 *  @return String Representation
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("AppsAction[");
		sb.append(_action);
		
		final Object accelerator = getValue(Action.ACCELERATOR_KEY);
		if (accelerator != null)
			sb.append(",Accelerator=").append(accelerator);
		
		final Character mnemonic = getMnemonic();
		if (mnemonic != null)
			sb.append(",MnemonicKey=").append(mnemonic);
		
		sb.append("]");
		return sb.toString();
	}   //  toString

// metas: begin
//	private boolean m_small = false;
	
	public AppsAction(boolean small, String action, KeyStroke accelerator, String text)
	{
		this(action, accelerator, text, false, small);
	} // AppsAction
	
	public AppsAction(String action, KeyStroke accelerator, boolean toggle,
			boolean small)
	{
		this(action, accelerator, null, toggle, small);
	}
	
	public void setAccelerator(final KeyStroke accelerator)
	{
		final KeyStroke acceleratorOld = this._accelerator;
		this._accelerator = accelerator;
		
		putValue(Action.ACCELERATOR_KEY, accelerator);      //  KeyStroke

		// Update accelerator in created components (if any)
		replaceAcceleratorKey(getButtonIfExists(), accelerator, acceleratorOld);
		replaceAcceleratorKey(getMenuItemIfExists(), accelerator, acceleratorOld);
	}
	
	public void setDefaultAccelerator()
	{
		setAccelerator(_acceleratorDefault);
	}
	
	public KeyStroke getAccelerator()
	{
		return _accelerator;
	}
	
	public String getAction()
	{
		return _action;
	}
// metas: end
}	//	AppsAction
