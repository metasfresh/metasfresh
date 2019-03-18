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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.NullCopyPasteSupportEditor;
import org.compiere.apps.APanel;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.model.MAccountLookup;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.SwingUtils;
import org.slf4j.Logger;

import de.metas.acct.api.AcctSchemaId;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;

/**
 * Account Control - Displays ValidCombination and launches Dialog
 *
 * @author Jorg Janke
 * @version $Id: VAccount.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1830531 ] Process parameter with type Account not working
 */
public final class VAccount extends JComponent
		implements VEditor, ActionListener, FocusListener
		, ICopyPasteSupportEditorAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4177614835777620089L;

	/**
	 * Constructor
	 * 
	 * @param columnName
	 * @param mandatory
	 * @param isReadOnly
	 * @param isUpdateable
	 * @param mAccount
	 * @param title
	 */
	public VAccount(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
			MAccountLookup mAccount, String title)
	{
		super();
		super.setName(columnName);
		m_columnName = columnName;
		m_mAccount = mAccount;
		m_title = title;
		
		//
		LookAndFeel.installBorder(this, "TextField.border");
		this.setLayout(new BorderLayout());
		
		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.addActionListener(this);
		m_text.addFocusListener(this);
		this.add(m_text, BorderLayout.CENTER);

		//
		// Button
		m_button = VEditorUtils.createActionButton("Account", m_text);
		m_button.addActionListener(this);
		VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);

		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		// Editable
		if (isReadOnly || !isUpdateable)
			setReadWrite(false);
		else
			setReadWrite(true);
		setMandatory(mandatory);
		
		// Create and bind the context menu
		new EditorContextPopupMenu(this);
	}	// VAccount

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
		m_mAccount = null;
	}   // dispose

	private CTextField m_text = new CTextField(VLookup.DISPLAY_LENGTH);
	private VEditorActionButton m_button = null;
	private MAccountLookup m_mAccount;
	private Object m_value;
	private final String m_title;
	private int m_WindowNo;
	private boolean mandatory = false;
	private boolean readWrite = true;

	private final String m_columnName;
	/** Logger */
	private static Logger log = LogManager.getLogger(VAccount.class);

	/**
	 * Enable/disable
	 * 
	 * @param value
	 */
	@Override
	public void setReadWrite(boolean value)
	{
		this.readWrite = value;
		
		m_text.setReadWrite(value);
		m_button.setReadWrite(value);
		
		setBackground(false);
	}	// setReadWrite

	/**
	 * IsReadWrite
	 * 
	 * @return true if read write
	 */
	@Override
	public boolean isReadWrite()
	{
		return readWrite;
	}	// isReadWrite

	/**
	 * Set Mandatory (and back bolor)
	 * 
	 * @param mandatory
	 */
	@Override
	public void setMandatory(final boolean mandatory)
	{
		this.mandatory = mandatory;
		setBackground(false);
	}	// setMandatory

	/**
	 * Is it mandatory
	 * 
	 * @return mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return mandatory;
	}

	/**
	 * Set Background
	 * 
	 * @param color
	 */
	@Override
	public void setBackground(Color color)
	{
		// if (!color.equals(m_text.getBackground()))
		m_text.setBackground(color);
	}	// setBackground

	/**
	 * Set Background based on editable / mandatory / error
	 * 
	 * @param error if true, set background to error color, otherwise mandatory/editable
	 */
	@Override
	public void setBackground(boolean error)
	{
		if (error)
			setBackground(AdempierePLAF.getFieldBackground_Error());
		else if (!isReadWrite())
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		else if (isMandatory())
			setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		else
			setBackground(AdempierePLAF.getFieldBackground_Normal());
	}   // setBackground

	/**
	 * Set Foreground
	 * 
	 * @param fg
	 */
	@Override
	public void setForeground(Color fg)
	{
		m_text.setForeground(fg);
	}   // setForeground

	/**
	 * Set Editor to value and fires a vetoable change
	 * 
	 * @param value
	 */
	@Override
	public void setValue(Object value)
	{
		m_value = value;
		m_text.setText(m_mAccount.getDisplay(value));	// loads value
		m_text.setToolTipText(m_mAccount.getDescription());

		// Data Binding
		try
		{
			fireVetoableChange(m_columnName, null, value);
		}
		catch (PropertyVetoException pve)
		{
		}
	}	// setValue

	/**
	 * Request Focus
	 */
	@Override
	public void requestFocus()
	{
		m_text.requestFocus();
	}	// requestFocus

	/**
	 * Property Change Listener
	 * 
	 * @param evt
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
			setValue(evt.getNewValue());

		// metas: request focus (2009_0027_G131)
		if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
			requestFocus();
		// metas end

	}   // propertyChange

	/**
	 * Return Editor value
	 * 
	 * @return value
	 */
	@Override
	public Object getValue()
	{
		return m_mAccount.getC_ValidCombination_ID();
	}	// getValue

	/**
	 * Return Display Value
	 * 
	 * @return String representation
	 */
	@Override
	public String getDisplay()
	{
		return m_text.getText();
	}   // getDisplay

	/**
	 * ActionListener - Button - Start Dialog
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == m_text)
			cmd_text();
		else if (e.getSource() == m_button)
			cmd_button();
	}	// actionPerformed

	/**
	 * Button - Start Dialog
	 */
	public void cmd_button()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			final AcctSchemaId acctSchemaId = getAcctSchemaId();
			final VAccountDialog ad = new VAccountDialog(SwingUtils.getFrame(this), m_title, m_mAccount, acctSchemaId);
			//
			final Integer newValue = ad.getValue();
			// if (newValue == null)
			// return;

			// set & redisplay & data binding (i.e. fire vetoable change)
			setValue(newValue);
		}
		finally
		{
			setCursor(Cursor.getDefaultCursor());
		}
	}	// cmd_button

	private AcctSchemaId getAcctSchemaId()
	{
		int C_AcctSchema_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "C_AcctSchema_ID", false);
		// Try to get C_AcctSchema_ID from global context - teo_sarca BF [ 1830531 ]
		if (C_AcctSchema_ID <= 0)
		{
			C_AcctSchema_ID = Env.getContextAsInt(Env.getCtx(), "$C_AcctSchema_ID");
		}
		return AcctSchemaId.ofRepoId(C_AcctSchema_ID);
	}

	private boolean m_cmdTextRunning = false;
	private GridField m_mField;

	/**
	 * Text - try to find Alias or start Dialog.
	 * <p>
	 * <b>IMPORTANT:</b> Keep in sync with {@link VAccountAutoCompleter#getSelectSQL(String, int, java.util.List)}<br>
	 * FIXME: extract the code from both methods into a service.
	 */
	public void cmd_text()
	{
		if (m_cmdTextRunning)
			return;
		m_cmdTextRunning = true;

		String text = m_text.getText();
		log.info("Text=" + text);
		if (text == null || text.length() == 0 || text.equals("%"))
		{
			cmd_button();
			m_cmdTextRunning = false;
			return;
		}
		if (!text.endsWith("%"))
			text += "%";
		//
		String sql = "SELECT C_ValidCombination_ID FROM C_ValidCombination "
				+ "WHERE C_AcctSchema_ID=?"
				+ " AND (UPPER(Alias) LIKE ? OR UPPER(Combination) LIKE ?)";
		sql = Env.getUserRolePermissions().addAccessSQL(sql,
				"C_ValidCombination", IUserRolePermissions.SQL_NOTQUALIFIED, Access.READ);
		int C_AcctSchema_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "C_AcctSchema_ID");
		//
		int C_ValidCombination_ID = 0;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_AcctSchema_ID);
			pstmt.setString(2, text.toUpperCase());
			pstmt.setString(3, text.toUpperCase());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				C_ValidCombination_ID = rs.getInt(1);
				if (rs.next())		// only one
					C_ValidCombination_ID = 0;
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		// We have a Value
		if (C_ValidCombination_ID > 0)
		{
			final Integer newValue = new Integer(C_ValidCombination_ID);

			// set & redisplay & Data Binding (i.e. fire vetoable change)
			setValue(newValue);
		}
		else
		{
			cmd_button();
		}

		m_cmdTextRunning = false;
	}	// actionPerformed

	/**
	 * Action Listener Interface
	 * 
	 * @param listener
	 */
	@Override
	public void addActionListener(ActionListener listener)
	{
		m_text.addActionListener(listener);
	}   // addActionListener
	
	@Override
	public void addMouseListener(final MouseListener l)
	{
		m_text.addMouseListener(l);
	}

	/**
	 * Sets the field for this VEditor. If {@link GridField#isAutocomplete()} returns <code>true</code>, then auto completion is enabled.
	 * 
	 * @param mField
	 * 
	 * @see VAccountAutoCompleter
	 */
	@Override
	public void setField(org.compiere.model.GridField mField)
	{
		if (mField != null)
		{
			m_WindowNo = mField.getWindowNo();
		}
		m_mField = mField;

		if (mField != null && mField.isAutocomplete())
		{
			enableLookupAutocomplete();
		}

		EditorContextPopupMenu.onGridFieldSet(this);
	}   // setField

	private VAccountAutoCompleter lookupAutoCompleter = null;

	/**
	 * 
	 * @return <code>true</code>
	 */
	private boolean enableLookupAutocomplete()
	{
		if (lookupAutoCompleter != null)
		{
			return true; // auto-complete already initialized
		}

		// FIXME: check what happens when lookup changes
		lookupAutoCompleter = new VAccountAutoCompleter(m_text, this, m_mAccount, getAcctSchemaId());
		return true;
	}

	@Override
	public GridField getField()
	{
		return m_mField;
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("VAccount[")
				.append(m_title)
				.append(", value=").append(m_value)
				.append("]");
		return sb.toString();
	}	// toString

	@Override
	public void focusGained(FocusEvent e)
	{
	}

	/**
	 * Mostly copied from {@link VLookup}, can't claim I really understand it.
	 */
	@Override
	public void focusLost(FocusEvent e)
	{
		if (e.isTemporary())
		{
			return;
		}

		if (m_button == null // guarding against NPE (i.e. component was disposed in meantime)
				|| !m_button.isEnabled())	// set by actionButton
		{
			return;
		}

		if (m_mAccount == null)
		{
			return;
		}

		// metas: begin:  02029: nerviges verhalten wenn man eine Maskeneingabe abbrechen will (2011082210000084)
		// Check if toolbar Ignore button was pressed
		if (e.getOppositeComponent() instanceof AbstractButton)
		{
			final AbstractButton b = (AbstractButton)e.getOppositeComponent();
			if (APanel.CMD_Ignore.equals(b.getActionCommand()))
			{
				return;
			}
		}
		// metas: end

		if (m_text == null)
			return; // arhipac: teo_sarca: already disposed
					// Test Case: Open a window, click on account field that is mandatory but not filled, close the window and you will get an NPE
					// TODO: integrate to trunk
		// New text
		String newText = m_text.getText();
		if (newText == null)
			newText = "";
		// Actual text
		String actualText = m_mAccount.getDisplay(m_value);
		if (actualText == null)
			actualText = "";
		// If text was modified, try to resolve the valid combination
		if (!newText.equals(actualText))
		{
			cmd_text();
		}
	}

	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

	@Override
	public ICopyPasteSupportEditor getCopyPasteSupport()
	{
		return m_text == null ? NullCopyPasteSupportEditor.instance : m_text.getCopyPasteSupport();
	}
}	// VAccount
