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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.IRefreshableEditor;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.APanel;
import org.compiere.apps.search.FindHelper;
import org.compiere.apps.search.Info;
import org.compiere.apps.search.InfoBuilder;
import org.compiere.apps.search.InfoFactory;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.ILookupDisplayColumn;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.Lookup;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductPrice;
import org.compiere.model.MQuery;
import org.compiere.swing.CTextField;
import org.compiere.swing.PopupMenuListenerAdapter;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *  Lookup Visual Field.
 *  <p>
 *	    When r/o - display a Label
 *		When STABLE - display a ComboBox
 *		Otherwise show Selection Dialog
 *  <p>
 *  Special handling of BPartner and Product
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VLookup.java,v 1.5 2006/10/06 00:42:38 jjanke Exp $
 *
 *  @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *				<li>BF [ 1740835 ] NPE when closing a window
 *				<li>BF [ 1817768 ] Isolate hardcoded table direct columns
 *				<li>BF [ 1834399 ] VLookup: pressing enter twice has a annoying behaviour
 *				<li>BF [ 1979213 ] VLookup.getDirectAccessSQL issue
 *				<li>BF [ 2552901 ] VLookup: TAB is not working OK
 *  @author		Michael Judd (MultiSelect)
 *  
 *  @author hengsin, hengsin.low@idalica.com
 *  @see FR [2887701] https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2887701&group_id=176962
 *  @sponsor www.metas.de
 */
