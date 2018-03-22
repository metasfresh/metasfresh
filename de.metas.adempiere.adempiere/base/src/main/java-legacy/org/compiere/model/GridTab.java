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
 *
 * @contributor Victor Perez , e-Evolution.SC FR [ 1757088 ] *
 *              Teo Sarca, www.arhipac.ro *
 *****************************************************************************/
package org.compiere.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import javax.swing.event.EventListenerList;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.api.impl.CalloutExecutor;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.exceptions.ExpressionException;
import org.adempiere.ad.persistence.po.NoDataFoundHandlerRetryRequestException;
import org.adempiere.ad.persistence.po.NoDataFoundHandlers;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.adempiere.ui.api.IGridTabSummaryInfoFactory;
import org.adempiere.ui.sideactions.model.ISideActionsGroupModel;
import org.adempiere.ui.sideactions.model.ISideActionsGroupsListModel;
import org.adempiere.ui.sideactions.model.SideActionsGroupModel;
import org.adempiere.ui.sideactions.model.SideActionsGroupsListModel;
import org.adempiere.ui.spi.IGridTabSummaryInfoProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.StateChangeEvent.StateChangeEventType;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Env.Scope;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.Evaluator;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.process.IProcessPreconditionsContext;

/**
 * Tab Model.
 * - a combination of AD_Tab (the display attributes) and AD_Table information.
 * <p>
 * The Tab owns also it's Table model and listens to data changes to update the Field values.
 *
 * <p>
 * The Tab maintains the bound property: CurrentRow
 *
 * <pre>
 *  Event Hierarchies:
 *      - dataChanged (from MTable)
 *          - setCurrentRow
 *              - Update all Field Values
 *
 *      - setValue
 *          - Update Field Value
 *          - Callout
 * </pre>
 *
 * @author Jorg Janke
 * @version $Id: GridTab.java,v 1.10 2006/10/02 05:18:39 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1742159 ] Editable number field for inactive record
 *         <li>BF [ 1968598 ] Callout is not called if tab is processed
 *         <li>BF [ 2104022 ]
 *         GridTab.processCallout: throws NPE if callout returns null
 *         <li>FR [ 2846871 ] Add method org.compiere.model.GridTab.getIncludedTabs
 *         https://sourceforge.net/tracker/?func=detail&aid=2846871&group_id=176962&atid=879335
 * @author Teo Sarca, teo.sarca@gmail.com
 *         <li>BF [ 2873323 ] ABP: Do not concatenate strings in SQL queries https://sourceforge.net/tracker/?func=detail&aid=2873323&group_id=176962&atid=879332
 *         <li>BF
 *         [ 2874109 ] Tab ORDER BY clause is not supporting context variables https://sourceforge.net/tracker/?func=detail&aid=2874109&group_id=176962&atid=879332
 *         <li>BF [ 2905287 ] GridTab query is
 *         not build correctly https://sourceforge.net/tracker/?func=detail&aid=2905287&group_id=176962&atid=879332
 * @author Victor Perez , e-Evolution.SC
 *         <li>FR [1877902] Implement JSR 223 Scripting APIs to Callout
 *         <li>BF [ 2910358 ] Error in context when a field is found in different tabs.
 *         https://sourceforge.net/tracker/?func=detail&aid=2910358&group_id=176962&atid=879332
 *         <li>BF [ 2910368 ] Error in context when IsActive field is found in different
 *         https://sourceforge.net/tracker/?func=detail&aid=2910368&group_id=176962&atid=879332
 * @author Carlos Ruiz, qss FR [1877902]
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1877902&group_id=176962 to FR [1877902]
 * @author Cristina Ghita, www.arhipac.ro FR [2870645] Set null value for an ID
 * @see https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2870645&group_id=176962
 * @author Paul Bowden, phib BF 2900767 Zoom to child tab - inefficient queries
 * @see https://sourceforge.net/tracker/?func=detail&aid=2900767&group_id=176962&atid=879332
 */
public class GridTab implements DataStatusListener, Evaluatee, Serializable, ICalloutRecord
{

	/**
	 *
	 */
	private static final long serialVersionUID = 7198494041906579986L;

	public static final GridTab fromCalloutRecordOrNull(final ICalloutRecord calloutRecord)
	{
		if (calloutRecord instanceof GridTab)
		{
			return (GridTab)calloutRecord;
		}

		log.warn("Cannot extract {} from {}. Returning null", GridTab.class, calloutRecord);
		return null;
	}

	// services
	private final transient IGridTabSummaryInfoFactory gridTabSummaryInfoFactory = Services.get(IGridTabSummaryInfoFactory.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	public static final String DEFAULT_STATUS_MESSAGE = "NavigateOrUpdate";

	/**
	 * Create Tab (Model) from Value Object.
	 * <p>
	 * MTab provides a property listener for changed rows and a DataStatusListener for communicating changes of the underlying data
	 *
	 * @param vo Value Object
	 * @param w
	 */
	public GridTab(final GridTabVO vo, final GridWindow w)
	{
		this(vo, w, false);
	}

	/**
	 * Create Tab (Model) from Value Object.
	 * <p>
	 * MTab provides a property listener for changed rows and a DataStatusListener for communicating changes of the underlying data
	 *
	 * @param vo Value Object
	 * @param w
	 * @param virtual
	 */
	public GridTab(final GridTabVO vo, final GridWindow w, final boolean virtual)
	{
		super();
		m_window = w;
		m_vo = vo;
		this.attachmentsMap = new AttachmentsMap(vo.AD_Table_ID);

		//
		// Create MTable
		m_mTable = new GridTable(m_vo.getCtx(), m_vo.getAD_Table_ID(), m_vo.getTableName(), m_vo.getWindowNo(), m_vo.getTabNo(), true, virtual);
		m_mTable.setReadOnly(m_vo.isReadOnly() || m_vo.isView());
		m_mTable.setDeleteable(m_vo.isDeleteable());
		m_mTable.setGridTab(this); // metas-2009_0021_AP1_G140

		calloutExecutor = CalloutExecutor.builder()
				.setTableName(m_vo.getTableName())
				.build();
		// Load Tab
		// initTab(false);
	}	// GridTab

	/** Value Object */
	private GridTabVO m_vo;

	// The window of this tab
	private final GridWindow m_window;

	/** The Table Model for Query */
	private final GridTable m_mTable;

	private String m_keyColumnName = "";
	private String m_linkColumnName = "";

	private String m_parentColumnName = "";
	private String m_extendedWhere;
	/** Attachments */
	private final AttachmentsMap attachmentsMap;
	/** Chats */
	private HashMap<Integer, Integer> m_Chats = null;
	/** Locks */
	private final ExtendedMemorizingSupplier<Set<Integer>> lockedRecordIdsSupplier = ExtendedMemorizingSupplier.of(() -> retrieveLockedRecordIds());

	/** Current Row */
	private int m_currentRow = -1;

	/** Property Change */
	private final PropertyChangeSupport m_propertyChangeSupport = new PropertyChangeSupport(this);
	/** Property Change Type */
	public static final String PROPERTY = "CurrentRow";
	/** A list of event listeners for this component. */
	private final EventListenerList m_listenerList = new EventListenerList();
	/** Current Data Status Event */
	private DataStatusEvent m_DataStatusEvent = null;
	/** Query */
	private MQuery m_query = new MQuery();
	private String m_oldQuery = "0=9";
	private String m_linkValue = "999999";

	/** Order By Array if SortNo 1..3 */
	private String[] m_OrderBys = new String[3];
	/** List of Key Parents */
	private ArrayList<String> m_parents = new ArrayList<>(2);

	/** Map of ColumnName of source field (key) and the dependant field (value) */
	private Multimap<String, GridField> m_depOnField = MultimapBuilder.hashKeys().arrayListValues().build();

	/** Async Loader */
	private Loader m_loader = null;
	/** Async Loading complete */
	private volatile boolean m_loadComplete = false;
	/** Is Tab Included in other Tab */
	private boolean m_included = false;

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(GridTab.class);

	// private boolean m_parentNeedSave = false; // metas: we are not using this anymore

	private long m_lastDataStatusEventTime;

	private DataStatusEvent m_lastDataStatusEvent;

	private ITabCallout tabCallouts = ITabCallout.NULL;

	// Context property names:
	private static final String CTX_Prefix = "_TabInfo_";
	public static final String CTX_KeyColumnName = CTX_Prefix + "KeyColumnName";
	public static final String CTX_LinkColumnName = CTX_Prefix + "LinkColumnName";
	public static final String CTX_TabLevel = CTX_Prefix + "TabLevel";
	public static final String CTX_AccessLevel = CTX_Prefix + "AccessLevel";
	public static final String CTX_AD_Tab_ID = CTX_Prefix + "AD_Tab_ID";
	public static final String CTX_Name = CTX_Prefix + "Name";
	public static final String CTX_AD_Table_ID = CTX_Prefix + "AD_Table_ID";
	public static final String CTX_FindSQL = CTX_Prefix + "FindSQL";
	public static final String CTX_SQL = CTX_Prefix + "SQL";
	public static final String CTX_CurrentRow = CTX_Prefix + "RowIndex";
	/**
	 * We have to distinguish when the row is loading, so the correct value is set and it is not considered a change.
	 *
	 * Used for avoiding saving issues
	 */
	// 04513
	public static final String CTX_RowLoading = CTX_Prefix + "RowLoading";

	/**************************************************************************
	 * Tab loader for Tabs > 0
	 */
	class Loader extends Thread
	{
		/**
		 * Async Loading of Tab > 0
		 */
		@Override
		public void run()
		{
			loadTab();
		}   // run
	}   // Loader

	public Properties getCtx()
	{
		return m_vo.getCtx();
	}

	/**
	 * Wait until load is complete
	 */
	private void waitLoadCompete()
	{
		if (m_loadComplete)
		{
			return;
		}
		//
		m_loader.setPriority(Thread.NORM_PRIORITY);
		log.info("");
		while (m_loader.isAlive())
		{
			try
			{
				Thread.sleep(100);     // 1/10 sec
			}
			catch (final Exception e)
			{
				log.error("", e);
			}
		}
		log.info("fini");
	}   // waitLoadComplete

	public boolean isLoadComplete()
	{
		return m_loadComplete;
	}

	/**
	 * Initialize Tab with record from AD_Tab_v.
	 *
	 * If this tab was already initialized, this method does nothing and returns true.
	 *
	 * @param async true if the tab shall be initialized asynchronously (in another thread)
	 * @return true if it was already initialized or it was initialized now correctly. <br/>
	 *         If asynchronous initialization was request and this tab was not already initialized, this method is returning false.
	 */
	public boolean initTab(final boolean async)
	{
		log.debug("initTab: {} - Async={} - Where={}", this, async, m_vo.getWhereClause());
		if (isLoadComplete())
		{
			return true;
		}

		if (m_loader != null && m_loader.isAlive())
		{
			waitLoadCompete();
			if (isLoadComplete())
			{
				return true;
			}
		}

		if (async)
		{
			m_loader = new Loader();
			m_loader.start();
			return false;
		}
		else
		{
			return loadTab();
		}
	}	// initTab

	protected boolean loadTab()
	{
		m_extendedWhere = m_vo.getWhereClause();

		// Get Field Data
		if (!loadFields())
		{
			m_loadComplete = true;
			return false;
		}

		// Order By
		m_mTable.setOrderClause(getOrderByClause(m_vo.onlyCurrentRows));

		// Metas start: R.Craciunescu@metas.ro: 02280
		// Run tab callout in GridTab
		tabCallouts = Services.get(ITabCalloutFactory.class).createAndInitialize(this);
		// Bind StateChangeEvent to tab callouts
		// It will cover almost all the tab callouts methods.
		GridTabCalloutStateChangeListener.bind(this, tabCallouts);
		// Metas end: R.Craciunescu@metas.ro: 02280

		m_loadComplete = true;
		return true;
	}

	/**
	 * Dispose - clean up resources
	 */
	protected void dispose()
	{
		log.debug("dispose: {}", this);
		m_OrderBys = null;
		//
		m_parents.clear();
		m_parents = null;
		//
		m_mTable.close(true);  // also disposes Fields
		// m_mTable = null;
		//
		m_depOnField.clear();
		m_depOnField = null;

		if (attachmentsMap != null)
		{
			attachmentsMap.reset();
		}

		if (m_Chats != null)
		{
			m_Chats.clear();
		}
		m_Chats = null;
		//
		m_vo.clearFields();

		m_vo = null;
		if (m_loader != null)
		{
			if (m_loader.isAlive())
			{
				m_loader.interrupt();
			}
			m_loader = null;
		}
	}	// dispose

	/**
	 * Get Field data and add to MTable, if it's required or displayed.
	 * Required fields are keys, parents, or standard Columns
	 *
	 * @return true if fields loaded
	 */
	private boolean loadFields()
	{
		log.debug("loadFields: {}", this);

		if (m_vo.getFields().isEmpty())
		{
			return false;
		}

		// Add Fields
		for (int f = 0; f < m_vo.getFields().size(); f++)
		{
			final GridFieldVO voF = m_vo.getFields().get(f);
			// Add Fields to Table
			if (voF != null)
			{
				final GridField field = new GridField(voF);
				field.setGridTab(this);
				final String columnName = field.getColumnName();
				// FR [ 1757088 ] - this create Bug [ 1866793 ]
				/*
				 * if(this.isReadOnly()) {
				 * voF.IsReadOnly = true;
				 * }
				 */
				// Record Info
				if (field.isKey())
				{
					setKeyColumnName(columnName);
				}
				// Parent Column(s)
				if (field.isParentColumn())
				{
					m_parents.add(columnName);
				}
				// Order By
				final int sortNo = field.getSortNo();
				if (sortNo == 0)
				{
					;
				}
				else if (Math.abs(sortNo) == 1)
				{
					m_OrderBys[0] = columnName;
					if (sortNo < 0)
					{
						m_OrderBys[0] += " DESC";
					}
				}
				else if (Math.abs(sortNo) == 2)
				{
					m_OrderBys[1] = columnName;
					if (sortNo < 0)
					{
						m_OrderBys[1] += " DESC";
					}
				}
				else if (Math.abs(sortNo) == 3)
				{
					m_OrderBys[2] = columnName;
					if (sortNo < 0)
					{
						m_OrderBys[2] += " DESC";
					}
				}
				// Add field
				m_mTable.addField(field);

				// List of ColumnNames, this field is dependent on
				final List<String> list = field.getDependentOn();
				for (int i = 0; i < list.size(); i++)
				{
					m_depOnField.put(list.get(i), field);   // ColumnName, Field
				}
				// Add fields all fields are dependent on
				if (columnName.equals("IsActive")
						|| columnName.equals("Processed")
						|| columnName.equals("Processing"))
				{
					m_depOnField.put(columnName, null);
				}
			}
		}     // for all fields

		if (!m_mTable.getTableName().equals(I_AD_PInstance_Log.Table_Name))
		{ // globalqss, bug 1662433
			final Properties ctx = getCtx();

			// FIXME: metas: i think this is not needed because the AD_Field_v is returning also the missing fields
			// Add Standard Fields
			if (m_mTable.getField("Created") == null)
			{
				final GridField created = new GridField(GridFieldVO.createStdField(ctx,
						m_vo.getWindowNo(), m_vo.getTabNo(),
						m_vo.getAD_Window_ID(), m_vo.getAD_Tab_ID(), false, true, true));
				m_mTable.addField(created);
			}
			if (m_mTable.getField("CreatedBy") == null)
			{
				final GridField createdBy = new GridField(GridFieldVO.createStdField(ctx,
						m_vo.getWindowNo(), m_vo.getTabNo(),
						m_vo.getAD_Window_ID(), m_vo.getAD_Tab_ID(), false, true, false));
				m_mTable.addField(createdBy);
			}
			if (m_mTable.getField("Updated") == null)
			{
				final GridField updated = new GridField(GridFieldVO.createStdField(ctx,
						m_vo.getWindowNo(), m_vo.getTabNo(),
						m_vo.getAD_Window_ID(), m_vo.getAD_Tab_ID(), false, false, true));
				m_mTable.addField(updated);
			}
			if (m_mTable.getField("UpdatedBy") == null)
			{
				final GridField updatedBy = new GridField(GridFieldVO.createStdField(ctx,
						m_vo.getWindowNo(), m_vo.getTabNo(),
						m_vo.getAD_Window_ID(), m_vo.getAD_Tab_ID(), false, false, false));
				m_mTable.addField(updatedBy);
			}
		}
		return true;
	}	// loadFields

	/**
	 * Get a list of variables, this tab is dependent on.
	 * - for display purposes
	 *
	 * @return ArrayList
	 */
	public ArrayList<String> getDependentOn()
	{
		final ArrayList<String> list = new ArrayList<>();
		// Display
		Evaluator.parseDepends(list, m_vo.getDisplayLogic()); // metas: 03093
		//
		if (list.size() > 0 && LogManager.isLevelFiner())
		{
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < list.size(); i++)
			{
				sb.append(list.get(i)).append(" ");
			}
			log.trace("DependentOn {}: {} ", m_vo, sb);
		}
		return list;
	}   // getDependentOn

