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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RootPaneContainer;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.ADialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.grid.GridController;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabMaxRows;
import org.compiere.model.GridTabMaxRowsRestrictionChecker;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MProduct;
import org.compiere.model.MQuery;
import org.compiere.model.MRefList;
import org.compiere.model.MTable;
import org.compiere.model.MUserQuery;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_Column;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTable;
import org.compiere.swing.CTextField;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;

import de.metas.adempiere.form.IClientUI;

/**
 * Find/Search Records. Based on AD_Find for persistency, query is build to restrict info
 * 
 * @author Jorg Janke
 * @version $Id: Find.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro <li>BF [ 2564070 ] Saving user queries can produce unnecessary db errors
 */
public final class FindPanel extends CPanel implements ActionListener,
		ChangeListener, DataStatusListener
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
	private static final transient CLogger log = CLogger.getCLogger(FindPanel.class);

	FindPanel(final FindPanelBuilder builder)
	{
		super();
		// super(owner, msgBL.getMsg(Env.getCtx(), "Find") + ": " + title, true);

		log.info(builder.getTitle());
		//
		gridController = builder.getGridController();
		gridTab = builder.getGridTab();
		m_targetWindowNo = builder.getTargetWindowNo();
		m_AD_Tab_ID = builder.getAD_Tab_ID(); // red1 new field for UserQuery [ 1798539 ]
		m_targetTabNo = builder.getTargetTabNo(); // metas-2009_0021_AP1_G113
		m_AD_Table_ID = builder.getAD_Table_ID();
		m_tableName = builder.getTableName();
		m_whereExtended = builder.getWhereExtended();
		m_findFields = builder.getFindFields();
		m_small = builder.isSmall();
		embedded = builder.isEmbedded();
		
		this.role = Env.getUserRolePermissions();
		this.maxRowsChecker = builder.newGridTabMaxRowsRestrictionChecker()
				.setUserRolePermissions(role)
				.build();
		
		//
		// metas: begin
		final MQuery query = builder.getQuery();
		if (query == null)
		{
			m_query = new MQuery(builder.getTableName());
		}
		else
		{
			m_query = new MQuery(query);
		}
		m_query.setUserQuery(true);
		// metas: end
		m_query.addRestriction(Env.parseContext(Env.getCtx(), m_targetWindowNo, builder.getWhereExtended(), false));
		// Required for Column Validation
		Env.setContext(Env.getCtx(), m_targetWindowNo, "Find_Table_ID", m_AD_Table_ID);
		// Context for Advanced Search Grid is WINDOW_FIND
		Env.setContext(Env.getCtx(), Env.WINDOW_FIND, "Find_Table_ID", m_AD_Table_ID);
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
			log.log(Level.SEVERE, "Find", e);
		}
		setFrame(frame);
		//
		if (query != null)
		{
			parseQuery(query);
		}
		
		if (builder.isHideStatusBar())
		{
			southPanel.setVisible(false);
		}
	} // Find

	private static final int JoinAndOr_AD_Reference_ID = 204; // AD_Find AndOr
	private static final ValueNamePair JOIN_AND = new ValueNamePair("AND", MRefList.getListName(Env.getCtx(), JoinAndOr_AD_Reference_ID, "A"));
	private static final ValueNamePair JOIN_OR = new ValueNamePair("OR", MRefList.getListName(Env.getCtx(), JoinAndOr_AD_Reference_ID, "O"));
	
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
	private final int m_AD_Tab_ID;
	/** Table ID */
	private final int m_AD_Table_ID;
	/** Table Name */
	private final String m_tableName;
	/** Where */
	private final String m_whereExtended;
	/** Search Fields */
	private final GridField[] m_findFields;
	private final IUserRolePermissions role;
	private final GridTabMaxRowsRestrictionChecker maxRowsChecker;
	/** Resulting query */
	private MQuery m_query = null;
	/** Is cancel ? */
	private boolean m_isCancel = false; // teo_sarca [ 1708717 ]

	/** Number of records */
	private int m_total;
	//
	private boolean hasValue = false;
	private boolean hasDocNo = false;
	private boolean hasName = false;
	private boolean hasDescription = false;
	private boolean hasSuche = false;
	private final boolean m_small;
	/** true if the find panel will be embedded in the window */
	private final boolean embedded;
	
	/** Line in Simple Content */
	private int m_sLine = 6;
	private int m_sColumn = 0;
	private final int m_sColumnMax = Services.get(ISysConfigBL.class).getIntValue(
			"org.compiere.apps.search.FindPanel.MaxColumns",
			4,
			Env.getAD_Client_ID(Env.getCtx()));
	private Component m_editorFirst = null; // metas-2009_0021_AP1_CR064: set
	// Cursor to the first search field
	// in the search panel

	/** List of VEditors */
	private ArrayList<VEditor> m_sEditors = new ArrayList<VEditor>();
	/** Target Fields with AD_Column_ID as key */
	private Hashtable<Integer, GridField> m_targetFields = new Hashtable<Integer, GridField>();

	/** For Grid Controller */
	public static final int TABNO = 99;
	/** Maximum allowed number of columns for a text field component displayed in simple search tab */
	private static final int MAX_TEXT_FIELD_COLUMNS = 20;
	/** Reference ID for Yes/No */
	public static final int AD_REFERENCE_ID_YESNO = 319;

	//
	private Window frame = null;
	private final CPanel panel = this;
	private CPanel southPanel = new CPanel();
	private BorderLayout southLayout = new BorderLayout();
	private StatusBar statusBar = new StatusBar();
	private CTabbedPane tabbedPane = new CTabbedPane();
	private CPanel advancedPanel = new CPanel();
	private BorderLayout advancedLayout = new BorderLayout();
	/** Confirm panel (advanced view) */
	private ConfirmPanel confirmPanelAdvanced;
	private CButton bIgnore = new CButton();
	private JToolBar toolBar = new JToolBar();
	private CComboBox<String> fQueryName = new CComboBox<>();
	private CButton bSave = new CButton();
	private CButton bNew = new CButton();
	private CButton bDelete = new CButton();
	/** Confirm panel (simple view) */
	private ConfirmPanel confirmPanelSimple;
	private BorderLayout simpleLayout = new BorderLayout();
	private CPanel scontentPanel = new CPanel();
	private GridBagLayout scontentLayout = new GridBagLayout();
	private CPanel simplePanel = new CPanel();
	private CLabel valueLabel = new CLabel();
	private CLabel nameLabel = new CLabel();
	private CLabel descriptionLabel = new CLabel();
	private CLabel searchLabel = new CLabel();
	private CTextField valueField = new CTextField();
	private CTextField nameField = new CTextField();
	private CTextField descriptionField = new CTextField();
	private CTextField searchField = new CTextField();
	private CLabel docNoLabel = new CLabel();
	private CTextField docNoField = new CTextField();
	// private Component spaceE;
	// private Component spaceN;
	// private Component spaceW;
	// private Component spaceS;
	private JScrollPane advancedScrollPane = new JScrollPane();
	private CTable advancedTable = new CTable()
	{

		private static final long serialVersionUID = -6201749159307529032L;

		@Override
		public boolean isCellEditable(int row, int column)
		{
			boolean editable = (column == INDEX_COLUMNNAME
					|| column == INDEX_OPERATOR || (column == INDEX_JOIN && row > 0));
			if (!editable && row >= 0)
			{
				String columnName = null;
				Object value = getModel().getValueAt(row, INDEX_COLUMNNAME);
				if (value != null)
				{
					if (value instanceof ValueNamePair)
						columnName = ((ValueNamePair)value).getValue();
					else
						columnName = value.toString();
				}

				// Create Editor
				editable = getTargetMField(columnName) != null;
			}
			return editable;
		}
	};

	/** Index Join Operator = 0 */
	public static final int INDEX_JOIN = 0;
	/** Index ColumnName = 1 */
	public static final int INDEX_COLUMNNAME = 1;
	/** Index Operator = 2 */
	public static final int INDEX_OPERATOR = 2;
	/** Index Value = 3 */
	public static final int INDEX_VALUE = 3;
	/** Index Value2 = 4 */
	public static final int INDEX_VALUE2 = 4;

	/** Advanced Search Column */
	public CComboBox<ValueNamePair> columns = null;
	/** Advanced Search Operators */
	public CComboBox<ValueNamePair> operators = null;
	private MUserQuery[] userQueries;
	private ValueNamePair[] columnValueNamePairs;

	private static final String FIELD_SEPARATOR = "<^>";
	private static final String SEGMENT_SEPARATOR = "<~>";

	/**
	 * Static Init.
	 * 
	 * <pre>
	 *  tabbedPane
	 *      simplePanel
	 *          scontentPanel
	 *          confirmPanelS
	 *      advancedPanel
	 *          toolBar
	 *          GC
	 *          confirmPanelA
	 *  southPanel
	 *      statusBar
	 * </pre>
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		final GridTab gridTab = getGridTab();
		final boolean withNewButton = !embedded && gridTab != null && !gridTab.isReadOnly();
		
		confirmPanelSimple = ConfirmPanel.builder()
				.withCancelButton(!embedded)
				.withNewButton(withNewButton)
				.withResetButton(true)
				.withText(true)
				.withSmallButtons(m_small)
				.build();
		confirmPanelAdvanced = ConfirmPanel.builder()
				.withCancelButton(!embedded)
				.withNewButton(withNewButton)
				.withResetButton(true)
				.withText(true)
				.withSmallButtons(m_small)
				.build();

		panel.setLayout(new BorderLayout());
		//
		// spaceE = Box.createHorizontalStrut(8);
		// spaceN = Box.createVerticalStrut(8);
		// spaceW = Box.createHorizontalStrut(8);
		// spaceS = Box.createVerticalStrut(8);
		bIgnore.setIcon(getIcon("Ignore", m_small));
		bIgnore.setMargin(new Insets(2, 2, 2, 2));
		bIgnore.setToolTipText(msgBL.getMsg(Env.getCtx(), "Ignore"));
		bIgnore.addActionListener(this);
		fQueryName.setToolTipText(msgBL.getMsg(Env.getCtx(), "QueryName"));
		fQueryName.setEditable(true);
		fQueryName.addActionListener(this);
		bSave.setIcon(getIcon("Save", m_small));
		bSave.setMargin(new Insets(2, 2, 2, 2));
		bSave.setToolTipText(msgBL.getMsg(Env.getCtx(), "Save"));
		bSave.addActionListener(this);
		bNew.setIcon(getIcon("New", m_small));
		bNew.setMargin(new Insets(2, 2, 2, 2));
		bNew.setToolTipText(msgBL.getMsg(Env.getCtx(), "New"));
		bNew.addActionListener(this);
		bDelete.setIcon(getIcon("Delete", m_small));
		bDelete.setMargin(new Insets(2, 2, 2, 2));
		bDelete.setToolTipText(msgBL.getMsg(Env.getCtx(), "Delete"));
		bDelete.addActionListener(this);
		//
		southPanel.setLayout(southLayout);
		valueLabel.setLabelFor(valueField);
		valueLabel.setText(msgBL.translate(Env.getCtx(), "Value"));
		nameLabel.setLabelFor(nameField);
		nameLabel.setText(msgBL.translate(Env.getCtx(), "Name"));
		descriptionLabel.setLabelFor(descriptionField);
		descriptionLabel.setText(msgBL.translate(Env.getCtx(), "Description"));
		// metas
		searchLabel.setLabelFor(searchField);
		searchLabel.setText(msgBL.translate(Env.getCtx(), "search"));
		// metas end
		// valueField.setText("%");
		valueField.setColumns(MAX_TEXT_FIELD_COLUMNS);
		// nameField.setText("%");
		nameField.setColumns(MAX_TEXT_FIELD_COLUMNS);
		// descriptionField.setText("%");
		descriptionField.setColumns(MAX_TEXT_FIELD_COLUMNS);
		// metas
		// sucheField.setText("");
		searchField.setColumns(MAX_TEXT_FIELD_COLUMNS);
		// metas end
		scontentPanel.setToolTipText(msgBL.getMsg(Env.getCtx(), "FindTip"));
		docNoLabel.setLabelFor(docNoField);
		docNoLabel.setText(msgBL.translate(Env.getCtx(), "DocumentNo"));
		// docNoField.setText("%");
		docNoField.setColumns(MAX_TEXT_FIELD_COLUMNS);
		advancedScrollPane.setPreferredSize(new Dimension(450, 150));
		southPanel.add(statusBar, BorderLayout.SOUTH);
		panel.add(southPanel, BorderLayout.SOUTH);
		//
		scontentPanel.setLayout(scontentLayout);
		simplePanel.setLayout(simpleLayout);
		simplePanel.add(confirmPanelSimple, BorderLayout.SOUTH);
		simplePanel.add(scontentPanel, BorderLayout.CENTER);
		//
		// tabbedPane.add(simplePanel, msgBL.getMsg(Env.getCtx(),"Find"));
		tabbedPane.add(simplePanel, msgBL.getMsg(Env.getCtx(), "Find"));
		//
		toolBar.add(bIgnore, null);
		toolBar.addSeparator();
		toolBar.add(bNew, null);
		toolBar.add(bDelete, null);
		toolBar.add(fQueryName, null);
		toolBar.add(bSave, null);
		advancedPanel.setLayout(advancedLayout);
		advancedPanel.add(toolBar, BorderLayout.NORTH);
		advancedPanel.add(confirmPanelAdvanced, BorderLayout.SOUTH);
		advancedPanel.add(advancedScrollPane, BorderLayout.CENTER);
		advancedScrollPane.getViewport().add(advancedTable, null);
		// tabbedPane.add(advancedPanel, msgBL.getMsg(Env.getCtx(),"Advanced"));
		tabbedPane.add(advancedPanel, msgBL.getMsg(Env.getCtx(), "Advanced"));
		//
		panel.add(tabbedPane, BorderLayout.CENTER);
		//
		confirmPanelAdvanced.setActionListener(this);
		confirmPanelSimple.setActionListener(this);
		//

		// Better Labels for OK/Cancel
		confirmPanelSimple.getOKButton().setToolTipText(msgBL.getMsg(Env.getCtx(), "QueryEnter"));
		confirmPanelSimple.getOKButton().setIcon(getIcon("Ok", m_small));
		confirmPanelAdvanced.getOKButton().setToolTipText(msgBL.getMsg(Env.getCtx(), "QueryEnter"));
		confirmPanelAdvanced.getOKButton().setIcon(getIcon("Ok", m_small));
		//
		confirmPanelSimple.getCancelButton().setToolTipText(msgBL.getMsg(Env.getCtx(), "QueryCancel"));
		confirmPanelAdvanced.getCancelButton().setToolTipText(msgBL.getMsg(Env.getCtx(), "QueryCancel"));
		confirmPanelSimple.getResetButton().setIcon(getIcon("Reset", m_small));
		confirmPanelAdvanced.getResetButton().setIcon(getIcon("Reset", m_small));
	} // jbInit

	/**
	 * Dynamic Init.6 Set up GridController
	 */
	private void initFind()
	{
		log.config("");

		// Get Info from target Tab
		for (int i = 0; i < m_findFields.length; i++)
		{
			GridField mField = m_findFields[i];
			String columnName = mField.getColumnName();

			// Make Yes-No searchable as list
			final int displayType = mField.getVO().getDisplayType();
			if (displayType == DisplayType.YesNo)
			{
				GridFieldVO vo = mField.getVO();
				GridFieldVO ynvo = vo.clone(vo.getCtx(), vo.WindowNo, vo.TabNo, vo.AD_Window_ID, vo.AD_Tab_ID, vo.tabReadOnly);
				ynvo.setIsDisplayed(true);
				ynvo.setDisplayType(DisplayType.List);
				ynvo.setAD_Reference_Value_ID(AD_REFERENCE_ID_YESNO);

				GridField ynfield = new GridField(ynvo);

				// replace the original field by the YN List field
				m_findFields[i] = ynfield;
				mField = ynfield;
			}
			// Make Button (with list) searchable as list
			else if (displayType == DisplayType.Button && mField.getVO().AD_Reference_Value_ID > 0)
			{
				final GridFieldVO vo = mField.getVO();
				final GridFieldVO buttonVO = vo.clone(vo.getCtx(), vo.WindowNo, vo.TabNo, vo.AD_Window_ID, vo.AD_Tab_ID, vo.tabReadOnly);
				buttonVO.setIsDisplayed(true);
				buttonVO.setDisplayType(DisplayType.List);

				final GridField buttonField = new GridField(buttonVO);

				// replace the original field by the YN List field
				m_findFields[i] = buttonField;
				mField = buttonField;
			}

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
			// TargetFields
			m_targetFields.put(new Integer(mField.getAD_Column_ID()), mField);
		} // for all target tab fields

		// Disable simple query fields
		// valueLabel.setVisible(hasValue);
		// valueField.setVisible(hasValue);
		if (hasValue)
		{
			addSimpleSearchField(valueLabel, valueField);
			valueField.addActionListener(this);
		}
		// docNoLabel.setVisible(hasDocNo);
		// docNoField.setVisible(hasDocNo);
		if (hasDocNo)
		{
			addSimpleSearchField(docNoLabel, docNoField);
			docNoField.addActionListener(this);
		}
		// nameLabel.setVisible(hasName);
		// nameField.setVisible(hasName);
		if (hasName)
		{
			addSimpleSearchField(nameLabel, nameField);
			nameField.addActionListener(this);
		}
		// descriptionLabel.setVisible(hasDescription);
		// descriptionField.setVisible(hasDescription);
		if (hasDescription)
		{
			addSimpleSearchField(descriptionLabel, descriptionField);
			descriptionField.addActionListener(this);
		}
		if (hasSuche)
		{
			addSimpleSearchField(searchLabel, searchField);
			searchField.addActionListener(this);
		}
		//
		// Add remaining fields
		for (int i = 0; i < m_findFields.length; i++)
		{
			GridField mField = m_findFields[i];
			String columnName = mField.getColumnName();
			if (!columnName.equals("Value")
					&& !columnName.equals("Name")
					&& !columnName.equals("DocumentNo")
					&& !columnName.equals("Description")
					&& !columnName.equals("Search")
					&& (mField.isSelectionColumn() || columnName.indexOf("Name") != -1))
			{
				addSelectionColumn(mField);
			}
		}

		// Get Total
		m_total = getNoOfRecords(null, false);
		setStatusDB(m_total);
		statusBar.setStatusLine("");

		tabbedPane.addChangeListener(this);
	} // initFind

	/**
	 * Add Selection Column to first Tab
	 * 
	 * @param mField
	 *            field
	 */
	private void addSelectionColumn(final GridField mField)
	{
		log.config(mField.getHeader());
		
		//
		// Enforce maximum text field columns
		int textFieldColumnsOrig = mField.getDisplayLength();
		if (textFieldColumnsOrig > MAX_TEXT_FIELD_COLUMNS)
			mField.setDisplayLength(MAX_TEXT_FIELD_COLUMNS);
		else
			textFieldColumnsOrig = 0;

		// Editor
		VEditor editor = null;
		if (mField.isLookup())
		{
			final VLookup vl = new VLookup(mField.getColumnName(), false, false, true, mField.getLookup());
			// setting mField to avoid NPE
			vl.setField(mField);

			vl.setName(mField.getColumnName());
			editor = vl;
		}
		else
		{
			editor = swingEditorFactory.getEditor(mField, false);
			editor.setMandatory(false);
			editor.setReadWrite(true);
		}
		// Add action listener to custom text fields - teo_sarca [ 1709292 ]
		if (editor instanceof CTextField)
		{
			((CTextField)editor).addActionListener(this);
		}
		CLabel label = swingEditorFactory.getLabel(mField);
		
		//
		// Restore original text field columns
		if (textFieldColumnsOrig > 0)
			mField.setDisplayLength(textFieldColumnsOrig);
		
		//
		addSimpleSearchField(label, swingEditorFactory.getEditorComponent(editor));
		m_sEditors.add(editor);
	} // addSelectionColumn

	/**
	 * Init Find GridController
	 */
	private void initFindAdvanced()
	{
		log.config("");
		advancedTable.setModel(new DefaultTableModel(0, 5));
		advancedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		advancedTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		advancedTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		TableCellRenderer renderer = new ProxyRenderer(advancedTable.getDefaultRenderer(Object.class));
		advancedTable.setDefaultRenderer(Object.class, renderer);

		final InputMap im = advancedTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		// On TAB pressed => request focus in window
		{
			final KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
			final Action tabAction = advancedTable.getActionMap().get(im.get(tab));
			Action tabActionWrapper = new AbstractAction()
			{
				private static final long serialVersionUID = -6868476640719619801L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					tabAction.actionPerformed(e);

					JTable table = (JTable)e.getSource();
					table.requestFocusInWindow();
				}
			};
			advancedTable.getActionMap().put(im.get(tab), tabActionWrapper);
		}

		// On SHIFT-TAB pressed => request focus in window
		{
			final KeyStroke shiftTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK);
			final Action shiftTabAction = advancedTable.getActionMap().get(im.get(shiftTab));
			final Action shiftTabActionWrapper = new AbstractAction()
			{
				private static final long serialVersionUID = 5493691483070046620L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					shiftTabAction.actionPerformed(e);

					JTable table = (JTable)e.getSource();
					table.requestFocusInWindow();
				}
			};
			advancedTable.getActionMap().put(im.get(shiftTab), shiftTabActionWrapper);
		}

		// 0 = Join Operator
		{
			final CComboBox<ValueNamePair> joinEditor = new CComboBox<>(new ValueNamePair[] { JOIN_AND, JOIN_OR });
			joinEditor.enableAutoCompletion();
			final FindCellEditor dce = new FindCellEditor(joinEditor);
			final TableColumn tc = advancedTable.getColumnModel().getColumn(INDEX_JOIN);
			tc.setPreferredWidth(30);
			tc.setCellEditor(dce);
			tc.setHeaderValue(msgBL.translate(Env.getCtx(), "JoinElement"));
		}

		// 1 = Columns
		{
			ArrayList<ValueNamePair> items = new ArrayList<ValueNamePair>();
			for (int c = 0; c < m_findFields.length; c++)
			{
				GridField field = m_findFields[c];
				String columnName = field.getColumnName();
				String header = field.getHeader();
				if (header == null || header.length() == 0)
				{
					header = msgBL.translate(Env.getCtx(), columnName);
					if (header == null || header.length() == 0)
						continue;
				}
				if (field.isKey())
					header += (" (ID)");
				ValueNamePair pp = new ValueNamePair(columnName, header);
				// System.out.println(pp + " = " + field);
				items.add(pp);
			}
			columnValueNamePairs = new ValueNamePair[items.size()];
			items.toArray(columnValueNamePairs);
			Arrays.sort(columnValueNamePairs); // sort alpha
			columns = new CComboBox<>(columnValueNamePairs);
			columns.addActionListener(this);

			final TableColumn tc = advancedTable.getColumnModel().getColumn(INDEX_COLUMNNAME);
			tc.setPreferredWidth(150);
			final FindCellEditor dce = new FindCellEditor(columns);

			dce.addCellEditorListener(new CellEditorListener()
			{
				@Override
				public void editingCanceled(ChangeEvent ce)
				{
				}

				@Override
				public void editingStopped(ChangeEvent ce)
				{
					final int row = advancedTable.getSelectedRow();
					if (row < 0)
					{
						return; // do nothing if there is no row selected; shall not happen
					}

					final int col = advancedTable.getSelectedColumn();
					if (col != INDEX_COLUMNNAME)
					{
						// make sure we are talking about ColumnName; shall not happen because we are listening only on that column
						return;
					}

					if (dce.isCellEditorValueChanged() || dce.isCellEditorValueNull())
					{
						advancedTable.setValueAt(null, row, INDEX_VALUE);
						advancedTable.setValueAt(null, row, INDEX_VALUE2);
					}
				}
			});
			tc.setCellEditor(dce);
			tc.setHeaderValue(msgBL.translate(Env.getCtx(), "AD_Column_ID"));
		}

		// 2 = Operators
		{
			operators = new CComboBox<>(MQuery.OPERATORS);
			TableColumn tc = advancedTable.getColumnModel().getColumn(INDEX_OPERATOR);
			tc.setPreferredWidth(40);
			final FindCellEditor dce = new FindCellEditor(operators);
			tc.setCellEditor(dce);
			tc.setHeaderValue(msgBL.getMsg(Env.getCtx(), "Operator"));
		}

		// 3 = QueryValue
		{
			TableColumn tc = advancedTable.getColumnModel().getColumn(INDEX_VALUE);
			FindValueEditor fve = new FindPanelValueEditor(this, false);
			tc.setCellEditor(fve);
			tc.setCellRenderer(new ProxyRenderer(new FindPanelValueRenderer(this, false)));
			tc.setHeaderValue(msgBL.getMsg(Env.getCtx(), "QueryValue"));
		}

		// 4 = QueryValue2
		{
			TableColumn tc = advancedTable.getColumnModel().getColumn(INDEX_VALUE2);
			tc.setPreferredWidth(50);
			FindValueEditor fve = new FindPanelValueEditor(this, true);
			tc.setCellEditor(fve);
			tc.setCellRenderer(new ProxyRenderer(new FindPanelValueRenderer(this, true)));
			tc.setHeaderValue(msgBL.getMsg(Env.getCtx(), "QueryValue2"));
		}

		columns.enableAutoCompletion();
		operators.enableAutoCompletion();

		// user query
		userQueries = MUserQuery.get(Env.getCtx(), m_AD_Tab_ID);
		String[] queries = new String[userQueries.length];
		for (int i = 0; i < userQueries.length; i++)
			queries[i] = userQueries[i].getName();
		fQueryName.setModel(new DefaultComboBoxModel<>(queries));
		fQueryName.setValue("");

		// No Row - Create one
		cmd_new();
	} // initFindAdvanced

	/**
	 * Dispose window
	 */
	public void dispose()
	{
		log.config("");

		// Remove action listener from custom fields - teo_sarca [ 1709292 ]
		for (VEditor editor : m_sEditors)
		{
			if (editor instanceof CTextField)
			{
				((CTextField)editor).removeActionListener(this);
			}
		}
		// TargetFields
		if (m_targetFields != null)
		{
			m_targetFields.clear();
		}
		m_targetFields = null;
		//
		panel.removeAll();
		// super.dispose();
		disposed = true; // metas: tsa
	} // dispose

	/**************************************************************************
	 * Action Listener
	 * 
	 * @param e
	 *            ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.info(e.getActionCommand());
		//
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			doCancel();
		else if (e.getActionCommand().equals(ConfirmPanel.A_REFRESH))
			cmd_refresh();
		//
		else if (e.getActionCommand().equals(ConfirmPanel.A_NEW))
		{
			m_query = MQuery.getNoRecordQuery(m_tableName, true);
			m_query.setUserQuery(true);
			m_total = 0;

			query(GridTabMaxRows.DEFAULT);
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
		//
		else if (e.getSource() == columns)
		{
			String columnName = null;

			Object selected = columns.getSelectedItem();
			if (selected != null)
			{
				if (selected instanceof ValueNamePair)
				{
					ValueNamePair column = (ValueNamePair)selected;
					columnName = column.getValue();
				}
				else
				{
					columnName = selected.toString();
				}
			}

			POInfo poInfo = POInfo.getPOInfo(Env.getCtx(), m_AD_Table_ID);

			final int ad_Column_ID = poInfo.getAD_Column_ID(columnName);

			final I_AD_Column adColumn = InterfaceWrapperHelper.create(Env.getCtx(), ad_Column_ID, I_AD_Column.class, ITrx.TRXNAME_None);

			// 08757
			// check if the column is columnSQL with reference
			final String columnSQL = adColumn == null ? null : adColumn.getColumnSQL();
			final boolean isColumnSQL = columnSQL != null && !columnSQL.isEmpty();
			final boolean isReference = adColumn == null ? false : adColumn.getAD_Reference_Value_ID() > 0;

			if (columnName != null)
			{
				log.config("Column: " + columnName);

				// 08757
				// make sure also the columnSQLs with reference are only getting the ID operators
				if ((isColumnSQL && isReference) || columnName.endsWith("_ID") || columnName.endsWith("_Acct"))
				{
					operators.setModel(new DefaultComboBoxModel<>(MQuery.OPERATORS_ID));
				}
				else if (columnName.startsWith("Is"))
				{
					operators.setModel(new DefaultComboBoxModel<>(MQuery.OPERATORS_YN));
				}
				else
				{
					operators.setModel(new DefaultComboBoxModel<>(MQuery.OPERATORS));
				}
			}
		}
		else if (e.getSource() == fQueryName)
		{
			Object o = fQueryName.getSelectedItem();
			if (userQueries != null && o != null)
			{
				String selected = o.toString();
				for (int i = 0; i < userQueries.length; i++)
				{
					if (userQueries[i].getName().equals(selected))
					{
						parseUserQuery(userQueries[i]);
						return;
					}
				}
			}
		}
		else
		// ConfirmPanel.A_OK and enter in fields
		{
			if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			{
				cmd_ok();
			}
			else if (e.getSource() instanceof JTextField
					&& tabbedPane.getSelectedIndex() == 0)
			{
				cmd_ok_Simple();
			}
		}
		//
		fireActionListner(e);
	} // actionPerformed

	private void parseUserQuery(MUserQuery userQuery)
	{
		String code = userQuery.getCode();
		String[] segments = code.split(Pattern.quote(SEGMENT_SEPARATOR));
		advancedTable.stopEditor(true);
		DefaultTableModel model = (DefaultTableModel)advancedTable.getModel();
		int cnt = model.getRowCount();
		for (int i = cnt - 1; i >= 0; i--)
			model.removeRow(i);

		for (int i = 0; i < segments.length; i++)
		{
			String segment = segments[i];
			if (!segment.trim().startsWith(JOIN_AND.getValue())
					&& !segment.trim().startsWith(JOIN_OR.getValue()))
			{
				segment = JOIN_AND.getValue() + FIELD_SEPARATOR + code;
			}
			String[] fields = segment.split(Pattern.quote(FIELD_SEPARATOR));
			addCriteria(null, MQuery.OPERATORS[MQuery.EQUAL_INDEX], null, null,
					JOIN_AND);
			String columnName = null;
			for (int j = 0; j < fields.length; j++)
			{
				if (j == INDEX_JOIN)
				{
					String fieldStr = fields[j].trim();
					Object joinOperator = JOIN_AND;
					if (JOIN_OR.getValue().equals(fieldStr))
						joinOperator = JOIN_OR;
					model.setValueAt(joinOperator, i, INDEX_JOIN);
				}
				else if (j == INDEX_COLUMNNAME)
				{
					for (ValueNamePair vnp : columnValueNamePairs)
					{
						if (vnp.getValue().equals(fields[j]))
						{
							model.setValueAt(vnp, i, INDEX_COLUMNNAME);
							columnName = fields[j];
							break;
						}
					}
				}
				else if (j == INDEX_OPERATOR)
				{
					for (ValueNamePair vnp : MQuery.OPERATORS)
					{
						if (vnp.getValue().equals(fields[j]))
						{
							model.setValueAt(vnp, i, INDEX_OPERATOR);
							break;
						}
					}
				}
				else if (j == INDEX_VALUE || j == INDEX_VALUE2)
				{
					GridField field = getTargetMField(columnName);
					Object value = parseString(field, fields[j]);
					model.setValueAt(value, i, j);
				}
			}
		}
		advancedTable.invalidate();
	}

	/**
	 * Change Listener (tab change)
	 * 
	 * @param e
	 *            ChangeEbent
	 */
	@Override
	public void stateChanged(final ChangeEvent e)
	{
		// log.info( "Find.stateChanged");
		setDefaultButton();
		if (tabbedPane.getSelectedIndex() == 1)
		{
			// initFindAdvanced();
			advancedTable.requestFocusInWindow();
		}
	} // stateChanged

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
		m_query = new MQuery(m_tableName);
		m_query.setUserQuery(true);
		m_query.addRestriction(Env.parseContext(Env.getCtx(), m_targetWindowNo,
				m_whereExtended, false));
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
		for (int i = 0; i < m_sEditors.size(); i++)
		{
			VEditor ved = m_sEditors.get(i);
			Object value = ved.getValue();
			if (value != null && value.toString().length() > 0)
			{
				String ColumnName = ((Component)ved).getName();
				log.fine(ColumnName + "=" + value);

				// globalqss - Carlos Ruiz - 20060711
				// fix a bug with virtualColumn + isSelectionColumn not yielding
				// results
				final GridField field = getTargetMField(ColumnName);
				final boolean isProductCategoryField = isProductCategoryField(field.getAD_Column_ID());
				final String ColumnSQL = field.getColumnSQL(false);

				// metas-2009_0021_AP1_CR064: begin
				if (value instanceof String
						&& DisplayType.isText(field.getDisplayType()) // note: at this place, a Y/N column's value is also a string
				)
				{
					FindHelper.addStringRestriction(m_query, ColumnSQL, (String)value, ColumnName, true); // metas
				}
				// metas: commented
				// if (value.toString().indexOf('%') != -1)
				// m_query.addRestriction(ColumnSQL, MQuery.LIKE_I, value.toString(), ColumnName, ved.getDisplay());
				// metas-2009_0021_AP1_CR064: end
				else if (isProductCategoryField && value instanceof Integer)
				{
					final int productCategoryId = ((Integer)value).intValue();
					m_query.addRestriction(getSubCategoryWhereClause(ColumnSQL, productCategoryId));
				}
				else
				{
					m_query.addRestriction(ColumnSQL, MQuery.EQUAL, value, ColumnName, ved.getDisplay());
				}
				/*
				 * if (value.toString().indexOf('%') != -1) m_query.addRestriction(ColumnName, MQuery.LIKE, value,
				 * ColumnName, ved.getDisplay()); else m_query.addRestriction(ColumnName, MQuery.EQUAL, value,
				 * ColumnName, ved.getDisplay());
				 */
				// end globalqss patch
			}
		} // editors

		m_isCancel = false; // teo_sarca [ 1708717 ]

		// Test for no records
		final boolean alertZeroRecords = true;
		final int noOfRecords = getNoOfRecords(m_query, alertZeroRecords);
		if (noOfRecords > 0)
		{
			query(GridTabMaxRows.of(noOfRecords));
		}
	} // cmd_ok_Simple

	private void cmd_ok()
	{
		if (tabbedPane.getSelectedIndex() == 0)
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
			query(GridTabMaxRows.of(noOfRecords));
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
		log.info("");
		m_query = null;
		m_total = TOTAL_NotAvailable;
		m_isCancel = true; // teo_sarca [ 1708717 ]
		dispose();
	} // cmd_ok

	/**
	 * Ignore
	 */
	private void cmd_ignore()
	{
		log.info("");
	} // cmd_ignore

	/**
	 * New record
	 */
	private void cmd_new()
	{
		advancedTable.stopEditor(true);
		addCriteria(null, MQuery.OPERATORS[MQuery.EQUAL_INDEX], null, null, JOIN_AND);
		advancedTable.requestFocusInWindow();
	} // cmd_new

	/**
	 * Save (Advanced)
	 */
	private void cmd_save(boolean saveQuery)
	{
		advancedTable.stopEditor(true);
		//
		m_query = new MQuery(m_tableName);
		m_query.setUserQuery(true);
		m_query.addRestriction(Env.parseContext(Env.getCtx(), m_targetWindowNo,
				m_whereExtended, false));
		StringBuffer code = new StringBuffer();
		for (int row = 0; row < advancedTable.getRowCount(); row++)
		{
			// Join Operator
			Object joinOp = advancedTable.getValueAt(row, INDEX_JOIN);
			if (joinOp == null || row == 0)
				joinOp = JOIN_AND;
			final boolean andCondition = joinOp == JOIN_AND;
			// Column
			Object column = advancedTable.getValueAt(row, INDEX_COLUMNNAME);
			if (column == null)
				continue;
			String ColumnName = column instanceof ValueNamePair
					? ((ValueNamePair)column).getValue()
					: column.toString();
			String infoName = column.toString();
			//
			GridField field = getTargetMField(ColumnName);
			if (field == null)
				continue;
			boolean isProductCategoryField = isProductCategoryField(field.getAD_Column_ID());
			String ColumnSQL = field.getColumnSQL(false);
			// Op
			Object op = advancedTable.getValueAt(row, INDEX_OPERATOR);
			if (op == null)
				continue;
			String Operator = ((ValueNamePair)op).getValue();
			// if (MQuery.LIKE.equals(Operator))
			// Operator = MQuery.LIKE_I; // metas-2009_0021_AP1_CR064

			// Value ******
			Object value = advancedTable.getValueAt(row, INDEX_VALUE);
			if (value == null)
				continue;
			Object parsedValue = parseValue(field, value);
			if (parsedValue == null)
				continue;
			String infoDisplay = value.toString();
			if (field.isLookup())
				infoDisplay = field.getLookup().getDisplay(value);
			else if (field.getDisplayType() == DisplayType.YesNo)
				infoDisplay = msgBL.getMsg(Env.getCtx(), infoDisplay);
			// Value2 ******
			Object value2 = null;
			if (MQuery.OPERATORS[MQuery.BETWEEN_INDEX].equals(op))
			{
				value2 = advancedTable.getValueAt(row, INDEX_VALUE2);
				if (value2 == null)
					continue;
				Object parsedValue2 = parseValue(field, value2);
				String infoDisplay_to = value2.toString();
				if (parsedValue2 == null)
					continue;
				m_query.addRangeRestriction(ColumnSQL, parsedValue,
						parsedValue2, infoName, infoDisplay, infoDisplay_to,
						andCondition);
			}
			else if (isProductCategoryField
					&& MQuery.OPERATORS[MQuery.EQUAL_INDEX].equals(op))
			{
				if (!(parsedValue instanceof Integer))
				{
					continue;
				}
				final int productCategoryId = ((Integer)parsedValue).intValue();
				m_query.addRestriction(getSubCategoryWhereClause(ColumnSQL, productCategoryId), andCondition);
			}
			else
			{
				m_query.addRestriction(ColumnSQL, Operator, parsedValue, infoName, infoDisplay, andCondition);
			}
			if (code.length() > 0)
				code.append(SEGMENT_SEPARATOR);
			code.append(andCondition ? JOIN_AND.getValue() : JOIN_OR.getValue()).append(FIELD_SEPARATOR); // metas
			code.append(ColumnName).append(FIELD_SEPARATOR).append(Operator)
					.append(FIELD_SEPARATOR).append(value.toString())
					.append(FIELD_SEPARATOR).append(value2 != null ? value2.toString() : "");
		}
		Object selected = fQueryName.getSelectedItem();
		if (selected != null && saveQuery)
		{
			String name = selected.toString();
			if (Check.isEmpty(name, true))
			{
				ADialog.warn(m_targetWindowNo, frame, "FillMandatory", msgBL.translate(Env.getCtx(), "Name"));
				return;
			}
			MUserQuery uq = MUserQuery.get(Env.getCtx(), m_AD_Tab_ID, name);
			if (uq == null && code.length() > 0)
			{
				uq = new MUserQuery(Env.getCtx(), 0, null);
				uq.setName(name);
				uq.setAD_Tab_ID(m_AD_Tab_ID); // red1 UserQuery [ 1798539 ]
				// taking in new field from
				// Compiere
				uq.setAD_User_ID(Env.getAD_User_ID(Env.getCtx())); // red1 - [
				// 1798539 ]
				// missing
				// in
				// Compiere
				// delayed
				// source
				// :-)
			}
			else if (uq != null && code.length() == 0)
			{
				if (uq.delete(true))
				{
					ADialog.info(m_targetWindowNo, frame, "Deleted", name);
					refreshUserQueries();
				}
				else
					ADialog.warn(m_targetWindowNo, frame, "DeleteError", name);
				return;
			}
			// else
			// return;
			uq.setCode(code.toString());
			uq.setAD_Table_ID(m_AD_Table_ID);
			//
			if (uq.save())
			{
				ADialog.info(m_targetWindowNo, frame, "Saved", name);
				refreshUserQueries();
			}
			else
				ADialog.warn(m_targetWindowNo, frame, "SaveError", name);
		}
	} // cmd_save

	private void refreshUserQueries()
	{
		Object selected = fQueryName.getSelectedItem();
		userQueries = MUserQuery.get(Env.getCtx(), m_AD_Tab_ID);
		String[] queries = new String[userQueries.length];
		for (int i = 0; i < userQueries.length; i++)
			queries[i] = userQueries[i].getName();
		fQueryName.setModel(new DefaultComboBoxModel<String>(queries));
		fQueryName.setSelectedItem(selected);
		if (fQueryName.getSelectedIndex() < 0)
			fQueryName.setValue("");
	}

	/**
	 * Checks the given column.
	 * 
	 * @param columnId
	 * @return true if the column is a product category column
	 */
	private boolean isProductCategoryField(int columnId)
	{
		X_AD_Column col = new X_AD_Column(Env.getCtx(), columnId, null);
		if (col.get_ID() == 0)
		{
			return false; // column not found...
		}
		return MProduct.COLUMNNAME_M_Product_Category_ID.equals(col.getColumnName());
	}

	/**
	 * Returns a sql where string with the given category id and all of its subcategory ids. It is used as restriction
	 * in MQuery.
	 * 
	 * @param columnSQL
	 * @param productCategoryId
	 * @return
	 */
	private String getSubCategoryWhereClause(final String columnSQL, final int productCategoryId)
	{
		// if a node with this id is found later in the search we have a loop in
		// the tree
		int subTreeRootParentId = 0;
		final StringBuilder retString = new StringBuilder(" (" + columnSQL + ") IN (");

		final String sql = " SELECT M_Product_Category_ID, M_Product_Category_Parent_ID FROM M_Product_Category";
		final Vector<SimpleTreeNode> categories = new Vector<SimpleTreeNode>(100);
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				if (rs.getInt(1) == productCategoryId)
				{
					subTreeRootParentId = rs.getInt(2);
				}
				categories.add(new SimpleTreeNode(rs.getInt(1), rs.getInt(2)));
			}
			retString.append(getSubCategoriesString(productCategoryId, categories, subTreeRootParentId));
			retString.append(") ");
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return "";
		}
		catch (AdempiereSystemError e)
		{
			log.log(Level.SEVERE, sql, e);
			return "";
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}
		return retString.toString();
	}

	/**
	 * Recursive search for subcategories with loop detection.
	 * 
	 * @param productCategoryId
	 * @param categories
	 * @param loopIndicatorId
	 * @return comma seperated list of category ids
	 * @throws AdempiereSystemError
	 *             if a loop is detected
	 */
	private String getSubCategoriesString(int productCategoryId,
			Vector<SimpleTreeNode> categories, int loopIndicatorId)
			throws AdempiereSystemError
	{
		String ret = "";
		final Iterator<SimpleTreeNode> iter = categories.iterator();
		while (iter.hasNext())
		{
			SimpleTreeNode node = iter.next();
			if (node.getParentId() == productCategoryId)
			{
				if (node.getNodeId() == loopIndicatorId)
				{
					throw new AdempiereSystemError(
							"The product category tree contains a loop on categoryId: "
									+ loopIndicatorId);
				}
				ret = ret
						+ getSubCategoriesString(node.getNodeId(), categories,
								loopIndicatorId) + ",";
			}
		}
		log.fine(ret);
		return ret + productCategoryId;
	}

	/**
	 * Simple tree node class for product category tree search.
	 * 
	 * @author Karsten Thiemann, kthiemann@adempiere.org
	 * 
	 */
	private class SimpleTreeNode
	{

		private int nodeId;

		private int parentId;

		public SimpleTreeNode(int nodeId, int parentId)
		{
			this.nodeId = nodeId;
			this.parentId = parentId;
		}

		public int getNodeId()
		{
			return nodeId;
		}

		public int getParentId()
		{
			return parentId;
		}
	}

	/**
	 * Parse Value
	 * 
	 * @param field
	 *            column
	 * @param in
	 *            value
	 * @return data type corected value
	 */
	private Object parseValue(GridField field, Object in)
	{
		if (in == null)
			return null;
		int dt = field.getDisplayType();
		try
		{
			// Return Integer
			if (dt == DisplayType.Integer
					|| (DisplayType.isID(dt) && field.getColumnName().endsWith(
							"_ID")))
			{
				if (in instanceof Integer)
					return in;
				int i = Integer.parseInt(in.toString());
				return new Integer(i);
			}
			// Return BigDecimal
			else if (DisplayType.isNumeric(dt))
			{
				if (in instanceof BigDecimal)
					return in;
				return DisplayType.getNumberFormat(dt).parse(in.toString());
			}
			// Return Timestamp
			else if (DisplayType.isDate(dt))
			{
				if (in instanceof Timestamp)
					return in;
				long time = 0;
				try
				{
					time = DisplayType.getDateFormat_JDBC()
							.parse(in.toString()).getTime();
					return new Timestamp(time);
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, in + "(" + in.getClass() + ")" + e);
					time = DisplayType.getDateFormat(dt).parse(in.toString())
							.getTime();
				}
				return new Timestamp(time);
			}
			// Return Y/N for Boolean
			else if (in instanceof Boolean)
				return ((Boolean)in).booleanValue() ? "Y" : "N";
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "Object=" + in, ex);
			String error = ex.getLocalizedMessage();
			if (error == null || error.length() == 0)
				error = ex.toString();
			StringBuffer errMsg = new StringBuffer();
			errMsg.append(field.getColumnName()).append(" = ").append(in)
					.append(" - ").append(error);
			//
			ADialog.error(0, frame, "ValidationError", errMsg.toString());
			return null;
		}

		return in;
	} // parseValue

	/**
	 * Parse String
	 * 
	 * @param field
	 *            column
	 * @param in
	 *            value
	 * @return data type corected value
	 */
	private Object parseString(GridField field, String in)
	{
		if (in == null)
			return null;
		int dt = field.getDisplayType();
		try
		{
			// Return Integer
			if (dt == DisplayType.Integer
					|| (DisplayType.isID(dt) && field.getColumnName().endsWith(
							"_ID")))
			{
				int i = Integer.parseInt(in);
				return new Integer(i);
			}
			// Return BigDecimal
			else if (DisplayType.isNumeric(dt))
			{
				return DisplayType.getNumberFormat(dt).parse(in);
			}
			// Return Timestamp
			else if (DisplayType.isDate(dt))
			{
				long time = 0;
				try
				{
					time = DisplayType.getDateFormat_JDBC().parse(in).getTime();
					return new Timestamp(time);
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, in + "(" + in.getClass() + ")" + e);
					time = DisplayType.getDateFormat(dt).parse(in).getTime();
				}
				return new Timestamp(time);
			}
			else if (dt == DisplayType.YesNo)
				return Boolean.valueOf(in);
			else
				return in;
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "Object=" + in, ex);
			return null;
		}

	} // parseValue

	/**
	 * Delete
	 */
	private void cmd_delete()
	{
		advancedTable.stopEditor(false);
		DefaultTableModel model = (DefaultTableModel)advancedTable.getModel();
		int row = advancedTable.getSelectedRow();
		if (row >= 0)
			model.removeRow(row);
		cmd_refresh();
		advancedTable.requestFocusInWindow();
	} // cmd_delete

	/**
	 * Refresh
	 */
	private void cmd_refresh()
	{
		advancedTable.stopEditor(false);
		int records = getNoOfRecords(m_query, true);
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
			log.warning("Query - over max");
		}
		else
		{
			log.log(Level.INFO, "Query={0}", m_query);
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
	} // getTotalRecords
	
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
	 * @param query
	 *            where clause for target tab
	 * @param alertZeroRecords
	 *            show dialog if there are no records
	 * @return number of selected records; if the results are more then allowed this method will return 0
	 */
	private int getNoOfRecords(final MQuery query, final boolean alertZeroRecords)
	{
		log.log(Level.CONFIG, "query={0}", query);
		
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
			log.log(Level.WARNING, "SQL could not be parsed: " + sql);
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
			log.log(Level.SEVERE, finalSQL, e);
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
			ADialog.info(m_targetWindowNo, frame, "FindZeroRecords");
		}
		else if (query != null && maxRowsChecker.isQueryMax(m_total))
		{
			//
			// More than allowed
			ADialog.error(m_targetWindowNo, frame, "FindOverMax", m_total + " > " + maxRowsChecker.getMaxQueryRecords());
			// m_total = 0; // return 0 if more than allowed - teo_sarca [ 1708717 ]
			m_total = maxRowsChecker.getMaxQueryRecords(); // task 08756: return as much as allowed
		}
		else
		{
			log.config("#" + m_total);
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
	 * @param currentCount
	 *            String representation of current/total
	 */
	private void setStatusDB(int currentCount)
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

	/**************************************************************************
	 * Grid Status Changed.
	 * 
	 * @param e
	 *            DataStatueEvent
	 */
	@Override
	public void dataStatusChanged(DataStatusEvent e)
	{
		log.config(e.getMessage());
		// Action control
		boolean changed = e.isChanged();
		bIgnore.setEnabled(changed);
		bNew.setEnabled(!changed);
		bSave.setEnabled(changed);
		bDelete.setEnabled(!changed);
	} // statusChanged

	/**
	 * Get Target MField
	 * 
	 * @param columnName
	 *            column name
	 * @return MField
	 */
	/* package */GridField getTargetMField(String columnName)
	{
		if (columnName == null)
		{
			return null;
		}
		for (int c = 0; c < m_findFields.length; c++)
		{
			GridField field = m_findFields[c];
			if (columnName.equals(field.getColumnName()))
			{
				return field;
			}
			if (columnName.equals(field.getColumnSQL(true)))
			{
				return field;
			}

			// note: this is called from AWT's replaint()!!, but it's OK, because we cached it in AdempiereBaseValidator
			final I_AD_Column column = InterfaceWrapperHelper.create(Env.getCtx(), field.getAD_Column_ID(), I_AD_Column.class, ITrx.TRXNAME_None);

			if (column == null)
			{
				continue;
			}

			if (columnName.equals(column.getColumnSQL()))
			{
				return field;
			}

		}
		return null;
	} // getTargetMField

	private class ProxyRenderer implements TableCellRenderer
	{
		/**
		 * Creates a Find.ProxyRenderer.
		 */
		public ProxyRenderer(TableCellRenderer renderer)
		{
			this.m_renderer = renderer;
		}

		/** The renderer. */
		private TableCellRenderer m_renderer;

		/**
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public Component getTableCellRendererComponent(final JTable table,
				Object value, boolean isSelected, boolean hasFocus,
				final int row, final int col)
		{
			Component comp = m_renderer.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, col);
			if (hasFocus && table.isCellEditable(row, col))
				table.editCellAt(row, col);
			return comp;
		}
	} // ProxyRenderer

	private void addSimpleSearchField(final CLabel label, final Component editor)
	{
		if (label != null)
		{
			label.setVisible(true);
		}
		editor.setVisible(true);
		editor.setMinimumSize(new Dimension(100, 23));
		//
		m_sColumn++;
		if (label != null) // may be null for Y/N
		{
			scontentPanel.add(label, new GridBagConstraints(m_sColumn * 2 - 1,
					m_sLine, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST,
					GridBagConstraints.NONE, new Insets(5, 7, 5, 5), 0, 0));
		}
		scontentPanel.add(editor, new GridBagConstraints(m_sColumn * 2,
				m_sLine, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
		//
		if (m_sColumn >= m_sColumnMax)
		{
			m_sColumn = 0;
			m_sLine++;
		}
		// metas-2009_0021_AP1_CR064: set Cursor to the first search field in
		// the search panel
		if (m_editorFirst == null)
		{
			m_editorFirst = editor;
			editor.requestFocus();
		}
	}

	public boolean isCancel()
	{
		return m_isCancel;
	}

	private void parseQuery(MQuery query)
	{
		boolean showAdvancedTab = true;
		advancedTable.stopEditor(true);

		// metas: cg: task: US119 : if is new record query don't check
		if (query.isNewRecordQuery())
		{
			return;
		}

		DefaultTableModel model = (DefaultTableModel)advancedTable.getModel();
		int cnt = model.getRowCount();
		for (int i = cnt - 1; i >= 0; i--)
			model.removeRow(i);

		int modelLine = -1;
		for (int i = 0; i < query.getRestrictionCount(); i++)
		{
			String columnName = query.getColumnName(i);
			final String op = query.getOperator(i);
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
			else if (hasDescription && hasFunction
					&& "Description".equals(columnName))
			{
				descriptionField.setValue(value);
				showAdvancedTab = false;
				continue;
			}
			else if (hasDocNo && hasFunction
					&& "DocumentNo".equals(columnName))
			{
				docNoField.setValue(value);
				showAdvancedTab = false;
				continue;
			}
			else
			{
				// Special Editors
				boolean found = false;
				for (VEditor ved : m_sEditors)
				{
					if (columnName.equals(((Component)ved).getName()))
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

			// 08880:
			// Use the InfoName when adding the criteria. It will also give the correct name for column sqls
			final String columnNameToUse = query.getInfoName(i);
			addCriteria(columnNameToUse, op, value, valueTo, andCondition ? JOIN_AND : JOIN_OR);
			model.getRowCount();
			modelLine++;
			//
			for (ValueNamePair vnp : columnValueNamePairs)
			{
				if (vnp.getValue().equals(columnName))
				{
					model.setValueAt(vnp, modelLine, INDEX_COLUMNNAME);
					// columnName = fields[j];
					break;
				}
			}
			//
			for (ValueNamePair vnp : MQuery.OPERATORS)
			{
				if (vnp.getValue().equals(op))
				{
					model.setValueAt(vnp, modelLine, INDEX_OPERATOR);
					break;
				}
			}
			//
			// GridField field = getTargetMField(columnName);
			// Object valueFixed = parseString(field, value);
			model.setValueAt(value, modelLine, INDEX_VALUE);
		}
		if (modelLine < 0 && showAdvancedTab)
			showAdvancedTab = false;
		//
		advancedTable.invalidate();
		if (showAdvancedTab)
			tabbedPane.setSelectedComponent(advancedPanel);
		else
			tabbedPane.setSelectedComponent(simplePanel);
	}

	private void cmd_reset()
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
		//
		final DefaultTableModel model = (DefaultTableModel)advancedTable.getModel();
		model.setRowCount(0);
		//
		m_query = new MQuery(m_tableName);
		m_query.setUserQuery(true);
		m_query.addRestriction(Env.parseContext(Env.getCtx(), m_targetWindowNo, m_whereExtended, false));
		m_total = 0;
	}

	/**
	 * 
	 * @param maxRows acts as a "LIMIT" for the underlying SQL.
	 */
	public void query(final GridTabMaxRows maxRows)
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

	private void addCriteria(String columnName, Object operator, Object value,
			Object value2, ValueNamePair join)
	{
		DefaultTableModel model = (DefaultTableModel)advancedTable.getModel();
		if (model.getRowCount() == 0 || join == null)
		{
			join = JOIN_AND;
		}
		model.addRow(new Object[] { join, columnName, operator, value, value2 });
	}

	private void setFrame(Window frame)
	{
		this.frame = frame;
		setDefaultButton();
	}

	private void setDefaultButton()
	{
		if (tabbedPane.getSelectedIndex() == 0)
		{
			setDefaultButton(confirmPanelSimple.getOKButton());
		}
		else
		{
			setDefaultButton(confirmPanelAdvanced.getOKButton());
		}
	}

	private void setDefaultButton(JButton button)
	{
		if (this.frame instanceof RootPaneContainer)
		{
			RootPaneContainer c = (RootPaneContainer)this.frame;
			c.getRootPane().setDefaultButton(confirmPanelSimple.getOKButton());
		}
	}

	protected EventListenerList listenerList = new EventListenerList();

	/* package */void addActionListener(ActionListener l)
	{
		listenerList.add(ActionListener.class, l);
	}

	private void fireActionListner(ActionEvent e)
	{
		ActionListener[] listeners = listenerList
				.getListeners(ActionListener.class);
		for (int i = listeners.length - 1; i >= 0; i -= 1)
		{
			listeners[i].actionPerformed(e);
		}
	}

	/**
	 * Get Icon with name action
	 * 
	 * @param name
	 *            name
	 * @param small
	 *            small
	 * @return Icon
	 */
	private ImageIcon getIcon(String name, boolean small)
	{
		String fullName = name + (small ? "16" : "24");
		return Images.getImageIcon2(fullName);
	} // getIcon
	
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
		if(m_editorFirst == null)
		{
			return false;
		}
		
		return super.isFocusable();
	}
	
	@Override
	public void setFocusable(final boolean focusable)
	{
		// ignore it
	}

	@Override
	public void requestFocus()
	{
		if (m_editorFirst != null)
		{
			m_editorFirst.requestFocus();
		}
		else
		{
			super.requestFocus();
		}
	}

	@Override
	public boolean requestFocusInWindow()
	{
		if (m_editorFirst != null)
		{
			return m_editorFirst.requestFocusInWindow();
		}
		else
		{
			return super.requestFocusInWindow();
		}
	}

	// metas: begin
	private boolean disposed = false;

	public boolean isDisposed()
	{
		return disposed;
	}
} // Find
