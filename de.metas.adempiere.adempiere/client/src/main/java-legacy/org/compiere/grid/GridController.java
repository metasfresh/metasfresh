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
 * @contributor Victor Perez , e-Evolution.SC FR [ 1757088 ]                  *
 * @contributor fer_luck @ centuryon                                          *
 *****************************************************************************/
package org.compiere.grid;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.adempiere.plaf.AdempiereTabbedPaneUI;
import org.adempiere.process.event.IProcessEventSupport;
import org.adempiere.ui.sideactions.swing.SideActionsGroupsListPanel;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.apps.APanel;
import org.compiere.apps.AppsAction;
import org.compiere.apps.search.FindPanel;
import org.compiere.apps.search.FindPanelContainer;
import org.compiere.grid.ed.VCellEditor;
import org.compiere.grid.ed.VCellRenderer;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VEditor2;
import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.grid.ed.VManagedEditor;
import org.compiere.grid.ed.VRowIDEditor;
import org.compiere.grid.ed.VRowIDRenderer;
import org.compiere.grid.ed.VString;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.grid.tree.VTreePanel;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.GridTabLayoutMode;
import org.compiere.model.GridTabMaxRows;
import org.compiere.model.GridTable;
import org.compiere.model.GridWindow;
import org.compiere.model.I_AD_Color;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.TableCellNone;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;

import de.metas.logging.LogManager;
import de.metas.util.IColorRepository;
import de.metas.util.MFColor;


/**
 *  The Grid Controller is the panel for single and multi-row presentation
 *  and links to the Model Tab.
 *
 *  <pre>
 *  UI Structure:
 *  this    (BorderLayout)
 *      splitPane (JSplitPane)
 *          left
 *              graphicPanel
 *          right
 *              cardPanel   JPanel  (CardLayout)
 *                  srPane  JSplitPane
 * 						vPane	JScrollPane
 *              	        vPanel  VPanel (GridBagLayout)
 * 						vIncludedGC	GridController
 *                  mrPane  JScrollPane
 *                      vTable  VTable
 * </pre>
 *
 *  <B>DataBinding:<B>
 * <pre>
 *  - MultiRow - is automatic between VTable and MTable
 *  - SingleRow
 *		- from VEditors via fireVetoableChange(m_columnName, null, getText());
 *			(vetoableChange)
 *		- to VEditors via updateSingleRow -> Editor.setValue(object)
 *
 *  Event Chains
 *  -- Navigation --
 *  (VTable selection -> GridController.valueChanged)
 *  (APanel selection)
 *      + MTab.navivate
 *          + MTab.setCurrentRow
 *              + Update all MFields
 *                  + MField.setValue
 *                      + setContext
 *                      + fire PropertyChange "Value"
 *                          + VEditor.propertyChange
 *                              + VEditor.setValue
 *              + MTab.fireProperyChange "CurrentRow"
 *                  + VTable.propertyChange (setRowSelectionInterval)
 *                      + GridController.valueChange
 *                          + GridController.dynamicDisplay(complete)
 *              + MTab.fireDataStatusChanged
 *                  + APanel.statusChanged
 *
 *  -- ValueChanges --
 *  VEditor.fireVetoableChange
 *      + (VCellEditor.vetoableChange/getCellEditorValue)   -- multi-row source
 *      + (GridController.vetoableChange)                   -- single-row source
 *          + MTable.setValueAt
 *              + MField.setValue
 *                  + setContext
 *                  + fire PropertyChange "Value"
 *                      + VEditor.setValue
 *              + MTable.fireDataStatusChanged
 *                  + MTab.dataStatusChanged
 *                      + MTab.fireDataStatusChanged
 *                          + APanel.statusChanged
 *                  + GridController.dataStatusChanged
 *                      + GridController.dynamicDisplay(selective)
 *  </pre>
 *  
 * @author  Jorg Janke
 * @version $Id: GridController.java,v 1.8 2006/09/25 00:59:52 jjanke Exp $
 * 
 * @author Teo Sarca - BF [ 1742159 ], BF [ 1707876 ]
 * @author the guys at Schaeffer-AG: Persistent user based column visibility
 * @contributor Victor Perez , e-Evolution.SC FR [ 1757088 ]
 * @contributor fer_luck @ centuryon  FR [ 1757088 ]
 */