	/**
	 * Get Display Logic
	 *
	 * @return display logic
	 */
	public ILogicExpression getDisplayLogic()
	{
		return m_vo.getDisplayLogic();
	}	// getDisplayLogic

	/**
	 * Get TableModel.
	 * <B>Do not directly communicate with the table model,
	 * but through the methods of this class</B>
	 *
	 * @return Table Model
	 */
	public GridTable getTableModel()
	{
		// Make sure this tab was initialized
		initTab(false); // not async

		return m_mTable;
	}   // getTableModel

	/**
	 * Get Tab Icon
	 *
	 * @return Icon
	 */
	public javax.swing.Icon getIcon()
	{
		if (m_vo.AD_Image_ID == 0)
		{
			return null;
		}
		//
		/**
		 * @todo Load Image
		 */
		return null;
	}   // getIcon

	/**************************************************************************
	 * Has this field dependents ?
	 *
	 * @param columnName column name
	 * @return true if column has dependent
	 */
	public boolean hasDependants(final String columnName)
	{
		// m_depOnField.printToLog();
		return m_depOnField.containsKey(columnName);
	}   // isDependentOn

	/**
	 * Get dependents fields of columnName
	 *
	 * @param columnName column name
	 * @return ArrayList with GridFields dependent on columnName
	 */
	public List<GridField> getDependantFieldsWithNullElements(final String columnName)
	{
		return new ArrayList<>(m_depOnField.get(columnName));
	}

	/**************************************************************************
	 * Set Query
	 *
	 * @param query query
	 */
	public void setQuery(final MQuery query)
	{
		m_mTable.resetNewRecordWhereClause(); // metas
		m_propertyChangeSupport.firePropertyChange("query", m_query, query); // metas 1030
		if (query == null)
		{
			m_query = new MQuery();
		}
		else
		{
			m_query = query;
		}
	}	// setQuery

	/**
	 * Get Query
	 *
	 * @return query
	 */
	public MQuery getQuery()
	{
		return m_query;
	}	// getQuery

	/**
	 * Is Query Active
	 *
	 * @return true if query active
	 */
	public boolean isQueryActive()
	{
		if (m_query != null)
		{
			return m_query.isActive();
		}
		return false;
	}	// isQueryActive

	/**
	 * Is Query New Record
	 *
	 * @return true if query active
	 */
	public boolean isQueryNewRecord()
	{
		if (m_query != null)
		{
			return m_query.isNewRecordQuery();
		}
		return false;
	}	// isQueryNewRecord

	/**
	 * Enable Events - enable data events of tabs (add listeners)
	 */
	public void enableEvents()
	{
		// Setup Events
		m_mTable.addDataStatusListener(this);
		// m_mTable.addTableModelListener(this);
	}   // enableEvents

	/**
	 * Assemble whereClause and query MTable and position to row 0.
	 *
	 * <pre>
	 * 	Scenarios:
	 * 	- Never opened 					(full query)
	 * 	- query changed 				(full query)
	 * 	- Detail link value changed		(full query)
	 * 	- otherwise 					(refreshAll)
	 * </pre>
	 *
	 * @param onlyCurrentRows only current rows (1 day)
	 */
	public void query(final boolean onlyCurrentRows)
	{
		final int onlyCurrentDays = 0;
		query(onlyCurrentRows, onlyCurrentDays, GridTabMaxRows.NO_RESTRICTION);
	}	// query

	/**
	 * Assemble whereClause and query MTable and position to row 0.
	 *
	 * <pre>
	 * 	Scenarios:
	 * 	- Never opened 					(full query)
	 * 	- query changed 				(full query)
	 * 	- Detail link value changed		(full query)
	 * 	- otherwise 					(refreshAll)
	 * </pre>
	 *
	 * @param onlyCurrentRows only current rows
	 * @param onlyCurrentDays if only current row, how many days back
	 * @param maxRows maximum rows restriction
	 */
	public void query(final boolean onlyCurrentRows, final int onlyCurrentDays, final GridTabMaxRows maxRows)
	{
		//
		// Make sure tab is initialized
		if (!m_loadComplete)
		{
			initTab(false); // async=false
		}

		// Log tab info
		if (log.isDebugEnabled())
		{
			log.debug("query: {} - Only Current Rows={}, Days={}, Detail={}", this, onlyCurrentRows, onlyCurrentDays, isDetail());
		}

		// is it same query?
		boolean refresh = m_oldQuery.equals(m_query.getWhereClause())
				&& m_vo.onlyCurrentRows == onlyCurrentRows
				&& m_vo.onlyCurrentDays == onlyCurrentDays;
		m_oldQuery = m_query.getWhereClause();
		m_vo.onlyCurrentRows = onlyCurrentRows;
		m_vo.onlyCurrentDays = onlyCurrentDays;

		/**
		 * Set Where Clause
		 */
		// Tab Where Clause
		final StringBuilder where = new StringBuilder(m_vo.getWhereClause());
		boolean where_IsValid = true;
		if (m_vo.onlyCurrentDays > 0)
		{
			if (where.length() > 0)
			{
				where.append(" AND ");
			}
			where.append("Created >= ");
			where.append("now() - ").append(m_vo.onlyCurrentDays);
		}
		// Detail Query
		if (isDetail())
		{
			// m_parentNeedSave = false; // metas: we are not using this anymore
			final String linkColumnName = getLinkColumnName();
			if (Check.isEmpty(linkColumnName, true))
			{
				final AdempiereException ex = new AdempiereException("No link column defined for tab " + this + "."
						+ "\nAppending 2=3 to tab's where clause => no results will be found");
				log.warn(ex.getLocalizedMessage(), ex);

				where.append(" 2=3");
				where_IsValid = false;
			}
			else
			{
				final Properties ctx = getCtx();
				final String parentColumnName;
				if (!Check.isEmpty(m_parentColumnName, true))
				{
					// explicit parent link defined
					parentColumnName = m_parentColumnName;
				}
				else
				{
					parentColumnName = linkColumnName;
				}

				String value = Env.getContext(ctx, m_vo.WindowNo, getParentTabNo(), parentColumnName, true);
				if (Env.isPropertyValueNull(parentColumnName, value))
				{
					value = Env.getContext(ctx, m_vo.WindowNo, parentColumnName, true); // back compatibility
				}

				// Same link value?
				if (refresh)
				{
					refresh = m_linkValue.equals(value);
				}
				m_linkValue = value;
				// Check validity
				if (Env.isPropertyValueNull(parentColumnName, value))
				{
					// log.error("No value for link column " + lc);
					// parent is new, can't retrieve detail
					// m_parentNeedSave = true; // metas: we are not using this anymore
					if (where.length() != 0)
					{
						where.append(" AND ");
					}
					// where.append(lc).append(" is null ");
					// as opened by this fix [ 1881480 ] Navigation problem between tabs
					// it's safer to avoid retrieving details at all if there is no parent value
					where.append(" 2=3");
					where_IsValid = false;
				}
				else
				{
					// we have column and value
					if (where.length() != 0)
					{
						where.append(" AND ");
					}
					where.append(getTableName()).append(".").append(linkColumnName).append("=");
					if (parentColumnName.endsWith("_ID"))
					{
						where.append(DB.TO_NUMBER(new BigDecimal(value), DisplayType.ID));
					}
					else
					{
						where.append(DB.TO_STRING(value));
					}
				}
			}
		}  	// isDetail

		m_extendedWhere = where.toString();
		// metas: begin - 01880
		// Case: see http://dewiki908/mediawiki/index.php/01880:_springe_zu_Datensatz_%28Auftrag%29_erzeugt_fehler_%282011072110000042%29#Mark_o_14:16.2C_17._Okt._2011_.28CEST.29
		if (m_extendedWhereInitial == null && where_IsValid)
		{
			m_extendedWhereInitial = m_extendedWhere;
		}

		//
		// We are reseting the tab query if:
		// * there is a a query to reset
		// * it's not a user query, because user queries shall be sticky (08380)
		// * if it was not a user entered query (using the search panel)
		// * where clause used also to join detail tab to parent tab changed (i.e. we navigated to another record)
		if (m_query.isActive()
				&& !m_query.isUserQuery() // 08380
				&& (!isSearchActive() || isDetail())
				&& !m_extendedWhere.equals(m_extendedWhereInitial))
		{
			m_query = new MQuery();
		}
		// metas: end

		// Final Query
		if (m_query.isActive())
		{
			final String queryWhereClause = validateQuery(m_query);
			if (!Check.isEmpty(queryWhereClause, true))
			{
				if (where.length() > 0)
				{
					where.append(" AND ");
				}
				where.append(" (").append(queryWhereClause).append(")");
			}
		}

		/**
		 * Query
		 */
		log.debug("{} - {}", this, where);
		// metas: begin: select same row after refresh
		int row_id = m_mTable.isOpen() ? getCurrentRow() : 0;
		int keyNo = getKeyID(row_id);
		if (!Check.isEmpty(m_vo.getDefaultWhereClause(), true))
		{
			if (m_mTable.isOpen())
			{
				row_id = getCurrentRow();
				keyNo = getKeyID(row_id);
			}
			else
			{
				final int maxRowsActual = getMaxQueryRecordsActual(maxRows);
				m_mTable.setSelectWhereClause(where.toString(),
						m_vo.getDefaultWhereClause(),
						m_vo.onlyCurrentRows && !isDetail(),
						onlyCurrentDays);
				m_mTable.open(maxRowsActual, 1);
				row_id = getCurrentRow();
				keyNo = getKeyID(row_id);
				m_mTable.close(false);
			}
		}
		// metas: end
		if (m_mTable.isOpen())
		{
			if (refresh)
			{
				m_mTable.dataRefreshAll();
			}
			else
			{
				m_mTable.dataRequery(where.toString(), m_vo.onlyCurrentRows && !isDetail(), onlyCurrentDays);
			}
		}
		else
		{
			final int maxRowsActual = getMaxQueryRecordsActual(maxRows);
			m_mTable.setSelectWhereClause(where.toString(), m_vo.getDefaultWhereClause(), m_vo.onlyCurrentRows && !isDetail(), onlyCurrentDays);
			m_mTable.open(maxRowsActual);
			
			// gh #986: find out if this is a "failed" zoom operation
			if (m_query != null 
					&& !Check.isEmpty(m_query.getZoomTableName()) 
					&& !Check.isEmpty(m_query.getZoomColumnName())
					&& m_mTable.getRowCount() < 1)
			{
				// gh #986: see if something can be done about the missing record. If so, request a retry. 
				if (NoDataFoundHandlers.get().invokeHandlers(
						m_query.getZoomTableName(), 
						new Object[] { m_query.getZoomValue() }, 
						PlainContextAware.newOutOfTrx(getCtx())))
				{
					// Since I don't know how to do the retry in here, I throw an exception and the retry will be handled somewhere else in the stack.
					throw new NoDataFoundHandlerRetryRequestException();
				}
			}
		}

		if (keyNo != m_mTable.getKeyID(m_currentRow))   // something changed
		{
			final int size = getRowCount();
			for (int i = 0; i < size; i++)
			{
				if (keyNo == m_mTable.getKeyID(i))
				{
					row_id = i;
					break;
				}
			}
		}
		// Go to Record 0
		setCurrentRow(row_id, true);

		//
		// Fire tab callouts: After Query
		try
		{
			tabCallouts.onAfterQuery(this);
		}
		catch (final Exception e)
		{
			log.error("Error while firing onAfterQuery", e);
		}
	}	// query

