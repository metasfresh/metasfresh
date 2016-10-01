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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import org.adempiere.acct.api.IAccountBL;
import org.adempiere.acct.api.IAccountDimension;
import org.adempiere.acct.api.IAccountDimensionValidator;
import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.impl.AccountDimension;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.grid.GridController;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;
import org.compiere.model.MAccountLookup;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.X_C_AcctSchema_Element;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;

/**
 * Dialog to enter Account Info
 *
 * @author Jorg Janke
 * @version $Id: VAccountDialog.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public final class VAccountDialog extends CDialog
		implements ActionListener, DataStatusListener, VetoableChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1980622319541357651L;

	private static final int ValidCombination_AD_Window_ID = 153;		// Maintain Account Combinations

	/**
	 * Mouse Listener
	 */
	class VAccountDialog_mouseAdapter extends java.awt.event.MouseAdapter
	{
		VAccountDialog_mouseAdapter(VAccountDialog adaptee)
		{
			this.adapteeRef = new WeakReference<VAccountDialog>(adaptee);
		}

		private WeakReference<VAccountDialog> adapteeRef;

		@Override
		public void mouseClicked(MouseEvent e)
		{
			// Table => select
			if (e.getSource() instanceof JTable && e.getClickCount() > 1)
			{
				final VAccountDialog adaptee = adapteeRef.get();
				if (adaptee == null)
				{
					return;
				}
				adaptee.m_changed = true;
				adaptee.dispose();
			}
		}
	}	// VAccountDialog_mouseListener

	/**
	 * Constructor
	 * 
	 * @param frame frame
	 * @param title title
	 * @param mAccount account info
	 * @param C_AcctSchema_ID as
	 */
	public VAccountDialog(
			final Frame frame,
			final String title,
			final MAccountLookup mAccount,
			final int C_AcctSchema_ID)
	{
		super(frame, title, true);
		if (log.isInfoEnabled())
		{
			log.info("C_AcctSchema_ID=" + C_AcctSchema_ID + ", C_ValidCombination_ID=" + mAccount.getC_ValidCombination_ID());
		}

		m_mAccount = mAccount;
		m_C_AcctSchema_ID = C_AcctSchema_ID;
		m_WindowNo = Env.createWindowNo(this);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try
		{
			jbInit();
		}
		catch (Exception ex)
		{
			log.error(ex.toString());
		}

		if (initAccount())
		{
			// make sure we have a decent size at least
			setMinimumSize(new Dimension(720, 350));
			
			AEnv.showCenterWindow(frame, this);
		}
		else
		{
			dispose();
		}
	}	// VLocationDialog

	// Services
	private final transient IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final transient IAccountBL accountBL = Services.get(IAccountBL.class);
	private final transient ISwingEditorFactory editorFactory = Services.get(ISwingEditorFactory.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	/** Window No */
	private int m_WindowNo;
	/**
	 * Journal Entry * private boolean m_onlyNonDocControlled = false; /** Selection changed
	 */
	protected boolean m_changed = false;

	// /** Accounting Schema */
	// private static MAcctSchema s_AcctSchema = null;
	/** MWindow for AccountCombination */
	private GridWindow m_mWindow = null;
	/** MTab for AccountCombination */
	private GridTab m_mTab = null;
	/** GridController */
	private GridController m_gridController = null;

	/** Account used */
	private MAccountLookup m_mAccount = null;
	/** Result + */
	private int m_C_ValidCombination_ID;
	/** Acct Schema */
	private final int m_C_AcctSchema_ID;
	/** Client */
	private int m_AD_Client_ID;
	/** Where clause for combination search */
	private MQuery m_query;
	/** Logger */
	private static Logger log = LogManager.getLogger(VAccountDialog.class);

	// Editors for Query
	private VEditor f_C_AcctSchema_ID;
	private VEditor f_Alias, f_Combination,
			f_AD_Org_ID, f_Account_ID, f_SubAcct_ID,
			f_M_Product_ID, f_C_BPartner_ID, f_C_Campaign_ID, f_C_LocFrom_ID, f_C_LocTo_ID,
			f_C_Project_ID, f_C_SalesRegion_ID, f_AD_OrgTrx_ID, f_C_Activity_ID,
			f_User1_ID, f_User2_ID;
	//
	private JLabel f_Description = new JLabel("", JLabel.CENTER);

	private GridBagConstraints m_gbc = new GridBagConstraints();
	private Insets m_labelInsets = new Insets(2, 15, 2, 0);		// top,left,bottom,right
	private Insets m_fieldInsets = new Insets(2, 5, 2, 10);
	private int m_line = 0;
	private boolean m_newRow = true;
	//
	private CPanel panel = new CPanel();
	private BorderLayout panelLayout = new BorderLayout();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private TitledBorder titledBorder;
	private StatusBar statusBar = new StatusBar();
	private CPanel northPanel = new CPanel();
	private CPanel parameterPanel = new CPanel();
	private GridBagLayout parameterLayout = new GridBagLayout();
	private BorderLayout northLayout = new BorderLayout();
	private JToolBar toolBar = new JToolBar();
	private CButton bRefresh = new CButton();
	private CButton bSave = new CButton();
	private CButton bIgnore = new CButton();

	/**
	 * Static component init.
	 * 
	 * <pre>
	 * -panel
	 * 		- northPanel
	 * 		- parameterPanel
	 * 		- toolBar
	 * 		- gridController
	 * 		- confirmPanel
	 * 		- statusBar
	 * </pre>
	 * 
	 * @throws Exception
	 */
	void jbInit() throws Exception
	{
		// [ 1707303 ] Account Combination Form(VAccountDialog) translation issue
		titledBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(134, 134, 134)), msgBL.getMsg(Env.getCtx(), "Parameter"));
		//
		panelLayout.setHgap(5);
		panelLayout.setVgap(5);
		northLayout.setHgap(5);
		northLayout.setVgap(5);
		//
		parameterPanel.setLayout(parameterLayout);
		parameterPanel.setBorder(titledBorder);
		northPanel.setLayout(northLayout);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBorder(null);
		toolBar.setRequestFocusEnabled(false);
		toolBar.setBorderPainted(false);
		toolBar.setMargin(new Insets(5, 5, 5, 5));
		bSave.setIcon(Images.getImageIcon2("Save24"));
		bSave.setMargin(new Insets(2, 2, 2, 2));
		bSave.setToolTipText(msgBL.getMsg(Env.getCtx(), "AccountNewUpdate"));
		bSave.addActionListener(this);
		bRefresh.setIcon(Images.getImageIcon2("Refresh24"));
		bRefresh.setMargin(new Insets(2, 2, 2, 2));
		bRefresh.setToolTipText(msgBL.getMsg(Env.getCtx(), "Refresh"));
		bRefresh.addActionListener(this);
		bIgnore.setIcon(Images.getImageIcon2("Ignore24"));
		bIgnore.setMargin(new Insets(2, 2, 2, 2));
		bIgnore.setToolTipText(msgBL.getMsg(Env.getCtx(), "Ignore"));
		bIgnore.addActionListener(this);
		//
		toolBar.addSeparator();
		toolBar.add(bRefresh, null);
		toolBar.add(bIgnore, null);
		toolBar.add(bSave, null);
		//
		getContentPane().add(panel);
		panel.setLayout(panelLayout);
		panel.add(confirmPanel, BorderLayout.SOUTH);
		panel.add(northPanel, BorderLayout.NORTH);
		northPanel.add(parameterPanel, BorderLayout.CENTER);
		northPanel.add(toolBar, BorderLayout.EAST);
		//
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	// jbInit

	/**
	 * Dyanmic Init. When a row is selected, the editor values are set (editors do not change grid)
	 * 
	 * @return true if initialized
	 */
	private boolean initAccount()
	{
		final Properties ctx = Env.getCtx();
		m_AD_Client_ID = Env.getContextAsInt(ctx, m_WindowNo, "AD_Client_ID");

		final I_C_AcctSchema s_AcctSchema = getC_AcctSchema();
		// Get AcctSchema Info
		Env.setContext(ctx, m_WindowNo, "C_AcctSchema_ID", m_C_AcctSchema_ID);

		// Model
		final GridWindowVO wVO = AEnv.getMWindowVO(m_WindowNo, ValidCombination_AD_Window_ID, 0); // AD_Menu_ID=0
		if (wVO == null)
			return false;
		m_mWindow = new GridWindow(wVO);
		m_mTab = m_mWindow.getTab(0);
		// Make sure is the tab is loaded - teo_sarca [ 1659124 ]
		if (!m_mTab.isLoadComplete())
			m_mWindow.initTab(0);

		// ParameterPanel restrictions
		m_mTab.getField("Alias").setDisplayLength(15);
		m_mTab.getField("Combination").setDisplayLength(15);
		// Grid restrictions
		m_mTab.getField("AD_Client_ID").setDisplayed(false);
		m_mTab.getField("C_AcctSchema_ID").setDisplayed(false);
		m_mTab.getField("IsActive").setDisplayed(false);
		m_mTab.getField("IsFullyQualified").setDisplayed(false);
		// don't show fields not being displayed in this environment
		for (int i = 0; i < m_mTab.getFieldCount(); i++)
		{
			GridField field = m_mTab.getField(i);
			if (!field.isDisplayed(true))      // check context
				field.setDisplayed(false);
		}

		// GridController
		m_gridController = new GridController();
		m_gridController.initGrid(m_mTab, true, m_WindowNo, null, null);
		m_gridController.setPreferredSize(new Dimension(300, 100));
		panel.add(m_gridController, BorderLayout.CENTER);

		// Prepare Parameter
		m_gbc.anchor = GridBagConstraints.NORTHWEST;
		m_gbc.gridy = 0;			// line
		m_gbc.gridx = 0;
		m_gbc.gridwidth = 1;
		m_gbc.insets = m_fieldInsets;
		m_gbc.fill = GridBagConstraints.HORIZONTAL;
		m_gbc.weightx = 0;
		m_gbc.weighty = 0;

		// Alias
		if (s_AcctSchema.isHasAlias())
		{
			GridField alias = m_mTab.getField("Alias");
			f_Alias = editorFactory.getEditor(m_mTab, alias, false);
			addLine(alias, f_Alias, false);
		}	// Alias

		// Combination
		GridField combination = m_mTab.getField("Combination");
		f_Combination = editorFactory.getEditor(m_mTab, combination, false);
		addLine(combination, f_Combination, false);
		m_newRow = true;

		// C_AcctSchema_ID
		// NOTE: if we are not doing this, all validation rules which are depending on C_AcctSchema_ID will fail because GridRowCtx is involved
		{
			final GridField field = m_mTab.getField(I_C_ValidCombination.COLUMNNAME_C_AcctSchema_ID);
			f_C_AcctSchema_ID = editorFactory.getEditor(m_mTab, field, false);
			f_C_AcctSchema_ID.setValue(getC_AcctSchema_ID());
			// NOTE: we are not adding it to the panel
		}

		/**
		 * Create Fields in Element Order
		 */
		for (final I_C_AcctSchema_Element ase : getAcctSchemaElements())
		{
			final String type = ase.getElementType();
			final boolean isMandatory = ase.isMandatory();
			//
			if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Organization))
			{
				GridField field = m_mTab.getField("AD_Org_ID");
				f_AD_Org_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_AD_Org_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Account))
			{
				GridField field = m_mTab.getField("Account_ID");
				f_Account_ID = editorFactory.getEditor(m_mTab, field, false);
				// ((VLookup)f_Account_ID).setWidth(400);
				addLine(field, f_Account_ID, isMandatory);
				f_Account_ID.addVetoableChangeListener(this);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SubAccount))
			{
				GridField field = m_mTab.getField("C_SubAcct_ID");
				f_SubAcct_ID = editorFactory.getEditor(m_mTab, field, false);
				// ((VLookup)f_SubAcct_ID).setWidth(400);
				addLine(field, f_SubAcct_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Product))
			{
				GridField field = m_mTab.getField("M_Product_ID");
				f_M_Product_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_M_Product_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner))
			{
				GridField field = m_mTab.getField("C_BPartner_ID");
				f_C_BPartner_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_C_BPartner_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign))
			{
				GridField field = m_mTab.getField("C_Campaign_ID");
				f_C_Campaign_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_C_Campaign_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom))
			{
				GridField field = m_mTab.getField("C_LocFrom_ID");
				f_C_LocFrom_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_C_LocFrom_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo))
			{
				GridField field = m_mTab.getField("C_LocTo_ID");
				f_C_LocTo_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_C_LocTo_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Project))
			{
				GridField field = m_mTab.getField("C_Project_ID");
				f_C_Project_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_C_Project_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion))
			{
				GridField field = m_mTab.getField("C_SalesRegion_ID");
				f_C_SalesRegion_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_C_SalesRegion_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx))
			{
				GridField field = m_mTab.getField("AD_OrgTrx_ID");
				f_AD_OrgTrx_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_AD_OrgTrx_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Activity))
			{
				GridField field = m_mTab.getField("C_Activity_ID");
				f_C_Activity_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_C_Activity_ID, isMandatory);
			}
			// User1
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList1))
			{
				GridField field = m_mTab.getField("User1_ID");
				f_User1_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_User1_ID, isMandatory);
			}
			else if (type.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserList2))
			{
				GridField field = m_mTab.getField("User2_ID");
				f_User2_ID = editorFactory.getEditor(m_mTab, field, false);
				addLine(field, f_User2_ID, isMandatory);
			}
		}	// Create Fields in Element Order

		// Add description
		m_newRow = true;
		m_gbc.gridy = m_line++;
		m_gbc.gridx = 0;
		m_gbc.gridwidth = 4;
		m_gbc.insets = new Insets(5, 15, 2, 0);		// top,left,bottom,right
		m_gbc.fill = GridBagConstraints.HORIZONTAL;
		f_Description.setFont(f_Description.getFont().deriveFont(Font.ITALIC));
		parameterPanel.add(f_Description, m_gbc);

		// Finish
		m_query = new MQuery();
		m_query.addRestriction("C_AcctSchema_ID", Operator.EQUAL, m_C_AcctSchema_ID);
		m_query.addRestriction("IsFullyQualified", Operator.EQUAL, "Y");
		if (m_mAccount.getC_ValidCombination_ID() <= 0)
		{
			m_mTab.setQuery(MQuery.getNoRecordQuery());
		}
		else
		{
			final MQuery query = new MQuery();
			query.addRestriction("C_AcctSchema_ID", Operator.EQUAL, m_C_AcctSchema_ID);
			query.addRestriction("C_ValidCombination_ID", Operator.EQUAL, m_mAccount.getC_ValidCombination_ID());
			m_mTab.setQuery(query);
		}
		m_mTab.query(false);
		m_gridController.getTable().addMouseListener(new VAccountDialog_mouseAdapter(this));
		m_gridController.addDataStatusListener(this);

		statusBar.setStatusLine(s_AcctSchema.toString());
		statusBar.setStatusDB("?");

		// Initial value
		if (m_mAccount.getC_ValidCombination_ID() > 0)
		{
			m_mTab.navigate(0);
		}

		log.info("fini");
		return true;
	}	// initAccount

	/**
	 * Add Editor to parameterPanel alernative right/left depending on m_newRow. Field Value changes update Editors
	 * 
	 * @param field field
	 * @param editor editor
	 * @param mandatory mandatory
	 */
	private void addLine(final GridField field, final VEditor editor, final boolean mandatory)
	{
		log.debug("Field=" + field);
		final JLabel label = editorFactory.getLabel(field);
		label.setLabelFor(editorFactory.getEditorComponent(editor));
		editor.setReadWrite(true);
		editor.setMandatory(mandatory);
		// MField => VEditor
		field.addPropertyChangeListener(editor);

		// label
		if (m_newRow)
		{
			m_gbc.gridy = m_line++;
			m_gbc.gridx = 0;
		}
		else
			m_gbc.gridx = 2;
		m_gbc.insets = m_labelInsets;
		m_gbc.fill = GridBagConstraints.HORIZONTAL;
		m_gbc.weightx = 0;
		parameterPanel.add(label, m_gbc);

		// Field
		if (m_newRow)
			m_gbc.gridx = 1;
		else
			m_gbc.gridx = 3;
		m_gbc.insets = m_fieldInsets;
		m_gbc.fill = GridBagConstraints.HORIZONTAL;
		m_gbc.weightx = 1;
		parameterPanel.add(editorFactory.getEditorComponent(editor), m_gbc);
		//
		m_newRow = !m_newRow;
	}	// addLine

	private void loadInfo(final I_C_ValidCombination account)
	{
		if (f_C_AcctSchema_ID != null)
		{
			f_C_AcctSchema_ID.setValue(account.getC_AcctSchema_ID());
		}

		if (f_Alias != null)
			f_Alias.setValue(account.getAlias());

		if (f_Combination != null)
			f_Combination.setValue(account.getCombination());
		//
		loadInfoOf(account.getAD_Org_ID(), f_AD_Org_ID);
		loadInfoOf(account.getAccount_ID(), f_Account_ID);
		loadInfoOf(account.getC_SubAcct_ID(), f_SubAcct_ID);
		//
		loadInfoOf(account.getM_Product_ID(), f_M_Product_ID);
		loadInfoOf(account.getC_BPartner_ID(), f_C_BPartner_ID);
		loadInfoOf(account.getC_Campaign_ID(), f_C_Campaign_ID);
		loadInfoOf(account.getC_LocFrom_ID(), f_C_LocFrom_ID);
		loadInfoOf(account.getC_LocTo_ID(), f_C_LocTo_ID);
		loadInfoOf(account.getC_Project_ID(), f_C_Project_ID);
		loadInfoOf(account.getC_SalesRegion_ID(), f_C_SalesRegion_ID);
		loadInfoOf(account.getAD_OrgTrx_ID(), f_AD_OrgTrx_ID);
		loadInfoOf(account.getC_AcctSchema_ID(), f_C_Activity_ID);
		loadInfoOf(account.getUser1_ID(), f_User1_ID);
		loadInfoOf(account.getUser2_ID(), f_User2_ID);
		//
		if (f_Description != null)
			f_Description.setText(account.getDescription());
	}

	private void loadInfoOf(final int value, VEditor editor)
	{
		if (editor == null)
		{
			return;
		}

		final boolean isNull;
		if (editor == f_AD_Org_ID || editor == f_AD_OrgTrx_ID)
		{
			isNull = value < 0;
		}
		else
		{
			isNull = value <= 0;
		}

		if (isNull)
			editor.setValue(null);
		else
			editor.setValue(value);
	}	// loadInfoOf

	/**
	 * dispose
	 */
	@Override
	public void dispose()
	{
		saveSelection();

		// GridController
		if (m_gridController != null)
		{
			m_gridController.dispose();
			m_gridController = null;
		}

		// Model
		m_mTab = null;
		if (m_mWindow != null)
		{
			m_mWindow.dispose();
			m_mWindow = null;
		}

		removeAll();
		Env.clearWinContext(m_WindowNo);
		super.dispose();
	}	// dispose

	/**
	 * Save Selection
	 */
	private void saveSelection()
	{
		if (m_changed && m_gridController != null)
		{
			int row = m_gridController.getTable().getSelectedRow();
			if (row >= 0)
				m_C_ValidCombination_ID = ((Integer)m_mTab.getValue(row, "C_ValidCombination_ID")).intValue();
			log.info("(" + row + ") - " + m_C_ValidCombination_ID);
		}
	}	// saveSelection

	/**
	 * ActionListener
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (Exception ex)
		{
			clientUI.warn(m_WindowNo, ex);
		}
	}

	private void actionPerformed0(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			m_changed = true;
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			m_changed = false;
			dispose();
		}
		//
		else if (e.getSource() == bSave)
		{
			action_Save();
		}
		else if (e.getSource() == bIgnore)
		{
			action_Ignore();
		}
		// all other
		else
		{
			action_Find(true);
		}

	}	// actionPerformed

	/**
	 * Status Change Listener
	 * 
	 * @param e event
	 */
	@Override
	public void dataStatusChanged(DataStatusEvent e)
	{
		log.info(e.toString());
		String info = (String)m_mTab.getValue("Description");
		f_Description.setText(info);
		//
		// log.info( ">> Field=" + m_mTab.getValue("AD_Org_ID"),
		// "Editor=" + f_AD_Org_ID.getValue());
		// if (f_AD_Org_ID.getValue() == null)
		// f_AD_Org_ID.setValue(m_mTab.getValue("AD_Org_ID"));
	}	// statusChanged

	/**
	 * Action Find. - create where clause - query database
	 * 
	 * @param includeAliasCombination include alias combination
	 */
	private void action_Find(final boolean includeAliasCombination)
	{
		log.info("");

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			final MQuery query = createMQueryFromFields(includeAliasCombination);

			// Query
			m_mTab.setQuery(query);
			m_mTab.query(false);
			statusBar.setStatusDB(String.valueOf(m_mTab.getRowCount()));
		}
		finally
		{
			setCursor(Cursor.getDefaultCursor());
		}
	}	// action_Find

	private MQuery createMQueryFromFields(final boolean includeAliasCombination)
	{
		// Create where Clause
		final MQuery query;
		if (m_query != null)
		{
			query = m_query.deepCopy();
		}
		else
		{
			query = new MQuery();
		}

		// Alias
		if (includeAliasCombination && f_Alias != null && f_Alias.getValue().toString().length() > 0)
		{
			String value = f_Alias.getValue().toString().toUpperCase();
			if (!value.endsWith("%"))
				value += "%";
			query.addRestriction("UPPER(Alias)", Operator.LIKE, value);
		}
		// Combination (mandatory)
		if (includeAliasCombination && f_Combination.getValue().toString().length() > 0)
		{
			String value = f_Combination.getValue().toString().toUpperCase();
			if (!value.endsWith("%"))
				value += "%";
			query.addRestriction("UPPER(Combination)", Operator.LIKE, value);
		}
		// Org (mandatory)
		if (f_AD_Org_ID != null && f_AD_Org_ID.getValue() != null)
			query.addRestriction("AD_Org_ID", Operator.EQUAL, f_AD_Org_ID.getValue());
		// Account (mandatory)
		if (f_Account_ID != null && f_Account_ID.getValue() != null)
			query.addRestriction("Account_ID", Operator.EQUAL, f_Account_ID.getValue());
		if (f_SubAcct_ID != null && f_SubAcct_ID.getValue() != null)
			query.addRestriction("C_SubAcct_ID", Operator.EQUAL, f_SubAcct_ID.getValue());

		// Product
		if (f_M_Product_ID != null && f_M_Product_ID.getValue() != null)
			query.addRestriction("M_Product_ID", Operator.EQUAL, f_M_Product_ID.getValue());
		// BPartner
		if (f_C_BPartner_ID != null && f_C_BPartner_ID.getValue() != null)
			query.addRestriction("C_BPartner_ID", Operator.EQUAL, f_C_BPartner_ID.getValue());
		// Campaign
		if (f_C_Campaign_ID != null && f_C_Campaign_ID.getValue() != null)
			query.addRestriction("C_Campaign_ID", Operator.EQUAL, f_C_Campaign_ID.getValue());
		// Loc From
		if (f_C_LocFrom_ID != null && f_C_LocFrom_ID.getValue() != null)
			query.addRestriction("C_LocFrom_ID", Operator.EQUAL, f_C_LocFrom_ID.getValue());
		// Loc To
		if (f_C_LocTo_ID != null && f_C_LocTo_ID.getValue() != null)
			query.addRestriction("C_LocTo_ID", Operator.EQUAL, f_C_LocTo_ID.getValue());
		// Project
		if (f_C_Project_ID != null && f_C_Project_ID.getValue() != null)
			query.addRestriction("C_Project_ID", Operator.EQUAL, f_C_Project_ID.getValue());
		// SRegion
		if (f_C_SalesRegion_ID != null && f_C_SalesRegion_ID.getValue() != null)
			query.addRestriction("C_SalesRegion_ID", Operator.EQUAL, f_C_SalesRegion_ID.getValue());
		// Org Trx
		if (f_AD_OrgTrx_ID != null && f_AD_OrgTrx_ID.getValue() != null)
			query.addRestriction("AD_OrgTrx_ID", Operator.EQUAL, f_AD_OrgTrx_ID.getValue());
		// Activity
		if (f_C_Activity_ID != null && f_C_Activity_ID.getValue() != null)
			query.addRestriction("C_Activity_ID", Operator.EQUAL, f_C_Activity_ID.getValue());
		// User 1
		if (f_User1_ID != null && f_User1_ID.getValue() != null)
			query.addRestriction("User1_ID", Operator.EQUAL, f_User1_ID.getValue());
		// User 2
		if (f_User2_ID != null && f_User2_ID.getValue() != null)
			query.addRestriction("User2_ID", Operator.EQUAL, f_User2_ID.getValue());
		return query;
	}

	/**
	 * Create/Save Account
	 */
	private void action_Save()
	{
		//
		// Get/Create new valid combination/account
		final I_C_ValidCombination account = getCreateAccountFromFields();

		// Show Info
		if (account != null && account.getC_ValidCombination_ID() > 0)
		{
			loadInfo(account);
		}

		action_Find(false);
	}	// action_Save

	private IAccountDimension createAccountDimensionFromFields()
	{
		final I_C_AcctSchema acctSchema = getC_AcctSchema();

		final AccountDimension.Builder accountDimension = AccountDimension.builder();
		accountDimension.setAD_Client_ID(m_AD_Client_ID);
		accountDimension.setC_AcctSchema_ID(acctSchema.getC_AcctSchema_ID());

		//
		// Get fields values
		if (f_Alias != null && f_Alias.getValue() != null)
		{
			final String Alias = f_Alias.getValue().toString();
			accountDimension.setAlias(Alias);
		}
		if (f_AD_Org_ID != null && f_AD_Org_ID.getValue() != null)
		{
			final int AD_Org_ID = ((Integer)f_AD_Org_ID.getValue()).intValue();
			accountDimension.setAD_Org_ID(AD_Org_ID);
		}
		if (f_Account_ID != null && f_Account_ID.getValue() != null)
		{
			final int elementValueId = ((Integer)f_Account_ID.getValue()).intValue();
			accountDimension.setC_ElementValue_ID(elementValueId);
		}
		if (f_SubAcct_ID != null && f_SubAcct_ID.getValue() != null)
		{
			final int C_SubAcct_ID = ((Integer)f_SubAcct_ID.getValue()).intValue();
			accountDimension.setC_SubAcct_ID(C_SubAcct_ID);
		}
		if (f_M_Product_ID != null && f_M_Product_ID.getValue() != null)
		{
			int M_Product_ID = ((Integer)f_M_Product_ID.getValue()).intValue();
			accountDimension.setM_Product_ID(M_Product_ID);
		}
		if (f_C_BPartner_ID != null && f_C_BPartner_ID.getValue() != null)
		{
			int C_BPartner_ID = ((Integer)f_C_BPartner_ID.getValue()).intValue();
			accountDimension.setC_BPartner_ID(C_BPartner_ID);
		}
		if (f_AD_OrgTrx_ID != null && f_AD_OrgTrx_ID.getValue() != null)
		{
			int AD_OrgTrx_ID = ((Integer)f_AD_OrgTrx_ID.getValue()).intValue();
			accountDimension.setAD_OrgTrx_ID(AD_OrgTrx_ID);
		}
		if (f_C_LocFrom_ID != null && f_C_LocFrom_ID.getValue() != null)
		{
			int C_LocFrom_ID = ((Integer)f_C_LocFrom_ID.getValue()).intValue();
			accountDimension.setC_LocFrom_ID(C_LocFrom_ID);
		}
		if (f_C_LocTo_ID != null && f_C_LocTo_ID.getValue() != null)
		{
			int C_LocTo_ID = ((Integer)f_C_LocTo_ID.getValue()).intValue();
			accountDimension.setC_LocTo_ID(C_LocTo_ID);
		}
		if (f_C_SalesRegion_ID != null && f_C_SalesRegion_ID.getValue() != null)
		{
			int C_SRegion_ID = ((Integer)f_C_SalesRegion_ID.getValue()).intValue();
			accountDimension.setC_SalesRegion_ID(C_SRegion_ID);
		}
		if (f_C_Project_ID != null && f_C_Project_ID.getValue() != null)
		{
			int C_Project_ID = ((Integer)f_C_Project_ID.getValue()).intValue();
			accountDimension.setC_Project_ID(C_Project_ID);
		}
		if (f_C_Campaign_ID != null && f_C_Campaign_ID.getValue() != null)
		{
			int C_Campaign_ID = ((Integer)f_C_Campaign_ID.getValue()).intValue();
			accountDimension.setC_Campaign_ID(C_Campaign_ID);
		}
		if (f_C_Activity_ID != null && f_C_Activity_ID.getValue() != null)
		{
			int C_Activity_ID = ((Integer)f_C_Activity_ID.getValue()).intValue();
			accountDimension.setC_Activity_ID(C_Activity_ID);
		}
		if (f_User1_ID != null && f_User1_ID.getValue() != null)
		{
			int User1_ID = ((Integer)f_User1_ID.getValue()).intValue();
			accountDimension.setUser1_ID(User1_ID);
		}
		if (f_User2_ID != null && f_User2_ID.getValue() != null)
		{
			int User2_ID = ((Integer)f_User2_ID.getValue()).intValue();
			accountDimension.setUser2_ID(User2_ID);
		}

		return accountDimension.build();
	}

	private MAccount getCreateAccountFromFields()
	{
		//
		// Get Account Dimension from fields
		final IAccountDimension accountDimension = createAccountDimensionFromFields();

		//
		// Validate it
		final I_C_AcctSchema acctSchema = getC_AcctSchema();
		final IAccountDimensionValidator validator = accountBL.createAccountDimensionValidator(acctSchema);
		validator.setAcctSchemaElements(getAcctSchemaElements());
		validator.validate(accountDimension);

		//
		// Get/create Account
		final MAccount acct = MAccount.get(getCtx(), accountDimension);
		Check.assumeNotNull(acct, "acct not null"); // shall not happen

		// Update Account with optional Alias
		if (!Check.isEmpty(accountDimension.getAlias(), true))
		{
			acct.setAlias(accountDimension.getAlias().trim());
		}

		// Update Value and Description
		accountBL.setValueDescription(acct);

		// Make sure is saved
		InterfaceWrapperHelper.save(acct);

		return acct;
	}

	/**
	 * Ignore
	 */
	private void action_Ignore()
	{
		if (f_C_AcctSchema_ID != null)
			f_C_AcctSchema_ID.setValue(getC_AcctSchema_ID());
		if (f_Alias != null)
			f_Alias.setValue("");
		if (f_Combination != null)
			f_Combination.setValue("");
		if (f_Description != null)
			f_Description.setText("");
		//
		// Org
		if (f_AD_Org_ID != null)
			f_AD_Org_ID.setValue(null);
		// Account (mandatory)
		if (f_Account_ID != null)
			f_Account_ID.setValue(null);
		if (f_SubAcct_ID != null)
			f_SubAcct_ID.setValue(null);

		// Product
		if (f_M_Product_ID != null)
			f_M_Product_ID.setValue(null);
		// BPartner
		if (f_C_BPartner_ID != null)
			f_C_BPartner_ID.setValue(null);
		// Campaign
		if (f_C_Campaign_ID != null)
			f_C_Campaign_ID.setValue(null);
		// Loc From
		if (f_C_LocFrom_ID != null)
			f_C_LocFrom_ID.setValue(null);
		// Loc To
		if (f_C_LocTo_ID != null)
			f_C_LocTo_ID.setValue(null);
		// Project
		if (f_C_Project_ID != null)
			f_C_Project_ID.setValue(null);
		// SRegion
		if (f_C_SalesRegion_ID != null)
			f_C_SalesRegion_ID.setValue(null);
		// Org Trx
		if (f_AD_OrgTrx_ID != null)
			f_AD_OrgTrx_ID.setValue(null);
		// Activity
		if (f_C_Activity_ID != null)
			f_C_Activity_ID.setValue(null);
		// User 1
		if (f_User1_ID != null)
			f_User1_ID.setValue(null);
		// User 2
		if (f_User2_ID != null)
			f_User2_ID.setValue(null);
	}	// action_Ignore

	/**
	 * Get selected account
	 * 
	 * @return account
	 */
	public Integer getValue()
	{
		if (!m_changed || m_C_ValidCombination_ID <= 0)
			return null;
		return m_C_ValidCombination_ID;
	}

	/**
	 * VetoableChange - Account Changed
	 *
	 * @param evt event
	 * @throws PropertyVetoException
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException
	{
		Object newValue = evt.getNewValue();
		if (newValue instanceof Integer)
		{
			Env.setContext(getCtx(), m_WindowNo, "Account_ID", ((Integer)newValue).intValue());
		}
	}	// vetoableChange

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private final int getC_AcctSchema_ID()
	{
		return m_C_AcctSchema_ID;
	}

	private final I_C_AcctSchema getC_AcctSchema()
	{
		if (_acctSchema == null || _acctSchema.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
		{
			_acctSchema = new MAcctSchema(getCtx(), m_C_AcctSchema_ID, ITrx.TRXNAME_None);
		}
		Check.assumeNotNull(_acctSchema, "acctSchema not null");
		return _acctSchema;
	}

	private I_C_AcctSchema _acctSchema;

	private List<I_C_AcctSchema_Element> getAcctSchemaElements()
	{
		final I_C_AcctSchema as = getC_AcctSchema();
		return acctSchemaDAO.retrieveSchemaElementsDisplayedInEditor(as);
	}

}	// VAccountDialog
