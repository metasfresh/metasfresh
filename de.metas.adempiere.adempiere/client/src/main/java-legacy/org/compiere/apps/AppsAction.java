/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.adempiere.images.Images;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBoxMenuItem;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CToggleButton;
import org.compiere.util.Env;

/**
 * Application Action.
 *
 * Creates Action with MenuItem and Button, delegate execution of action to an attached ActionListener instance The ActionCommand is translated for display If translated text contains &, the next
 * character is the Mnemonic.
 *
 * To create a new {@link AppsAction}, please use {@link #builder()}.
 *
 * @author Jorg Janke
 * @author metas-dev <dev@metasfresh.com>
 */
public final class AppsAction extends AbstractAction
{
	private static final long serialVersionUID = 8522301377339185496L;

	/**
	 * @return Application Action builder
	 */
	public static final Builder builder()
	{
		return new Builder();
	}

	private AppsAction(final Builder builder)
	{
		super();
		_actionCommand = builder.getAction();
		_acceleratorDefault = builder.getAccelerator();
		toggleButton = builder.isToggleButton();

		final String displayName;
		if (builder.isIsRetrieveAppsActionMessage())
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			displayName = msgBL.getMsg(Env.getCtx(), _actionCommand);
		}
		else
		{
			displayName = _actionCommand;
		}

		//
		// Button insets
		if (builder.getButtonInsets() != null)
		{
			buttonInsets = builder.getButtonInsets();
		}
		else
		{
			buttonInsets = BUTTON_INSETS;
		}
		//
		buttonDefaultCapable = builder.getButtonDefaultCapable();

		//
		// Tooltip and mnemonic
		String toolTipText = builder.getToolTipText();
		{
			if (toolTipText == null)
			{
				toolTipText = displayName;
			}
			final int pos = toolTipText.indexOf('&');
			if (pos != -1 && toolTipText.length() > pos)     	// We have a mnemonic - creates ALT-_
			{
				final Character ch = toolTipText.toUpperCase().charAt(pos + 1);
				if (ch != ' ')
				{
					toolTipText = toolTipText.substring(0, pos) + toolTipText.substring(pos + 1);
					putValue(Action.MNEMONIC_KEY, new Integer(ch.hashCode()));
				}
			}
		}

		//
		// Load icons
		final Icon iconSmall = getIcon(_actionCommand, true);
		final Icon iconLarge = getIcon(_actionCommand, false);
		Icon iconSmallPressed = null;
		Icon iconLargePressed = null;
		if (toggleButton)
		{
			final String iconNamePressed = _actionCommand + "X"; // NOTE: ToggleIcons have the pressed name with X
			iconSmallPressed = getIcon(iconNamePressed, true);
			if (iconSmallPressed == null)
			{
				iconSmallPressed = iconSmall;
			}

			iconLargePressed = getIcon(iconNamePressed, false);
			if (iconLargePressed == null)
			{
				iconLargePressed = iconLarge;
			}
		}
		// Set icons
		this.iconSmallPressed = iconSmallPressed;
		if (builder.isSmallSize())
		{
			icon = iconSmall;
			iconPressed = iconSmallPressed;
		}
		else
		{
			icon = iconLarge;
			iconPressed = iconLargePressed;
		}

		//
		// Button text
		String buttonText;
		if (builder.isUseTextFromActionName())
		{
			buttonText = displayName;
		}
		else
		{
			buttonText = builder.getText();
		}
		// If there is no icon and no button text, then use the text from action,
		// because else we will get an empty button.
		// NOTE: we are checking the text without stripping the spaces because we want to handle the case when,
		// the developer programatically sets " " as text.
		if (icon == null && Check.isEmpty(buttonText, false))
		{
			buttonText = displayName;
		}
		this.buttonText = buttonText;

		// Attributes
		putValue(Action.NAME, displayName);						// Display
		putValue(Action.SMALL_ICON, iconSmall);					// Icon
		putValue(Action.SHORT_DESCRIPTION, toolTipText);		// Tooltip
		putValue(Action.ACTION_COMMAND_KEY, _actionCommand);	// ActionCommand

