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
package org.compiere.apps.search;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.ui.DefaultTableColorProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.model.IWindowNoAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserSortPrefDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.search.impl.InfoWindowGridRowBuilders;
import org.compiere.grid.ed.Calculator;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.GridTabMaxRowsRestrictionChecker;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_SortPref_Line;
import org.compiere.model.I_AD_User_SortPref_Line_Product;
import org.compiere.model.I_M_Product;
import org.compiere.model.MQuery;
import org.compiere.model.X_AD_User_SortPref_Hdr;
import org.compiere.swing.CDialog;
import org.compiere.swing.CFrame;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.MSort;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Search Information and return selection - Base Class.
 *
 * To create a new instance, please use {@link InfoBuilder}.
 *
 * @author Jorg Janke
 * @version $Id: Info.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *
 * @author Teo Sarca
 *         <ul>
 *         <li>FR [ 2846869 ] Info class - add more helper methods https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2846869&group_id=176962
 *         <li>FR [ 2847305 ] Info class improvements https://sourceforge.net/tracker/?func=detail&aid=2847305&group_id=176962&atid=879335
 *         <li>BF [ 2860556 ] Info class throws false error https://sourceforge.net/tracker/?func=detail&aid=2860556&group_id=176962&atid=879332
 *         </ul>
 */