public final class GridController extends CPanel
	implements DataStatusListener, ListSelectionListener, Evaluatee,
		VetoableChangeListener,	PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308782933999556880L;
	
	public static final Builder builder()
	{
		return new Builder();
	}

	// services
	private static final transient Logger log = LogManager.getLogger(GridController.class);
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

	/**
	 *  Constructor - you need to call initGrid for instantiation
	 */
	@Deprecated
	public GridController()
	{
		super();
		
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			log.error("", e);
		}
	}

	private GridController(final Builder builder)
	{
		super();
		final APanel aPanel = builder.getAPanel(); // could be null
		this.alignVerticalTabsWithHorizontalTabs = !builder.isIncludedTab() && aPanel != null && aPanel.isAlignVerticalTabsWithHorizontalTabs();
		
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			log.error("", e);
		}

		final MFColor backgroundColor = builder.getBackgroundColor();
		if (backgroundColor != null)
		{
			setBackgroundColor(backgroundColor);
		}

		initGrid(
				builder.getGridTab(), // mTab,
				builder.isGridModeOnly(), // onlyMultiRow,
				builder.getWindowNo(), // WindowNo,
				aPanel, // APanel
				builder.getGridWindow(), // mWindow,
				builder.isLazyInitialization() // lazyInitialization
		);
		
		//
		// Bindings
		addDataStatusListener(aPanel);
		registerESCAction(builder.getIgnoreAction()); //  register Escape Key
		
		if (builder.isGoSingleRowLayout())
		{
			switchSingleRow();
		}
	}
	
	/**
	 *  toString
	 *  @return string representation
	 */
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("tab", m_mTab)
				.toString();
	}   //  toString
	
	public boolean isAlignVerticalTabsWithHorizontalTabs()
	{
		return alignVerticalTabsWithHorizontalTabs;
	}
	
	private boolean alignVerticalTabsWithHorizontalTabs = false;

	/**
	 * Main panel:
	 * <ul>
	 * <li>Left - the {@link #graphPanel} (e.g. were we will put the tree if needed)
	 * <li>Right - the actual content (single/multi view panels)
	 * </ul>
	 */
	private JSplitPane splitPane = new JSplitPane();
	
	/** {@link #splitPane}'s left component (i.e. displays the tree if any) */
	private CPanel graphPanel = new CPanel();
	/** Tree Panel (optional)       */
	private VTreePanel  treePanel;

	/** The tab content layout, contains single row layout ({@link #vPanel}) and grid layout ({@link #vTable}) */
	private final CPanel cardPanel = new CPanel();
	private final CardLayout cardLayout = new CardLayout();
	private static final String CARDNAME_SingleRowView = "SingleRowView";
	private static final String CARDNAME_MultiRowView = "MultiRowView";
	
	/** Single-row scroll pane with horizontal tabs */
	private VPanel vPanel = null;
	/** Multi-row grid panel */
	private VTable vTable = new VTable();
	/**
	 * Optional tabbed pane, with one tab which contains the {@link #vTable}. Used for vertical tabs with horizontal tabs alignment.
	 * 
	 * @see #isAlignVerticalTabsWithHorizontalTabs()
	 */
	private CTabbedPane vTableTabbedPane = null;

	private boolean detailGrid = false;
	
	private FindPanelContainer findPanel = null; // metas: teo_sarca
	
	private APanel _aPanel; // metas: 02553: renamed variable name to make sure is not used directly

	/**
	 * Listens on {@link VEditor}s and forward the action performed event to {@link #_aPanel}.
	 * 
	 * We use this man-in-the-middle listener because we want to change the {@link #_aPanel} on fly
	 */
	// metas: 02553
	private final ActionListener editor2APanelDelegateListener = new ActionListener()
	{
		
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			if (_aPanel != null)
			{
				_aPanel.actionPerformed(e);
			}
		}
	};

	/**
	 * @return the underlying grid table (i.e. multi-row display)
	 */
	public final VTable getVTable()
	{
		return vTable;
	}
	
	/**
	 *  Static Layout init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setLayout(new BorderLayout());
		
		//
		// Main panel - Center
		{
			this.add(splitPane, BorderLayout.CENTER);
			splitPane.setOpaque(false);
			splitPane.setBorder(BorderFactory.createEmptyBorder());
			splitPane.setName("gc_splitPane");

			// Left - the graph panel (e.g. were we will put the tree if needed)
			{
				splitPane.setLeftComponent(graphPanel);
				graphPanel.setLayout(new BorderLayout());
				graphPanel.setBorder(BorderFactory.createEmptyBorder());
				graphPanel.setName("gc_graphPanel");
			}

			// Right - the actual content (single/multi view panels)
			{
				splitPane.setRightComponent(cardPanel);
				cardPanel.setLayout(cardLayout);
				cardPanel.setBorder(BorderFactory.createEmptyBorder());
				cardPanel.setName("gc_cardPanel");
				
				// Single view panel
				{
				}
				
				// Multi view (grid mode) panel
				{
					final CScrollPane vTableScrollPane = new CScrollPane();
					vTableScrollPane.setName("gc_mrPane");
					vTableScrollPane.setBorder(BorderFactory.createEmptyBorder());

					// bugfix/workaround: Change scrollmode from BLIT_SCROLL_MODE to BACKINGSTORE_SCROLL_MODE, because in some cases (see task description),
					// the JTable inside the scroll pane is weird painted when scrolling up and down.
					// It could be related to a particular JVM, OS, graphics hardware, but we reproduced on several machines....
					// See also:
					// * http://stackoverflow.com/questions/23886275/jscrollpane-visual-glitch-when-scrolling
					vTableScrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE); // task 09389
					
					vTableScrollPane.setViewportView(vTable);

					//
					// Do we have to do vertical tabs to horizontal tabs alignment?
					if (isAlignVerticalTabsWithHorizontalTabs())
					{
						this.vTableTabbedPane = new CTabbedPane(CTabbedPane.TOP);
						vTableTabbedPane.setHideIfOneTab(false);
						vTableTabbedPane.addTab("", vTableScrollPane); // tab name will be set later when it's available (now it's not)
					}
					
					// Add it to main panel
					cardPanel.add(vTableTabbedPane != null ? vTableTabbedPane : vTableScrollPane, CARDNAME_MultiRowView);
				}
			}
		}
	}   //  jbInit

	/**
	 *  Dispose
	 */
	public void dispose()
	{
		log.debug("Disposing: {}", m_mTab);
		
		stopEditor(false);
		
		//  clear info
		if (m_mTab != null)
		{
			if (m_mTab.isLoadComplete())
			{
				if (m_mTab.needSave(true, false))
					m_mTab.dataIgnore();
			}
	
			//  Listeners
			if (m_mTab.isLoadComplete())
			{
				m_mTab.getTableModel().removeDataStatusListener(this);
				m_mTab.getTableModel().removeVetoableChangeListener(this);
			}
			m_mTab.removePropertyChangeListener(vTable);
		}
		if (vTable != null)
		{
			vTable.getSelectionModel().removeListSelectionListener(this);
		}

		//
		// Single row layout editors
		if (vPanel != null && m_mTab != null)
		{
			for (final String columnName : vPanel.getEditorColumnNames())
			{
				final VEditor vEditor = vPanel.getEditor(columnName);
				vEditor.removeVetoableChangeListener(this);
				final GridField mField = m_mTab.getField(columnName);
				if (mField != null)
					mField.removePropertyChangeListener(vEditor);
				vEditor.dispose();
			}
		}
		
		// TODO Remove APanel Button listeners
		
		// metas: clear colors cache for current window (#02530)
		if (m_mTab != null) // metas
		{
			s_cacheColors.remove(m_mTab.getWindowNo()); // metas
		}
		
		if (vTableTabbedPane != null)
		{
			vTableTabbedPane.removeAll();
			vTableTabbedPane = null;
		}
		if(vTable != null)
		{
			vTable.removeAll();
			vTable.setModel(new DefaultTableModel());   //  remove reference
			vTable = null;
		}
		
		if (vPanel != null)
		{
			vPanel.removeAll();
			vPanel = null;
		}

		if (splitPane != null)
		{
			splitPane.removeAll();
			splitPane = null;
		}
		
		m_mTab = null;
		
		if(treePanel != null)
		{
			// metas: remove the tree panel from the event support listeners
			Services.get(IProcessEventSupport.class).removeListener(treePanel);
			treePanel = null;
		}
		
		this.removeAll();
	}   //  dispose

	/** Model Tab                   */
	private GridTab		m_mTab = null;
	/** Window                      */
	private int         m_WindowNo;
	/** Only Multi-Row exist        */
	private boolean     m_onlyMultiRow = false;
	/** Single/Multi Row indicator  */
	private boolean     m_singleRow = true;
	/** Veto Active                 */
	private boolean     m_vetoActive = false;

	/**
	 * This member is set to <code>true</code> at the end of the {@link #initIfNeeded()} method.
	 */
	private boolean initialized;

	private final List<GridSynchronizer> synchronizerList = new ArrayList<>();

	/**
	 * 
	 * @param mTab
	 * @param onlyMultiRow
	 * @param WindowNo
	 * @param aPanel
	 * @param mWindow
	 * @return
	 * @deprecated Please use {@link #builder()}
	 */
	@Deprecated
	public boolean initGrid (final GridTab mTab,
			final boolean onlyMultiRow, 
			final int WindowNo,
			final APanel aPanel,
			final GridWindow mWindow)
	{
		final boolean lazyInitialization = false;
		return initGrid(mTab, onlyMultiRow, WindowNo, aPanel, mWindow, lazyInitialization);
	}
	
	/**************************************************************************
	 *  Init Grid.
	 *  <pre>
	 *  - Map table to model
	 *  - Update (multi-row) table info with renderers/editors
	 *  - build single-row panel
	 *  - initialize display
	 *  </pre>
	 *  @param mTab tab
	 *  @param onlyMultiRow only table
	 *  @param WindowNo window no
	 *  @param aPanel optional Application Panel for adding button listeners
	 * 	@param mWindow parent Window Model
	 *  @return true if initialized
	 */
	private final boolean initGrid (final GridTab mTab,
			final boolean onlyMultiRow, 
			final int WindowNo,
			final APanel aPanel,
			final GridWindow mWindow,
			final boolean lazyInitialization)
	{
		log.debug("Init: {}", mTab);
		
		Check.assumeNotNull(mTab, "mTab not null");
		m_mTab = mTab;
		m_WindowNo = WindowNo;
		m_onlyMultiRow = onlyMultiRow;
		setAPanel(aPanel);
		setName("GC-" + mTab);
		setTabLevel(m_mTab.getTabLevel());

		//
		// Single row layout
		{
			vPanel = VPanel.newStandardWindowPanel(mTab.getName(), m_WindowNo);
			setupVPanel(); // make sure VPanel is loaded before we are adding it to parent component.
			cardPanel.add(vPanel, CARDNAME_SingleRowView);
		}

		//
		// Init if needed
		if (!lazyInitialization)
		{
			initIfNeeded();
		}
		else
		{
			//Load tab meta data, needed for includeTab to work
			m_mTab.initTab(false); //async=false
		}

		// metas-2009_0021_AP1_G113: begin
		if (m_mTab.isSearchActive())
		{
			try
			{
				findPanel = FindPanel.builder()
						.setGridController(this)
						.setTargetWindowNo(m_WindowNo) // FIXME: i think is redundant
						// .setReadOnly(m_mTab.isReadOnly()) // NOTE: not used anyways
						.setSmall(true)
						.setEmbedded(true) // the panel is embedded (we expect some of the buttons of find panel to be hidden because they make no sense)
						.buildFindPanelContainer();
				this.add(findPanel.getComponent(), BorderLayout.NORTH); // metas: teo_sarca
			}
			catch (Exception e)
			{
				log.warn("Failed creating the find panel", e);
				// m_mTab.setSearchActive(false); // TODO: introduce method
			}
		}
		// metas-2009_0021_AP1_G113: end
		
		//
		// Side actions panel
		if (!m_mTab.isDetail())
		{
			final SideActionsGroupsListPanel actionsSidePanel = new SideActionsGroupsListPanel();
			actionsSidePanel.setModel(m_mTab.getSideActionsGroupsModel());
			this.add(actionsSidePanel, BorderLayout.EAST);
		}

		return true;
	} // initGrid
	
	private final void initIfNeeded()
	{
		if (initialized)
		{
			return;
		}
		
		//  Multi Row layout (i.e. grid)
		setupVTable();

		//  Set Color on Tab Level
		//  this.setBackgroundColor (mTab.getColor());

		//  Single Row  -------------------------------------------------------
		setupVPanel();

		//
		//  Tree Graphics Layout
		try
		{
			initTree();
		}
		catch (Exception e)
		{
			log.error(e.getLocalizedMessage(), e);
		}
		if (treePanel != null)
		{
			AdempiereTabbedPaneUI.applyTabbedPaneTopGapToLeftComponent(splitPane);
			
			graphPanel.add(treePanel, BorderLayout.CENTER);
			splitPane.setDividerLocation(250);
		//	splitPane.resetToPreferredSizes();
		}
		else    //  No Graphics - hide
		{
			graphPanel.setPreferredSize(new Dimension(0,0));
			splitPane.setDividerSize(0);
			splitPane.setDividerLocation(0);
		}

		//  Receive DataStatusChanged info from MTab
		m_mTab.addDataStatusListener(this);
		//  Receive vetoableChange info from MTable when saving
		m_mTab.getTableModel().addVetoableChangeListener(this);
		//	Selection Listener -> valueChanged
		vTable.getSelectionModel().addListSelectionListener(this);
		//  Navigation (RowChanged)
		m_mTab.addPropertyChangeListener(vTable);

		//  Update UI
		vTable.autoSize(true);

		//  Set initial presentation
		if (m_onlyMultiRow || !m_mTab.isSingleRow())
		{
			switchMultiRow();
		}
		else
		{
			switchSingleRow();
		}
		
		initialized = true;
	}
	
	private static final String PROPERTYNAME_ComponentInitialized = "org.compiere.grid.GridController.component.initialized";
	/**
	 * Flag given component as "initialized". 
	 * @param comp
	 * @return <ul>
	 * <li>true if the component was flagged right now
	 * <li>false if the component was already flagged as initialized
	 * </ul>
	 */
	private static final boolean markComponentInitialized(final JComponent comp)
	{
		if (comp.getClientProperty(PROPERTYNAME_ComponentInitialized) != null)
		{
			return false; // already initialized
		}
		comp.putClientProperty(PROPERTYNAME_ComponentInitialized, true);
		return true;
	}


	/**
	 * Setup single row layout panel.
	 * 
	 * Mainly, it is:
	 * <ul>
	 * <li>adding the fields to panel
	 * <li>if the {@link VPanel} was already initialized, it does nothing
	 * </ul>
	 * 
	 * If the VPanel was already initialized, this method does nothing, so it's safe to call it as many times as you want.
	 */
	private final void setupVPanel()
	{
		Check.assumeNotNull(vPanel, "vPanel not null");
		
		// If the tab fields were not yet loaded, postpone the initialization 
		if (!m_mTab.isLoadComplete())
		{
			return;
		}
		
		//
		// Mark the component as initialized and do nothing if it was already initialized
		if (!markComponentInitialized(vPanel))
		{
			return;
		}

		//
		// Do nothing if single row layout is not allowed
		if (m_onlyMultiRow)
		{
			return;
		}
		
		//
		// Do nothing if tab is not displayed
		if (!m_mTab.isDisplayed())
		{
			return;
		}
		
		// Hide the tabs if there will be only one horizontal tab
		// NOTE: we hide if one tab, only if aligning vertical tabs with panel's horizontal tabs is NOT enabled (because we need to align with horizontal tabs anyway)
		vPanel.setHideIfOneTab(!isAlignVerticalTabsWithHorizontalTabs());

		//
		//	Set Softcoded Mnemonic &x
		final int fieldCount = m_mTab.getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			final GridField mField = m_mTab.getField(fieldIndex);
			if (mField.isDisplayed(GridTabLayoutMode.SingleRowLayout))
			{
				vPanel.setMnemonic(mField);
			}
		}   //  for all fields
		
		//
		//	Add Fields to VPanel
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			final GridField mField = m_mTab.getField(fieldIndex);
			if (mField.isDisplayed(GridTabLayoutMode.SingleRowLayout))
			{
				final VEditor vEditor = swingEditorFactory.getEditor(m_mTab, mField, false);
				if (vEditor == null)
				{
					log.warn("Editor not created for " + mField.getColumnName());
					continue;
				}
				//  MField => VEditor - New Field value to be updated to editor
				mField.addPropertyChangeListener(vEditor);
				//  VEditor => this - New Editor value to be updated here (MTable)
				vEditor.addVetoableChangeListener(this);
				//  Add to VPanel
				vPanel.addField(vEditor);
				//  APanel Listen to buttons
				if (mField.getDisplayType() == DisplayType.Button)
				{
					// metas: 02553: use panel delegate to be able to change aPanel reference on fly
					vEditor.addActionListener(editor2APanelDelegateListener);
				}
			}
		}   //  for all fields
	}
	
	private void initTree() //metas
	{
		treePanel = null;
		if (!m_mTab.isTreeTab())
		{
			return;
		}
		
		// note: we create our tree panel now, but we will only load something into it if
		// 1. this is the "root" tab
		// 2. there is a default AD_Tree_ID for the current client and key column
		treePanel = new VTreePanel(m_WindowNo, false, true); // hasBar=false, editable=true
		
		if (m_mTab.getTabNo() > 0)	
		{
			return; // initialize the tab later (in activate())
		}
		
		final int AD_Tree_ID = getAD_Tree_ID();
		if (AD_Tree_ID <= 0)
		{
			return; // there is no tree for us
		}
		treePanel.initTree(AD_Tree_ID);
		
		// metas: register the tree panel to be informed of records
		// changed by processes.
		Services.get(IProcessEventSupport.class).addListener(treePanel);
		// metas end

		treePanel.addPropertyChangeListener(VTreePanel.PROPERTY_ExecuteNode, this);
	}
	
	/**
	 * Include Tab
	 * 
	 * @param sync grid synchronizer which contains the child tab to be included
	 */
	//FR [ 1757088 ]
	public void includeTab (final GridSynchronizer sync)
	{	
		Check.assume(this == sync.getParent(), "Valid GridSynchronizer parent");
		
		final GridController detail = sync.getChild();
		
		detail.setDetailGrid(true);
		detail.setGCParent(this);
		detail.enableEvents(AWTEvent.HIERARCHY_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK);
		
		vPanel.includeTab(detail);
		
		synchronizerList.add(sync);
	}

	//FR [ 1757088 ]
	private void setDetailGrid(final boolean detailGrid)
	{
		this.detailGrid = detailGrid;
	}
	
	public boolean isDetailGrid()
	{
		return detailGrid;
	}
	/**
	 * 	Get Title
	 *	@return title
	 */
	public String getTitle ()
	{
		return m_mTab.getName();
	}	//	getTitle
	
	/**
	 * Setup Multi-Row Table (add fields)
	 */
	private void setupVTable()
	{
		//
		// Do nothing if already initialized
		if (!markComponentInitialized(vTable))
		{
			return;
		}

		//
		// Do nothing if tab is not displayed
		if (!m_mTab.isDisplayed())
		{
			return;
		}

		//
		// Set the title of grid layout's first tab (if any)
		if (vTableTabbedPane != null)
		{
			vTableTabbedPane.setTitleAt(0, m_mTab.getName());
		}
		
		vTable.setColumnControlVisible(false); // don't show it because we are showing it in toolbar

		// Set TableModel
		// ... and check if we got the right number of columns
		vTable.setModel(m_mTab.getTableModel());
		//
		final int fieldCount = m_mTab.getFieldCount();
		final TableColumnModel tcm = vTable.getColumnModel();
		if (fieldCount != tcm.getColumnCount())
		{
			throw new IllegalStateException("TableColumn Size <> TableModel");
		}

		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			final GridField mField = m_mTab.getField (fieldIndex);
			TableColumn tc = tcm.getColumn(fieldIndex);

			// make sure the column identifier equals the colNamed no matter of
			// localization or other UI related tweaks.
			tc.setIdentifier(mField.getColumnName());

			tc.setMinWidth(30);
			//
			if (mField.getColumnName().equals(tc.getIdentifier().toString()))
			{
				//don't show included tab field in grid
				if (mField.getIncluded_Tab_ID() > 0) 
				{
					TableCellNone tcn = new TableCellNone(mField.getColumnName());
					tc.setCellRenderer (tcn);
					tc.setCellEditor (tcn);
					tc.setHeaderValue (null);
					tc.setMinWidth (0);
					tc.setMaxWidth (0);
					tc.setPreferredWidth (0);
				} 
				else if (mField.getDisplayType () == DisplayType.RowID)
				{
					tc.setCellRenderer (new VRowIDRenderer (false));
					tc.setCellEditor (new VRowIDEditor (false));
					tc.setHeaderValue ("");
					tc.setMaxWidth (2);
				}
				else
				{
					//  need to set CellEditor explicitly as default editor based on class causes problem (YesNo-> Boolean)
					if (mField.isDisplayable())
					{
						tc.setCellRenderer (new VCellRenderer (mField));
						
						final VCellEditor ce = new VCellEditor (vTable, mField);
						tc.setCellEditor (ce);
						//
						tc.setHeaderValue (mField.getHeader ());
						// NOTE: the Min/Max/Preferred Width is updated in CTable
						tc.setHeaderRenderer (new VHeaderRenderer (mField.getDisplayType ()));

						//  Enable Button actions in grid
						if (mField.getDisplayType () == DisplayType.Button)
						{
							ce.setActionListener(editor2APanelDelegateListener); // metas: 02553: use panel delegate
						}
						
//						final boolean visible = mField.getVO().isDisplayedGrid();
//						table.setColumnVisibility(tc, visible);
					}
					else //  column not displayed
					{
						final TableCellNone tcn = new TableCellNone(mField.getColumnName());
						tc.setCellRenderer (tcn);
						tc.setCellEditor (tcn);
						tc.setHeaderValue (null);
						tc.setMinWidth (0);
						tc.setMaxWidth (0);
						tc.setPreferredWidth (0);
					}
				}
			}	//	found field
			else
			{
				log.error("TableColumn " + tc.getIdentifier () + " <> MField " + mField.getColumnName() + mField.getHeader());
			}
		} 	//  for all fields
		
		// metas
		CTableColumns2GridTabSynchronizer.setup(vTable, m_mTab);
	} // setupVTable

	/**
	 * 	Activate Grid Controller.
	 * 	Called by APanel when GridController is displayed (foreground)
	 */
	public void activate()
	{
		initIfNeeded();
		
		//	Tree to be initiated on second/.. tab
		if (m_mTab.isTreeTab() && m_mTab.getTabNo() != 0)
		{
			if (treePanel != null)
			{
				final int AD_Tree_ID = getAD_Tree_ID();
				treePanel.initTree (AD_Tree_ID);
				treePanel.addPropertyChangeListener(VTreePanel.PROPERTY_ExecuteNode, this);
			}
		}
		
		activateChilds();
	}	//	activate

	private int getAD_Tree_ID()
	{
		final String keyColumnName = m_mTab.getKeyColumnName();
		String treeName = "AD_Tree_ID";
		
		// determine the tree name (FIXME should be done via some SPI mechanism)
		if (keyColumnName.startsWith("CM"))
		{
			if (keyColumnName.equals("CM_Container_ID"))
			{
				treeName = "AD_TreeCMC_ID";
			}
			else if (keyColumnName.equals("CM_CStage_ID"))
			{
				treeName = "AD_TreeCMS_ID";
			}
			else if (keyColumnName.equals("CM_Template_ID"))
			{
				treeName = "AD_TreeCMT_ID";
			}
			else if (keyColumnName.equals("CM_Media_ID"))
			{
				treeName = "AD_TreeCMM_ID";
			}
		}
		
		// look for the AD_Tree_ID in the context (=>if parent tab has an AD_Field, it should be there)
		int AD_Tree_ID = Env.getContextAsInt (Env.getCtx(), m_WindowNo, treeName);
		if (AD_Tree_ID <= 0)
		{
			// get the default tree for the current AD_Client and column name
			AD_Tree_ID = MTree.getDefaultAD_Tree_ID(
					Env.getAD_Client_ID(Env.getCtx()),
					m_mTab.getKeyColumnName());
		}
		return AD_Tree_ID;
	}

	/**
	 * activate child grid controller ( included tab )
	 */
	private void activateChilds()
	{
		for (GridSynchronizer s : synchronizerList )
		{
			s.activateChild();
		}
	}
	
	private void refreshChildren()
	{
		for (GridSynchronizer s : synchronizerList )
		{
			s.refreshChild();
		}
	}
	
	/**
	 * Gets child {@link GridController}s (i.e. included ones)
	 * @return
	 */
	public List<GridController> getChildGridControllers()
	{
		if (synchronizerList.isEmpty())
		{
			return Collections.emptyList();
		}
		
		final List<GridController> children = new ArrayList<>(synchronizerList.size());
		for (final GridSynchronizer s : synchronizerList)
		{
			final GridController child = s.getChild();
			if (children.contains(child))
			{
				continue;
			}
			children.add(child);
		}

		return children;
	}

	public GridController findChild(GridTab gTab)
	{
		GridController gc = null;
		for (GridSynchronizer s : synchronizerList )
		{
			if (s.getChild().getMTab().equals(gTab))
			{
				gc = s.getChild();
				break;
			}
		}
		return gc;
	}

	/**
	 *  Register ESC Actions
	 *  - overwrite VTable's Keystrokes assignment for ESC
	 *  @param aIgnore ignore
	 */
	public void registerESCAction (AppsAction aIgnore)
	{
		int c = VTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
		vTable.getInputMap(c).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), aIgnore.getName());
		vTable.getActionMap().put(aIgnore.getName(), aIgnore);
	}   //  registerESCAction

	/**
	 *  Query Tab and resize Table
	 *  (called from APanel)
	 *  @param onlyCurrentRows only current rows
	 *  @param onlyCurrentDays how many days back
	 *  @param maxRows maximum rows restriction
	 */
	public void query (final boolean onlyCurrentRows, final int onlyCurrentDays, final GridTabMaxRows maxRows)
	{
		//  start loading while building screen
		m_mTab.query(onlyCurrentRows, onlyCurrentDays, maxRows);

		//
		//  Update UI
		if (!isSingleRow())
		{
			vTable.autoSize(true);
		}
		else
		{
			// Make sure the single row layout was setup
			setupVPanel();
		}
		
		//
		// Activate included tabs
		activateChilds();
		
		//
		// Update the tree panel (if any)
		if (treePanel != null)
		{
			final List<Integer> ids = m_mTab.getKeyIDs();
			treePanel.filterIds(ids);
		}
		// metas: end
	}   //  query
	
	/**************************************************************************
	 *  Switch from single to multi & vice versa
	 */
	public void switchRowPresentation()
	{
		stopEditor(true);
		if (m_singleRow)
			switchMultiRow();
		else
			switchSingleRow();
	}   //  switchRowPresentation

	/**
	 *  Switch to SingleRow Presentation
	 */
	public void switchSingleRow()
	{
		if (m_onlyMultiRow)
		{
			return;
		}
		
		cardLayout.show(cardPanel, CARDNAME_SingleRowView);
		m_singleRow = true;
		dynamicDisplay(0);
		refreshChildren();
	//	vPanel.requestFocus();
	}   //  switchSingleRow

	/**
	 *  Switch to MultiRow Presentation
	 */
	public void switchMultiRow()
	{
		cardLayout.show(cardPanel, CARDNAME_MultiRowView);
		
		m_singleRow = false;
		// metas: cg: task 04771 start
		if (m_mTab.isOpen())
		{
			final int currentRow = m_mTab.getCurrentRow();
			if (currentRow != vTable.getSelectedRow())
			{
				vTable.setRowSelectionInterval(currentRow, currentRow);
			}
		}
		// metas: cg: task 04771 end
		vTable.autoSize(true);	//	resizes
	//	vTable.requestFocus();
	}   //  switchSingleRow

	/**
	 *  Is Single Row presentation
	 *  @return true if Single Row is displayed
	 */
	public boolean isSingleRow()
	{
		return m_singleRow;
	}   //  isSingleRow

	
	/**************************************************************************
	 *  Remove Listener - pass on to MTab
	 *  @param l listener
	 */
	public synchronized void removeDataStatusListener(DataStatusListener l)
	{
		if(m_mTab != null)
		{
			m_mTab.removeDataStatusListener(l);
		}
	}   //  removeDataStatusListener

	/**
	 *  Add Data Status Listener - pass on to MTab
	 *  @param l listener
	 */
	public synchronized void addDataStatusListener(DataStatusListener l)
	{
		// assume tab is not null
		m_mTab.addDataStatusListener(l);
	}

	/**
	 *  Data Status Listener - for MTab events.
	 *  <p>
	 *  Callouts are processed here for GUI changes
	 *  - same as in MTab.setValue for batch changes
	 *  <p>
	 *  calls dynamicDisplay
	 *  @param e event
	 */
	@Override
	public void dataStatusChanged(DataStatusEvent e)
	{
	//	if (e.getChangedColumn() == 0)
	//		return;
		int col = e.getChangedColumn();
		log.debug("dataStatusChanged({}) Col={}: {}", m_mTab, col, e);

		//  Process Callout
		final GridField mField = m_mTab.getField(col);
		if (mField != null) 
		{
			//  Dependencies & Callout
			final String msg = m_mTab.processFieldChange(mField, false); // force=false
			if (msg.length() > 0)
			{
				 ADialog.error(m_WindowNo, this, "Error", msg); // metas: proper use of ADialog.error method
			}
		}
		
		//if (col >= 0)
		dynamicDisplay(col);
	}   //  dataStatusChanged

	
	/**************************************************************************
	 *  List Selection Listener (VTable) - row changed
	 *  @param e event
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		//  no rows
		if (m_mTab.getRowCount() == 0)
			return;

	//	vTable.stopEditor(graphPanel);
		int rowTable = vTable.getSelectedRow();
		int rowCurrent = m_mTab.getCurrentRow();
		log.debug("valueChanged({}) Row in Table={}, in Model={}", m_mTab, rowTable, rowCurrent);
		/* BT [ 1972495 ] Multirow Automatic New Record loses context
		// FR [ 1757088 ]
		if(rowCurrent + 1 == vTable.getRowCount() && !isSingleRow() && Env.isAutoNew(Env.getCtx()) && m_mTab.getRecord_ID() != -1)
		{
		  //stopEditor(true);
		  vTable.getSelectionModel().removeListSelectionListener(this);
		  m_mTab.dataNew(false);
		  dynamicDisplay(0);
		  vTable.getSelectionModel().addListSelectionListener(this);
		  return;
		 } */
		if (rowTable == -1)  //  nothing selected
		{
			if (rowCurrent >= 0)
			{
				vTable.setRowSelectionInterval(rowCurrent, rowCurrent); //  causes this method to be called again
				return;
			}
		}
		else
		{
			if (rowTable != rowCurrent) {
				//make sure table selection is consistent with model
				int t = m_mTab.navigate(rowTable);
				if (t != rowTable) {
					rowTable = t;
					vTable.setRowSelectionInterval(rowTable, rowTable);
				}
			}
			dynamicDisplay(0);
		}

		//	TreeNavigation - Synchronize 	-- select node in tree
		if (treePanel != null)
		{
			treePanel.setTreeSelectionPath (m_mTab.getRecord_ID());	//	ignores new (-1)
		}

	}   //  valueChanged

	/**
	 *  PropertyChange Listener - Tree Panel - node selection
	 *  @param e event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e)
	{
		if (e == null)
		{
			return;
		}
		final Object value = e.getNewValue();
		if (value == null)
		{
			return;
		}
		
		if (!(value instanceof MTreeNode))
		{
			return;
		}

		//  We Have a TreeNode
		final int nodeID = ((MTreeNode)value).getNode_ID();
		//  root of tree selected - ignore
		//if (nodeID == 0)
			//return;

		//  Search all rows for mode id
		final int size = m_mTab.getRowCount();
		int row = -1;
		for (int i = 0; i < size; i++)
		{
			if (m_mTab.getKeyID(i) == nodeID)
			{
				row = i;
				break;
			}
		}
		if (row == -1)
		{
			if (nodeID > 0)
			{
				log.warn("Tab does not have ID with Node_ID=" + nodeID);
			}
			return;
		}

		//  Navigate to node row
		m_mTab.navigate(row);
	}   //  propertyChange

	/**
	 *  Dynamic Display.
	 *  - Single Row Screen layout and update of dynamic Lookups
	 *  <p>
	 *  Single Row layout:
	 *  the components's name is the ColumnName; if it matches, the
	 *  MField.isDisplayed(true) is used to determine if it is visible
	 *  if the component is a VEditor, setEnabled is set from the MField
	 *  <p>
	 *  Multi Row layout is not changed:
	 *  VCellRenderer calls JTable.isCellEditable -> checks MField.isEditable (Active, isDisplayed)
	 *  VCellEditor.isCellEditable calls MField.isEditable(true) <br>
	 *  If a column is not displayed, the width is set to 0 in dynInit
	 *  <p>
	 *  Dynamic update of data is handeled in VLookup.focusGained/Lost.
	 *  When focus is gained the model is temporarily updated with the
	 *  specific validated data, if lost, it is switched back to the
	 *  unvalidated data (i.e. everything). This allows that the display
	 *  methods have a lookup to display. <br>
	 *  Here: if the changed field has dependents and the dependent
	 *  is a Lookup and this lookup has a dynamic dependence of the changed field,
	 *  the value of that field is set to null (in MTab.processDependencies -
	 *  otherwise it would show an invalid value).
	 *  As Editors listen for value changed of their MField, the display is updated.
	 *  <p>
	 *  Called from GridController.valueChanged/dataStatusChanged, APane;.stateChanged/unlock/cmd_...
	 *  @param columnIndex selective column number or 0 if all
	 */
	public void dynamicDisplay (final int columnIndex)
	{
		//	Don't update if multi-row
		if (!isSingleRow() || m_onlyMultiRow)
			return;
		
		// Do nothing if tab is not open yet because there are no fields)
		if (!m_mTab.isOpen())
			return;
		
		//  Selective
		if (columnIndex > 0)
		{
			final GridField changedField = m_mTab.getField(columnIndex);
			final String columnName = changedField.getColumnName();
			final List<GridField> dependants = m_mTab.getDependantFieldsWithNullElements(columnName);
			//	No Dependents and no Callout - Set just Background
			if (dependants.size() == 0 && !m_mTab.getCalloutExecutor().hasCallouts(changedField))
			{
				final VEditor ve = vPanel.getEditor(columnName);
				if (ve != null)
				{
					boolean mandatoryButMissing = false;
					final boolean noValue = changedField.getValue() == null || changedField.getValue().toString().length() == 0;
					if (noValue && changedField.isEditable(true) && changedField.isMandatory(true))    //  check context
						mandatoryButMissing = true;
					ve.setBackground(mandatoryButMissing || changedField.isError());
					// start: metas-2009_0021_AP1_CR52
					// if (ve instanceof VLookup)
					// ((VLookup) ve).refreshValue();
					// end: metas-2009_0021_AP1_CR52
				}
				return;
			}
		}   //  selective


		//
		// All Components in vPanel (Single Row)
		final boolean noData = m_mTab.getRowCount() == 0;
		for (final String columnName : vPanel.getEditorColumnNames())
		{
			final VEditor editor = vPanel.getEditor(columnName);
			if (editor == null)
			{
				continue;
			}

			final GridField mField = m_mTab.getField(columnName);
			if (mField == null)
			{
				continue;
			}
			
			// Update editor's properties: visible, read-write, background color
			if (mField.isDisplayed(true))		// check context
			{
				if (!editor.isVisible())
					editor.setVisible(true);		// visibility

				//
				// Enable runtime change of VFormat
				// Feature Request [1707462]
				// @author fer_luck
				if (editor instanceof VString)
				{
					final VString stringEditor = (VString)editor;
					if ((stringEditor.getVFormat() != null && stringEditor.getVFormat().length() > 0 && mField.getVFormat() == null)
							|| (stringEditor.getVFormat() == null && mField.getVFormat() != null && mField.getVFormat().length() > 0)
							|| (stringEditor.getVFormat() != null && mField.getVFormat() != null && !stringEditor.getVFormat().equals(mField.getVFormat())))
					{
						stringEditor.setVFormat(mField.getVFormat());
					}
				}

				if (noData)
				{
					editor.setReadWrite(false);
				}
				else
				{
					final boolean rw = mField.isEditable(true);	// r/w - check Context
					editor.setReadWrite(rw);
					boolean mandatoryButMissing = false;
					// least expensive operations first // missing mandatory
					if (rw && mField.getValue() == null && mField.isMandatory(true))    // check context
					{
						mandatoryButMissing = true;
					}
					editor.setBackground(mandatoryButMissing || mField.isError());
				}
				setColor(Env.getCtx(), mField, editor); // metas-2009_0021_AP1_CR045
			}
			else
			{
				if (editor.isVisible())
					editor.setVisible(false);
			}
			
			// Update label
			final CLabel editorLabel = vPanel.getEditorLabel(columnName);
			if (editorLabel != null)
			{
				editorLabel.setVisible(editor.isVisible());
			}
		}   // all components

		// hide empty field group based on the environment
		vPanel.updateVisibleFieldGroups();
	}   //  dynamicDisplay

	/**
	 *  Row Changed - synchronize with Tree
	 *
	 *  @param  save    true the row was saved (changed/added), false if the row was deleted
	 *  @param  keyID   the ID of the row changed
	 */
	public void rowChanged (boolean save, int keyID)
	{
		if (treePanel == null || keyID <= 0)
			return;
		
		final IPOTreeSupport poTreeSupport = Services.get(IPOTreeSupportFactory.class).get(m_mTab.getTableName());
		final MTreeNode info = poTreeSupport.getNodeInfo(m_mTab);
		//
		treePanel.nodeChanged(save, info);
	}   //  rowChanged


	/**************************************************************************
	 * Save Multiple records - Clone a record and assign new values to each 
	 * clone for a specific column.
	 * @param ctx context
	 * @param tableName Table Name
	 * @param columnName Column for which value need to be changed
	 * @param recordId Record to clone
	 * @param values Values to be assigned to clones for the specified column
	 * @param trxName Transaction
	 * @throws Exception If error is occured when loading the PO or saving clones
	 * 
	 * @author ashley
	 */
	private void saveMultipleRecords(Properties ctx, String tableName, 
			String columnName, int recordId, Integer[] values, 
			String trxName) throws Exception
	{
		if (values == null)
		{
			return ;
		}
		
		int oldRow = m_mTab.getCurrentRow();
		GridField lineField = m_mTab.getField("Line");	
		
		for (int i = 0; i < values.length; i++)
		{
			if (!m_mTab.dataNew(DataNewCopyMode.Copy))
			{
				throw new IllegalStateException("Could not clone tab");
			}
			
			m_mTab.setValue(columnName, values[i]);
			
			if (lineField != null)
			{
				m_mTab.setValue(lineField, 0);
			}
			
			if (!m_mTab.dataSave(false))
			{
				throw new IllegalStateException("Could not update tab");
			}
			
			m_mTab.setCurrentRow(oldRow);
		}
	}
	
	/**************************************************************************
	 * Vetoable Change Listener.
	 *
	 * Called from
	 * <ul>
	 * <li> {@link VEditor}, on Single Row layout: Update MTable
	 * <li> {@link GridTable} for Save Confirmation dialog
	 * </ul>
	 * 
	 * </p>Regarding {@link VEditor2}, note that
	 * <ul>
	 * <li>if the event's source is <code>instanceof VEditor2</code> and</li>
	 * <li>if the event is not considered a "real" change by the GridController, but by the VEditor2 instance (see {@link VEditor2#isRealChange(PropertyChangeEvent)})</li>
	 * <li>then the new value is set in the respective GridTable with forced==true, because the GridTable would otherwise ignore the new value</li>
	 * </ul>
	 * 
	 * @param e event
	 * @throws PropertyVetoException
	 */
	@Override
	public void vetoableChange(final PropertyChangeEvent e) throws PropertyVetoException
	{
		// Do nothing if disposed
		if(m_mTab == null)
		{
			return;
		}
		
		if (m_mTab.isProcessed() || !m_mTab.isActive())		//	only active records
		{
			Object source = e.getSource();
			if (source instanceof VEditor)
			{
				if (!((VEditor)source).isReadWrite())
				{
					return;
				}
			}
			else
			{
				return;
			}
		}	//	processed
		
		if (log.isDebugEnabled())
		{
			log.debug("(" + m_mTab + ") "
					+ e.getPropertyName() + "=" + e.getNewValue() + " (" + e.getOldValue() + ") "
					+ (e.getOldValue() == null ? "" : e.getOldValue().getClass().getName()));
		}		

		//  Save Confirmation dialog    MTable-RowSave
		if (GridTable.PROPERTY.equals(e.getPropertyName()))
		{
			//  throw new PropertyVetoException will call this listener again to revert to old value
			if (m_vetoActive)
			{
				//ignore
				m_vetoActive = false;
				return;
			}
			if (!Env.isAutoCommit(Env.getCtx(), m_WindowNo) || m_mTab.getCommitWarning().length() > 0)
			{
				if (!ADialog.ask(m_WindowNo, this, "SaveChanges?", m_mTab.getCommitWarning()))
				{
					m_vetoActive = true;
					throw new PropertyVetoException ("UserDeniedSave", e);
				}
			}
			return;
		}   //  saveConfirmation


		//  Get Row/Col Info
		final GridTable mTable = m_mTab.getTableModel();
		int row = m_mTab.getCurrentRow();
		int col = mTable.findColumn(e.getPropertyName());
		//

		//
		// Check is our change event is a real value change (05005)
		final boolean oldValueNull = e.getOldValue() == null
				|| e.getOldValue().toString().length() == 0; // some editors return "" instead of null

		// TODO ts: Question: why don't we check for "" instead of null with getNewValue(), too? 
		final boolean newValueNull = e.getNewValue() == null;
		
		// Condition for 'realChange' to be true: if newValueNull is true, then oldValueNull must be true, too
		// TODO ts: Question: why is newValueNull==false and oldValueNull==false a real change, 
		// but not newValueNull=true and oldValueNull=false?
		boolean realChange = oldValueNull || !newValueNull;
		boolean forceSetValue = false;
		
		if (!realChange && (e.getSource() instanceof VEditor2))
		{
			// Case: we detected that this is not a "real" change but we have VEditor2 as source
			// If VEditor2 tells us that this is a real change, we need to perform it but we need to force setting it (because new value is null)
			final VEditor2 editor2 = (VEditor2)e.getSource();
			
			// Override the GridTab's opinion on this matter
			realChange = editor2.isRealChange(e);
			
			// this will set the new value to 'mTable', even if 'mTable' thinks that there was no change
			// note that this only plays a role if realChange==true
			forceSetValue = true; 
		}
		
		if (!realChange)
		{
			mTable.setChanged (true);
		}
		else
		{
		//	mTable.setValueAt (e.getNewValue(), row, col, true);
			/*
         	 * Changes: Added the logic below to handle multiple values for a single field
         	 *          due to multiple selection in Lookup (Info).
         	 * @author ashley
         	 */
			Object newValue = e.getNewValue();
			Integer newValues[] = null;
			
			if (newValue instanceof Integer[])
			{
				newValues = ((Integer[])newValue);
				newValue = newValues[0];
				
				if (newValues.length > 1)
				{
					Integer valuesCopy[] = new Integer[newValues.length - 1];
					System.arraycopy(newValues, 1, valuesCopy, 0, valuesCopy.length);
					newValues = valuesCopy;
				}
				else
				{
					newValues = null;
				}
			}
			else if (newValue instanceof Object[])
			{
				log.error("Multiple values can only be processed for IDs (Integer)");
				throw new PropertyVetoException("Multiple Selection values not available for this field", e);
			}
			else if (newValue instanceof String)
			{

				// metas: protect ourselves from texts that are too large
				final String newString = (String) newValue;
				if (newString.length() > 50000) {

					log.error("value string is too big");
					throw new PropertyVetoException("value string is too big", e);
				}
			}

			mTable.setValueAt (newValue, row, col, forceSetValue);	//	-> dataStatusChanged -> dynamicDisplay
			
			//	Force Callout
			if (e.getPropertyName().equals("S_ResourceAssignment_ID"))
			{
				GridField mField = m_mTab.getField(col);
				if (mField != null && m_mTab.getCalloutExecutor().hasCallouts(mField))
					m_mTab.processFieldChange(mField);     //  Dependencies & Callout
			}
			
			if (newValues != null && newValues.length > 0)
			{
				// Save data, since record need to be used for generating clones.
				if (!m_mTab.dataSave(false))
				{
					throw new PropertyVetoException("SaveError", e);
				}
				
				// Retrieve the current record ID
				final int recordId = m_mTab.getKeyID(m_mTab.getCurrentRow());
				
				try
				{
					final Integer[] values = newValues;
					Services.get(ITrxManager.class).run(new TrxRunnableAdapter()
					{
						@Override
						public void run(String localTrxName) throws Exception
						{
							saveMultipleRecords(Env.getCtx(), mTable.getTableName(), e.getPropertyName(), recordId, values, localTrxName);
						}
					});
					//
					m_mTab.dataRefreshAll();
				}
				catch(Exception ex)
				{
					log.error(ex.getLocalizedMessage(), ex);
					throw new PropertyVetoException("SaveError", e);
				}
			}
		}
	}   //  vetoableChange

	
	/**************************************************************************
	 *  Get Model Tab
	 *  @return Model Tab
	 */
	public GridTab getMTab()
	{
		return m_mTab;
	}   //  getMTab

	/**
	 * 	Get Display Logic
	 *	@return Display Logic
	 */
	public ILogicExpression getDisplayLogic()
	{
		return m_mTab.getDisplayLogic();
	}	//	getDisplayLogic
	
	/**
	 *  Get VTable
	 *  @return VTable
	 */
	public VTable getTable()
	{
		return vTable;
	}   //  getTable

	
	/**
	 * 	Set Window level Mnemonics
	 *	@param set true if set otherwise unregiser
	 */
	public void setMnemonics (boolean set)
	{
		if (vPanel != null)
			vPanel.setMnemonics(set);
	}	//	setMnemonics
	
	/**
	 *  Stop Table & SR Editors and move focus to graphPanel
	 *  @param saveValue true if we shall commit the editing value because stopping the editing
	 */
	public void stopEditor (final boolean saveValue)
	{
		final VTable vTable = getVTable();
		if (vTable == null)
		{
			log.debug("stopEditor: Nothing to do because this component is disposing/disposed");
			return;
		}
		
		if(log.isDebugEnabled())
			log.debug("stopEditor: tab={}, TableEditing={}", m_mTab, vTable.isEditing());

		//  MultiRow - remove editors
		vTable.stopEditor(saveValue);

		//  SingleRow - stop editors by changing focus
		if (m_singleRow)
		{
			final VPanel vPanel = getvPanel();
			if(vPanel != null)
			{
				vPanel.transferFocus();
			}
		}
	}   //  stopEditors

	/**
	 * 	Get Variable Value
	 *	@param variableName name
	 *	@return value
	 */
	@Override
	public String get_ValueAsString (String variableName)
	{
		return Env.getContext(Env.getCtx(), m_WindowNo, variableName);
	}	//	get_ValueAsString

	/**
	 * Is controller data not stale
	 * @return boolean
	 */
	public boolean isCurrent()
	{
		return m_mTab != null ? m_mTab.isCurrent() : false;
	}
	
     //FR [ 1757088 ]
	public VPanel getvPanel()
	{
		return vPanel;
	}
	
	//BEGIN - [FR 1953734]
	GridController _parentGC;
	private void setGCParent(final GridController gc)
	{
		_parentGC = gc;
	}
	public final GridController getGCParent()
	{
		return _parentGC;
	}
	public void refreshMTab(final GridController includedTab)
	{
		int m_CurrentRowBeforeSave = includedTab.m_mTab.getCurrentRow();
		m_mTab.dataRefresh(m_mTab.getCurrentRow());
		includedTab.m_mTab.setCurrentRow(m_CurrentRowBeforeSave);
	}
	//END - [FR 1953734]
	
	/**
	 * Accept pending editor changes.
	 */
	public void acceptEditorChanges() 
	{
		if (isSingleRow())
		{
			Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
			if (c != null && this.isAncestorOf(c))
			{				
				Component t = c;
				while (t != null && t != this) 
				{
					if (t instanceof VManagedEditor)
					{
						((VManagedEditor)t).commitChanges();
						return;
					}
					t = t.getParent();
				}
			}
		}
	}

	/** @return tab's top search panel or <code>null</code> if the tab search is not allowed */
	public final FindPanelContainer getFindPanel()
	{
		return this.findPanel;
	}

	/** Cache fields color logic: WindowNo -> SQL -> Color */
	private static final CCache<Integer, Map<String, Color>> s_cacheColors = new CCache<>(I_AD_Color.Table_Name + "_ForColorLogic", 20, 10);
	private static final Color ColorNone = new Color(0);
	/**
	 * Get Color for given GridField. (metas-2009_0021_AP1_CR045)
	 * 
	 * @param ctx
	 * @param field
	 * @return Color or null
	 */
	public static Color getColor(final Properties ctx, final GridField field)
	{
		final IStringExpression colorLogic = field.getColorLogic();
		if (colorLogic == null)
		{
			return null;
		}
		
		final Evaluatee evalCtx = field.createEvaluationContext(ctx);
		final boolean ignoreUnparsable = false;
		final String sql = colorLogic.evaluate(evalCtx, ignoreUnparsable);
		if (Check.isEmpty(sql, true))
		{
			// expression could not be evaluated, return null color
			return null;
		}
		
		// Check cache first
		Map<String, Color> winColorsCache = s_cacheColors.get(field.getWindowNo());
		if (winColorsCache == null)
		{
			winColorsCache = new HashMap<>(50);
			s_cacheColors.put(field.getWindowNo(), winColorsCache);
		}
		
		Color awtColor = winColorsCache.get(sql);
		if (awtColor != null)
		{
			if (awtColor == ColorNone)
				awtColor = null;
			log.trace("color={} (cached)(sql={})", awtColor, sql);
			return awtColor;
		}
		
		// Check database
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int adColorId = -1;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				adColorId = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			// Adding the query to negative cache because looks like an SQL issue
			winColorsCache.put(sql, ColorNone);
			
			
			// Better not throw DBException because will break the UI
			// throw new DBException(e, sql);
			log.warn("Failed fetching the color for {} -- SQL={}", field, e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		if (adColorId <= 0)
		{
			winColorsCache.put(sql, ColorNone);
			return null;
		}
		//
		final MFColor color = Services.get(IColorRepository.class).getColorById(adColorId);
		if (color == null)
		{
			winColorsCache.put(sql, ColorNone);
			return null;
		}
		
		awtColor = color.getFlatColor();
		winColorsCache.put(sql, awtColor == null ? ColorNone : awtColor);
		
		return awtColor;
	}

	/**
	 * Set field color from color logic (if possible)
	 * (metas-2009_0021_AP1_CR045)
	 * 
	 * @param ctx
	 * @param mField
	 * @param ve
	 */
	private static void setColor(Properties ctx, GridField mField, VEditor ve)
	{
		final Color bg = getColor(ctx, mField);
		if (bg == null)
		{
			return;
		}
		
		ve.setBackground(bg);
	}

	public void setFixedHeight(final int height)
	{
		if (height > 0)
		{
			final JComponent comp = splitPane;
			comp.setPreferredSize(new Dimension(comp.getPreferredSize().width, height));
			comp.setMinimumSize(new Dimension(200, height));
			comp.setMaximumSize(new Dimension(9999, height));
		}
	}
	
	/**
	 * Scroll the view in order to make visible the actual content of this panel (i.e. the single row panel or the grid table).
	 */
	public void scrollToVisible()
	{
		this.scrollRectToVisible(splitPane.getBounds());
	}

	@Override
	public final void requestFocus()
	{
		// Try requesting focus on find panel if possible
		final FindPanelContainer findPanel = getFindPanelIfFocusable();
		if (findPanel != null)
		{
			findPanel.requestFocus();
			return;
		}
		
		// Fallback: request focus on this component
		super.requestFocus();
	}
	
	@Override
	public final boolean requestFocusInWindow()
	{
		// Try requesting focus on find panel if possible
		final FindPanelContainer findPanel = getFindPanelIfFocusable();
		if (findPanel != null && findPanel.requestFocusInWindow())
		{
			return true;
		}
		
		// Fallback: request focus on this component
		return super.requestFocusInWindow();
	}
	
	/** @return the find panel if exists and it's focusable; <code>null</code> otherwise */
	private final FindPanelContainer getFindPanelIfFocusable()
	{
		if (!m_singleRow)
		{
			return null;
		}
		
		// Only if the search is configured to be active
		if (m_mTab == null || !m_mTab.isSearchActive())
		{
			return null;
		}
		
		// Only if the find panel exists
		final FindPanelContainer findPanel = getFindPanel();
		if(findPanel == null)
		{
			return null;
		}

		// Only if the find panel allows it
		if (!findPanel.isFocusable())
		{
			return null;
		}
		
		return findPanel;
	}
	
	/**
	 * Sets the main/root {@link APanel} in which this grid controller will reside.
	 * 
	 * Also this call will disable the key bindings of underlying components, key bindings which are present in given {@link APanel}. We do this because we want the APanel key bindings (i.e. the
	 * toolbar shortcuts) to take priority.
	 * 
	 * @param aPanel
	 * @task 02553
	 */
	final void setAPanel(final APanel aPanel)
	{
		// Do nothing if same APanel
		if (this._aPanel == aPanel)
		{
			return;
		}
		
		this._aPanel = aPanel;

		//
		// For some of our components, remove the key binding which are defined on APanel level (task 09456). 
		// AIM: APanel's toolbar shortcuts will work because they will not be intercepted by our components.
		if (aPanel != null)
		{
			// Remove the key bindings of the split pane which are defined in our APanel.
			// i.e. more precise, at the moment of writing this, the affected key bindings are:
			// * splitPane's F6-toggleFocus which prevents toolbar's F6-Find
			// * splitPane's F8-startResize which prevents toolbar's F8-Multi (switch single row layout/grid layout)
			aPanel.removeAncestorKeyBindingsOf(splitPane, null);
			
			// Remove the key bindings of included grid layout.
			// i.e. more precise, at the moment of writing this, the affected key bindings are:
			// * vTable's ESCAPE-cancel which prevents toolbar's ESCAPE-Ignore => don't remove this because it's fine to have ESC working in JTable
			// * vTable's F2-startEditing which prevents toolbar's F2-New
			// * vTable's F8->focusHeader which prevents toolbar's F8-Multi (switch single row layout/grid layout)
			aPanel.removeAncestorKeyBindingsOf(vTable, new Predicate<KeyStroke>()
			{
				@Override
				public boolean apply(final KeyStroke key)
				{
					// Escape is used in JTable to cancel the current editing and is active only when needed,
					// so it's safe to left it in place.
					if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
					{
						return false; // don't remove
					}
					
					return true; // remove any other key binding
				}
			});
		}
	}
	
	public static final class Builder
	{
		private GridTab gridTab;
		private boolean includedTab = false;
		private APanel aPanel;
		private boolean goSingleRowLayout = false;

		private Builder()
		{
			super();
		}
		
		public GridController build()
		{
			return new GridController(this);
		}

		public Builder setGridTab(GridTab gridTab)
		{
			this.gridTab = gridTab;
			return this;
		}
		
		private final GridTab getGridTab()
		{
			Check.assumeNotNull(gridTab, "gridTab not null");
			return gridTab;
		}

		private int getWindowNo()
		{
			return getAPanel().getWindowNo();
		}

		public Builder setIncludedTab(boolean includedTab)
		{
			this.includedTab = includedTab;
			return this;
		}
		
		private final boolean isIncludedTab()
		{
			return includedTab;
		}
		
		private final boolean isGridModeOnly()
		{
			return getGridTab().isGridModeOnly();
		}
		
		private final GridWindow getGridWindow()
		{
			return getGridTab().getGridWindow();
		}

		public Builder setAPanel(final APanel aPanel)
		{
			this.aPanel = aPanel;
			return this;
		}
		
		private final APanel getAPanel()
		{
			Check.assumeNotNull(aPanel, "aPanel not null");
			return aPanel;
		}
		
		private final MFColor getBackgroundColor()
		{
			// use Window level background color
			return getGridWindow().getColor();
		}
		
		private final AppsAction getIgnoreAction()
		{
			return getAPanel().getIgnoreAction();
		}

		private int getTabIndex()
		{
			return getGridTab().getTabNo();
		}
		
		private final boolean isLazyInitialization()
		{
			// lazy if not first tab
			return getTabIndex() != 0;
		}

		public Builder setGoSingleRowLayout(final boolean goSingleRowLayout)
		{
			this.goSingleRowLayout = goSingleRowLayout;
			return this;
		}
		
		private final boolean isGoSingleRowLayout()
		{
			return goSingleRowLayout;
		}

	}
}   //  GridController
