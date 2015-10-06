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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;

/**
 * Application Confirm Panel. <code>
 *  if (e.getActionCommand().equals(ConfirmPanel.A_OK))
 *  </code>
 * 
 * @author Jorg Janke
 * @version $Id: ConfirmPanel.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ConfirmPanel extends CPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6041019802043360966L;
	
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Properties getCtx()
	{
		return Env.getCtx();
	}

	/**
	 * Create OK Button with label text and F4 Shortcut
	 * 
	 * @param text
	 *            text
	 * @return OK Button
	 */
	public static final CButton createOKButton(String text)
	{
		final boolean small = false;
		return createOKButton(text, small);
	} // createOKButton

	public static final CButton createOKButton(final String text, final boolean small)
	{
		final KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		final AppsAction aa = new AppsAction(small, A_OK, keyStroke, text);
		
		// Customize button
		final CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		button.setDefaultCapable(true);
		
		return button;
	}

	/**
	 * Create OK Button with Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return OK Button
	 */
	public static final CButton createOKButton(boolean withText)
	{
		final boolean small = false;
		final CButton button = createOKButton(withText, small);
		return button;
	} // createOKButton

	private static final CButton createOKButton(boolean withText, boolean small)
	{
		final String text;
		if (withText)
		{
			text = Services.get(IMsgBL.class).getMsg(getCtx(), A_OK);
		}
		else
		{
			text = "";
		}
		
		return createOKButton(text, small);
	}

	/**
	 * Create Cancel Button wlth label text and register ESC as KeyStroke
	 * 
	 * @param text
	 *            text
	 * @return Cancel Button
	 */
	public static final CButton createCancelButton(String text)
	{
		final boolean small = false;
		return createCancelButton(text, small);
	} // createCancelButton

	public static final CButton createCancelButton(final String text, final boolean small)
	{
		final KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		final AppsAction aa = new AppsAction(small, A_CANCEL, keyStroke, text);
		final CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createCancelButton

	/**
	 * Create Cancel Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createCancelButton(boolean withText)
	{
		final boolean small = false;
		return createCancelButton(withText, small);
	} // createCancelButton

	public static final CButton createCancelButton(final boolean withText, final boolean small)
	{
		final String text;
		if (withText)
		{
			text = Services.get(IMsgBL.class).getMsg(getCtx(), A_CANCEL);
		}
		else
		{
			text = "";
		}
		return createCancelButton(text, small);
	} // createCancelButton

	/************************
	 * Create Refresh Button wlth label text and F5
	 * 
	 * @param text
	 *            text
	 * @return button
	 */
	public static final CButton createRefreshButton(String text)
	{
		AppsAction aa = new AppsAction(A_REFRESH, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createRefreshButton

	public static final CButton createRefreshButton(String text, boolean small) {
		AppsAction aa = new AppsAction(small, A_REFRESH, KeyStroke
				.getKeyStroke(KeyEvent.VK_F5, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	}

	/**
	 * Create Refresh Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createRefreshButton(boolean withText) {
		if (withText)
			return createRefreshButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_REFRESH));
		return createRefreshButton("");
	} // createRefreshButton

	public static final CButton createRefreshButton(boolean withText,
			boolean small) {
		if (withText)
			return createRefreshButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_REFRESH),
					small);
		return createRefreshButton("", small);
	} // createRefreshButton

	/************************
	 * Create Reset Button wlth label text
	 * 
	 * @param text
	 *            text
	 * @return button
	 */
	public static final CButton createResetButton(String text) {
		AppsAction aa = new AppsAction(A_RESET, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createResetButton

	public static final CButton createResetButton(String text, boolean small) {
		AppsAction aa = new AppsAction(small, A_RESET, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createResetButton

	/**
	 * Create Reset Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createResetButton(boolean withText) {
		if (withText)
			return createResetButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_RESET));
		return createResetButton(null);
	} // createRefreshButton

	public static final CButton createResetButton(boolean withText,
			boolean small) {
		if (withText)
			return createResetButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_RESET), small);
		return createResetButton(null, small);
	}

	/************************
	 * Create Customize Button wlth label text
	 * 
	 * @param text
	 *            text
	 * @return button
	 */
	public static final CButton createCustomizeButton(String text) {
		AppsAction aa = new AppsAction(A_CUSTOMIZE, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createCustomizeButton

	public static final CButton createCustomizeButton(String text, boolean small) {
		AppsAction aa = new AppsAction(small, A_CUSTOMIZE, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	}

	/**
	 * Create Customize Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createCustomizeButton(boolean withText) {
		if (withText)
			return createCustomizeButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_CUSTOMIZE));
		return createCustomizeButton(null);
	} // createCustomizeButton

	public static final CButton createCustomizeButton(boolean withText,
			boolean small) {
		if (withText)
			return createCustomizeButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_CUSTOMIZE),
					small);
		return createCustomizeButton(null, small);
	}

	/************************
	 * Create History Button wlth label text
	 * 
	 * @param text
	 *            text
	 * @return button
	 */
	public static final CButton createHistoryButton(String text) {
		AppsAction aa = new AppsAction(A_HISTORY, KeyStroke.getKeyStroke(
				KeyEvent.VK_F9, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createHistoryButton

	public static final CButton createHistoryButton(String text, boolean small) {
		AppsAction aa = new AppsAction(small, A_HISTORY, KeyStroke
				.getKeyStroke(KeyEvent.VK_F9, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	}

	/**
	 * Create History Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createHistoryButton(boolean withText) {
		if (withText)
			return createHistoryButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_HISTORY));
		return createHistoryButton(null);
	} // createHistoryButton

	public static final CButton createHistoryButton(boolean withText,
			boolean small) {
		if (withText)
			return createHistoryButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_HISTORY),
					small);
		return createHistoryButton(null, small);
	}

	/************************
	 * Create Zoom Button wlth label text
	 * 
	 * @param text
	 *            text
	 * @return button
	 */
	public static final CButton createZoomButton(String text) {
		AppsAction aa = new AppsAction(A_ZOOM, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createZoomButton

	public static final CButton createZoomButton(String text, boolean small) {
		AppsAction aa = new AppsAction(small, A_ZOOM, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createZoomButton

	/**
	 * Create Zoom Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createZoomButton(boolean withText) {
		if (withText)
			return createZoomButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_ZOOM));
		return createZoomButton(null);
	} // createZoomButton

	public static final CButton createZoomButton(boolean withText, boolean small) {
		if (withText)
			return createZoomButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_ZOOM), small);
		return createZoomButton(null, small);
	}

	/************************
	 * Create Process Button wlth label text Shift-F4
	 * 
	 * @param text
	 *            text
	 * @return button
	 */
	public static final CButton createProcessButton(String text)
	{
		final KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_MASK);
		AppsAction aa = new AppsAction(A_PROCESS, keyStroke, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createProcessButton

	/**
	 * Create Process Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createProcessButton(boolean withText) {
		if (withText)
			return createProcessButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_PROCESS));
		return createProcessButton(null);
	} // createProcessButton

	/************************
	 * Create Print Button wlth label text
	 * 
	 * @param text
	 *            text
	 * @return button
	 */
	public static final CButton createPrintButton(String text) {
		AppsAction aa = new AppsAction(A_PRINT, KeyStroke.getKeyStroke(
				KeyEvent.VK_F12, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createPrintButton

	/**
	 * Create Print Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createPrintButton(boolean withText) {
		if (withText)
			return createPrintButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_PRINT));
		return createPrintButton(null);
	} // createPrintButton

	/************************
	 * Create Help Button wlth label text
	 * 
	 * @param text
	 *            text
	 * @return Button
	 */
	public static final CButton createHelpButton(String text) {
		AppsAction aa = new AppsAction(A_HELP, KeyStroke.getKeyStroke(
				KeyEvent.VK_F1, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createHelpButton

	/**
	 * Create Help Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createHelpButton(boolean withText) {
		if (withText)
			return createHelpButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_HELP));
		return createHelpButton(null);
	} // createHelpButton

	/************************
	 * Create Export Button wlth label text
	 * 
	 * @param text
	 *            text
	 * @return Button
	 */
	public static final CButton createExportButton(String text) {
		AppsAction aa = new AppsAction(A_EXPORT, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createExportButton

	/**
	 * Create Export Button wlth Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Button
	 */
	public static final CButton createExportButton(boolean withText) {
		if (withText)
			return createExportButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_EXPORT));
		return createExportButton(null);
	} // createExportButton

	/************************
	 * Create Delete Button with label text - F3
	 * 
	 * @param text
	 *            text
	 * @return Delete Button
	 */
	public static final CButton createDeleteButton(String text) {
		AppsAction aa = new AppsAction(A_DELETE, KeyStroke.getKeyStroke(
				KeyEvent.VK_F3, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createDeleteButton

	/**
	 * Create Delete Button with Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Delete Button
	 */
	public static final CButton createDeleteButton(boolean withText) {
		if (withText)
			return createDeleteButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_DELETE));
		return createDeleteButton(null);
	} // createDeleteButton

	/************************
	 * Create Product Attribute Button with Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return Product Attribute Button
	 */
	public static final CButton createPAttributeButton(boolean withText) {
		if (withText)
			return createPAttributeButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_PATTRIBUTE));
		return createPAttributeButton(null);
	} // createPAttributeButton

	/**
	 * Create Product Attribute Button with label text
	 * 
	 * @param text
	 *            text
	 * @return Product Attribute Button
	 */
	public static final CButton createPAttributeButton(String text) {
		AppsAction aa = new AppsAction(A_PATTRIBUTE, null, text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createPAttributeButton

	/************************
	 * Create New Button with Standard text
	 * 
	 * @param withText
	 *            with text
	 * @return New Button
	 */
	public static final CButton createNewButton(boolean withText) {
		if (withText)
			return createNewButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_NEW));
		return createNewButton(null);
	} // createNewButton

	public static final CButton createNewButton(boolean withText, boolean small) {
		if (withText)
			return createNewButton(Services.get(IMsgBL.class).getMsg(getCtx(), A_NEW), small);
		return createNewButton(null, small);
	} // createNewButton

	/**
	 * Create New Button with label text - F2
	 * 
	 * @param text
	 *            text
	 * @return Product Attribute Button
	 */
	public static final CButton createNewButton(String text) {
		AppsAction aa = new AppsAction(A_NEW, KeyStroke.getKeyStroke(
				KeyEvent.VK_F2, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createNewButton

	public static final CButton createNewButton(String text, boolean small) {
		AppsAction aa = new AppsAction(small, A_NEW, KeyStroke.getKeyStroke(
				KeyEvent.VK_F2, 0), text);
		CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	} // createNewButton
	
	
	public static final CButton createBackButton(boolean withText)
	{
		final boolean small = false;
		final CButton button = createBackButton(withText, small);
		return button;
	} // createOKButton

	private static final CButton createBackButton(boolean withText, boolean small)
	{
		final String text;
		if (withText)
		{
			text = Services.get(IMsgBL.class).getMsg(getCtx(), A_Back);
		}
		else
		{
			text = "";
		}
		
		return createBackButton(text, small);
	}

	private static final CButton createBackButton(final String text, final boolean small)
	{
		final KeyStroke keyStroke = null;
		final AppsAction aa = new AppsAction(small, A_Back, keyStroke, text);
		final CButton button = (CButton) aa.getButton();
		button.setMargin(s_insets);
		return button;
	}

	/*************************************************************************/

	/**	Action String OK        */
	public static final String A_OK = "Ok";
	/**	Action String Cancel    */
	public static final String A_CANCEL = "Cancel";
	/**	Action String Refresh   */
	public static final String A_REFRESH = "Refresh";
	/**	Action String Reset   	*/
	public static final String A_RESET = "Reset";
	/**	Action String Customize */
	public static final String A_CUSTOMIZE = "Customize";
	/**	Action String History   */
	public static final String A_HISTORY = "History";
	/**	Action String Zoom      */
	public static final String A_ZOOM = "Zoom";

	/** Action String Process   */
	public static final String A_PROCESS = "Process";
	/** Action String Print     */
	public static final String A_PRINT = "Print";
	/** Action String Export    */
	public static final String A_EXPORT = "Export";
	/** Action String Help      */
	public static final String A_HELP = "Help";
	/**	Action String Delete    */
	public static final String A_DELETE = "Delete";
	/**	Action String PAttribute	*/
	public static final String A_PATTRIBUTE = "PAttribute";
	/**	Action String New	*/
	public static final String A_NEW = "New";
	/** Action: Back */
	public static final String A_Back = "Back";

	/**	Standard Insets used    */
	public static Insets 	s_insets = new Insets (0, 10, 0, 10);
	
	
	/**************************************************************************
	 * Create Confirmation Panel with OK Button
	 * 
	 * @deprecated Please use {@link #builder()}
	 */
	@Deprecated
	public ConfirmPanel()
	{
		this(new Builder()
				.withText(true));
		//this(false, false, false, false, false, false, true);
	} // ConfirmPanel

	/**
	 * Create Confirmation Panel with OK and Cancel Button
	 * 
	 * @param withCancelButton
	 *            with cancel
	 * 
	 * @deprecated Please use {@link #builder()}
	 */
	@Deprecated
	public ConfirmPanel(boolean withCancelButton)
	{
		this(new Builder()
				.withCancelButton(withCancelButton)
				.withText(true));
		// this(withCancelButton, false, false, false, false, false, true);
	} // ConfirmPanel

	/**
	 * Create Confirmation Panel with different buttons
	 * 
	 * @param withCancelButton
	 *            with cancel
	 * @param withRefreshButton
	 *            with refresh
	 * @param withResetButton
	 *            with reset
	 * @param withCustomizeButton
	 *            with customize
	 * @param withHistoryButton
	 *            with history
	 * @param withZoomButton
	 *            with zoom
	 * @param withText
	 *            with text
	 *            
	 * @deprecated Please use {@link #builder()}
	 */
	@Deprecated
	public ConfirmPanel(boolean withCancelButton, boolean withRefreshButton,
			boolean withResetButton, boolean withCustomizeButton,
			boolean withHistoryButton, boolean withZoomButton, boolean withText)
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
		this.setLayout(mainLayout);
		this.setName("confirmPanel");
		
		//
		// Buttons panel
		final CPanel okCancel = new CPanel(new FlowLayout(FlowLayout.RIGHT));
		okCancel.setOpaque(false);
		this.add(okCancel, BorderLayout.EAST);
		
		// Cancel button
		{
			bCancel = createCancelButton(withText, small);
			okCancel.add(bCancel);
			setCancelVisible(builder.withCancelButton);
		}
		
		// OK button
		{
			bOK = createOKButton(withText, small);
			okCancel.add(bOK);
		}
		
		//
		if (builder.withNewButton)
		{
			bNew = createNewButton(withText, small);
			addComponent(bNew);
		}
		if (builder.withRefreshButton)
		{
			bRefresh = createRefreshButton(withText, small);
			addComponent(bRefresh);
		}
		if (builder.withResetButton)
		{
			bReset = createResetButton(withText, small);
			addComponent(bReset);
		}
		if (builder.withCustomizeButton)
		{
			bCustomize = createCustomizeButton(withText, small);
			addComponent(bCustomize);
		}
		if (builder.withHistoryButton)
		{
			bHistory = createHistoryButton(withText, small);
			addComponent(bHistory);
		}
		if (builder.withZoomButton)
		{
			bZoom = createZoomButton(withText, small);
			addComponent(bZoom);
		}
	}	//	ConfirmPanel

	/**	Additional Component Panel			*/
	private CPanel  m_addlComponents = null;

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
	 *  Add Component to left side of confirmPanel
	 *  @param component button
	 */
	public void addComponent (Component component)
	{
		if (m_addlComponents == null)
		{
			m_addlComponents = new CPanel(new FlowLayout(FlowLayout.LEFT));
			this.add(m_addlComponents, BorderLayout.WEST);
		}
		m_addlComponents.add(component);
	}   //  addButton

	/**
	 *  Add Button to left side of confirmPanel
	 *  @param action action command
	 *  @param toolTipText tool tip text
	 *  @param icon icon
	 *  @return JButton
	 */
	public Component addButton(String action, String toolTipText, Icon icon, boolean toogle) {
		AppsAction aa = new AppsAction(action, null, toolTipText, toogle);
		Component b;
		if (toogle)
		{
			b = aa.getButton();
		}
		else
		{
			b = aa.getButton();
		}
		// new DialogButton (action, toolTipText, icon);
		addComponent(b);
		return b;
	}   //  addButton

	/**
	 *  Add Button to left side of confirmPanel
	 *  @param button button
	 *  @return JButton
	 */
	public JButton addButton (JButton button)
	{
		addComponent (button);
		return button;
	}   //  addButton

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
	 *	Get OK Button
	 *  @return OK Button
	 */
	public CButton getOKButton()
	{
		return bOK;
	}	//	getOKButton

	/**
	 *	Get Cancel Button
	 *  @return Cancel Button
	 */
	public CButton getCancelButton()
	{
		return bCancel;
	}	//	getCancelButton

	/**
	 *	Show OK button
	 *  @param value true for visible
	 */
	public void setOKVisible (boolean value)
	{
		bOK.setVisible(value);
		bOK.setEnabled(value);
	}	//	setOKVisible

	/**
	 *	Is OK Visible
	 *  @return true of OK visisble
	 */
	public boolean isOKVisible()
	{
		return bOK.isVisible();
	}	//	isOKVisible

	/**
	 *	Show Cancel button
	 *  @param value trie for visible
	 */
	public void setCancelVisible (boolean value)
	{
		bCancel.setVisible(value);
		bCancel.setEnabled(value);
	}	//	setCancelVisible

	/**
	 *	Is Cancel Visible
	 *  @return true if Canvel is visible
	 */
	public boolean isCancelVisible()
	{
		return bCancel.isVisible();
	}	//	isCancelVisible

	/**
	 *	Get Reset Button
	 *  @return Button
	 */
	public CButton getResetButton()
	{
		return bReset;
	}	//	getResetButton
	
	/**
	 *	Get Customize Button
	 *  @return Button
	 */
	public CButton getCustomizeButton()
	{
		return bCustomize;
	}	//	getCustomizeButton

	/**
	 *	Get History Button
	 *  @return Button
	 */
	public CButton getHistoryButton()
	{
		return bHistory;
	}	//	getHistoryButton
	
	/**
	 *	Get Zoom Button
	 *  @return Button
	 */
	public CButton getZoomButton()
	{
		return bZoom;
	}	//	getZoomyButton

	/**
	 *	Get Refresh Button
	 *  @return Button
	 */
	public CButton getRefreshButton()
	{
		return bRefresh;
	}	//	getRefreshButton
	
	public CButton getNewButton()
	{
		return bNew;
	}
	
	
	/**
	 * 	Add Action Listener
	 *  <code>
	 *  if (e.getActionCommand().equals(ConfirmPanel.A_OK))
	 *  </code>
	 *  
	 * @param al listener
	 * @deprecated: method name is misleading: the current implementation supports only one action listener. use {@link #setActionListener(ActionListener)} instead.
	 */
	@Deprecated
	public void addActionListener(ActionListener al)
	{
		setActionListener(al);
	}
		
	/**
	 * Set the given listener to be notified in any of the buttons are pressed.
	 * 
	 */
	public void setConfirmPanelListener(ConfirmPanelListener l)
	{
		setActionListener(l);
	}
	
	/**************************************************************************
	 *	Set the given action listener for this panel's buttons.
	 *
	 *  <code>
	 *  if (e.getActionCommand().equals(ConfirmPanel.A_OK))
	 *  </code>
	 *  @param al listener
	 */
	public void setActionListener(final ActionListener al)
	{
		((AppsAction)bOK.getAction()).setDelegate(al);
		((AppsAction)bCancel.getAction()).setDelegate(al);
		//
		if (bNew != null)
			((AppsAction)bNew.getAction()).setDelegate(al);
		if (bRefresh != null)
			((AppsAction)bRefresh.getAction()).setDelegate(al);
		if (bReset != null)
			((AppsAction)bReset.getAction()).setDelegate(al);
		if (bCustomize != null)
			((AppsAction)bCustomize.getAction()).setDelegate(al);
		if (bHistory != null)
			((AppsAction)bHistory.getAction()).setDelegate(al);
		if (bZoom != null)
			((AppsAction)bZoom.getAction()).setDelegate(al);
			
		//	Set OK as default Button
		JRootPane rootpane = null;
		if (al instanceof JDialog)
			rootpane = ((JDialog)al).getRootPane();
		else if (al instanceof JFrame)
			rootpane = ((JFrame)al).getRootPane();
		if (rootpane != null)
			rootpane.setDefaultButton(bOK);
	}	//	addActionListener

	/**
	 *  Enable all components
	 *  @param enabled trie if enabled
	 */
	@Override
	public void setEnabled (boolean enabled)
	{
		super.setEnabled(enabled);
		bOK.setEnabled(enabled);
		bCancel.setEnabled(enabled);
		//
		if (bNew != null)
			bNew.setEnabled(enabled);
		if (bRefresh != null)
			bRefresh.setEnabled(enabled);
		if (bCustomize != null)
			bCustomize.setEnabled(enabled);
		if (bHistory != null)
			bHistory.setEnabled(enabled);
		if (bZoom != null)
			bZoom.setEnabled(enabled);
	}   //  setEnabled
	
	public void dispose()
	{
		setActionListener(null);
		
		if (m_addlComponents != null)
		{
			m_addlComponents.removeAll();
			m_addlComponents = null;
		}
		
		removeAll();
	}


	/**
	 * Convenient {@link ActionListener} implementation to listen for the confirm panel's various buttons.
	 * 
	 * @author ts
	 * 
	 */
	public static abstract class ConfirmPanelListener implements ActionListener
	{
		@Override
		public final void actionPerformed(final ActionEvent e)
		{
			final String action = e.getActionCommand();
			if (action == null || action.length() == 0)
			{
				return;
			}
			else if (action.equals(ConfirmPanel.A_CANCEL))
			{
				cancelButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_OK))
			{
				okButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_REFRESH))
			{
				refreshButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_RESET))
			{
				resetButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_CUSTOMIZE))
			{
				customizeButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_HISTORY))
			{
				historyButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_ZOOM))
			{
				zoomButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_PROCESS))
			{
				processButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_PRINT))
			{
				printButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_EXPORT))
			{
				exportButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_HELP))
			{
				helpButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_DELETE))
			{
				deleteButtonPressed(e);
			}
			else if (action.equals(ConfirmPanel.A_NEW))
			{
				newButtonPressed(e);
			}
		}

		public void okButtonPressed(ActionEvent e)
		{}
		
		public void cancelButtonPressed(ActionEvent e)
		{}
		
		public void refreshButtonPressed(ActionEvent e)
		{}
		
		public void resetButtonPressed(ActionEvent e)
		{}
		
		public void customizeButtonPressed(ActionEvent e)
		{}
		
		public void historyButtonPressed(ActionEvent e)
		{}
		
		public void zoomButtonPressed(ActionEvent e)
		{}
		
		public void processButtonPressed(ActionEvent e)
		{}
		
		public void printButtonPressed(ActionEvent e)
		{}
		
		public void exportButtonPressed(ActionEvent e)
		{}
		
		public void helpButtonPressed(ActionEvent e)
		{}
		
		public void deleteButtonPressed(ActionEvent e)
		{}
		
		public void pAttributeButtonPressed(ActionEvent e)
		{}
		
		public void newButtonPressed(ActionEvent e)
		{}
	}
	
	public static final class Builder
	{
		private boolean withText = true; // default=true for backward compatibility 
		private boolean smallButtons = false; // default=false for backward compatibility
		//
		private boolean withCancelButton;
		private boolean withNewButton;
		private boolean withRefreshButton;
		private boolean withResetButton;
		private boolean withCustomizeButton;
		private boolean withHistoryButton;
		private boolean withZoomButton;
		
		private Builder()
		{
			super();
		}
		
		public ConfirmPanel build()
		{
			return new ConfirmPanel(this);
		}

		public Builder withCancelButton(boolean withCancelButton)
		{
			this.withCancelButton = withCancelButton;
			return this;
		}

		public Builder withNewButton(boolean withNewButton)
		{
			this.withNewButton = withNewButton;
			return this;
		}

		public Builder withRefreshButton(boolean withRefreshButton)
		{
			this.withRefreshButton = withRefreshButton;
			return this;
		}

		public Builder withResetButton(boolean withResetButton)
		{
			this.withResetButton = withResetButton;
			return this;
		}

		public Builder withCustomizeButton(boolean withCustomizeButton)
		{
			this.withCustomizeButton = withCustomizeButton;
			return this;
		}

		public Builder withHistoryButton(boolean withHistoryButton)
		{
			this.withHistoryButton = withHistoryButton;
			return this;
		}

		public Builder withZoomButton(boolean withZoomButton)
		{
			this.withZoomButton = withZoomButton;
			return this;
		}

		public Builder withText(boolean withText)
		{
			this.withText = withText;
			return this;
		}
		
		public Builder withSmallButtons(final boolean withSmallButtons)
		{
			this.smallButtons = withSmallButtons;
			return this;
		}
	}
}	//	ConfirmPanel
