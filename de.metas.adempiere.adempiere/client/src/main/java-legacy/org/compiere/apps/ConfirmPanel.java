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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;

/**
 * Confirm Panel component.
 *
 * To create a new {@link ConfirmPanel} instance, please use:
 * <ul>
 * <li> {@link #builder()} which will allow you to start building the confirm panel
 * <li>predefined convenient methods like {@link #newWithOK()}, {@link #newWithOKAndCancel()} etc
 * </ul>
 *
 * Also, using this class, you can create individual buttons, like
 * <ul>
 * <li> {@link #createOKButton(boolean)}, {@link #createOKButton(String)}
 * <li> {@link #createCancelButton(boolean)}, {@link #createCancelButton(String)}
 * <li>etc
 * </ul>
 *
 * @author Jorg Janke
 * @author metas-dev <dev@metasfresh.com>
 */
public final class ConfirmPanel extends CPanel
{
	private static final long serialVersionUID = 5617533492268854834L;

	/** @return {@link ConfirmPanel} instance with OK button only */
	public static final ConfirmPanel newWithOK()
	{
		return builder().build();
	}

	/** @return {@link ConfirmPanel} instance with OK and Cancel buttons */
	public static final ConfirmPanel newWithOKAndCancel()
	{
		return builder()
				.withCancelButton(true)
				.build();
	}

	/** @return {@link ConfirmPanel} builder */
	public static final Builder builder()
	{
		return new Builder();
	}

	/** @return new {@link AppsAction} builder */
	private static final AppsAction.Builder newButtonBuilder(final String action)
	{
		return AppsAction.builder()
				.setButtonInsets(s_insets)
				.setAction(action);
	}

	public static final AppsAction.Builder buildOKButton()
	{
		return newButtonBuilder(A_OK)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
				.setButtonDefaultCapable(true);
	}

	/**
	 * Create OK Button with label text and F4 Shortcut
	 *
	 * @param text text
	 * @return OK Button
	 */
	public static final CButton createOKButton(final String text)
	{
		return buildOKButton()
				.setText(text)
				.buildAndGetCButton();
	}

	/**
	 * Create OK Button with Standard text
	 *
	 * @param withText with text
	 * @return OK Button
	 */
	public static final CButton createOKButton(final boolean withText)
	{
		return buildOKButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	} // createOKButton

	public static final AppsAction.Builder buildCancelButton()
	{
		return newButtonBuilder(A_CANCEL)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
	}

	/**
	 * Create Cancel Button with label text and register ESC as KeyStroke
	 *
	 * @param text text
	 * @return Cancel Button
	 */
	public static final CButton createCancelButton(final String text)
	{
		return buildCancelButton()
				.setText(text)
				.buildAndGetCButton();
	}