		//
		// Accelerator
		setAccelerator(_acceleratorDefault);
	}	// Action

	//
	// Button
	/** Button Size */
	public static final Dimension BUTTON_SIZE = new Dimension(28, 28);
	/** Default button Insets */
	public static final Insets BUTTON_INSETS = new Insets(0, 0, 0, 0);
	/** {@link CButton} or {@link CToggleButton} */
	private AbstractButton _button;
	private final String buttonText;
	private final Insets buttonInsets;
	private final Boolean buttonDefaultCapable;

	/** Menu */
	private JMenuItem _menuItem;

	private final String _actionCommand;
	//
	private final KeyStroke _acceleratorDefault;
	private KeyStroke _accelerator = null;
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
	 * Get Icon with name action
	 *
	 * @param name name
	 * @param small small
	 * @return Icon
	 */
	private static ImageIcon getIcon(final String name, final boolean small)
	{
		final String fullName = name + (small ? "16" : "24");
		return Images.getImageIcon2(fullName);
	}	// getIcon

	/**
	 * Get Name/ActionCommand
	 *
	 * @return ActionName
	 */
	public String getName()
	{
		return getAction();
	}	// getName

	/**
	 * Return Button
	 *
	 * @return Button
	 */
	public final AbstractButton getButton()
	{
		if (_button == null)
		{
			_button = createButton();
		}
		return _button;
	}

	/**
	 * @return {@link #getButton()}, casted to {@link CButton}.
	 * @throws IllegalStateException if the button cannot be casted to {@link CButton} (i.e. it's a toggle button)
	 */
	public final CButton getCButton()
	{
		final AbstractButton button = getButton();
		if (button instanceof CButton)
		{
			return (CButton)button;
		}
		else
		{
			throw new IllegalStateException("Cannot cast " + button + " to " + CButton.class);
		}
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
			final CButton cbutton = new CButton(this);
			if (buttonDefaultCapable != null)
			{
				cbutton.setDefaultCapable(buttonDefaultCapable);
			}
			button = cbutton;
		}

		button.setName(action);
		button.setIcon(icon);
		button.setText(buttonText);

		button.setActionCommand(action);
		button.setMargin(buttonInsets);
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

	/**
	 * @return current created button or null
	 */
	private AbstractButton getButtonIfExists()
	{
		return _button;
	}

	private final void replaceAcceleratorKey(final JComponent comp, final KeyStroke acceleratorNew, final KeyStroke acceleratorOld)
	{
		if (comp == null)
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

		if (button == null)
		{
			return;
		}

		button.setSelected(pressed);
	}

	/**
	 * Return MenuItem
	 *
	 * @return MenuItem
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
	 * Set Delegate to receive the actionPerformed calls
	 *
	 * @param al listener
	 */
	public void setDelegate(final ActionListener al)
	{
		m_delegate = al;
	}	// setDelegate

	/**
	 * Toggle
	 *
	 * @param pressed pressed
	 */
	public void setPressed(final boolean pressed)
	{
		if (!toggleButton)
		{
			return;
		}

		this.pressed = pressed;

		updateButtonPressedState(getButtonIfExists());
		updateButtonPressedState(getMenuItemIfExists());
	}	// setPressed

	/**
	 * IsPressed
	 *
	 * @return true if pressed
	 */
	public boolean isPressed()
	{
		return pressed;
	}	// isPressed

	/**
	 * Get Mnemonic character
	 *
	 * @return character
	 */
	public Character getMnemonic()
	{
		final Object oo = getValue(Action.MNEMONIC_KEY);
		if (oo instanceof Integer)
		{
			return (char)((Integer)oo).intValue();
		}
		return null;
	}	// getMnemonic

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
	 * Dispose
	 */
	public void dispose()
	{
		m_delegate = null;

		// dispose generated components
		_button = null;
		_menuItem = null;
	}	// dispose

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("AppsAction[");
		sb.append(_actionCommand);

		final Object accelerator = getValue(Action.ACCELERATOR_KEY);
		if (accelerator != null)
		{
			sb.append(",Accelerator=").append(accelerator);
		}

		final Character mnemonic = getMnemonic();
		if (mnemonic != null)
		{
			sb.append(",MnemonicKey=").append(mnemonic);
		}

		sb.append("]");
		return sb.toString();
	}   // toString

	public void setAccelerator(final KeyStroke accelerator)
	{
		final KeyStroke acceleratorOld = _accelerator;
		_accelerator = accelerator;

		putValue(Action.ACCELERATOR_KEY, accelerator);      // KeyStroke

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
		return _actionCommand;
	}

	/**
	 * {@link AppsAction} builder
	 */
	public static final class Builder
	{
		private String action;
		private KeyStroke accelerator = null;
		private String toolTipText;
		private boolean toggleButton = false;
		private boolean smallSize = false;

		private String text = null;
		private boolean useTextFromAction = false;

		private Insets buttonInsets = null;
		private Boolean buttonDefaultCapable = null;

		/**
		 * See {@link #setRetrieveAppsActionMsg(boolean)}.
		 */
		private boolean retrieveAppsActionMessage = true;

		private Builder()
		{
		}

		/**
		 * Build the action.
		 *
		 * @return built action.
		 */
		public final AppsAction build()
		{
			return new AppsAction(this);
		}

		/**
		 * Build the {@link AppsAction} and return the action's button, casted to {@link CButton}.
		 *
		 * @return action's button casted to {@link CButton}.
		 * @see AppsAction#getCButton()
		 */
		public final CButton buildAndGetCButton()
		{
			return build().getCButton();
		}

		/**
		 * Sets the action name.
		 *
		 * This action name, will be also used for:
		 * <ul>
		 * <li>setting action's icon
		 * <li>setting button's text, if {@link #useTextFromActionName()} is enabled
		 * <li>setting menu item's text
		 * </ul>
		 *
		 * @param action base action command
		 */
		public Builder setAction(final String action)
		{
			this.action = action;
			return this;
		}

		private String getAction()
		{
			Check.assumeNotEmpty(action, "action not empty");
			return action;
		}

		/**
		 * Shall we invoke {@link IMsgBL} to attempt and get the action's displayed name from the <code>action</code> string?
		 * Default is <code>true</code>, because that used to be the hardcoded behavior.
		 *
		 * @param retrieveAppsActionMessage
		 * @return
		 */
		public Builder setRetrieveAppsActionMsg(final boolean retrieveAppsActionMessage)
		{
			this.retrieveAppsActionMessage = retrieveAppsActionMessage;
			return this;
		}

		private boolean isIsRetrieveAppsActionMessage()
		{
			return retrieveAppsActionMessage;
		}

		public Builder setAccelerator(final KeyStroke accelerator)
		{
			this.accelerator = accelerator;
			return this;
		}

		private KeyStroke getAccelerator()
		{
			return accelerator;
		}

		public Builder setText(final String text)
		{
			this.text = text;
			useTextFromAction = false;
			return this;
		}

		public Builder useTextFromActionName()
		{
			return useTextFromActionName(true);
		}

		public Builder useTextFromActionName(final boolean useTextFromAction)
		{
			this.useTextFromAction = useTextFromAction;
			return this;
		}

		private final boolean isUseTextFromActionName()
		{
			return useTextFromAction;
		}

		private String getText()
		{
			return text;
		}

		public Builder setToolTipText(final String toolTipText)
		{
			this.toolTipText = toolTipText;
			return this;
		}

		private String getToolTipText()
		{
			return toolTipText;
		}

		/**
		 * Advice the builder that we will build a toggle button.
		 */
		public Builder setToggleButton()
		{
			return setToggleButton(true);
		}

		/**
		 * @param toggleButton is toggle action (maintains state)
		 */
		public Builder setToggleButton(final boolean toggleButton)
		{
			this.toggleButton = toggleButton;
			return this;
		}

		private boolean isToggleButton()
		{
			return toggleButton;
		}

		/**
		 * Advice the builder to create a small size button.
		 */
		public Builder setSmallSize()
		{
			return setSmallSize(true);
		}

		/**
		 * Advice the builder if a small size button is needed or not.
		 *
		 * @param smallSize true if small size button shall be created
		 */
		public Builder setSmallSize(final boolean smallSize)
		{
			this.smallSize = smallSize;
			return this;
		}

		private boolean isSmallSize()
		{
			return smallSize;
		}

		/**
		 * Advice the builder to set given {@link Insets} to action's button.
		 *
		 * @param buttonInsets
		 * @see AbstractButton#setMargin(Insets)
		 */
		public Builder setButtonInsets(final Insets buttonInsets)
		{
			this.buttonInsets = buttonInsets;
			return this;
		}

		private final Insets getButtonInsets()
		{
			return buttonInsets;
		}

		/**
		 * Sets the <code>defaultCapable</code> property, which determines whether the button can be made the default button for its root pane.
		 *
		 * @param defaultCapable
		 * @return
		 * @see JButton#setDefaultCapable(boolean)
		 */
		public Builder setButtonDefaultCapable(final boolean defaultCapable)
		{
			buttonDefaultCapable = defaultCapable;
			return this;
		}

		private Boolean getButtonDefaultCapable()
		{
			return buttonDefaultCapable;
		}
	}
}