public class VLookup extends JComponent
	implements VEditor, ActionListener, FocusListener
	, IRefreshableEditor // metas
	, ICopyPasteSupportEditorAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1307112072890929329L;
	
	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e,
			int condition, boolean pressed)
	{
		if (e.getSource() == m_combo || e.getSource() == m_text || e.getSource() == this) {
			return super.processKeyBinding(ks, e, condition, pressed);
		}

		JComponent editorComp = getEditorComponent();
		InputMap map = editorComp.getInputMap(condition);
        ActionMap am = editorComp.getActionMap();

        if(map!=null && am!=null && isEnabled())
        {
            Object binding = map.get(ks);
            Action action = (binding==null) ? null : am.get(binding);
            if(action!=null)
            {
                return SwingUtilities.notifyAction(action, ks, e, editorComp, e.getModifiers());
            }
        }
        return false;
	}

	/**
	 *  Create Optional BPartner Search Lookup
	 *  @param WindowNo window
	 *  @return VLookup
	 */
	public static VLookup createBPartner (int WindowNo)
	{
		int AD_Column_ID = 3499;    //  C_Invoice.C_BPartner_ID
		try
		{
			Lookup lookup = MLookupFactory.get (Env.getCtx(), WindowNo,
				0, AD_Column_ID, DisplayType.Search);
			return new VLookup ("C_BPartner_ID", false, false, true, lookup);
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		return null;
	}   //  createBPartner

	/**
	 *  Create Optional Product Search Lookup
	 *  @param WindowNo window
	 *  @return VLookup
	 */
	public static VLookup createProduct (int WindowNo)
	{
		int AD_Column_ID = 3840;    //  C_InvoiceLine.M_Product_ID
		try
		{
			Lookup lookup = MLookupFactory.get (Env.getCtx(), WindowNo, 0,
				AD_Column_ID, DisplayType.Search);
			return new VLookup ("M_Product_ID", false, false, true, lookup);
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		return null;
	}   //  createProduct

	/**
	 *  Create Optional User Search Lookup
	 *  @param WindowNo window
	 *  @return VLookup
	 */
	public static VLookup createUser (int WindowNo)
	{
		int AD_Column_ID = 10443;    //  AD_WF_Activity.AD_User_UD
		try
		{
			Lookup lookup = MLookupFactory.get (Env.getCtx(), WindowNo, 0,
				AD_Column_ID, DisplayType.Search);
			return new VLookup ("AD_User_ID", false, false, true, lookup);
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		return null;
	}   //  createProduct


	/*************************************************************************
	 *	Detail Constructor
	 *
	 *  @param columnName column
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 *  @param isUpdateable updateable
	 *  @param lookup lookup
	 */
	public VLookup (final String columnName,
			final boolean mandatory,
			final boolean isReadOnly,
			final boolean isUpdateable,
			final Lookup lookup)
	{
		super();
		m_columnName = columnName;
		super.setName(columnName);
		m_combo.setName(columnName);
		setMandatory(mandatory);
//		m_lookup = lookup;
//		if (m_lookup != null)
//			m_lookup.setMandatory(isMandatory());
		
		//
		setLayout(new BorderLayout());
		
		//
		// Text field
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setName(columnName);
		m_text.addActionListener(this);
		m_text.addFocusListener(this);
		
		//
		//  Button
		m_button = VEditorUtils.createActionButton(getActionButtonIconName(), m_text);
		m_button.addActionListener(this);

		//
		// Lookup config & load
		setLookup(lookup);
//		if (isComboBox())
//		{
//			//  Don't have to fill up combobox if it is readonly
//			if (!isReadOnly && isUpdateable)
//				m_lookup.fillComboBox (isMandatory(), true, true, false);
//			//m_combo.setModel(m_lookup);
//			m_comboModelProxy.setDelegate(m_lookup);
//		}

		//
		// Combobox
		if (isComboBox())	//	No Search
		{
			m_combo.setName(columnName);
			m_combo.enableAutoCompletion();
			m_combo.addActionListener(this);							//	Selection
//			m_combo.getEditor().getEditorComponent().addMouseListener(mouseAdapter);	                        //	popup
			//	FocusListener to refresh selection before opening
			m_combo.addFocusListener(this);
			m_combo.getEditor().getEditorComponent().addFocusListener(this);			
			m_combo.addPopupMenuListener(new PopupMenuListenerAdapter()
			{
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e)
				{
					refreshLookupIfNeeded();
				}
			});
		}

		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);
		final Dimension size = m_text.getPreferredSize();
		m_combo.setPreferredSize(new Dimension(size));

		copyPasteSupport = new VLookupCopyPasteSupportEditor(m_combo, m_text);
		setUI (true);

		
		//	ReadWrite	-	decides what components to show
		if (isReadOnly || !isUpdateable)
		{
			setReadWrite(false);
		}
		else
		{
			setReadWrite(true);
		}
		
		//
		// Create and bind the context menu
		new EditorContextPopupMenu(this);
	}	//	VLookup
	
	private final String getActionButtonIconName()
	{
		final String actionButtonIconName;
		if ("C_BPartner_ID".equals(m_columnName))
		{
			actionButtonIconName = "BPartner";
		}
		else if ("M_Product_ID".equals(m_columnName))
		{
			actionButtonIconName = "Product";
		}
		else
		{
			actionButtonIconName = "PickOpen";
		}
		return actionButtonIconName;
	}
	
	private void setLookup(final Lookup lookup)
	{
		m_lookup = lookup;
		
		if (lookup != null)
		{
			lookup.setMandatory(isMandatory());
			if (lookup.isAutoComplete())
			{
				enableLookupAutocomplete();
			}
		}
		
		if (isComboBox())
		{
			//  Don't have to fill up combobox if it is readonly
			// FIXME: postpone loading
//			if (isReadWrite())
//				m_lookup.fillComboBox (isMandatory(), true, true, false);
			
			//m_combo.setModel(m_lookup);
			m_comboModelProxy.setDelegate(lookup);
		}
	}
	
	public String getColumnName()
	{
		return m_columnName;
	}

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
//		m_lookup = null;
		m_mField = null;
		//
		if (m_combo != null)
		{
			m_combo.getEditor().getEditorComponent().removeFocusListener(this);
	//		m_combo.getEditor().getEditorComponent().removeMouseListener(mouseAdapter);
			m_combo.removeFocusListener(this);
			m_combo.removeActionListener(this);
			m_combo.setSelectedItem(null);
			m_combo.setModel(new DefaultComboBoxModel<>());    //  remove reference
			m_comboModelProxy.setDelegate(null);
		//	m_combo.removeAllItems();
			m_combo = null;
		}
		lookupAutoCompleter = null;
	}   //  dispose

	/** Display Length for Lookups (15)         */
	public final static int DISPLAY_LENGTH = 15;

	/** Search: The Editable Text Field         */
	private CTextField 			m_text = new CTextField (DISPLAY_LENGTH);
	/** Search: The Button to open Editor   */
	private VEditorActionButton m_button = null;
	private final MutableComboBoxModelProxy m_comboModelProxy = new MutableComboBoxModelProxy();
	/** The Combo Box if not a Search Lookup    */
	private VComboBox			m_combo = new VComboBox(m_comboModelProxy);
	/** Indicator that value is being set       */
	private volatile boolean 	m_settingValue = false;
	/** Indicator - inserting new value			*/
	private volatile boolean	m_inserting = false;
	/** Last Display							*/
	private String				m_lastDisplay = "";
	/** Column Name								*/
	private final String		m_columnName;
	/** Lookup									*/
	private Lookup				m_lookup;
	/** Combo Box Active						*/
	private boolean				m_comboActive = true;
	/** The Value								*/
	private Object				m_value;

	private boolean 			m_stopediting = false;

	//	Field for Value Preference
	private GridField              m_mField = null;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VLookup.class);

	private final VLookupCopyPasteSupportEditor copyPasteSupport;

	/**
	 * Set Content and Size of Components
	 * 
	 * @param initial true if this is the initial call (on component construction)
	 */
	private void setUI (final boolean initial)
	{
		//	What to show
		this.remove(m_combo);
		this.remove(m_button);
		this.remove(m_text);
		//
		if (!isReadWrite())									//	r/o - show text only
		{
			LookAndFeel.installBorder(this, "TextField.border");
			this.add(m_text, BorderLayout.CENTER);
			m_text.setReadWrite(false);
			m_combo.setReadWrite(false);
			m_comboActive = false;
			copyPasteSupport.activateTextField();
		}
		else if (isComboBox())	    //	show combo if not Search
		{
			this.setBorder(null); // no border, because we are showing the combobox, which already has a border
			this.add(m_combo, BorderLayout.CENTER);
			m_comboActive = true;
			copyPasteSupport.activateCombo();
		}
		else 												//	Search or unstable - show text & button
		{
			LookAndFeel.installBorder(this, "TextField.border");
			this.add(m_text, BorderLayout.CENTER);
			VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);
			m_text.setReadWrite (true);
			m_comboActive = false;
			copyPasteSupport.activateTextField();
		}
	}   //  setUI
	
	public Lookup getLookup()
	{
		return m_lookup;
	}
	
	public boolean isComboBox()
	{
		final Lookup lookup = getLookup();
		return lookup != null && lookup.getDisplayType() != DisplayType.Search;		
	}

	public boolean isSearchBox()
	{
		final Lookup lookup = getLookup();
		return lookup != null && lookup.getDisplayType() == DisplayType.Search;		
	}

	/**
	 * Exposed because key binding listeners are extremely fragile and sometimes needed to override {@link #processKeyBinding(KeyStroke, KeyEvent, int, boolean)}.
	 *
	 * @return editor component
	 */
	public final JComponent getEditorComponent()
	{
		final JComponent editorComp;
		if (isComboBox())
		{
			editorComp = m_combo;
		}
		else
		{
			editorComp = m_text;
		}

		return editorComp;
	}

	/**
	 * Loads first item for a comboBox
	 * 
	 * @throws AdempiereException if {@link #m_combo} is null or if {@link #isComboBox()} is false
	 */
	public void loadFirstItem() throws AdempiereException
	{
		Check.assumeNotNull(m_combo, "m_combo not null");
		Check.assume(isComboBox(), "comboBox lookup expected for {}", this);

		final Lookup lookup = getLookup();
		if (lookup == null)
		{
			return;
		}

		//
		// Load data right now
		lookup.fillComboBox(false); // restore
		if (!lookup.isLoaded() || lookup.getSize() <= 0) // lookup must have data loaded
		{
			return;
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				final Object firstKey;
				final Object firstElement = lookup.getElementAt(0);
				if (firstElement == null || !(firstElement instanceof KeyNamePair))
				{
					firstKey = firstElement;
				}
				else
				{
					firstKey = ((KeyNamePair)firstElement).getKey();
				}
				setValue(firstKey);
			}
		});
	}

	/**
	 * 
	 * @return window number or {@link Env#WINDOW_None}
	 */
	private int getWindowNo()
	{
		final Lookup lookup = getLookup();
		if (lookup != null)
		{
			return lookup.getWindowNo();
		}
		
		if (m_mField != null)
		{
			return m_mField.getWindowNo();
		}
		
		return Env.WINDOW_None;
	}

	/**
	 *	Set ReadWrite
	 *  @param value ReadWrite
	 */
	@Override
	public void setReadWrite (final boolean value)
	{
		boolean rw = value;
		
		final Lookup lookup = getLookup();
		if (lookup == null)
		{
			rw = false;
		}
		
		if (m_combo.isReadWrite() != value)
		{
			m_combo.setReadWrite(rw);
			setUI(false);
			if (value && m_comboActive)
			{
				m_settingValue = true;		//	disable actions
				try
				{
					refresh();
				}
				finally
				{
					m_settingValue = false;
				}
			}
			if (m_comboActive)
			{
				setValue (m_value);
			}
		}
		// If the field is readonly the BPartner new option should be hidden - teo_sarca [ 1721710 ]
//		if (mBPartnerNew != null)
//			mBPartnerNew.setVisible(value);
	}	//	setReadWrite

	/**
	 *	IsEditable
	 *  @return is lookup ReadWrite
	 */
	@Override
	public boolean isReadWrite()
	{
		return m_combo.isReadWrite();
	}	//	isReadWrite

	/**
	 *	Set Mandatory (and back color)
	 *  @param mandatory mandatory
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_combo.setMandatory(mandatory);
		m_text.setMandatory(mandatory);
	}	//	setMandatory

	/**
	 *	Is it mandatory
	 *  @return true if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_combo.isMandatory();
	}	//	isMandatory

	/**
	 *	Set Background
	 *  @param color color
	 */
	@Override
	public void setBackground(Color color)
	{
		m_text.setBackground(color);
		m_combo.setBackground(color);
	}	//	setBackground

	/**
	 *	Set Background
	 *  @param error error
	 */
	@Override
	public void setBackground (boolean error)
	{
		m_text.setBackground(error);
		m_combo.setBackground(error);
	}	//	setBackground

	/**
	 *  Set Foreground
	 *  @param fg Foreground color
	 */
	@Override
	public void setForeground(Color fg)
	{
		m_text.setForeground(fg);
		m_combo.setForeground(fg);
	}   //  setForeground

	/**
	 * 	Request Focus
	 */
	@Override
	public void requestFocus ()
	{
		final JComponent editorComp = getEditorComponent();
		if (editorComp != null)
		{
			editorComp.requestFocus();
		}
	}	//	requestFocus


	/**
	 *  Set Editor to value
	 *  @param value new Value
	 */
	@Override
	public void setValue (Object value)
	{
		log.debug(m_columnName + "=" + value);
		m_settingValue = true;		//	disable actions
		m_value = value;

		//	Set both for switching
		if (value == null)
		{
			// even if the value is null, we still need to refresh lookup
			// we need in the list the new values
			if (m_lookup != null)
			{
				m_lookup.refresh();
			}
			m_combo.setValue (value);
			m_text.setText (null);
			m_lastDisplay = "";
			m_settingValue = false;
			return;
		}
		
		final Lookup lookup = getLookup();
		if (lookup == null)
		{
			m_combo.setValue (value);
			m_text.setText (value.toString());
			m_lastDisplay = value.toString();
			m_settingValue = false;
			return;
		}

		//must call m_combo.setvalue after m_lookup as
		//loading of combo data might happen in m_lookup.getDisplay
		final IValidationContext validationCtx = getValidationContext();
		m_lastDisplay = lookup.getDisplay(validationCtx, value);
		m_combo.setValue (value);

		if (m_lastDisplay.equals("<-1>"))
		{
			m_lastDisplay = "";
			m_value = null;
		}
		boolean notFound = m_lastDisplay.startsWith("<") && m_lastDisplay.endsWith(">");
		m_text.setText (m_lastDisplay);
		m_lastDisplay = m_text.getText(); // metas: 02027: reason of doing this is because if the string contains "\r\n" the getText will return "\r" only
		m_text.setCaretPosition (0); //	show beginning

		//	Nothing showing in Combo and should be showing
		if (m_combo.getSelectedItem() == null
			&& (m_comboActive || (m_inserting && lookup.getDisplayType() != DisplayType.Search)))
		{
			//  lookup found nothing too
			if (notFound)
			{
				log.trace(m_columnName + "=" + value + ": Not found - " + m_lastDisplay);
				//  we may have a new value
				lookup.refresh();
				final boolean valueSet = m_combo.setValueAndReturnIfSet(value);
				m_lastDisplay = lookup.getDisplay(validationCtx, value);
				m_text.setText (m_lastDisplay);
				m_text.setCaretPosition (0);	//	show beginning
				notFound = !valueSet;
			}
			
			final boolean rowLoading = isRowLoading();
			if (notFound && rowLoading)
			{
				// NOTE: In case row is loading we need to display the value anyway, even if it's not available anymore
				// See 04764, G01T080, G01T082, G01T100
				m_lastDisplay = lookup.getDisplay(IValidationContext.DISABLED, value);
				m_text.setText (m_lastDisplay);
				m_text.setCaretPosition (0);	//	show beginning
				
				final NamePair pp;
				if (m_value instanceof Integer)
				{
					final int id = (Integer)m_value;
					pp = new KeyNamePair(id, m_lastDisplay);					
				}
				else
				{
					final String valueStr = m_value == null ? "" : m_value.toString();
					pp = new ValueNamePair(valueStr, m_lastDisplay);
				}
				
				m_combo.addItem (pp);
				m_combo.setValue (value);
				
			}
			else if (notFound && !rowLoading)	//	<key>
			{
				m_value = null;
				actionCombo (null);             //  data binding
				log.debug(m_columnName + "=" + value + ": Not found");
			}
			//  we have lookup
			else if (m_combo.getSelectedItem() == null)
			{
				NamePair pp = lookup.get(getValidationContext(), value);
				if (pp != null)
				{
					log.debug(m_columnName + " added to combo - " + pp);
					//  Add to Combo
					m_combo.addItem (pp);
					m_combo.setValue (value);
				}
			}
			//  Not in Lookup - set to Null
			if (m_combo.getSelectedItem() == null)
			{
				log.info(m_columnName + "=" + value + ": not in Lookup - set to NULL");
				actionCombo (null);             //  data binding (calls setValue again)
				m_value = null;
			}
		}
		m_settingValue = false;
	}	//	setValue

	/**
	 *  Property Change Listener.
	 *  
	 *  NOTE: this is used for Model to View binding
	 *  @param evt PropertyChangeEvent
	 */
	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{
		if (m_stopediting)
			return;

	//	log.debug( "VLookup.propertyChange", evt);
		if (evt.getPropertyName().equals(GridField.PROPERTY))
		{
			m_inserting = GridField.INSERTING.equals(evt.getOldValue());	//	MField.setValue
			setValue(evt.getNewValue());
			m_inserting = false;
		}
		// metas: request focus (2009_0027_G131)
		else if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
		{
			requestFocus();
		}
		// metas end

	}   //  propertyChange

	/**
	 *	Return Editor Key Value (Integer)
	 *  @return value
	 */
	@Override
	public Object getValue()
	{
		if (m_comboActive)
		{
			return m_combo.getValue ();
		}
		return m_value;
	}	//	getValue
	
	public int getValueAsInt()
	{
		final Object value = getValue();
		if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		
		return -1;
	}

	/**
	 *  Return editor display
	 *  @return display value
	 */
	@Override
	public String getDisplay()
	{
		if (m_comboActive)
		{
			return m_combo.getDisplay();
		}
		
		//  check lookup
		final String retValue;
		final Lookup lookup = getLookup();
		if (lookup == null)
		{
			retValue = m_value == null ? null : m_value.toString();
		}
		else
		{
			retValue = lookup.getDisplay(m_value);
		}
		return retValue;
	}   //  getDisplay

	/**
	 *  Set Field/WindowNo for ValuePreference
	 *  @param mField Model Field for Lookup
	 */
	@Override
	public void setField (GridField mField)
	{
		m_mField = mField;
		
		//
		// metas-2009_0021_AP1_CR054: begin
		if (mField != null && mField.isAutocomplete())
		{
			enableLookupAutocomplete();
		}
		// metas-2009_0021_AP1_CR054: end

		EditorContextPopupMenu.onGridFieldSet(this);
	}   //  setField

	@Override
	public GridField getField()
	{
		return m_mField;
	}

	/**************************************************************************
	 *	Action Listener	- data binding
	 *  @param e ActionEvent
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (m_settingValue || m_stopediting)
			return;
		
		if (m_settingFocus)
		{
			return;
		}
		
		log.info(m_columnName + " - " + e.getActionCommand() + ", ComboValue=" + m_combo.getSelectedItem());

		//  Combo Selection
		if (e.getSource() == m_combo)
		{
			Object value = getValue();
			Object o = m_combo.getSelectedItem();
			if (o != null)
			{
				String s = o.toString();
				//  don't allow selection of inactive
				if (s.startsWith(MLookup.INACTIVE_S) && s.endsWith(MLookup.INACTIVE_E))
				{
					log.info(m_columnName + " - selection inactive set to NULL");
					value = null;
				}
			}
			actionCombo (value);                //  data binding
		}
		//  Button pressed
		else if (e.getSource() == m_button)
			actionButton ("");
		//  Text entered
		else if (e.getSource() == m_text)
			actionText();
	}	//	actionPerformed

	/**
	 * Action Listener Interface
	 *
	 * @param listener
	 */
	@Override
	public void addActionListener(final ActionListener listener)
	{
		m_combo.addActionListener(listener);
		m_text.addActionListener(listener);
	}   // addActionListener

	/**
	 * Key Listener Interface
	 *
	 * @param listener
	 */
	@Override
	public void addKeyListener(final KeyListener listener)
	{
		//
		// Combo Box Lookup
		{
			final ComboBoxEditor m_comboEditor = m_combo.getEditor();
			final Component m_comboEditorComponent = m_comboEditor.getEditorComponent();
			m_comboEditorComponent.addKeyListener(listener);
		}
		//
		// Text Field Lookup
		{
			m_text.addKeyListener(listener);
		}
	}

	/**
	 *	Action - Combo.
	 *  <br>
	 *	== dataBinding == inform of new value
	 *  <pre>
	 *  VLookup.actionCombo
	 *      GridController.vetoableChange
	 *          MTable.setValueAt
	 *              MField.setValue
	 *                  VLookup.setValue
	 *          MTab.dataStatusChanged
	 *  </pre>
	 *  @param value new value
	 */
	// metas: changed access from private to protected
	protected void actionCombo(Object value)
	{
		log.debug("Value=" + value);
		try
		{
			// -> GridController.vetoableChange
			fireVetoableChange (m_columnName, null, value);
		}
		catch (PropertyVetoException pve)
		{
			log.error(m_columnName, pve);
		}
		//  is the value updated ?
		boolean updated = false;

		Object updatedValue = value;

		if (updatedValue instanceof Object[] && ((Object[])updatedValue).length > 0)
		{
			updatedValue = ((Object[])updatedValue)[0];
		}

		if (updatedValue == null && m_value == null)
			updated = true;
		else if (updatedValue != null && value.equals(m_value))
			updated = true;
		if (!updated)
		{
			//  happens if VLookup is used outside of APanel/GridController (no property listener)
			log.debug(m_columnName + " - Value explicitly set - new=" + updatedValue + ", old=" + m_value);
			
			// phib: the following check causes the update to fail on jre > 1.6.0_13
			// commenting out as it does not appear to be necessary
			//if (getListeners(PropertyChangeListener.class).length <= 0)
				setValue(updatedValue);
		}
	}	//	actionCombo


	/**
	 *	Action - Button.
	 *	- Call Info
	 *	@param queryValue initial query value
	 */
	private void actionButton (final String queryValue)
	{
		if (m_button == null)
		{
			// lookup was disposed in meantime
			return;
		}

		final boolean enabledOld = m_button.isEnabled();
		m_button.setEnabled(false);                 //  disable double click
		
		// If there is lookup, leave button disabled and quit
		if (getLookup() == null)
		{
			return;
		}
		
		m_text.requestFocus();						//  closes other editors
		
		try
		{
			actionButton0(queryValue);
		}
		finally
		{
			m_button.setEnabled(enabledOld);
			m_text.requestFocus();
		}
	}
	
	private void actionButton0 (String queryValue)
	{
		Frame frame = Env.getFrame(this);

		/**
		 *  Three return options:
		 *  - Value Selected & OK pressed   => store result => result has value
		 *  - Cancel pressed                => store null   => result == null && cancelled
		 *  - Window closed                 -> ignore       => result == null && !cancalled
		 */

		//
		final Lookup lookup = getLookup();
		final String keyColumnName = lookup.getColumnNameNotFQ();
		final String tableName = lookup.getTableName(); 
		//  Zoom / Validation
		String whereClause = getWhereClause(lookup);
		//
		log.debug(keyColumnName + ", Zoom=" + lookup.getZoom() + " (" + whereClause + ")");

		//
		// Ask the Info Window
		final String infoFactoryClass = lookup.getInfoFactoryClass();
		Object result[] = null;
		boolean cancelled = false;
		boolean resetValue = false;	//	reset value so that is always treated as new entry
		boolean multipleSelection = false;
		
		if (!infoWindowEnabled)
		{
			cancelled = false;
			resetValue = false;
			result = null;
		}
		else if (infoFactoryClass != null && infoFactoryClass.trim().length() > 0)
		{
			try
			{
				@SuppressWarnings("unchecked")
				final Class<InfoFactory> clazz = (Class<InfoFactory>)this.getClass().getClassLoader().loadClass(infoFactoryClass);
				InfoFactory factory = clazz.newInstance();
				Info ig = factory.create (frame, true, lookup.getWindowNo(), tableName, keyColumnName, queryValue, false, whereClause);
				ig.showWindow(); // show window and wait until the modal dialog closes
				cancelled = ig.isCancelled();
				result = ig.getSelectedKeys();
			}
			catch (Exception e)
			{
				log.error("Failed to load custom InfoFactory - " + e.getLocalizedMessage(), e);
			}
		}
		else if (keyColumnName.equals("M_Product_ID"))
		{
			//	Reset
			resetTabInfo();
			//  Replace Value with name if no value exists
			if (queryValue.length() == 0 && m_text.getText().length() > 0)
				queryValue = "@" + m_text.getText() + "@";   //  Name indicator - otherwise Value

			if(m_mField != null)
			{
				int AD_Table_ID = m_mField.getAD_Table_ID();
				multipleSelection = (MOrderLine.Table_ID ==  AD_Table_ID) || (MInvoiceLine.Table_ID == AD_Table_ID) || (I_PP_Product_BOMLine.Table_ID == AD_Table_ID) || (MProductPrice.Table_ID == AD_Table_ID);
			}
			
			//	Show Info
			final Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("M_Warehouse_ID", Env.getContextAsInt(Env.getCtx(), lookup.getWindowNo(), "M_Warehouse_ID"));
			attributes.put("M_PriceList_ID", Env.getContextAsInt(Env.getCtx(), lookup.getWindowNo(), "M_PriceList_ID"));
			
			final Info ip = InfoBuilder.newBuilder()
					.setParentFrame(frame)
					.setWindowNo(lookup.getWindowNo())
					.setModal(true)
					.setTableName(I_M_Product.Table_Name)
					.setSearchValue(queryValue)
					.setMultiSelection(multipleSelection)
					.setWhereClause(whereClause)
					.setAttributes(attributes)
					.buildAndShow(); // show window and wait until the modal dialog closes
			cancelled = ip.isCancelled();
			result = ip.getSelectedKeys();
			resetValue = true;
		}
		else if (keyColumnName.equals("C_BPartner_ID"))
		{
			//  Replace Value with name if no value exists
			if (queryValue.length() == 0 && m_text.getText().length() > 0)
				queryValue = m_text.getText();
//			boolean isSOTrx = true;     //  default
//			if (Env.getContext(Env.getCtx(), m_lookup.getWindowNo(), "IsSOTrx").equals("N"))
//				isSOTrx = false;
			final Info ip = InfoBuilder.newBuilder()
					.setParentFrame(frame)
					.setWindowNo(lookup.getWindowNo())
					.setModal(true)
					.setTableName(I_C_BPartner.Table_Name)
					.setSearchValue(queryValue)
					.setMultiSelection(multipleSelection)
					.setWhereClause(whereClause)
					.buildAndShow(); // show window and wait until the modal dialog closes
			cancelled = ip.isCancelled();
			result = ip.getSelectedKeys();
		}
		else	//	General Info
		{
//			if (m_tableName == null)	//	sets table name & key column
//				getDirectAccessSQL("*");
			final Info ig = InfoBuilder.newBuilder()
					.setParentFrame(frame)
					.setWindowNo(lookup.getWindowNo())
					.setModal(true)
					.setTableName(tableName)
					.setKeyColumn(keyColumnName)
					.setSearchValue(queryValue)
					.setMultiSelection(multipleSelection)
					.setWhereClause(whereClause)
					.buildAndShow(); // show window and wait until the modal dialog closes
			cancelled = ig.isCancelled();
			result = ig.getSelectedKeys();
		}

		//  Result
		if (result != null && result.length > 0)
		{
			log.info(m_columnName + " - Result = " + result.toString() + " (" + result.getClass().getName() + ")");
			//  make sure that value is in cache
			lookup.getDirect(IValidationContext.NULL, result[0], false, true); // saveInCache=false, cacheLocal=true
			if (resetValue)
				actionCombo (null);
			// juddm added logic for multi-select handling
			if (result.length > 1)
				actionCombo (result);	//	data binding
			else
				actionCombo (result[0]);

		}
		else if (cancelled)
		{
			log.info(m_columnName + " - Result = null (cancelled)");
			actionCombo(null);
		}
		else
		{
			log.info(m_columnName + " - Result = null (not cancelled)");
			setValue(m_value);      //  to re-display value
		}
	}	//	actionButton

	/**
	 * 	Get Where Clause
	 *	@return where clause or ""
	 */
	private static String getWhereClause(final Lookup lookup)
	{
//		final Lookup lookup = getLookup();
		if (lookup == null)
			return "";
		
		String whereClause = "";
		
		if (lookup.getZoomQuery() != null)
			whereClause = lookup.getZoomQuery().getWhereClause();
		String validation = lookup.getValidation();
		if (validation == null)
			validation = "";
		if (whereClause.length() == 0)
			whereClause = validation;
		else if (validation.length() > 0)
			whereClause += " AND (" + validation + ")";
	//	log.trace("ZoomQuery=" + (m_lookup.getZoomQuery()==null ? "" : m_lookup.getZoomQuery().getWhereClause())
	//		+ ", Validation=" + m_lookup.getValidation());
		if (whereClause.indexOf('@') != -1)
		{
			String validated = Env.parseContext(Env.getCtx(), lookup.getWindowNo(), whereClause, false);
			if (validated.length() == 0)
				log.error(lookup + " - Cannot Parse=" + whereClause);
			else
			{
				log.debug(lookup + " - Parsed: " + validated);
				return validated;
			}
		}
		return whereClause;
	}	//	getWhereClause

	/**
	 *	Check, if data returns unique entry, otherwise involve Info via Button
	 */
	private void actionText()
	{
		//
		// services
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		
		final String text = m_text.getText();
		// Nothing entered, just pressing enter again => ignore - teo_sarca BF [ 1834399 ]
		if (text != null && text.length() > 0 && text.equals(m_lastDisplay))
		{
			log.trace("Nothing entered [SKIP]");
			return;
		}
		
		//	Field cleared by user => set null
		if (text == null || text.length() == 0)
		{
			actionCombo(null);
			return;
		}

		//	Nothing entered
		if (text == null || text.length() == 0 || text.equals("%"))
		{
			actionButton(text);
			return;
		}

		final Lookup lookup = getLookup();
		if (lookup == null)
		{
			// No lookup, nothing to do
			return;
		}
		
		resetTabInfo();
		
		final String sql = getDirectAccessSQL(text, m_columnName, lookup);
		final String finalSQL = msgBL.parseTranslation(Env.getCtx(), sql);

		// metas: cg:  task: 02491 : use the function UPPER of the database
//		text = text.toUpperCase(); 
//		log.info(m_columnName + " - " + text);
		//	Exact first
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int id = -3;
		try
		{
			pstmt = DB.prepareStatement(finalSQL, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				id = rs.getInt(1);		//	first
				final boolean fullyMatched = "Y".equals(rs.getString(2));
				if (fullyMatched)
				{
					if (rs.next())
					{
						final boolean nextFullyMatched = "Y".equals(rs.getString(2));
						if (nextFullyMatched)
						{
							// We have more then one fully matched records => search in all
							id = -1;
						}
					}
				}
				else
				{
					// If is not the exact match, search in all (02491)
					id = -1;
				}
			}
		}
		catch (Exception e)
		{
			log.error(finalSQL, e);
			id = -2;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//	No (unique) result
		if (id <= 0)
		{
			if (id == -3)
				log.debug(m_columnName + " - Not Found - " + finalSQL);
			else
				log.debug(m_columnName + " - Not Unique - " + finalSQL);
			m_value = null;	// force re-display
			actionButton(m_text.getText());
			return;
		}
		log.debug(m_columnName + " - Unique ID=" + id);
		m_value = null;     //  forces re-display if value is unchanged but text updated and still unique
		resetTabInfo();
		actionCombo (new Integer(id));          //  data binding
		//
		// Don't request focus if value was solved - teo_sarca [ 2552901 ]
		if (id <= 0)
		{
			m_text.requestFocus();
		}
	}	//	actionText


//	private String		m_tableName = null;
//	private String		m_keyColumnName = null;

	/**
	 * 	Generate Access SQL for Search.
	 * 	The SQL returns the ID of the value entered
	 * 	Also sets m_tableName and m_keyColumnName
	 *	@param text search text
	 *	@return sql or ""
	 *  Example
	 *	SELECT C_Payment_ID, (IsFullyMatched Y/N) FROM C_Payment WHERE UPPER(DocumentNo) LIKE x OR ...
	 */
	private static String getDirectAccessSQL (final String text, final String m_columnName, final Lookup lookup)
	{
		final StringBuilder sql = new StringBuilder();
		String m_tableName = MQuery.getZoomTableName(m_columnName);
		
		// prepare text
		final String preparedSearchText = DB.TO_STRING(FindHelper.prepareSearchString(text));
		// upper with DB function the prepared text
		final String upperPreparedSearchText = "UPPER(" + preparedSearchText + ")"; //metas: cg: task: 02491
		// upper with DB function the original text
		final String upperText = "UPPER(" + DB.TO_STRING(text) + ")";
		String orderBySql = " ORDER BY ";
		//
		if (m_columnName.equals("M_Product_ID"))
		{
			//	Reset
			//resetTabInfo();
			String sqlFullMatch = "(CASE WHEN UPPER(Value)="+upperText
					+" OR UPPER(Name)="+upperText
					+" OR SKU="+DB.TO_STRING(text) 
					+"OR UPC="+DB.TO_STRING(text)
					+" THEN 'Y' ELSE 'N' END)";

			sql.append("SELECT M_Product_ID")
				.append(", ").append(sqlFullMatch)
				.append(" FROM M_Product WHERE (UPPER(Value) LIKE ")
				.append(upperPreparedSearchText) 
				.append(" OR UPPER(Name) LIKE ").append(upperPreparedSearchText)
				.append(" OR SKU LIKE ").append(preparedSearchText)
				.append(" OR UPC LIKE ").append(preparedSearchText).append(")");
			orderBySql = orderBySql + " " + sqlFullMatch + " DESC";
		}
		else if (m_columnName.equals("C_BPartner_ID"))
		{
			// metas: cg: start: task: 02491
			final String sqlFullMatch = "(CASE WHEN UPPER(Value)="+upperText
					+" OR UPPER(Name)="+upperText 
					+" THEN 'Y' ELSE 'N' END) ";
			sql.append("SELECT C_BPartner_ID")
			   .append(", ").append(sqlFullMatch)
			   .append(" FROM C_BPartner WHERE (UPPER(Value) LIKE ")
				.append(upperPreparedSearchText)
				.append(" OR UPPER(Name) LIKE ").append(upperPreparedSearchText).append(")");
			orderBySql = orderBySql+" "+sqlFullMatch+" DESC";
			// metas: cg: end: task: 02491
		}
		else if (m_columnName.equals("C_Order_ID"))
		{
			final String sqlFullMatch = "(CASE WHEN UPPER(DocumentNo)="+upperText+" THEN 'Y' ELSE 'N' END) ";
			sql.append("SELECT C_Order_ID")
			   .append(", ").append(sqlFullMatch)
			   .append(" FROM C_Order WHERE UPPER(DocumentNo) LIKE ")
			   .append(upperPreparedSearchText);
			orderBySql = orderBySql+" "+sqlFullMatch+" DESC";
		}
		else if (m_columnName.equals("C_Invoice_ID"))
		{
			final String sqlFullMatch = "(CASE WHEN UPPER(DocumentNo)="+upperText+" THEN 'Y' ELSE 'N' END)";
			sql.append("SELECT C_Invoice_ID")
			   .append(", ").append(sqlFullMatch)
			   .append(" FROM C_Invoice WHERE UPPER(DocumentNo) LIKE ")
			   .append(upperPreparedSearchText);
			orderBySql = orderBySql+" "+sqlFullMatch+" DESC";
		}
		else if (m_columnName.equals("M_InOut_ID"))
		{
			// metas: cg: start: task: 02491
			final String sqlFullMatch = "(CASE WHEN UPPER(DocumentNo)="+upperText+" THEN 'Y' ELSE 'N' END) ";
			sql.append("SELECT M_InOut_ID")
			   .append(", ").append(sqlFullMatch)
			   .append(" FROM M_InOut WHERE UPPER(DocumentNo) LIKE ").append(upperPreparedSearchText);
			orderBySql = orderBySql+" "+sqlFullMatch+" DESC";
		}
		else if (m_columnName.equals("C_Payment_ID"))
		{
			final String sqlFullMatch = "(CASE WHEN UPPER(DocumentNo)="+upperText+" THEN 'Y' ELSE 'N' END) ";
			sql.append("SELECT C_Payment_ID")
			   .append(", ").append(sqlFullMatch)
			   .append(" FROM C_Payment WHERE UPPER(DocumentNo) LIKE ").append(upperPreparedSearchText);
			orderBySql = orderBySql+" "+sqlFullMatch+" DESC";
		}
		else if (m_columnName.equals("GL_JournalBatch_ID"))
		{
			final String sqlFullMatch = "(CASE WHEN UPPER(DocumentNo)="+upperText+" THEN 'Y' ELSE 'N' END) ";
			sql.append("SELECT GL_JournalBatch_ID")
			   .append(", ").append(sqlFullMatch)
			   .append(" FROM GL_JournalBatch WHERE UPPER(DocumentNo) LIKE ")
			   .append(upperPreparedSearchText);
			orderBySql = orderBySql+" "+sqlFullMatch+" DESC";
		}
		else if (m_columnName.equals("SalesRep_ID"))
		{
			final String sqlFullMatch = "(CASE WHEN UPPER(Name)="+upperText+" THEN 'Y' ELSE 'N' END) ";
			sql.append("SELECT AD_User_ID")
			   .append(", ").append(sqlFullMatch)
			   .append(" FROM AD_User WHERE UPPER(Name) LIKE ").append(upperPreparedSearchText);
			m_tableName = "AD_User";
			orderBySql = orderBySql+" "+sqlFullMatch+" DESC";
		}
		
		//	Predefined
		if (sql.length() > 0)
		{
			final String sqlWhereClauseLookup = getWhereClause(lookup);
			if (sqlWhereClauseLookup != null && sqlWhereClauseLookup.length() > 0)
			{
				sql.append(" AND ").append(sqlWhereClauseLookup);
			}
			sql.append(" AND IsActive='Y'");
			//	***
			log.trace(m_columnName + " (predefined) " + sql.toString());
			// metas: cg: start: task: 02491
			String finalSql = Env.getUserRolePermissions().addAccessSQL(sql.toString(),
					m_tableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
			finalSql = finalSql + orderBySql;
			return finalSql;
			// metas: cg: end: task: 02491
		}

		//	Check if it is a Table Reference
		if (lookup != null && lookup instanceof MLookup)
		{
			final MLookup mlookup = (MLookup)lookup;
			final String sqlDirect = getDirectAccessSQL_Table(text, m_columnName, mlookup);
			if (!Check.isEmpty(sqlDirect, true))
			{
				return sqlDirect;
			}
		}	//	MLookup
		
		// Assume TableDir
		return getDirectAccessSQL_TableDir(text, m_columnName, lookup);

	}	//	getDirectAccessSQL

	private static String getDirectAccessSQL_Table(final String text, final String columnName, final MLookup lookup)
	{
		final MLookupInfo lookupInfo = lookup.getLookupInfo();
		if(lookupInfo == null)
		{
			// NOTE: lookup was disposed
			return null;
		}
		final List<ILookupDisplayColumn> displayColumns = lookupInfo.getDisplayColumns();
		if (displayColumns.isEmpty())
		{
			// no display columns found
			return null;
		}

		final String preparedSearchText = DB.TO_STRING(FindHelper.prepareSearchString(text));
		final String upperPreparedSearchText = "UPPER(" + preparedSearchText + ")"; // metas: cg: task: 02491
		final String upperText = "UPPER(" + DB.TO_STRING(text) + ")";
		
		final StringBuilder sqlFullMatch = new StringBuilder();
		final StringBuilder sqlWhereClause = new StringBuilder();
		for (final ILookupDisplayColumn ldc : displayColumns)
		{
			if (!DisplayType.isText(ldc.getDisplayType()))
			{
				continue;
			}
			
			final String displayColumnName = ldc.isVirtual() ? ldc.getColumnSQL() : ldc.getColumnName();
			
			//
			// SQL for Full Matched flag
			if (sqlFullMatch.length() > 0)
			{
				sqlFullMatch.append(" OR ");
			}
			sqlFullMatch.append("UPPER(").append(displayColumnName).append(")=").append(upperText);
			//final String sqlFullMatch = "(CASE WHEN UPPER(" + displayColumnName + ")=" + upperText + " THEN 'Y' ELSE 'N' END)";
			
			//
			// SQL for Where Clause
			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append(" OR ");
			}
			sqlWhereClause.append(" UPPER(").append(displayColumnName).append(") LIKE ").append(upperPreparedSearchText); // metas: cg: task: 02491
		}
		sqlFullMatch.insert(0, "(CASE WHEN ").append(" THEN 'Y' ELSE 'N' END)");

		final String refTableName = lookupInfo.getTableName();
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ")
				.append(lookupInfo.getKeyColumn()) // 1
				.append(", ").append(sqlFullMatch) // 2
				.append(" FROM ").append(refTableName)
				.append(" WHERE ")
				.append(" IsActive='Y'")
				.append(" AND (").append(sqlWhereClause).append(")"); // metas: cg: task: 02491

		final String sqlWhereClauseLookup = getWhereClause(lookup);
		if (!Check.isEmpty(sqlWhereClauseLookup, true))
		{
			sql.append(" AND (").append(sqlWhereClauseLookup).append(")");
		}
		
		String finalSql = Env.getUserRolePermissions().addAccessSQL(sql.toString(), refTableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		finalSql = finalSql + " ORDER BY " + sqlFullMatch + " DESC";
		return finalSql;
	}

	private static String getDirectAccessSQL_TableDir(final String text, final String columnName, final Lookup lookup)
	{
		final String refKeyColumnName = MQuery.getZoomColumnName(columnName);

		final String preparedSearchText = DB.TO_STRING(FindHelper.prepareSearchString(text));
		final String upperPreparedSearchText = "UPPER(" + preparedSearchText + ")";

		// Check Well Known Columns of Table
		final String query = "SELECT t.TableName, c.ColumnName "
				+ "FROM AD_Column c "
				+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID AND t.IsView='N') "
				+ "WHERE (c.ColumnName IN ('DocumentNo', 'Value', 'Name') OR c.IsIdentifier='Y')"
				+ " AND c.AD_Reference_ID IN (10,14)"
				+ " AND EXISTS (SELECT * FROM AD_Column cc WHERE cc.AD_Table_ID=t.AD_Table_ID"
				+ " AND cc.IsKey='Y' AND cc.ColumnName=?)";

		final StringBuilder sqlFullMatch = new StringBuilder();
		final StringBuilder sqlWhereClause = new StringBuilder();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String tableName = null;
		try
		{
			pstmt = DB.prepareStatement(query, null);
			pstmt.setString(1, refKeyColumnName);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				tableName = rs.getString(1);
				final String searchColumnName = rs.getString(2);

				if (sqlFullMatch.length() > 0)
				{
					sqlFullMatch.append(" OR ");
				}
				sqlFullMatch.append("UPPER(").append(searchColumnName).append(")=UPPER(").append(DB.TO_STRING(text)).append(")");

				if (sqlWhereClause.length() > 0)
				{
					sqlWhereClause.append(" OR ");
				}
				sqlWhereClause.append("UPPER(").append(searchColumnName).append(") LIKE ").append(upperPreparedSearchText); // metas: cg: task: 02491
			}
		}
		catch (SQLException ex)
		{
			log.error(query, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		if (sqlWhereClause.length() == 0)
		{
			log.error(columnName + " (TableDir) - no standard/identifier columns");
			return "";
		}

		sqlFullMatch.insert(0, "(CASE WHEN ").append(" THEN 'Y' ELSE 'N' END)");

		//
		// metas: cg: start: task: 02491
		StringBuffer sqlDirect = new StringBuffer("SELECT ")
				.append(columnName).append(", ").append(sqlFullMatch)
				.append(" FROM ").append(tableName)
				.append(" WHERE (").append(sqlWhereClause).append(")")
				.append(" AND IsActive='Y'");
		// metas: cg: end: task: 02491

		final String sqlWhereClauseLookup = getWhereClause(lookup);
		if (sqlWhereClauseLookup != null && sqlWhereClauseLookup.length() > 0)
		{
			sqlDirect.append(" AND ").append(sqlWhereClauseLookup);
		}
		// ***
		log.trace(columnName + " (TableDir) " + sqlWhereClause.toString());

		String sqlDirectFinal = Env.getUserRolePermissions().addAccessSQL(sqlDirect.toString(), tableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		sqlDirectFinal = sqlDirectFinal + " ORDER BY " + sqlFullMatch + " DESC";
		return sqlDirectFinal;
	}

	// start: metas-2009_0021_AP1_CR52
	/**
	 * Refresh lookup value.
	 * 
	 * NOTE: usually this method is called when user Right-click->Refresh on a field.
	 */
	@Override
	public void refreshValue()
	{
		final Lookup lookup = getLookup();
		if (lookup == null)
		{
			return;
		}
		
		if (!isReadWrite())
		{
			// no need to refresh readonly lookup
			m_settingValue = true;		// disable actions
			try
			{
				// lookup.removeAllElements(); // fires #actionPerformed and it is changing the value to null
				m_lastDisplay = lookup.getDisplay(m_value);
				m_text.setText(m_lastDisplay);
				m_text.setCaretPosition(0);
			}
			finally
			{
				m_settingValue = false;
			}
		}
		else
		{
			final NamePair selectedItemOld;
			final Object selectedItemKey;
			final IValidationContext validationCtx;
			if (m_comboActive)
			{
				selectedItemOld = m_combo.getSelectedItem();
				selectedItemKey = selectedItemOld == null ? null : selectedItemOld.getID();
				validationCtx = IValidationContext.CACHED;
			}
			else
			{
				selectedItemOld = null; // N/A
				selectedItemKey = getValue();
				validationCtx = getValidationContext();
			}
			log.info(m_columnName + " #" + lookup.getSize() + ", Selected=" + selectedItemKey);

			//
			// Actually refresh the combo
			lookup.refresh();
			lookup.fillComboBox(isMandatory(), true, true, false); //  onlyValidated=true, onlyActive=true, temporary=false
			
			NamePair selectedItemNew = lookup.get(validationCtx, selectedItemKey);
			if (m_comboActive && selectedItemNew == null)
			{
				selectedItemNew = lookup.suggestValidValue(selectedItemOld);
			}
			m_lastDisplay = selectedItemNew == null ? "" : selectedItemNew.getName();
			m_text.setText(m_lastDisplay);
			m_combo.setSelectedItem(selectedItemNew);
		}

		//
		setCursor(Cursor.getDefaultCursor());
		log.info(m_columnName + " #" + lookup.getSize() + ", Selected=" + m_combo.getSelectedItem());
	}	//	actionRefresh

	/**
	 * 
	 * @return true if refreshed
	 */
	private boolean refreshLookupIfNeeded()
	{
		final Lookup lookup = getLookup();
		if (lookup == null)
		{
			return false;
		}

		//avoid repeated query
		if (lookup.isValidated() && lookup.isLoaded())
		{
			return false;
		}
		
		final Object selectedObj = lookup.getSelectedItem();
		log.info(m_columnName + " - Start    Count=" + m_combo.getItemCount() + ", Selected=" + selectedObj);

		lookup.fillComboBox(isMandatory(), true, true, false);     //  only validated & active

		log.info(m_columnName + " - Update   Count=" + m_combo.getItemCount() + ", Selected=" + selectedObj);
		lookup.setSelectedItem(selectedObj);
		log.info(m_columnName + " - Selected Count=" + m_combo.getItemCount() + ", Selected=" + lookup.getSelectedItem());
		
		return true;
	}


	/** Indicator that focus is being set       */
	private volatile boolean 	m_settingFocus = false;
	/** Indicator that Lookup has focus         */
	private volatile boolean	m_haveFocus = false;
	
	/**
	 * True if Info Window is accessible and enabled
	 */
	private boolean infoWindowEnabled = true;

	/**************************************************************************
	 *	Focus Listener for ComboBoxes with missing Validation or invalid entries
	 *	- Requery listener for updated list
	 *  @param e FocusEvent
	 */
	@Override
	public void focusGained (FocusEvent e)
	{
		if (m_combo == null || m_combo.getEditor() == null)
		{
			return;
		}
		if ((e.getSource() != m_combo && e.getSource() != m_combo.getEditor().getEditorComponent())
			|| e.isTemporary())
		{
			return;
		}
		
		if (m_haveFocus)
		{
			return;
		}
		m_haveFocus = true;     //  prevents calling focus gained twice
		
		m_settingFocus = true;  //  prevents actionPerformed
		try
		{
			final boolean refreshed = refreshLookupIfNeeded();
			if (refreshed && m_combo.isPopupVisible())
			{
				m_combo.hidePopup();
				m_combo.showPopup();
			}
		}
		finally
		{
			m_settingFocus = false;
		}
	}	//	focusGained

	/**
	 *	Reset Selection List
	 *  @param e FocusEvent
	 */
	@Override
	public void focusLost(FocusEvent e)
	{
		if (e.isTemporary())
		{
			return;
		}
		
		if (m_button == null // guarding against NPE (i.e. component was disposed in meantime)
			|| !m_button.isEnabled() )	//	set by actionButton
		{
			return;
		}
		
		final Lookup lookup = getLookup();
		if (lookup == null)
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
		
		//	Text Lost focus
		if (e.getSource() == m_text)
		{
			String text = m_text.getText();
			log.info(m_columnName + " (Text) " + m_columnName + " = " + m_value + " - " + text);
			m_haveFocus = false;
			//	Skip if empty
			if ((m_value == null
				&& m_text.getText().length() == 0))
			{
				return;
			}
			// Skip if nothing changed
			if (m_lastDisplay.equals(text))
			{
				return;
			}
			//
			actionText();	//	re-display
			return;
		}
		//	Combo lost focus
		if (e.getSource() != m_combo && e.getSource() != m_combo.getEditor().getEditorComponent())
			return;
		if (lookup.isValidated() && !lookup.hasInactive())
		{
			m_haveFocus = false;
			return;
		}
		//
		m_settingFocus = true;  //  prevents actionPerformed
		//
		log.info(m_columnName + " = " + m_combo.getSelectedItem());
		Object obj = m_combo.getSelectedItem();
		/*
		//	set original model
		if (!m_lookup.isValidated())
			m_lookup.fillComboBox(true);    //  previous selection
		*/
		//	Set value
		if (obj != null)
		{
			m_combo.setSelectedItem(obj);
			//	original model may not have item
			if (!m_combo.getSelectedItem().equals(obj))
			{
				log.debug(m_columnName + " - added to combo - " + obj);
				m_combo.addItem(obj);
				m_combo.setSelectedItem(obj);
			}
		}
	//	actionCombo(getValue());
		m_settingFocus = false;
		m_haveFocus = false;    //  can gain focus again
	}	//	focusLost

	/**
	 *  Set ToolTip
	 *  @param text tool tip text
	 */
	@Override
	public void setToolTipText(String text)
	{
		super.setToolTipText(text);
		m_button.setToolTipText(text);
		m_text.setToolTipText(text);
		m_combo.setToolTipText(text);
	}   //  setToolTipText

	/**
	 * Reset Env.TAB_INFO context variables
	 * @param columnName
	 */
	private void resetTabInfo()
	{
		String columnName = m_columnName;
		//
		// TODO : hardcoded
		final String[] infoNames;
		if ("M_Product_ID".equals(columnName))
		{
			infoNames = new String[]{"M_Product_ID","M_AttributeSetInstance_ID","M_Locator_ID","M_Lookup_ID"};
		}
		else
		{
			infoNames = new String[]{};
		}
		for (String name : infoNames)
		{
			Env.setContext(Env.getCtx(), getWindowNo(), Env.TAB_INFO, name, null);
		}
	}

	/**
	 * 	Refresh Query
	 *	@return count
	 */
	private int refresh()
	{
		final Lookup lookup = getLookup();
		if (lookup == null)
			return -1;

		//no need to refresh readonly lookup, just remove direct cache
		if (!isReadWrite()) {
			lookup.removeAllElements();
			return 0;
		}

		return lookup.refresh();
	}	//	refresh

	/**
	 * Use by vcelleditor to indicate editing is off and don't invoke databinding
	 * @param stopediting
	 */
	public void setStopEditing(boolean stopediting) {
		m_stopediting = stopediting;
	}

	private VLookupAutoCompleter lookupAutoCompleter = null;

	// metas-2009_0021_AP1_CR054: begin
	/**
	 * @return true if enabled
	 */
	public boolean enableLookupAutocomplete()
	{
		if (lookupAutoCompleter != null)
		{
			return true; // auto-complete already initialized
		}

		final Lookup lookup = getLookup();
		if (lookup instanceof MLookup && lookup.getDisplayType() == DisplayType.Search)
		{
			final MLookup mlookup = (MLookup)lookup;
			final MLookupInfo lookupInfo = mlookup.getLookupInfo();

			// FIXME: check what happens when lookup changes
			lookupAutoCompleter = new VLookupAutoCompleter(this.m_text, this, lookupInfo);

			return true;
		}

		return false;
	}
	// metas-2009_0021_AP1_CR054: end

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}
	
	// metas
	@Override
	public void addMouseListener(MouseListener l)
	{
		m_text.addMouseListener(l);
		m_button.addMouseListener(l);
		m_combo.getEditor().getEditorComponent().addMouseListener(l);	                        //	popup
	}
	
	private boolean isRowLoading()
	{
		if (m_mField == null)
		{
			return false;
		}
		final String rowLoadingStr = Env.getContext(Env.getCtx(), m_mField.getWindowNo(), m_mField.getVO().TabNo, GridTab.CTX_RowLoading);
		return "Y".equals(rowLoadingStr);
	}

	/* package */IValidationContext getValidationContext()
	{
		final GridField gridField = m_mField;
		
		// In case there is no GridField set (e.g. VLookup was created from a custom swing form)
		if (gridField == null)
		{
			return IValidationContext.DISABLED;
		}

		final IValidationContext evalCtx = Services.get(IValidationRuleFactory.class).createValidationContext(gridField);
		
		// In case grid field validation context could not be created, disable validation
		// NOTE: in most of the cases when we reach this point is when we are in a custom form which used GridFields to create the lookups
		// but those custom fields does not have a GridTab
		// e.g. de.metas.paymentallocation.form.PaymentAllocationForm
		if (evalCtx == null)
		{
			return IValidationContext.DISABLED;
		}
		return evalCtx;
	}

	@Override
	public void addFocusListener(final FocusListener l)
	{
		getEditorComponent().addFocusListener(l);
	}
	
    @Override
	public void removeFocusListener(FocusListener l)
    {
    	getEditorComponent().removeFocusListener(l);
    }

    public void setInfoWindowEnabled(final boolean enabled)
    {
    	this.infoWindowEnabled = enabled;
    	
    	if (m_button != null)
    	{
    		m_button.setVisible(enabled);
    	}
    }

	@Override
	public ICopyPasteSupportEditor getCopyPasteSupport()
	{
		return copyPasteSupport;
	}
}	//	VLookup