	/**
	 * Create Cancel Button with Standard text
	 *
	 * @param withText with text
	 * @return Button
	 */
	public static final CButton createCancelButton(final boolean withText)
	{
		return buildCancelButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildRefreshButton()
	{
		return newButtonBuilder(A_REFRESH)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
	}

	/**
	 * Create Refresh Button with label text and F5
	 *
	 * @param text text
	 * @return button
	 */
	public static final CButton createRefreshButton(final String text)
	{
		return buildRefreshButton()
				.setText(text)
				.buildAndGetCButton();
	}

	/**
	 * Create Refresh Button with Standard text
	 *
	 * @param withText with text
	 * @return Button
	 */
	public static final CButton createRefreshButton(final boolean withText)
	{
		return buildRefreshButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildResetButton()
	{
		return newButtonBuilder(A_RESET);
	}

	public static final AppsAction.Builder buildCustomizeButton()
	{
		return newButtonBuilder(A_CUSTOMIZE);
	}

	public static final AppsAction.Builder buildHistoryButton()
	{
		return newButtonBuilder(A_HISTORY)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
	}

	public static final AppsAction.Builder buildZoomButton()
	{
		return newButtonBuilder(A_ZOOM);
	}

	/************************
	 * Create Zoom Button with label text
	 *
	 * @param text text
	 * @return button
	 */
	public static final CButton createZoomButton(final String text)
	{
		return buildZoomButton()
				.setText(text)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildProcessButton()
	{
		return newButtonBuilder(A_PROCESS)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK));
	}

	/************************
	 * Create Process Button with label text Shift-F4
	 *
	 * @param text text
	 * @return button
	 */
	public static final CButton createProcessButton(final String text)
	{
		return buildProcessButton()
				.setText(text)
				.buildAndGetCButton();
	}

	/**
	 * Create Process Button with Standard text
	 *
	 * @param withText with text
	 * @return Button
	 */
	public static final CButton createProcessButton(final boolean withText)
	{
		return buildProcessButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildPrintButton()
	{
		return newButtonBuilder(A_PRINT)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
	}

	/**
	 * Create Print Button with Standard text
	 *
	 * @param withText with text
	 * @return Button
	 */
	public static final CButton createPrintButton(final boolean withText)
	{
		return buildPrintButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildHelpButton()
	{
		return newButtonBuilder(A_HELP)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
	}

	/**
	 * Create Help Button with Standard text
	 *
	 * @param withText with text
	 * @return Button
	 */
	public static final CButton createHelpButton(final boolean withText)
	{
		return buildHelpButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	} // createHelpButton

	public static final AppsAction.Builder buildExportButton()
	{
		return newButtonBuilder(A_EXPORT);
	}

	/************************
	 * Create Export Button wlth label text
	 *
	 * @param text text
	 * @return Button
	 */
	public static final CButton createExportButton(final String text)
	{
		return buildExportButton()
				.setText(text)
				.buildAndGetCButton();
	}

	/**
	 * Create Export Button with Standard text
	 *
	 * @param withText with text
	 * @return Button
	 */
	public static final CButton createExportButton(final boolean withText)
	{
		return buildExportButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildDeleteButton()
	{
		return newButtonBuilder(A_DELETE)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
	}

	/************************
	 * Create Delete Button with label text - F3
	 *
	 * @param text text
	 * @return Delete Button
	 */
	public static final CButton createDeleteButton(final String text)
	{
		return buildDeleteButton()
				.setText(text)
				.buildAndGetCButton();
	}

	/**
	 * Create Delete Button with Standard text
	 *
	 * @param withText with text
	 * @return Delete Button
	 */
	public static final CButton createDeleteButton(final boolean withText)
	{
		return buildDeleteButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	} // createDeleteButton

	public static final AppsAction.Builder buildPAttributeButton()
	{
		return newButtonBuilder(A_PATTRIBUTE);
	}

	/************************
	 * Create Product Attribute Button with Standard text
	 *
	 * @param withText with text
	 * @return Product Attribute Button
	 */
	public static final CButton createPAttributeButton(final boolean withText)
	{
		return buildPAttributeButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	}

	/**
	 * Create Product Attribute Button with label text
	 *
	 * @param text text
	 * @return Product Attribute Button
	 */
	public static final CButton createPAttributeButton(final String text)
	{
		return buildPAttributeButton()
				.setText(text)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildNewButton()
	{
		return newButtonBuilder(A_NEW)
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
	}

	/**
	 * Create New Button with label text - F2
	 *
	 * @param text text
	 * @return Product Attribute Button
	 */
	public static final CButton createNewButton(final String text)
	{
		return buildNewButton()
				.setText(text)
				.buildAndGetCButton();
	}

	public static final AppsAction.Builder buildBackButton()
	{
		return newButtonBuilder(A_Back);
	}

	public static final CButton createBackButton(final boolean withText)
	{
		return buildBackButton()
				.useTextFromActionName(withText)
				.buildAndGetCButton();
	} // createOKButton

	/*************************************************************************/

	/** Action String OK */
	public static final String A_OK = "Ok";
	/** Action String Cancel */
	public static final String A_CANCEL = "Cancel";
	/** Action String Refresh */
	public static final String A_REFRESH = "Refresh";
	/** Action String Reset */
	public static final String A_RESET = "Reset";
	/** Action String Customize */
	public static final String A_CUSTOMIZE = "Customize";
	/** Action String History */
	public static final String A_HISTORY = "History";
	/** Action String Zoom */
	public static final String A_ZOOM = "Zoom";

	/** Action String Process */
	public static final String A_PROCESS = "Process";
	/** Action String Print */
	public static final String A_PRINT = "Print";
	/** Action String Export */
	public static final String A_EXPORT = "Export";
	/** Action String Help */
	public static final String A_HELP = "Help";
	/** Action String Delete */
	public static final String A_DELETE = "Delete";
	/** Action String PAttribute */
	public static final String A_PATTRIBUTE = "PAttribute";
	/** Action String New */
	public static final String A_NEW = "New";
	/** Action: Back */
	public static final String A_Back = "Back";

	/** Standard Insets used */
	public static Insets s_insets = new Insets(0, 10, 0, 10);

	/**************************************************************************
	 * Create Confirmation Panel with OK Button
	 *
	 * @deprecated Please use {@link #builder()}
	 */
	@Deprecated
	public ConfirmPanel()
	{
		this(new Builder());
	}

	/**
	 * Create Confirmation Panel with OK and Cancel Button
	 *
	 * @param withCancelButton with cancel
	 *
	 * @deprecated Please use {@link #builder()}
	 */
	@Deprecated
	public ConfirmPanel(final boolean withCancelButton)
	{
		this(new Builder()
				.withCancelButton(withCancelButton));
	}

	/**
	 * Create Confirmation Panel with different buttons
	 *
	 * @param withCancelButton with cancel
	 * @param withRefreshButton with refresh
	 * @param withResetButton with reset
	 * @param withCustomizeButton with customize
	 * @param withHistoryButton with history
	 * @param withZoomButton with zoom
	 * @param withText with text
	 * 
	 * @deprecated Please use {@link #builder()}
	 */
	@Deprecated
	public ConfirmPanel(final boolean withCancelButton,
			final boolean withRefreshButton,
			final boolean withResetButton,
			final boolean withCustomizeButton,
			final boolean withHistoryButton,
			final boolean withZoomButton,
			final boolean withText)
	{
		this(new Builder()
				.withCancelButton(withCancelButton)
				.withRefreshButton(withRefreshButton)
				.withResetButton(withResetButton)
				.withCustomizeButton(withCustomizeButton)
				.withHistoryButton(withHistoryButton)
				.withZoomButton(withZoomButton)
				.withText(withText)
				.withSmallButtons(false));

	} // ConfirmPanel

	private ConfirmPanel(final Builder builder)
	{
		super();

		final boolean withText = builder.withText;
		final boolean small = builder.smallButtons;

		final BorderLayout mainLayout = new BorderLayout();
		setLayout(mainLayout);
		setName("confirmPanel");

		//
		// Buttons panel
		final CPanel okCancelPanel = new CPanel(new FlowLayout(FlowLayout.RIGHT));
		okCancelPanel.setOpaque(false);
		this.add(okCancelPanel, BorderLayout.EAST);

		// Cancel button
		{
			bCancel = buildCancelButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			okCancelPanel.add(bCancel);
			setCancelVisible(builder.withCancelButton);
		}

		// OK button
		{
			bOK = buildOKButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			okCancelPanel.add(bOK);
		}

		//
		if (builder.withNewButton)
		{
			bNew = buildNewButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			addComponent(bNew);
		}
		if (builder.withRefreshButton)
		{
			bRefresh = buildRefreshButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			addComponent(bRefresh);
		}
		if (builder.withResetButton)
		{
			bReset = buildResetButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			addComponent(bReset);
		}
		if (builder.withCustomizeButton)
		{
			bCustomize = buildCustomizeButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			addComponent(bCustomize);
		}
		if (builder.withHistoryButton)
		{
			bHistory = buildHistoryButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			addComponent(bHistory);
		}
		if (builder.withZoomButton)
		{
			bZoom = buildZoomButton()
					.useTextFromActionName(withText)
					.setSmallSize(small)
					.buildAndGetCButton();
			addComponent(bZoom);
		}
	}	// ConfirmPanel

	/**
	 * Additional Component Panel, displayed on the left side of this confirm panel.
	 */
	private CPanel additionalComponentsPanel = null;

	private final CButton bOK;
	private final CButton bCancel;
	//
	private CButton bNew;
	private CButton bRefresh;
	private CButton bReset;
	private CButton bCustomize;
	private CButton bHistory;
	private CButton bZoom;

	/**
	 * Add Component to left side of confirmPanel
	 * 
	 * @param component button
	 */
	public void addComponent(final Component component)
	{
		if (additionalComponentsPanel == null)
		{
			additionalComponentsPanel = new CPanel(new FlowLayout(FlowLayout.LEFT));
			this.add(additionalComponentsPanel, BorderLayout.WEST);
		}
		additionalComponentsPanel.add(component);
	}   // addButton

	/**
	 * Add Button to left side of confirmPanel
	 * 
	 * @param action action command
	 * @param toolTipText tool tip text
	 * @param icon icon
	 * @return button
	 */
	public AbstractButton addButton(final String action, final String toolTipText, final Icon icon, final boolean toggle)
	{
		final AppsAction aa = AppsAction.builder()
				.setAction(action)
				.setToolTipText(toolTipText)
				.setToggleButton(toggle)
				.build();
		final AbstractButton button = aa.getButton();
		if (icon != null)
		{
			button.setIcon(icon);
		}
		addComponent(button);
		return button;
	}

	/**
	 * Add Button to left side of confirmPanel
	 * 
	 * @param button button
	 * @return JButton
	 */
	public JButton addButton(final JButton button)
	{
		addComponent(button);
		return button;
	}   // addButton

	/**
	 * Add Component to left side of confirmPanel
	 *
	 * @param text
	 * @return CLabel
	 */
	public CLabel addLabel(final String text, final Icon icon, final int horizontalAlignment)
	{
		final CLabel label = new CLabel(text, icon, horizontalAlignment);
		addComponent(label);
		return label;
	}

	/**
	 * Add Component to left side of confirmPanel
	 *
	 * @param text
	 * @return CLabel
	 */
	public CLabel addLabel(final String text, final String toolTip)
	{
		final CLabel label = new CLabel(text, toolTip);
		addComponent(label);
		return label;
	}

	/**
	 * Get OK Button
	 * 
	 * @return OK Button
	 */
	public CButton getOKButton()
	{
		return bOK;
	}	// getOKButton

	/**
	 * Get Cancel Button
	 * 
	 * @return Cancel Button
	 */
	public CButton getCancelButton()
	{
		return bCancel;
	}	// getCancelButton

	/**
	 * Show OK button
	 * 
	 * @param value true for visible
	 */
	public void setOKVisible(final boolean value)
	{
		bOK.setVisible(value);
		bOK.setEnabled(value);
	}	// setOKVisible

	/**
	 * Is OK Visible
	 * 
	 * @return true of OK visisble
	 */
	public boolean isOKVisible()
	{
		return bOK.isVisible();
	}	// isOKVisible

	/**
	 * Show Cancel button
	 * 
	 * @param value trie for visible
	 */
	public void setCancelVisible(final boolean value)
	{
		bCancel.setVisible(value);
		bCancel.setEnabled(value);
	}	// setCancelVisible

	/**
	 * Is Cancel Visible
	 * 
	 * @return true if Canvel is visible
	 */
	public boolean isCancelVisible()
	{
		return bCancel.isVisible();
	}	// isCancelVisible

	/**
	 * Get Reset Button
	 * 
	 * @return Button
	 */
	public CButton getResetButton()
	{
		return bReset;
	}	// getResetButton

	/**
	 * Get Customize Button
	 * 
	 * @return Button
	 */
	public CButton getCustomizeButton()
	{
		return bCustomize;
	}	// getCustomizeButton

	/**
	 * Get History Button
	 * 
	 * @return Button
	 */
	public CButton getHistoryButton()
	{
		return bHistory;
	}	// getHistoryButton

	/**
	 * Get Zoom Button
	 * 
	 * @return Button
	 */
	public CButton getZoomButton()
	{
		return bZoom;
	}	// getZoomyButton

	/**
	 * Get Refresh Button
	 * 
	 * @return Button
	 */
	public CButton getRefreshButton()
	{
		return bRefresh;
	}	// getRefreshButton

	public CButton getNewButton()
	{
		return bNew;
	}

	/**
	 * Add Action Listener <code>
	 *  if (e.getActionCommand().equals(ConfirmPanel.A_OK))
	 *  </code>
	 * 
	 * @param al listener
	 * @deprecated: method name is misleading: the current implementation supports only one action listener. use {@link #setActionListener(ActionListener)} instead.
	 */
	@Deprecated
	public void addActionListener(final ActionListener al)
	{
		setActionListener(al);
	}

	/**
	 * Set the given listener to be notified in any of the buttons are pressed.
	 *
	 * @param l listener
	 */
	public void setConfirmPanelListener(final ConfirmPanelListener l)
	{
		setActionListener(l);
	}

	/**
	 * Set the given action listener for this panel's buttons.
	 *
	 * @param al listener
	 */
	public void setActionListener(final ActionListener al)
	{
		if (bOK != null)
		{
			((AppsAction)bOK.getAction()).setDelegate(al);
		}
		if (bCancel != null)
		{
			((AppsAction)bCancel.getAction()).setDelegate(al);
		}
		//
		if (bNew != null)
		{
			((AppsAction)bNew.getAction()).setDelegate(al);
		}
		if (bRefresh != null)
		{
			((AppsAction)bRefresh.getAction()).setDelegate(al);
		}
		if (bReset != null)
		{
			((AppsAction)bReset.getAction()).setDelegate(al);
		}
		if (bCustomize != null)
		{
			((AppsAction)bCustomize.getAction()).setDelegate(al);
		}
		if (bHistory != null)
		{
			((AppsAction)bHistory.getAction()).setDelegate(al);
		}
		if (bZoom != null)
		{
			((AppsAction)bZoom.getAction()).setDelegate(al);
		}

		//
		// Set OK as default Button
		JRootPane rootpane = null;
		if (al instanceof JDialog)
		{
			rootpane = ((JDialog)al).getRootPane();
		}
		else if (al instanceof JFrame)
		{
			rootpane = ((JFrame)al).getRootPane();
		}
		if (rootpane != null)
		{
			rootpane.setDefaultButton(bOK);
		}
	}	// addActionListener

	/**
	 * Enable all components
	 * 
	 * @param enabled trie if enabled
	 */
	@Override
	public void setEnabled(final boolean enabled)
	{
		super.setEnabled(enabled);
		bOK.setEnabled(enabled);
		bCancel.setEnabled(enabled);
		//
		if (bNew != null)
		{
			bNew.setEnabled(enabled);
		}
		if (bRefresh != null)
		{
			bRefresh.setEnabled(enabled);
		}
		if (bCustomize != null)
		{
			bCustomize.setEnabled(enabled);
		}
		if (bHistory != null)
		{
			bHistory.setEnabled(enabled);
		}
		if (bZoom != null)
		{
			bZoom.setEnabled(enabled);
		}
	}   // setEnabled

	public void dispose()
	{
		setActionListener(null);

		if (additionalComponentsPanel != null)
		{
			additionalComponentsPanel.removeAll();
			additionalComponentsPanel = null;
		}

		removeAll();
	}

	/**
	 * {@link ConfirmPanel} builder.
	 *
	 * NOTE: by default, ALL flags are false, so you will get no buttons and no text on them.
	 */
	public static final class Builder
	{
		private boolean withText = false; // default=false for backward compatibility
		private boolean smallButtons = false; // default=false for backward compatibility
		//
		private boolean withCancelButton = false;
		private boolean withNewButton = false;
		private boolean withRefreshButton = false;
		private boolean withResetButton = false;
		private boolean withCustomizeButton = false;
		private boolean withHistoryButton = false;
		private boolean withZoomButton = false;

		private Builder()
		{
			super();
		}

		public ConfirmPanel build()
		{
			return new ConfirmPanel(this);
		}

		public Builder withCancelButton(final boolean withCancelButton)
		{
			this.withCancelButton = withCancelButton;
			return this;
		}

		public Builder withNewButton(final boolean withNewButton)
		{
			this.withNewButton = withNewButton;
			return this;
		}

		public Builder withRefreshButton()
		{
			return withRefreshButton(true);
		}

		public Builder withRefreshButton(final boolean withRefreshButton)
		{
			this.withRefreshButton = withRefreshButton;
			return this;
		}

		public Builder withResetButton(final boolean withResetButton)
		{
			this.withResetButton = withResetButton;
			return this;
		}

		public Builder withCustomizeButton(final boolean withCustomizeButton)
		{
			this.withCustomizeButton = withCustomizeButton;
			return this;
		}

		public Builder withHistoryButton(final boolean withHistoryButton)
		{
			this.withHistoryButton = withHistoryButton;
			return this;
		}

		public Builder withZoomButton(final boolean withZoomButton)
		{
			this.withZoomButton = withZoomButton;
			return this;
		}

		/**
		 * Advice builder to create the buttons with text on them.
		 */
		public Builder withText()
		{
			return withText(true);
		}

		/**
		 * Advice builder to create the buttons without any text on them.
		 * 
		 * NOTE: this is the default option anyway. You can call it just to explicitly state the obvious.
		 */
		public Builder withoutText()
		{
			return withText(false);
		}

		/**
		 * Advice builder to create the buttons with or without text on them.
		 *
		 * @param withText true if buttons shall have text on them
		 */
		public Builder withText(final boolean withText)
		{
			this.withText = withText;
			return this;
		}

		public Builder withSmallButtons(final boolean withSmallButtons)
		{
			smallButtons = withSmallButtons;
			return this;
		}
	}
}	// ConfirmPanel