	/**
	 * Validate Query.
	 * If query column is not a tab column create EXISTS query
	 *
	 * @param query query
	 * @return where clause
	 */
	private String validateQuery(final MQuery query)
	{
		if (query == null || query.getRestrictionCount() == 0)
		{
			return null;
		}

		// Check: only one restriction
		if (query.getRestrictionCount() != 1)
		{
			log.debug("Ignored(More than 1 Restriction): " + query);
			return query.getWhereClause();
		}

		String colName = query.getColumnName(0);
		// metas: if query has no column name, see if it has a ZoomColumnName
		if (colName == null)
		{
			colName = query.getZoomColumnName();
		}
		// metas end
		if (colName == null)
		{
			log.debug("Ignored(No Column): " + query);
			return query.getWhereClause();
		}
		// a '(' in the name = function - don't try to resolve
		if (colName.indexOf('(') != -1)
		{
			log.debug("Ignored(Function): " + colName);
			return query.getWhereClause();
		}
		// OK - Query is valid

		// Zooms to the same Window (Parents, ..)
		String refColName = null;
		if (colName.equals("R_RequestRelated_ID"))
		{
			refColName = "R_Request_ID";
		}
		else if (colName.startsWith("C_DocType"))
		{
			refColName = "C_DocType_ID";
		}
		if (refColName != null)
		{
			query.setColumnName(0, refColName);
			if (getField(refColName) != null)
			{
				log.debug("Column " + colName + " replaced with synonym " + refColName);
				return query.getWhereClause();
			}
			refColName = null;
		}

		// Simple Query.
		if (getField(colName) != null)
		{
			log.debug("Field Found: " + colName);
			return query.getWhereClause();
		}

		// Find Reference Column e.g. BillTo_ID -> C_BPartner_Location_ID
		{
			final String sql = "SELECT cc.ColumnName "
					+ "FROM AD_Column c"
					+ " INNER JOIN AD_Ref_Table r ON (c.AD_Reference_Value_ID=r.AD_Reference_ID)"
					+ " INNER JOIN AD_Column cc ON (r.AD_Key=cc.AD_Column_ID) "
					+ "WHERE c.AD_Reference_ID IN (18,30)" 	// Table/Search
					+ " AND c.ColumnName=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setString(1, colName);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					refColName = rs.getString(1);
				}
			}
			catch (final SQLException e)
			{
				log.error("(ref) - Column=" + colName, e);
				return query.getWhereClause();
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}

		// Reference Column found
		if (refColName != null)
		{
			query.setColumnName(0, refColName);
			if (getField(refColName) != null)
			{
				log.debug("Column " + colName + " replaced with " + refColName);
				return query.getWhereClause();
			}
			colName = refColName;
		}

		// Column NOT in Tab - create EXISTS subquery
		String tableName = null;
		final String tabKeyColumn = getKeyColumnName();
		// Column=SalesRep_ID, Key=AD_User_ID, Query=SalesRep_ID=101