public abstract class Info extends Component
		implements ActionListener, ListSelectionListener, MouseListener, IWindowNoAware
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5606614040914295869L;

	private static final String SYSCONFIG_INFO_DEFAULTSELECTED = "INFO_DEFAULTSELECTED";
	private static final String SYSCONFIG_INFO_DOUBLECLICKTOGGLESSELECTION = "INFO_DOUBLECLICKTOGGLESSELECTION";

	private static final String SORTPREF_Action = X_AD_User_SortPref_Hdr.ACTION_Info_Fenster;

	/** Window Width */
	// metas: changed from protected to public
	public static final int INFO_WIDTH = 800;

	/** Default icon name to be used for Info windows */
	public static final String DEFAULT_IconName = "Info16";

	private Window _window = null;

	/**************************************************************************
	 * Detail Constructor
	 *
	 * @param frame parent frame
	 * @param modal modal
	 * @param WindowNo window no
	 * @param tableName table name
	 * @param keyColumn key column name
	 * @param multiSelection multiple selection
	 * @param whereClause where clause
	 */
	protected Info(final Frame owner, final boolean modal, final int WindowNo,
			final String tableName, final String keyColumn,
			final boolean multiSelection, final String whereClause)
	{
		this(owner, modal);

		init(modal, WindowNo, tableName, keyColumn, multiSelection, whereClause);
	}

	protected Info(final Frame owner, final boolean modal)
	{
		super();

		initWindow(owner, modal);
	}

	private final void initWindow(final Window owner, final boolean modal)
	{
		if (_window != null)
		{
			_window.dispose(); // dispose if somehow re-initialized
		}

		if (modal)
		{
			_window = new CDialog(owner, null, modal);
		}
		else
		{
			_window = new CFrame();
		}
	}

	public final Window getWindow()
	{
		Check.assumeNotNull(_window, "_window not null");
		return _window;
	}

	/**
	 * Display this info window on screen.
	 *
	 * If the Info window will be displayed maximized or centered is up to implementing class.
	 *
	 * By default, the Info window will be shown centered but not maximized.
	 */
	public void showWindow()
	{
		if (disposed || disposing)
		{
			return;
		}

		final Window window = getWindow();
		AEnv.showCenterScreen(window);
	}

	@OverridingMethodsMustInvokeSuper
	/* package */void init(final boolean modal,
			final int WindowNo,
			final String tableName,
			final String keyColumn,
			final boolean multiSelection,
			final String whereClause)
	{
		// metas: end
		log.info("WinNo=" + p_WindowNo + " " + whereClause);

		final Properties ctx = getCtx();

		// WindowNo
		if (WindowNo <= 0)
		{
			final Window window = getWindow();
			p_WindowNo = Env.createWindowNo(window);
			AEnv.addToWindowManager(window);
			localWindowNo = true;
		}
		else
		{
			if (!modal)
			{
				new AdempiereException("Posible window leak because modal=false and WindowNo is provided. This window won't be added to window manager.")
						.throwIfDeveloperModeOrLogWarningElse(log);
			}
			p_WindowNo = WindowNo;
			localWindowNo = false;
		}

		p_tableName = tableName;
		p_keyColumn = keyColumn;
		p_multiSelection = multiSelection;
		if (whereClause == null || whereClause.indexOf('@') == -1)
		{
			p_whereClause = whereClause;
		}
		else
		{
			p_whereClause = Env.parseContext(ctx, p_WindowNo, whereClause, false, false);
			if (p_whereClause.length() == 0)
			{
				log.error("Cannot parse context= " + whereClause);
			}
		}

		try
		{
			jbInit();
		}
		catch (final Exception ex)
		{
			log.error("Info", ex);
		}
	} // Info

	/** Master (owning) Window */
	private int p_WindowNo;
	/** true if WindowNo was created locally */
	private boolean localWindowNo = false;

	/** Table Name */
	private String p_tableName;
	/** Key Column Name */
	private String p_keyColumn;
	/** Enable more than one selection */
	private boolean p_multiSelection;
	/** Specify if the records should be checked(selected) by default (multi selection mode only) */
	private boolean p_isDefaultSelected = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_INFO_DEFAULTSELECTED, false, getCtxAD_Client_ID());
	/** True if double click on a row toggles if row is selected (multi selection mode only) */
	private boolean p_doubleClickTogglesSelection = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_INFO_DOUBLECLICKTOGGLESSELECTION, false, getCtxAD_Client_ID());
	/** Initial WHERE Clause */
	protected String p_whereClause = "";

	/** Table */
	protected MiniTable p_table = new MiniTable();
	/** Model Index of Key Column */
	private int m_keyColumnIndex = -1;
	/** OK pressed */
	private boolean m_ok = false;
	/** Cancel pressed - need to differentiate between OK - Cancel - Exit */
	private boolean m_cancel = false;
	/** Result IDs */
	private final ArrayList<Integer> m_results = new ArrayList<Integer>(3);

	/** Layout of Grid */
	protected Info_Column[] p_layout;
	/** Main SQL Statement */
	private String m_sqlMain;
	/** Count SQL Statement */
	private String m_sqlCount;
	/** Order By Clause */
	private String m_sqlOrder;

	/** Loading success indicator */
	protected boolean p_loadedOK = false;
	/** SO Zoom Window */
	private int m_SO_Window_ID = -1;
	/** PO Zoom Window */
	private int m_PO_Window_ID = -1;

	/** Worker */
	private Worker m_worker = null;

	/** Logger */
	protected Logger log = LogManager.getLogger(getClass());

	// Overrides isLoading().
	private boolean ignoreLoading = false;

	/** Static Layout */
	private final CPanel southPanel = new CPanel();
	protected final ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withRefreshButton(true)
			.withResetButton(true)
			.withCustomizeButton(true)
			.withHistoryButton(true)
			.withZoomButton(true)
			.withoutText()
			.build();
	protected final CPanel addonPanel = new CPanel();
	protected final StatusBar statusBar = new StatusBar();
	protected final CPanel parameterPanel = new CPanel();
	/** {@link #p_table}'s scroll pane container */
	private final JScrollPane scrollPane = new JScrollPane();
	//
	private final JPopupMenu popup = new JPopupMenu();
	private final CMenuItem calcMenu = new CMenuItem();

	/**
	 * Static Init
	 *
	 * @throws Exception
	 */
	protected void jbInit() throws Exception
	{
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Set window icon if any
		final Image icon = Images.getImage2(DEFAULT_IconName);
		if (icon != null)
		{
			getWindow().setIconImage(icon);
		}

		southPanel.setLayout(new BorderLayout());
		southPanel.add(addonPanel, BorderLayout.NORTH);
		southPanel.add(confirmPanel, BorderLayout.CENTER);
		southPanel.add(statusBar, BorderLayout.SOUTH);

		final Container contentPane = getContentPane();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		contentPane.add(parameterPanel, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(p_table);
		// have some left/right space for better visibility
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 2, 0, 2) // outsideBorder
				, scrollPane.getBorder() // insideBorder
		));
		//
		confirmPanel.setActionListener(this);
		confirmPanel.getResetButton().setVisible(hasReset());
		confirmPanel.getCustomizeButton().setVisible(hasCustomize());
		confirmPanel.getHistoryButton().setVisible(hasHistory());
		confirmPanel.getZoomButton().setVisible(hasZoom());
		//
		popup.add(calcMenu);
		calcMenu.setText(Services.get(IMsgBL.class).getMsg(getCtx(), "Calculator"));
		calcMenu.setIcon(Images.getImageIcon2("Calculator16"));
		calcMenu.addActionListener(this);
		//
		p_table.getSelectionModel().addListSelectionListener(this);
		enableButtons();
	} // jbInit

	private void setDefaultCloseOperation(final int operation)
	{
		final Window window = getWindow();
		if (window instanceof JDialog)
		{
			((JDialog)window).setDefaultCloseOperation(operation);
		}
		else if (window instanceof JFrame)
		{
			((JFrame)window).setDefaultCloseOperation(operation);
		}
		else
		{
			throw new UnsupportedOperationException("setDefaultCloseOperation not supported for window instance, window=" + window);
		}
	}

	public void setTitle(final String title)
	{
		final Window window = getWindow();
		if (window instanceof Dialog)
		{
			((Dialog)window).setTitle(title);
		}
		else if (window instanceof Frame)
		{
			((Frame)window).setTitle(title);
		}
		else
		{
			throw new UnsupportedOperationException("setTitle not supported for window instance, window=" + window);
		}
	}

	public String getTitle()
	{
		final Window window = getWindow();
		if (window instanceof Dialog)
		{
			return ((Dialog)window).getTitle();
		}
		else if (window instanceof Frame)
		{
			return ((Frame)window).getTitle();
		}
		else
		{
			throw new UnsupportedOperationException("setTitle not supported for window instance, window=" + window);
		}
	}

	@Override
	public int getWindowNo()
	{
		return p_WindowNo;
	}

	/**
	 * Loaded correctly
	 *
	 * @return true if loaded OK
	 */
	public boolean loadedOK()
	{
		return p_loadedOK;
	} // loadedOK

	/**
	 * Set Status Line
	 *
	 * @param text text
	 * @param error error
	 */
	public void setStatusLine(final String text, final boolean error)
	{
		statusBar.setStatusLine(text, error);
		Thread.yield();
	} // setStatusLine

	/**
	 * Set Status DB
	 *
	 * @param text text
	 */
	public void setStatusDB(final String text)
	{
		statusBar.setStatusDB(text);
	} // setStatusDB

	/**************************************************************************
	 * Prepare Table, Construct SQL (m_m_sqlMain, m_sqlAdd) and size Window
	 *
	 * @param layout layout array
	 * @param from from clause
	 * @param staticWhere where clause
	 * @param orderBy order by clause
	 */
	protected void prepareTable(final Info_Column[] layout, final String from, final String staticWhere, final String orderBy)
	{
		final DefaultTableColorProvider colorProvider = new DefaultTableColorProvider();
		p_table.setColorProvider(colorProvider);

		p_layout = layout;
		final StringBuilder sql = new StringBuilder("SELECT DISTINCT "); // metas: cg: task US780
		// add columns & sql
		for (int i = 0; i < layout.length; i++)
		{
			if (i > 0)
			{
				sql.append("\n, ");
			}

			final boolean isIDColumn = layout[i].isIDcol();

			//
			// Info column / ID's display name
			sql.append(layout[i].getColSQL());
			if (isIDColumn)
			{
				// Make sure the display column has a column name set and don't allow databases like PG9.3 to generate one (FRESH-235)
				String columnName = layout[i].getColumnName();
				if (Check.isEmpty(columnName, true))
				{
					columnName = "Col" + i + "_DisplayName";
				}
				sql.append(" AS ").append(columnName).append("_DisplayName");
			}
			// Else, append the " AS ColumnName" (FRESH-235)
			// NOTE: we mainly do this for "AD_Ref_List" based columns.
			else if (!Check.isEmpty(layout[i].getColumnName(), true))
			{
				sql.append(" AS ").append(layout[i].getColumnName());
			}

			//
			// adding ID column
			if (isIDColumn)
			{
				sql.append("\n, ").append(layout[i].getIDcolSQL());
			}

			// add to model
			p_table.addColumn(layout[i].getColHeader());
			if (layout[i].isColorColumn())
			{
				colorProvider.setColorColumnIndex(i);
			}
			if (layout[i].getColClass() == IDColumn.class)
			{
				m_keyColumnIndex = i;
			}
		}

		// No KeyColumnIndex found. Pick the first ID column
		if (m_keyColumnIndex < 0)
		{
			int count = 0;
			for (final Info_Column infoColumn : layout)
			{
				if (infoColumn.isIDcol())
				{
					m_keyColumnIndex = count;
					break;
				}
				else
				{
					count++;
				}
			}
		}

		// Table Selection (Invoked before setting column class so that row selection is enabled)
		p_table.setRowSelectionAllowed(true);
		p_table.addMouseListener(this);
		p_table.setMultiSelection(p_multiSelection);
		p_table.setShowTotals(false); // c.ghita@metas.ro : if true can cause 'Cannot format given Object as a Number ( S ) String'

		// set editors (two steps)
		for (int i = 0; i < layout.length; i++)
		{
			p_table.setColumnClass(i, layout[i]);
		}

		sql.append("\n FROM ").append(from);
		//
		sql.append("\n WHERE ").append(staticWhere);
		m_sqlMain = sql.toString();
		m_sqlCount = "SELECT COUNT(*) FROM " + from + " WHERE " + staticWhere;
		//
		m_sqlOrder = "";
		if (orderBy != null && orderBy.length() > 0)
		{
			m_sqlOrder = " ORDER BY " + orderBy;
		}

		if (m_keyColumnIndex == -1)
		{
			log.error("No KeyColumn - " + sql);
		}

		// Window Sizing
		parameterPanel.setPreferredSize(new Dimension(INFO_WIDTH, parameterPanel.getPreferredSize().height));
		// Begin - [FR 1823612 ] Product Info Screen Improvements
		scrollPane.setPreferredSize(new Dimension(INFO_WIDTH, 300));
		// End - [FR 1823612 ] Product Info Screen Improvements
	} // prepareTable

	/**
	 * fresh 08329: Set sort preferences to conference mode
	 */
	private boolean isConferenceSortPreferences = false;

	public final void setConferenceSortPreferences(final boolean isConferenceSortPreferences)
	{
		this.isConferenceSortPreferences = isConferenceSortPreferences;
	}

	/**
	 * Can be overridden by implementing classes
	 *
	 * @param ctx
	 * @return ID of the current instance's info window
	 */
	protected int getAD_InfoWindow_ID()
	{
		//
		// Services
		final IADInfoWindowDAO infoWindowDAO = Services.get(IADInfoWindowDAO.class);

		//
		// FIXME: This is uber-hacky; should be replaced later with a proper way to persist the window id in the Info Window forms
		final String currentClassName = getClass().getName();

		int currentInfoWindowId = -1;

		final Properties ctx = getCtx();

		final List<I_AD_InfoWindow> infoWindowsInMenu = infoWindowDAO.retrieveInfoWindowsInMenu(ctx);
		for (final I_AD_InfoWindow infoWindow : infoWindowsInMenu)
		{
			final String infoWindowClassName = infoWindow.getClassname();
			if (!currentClassName.equals(infoWindowClassName))
			{
				continue;
			}
			currentInfoWindowId = infoWindow.getAD_InfoWindow_ID();
		}
		return currentInfoWindowId;
	}

	/**
	 * Execute our query and load the data, using a worker thread.<br>
	 * If the loading is already in progress then the method makes sure that the load will be repeated.
	 *
	 * @see #isWorkerRepeatLoadData()
	 * @task 08755
	 */
	public void executeQuery()
	{
		_workerRepeatLoadData.lock();
		try
		{
			if (_workerIsRunning)
			{
				// when already running, make sure that it will run another time, as the parameters and stuff have changed
				_workerShallRunAgain = true;
				return; // the worker thread will repeat its work, we can return
			}
			else
			{
				// the worker is currently not running, but this thread will now start a worker-thread.
				// we set this boolean now, so if another thread enters this lock, then it will know that it needs to call setWorkerShallRunAgain()
				_workerIsRunning = true;
			}
		}
		finally
		{
			_workerRepeatLoadData.unlock();
		}

		m_worker = new Worker();
		m_worker.setName(m_worker.getClass().getName());

		m_worker.start();
	} // executeQuery

	/**
	 * Lock used to synchronize the access to {@link #_workerIsRunning} and {@link #_workerShallRunAgain} which are accessed by both the worker thread and UI threads.
	 *
	 * @see #isWorkerRepeatLoadData()
	 * @see #executeQuery()
	 * @task 08775
	 */
	private final ReentrantLock _workerRepeatLoadData = new ReentrantLock();

	/**
	 * Only accessed within areas that are synchronized by {@link #_workerRepeatLoadData}.
	 *
	 * @see #isWorkerRepeatLoadData()
	 * @see #executeQuery()
	 * @task 08775
	 */
	private volatile boolean _workerShallRunAgain = false;

	/**
	 * Only accessed within areas that are synchronized by {@link #_workerRepeatLoadData}. Note that we use this variable instead of relying on {@link Thread#isAlive()} because the thread might still
	 * be alive after having exited its internal loop and therefore won't be able to repeat the work. This member is <code>true</code> only while the worker can still be made to repeat its work.
	 *
	 * @see #isWorkerRepeatLoadData()
	 * @see #executeQuery()
	 * @task 08775
	 */
	private volatile boolean _workerIsRunning = false;

	/**
	 * This method is called from the worker thread, after {@link Worker#loadData()} was performed. It does three things:
	 * <p>
	 * 1. it checks the value of {@link #_workerShallRunAgain} and returns it. If this method returns <code>true</code> then we expect the {@link Worker} thread to perform {@link Worker#loadData()}
	 * once more.
	 * <p>
	 * 2. if the value of {@link #_workerShallRunAgain} is <code>true</code>, then this method sets the value to <code>false</code>. This means that the worker will run one more time and then finish,
	 * unless someone calls {@link #setWorkerShallRunAgain()} once more.
	 * <p>
	 * 3. if the value of {@link #_workerShallRunAgain} is <code>false</code>, then this method sets {@link #_workerIsRunning} to <code>false</code>, because the worker thread did the actual work and
	 * because it is not requested to repeat the work, it will soon finish.
	 *
	 * @return
	 * @task 08775
	 */
	private boolean isWorkerRepeatLoadData()
	{
		_workerRepeatLoadData.lock();
		try
		{
			if (_workerShallRunAgain)
			{
				_workerShallRunAgain = false; // see "2." from the javadoc
				return true;
			}

			_workerIsRunning = false; // see "3." from the javadoc
			return false;
		}
		finally
		{
			_workerRepeatLoadData.unlock();
		}
	}

	/**
	 * Called when Info window is opened.
	 *
	 * By default this method it's just executing {@link #executeQuery()} (only on first invocation) but developer is free to change it in extending classes.
	 */
	protected void executeQueryOnInit()
	{
		if (_executeQueryOnInitRun)
		{
			return;
		}

		executeQuery();
		_executeQueryOnInitRun = true;
	}

	private boolean _executeQueryOnInitRun = false;

	/**
	 * Test Row Count
	 *
	 * @return true if display
	 */
	private boolean testCount()
	{
		final long start = System.currentTimeMillis();
		final String dynWhere = getSQLWhere();
		final StringBuilder sql = new StringBuilder(m_sqlCount);
		if (dynWhere.length() > 0)
		{
			sql.append(dynWhere); // includes first AND
		}

		final IStringExpression sqlExpression = Services.get(IExpressionFactory.class).compile(sql.toString(), IStringExpression.class);
		final Evaluatee evalCtx = Evaluatees.ofCtx(getCtx(), getWindowNo(), false); // onlyWindow=false
		String countSql = sqlExpression.evaluate(evalCtx, true); // ignoreUnparsable=true

		countSql = Env.getUserRolePermissions().addAccessSQL(countSql, getTableName(), IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		log.trace(countSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int no = -1;
		try
		{
			pstmt = DB.prepareStatement(countSql, null);
			setParameters(pstmt, true);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				no = rs.getInt(1);
			}
		}
		catch (final Exception e)
		{
			log.error(countSql, e);
			no = -2;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		log.debug("#" + no + " - " + (System.currentTimeMillis() - start) + "ms");
		// Armen: add role checking (Patch #1694788 )

		final GridTabMaxRowsRestrictionChecker maxRowsChecker = GridTabMaxRowsRestrictionChecker.builder()
				// .setAD_Role(role) // use default role
				// .setGridTab(gridTab) // no grid tab available
				.build();
		if (maxRowsChecker.isQueryMax(no))
		{
			return ADialog.ask(p_WindowNo, getWindow(), "InfoHighRecordCount", String.valueOf(no));
		}
		return true;
	} // testCount

	/**
	 * Save Selection - Called by dispose
	 */
	protected void saveSelection()
	{
		// Already disposed
		if (p_table == null)
		{
			return;
		}

		//
		// Stop editor if any and save the value
		p_table.stopEditor(true);

		log.info("OK=" + m_ok);
		if (!m_ok)          // did not press OK
		{
			m_results.clear();
			p_table.removeAll();
			p_table = null;
			return;
		}

		// Multi Selection
		if (p_multiSelection)
		{
			m_results.addAll(getSelectedRowKeys());
		}
		else
		// singleSelection
		{
			final Integer data = getSelectedRowKey();
			if (data != null)
			{
				m_results.add(data);
			}
		}
		log.info(getSelectedSQL());

		//
		// Call column controllers to save data
		final Properties ctx = getCtx();

		final InfoWindowGridRowBuilders builders = new InfoWindowGridRowBuilders();
		for (int columnIndex = 0; columnIndex < p_layout.length; columnIndex++)
		{
			final IInfoColumnController columnController = p_layout[columnIndex].getColumnController();
			if (columnController != null)
			{
				columnController.save(ctx, p_WindowNo, builders);
			}
		}
		builders.saveToContext(ctx, p_WindowNo);

		// Save Settings of detail info screens
		pcs.firePropertyChange(PROP_SaveSelection, null, true); // metas
		saveSelectionDetail();
		// Clean-up
		p_table.removeAll();
		p_table = null;
	} // saveSelection

	/**
	 * Get the key of currently selected row
	 *
	 * @return selected key
	 */
	// metas: changed from protected to public
	public Integer getSelectedRowKey()
	{
		final ArrayList<Integer> selectedDataList = getSelectedRowKeys();
		if (selectedDataList.size() == 0)
		{
			return null;
		}
		else
		{
			return selectedDataList.get(0);
		}
	} // getSelectedRowKey

	/**
	 *
	 * @param rowIndexModel
	 * @return row key or -1
	 */
	public int getRecordId(final int rowIndexModel)
	{
		final Object data = p_table.getModel().getValueAt(rowIndexModel, m_keyColumnIndex);
		if (data instanceof IDColumn)
		{
			final IDColumn dataColumn = (IDColumn)data;
			final Integer rowKeyObj = dataColumn.getRecord_ID();
			return rowKeyObj == null ? -1 : rowKeyObj;
		}
		else if (data == null)
		{
			return -1;
		}
		else
		{
			log.error("Cannot get Record_ID from: " + data);
			return -1;
		}
	}

	/**
	 * Get the keys of selected row/s based on layout defined in prepareTable
	 *
	 * @return IDs if selection present
	 * @author ashley
	 */
	protected ArrayList<Integer> getSelectedRowKeys()
	{
		final ArrayList<Integer> selectedDataList = new ArrayList<Integer>();

		if (m_keyColumnIndex == -1)
		{
			return selectedDataList;
		}

		if (p_multiSelection)
		{
			final int rows = p_table.getRowCount();
			for (int row = 0; row < rows; row++)
			{
				// metas: refactored following code:
				// // If this is the Totals row (last row), we need to skip it - teo_sarca [ 2860556 ]
				// if (p_table.getShowTotals() && row == rows - 1)
				if (!isDataRow(row))
				{
					continue;
				}
				final Object data = p_table.getModel().getValueAt(row, m_keyColumnIndex);
				if (data instanceof IDColumn)
				{
					final IDColumn dataColumn = (IDColumn)data;
					if (dataColumn.isSelected())
					{
						selectedDataList.add(dataColumn.getRecord_ID());
					}
				}
				else if (data == null)
				{
					continue;
				}
				else
				{
					log.error("For multiple selection, IDColumn should be key column for selection");
				}
			}
		}

		if (selectedDataList.size() == 0)
		{
			final int row = p_table.getSelectedRow();
			if (row != -1 && m_keyColumnIndex != -1)
			{
				final Object data = p_table.getModel().getValueAt(row, m_keyColumnIndex);
				if (data instanceof IDColumn)
				{
					selectedDataList.add(((IDColumn)data).getRecord_ID());
				}
				if (data instanceof Integer)
				{
					selectedDataList.add((Integer)data);
				}
				if (data instanceof KeyNamePair)
				{
					selectedDataList.add(((KeyNamePair)data).getKey());
				}
			}
		}

		return selectedDataList;
	} // getSelectedRowKeys

	/**
	 * Get selected Keys
	 *
	 * @return selected keys (Integers)
	 */
	public Object[] getSelectedKeys()
	{
		if (!m_ok || m_results.size() == 0)
		{
			return null;
		}
		final Integer values[] = new Integer[m_results.size()];
		m_results.toArray(values);
		return values;
	} // getSelectedKeys;

	/**
	 * Get (first) selected Key
	 *
	 * @return selected key
	 */
	public Object getSelectedKey()
	{
		if (!m_ok || m_results.size() == 0)
		{
			return null;
		}
		return m_results.get(0);
	} // getSelectedKey

	/**
	 * Is cancelled? - if pressed Cancel = true - if pressed OK or window closed = false
	 *
	 * @return true if cancelled
	 */
	public boolean isCancelled()
	{
		return m_cancel;
	} // isCancelled

	/**
	 * Get where clause for (first) selected key
	 *
	 * @return WHERE Clause
	 */
	public String getSelectedSQL()
	{
		// No results
		final Object[] keys = getSelectedKeys();
		if (keys == null || keys.length == 0)
		{
			log.info("No Results - OK="
					+ m_ok + ", Cancel=" + m_cancel);
			return "";
		}
		//
		final StringBuilder sb = new StringBuilder(getKeyColumn());
		if (keys.length > 1)
		{
			sb.append(" IN (");
		}
		else
		{
			sb.append("=");
		}

		// Add elements
		for (int i = 0; i < keys.length; i++)
		{
			if (getKeyColumn().endsWith("_ID"))
			{
				sb.append(keys[i].toString()).append(",");
			}
			else
			{
				sb.append("'").append(keys[i].toString()).append("',");
			}
		}

		sb.replace(sb.length() - 1, sb.length(), "");
		if (keys.length > 1)
		{
			sb.append(")");
		}
		return sb.toString();
	} // getSelectedSQL;

	/**************************************************************************
	 * (Button) Action Listener & Popup Menu
	 *
	 * @param e event
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// Popup => Calculator
		if (e.getSource().equals(calcMenu))
		{
			BigDecimal number = null;
			final Object data = p_table.getSelectedValue();
			try
			{
				if (data != null)
				{
					if (data instanceof BigDecimal)
					{
						number = (BigDecimal)data;
					}
					else
					{
						number = new BigDecimal(data.toString());
					}
				}
			}
			catch (final Exception ex)
			{
			}
			final Calculator c = new Calculator(null, number);
			c.setVisible(true);
			return;
		}          // popup

		// Confirm Panel
		final String cmd = e.getActionCommand();
		if (cmd.equals(ConfirmPanel.A_OK))
		{
			dispose(true);
		}
		else if (cmd.equals(ConfirmPanel.A_CANCEL))
		{
			m_cancel = true;
			dispose(false);
		}
		//
		else if (cmd.equals(ConfirmPanel.A_HISTORY))
		{
			showHistory();
		}
		else if (cmd.equals(ConfirmPanel.A_CUSTOMIZE))
		{
			customize();
		}
		else if (cmd.equals(ConfirmPanel.A_ZOOM))
		{
			zoom();
		}
		else if (cmd.equals(ConfirmPanel.A_RESET))
		{
			doReset();
		}
		else
		{
			executeQuery();
		}
	} // actionPerformed

	/**
	 * Zoom to target
	 *
	 * @param AD_Window_ID window id
	 * @param zoomQuery zoom query
	 */
	protected void zoom(final int AD_Window_ID, final MQuery zoomQuery)
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		final AWindow frame = new AWindow();
		if (!frame.initWindow(AD_Window_ID, zoomQuery))
		{
			return;
		}
		AEnv.addToWindowManager(frame);
		handleModalZoomUI();

		// VLookup gets info after method finishes
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					sleep(50);
				}
				catch (final Exception e)
				{
				}
				AEnv.showCenterScreenOrMaximized(frame);
			}
		}.start();
	} // zoom

	private void handleModalZoomUI()
	{
		final Window window = getWindow();

		if (window instanceof JDialog)
		{
			final JDialog dialog = (JDialog)window;

			// Modal Window causes UI lock
			if (dialog.isModal())
			{
				dialog.setModal(false); // remove modal option has no effect
				dispose();
			}
			else
			{
				setCursor(Cursor.getDefaultCursor());
			}
		}
		else
		{
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	 * Dispose (not OK)
	 */
	public final void dispose()
	{
		dispose(false);
	} // dispose

	private boolean disposing = false;
	private boolean disposed = false;

	/**
	 * Dispose and save Selection
	 *
	 * @param ok OK pressed
	 */
	public final void dispose(final boolean ok)
	{
		if (disposing || disposed)
		{
			// Info window is currently dispossing or it was already disposed
			return;
		}

		disposing = true;
		try
		{
			log.info("OK=" + ok);
			m_ok = ok;

			// End Worker
			stopWorker();

			//
			// Save Selection (only if user did not press Cancel)
			if (ok)
			{
				saveSelection();
			}

			if (_window != null)
			{
				_window.removeAll();
				_window.dispose();
			}
			_window = null;

			// 05203: In case the windowNo was created locally, we need cleanup the context for it and to remove the window
			if (localWindowNo)
			{
				Env.clearWinContext(getCtx(), p_WindowNo);
			}
		}
		finally
		{
			disposing = false;
			disposed = true;
		}
	} // dispose

	/**
	 *
	 * @return true if this window is currently disposing or it was already disposed
	 */
	public final boolean isDisposed()
	{
		return disposing || disposed;
	}

	private void stopWorker()
	{
		if (m_worker != null)
		{
			// worker continues, but it does not block UI
			if (m_worker.isAlive())
			{
				m_worker.interrupt();
				try
				{
					m_worker.join();
				}
				catch (final InterruptedException e)
				{
					// nothing
				}
			}
			log.info("Worker alive=" + m_worker.isAlive());
		}
		m_worker = null;
	}

	public IMiniTable getMiniTable()
	{
		return p_table;
	}

	/**
	 * Get Table name Synonym
	 *
	 * @return table name
	 */
	protected String getTableName()
	{
		return p_tableName;
	} // getTableName

	/**
	 * Get Key Column Name
	 *
	 * @return column name
	 */
	protected String getKeyColumn()
	{
		return p_keyColumn;
	} // getKeyColumn

	protected final boolean isMultiSelection()
	{
		return p_multiSelection;
	}

	/**************************************************************************
	 * Table Selection Changed
	 *
	 * @param e event
	 */
	@Override
	public void valueChanged(final ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
		{
			return;
		}
		enableButtons();
	} // calueChanged

	/**
	 * Enable OK, History, Zoom if row/s selected --- Changes: Changed the logic for accomodating multiple selection
	 *
	 * @author ashley
	 */
	protected void enableButtons()
	{
		final boolean enable = p_table.getSelectedRowCount() == 1;
		confirmPanel.getOKButton().setEnabled(p_table.getSelectedRowCount() > 0);

		if (hasHistory())
		{
			confirmPanel.getHistoryButton().setEnabled(enable);
		}
		if (hasZoom())
		{
			confirmPanel.getZoomButton().setEnabled(enable);
		}
	} // enableButtons

	/**************************************************************************
	 * Get dynamic WHERE part of SQL To be overwritten by concrete classes
	 *
	 * @return WHERE clause
	 */
	protected abstract String getSQLWhere();

	/**
	 * Set Parameters for Query To be overwritten by concrete classes
	 *
	 * @param pstmt statement
	 * @param forCount for counting records
	 * @throws SQLException
	 */
	protected abstract void setParameters(PreparedStatement pstmt, boolean forCount)
			throws SQLException;

	/**
	 * Reset Parameters To be overwritten by concrete classes
	 */
	protected void doReset()
	{
	}

	/**
	 * Has Reset (false) To be overwritten by concrete classes
	 *
	 * @return true if it has reset (default false)
	 */
	protected boolean hasReset()
	{
		return false;
	}

	/**
	 * History dialog To be overwritten by concrete classes
	 */
	protected void showHistory()
	{
	}

	/**
	 * Has History (false) To be overwritten by concrete classes
	 *
	 * @return true if it has history (default false)
	 */
	protected boolean hasHistory()
	{
		return false;
	}

	/**
	 * Customize dialog To be overwritten by concrete classes
	 */
	protected void customize()
	{
	}

	/**
	 * Has Customize (false) To be overwritten by concrete classes
	 *
	 * @return true if it has customize (default false)
	 */
	protected boolean hasCustomize()
	{
		return false;
	}

	/**
	 * Zoom action To be overwritten by concrete classes
	 */
	protected void zoom()
	{
	}

	/**
	 * Has Zoom (false) To be overwritten by concrete classes
	 *
	 * @return true if it has zoom (default false)
	 */
	protected boolean hasZoom()
	{
		return false;
	}

	/**
	 * Save Selection Details To be overwritten by concrete classes
	 */
	protected void saveSelectionDetail()
	{
	}

	/**
	 * Get Zoom Window
	 *
	 * @param tableName table name
	 * @param isSOTrx sales trx
	 * @return AD_Window_ID
	 */
	protected int getAD_Window_ID(final String tableName, final boolean isSOTrx)
	{
		if (!isSOTrx && m_PO_Window_ID > 0)
		{
			return m_PO_Window_ID;
		}
		if (m_SO_Window_ID > 0)
		{
			return m_SO_Window_ID;
		}
		//
		final String sql = "SELECT AD_Window_ID, PO_Window_ID FROM AD_Table WHERE TableName=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_SO_Window_ID = rs.getInt(1);
				m_PO_Window_ID = rs.getInt(2);
			}
		}
		catch (final Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		if (!isSOTrx && m_PO_Window_ID > 0)
		{
			return m_PO_Window_ID;
		}
		return m_SO_Window_ID;
	} // getAD_Window_ID

	/**
	 *
	 * @return Index of Key Column
	 */
	protected int getKeyColumnIndex()
	{
		return m_keyColumnIndex;
	}

	/**
	 *
	 * @return true if OK button was pressed
	 */
	public boolean isOkPressed()
	{
		return m_ok;
	}

	/**
	 *
	 * @return true if Cancel button was pressed
	 */
	public boolean isCancelPressed()
	{
		return m_cancel;
	}

	/**
	 * Specify if the records should be checked(selected) by default. (for multi-selection only)
	 *
	 * @param value
	 */
	public void setDefaultSelected(final boolean value)
	{
		p_isDefaultSelected = value;
	}

	/**
	 * (for multi-selection only)
	 *
	 * @return true if records are selected by default
	 */
	public boolean isDefaultSelected()
	{
		return p_isDefaultSelected;
	}

	/**
	 * (for multi-selection only)
	 *
	 * @param value true if double click should toggle record selection
	 */
	public void setDoubleClickTogglesSelection(final boolean value)
	{
		p_doubleClickTogglesSelection = value;
	}

	/**
	 * (for multi-selection only)
	 *
	 * @return true if double click should toggle record selection
	 */
	public boolean isDoubleClickTogglesSelection()
	{
		return p_doubleClickTogglesSelection;
	}

	/**************************************************************************
	 * Mouse Clicked
	 *
	 * @param e event
	 */
	@Override
	public void mouseClicked(final MouseEvent e)
	{
		// log.debug( "Info.mouseClicked",
		// "ClickCount=" + e.getClickCount() + ", Right=" + SwingUtilities.isRightMouseButton(e)
		// + ", r=" + m_table.getSelectedRow() + ", c=" + m_table.getSelectedColumn());

		// Double click with selected row => exit/zoom
		if (e.getClickCount() > 1 && p_table.getSelectedRow() != -1)
		{
			if (p_WindowNo == 0)
			{
				zoom();
			}
			else if (p_multiSelection && isDoubleClickTogglesSelection())
			{
				if (m_keyColumnIndex >= 0)
				{
					final Object data = p_table.getValueAt(p_table.getSelectedRow(), m_keyColumnIndex);
					if (data instanceof IDColumn)
					{
						final IDColumn id = (IDColumn)data;
						id.setSelected(!id.isSelected());
						p_table.setValueAt(data, p_table.getSelectedRow(), m_keyColumnIndex);
					}
				}
			}
			else
			{
				dispose(true);
			}
		}
		// Right Click => start Calculator
		else if (SwingUtilities.isRightMouseButton(e))
		{
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	} // mouseClicked

	@Override
	public void mousePressed(final MouseEvent e)
	{
		// nothing at this level
	}

	@Override
	public void mouseReleased(final MouseEvent e)
	{
		// nothing at this level
	}

	@Override
	public void mouseEntered(final MouseEvent e)
	{
		// nothing at this level
	}

	@Override
	public void mouseExited(final MouseEvent e)
	{
		// nothing at this level
	}

	/**
	 * Worker
	 */
	class Worker extends Thread
	{
		private PreparedStatement m_pstmt = null;
		private ResultSet m_rs = null;

		/**
		 * Make sure execution only initializes sorting preferences once.
		 */
		private boolean sortingPreferencesInitialized = false;

		/**
		 * Do Work (load data). This method repeatedly calls {@link #loadData()}, until it mananages to finish before another thread requests another run by calling {@link Info#executeQuery()}.
		 *
		 */
		@Override
		public void run()
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));	// will be reset in the finally block
			try
			{
				do
				{
					setStatusLine(Services.get(IMsgBL.class).getMsg(getCtx(), "StartSearch"), false);
					loadData(); // do the actual work
				}
				while (isWorkerRepeatLoadData()); // task 08775: when loadData is done, we check if the work needs to be repeated
			}
			catch (final Throwable t)
			{
				// task 08775: make sure that upon exit we have the flag in a consistent state, also in case of a throwable. In the normal case, this is done by isWorkerRepeatLoadData().
				_workerRepeatLoadData.lock();
				try
				{
					_workerIsRunning = false;
					throw t;
				}
				finally
				{
					_workerRepeatLoadData.unlock();
				}
			}
			finally
			{
				// restore the wait cursor
				setCursor(Cursor.getDefaultCursor());
			}
		} // run

		private void loadData()
		{
			// task 08672: moved the testcount() invocation into this run method, because it is also called from executeQuery()
			// and also accesses the where-clause and stuff; this way, we have it all in the same thread and don't need to worry
			// about synchronization and non-volatile issues
			if (!testCount())
			{
				return;
			}

			// metas begin: if the p_table is null, do not allow to go forward
			if (p_table == null)
			{
				return;
			}
			// metas end

			final IMsgBL msgBL = Services.get(IMsgBL.class);
			final Properties ctx = getCtx();

			// setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			// setStatusLine(Services.get(IMsgBL.class).getMsg(getCtx(), "StartSearch"), false);
			final long start = System.currentTimeMillis();

			// Clear Table
			p_table.setRowCount(0);
			//
			final String dynWhere = getSQLWhere();
			final StringBuilder sql = new StringBuilder(getSQLSelect()); // metas: use getter
			if (dynWhere.length() > 0)
			{
				sql.append(dynWhere); // includes first AND
			}
			sql.append(m_sqlOrder);
			String dataSql = msgBL.parseTranslation(ctx, sql.toString()); // Variables
			dataSql = Env.getUserRolePermissions().addAccessSQL(dataSql, getTableName(), IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
			log.trace(dataSql);

			//
			// Make sure table is not editable while we are loading
			final boolean editableOld = p_table.isEditable();
			p_table.setEditable(false);

			try
			{
				final PreparedStatement m_pstmt = DB.prepareStatement(dataSql, ITrx.TRXNAME_None);
				this.m_pstmt = m_pstmt;
				setParameters(m_pstmt, false); // no count

				log.debug("Start query - " + (System.currentTimeMillis() - start) + "ms");
				final ResultSet m_rs = m_pstmt.executeQuery();
				this.m_rs = m_rs;
				log.debug("End query - " + (System.currentTimeMillis() - start) + "ms");

				while (m_rs != null && m_rs.next())
				{
					if (isInterrupted())
					{
						log.trace("Interrupted");
						close();
						return;
					}

					final int row = p_table.getRowCount();
					int colOffset = 1; // columns start with 1

					//
					// Load row
					final Object[] rowData = new Object[p_layout.length];
					int rowRecordId = -1;
					for (int col = 0; col < p_layout.length; col++)
					{
						Object data = null;
						final Class<?> c = p_layout[col].getColClass();
						final int colIndex = col + colOffset;
						if (c == IDColumn.class)
						{
							rowRecordId = m_rs.getInt(colIndex);
							data = new IDColumn(rowRecordId);
							if (p_multiSelection)
							{
								((IDColumn)data).setSelected(isDefaultSelected());
							}
						}
						else if (c == Boolean.class)
						{
							data = new Boolean("Y".equals(m_rs.getString(colIndex)));
						}
						else if (c == Timestamp.class)
						{
							data = m_rs.getTimestamp(colIndex);
						}
						else if (c == BigDecimal.class)
						{
							data = m_rs.getBigDecimal(colIndex);
						}
						else if (c == Double.class)
						{
							data = new Double(m_rs.getDouble(colIndex));
						}
						else if (c == Integer.class)
						{
							data = new Integer(m_rs.getInt(colIndex));
						}
						else if (c == KeyNamePair.class)
						{
							final String display = m_rs.getString(colIndex);
							final int key = m_rs.getInt(colIndex + 1);
							data = new KeyNamePair(key, display);
							colOffset++;
						}
						else
						{
							data = m_rs.getString(colIndex);
						}
						// store
						rowData[col] = data;
						// p_table.setValueAt(data, row, col);
						// log.debug( "r=" + row + ", c=" + col + " " + m_layout[col].getColHeader(),
						// "data=" + data.toString() + " " + data.getClass().getName() + " * " +
						// m_table.getCellRenderer(row, col));
					}

					//
					// Store row data to table model
					setIgnoreLoading(false);
					p_table.setRowCount(row + 1);
					for (int col = 0; col < p_layout.length; col++)
					{
						final Object data = rowData[col];
						p_table.setValueAt(data, row, col);
					}

					//
					// Call controller after load
					setIgnoreLoading(true);
					for (int col = 0; col < p_layout.length; col++)
					{
						final IInfoColumnController columnController = p_layout[col].getColumnController();
						if (columnController == null)
						{
							continue;
						}

						final Object data = rowData[col];
						final Object dataConv = columnController.gridConvertAfterLoad(p_layout[col], row, rowRecordId, data);
						if (Util.same(data, dataConv))
						{
							// nothing changed
							continue;
						}

						p_table.setValueAt(dataConv, row, col);
					}

				}
			}
			catch (final SQLException e)
			{
				log.error(dataSql, e);
				throw AdempiereException.wrapIfNeeded(e);
			}
			finally
			{
				close();

				if (p_table != null)
				{
					p_table.setEditable(editableOld);
				}
			}

			//
			final int no = p_table.getRowCount();
			log.debug("#" + no + " - " + (System.currentTimeMillis() - start) + "ms");
			if (p_table.getShowTotals())
			{
				p_table.addTotals(p_layout);
			}

			//
			// Initialize sorting preferences
			if (!sortingPreferencesInitialized)
			{
				initSortingPreferences(ctx);
				sortingPreferencesInitialized = true;
			}

			if (!p_table.isInitialSortIndexesEmpty())
			{
				// task 08329: Sort after table was loaded
				// task 08416: ...but only if there are sort settings. If not, it means that the table is *already* sorted by the info window's ORDER BY clause.
				p_table.sort();
			}
			p_table.autoSize();
			p_table.repaint();

			//
			setStatusLine(Integer.toString(no) + " " + msgBL.getMsg(ctx, "SearchRows_EnterQuery"), false);
			setStatusDB(Integer.toString(no));
			if (no == 0)
			{
				log.debug(dataSql);
			}
			else
			{
				selectRowAfterLoad();
				if (confirmPanel.getRefreshButton().hasFocus())
				{
					p_table.requestFocus(); // only if we clicked on the refresh button; otherwise (assuming fields triggered it, do not change focus)
				}
			}
		}

		/**
		 * Close ResultSet and Statement
		 */
		private void close()
		{
			DB.close(m_rs, m_pstmt);
			m_rs = null;
			m_pstmt = null;
		}

		/**
		 * Interrupt this thread - cancel the query if still in execution Carlos Ruiz - globalqss - [2826660] - Info product performance BIG problem
		 */
		@Override
		public void interrupt()
		{
			if (m_pstmt != null)
			{
				try
				{
					m_pstmt.cancel();
				}
				catch (final SQLException e)
				{
					log.error("Cannot cancel SQL statement", e);
				}
				finally
				{
					close();
				}
			}
			super.interrupt();
		}

		@Override
		public String toString()
		{
			return String.format("Worker [m_pstmt=%s, m_rs=%s, sortingPreferencesInitialized=%s]", m_pstmt, m_rs, sortingPreferencesInitialized);
		}
	} // Worker

	/**
	 * task 09961
	 *
	 * Method to tell which row is to be selected after loading.
	 * By default, the selected row will be the first one
	 */
	protected void selectRowAfterLoad()
	{
		p_table.getSelectionModel().setSelectionInterval(0, 0);
	}

	/**
	 * fresh 08329: Initialize AD_User sorting preferences
	 *
	 * @param ctx
	 */
	protected final void initSortingPreferences(final Properties ctx)
	{
		//
		// Services
		final IUserSortPrefDAO userSortPrefDAO = Services.get(IUserSortPrefDAO.class);

		final int loginUserId = Env.getAD_User_ID(ctx);
		final I_AD_User loginUser = InterfaceWrapperHelper.create(ctx, loginUserId, I_AD_User.class, ITrx.TRXNAME_None);
		final String action = SORTPREF_Action;
		final int infoWindowId = getAD_InfoWindow_ID();
		if (infoWindowId <= 0)
		{
			return;
		}

		// index of sort-column to sort direction. Note that it is a linked map, so the order of the sort column indexes is also stored in it.
		final LinkedHashMap<Integer, Boolean> initialSortIndexes2Direction = new LinkedHashMap<Integer, Boolean>();

		//
		// fresh 08329: If in conference mode, then retrieve conference sort preferences, and not the user's
		final List<I_AD_User_SortPref_Line> sortPreferenceLines;
		if (isConferenceSortPreferences)
		{
			sortPreferenceLines = userSortPrefDAO.retrieveConferenceSortPreferenceLines(ctx, action, infoWindowId);
		}
		else
		{
			sortPreferenceLines = userSortPrefDAO.retrieveSortPreferenceLines(loginUser, action, infoWindowId);
		}

		for (final I_AD_User_SortPref_Line sortPreferenceLine : sortPreferenceLines)
		{
			//
			// Get model column index by the preference's field
			final int modelColumnIndex;
			{
				final I_AD_InfoColumn column = sortPreferenceLine.getAD_InfoColumn();
				final String columnName = column.getColumnName();

				modelColumnIndex = p_table.getColumnModelIndex(columnName);
				if (modelColumnIndex < 0 || modelColumnIndex > p_table.getColumnCount() - 1)         // make sure index is right
				{
					final Exception ex = new AdempiereException("Model column out of bounds: {}", new Object[] { columnName });
					log.warn(ex.getLocalizedMessage(), ex);
					continue;
				}
			}

			final boolean isAscending = sortPreferenceLine.isAscending();

			initialSortIndexes2Direction.put(modelColumnIndex, isAscending);

			//
			// Configure product sorting which takes precedence to normal sorting (user-defined sequencing for first N products)
			final List<Object> precedenceProductNames = new ArrayList<>();

			final List<I_AD_User_SortPref_Line_Product> sortPreferenceLineProducts = userSortPrefDAO.retrieveSortPreferenceLineProducts(sortPreferenceLine);
			for (final I_AD_User_SortPref_Line_Product sortPreferenceLineProduct : sortPreferenceLineProducts)
			{
				final I_M_Product product = sortPreferenceLineProduct.getM_Product();
				// FRESH-438: check for null and give an informative message if the product is null
				if (product == null)
				{
					final String msg = "Missing M_Product for M_Product_ID=" + sortPreferenceLineProduct.getM_Product_ID()
							+ "\n in AD_User_SortPref_Line_Product=" + sortPreferenceLineProduct
							+ "\n in I_AD_User_SortPref_Line_ID=" + sortPreferenceLine.getAD_User_SortPref_Line_ID() + " (SeqNo=" + sortPreferenceLine.getSeqNo() + ")"
							+ "\n in AD_User_SortPref_Hdr_ID= " + sortPreferenceLine.getAD_User_SortPref_Hdr_ID()
							+ "\n isConferenceSortPreferences=" + isConferenceSortPreferences + ";"
							+ " action=" + action + ";"
							+ " infoWindowId=" + infoWindowId + ";";

					new AdempiereException(msg)
							.throwIfDeveloperModeOrLogWarningElse(log);
					continue;
				}
				final String productName = product.getName();
				precedenceProductNames.add(productName);
			}

			//
			// Configure and add product comparator
			if (precedenceProductNames.isEmpty())
			{
				p_table.setSortIndexComparator(modelColumnIndex, null);
			}
			else
			{
				final MSort sort = new MSort(0, null);
				sort.setSortAsc(isAscending);

				final Comparator<Object> fixedPrecedenceComparator = new FixedPrecedenceComparator(sort, modelColumnIndex, precedenceProductNames);
				p_table.setSortIndexComparator(modelColumnIndex, fixedPrecedenceComparator);
			}
		}
		p_table.setInitialSortIndexes(initialSortIndexes2Direction);
	}

	/**
	 * Compares with the inner data vector of the model
	 *
	 * @author al
	 */
	private static class FixedPrecedenceComparator implements Comparator<Object>
	{
		private final MSort sort;
		private final int modelColumnIndex;
		private final List<Object> precedenceObjects;

		public FixedPrecedenceComparator(final MSort sort, final int modelColumnIndex, final List<Object> precedenceObjects)
		{
			super();

			this.sort = sort;
			this.modelColumnIndex = modelColumnIndex;
			this.precedenceObjects = precedenceObjects;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public int compare(final Object o1, final Object o2)
		{
			final Object item1 = ((Vector)o1).get(modelColumnIndex);
			final Object item2 = ((Vector)o2).get(modelColumnIndex);

			if (precedenceObjects.contains(item1)
					&& precedenceObjects.contains(item2))
			{
				return precedenceObjects.indexOf(item1) - precedenceObjects.indexOf(item2); // see within our ordered list
			}
			else if (precedenceObjects.contains(item1))
			{
				return -1; // item1 on list, item2 remains with the rest
			}
			else if (precedenceObjects.contains(item2))
			{
				return 1; // item2 on the list, item1 goes with the rest
			}
			else
			{
				return sort.compare(item1, item2);
			}
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			final FixedPrecedenceComparator other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}

			return new EqualsBuilder()
					.append(modelColumnIndex, other.modelColumnIndex)
					.append(precedenceObjects, other.precedenceObjects)
					.isEqual();
		}

		@Override
		public String toString()
		{
			return String.format("FixedPrecedenceComparator [sort=%s, modelColumnIndex=%s, precedenceObjects=%s]", sort, modelColumnIndex, precedenceObjects);
		}
	}

	// metas: begin
	// metas: loaded layout to allow optional tree on the left window side
	// (-> product category tree in product info)
	private JSplitPane splitPane;

	/**
	 * Injects the tree panel component in Info window layout.
	 *
	 * Under the hood, this method will create a split pane, will add the grid table in it's right component and it will add the tree panel in it's left component. We are creating the split pane only
	 * when we know that we have to display something in it's left side (i.e. the tree panel) because else it will draw some borders which are not looking nice.
	 *
	 * @param treePanel tree panel component to be set in the left side of the split pane.
	 * @param dividerLocation split pane's divider location
	 */
	protected final void setTreePanel(final Component treePanel, final int dividerLocation)
	{
		if (treePanel == null)
		{
			return;
		}

		//
		// Create the split pane if not already created.
		// This will involve layout changes.
		if (splitPane == null)
		{
			final Container contentPane = getContentPane();
			contentPane.add(parameterPanel, BorderLayout.PAGE_START);

			final JPanel centerAndSouth = new JPanel();
			centerAndSouth.setLayout(new GridBagLayout());

			final GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1;
			c.weighty = 1;
			centerAndSouth.add(scrollPane, c);

			c.gridx = 0;
			c.gridy = 1;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0;
			c.weighty = 0;
			centerAndSouth.add(southPanel, c);
			centerAndSouth.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));

			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			splitPane.setBorder(BorderFactory.createEmptyBorder());
			splitPane.add(centerAndSouth, JSplitPane.RIGHT);
			contentPane.add(splitPane, BorderLayout.CENTER);
		}

		// Set the tree panel in split pane's left side.
		splitPane.add(treePanel, JSplitPane.LEFT);

		// Set the split pane's divider location.
		if (dividerLocation >= 0)
		{
			splitPane.setDividerLocation(dividerLocation);
		}
	}

	private Container getContentPane()
	{
		final Window window = getWindow();
		Check.assumeInstanceOf(window, RootPaneContainer.class, "RootPaneContainer (i.e JDialog, JFrame)");
		final RootPaneContainer rootPaneContainer = (RootPaneContainer)window;
		return rootPaneContainer.getContentPane();
	}

	protected String getSQLSelect()
	{
		return m_sqlMain;
	}

	protected void setKeyColumnIndex(final int keyColumnIndex)
	{
		m_keyColumnIndex = keyColumnIndex;
	}

	/**
	 *
	 * @param row
	 * @return true if given row is a data row (e.g. not a totals row)
	 */
	public boolean isDataRow(final int row)
	{
		if (row < 0)
		{
			return false;
		}

		final int rows = p_table.getRowCount();
		// If this is the Totals row (last row), we need to skip it - teo_sarca [ 2860556 ]
		if (p_table.getShowTotals() && row == rows - 1)
		{
			return false;
		}
		return row < rows;
	}

	public static final String PROP_SaveSelection = "SaveSelection";
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public IInfoColumnController getInfoColumnController(final int index)
	{
		return p_layout[index].getColumnController();
	}

	public boolean isLoading()
	{
		if (m_worker == null)
		{
			return false;
		}

		// Check for override.
		if (ignoreLoading)
		{
			return false;
		}
		return m_worker.isAlive();
	}

	// metas: end

	public void setIgnoreLoading(final boolean ignoreLoading)
	{
		this.ignoreLoading = ignoreLoading;
	}

	private static final Properties getCtx()
	{
		return Env.getCtx();
	}

	private static final int getCtxAD_Client_ID()
	{
		return Env.getAD_Client_ID(getCtx());
	}
} // Info
