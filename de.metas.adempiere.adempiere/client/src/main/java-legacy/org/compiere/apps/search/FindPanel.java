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
package org.compiere.apps.search;

import static org.compiere.apps.search.FindPanelSearchField.MAX_TEXT_FIELD_COLUMNS;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RootPaneContainer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.images.Images;
import org.adempiere.plaf.VEditorUI;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.search.IUserQueryRestriction.Join;
import org.compiere.grid.GridController;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabMaxRows;
import org.compiere.model.GridTabMaxRowsRestrictionChecker;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MTable;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextField;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * Find/Search Records. Based on AD_Find for persistency, query is build to restrict info
 * 
 * @author Jorg Janke
 * @version $Id: Find.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro <li>BF [ 2564070 ] Saving user queries can produce unnecessary db errors
 */
public final class FindPanel extends CPanel implements ActionListener
{
	private static final long serialVersionUID = 6414604433732835410L;

	public static final FindPanelBuilder builder()
	{
		return new FindPanelBuilder();
	}

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);
	private static final transient Logger log = LogManager.getLogger(FindPanel.class);

	FindPanel(final FindPanelBuilder builder)
	{
		super();
		//
		gridController = builder.getGridController();
		gridTab = builder.getGridTab();
		m_targetWindowNo = builder.getTargetWindowNo();
		final int adTabId = builder.getAD_Tab_ID();
		m_targetTabNo = builder.getTargetTabNo(); // metas-2009_0021_AP1_G113
		final int adTableId = builder.getAD_Table_ID();
		m_tableName = builder.getTableName();
		m_whereExtended = builder.getWhereExtended();
		_columnName2searchFields = FindPanelSearchField.createMapIndexedByColumnName(builder.getFindFields());
		userQueriesRepository = UserQueryRepository.builder()
				.setSearchFields(_columnName2searchFields.values())
				.setAD_Tab_ID(adTabId)
				.setAD_Table_ID(adTableId)
				.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()))
				.build();
		drawSmallButtons = builder.isSmall();
		embedded = builder.isEmbedded();

		this.role = Env.getUserRolePermissions();
		this.maxRowsChecker = builder.newGridTabMaxRowsRestrictionChecker()
				.setUserRolePermissions(role)
				.build();

		//
		// Create the query
		final MQuery queryInitial = builder.getQuery();
		m_query = createNewQuery(queryInitial);
		
		// Required for Column Validation
		Env.setContext(Env.getCtx(), m_targetWindowNo, "Find_Table_ID", adTableId);
		// Context for Advanced Search Grid is WINDOW_FIND
		Env.setContext(Env.getCtx(), Env.WINDOW_FIND, "Find_Table_ID", adTableId);
		//
		try
		{
			jbInit();
			initFind();
			initFindAdvanced();
			if (m_total < builder.getMinRecords())
			{
				dispose();
				return;
			}
		}
		catch (Exception e)
		{
			log.error("Find", e);
		}

		setDefaultButton();

		//
		if (queryInitial != null)
		{
			parseQuery(queryInitial);
		}

		if (builder.isHideStatusBar())
		{
			southPanel.setVisible(false);
		}
	} // Find

	/**
	 * Constant returned by methods like {@link #getTotalRecords()} or {@link #getNoOfRecords(MQuery, boolean)}, when we are dealing with high volume tables and we did not count them (for performance
	 * purpose) but we assume they are big.
	 */
	static final int TOTAL_NotAvailable = Integer.MAX_VALUE;

	private final GridController gridController;
	private final GridTab gridTab;
	/** Target Window No */
	private final int m_targetWindowNo;
	private final int m_targetTabNo; // metas-2009_0021_AP1_G113
	/** Table Name */
	private final String m_tableName;
	/** Where */
	private final String m_whereExtended;
	/** Available search Fields */
	private final Map<String, FindPanelSearchField> _columnName2searchFields;
	private UserQueryRepository userQueriesRepository;
	private final IUserRolePermissions role;
	private final GridTabMaxRowsRestrictionChecker maxRowsChecker;
	/** Resulting query */
	private MQuery m_query = null;
	/** Is cancel ? */
	private boolean m_isCancel = false; // teo_sarca [ 1708717 ]
	/** Find panel's action listener */
	private FindPanelActionListener actionListener = FindPanelActionListener.NULL;

	/** Number of records */
	private int m_total;
	//
	private boolean hasValue = false;
	private boolean hasDocNo = false;
	private boolean hasName = false;
	private boolean hasDescription = false;
	private boolean hasSuche = false;
	private final boolean drawSmallButtons;
	/** true if the find panel will be embedded in the window */
	private final boolean embedded;

	private final int m_sColumnMax = Services.get(ISysConfigBL.class).getIntValue(
			"org.compiere.apps.search.FindPanel.MaxColumns",
			4,
			Env.getAD_Client_ID(Env.getCtx()));
	private Component m_editorFirst = null; // metas-2009_0021_AP1_CR064: set

	/** List of VEditors in simple search panel */
	private final List<VEditor> m_sEditors = new ArrayList<VEditor>();

	/** For Grid Controller */
	private static final int TABNO = 99;

	//
	// UI
	private final CPanel mainPanel = this;
	private final CPanel southPanel = new CPanel();
	private final StatusBar statusBar = new StatusBar();
	private final CTabbedPane tabbedPane = new CTabbedPane();
	private final CPanel advancedPanel = new CPanel();
	private final CButton bIgnore = new CButton();
	private final CComboBox<String> fQueryName = new CComboBox<>();
	private final CButton bSave = new CButton();
	private final CButton bNew = new CButton();
	private final CButton bDelete = new CButton();
	/** Confirm panel (simple view) */
	private ConfirmPanel confirmPanel;
	private final CPanel simplePanelContent = new CPanel();
	private final CPanel simplePanel = new CPanel();
	private boolean simplePanelDisabled = false;
	private final CLabel valueLabel = new CLabel();
	private final CLabel nameLabel = new CLabel();
	private final CLabel descriptionLabel = new CLabel();
	private final CLabel searchLabel = new CLabel();
	private final CTextField valueField = new CTextField();
	private final CTextField nameField = new CTextField();
	private final CTextField descriptionField = new CTextField();
	private final CTextField searchField = new CTextField();
	private final CLabel docNoLabel = new CLabel();
	private final CTextField docNoField = new CTextField();
	private final FindAdvancedSearchTable advancedTable = new FindAdvancedSearchTable();

	/**
	 * Static Init.
	 */
	private void jbInit() throws Exception
	{
		final Properties ctx = Env.getCtx();

		//
		// Confirm panel
		{
			final GridTab gridTab = getGridTab();
			final boolean withNewButton = !embedded && gridTab != null && !gridTab.isReadOnly() && gridTab.isInsertRecord();

			confirmPanel = ConfirmPanel.builder()
					.withCancelButton(!embedded)
					.withNewButton(withNewButton)
					.withResetButton(true)
					.withoutText()
					.withSmallButtons(drawSmallButtons)
					.build();
			confirmPanel.getOKButton().setToolTipText(msgBL.getMsg(ctx, "QueryEnter"));
			confirmPanel.getOKButton().setIcon(getIcon("Ok"));
			confirmPanel.getCancelButton().setToolTipText(msgBL.getMsg(ctx, "QueryCancel"));
			confirmPanel.getResetButton().setIcon(getIcon("Reset"));
			confirmPanel.setActionListener(this);
		}
		
		//
		// Tabbed pane
		{
			tabbedPane.setHideIfOneTab(true);
		}

		//
		// Simple panel standard fields
		{
			valueLabel.setLabelFor(valueField);
			valueLabel.setText(msgBL.translate(ctx, "Value"));
			valueField.setColumns(MAX_TEXT_FIELD_COLUMNS);
			
			nameLabel.setLabelFor(nameField);
			nameLabel.setText(msgBL.translate(ctx, "Name"));
			nameField.setColumns(MAX_TEXT_FIELD_COLUMNS);
			
			descriptionLabel.setLabelFor(descriptionField);
			descriptionLabel.setText(msgBL.translate(ctx, "Description"));
			descriptionField.setColumns(MAX_TEXT_FIELD_COLUMNS);
			
			searchLabel.setLabelFor(searchField);
			searchLabel.setText(msgBL.translate(ctx, "search"));
			searchField.setColumns(MAX_TEXT_FIELD_COLUMNS);
			
			docNoLabel.setLabelFor(docNoField);
			docNoLabel.setText(msgBL.translate(ctx, "DocumentNo"));
			docNoField.setColumns(MAX_TEXT_FIELD_COLUMNS);
		}

		//
		// Advanced panel toolbar
		final JToolBar toolBar = new JToolBar();
		{
			final int height = VEditorUI.getVEditorHeight();
			bIgnore.setIcon(getIcon("Ignore"));
			bIgnore.setToolTipText(msgBL.getMsg(ctx, "Ignore"));
			bIgnore.setPreferredSize(new Dimension(height, height));
			bIgnore.addActionListener(this);

			fQueryName.enableAutoCompletion()
					.setStrictMode(false);
			fQueryName.setToolTipText(msgBL.getMsg(ctx, "QueryName"));
			fQueryName.setEditable(true);
			fQueryName.addActionListener(this);
			
			bSave.setIcon(getIcon("Save"));
			bSave.setToolTipText(msgBL.getMsg(ctx, "Save"));
			bSave.setPreferredSize(new Dimension(height, height));
			bSave.addActionListener(this);
			
			bNew.setIcon(getIcon("New"));
			bNew.setToolTipText(msgBL.getMsg(ctx, "New"));
			bNew.setPreferredSize(new Dimension(height, height));
			bNew.addActionListener(this);
			
			bDelete.setIcon(getIcon("Delete"));
			bDelete.setToolTipText(msgBL.getMsg(ctx, "Delete"));
			bDelete.setPreferredSize(new Dimension(height, height));
			bDelete.addActionListener(this);
			//
			toolBar.add(bIgnore, null);
			toolBar.addSeparator();
			toolBar.add(bNew, null);
			toolBar.add(bDelete, null);
			toolBar.add(fQueryName, null);
			toolBar.add(bSave, null);
		}

		//
		// Layout
		{
			mainPanel.setLayout(new BorderLayout());

			//
			// Center: Simple / Advanced panels (as a tabbed pane)
			{
				mainPanel.add(tabbedPane, BorderLayout.CENTER);

				// Simple search panel
				{
					final LC layoutConstraints = new LC()
							.fillX()// fill the whole available width in the container
//							.debug(1000)
							.wrapAfter((m_sColumnMax <= 0 ? 4 : m_sColumnMax) * 2);

					final MigLayout simplePanelContentLayout = new MigLayout(layoutConstraints);
					simplePanelContent.setLayout(simplePanelContentLayout);
					simplePanelContent.setToolTipText(msgBL.getMsg(ctx, "FindTip"));

					simplePanel.setLayout(new BorderLayout());
					simplePanel.add(simplePanelContent, BorderLayout.CENTER);
					tabbedPane.addTab(msgBL.getMsg(ctx, "Find"), simplePanel);
				}

				// Advanced search panel
				{
					final CScrollPane advancedTableScrollPane = new CScrollPane();
					advancedTableScrollPane.setBorder(BorderFactory.createEmptyBorder()); // no border
					advancedTableScrollPane.setViewportView(advancedTable);

					advancedPanel.setLayout(new BorderLayout());
					advancedPanel.add(toolBar, BorderLayout.NORTH);
					advancedPanel.add(advancedTableScrollPane, BorderLayout.CENTER);
					
					tabbedPane.addTab(msgBL.getMsg(ctx, "Advanced"), advancedPanel);
				}
			}
			
			//
			// South: status bar
			{
				statusBar.removeBorders();
				
				southPanel.setLayout(new BorderLayout());
				southPanel.add(confirmPanel, BorderLayout.CENTER);
				southPanel.add(statusBar, BorderLayout.SOUTH);
				mainPanel.add(southPanel, BorderLayout.SOUTH);
			}
		}
	} // jbInit

	/**
	 * Dynamic Init.6 Set up GridController
	 */
	private void initFind()
	{
		// Get Info from target Tab
		for (final IUserQueryField searchField : getAvailableSearchFields())
		{
			final String columnName = searchField.getColumnName();

			if (columnName.equals("Value"))
				hasValue = true;
			else if (columnName.equals("Name"))
				hasName = true;
			else if (columnName.equals("DocumentNo"))
				hasDocNo = true;
			// metas-2009_0021_AP1_CR064: change column "description" to be not automatically a search field
			// else if (columnName.equals("Description"))
			// hasDescription = true;
			// else if (mField.isSelectionColumn())
			// addSelectionColumn (mField);
			// else if (columnName.indexOf("Name") != -1)
			// addSelectionColumn (mField);
			else if (columnName.equals("Search"))
				hasSuche = true;
		} // for all target tab fields
		
		boolean hasEditors = false;

		//
		// Add standard query fields to simple search panel
		if (hasValue)
		{
			addSimpleSearchField(valueLabel, valueField);
			valueField.addActionListener(this);
			hasEditors = true;
		}
		if (hasDocNo)
		{
			addSimpleSearchField(docNoLabel, docNoField);
			docNoField.addActionListener(this);
			hasEditors = true;
		}
		if (hasName)
		{
			addSimpleSearchField(nameLabel, nameField);
			nameField.addActionListener(this);
			hasEditors = true;
		}
		if (hasDescription)
		{
			addSimpleSearchField(descriptionLabel, descriptionField);
			descriptionField.addActionListener(this);
			hasEditors = true;
		}
		if (hasSuche)
		{
			addSimpleSearchField(searchLabel, searchField);
			searchField.addActionListener(this);
			hasEditors = true;
		}

		//
		// Add remaining fields to simple search panel
		for (final IUserQueryField searchField : getAvailableSearchFields())
		{
			final String columnName = searchField.getColumnName();
			if (!columnName.equals("Value")
					&& !columnName.equals("Name")
					&& !columnName.equals("DocumentNo")
					&& !columnName.equals("Description")
					&& !columnName.equals("Search")
					&& (FindPanelSearchField.isSelectionColumn(searchField) || columnName.indexOf("Name") != -1))
			{
				addSelectionColumn(searchField);
				hasEditors = true;
			}
		}

		//
		// If no editors, get rid of simple search tab because it's pointless
		if (!hasEditors)
		{
			simplePanel.setEnabled(false);
			simplePanel.setVisible(false);
			simplePanelDisabled = true;
			tabbedPane.removeTabAt(0); // remove tab
		}
		//
		tabbedPane.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				onTabChanged(e);
			}
		});
		
		//
		// Get Total
		m_total = getNoOfRecords(null, false);
		setStatusDB(m_total);
		statusBar.setStatusLine("");
	} // initFind

	/**
	 * Add Selection Column to simple search tab
	 * 
	 * @param searchField field
	 */
	private void addSelectionColumn(final IUserQueryField searchField)
	{
		final FindPanelSearchField findPanelSearchField = FindPanelSearchField.castToFindPanelSearchField(searchField);
		// Editor
		final VEditor editor = findPanelSearchField.createEditor(false);
		if (editor == null)
		{
			return; // shall not happen
		}
		
		//
		// Add action listener to custom text fields - teo_sarca [ 1709292 ]
		// i.e. when user presses enter we shall start searching
		if (editor instanceof CTextField)
		{
			((CTextField)editor).addActionListener(this);
		}
		
		final CLabel label = findPanelSearchField.createEditorLabel();

		//
		addSimpleSearchField(label, swingEditorFactory.getEditorComponent(editor));
		m_sEditors.add(editor);
	} // addSelectionColumn

	/**
	 * Init Find GridController
	 */
	private void initFindAdvanced()
	{
		//
		// Advanced search table
		advancedTable.getModel().setAvailableSearchFields(getAvailableSearchFields());
		cmd_new();

		//
		// Load user queries
		{
			fQueryName.setModel(new ListComboBoxModel<>(userQueriesRepository.getUserQueryNames()));
			fQueryName.setValue(null);
		}

	} // initFindAdvanced

	/**
	 * Dispose window
	 */
	public void dispose()
	{
		// Remove action listener from custom fields - teo_sarca [ 1709292 ]
		for (VEditor editor : m_sEditors)
		{
			if (editor instanceof CTextField)
			{
				((CTextField)editor).removeActionListener(this);
			}
		}
		m_sEditors.clear();

		mainPanel.removeAll();
		
		listenerList = null;

		disposed = true; // metas: tsa
	} // dispose
	
	private boolean isSimpleSearchPanelActive()
	{
		return tabbedPane.getSelectedComponent() == simplePanel;
	}

	/**************************************************************************
	 * Action Listener
	 * 
	 * @param e ActionEvent
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
			clientUI.error(m_targetWindowNo, ex);
		}
	}
	
	private void actionPerformed0(final ActionEvent e)
	{
		if (isDisposed())
		{
			return;
		}
		
		// ConfirmPanel.A_OK and enter in fields
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			cmd_ok();
			actionListener.onSearch(false);
		}
		// Enter pressed in a field of simple search panel => trigger search
		else if ((e.getSource() instanceof JTextField) && isSimpleSearchPanelActive())
		{
			cmd_ok_Simple();
			actionListener.onSearch(true);
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			doCancel();
			actionListener.onCancel();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_REFRESH))
		{
			cmd_refresh();
		}
		//
		else if (e.getActionCommand().equals(ConfirmPanel.A_NEW))
		{
			m_query = MQuery.getNoRecordQuery(m_tableName, true);
			m_query.setUserQuery(true);
			m_total = 0;

			executeQuery(GridTabMaxRows.DEFAULT);
			
			actionListener.onOpenAsNewRecord();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_RESET))
		{
			cmd_reset(); // metas
		}
		//
		else if (e.getSource() == bIgnore)
			cmd_ignore();
		else if (e.getSource() == bNew)
			cmd_new();
		else if (e.getSource() == bSave)
			cmd_save(true);
		else if (e.getSource() == bDelete)
			cmd_delete();
		else if (e.getSource() == fQueryName)
		{
			onUserQuerySelected();
		}
	} // actionPerformed
	
	private final MQuery createNewQuery(final MQuery template)
	{
		final MQuery query;
		if(template != null)
		{
			query = template.deepCopy();
		}
		else
		{
			query = new MQuery(m_tableName);
		}
		query.setUserQuery(true);
		query.addRestriction(Env.parseContext(Env.getCtx(), m_targetWindowNo, m_whereExtended, false));
		return query;
	}

	private void onUserQuerySelected()
	{
		final String selectedUserQueryName = fQueryName.getSelectedItem();
		if (Check.isEmpty(selectedUserQueryName, true))
		{
			return;
		}
		
		final IUserQuery userQuery = userQueriesRepository.getUserQueryByName(selectedUserQueryName);
		if(userQuery == null)
		{
			return;
		}

		advancedTable.stopEditor(false);
		final FindAdvancedSearchTableModel model = advancedTable.getModel();
		model.setRows(userQuery.getRestrictions());
	}

	/**
	 * User changed between Simple search tab and Advanced search tab
	 */
	private final void onTabChanged(final ChangeEvent e)
	{
		setDefaultButton();
		final Component selectedTab = tabbedPane.getSelectedComponent();
		if(selectedTab != null)
		{
			selectedTab.requestFocusInWindow();
		}
	}

	/**
	 * Simple OK Button pressed; Shows a wait cursor while it's running
	 */
	private void cmd_ok_Simple()
	{
		clientUI.executeLongOperation(getParent(), new Runnable()
		{
			@Override
			public void run()
			{
				cmd_ok_Simple0();
			}
		});
	}

	private void cmd_ok_Simple0()
	{
		// Create Query String
		m_query = createNewQuery(null);
		
		if (hasValue && !valueField.getText().equals("%")
				&& valueField.getText().length() != 0)
		{
			String value = valueField.getText();
			FindHelper.addStringRestriction(m_query, "Value", value, valueLabel.getText(), true); // metas
		}
		//
		if (hasDocNo && !docNoField.getText().equals("%")
				&& docNoField.getText().length() != 0)
		{
			String value = docNoField.getText();
			FindHelper.addStringRestriction(m_query, "DocumentNo", value, docNoLabel.getText(), true); // metas
		}
		//
		if ((hasName) && !nameField.getText().equals("%")
				&& nameField.getText().length() != 0)
		{
			String value = nameField.getText();
			FindHelper.addStringRestriction(m_query, "Name", value, nameLabel.getText(), true); // metas
		}
		//
		if (hasDescription && !descriptionField.getText().equals("%")
				&& descriptionField.getText().length() != 0)
		{
			String value = descriptionField.getText();
			FindHelper.addStringRestriction(m_query, "Description", value, descriptionLabel.getText(), true); // metas
		}
		if (hasSuche && !searchField.getText().equals("%")
				&& searchField.getText().length() != 0)
		{
			String value = searchField.getText();
			FindHelper.addStringRestriction(m_query, "Search", value, searchLabel.getText(), false); // metas
		}

		// Special Editors
		for (final VEditor veditor : m_sEditors)
		{
			final Object value = veditor.getValue();
			if (value != null && value.toString().length() > 0)
			{
				final String ColumnName = veditor.getName();
				log.debug(ColumnName + "=" + value);

				final FindPanelSearchField field = getSearchFieldByColumnName(ColumnName);
				final String ColumnSQL = field.getColumnSQL();

				// metas-2009_0021_AP1_CR064: begin
				if (value instanceof String
						&& DisplayType.isText(field.getDisplayType()) // note: at this place, a Y/N column's value is also a string
				)
				{
					FindHelper.addStringRestriction(m_query, ColumnSQL, (String)value, ColumnName, true); // metas
				}
				else
				{
					m_query.addRestriction(ColumnSQL, Operator.EQUAL, value, ColumnName, veditor.getDisplay());
				}
			}
		} // editors

		m_isCancel = false; // teo_sarca [ 1708717 ]

		// Test for no records
		final boolean alertZeroRecords = true;
		final int noOfRecords = getNoOfRecords(m_query, alertZeroRecords);
		if (noOfRecords > 0)
		{
			executeQuery(GridTabMaxRows.of(noOfRecords));
		}
	} // cmd_ok_Simple

	private void cmd_ok()
	{
		if (isSimpleSearchPanelActive())
		{
			cmd_ok_Simple();
		}
		else
		{
			cmd_ok_Advanced();
		}
	}

	/**
	 * Advanced OK Button pressed
	 */
	private void cmd_ok_Advanced()
	{
		m_isCancel = false; // teo_sarca [ 1708717 ]
		
		// save pending
		if (bSave.isEnabled())
		{
			cmd_save(false);
		}

		final boolean alertZeroRecords = true;
		final int noOfRecords = getNoOfRecords(m_query, alertZeroRecords);
		if (noOfRecords > 0)
		{
			executeQuery(GridTabMaxRows.of(noOfRecords));
		}
	} // cmd_ok_Advanced

	/**
	 * Cancel Button pressed
	 */
	protected void doCancel()
	{
		// If cancel buttons are not active then do nothing.
		// This shall not happen, but it's better to guard.
		// NOTE: the use case is: we included this panel in a window. Should not call this method.
		if (embedded)
		{
			return;
		}

		advancedTable.stopEditor(false);
		
		m_query = null;
		m_total = TOTAL_NotAvailable;
		m_isCancel = true; // teo_sarca [ 1708717 ]
		
		dispose();
	} // cmd_ok

	/**
	 * Ignore - reload selected user query if any
	 */
	private void cmd_ignore()
	{
		onUserQuerySelected();
	}

	/**
	 * New record
	 */
	private void cmd_new()
	{
		advancedTable.newRow();
	} // cmd_new

	/**
	 * Save (Advanced)
	 */
	private void cmd_save(final boolean saveQuery)
	{
		advancedTable.stopEditor(true);
		
		final List<IUserQueryRestriction> rows = advancedTable.getModel().getRows();

		//
		final MQuery queryToSave = createNewQuery(null);
		
		String userQueryNameToSave = null;
		if (saveQuery)
		{
			final String selectedUserQueryName = fQueryName.getSelectedItem();
			if (!Check.isEmpty(selectedUserQueryName, true))
			{
				userQueryNameToSave = selectedUserQueryName;
			}
		}


		userQueriesRepository.saveRowsToQuery(rows, queryToSave, userQueryNameToSave);
		m_query = queryToSave;

		if (userQueryNameToSave != null)
		{
			refreshUserQueries();
			// TODO: show message
		}
	} // cmd_save

	private void refreshUserQueries()
	{
		final String userQueryNameSelectedBefore = fQueryName.getSelectedItem();
		
		fQueryName.setModel(new ListComboBoxModel<>(userQueriesRepository.getUserQueryNames()));
		
		fQueryName.setSelectedItem(userQueryNameSelectedBefore);
		if (fQueryName.getSelectedIndex() < 0)
		{
			fQueryName.setValue(null);
		}
	}

	/**
	 * Delete
	 */
	private void cmd_delete()
	{
		advancedTable.deleteCurrentRow();
		cmd_refresh();
	} // cmd_delete

	/**
	 * Refresh
	 */
	private void cmd_refresh()
	{
		advancedTable.stopEditor(false);
		final int records = getNoOfRecords(m_query, true);
		setStatusDB(records);
		statusBar.setStatusLine("");
	} // cmd_refresh

	/**************************************************************************
	 * Get Query - Retrieve result
	 * 
	 * @return String representation of query
	 */
	public MQuery getQuery()
	{
		//
		// Validate the existing query
		if (m_isCancel)
		{
			// do nothing
		}
		else if (isTotalRecordsOverMaximumAllowed())
		{
			m_query = MQuery.getNoRecordQuery(m_tableName, false);
			m_query.setUserQuery(true);
			m_total = 0;
			log.warn("Query - over max");
		}
		else
		{
			log.info("Query={}", m_query);
		}

		return m_query;
	} // getQuery

	/**
	 * Get Total Records
	 * 
	 * @return no of records or {@link #TOTAL_NotAvailable}
	 */
	/* package */int getTotalRecords()
	{
		return m_total;
	}

	private boolean isTotalRecordsOverMaximumAllowed()
	{
		final GridTabMaxRowsRestrictionChecker maxRowsChecker = getMaxRowsChecker();

		//
		// If total rows is not available (i.e. we are dealing with high volume tables and we did not calculate them but we assume they are big),
		// then consider the total exceeding maximum allowed
		final int totalRecords = getTotalRecords();
		final int maxRowsAllowed = maxRowsChecker.getMaxQueryRecords();
		if (totalRecords == TOTAL_NotAvailable && maxRowsAllowed > 0)
		{
			return true;
		}
		return maxRowsChecker.isQueryMax(totalRecords);
	}

	/**
	 * Get the number of records of target tab
	 * 
	 * @param query where clause for target tab
	 * @param alertZeroRecords show dialog if there are no records
	 * @return number of selected records; if the results are more then allowed this method will return 0
	 */
	private int getNoOfRecords(final MQuery query, final boolean alertZeroRecords)
	{
		log.info("query={}", query);

		// metas-2009_0021_AP1_G113: begin
		if (!alertZeroRecords // we do count optimizations only if
				// alertZeroRecords is false (not a real count is required)
				&& (m_targetTabNo > 0 // do not count records for secondary tabs
						|| MTable.get(Env.getCtx(), m_tableName).isHighVolume() // or we have a high volume table
				|| (query != null && query.getRecordCount() == 0) // query has no results
				))
		{
			return TOTAL_NotAvailable;
		}
		// metas-2009_0021_AP1_G113: end
		final StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
		sql.append(m_tableName);

		final StringBuilder whereClause = new StringBuilder(" WHERE 1=1");

		if (!Check.isEmpty(this.m_whereExtended))
		{
			whereClause.append(" AND (")
					.append(this.m_whereExtended)
					.append(")");
		}

		final GridTab tab = getGridTab();
		if (tab != null)
		{
			final String gcTabWhereExtended = tab.getWhereExtended();
			if (!Check.isEmpty(gcTabWhereExtended))
			{
				whereClause.append(" AND (")
						.append(gcTabWhereExtended)
						.append(")");
			}
		}

		if (query != null && query.isActive())
		{
			final String queryWhereClause = query.getWhereClause();
			if (!Check.isEmpty(queryWhereClause))
			{
				whereClause.append(" AND (")
						.append(queryWhereClause)
						.append(")");
			}
		}

		//
		// finally, append where clause to existing SQL
		sql.append(whereClause);

		// Add Access
		final IUserRolePermissions role = getUserRolePermissions();
		String finalSQL = role.addAccessSQL(sql.toString(),
				m_tableName, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		finalSQL = Env.parseContext(Env.getCtx(), m_targetWindowNo, finalSQL, false);
		Env.setContext(Env.getCtx(), m_targetWindowNo, TABNO, "FindSQL", finalSQL);

		if (Check.isEmpty(finalSQL, true))
		{
			log.warn("SQL could not be parsed: " + sql);
			return 0;
		}

		// Execute Query
		m_total = TOTAL_NotAvailable;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.createStatement();
			rs = stmt.executeQuery(finalSQL);
			if (rs.next())
			{
				m_total = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			m_total = -1; // don'T show the "too many records" message when it fact we don't know how many bloody records there are.
			log.error(finalSQL, e);
			clientUI.warn(m_targetWindowNo, e); // .. show the error instead so the user can call us to fix it
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}

		final GridTabMaxRowsRestrictionChecker maxRowsChecker = getMaxRowsChecker();
		if (m_total == 0 && alertZeroRecords)
		{
			//
			// No Records
			clientUI.info(m_targetWindowNo, "FindZeroRecords");
		}
		else if (query != null && maxRowsChecker.isQueryMax(m_total))
		{
			//
			// More than allowed
			clientUI.error(m_targetWindowNo, "FindOverMax", m_total + " > " + maxRowsChecker.getMaxQueryRecords());
			m_total = maxRowsChecker.getMaxQueryRecords(); // task 08756: return as much as allowed
		}
		else
		{
			log.info("#" + m_total);
		}

		//
		if (query != null)
		{
			statusBar.setStatusToolTip(query.getWhereClause());
		}
		return m_total;
	} // getNoOfRecords

	private final IUserRolePermissions getUserRolePermissions()
	{
		return role;
	}

	private final GridController getGridController()
	{
		return gridController;
	}

	private final GridTab getGridTab()
	{
		return gridTab;
	}

	private GridTabMaxRowsRestrictionChecker getMaxRowsChecker()
	{
		return maxRowsChecker;
	}

	/**
	 * Display current count
	 * 
	 * @param currentCount String representation of current/total
	 */
	private void setStatusDB(final int currentCount)
	{
		final String text;
		final int totalCount = getTotalRecords();
		if (currentCount == TOTAL_NotAvailable || totalCount == TOTAL_NotAvailable)
		{
			// In case the current count or the total count was not available, don't display the information.
			text = "";
		}
		else
		{
			text = " " + currentCount + " / " + totalCount + " ";
		}
		statusBar.setStatusDB(text);
	} // setDtatusDB

	private final Collection<FindPanelSearchField> getAvailableSearchFields()
	{
		return _columnName2searchFields.values();
	}

	private final FindPanelSearchField getSearchFieldByColumnName(final String columnName)
	{
		if (columnName == null)
		{
			return null;
		}

		//
		// Search by ColumnName (quick)
		{
			FindPanelSearchField searchField = _columnName2searchFields.get(columnName);
			if (searchField != null)
			{
				return searchField;
			}
		}

		//
		// Iterate the search fields and check for matching
		for (final FindPanelSearchField searchField : _columnName2searchFields.values())
		{
			if (searchField.matchesColumnName(columnName))
			{
				return searchField;
			}
		}
		return null;
	}

	private void addSimpleSearchField(final CLabel label, final Component editorComp)
	{
		if (label != null)
		{
			label.setVisible(true);
			label.setLabelFor(editorComp);
		}
		editorComp.setVisible(true);

		//
		final Component labelComp = label != null ? label : Box.createHorizontalStrut(1);
		simplePanelContent.add(labelComp, new CC()
				.alignX("trailing")
				.alignY("top")
				.growPrioX(0)
				.growX(0)
				);

		simplePanelContent.add(editorComp, new CC()
				.alignY("top")
				.sizeGroupX("editors")
				.growPrioX(100)
				.growX()
				.minWidth("100px")
				.maxWidth("300px")
				);

		//
		// Set Cursor to the first search field in the search panel (metas-2009_0021_AP1_CR064)
		if (m_editorFirst == null)
		{
			m_editorFirst = editorComp;
			editorComp.requestFocus();
		}
	}

	public boolean isCancel()
	{
		return m_isCancel;
	}

	private void parseQuery(final MQuery query)
	{
		boolean showAdvancedTab = true;
		advancedTable.stopEditor(true);

		// metas: cg: task: US119 : if is new record query don't check
		if (query.isNewRecordQuery())
		{
			return;
		}

		final List<IUserQueryRestriction> rows = new ArrayList<>();
		for (int i = 0; i < query.getRestrictionCount(); i++)
		{
			String columnName = query.getColumnName(i);
			final Operator operator = query.getOperator(i);
			final Object value = query.getCode(i);
			final Object valueTo = query.getCodeTo(i);
			final boolean andCondition = query.isAndCondition(i);
			if (Check.isEmpty(columnName, true))
			{
				// Maybe it's a direct whereClause, we need to skip it
				continue;
			}
			boolean hasFunction = false;
			if (columnName.toUpperCase().startsWith("UPPER("))
			{
				columnName = columnName.substring(6, columnName.length() - 1);
				hasFunction = true;
			}

			//
			if (hasValue && hasFunction && "Value".equals(columnName))
			{
				valueField.setValue(value);
				showAdvancedTab = false;
				continue;
			}
			else if (hasName && hasFunction && "Name".equals(columnName))
			{
				nameField.setValue(value);
				showAdvancedTab = false;
				continue;
			}
			else if (hasDescription && hasFunction && "Description".equals(columnName))
			{
				descriptionField.setValue(value);
				showAdvancedTab = false;
				continue;
			}
			else if (hasDocNo && hasFunction && "DocumentNo".equals(columnName))
			{
				docNoField.setValue(value);
				showAdvancedTab = false;
				continue;
			}
			else
			{
				// Special Editors
				boolean found = false;
				for (final VEditor ved : m_sEditors)
				{
					if (columnName.equals(ved.getName()))
					{
						ved.setValue(value);
						showAdvancedTab = false;
						found = true;
						break;
					}
				}
				if (found)
				{
					continue;
				}
			}

			//
			// Add to advanced table
			{
				final IUserQueryRestriction row = IUserQueryRestriction.newInstance();
				row.setJoin(andCondition ? Join.AND : Join.OR);

				IUserQueryField searchField = getSearchFieldByColumnName(columnName);
				if (searchField == null)
				{
					// Use the InfoName when adding the criteria. It will also give the correct name for column sqls (08880)
					searchField = getSearchFieldByColumnName(query.getInfoName(i));
				}
				row.setSearchField(searchField);
				row.setOperator(operator);

				//
				// GridField field = getTargetMField(columnName);
				// Object valueFixed = parseString(field, value);
				row.setValue(value);
				row.setValueTo(valueTo);

				rows.add(row);
			}
		}
		if (rows.isEmpty())
		{
			showAdvancedTab = false;
		}
		if (simplePanelDisabled)
		{
			showAdvancedTab = true;
		}

		//
		// Update advanced search table
		advancedTable.getModel().setRows(rows);
		advancedTable.invalidate();

		if (showAdvancedTab)
			tabbedPane.setSelectedComponent(advancedPanel);
		else
			tabbedPane.setSelectedComponent(simplePanel);
	}

	private void cmd_reset()
	{
		//
		// Simple search panel reset
		{
			for (VEditor editor : m_sEditors)
			{
				editor.setValue(null);
			}
			valueField.setText("");
			nameField.setText("");
			descriptionField.setText("");
			docNoField.setText("");
			searchField.setText("");
		}

		//
		// Advanced search panel reset
		{
			advancedTable.getModel().clear();
			fQueryName.setSelectedItem(null);
		}

		//
		// Reset query
		m_query = createNewQuery(null);
		
		//
		// Reset counters
		m_total = 0;
	}

	/**
	 * 
	 * @param maxRows acts as a "LIMIT" for the underlying SQL.
	 */
	private void executeQuery(final GridTabMaxRows maxRows)
	{
		final GridController gc = getGridController();
		if (gc != null)
		{
			clientUI.invoke()
					.setLongOperation(true)
					.setParentComponentByWindowNo(m_targetWindowNo)
					.setRunnable(new Runnable()
					{
						@Override
						public void run()
						{
							// m_onlyCurrentRows = false; // search history too
							// cmd_save(false);
							m_query.setUserQuery(true);
							gc.getMTab().setQuery(m_query);

							final boolean onlyCurrentRows = false;
							final int onlyCurrentDays = 0;
							gc.query(onlyCurrentRows, onlyCurrentDays, maxRows); // autoSize
						}
					}).
					invoke();
			;
		}
	}

	public void setActionListener(final FindPanelActionListener actionListener)
	{
		Check.assumeNotNull(actionListener, "actionListener not null");
		this.actionListener = actionListener;
	}

	private void setDefaultButton()
	{
			setDefaultButton(confirmPanel.getOKButton());
	}
	
	private void setDefaultButton(final JButton button)
	{
		final Window frame = AEnv.getWindow(this);
		if (frame instanceof RootPaneContainer)
		{
			final RootPaneContainer c = (RootPaneContainer)frame;
			c.getRootPane().setDefaultButton(button);
		}
	}

	/**
	 * Get Icon with name action
	 * 
	 * @param name name
	 * @param small small
	 * @return Icon
	 */
	private final ImageIcon getIcon(String name)
	{
		final String fullName = name + (drawSmallButtons ? "16" : "24");
		return Images.getImageIcon2(fullName);
	}

	/**
	 * @return true if the panel is visible and it has at least one field in simple search mode
	 */
	@Override
	public boolean isFocusable()
	{
		if (!isVisible())
		{
			return false;
		}
		
		if (isSimpleSearchPanelActive())
		{
			return m_editorFirst != null;
		}
		
		return false;
	}

	@Override
	public void setFocusable(final boolean focusable)
	{
		// ignore it
	}

	@Override
	public void requestFocus()
	{
		if (isSimpleSearchPanelActive())
		{
			if (m_editorFirst != null)
			{
				m_editorFirst.requestFocus();
			}
		}
	}

	@Override
	public boolean requestFocusInWindow()
	{
		if (isSimpleSearchPanelActive())
		{
			if (m_editorFirst != null)
			{
				return m_editorFirst.requestFocusInWindow();
			}
		}
		
		return false;
	}

	private boolean disposed = false;

	boolean isDisposed()
	{
		return disposed;
	}
}