		{
			final String sql = "SELECT t.TableName "
					+ "FROM AD_Column c"
					+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
					+ "WHERE c.ColumnName=? AND IsKey='Y'"		// #1 Link Column
					+ " AND EXISTS (SELECT * FROM AD_Column cc"
					+ " WHERE cc.AD_Table_ID=t.AD_Table_ID AND cc.ColumnName=?)";	// #2 Tab Key Column
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setString(1, colName);
				pstmt.setString(2, tabKeyColumn);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					tableName = rs.getString(1);
				}
			}
			catch (final SQLException e)
			{
				log.error("Column=" + colName + ", Key=" + tabKeyColumn, e);
				return null;
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}

		// Special Reference Handling
		if (tabKeyColumn.equals("AD_Reference_ID"))
		{
			// Column=AccessLevel, Key=AD_Reference_ID, Query=AccessLevel='6'
			final String sql = "SELECT AD_Reference_ID FROM AD_Column WHERE ColumnName=?";
			final int AD_Reference_ID = DB.getSQLValue(null, sql, colName);
			return "AD_Reference_ID=" + AD_Reference_ID;
		}

		// Causes could be functions in query
		// e.g. Column=UPPER(Name), Key=AD_Element_ID, Query=UPPER(AD_Element.Name) LIKE '%CUSTOMER%'
		if (tableName == null)
		{
			log.info("Not successfull - Column="
					+ colName + ", Key=" + tabKeyColumn
					+ ", Query=" + query);
			return query.getWhereClause();
		}

		query.setTableName("xx");
		// use IN instead of EXISTS as subquery should be highly selective
		final StringBuffer result = new StringBuffer(getTableName()).append(".").append(tabKeyColumn)
				.append(" IN (SELECT xx.").append(tabKeyColumn)
				.append(" FROM ")
				.append(tableName).append(" xx WHERE ")
				.append(query.getWhereClause(true))
				.append(")");
		log.debug(result.toString());
		return result.toString();
	}	// validateQuery

	/**************************************************************************
	 * Refresh all data
	 */
	@Override
	public void dataRefreshAll()
	{
		final boolean retainCurrentRowIfAny = true; // default, backward compatibility
		dataRefreshAll(retainCurrentRowIfAny);
	}

	public void dataRefreshAll(final boolean retainCurrentRowIfAny)
	{
		log.debug("dataRefreshAll: {}", this);
		/**
		 * @todo does not work with alpha key
		 */
		final int keyNo = m_mTable.getKeyID(m_currentRow);

		// metas: c.ghita@metas.ro : US1207 : start
		if (retainCurrentRowIfAny && m_currentRow >= 0)
		{
			final Object[] rowData = m_mTable.getDataAtRow(m_currentRow);
			if (rowData != null)
			{
				// addNewRecordWhereClause only if we have a row selected and that row contains data
				// (e.g. he can have a row selected but somebody from other instance, deleted the data in meantime)

				// TODO: tsa: getWhereClause can log an warning if the current tab is new, save failed, and dataSave method is calling this one
				m_mTable.addNewRecordWhereClause(m_mTable.getWhereClause(rowData));
			}
		}
		// metas: c.ghita@metas.ro : US1207 : end

		if (!getTableModel().isCopyWithDetails())
		{
			m_mTable.dataRefreshAll();
		}
		// Should use RowID - not working for tables with multiple keys
		if (keyNo != -1)
		{
			if (keyNo != m_mTable.getKeyID(m_currentRow))     // something changed
			{
				final int size = getRowCount();
				for (int i = 0; i < size; i++)
				{
					if (keyNo == m_mTable.getKeyID(i))
					{
						m_currentRow = i;
						break;
					}
				}
			}
		}
		setCurrentRow(m_currentRow, true);
		fireStateChangeEvent(StateChangeEventType.DATA_REFRESH_ALL);
	}   // dataRefreshAll

	/**
	 * Refresh current row data
	 */
	@Override
	public void dataRefresh()
	{
		dataRefresh(m_currentRow);
	}   // dataRefresh

	/**
	 * Refresh current row data and refreshes data from included tabs too.
	 */
	@Override
	public void dataRefreshRecursively()
	{
		for (final GridTab includedTab : getIncludedTabs())
		{
			includedTab.dataRefreshAll();
		}
		dataRefresh();
	}

	/**
	 * Refresh row data
	 *
	 * @param row index
	 */
	public void dataRefresh(final int row)
	{
		log.debug("dataRefresh: {}, row={}", this, row);
		m_mTable.dataRefresh(row);
		setCurrentRow(row, true);
		fireStateChangeEvent(StateChangeEventType.DATA_REFRESH);
	}   // dataRefresh

	/**************************************************************************
	 * Unconditionally Save data
	 *
	 * @param manualCmd if true, no vetoable PropertyChange will be fired for save confirmation from MTable
	 * @return true if save complete (or nor required)
	 */
	@Override
	public boolean dataSave(final boolean manualCmd)
	{
		log.debug("dataSave: {}, row={}", this, m_currentRow);
		try
		{
			if (hasChangedCurrentTabAndParents())
			{
				return false;
			}
			final Properties ctx = getCtx();
			final boolean newRecord = m_mTable.isInserting(); // metas-2009_0021_AP1_CR061:
			// start: c.ghita@metas.ro: check for warning
			final String beforeChangeMsg = MIndexTable.getBeforeChangeWarning(ctx, this, newRecord);
			if (!Check.isEmpty(beforeChangeMsg))
			{
				if (!clientUI.ask(m_window.getWindowNo(), "Warning", beforeChangeMsg))
				{
					return true;
				}
			}
			// end: c.ghita@metas.ro: check for warning

			final char gridTableDataSaveResult = m_mTable.dataSave(manualCmd);
			final boolean retValue = gridTableDataSaveResult == GridTable.SAVE_OK;
			if (manualCmd)
			{
				setCurrentRow(m_currentRow, false);
				if (m_lastDataStatusEvent != null && m_lastDataStatusEvent.getCurrentRow() == m_currentRow
						&& (m_lastDataStatusEvent.Record_ID != null && m_lastDataStatusEvent.Record_ID instanceof Integer
								&& (Integer)m_lastDataStatusEvent.Record_ID == 0 || m_lastDataStatusEvent.Record_ID == null))
				{
					updateDataStatusEventProperties(m_lastDataStatusEvent);
				}
			}
			fireStateChangeEvent(StateChangeEventType.DATA_SAVE);

			if (retValue)
			{
				// refresh parent tabs
				refreshParents();
			}
			// metas-2009_0021_AP1_CR061: teo_sarca: begin
			if (retValue && MIndexTable.isAnyIndexedValueChanged(ctx, this, newRecord))   // metas: cg: task 03475
			{
				dataRefreshAll();
			}
			// metas-2009_0021_AP1_CR061: teo_sarca: end

			return retValue;
		}
		catch (final Exception e)
		{
			log.error("{} - row={}", this, m_currentRow, e);
		}
		return false;
	}   // dataSave

	/**
	 * Validate if the current tab record has changed in database or any parent record.
	 *
	 * @return true if there are changes
	 */
	public boolean hasChangedCurrentTabAndParents()
	{
		String msg = null;
		// Carlos Ruiz / globalqss - [ adempiere-Bugs-1985481 ] Processed documents can be edited
		// Validate that current record has not changed and validate that every parent above has not changed
		if (m_mTable.hasChanged(m_currentRow))
		{
			// return error stating that current record has changed and it cannot be saved
			msg = msgBL.getMsg(Env.getCtx(), "CurrentRecordModified");
			MetasfreshLastError.saveError(log, "CurrentRecordModified", msg, false);
			return true;
		}
		if (isDetail() && m_vo.IsCheckParentsChanged)   // 01962: check parents only if flag is active
		{
			// get parent tab
			// the parent tab is the first tab above with level = this_tab_level-1
			int level = getTabLevel();
			for (int i = m_window.getTabIndex(this) - 1; i >= 0; i--)
			{
				final GridTab parentTab = m_window.getTab(i);
				if (parentTab.getTabLevel() == level - 1)
				{
					// this is parent tab
					if (parentTab.m_mTable.hasChanged(parentTab.m_currentRow))
					{
						// return error stating that current record has changed and it cannot be saved
						msg = msgBL.getMsg(Env.getCtx(), "ParentRecordModified") + ": " + parentTab.getName();
						MetasfreshLastError.saveError(log, "ParentRecordModified", msg, false);
						return true;
					}
					else
					{
						// search for the next parent
						if (parentTab.isDetail())
						{
							level = parentTab.getTabLevel();
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		return false;
	}

	private void refreshParents()
	{
		if (isDetail())
		{
			// get parent tab
			// the parent tab is the first tab above with level = this_tab_level-1
			int level = getTabLevel();
			for (int i = m_window.getTabIndex(this) - 1; i >= 0; i--)
			{
				final GridTab parentTab = m_window.getTab(i);
				if (parentTab.getTabLevel() == level - 1)
				{
					// metas: nach letztem Merge von Teo war diese aenderung vorhanden
					// if (parentTab.getAD_Table_ID() == getAD_Table_ID()) {
					parentTab.dataRefresh();
					// }
					// metas end
					// search for the next parent
					if (parentTab.isDetail())
					{
						level = parentTab.getTabLevel();
					}
					else
					{
						break;
					}
				}
			}
			// refresh this tab
			dataRefresh();
		}
	}

	/**
	 * Do we need to Save?
	 *
	 * @param rowChange row change
	 * @param onlyRealChange if true the value of a field was actually changed
	 *            (e.g. for new records, which have not been changed) - default false
	 * @return true it needs to be saved
	 */
	public boolean needSave(final boolean rowChange, final boolean onlyRealChange)
	{
		if (rowChange)
		{
			return m_mTable.needSave(-2, onlyRealChange);
		}
		else
		{
			if (onlyRealChange)
			{
				return m_mTable.needSave();
			}
			else
			{
				return m_mTable.needSave(onlyRealChange);
			}
		}
	}   // isDataChanged

	/**
	 * Ignore data changes
	 */
	public void dataIgnore()
	{
		log.debug("dataIgnore: {}", this);
		//
		final int currentRow = m_currentRow;
		int previousRow = m_currentRow;
		if (m_mTable.isInserting() && m_currentRow > 0)
		{
			previousRow = m_currentRow - 1;
		}
		//
		m_mTable.dataIgnore();
		setCurrentRow(previousRow, false); // re-load data
		//
		if (currentRow != previousRow)
		{
			m_propertyChangeSupport.firePropertyChange(PROPERTY, currentRow, previousRow);
		}

		fireStateChangeEvent(StateChangeEventType.DATA_IGNORE);
		log.debug("dataIgnore finish: {}", this);
	}   // dataIgnore

	/**
	 * Create (or copy to a) new Row and process Callouts
	 *
	 * @param copyMode
	 * @return true if copied/new
	 */
	public boolean dataNew(final DataNewCopyMode copyMode)
	{
		Check.assumeNotNull(copyMode, "copyMode not null");

		// metas: end
		log.debug("dataNew: {}, copyMode={}", this, copyMode);
		if (!isInsertRecord())
		{
			log.warn("Insert not allowed for {}", this);
			return false;
		}

		// Prevent New Where Main Record is processed
		// but not apply for TabLevel=0 - teo_sarca [ 1673902 ]
		if (getTabLevel() > 0 && getTabNo() > 0)
		{
			if (isParentProcessedOrNotActive())
			{
				log.warn("New not allowed because parent tab is Processed/Not active");
				return false;
			}
		}

		// hengsin, dont create new when parent is empty
		if (isDetail() && isParentChanged(true))
		{
			final boolean parentTabSaved = getParentGridTab().dataSave(true); // metas: cg: task 05382
			if (!parentTabSaved)
			{
				return false;
			}
		}

		try
		{
			// Set Data Copy mode (05291)
			setDataNewCopyMode(copyMode);

			// temporary set currentrow to point to the new row to ensure even cause by m_mTable.dataNew
			// is handle properly.
			final int oldCurrentRow = m_currentRow;
			m_currentRow = m_currentRow + 1;
			final boolean retValue = m_mTable.dataNew(oldCurrentRow, copyMode); // metas: c.ghita@metas.ro
			m_currentRow = oldCurrentRow;
			if (!retValue)
			{
				return retValue;
			}
			setCurrentRow(m_currentRow + 1, true);

			// process all Callouts (no dependency check - assumed that settings are valid)
			for (int i = 0; i < getFieldCount(); i++)
			{
				processCallout(getField(i));
			}

			// check validity of defaults
			for (int i = 0; i < getFieldCount(); i++)
			{
				getField(i).refreshLookup();
				getField(i).validateValue();
			}

			// metas: Mark: the table as not changed only if the table was not already saved (Inserting)
			// i.e. during the creation of a new record a callout called dataSave() method
			if (m_mTable.isInserting())
			{
				m_mTable.setChanged(false);
			}

			//
			// Notify listeners that a new record is created.
			// NOTE: we need to do this while we keep the copyMode set because Tab Callouts will react here,
			// and they will try to do changes to current row and some of the callouts really depend on CopyMode value!
			fireStateChangeEvent(StateChangeEventType.DATA_NEW);

			return true; // success
		}
		finally
		{
			// Reset Data Copy mode (05291)
			resetDataNewCopyMode();
		}
	}   // dataNew

	/**
	 * Delete current Row
	 *
	 * @return true if deleted
	 */
	public boolean dataDelete()
	{
		log.debug("dataDelete: {}, row={}", this, m_currentRow);
		final boolean retValue = m_mTable.dataDelete(m_currentRow);
		setCurrentRow(m_currentRow, true);
		fireStateChangeEvent(StateChangeEventType.DATA_DELETE);

		// metas-2009_0021_AP1_CR061: teo_sarca: begin
		final Properties ctx = getCtx();
		if (MIndexTable.isAnyIndexedValueChanged(ctx, this, true))
		{
			dataRefreshAll();
		}
		// metas-2009_0021_AP1_CR061: teo_sarca: end
		// metas - us092: kh begin
		if (m_included)
		{
			refreshParents();
		}
		// metas - us092: kh end

		return retValue;
	}   // dataDelete

	/**
	 * Get Name of Tab
	 *
	 * @return name
	 */
	public String getName()
	{
		return m_vo.getName();
	}	// getName

	/**
	 * Get Description of Tab
	 *
	 * @return description
	 */
	public String getDescription()
	{
		return m_vo.getDescription();
	}	// getDescription

	/**
	 * Get Help of Tab
	 *
	 * @return help
	 */
	public String getHelp()
	{
		return m_vo.getHelp();
	}	// getHelp

	/**
	 * Get Tab Level
	 *
	 * @return tab level
	 */
	public int getTabLevel()
	{
		return m_vo.getTabLevel();
	}   // getTabLevel

	/**
	 * Get Commit Warning
	 *
	 * @return commit warning
	 */
	public String getCommitWarning()
	{
		return m_vo.getCommitWarning();
	}   // getCommitWarning

	/**
	 * Return Table Model
	 *
	 * @return MTable
	 */
	public final GridTable getMTable()
	{
		return m_mTable;
	}	// getMTable

	/**
	 * Return the name of the key column - may be ""
	 *
	 * @return key column name
	 */
	public String getKeyColumnName()
	{
		return m_keyColumnName;
	}	// getKeyColumnName

	/**
	 * Set Name of the Key Column
	 *
	 * @param keyColumnName
	 */
	private void setKeyColumnName(final String keyColumnName)
	{
		m_keyColumnName = keyColumnName;
		final Properties ctx = getCtx();
		Env.setContext(ctx, m_vo.getWindowNo(), m_vo.getTabNo(), CTX_KeyColumnName, keyColumnName);

		attachmentsMap.setKeyColumnName(keyColumnName);
		lockedRecordIdsSupplier.forget();
	}

	/**
	 * Return Name of link column
	 *
	 * @return link column name
	 */
	public String getLinkColumnName()
	{
		return m_linkColumnName;
	}	// getLinkColumnName

	/**
	 * Set Name of link column.
	 * Set from MWindow.loadTabData
	 * Used in MTab.isCurreny, (.setCurrentRow) .query - APanel.cmd_report
	 * and MField.isEditable and .isDefault via context
	 *
	 * @param linkColumnName name of column - or sets name to AD_Column_ID, if exists
	 */
	/* package */void setLinkColumnName(final String linkColumnName)
	{
		//
		// Set parent column name (if any)
		if (m_vo.getParent_Column_ID() > 0)
		{
			m_parentColumnName = Services.get(IADTableDAO.class).retrieveColumnName(m_vo.getParent_Column_ID());
		}
		else
		{
			m_parentColumnName = "";
		}
		// Normalize m_parentColumnName
		if (Check.isEmpty(m_parentColumnName, true))
		{
			m_parentColumnName = "";
		}

		//
		// Set LinkColumnName
		if (linkColumnName != null)
		{
			m_linkColumnName = linkColumnName;
		}
		else
		{
			final int linkColumnId = m_vo.getAD_Column_ID();
			if (linkColumnId <= 0)
			{
				return;
			}
			// we have a link column identified (primary parent column)
			else
			{
				m_linkColumnName = Services.get(IADTableDAO.class).retrieveColumnName(linkColumnId);
				log.debug("AD_Column_ID={} - {}", linkColumnId, m_linkColumnName);
			}
		}

		final Properties ctx = getCtx();
		Env.setContext(ctx, m_vo.getWindowNo(), m_vo.getTabNo(), CTX_LinkColumnName, m_linkColumnName);
	}	// setLinkColumnName

	/**
	 * Is the tab current?.
	 *
	 * <pre>
	 * Yes 	- Table must be open
	 * 		- Query String is the same
	 * 		- Not Detail
	 * 		- Old link column value is same as current one
	 * </pre>
	 *
	 * @return true if current
	 */
	public boolean isCurrent()
	{
		// Open?
		if (!m_mTable.isOpen())
		{
			return false;
		}
		// Same Query
		if (!m_oldQuery.equals(m_query.getWhereClause()))
		{
			return false;
		}
		// Detail?
		if (!isDetail())
		{
			return true;
		}
		// Same link column value
		final Properties ctx = getCtx();
		final String value = Env.getContext(ctx, m_vo.WindowNo, getLinkColumnName());
		return m_linkValue.equals(value);
	}	// isCurrent

	/**
	 * Is the tab/table currently open
	 *
	 * @return true if open
	 */
	public boolean isOpen()
	{
		// Open?
		if (m_mTable != null)
		{
			return m_mTable.isOpen();
		}
		return false;
	}	// isCurrent

	/**
	 * Is Tab Included in other Tab
	 *
	 * @return true if included
	 */
	public boolean isIncluded()
	{
		return m_included;
	}   // isIncluded

	/**
	 * Is Tab Included in other Tab
	 *
	 * @param isIncluded true if included
	 */
	public void setIncluded(final boolean isIncluded)
	{
		m_included = isIncluded;
	}   // setIncluded

	/**
	 * Are Only Current Rows displayed
	 *
	 * @return true if no history
	 */
	public boolean isOnlyCurrentRows()
	{
		return m_vo.onlyCurrentRows;
	}   // isOnlyCurrentRows

	/**
	 * Return Parent ArrayList
	 *
	 * @return parent column names
	 */
	public List<String> getParentColumnNames()
	{
		return m_parents;
	}	// getParentColumnNames

	/**
	 * Get Tree ID of this tab
	 *
	 * @return ID
	 */

	/**
	 * Returns true if this is a detail record
	 *
	 * @return true if not parent tab
	 */
	public boolean isDetail()
	{
		// First Tab Level is not a detail
		if (m_vo.getTabLevel() == 0)
		{
			return false;
		}
		// We have IsParent columns and/or a link column
		if (m_parents.size() > 0 || m_vo.getAD_Column_ID() > 0)
		{
			return true;
		}
		return false;
	}	// isDetail

	/**
	 * Is Printed (Document can be printed)
	 *
	 * @return true if printing
	 */
	public boolean isPrinted()
	{
		return m_vo.getPrint_Process_ID() > 0;
	}	// isPrinted

	/**
	 * Get WindowNo
	 *
	 * @return window no
	 */
	public final int getWindowNo()
	{
		return m_vo.WindowNo;
	}	// getWindowNo

	/**
	 * Get TabNo
	 *
	 * @return tab no
	 */
	public final int getTabNo()
	{
		return m_vo.getTabNo();
	}	// getTabNo

	/**
	 * Get Process ID
	 *
	 * @return Process ID
	 */
	public int getAD_Process_ID()
	{
		return m_vo.getPrint_Process_ID();
	}	// getAD_Process_ID

	/**
	 * Is High Volume?
	 *
	 * @return true if high volume table
	 */
	public boolean isHighVolume()
	{
		return m_vo.IsHighVolume;
	}	// isHighVolume

	/**
	 * Is Read Only?
	 *
	 * @return true if read only
	 */
	public boolean isReadOnly()
	{
		if (m_vo.isReadOnly())
		{
			return true;
		}

		// hengsin, make detail readonly when parent is empty
		if (isParentChanged(true))
		{
			return true;
		}

		// ** dynamic content ** uses get_ValueAsString
		final ILogicExpression readOnlyLogic = m_vo.getReadOnlyLogic();
		final boolean readOnly = readOnlyLogic.evaluate(this, true); // ignoreUnparsable=true // metas: 03093
		log.trace("Evaluated IsReadOnly: {} {} => {}", m_vo, readOnlyLogic, readOnly);
		if (readOnly)
		{
			return true;
		}

		//
		// Make sure our parent (if any) is not processed and it's active
		if (isParentProcessedOrNotActive())
		{
			return true;
		}

		return readOnly;
	}	// isReadOnly

	/**
	 * @task 08428
	 * @return <code>true</code> if this tab is a detail tab, of the parent tab is inactive, processed or currently processing.
	 */
	/* package */final boolean isParentProcessedOrNotActive()
	{
		// If we are not on a detail tab, we consider our "parent" as Not Processed / Active
		if (!isDetail())
		{
			return false;
		}

		final int parentTabNo = getParentTabNo();
		if (parentTabNo < 0)
		{
			// No parent TabNo found?!?!
			// => consider the parent as processed to prevent other fucked up things
			log.warn("No parent TabNo found?!?! Consider the parent as processed to prevent other errors.");
			return true;
		}

		if (isProcessedOrNotActive(getCtx(), getWindowNo(), parentTabNo))
		{
			return true;
		}

		// Fallback: consider parent not processed/active
		return false;
	}

	/**
	 * Recursivelly check if this {@link GridTab} or any of it parents are Processed or Not Active.
	 *
	 * @param ctx context to be used for evaluation
	 * @return true if this tab (or one of it's parents) is processed
	 */
	public final boolean isProcessedOrNotActive(final Properties ctx)
	{
		final int windowNo = getWindowNo();

		GridTab currentTab = this;
		while (currentTab != null)
		{
			final int tabNo = currentTab.getTabNo();
			if (isProcessedOrNotActive(ctx, windowNo, tabNo))
			{
				return true;
			}

			currentTab = currentTab.getParentGridTab();
		}

		// Fallback: consider it as not processed/active
		return false;
	}

	/**
	 * Helper method which looks into the context for a given <code>windowNo</code>/<code>tabNo</code> and checks is it's Processed or Not Active.
	 *
	 * @param ctx
	 * @param windowNo
	 * @param tabNo
	 * @return true if the context for windowNo/tabNo says that is Processed or Not Active
	 */
	/* package */static final boolean isProcessedOrNotActive(final Properties ctx, final int windowNo, final int tabNo)
	{
		final boolean processed = isProcessed(ctx, windowNo, tabNo);
		if (processed)
		{
			return true;
		}

		final boolean isActive = "Y".equals(Env.getContext(ctx, windowNo, tabNo, "IsActive", Scope.Tab));
		if (!isActive)
		{
			return true;
		}

		// Fallback: consider it as not processed/active
		return false;
	}

	/* package */static final boolean isProcessed(final Properties ctx, final int windowNo, final int tabNo)
	{
		final boolean processed = "Y".equals(Env.getContext(ctx, windowNo, tabNo, "Processed", Scope.Tab));
		if (processed)
		{
			return true;
		}

		final boolean processing = "Y".equals(Env.getContext(ctx, windowNo, tabNo, "Processing", Scope.Tab));
		if (processing)
		{
			return true;
		}

		return false; // not processed
	}

	/**
	 * Tab contains Always Update Field
	 *
	 * @return true if field with always updateable
	 */
	public boolean isAlwaysUpdateField()
	{
		for (int i = 0; i < m_mTable.getColumnCount(); i++)
		{
			final GridField field = m_mTable.getField(i);
			if (field.isAlwaysUpdateable())
			{
				return true;
			}
		}
		return false;
	}	// isAlwaysUpdateField

	/**
	 * Can we Insert Records?
	 *
	 * @return true not read only and allowed
	 */
	public boolean isInsertRecord()
	{
		// metas: Sort tabs does not support record inserting
		if (isSortTab())
		{
			return false;
		}
		if (isReadOnly())
		{
			return false;
		}
		return m_vo.isInsertRecord();
	}	// isInsertRecord

	/**
	 * Can we Delelte Records?
	 *
	 * @return true of not read-only and allowed
	 */
	public boolean isDeletable()
	{
		// metas: Sort tabs does not support record inserting
		if (isSortTab())
		{
			return false;
		}
		if (isReadOnly())
		{
			return false;
		}
		return m_vo.isDeleteable();
	}

	/**
	 * Is the Tab Visible.
	 * Called when constructing the window.
	 *
	 * @return true, if displayed
	 */
	public boolean isDisplayed()
	{
		// metas: 03093: using IExpressionEvaluatorBL framework

		// metas: 03224: Note that we need to provide a 'm_vo.WindowNo' or else it can't resolve non-global ctx
		// values. In the old code 'Env.parseContext()' with WindowNo 0 didn't find anything, but when nothing came out
		// of the parsing, we assumed 'true'. If parsing fails, we still assume true, but with the 'WindowNo', there is
		// at least a chance of suceeding.
		final Properties ctx = getCtx();
		final Evaluatee evaluateeCtx = Evaluatees.ofCtx(ctx, m_vo.getWindowNo(), false);
		try
		{
			return m_vo.getDisplayLogic().evaluate(evaluateeCtx, false); // ignoreUnparsable
		}
		catch (final ExpressionException e)
		{
			// Expression could not be evaluated at this point.
			// For safety we will display this tab and the actual evaluation will be done later
			return true;
		}
	}	// isDisplayed

	/**
	 * Get Variable Value (Evaluatee)
	 *
	 * @param variableName name
	 * @return value
	 */
	@Override
	public String get_ValueAsString(final String variableName)
	{
		final Properties ctx = getCtx();
		return Env.getContext(ctx, m_vo.WindowNo, variableName, true);
	}	// get_ValueAsString

	/**
	 * Is Single Row
	 *
	 * @return true if single row
	 */
	public boolean isSingleRow()
	{
		return m_vo.IsSingleRow;
	}   // isSingleRow;

	/**
	 * Set Single Row.
	 * Temporary store of current value
	 *
	 * @param isSingleRow toggle
	 */
	public void setSingleRow(final boolean isSingleRow)
	{
		m_vo.IsSingleRow = isSingleRow;
	}   // setSingleRow

	/**
	 * Has Tree
	 *
	 * @return true if tree exists
	 */
	public boolean isTreeTab()
	{
		return m_vo.HasTree;
	}   // isTreeTab

	/**
	 * Get Tab ID
	 *
	 * @return Tab ID
	 */
	@Override
	public int getAD_Tab_ID()
	{
		return m_vo.getAD_Tab_ID();
	}	// getAD_Tab_ID

	/**
	 * Get Table ID
	 *
	 * @return Table ID
	 */
	public int getAD_Table_ID()
	{
		return m_vo.getAD_Table_ID();
	}	// getAD_Table_ID

	/**
	 * Get Window ID
	 *
	 * @return Window ID
	 */
	public int getAD_Window_ID()
	{
		return m_vo.getAD_Window_ID();
	}	// getAD_Window_ID

	/**
	 * Get Included Tab ID
	 *
	 * @return Included_Tab_ID
	 */
	public int getIncluded_Tab_ID()
	{
		return m_vo.Included_Tab_ID;
	}	// getIncluded_Tab_ID

	/**
	 * Get TableName
	 *
	 * @return Table Name
	 */
	@Override
	public String getTableName()
	{
		return m_vo.TableName;
	}	// getTableName

	/**
	 * Get Tab Where Clause
	 *
	 * @return where clause
	 */
	public String getWhereClause()
	{
		return m_vo.getWhereClause();
	}	// getWhereClause

	/**
	 * Is Sort Tab
	 *
	 * @return true if sort tab
	 */
	public boolean isSortTab()
	{
		return m_vo.IsSortTab;
	}	// isSortTab

	/**
	 * Get Order column for sort tab
	 *
	 * @return AD_Column_ID
	 */
	public int getAD_ColumnSortOrder_ID()
	{
		return m_vo.AD_ColumnSortOrder_ID;
	}	// getAD_ColumnSortOrder_ID

	/**
	 * Get Yes/No column for sort tab
	 *
	 * @return AD_Column_ID
	 */
	public int getAD_ColumnSortYesNo_ID()
	{
		return m_vo.AD_ColumnSortYesNo_ID;
	}	// getAD_ColumnSortYesNo_ID

	/**************************************************************************
	 * Get extended Where Clause (parent link)
	 *
	 * @return parent link
	 */
	public String getWhereExtended()
	{
		return m_extendedWhere;
	}	// getWhereExtended

	/**
	 * Get Order By Clause
	 *
	 * @param onlyCurrentRows only current rows
	 * @return Order By Clause
	 */
	private String getOrderByClause(final boolean onlyCurrentRows)
	{
		// First Prio: Tab Order By
		if (m_vo.OrderByClause.length() > 0)
		{
			final Properties ctx = getCtx();
			final String orderBy = Env.parseContext(ctx, m_vo.WindowNo, m_vo.OrderByClause, false, false);
			return orderBy;
		}

		// Second Prio: Fields (save it)
		m_vo.OrderByClause = "";
		for (int i = 0; i < 3; i++)
		{
			final String order = m_OrderBys[i];
			if (order != null && order.length() > 0)
			{
				if (m_vo.OrderByClause.length() > 0)
				{
					m_vo.OrderByClause += ",";
				}
				m_vo.OrderByClause += order;
			}
		}
		if (m_vo.OrderByClause.length() > 0)
		{
			return m_vo.OrderByClause;
		}

		// Third Prio: onlyCurrentRows
		m_vo.OrderByClause = "Created";
		if (onlyCurrentRows && !isDetail())
		{
			m_vo.OrderByClause += " DESC";
		}
		return m_vo.OrderByClause;
	}	// getOrderByClause

	/**
	 * Load Dependent Information
	 */
	private void loadDependentInfo()
	{
		/**
		 * Load Order Type from C_DocTypeTarget_ID
		 */
		if (m_vo.TableName.equals("C_Order"))
		{
			int C_DocTyp_ID = 0;
			final Integer target = (Integer)getValue("C_DocTypeTarget_ID");
			if (target != null)
			{
				C_DocTyp_ID = target.intValue();
			}
			if (C_DocTyp_ID == 0)
			{
				return;
			}

			final String sql = "SELECT DocSubType FROM C_DocType WHERE C_DocType_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, C_DocTyp_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					Env.setContext(getCtx(), m_vo.WindowNo, "OrderType", rs.getString(1));
				}
			}
			catch (final SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}     // loadOrderInfo

		// Set the Phone Format on BPartnerLocation based on Country
		if (m_vo.TableName.equals("C_BPartner_Location"))
		{
			final Integer location_int = (Integer)getValue(I_C_BPartner_Location.COLUMNNAME_C_Location_ID);
			String phone_frm = null;
			if (location_int != null)
			{
				// take the phone format from country
				phone_frm = DB.getSQLValueString(null, "SELECT ExpressionPhone FROM C_Country c, C_Location l WHERE c.C_Country_ID = l.C_Country_ID AND l.C_location_ID = ?", location_int);
			}
			final GridField fPhone = getField(I_C_BPartner_Location.COLUMNNAME_Phone);
			MColumn colPhone = null;
			if (fPhone != null)
			{
				colPhone = MColumn.get(Env.getCtx(), fPhone.getAD_Column_ID());
			}
			final GridField fPhone2 = getField(I_C_BPartner_Location.COLUMNNAME_Phone2);
			MColumn colPhone2 = null;
			if (fPhone2 != null)
			{
				colPhone2 = MColumn.get(Env.getCtx(), fPhone2.getAD_Column_ID());
			}
			final GridField fFax = getField(I_C_BPartner_Location.COLUMNNAME_Fax);
			MColumn colFax = null;
			if (fFax != null)
			{
				colFax = MColumn.get(Env.getCtx(), fFax.getAD_Column_ID());
			}
			// Apply the country format if the column doesn't have format
			if (colPhone != null && (colPhone.getVFormat() == null || colPhone.getVFormat().length() == 0))
			{
				fPhone.setVFormat(phone_frm);
			}
			if (colPhone2 != null && (colPhone2.getVFormat() == null || colPhone2.getVFormat().length() == 0))
			{
				fPhone2.setVFormat(phone_frm);
			}
			if (colFax != null && (colFax.getVFormat() == null || colFax.getVFormat().length() == 0))
			{
				fFax.setVFormat(phone_frm);
			}
		}

	}   // loadDependentInfo

	/**************************************************************************
	 * Load Attachments for this table
	 */
	public void loadAttachments()
	{
		final int recordId = getRecord_ID();
		attachmentsMap.reset(recordId);
	}	// loadAttachment

	/**
	 * Can this tab have Attachments?.
	 * <p>
	 * It can have an attachment if it has a key column ending with _ID. The key column is empty, if there is no single identifying key.
	 *
	 * @return true if record can have attachment
	 */
	public boolean canHaveAttachment()
	{
		return attachmentsMap.canHaveAttachment();
	}   // canHaveAttachment

	/**
	 * Returns true, if current row has an Attachment
	 *
	 * @return true if record has attachment
	 */
	public boolean hasAttachment()
	{
		final int recordId = getRecord_ID();
		return attachmentsMap.hasAttachment(recordId);
	}	// hasAttachment

	/**
	 * Get Attachment_ID for current record.
	 *
	 * @return ID or 0, if not found
	 */
	public int getAD_AttachmentID()
	{
		final int recordId = getRecord_ID();
		return attachmentsMap.getAD_Attachment_ID(recordId);
	}	// getAttachmentID

	/**************************************************************************
	 * Load Chats for this table
	 */
	public void loadChats()
	{
		log.debug("loadChats: {}", this);
		if (!canHaveAttachment())
		{
			return;
		}

		final String sql = "SELECT CM_Chat_ID, Record_ID FROM CM_Chat WHERE AD_Table_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			if (m_Chats == null)
			{
				m_Chats = new HashMap<>();
			}
			else
			{
				m_Chats.clear();
			}
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, m_vo.AD_Table_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final Integer recordId = rs.getInt(2);	// Record_ID
				final Integer chatId = rs.getInt(1);	// CM_Chat_ID
				m_Chats.put(recordId, chatId);
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		log.info("#" + m_Chats.size());
	}	// loadChats

	/**
	 * Returns true, if current row has a Chat
	 *
	 * @return true if record has chat
	 */
	public boolean hasChat()
	{
		if (m_Chats == null)
		{
			loadChats();
		}
		if (m_Chats == null || m_Chats.isEmpty())
		{
			return false;
		}
		//
		final Integer key = new Integer(m_mTable.getKeyID(m_currentRow));
		return m_Chats.containsKey(key);
	}	// hasChat

	/**
	 * Get Chat_ID for this record.
	 *
	 * @return ID or 0, if not found
	 */
	public int getCM_ChatID()
	{
		if (m_Chats == null)
		{
			loadChats();
		}
		if (m_Chats.isEmpty())
		{
			return 0;
		}
		//
		final Integer key = new Integer(m_mTable.getKeyID(m_currentRow));
		final Integer value = m_Chats.get(key);
		if (value == null)
		{
			return 0;
		}
		else
		{
			return value.intValue();
		}
	}	// getCM_ChatID

	/** Retrieve locked recordIds for current user */
	private Set<Integer> retrieveLockedRecordIds()
	{
		if (!canHaveAttachment())
		{
			return ImmutableSet.of();
		}
		
		final int adUserId = Env.getAD_User_ID(Env.getCtx());
		return Services.get(IUserRolePermissionsDAO.class).retrievePrivateAccessRecordIds(adUserId, getAD_Table_ID());
	}
	
	private Set<Integer> getLockedRecordIds()
	{
		return lockedRecordIdsSupplier.get();
	}

	/**
	 * @return true if the record is locked
	 */
	public boolean isLocked()
	{
		if (!Env.getUserRolePermissions(getCtx()).hasPermission(IUserRolePermissions.PERMISSION_PersonalLock))
		{
			return false;
		}
		
		return getLockedRecordIds().contains(getRecord_ID());
	}	// isLocked

	/**
	 * Lock Record
	 *
	 * @param ctx context
	 * @param recordId id
	 * @param lock true if lock, otherwise unlock
	 */
	public void lock(final int recordId, final boolean lock)
	{
		final int adUserId = Env.getAD_User_ID(Env.getCtx());
		final int adTableId = getAD_Table_ID();
		
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		if(lock)
		{
			permissionsDAO.createPrivateAccess(adUserId, adTableId, recordId);
		}
		else
		{
			permissionsDAO.deletePrivateAccess(adUserId, adTableId, recordId);
		}
		
		lockedRecordIdsSupplier.forget();
	}

	/**************************************************************************
	 * Data Status Listener from MTable.
	 * - get raw info and add current row information
	 * - update the current row
	 * - redistribute (fire) Data Status event
	 *
	 * @param e event
	 */
	@Override
	public void dataStatusChanged(final DataStatusEvent e)
	{
		log.debug("dataStatusChanged: {} - {}", this, e);
		final int oldCurrentRow = e.getCurrentRow();
		m_DataStatusEvent = e;          // save it
		// when sorted set current row to 0
		final String msg = m_DataStatusEvent.getAD_Message();
		if (msg != null && msg.equals("Sorted"))
		{
			setCurrentRow(0, true);
		}
		// set current row
		m_DataStatusEvent = e;          // setCurrentRow clear it, need to save again
		m_DataStatusEvent.setCurrentRow(m_currentRow);
		// Same row - update value
		if (oldCurrentRow == m_currentRow)
		{
			final GridField field = m_mTable.getField(e.getChangedColumn());
			if (field != null)
			{
				final Object value = m_mTable.getValueAt(m_currentRow, e.getChangedColumn());
				field.setValue(value, m_mTable.isInserting());
			}
		}
		else
		{
			// Redistribute Info with current row info
			fireDataStatusChanged(m_DataStatusEvent);
		}

		// reset
		m_lastDataStatusEventTime = System.currentTimeMillis();
		m_lastDataStatusEvent = m_DataStatusEvent;
		m_DataStatusEvent = null;
		// log.debug("dataStatusChanged #" + m_vo.TabNo + "- fini", e.toString());
	}	// dataStatusChanged

	/**
	 * Inform Listeners and build WHO info
	 *
	 * @param e event
	 */
	// metas: CHANGED - needs to be public, called from AttachmentDndTransferHandler
	public void fireDataStatusChanged(final DataStatusEvent e)
	{
		final DataStatusListener[] listeners = m_listenerList.getListeners(DataStatusListener.class);
		if (Check.isEmpty(listeners))
		{
			return;
		}

		log.debug("Event: {}", e);
		// WHO Info
		if (e.getCurrentRow() >= 0)
		{
			updateDataStatusEventProperties(e);
		}
		e.setInserting(m_mTable.isInserting());
		// Distribute/fire it
		for (int i = 0; i < listeners.length; i++)
		{
			listeners[i].dataStatusChanged(e);
			// log.debug("fini - " + e.toString());
		}
	}	// fireDataStatusChanged

	private void updateDataStatusEventProperties(final DataStatusEvent e)
	{
		final String keyColumnName = getKeyColumnName();

		e.Created = (Timestamp)getValue("Created");
		e.CreatedBy = (Integer)getValue("CreatedBy");
		e.Updated = (Timestamp)getValue("Updated");
		e.UpdatedBy = (Integer)getValue("UpdatedBy");
		e.Record_ID = getValue(keyColumnName);
		// Info
		final StringBuilder info = new StringBuilder(getTableName());
		// We have a key column
		if (keyColumnName != null && keyColumnName.length() > 0)
		{
			info.append(" - ").append(keyColumnName).append("=").append(e.Record_ID);
		}
		else
		// we have multiple parents
		{
			for (int i = 0; i < m_parents.size(); i++)
			{
				final String keyCol = m_parents.get(i);
				info.append(" - ").append(keyCol).append("=").append(getValue(keyCol));
			}
		}
		e.Info = info.toString();
	}

	/**
	 * Create and fire Data Status Error Event
	 *
	 * @param AD_Message message
	 * @param info info
	 * @param isError if not true, it is a Warning
	 */
	public void fireDataStatusEEvent(final String AD_Message, final String info, final boolean isError)
	{
		m_mTable.fireDataStatusEEvent(AD_Message, info, isError);
	}   // fireDataStatusEvent

	/**
	 * Create and fire Data Status Error Event (from Error Log)
	 *
	 * @param errorLog log
	 */
	public void fireDataStatusEEvent(final ValueNamePair errorLog)
	{
		if (errorLog != null)
		{
			m_mTable.fireDataStatusEEvent(errorLog);
		}
	}   // fireDataStatusEvent

	/**
	 * Get Current Row. This method will validate the current row before returing it.
	 *
	 * @return current row
	 */
	public int getCurrentRow()
	{
		if (m_currentRow != verifyRow(m_currentRow))
		{
			setCurrentRow(m_mTable.getRowCount() - 1, true);
		}
		return m_currentRow;
	}   // getCurrentRow

	/**
	 * Gets current row as it is now. Do not validate it, just get it.
	 *
	 * @return
	 */
	public int getCurrentRowNoCheck()
	{
		return m_currentRow;
	}

	/**
	 * Get Current Table Key ID
	 *
	 * @return Record_ID
	 */
	public int getRecord_ID()
	{
		final GridTable gridTable = getMTable();
		if (gridTable == null)
		{
			return -1;
		}
		return gridTable.getKeyID(m_currentRow);
	}   // getRecord_ID

	@Override
	public <T> T getModel(final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(this, modelClass);
	}

	@Override
	public <T> T getModelBeforeChanges(final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.createOld(this, modelClass);
	}

	/**
	 * Get Key ID of row
	 *
	 * @param row row number
	 * @return The Key ID of the row or -1 if not found
	 */
	public int getKeyID(final int row)
	{
		return m_mTable.getKeyID(row);
	}   // getCurrentKeyID

	/**
	 * Navigate absolute - goto Row - (zero based).
	 * - does nothing, if in current row
	 * - saves old row if required
	 *
	 * @param targetRow target row
	 * @return current row
	 */
	public int navigate(final int targetRow)
	{
		// nothing to do
		if (targetRow == m_currentRow)
		{
			return m_currentRow;
		}
		log.info("Row=" + targetRow);

		// Row range check
		int newRow = verifyRow(targetRow);

		// Check, if we have old uncommitted data
		if (m_mTable.dataSave(newRow, false) == false)
		{
			return m_currentRow;
		}

		// remove/ignore new and unchange row
		if (m_mTable.isInserting())
		{
			if (newRow > m_currentRow)
			{
				newRow--;
			}
			dataIgnore();
		}

		// new position
		return setCurrentRow(newRow, true);
	}   // navigate

	/**
	 * Navigate relatively - i.e. plus/minus from current position
	 *
	 * @param rowChange row change
	 * @return current row
	 */
	public int navigateRelative(final int rowChange)
	{
		return navigate(m_currentRow + rowChange);
	}   // navigateRelative

	/**
	 * Navigate to current now (reload)
	 *
	 * @return current row
	 */
	public int navigateCurrent()
	{
		log.info("Row=" + m_currentRow);
		return setCurrentRow(m_currentRow, true);
	}   // navigateCurrent

	/**
	 * Row Range check
	 *
	 * @param targetRow target row
	 * @return checked row
	 */
	private int verifyRow(final int targetRow)
	{
		int newRow = targetRow;
		// Table Open?
		if (!m_mTable.isOpen())
		{
			log.error("Table not open");
			return -1;
		}
		// Row Count
		final int rows = getRowCount();
		if (rows == 0)
		{
			log.debug("No Rows");
			return -1;
		}
		if (newRow >= rows)
		{
			newRow = rows - 1;
			log.debug("Set to max Row: " + newRow);
		}
		else if (newRow < 0)
		{
			newRow = 0;
			log.debug("Set to first Row");
		}
		return newRow;
	}   // verifyRow

	/**
	 * Set current row and load data into fields.
	 * If there is no row - load nulls
	 *
	 * @param newCurrentRow new current row
	 * @param fireEvents fire GridTab events; Please note, this is NOT about GridField events (which will be always fired).
	 * @return current row
	 */
	private int setCurrentRow(final int newCurrentRow, final boolean fireEvents)
	{
		final int oldCurrentRow = m_currentRow;
		m_currentRow = verifyRow(newCurrentRow);
		log.debug("Row=" + m_currentRow + " - fire=" + fireEvents);

		Env.setContextAsInt(getCtx(), getWindowNo(), getTabNo(), CTX_CurrentRow, m_currentRow);

		Env.setContext(getCtx(), getWindowNo(), getTabNo(), CTX_RowLoading, "Y");
		try
		{
			// Clear tab context
			// We want to make sure we get rid of leftovers
			clearTabContext();

			// Update Field Values
			final int size = m_mTable.getColumnCount();
			final boolean isInserting = m_mTable.isInserting();

			for (int i = 0; i < size; i++)
			{
				final GridField mField = m_mTable.getField(i);

				// We need to delay all events because,
				// first we need to set all values and update the context
				// and then fire the events which propagate the value to UI component
				// and it will also trigger context based validations.
				// NOTE: if we are not doing this, when validating, it could happen to have an INCONSISTENT context (some values are set, some are not).
				mField.delayEvents();

				// get Value from Table
				if (m_currentRow >= 0)
				{
					final Object value = m_mTable.getValueAt(m_currentRow, i);
					mField.setValue(value, isInserting);
				}
				else
				{   // no rows - set to a reasonable value - not updateable
					// Object value = null;
					// if (mField.isKey() || mField.isParent() || mField.getColumnName().equals(m_linkColumnName))
					// value = mField.getDefault();

					// CarlosRuiz - globalqss [ 1881480 ] Navigation problem between tabs
					// the implementation of linking with window context variables is very weak
					// you must be careful when defining a column in a detail tab with a field
					// with the same column name as some of the links of the tabs above
					// this can cause bad behavior of linking
					final boolean updateContext = mField.isKey();
					mField.setValueToNull(updateContext);
				}
			}

			//
			// Release all blocked GridField events
			for (int i = 0; i < size; i++)
			{
				final GridField gridField = m_mTable.getField(i);
				gridField.releaseDelayedEvents();

				//
				// Validate value if needed
				if (m_currentRow >= 0 && isInserting)
				{
					gridField.validateValue();
				}
			}

			loadDependentInfo();

			if (!fireEvents)
			{
				return m_currentRow;
			}

			// inform VTable/.. -> rowChanged
			m_propertyChangeSupport.firePropertyChange(PROPERTY, oldCurrentRow, m_currentRow);

			// check last data status event
			final long since = System.currentTimeMillis() - m_lastDataStatusEventTime;
			if (since <= 500 && m_lastDataStatusEvent != null)
			{
				m_DataStatusEvent = m_lastDataStatusEvent;
			}

			// inform APanel/.. -> dataStatus with row updated
			if (m_DataStatusEvent == null)
			{
				m_DataStatusEvent = new DataStatusEvent(this, getRowCount(),
						isInserting,  		// changed
						Env.isAutoCommit(Env.getCtx(), m_vo.WindowNo), isInserting);
			}
			//
			m_DataStatusEvent.setCurrentRow(m_currentRow);
			final String status = m_DataStatusEvent.getAD_Message();
			if (status == null || status.length() == 0)
			{
				m_DataStatusEvent.setInfo(DEFAULT_STATUS_MESSAGE, null, false, false);
			}
			fireDataStatusChanged(m_DataStatusEvent);

			return m_currentRow;
		}
		finally
		{
			Env.setContext(Env.getCtx(), getWindowNo(), getTabNo(), CTX_RowLoading, null);
		}
	}   // setCurrentRow

	public void setCurrentRowByRecord(final ITableRecordReference record)
	{
		if (record == null)
		{
			return;
		}

		if (!getTableName().equals(record.getTableName()))
		{
			log.warn("Cannot select current row by record because table name is not compatible (GridTab:{}, Record:{})", this, record);
			return;
		}

		final int recordId = record.getRecord_ID();

		final int rowCount = m_mTable.getRowCount();
		for (int rowNo = 0; rowNo < rowCount; rowNo++)
		{
			if (recordId == m_mTable.getKeyID(rowNo))
			{
				final boolean fireEvents = true;
				setCurrentRow(rowNo, fireEvents);
				return;
			}
		}

		log.info("Cannot select current row by record because the record ID was not found (GridTab:{}, Record:{})", this, record);
	}

	/**
	 * Set current row - used for deleteSelection
	 *
	 * @return current row
	 */
	public void setCurrentRow(final int row)
	{
		setCurrentRow(row, false);
	}

	/**************************************************************************
	 * Get RowCount
	 *
	 * @return row count
	 */
	public int getRowCount()
	{
		int count = m_mTable.getRowCount();
		// Wait a bit if currently loading
		if (count == 0 && m_mTable.isLoading())
		{
			try
			{
				Thread.sleep(100);      // .1 sec
			}
			catch (final Exception e)
			{
			}
			count = m_mTable.getRowCount();
		}
		return count;
	}   // getRowCount

	/**
	 * Get Column/Field Count
	 *
	 * @return field count
	 */
	public int getFieldCount()
	{
		return m_mTable.getColumnCount();
	}   // getFieldCount

	/**
	 * Get Field by index
	 *
	 * @param index index
	 * @return MField
	 */
	public GridField getField(final int index)
	{
		return index >= 0 ? m_mTable.getField(index) : null;
	}   // getField

	/**
	 * Get Field by DB column name
	 *
	 * @param columnName column name
	 * @return GridField or null
	 */
	public GridField getField(final String columnName)
	{
		return m_mTable.getField(columnName);
	}   // getField

	/**
	 * Get all Fields
	 *
	 * @return MFields
	 */
	public GridField[] getFields()
	{
		return m_mTable.getFields();
	}   // getField

	/**
	 * Set New Value & call Callout
	 *
	 * @param columnName database column name
	 * @param value value
	 * @return error message or ""
	 */
	@Override
	public String setValue(final String columnName, final Object value)
	{
		if (columnName == null)
		{
			return "NoColumn";
		}

		return setValue(m_mTable.getField(columnName), value);
	}

	/**
	 * Set New Value & call Callout
	 *
	 * @param field field
	 * @param value value
	 * @return error message or ""
	 */
	public String setValue(final GridField field, final Object value)
	{
		if (field == null)
		{
			return "NoField";
		}

		//
		// 07633: Skip callout execution in copy-mode if the field is NOT calculated (was copied from the other record).
		// Note that we do not care about nulls because if the old record would have had it mandatory, then it means that it would've been set in the first place.
		final boolean isCalculated = field.getVO().IsCalculated;
		if (isDataNewCopy() && !isCalculated)
		{
			return "";
		}

		log.debug(field.getColumnName() + "=" + value + " - Row=" + m_currentRow);

		//
		// Convert the given value to internal value
		// ValueNamePair to Value(string)
		// KeyNamePair to Key(Integer)
		final Object valueToUse;
		if (DisplayType.isID(field.getDisplayType()) && value instanceof Integer && ((Integer)value).intValue() < 0)
		{
			valueToUse = null;
		}
		else if (value instanceof ValueNamePair)
		{
			valueToUse = ((ValueNamePair)value).getValue();
		}
		else if (value instanceof KeyNamePair)
		{
			valueToUse = ((KeyNamePair)value).getKey();
		}
		else
		{
			valueToUse = value;
		}

		final int col = m_mTable.findColumn(field.getColumnName());
		final boolean valueSet = m_mTable.setValueAt(valueToUse, m_currentRow, col, false);
		// metas: begin: there is no point to revalidate all depending fields if the value was not actually set
		// the only exception is the button, where we need to trigger the callouts
		if (!valueSet && DisplayType.Button != field.getDisplayType())
		{
			return "";
		}
		// metas: end:
		//
		return processFieldChange(field);
	}

	/**
	 * Is Processed
	 *
	 * @return true if current record is processed
	 */
	public boolean isProcessed()
	{
		return getValueAsBoolean("Processed");
	}

	/**
	 * Is the current record active
	 *
	 * @return true if current record is active
	 * @author Teo Sarca - BF [ 1742159 ]
	 */
	public boolean isActive()
	{
		return getValueAsBoolean("IsActive");
	}

	/**
	 * Process Field Change - evaluate Dependencies and process Callouts.
	 *
	 * called from MTab.setValue or GridController.dataStatusChanged
	 *
	 * @param changedField changed field
	 * @return error message or ""
	 */
	public String processFieldChange(final GridField changedField)
	{
		final boolean force = true; // backward compatibility
		return processFieldChange(changedField, force);
	}

	public String processFieldChange(final GridField changedField, final boolean force)
	{
		if (!force)
		{
			if (!getCalloutExecutor().hasCallouts(changedField)
					&& !hasDependants(changedField.getColumnName()))
			{
				// Field does not have callouts, nor dependent columns
				// nothing to do
				return "";
			}
		}

		processDependencies(changedField);
		return processCallout(changedField);
	}   // processFieldChange

	/**
	 * Evaluate Dependencies
	 *
	 * @param changedField changed field
	 */
	private void processDependencies(final GridField changedField)
	{
		final String columnName = changedField.getColumnName();
		// log.trace(log.l4_Data, "Changed Column", columnName);

		// when column name is not in list of DependentOn fields - fini
		if (!hasDependants(columnName))
		{
			return;
		}

		// Get dependent MFields (may be because of display or dynamic lookup)
		final List<GridField> dependentFields = getDependantFieldsWithNullElements(columnName);
		for (final GridField dependentField : dependentFields)
		{
			if (dependentField == null)
			{
				continue;
			}

			// if the field has a lookup
			final Lookup lookup = dependentField.getLookup();
			if (lookup != null && lookup.getParameters().contains(columnName))
			{
				// metas: US990 check if the field really needs to be reset
				final NamePair selected = lookup.get(dependentField.getValue());
				lookup.refresh();
				if (lookup.getIndexOf(selected) < 0)
				{
					final NamePair valueNew;
					if (isDataNewCopy())
					{
						// force setting the current copied value
						if (selected != null)
						{
							valueNew = selected;
							lookup.addElement(valueNew);
						}
						else
						{
							valueNew = null;
						}
					}
					else
					{
						valueNew = lookup.suggestValidValue(selected);
					}
					log.debug(columnName + " changed - " + dependentField.getColumnName() + " set to " + valueNew);
					// invalidate current selection
					setValue(dependentField, valueNew);
				}   // metas
			}
		}     // for all dependent fields
	}   // processDependencies

	/**************************************************************************
	 * Process Callout(s).
	 * <p>
	 * The Callout is in the string of "class.method;class.method;" If there is no class name, i.e. only a method name, the class is regarded as CalloutSystem. The class needs to comply with the
	 * Interface Callout.
	 *
	 * For a limited time, the old notation of Sx_matheod / Ux_menthod is maintained.
	 *
	 * @param field field
	 * @return error message or ""
	 * @see org.compiere.model.Callout
	 */
	public String processCallout(final GridField field)
	{
		try
		{
			calloutExecutor.execute(field);
		}
		catch(final CalloutException e)
		{
			final String errmsg = AdempiereException.extractMessage(e);
			log.warn("Failed executing callout on {}. \n Error message: {} \n CalloutInstance: {}", field, errmsg, e.getCalloutInstance(), e);
			return errmsg;
		}
		catch (final Exception e)
		{
			final String errmsg = AdempiereException.extractMessage(e);

			log.warn(errmsg, e);
			return errmsg;
		}

		return "";
	}

	public ICalloutExecutor getCalloutExecutor()
	{
		return calloutExecutor;
	}

	private final ICalloutExecutor calloutExecutor;

	/**
	 * Get Value of Field with columnName
	 *
	 * @param columnName column name
	 * @return value
	 */
	@Override
	public Object getValue(final String columnName)
	{
		if (columnName == null)
		{
			return null;
		}
		final GridField field = m_mTable.getField(columnName);
		return getValue(field);
	}   // getValue

	/**
	 * Get Boolean Value of Field with columnName.
	 * If there is no column with the given name, the context for current window will be checked.
	 *
	 * @param columnName column name
	 * @return boolean value or false if the field was not found
	 * @author Teo Sarca
	 */
	public boolean getValueAsBoolean(final String columnName)
	{
		final int index = m_mTable.findColumn(columnName);
		if (index != -1)
		{
			final Object oo = m_mTable.getValueAt(m_currentRow, index);
			if (oo instanceof String)
			{
				return "Y".equals(oo);
			}
			if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
		}
		return "Y".equals(Env.getContext(getCtx(), m_vo.WindowNo, columnName));
	}	// isProcessed

	/**
	 * Get Value of Field
	 *
	 * @param field field
	 * @return value
	 */
	public Object getValue(final GridField field)
	{
		if (field == null)
		{
			return null;
		}
		return field.getValue();
	}   // getValue

	/**
	 * Get Value of Field in row
	 *
	 * @param row row
	 * @param columnName column name
	 * @return value
	 */
	public Object getValue(final int row, final String columnName)
	{
		final int col = m_mTable.findColumn(columnName);
		if (col == -1)
		{
			return null;
		}
		return m_mTable.getValueAt(row, col);
	}   // getValue

	/*
	 * public boolean isNeedToSaveParent()
	 * {
	 * if (isDetail())
	 * return m_parentNeedSave;
	 * else
	 * return false;
	 * }
	 */

	/**
	 * toString
	 *
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		final ToStringHelper builder = MoreObjects.toStringHelper(this);
		final GridTabVO vo = m_vo;
		if (vo != null)
		{
			builder.add("TabNo", vo.getTabNo())
					.add("TabLevel", vo.getTabLevel())
					.add("Name", vo.getName())
					.add("AD_Tab_ID", vo.getAD_Tab_ID())
					.add("TableName", vo.getTableName());
		}
		else
		{
			builder.addValue("no VO");
		}
		return builder.toString();
	}   // toString

	/**************************************************************************
	 * @param l listener
	 */
	public synchronized void removePropertyChangeListener(final PropertyChangeListener l)
	{
		m_propertyChangeSupport.removePropertyChangeListener(l);
	}

	/**
	 * @param l listener
	 */
	public synchronized void addPropertyChangeListener(final PropertyChangeListener l)
	{
		m_propertyChangeSupport.addPropertyChangeListener(l);
	}

	/**
	 * @param l listener
	 */
	public synchronized void removeDataStatusListener(final DataStatusListener l)
	{
		m_listenerList.remove(DataStatusListener.class, l);
	}

	/**
	 * @param l listener
	 */
	public synchronized void addDataStatusListener(final DataStatusListener l)
	{
		m_listenerList.add(DataStatusListener.class, l);
	}

	/**
	 * @param l
	 */
	public synchronized void addStateChangeListener(final StateChangeListener l)
	{
		m_listenerList.add(StateChangeListener.class, l);
	}

	/**
	 * @param l
	 */
	public synchronized void removeStateChangeListener(final StateChangeListener l)
	{
		m_listenerList.remove(StateChangeListener.class, l);
	}

	/**
	 * Feature Request [1707462]
	 * Enable runtime change of VFormat
	 *
	 * @param Identifier field indent
	 * @param strNewFormat new mask
	 * @author fer_luck
	 */
	public void setFieldVFormat(final String identifier, final String strNewFormat)
	{
		m_mTable.setFieldVFormat(identifier, strNewFormat);
	}	// setFieldVFormat

	/**
	 * Switches the line/seqNo of the two rows
	 *
	 * @param from row index
	 * @param to row index
	 * @param sortColumn column index of sort column
	 * @param ascending sorting modus
	 */
	public final void switchRows(final int from, int to, final int sortColumn, final boolean ascending)
	{
		log.debug("Row index={}->{}, sortColumn={}, ascending={}", from, to, sortColumn, ascending);

		// nothing to do
		if (from == to)
		{
			log.trace("nothing to do - from == to");
			return;
		}
		// check if lines are editable
		if (!(m_mTable.isRowEditable(from) && m_mTable.isRowEditable(to)))
		{
			log.trace("row not editable - return");
			return;
		}
		// Row range check
		to = verifyRow(to);
		if (to == -1)
		{
			log.trace("Row range check - return");
			return;
		}

		// Check, if we have old uncommitted data
		m_mTable.dataSave(to, false);

		// find the line column
		int lineCol = m_mTable.findColumn("Line");
		if (lineCol == -1)
		{
			lineCol = m_mTable.findColumn("SeqNo");
		}
		if (lineCol == -1)
		{
			// no Line, no SeqNo
			return;
		}
		// get the line/seq numbers
		Integer lineNoCurrentRow = null;
		Integer lineNoNextRow = null;
		if (m_mTable.getValueAt(from, lineCol) instanceof Integer)
		{
			lineNoCurrentRow = (Integer)m_mTable.getValueAt(from, lineCol);
			lineNoNextRow = (Integer)m_mTable.getValueAt(to, lineCol);
		}
		else if (m_mTable.getValueAt(from, lineCol) instanceof BigDecimal)
		{
			lineNoCurrentRow = new Integer(((BigDecimal)m_mTable.getValueAt(from, lineCol))
					.intValue());
			lineNoNextRow = new Integer(((BigDecimal)m_mTable.getValueAt(to, lineCol))
					.intValue());
		}
		else
		{
			log.debug("unknown value format - return");
			return;
		}
		// don't sort special lines like taxes
		if (lineNoCurrentRow >= 9900
				|| lineNoNextRow >= 9900)
		{
			log.debug("don't sort - might be special lines");
			return;
		}
		// switch the line numbers and save new values

		m_mTable.setValueAt(lineNoCurrentRow, to, lineCol);
		setCurrentRow(to, false);
		m_mTable.dataSave(true);
		m_mTable.setValueAt(lineNoNextRow, from, lineCol);
		setCurrentRow(from, false);
		m_mTable.dataSave(true);
		// resort
		if (sortColumn != -1)
		{
			m_mTable.sort(sortColumn, ascending);
		}
		else
		{
			m_mTable.sort(lineCol, true);
		}
		navigate(to);
	}

	private void fireStateChangeEvent(final StateChangeEventType eventType)
	{
		final StateChangeListener[] listeners = m_listenerList.getListeners(StateChangeListener.class);
		if (Check.isEmpty(listeners))
		{
			return;
		}

		final StateChangeEvent event = new StateChangeEvent(this, eventType);
		for (int i = 0; i < listeners.length; i++)
		{
			listeners[i].stateChange(event);
		}
	}

	/**
	 *
	 * @return list of all tabs included in this tab
	 */
	public List<GridTab> getIncludedTabs()
	{
		final List<GridTab> list = new ArrayList<>(1);
		for (final GridField field : getFields())
		{
			if (field.getIncluded_Tab_ID() > 0)
			{
				for (int i = 0; i < m_window.getTabCount(); i++)
				{
					final GridTab detailTab = m_window.getTab(i);
					if (detailTab.getAD_Tab_ID() == field.getIncluded_Tab_ID())
					{
						list.add(detailTab);
						break;
					}
				}
			}
		}
		return list;
	}

	// BF [ 2910358 ]
	/**
	 * get Parent Tab No
	 *
	 * @return Tab No
	 */
	// metas: changed from private to public
	public int getParentTabNo()
	{
		int tabNo = getTabNo();
		int currentLevel = getTabLevel();

		// usually, the parent tab's level is currentLevel - 1, but sometimes the "level-gap" might be larger, like e.g. in the Rechnung window (MatchInv-level is 2, parent tab's level is 0)
		final int parentLevelMax = currentLevel - 1;
		if (parentLevelMax < 0)
		{
			return tabNo;
		}

		while (parentLevelMax < currentLevel)
		{
			if (tabNo < 0)
			{
				log.warn("No parent TabNo found for '{}'. Expected parent TabLevel={}", this, parentLevelMax);
				break;
			}
			tabNo--;
			currentLevel = Env.getContextAsInt(getCtx(), m_vo.WindowNo, tabNo, GridTab.CTX_TabLevel);
		}
		return tabNo;
	}

	// metas
	public List<Integer> getKeyIDs()
	{
		final List<Integer> ids = new ArrayList<>();
		final GridTable gridTable = getTableModel();
		final int currentRow = getCurrentRow();

		for (int row = 0; row < gridTable.getRowCount(); row++)
		{
			final int id = gridTable.getKeyID(row);
			if (id < 0)
			{
				continue;
			}
			if (row == currentRow)
			{
				ids.add(0, id);
			}
			else
			{
				ids.add(id);
			}
		}
		return ids;
	}

	// metas: begin
	/**
	 * Holds first extendedWhere clause that is used for this tab.
	 * Introduced by http://dewiki908/mediawiki/index.php/01880:_springe_zu_Datensatz_%28Auftrag%29_erzeugt_fehler_%282011072110000042%29#Mark_o_14:16.2C_17._Okt._2011_.28CEST.29
	 */
	private String m_extendedWhereInitial = null;

	final String STATUSLINE_MESSAGE_ID = "AD_Message_ID";

	// metas-2009_0021_AP1_CR050: begin
	/**
	 * @return true if we need to refresh all tab when activating it, instead of refreshing only current row
	 */
	public boolean isRefreshAllOnActivate()
	{
		return m_vo.IsRefreshAllOnActivate;
	}

	// metas-2009_0021_AP1_CR050: end

	// metas-2009_0021_AP1_CR057: begin
	/**
	 * Is Search Active
	 *
	 * @return true if is search active
	 */
	public boolean isSearchActive()
	{
		return m_vo.IsSearchActive;
	}

	// metas-2009_0021_AP1_CR057: end
	// metas-2009_0021_AP1_CR064: begin
	/**
	 * @return true if we should collapse the search panel
	 */
	public boolean isSearchCollapsed()
	{
		return m_vo.IsSearchCollapsed;
	}

	/**
	 * @return true if we should load data after open
	 */
	public boolean isQueryOnLoad()
	{
		return m_vo.IsQueryOnLoad;
	}

	// metas-2009_0021_AP1_CR064: end

	// metas-2009_0021_AP1_CR059
	public boolean isGridModeOnly()
	{
		return m_vo.IsGridModeOnly;
	}

	/**
	 * Get Tab Default Where Clause
	 *
	 * @return default where clause
	 */
	public String getDefaultWhereClause()
	{
		return m_vo.getDefaultWhereClause();
	} // getDefaultWhereClause

	/**
	 * Gets grid tab's summary info message (that will be displayed on bottom of the window)
	 *
	 * @return summary info message (translated)
	 */
	public final String getSummaryInfoMessage()
	{
		final IGridTabSummaryInfo summaryInfo = getSummaryInfo();
		return summaryInfo.getSummaryMessageTranslated(getCtx());
	}

	/**
	 * @return grid tab's summary info (that will be displayed on bottom of the window); never returns <code>null</code>
	 * @task 07985
	 */
	public final IGridTabSummaryInfo getSummaryInfo()
	{
		final IGridTabSummaryInfoProvider gridTabSummaryInfo = gridTabSummaryInfoFactory.getSummaryInfoProvider(this);
		final IGridTabSummaryInfo summaryInfo = gridTabSummaryInfo.getSummaryInfo(this);
		_lastSummaryInfo = summaryInfo == null ? IGridTabSummaryInfo.NULL : summaryInfo;
		return _lastSummaryInfo;
	}

	/**
	 * Gets grid tab's LAST summary info (that will be displayed on bottom of the window).
	 *
	 * Compared with {@link #getSummaryInfo()} this info is the info user seen last time, so it could be staled.
	 *
	 * @return last summary info or <code>null</code>
	 */
	public final IGridTabSummaryInfo getLastSummaryInfo()
	{
		// NOTE: don't query even if it's null, we want to get the last message user saw (if any)
		return _lastSummaryInfo;
	}

	private IGridTabSummaryInfo _lastSummaryInfo = null;

	public void updateContext()
	{
		for (final GridField field : getFields())
		{
			field.updateContext();
		}
	}

	// metas: make GridTab compatible with PO conventions
	public String get_TableName()
	{
		return getTableName();
	}

	// metas
	public GridWindow getGridWindow()
	{
		return m_window;
	}

	// metas
	public GridTabVO getGridTabVO()
	{
		return m_vo;
	}

	public final int getMaxQueryRecords()
	{
		return m_vo.getMaxQueryRecords();
	}

	private final int getMaxQueryRecordsActual(final GridTabMaxRows maxQueryRecords)
	{
		return GridTabMaxRowsRestrictionChecker.builder()
				.setAD_Tab(this)
				.build()
				.resolve(maxQueryRecords);
	}

	/**
	 *
	 * @return true if values of the current row where changed
	 */
	// metas
	public boolean isCurrentRowChanged()
	{
		return m_mTable.isChanged();
	}

	/**
	 *
	 * @return parent {@link GridTab} or null if this tab does not have a parent
	 */
	// metas
	public GridTab getParentGridTab()
	{
		if (!isDetail())
		{
			return null;
		}

		final int parentTabNo = getParentTabNo();
		if (parentTabNo < 0)
		{
			log.warn("ParentTabNo not found for " + this + ", even if is a detail tab. Returning null");
			return null;
		}

		final GridTab parentTab = m_window.getTab(parentTabNo);
		return parentTab;
	}

	/**
	 *
	 * @param recursively if true, parent's parent will be checked recursively
	 * @return true if parent tab was changed
	 */
	// metas
	public boolean isParentChanged(final boolean recursively)
	{
		final GridTab parentTab = getParentGridTab();
		if (parentTab == null)
		{
			return false;
		}

		if (parentTab.isCurrentRowChanged())
		{
			return true;
		}

		// metas: Because at the moment, isCurrentRowChanged() is not checking if the record is in Insert/Not yet saved state
		// we are checking here explicitelly
		if (parentTab.getTableModel().isInserting())
		{
			return true;
		}

		final boolean parentChanged;
		if (recursively)
		{
			parentChanged = parentTab.isParentChanged(true);
		}
		else
		{
			parentChanged = false;
		}

		return parentChanged;
	}

	/**
	 * Create {@link MQuery} to be used when reporting on this grid tab
	 *
	 * @return query
	 */
	// 03917
	public MQuery createReportingQuery()
	{
		final Properties ctx = getCtx();
		final MQuery query;

		//
		// If we are reporting on a header tab (not detail) we shall use exactly the same items as the user is seeing in Tab (03917)
		if (Check.isEmpty(getLinkColumnName(), true))
		{
			query = getQuery().deepCopy();
		}

		//
		// Reporting on an included tab
		// In this case we are keeping the old ADempiere functionality, which was moved and refactored from:
		// * org.compiere.apps.APanel.cmd_report()
		// * org.adempiere.webui.panel.AbstractADWindowPanel.onReport()
		else
		{
			query = new MQuery(getTableName());
			// Link for detail records
			String queryColumn = getLinkColumnName();
			// Current row otherwise
			if (queryColumn.length() == 0)
			{
				queryColumn = getKeyColumnName();
			}
			// Find display
			String infoName = null;
			String infoDisplay = null;
			for (int i = 0; i < getFieldCount(); i++)
			{
				final GridField field = getField(i);
				if (field.isKey())
				{
					infoName = field.getHeader();
				}
				if ((field.getColumnName().equals("Name") || field.getColumnName().equals("DocumentNo"))
						&& field.getValue() != null)
				{
					infoDisplay = field.getValue().toString();
				}
				if (infoName != null && infoDisplay != null)
				{
					break;
				}
			}
			if (queryColumn.length() != 0)
			{
				if (queryColumn.endsWith("_ID"))
				{
					query.addRestriction(queryColumn, Operator.EQUAL,
							new Integer(Env.getContextAsInt(ctx, getWindowNo(), queryColumn)),
							infoName, infoDisplay);
				}
				else
				{
					query.addRestriction(queryColumn, Operator.EQUAL,
							Env.getContext(ctx, getWindowNo(), queryColumn),
							infoName, infoDisplay);
				}
			}
		}

		//
		// Make sure we are adding the extended where clause in our reporting query:
		// (e.g. of extended where clause - in Invoice window, header tab, the extended where is IsSOTrx=Y)
		final String whereExtended = getWhereExtended();
		if (!Check.isEmpty(whereExtended, true))
		{
			final String whereExtendedParsed = Env.parseContext(ctx, getWindowNo(), whereExtended, false);
			if (Check.isEmpty(whereExtendedParsed, true))
			{
				log.warn("Cannot parse whereExtended: ", whereExtended);
			}
			else
			{
				query.addRestriction(whereExtendedParsed);
			}
		}

		return query;
	}

	/** Copy mode when {@link GridTab#dataNew(boolean)} is invoked */
	public static enum DataNewCopyMode
	{
		NoCopy, Copy, CopyWithDetails;

		public static boolean isCopy(final DataNewCopyMode copyMode)
		{
			return copyMode == Copy || copyMode == CopyWithDetails;
		}

		public static boolean isCopyWithDetails(final DataNewCopyMode copyMode)
		{
			return copyMode == CopyWithDetails;
		}
	};

	/**
	 * Field set by {@link #dataNew(boolean, boolean)} while we are in copy record mode
	 */
	private DataNewCopyMode _dataNewCopyMode = null;
	/** Tables suggested by user for copy with details */
	private List<CopyRecordSupportTableInfo> m_suggestedCopyWithDetailsList = null;

	/**
	 * Returns if we are in record copying mode.
	 *
	 * NOTE: this information is available while we are in {@link #dataNew(DataNewCopyMode)} method.
	 *
	 * @return true if we are in copy record mode (i.e. {@link #dataNew(DataNewCopyMode)} was called with copy with/without details
	 */
	public boolean isDataNewCopy()
	{
		return DataNewCopyMode.isCopy(_dataNewCopyMode);
	}

	/**
	 * Sets currently active copying mode when {@link #dataNew(DataNewCopyMode)}.
	 *
	 * @param dataNewCopyMode
	 */
	private final void setDataNewCopyMode(final DataNewCopyMode dataNewCopyMode)
	{
		Check.assumeNotNull(dataNewCopyMode, "dataNewCopyMode not null");
		this._dataNewCopyMode = dataNewCopyMode;
	}

	/**
	 * Reset currently active copy mode
	 */
	private final void resetDataNewCopyMode()
	{
		this._dataNewCopyMode = null;
	}

	public final void setSuggestedCopyWithDetailsList(final List<CopyRecordSupportTableInfo> suggestedCopyWithDetailsList)
	{
		this.m_suggestedCopyWithDetailsList = ImmutableList.copyOf(suggestedCopyWithDetailsList);
	}

	public final void resetSuggestedCopyWithDetailsList()
	{
		this.m_suggestedCopyWithDetailsList = null;
	}

	public final List<CopyRecordSupportTableInfo> getSuggestedCopyWithDetailsList()
	{
		return m_suggestedCopyWithDetailsList;
	}

	/**
	 * Remove all context variables which are for this tab.
	 *
	 * This method won't remove those special variables (i.e. those who start with {@link #CTX_Prefix}).
	 */
	private final void clearTabContext()
	{
		final Properties ctx = getCtx();
		final int windowNo = getWindowNo();
		final int tabNo = getTabNo();
		final String keyPrefix = Env.createContextName(windowNo, tabNo, "");
		final String keyPrefixToExclude = Env.createContextName(windowNo, tabNo, CTX_Prefix);

		Env.removeContextMatching(ctx, new Predicate<Object>()
		{
			@Override
			public boolean test(final Object key)
			{
				if (key == null)
				{
					// shall not happen
					return false;
				}

				final String name = key.toString();

				// Skip special tab context variables
				if (name.startsWith(keyPrefixToExclude))
				{
					return false;
				}

				// Skip those names which are not about our tab
				if (!name.startsWith(keyPrefix))
				{
					return false;
				}

				return true; // remove it
			}
		});
	}

	/**
	 * ID of the side action group which shall contain common actions.
	 */
	public static final String SIDEACTIONS_Actions_GroupId = "org.compiere.model.GridTab.SideActionsGroups.Actions";

	/**
	 * Gets side actions groups model. Those will be displayed on right side of the window.
	 *
	 * @return side actions groups model; never return null
	 */
	public final ISideActionsGroupsListModel getSideActionsGroupsModel()
	{
		if (_sideActionsGroupsModel == null)
		{
			_sideActionsGroupsModel = new SideActionsGroupsListModel();

			//
			// Create Generic actions group
			final String title = msgBL.translate(Env.getCtx(), SIDEACTIONS_Actions_GroupId);
			final boolean defaultCollapsed = false;
			final ISideActionsGroupModel groupActions = new SideActionsGroupModel(SIDEACTIONS_Actions_GroupId, title, defaultCollapsed);
			_sideActionsGroupsModel.addGroup(groupActions);

		}
		return _sideActionsGroupsModel;
	}

	private ISideActionsGroupsListModel _sideActionsGroupsModel = null;

	/**
	 * Creates a {@link IQueryBuilder} which will return exactly the rows that we have in this {@link GridTab}.
	 *
	 * FIXME: no ORDER BY is enforced
	 *
	 * @param modelClass
	 * @return query builder
	 */
	public <T> IQueryBuilder<T> createQueryBuilder(final Class<T> modelClass)
	{
		final IQueryFilter<T> gridTabFilter = createCurrentRecordsQueryFilter(modelClass);
		final IQueryBuilder<T> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(modelClass, getCtx(), ITrx.TRXNAME_None)
				.filter(gridTabFilter);
		return queryBuilder;
	}

	/**
	 * Creates a {@link IQueryFilter} which will return exactly the rows that we have in this {@link GridTab}.
	 *
	 * @param modelClass
	 * @return query builder
	 */
	public <T> IQueryFilter<T> createCurrentRecordsQueryFilter(final Class<T> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		Check.assume(tableName.equals(getTableName()), "{}'s table is not compatible with {}'s table", modelClass, this);

		final String sqlWhereClause = getMTable().getSelectWhereClauseFinal();

		// If there is no SQL where clause, it means that the GridTab query was not initialized,
		// so user sees to records,
		// so this is what we also need to return => no records
		if (Check.isEmpty(sqlWhereClause, true))
		{
			return ConstantQueryFilter.of(false);
		}

		final IQueryFilter<T> gridTabFilter = TypedSqlQueryFilter.of(sqlWhereClause);
		return gridTabFilter;
	}
	// metas: end

	private static final class AttachmentsMap
	{
		private static final transient Logger logger = LogManager.getLogger(AttachmentsMap.class);

		private static final int BUFFER_SIZE = 100;

		private final int adTableId;
		private volatile boolean canHaveAttachments = false;
		private Map<Integer, Integer> recordId2attachementId = null;
		private boolean partiallyLoaded = true;

		public AttachmentsMap(final int adTableId)
		{
			super();
			this.adTableId = adTableId;
			reset();
		}

		public void setKeyColumnName(final String keyColumnName)
		{
			this.canHaveAttachments = keyColumnName != null && keyColumnName.endsWith("_ID");
		}

		public boolean canHaveAttachment()
		{
			return canHaveAttachments;
		}

		public synchronized void reset()
		{
			if (recordId2attachementId != null)
			{
				recordId2attachementId.clear();
				recordId2attachementId = null;
			}
			partiallyLoaded = true;
		}

		public synchronized void reset(final int recordId)
		{
			if (recordId2attachementId == null)
			{
				return;
			}

			// clean cache
			recordId2attachementId.remove(recordId);

			// #578 commented out because it was preventing the attachment from being put in the map when loaded.

			// if(attachmentId == null)
			// {
			// return;
			// }

			// load the attachment
			final boolean forceLoadIfNotExists = true;
			getAD_Attachment_ID(recordId, forceLoadIfNotExists); // reload it
		}

		/**
		 * Get AD_Attachment_ID for given Record_ID.
		 *
		 * @return AD_Attachment_ID or 0, if not found
		 */
		public final synchronized int getAD_Attachment_ID(final int recordId)
		{
			final boolean forceLoadIfNotExists = false;
			return getAD_Attachment_ID(recordId, forceLoadIfNotExists);
		}

		private final synchronized int getAD_Attachment_ID(final int recordId, final boolean forceLoadIfNotExists)
		{
			if (recordId < 0)
			{
				return 0;
			}

			final Map<Integer, Integer> recordId2attachmentId = getMap();

			final Integer attachmentIdExisting = recordId2attachmentId.get(recordId);
			if (attachmentIdExisting != null)
			{
				return attachmentIdExisting > 0 ? attachmentIdExisting : 0;
			}

			if (partiallyLoaded || forceLoadIfNotExists)
			{
				final String sql = "SELECT AD_Attachment_ID FROM " + I_AD_Attachment.Table_Name + " WHERE AD_Table_ID=? AND Record_ID=?";
				int attachmentId = DB.getSQLValue(ITrx.TRXNAME_None, sql, adTableId, recordId);
				if (attachmentId <= 0)
				{
					attachmentId = 0;
				}
				recordId2attachmentId.put(recordId, attachmentId); // cache it even if the AD_Attachment_ID was not found
				return attachmentId;
			}

			return 0;
		}

		public final boolean hasAttachment(final int recordId)
		{
			final int attachmentId = getAD_Attachment_ID(recordId);
			return attachmentId > 0;
		}

		private final Map<Integer, Integer> getMap()
		{
			if (!canHaveAttachment())
			{
				return ImmutableMap.of();
			}

			if (this.recordId2attachementId != null)
			{
				return this.recordId2attachementId;
			}

			final Map<Integer, Integer> recordId2attachementId = new HashMap<>(BUFFER_SIZE);
			boolean partiallyLoaded = false;

			final String sql = "SELECT AD_Attachment_ID, Record_ID FROM " + I_AD_Attachment.Table_Name + " WHERE AD_Table_ID=? LIMIT ?";
			int rowsLoaded = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setInt(1, adTableId);
				pstmt.setInt(2, BUFFER_SIZE + 1); // buffer size + 1 to be able to figure out when the buffer is exceeded
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					// Check if we exceeded the buffer size
					if (rowsLoaded >= BUFFER_SIZE)
					{
						partiallyLoaded = true;
						break;
					}

					final int attachmentId = rs.getInt(1);
					final int recordId = rs.getInt(2);
					recordId2attachementId.put(recordId, attachmentId);
					rowsLoaded++;
				}
			}
			catch (final SQLException e)
			{
				logger.error("Failed loading the attachments", e);
				partiallyLoaded = true; // consider it partially loaded
			}
			finally
			{
				DB.close(rs, pstmt);
			}

			this.recordId2attachementId = recordId2attachementId;
			this.partiallyLoaded = partiallyLoaded;

			return recordId2attachementId;
		}

	}
	
	
	//
	//
	//
	
	public IProcessPreconditionsContext toPreconditionsContext()
	{
		return new GridTabAsPreconditionsContext(this);
	}
	
	private static final class GridTabAsPreconditionsContext implements IProcessPreconditionsContext
	{
		private final GridTab gridTab;

		private GridTabAsPreconditionsContext(final GridTab gridTab)
		{
			this.gridTab = gridTab;
		}
		
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).addValue(gridTab).toString();
		}
		
		@Override
		public int getAD_Window_ID()
		{
			return gridTab.getAD_Window_ID();
		}

		@Override
		public String getTableName()
		{
			return gridTab.getTableName();
		}

		@Override
		public <T> T getSelectedModel(final Class<T> modelClass)
		{
			return gridTab.getModel(modelClass);
		}
		
		@Override
		public <T> List<T> getSelectedModels(final Class<T> modelClass)
		{
			// backward compatibility
			final T model = getSelectedModel(modelClass);
			return ImmutableList.of(model);
		}
		
		@Override
		public int getSingleSelectedRecordId()
		{
			return gridTab.getRecord_ID();
		}
		
		@Override
		public int getSelectionSize()
		{
			// backward compatibility
			return 1;
		}

	}
}	// GridTab
